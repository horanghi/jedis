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
	}

	@Test
	public void testgaddnGradiusDetail() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfrangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Point<String>> Points = geodis.gfrangeByRadius(key, 0, 0, 100, UNITS.M);
		for (Point<String> point : Points) {
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
		List<Point<String>> Points = geodis.gfrangeCircleByRadiusWithMatch(key, 0, 0, 40, M, "*memkey4*");
		assertThat(Points.size(), is(1));
		for (Point<String> point : Points) {
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
		List<Point<byte[]>> Pointsb = geodis.gfrangeCircleByRadiusWithMatch(keyb, 0, 0, 40, M, "*4*".getBytes());
		assertThat(Pointsb.size(), is(1));
		for (Point<byte[]> point : Pointsb) {
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
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1),
				new Point<String>(-1, -1), new Point<String>(-1, 1), new Point<String>(1, 1));
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

}
