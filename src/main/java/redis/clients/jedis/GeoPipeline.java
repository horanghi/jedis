package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

/**
 * @author horanghi
 */
public interface GeoPipeline {

	Response<Long> gadd(String key, double lat, double lon, String member, String value);

	Response<Long> gadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	Response<Long> gfadd(String key, double lat, double lon, double distance, UNITS unit, String member, String value);

	Response<Long> gfadd(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] member, byte[] value);

	Response<List<Point>> gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit);

	Response<List<Point>> gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	Response<List<Circle>> gfrangeCircleByRadius(String key, double lat, double lon, double distance, UNITS unit);

	Response<List<Circle>> gfrangeCircleByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	Response<List<Point>> gfrangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	Response<List<Point>> gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	Response<List<Point>> gfrangeCircleByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	Response<List<Point>> gfrangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	Response<Long> gfcard(String key);

	Response<Long> gfcard(byte[] key);

	Response<Long> gfrem(String key, String member);

	Response<Long> gfrem(byte[] key, byte[] member);

	Response<Point> gfget(String key, String member);

	Response<Point> gfget(byte[] key, byte[] member);

	Response<List<Point>> gfmget(String key, String... members);

	Response<List<Point>> gfmget(byte[] key, byte[]... members);

	Response<List<Point>> gfnn(String key, double lat, double lon, long count);

	Response<List<Point>> gfnn(byte[] key, double lat, double lon, long count);

	Response<List<Point>> gfrangeByRegion(String key, Polygon polygon);

	Response<List<Point>> gfrangeByRegion(byte[] key, Polygon polygon);
}
