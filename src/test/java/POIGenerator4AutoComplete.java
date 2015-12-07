import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minidev.json.JSONObject;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.LineStringRange;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

import com.jayway.jsonpath.JsonPath;

public class POIGenerator4AutoComplete {

	static JedisPool jedispool = new JedisPool(new GenericObjectPoolConfig(), "172.19.114.206", 29009, 100000, "svc07_01");
	static Jedis staticJedis;
	String dir;
	static String key = "autocomplete";

	public static void main(String[] args) throws IOException {
		// long s = System.currentTimeMillis();
		POIGenerator4AutoComplete pg = new POIGenerator4AutoComplete();
		pg.execute();

		/*
		 * 2) "33.118250000000003" "126.2666"
		 */
		// jedis.ggadd("mygg", "polygon", "polygon_value", new Polygon<String>(33.118250000000002, 126.26659, 33.118250000000004, 126.26659,
		// 33.118250000000004, 126.26661, 33.118250000000002, 126.26661, 33.118250000000002, 126.26659));

		long s = System.currentTimeMillis();
		// List<Point<String>> result = jedis.gpradius("autocomplete", 37.566404, 126.985037, 1000, UNITS.M, "*세븐일레븐*");

		// List<Point<String>> result = staticJedis.gpradius(key, 37.566404, 126.985037, 100, UNITS.M, "*스타벅스*");
		// System.out.println(result.size());
		// result = staticJedis.gpradius(key, 37.566404, 126.985037, 1000, UNITS.M, "*스타벅스*");
		// System.out.println(result.size());
		// result = staticJedis.gpradius(key, 37.566404, 126.985037, 1000, UNITS.M, "*은행*");
		// System.out.println(result.size());
		//
		// System.out.println("스타벅수 count : " + result.size());
		// long e = System.currentTimeMillis();
		// System.out.println("time : " + (e - s));
		//
		// result = staticJedis.gpregion(key, new LineStringRange(new LineString<String>(37.566681, 126.982724, 37.566723, 126.987391), 100,
		// UNITS.M));
		// System.out.println("line range count : " + result.size());
		// for (Point<String> p : result) {
		// System.out.println(p);
		// }
		// Long seed = (Long) jedis.gpcard("autocomplete");
		// Random r = new Random();
		// long num = r.nextInt(seed.intValue());
		// System.out.println(num);
		// System.out.println(jedis.gprange("autocomplete", num, num).size());

		// jedis.gpupdate("autocomplete", "세븐일레븐 명동채원점", 10);
		//
		// result = jedis.gpregion("autocomplete", new Polygon<String>(37.664662, 126.283428, 37.664662, 127.635342, 37.398235, 127.263163,
		// 37.334278, 126.655606, 37.664662, 126.283428), "-inf", "+inf", "*");
		// System.out.println("All count : " + result.size());

		// System.out.println(jedis.gpregionByMember("autocomplete", "mygg", "polygon").get(0));

		// e = System.currentTimeMillis();
		// System.out.println("time : " + (e - s));

		jedispool.returnResource(staticJedis);
		jedispool.destroy();

	}

	double[] xs = { 37.56322, 37.56323, 37.56324, 37.56325, 37.56326, 37.56327, 37.56328, 37.56329, 37.56330, 37.56331 };
	double[] ys = { 127.01508, 127.01509, 127.01510, 127.01511, 127.01512, 127.01513, 127.01514, 127.01515, 127.01516, 127.01517 };

	private void getTest() {

		staticJedis = jedispool.getResource();
		List<Point<String>> result = staticJedis.gpradius(key, 37.271526, 127.12666, 10, UNITS.M, "*");
		System.out.println(result.size());
		result = staticJedis.gpradius(key, 37.271526, 127.12666, 100, UNITS.M, "*");
		System.out.println(result.size());
		result = staticJedis.gpradius(key, 37.271526, 127.12666, 1000, UNITS.M, "*");
		System.out.println(result.size());
		result = staticJedis.gpradius(key, 37.271526, 127.12666, 10000, UNITS.M, "*");
		System.out.println(result.size());
		// jedis.gpupdate(key, "gs25ㅁㅏㄹㅏㄷㅗㅈㅓㅁ gs25 마라도점", 19);

		result = staticJedis.gpnn(key, 37.271526, 127.12666, 0, 3, "*");
		System.out.println(result.size());
		result = staticJedis.gpnn(key, 37.271526, 127.12666, 0, 15, "*");
		System.out.println(result.size());
		result = staticJedis.gpnn(key, 37.271526, 127.12666, 0, 27, "*");
		System.out.println(result.size());
		result = staticJedis.gpnn(key, 37.271526, 127.12666, 0, 2022, "*");
		System.out.println(result.size());

		// 0
		// 3
		// 15
		// 27
		// 2022
		jedispool.returnBrokenResource(staticJedis);
	}

	public POIGenerator4AutoComplete() {
		this.dir = "/Users/horanghi/autocomplete/";
	}

	public POIGenerator4AutoComplete(String files) {
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
			System.out.println(file.getName());
			if (file.isFile()) {
				// parsenSave(loadConfigGetJson(file.getAbsolutePath()));
				parsenSave(loadConfigGetDelimeter(file.getAbsolutePath()));

			}
		}
	}

	public String loadConfigGetDelimeter(String filePathName) {
		String statementStr = null;
		BufferedReader in = null;
		try {
			File jsonfile = new File(filePathName);
			in = new BufferedReader(new InputStreamReader(new FileInputStream(jsonfile), "UTF8"));

			StringBuilder builder = new StringBuilder();
			int ch;
			while ((ch = in.read()) != -1) {
				builder.append((char) ch);
			}

			statementStr = builder.toString();

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
		return statementStr;

	}

	public boolean parsenSave(final String statementStr) {
		String[] _valuelist = statementStr.split("\n");
		ArrayList<String> valuelist = new ArrayList<String>();
		for (String value : _valuelist) {
			valuelist.add(value);
		}
		Iterator<String> it = valuelist.iterator();
		int idx = 0;
		Jedis jedis = null;
		Pipeline pl = null;
		jedis = jedispool.getResource();

		while (it.hasNext()) {
			if (pl == null) {
				pl = jedis.pipelined();
			}
			idx++;
			String[] eles = it.next().split("\t");
			String mkey = eles[1];
			String value = eles[0] + " " + eles[1];

			if (eles[2].indexOf("[") == -1) {
				continue;
			}

			ArrayList<String> latlons = latlons = parseLocation(eles[2]);
			double score = Double.valueOf(eles[3]);
			if (latlons.size() > 2) {
				Iterator<String> latloniter = latlons.iterator();
				while (latloniter.hasNext()) {
					String lat = latloniter.next();
					String lon = latloniter.next();
					pl.gpadd(key, Double.valueOf(lat), Double.valueOf(lon), mkey, value, score);
				}
			} else {
				pl.gpadd(key, Double.valueOf(latlons.get(0).trim()), Double.valueOf(latlons.get(1).trim()), mkey, value, score);
			}
			if (idx > 100) {
				pl.syncAndReturnAll();
				System.out.println(".");
				idx = 0;
			}
		}

		jedispool.returnResource(jedis);
		return true;
	}

	private ArrayList parseLocation(String string) {
		ArrayList<String> res = new ArrayList<String>();
		for (String vals4 : string.substring(1, string.lastIndexOf("]") - 1).split(";")) {
			String[] vals5 = vals4.split(",");
			res.add(vals5[0]);
			res.add(vals5[1]);
		}
		return res;
	}

	private void parse(JSONObject jo) {
		double lot = JsonPath.read(jo, "$.location.coordinates.[0]");
		double lat = JsonPath.read(jo, "$.location.coordinates.[1]");
		int poi_id = JsonPath.read(jo, "$.poi_id");
		String memberKey;
		String name = JsonPath.read(jo, "$.name");
		String category = JsonPath.read(jo, "$.category.name");
		String address = JsonPath.read(jo, "$.address");
		// memberKey = "poi_id:" + poi_id ;
		memberKey = "poi_id:" + poi_id + "|name:" + name + "|address:" + address + "|category:" + category;
		// pl.gpadd(key, lat, lot, memberKey, jo.toJSONString());
	}

}
