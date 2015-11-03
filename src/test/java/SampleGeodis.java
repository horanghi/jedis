import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.GeoUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.jedis.Response;
import redis.clients.spatial.model.Point;

public class SampleGeodis {

	static JedisPool pool;

	String key = "HelloWorld";
	String member1 = "memkey1";
	String member2 = "memkey2";
	String member3 = "memkey3";
	String member4 = "memkey4";
	String member5 = "memkey5";
	String value = "{ \"Hello\" : \"world\" }";

	List<Point<String>> opoints = Arrays.asList(new Point<String>(member1, 0, 0, value), new Point<String>(member2, 1, 1, value),
			new Point<String>(member3, 2, 2, value));

	Long OKl = 1l;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// spatial redis
		pool = new JedisPool(new GenericObjectPoolConfig(), "172.19.114.203", 19006, 2000, "1234");

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
			List<Point<String>> Points = geodis.gpradius(key, 0, 0, 100, UNITS.M);

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

		try {
			// clean
			geodis.del(key);

			// save POI
			assertThat(geodis.gpadd(key, 0, 0, member1, value), is(OKl));
			assertThat(geodis.gpadd(key, 1, 1, member2, value), is(OKl));
			assertThat(geodis.gpadd(key, 2, 2, member3, value), is(OKl));

			double distance = geodis.gpdistance(0, 0, 1, 1);

			// select
			List<Point<String>> points = geodis.gpradius(key, 0, 0, distance, UNITS.M);

			// assert
			assertThat(points.size(), is(1));
			for (Point<String> point : points) {
				assertTrue(opoints.contains(point)); // assert contain ?
			}

			distance = geodis.gpdistance(0, 0, 2, 2);

			points = geodis.gpradius(key, 0, 0, distance, UNITS.M);

			// assert
			assertThat(points.size(), is(2));
			for (Point<String> point : points) {
				assertTrue(opoints.contains(point)); // assert contain ?
			}

			distance = geodis.gpdistance(0, 0, 3, 3);

			points = geodis.gpradius(key, 0, 0, distance, UNITS.M);

			// assert
			assertThat(points.size(), is(3));
			for (Point<String> point : points) {
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
	public void testpipeline() {
		Jedis geodis = pool.getResource();

		try {
			// clean
			geodis.del(key);
			Pipeline pl = geodis.pipelined();

			double distance1 = geodis.gpdistance(0, 0, 1, 1);
			double distance2 = geodis.gpdistance(0, 0, 2, 2);

			// save POI
			pl.gpadd(key, 0, 0, member1, value);
			pl.gpadd(key, 1, 1, member2, value);
			pl.gpadd(key, 2, 2, member3, value);

			// select
			Response<List<Point<String>>> rpoints1 = pl.gpradius(key, 0, 0, distance1, UNITS.M);
			Response<List<Point<String>>> rpoints2 = pl.gpradius(key, 0, 0, distance2, UNITS.M);

			pl.sync();

			List<Point<String>> points = rpoints1.get();

			// assert
			assertThat(points.size(), is(1));
			for (Point<String> point : points) {
				assertTrue(opoints.contains(point)); // assert contain ?
			}

			List<Point<String>> points2 = rpoints2.get();

			// assert
			assertThat(points2.size(), is(2));
			for (Point<String> point : points2) {
				assertTrue(opoints.contains(point));
			}

			// clean
			geodis.del(key);

			pool.returnResource(geodis);
		} catch (Exception e) {
			pool.returnBrokenResource(geodis);
		}
	}

	@Test
	public void testCoord() {
		Point<String> point = GeoUtils.transWGS84ToBessel1841(1, 2);
		Point<String> rpoint = GeoUtils.transBessel1841ToWGS84(point);

		assertTrue(rpoint.getX() == 0.9999999999999999);
		assertTrue(rpoint.getY() == 1.9999999993709712);

		point = GeoUtils.transWGS84ToBessel1841(new Point<String>(1, 2));
		rpoint = GeoUtils.transBessel1841ToWGS84(point);
		assertTrue(rpoint.getX() == 0.9999999999999999);
		assertTrue(rpoint.getY() == 1.9999999993709712);
	}

}
