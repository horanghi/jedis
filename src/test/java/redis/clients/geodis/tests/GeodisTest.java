package redis.clients.geodis.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Geodis;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public class GeodisTest {

	Geodis geodis;
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
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		geodis = new Geodis("172.19.114.201", 19006);
	}

	@After
	public void tearDown() throws Exception {
		geodis.disconnect();
	}

	@Test
	public void testgaddnGradius() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfrangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(2));
		List<String> points = geodis.gfrangeByRadius(key, 0, 0, 100, UNITS.M);
		for (String point : points) {
			System.out.println(point);
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfrangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(2));
		List<byte[]> pointsb = geodis.gfrangeByRadius(keyb, 0, 0, 100, UNITS.M);
		for (byte[] point : pointsb) {
			System.out.println(new String(point));
		}
		geodis.del(key);
	}

	@Test
	public void testgaddnGradiusDetail() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfrangeByRadius(key, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Circle> circles = geodis.gfrangeByRadiusDetail(key, 0, 0, 100, UNITS.M);
		for (Circle circle : circles) {
			System.out.println(circle.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfrangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Circle> circlesb = geodis.gfrangeByRadiusDetail(keyb, 0, 0, 100, UNITS.M);
		for (Circle circle : circlesb) {
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
		List<Circle> circles = geodis.gfrangeByRadiusWithMatch(key, 0, 0, 100, UNITS.M, "member*");
		for (Circle circle : circles) {
			System.out.println(circle.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfrangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Circle> circlesb = geodis.gfrangeByRadiusWithMatch(keyb, 0, 0, 100, UNITS.M, "member*".getBytes());
		for (Circle circle : circlesb) {
			System.out.println(circle.toString());
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
		List<Circle> circles = geodis.gfrangeByRadiusWithMatch(key, 0, 0, 100, UNITS.M, "memberd*");
		assertThat(circles.size(), is(1));
		assertTrue(circles.iterator().next().equals(new Circle("memberd", 0, 0, 0, UNITS.M, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, "memberd".getBytes(), valueb), is(OKl));
		assertThat(geodis.gfrangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(3));
		List<Circle> circlesb = geodis.gfrangeByRadiusWithMatch(keyb, 0, 0, 100, UNITS.M, "memberd*".getBytes());
		assertThat(circlesb.size(), is(1));
		assertTrue(circlesb.iterator().next().equals(new Circle("memberd".getBytes(), 0, 0, 0, UNITS.M, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgaddnRegion() {
		// [1,1], [1,-1], [-1,-1], [-1,1], [1,1]
		Polygon polygon = new Polygon(new Point(1, 1), new Point(1, -1), new Point(1, 1), new Point(-1, -1), new Point(-1, 1), new Point(1,
				1));
		System.out.println(polygon.getJsonStr());

		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfrangeByRegion(key, polygon).size(), is(2));
		List<Circle> circles = geodis.gfrangeByRegion(key, polygon);
		for (Circle circle : circles) {
			System.out.println(circle.toString());
		}
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfrangeByRadius(keyb, 0, 0, 10, UNITS.KM).size(), is(2));
		List<Circle> circlesb = geodis.gfrangeByRegion(keyb, polygon);
		for (Circle circle : circlesb) {
			System.out.println(circle.toString());
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
		assertTrue(geodis.gfget(key, member1).equals(new Circle(member1, 0, 0, 0, UNITS.M, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertNotNull(geodis.gfget(keyb, member1b));
		assertTrue(geodis.gfget(keyb, member1b).equals(new Circle(member1b, 0, 0, 0, UNITS.M, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgfgetIfnotexist() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertNull(geodis.gfget(key, member3));
		assertTrue(geodis.gfget(key, member1).equals(new Circle(member1, 0, 0, 0, UNITS.M, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertNull(geodis.gfget(keyb, member3b));
		assertTrue(geodis.gfget(keyb, member1b).equals(new Circle(member1b, 0, 0, 0, UNITS.M, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgfmget() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfmget(key, member1).size(), is(1));
		assertTrue(geodis.gfmget(key, member2).iterator().next().equals(new Circle(member2, 0, 0, 0, UNITS.M, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfmget(keyb, member1b).size(), is(1));
		assertTrue(geodis.gfmget(keyb, member2b).iterator().next().equals(new Circle(member2b, 0, 0, 0, UNITS.M, valueb, 0)));
		geodis.del(keyb);
	}

	@Test
	public void testgfmgetIfnotexist() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gfmget(key, member3, member4).size(), is(0));
		assertTrue(geodis.gfmget(key, member2).iterator().next().equals(new Circle(member2, 0, 0, 0, UNITS.M, value, 0)));
		geodis.del(key);

		geodis.del(keyb);
		assertThat(geodis.gadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gfmget(keyb, member3b, member4b).size(), is(0));
		assertTrue(geodis.gfmget(keyb, member2b).iterator().next().equals(new Circle(member2b, 0, 0, 0, UNITS.M, valueb, 0)));
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
		List<Circle> result = geodis.gfnn(key, 0, 0, 4);
		assertThat(result.size(), is(4));
		int idx = 0;
		for (Circle circle : result) {
			circle.equals(new Circle(members[idx++], 0, 0, 0, UNITS.M, value, 0));
		}
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		assertThat(geodis.gadd(keyb, 0.0, 0.0, membersb[0], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.1, 0.1, membersb[1], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.2, 0.2, membersb[2], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.3, 0.3, membersb[3], valueb), is(OKl));
		assertThat(geodis.gfnn(keyb, 0, 0, 3).size(), is(3));
		List<Circle> resultb = geodis.gfnn(keyb, 0, 0, 4);
		assertThat(result.size(), is(4));
		int idx2 = 0;
		for (Circle circle : resultb) {
			circle.equals(new Circle(membersb[idx2++], 0, 0, 0, UNITS.M, valueb, 0));
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
		List<Circle> result = geodis.gfnn(key, 0, 0, 5);
		assertThat(result.size(), is(2));
		int idx = 0;
		for (Circle circle : result) {
			circle.equals(new Circle(members[idx++], 0, 0, 0, UNITS.M, value, 0));
		}
		geodis.del(key);

		geodis.del(keyb);
		byte[][] membersb = { "member1".getBytes(), "member2".getBytes(), "member3".getBytes(), "member4".getBytes() };
		assertThat(geodis.gadd(keyb, 0.0, 0.0, membersb[0], valueb), is(OKl));
		assertThat(geodis.gadd(keyb, 0.1, 0.1, membersb[1], valueb), is(OKl));
		assertThat(geodis.gfnn(keyb, 0, 0, 3).size(), is(3));
		List<Circle> resultb = geodis.gfnn(keyb, 0, 0, 5);
		assertThat(result.size(), is(5));
		int idx2 = 0;
		for (Circle circle : resultb) {
			circle.equals(new Circle(membersb[idx2++], 0, 0, 0, UNITS.M, valueb, 0));
		}
		geodis.del(keyb);
	}
	//
	// @Test
	// public void testgaddnMembers() {
	// String vv = "こんにちはそうこれを使用していただきありがとうございます。";
	// geodis.del(key);
	// assertThat(geodis.gadd(key, 0, 0, 0, member1, vv), is(OKl));
	// assertThat(geodis.gadd(key, 0, 0, 10, member2, vv), is(OKl));
	// assertThat(geodis.gradius(key, 0, 0, 100).size(), is(2));
	// Set<String> points = geodis.gradius(key, 0, 0, 100);
	// for (String point : points) {
	// GCircle go = geodis.gmember(key, point);
	// System.out.println(go.getMember() + " : x. " + go.getX() + " y. " + go.getY() + " dis. " + go.getDistance() + " dsc :"
	// + go.getValue());
	// }
	// geodis.del(key);
	// }
	//
	// @Test
	// public void testgaddCirclengsearchCircle() {
	// geodis.del(key);
	// assertThat(geodis.gaddCircle(key, 0, 0, 10, member1, value), is(OKl));
	// assertThat(geodis.gaddCircle(key, 0, 0, 20, member1, value), is(0l));
	// assertThat(geodis.gaddCircle(key, 0, 0, 30, member2, value), is(OKl));
	// assertThat(geodis.gsearchCircle(key, 0, 0, 100).size(), is(2));
	// Set<GCircle> circles = geodis.gsearchCircle(key, 0, 0, 100);
	// for (GCircle go : circles) {
	// System.out.println(go.getMember() + " : x. " + go.getX() + " y. " + go.getY() + " dis. " + go.getDistance() + " dsc :"
	// + go.getValue());
	// }
	// geodis.del(key);
	// }
	//
	// @Test
	// public void testgaddCirclengsearchCircle4real() {
	// geodis.del(key);
	// assertThat(geodis.gaddCircle(key, 0, 0, 10, member1, value), is(OKl));
	// assertThat(geodis.gaddCircle(key, 0, 0, 20, member1, value), is(0l));
	// assertThat(geodis.gaddCircle(key, 0, 0, 30, member2, value), is(OKl));
	// assertThat(geodis.gsearchCircle(key, 0, 0, 100).size(), is(2));
	// Set<GCircle> circles = geodis.gsearchCircle(key, 0, 0, 100);
	// for (GCircle go : circles) {
	// System.out.println(go.getMember() + " : x. " + go.getX() + " y. " + go.getY() + " dis. " + go.getDistance() + " dsc :"
	// + go.getValue());
	// }
	// geodis.del(key);
	// }
	//
	// @Test
	// public void testgaddCirclengsearchPoint() {
	// geodis.del(key);
	// assertThat(geodis.gaddCircle(key, 0, 0, 0, member1, value), is(OKl));
	// assertThat(geodis.gaddCircle(key, 1, 0, 0, member2, value), is(OKl));
	// assertThat(geodis.gaddCircle(key, 0, 0, 30, member3, value), is(OKl));
	// assertThat(geodis.gsearchPoint(key, 0, 0, 1000000).size(), is(2));
	// Set<GPoint> points = geodis.gsearchPoint(key, 0, 0, 1000000);
	// for (GPoint go : points) {
	// System.out.println(go.getMember() + " : x. " + go.getX() + " y. " + go.getY() + " dsc :" + go.getValue());
	// }
	// geodis.del(key);
	// }

	/*
	 * // 좌표A: 제주국제공항 $lat1 = '33.50674337041539'; // 위도 $lon1 = '126.49184109764144'; // 경도
	 * 
	 * // 좌표B: 한라산 $lat2 = '33.3586056496444'; // 위도 $lon2 = '126.53189806385355'; // 경도
	 */
	// @Test
	// public void testDistance(){
	// System.out.println(geodis.distance(33.50674337041539, 126.49184109764144, 33.3586056496444, 126.53189806385355));
	// //36.36770178
	// //127.40182709999999
	// }

}
