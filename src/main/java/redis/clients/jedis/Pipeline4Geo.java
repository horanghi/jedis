package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.SCOPE;
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

	Response<Long> gadd(String key, double lat, double lon, long distance, UNITS unit, String member, String value);

	Response<Long> gadd(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] member, byte[] value);

	Response<Long> gupdate(String key, double lat, double lon, String member);
	
	Response<Long> gupdate(byte[] key, double lat, double lon, byte[] member);

	Response<Long> gupdate(String key, double lat, double lon, long distance, UNITS unit, String member);
	
	Response<Long> gupdate(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] member);
	
	Response<List<Point<String>>> grangeBy(String key, String bykey, String bymember);

	Response<List<Point<byte[]>>> grangeBy(byte[] key, byte[] bykey, byte[] bymember);

	Response<List<Point<String>>> grangeBy(String key, String bykey, String bymember, String pattern, long count);

	Response<List<Point<byte[]>>> grangeBy(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern, long count);
	
	Response<List<Point<String>>> grangeByRadius(String key, double lat, double lon, long distance, UNITS unit);

	Response<List<Point<byte[]>>> grangeByRadius(byte[] key, double lat, double lon, long distance, UNITS unit);

	Response<List<Circle<String>>> grangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit);

	Response<List<Circle<byte[]>>> grangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit);

	Response<List<Circle<String>>> grangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit, SCOPE scope,
			ORDERBY order);

	Response<List<Circle<byte[]>>> grangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit, SCOPE scope,
			ORDERBY order);

	Response<List<Point<String>>> grangeByRadius(String key, double lat, double lon, long distance, UNITS unit, String pattern);

	Response<List<Point<byte[]>>> grangeByRadius(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] pattern);

	Response<List<Circle<String>>> grangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit,
			String pattern);

	Response<List<Circle<byte[]>>> grangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit,
			byte[] pattern);

	Response<List<Circle<String>>> grangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit,
			String pattern, SCOPE scope, ORDERBY order);

	Response<List<Circle<byte[]>>> grangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit,
			byte[] pattern, SCOPE scope, ORDERBY order);

	Response<Long> gcard(String key);

	Response<Long> gcard(byte[] key);

	Response<Long> grem(String key, String member);

	Response<Long> grem(byte[] key, byte[] member);

	Response<Point<String>> gget(String key, String member);

	Response<Point<byte[]>> gget(byte[] key, byte[] member);

	Response<List<Point<String>>> gmget(String key, String... members);

	Response<List<Point<byte[]>>> gmget(byte[] key, byte[]... members);

	Response<List<Point<String>>> gnn(String key, double lat, double lon, long count);

	Response<List<Point<byte[]>>> gnn(byte[] key, double lat, double lon, long count);

	Response<List<Point<String>>> grangeByRegion(String key, Polygon<String> polygon);

	Response<List<Point<byte[]>>> grangeByRegion(byte[] key, Polygon<byte[]> polygon);

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

	Response<List<Geometry<String>>> ggrelation(String key, Polygon<String> polygon);

	Response<List<Geometry<byte[]>>> ggrelation(byte[] key, Polygon<byte[]> polygon);

	Response<List<Geometry<String>>> ggrelation(String key, LineString<String> lineString);

	Response<List<Geometry<byte[]>>> ggrelation(byte[] key, LineString<byte[]> lineString);

	Response<List<Geometry<String>>> ggrelation(String key, Point<String> point);

	Response<List<Geometry<byte[]>>> ggrelation(byte[] key, Point<byte[]> point);
	
	Response<List<Geometry<String>>> ggrelationBy(String key, String byKey, String byMember);

	Response<List<Geometry<byte[]>>> ggrelationBy(byte[] key, byte[] byKey, byte[] byMember);

	Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count);

	Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count);

	Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count, String pattern);

	Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count, byte[] pattern);

}
