import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Point;

import com.jayway.jsonpath.JsonPath;

public class POIGenerator {

	Jedis jedis = new Jedis("172.19.114.204", 19000);
	String dir;
	String key = "aservice";
	

	public static void main(String[] args) throws IOException {
		POIGenerator pg = new POIGenerator();
		pg.execute();
//		pg.getTest();
		
	}

	private void getTest() {
		Iterator<Point<String>> result = jedis.grangeByRadius(key, 37.56322, 127.01508, 100, UNITS.KM, "*").iterator();
		while(result.hasNext()){
			Point<String> p = result.next();
			System.out.println(p.getMember() + ", lat :"+ p.getX() + ", lot :" + p.getY() + ", values :"+ p.getValue());
		}
		
	}

	public POIGenerator() {
		this.dir = "/Users/horanghi/pois/";
		jedis.auth("a1234");
	}

	public POIGenerator(String files) {
		this.dir = dir;
	}

	public void execute() {
		loadJsonFormFiles(this.dir);
	}

	public void loadJsonFormFiles(String source) {
		File dir = new File(source);
		File[] fileList = dir.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			File file = fileList[i];
			if (file.isFile()) {
				parsenSave(loadConfigGetJson(file.getAbsolutePath()));
			}
		}
	}

	public String loadConfigGetJson(String filePathName) {
		String jsonStr = null;
		BufferedReader in = null;
		try {
			File jsonfile = new File(filePathName);
			in = new BufferedReader(new InputStreamReader(new FileInputStream(jsonfile), "UTF8"));

			StringBuilder builder = new StringBuilder();
			int ch;
			while ((ch = in.read()) != -1) {
				builder.append((char) ch);
			}

			jsonStr = builder.toString();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return jsonStr;

	}

	public boolean parsenSave(final String jsonStr) {
		JSONArray values = JsonPath.read(jsonStr, "$.pois.[0:30]");
		try{
			String next = JsonPath.read(jsonStr, "$.paging.next");
			System.out.println("next key :"+next);
		}catch(Exception ex){
		}
		
		Iterator objs = values.iterator();
		
		while (objs.hasNext()) {
			JSONObject jo = new JSONObject((Map) objs.next());
			parse(jo);
		}

		return true;
	}

	private void parse(JSONObject jo) {
		double lot = JsonPath.read(jo, "$.location.coordinates.[0]");
		double lat = JsonPath.read(jo, "$.location.coordinates.[1]");
		int poi_id = JsonPath.read(jo, "$.poi_id");
		String memberKey;
		String name = JsonPath.read(jo, "$.name");
		String category = JsonPath.read(jo, "$.category.name");
		String address = JsonPath.read(jo, "$.address");
		memberKey = "poi_id:"+poi_id+"|name:" + name + "|address:" + address + "|category:" + category;
		System.out.println(key +", "+ lat+", "+ lot+", "+ memberKey+", "+ jo.toJSONString());
		jedis.gadd(key, lat, lot, memberKey, jo.toJSONString());
	}
	
}
