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
	
	List<Point> gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit);

	List<Point> gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	List<Circle> gfrangeCircleByRadius(String key, double lat, double lon, double distance, UNITS unit);

	List<Circle> gfrangeCircleByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	List<Point> gfrangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	List<Point> gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);
	
	List<Point> gfrangeCircleByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	List<Point> gfrangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);
	
	Long gfcard(String key);

	Long gfcard(byte[] key);

	Long gfrem(String key, String member);

	Long gfrem(byte[] key, byte[] member);

	Point gfget(String key, String member);

	Point gfget(byte[] key, byte[] member);

	List<Point> gfmget(String key, String... members);
	
	List<Point> gfmget(byte[] key, byte[]... members);

	List<Point> gfnn(String key, double lat, double lon, long count);

	List<Point> gfnn(byte[] key, double lat, double lon, long count);

	List<Point> gfrangeByRegion(String key, Polygon polygon);

	List<Point> gfrangeByRegion(byte[] key, Polygon polygon);

	Double distance(double dLat1, double dLon1, double dLat2, double dLon2);



	

}
