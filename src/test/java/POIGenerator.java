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
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.spatial.model.Point;

import com.jayway.jsonpath.JsonPath;

public class POIGenerator {

	static JedisPool jedispool = new JedisPool("172.19.114.204", 19000);
	static Jedis jedis;
	String dir;
	String key = "aservice";

	public static void main(String[] args) throws IOException {
		jedis = jedispool.getResource();
		POIGenerator pg = new POIGenerator();
		// pg.execute();
		pg.getTest();

		jedispool.destroy();

	}

	double[] xs = { 37.56322, 37.56323, 37.56324, 37.56325, 37.56326, 37.56327, 37.56328, 37.56329, 37.56330, 37.56331 };
	double[] ys = { 127.01508, 127.01509, 127.01510, 127.01511, 127.01512, 127.01513, 127.01514, 127.01515, 127.01516, 127.01517 };

	private void getTest() {
		jedis = jedispool.getResource();
		try {
			for (int idx = 0; idx < xs.length; idx++) {
				List<Point<String>> result = jedis.gprangeByRadius(key, xs[idx], ys[idx], 1, UNITS.KM);
				List<Point<String>> result2 = jedis.gprangeByRadius(key, xs[idx], ys[idx], 1, UNITS.KM, "*");
				System.out.println(idx);
				if (result.size() != result2.size()) {
					throw new JedisDataException(" not equals ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedispool.returnResource(jedis);
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
		try {
			String next = JsonPath.read(jsonStr, "$.paging.next");
			System.out.println("next key :" + next);
		} catch (Exception ex) {
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
		memberKey = "poi_id:" + poi_id + "|name:" + name + "|address:" + address + "|category:" + category;
		System.out.println(key + ", " + lat + ", " + lot + ", " + memberKey + ", " + jo.toJSONString());
		jedis.gpadd(key, lat, lot, memberKey, jo.toJSONString());
	}

}
