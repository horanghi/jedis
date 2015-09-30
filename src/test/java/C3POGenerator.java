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
import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Point;

import com.jayway.jsonpath.JsonPath;

public class C3POGenerator {

	static JedisPool jedispool = new JedisPool(new GenericObjectPoolConfig(), "172.19.114.202", 19009, 3000);
	static Jedis jedis;
	static Pipeline pl;
	String dir;
	String key = "c3po";

	public static void main(String[] args) throws IOException {
		long s = System.currentTimeMillis();
		jedis = jedispool.getResource();
		// pl = jedis.pipelined();
		C3POGenerator pg = new C3POGenerator();
		// pg.execute();
		pg.getTest();
		// pl.sync();

		jedispool.destroy();
		long e = System.currentTimeMillis();
		System.out.println("time : " + (e - s));

	}
	
	private void getTest() {

		jedis = jedispool.getResource();

		List<Point<String>> pois = jedis.gpradius(key, 37.5186366, 127.0443256, 100, UNITS.M, "*|accuracy:6*");
		for (Point<String> poi : pois) {
			System.out.println(poi);
		}

		System.out.println("time history =========================================");
		pois = jedis.gpradius(key, 37.5186366, 127.0443256, 1000, UNITS.M, "20150905080253574", "20150905090253574", ORDERBY.DISTANCE_ASC);
		for (Point<String> poi : pois) {
			System.out.println(poi);
		}

		jedispool.returnBrokenResource(jedis);

	}

	public C3POGenerator() {
		this.dir = "/Users/horanghi/c3po/";
	}

	public C3POGenerator(String files) {
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
			System.out.println(file.getAbsolutePath());
			if (file.isFile()) {
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
		String[] _valuelist = statementStr.split("\r");
		ArrayList<String> valuelist = new ArrayList<String>();
		for (String value : _valuelist) {
			valuelist.add(value);
		}
		Iterator<String> it = valuelist.iterator();
		while (it.hasNext()) {
			String[] eles = it.next().split(",");
			// key:{value}|transaction_id:{value}|tech_type:{value}|event_type:{value}|target_id:{value}|tech_id:{value}|owner_id:{value}|accuracy:{value}
			String mkey = "key:" + eles[0] + "|transaction_id:" + eles[1] + "|tech_type:" + eles[2] + "|event_type:" + eles[3]
					+ "|target_id:" + eles[4] + "|tech_id:" + eles[5] + "|owner_id:" + eles[6] + "|accuracy:" + eles[9];
			// lat 7, lon 8
			// timestamp 10
			try {
				double lat = Double.valueOf(eles[7]);
				double lot = Double.valueOf(eles[8]);
				double score = Double.valueOf(eles[10]);
				pl.gpadd(key, lat, lot, mkey, eles[10], score);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		return true;
	}

}
