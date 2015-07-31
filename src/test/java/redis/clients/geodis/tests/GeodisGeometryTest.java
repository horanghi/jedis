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
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.Type;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;
import redis.clients.util.GEOMETRY;

public class GeodisGeometryTest {

	static JedisPool geodisPool;
	Jedis geodis;
	String key = "helloKey";
	String keyGG = "helloKeyGG";
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
		// spatial redis
		geodisPool = new JedisPool("172.19.114.202", 19006);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		geodisPool.destroy();
	}

	@Before
	public void setUp() throws Exception {
		geodis = geodisPool.getResource();
		geodis.del(key);
		geodis.gmsetBoundary(key, -100, -100, 100, 100);
	}

	@After
	public void tearDown() throws Exception {
		geodis.del(key);
		geodisPool.returnResource(geodis);
	}

	@Test
	public void testgmsetBoundary() {
		assertThat(geodis.gmgetBoundary(key).size(), is(2));
		assertTrue(geodis.gmgetBoundary(key).contains(new Point<String>(-100, -100)));
		assertTrue(geodis.gmgetBoundary(key).contains(new Point<String>(100, 100)));
		assertFalse(geodis.gmgetBoundary(key).contains(new Point<String>(100, 50)));

		assertTrue(geodis.gmrebuildBoundary(key, -50, -50, 50, 50) == 0); // the returned value is dummy value;

		assertThat(geodis.gmgetBoundary(key).size(), is(2));
		assertFalse(geodis.gmgetBoundary(key).contains(new Point<String>(-100, -100)));
		assertFalse(geodis.gmgetBoundary(key).contains(new Point<String>(100, 100)));
		assertFalse(geodis.gmgetBoundary(key).contains(new Point<String>(100, 50)));

		assertTrue(geodis.gmgetBoundary(key).contains(new Point<String>(-50, -50)));
		assertTrue(geodis.gmgetBoundary(key).contains(new Point<String>(50, 50)));

	}

	@Test
	public void testgmsetBoundaryException() {
		assertTrue(geodis.gmgetBoundary("horanghi").isEmpty()); // not created
		assertThat(geodis.gmrebuildBoundary("horanghi", 0, 0, 1, 1), is(0l)); // not applied
		assertTrue(geodis.gmgetBoundary("horanghi").isEmpty()); // not created

		assertTrue(geodis.gmgetBoundary("horanghi".getBytes()).isEmpty()); // not created
		assertThat(geodis.gmrebuildBoundary("horanghi".getBytes(), 0, 0, 1, 1), is(0l)); // not applied
		assertTrue(geodis.gmgetBoundary("horanghi".getBytes()).isEmpty()); // not created
	}

	@Test
	public void testgmsetBoundaryb() {
		assertThat(geodis.gmgetBoundary(keyb).size(), is(2));
		assertTrue(geodis.gmgetBoundary(keyb).contains(new Point<byte[]>(-100, -100)));
		assertTrue(geodis.gmgetBoundary(keyb).contains(new Point<byte[]>(100, 100)));
		assertFalse(geodis.gmgetBoundary(keyb).contains(new Point<byte[]>(100, 50)));

		assertTrue(geodis.gmrebuildBoundary(keyb, -50, -50, 50, 50) == 0); // the returned value is dummy value;

		assertThat(geodis.gmgetBoundary(keyb).size(), is(2));
		assertFalse(geodis.gmgetBoundary(keyb).contains(new Point<byte[]>(-100, -100)));
		assertFalse(geodis.gmgetBoundary(keyb).contains(new Point<byte[]>(100, 100)));
		assertFalse(geodis.gmgetBoundary(keyb).contains(new Point<byte[]>(100, 50)));

		assertTrue(geodis.gmgetBoundary(keyb).contains(new Point<byte[]>(-50, -50)));
		assertTrue(geodis.gmgetBoundary(keyb).contains(new Point<byte[]>(50, 50)));

	}

	@Test
	public void testgmaddngmget() {
		assertThat(geodis.gmadd(key, 10, 10, member1, value), is(OKl));
		assertThat(geodis.gmadd(key, 10, 20, member2, value), is(OKl));
		assertThat(geodis.gmupdate(key, member1, new Point<String>(10, 11)), is(OKl));
		assertThat(geodis.gmupdate(key, member2, new Point<String>(10, 21)), is(OKl));

		assertThat((Point<String>) geodis.gmget(key, member1), is(new Point<String>(member1, 10, 11, value)));
		assertThat((Point<String>) geodis.gmget(key, member2), is(new Point<String>(member2, 10, 21, value)));

		assertTrue(geodis.gmget(key, member1).equalsDeep(new Point<String>(member1, 10, 11, value)));
		assertTrue(geodis.gmget(key, member2).equalsDeep(new Point<String>(member2, 10, 21, value)));

		assertFalse(((Point<String>) geodis.gmget(key, member2)).equalsDeep(new Point<String>(member2, 10, 23, value)));

		assertFalse(geodis.gmget(key, member2).equalsDeep(new Point<String>(member2, 10, 21, value + "1")));

		assertThat(geodis.gmupdate(key, member1, new Point<String>(10, 12)), is(OKl));
		assertThat(geodis.gmupdate(key, member2, new Point<String>(10, 22)), is(OKl));

		assertThat((Point<String>) geodis.gmget(key, member1), is(new Point<String>(member1, 10, 12, value)));
		assertThat((Point<String>) geodis.gmget(key, member2), is(new Point<String>(member2, 10, 22, value)));

	}

	public void testgmaddngmgetb() {
		assertThat(geodis.gmadd(keyb, 10, 10, member1b, valueb), is(OKl));
		assertThat(geodis.gmadd(keyb, 10, 20, member2b, valueb), is(OKl));
		assertThat(geodis.gmupdate(keyb, member1b, new Point<byte[]>(10, 11)), is(OKl));
		assertThat(geodis.gmupdate(keyb, member2b, new Point<byte[]>(10, 21)), is(OKl));

		assertThat((Point<byte[]>) geodis.gmget(keyb, member1b), is(new Point<byte[]>(member1b, 10, 11, valueb)));
		assertThat((Point<byte[]>) geodis.gmget(keyb, member2b), is(new Point<byte[]>(member2b, 10, 21, valueb)));

		assertTrue(geodis.gmget(keyb, member1b).equalsDeep(new Point<byte[]>(member1b, 10, 11, valueb)));
		assertTrue(geodis.gmget(keyb, member2b).equalsDeep(new Point<byte[]>(member2b, 10, 21, valueb)));

		assertFalse(((Point<byte[]>) geodis.gmget(keyb, member2b)).equalsDeep(new Point<byte[]>(member2b, 10, 23, valueb)));

		assertThat(geodis.gmupdate(keyb, member1b, new Point<byte[]>(10, 12)), is(OKl));
		assertThat(geodis.gmupdate(keyb, member2b, new Point<byte[]>(10, 22)), is(OKl));

		assertThat((Point<byte[]>) geodis.gmget(keyb, member1b), is(new Point<byte[]>(member1b, 10, 12, valueb)));
		assertThat((Point<byte[]>) geodis.gmget(keyb, member2b), is(new Point<byte[]>(member2b, 10, 22, valueb)));
	}

	@Test
	public void testgmaddngmrelation01() {

		assertThat(geodis.gmadd(key, 10, 10, member1, value), is(OKl));
		assertThat(geodis.gmadd(key, 10, 12, member2, value), is(OKl));
		assertThat(geodis.gmadd(key, 10, 13, member3, value), is(OKl));
		
		assertThat(geodis.gmexists(key, member1), is(1l));
		assertThat(geodis.gmexists(key, member2), is(1l));
		assertThat(geodis.gmexists(key, "horanghi"), is(0l));
		
		assertThat(
				geodis.gmrelation(
						key,
						new LineString<String>(Arrays.asList(new Point<String>(-1, -1), new Point<String>(0, 0), new Point<String>(10, 12),
								new Point<String>(11, 12)))).size(), is(1));
		assertThat(
				geodis.gmrelation(
						key,
						new Polygon<String>(Arrays.asList(new Point<String>(-1, -1), new Point<String>(11, -1), new Point<String>(11, 12),
								new Point<String>(-1, 1), new Point<String>(-1, -1)))).size(), is(1));
		assertThat(
				geodis.gmrelation(
						key,
						new Polygon<String>(Arrays.asList(new Point<String>(-1, -1), new Point<String>(11, -1), new Point<String>(11, 13),
								new Point<String>(-1, 13), new Point<String>(-1, -1)))).size(), is(2));
		assertThat(
				geodis.gmrelation(
						key,
						new Polygon<String>(Arrays.asList(new Point<String>(-1, -1), new Point<String>(11, -1), new Point<String>(11, 14),
								new Point<String>(-1, 14), new Point<String>(-1, -1)))).size(), is(3));

		assertThat(geodis.gmrelation(key, new Point<String>(10, 10)).size(), is(1));
	}

	@Test
	public void testgmaddngmrelation01b() {

		assertThat(geodis.gmadd(keyb, 10, 10, member1b, valueb), is(OKl));
		assertThat(geodis.gmadd(keyb, 10, 12, member2b, valueb), is(OKl));
		assertThat(geodis.gmadd(keyb, 10, 13, member3b, valueb), is(OKl));
		
		assertThat(geodis.gmexists(keyb, member1b), is(1l));
		assertThat(geodis.gmexists(keyb, member2b), is(1l));
		assertThat(geodis.gmexists(keyb, "horanghi".getBytes()), is(0l));
		
		assertThat(
				geodis.gmrelation(
						keyb,
						new LineString<byte[]>(Arrays.asList(new Point<byte[]>(-1, -1), new Point<byte[]>(0, 0), new Point<byte[]>(10, 12),
								new Point<byte[]>(11, 12)))).size(), is(1));
		assertThat(
				geodis.gmrelation(
						keyb,
						new Polygon<byte[]>(Arrays.asList(new Point<byte[]>(-1, -1), new Point<byte[]>(11, -1), new Point<byte[]>(11, 12),
								new Point<byte[]>(-1, 1), new Point<byte[]>(-1, -1)))).size(), is(1));
		assertThat(
				geodis.gmrelation(
						keyb,
						new Polygon<byte[]>(Arrays.asList(new Point<byte[]>(-1, -1), new Point<byte[]>(11, -1), new Point<byte[]>(11, 13),
								new Point<byte[]>(-1, 13), new Point<byte[]>(-1, -1)))).size(), is(2));
		assertThat(
				geodis.gmrelation(
						keyb,
						new Polygon<byte[]>(Arrays.asList(new Point<byte[]>(-1, -1), new Point<byte[]>(11, -1), new Point<byte[]>(11, 14),
								new Point<byte[]>(-1, 14), new Point<byte[]>(-1, -1)))).size(), is(3));
		assertThat(geodis.gmrelation(keyb, new Point<byte[]>(10, 10)).size(), is(1));
	}

	@Test
	public void testgmaddngmcard() {
		assertThat(geodis.gmadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gmadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gmadd(key, 0, 0, member3, value), is(OKl));
		assertThat(geodis.gmadd(key, 0, 0, member4, value), is(OKl));

		assertThat(geodis.gmcard(key), is(4l));
		assertThat(geodis.gmcard(keyb), is(4l));
	}

	@Test
	public void testgmaddngmremngmcard() {

		Polygon<String> polygon = new Polygon<String>(Arrays.asList(new Point<String>(-1, -1), new Point<String>(11, -1),
				new Point<String>(11, 13), new Point<String>(-1, 13), new Point<String>(-1, -1)));
		Polygon<byte[]> polygonb = new Polygon<byte[]>(Arrays.asList(new Point<byte[]>(-1, -1), new Point<byte[]>(11, -1),
				new Point<byte[]>(11, 13), new Point<byte[]>(-1, 13), new Point<byte[]>(-1, -1)));

		Polygon<String> polygonc = new Polygon<String>(Arrays.asList(new Point<String>(-1, 0), new Point<String>(11, -1),
				new Point<String>(11, 13), new Point<String>(-1, 13), new Point<String>(-1, 0)));
		Polygon<byte[]> polygoncb = new Polygon<byte[]>(Arrays.asList(new Point<byte[]>(-1, 0), new Point<byte[]>(11, -1),
				new Point<byte[]>(11, 13), new Point<byte[]>(-1, 13), new Point<byte[]>(-1, 0)));

		LineString<String> linestr = new LineString<String>(Arrays.asList(new Point<String>(-1, -1), new Point<String>(0, 0),
				new Point<String>(10, 12), new Point<String>(11, 12)));
		LineString<byte[]> linestrb = new LineString<byte[]>(Arrays.asList(new Point<byte[]>(-1, -1), new Point<byte[]>(0, 0),
				new Point<byte[]>(10, 12), new Point<byte[]>(11, 12)));

		assertThat(geodis.gmadd(keyb, member1b, valueb, linestrb), is(OKl));
		assertThat(geodis.gmadd(key, member2, value, linestr), is(OKl));
		assertThat(geodis.gmcard(key), is(2l));
		assertThat((LineString<byte[]>) geodis.gmget(keyb, member1b), is(linestrb));
		assertThat((LineString<String>) geodis.gmget(key, member2), is(linestr));
		assertThat(geodis.gmrem(key, member1), is(OKl));
		assertThat(geodis.gmrem(key, member2), is(OKl));
		assertThat(geodis.gmcard(key), is(0l));

		assertThat(geodis.gmadd(keyb, member1b, valueb, new Point<byte[]>(0, 0)), is(OKl));
		assertThat(geodis.gmadd(key, member2, value, new Point<String>(0, 0)), is(OKl));
		assertThat(geodis.gmcard(key), is(2l));
		assertThat((Point<byte[]>) geodis.gmget(keyb, member1b), is(new Point<byte[]>(0, 0)));
		assertThat((Point<String>) geodis.gmget(key, member2), is(new Point<String>(0, 0)));
		assertThat(geodis.gmrem(key, member1), is(OKl));
		assertThat(geodis.gmrem(key, member2), is(OKl));
		assertThat(geodis.gmcard(key), is(0l));

		assertThat(geodis.gmadd(keyb, member1b, valueb, polygonb), is(OKl));
		assertThat(geodis.gmadd(key, member2, value, polygon), is(OKl));
		assertThat(geodis.gmupdate(key, member1, polygonc), is(OKl));
		assertThat(geodis.gmcard(key), is(2l));
		assertThat((Polygon<byte[]>) geodis.gmget(keyb, member1b), is(polygoncb));
		assertThat((Polygon<String>) geodis.gmget(key, member1), is(polygonc));
		assertThat(geodis.gmget(key, member2), not((Geometry<String>) linestr));
		assertThat(geodis.gmrem(key, member1), is(OKl));
		assertThat(geodis.gmrem(key, member2), is(OKl));
		assertThat(geodis.gmcard(key), is(0l));

	}

	@Test
	public void testgmaddngmrem() {
		assertThat(geodis.gmadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gmadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gmadd(key, 0, 0, member3, value), is(OKl));
		assertThat(geodis.gmadd(key, 0, 0, member4, value), is(OKl));

		assertThat(geodis.gmrem(keyb, member1b), is(OKl));
		assertThat(geodis.gmcard(keyb), is(3l));
		assertThat(geodis.gmrem(keyb, member1b), is(0l));
		assertThat(geodis.gmcard(keyb), is(3l));
		assertThat(geodis.gmrem(key, member3), is(1l));
		assertThat(geodis.gmcard(key), is(2l));
	}

	@Test
	public void testgmaddngmmgetngmrange() {
		assertThat(geodis.gmadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gmadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gmadd(key, 0, 0, member3, value), is(OKl));
		assertThat(geodis.gmadd(key, 0, 0, member4, value), is(OKl));

		assertThat(geodis.gmrem(keyb, member1b), is(OKl));
		assertThat(geodis.gmcard(keyb), is(3l));
		assertThat(geodis.gmrem(keyb, member1b), is(0l));
		assertThat(geodis.gmcard(keyb), is(3l));
		assertThat(geodis.gmrem(key, member3), is(1l));
		List<Geometry<String>> geoms = geodis.gmmget(key, member1, member2, member3, member4);
		for (Geometry<String> geo : geoms) {
			assertThat(geo.getType(), is(Type.POINT));
		}

		List<Geometry<byte[]>> geomsb = geodis.gmmget(keyb, member1b, member2b, member3b, member4b);
		for (Geometry<byte[]> geo : geomsb) {
			assertThat(geo.getType(), is(Type.POINT));
		}

		List<Geometry<String>> geoms2 = geodis.gmrange(key, 0, -1);
		for (Geometry<String> geo : geoms2) {
			assertThat(geo.getType(), is(Type.POINT));
		}

		List<Geometry<byte[]>> geoms2b = geodis.gmrange(keyb, 0, -1);
		for (Geometry<byte[]> geo : geoms2b) {
			assertThat(geo.getType(), is(Type.POINT));
		}
		assertThat(geoms.size(), is(2));
	}

	@Test
	public void testgmaddngmmgetngmrevrange() {
		assertThat(geodis.gmadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gmadd(keyb, 0, 0, member2b, valueb), is(OKl));
		assertThat(geodis.gmadd(key, 0, 0, member3, value), is(OKl));
		assertThat(geodis.gmadd(key, 0, 0, member4, value), is(OKl));

		assertThat(geodis.gmrem(keyb, member1b), is(OKl));
		assertThat(geodis.gmcard(keyb), is(3l));
		assertThat(geodis.gmrem(keyb, member1b), is(0l));
		assertThat(geodis.gmcard(keyb), is(3l));
		assertThat(geodis.gmrem(key, member3), is(1l));
		List<Geometry<String>> geoms = geodis.gmmget(key, member1, member2, member3, member4);
		for (Geometry<String> geo : geoms) {
			assertThat(geo.getType(), is(Type.POINT));
		}

		List<Geometry<String>> geoms2 = geodis.gmrevrange(key, 0, -1);
		assertThat(((Point<String>) geoms2.get(0)), is(new Point<String>(member1, 0, 0, value)));
		for (Geometry<String> geo : geoms2) {
			assertThat(geo.getType(), is(Type.POINT));
		}

		List<Geometry<byte[]>> geoms2b = geodis.gmrevrange(keyb, 0, -1);
		assertThat(((Point<byte[]>) geoms2b.get(0)), is(new Point<byte[]>(member1b, 0, 0, valueb)));
		for (Geometry<byte[]> geo : geoms2b) {
			assertThat(geo.getType(), is(Type.POINT));
		}

		List<Geometry<String>> geoms2c = geodis.gmrevrange(key, 0, 1);
		assertThat(((Point<String>) geoms2c.get(0)), is(new Point<String>(member4, 0, 0, value)));
		for (Geometry<String> geo : geoms2c) {
			assertThat(geo.getType(), is(Type.POINT));
		}

		List<Geometry<byte[]>> geoms2bc = geodis.gmrevrange(keyb, 0, 1);
		assertThat(((Point<byte[]>) geoms2bc.get(0)), is(new Point<byte[]>(member4b, 0, 0, valueb)));
		for (Geometry<byte[]> geo : geoms2bc) {
			assertThat(geo.getType(), is(Type.POINT));
		}

		assertThat(geoms.size(), is(2));
	}

	@Test
	public void testgmaddngmnn() {
		assertThat(geodis.gmadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gmadd(keyb, 1, 1, member2b, valueb), is(OKl));
		assertThat(geodis.gmadd(key, 2, 2, member3, value), is(OKl));
		assertThat(geodis.gmadd(key, 3, 3, member4, value), is(OKl));

		assertThat(geodis.gmnn(keyb, 4, 4, 2).size(), is(2));
		assertThat(geodis.gmnn(keyb, 4, 4, 3).size(), is(3));
	}

	@Test
	public void testgmaddngmnnpattern() {
		assertThat(geodis.gmadd(keyb, 0, 0, member1b, valueb), is(OKl));
		assertThat(geodis.gmadd(keyb, 1, 1, member2b, valueb), is(OKl));
		assertThat(geodis.gmadd(key, 2, 2, member3, value), is(OKl));
		assertThat(geodis.gmadd(key, 3, 3, member4, value), is(OKl));

		assertThat(geodis.gmnn(key, 4, 4, 2, "memkey*").size(), is(2));
		assertThat(geodis.gmnn(key, 4, 4, 3, "memkey*").size(), is(3));
	}

}
