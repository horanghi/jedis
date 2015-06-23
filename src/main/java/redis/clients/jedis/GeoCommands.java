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

	Long gpadd(String key, double lat, double lon, String member, String value);

	Long gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	Long gpadd(String key, double lat, double lon, long radius, UNITS unit, String member, String value);

	Long gpadd(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] member, byte[] value);

	Long gpupdate(String key, String member, double lat, double lon);

	Long gpupdate(byte[] key, byte[] member, double lat, double lon);

	Long gpupdate(String key, String member, double lat, double lon, long radius, UNITS unit);

	Long gpupdate(byte[] key, byte[] member, double lat, double lon, long radius, UNITS unit);

	List<Point<String>> gprangeBy(String key, String bykey, String bymember);

	List<Point<byte[]>> gprangeBy(byte[] key, byte[] bykey, byte[] bymember);

	List<Point<String>> gprangeBy(String key, String bykey, String bymember, String pattern, long count);

	List<Point<byte[]>> gprangeBy(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern, long count);

	List<Point<String>> gprangeByRadius(String key, double lat, double lon, long radius, UNITS unit);

	List<Point<byte[]>> gprangeByRadius(byte[] key, double lat, double lon, long radius, UNITS unit);

	List<Circle<String>> gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit);

	List<Circle<byte[]>> gprangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit);

	List<Circle<String>> gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit, RELATION scope, ORDERBY order);

	List<Circle<byte[]>> gprangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, RELATION scope, ORDERBY order);

	List<Point<String>> gprangeByRadius(String key, double lat, double lon, long radius, UNITS unit, String pattern);

	List<Point<byte[]>> gprangeByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern);

	List<Circle<String>> gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit, String pattern);

	List<Circle<byte[]>> gprangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern);

	List<Circle<String>> gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit, String pattern, RELATION ops,
			ORDERBY sort);

	List<Circle<byte[]>> gprangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern, RELATION ops,
			ORDERBY sort);

	Long gpcard(String key);

	Long gpcard(byte[] key);

	Long gprem(String key, String member);

	Long gprem(byte[] key, byte[] member);

	Point<String> gpget(String key, String member);

	Point<byte[]> gpget(byte[] key, byte[] member);

	List<Point<String>> gpmget(String key, String... members);

	List<Point<byte[]>> gpmget(byte[] key, byte[]... members);

	List<Point<String>> gpnn(String key, double lat, double lon, long count);

	List<Point<byte[]>> gpnn(byte[] key, double lat, double lon, long count);

	List<Point<String>> gprangeByRegion(String key, Polygon<?> polygon);

	List<Point<byte[]>> gprangeByRegion(byte[] key, Polygon<?> polygon);

	List<Point<String>> gprangeByRegion(String key, LineString<?> lineString);

	List<Point<byte[]>> gprangeByRegion(byte[] key, LineString<?> lineString);

	List<Point<String>> gprangeByRegion(String key, Point<?> point);

	List<Point<byte[]>> gprangeByRegion(byte[] key, Point<?> point);

	List<Point<String>> gprangeByRegion(String key, Polygon<?> polygon, String pattern);

	List<Point<byte[]>> gprangeByRegion(byte[] key, Polygon<?> polygon, byte[] pattern);

	List<Point<String>> gprangeByRegion(String key, LineString<?> lineString, String pattern);

	List<Point<byte[]>> gprangeByRegion(byte[] key, LineString<?> lineString, byte[] pattern);

	List<Point<String>> gprangeByRegion(String key, Point<?> point, String pattern);

	List<Point<byte[]>> gprangeByRegion(byte[] key, Point<?> point, byte[] pattern);

	List<Point<String>> gprangeByRegion(String key, Polygon<?> polygon, String pattern, long count);

	List<Point<byte[]>> gprangeByRegion(byte[] key, Polygon<?> polygon, byte[] pattern, long count);

	double distance(double dLat1, double dLon1, double dLat2, double dLon2);

	/* Geography */

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

	List<Geometry<String>> ggrelationBy(String key, String byKey, String byMember);

	List<Geometry<byte[]>> ggrelationBy(byte[] key, byte[] byKey, byte[] byMember);

}
