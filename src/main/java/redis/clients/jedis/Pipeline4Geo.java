package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

/**
 * @author horanghi
 */
public interface Pipeline4Geo {

	Response<Long> gadd(String key, double lat, double lon, String member, String value);

	Response<Long> gadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	Response<Long> gfadd(String key, double lat, double lon, double distance, UNITS unit, String member, String value);

	Response<Long> gfadd(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] member, byte[] value);

	Response<List<Point<String>>> gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit);

	Response<List<Point<byte[]>>> gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	Response<List<Circle<String>>> gfrangeCircleByRadius(String key, double lat, double lon, double distance, UNITS unit);

	Response<List<Circle<byte[]>>> gfrangeCircleByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	Response<List<Point<String>>> gfrangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	Response<List<Point<byte[]>>> gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	Response<List<Circle<String>>> gfrangeCircleByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	Response<List<Circle<byte[]>>> gfrangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	Response<Long> gfcard(String key);

	Response<Long> gfcard(byte[] key);

	Response<Long> gfrem(String key, String member);

	Response<Long> gfrem(byte[] key, byte[] member);

	Response<Point<String>> gfget(String key, String member);

	Response<Point<byte[]>> gfget(byte[] key, byte[] member);

	Response<List<Point<String>>> gfmget(String key, String... members);

	Response<List<Point<byte[]>>> gfmget(byte[] key, byte[]... members);

	Response<List<Point<String>>> gfnn(String key, double lat, double lon, long count);

	Response<List<Point<byte[]>>> gfnn(byte[] key, double lat, double lon, long count);

	Response<List<Point<String>>> gfrangeByRegion(String key, Polygon<String> polygon);

	Response<List<Point<byte[]>>> gfrangeByRegion(byte[] key, Polygon<byte[]> polygon);

	Response<Long> ggadd(String key, String member, String value, Polygon<String> polygon);

	Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);

	Response<Long> ggadd(String key, String member, String value, LineString<String> lineString);

	Response<Long> ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	Response<List<Geometry<String>>> ggrange(String key, long start, long stop);

	Response<List<Geometry<byte[]>>> ggrange(byte[] key, long start, long stop);

	Response<Long> ggadd(String key, String member, String value, Point<String> point);

	Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Point<?> point);

	Response<List<Geometry<String>>> ggrevrange(String key, long start, long stop);

	Response<List<Geometry<byte[]>>> ggrevrange(byte[] key, long start, long stop);

	Response<Long> ggcard(String key);

	Response<Long> ggcard(byte[] key);

	Response<Long> ggrem(String key, String member);

	Response<Long> ggrem(byte[] key, byte[] member);

	Response<Geometry<String>> ggget(String key, String member);

	Response<Geometry<byte[]>> ggget(byte[] key, byte[] member);

	Response<List<Geometry<String>>> ggmget(String key, String[] members);

	Response<List<Geometry<byte[]>>> ggmget(byte[] key, byte[][] members);
}
