package redis.clients.geodis.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.SCOPE;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;
import redis.clients.util.GEOMETRY;

public class GeodisTest {

	static JedisPool geodisPool;
	Jedis geodis;
	String key = "helloKey";
	String member1 = "memkey1";
	String member2 = "memkey2";
	String member3 = "memkey3";
	String member4 = "memkey4";
	String member5 = "memkey5";
	String value = "desc";

	byte[] keyb = key.getBytes();

	byte[] member1b = member1.getBytes();
	byte[] member2b = member2.getBytes();
	byte[] member3b = member3.getBytes();
	byte[] member4b = member4.getBytes();
	byte[] member5b = member5.getBytes();
	byte[] valueb = value.getBytes();

	Long OKl = 1l;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//spatial redis
		geodisPool = new JedisPool("172.19.114.201", 19006);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		geodisPool.destroy();
	}

	@Before
	public void setUp() throws Exception {
		geodis = geodisPool.getResource();
	}

	@After
	public void tearDown() throws Exception {
		geodisPool.returnResource(geodis);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void test0Equals() {
		Point<?>[] opoints = new Point[] { new Point(1, 0), new Point(1, 1), new Point(-1, -1), new Point(0, 0) };
		Point<?>[] old = new Point[] { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(-1, -1) };

		List<Point<?>> ais = new ArrayList<Point<?>>();
		ais.addAll(Arrays.asList(opoints));
		List<Point<?>> bis = new ArrayList<Point<?>>();
		bis.addAll(Arrays.asList(old));

		assertTrue(GEOMETRY.equals(ais, bis));

		String[] vas = { "1", "2", "3", "4" };
		sendCommand(4, vas);

	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void test1Equals() {
		Point<String> asop = new Point<String>("member1", 0, 0 , "values");
		Point<String> asop2 = new Point<String>("member2", 0, 0 , "values");
		Point<String> asop3 = new Point<String>(0, 0);
		Point<String> asop4 = new Point<String>("mm", 0, 0, "values");
		Point<String> asop11 = new Point<String>("member1", 0, 0 , "values");
		
		assertThat(asop, is(asop2));
		assertTrue(asop.equals(asop2));
		
		assertTrue(asop.equals(asop3));
		assertThat(asop, is(asop3));
		
		assertFalse(asop.equalsDeep(asop2));
		assertFalse(asop.equalsDeep(asop3));
		assertFalse(asop.equalsDeep(asop4));
		assertTrue(asop.equalsDeep(asop11));
		

	}

	protected void sendCommand(int count, final String... args) {
		String[] vv = new String[args.length];
		assertTrue(vv.length == args.length);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testhoranghi() {
		byte[] b1 = "good".getBytes();
		byte[] b2 = "good".getBytes();
		assertTrue(Arrays.equals(b1, b2));
		Point<String>[] opoints = new Point[] { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(-1, -1) };
		Point<String>[] old = new Point[] { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(-1, -1) };

		for (int idx = 0; idx < opoints.length; idx++) {
			assertTrue(opoints[idx].equals(old[idx]));
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testgaddnGradiusDetail() {
		geodis.del(key);
		String[] members = { member1, member2 };
		String[] values = { value, value };
		Point<String>[] opoints = new Point[] { new Point(member1, 0, 0, value), new Point(member2, 0, 0, value) };
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.grangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<String>> Points = geodis.grangeByRadius(key, 0, 0, 100, UNITS.M);
		int idx = 0;
		for (Point<String> point : Points) {
			assertTrue(point.getMember().equals(members[idx]));
			assertTrue(point.getValue().equals(values[idx]));
			assertTrue(point.equals(opoints[idx++]));
			System.out.println(point.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.grangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<byte[]>> Pointsb = geodis.grangeByRadius(keyb, 0, 0, 100, UNITS.M);
		for (Point<byte[]> point : Pointsb) {
			System.out.println(point.toString());
		}
		geodis.del(keyb);
	}

	@Test
	public void testgaddnGradiusForCircle() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 10, UNITS.M, member3, value), is(OKl));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 10, UNITS.KM).size(), is(1));
		List<Circle<String>> circles = geodis.grangeCircleByRadius(key, 0, 0, 100, UNITS.M);
		for (Circle<String> circle : circles) {
			System.out.println(circle.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 10, UNITS.M, member3, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 30, UNITS.M, member4, value), is(OKl));
		assertThat(geodis.grangeCircleByRadius(keyb, 0, 0, 50, UNITS.M).size(), is(2));
		List<Circle<byte[]>> circlesb = geodis.grangeCircleByRadius(keyb, 0, 0, 50, UNITS.M);
		for (Circle<byte[]> circle : circlesb) {
			System.out.println(circle.toString());
		}
		geodis.del(keyb);
	}
	
	@Test
	public void testgaddnGradiusForCircleEXT() {
		geodis.del(key);
		System.out.println(geodis.distance(0.0000001, 0, 0.0000002, 0));
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 11, UNITS.M, member3, value), is(OKl));
		assertThat(geodis.gadd(key, 0.0000002, 0, 30, UNITS.M, member4, value), is(OKl));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Circle<String>> circles = geodis.grangeCircleByRadius(key, 0, 0, 100, UNITS.M, SCOPE.CONTAINS, ORDERBY.ASC);
		assertThat(circles.size(), is(2));
		assertTrue(circles.get(0).getDistance() < circles.get(1).getDistance());
		for (Circle<String> circle : circles) {
			System.out.println(circle.toString());
		}
		List<Circle<String>> circles2 = geodis.grangeCircleByRadius(key, 0, 0, 10, UNITS.M, SCOPE.WITHIN, ORDERBY.DESC);
		assertThat(circles2.size(), is(2));
		assertTrue(circles2.get(0).getDistance() > circles2.get(1).getDistance());
		for (Circle<String> circle : circles2) {
			System.out.println(circle.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 10, UNITS.M, member3, value), is(OKl));
		assertThat(geodis.gadd(key, 0.0000002, 0, 30, UNITS.M, member4, value), is(OKl));
		assertThat(geodis.grangeCircleByRadius(keyb, 0, 0, 50, UNITS.M).size(), is(2));
		List<Circle<byte[]>> circlesb = geodis.grangeCircleByRadius(keyb, 0, 0, 50, UNITS.M, SCOPE.CONTAINS, ORDERBY.ASC);
		assertThat(circlesb.size(), is(2));
		assertTrue(circlesb.get(0).getDistance() < circlesb.get(1).getDistance());
		for (Circle<byte[]> circle : circlesb) {
			System.out.println(circle.toString());
		}
		List<Circle<byte[]>> circles2b = geodis.grangeCircleByRadius(keyb, 0, 0, 9, UNITS.M, SCOPE.WITHIN, ORDERBY.DESC);
		assertThat(circles2b.size(), is(2));
		assertTrue(circles2b.get(0).getDistance() > circles2b.get(1).getDistance());

		geodis.del(keyb);
	}

	@Test
	public void testgaddnGradiusWithMatch() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.grangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<String>> Points = geodis.grangeByRadiusWithMatch(key, 0, 0, 10, M, "member*");

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.grangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<byte[]>> Pointsb = geodis.grangeByRadiusWithMatch(keyb, 0, 0, 10, M, "member*".getBytes());
		geodis.del(keyb);
	}

	@Test
	public void testgaddnGradiusForCircleWithMatch() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 10, UNITS.M, member3, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 30, UNITS.M, member4, value), is(OKl));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 10, UNITS.M).size(), is(1));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 30, UNITS.KM).size(), is(2));
		List<Circle<String>> circles = geodis.grangeCircleByRadiusWithMatch(key, 0, 0, 40, M, "*memkey4*");
		assertThat(circles.size(), is(1));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, 10, UNITS.M, member3b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, 30, UNITS.M, member4b, valueb), is(OKl));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 10, UNITS.M).size(), is(1));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 30, UNITS.KM).size(), is(2));
		List<Circle<byte[]>> circlesb = geodis.grangeCircleByRadiusWithMatch(keyb, 0, 0, 40, M, "*4*".getBytes());
		assertThat(circlesb.size(), is(1));
		geodis.del(keyb);
	}
	
	@Test
	public void testgaddnGradiusForCircleWithMatchEXT() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 10, UNITS.M, member3, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 30, UNITS.M, member4, value), is(OKl));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 10, UNITS.M).size(), is(1));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 30, UNITS.KM).size(), is(2));
		List<Circle<String>> circles = geodis.grangeCircleByRadiusWithMatch(key, 0, 0, 40, M, "*memkey4*", SCOPE.CONTAINS, ORDERBY.ASC);
		assertThat(circles.size(), is(1));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, 10, UNITS.M, member3b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, 30, UNITS.M, member4b, valueb), is(OKl));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 10, UNITS.M).size(), is(1));
		assertThat(geodis.grangeCircleByRadius(key, 0, 0, 30, UNITS.KM).size(), is(2));
		List<Circle<byte[]>> circlesb = geodis.grangeCircleByRadiusWithMatch(keyb, 0, 0, 40, M, "*4*".getBytes(), SCOPE.CONTAINS, ORDERBY.ASC);
		assertThat(circlesb.size(), is(1));
		geodis.del(keyb);
	}

	@Test
	public void testgaddnGradiusWithMatchIfnotMatch() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, "memberd", value), is(OKl));
		assertThat(geodis.grangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(3));
		List<Point<String>> Points = geodis.grangeByRadiusWithMatch(key, 0, 0, 10, M, "memberd*");
		assertThat(Points.size(), is(1));
		assertTrue(Points.iterator().next().equals(new Point<String>("memberd", 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, "memberd".getBytes(), valueb), is(OKl));
		assertThat(geodis.grangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(3));
		List<Point<byte[]>> Pointsb = geodis.grangeByRadiusWithMatch(keyb, 0, 0, 10, M, "memberd*".getBytes());
		assertThat(Pointsb.size(), is(1));
		assertTrue(Pointsb.iterator().next().equals(new Point<byte[]>("memberd".getBytes(), 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testgaddnRegion() {
		// [1,1], [1,-1], [-1,-1], [-1,1], [1,1]
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		System.out.println(polygon.getJsonStr());

		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.grangeByRegion(key, polygon).size(), is(2));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.grangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(2));
		geodis.del(keyb);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgaddnRegion2() {
		// [1,1], [1,-1], [-1,-1], [-1,1], [1,1]
		Polygon<byte[]> polygon = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		System.out.println(polygon.getJsonStr());

		geodis.del(key);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.grangeByRegion(keyb, polygon).size(), is(2));
		geodis.del(key);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGEOMETRY_Equals() {

		Point<String> point1 = new Point<String>(0, 0);
		Point<String> point2 = new Point<String>(0, 0);
		Point<String> point3 = new Point<String>(1, 0);
		Point<String> point4 = new Point<String>(0, 1);
		Point<String> point5 = new Point<String>(0, -1);
		Point<String> point1_1 = new Point<String>(member1, 0, 0, value);
		Point<String> point1_2 = new Point<String>(member2, 0, 0, value + "1");

		assertTrue(point1.equals(point2)); // x,y만 비교
		assertFalse(point1.equals(point3));// x,y만 비교
		assertFalse(point1.equals(point4));// x,y만 비교
		assertFalse(point1.equals(point5));// x,y만 비교
		assertTrue(point1.equals(point1_1)); // x,y만 비교
		assertTrue(point1_1.equals(point1_2)); // x,y만 비교

		assertThat(point1, is(point2)); // x,y만 비교
		assertThat(point1, not(point3));// x,y만 비교
		assertThat(point1, not(point4));// x,y만 비교
		assertThat(point1, not(point5));// x,y만 비교
		assertThat(point1, is(point1_1)); // x,y만 비교
		assertThat(point1_1, is(point1_2)); // x,y만 비교

		assertTrue(((Geometry) point1_1).equals((Geometry) point1_2)); // x,y만 비교

		Circle<String> circle1 = new Circle<String>(member1, 0, 0, 10, M, value);
		Circle<String> circle2 = new Circle<String>(member2, 0, 0, 10, M, value);
		Circle<String> circle3 = new Circle<String>(member3, 0, 0, 30, M, value);
		Circle<String> circle4 = new Circle<String>(member1, 0, 0, 40, M, value);
		Circle<String> circle1_1 = new Circle<String>(member1, 0, 0, 10, M, value);
		Circle<String> circle1_2 = new Circle<String>(member2, 0, 0, 10, M, value + "1");

		assertTrue(circle1.equals(circle2)); // x,y, radius 비교
		assertFalse(circle1.equals(circle3));// x,y, radius 비교
		assertFalse(circle1.equals(circle4));// x,y, radius 비교
		assertTrue(circle1_1.equals(circle1_2)); // x,y, radius 비교

		// [1,1], [1,-1], [-1,-1], [-1,1], [1,1]
		Polygon<String> polygon1 = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		Polygon<String> polygon2 = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		Polygon<String> polygon3 = new Polygon<String>(new Point<String>(0, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));// 다름.
		Polygon<String> polygon4 = new Polygon<String>(new Point<String>(0, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1), new Point<String>(1, 1));// 다름.
		Polygon<String> polygon1_1 = new Polygon<String>(new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1),
				new Point<String>(1, 1), new Point<String>(1, -1));

		assertTrue(polygon1.equals(polygon2)); // x,y만 비교
		assertFalse(polygon1.equals(polygon3)); // x,y만 비교
		assertFalse(polygon1.equals(polygon4)); // x,y만 비교
		assertTrue(polygon1.equals(polygon1_1)); // x,y만 비교

		assertThat(polygon1, is(polygon2)); // x,y만 비교
		assertThat(polygon1, not(polygon3)); // x,y만 비교
		assertThat(polygon1, not(polygon4)); // x,y만 비교
		assertThat(polygon1, is(polygon1_1)); // x,y만 비교

		LineString<String> linestr1 = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		LineString<String> linestr2 = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		LineString<String> linestr3 = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(-1, 0)); // 다름
		LineString<String> linestr4 = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 0));// 다름
		LineString<String> linestr1_1 = new LineString<String>(new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));

		assertTrue(linestr1.equals(linestr2)); // x,y만 비교
		assertFalse(linestr1.equals(linestr3)); // x,y만 비교
		System.out.println(linestr1.getJsonStr().equals(linestr4.getJsonStr()));
		assertFalse(linestr1.equals(linestr4)); // x,y만 비교
		assertTrue(linestr1.equals(linestr1_1)); // x,y만 비교

		assertThat(linestr1, is(linestr2)); // x,y만 비교
		assertThat(linestr1, not(linestr3)); // x,y만 비교
		assertThat(linestr1, not(linestr4)); // x,y만 비교
		assertThat(linestr1, is(linestr1_1)); // x,y만 비교
	}

	@Test
	public void testdiff() {
		LineString<String> linestr1 = new LineString<String>(new Point<String>(1, 1), new Point<String>(-1, 1));
		LineString<String> linestr4 = new LineString<String>(new Point<String>(1, 1), new Point<String>(-1, 0));// 다름
		LineString<String> linestr1_1 = new LineString<String>(new Point<String>(-1, 1), new Point<String>(1, 1));

		assertTrue(linestr1.equals(linestr1_1)); // x,y만 비교
		assertThat(linestr1, is(linestr1_1)); // x,y만 비교
		assertFalse(linestr1.equals(linestr4)); // x,y만 비교

		assertThat(linestr1, not(linestr4)); // x,y만 비교
	}

	@Test
	public void testgfcard() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gcard(key), is(2l));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gcard(keyb), is(2l));
		geodis.del(keyb);
	}

	@Test
	public void testgfrem() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.grem(key, member1), is(1l));
		assertThat(geodis.gcard(key), is(1l));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.grem(keyb, member1b), is(1l));
		assertThat(geodis.gcard(keyb), is(1l));
		geodis.del(keyb);
	}

	@Test
	public void testgget() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertNotNull(geodis.gget(key, member1));
		assertTrue(geodis.gget(key, member1).equals(new Point<String>(member1, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertNotNull(geodis.gget(keyb, member1b));
		assertTrue(geodis.gget(keyb, member1b).equalsDeep(new Point<byte[]>(member1b, 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testggetIfnotexist() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertNull(geodis.gget(key, member3));
		assertTrue(geodis.gget(key, member1).equals(new Point<String>(member1, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertNull(geodis.gget(keyb, member3b));
		assertTrue(geodis.gget(keyb, member1b).equals(new Point<byte[]>(member1b, 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgmget() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gmget(key, member1).size(), is(1));
		assertThat(geodis.gmget(key, member2).iterator().next(), is(new Point<String>(member2, 0, 0, value, 0)));
		assertTrue(geodis.gmget(key, member2).iterator().next().equalsDeep(new Point<String>(member2, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gmget(keyb, member1b).size(), is(1));
		assertTrue(geodis.gmget(keyb, member2b).iterator().next().equals((Object) new Point<byte[]>(member2b, 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgmgetIfnotexist() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gmget(key, member3, member4).size(), is(0));
		assertTrue(geodis.gmget(key, member2).iterator().next().equals(new Point<String>(member2, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gmget(keyb, member3b, member4b).size(), is(0));
		assertTrue(geodis.gmget(keyb, member2b).iterator().next().equals(new Point<byte[]>(member2b, 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgfnn() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		assertThat(geodis.gadd(key, 0.0, 0.0, members[0], value), is(OKl));
		assertThat(geodis.gadd(key, 0.1, 0.1, members[1], value), is(OKl));
		assertThat(geodis.gadd(key, 0.2, 0.2, members[2], value), is(OKl));
		assertThat(geodis.gadd(key, 0.3, 0.3, members[3], value), is(OKl));
		assertThat(geodis.gnn(key, 0, 0, 3).size(), is(3));
		List<Point<String>> result = geodis.gnn(key, 0, 0, 4);
		assertThat(result.size(), is(4));
		int idx = 0;
		for (Point<String> point : result) {
			point.equals(new Point<String>(members[idx++], 0, 0, value, 0));
		}

		List<Point<String>> result2 = geodis.gnn(key, 0.01, 0.01, 5);
		assertThat(result2.size(), is(4));
		assertThat(result2.get(0).getMember(), is(members[0]));
		assertThat(result2.get(1).getMember(), is(members[1]));
		assertThat(result2.get(2).getMember(), is(members[2]));
		assertThat(result2.get(3).getMember(), is(members[3]));

		List<Point<String>> result3 = geodis.gnn(key, 0.11, 0.11, 1);
		assertThat(result3.size(), is(1));
		assertThat(result3.get(0).getMember(), is(members[1]));

		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		assertThat(geodis.gadd(keyb, 0.0, 0.0, membersb[0], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.1, 0.1, membersb[1], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.2, 0.2, membersb[2], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.3, 0.3, membersb[3], valueb), is(OKl));
		assertThat(geodis.gnn(keyb, 0, 0, 3).size(), is(3));
		List<Point<byte[]>> resultb = geodis.gnn(keyb, 0, 0, 4);
		assertThat(result.size(), is(4));
		int idx2 = 0;
		for (Point<byte[]> point : resultb) {
			point.equals(new Point<byte[]>(membersb[idx2++], 0, 0, valueb, 0));
		}
		geodis.del(keyb);
	}

	@Test
	public void testgfnnIfnotexist() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		assertThat(geodis.gadd(key, 0.0, 0.0, members[0], value), is(OKl));
		assertThat(geodis.gadd(key, 0.1, 0.1, members[1], value), is(OKl));
		assertThat(geodis.gnn(key, 0, 0, 5).size(), is(2));
		List<Point<String>> result = geodis.gnn(key, 0, 0, 5);
		assertThat(result.size(), is(2));
		int idx = 0;
		for (Point<String> point : result) {
			point.equals(new Point<String>(members[idx++], 0, 0, value, 0));
		}
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		assertThat(geodis.gadd(keyb, 0.0, 0.0, membersb[0], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.1, 0.1, membersb[1], valueb), is(OKl));
		assertThat(geodis.gnn(keyb, 0, 0, 3).size(), is(2));
		List<Point<byte[]>> resultb = geodis.gnn(keyb, 0, 0, 5);
		assertThat(result.size(), is(2));
		int idx2 = 0;
		for (Point<byte[]> point : resultb) {
			point.equals(new Point<byte[]>(membersb[idx2++], 0, 0, valueb, 0));
		}
		geodis.del(keyb);
	}

	/*
	 * // 좌표A: 제주국제공항 $lat1 = '33.50674337041539'; // 위도 $lon1 = '126.49184109764144'; // 경도
	 * 
	 * // 좌표B: 한라산 $lat2 = '33.3586056496444'; // 위도 $lon2 = '126.53189806385355'; // 경도
	 */
	@Test
	public void testDistance() {
		System.out.println(geodis.distance(33.50674337041539, 126.49184109764144, 33.3586056496444, 126.53189806385355));
		// 36.36770178
		// 127.40182709999999
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testggaddandggrange() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(1, 1);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));
		assertThat(geodis.ggrange(key, 0, -1).size(), is(3));
		assertThat(geodis.ggrange(keyb, 0, -1).size(), is(3));
		List<Geometry<String>> result = geodis.ggrange(key, 0, -1);
		assertThat(result.size(), is(3));
		for (Geometry<String> geo : result) {
			if (geo instanceof Polygon) {
				System.out.println(((Polygon) geo).getType().name());
				assertThat(geo.getMember(), is(members[0]));
			} else if (geo instanceof LineString) {
				System.out.println(((LineString) geo).getType().name());
				assertThat(geo.getMember(), is(members[1]));
			} else if (geo instanceof Point) {
				System.out.println(((Point) geo).getType().name());
				assertThat(geo.getMember(), is(members[2]));
			}
		}
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		Point<byte[]> pointb = new Point<byte[]>(1, 1);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));
		assertThat(geodis.ggrange(keyb, 0, -1).size(), is(3));
		List<Geometry<byte[]>> resultb = geodis.ggrange(keyb, 0, -1);
		assertThat(resultb.size(), is(3));
		for (Geometry<byte[]> geo : resultb) {
			if (geo instanceof Polygon) {
				System.out.println(((Polygon) geo).getType().name());
				assertThat(geo.getMember(), is(membersb[0]));
			} else if (geo instanceof LineString) {
				System.out.println(((LineString) geo).getType().name());
				assertThat(geo.getMember(), is(membersb[1]));
			} else if (geo instanceof Point) {
				System.out.println(((Point) geo).getType().name());
				assertThat(geo.getMember(), is(membersb[2]));
			}
		}

		// byte => string 조회 . 동일한 값임.
		assertThat(geodis.ggrange(key, 0, -1).size(), is(3));
		List<Geometry<String>> result2 = geodis.ggrange(key, 0, -1);
		assertThat(result2.size(), is(3));
		for (Geometry<String> geo : result2) {
			if (geo instanceof Polygon) {
				System.out.println(((Polygon) geo).getType().name());
				assertThat(geo.getMember(), is(members[0]));
			} else if (geo instanceof LineString) {
				System.out.println(((LineString) geo).getType().name());
				assertThat(geo.getMember(), is(members[1]));
			} else if (geo instanceof Point) {
				System.out.println(((Point) geo).getType().name());
				assertThat(geo.getMember(), is(members[2]));
			}
		}
		geodis.del(keyb);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testggaddandggrevrange() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(1, 1);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));
		assertThat(geodis.ggrevrange(key, 0, -1).size(), is(3));
		assertThat(geodis.ggrevrange(keyb, 0, -1).size(), is(3));
		List<Geometry<String>> result = geodis.ggrevrange(key, 0, -1);
		assertThat(result.size(), is(3));
		for (Geometry<String> geo : result) {
			if (geo instanceof Polygon) {
				System.out.println(((Polygon) geo).getType().name());
				assertThat(geo.getMember(), is(members[0]));
			} else if (geo instanceof LineString) {
				System.out.println(((LineString) geo).getType().name());
				assertThat(geo.getMember(), is(members[1]));
			} else if (geo instanceof Point) {
				System.out.println(((Point) geo).getType().name());
				assertThat(geo.getMember(), is(members[2]));
			}
		}
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		Point<byte[]> pointb = new Point<byte[]>(1, 1);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));
		assertThat(geodis.ggrevrange(keyb, 0, -1).size(), is(3));
		List<Geometry<byte[]>> resultb = geodis.ggrevrange(keyb, 0, -1);
		assertThat(resultb.size(), is(3));
		for (Geometry<byte[]> geo : resultb) {
			if (geo instanceof Polygon) {
				System.out.println(((Polygon) geo).getType().name());
				assertThat(geo.getMember(), is(membersb[0]));
			} else if (geo instanceof LineString) {
				System.out.println(((LineString) geo).getType().name());
				assertThat(geo.getMember(), is(membersb[1]));
			} else if (geo instanceof Point) {
				System.out.println(((Point) geo).getType().name());
				assertThat(geo.getMember(), is(membersb[2]));
			}
		}

		// byte => string 조회 . 동일한 값임.
		assertThat(geodis.ggrevrange(key, 0, -1).size(), is(3));
		List<Geometry<String>> result2 = geodis.ggrevrange(key, 0, -1);
		assertThat(result2.size(), is(3));
		for (Geometry<String> geo : result2) {
			if (geo instanceof Polygon) {
				System.out.println(((Polygon) geo).getType().name());
				assertThat(geo.getMember(), is(members[0]));
			} else if (geo instanceof LineString) {
				System.out.println(((LineString) geo).getType().name());
				assertThat(geo.getMember(), is(members[1]));
			} else if (geo instanceof Point) {
				System.out.println(((Point) geo).getType().name());
				assertThat(geo.getMember(), is(members[2]));
			}
		}
		geodis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testggcard() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(1, 1);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));
		assertThat(geodis.ggcard(key), is(3l));
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		Point<byte[]> pointb = new Point<byte[]>(1, 1);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));
		assertThat(geodis.ggcard(keyb), is(3l));
		geodis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testggrem() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(1, 1);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));
		assertThat(geodis.ggrem(key, members[0]), is(1l));
		assertThat(geodis.ggcard(key), is(2l));
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		Point<byte[]> pointb = new Point<byte[]>(1, 1);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));
		assertThat(geodis.ggrem(keyb, membersb[0]), is(1l));
		assertThat(geodis.ggcard(keyb), is(2l));
		geodis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testggget() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(1, 1);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));
		assertThat((Polygon<String>) geodis.ggget(key, members[0]), is(polygon));
		assertThat((LineString<String>) geodis.ggget(key, members[1]), is(linestr));
		assertThat((Point<String>) geodis.ggget(key, members[2]), is(point));
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		Point<byte[]> pointb = new Point<byte[]>(1, 1);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));
		assertNotNull(geodis.ggget(keyb, membersb[0]));
		assertThat((Point<byte[]>) geodis.ggget(keyb, membersb[2]), is(new Point<byte[]>(1, 1)));
		geodis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testggmget2() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(1, 1);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));

		assertThat((Polygon<String>) geodis.ggget(key, members[0]), is(polygon));
		assertThat((LineString<String>) geodis.ggget(key, members[1]), is(linestr));
		assertThat((Point<String>) geodis.ggget(key, members[2]), is(point));

		assertThat((Polygon<String>) geodis.ggmget(key, members).get(0), is(polygon));
		assertThat((LineString<String>) geodis.ggmget(key, members).get(1), is(linestr));
		assertThat((Point<String>) geodis.ggmget(key, members).get(2), is(point));
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		Point<byte[]> pointb = new Point<byte[]>(1, 1);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));
		assertNotNull(geodis.ggmget(keyb, membersb));
		assertTrue(((Polygon<byte[]>) geodis.ggmget(keyb, membersb).get(0)).equals(polygonb));
		assertTrue(((LineString<byte[]>) geodis.ggmget(keyb, membersb).get(1)).equals(linestrb));
		assertTrue(((Point<byte[]>) geodis.ggmget(keyb, membersb).get(2)).equals((Object) new Point<byte[]>(1, 1)));
		geodis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testgggetandggmgetIfnotexist() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(1, 1);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));
		assertThat((Polygon<String>) geodis.ggmget(key, members).get(0), is(polygon));
		assertThat((LineString<String>) geodis.ggmget(key, members).get(1), is(linestr));
		assertThat((Point<String>) geodis.ggmget(key, members).get(2), is(point));

		assertThat(geodis.ggmget(key, members).size(), is(3));

		// case when not exist
		assertNull(geodis.ggget(key, members[3]));

		assertNotNull(geodis.ggmget(key, members[3], members[1]));
		assertThat((LineString<String>) geodis.ggmget(key, members[3], members[1]).get(0), is(linestr));

		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		Point<byte[]> pointb = new Point<byte[]>(1, 1);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));
		assertNotNull(geodis.ggmget(keyb, membersb));
		assertTrue(((Polygon<byte[]>) geodis.ggmget(keyb, membersb).get(0)).equals(polygonb));
		assertTrue(((LineString<byte[]>) geodis.ggmget(keyb, membersb).get(1)).equals(linestrb));
		assertTrue(((Point<byte[]>) geodis.ggmget(keyb, membersb).get(2)).equals(new Point<byte[]>(1, 1)));

		// case when not exist
		assertNull(geodis.ggget(keyb, membersb[3]));

		assertNotNull(geodis.ggmget(keyb, membersb[3], membersb[1]));
		assertThat((LineString<byte[]>) geodis.ggmget(keyb, membersb[3], membersb[1]).get(0), is(linestrb));

		geodis.del(keyb);
	}

	@Test
	public void testgggetIfnotexist() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertNull(geodis.gget(key, member3));
		assertTrue(geodis.gget(key, member1).equals(new Point<String>(member1, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertNull(geodis.gget(keyb, member3b));
		assertTrue(geodis.gget(keyb, member1b).equalsDeep(new Point<byte[]>(member1b, 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testggmget() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gmget(key, member1).size(), is(1));
		assertThat(geodis.gmget(key, member2).iterator().next(), is(new Point<String>(member2, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gmget(keyb, member1b).size(), is(1));
		Point<byte[]> pb = new Point<byte[]>(member2b, 0, 0, valueb, 0);
		assertTrue(geodis.gmget(keyb, member2b).iterator().next().equals((Object) pb));
		geodis.del(keyb);
	}

	@Test
	public void testggmgetIfnotexist() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gmget(key, member3, member4).size(), is(0));
		assertThat(geodis.gmget(key, member2).iterator().next(), is(new Point<String>(member2, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gmget(keyb, member3b, member4b).size(), is(0));
		assertTrue(geodis.gmget(keyb, member2b).iterator().next().equals((Object) new Point<byte[]>(member2b, 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testggrelation() {
		geodis.del(key);
		String[] members = { "member01", "member02", "member03", "member04" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(0, 0), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(0, 0));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(2, 2);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));

		Polygon<String> polygon1 = new Polygon<String>(new Point<String>(2, 2), new Point<String>(2, -2), new Point<String>(-2, -2),
				new Point<String>(-2, 2), new Point<String>(2, 2));
		assertThat(geodis.ggrelation(key, polygon1).size(), is(2));// not contain point

		assertThat((Polygon<String>) geodis.ggrelation(key, polygon).get(0), is(polygon));
		assertThat((LineString<String>) geodis.ggrelation(key, linestr).get(0), is(linestr));
		assertThat((Point<String>) geodis.ggrelation(key, point).get(0), is(point));

		// contain operation with point (1.9,1.9)
		Point<String> pointq = new Point<String>(1.9, 1.9);
		assertThat(geodis.ggadd(key, members[3], value, pointq), is(OKl));
		Polygon<String> polygon2 = new Polygon<String>(new Point<String>(2, 2), new Point<String>(2, -2), new Point<String>(-2, -2),
				new Point<String>(-2, 2), new Point<String>(2, 2));
		assertThat(geodis.ggrelation(key, polygon2).size(), is(3));

		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member01".getBytes(), "member02".getBytes(), "member03".getBytes(), "member04".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(0, 0), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(0, 0));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1));
		Point<byte[]> pointb = new Point<byte[]>(2, 2);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));

		Polygon<byte[]> polygon1b = new Polygon<byte[]>(new Point<byte[]>(2, 2), new Point<byte[]>(2, -2), new Point<byte[]>(-2, -2),
				new Point<byte[]>(-2, 2), new Point<byte[]>(2, 2));
		assertThat(geodis.ggrelation(keyb, polygon1b).size(), is(2));// not contain point

		assertThat((Polygon<byte[]>) geodis.ggrelation(keyb, polygonb).get(0), is(polygonb));
		assertThat((LineString<byte[]>) geodis.ggrelation(keyb, linestrb).get(0), is(linestrb));
		assertThat((Point<byte[]>) geodis.ggrelation(keyb, pointb).get(0), is(pointb));

		// contain operation with point (1.9,1.9)
		Point<byte[]> pointqb = new Point<byte[]>(1.9, 1.9);
		assertThat(geodis.ggadd(keyb, membersb[3], valueb, pointqb), is(OKl));
		Polygon<byte[]> polygon2b = new Polygon<byte[]>(new Point<byte[]>(2, 2), new Point<byte[]>(2, -2), new Point<byte[]>(-2, -2),
				new Point<byte[]>(-2, 2), new Point<byte[]>(2, 2));
		assertThat(geodis.ggrelation(keyb, polygon2b).size(), is(3));

		geodis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testggnn() {
		geodis.del(key);
		String[] members = { "member01", "member02", "member03", "member04" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(0, 0), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(0, 0));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(2, 2);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));

		assertThat(geodis.ggnn(key, 0, 0, 3).size(), is(3));
		
		assertTrue(geodis.ggnn(key, 0, 0, 3).contains(polygon));
		assertTrue(geodis.ggnn(key, 0, 0, 3).contains(linestr));
		assertTrue(geodis.ggnn(key, 0, 0, 3).contains(point));
		assertTrue(geodis.ggnn(key, 0, 0, 3).contains(point));
		assertTrue(geodis.ggnn(key, 2, 2, 3).contains(point));

		geodis.del(key);
		
		geodis.del(keyb);
		byte[][] membersb = { "member01".getBytes(), "member02".getBytes(), "member03".getBytes(), "member04".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(0, 0), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(0, 0));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1));
		Point<byte[]> pointb = new Point<byte[]>(2, 2);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));

		assertThat(geodis.ggnn(keyb, 0, 0, 3).size(), is(3));
		
		assertTrue(geodis.ggnn(keyb, 0, 0, 3).contains(polygon));
		assertTrue(geodis.ggnn(keyb, 0, 0, 3).contains(linestr));
		assertTrue(geodis.ggnn(keyb, 0, 0, 3).contains(point));
		assertTrue(geodis.ggnn(keyb, 0, 0, 3).contains(point));
		assertTrue(geodis.ggnn(keyb, 2, 2, 3).contains(point));

		geodis.del(keyb);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testggnnWithMatch() {
		geodis.del(key);
		String[] members = { "member01", "member02", "member03", "member04" };
		Polygon<String> polygon = new Polygon<String>(new Point<String>(0, 0), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(0, 0));
		LineString<String> linestr = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		Point<String> point = new Point<String>(2, 2);
		assertThat(geodis.ggadd(key, members[0], value, polygon), is(OKl));
		assertThat(geodis.ggadd(key, members[1], value, linestr), is(OKl));
		assertThat(geodis.ggadd(key, members[2], value, point), is(OKl));

		assertThat(geodis.ggnnWithMatch(key, 0, 0, 1, "member01*").size(), is(1));
		assertThat(geodis.ggnnWithMatch(key, 0, 0, 1, "*01*").size(), is(1));
		assertThat(geodis.ggnnWithMatch(key, 0, 0, 1, "member?1*").size(), is(1));
		
		assertTrue(geodis.ggnnWithMatch(key, 0, 0, 3, "member*").contains(polygon));
		assertTrue(geodis.ggnnWithMatch(key, 0, 0, 3, "member*").contains(linestr));
		assertTrue(geodis.ggnnWithMatch(key, 0, 0, 3, "member*").contains(point));
		assertTrue(geodis.ggnnWithMatch(key, 0, 0, 3, "member*").contains(point));
		assertTrue(geodis.ggnnWithMatch(key, 2, 2, 3, "member*").contains(point));

		geodis.del(key);
		
		geodis.del(keyb);
		byte[][] membersb = { "member01".getBytes(), "member02".getBytes(), "member03".getBytes(), "member04".getBytes() };
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(0, 0), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(0, 0));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1));
		Point<byte[]> pointb = new Point<byte[]>(2, 2);
		assertThat(geodis.ggadd(keyb, membersb[0], valueb, polygonb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[1], valueb, linestrb), is(OKl));
		assertThat(geodis.ggadd(keyb, membersb[2], valueb, pointb), is(OKl));

		assertThat(geodis.ggnnWithMatch(keyb, 0, 0, 8, "member*".getBytes()).size(), is(3));
		
		assertTrue(geodis.ggnnWithMatch(keyb, 0, 0, 3, "member*".getBytes()).contains(polygonb));
		assertTrue(geodis.ggnnWithMatch(keyb, 0, 0, 3, "member*".getBytes()).contains(linestrb));
		assertTrue(geodis.ggnnWithMatch(keyb, 0, 0, 3, "member*".getBytes()).contains(pointb));
		assertTrue(geodis.ggnnWithMatch(keyb, 0, 0, 3, "member*".getBytes()).contains(pointb));
		assertTrue(geodis.ggnnWithMatch(keyb, 2, 2, 3, "member*".getBytes()).contains(pointb));

		geodis.del(keyb);
	}
}
