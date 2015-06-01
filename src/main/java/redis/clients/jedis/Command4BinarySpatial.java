package redis.clients.jedis;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.SCOPE;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public interface Command4BinarySpatial {

	public void gadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	public void gadd(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] member, byte[] value);

	void grangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit);
	
	void grangeCircleByRadius(byte[] key, double lat, double lon, long distance, UNITS unit, SCOPE scope, ORDERBY order);

	void grangeByRadius(byte[] key, double lat, double lon, long distance, UNITS unit);
	
	void gcard(byte[] key);

	void grem(byte[] key, byte[] member);

	void gget(byte[] key, byte[] member);

	void gmget(byte[] key, byte[]... members);

	void gnn(byte[] key, double lat, double lon, long count);

	void grangeByRadiusWithMatch(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] pattern);

	void grangeByRegion(byte[] key, Polygon<?> polygon);

	void grangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] pattern);

	void grangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] pattern, SCOPE scope,
			ORDERBY order);

	void ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);

	void ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	void ggrange(byte[] key, long start, long stop);

	void ggadd(byte[] key, byte[] member, byte[] value, Point<?> point);

	void ggrevrange(byte[] key, long start, long stop);

	void ggcard(byte[] key);

	void ggrem(byte[] key, byte[] member);

	void ggget(byte[] key, byte[] member);

	void ggmget(byte[] key, byte[][] members);

	void ggrelation(byte[] key, Polygon<?> polygon);

	void ggrelation(byte[] key, LineString<?> lineString);

	void ggrelation(byte[] key, Point<?> point);

	void ggnn(byte[] key, double lat, double lon, long count);

	void ggnnWithMatch(byte[] key, double lat, double lon, long count, byte[] pattern);

	

}
