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

	Long gadd(String key, double lat, double lon, double distance, UNITS unit, String member, String value);

	Long gadd(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] member, byte[] value);

	List<Point<String>> grangeByRadius(String key, double lat, double lon, double distance, UNITS unit);

	List<Point<byte[]>> grangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	List<Circle<String>> grangeCircleByRadius(String key, double lat, double lon, double distance, UNITS unit);

	List<Circle<byte[]>> grangeCircleByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	List<Point<String>> grangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	List<Point<byte[]>> grangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	List<Circle<String>> grangeCircleByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	List<Circle<byte[]>> grangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	Long gcard(String key);

	Long gcard(byte[] key);

	Long grem(String key, String member);

	Long grem(byte[] key, byte[] member);

	Point<String> gget(String key, String member);

	Point<byte[]> gget(byte[] key, byte[] member);

	List<Point<String>> gmget(String key, String... members);

	List<Point<byte[]>> gmget(byte[] key, byte[]... members);

	List<Point<String>> gnn(String key, double lat, double lon, long count);

	List<Point<byte[]>> gnn(byte[] key, double lat, double lon, long count);

	List<Point<String>> grangeByRegion(String key, Polygon<String> polygon);

	List<Point<byte[]>> grangeByRegion(byte[] key, Polygon<?> polygon);

	Double distance(double dLat1, double dLon1, double dLat2, double dLon2);
	
	/* Geography */

	Long ggadd(String key, String member, String value, Polygon<String> polygon);
	
	Long ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);
	
	Long ggadd(String key, String member, String value, LineString<String> lineString);
	
	Long ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	List<Geometry<String>> ggrange(String key, long start, long stop);

	List<Geometry<byte[]>> ggrange(byte[] key, long start, long stop);

	Long ggadd(String key, String member, String value, Point<String> point);

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

	List<Geometry<String>> ggrelation(String key, Polygon<String> polygon);

	List<Geometry<byte[]>> ggrelation(byte[] key, Polygon<byte[]> polygon);

	List<Geometry<String>> ggrelation(String key, LineString<String> lineString);

	List<Geometry<byte[]>> ggrelation(byte[] key, LineString<byte[]> lineString);

	List<Geometry<String>> ggrelation(String key, Point<String> point);

	List<Geometry<byte[]>> ggrelation(byte[] key, Point<byte[]> point);

	List<Geometry<String>> ggnn(String key, double lat, double lon, long count);

	List<Geometry<byte[]>> ggnn(byte[] key, double lat, double lon, long count);

	List<Geometry<String>> ggnnWithMatch(String key, double lat, double lon, long count, String pattern);

	List<Geometry<byte[]>> ggnnWithMatch(byte[] key, double lat, double lon, long count, byte[] pattern);

}
