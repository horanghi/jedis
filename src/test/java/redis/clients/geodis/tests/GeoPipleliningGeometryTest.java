package redis.clients.geodis.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol.Type;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;
import redis.clients.util.GEOMETRY;

public class GeoPipleliningGeometryTest {

	static JedisPool geodisPool;
	Jedis geodis;

	String key = "helloKeyGM";
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

	String kkb = "keykey";
	
	
	Long OKl = 1l;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// gcontainer
		geodisPool = new JedisPool(new GenericObjectPoolConfig(), "172.19.114.203", 19006, 2000, "1234");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		geodisPool.destroy();
	}

	@Before
	public void setUp() throws Exception {
		geodis = geodisPool.getResource();
		geodis.gmsetBoundary(key, -100, -100, 100, 100);
	}

	@After
	public void tearDown() throws Exception {
		geodis.del(key);
		geodis.del(key + "1");
		geodis.del(kkb);
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

	@SuppressWarnings("unchecked")
	@Test
	public void testPipelinegetsetBoundary() {
		Pipeline p = geodis.pipelined();
		p.gmsetBoundary(key + "1", -100, -100, 100, 100);
		p.gmrebuildBoundary(key + "1", -100, -100, 50, 50);
		p.gmrebuildBoundary((key + "1").getBytes(), -100, -100, 10, 10);
		p.del(key + "1");
		p.gmsetBoundary((key + "1").getBytes(), -100, -100, 100, 100);
		p.gmgetBoundary(key + "1");
		p.gmgetBoundary((key + "1").getBytes());

		List<Object> results = p.syncAndReturnAll();

		assertThat(((String) results.get(0)), is("OK"));
		assertThat(((Long) results.get(1)), is(0l));
		assertThat(((Long) results.get(2)), is(0l));
		assertThat(((Long) results.get(3)), is(OKl));
		assertThat(new String((byte[])results.get(4)), is("OK"));

		assertThat(((List<Point<String>>) results.get(5)).get(0), is(new Point<String>(-100, -100)));
		assertThat(((List<Point<String>>) results.get(5)).get(1), is(new Point<String>(100, 100)));

		assertThat(((List<Point<byte[]>>) results.get(6)).get(0), is(new Point<byte[]>(-100, -100)));
		assertThat(((List<Point<byte[]>>) results.get(6)).get(1), is(new Point<byte[]>(100, 100)));

	}

	@Test
	public void test1Equals() {
		Point<String> asop = new Point<String>("member1", 0, 0, "values");
		Point<String> asop2 = new Point<String>("member2", 0, 0, "values");
		Point<String> asop3 = new Point<String>(0, 0);
		Point<String> asop4 = new Point<String>("mm", 0, 0, "values");
		Point<String> asop11 = new Point<String>("member1", 0, 0, "values");

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
	public void testPipelinegmaddngmrange() {

		String[] members = { member1, member2 };
		String[] values = { value, value };
		Point<String>[] opoints = new Point[] { new Point(member1, 0, 0, value), new Point(member2, 0, 0, value) };

		Pipeline p = geodis.pipelined();
		
		p.gmadd(key, member1, value, new Point<String>(0, 0));
		p.gmadd(key, member2, value, new Point<String>(0, 0));
		p.gmrange(key, 0, -1);
		p.gmrevrange(key, 0, 1);

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((List<Geometry<String>>) result.get(2)).size(), is(2));
		assertThat(((List<Geometry<String>>) result.get(3)).size(), is(2));

		List<Geometry<String>> Points = (List<Geometry<String>>) result.get(2);
		int idx = 0;
		for (Geometry<String> geo : Points) {
			Point<String> point = (Point<String>) geo;
			assertTrue(point.getMember().equals(members[idx]));
			assertTrue(point.getValue().equals(values[idx]));
			assertTrue(point.equals(opoints[idx++]));
			System.out.println(point.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testPipelinegmaddngmrangeb() {

		Point<byte[]>[] opoints = new Point[] { new Point(member1b, 0, 0, valueb), new Point(member2b, 0, 0, valueb) };
		byte[][] membersb = { member1b, member2b };
		byte[][] valuesb = { valueb, valueb };

		Pipeline p = geodis.pipelined();
		p.gmadd(keyb, member1b, valueb, new Point<byte[]>(0, 0));
		p.gmadd(keyb, member2b, valueb, new Point<byte[]>(0, 0));
		p.gmrange(keyb, 0, -1);
		p.gmrevrange(keyb, 0, 1);

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((List<Geometry<byte[]>>) result.get(2)).size(), is(2));
		assertThat(((List<Geometry<byte[]>>) result.get(3)).size(), is(2));

		List<Geometry<byte[]>> Points = (List<Geometry<byte[]>>) result.get(2);
		int idx = 0;
		for (Geometry<byte[]> geo : Points) {
			Point<byte[]> point = (Point<byte[]>) geo;
			assertTrue(Arrays.equals(point.getMember(), membersb[idx]));
			assertTrue(Arrays.equals(point.getValue(), valuesb[idx]));
			assertTrue(point.equals(opoints[idx++]));
			System.out.println(point.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPipelinegmaddngmrange02() {
		Pipeline p = geodis.pipelined();
		p.gmadd(key, 0, 0, member1, value);
		p.gmadd(key, 0, 1, member2, value);
		p.gmrange(key, 0, 0);
		p.gmrange(key, 0, 1);

		List<Object> result = p.syncAndReturnAll();

		assertThat((Long) result.get(0), is(OKl));
		assertThat((Long) result.get(1), is(OKl));
		assertThat(((List<Geometry<String>>) result.get(2)).size(), is(1));
		List<Geometry<String>> circles = ((List<Geometry<String>>) result.get(2));
		for (Geometry<String> circle : circles) {
			System.out.println(circle.toString());
		}
		List<Geometry<String>> circles2 = ((List<Geometry<String>>) result.get(3));
		assertThat(circles2.size(), is(2));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPipelinegmaddngmrange02b() {
		Pipeline p = geodis.pipelined();
		p.gmadd(keyb, 0, 0, member1b, valueb);
		p.gmadd(keyb, 0, 1, member2b, valueb);
		p.gmrange(keyb, 0, 0);
		p.gmrange(keyb, 0, 1);

		List<Object> result = p.syncAndReturnAll();

		assertThat((Long) result.get(0), is(OKl));
		assertThat((Long) result.get(1), is(OKl));
		assertThat(((List<Geometry<byte[]>>) result.get(2)).size(), is(1));
		List<Geometry<byte[]>> circles = ((List<Geometry<byte[]>>) result.get(2));
		for (Geometry<byte[]> circle : circles) {
			System.out.println(circle.toString());
		}
		List<Geometry<byte[]>> circles2 = ((List<Geometry<byte[]>>) result.get(3));
		assertThat(circles2.size(), is(2));
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Test
	public void testPipelinegmaddngmget() {

		Pipeline p = geodis.pipelined();

		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		Polygon<String> polygon2 = new Polygon<String>(new Point<String>(2, 2), new Point<String>(2, -2), new Point<String>(-2, -2),
				new Point<String>(-2, 2), new Point<String>(2, 2));
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(0, 0), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		LineString<String> linestr2 = new LineString<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(0, 0), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1));

		Point<String> point1 = new Point(0, 0);
		Point<String> point2 = new Point(1, 1);

		p.gmadd(key, 0, 0, member1, value);
		p.gmadd(key, member2, value, point2);
		p.gmadd(key, member3, value, polygon);
		p.gmadd(key, member4, value, linestr);
		p.gmget(key, member1);
		p.gmget(key, member2);
		p.gmget(key, member3);
		p.gmget(key, member4);
		p.gmmget(key, member1, member2, member4);

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((Long) result.get(2)), is(OKl));
		assertThat(((Long) result.get(3)), is(OKl));

		assertThat((Point<String>) result.get(4), is(point1));
		assertThat(((Point<String>) result.get(4)).getType(), is(Type.POINT));
		assertThat(((Point<String>) result.get(5)), is(point2));
		assertThat(((Point<String>) result.get(5)).getType(), is(Type.POINT));
		assertThat(((Polygon<String>) result.get(6)), is(polygon));
		assertThat(((Polygon<String>) result.get(6)).getType(), is(Type.POLYGON));
		assertThat((LineString<String>) result.get(7), is(linestr));
		assertThat(((LineString<String>) result.get(7)).getType(), is(Type.LINESTRING));

		assertThat(((List<Geometry<String>>) result.get(8)).size(), is(3));
		assertTrue(((List<Geometry<String>>) result.get(8)).contains(point1));
		assertTrue(((List<Geometry<String>>) result.get(8)).contains(point2));
		assertTrue(((List<Geometry<String>>) result.get(8)).contains(linestr));

	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Test
	public void testPipelinegmaddngmgetb() {

		Pipeline p = geodis.pipelined();

		Polygon<byte[]> polygon = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		Polygon<byte[]> polygon2 = new Polygon<byte[]>(new Point<byte[]>(2, 2), new Point<byte[]>(2, -2), new Point<byte[]>(-2, -2),
				new Point<byte[]>(-2, 2), new Point<byte[]>(2, 2));
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<byte[]> linestr = new LineString<byte[]>(new Point<byte[]>(0, 0), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1));
		LineString<byte[]> linestr2 = new LineString<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1));
		LineString<byte[]> linestrb = new LineString<byte[]>(new Point<byte[]>(0, 0), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1));

		Point<byte[]> point1 = new Point(0, 0);
		Point<byte[]> point2 = new Point(1, 1);

		p.gmadd(keyb, 0, 0, member1b, valueb);
		p.gmadd(keyb, member2b, valueb, point2);
		p.gmadd(keyb, member3b, valueb, polygon);
		p.gmadd(keyb, member4b, valueb, linestr);
		p.gmget(keyb, member1b);
		p.gmget(keyb, member2b);
		p.gmget(keyb, member3b);
		p.gmget(keyb, member4b);
		p.gmmget(keyb, member1b, member2b, member4b);

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((Long) result.get(2)), is(OKl));
		assertThat(((Long) result.get(3)), is(OKl));

		assertThat((Point<byte[]>) result.get(4), is(point1));
		assertThat(((Point<byte[]>) result.get(4)).getType(), is(Type.POINT));
		assertThat(((Point<byte[]>) result.get(5)), is(point2));
		assertThat(((Point<byte[]>) result.get(5)).getType(), is(Type.POINT));
		assertThat(((Polygon<byte[]>) result.get(6)), is(polygon));
		assertThat(((Polygon<byte[]>) result.get(6)).getType(), is(Type.POLYGON));
		assertThat((LineString<byte[]>) result.get(7), is(linestr));
		assertThat(((LineString<byte[]>) result.get(7)).getType(), is(Type.LINESTRING));

		assertThat(((List<Geometry<String>>) result.get(8)).size(), is(3));
		assertTrue(((List<Geometry<String>>) result.get(8)).contains(point1));
		assertTrue(((List<Geometry<String>>) result.get(8)).contains(point2));
		assertTrue(((List<Geometry<String>>) result.get(8)).contains(linestr));

	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Test
	public void testPipelinegmaddngmrelation() {

		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		Polygon<String> polygon2 = new Polygon<String>(new Point<String>(2, 2), new Point<String>(2, -2), new Point<String>(-2, -2),
				new Point<String>(-2, 2), new Point<String>(2, 2));
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(0, 0), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		LineString<String> linestr2 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1), new Point<String>(-1, 4));
		LineString<String> linestr3 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1));

		Point<String> point1 = new Point(0, 0);
		Point<String> point2 = new Point(1, 1);

		Pipeline p = geodis.pipelined();

		p.gmadd(key, 0, 0, member1, value);
		p.gmadd(key, member2, value, point2);
		p.gmadd(key, member3, value, polygon);
		p.gmadd(key, member4, value, linestr);
		p.gmrelation(key, polygon2);
		p.gmrelation(key, linestr2);
		p.gmrelation(key, linestr3);
		p.gmrelation(key, linestr);
		p.gmrelation(key, point1);

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((Long) result.get(2)), is(OKl));
		assertThat(((Long) result.get(3)), is(OKl));

		List<Geometry<String>> _result = (List<Geometry<String>>) result.get(4);

		assertThat(_result.size(), is(4));
		assertTrue(_result.contains(point1));
		assertTrue(_result.contains(point2));
		assertTrue(_result.contains(polygon));
		assertTrue(_result.contains(linestr));

		assertFalse(_result.contains(polygon2));

		List<Geometry<String>> result2 = (List<Geometry<String>>) result.get(5);
		assertThat(result2.size(), is(3));
		assertTrue(result2.contains(point1));
		assertTrue(result2.contains(point2));
		assertFalse(result2.contains(polygon));
		assertTrue(result2.contains(linestr));

		result2 = (List<Geometry<String>>) result.get(6);
		assertThat(result2.size(), is(3));
		assertTrue(result2.contains(point1));
		assertTrue(result2.contains(point2));
		assertFalse(result2.contains(polygon));
		assertTrue(result2.contains(linestr));

		result2 = (List<Geometry<String>>) result.get(7);
		assertThat(result2.size(), is(1));
		assertFalse(result2.contains(point1));
		assertFalse(result2.contains(point2));
		assertFalse(result2.contains(polygon));
		assertTrue(result2.contains(linestr));

		result2 = (List<Geometry<String>>) result.get(8);
		assertThat(result2.size(), is(1));
		assertTrue(result2.contains(point1));
		assertFalse(result2.contains(point2));
		assertFalse(result2.contains(polygon));
		assertFalse(result2.contains(linestr));

	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Test
	public void testPipelinegmaddngmrelationb() {

		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		Polygon<String> polygon2 = new Polygon<String>(new Point<String>(2, 2), new Point<String>(2, -2), new Point<String>(-2, -2),
				new Point<String>(-2, 2), new Point<String>(2, 2));
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(0, 0), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		LineString<String> linestr2 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1), new Point<String>(-1, 4));
		LineString<String> linestr3 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1));

		Point<String> point1 = new Point(0, 0);
		Point<String> point2 = new Point(1, 1);

		Pipeline p = geodis.pipelined();

		p.gmadd(keyb, 0, 0, member1b, valueb);
		p.gmadd(keyb, member2b, valueb, point2);
		p.gmadd(keyb, member3b, valueb, polygon);
		p.gmadd(keyb, member4b, valueb, linestr);
		p.gmrelation(keyb, polygon2);
		p.gmrelation(keyb, linestr2);
		p.gmrelation(keyb, linestr3);
		p.gmrelation(keyb, linestr);
		p.gmrelation(keyb, point1);

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((Long) result.get(2)), is(OKl));
		assertThat(((Long) result.get(3)), is(OKl));

		List<Geometry<byte[]>> _result = (List<Geometry<byte[]>>) result.get(4);

		assertThat(_result.size(), is(4));
		assertTrue(_result.contains(point1));
		assertTrue(_result.contains(point2));
		assertTrue(_result.contains(polygon));
		assertTrue(_result.contains(linestr));

		assertFalse(_result.contains(polygon2));

		List<Geometry<byte[]>> result2 = (List<Geometry<byte[]>>) result.get(5);
		assertThat(result2.size(), is(3));
		assertTrue(result2.contains(point1));
		assertTrue(result2.contains(point2));
		assertFalse(result2.contains(polygon));
		assertTrue(result2.contains(linestr));

		result2 = (List<Geometry<byte[]>>) result.get(6);
		assertThat(result2.size(), is(3));
		assertTrue(result2.contains(point1));
		assertTrue(result2.contains(point2));
		assertFalse(result2.contains(polygon));
		assertTrue(result2.contains(linestr));

		result2 = (List<Geometry<byte[]>>) result.get(7);
		assertThat(result2.size(), is(1));
		assertFalse(result2.contains(point1));
		assertFalse(result2.contains(point2));
		assertFalse(result2.contains(polygon));
		assertTrue(result2.contains(linestr));

		result2 = (List<Geometry<byte[]>>) result.get(8);
		assertThat(result2.size(), is(1));
		assertTrue(result2.contains(point1));
		assertFalse(result2.contains(point2));
		assertFalse(result2.contains(polygon));
		assertFalse(result2.contains(linestr));

	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Test
	public void testPipelinegmaddngmrelationby() {

		
		geodis.gmsetBoundary(kkb, -200, -200, 200, 200);

		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		Polygon<String> polygon2 = new Polygon<String>(new Point<String>(2, 2), new Point<String>(2, -2), new Point<String>(-2, -2),
				new Point<String>(-2, 2), new Point<String>(2, 2));
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(0, -1), new Point<String>(0, 0), new Point<String>(0.5, 0.5),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1));
		LineString<String> linestr2 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1), new Point<String>(-1, 4));
		LineString<String> linestr3 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1));

		Point<String> point1 = new Point(0, 0);
		Point<String> point2 = new Point(0.5, 0.5);

		Pipeline p = geodis.pipelined();

		p.gmadd(key, 0, 0, member1, value);
		p.gmadd(key, 0.5, 0.5, member2, value);
		p.gmadd(kkb, member3, value, polygon);
		p.gmadd(kkb, member4, value, linestr);
		p.gmadd(kkb, member5, value, point1);

		p.gmrelationByMember(key, kkb, member3);
		p.gmrelationByMember(key, kkb, member4);
		p.gmrelationByMember(key, kkb, member5);
		p.gmcard(key);
		p.gmcard(kkb);
		p.gmrem(key, member1);
		p.gmrem(key, member2);
		p.gmcard(key);

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((Long) result.get(2)), is(OKl));
		assertThat(((Long) result.get(3)), is(OKl));
		assertThat(((Long) result.get(4)), is(OKl));

		List<Geometry<String>> result1 = (List<Geometry<String>>) result.get(5);
		assertThat(result1.size(), is(2));
		assertTrue(result1.contains(point1));
		assertTrue(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<String> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<String> geop = (Point<String>) geo;
				assertThat(geop.getType(), is(Type.POINT));
			} else {
				throw new JedisException("");
			}
		}

		result1 = (List<Geometry<String>>) result.get(6);
		assertThat(result1.size(), is(2));
		assertTrue(result1.contains(point1));
		assertTrue(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<String> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<String> geop = (Point<String>) geo;
				assertThat(geop.getType(), is(Type.POINT));
			} else {
				throw new JedisException("");
			}
		}

		result1 = (List<Geometry<String>>) result.get(7);
		assertThat(result1.size(), is(1));
		assertTrue(result1.contains(point1));
		assertFalse(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<String> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<String> geop = (Point<String>) geo;
				assertThat(geop.getType(), is(Type.POINT));
			} else {
				throw new JedisException("");
			}
		}

		assertThat((Long) result.get(8), is(2l));

		assertThat((Long) result.get(9), is(3l));

		assertThat((Long) result.get(10), is(1l));
		assertThat((Long) result.get(11), is(1l));
		assertThat((Long) result.get(12), is(0l));

	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Test
	public void testPipelinegmaddngmrelationbyb() {

		String kkb = "keykey";
		byte[] kkbb = "keykey".getBytes();
		geodis.del(kkb);
		geodis.gmsetBoundary(kkb, -200, -200, 200, 200);

		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		Polygon<String> polygon2 = new Polygon<String>(new Point<String>(2, 2), new Point<String>(2, -2), new Point<String>(-2, -2),
				new Point<String>(-2, 2), new Point<String>(2, 2));
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(0, -1), new Point<String>(0, 0), new Point<String>(0.5, 0.5),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1));
		LineString<String> linestr2 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1), new Point<String>(-1, 4));
		LineString<String> linestr3 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1));

		Point<String> point1 = new Point(0, 0);
		Point<String> point2 = new Point(0.5, 0.5);

		Pipeline p = geodis.pipelined();

		p.gmadd(keyb, 0, 0, member1b, valueb);
		p.gmadd(keyb, 0.5, 0.5, member2b, valueb);
		p.gmadd(kkbb, member3b, valueb, polygon);
		p.gmadd(kkbb, member4b, valueb, linestr);
		p.gmadd(kkbb, member5b, valueb, point1);

		p.gmrelationByMember(keyb, kkbb, member3b);
		p.gmrelationByMember(keyb, kkbb, member4b);
		p.gmrelationByMember(keyb, kkbb, member5b);
		p.gmcard(keyb);
		p.gmcard(kkbb);
		p.gmrem(keyb, member1b);
		p.gmrem(keyb, member2b);
		p.gmcard(keyb);

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((Long) result.get(2)), is(OKl));
		assertThat(((Long) result.get(3)), is(OKl));
		assertThat(((Long) result.get(4)), is(OKl));

		List<Geometry<byte[]>> result1 = (List<Geometry<byte[]>>) result.get(5);
		assertThat(result1.size(), is(2));
		assertTrue(result1.contains(point1));
		assertTrue(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<byte[]> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<byte[]> geop = (Point<byte[]>) geo;
				assertThat(geop.getType(), is(Type.POINT));
			} else {
				throw new JedisException("");
			}
		}

		result1 = (List<Geometry<byte[]>>) result.get(6);
		assertThat(result1.size(), is(2));
		assertTrue(result1.contains(point1));
		assertTrue(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<byte[]> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<byte[]> geop = (Point<byte[]>) geo;
				assertThat(geop.getType(), is(Type.POINT));
			} else {
				throw new JedisException("");
			}
		}

		result1 = (List<Geometry<byte[]>>) result.get(7);
		assertThat(result1.size(), is(1));
		assertTrue(result1.contains(point1));
		assertFalse(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<byte[]> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<byte[]> geop = (Point<byte[]>) geo;
				assertThat(geop.getType(), is(Type.POINT));
			} else {
				throw new JedisException("");
			}
		}

		assertThat((Long) result.get(8), is(2l));

		assertThat((Long) result.get(9), is(3l));

		assertThat((Long) result.get(10), is(1l));
		assertThat((Long) result.get(11), is(1l));
		assertThat((Long) result.get(12), is(0l));

	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Test
	public void testPipelinegmaddngmnn() {

		Polygon<String> polygon = new Polygon<String>(new Point<String>(1, 1), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1), new Point<String>(1, 1));
		Polygon<String> polygon2 = new Polygon<String>(new Point<String>(2, 2), new Point<String>(2, -2), new Point<String>(-2, -2),
				new Point<String>(-2, 2), new Point<String>(2, 2));
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<String> linestr = new LineString<String>(new Point<String>(0, 0), new Point<String>(1, -1), new Point<String>(-1, -1),
				new Point<String>(-1, 1));
		LineString<String> linestr2 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1), new Point<String>(-1, 4));
		LineString<String> linestr3 = new LineString<String>(new Point<String>(1, 3), new Point<String>(1, 1), new Point<String>(0, 0),
				new Point<String>(1, -1), new Point<String>(-1, -1), new Point<String>(-1, 1));

		Point<String> point1 = new Point(0, 0);
		Point<String> point2 = new Point(1, 1);

		Pipeline p = geodis.pipelined();

		p.gmadd(key, 0, 0, member1, value);
		p.gmadd(key, 1, 1, member2, value);
		p.gmadd(key, member3, value, polygon);
		p.gmadd(key, member4, value, linestr);

		p.gmnn(key, 0.4, 0.4, 2);
		p.gmnn(key, 0.4, 0.4, 2, "*1*");
		p.gmnn(key, 0.4, 0.4, 2, "*3*");
		p.gmnn(key, 0.4, 0.4, 2, "*mem???3");

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((Long) result.get(2)), is(OKl));
		assertThat(((Long) result.get(3)), is(OKl));

		List<Geometry<String>> result1 = (List<Geometry<String>>) result.get(4);
		assertThat(result1.size(), is(2));
		assertTrue(result1.contains(point1));
		assertTrue(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<String> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<String> geop = (Point<String>) geo;
				System.out.println(geop.getMember() + " , distance =" + geop.getDistance());
				assertTrue(geop.getDistance() > 0);
			} else {
				assertTrue(geo.getType() != Type.POINT);
			}
		}

		result1 = (List<Geometry<String>>) result.get(5); // expected value => 0, 0 memkey1
		assertThat(result1.size(), is(1));
		assertTrue(result1.contains(point1));
		assertFalse(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<String> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<String> geop = (Point<String>) geo;
				System.out.println(geop.getMember() + " , distance =" + geop.getDistance());
				assertTrue(geop.getDistance() > 0);
			} else {
				assertTrue(geo.getType() != Type.POINT);
			}
		}

		result1 = (List<Geometry<String>>) result.get(6); // expected value => polygon memkey3
		assertThat(result1.size(), is(1));
		assertFalse(result1.contains(point1));
		assertFalse(result1.contains(point2));
		assertTrue(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<String> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<String> geop = (Point<String>) geo;
				System.out.println(geop.getMember() + " , distance =" + geop.getDistance());
				assertTrue(geop.getDistance() > 0);
			} else {
				assertTrue(geo.getType() != Type.POINT);
			}
		}

		result1 = (List<Geometry<String>>) result.get(7); // expected value => polygon memkey3
		assertThat(result1.size(), is(1));
		assertFalse(result1.contains(point1));
		assertFalse(result1.contains(point2));
		assertTrue(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<String> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<String> geop = (Point<String>) geo;
				System.out.println(geop.getMember() + " , distance =" + geop.getDistance());
				assertTrue(geop.getDistance() > 0);
			} else {
				assertTrue(geo.getType() != Type.POINT);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Test
	public void testPipelinegmaddngmnnb() {

		Polygon<byte[]> polygon = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		Polygon<byte[]> polygon2 = new Polygon<byte[]>(new Point<byte[]>(2, 2), new Point<byte[]>(2, -2), new Point<byte[]>(-2, -2),
				new Point<byte[]>(-2, 2), new Point<byte[]>(2, 2));
		Polygon<byte[]> polygonb = new Polygon<byte[]>(new Point<byte[]>(1, 1), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1), new Point<byte[]>(1, 1));
		LineString<byte[]> linestr = new LineString<byte[]>(new Point<byte[]>(0, 0), new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1),
				new Point<byte[]>(-1, 1));
		LineString<byte[]> linestr2 = new LineString<byte[]>(new Point<byte[]>(1, 3), new Point<byte[]>(1, 1), new Point<byte[]>(0, 0),
				new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1), new Point<byte[]>(-1, 1), new Point<byte[]>(-1, 4));
		LineString<byte[]> linestr3 = new LineString<byte[]>(new Point<byte[]>(1, 3), new Point<byte[]>(1, 1), new Point<byte[]>(0, 0),
				new Point<byte[]>(1, -1), new Point<byte[]>(-1, -1), new Point<byte[]>(-1, 1));

		Point<byte[]> point1 = new Point(0, 0);
		Point<byte[]> point2 = new Point(1, 1);

		Pipeline p = geodis.pipelined();

		p.gmadd(keyb, 0, 0, member1b, valueb);
		p.gmadd(keyb, 1, 1, member2b, valueb);
		p.gmadd(keyb, member3b, valueb, polygon);
		p.gmadd(keyb, member4b, valueb, linestr);

		p.gmnn(keyb, 0.4, 0.4, 2);
		p.gmnn(keyb, 0.4, 0.4, 2, "*1*".getBytes());
		p.gmnn(keyb, 0.4, 0.4, 2, "*3*".getBytes());
		p.gmnn(keyb, 0.4, 0.4, 2, "*mem???3".getBytes());

		List<Object> result = p.syncAndReturnAll();

		assertThat(((Long) result.get(0)), is(OKl));
		assertThat(((Long) result.get(1)), is(OKl));
		assertThat(((Long) result.get(2)), is(OKl));
		assertThat(((Long) result.get(3)), is(OKl));

		List<Geometry<byte[]>> result1 = (List<Geometry<byte[]>>) result.get(4);
		assertThat(result1.size(), is(2));
		assertTrue(result1.contains(point1));
		assertTrue(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<byte[]> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<byte[]> geop = (Point<byte[]>) geo;
				System.out.println(geop.getMember() + " , distance =" + geop.getDistance());
				assertTrue(geop.getDistance() > 0);
			} else {
				assertTrue(geo.getType() != Type.POINT);
			}
		}

		result1 = (List<Geometry<byte[]>>) result.get(5); // expected value => 0, 0 memkey1
		assertThat(result1.size(), is(1));
		assertTrue(result1.contains(point1));
		assertFalse(result1.contains(point2));
		assertFalse(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<byte[]> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<byte[]> geop = (Point<byte[]>) geo;
				System.out.println(geop.getMember() + " , distance =" + geop.getDistance());
				assertTrue(geop.getDistance() > 0);
			} else {
				assertTrue(geo.getType() != Type.POINT);
			}
		}

		result1 = (List<Geometry<byte[]>>) result.get(6); // expected value => polygon memkey3
		assertThat(result1.size(), is(1));
		assertFalse(result1.contains(point1));
		assertFalse(result1.contains(point2));
		assertTrue(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<byte[]> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<byte[]> geop = (Point<byte[]>) geo;
				System.out.println(geop.getMember() + " , distance =" + geop.getDistance());
				assertTrue(geop.getDistance() > 0);
			} else {
				assertTrue(geo.getType() != Type.POINT);
			}
		}

		result1 = (List<Geometry<byte[]>>) result.get(7); // expected value => polygon memkey3
		assertThat(result1.size(), is(1));
		assertFalse(result1.contains(point1));
		assertFalse(result1.contains(point2));
		assertTrue(result1.contains(polygon));
		assertFalse(result1.contains(linestr));
		assertFalse(result1.contains(polygon2));

		for (Geometry<byte[]> geo : result1) {
			if (geo.getType() == Type.POINT) {
				Point<byte[]> geop = (Point<byte[]>) geo;
				System.out.println(geop.getMember() + " , distance =" + geop.getDistance());
				assertTrue(geop.getDistance() > 0);
			} else {
				assertTrue(geo.getType() != Type.POINT);
			}
		}
	}

}
