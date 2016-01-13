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

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
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
import redis.clients.jedis.tests.benchmark.GetSetBenchmark;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;
import redis.clients.util.GEOMETRY;

public class SampleTest {

	static JedisPool geodisPool;
	static Jedis geodis;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// spatial redis
		geodisPool = new JedisPool(new GenericObjectPoolConfig(), "172.19.114.203", 19007, 2000, "1234");

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
	public void testSample() {
		geodis.set("hello0", "world");
		geodis.set("hello1", "world");

		geodis.mget("hello0", "hello2", "hello1");
		String key = "good";
		geodis.gpadd(key, 0, 0, "member1", "value");
		geodis.gpmget(key, "member1", "member2");
	}

}
