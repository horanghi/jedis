package redis.clients.jedis;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public interface Command4BinarySpatial {

	void gadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	void gadd(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] member, byte[] value);

	void gupdate(byte[] key, double lat, double lon, byte[] member);

	void gupdate(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] member);

	void grangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit);

	void grangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, RELATION scope, ORDERBY order);

	void grangeByRadius(byte[] key, double lat, double lon, long radius, UNITS unit);

	void gcard(byte[] key);

	void grem(byte[] key, byte[] member);

	void gget(byte[] key, byte[] member);

	void gmget(byte[] key, byte[]... members);

	void gnn(byte[] key, double lat, double lon, long count);

	void grangeByRadiusWithMatch(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern);

	void grangeByRegion(byte[] key, Polygon<?> polygon);
	
	void grangeByRegion(byte[] key, LineString<?> lineString);

	void grangeByRegion(byte[] key, Point<?> point);
	
	void grangeByRegionWithMatch(byte[] key, Polygon<?> polygon, byte[] pattern);
	
	void grangeByRegionWithMatch(byte[] key, LineString<?> lineString, byte[] pattern);

	void grangeByRegionWithMatch(byte[] key, Point<?> point, byte[] pattern);

	void grangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern);

	void grangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern, RELATION scope,
			ORDERBY order);
	
	void grangeBy(byte[] key, byte[] bykey, byte[] bymember);

	void grangeByWithMatch(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern, long count);

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

	void ggrelationBy(byte[] key, byte[] byKey, byte[] byMember);

	void ggnn(byte[] key, double lat, double lon, long count);

	void ggnnWithMatch(byte[] key, double lat, double lon, long count, byte[] pattern);

	void ggupdate(byte[] key, byte[] member, Polygon<?> polygon);

	void ggupdate(byte[] key, byte[] member, Point<?> point);

	void ggupdate(byte[] key, byte[] member, LineString<?> lineString);

	void grangeByRegionWithMatch(byte[] key, Polygon<?> polygon, byte[] pattern, long count);

}
