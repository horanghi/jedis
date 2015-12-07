import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

import javax.swing.text.Document;

import net.minidev.json.JSONObject;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class PlaceDBGenerator {

	static JedisPool jedispool = new JedisPool(new GenericObjectPoolConfig(), "172.19.114.202", 19004, 30000);
	static Jedis jedis;
	static Pipeline pl;
	String dir;
	String key = "aservice";

	public static void main(String[] args) throws IOException {
		jedis = jedispool.getResource();

		PlaceDBGenerator pg = new PlaceDBGenerator();
		pg.execute();
		// pg.getTest();

		jedispool.destroy();

	}

	public PlaceDBGenerator() {
		this.dir = "/Users/horanghi/placeDB/";
	}

	public PlaceDBGenerator(String files) {
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
				String fpath = file.getAbsolutePath();
				String contents = loadConfigGetJson(fpath);
				parsenSave(contents);
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

	public boolean parsenSave(String jsonStr) {
		Pipeline pl = jedis.pipelined();
		String[] linejsons = jsonStr.split("\n");

		int idx = 0;
		int pcount = 10000;

		for (String jsonstr : linejsons) {
			int sidx = jsonstr.indexOf("{\"id\":");
			int eidx = jsonstr.indexOf("\"score\":");
			String value = jsonstr.substring(sidx, eidx - 2);
			DocumentContext dc = JsonPath.parse(jsonstr);
			String memberKey = dc.read("$.value.id");
			double lat = dc.read("$.value.lat");
			double lot = dc.read("$.value.lot");
			double score = dc.read("$.score");
			try {
				String classA = dc.read("$.value.classA");
				String classB = dc.read("$.value.classB");
				String classC = dc.read("$.value.classC");
				String abcKey = "a" + classA + "b" + classB + "c" + classC;
				pl.gpadd(abcKey, lat, lot, memberKey, value, score);
				// System.out.println(abcKey);
			} catch (Exception ex) {
				pl.gpadd("rest", lat, lot, memberKey, value, score);
				// System.out.println("rest");
			}

			if (idx++ > pcount) {
				pl.sync();
				pl = jedis.pipelined();
				idx = 0;
			}
		}
		pl.sync();

		return true;
	}

}
