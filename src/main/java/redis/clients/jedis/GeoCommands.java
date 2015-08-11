package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

/**
 * Common interface for sharded and non-sharded Jedis
 */
public interface GeoCommands {

	Long gpexists(String key, String member);

	Long gpexists(byte[] key, byte[] member);

	Long gpadd(String key, double lat, double lon, String member, String value);

	Long gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	Long gpadd(String key, double lat, double lon, String member, String value, double score);

	Long gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value, double score);

	Long gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value);

	Long gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value);

	Long gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value, double score);

	Long gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value, double score);

	// gpupdate

	Long gpupdate(String key, String member, double lat, double lon);

	Long gpupdate(byte[] key, byte[] member, double lat, double lon);

	Long gpupdate(String key, String member, double lat, double lon, double radius, UNITS unit);

	Long gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit);

	// gpradius

	List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit);

	List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit);

	List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String pattern);

	List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] pattern);

	List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max, String pattern);

	List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, byte[] pattern);

	List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max, ORDERBY order);

	List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, ORDERBY order);

	List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max, String pattern,
			long offset, long count, ORDERBY order);

	List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, byte[] pattern,
			long offset, long count, ORDERBY order);

	// gpcircle

	List<Circle<String>> gpcircle(String key, double lat, double lon, double radius, UNITS unit);

	List<Circle<byte[]>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit);

	List<Circle<String>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, RELATION scope, ORDERBY order);

	List<Circle<byte[]>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, RELATION scope, ORDERBY order);

	List<Circle<String>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String pattern);

	List<Circle<byte[]>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] pattern);

	List<Circle<String>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String pattern, RELATION ops, ORDERBY sort);

	List<Circle<byte[]>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] pattern, RELATION ops, ORDERBY sort);

	// gpradiusByMember

	List<Point<String>> gpradiusByMember(String key, String bykey, String bymember);

	List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember);

	List<Point<String>> gpradiusByMember(String key, String bykey, String bymember, String pattern);

	List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern);

	List<Point<String>> gpradiusByMember(String key, String bykey, String bymember, String min, String max, String pattern);

	List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] pattern);

	List<Point<String>> gpradiusByMember(String key, String bykey, String bymember, String min, String max, String pattern, long offset,
			long count, ORDERBY order);

	List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] pattern, long offset,
			long count, ORDERBY order);

	// gpregionByMember

	List<Point<String>> gpregionByMember(String key, String bykey, String bymember);

	List<Point<byte[]>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember);

	List<Point<String>> gpregionByMember(String key, String bykey, String bymember, String pattern);

	List<Point<byte[]>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern);

	List<Point<String>> gpregionByMember(String key, String bykey, String bymember, String min, String max, String pattern);

	List<Point<byte[]>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] pattern);

	List<Point<String>> gpregionByMember(String key, String bykey, String bymember, String min, String max, String pattern, long offset,
			long count, ORDERBY order);

	List<Point<byte[]>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] pattern, long offset,
			long count, ORDERBY order);

	// gpnn

	List<Point<String>> gpnn(String key, double lat, double lon, long offset, long count);

	List<Point<byte[]>> gpnn(byte[] key, double lat, double lon, long offset, long count);

	List<Point<String>> gpnn(String key, double lat, double lon, long offset, long count, String pattern);

	List<Point<byte[]>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] pattern);

	List<Point<String>> gpnn(String key, double lat, double lon, long offset, long count, String pattern, String min, String max,
			ORDERBY order);

	List<Point<byte[]>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] pattern, byte[] min, byte[] max,
			ORDERBY order);

	// gpregion

	List<Point<String>> gpregion(String key, Polygon<?> polygon);

	List<Point<byte[]>> gpregion(byte[] key, Polygon<?> polygon);

	List<Point<String>> gpregion(String key, LineString<?> lineString);

	List<Point<byte[]>> gpregion(byte[] key, LineString<?> lineString);

	List<Point<String>> gpregion(String key, Point<?> point);

	List<Point<byte[]>> gpregion(byte[] key, Point<?> point);

	List<Point<String>> gpregion(String key, Polygon<?> polygon, String pattern);

	List<Point<byte[]>> gpregion(byte[] key, Polygon<?> polygon, byte[] pattern);

	List<Point<String>> gpregion(String key, LineString<?> lineString, String pattern);

	List<Point<byte[]>> gpregion(byte[] key, LineString<?> lineString, byte[] pattern);

	List<Point<String>> gpregion(String key, Point<?> point, String pattern);

	List<Point<byte[]>> gpregion(byte[] key, Point<?> point, byte[] pattern);

	List<Point<String>> gpregion(String key, Polygon<?> polygon, String min, String max, String pattern);

	List<Point<byte[]>> gpregion(byte[] key, Polygon<?> polygon, byte[] min, byte[] max, byte[] pattern);

	List<Point<String>> gpregion(String key, LineString<?> lineString, String min, String max, String pattern);

	List<Point<byte[]>> gpregion(byte[] key, LineString<?> lineString, byte[] min, byte[] max, byte[] pattern);

	List<Point<String>> gpregion(String key, Polygon<?> polygon, String min, String max, long offset, long count, String pattern);

	List<Point<byte[]>> gpregion(byte[] key, Polygon<?> polygon, byte[] min, byte[] max, long offset, long count, byte[] pattern);

	List<Point<String>> gpregion(String key, LineString<?> lineString, String min, String max, long offset, long count, String pattern);

	List<Point<byte[]>> gpregion(byte[] key, LineString<?> lineString, byte[] min, byte[] max, long offset, long count, byte[] pattern);

	Long gpcard(String key);

	Long gpcard(byte[] key);

	Long gprem(String key, String member);

	Long gprem(byte[] key, byte[] member);

	Point<String> gpget(String key, String member);

	Point<byte[]> gpget(byte[] key, byte[] member);

	List<Point<String>> gpmget(String key, String... members);

	List<Point<byte[]>> gpmget(byte[] key, byte[]... members);
	
	List<Point<String>> gpscope(String key, String min, String max, String pattern, ORDERBY order);

	List<Point<byte[]>> gpscope(byte[] key, byte[] min, byte[] max, byte[] pattern, ORDERBY order);

	List<Point<String>> gpscope(String key, String min, String max, long offset, long count, String pattern, ORDERBY order);

	List<Point<byte[]>> gpscope(byte[] key, byte[] min, byte[] max, long offset, long count, byte[] pattern, ORDERBY order);

	double gpdistance(double dLat1, double dLon1, double dLat2, double dLon2);

	/* Geography */

	Long ggexists(String key, String member);

	Long ggexists(byte[] key, byte[] member);

	Long ggadd(String key, String member, String value, Polygon<?> polygon);

	Long ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);

	Long ggadd(String key, String member, String value, LineString<?> lineString);

	Long ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	List<Geometry<String>> ggrange(String key, long start, long stop);

	List<Geometry<byte[]>> ggrange(byte[] key, long start, long stop);

	Long ggadd(String key, String member, String value, Point<?> point);

	Long ggadd(byte[] key, byte[] member, byte[] value, Point<?> point);

	List<Geometry<String>> ggrevrange(String key, long start, long stop);

	List<Geometry<byte[]>> ggrevrange(byte[] key, long start, long stop);

	Long ggcard(String key);

	Long ggcard(byte[] key);

	Long ggrem(String key, String member);

	Long ggrem(byte[] key, byte[] member);

	Geometry<String> ggget(String key, String member);

	Geometry<byte[]> ggget(byte[] key, byte[] member);

	List<Geometry<String>> ggmget(String key, String... members);

	List<Geometry<byte[]>> ggmget(byte[] key, byte[]... members);

	List<Geometry<String>> ggrelation(String key, Polygon<?> polygon);

	List<Geometry<byte[]>> ggrelation(byte[] key, Polygon<?> polygon);

	List<Geometry<String>> ggrelation(String key, LineString<?> lineString);

	List<Geometry<byte[]>> ggrelation(byte[] key, LineString<?> lineString);

	List<Geometry<String>> ggrelation(String key, Point<?> point);

	List<Geometry<byte[]>> ggrelation(byte[] key, Point<?> point);

	List<Geometry<String>> ggnn(String key, double lat, double lon, long count);

	List<Geometry<byte[]>> ggnn(byte[] key, double lat, double lon, long count);

	List<Geometry<String>> ggnn(String key, double lat, double lon, long count, String pattern);

	List<Geometry<byte[]>> ggnn(byte[] key, double lat, double lon, long count, byte[] pattern);

	Long ggupdate(String key, String member, Point<?> point);

	Long ggupdate(byte[] key, byte[] member, Point<?> point);

	Long ggupdate(String key, String member, Polygon<?> polygon);

	Long ggupdate(byte[] key, byte[] member, Polygon<?> polygon);

	Long ggupdate(String key, String member, LineString<?> lineString);

	Long ggupdate(byte[] key, byte[] member, LineString<?> lineString);

	List<Geometry<String>> ggrelationByMember(String key, String byKey, String byMember);

	List<Geometry<byte[]>> ggrelationByMember(byte[] key, byte[] byKey, byte[] byMember);

	/* Geometry */

	Long gmexists(String key, String member);

	Long gmexists(byte[] key, byte[] member);

	double gmdistance(double x1, double y1, double x2, double y2);

	String gmsetBoundary(String key, double minx, double miny, double maxx, double maxy);

	String gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy);

	List<Point<String>> gmgetBoundary(String key);

	List<Point<byte[]>> gmgetBoundary(byte[] key);

	Long gmrebuildBoundary(String key, double minx, double miny, double maxx, double maxy);

	Long gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy);

	Long gmadd(String key, String member, String value, Polygon<?> polygon);

	Long gmadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);

	Long gmadd(String key, String member, String value, LineString<?> lineString);

	Long gmadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	Long gmadd(String key, String member, String value, Point<?> point);

	Long gmadd(byte[] key, byte[] member, byte[] value, Point<?> point);

	Long gmadd(String key, double x, double y, String member, String value);

	Long gmadd(byte[] key, double x, double y, byte[] member, byte[] value);

	List<Geometry<String>> gmrange(String key, long start, long stop);

	List<Geometry<byte[]>> gmrange(byte[] key, long start, long stop);

	List<Geometry<String>> gmrevrange(String key, long start, long stop);

	List<Geometry<byte[]>> gmrevrange(byte[] key, long start, long stop);

	Long gmcard(String key);

	Long gmcard(byte[] key);

	Long gmrem(String key, String member);

	Long gmrem(byte[] key, byte[] member);

	Geometry<String> gmget(String key, String member);

	Geometry<byte[]> gmget(byte[] key, byte[] member);

	List<Geometry<String>> gmmget(String key, String... members);

	List<Geometry<byte[]>> gmmget(byte[] key, byte[]... members);

	List<Geometry<String>> gmrelation(String key, Polygon<?> polygon);

	List<Geometry<byte[]>> gmrelation(byte[] key, Polygon<?> polygon);

	List<Geometry<String>> gmrelation(String key, LineString<?> lineString);

	List<Geometry<byte[]>> gmrelation(byte[] key, LineString<?> lineString);

	List<Geometry<String>> gmrelation(String key, Point<?> point);

	List<Geometry<byte[]>> gmrelation(byte[] key, Point<?> point);

	List<Geometry<String>> gmnn(String key, double x, double y, long count);

	List<Geometry<byte[]>> gmnn(byte[] key, double x, double y, long count);

	List<Geometry<String>> gmnn(String key, double x, double y, long count, String pattern);

	List<Geometry<byte[]>> gmnn(byte[] key, double x, double y, long count, byte[] pattern);

	Long gmupdate(String key, String member, Point<?> point);

	Long gmupdate(byte[] key, byte[] member, Point<?> point);

	Long gmupdate(String key, String member, Polygon<?> polygon);

	Long gmupdate(byte[] key, byte[] member, Polygon<?> polygon);

	Long gmupdate(String key, String member, LineString<?> lineString);

	Long gmupdate(byte[] key, byte[] member, LineString<?> lineString);

	List<Geometry<String>> gmrelationByMember(String key, String byKey, String byMember);

	List<Geometry<byte[]>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember);


}