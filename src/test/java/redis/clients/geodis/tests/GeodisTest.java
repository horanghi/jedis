package redis.clients.geodis.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.GCircle;
import redis.clients.jedis.GPoint;
import redis.clients.jedis.Geodis;

public class GeodisTest {

	Geodis geodis;
	String key = "helloKey";
	String member1 = "memkey1";
	String member2 = "memkey2";
	String member3 = "memkey3";
	String value = "desc";

	Long OKl = 1l;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		geodis = new Geodis("172.19.114.201", 19005);
	}

	@After
	public void tearDown() throws Exception {
		geodis.disconnect();
	}

	@Test
	public void testgaddnGradius() {
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 10, member2, value), is(OKl));
		assertThat(geodis.gradius(key, 0, 0, 100).size(), is(2));
		Set<String> points = geodis.gradius(key, 0, 0, 100);
		for (String point : points) {
			System.out.println(point);
		}
		geodis.del(key);
	}

	@Test
	public void testgaddnMembers() {
		String vv = "こんにちはそうこれを使用していただきありがとうございます。";
		geodis.del(key);
		assertThat(geodis.gadd(key, 0, 0, 0, member1, vv), is(OKl));
		assertThat(geodis.gadd(key, 0, 0, 10, member2, vv), is(OKl));
		assertThat(geodis.gradius(key, 0, 0, 100).size(), is(2));
		Set<String> points = geodis.gradius(key, 0, 0, 100);
		for (String point : points) {
			GCircle go = geodis.gmember(key, point);
			System.out.println(go.getMember() + " : x. " + go.getX() + " y. " + go.getY() + " dis. " + go.getDistance() + " dsc :"
					+ go.getValue());
		}
		geodis.del(key);
	}

	@Test
	public void testgaddCirclengsearchCircle() {
		geodis.del(key);
		assertThat(geodis.gaddCircle(key, 0, 0, 10, member1, value), is(OKl));
		assertThat(geodis.gaddCircle(key, 0, 0, 20, member1, value), is(0l));
		assertThat(geodis.gaddCircle(key, 0, 0, 30, member2, value), is(OKl));
		assertThat(geodis.gsearchCircle(key, 0, 0, 100).size(), is(2));
		Set<GCircle> circles = geodis.gsearchCircle(key, 0, 0, 100);
		for (GCircle go : circles) {
			System.out.println(go.getMember() + " : x. " + go.getX() + " y. " + go.getY() + " dis. " + go.getDistance() + " dsc :"
					+ go.getValue());
		}
		geodis.del(key);
	}

	@Test
	public void testgaddCirclengsearchCircle4real() {
		geodis.del(key);
		assertThat(geodis.gaddCircle(key, 0, 0, 10, member1, value), is(OKl));
		assertThat(geodis.gaddCircle(key, 0, 0, 20, member1, value), is(0l));
		assertThat(geodis.gaddCircle(key, 0, 0, 30, member2, value), is(OKl));
		assertThat(geodis.gsearchCircle(key, 0, 0, 100).size(), is(2));
		Set<GCircle> circles = geodis.gsearchCircle(key, 0, 0, 100);
		for (GCircle go : circles) {
			System.out.println(go.getMember() + " : x. " + go.getX() + " y. " + go.getY() + " dis. " + go.getDistance() + " dsc :"
					+ go.getValue());
		}
		geodis.del(key);
	}

	@Test
	public void testgaddCirclengsearchPoint() {
		geodis.del(key);
		assertThat(geodis.gaddCircle(key, 0, 0, 0, member1, value), is(OKl));
		assertThat(geodis.gaddCircle(key, 1, 0, 0, member2, value), is(OKl));
		assertThat(geodis.gaddCircle(key, 0, 0, 30, member3, value), is(OKl));
		assertThat(geodis.gsearchPoint(key, 0, 0, 1000000).size(), is(2));
		Set<GPoint> points = geodis.gsearchPoint(key, 0, 0, 1000000);
		for (GPoint go : points) {
			System.out.println(go.getMember() + " : x. " + go.getX() + " y. " + go.getY() + " dsc :" + go.getValue());
		}
		geodis.del(key);
	}

	/*
	 * // 좌표A: 제주국제공항 $lat1 = '33.50674337041539'; // 위도 $lon1 = '126.49184109764144'; // 경도
	 * 
	 * // 좌표B: 한라산 $lat2 = '33.3586056496444'; // 위도 $lon2 = '126.53189806385355'; // 경도
	 */
//	 @Test
//	 public void testDistance(){
//	 System.out.println(geodis.distance(33.50674337041539, 126.49184109764144, 33.3586056496444, 126.53189806385355));
//	 //36.36770178
//	 //127.40182709999999
//	 }

}
