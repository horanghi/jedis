package redis.clients.jedis;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public interface Command4BinarySpatial {

	void gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	void gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value);

	void gpupdate(byte[] key, byte[] member, double lat, double lon);

	void gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit);

	void gprangeCircleByRadius(byte[] key, double lat, double lon, double radius, UNITS unit);

	void gprangeCircleByRadius(byte[] key, double lat, double lon, double radius, UNITS unit, RELATION scope, ORDERBY order);

	void gprangeByRadius(byte[] key, double lat, double lon, double radius, UNITS unit);

	void gpcard(byte[] key);

	void gprem(byte[] key, byte[] member);

	void gpget(byte[] key, byte[] member);

	void gpmget(byte[] key, byte[]... members);

	void gpnn(byte[] key, double lat, double lon, long count);
	
	void gpnn(byte[] key, double lat, double lon, long count, byte[] pattern);

	void gprangeByRadiusWithMatch(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] pattern);

	void gprangeByRegion(byte[] key, Polygon<?> polygon);

	void gprangeByRegion(byte[] key, LineString<?> lineString);

	void gprangeByRegion(byte[] key, Point<?> point);

	void gprangeByRegionWithMatch(byte[] key, Polygon<?> polygon, byte[] pattern);

	void gprangeByRegionWithMatch(byte[] key, LineString<?> lineString, byte[] pattern);

	void gprangeByRegionWithMatch(byte[] key, Point<?> point, byte[] pattern);

	void gprangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] pattern);

	void gprangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] pattern, RELATION scope,
			ORDERBY order);

	void gprangeBy(byte[] key, byte[] bykey, byte[] bymember);

	void gprangeByWithMatch(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern, long count);

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

	void gprangeByRegionWithMatch(byte[] key, Polygon<?> polygon, byte[] pattern, long count);

	// Gemotry
	
	void gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy);

	void gmgetBoundary(byte[] key);

	void gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy);
	
	void gmadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);

	void gmadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	void gmrange(byte[] key, long start, long stop);

	void gmadd(byte[] key, byte[] member, byte[] value, Point<?> point);

	void gmrevrange(byte[] key, long start, long stop);

	void gmcard(byte[] key);

	void gmrem(byte[] key, byte[] member);

	void gmget(byte[] key, byte[] member);

	void gmmget(byte[] key, byte[][] members);

	void gmrelation(byte[] key, Polygon<?> polygon);

	void gmrelation(byte[] key, LineString<?> lineString);

	void gmrelation(byte[] key, Point<?> point);

	void gmrelationBy(byte[] key, byte[] byKey, byte[] byMember);

	void gmnn(byte[] key, double x, double y, long count);

	void gmnnWithMatch(byte[] key, double x, double y, long count, byte[] pattern);

	void gmupdate(byte[] key, byte[] member, Polygon<?> polygon);

	void gmupdate(byte[] key, byte[] member, Point<?> point);

	void gmupdate(byte[] key, byte[] member, LineString<?> lineString);

}
