import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minidev.json.JSONObject;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

import com.jayway.jsonpath.JsonPath;

public class POIGenerator4AutoComplete {

	static JedisPool jedispool = new JedisPool(new GenericObjectPoolConfig(), "172.19.114.203", 19006, 3000, "1234");
	static Jedis jedis;
	static Pipeline pl;
	String dir;
	String key = "autocomplete";

	public static void main(String[] args) throws IOException {
		long s = System.currentTimeMillis();
		jedis = jedispool.getResource();
		// pl = jedis.pipelined();
		// POIGenerator4AutoComplete pg = new POIGenerator4AutoComplete();
		// pg.execute();
		// // pg.getTest();
		// pl.sync();

		/*
		 * 2) "33.118250000000003" "126.2666"
		 */
		// jedis.ggadd("mygg", "polygon", "polygon_value", new Polygon<String>(33.118250000000002, 126.26659, 33.118250000000004, 126.26659,
		// 33.118250000000004, 126.26661, 33.118250000000002, 126.26661, 33.118250000000002, 126.26659));

		System.out.println(jedis.gpregion("autocomplete",
				new Polygon<String>(33.11824, 126.2665, 33.11826, 126.2665, 33.11826, 126.2667, 33.11824, 126.2667, 33.11824, 126.2665))
				.get(0));
		System.out.println(jedis.gpregionByMember("autocomplete", "mygg", "polygon").get(0));

		jedispool.returnResource(jedis);
		jedispool.destroy();
		long e = System.currentTimeMillis();
		System.out.println("time : " + (e - s));

	}

	double[] xs = { 37.56322, 37.56323, 37.56324, 37.56325, 37.56326, 37.56327, 37.56328, 37.56329, 37.56330, 37.56331 };
	double[] ys = { 127.01508, 127.01509, 127.01510, 127.01511, 127.01512, 127.01513, 127.01514, 127.01515, 127.01516, 127.01517 };

	private void getTest() {

		jedis = jedispool.getResource();
		List<Point<String>> result = jedis.gpradius(key, 37.271526, 127.12666, 10, UNITS.M, "*");
		System.out.println(result.size());
		result = jedis.gpradius(key, 37.271526, 127.12666, 100, UNITS.M, "*");
		System.out.println(result.size());
		result = jedis.gpradius(key, 37.271526, 127.12666, 1000, UNITS.M, "*");
		System.out.println(result.size());
		result = jedis.gpradius(key, 37.271526, 127.12666, 10000, UNITS.M, "*");
		System.out.println(result.size());
		// jedis.gpupdate(key, "gs25ㅁㅏㄹㅏㄷㅗㅈㅓㅁ gs25 마라도점", 19);

		result = jedis.gpnn(key, 37.271526, 127.12666, 0, 3, "*");
		System.out.println(result.size());
		result = jedis.gpnn(key, 37.271526, 127.12666, 0, 15, "*");
		System.out.println(result.size());
		result = jedis.gpnn(key, 37.271526, 127.12666, 0, 27, "*");
		System.out.println(result.size());
		result = jedis.gpnn(key, 37.271526, 127.12666, 0, 2022, "*");
		System.out.println(result.size());

		// 0
		// 3
		// 15
		// 27
		// 2022
		jedispool.returnBrokenResource(jedis);

		// try {
		// for (int idx = 0; idx < xs.length; idx++) {
		// List<Point<String>> result = jedis.gpradius(key, xs[idx], ys[idx], 1, UNITS.KM);
		// List<Point<String>> result2 = jedis.gpradius(key, xs[idx], ys[idx], 1, UNITS.KM, "*");
		// if (result.size() != result2.size()) {
		// throw new JedisDataException(" not equals ");
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// jedispool.returnResource(jedis);
		// }
		//
		// jedis = jedispool.getResource();
		// try {
		// for (int idx = 0; idx < xs.length; idx++) {
		// List<Point<String>> result = jedis.gpnn(key, xs[idx], ys[idx], 0, 100, "*");
		// List<Point<String>> result2 = jedis.gpnn(key, xs[idx], ys[idx], 0, 100, "*");
		// if (result.size() != result2.size()) {
		// throw new JedisDataException(" not equals ");
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// jedispool.returnResource(jedis);
		// }
		// System.out.println("OK");
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
		int Tidx = 0;
		while (it.hasNext()) {
			String[] eles = it.next().split("\t");
			String mkey = eles[0] + " " + eles[1];

			if (eles[2].isEmpty()) {
				continue;
			}
			ArrayList<String> latlons = parseLocation(eles[2]);
			double score = Double.valueOf(eles[3]);

			if (latlons.size() > 2) {
				Iterator<String> latloniter = latlons.iterator();
				int idx = 0 + (Tidx++);
				while (latloniter.hasNext()) {
					// System.out.print(".");
					pl.gpadd(key, Double.valueOf(latloniter.next()), Double.valueOf(latloniter.next()), "sample" + idx, "sample value"
							+ idx, score);
				}
			} else {
				// System.out.print(".\n");
				pl.gpadd(key, Double.valueOf(latlons.get(0).trim()), Double.valueOf(latlons.get(1).trim()), "hello" + (Tidx), "hello value"
						+ (Tidx++), score);
			}
		}

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
		pl.gpadd(key, lat, lot, memberKey, jo.toJSONString());
	}

}
