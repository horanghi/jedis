package redis.clients.geodis.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

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

	@Test
	public void testhoranghi() {
		byte[] b1 = "good".getBytes();
		byte[] b2 = "good".getBytes();
		assertTrue(Arrays.equals(b1, b2));
		Point<String>[] opoints = new Point[] { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(-1, -1) };
		Point<String>[] old = new Point[] { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(-1, -1) };

		LineString<String> asis = new LineString<String>(opoints);
		for (Point<String> pp : old) {
			assertTrue(asis.getPoints().contains(pp));
		}
	}

	@Test
	public void testgaddnGradiusDetail() {
		geodis.del(key);
		String[] members = { member1, member2 };
		String[] values = { value, value };
		Point<String>[] opoints = new Point[] { new Point(member1, 0, 0, value), new Point(member2, 0, 0, value) };
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfrangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<String>> Points = geodis.gfrangeByRadius(key, 0, 0, 100, UNITS.M);
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
		assertThat(geodis.gfrangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<byte[]>> Pointsb = geodis.gfrangeByRadius(keyb, 0, 0, 100, UNITS.M);
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
		assertThat(geodis.gfadd(key, 0, 0, 10, UNITS.M, member3, value), is(OKl));
		assertThat(geodis.gfrangeCircleByRadius(key, 0, 0, 10, UNITS.KM).size(), is(1));
		List<Circle<String>> circles = geodis.gfrangeCircleByRadius(key, 0, 0, 100, UNITS.M);
		for (Circle<String> circle : circles) {
			System.out.println(circle.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfadd(key, 0, 0, 10, UNITS.M, member3, value), is(OKl));
		assertThat(geodis.gfadd(key, 0, 0, 30, UNITS.M, member4, value), is(OKl));
		assertThat(geodis.gfrangeCircleByRadius(keyb, 0, 0, 50, UNITS.M).size(), is(2));
		List<Circle<byte[]>> circlesb = geodis.gfrangeCircleByRadius(keyb, 0, 0, 50, UNITS.M);
		for (Circle<byte[]> circle : circlesb) {
			System.out.println(circle.toString());
		}
		geodis.del(keyb);
	}

	@Test
	public void testgaddnGradiusWithMatch() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfrangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<String>> Points = geodis.gfrangeByRadiusWithMatch(key, 0, 0, 10, M, "member*");
		for (Point<String> point : Points) {
			System.out.println(point.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfrangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<byte[]>> Pointsb = geodis.gfrangeByRadiusWithMatch(keyb, 0, 0, 10, M, "member*".getBytes());
		for (Point<byte[]> point : Pointsb) {
			System.out.println(point.toString());
		}
		geodis.del(keyb);
	}

	@Test
	public void testgaddnGradiusForCircleWithMatch() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfadd(key, 0, 0, 10, UNITS.M, member3, value), is(OKl));
		assertThat(geodis.gfadd(key, 0, 0, 30, UNITS.M, member4, value), is(OKl));
		assertThat(geodis.gfrangeCircleByRadius(key, 0, 0, 10, UNITS.M).size(), is(1));
		assertThat(geodis.gfrangeCircleByRadius(key, 0, 0, 30, UNITS.KM).size(), is(2));
		List<Circle<String>> circles = geodis.gfrangeCircleByRadiusWithMatch(key, 0, 0, 40, M, "*memkey4*");
		assertThat(circles.size(), is(1));
		for (Circle<String> point : circles) {
			System.out.println(point.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfadd(keyb, 0, 0, 10, UNITS.M, member3b, valueb), is(OKl));
		assertThat(geodis.gfadd(keyb, 0, 0, 30, UNITS.M, member4b, valueb), is(OKl));
		assertThat(geodis.gfrangeCircleByRadius(key, 0, 0, 10, UNITS.M).size(), is(1));
		assertThat(geodis.gfrangeCircleByRadius(key, 0, 0, 30, UNITS.KM).size(), is(2));
		List<Circle<byte[]>> circlesb = geodis.gfrangeCircleByRadiusWithMatch(keyb, 0, 0, 40, M, "*4*".getBytes());
		assertThat(circlesb.size(), is(1));
		for (Circle<byte[]> point : circlesb) {
			System.out.println(point.toString());
		}
		geodis.del(keyb);
	}

	@Test
	public void testgaddnGradiusWithMatchIfnotMatch() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, "memberd", value), is(OKl));
		assertThat(geodis.gfrangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(3));
		List<Point<String>> Points = geodis.gfrangeByRadiusWithMatch(key, 0, 0, 10, M, "memberd*");
		assertThat(Points.size(), is(1));
		assertTrue(Points.iterator().next().equals(new Point<String>("memberd", 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, "memberd".getBytes(), valueb), is(OKl));
		assertThat(geodis.gfrangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(3));
		List<Point<byte[]>> Pointsb = geodis.gfrangeByRadiusWithMatch(keyb, 0, 0, 10, M, "memberd*".getBytes());
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
		assertThat(geodis.gfrangeByRegion(key, polygon).size(), is(2));
		List<Point<String>> Points = geodis.gfrangeByRegion(key, polygon);
		for (Point<String> point : Points) {
			System.out.println(point.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfrangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<byte[]>> Pointsb = geodis.gfrangeByRegion(keyb, polygon);
		for (Point<byte[]> point : Pointsb) {
			System.out.println(point.toString());
		}
		geodis.del(keyb);
	}

	@Test
	public void testgfcard() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfcard(key), is(2l));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfcard(keyb), is(2l));
		geodis.del(keyb);
	}

	@Test
	public void testgfrem() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfrem(key, member1), is(1l));
		assertThat(geodis.gfcard(key), is(1l));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfrem(keyb, member1b), is(1l));
		assertThat(geodis.gfcard(keyb), is(1l));
		geodis.del(keyb);
	}

	@Test
	public void testgfget() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertNotNull(geodis.gfget(key, member1));
		assertTrue(geodis.gfget(key, member1).equals(new Point<String>(member1, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertNotNull(geodis.gfget(keyb, member1b));
		assertTrue(geodis.gfget(keyb, member1b).equals(new Point<byte[]>(member1b, 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgfgetIfnotexist() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertNull(geodis.gfget(key, member3));
		assertTrue(geodis.gfget(key, member1).equals(new Point<String>(member1, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertNull(geodis.gfget(keyb, member3b));
		assertTrue(geodis.gfget(keyb, member1b).equals(new Point<byte[]>(member1b, 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgfmget() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfmget(key, member1).size(), is(1));
		assertTrue(geodis.gfmget(key, member2).iterator().next().equals(new Point<String>(member2, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfmget(keyb, member1b).size(), is(1));
		assertTrue(geodis.gfmget(keyb, member2b).iterator().next().equals(new Point<byte[]>(member2b, 0, 0, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgfmgetIfnotexist() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfmget(key, member3, member4).size(), is(0));
		assertTrue(geodis.gfmget(key, member2).iterator().next().equals(new Point<String>(member2, 0, 0, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfmget(keyb, member3b, member4b).size(), is(0));
		assertTrue(geodis.gfmget(keyb, member2b).iterator().next().equals(new Point<byte[]>(member2b, 0, 0, valueb, 0)));
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
		assertThat(geodis.gfnn(key, 0, 0, 3).size(), is(3));
		List<Point<String>> result = geodis.gfnn(key, 0, 0, 4);
		assertThat(result.size(), is(4));
		int idx = 0;
		for (Point<String> point : result) {
			point.equals(new Point<String>(members[idx++], 0, 0, value, 0));
		}
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		assertThat(geodis.gadd(keyb, 0.0, 0.0, membersb[0], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.1, 0.1, membersb[1], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.2, 0.2, membersb[2], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.3, 0.3, membersb[3], valueb), is(OKl));
		assertThat(geodis.gfnn(keyb, 0, 0, 3).size(), is(3));
		List<Point<byte[]>> resultb = geodis.gfnn(keyb, 0, 0, 4);
		assertThat(result.size(), is(4));
		int idx2 = 0;
		for (Point<byte[]> point : resultb) {
			point.equals(new Point<byte[]>(membersb[idx2++], 0, 0, valueb, 0));
		}
		geodis.del(keyb);
	}

	// @Test
	public void testgfnnIfnotexist() {
		geodis.del(key);
		String[] members = { "member1", "member2", "member3", "member4" };
		assertThat(geodis.gadd(key, 0.0, 0.0, members[0], value), is(OKl));
		assertThat(geodis.gadd(key, 0.1, 0.1, members[1], value), is(OKl));
		assertThat(geodis.gfnn(key, 0, 0, 5).size(), is(2));
		List<Point<String>> result = geodis.gfnn(key, 0, 0, 5);
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
		assertThat(geodis.gfnn(keyb, 0, 0, 3).size(), is(3));
		List<Point<byte[]>> resultb = geodis.gfnn(keyb, 0, 0, 5);
		assertThat(result.size(), is(5));
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
		assertTrue(geodis.ggget(keyb, membersb[2]).equals(new Point<byte[]>(1, 1)));
		geodis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testggmget() {
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
	// @Test
	// public void testgggetIfnotexist() {
	// geodis.del(key);
	// assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
	// assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
	// assertNull(geodis.gfget(key, member3));
	// assertTrue(geodis.gfget(key, member1).equals(new Point<String>(member1, 0, 0, value, 0)));
	// geodis.del(key);
	//
	// geodis.del(keyb);
	// assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
	// assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
	// assertNull(geodis.gfget(keyb, member3b));
	// assertTrue(geodis.gfget(keyb, member1b).equals(new Point<byte[]>(member1b, 0, 0, valueb, 0)));
	// geodis.del(keyb);
	// }
	//
	// @Test
	// public void testggmget() {
	// geodis.del(key);
	// assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
	// assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
	// assertThat(geodis.gfmget(key, member1).size(), is(1));
	// assertTrue(geodis.gfmget(key, member2).iterator().next().equals(new Point<String>(member2, 0, 0, value, 0)));
	// geodis.del(key);
	//
	// geodis.del(keyb);
	// assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
	// assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
	// assertThat(geodis.gfmget(keyb, member1b).size(), is(1));
	// assertTrue(geodis.gfmget(keyb, member2b).iterator().next().equals(new Point<byte[]>(member2b, 0, 0, valueb, 0)));
	// geodis.del(keyb);
	// }
	//
	// @Test
	// public void testggmgetIfnotexist() {
	// geodis.del(key);
	// assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
	// assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
	// assertThat(geodis.gfmget(key, member3, member4).size(), is(0));
	// assertTrue(geodis.gfmget(key, member2).iterator().next().equals(new Point<String>(member2, 0, 0, value, 0)));
	// geodis.del(key);
	//
	// geodis.del(keyb);
	// assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
	// assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
	// assertThat(geodis.gfmget(keyb, member3b, member4b).size(), is(0));
	// assertTrue(geodis.gfmget(keyb, member2b).iterator().next().equals(new Point<byte[]>(member2b, 0, 0, valueb, 0)));
	// geodis.del(keyb);
	// }
}
