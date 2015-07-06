import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.spatial.model.Point;

import com.jayway.jsonpath.JsonPath;

public class SampleGeodis {

	static JedisPool pool;

	String key = "HelloWorld";
	String member1 = "memkey1";
	String member2 = "memkey2";
	String member3 = "memkey3";
	String member4 = "memkey4";
	String member5 = "memkey5";
	String value = "{ \"Hello\" : \"world\" }";

	List<Point<String>> opoints = Arrays.asList(new Point<String>(member1, 0, 0, value), new Point<String>(member2, 1, 1, value));

	Long OKl = 1l;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// spatial redis
		pool = new JedisPool("172.19.114.201", 19006);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		pool.destroy();
	}

	@Test
	public void testgaddngrange01() {
		Jedis geodis = pool.getResource();

		try {
			// clean
			geodis.del(key);

			// save POI
			assertThat(geodis.gpadd(key, 0, 0, member1, value), is(OKl));
			assertThat(geodis.gpadd(key, 1, 1, member2, value), is(OKl));

			// select
			List<Point<String>> Points = geodis.gprangeByRadius(key, 0, 0, 100, UNITS.M);

			// assert
			for (Point<String> point : Points) {
				assertTrue(opoints.contains(point)); // assert contain ?
			}

			// clean
			geodis.del(key);

			pool.returnResource(geodis);
		} catch (Exception e) {
			pool.returnBrokenResource(geodis);
		}

	}

	@Test
	public void testgaddngrange02() {
		Jedis geodis = pool.getResource();
		
		try{
			//clean
			geodis.del(key);
			
			//save POI
			assertThat(geodis.gpadd(key, 0, 0, member1, value), is(OKl));
			assertThat(geodis.gpadd(key, 1, 1, member2, value), is(OKl));
			assertThat(geodis.gpadd(key, 2, 2, member3, value), is(OKl));
			
			double distance = geodis.gpdistance(0, 0, 2, 2);
			
			System.out.println(distance); //1.3967406199581829
			
//			List<Point<String>> points = geodis.gprangeByRadius(key, 0.0, 0.0, 2, UNITS.M);
			
			//select 
			List<Point<String>> points = geodis.gprangeByRadius(key, 0, 0, distance - 0.0000000000000001, UNITS.M);
//			
//			//assert
			for (Point<String> point : points) {
				assertTrue(opoints.contains(point)); //assert contain ?
			}
			
			//clean
			geodis.del(key);
			
			pool.returnResource(geodis);
		}catch(Exception e){
			pool.returnBrokenResource(geodis);
		}
		
	}

}
