package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
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

	List<Point<String>> gfrangeByRegion(String key, Polygon polygon);

	List<Point<byte[]>> gfrangeByRegion(byte[] key, Polygon polygon);

	Double distance(double dLat1, double dLon1, double dLat2, double dLon2);



	

}
