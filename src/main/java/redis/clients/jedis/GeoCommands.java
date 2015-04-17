package redis.clients.jedis;

import java.util.List;

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

	Long gadd(String key, double lat, double lon, String member, String value);

	Long gadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	Long gfadd(String key, double lat, double lon, double distance, UNITS unit, String member, String value);

	Long gfadd(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] member, byte[] value);

	List<Point<String>> gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit);

	List<Point<byte[]>> gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	List<Circle<String>> gfrangeCircleByRadius(String key, double lat, double lon, double distance, UNITS unit);

	List<Circle<byte[]>> gfrangeCircleByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	List<Point<String>> gfrangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	List<Point<byte[]>> gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	List<Point<String>> gfrangeCircleByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	List<Point<byte[]>> gfrangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	Long gfcard(String key);

	Long gfcard(byte[] key);

	Long gfrem(String key, String member);

	Long gfrem(byte[] key, byte[] member);

	Point<String> gfget(String key, String member);

	Point<byte[]> gfget(byte[] key, byte[] member);

	List<Point<String>> gfmget(String key, String... members);

	List<Point<byte[]>> gfmget(byte[] key, byte[]... members);

	List<Point<String>> gfnn(String key, double lat, double lon, long count);

	List<Point<byte[]>> gfnn(byte[] key, double lat, double lon, long count);

	List<Point<String>> gfrangeByRegion(String key, Polygon<String> polygon);

	List<Point<byte[]>> gfrangeByRegion(byte[] key, Polygon<?> polygon);

	Double distance(double dLat1, double dLon1, double dLat2, double dLon2);
	
	/* Geography */

	Long ggadd(String key, String member, String value, Polygon<String> polygon);
	
	Long ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);
	
	Long ggadd(String key, String member, String value, LineString<String> lineString);
	
	Long ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	List<Geometry<String>> ggrange(String key, long start, long stop);

	List<Geometry<byte[]>> ggrange(byte[] key, long start, long stop);

//	List<Point<String>> (String key, Polygon<String> polygon);
	
//	List<Geometry<T>> ggrange(String key, double lat, double lon, double distance, UNITS unit);
}
