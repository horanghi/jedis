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
 * @author horanghi
 */
public interface Pipeline4Geo {

	Response<Long> gpadd(String key, double lat, double lon, String member, String value);

	Response<Long> gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	Response<Long> gpadd(String key, double lat, double lon, long distance, UNITS unit, String member, String value);

	Response<Long> gpadd(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] member, byte[] value);

	Response<Long> gpupdate(String key, double lat, double lon, String member);
	
	Response<Long> gpupdate(byte[] key, double lat, double lon, byte[] member);

	Response<Long> gpupdate(String key, double lat, double lon, long distance, UNITS unit, String member);
	
	Response<Long> gpupdate(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] member);
	
	Response<List<Point<String>>> gprangeBy(String key, String bykey, String bymember);

	Response<List<Point<byte[]>>> gprangeBy(byte[] key, byte[] bykey, byte[] bymember);

	Response<List<Point<String>>> gprangeBy(String key, String bykey, String bymember, String pattern, long count);

	Response<List<Point<byte[]>>> gprangeBy(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern, long count);
	
	Response<List<Point<String>>> gprangeByRadius(String key, double lat, double lon, long distance, UNITS unit);

	Response<List<Point<byte[]>>> gprangeByRadius(byte[] key, double lat, double lon, long distance, UNITS unit);

	Response<List<Circle<String>>> gprangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit);

	Response<List<Circle<byte[]>>> gprangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit);

	Response<List<Circle<String>>> gprangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit, RELATION scope,
			ORDERBY order);

	Response<List<Circle<byte[]>>> gprangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit, RELATION scope,
			ORDERBY order);

	Response<List<Point<String>>> gprangeByRadius(String key, double lat, double lon, long distance, UNITS unit, String pattern);

	Response<List<Point<byte[]>>> gprangeByRadius(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] pattern);

	Response<List<Circle<String>>> gprangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit,
			String pattern);

	Response<List<Circle<byte[]>>> gprangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit,
			byte[] pattern);

	Response<List<Circle<String>>> gprangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit,
			String pattern, RELATION scope, ORDERBY order);

	Response<List<Circle<byte[]>>> gprangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit,
			byte[] pattern, RELATION scope, ORDERBY order);

	Response<Long> gpcard(String key);

	Response<Long> gpcard(byte[] key);

	Response<Long> gprem(String key, String member);

	Response<Long> gprem(byte[] key, byte[] member);

	Response<Point<String>> gpget(String key, String member);

	Response<Point<byte[]>> gpget(byte[] key, byte[] member);

	Response<List<Point<String>>> gpmget(String key, String... members);

	Response<List<Point<byte[]>>> gpmget(byte[] key, byte[]... members);

	Response<List<Point<String>>> gpnn(String key, double lat, double lon, long count);

	Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long count);

	Response<List<Point<String>>> gprangeByRegion(String key, Polygon<?> polygon);

	Response<List<Point<byte[]>>> gprangeByRegion(byte[] key, Polygon<?> polygon);

	Response<Long> ggadd(String key, String member, String value, Polygon<?> polygon);

	Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);

	Response<Long> ggadd(String key, String member, String value, LineString<?> lineString);

	Response<Long> ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	Response<List<Geometry<String>>> ggrange(String key, long start, long stop);

	Response<List<Geometry<byte[]>>> ggrange(byte[] key, long start, long stop);

	Response<Long> ggadd(String key, String member, String value, Point<?> point);

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

	Response<List<Geometry<String>>> ggrelation(String key, Polygon<?> polygon);

	Response<List<Geometry<byte[]>>> ggrelation(byte[] key, Polygon<?> polygon);

	Response<List<Geometry<String>>> ggrelation(String key, LineString<?> lineString);

	Response<List<Geometry<byte[]>>> ggrelation(byte[] key, LineString<?> lineString);

	Response<List<Geometry<String>>> ggrelation(String key, Point<?> point);

	Response<List<Geometry<byte[]>>> ggrelation(byte[] key, Point<?> point);
	
	Response<List<Geometry<String>>> ggrelationBy(String key, String byKey, String byMember);

	Response<List<Geometry<byte[]>>> ggrelationBy(byte[] key, byte[] byKey, byte[] byMember);

	Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count);

	Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count);

	Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count, String pattern);

	Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count, byte[] pattern);

}
