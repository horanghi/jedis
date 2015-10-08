import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.jedis.Response;
import redis.clients.spatial.model.Point;

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
		pool = new JedisPool("172.19.114.202", 19006);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		pool.destroy();
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
		// Point point = new Point(126.97553009053178, 37.56456569639763);
		// Point resultP = Covert2Coordinates.transCoord(point, COORD.COORD_TYPE_WGS84, COORD.COORD_TYPE_KTM);
		// Point resultP = Covert2Coordinates.transCoord(resultP2, COORD.COORD_TYPE_TM, COORD.COORD_TYPE_KTM);
		// System.out.println(resultP);
		// // POINT (126.97764916825773 37.56178748745262) 
		// // <result x="126.97762994246017" y="37.56176812268042"/>

//		CRSFactory crsFactory = new CRSFactory();
//
//		CoordinateReferenceSystem src = crsFactory.createFromName("epsg:4326");
//		CoordinateReferenceSystem dest = crsFactory.createFromName("epsg:4162");
//
//		CoordinateTransformFactory ctf = new CoordinateTransformFactory();
//		CoordinateTransform transform = ctf.createTransform(src, dest);
//
//		ProjCoordinate srcPt = new ProjCoordinate(37.56456569639763, 126.97553009053178);
//		ProjCoordinate destPt = new ProjCoordinate();
//
//		transform.transform(srcPt, destPt);
//		System.out.println(srcPt + " ==> " + destPt);
//
//		// do it again
//		ProjCoordinate destPt2 = new ProjCoordinate();
//		transform.transform(srcPt, destPt2);
//		System.out.println(srcPt + " ==> " + destPt2);
		
		//ProjCoordinate[37.56456569639763 126.97553009053178 NaN] ==> ProjCoordinate[37.56456569639763 126.97553009053179 NaN]
		//ProjCoordinate[37.56456569639763 126.97553009053178 NaN] ==> ProjCoordinate[37.56456569639763 126.97553009053179 NaN]
		//               37.56456569639763 126.97553009053179
				
		CRSFactory crsFactory2 = new CRSFactory();

		CoordinateReferenceSystem src2 = crsFactory2.createFromName("epsg:4162");
		CoordinateReferenceSystem dest2 = crsFactory2.createFromName("epsg:4326");

		CoordinateTransformFactory ctf2 = new CoordinateTransformFactory();
		CoordinateTransform transform2 = ctf2.createTransform(src2, dest2);

		ProjCoordinate srcPt2 = new ProjCoordinate(37.56456569639763, 126.97553009053179);
		ProjCoordinate destPt2 = new ProjCoordinate();

		transform2.transform(srcPt2, destPt2);
		System.out.println(srcPt2 + " ==> " + destPt2);

		// do it again
//		ProjCoordinate destPt2 = new ProjCoordinate();
//		transform.transform(srcPt, destPt2);
//		System.out.println(srcPt + " ==> " + destPt2);
	}
}
