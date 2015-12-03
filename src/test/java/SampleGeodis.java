import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import redis.clients.spatial.model.Polygon;

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
		// 172.19.114.202 -p 19004
		// pool = new JedisPool(new GenericObjectPoolConfig(), "172.19.114.202", 19004, 2000);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		pool.destroy();
	}

	// @Test
	// public void testPlaceDB() {
	// Polygon<String> polygon = new Polygon<String>(37.48335120413632, 126.92966390402688, 37.483201199030994, 126.92840291197126,
	// 37.483109531946965, 126.92778352651186, 37.48304564278844, 126.92735301195984, 37.48297897299903, 126.92674473589668,
	// 37.48294563857588, 126.92646698435105, 37.482873414869246, 126.92591425894099, 37.48254007426454, 126.92333950279912,
	// 37.482473408396835, 126.92295065119818, 37.48427552191557, 126.92295065119818, 37.48434218778328, 126.92333950279912,
	// 37.48467552838798, 126.92591425894099, 37.48474775209461, 126.92646698435105, 37.48478108651776, 126.92674473589668,
	// 37.484847756307175, 126.92735301195984, 37.4849116454657, 126.92778352651186, 37.48500331254973, 126.92840291197126,
	// 37.485153317655055, 126.92966390402688);
	//
	// Jedis geodis = pool.getResource();
	//
	// List<Point<String>> ps = geodis.gpregion("sap1", polygon);
	// Point<String> pp = new Point<String>(37.48511192974065, 126.91665620600378);
	// for (Point<String> p : ps) {
	// if (p.equals(pp)) {
	// System.out.println(p);
	// }
	// }
	//
	// System.out.println(ps.size());
	//
	// pool.returnResource(geodis);
	//
	// }

	@Test
	public void testclose() {
		List<Point<String>> listPoints = new ArrayList<Point<String>>();
		listPoints.add(new Point<String>(1, 1));
		listPoints.add(new Point<String>(2, 2));
		listPoints.add(new Point<String>(3, 3));

		Polygon<String> polygon1 = new Polygon<String>(1, 1, 2, 2, 3, 3);
		Polygon<String> polygon2 = new Polygon<String>(new Point<String>(1, 1), new Point<String>(2, 2), new Point<String>(3, 3));
		Polygon<String> polygon3 = new Polygon<String>(listPoints);
		Polygon<String> polygon4 = new Polygon<String>(1, 1, 2, 2, 3, 3, 1, 1);

		assertTrue(polygon1.getJsonStr().equals(polygon2.getJsonStr()));
		assertTrue(polygon1.getJsonStr().equals(polygon3.getJsonStr()));
		assertTrue(polygon4.getJsonStr().equals(polygon1.getJsonStr()));
		assertTrue(polygon4.getJsonStr().equals(polygon2.getJsonStr()));
		assertTrue(polygon4.getJsonStr().equals(polygon3.getJsonStr()));

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
		// Point<String> point = GeoUtils.transWGS84ToBessel1841(1, 2);
		// Point<String> rpoint = GeoUtils.transBessel1841ToWGS84(point);
		//
		// assertTrue(rpoint.getX() == 0.9999999999999999);
		// assertTrue(rpoint.getY() == 1.9999999993709712);
		//
		// point = GeoUtils.transWGS84ToBessel1841(new Point<String>(1, 2));
		// rpoint = GeoUtils.transBessel1841ToWGS84(point);
		// assertTrue(rpoint.getX() == 0.9999999999999999);
		// assertTrue(rpoint.getY() == 1.9999999993709712);

		Point<String> point = GeoUtils.transBessel1841ToWGS84(36.34802778, 129.3876389);
		System.out.println(point);
		// 129.3876389 36.34802778 129.3853333 36.351
		// 127.8112222 37.98369444 127.809 37.98644444
		// 127.4391389 37.09011111 127.4369722 37.09294444
		// 127.9853611 34.74922222 127.9832222 34.75233333
		// 128.5805556 35.20302778 128.5783333 35.20611111
		// 128.3424444 36.89147222 128.3402222 36.89436111
		// 127.3188056 37.35688889 127.3166389 37.35969444
		// 127.0005556 37.49430556 126.9984167 37.49708333
		// 127.0276389 35.36244444 127.0255556 35.36547222
		// 128.1303889 36.65169444 128.1281667 36.65458333
		// 127.7557222 37.87361111 127.7535 37.87636111
		// 126.9500833 35.33377778 126.9480278 35.33680556
		// 127.04225 37.52263889 127.0401111 37.52541667
		// 126.6011944 34.97561111 126.5991667 34.97866667
		// 126.3218056 37.68394444 126.3197222 37.68669444
		// 128.0423333 34.79691667 128.0401944 34.80002778
		// 126.9314167 37.55175 126.9292778 37.55452778
		// 126.9871111 37.34063889 126.9849722 37.34344444
		// 126.524 36.43613889 126.5219444 36.43902778
		// 127.0966667 37.31841667 127.0945278 37.32122222
		// 127.0044444 37.55744444 127.0023056 37.56022222
		// 126.8416111 37.64016667 126.8395 37.64291667
		// 127.4976389 37.49072222 127.4954722 37.49352778
		// 126.5126667 37.60911111 126.5105833 37.61186111
		// 126.7426944 37.34755556 126.7405833 37.35036111
		// 126.6473889 37.20338889 126.6453056 37.20619444
		// 126.8523056 36.67666667 126.8502222 36.67952778
		// 129.1281389 35.56008333 129.1258611 35.56313889
		// 126.8537778 37.71188889 126.8516667 37.71463889
		// 126.9755278 37.2645 126.9734167 37.26730556
		// 127.9279444 36.98597222 127.92575 36.98883333
		// 127.0400556 37.48155556 127.0379167 37.48433333
		// 127.1528889 35.80855556 127.1507778 35.81152778
		// 127.12825 37.509 127.1261111 37.51177778
		// 127.0253333 37.50730556 127.0231944 37.51008333
		// 127.1546667 35.81005556 127.1525556 35.81302778
		// 126.9241389 37.54983333 126.922 37.55261111
		// 127.1418333 37.59930556 127.1396944 37.60208333
		// 126.9018333 37.48063889 126.8997222 37.48341667
		// 127.0376944 37.49175 127.0355556 37.49452778
	}

}
