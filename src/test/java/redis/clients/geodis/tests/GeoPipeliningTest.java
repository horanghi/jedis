package redis.clients.geodis.tests;

import static org.hamcrest.CoreMatchers.is;
import static redis.clients.jedis.Protocol.UNITS.KM;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Response;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public class GeoPipeliningTest extends Assert {
	static JedisPool geodisPool;
	private Jedis jedis;

	Long OKl = 1l;

	String keyf = "helloKeyf";
	String keyg = "helloKeyg";
	String member1 = "memkey1";
	String member2 = "memkey2";
	String member3 = "memkey3";
	String member4 = "memkey4";
	String member5 = "memkey5";
	String member6 = "memkey6";
	String member7 = "memkey7";
	String member8 = "memkey8";
	String member9 = "memkey9";
	String member10 = "memkey10";
	String member11 = "memkey11";
	String value = "desc";

	byte[] keyb = keyf.getBytes();
	byte[] member1b = member1.getBytes();
	byte[] member2b = member2.getBytes();
	byte[] member3b = member3.getBytes();
	byte[] member4b = member4.getBytes();
	byte[] member5b = member5.getBytes();
	byte[] member6b = member6.getBytes();
	byte[] member7b = member7.getBytes();
	byte[] member8b = member8.getBytes();
	byte[] member9b = member9.getBytes();
	byte[] member10b = member10.getBytes();
	byte[] valueb = value.getBytes();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		geodisPool = new JedisPool("172.19.114.202", 19006);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		geodisPool.destroy();
	}

	@Before
	public void setUp() throws Exception {
		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));

		jedis = geodisPool.getResource();
		jedis.flushAll();
		jedis.gpadd(keyf, 0, 0, member1, value);
		jedis.gpadd(keyf, 0, 0, member2, value);
		jedis.gpadd(keyf, 0, 0, member3, value);
		jedis.gpadd(keyf, 0, 0, member4, value);
		jedis.gpadd(keyf, 0, 0, member5, value);
		jedis.gpadd(keyf, 0, 0, member11, value);

		jedis.gpadd(keyf, 0.1, 0.1, 1, M, member6, value);
		jedis.gpadd(keyf, 0.1, 0.2, 1, M, member7, value);
		jedis.gpadd(keyf, 0.1, 0.3, 1, M, member8, value);
		jedis.gpadd(keyf, 0.1, 0.4, 1, M, member9, value);
		jedis.gpadd(keyf, 0.1, 0.5, 1, M, member10, value);

		jedis.ggadd(keyg, member1, value, polygon);
		jedis.ggadd(keyg, member2, value, polygon);
		jedis.ggadd(keyg, member3, value, polygon);
		jedis.ggadd(keyg, member4, value, polygon);
		jedis.ggadd(keyg, member5, value, polygon);

		jedis.del(new String[] { "foo", "bar", "string", "hash", "list", "set", "zset" });
	}

	@After
	public void release() throws Exception {
		jedis.flushAll();
		geodisPool.returnResource(jedis);
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void pipelinegpregionByMember() throws UnsupportedEncodingException {
		jedis.del(keyf);
		jedis.gpadd(keyf, 0.1, 0.1, member1, value, 10);
		jedis.gpadd(keyf, 0.1, 0.2, member2, value, 20);
		jedis.gpadd(keyf, 0.1, 0.3, member3, value, 30);
		jedis.gpadd(keyf, 0.1, 0.4, member4, value, 40);

		Point<String> point1 = new Point<String>(member1, 0.1, 0.1, value);
		Point<String> point2 = new Point<String>(member2, 0.1, 0.2, value);
		Point<String> point3 = new Point<String>(member3, 0.1, 0.3, value);
		Point<String> point4 = new Point<String>(member4, 0.1, 0.4, value);
		Point<String> point5 = new Point<String>(member5, 0.1, 0.5, value);

		Point<byte[]> point1b = new Point<byte[]>(member1b, 0.1, 0.1, valueb);
		Point<byte[]> point2b = new Point<byte[]>(member2b, 0.1, 0.2, valueb);
		Point<byte[]> point3b = new Point<byte[]>(member3b, 0.1, 0.3, valueb);
		Point<byte[]> point4b = new Point<byte[]>(member4b, 0.1, 0.4, valueb);
		Point<byte[]> point5b = new Point<byte[]>(member5b, 0.1, 0.5, valueb);

		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));

		jedis.ggadd(keyg, member5, value, polygon);
		double distance = jedis.gpdistance(0.1, 0.4, 0.1, 0.5) + 0.001;

		Pipeline p = jedis.pipelined();
		p.gpregionByMember(keyf, keyg, member5); // 0
		p.gpregionByMember(keyb, keyg.getBytes(), member5.getBytes());// 1
		p.gpregionByMember(keyf, keyg, member5, "memkey1"); // 2
		p.gpregionByMember(keyb, keyg.getBytes(), member5.getBytes(), "memkey1".getBytes());// 3
		p.gpregionByMember(keyf, keyg, member5, "10", "20", "memkey1");// 4
		p.gpregionByMember(keyb, keyg.getBytes(), member5.getBytes(), "10".getBytes(), "20".getBytes(), "memkey1".getBytes());// 5
		p.gpregionByMember(keyf, keyg, member5, "10", "20", "memkey1", 0, 10, ORDERBY.DISTANCE_DESC);// 6
		p.gpregionByMember(keyb, keyg.getBytes(), member5.getBytes(), "10".getBytes(), "20".getBytes(), "memkey1".getBytes(), 0, 10,
				ORDERBY.DISTANCE_DESC);// 7
		List<Object> results = p.syncAndReturnAll();

		assertEquals(8, results.size());
		assertTrue(((List<Point<String>>) results.get(0)).get(0).getMember().equals(member1));
		assertThat(((List<Point<String>>) results.get(0)).size(), is(4));
		assertTrue(((List<Point<String>>) results.get(0)).contains(point1));
		assertTrue(((List<Point<String>>) results.get(0)).contains(point2));
		assertTrue(((List<Point<String>>) results.get(0)).contains(point3));
		assertTrue(((List<Point<String>>) results.get(0)).contains(point4));
		assertFalse(((List<Point<String>>) results.get(0)).contains(point5));

		assertTrue(((List<Point<byte[]>>) results.get(1)).contains(point4b));
		assertFalse(((List<Point<byte[]>>) results.get(1)).contains(point5b));
		assertThat(((List<Point<byte[]>>) results.get(1)).size(), is(4));

		assertThat(((List<Point<String>>) results.get(2)).size(), is(1));
		assertTrue(((List<Point<String>>) results.get(2)).contains(point1));
		assertFalse(((List<Point<String>>) results.get(2)).contains(point2));

		 assertThat(((List<Point<byte[]>>) results.get(3)).size(), is(1));
		 assertTrue(((List<Point<byte[]>>) results.get(3)).contains(point1b));
		 assertFalse(((List<Point<byte[]>>) results.get(3)).contains(point2b));

		assertThat(((List<Point<String>>) results.get(4)).size(), is(1));
		assertTrue(((List<Point<String>>) results.get(4)).contains(point1));
		assertFalse(((List<Point<String>>) results.get(4)).contains(point2));

		assertThat(((List<Point<byte[]>>) results.get(5)).size(), is(1));
		assertTrue(((List<Point<byte[]>>) results.get(5)).contains(point1b));
		assertFalse(((List<Point<byte[]>>) results.get(5)).contains(point2b));

		assertThat(((List<Point<String>>) results.get(6)).size(), is(1));
		assertTrue(((List<Point<String>>) results.get(6)).contains(point1));
		assertFalse(((List<Point<String>>) results.get(6)).contains(point2));

		assertThat(((List<Point<byte[]>>) results.get(7)).size(), is(1));
		assertTrue(((List<Point<byte[]>>) results.get(7)).contains(point1b));
		assertFalse(((List<Point<byte[]>>) results.get(7)).contains(point2b));

		jedis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelinegrangeByRadius() throws UnsupportedEncodingException {
		jedis.del(keyf);
		jedis.gpadd(keyf, 0.1, 0.1, member1, value);
		jedis.gpadd(keyf, 0.1, 0.2, member2, value);
		jedis.gpadd(keyf, 0.1, 0.3, member3, value);
		jedis.gpadd(keyf, 0.1, 0.4, member4, value);
		jedis.gpadd(keyf, 0.1, 0.5, member5, value);

		Pipeline p = jedis.pipelined();
		p.gpradius(keyf, 0.1, 0.1, 1, M);
		p.gpradius(keyf, 0.1, 0.2, 1, M);
		p.gpradius(keyf, 0.1, 0.3, 1, M);
		p.gpradius(keyf, 0.1, 0.4, 1, M);
		p.gpradius(keyf, 0.1, 0.5, 1, M);
		List<Object> results = p.syncAndReturnAll();

		assertEquals(5, results.size());
		assertTrue(((List<Point<String>>) results.get(0)).get(0).getMember().equals(member1));
		assertTrue(((List<Point<String>>) results.get(1)).get(0).getMember().equals(member2));
		assertTrue(((List<Point<String>>) results.get(2)).get(0).getMember().equals(member3));
		assertTrue(((List<Point<String>>) results.get(3)).get(0).getMember().equals(member4));
		assertTrue(((List<Point<String>>) results.get(4)).get(0).getMember().equals(member5));

		assertThat(((List<Point<String>>) results.get(0)).get(0), is(new Point<String>(member1, 0.1, 0.1, value)));
		assertThat(((List<Point<String>>) results.get(1)).get(0), is(new Point<String>(member2, 0.1, 0.2, value)));
		assertThat(((List<Point<String>>) results.get(2)).get(0), is(new Point<String>(member3, 0.1, 0.3, value)));
		assertThat(((List<Point<String>>) results.get(3)).get(0), is(new Point<String>(member4, 0.1, 0.4, value)));
		assertThat(((List<Point<String>>) results.get(4)).get(0), is(new Point<String>(member5, 0.1, 0.5, value)));

		assertTrue(((List<Point<String>>) results.get(4)).get(0).equals(new Point<String>(member5, 0.1, 0.5, value)));

		p.gpradius(keyb, 0.1, 0.1, 1, M);
		p.gpradius(keyb, 0.1, 0.2, 1, M);
		p.gpradius(keyb, 0.1, 0.3, 1, M);
		p.gpradius(keyb, 0.1, 0.4, 1, M);
		p.gpradius(keyb, 0.1, 0.5, 1, M);
		List<Object> resultsb = p.syncAndReturnAll();

		assertEquals(5, results.size());
		assertTrue((new String(((List<Point<byte[]>>) resultsb.get(0)).get(0).getMember())).equals(new String(member1b)));
		assertTrue((new String(((List<Point<byte[]>>) resultsb.get(1)).get(0).getMember())).equals(new String(member2b)));
		assertTrue((new String(((List<Point<byte[]>>) resultsb.get(2)).get(0).getMember())).equals(new String(member3b)));
		assertTrue((new String(((List<Point<byte[]>>) resultsb.get(3)).get(0).getMember())).equals(new String(member4b)));
		assertTrue((new String(((List<Point<byte[]>>) resultsb.get(4)).get(0).getMember())).equals(new String(member5b)));

		assertThat(((List<Point<byte[]>>) results.get(0)).get(0), is(new Point<byte[]>(member1b, 0.1, 0.1, valueb)));
		assertThat(((List<Point<byte[]>>) results.get(1)).get(0), is(new Point<byte[]>(member2b, 0.1, 0.2, valueb)));
		assertThat(((List<Point<byte[]>>) results.get(2)).get(0), is(new Point<byte[]>(member3b, 0.1, 0.3, valueb)));
		assertThat(((List<Point<byte[]>>) results.get(3)).get(0), is(new Point<byte[]>(member4b, 0.1, 0.4, valueb)));
		assertThat(((List<Point<byte[]>>) results.get(4)).get(0), is(new Point<byte[]>(member5b, 0.1, 0.5, valueb)));

		jedis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelinegrangeCircleByRadius() throws UnsupportedEncodingException {
		Pipeline p = jedis.pipelined();
		p.gpcircle(keyf, 0.1, 0.1, 1, M);
		p.gpcircle(keyf, 0.1, 0.2, 1, M);
		p.gpcircle(keyf, 0.1, 0.3, 1, M);
		p.gpcircle(keyf, 0.1, 0.4, 1, M);
		p.gpcircle(keyf, 0.1, 0.5, 1, M);
		p.gpupdate(keyf, member11, 0, 0);
		p.gpget(keyf, member11);
		p.gpupdate(keyf, member11, 10, 10, 1, M);
		p.gpget(keyf, member11);
		List<Object> results = p.syncAndReturnAll();

		assertEquals(9, results.size());
		System.out.println(((List<Circle<String>>) results.get(0)).get(0));
		assertTrue(((List<Circle<String>>) results.get(0)).get(0).getMember().equals(member6));
		assertTrue(((List<Circle<String>>) results.get(1)).get(0).getMember().equals(member7));
		assertTrue(((List<Circle<String>>) results.get(2)).get(0).getMember().equals(member8));
		assertTrue(((List<Circle<String>>) results.get(3)).get(0).getMember().equals(member9));
		assertTrue(((List<Circle<String>>) results.get(4)).get(0).getMember().equals(member10));

		assertTrue(((List<Circle<String>>) results.get(4)).get(0).equals(new Circle<String>(member5, 0.1, 0.5, 1, M, value)));

		assertThat((Long) results.get(5), is(OKl));
		Point<String> pp00 = new Point<String>(member11, 0, 0, value);
		assertThat((Point<String>) results.get(6), is(pp00));
		assertThat((Long) results.get(7), is(OKl));
		Point<String> pp11 = new Point<String>(member11, 10, 10, value);
		assertThat((Point<String>) results.get(8), is(pp11));

		p.gpcircle(keyb, 0.1, 0.1, 1, M);
		p.gpcircle(keyb, 0.1, 0.2, 1, M);
		p.gpcircle(keyb, 0.1, 0.3, 1, M);
		p.gpcircle(keyb, 0.1, 0.4, 1, M);
		p.gpcircle(keyb, 0.1, 0.5, 1, M);
		List<Object> resultsb = p.syncAndReturnAll();

		assertEquals(9, results.size());
		System.out.println(((List<Circle<byte[]>>) resultsb.get(0)).get(0));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(0)).get(0).getMember()).equals(member6));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(1)).get(0).getMember()).equals(member7));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(2)).get(0).getMember()).equals(member8));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(3)).get(0).getMember()).equals(member9));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(4)).get(0).getMember()).equals(member10));

		assertTrue(((List<Circle<byte[]>>) resultsb.get(4)).get(0).equals(new Circle<byte[]>(member5b, 0.1, 0.5, 1, M, valueb)));
		jedis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelinegrangeCircleByRadiusWithMatch() throws UnsupportedEncodingException {
		Pipeline p = jedis.pipelined();
		p.gpcircle(keyf, 0.1, 0.1, 1, M, "*");
		p.gpcircle(keyf, 0.1, 0.2, 1, M, "memkey7");
		p.gpcircle(keyf, 0.1, 0.3, 1, M, "?emkey8*");
		p.gpcircle(keyf, 0.1, 0.4, 1, M, "*memk?y9");
		p.gpcircle(keyf, 0.1, 0.5, 1, M, "*memkey10*");
		p.gpcircle(keyf, 0.1, 0.5, 1, M, "*memkey10*", RELATION.CONTAINS, ORDERBY.DISTANCE_ASC);
		List<Object> results = p.syncAndReturnAll();

		assertEquals(6, results.size());
		System.out.println(((List<Circle<String>>) results.get(0)).get(0));
		assertTrue(((List<Circle<String>>) results.get(0)).get(0).getMember().equals(member6));
		assertTrue(((List<Circle<String>>) results.get(1)).get(0).getMember().equals(member7));
		assertTrue(((List<Circle<String>>) results.get(2)).get(0).getMember().equals(member8));
		assertTrue(((List<Circle<String>>) results.get(3)).get(0).getMember().equals(member9));
		assertTrue(((List<Circle<String>>) results.get(4)).get(0).getMember().equals(member10));
		assertTrue(((List<Circle<String>>) results.get(5)).get(0).getMember().equals(member10));

		assertTrue(((List<Circle<String>>) results.get(4)).get(0).equals(new Circle<String>(member5, 0.1, 0.5, 1, M, value)));

		p.gpcircle(keyb, 0.1, 0.1, 1, M, "*".getBytes());
		p.gpcircle(keyb, 0.1, 0.2, 1, M, "memkey7".getBytes());
		p.gpcircle(keyb, 0.1, 0.3, 1, M, "?emkey8*".getBytes());
		p.gpcircle(keyb, 0.1, 0.4, 1, M, "*memk?y9".getBytes());
		p.gpcircle(keyb, 0.1, 0.5, 1, M, "*memkey10*".getBytes());
		p.gpcircle(keyb, 0.1, 0.5, 1, M, "*memkey10*".getBytes(), RELATION.CONTAINS, ORDERBY.DISTANCE_ASC);
		List<Object> resultsb = p.syncAndReturnAll();

		assertEquals(6, results.size());
		System.out.println(((List<Circle<byte[]>>) resultsb.get(0)).get(0));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(0)).get(0).getMember()).equals(member6));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(1)).get(0).getMember()).equals(member7));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(2)).get(0).getMember()).equals(member8));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(3)).get(0).getMember()).equals(member9));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(4)).get(0).getMember()).equals(member10));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(5)).get(0).getMember()).equals(member10));

		assertTrue(((List<Circle<byte[]>>) resultsb.get(4)).get(0).equals(new Circle<byte[]>(member5b, 0.1, 0.5, 1, M, valueb)));
		jedis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelinegrangeByRadiusMatch() throws UnsupportedEncodingException {
		Pipeline p = jedis.pipelined();
		p.gpradius(keyf, 0.1, 0.1, 20, KM, "memkey1*");
		p.gpradius(keyf, 0.1, 0.2, 25, KM, "memkey2*");
		p.gpradius(keyf, 0.1, 0.3, 36, KM, "*memkey3");
		p.gpradius(keyf, 0.1, 0.4, 46, KM, "*memkey4*");
		p.gpradius(keyf, 0.1, 0.5, 57, KM, "memkey5**");
		List<Object> results = p.syncAndReturnAll();

		assertEquals(5, results.size());

		assertTrue(((List<Point<String>>) results.get(0)).get(0).getMember().equals(member1));
		assertTrue(((List<Point<String>>) results.get(1)).get(0).getMember().equals(member2));
		assertTrue(((List<Point<String>>) results.get(2)).get(0).getMember().equals(member3));
		assertTrue(((List<Point<String>>) results.get(3)).get(0).getMember().equals(member4));
		assertTrue(((List<Point<String>>) results.get(4)).get(0).getMember().equals(member5));

		assertTrue(((List<Point<String>>) results.get(4)).get(0).equals(new Point<String>(member5, 0, 0, value)));

		p.gpradius(keyb, 0.1, 0.1, 20, KM, "memkey1*".getBytes());
		p.gpradius(keyb, 0.1, 0.2, 25, KM, "memkey2*".getBytes());
		p.gpradius(keyb, 0.1, 0.3, 36, KM, "memkey3*".getBytes());
		p.gpradius(keyb, 0.1, 0.4, 46, KM, "memkey4".getBytes());
		p.gpradius(keyb, 0.1, 0.5, 57, KM, "memkey5**".getBytes());

		p.gpradius(keyb, 0.1, 0.5, 57, KM, "memkey6**".getBytes());

		List<Object> resultsb = p.syncAndReturnAll();

		assertEquals(5, results.size());
		assertTrue(new String(((List<Point<byte[]>>) resultsb.get(0)).get(0).getMember()).equals(member1));
		assertTrue(new String(((List<Point<byte[]>>) resultsb.get(1)).get(0).getMember()).equals(member2));
		assertTrue(new String(((List<Point<byte[]>>) resultsb.get(2)).get(0).getMember()).equals(member3));
		assertTrue(new String(((List<Point<byte[]>>) resultsb.get(3)).get(0).getMember()).equals(member4));
		assertTrue(new String(((List<Point<byte[]>>) resultsb.get(4)).get(0).getMember()).equals(member5));

		assertTrue(((List<Point<byte[]>>) resultsb.get(5)).size() == 0);

		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).get(0).equals(new Point<byte[]>(member5b, 0, 0, valueb)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelinegnn() throws UnsupportedEncodingException {
		jedis.del(keyf);

		jedis.gpadd(keyf, 0, 0.01, member1, value);
		jedis.gpadd(keyf, 0, 0.02, member2, value);
		jedis.gpadd(keyf, 0, 0.03, member3, value);
		jedis.gpadd(keyf, 0, 0.04, member4, value);
		jedis.gpadd(keyf, 0, 0.05, member5, value);

		Pipeline p = jedis.pipelined();
		p.gpnn(keyf, 0, 0.1, 0, 1);
		p.gpnn(keyf, 0, 0.2, 0, 2);
		p.gpnn(keyf, 0, 0.3, 0, 3);
		p.gpnn(keyf, 0, 0.4, 0, 4);
		p.gpnn(keyf, 0, 0.5, 0, 2);
		List<Object> results = p.syncAndReturnAll();

		assertEquals(5, results.size());

		Point<String> mm1 = new Point<String>(member1, 0, 0.01, value);
		Point<String> mm2 = new Point<String>(member2, 0, 0.02, value);
		Point<String> mm3 = new Point<String>(member3, 0, 0.03, value);
		Point<String> mm4 = new Point<String>(member4, 0, 0.04, value);
		Point<String> mm5 = new Point<String>(member5, 0, 0.05, value);

		assertTrue(((List<Point<String>>) results.get(0)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm3));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm5));

		Point<String> mm1e = new Point<String>(0, 0.01);
		Point<String> mm2e = new Point<String>(0, 0.02);
		Point<String> mm3e = new Point<String>(0, 0.03);
		Point<String> mm4e = new Point<String>(0, 0.04);
		Point<String> mm5e = new Point<String>(0, 0.05);

		assertTrue(((List<Point<String>>) results.get(0)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm3e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3e));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm5e));

		jedis.del(keyf);

		jedis.del(keyb);

		jedis.gpadd(keyb, 0, 0.01, member1b, valueb);
		jedis.gpadd(keyb, 0, 0.02, member2b, valueb);
		jedis.gpadd(keyb, 0, 0.03, member3b, valueb);
		jedis.gpadd(keyb, 0, 0.04, member4b, valueb);
		jedis.gpadd(keyb, 0, 0.05, member5b, valueb);

		Pipeline pb = jedis.pipelined();
		pb.gpnn(keyb, 0, 0.1, 0, 1);
		pb.gpnn(keyb, 0, 0.2, 0, 2);
		pb.gpnn(keyb, 0, 0.3, 0, 3);
		pb.gpnn(keyb, 0, 0.4, 0, 4);
		pb.gpnn(keyb, 0, 0.5, 0, 2);
		List<Object> resultsb = pb.syncAndReturnAll();

		assertEquals(5, resultsb.size());

		Point<byte[]> mm1b = new Point<byte[]>(member1b, 0, 0.01, valueb);
		Point<byte[]> mm2b = new Point<byte[]>(member2b, 0, 0.02, valueb);
		Point<byte[]> mm3b = new Point<byte[]>(member3b, 0, 0.03, valueb);
		Point<byte[]> mm4b = new Point<byte[]>(member4b, 0, 0.04, valueb);
		Point<byte[]> mm5b = new Point<byte[]>(member5b, 0, 0.05, valueb);

		assertTrue(((List<Point<byte[]>>) resultsb.get(0)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm3b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm5b));

		Point<byte[]> mm1eb = new Point<byte[]>(0, 0.01);
		Point<byte[]> mm2eb = new Point<byte[]>(0, 0.02);
		Point<byte[]> mm3eb = new Point<byte[]>(0, 0.03);
		Point<byte[]> mm4eb = new Point<byte[]>(0, 0.04);
		Point<byte[]> mm5eb = new Point<byte[]>(0, 0.05);

		assertTrue(((List<Point<byte[]>>) resultsb.get(0)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm3eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm5eb));

		jedis.del(keyb);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelinegadd() throws UnsupportedEncodingException {
		jedis.del(keyf);

		Pipeline p = jedis.pipelined();
		p.gpadd(keyf, 0, 0.01, member1, value);
		p.gpadd(keyf, 0, 0.02, member2, value);
		p.gpadd(keyf, 0, 0.03, member3, value);
		p.gpadd(keyf, 0, 0.04, member4, value);
		p.gpadd(keyf, 0, 0.05, member5, value);
		p.sync();

		Pipeline p2 = jedis.pipelined();
		p2.gpnn(keyf, 0, 0.1, 0, 1);
		p2.gpnn(keyf, 0, 0.2, 0, 2);
		p2.gpnn(keyf, 0, 0.3, 0, 3);
		p2.gpnn(keyf, 0, 0.4, 0, 4);
		p2.gpnn(keyf, 0, 0.5, 0, 2);
		List<Object> results = p2.syncAndReturnAll();

		assertEquals(5, results.size());

		Point<String> mm1 = new Point<String>(member1, 0, 0.01, value);
		Point<String> mm2 = new Point<String>(member2, 0, 0.02, value);
		Point<String> mm3 = new Point<String>(member3, 0, 0.03, value);
		Point<String> mm4 = new Point<String>(member4, 0, 0.04, value);
		Point<String> mm5 = new Point<String>(member5, 0, 0.05, value);

		assertTrue(((List<Point<String>>) results.get(0)).contains(mm5));
		System.out.println(((List<Point<String>>) results.get(1)).size());
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm3));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm5));

		Point<String> mm1e = new Point<String>(0, 0.01);
		Point<String> mm2e = new Point<String>(0, 0.02);
		Point<String> mm3e = new Point<String>(0, 0.03);
		Point<String> mm4e = new Point<String>(0, 0.04);
		Point<String> mm5e = new Point<String>(0, 0.05);

		assertTrue(((List<Point<String>>) results.get(0)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm3e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3e));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm5e));

		jedis.del(keyf);

		jedis.del(keyb);

		Pipeline pb = jedis.pipelined();
		pb.gpadd(keyb, 0, 0.01, member1b, valueb);
		pb.gpadd(keyb, 0, 0.02, member2b, valueb);
		pb.gpadd(keyb, 0, 0.03, member3b, valueb);
		pb.gpadd(keyb, 0, 0.04, member4b, valueb);
		pb.gpadd(keyb, 0, 0.05, member5b, valueb);
		pb.sync();

		Pipeline p2b = jedis.pipelined();
		p2b.gpnn(keyb, 0, 0.1, 0, 1);
		p2b.gpnn(keyb, 0, 0.2, 0, 2);
		p2b.gpnn(keyb, 0, 0.3, 0, 3);
		p2b.gpnn(keyb, 0, 0.4, 0, 4);
		p2b.gpnn(keyb, 0, 0.5, 0, 2);
		List<Object> resultsb = p2b.syncAndReturnAll();

		assertEquals(5, resultsb.size());

		Point<byte[]> mm1b = new Point<byte[]>(member1b, 0, 0.01, valueb);
		Point<byte[]> mm2b = new Point<byte[]>(member2b, 0, 0.02, valueb);
		Point<byte[]> mm3b = new Point<byte[]>(member3b, 0, 0.03, valueb);
		Point<byte[]> mm4b = new Point<byte[]>(member4b, 0, 0.04, valueb);
		Point<byte[]> mm5b = new Point<byte[]>(member5b, 0, 0.05, valueb);

		assertTrue(((List<Point<byte[]>>) resultsb.get(0)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm3b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm5b));

		Point<byte[]> mm1eb = new Point<byte[]>(0, 0.01);
		Point<byte[]> mm2eb = new Point<byte[]>(0, 0.02);
		Point<byte[]> mm3eb = new Point<byte[]>(0, 0.03);
		Point<byte[]> mm4eb = new Point<byte[]>(0, 0.04);
		Point<byte[]> mm5eb = new Point<byte[]>(0, 0.05);

		assertTrue(((List<Point<byte[]>>) resultsb.get(0)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm3eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm5eb));

		jedis.del(keyb);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelineggaddgggetggmget() throws UnsupportedEncodingException {
		jedis.del(keyf);
		Point<String> mm01 = new Point<String>(0, 0.01);
		Point<String> mm02 = new Point<String>(0, 0.02);
		Point<String> mm03 = new Point<String>(0, 0.03);
		Polygon<String> mmp = new Polygon<String>(mm01, mm02, mm03, mm01);
		LineString<String> mml = new LineString<String>(mm01, mm02, mm03);

		Pipeline p = jedis.pipelined();
		p.ggadd(keyf, member1, value, mm01);
		p.ggadd(keyf, member2, value, mm02);
		p.ggadd(keyf, member3, value, mm03);
		p.ggadd(keyf, member4, value, mmp);
		p.ggadd(keyf, member5, value, mml);

		p.sync();

		p = jedis.pipelined();
		p.ggget(keyf, member1);
		p.ggmget(keyf, member1);
		p.ggmget(keyf, member1, member2, member3, member4, member5);
		p.ggmget(keyf, member1, member2, member3, member4, member5, "member00");
		List<Object> results = p.syncAndReturnAll();

		assertEquals(4, results.size());

		Point<String> mm1 = new Point<String>(member1, 0, 0.01, value);
		Point<String> mm2 = new Point<String>(member2, 0, 0.02, value);
		Point<String> mm3 = new Point<String>(member3, 0, 0.03, value);
		Polygon<String> mm4 = new Polygon<String>(member4, value, mm1, mm2, mm3, mm1);
		LineString<String> mm5 = new LineString<String>(member5, value, mm1, mm2, mm3);

		System.out.println(results.get(0));

		assertThat(((Point<String>) results.get(0)), is(mm1));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(1)).get(0), is(mm1));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(2)).get(0), is(mm1));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(2)).get(1), is(mm2));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(2)).get(2), is(mm3));
		assertThat((Polygon<String>) ((List<Geometry<String>>) results.get(2)).get(3), is(mm4));
		assertThat((LineString<String>) ((List<Geometry<String>>) results.get(2)).get(4), is(mm5));

		assertThat(((List<Geometry<String>>) results.get(3)).size(), is(5));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(3)).get(0), is(mm1));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(3)).get(1), is(mm2));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(3)).get(2), is(mm3));
		assertThat((Polygon<String>) ((List<Geometry<String>>) results.get(3)).get(3), is(mm4));
		assertThat((LineString<String>) ((List<Geometry<String>>) results.get(3)).get(4), is(mm5));

		Point<String> mm1e = new Point<String>(member1, 0, 0.01, value);
		Point<String> mm2e = new Point<String>(member2, 0, 0.02, value);
		Point<String> mm3e = new Point<String>(member3, 0, 0.03, value);
		Polygon<String> mm4e = new Polygon<String>(member4, value, mm1e, mm2e, mm3e, mm1e);
		LineString<String> mm5e = new LineString<String>(member5, value, mm1e, mm2e, mm3e);

		assertThat(((Point<String>) results.get(0)), is(mm1e));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(1)).get(0), is(mm1e));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(2)).get(0), is(mm1e));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(2)).get(1), is(mm2e));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(2)).get(2), is(mm3e));
		assertThat((Polygon<String>) ((List<Geometry<String>>) results.get(2)).get(3), is(mm4e));
		assertThat((LineString<String>) ((List<Geometry<String>>) results.get(2)).get(4), is(mm5e));

		assertThat(((List<Geometry<String>>) results.get(3)).size(), is(5));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(3)).get(0), is(mm1e));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(3)).get(1), is(mm2e));
		assertThat((Point<String>) ((List<Geometry<String>>) results.get(3)).get(2), is(mm3e));
		assertThat((Polygon<String>) ((List<Geometry<String>>) results.get(3)).get(3), is(mm4e));
		assertThat((LineString<String>) ((List<Geometry<String>>) results.get(3)).get(4), is(mm5e));

		jedis.del(keyf);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelineggaddgggetggmget_byte() throws UnsupportedEncodingException {
		jedis.del(keyb);
		Point<byte[]> mm01 = new Point<byte[]>(0, 0.01);
		Point<byte[]> mm02 = new Point<byte[]>(0, 0.02);
		Point<byte[]> mm03 = new Point<byte[]>(0, 0.03);
		Polygon<byte[]> mmp = new Polygon<byte[]>(mm01, mm02, mm03, mm01);
		LineString<byte[]> mml = new LineString<byte[]>(mm01, mm02, mm03);

		Pipeline p = jedis.pipelined();
		p.ggadd(keyb, member1b, valueb, mm01);
		p.ggadd(keyb, member2b, valueb, mm02);
		p.ggadd(keyb, member3b, valueb, mm03);
		p.ggadd(keyb, member4b, valueb, mmp);
		p.ggadd(keyb, member5b, valueb, mml);

		p.sync();

		p = jedis.pipelined();
		p.ggget(keyb, member1b);
		p.ggmget(keyb, member1b);
		p.ggmget(keyb, member1b, member2b, member3b, member4b, member5b);
		p.ggmget(keyb, member1b, member2b, member3b, member4b, member5b, "member00".getBytes());
		List<Object> results = p.syncAndReturnAll();

		assertEquals(4, results.size());

		Point<byte[]> mm1 = new Point<byte[]>(member1b, 0, 0.01, valueb);
		Point<byte[]> mm2 = new Point<byte[]>(member2b, 0, 0.02, valueb);
		Point<byte[]> mm3 = new Point<byte[]>(member3b, 0, 0.03, valueb);
		Polygon<byte[]> mm4 = new Polygon<byte[]>(member4b, valueb, mm1, mm2, mm3, mm1);
		LineString<byte[]> mm5 = new LineString<byte[]>(member5b, valueb, mm1, mm2, mm3);

		System.out.println(results.get(0));

		assertThat(((Point<byte[]>) results.get(0)), is(mm1));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(1)).get(0), is(mm1));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(0), is(mm1));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(1), is(mm2));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(2), is(mm3));
		assertThat((Polygon<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(3), is(mm4));
		assertThat((LineString<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(4), is(mm5));

		assertThat(((List<Geometry<byte[]>>) results.get(3)).size(), is(5));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(0), is(mm1));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(1), is(mm2));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(2), is(mm3));
		assertThat((Polygon<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(3), is(mm4));
		assertThat((LineString<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(4), is(mm5));

		Point<byte[]> mm1e = new Point<byte[]>(member1b, 0, 0.01, valueb);
		Point<byte[]> mm2e = new Point<byte[]>(member2b, 0, 0.02, valueb);
		Point<byte[]> mm3e = new Point<byte[]>(member3b, 0, 0.03, valueb);
		Polygon<byte[]> mm4e = new Polygon<byte[]>(member4b, valueb, mm1e, mm2e, mm3e, mm1e);
		LineString<byte[]> mm5e = new LineString<byte[]>(member5b, valueb, mm1e, mm2e, mm3e);

		assertThat(((Point<byte[]>) results.get(0)), is(mm1e));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(1)).get(0), is(mm1e));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(0), is(mm1e));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(1), is(mm2e));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(2), is(mm3e));
		assertThat((Polygon<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(3), is(mm4e));
		assertThat((LineString<byte[]>) ((List<Geometry<byte[]>>) results.get(2)).get(4), is(mm5e));

		assertThat(((List<Geometry<byte[]>>) results.get(3)).size(), is(5));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(0), is(mm1e));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(1), is(mm2e));
		assertThat((Point<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(2), is(mm3e));
		assertThat((Polygon<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(3), is(mm4e));
		assertThat((LineString<byte[]>) ((List<Geometry<byte[]>>) results.get(3)).get(4), is(mm5e));

		jedis.del(keyb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelinegaddgcardgrem() throws UnsupportedEncodingException {
		jedis.del(keyf);

		Pipeline p = jedis.pipelined();
		p.gpadd(keyf, 0, 0.01, member1, value);
		p.gpadd(keyf, 0, 0.02, member2, value);
		p.gpadd(keyf, 0, 0.03, member3, value);
		p.gpadd(keyf, 0, 0.04, member4, value);
		p.gpadd(keyf, 0, 0.05, member5, value);
		p.sync();

		Pipeline p2 = jedis.pipelined();
		p2.gpcard(keyf);
		p2.gpnn(keyf, 0, 0.2, 0, 2);
		p2.gpnn(keyf, 0, 0.3, 0, 3);
		p2.gpnn(keyf, 0, 0.4, 0, 4);
		p2.gpnn(keyf, 0, 0.5, 0, 2);
		List<Object> results = p2.syncAndReturnAll();

		assertEquals(5, results.size());

		assertThat((long) results.get(0), is(5l));

		Point<String> mm1 = new Point<String>(member1, 0, 0.01, value);
		Point<String> mm2 = new Point<String>(member2, 0, 0.02, value);
		Point<String> mm3 = new Point<String>(member3, 0, 0.03, value);
		Point<String> mm4 = new Point<String>(member4, 0, 0.04, value);
		Point<String> mm5 = new Point<String>(member5, 0, 0.05, value);

		assertTrue(((List<Point<String>>) results.get(1)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm3));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm5));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm4));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm5));

		Point<String> mm1e = new Point<String>(0, 0.01);
		Point<String> mm2e = new Point<String>(0, 0.02);
		Point<String> mm3e = new Point<String>(0, 0.03);
		Point<String> mm4e = new Point<String>(0, 0.04);
		Point<String> mm5e = new Point<String>(0, 0.05);

		assertTrue(((List<Point<String>>) results.get(1)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(1)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(2)).contains(mm3e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm5e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3e));
		assertTrue(((List<Point<String>>) results.get(3)).contains(mm3e));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm4e));
		assertTrue(((List<Point<String>>) results.get(4)).contains(mm5e));

		p2 = jedis.pipelined();
		p2.gprem(keyf, member1);
		p2.gpcard(keyf);
		results = p2.syncAndReturnAll();

		assertEquals(2, results.size());
		assertThat((long) results.get(0), is(1l));
		assertThat((long) results.get(1), is(4l));

		jedis.del(keyf);

		jedis.del(keyb);

		Pipeline pb = jedis.pipelined();
		pb.gpadd(keyb, 0, 0.01, member1b, valueb);
		pb.gpadd(keyb, 0, 0.02, member2b, valueb);
		pb.gpadd(keyb, 0, 0.03, member3b, valueb);
		pb.gpadd(keyb, 0, 0.04, member4b, valueb);
		pb.gpadd(keyb, 0, 0.05, member5b, valueb);
		pb.sync();

		Pipeline p2b = jedis.pipelined();
		p2b.gpcard(keyf);
		p2b.gpnn(keyb, 0, 0.2, 0, 2);
		p2b.gpnn(keyb, 0, 0.3, 0, 3);
		p2b.gpnn(keyb, 0, 0.4, 0, 4);
		p2b.gpnn(keyb, 0, 0.5, 0, 2);
		List<Object> resultsb = p2b.syncAndReturnAll();

		assertEquals(5, resultsb.size());
		assertThat((long) resultsb.get(0), is(5l));

		Point<byte[]> mm1b = new Point<byte[]>(member1b, 0, 0.01, valueb);
		Point<byte[]> mm2b = new Point<byte[]>(member2b, 0, 0.02, valueb);
		Point<byte[]> mm3b = new Point<byte[]>(member3b, 0, 0.03, valueb);
		Point<byte[]> mm4b = new Point<byte[]>(member4b, 0, 0.04, valueb);
		Point<byte[]> mm5b = new Point<byte[]>(member5b, 0, 0.05, valueb);

		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm3b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm5b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm4b));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm5b));

		Point<byte[]> mm1eb = new Point<byte[]>(0, 0.01);
		Point<byte[]> mm2eb = new Point<byte[]>(0, 0.02);
		Point<byte[]> mm3eb = new Point<byte[]>(0, 0.03);
		Point<byte[]> mm4eb = new Point<byte[]>(0, 0.04);
		Point<byte[]> mm5eb = new Point<byte[]>(0, 0.05);

		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(1)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(2)).contains(mm3eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm5eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(3)).contains(mm3eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm4eb));
		assertTrue(((List<Point<byte[]>>) resultsb.get(4)).contains(mm5eb));

		p2b = jedis.pipelined();
		p2b.gprem(keyb, member1b);
		p2b.gpcard(keyb);
		results = p2b.syncAndReturnAll();

		assertEquals(2, results.size());
		assertThat((long) results.get(0), is(1l));
		assertThat((long) results.get(1), is(4l));

		jedis.del(keyb);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void pipelinegadd2() throws UnsupportedEncodingException {
		jedis.del(keyf);

		Pipeline p = jedis.pipelined();
		p.gpadd(keyf, 0.1, 0.1, 1, M, member6, value);
		p.gpadd(keyf, 0.1, 0.2, 1, M, member7, value);
		p.gpadd(keyf, 0.1, 0.3, 1, M, member8, value);
		p.gpadd(keyf, 0.1, 0.4, 1, M, member9, value);
		p.gpadd(keyf, 0.1, 0.5, 1, M, member10, value);
		p.sync();

		p = jedis.pipelined();
		p.gpcircle(keyf, 0.1, 0.1, 1, M);
		p.gpcircle(keyf, 0.1, 0.2, 1, M);
		p.gpcircle(keyf, 0.1, 0.3, 1, M);
		p.gpcircle(keyf, 0.1, 0.4, 1, M);
		p.gpcircle(keyf, 0.1, 0.5, 1, M);
		p.gpcircle(keyf, 0.1, 0.5, 1, M, RELATION.CONTAINS, ORDERBY.DISTANCE_ASC);
		List<Object> results = p.syncAndReturnAll();

		assertEquals(6, results.size());
		System.out.println(((List<Circle<String>>) results.get(0)).get(0));
		assertTrue(((List<Circle<String>>) results.get(0)).get(0).getMember().equals(member6));
		assertTrue(((List<Circle<String>>) results.get(1)).get(0).getMember().equals(member7));
		assertTrue(((List<Circle<String>>) results.get(2)).get(0).getMember().equals(member8));
		assertTrue(((List<Circle<String>>) results.get(3)).get(0).getMember().equals(member9));
		assertTrue(((List<Circle<String>>) results.get(4)).get(0).getMember().equals(member10));
		assertTrue(((List<Circle<String>>) results.get(5)).get(0).getMember().equals(member10));

		assertTrue(((List<Circle<String>>) results.get(4)).get(0).equals(new Circle<String>(member5, 0.1, 0.5, 1, M, value)));

		Pipeline pb = jedis.pipelined();
		p.gpadd(keyb, 0.1, 0.1, 1, M, member6b, valueb);
		p.gpadd(keyb, 0.1, 0.2, 1, M, member7b, valueb);
		p.gpadd(keyb, 0.1, 0.3, 1, M, member8b, valueb);
		p.gpadd(keyb, 0.1, 0.4, 1, M, member9b, valueb);
		p.gpadd(keyb, 0.1, 0.5, 1, M, member10b, valueb);
		pb.sync();

		pb = jedis.pipelined();
		pb.gpcircle(keyb, 0.1, 0.1, 1, M);
		pb.gpcircle(keyb, 0.1, 0.2, 1, M);
		pb.gpcircle(keyb, 0.1, 0.3, 1, M);
		pb.gpcircle(keyb, 0.1, 0.4, 1, M);
		pb.gpcircle(keyb, 0.1, 0.5, 1, M);
		pb.gpcircle(keyb, 0.1, 0.5, 1, M, RELATION.CONTAINS, ORDERBY.DISTANCE_ASC);

		List<Object> resultsb = pb.syncAndReturnAll();

		assertEquals(6, results.size());
		System.out.println(((List<Circle<byte[]>>) resultsb.get(0)).get(0));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(0)).get(0).getMember()).equals(member6));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(1)).get(0).getMember()).equals(member7));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(2)).get(0).getMember()).equals(member8));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(3)).get(0).getMember()).equals(member9));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(4)).get(0).getMember()).equals(member10));
		assertTrue(new String(((List<Circle<byte[]>>) resultsb.get(5)).get(0).getMember()).equals(member10));

		assertTrue(((List<Circle<byte[]>>) resultsb.get(4)).get(0).equals(new Circle<byte[]>(member5b, 0.1, 0.5, 1, M, valueb)));
		jedis.del(keyb);

		jedis.del(keyb);

	}

	@Test
	public void pipelineResponse() {
		jedis.set("string", "foo");
		jedis.lpush("list", "foo");
		jedis.hset("hash", "foo", "bar");
		jedis.zadd("zset", 1, "foo");
		jedis.sadd("set", "foo");

		Pipeline p = jedis.pipelined();
		Response<String> string = p.get("string");
		Response<String> list = p.lpop("list");
		Response<String> hash = p.hget("hash", "foo");
		Response<Set<String>> zset = p.zrange("zset", 0, -1);
		Response<String> set = p.spop("set");
		Response<Boolean> blist = p.exists("list");
		Response<Double> zincrby = p.zincrby("zset", 1, "foo");
		Response<Long> zcard = p.zcard("zset");
		p.lpush("list", "bar");
		Response<List<String>> lrange = p.lrange("list", 0, -1);
		Response<Map<String, String>> hgetAll = p.hgetAll("hash");
		p.sadd("set", "foo");
		Response<Set<String>> smembers = p.smembers("set");
		Response<Set<Tuple>> zrangeWithScores = p.zrangeWithScores("zset", 0, -1);
		p.sync();

		assertEquals("foo", string.get());
		assertEquals("foo", list.get());
		assertEquals("bar", hash.get());
		assertEquals("foo", zset.get().iterator().next());
		assertEquals("foo", set.get());
		assertEquals(false, blist.get());
		assertEquals(Double.valueOf(2), zincrby.get());
		assertEquals(Long.valueOf(1), zcard.get());
		assertEquals(1, lrange.get().size());
		assertNotNull(hgetAll.get().get("foo"));
		assertEquals(1, smembers.get().size());
		assertEquals(1, zrangeWithScores.get().size());
	}

	@Test
	public void pipelineResponseWithData() {
		jedis.zadd("zset", 1, "foo");

		Pipeline p = jedis.pipelined();
		Response<Double> score = p.zscore("zset", "foo");
		p.sync();

		assertNotNull(score.get());
	}

	@Test
	public void pipelineBinarySafeHashCommands() {
		jedis.hset("key".getBytes(), "f1".getBytes(), "v111".getBytes());
		jedis.hset("key".getBytes(), "f22".getBytes(), "v2222".getBytes());

		Pipeline p = jedis.pipelined();
		Response<Map<byte[], byte[]>> fmap = p.hgetAll("key".getBytes());
		Response<Set<byte[]>> fkeys = p.hkeys("key".getBytes());
		Response<List<byte[]>> fordered = p.hmget("key".getBytes(), "f22".getBytes(), "f1".getBytes());
		Response<List<byte[]>> fvals = p.hvals("key".getBytes());
		p.sync();

		assertNotNull(fmap.get());
		// we have to do these strange contortions because byte[] is not a very
		// good key
		// for a java Map. It only works with equality (you need the exact key
		// object to retrieve
		// the value) I recommend we switch to using ByteBuffer or something
		// similar:
		// http://stackoverflow.com/questions/1058149/using-a-byte-array-as-hashmap-key-java
		Map<byte[], byte[]> map = fmap.get();
		Set<byte[]> mapKeys = map.keySet();
		Iterator<byte[]> iterMap = mapKeys.iterator();
		byte[] firstMapKey = iterMap.next();
		byte[] secondMapKey = iterMap.next();
		assertFalse(iterMap.hasNext());
		verifyHasBothValues(firstMapKey, secondMapKey, "f1".getBytes(), "f22".getBytes());
		byte[] firstMapValue = map.get(firstMapKey);
		byte[] secondMapValue = map.get(secondMapKey);
		verifyHasBothValues(firstMapValue, secondMapValue, "v111".getBytes(), "v2222".getBytes());

		assertNotNull(fkeys.get());
		Iterator<byte[]> iter = fkeys.get().iterator();
		byte[] firstKey = iter.next();
		byte[] secondKey = iter.next();
		assertFalse(iter.hasNext());
		verifyHasBothValues(firstKey, secondKey, "f1".getBytes(), "f22".getBytes());

		assertNotNull(fordered.get());
		assertArrayEquals("v2222".getBytes(), fordered.get().get(0));
		assertArrayEquals("v111".getBytes(), fordered.get().get(1));

		assertNotNull(fvals.get());
		assertEquals(2, fvals.get().size());
		byte[] firstValue = fvals.get().get(0);
		byte[] secondValue = fvals.get().get(1);
		verifyHasBothValues(firstValue, secondValue, "v111".getBytes(), "v2222".getBytes());
	}

	private void verifyHasBothValues(byte[] firstKey, byte[] secondKey, byte[] value1, byte[] value2) {
		assertFalse(Arrays.equals(firstKey, secondKey));
		assertTrue(Arrays.equals(firstKey, value1) || Arrays.equals(firstKey, value2));
		assertTrue(Arrays.equals(secondKey, value1) || Arrays.equals(secondKey, value2));
	}

	@Test
	public void pipelineSelect() {
		Pipeline p = jedis.pipelined();
		p.select(1);
		p.sync();
	}

	@Test
	public void pipelineResponseWithoutData() {
		jedis.zadd("zset", 1, "foo");

		Pipeline p = jedis.pipelined();
		Response<Double> score = p.zscore("zset", "bar");
		p.sync();

		assertNull(score.get());
	}

	@Test(expected = JedisDataException.class)
	public void pipelineResponseWithinPipeline() {
		jedis.set("string", "foo");

		Pipeline p = jedis.pipelined();
		Response<String> string = p.get("string");
		string.get();
		p.sync();
	}

	@Test
	public void pipelineWithPubSub() {
		Pipeline pipelined = jedis.pipelined();
		Response<Long> p1 = pipelined.publish("foo", "bar");
		Response<Long> p2 = pipelined.publish("foo".getBytes(), "bar".getBytes());
		pipelined.sync();
		assertEquals(0, p1.get().longValue());
		assertEquals(0, p2.get().longValue());
	}

	@Test
	public void canRetrieveUnsetKey() {
		Pipeline p = jedis.pipelined();
		Response<String> shouldNotExist = p.get(UUID.randomUUID().toString());
		p.sync();
		assertNull(shouldNotExist.get());
	}

	@Test
	public void piplineWithError() {
		Pipeline p = jedis.pipelined();
		p.set("foo", "bar");
		Response<Set<String>> error = p.smembers("foo");
		Response<String> r = p.get("foo");
		p.sync();
		try {
			error.get();
			fail();
		} catch (JedisDataException e) {
			// that is fine we should be here
		}
		assertEquals(r.get(), "bar");
	}

	@Test
	public void multi() {
		Pipeline p = jedis.pipelined();
		p.multi();
		Response<Long> r1 = p.hincrBy("a", "f1", -1);
		Response<Long> r2 = p.hincrBy("a", "f1", -2);
		Response<List<Object>> r3 = p.exec();
		List<Object> result = p.syncAndReturnAll();

		assertEquals(new Long(-1), r1.get());
		assertEquals(new Long(-3), r2.get());

		assertEquals(4, result.size());

		assertEquals("OK", result.get(0));
		assertEquals("QUEUED", result.get(1));
		assertEquals("QUEUED", result.get(2));

		// 4th result is a list with the results from the multi
		@SuppressWarnings("unchecked")
		List<Object> multiResult = (List<Object>) result.get(3);
		assertEquals(new Long(-1), multiResult.get(0));
		assertEquals(new Long(-3), multiResult.get(1));

		assertEquals(new Long(-1), r3.get().get(0));
		assertEquals(new Long(-3), r3.get().get(1));

	}

	@Test
	public void multiWithSync() {
		jedis.set("foo", "314");
		jedis.set("bar", "foo");
		jedis.set("hello", "world");
		Pipeline p = jedis.pipelined();
		Response<String> r1 = p.get("bar");
		p.multi();
		Response<String> r2 = p.get("foo");
		p.exec();
		Response<String> r3 = p.get("hello");
		p.sync();

		// before multi
		assertEquals("foo", r1.get());
		// It should be readable whether exec's response was built or not
		assertEquals("314", r2.get());
		// after multi
		assertEquals("world", r3.get());
	}

	@Test
	public void testDiscardInPipeline() {
		Pipeline pipeline = jedis.pipelined();
		pipeline.multi();
		pipeline.set("foo", "bar");
		Response<String> discard = pipeline.discard();
		Response<String> get = pipeline.get("foo");
		pipeline.sync();
		discard.get();
		get.get();
	}

	@Test
	public void testEval() {
		String script = "return 'success!'";

		Pipeline p = jedis.pipelined();
		Response<String> result = p.eval(script);
		p.sync();

		assertEquals("success!", result.get());
	}

	@Test
	public void testEvalKeyAndArg() {
		String key = "test";
		String arg = "3";
		String script = "redis.call('INCRBY', KEYS[1], ARGV[1]) redis.call('INCRBY', KEYS[1], ARGV[1])";

		Pipeline p = jedis.pipelined();
		p.set(key, "0");
		Response<String> result0 = p.eval(script, Arrays.asList(key), Arrays.asList(arg));
		p.incr(key);
		Response<String> result1 = p.eval(script, Arrays.asList(key), Arrays.asList(arg));
		Response<String> result2 = p.get(key);
		p.sync();

		assertNull(result0.get());
		assertNull(result1.get());
		assertEquals("13", result2.get());
	}

	@Test
	public void testEvalsha() {
		String script = "return 'success!'";
		String sha1 = jedis.scriptLoad(script);

		assertTrue(jedis.scriptExists(sha1));

		Pipeline p = jedis.pipelined();
		Response<String> result = p.evalsha(sha1);
		p.sync();

		assertEquals("success!", result.get());
	}

	@Test
	public void testEvalshaKeyAndArg() {
		String key = "test";
		String arg = "3";
		String script = "redis.call('INCRBY', KEYS[1], ARGV[1]) redis.call('INCRBY', KEYS[1], ARGV[1])";
		String sha1 = jedis.scriptLoad(script);

		assertTrue(jedis.scriptExists(sha1));

		Pipeline p = jedis.pipelined();
		p.set(key, "0");
		Response<String> result0 = p.evalsha(sha1, Arrays.asList(key), Arrays.asList(arg));
		p.incr(key);
		Response<String> result1 = p.evalsha(sha1, Arrays.asList(key), Arrays.asList(arg));
		Response<String> result2 = p.get(key);
		p.sync();

		assertNull(result0.get());
		assertNull(result1.get());
		assertEquals("13", result2.get());
	}
}