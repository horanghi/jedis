package redis.clients.jedis;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public interface Commands4Spatial extends Commands {

	void gpadd(String key, double lat, double lon, String member, String value);

	void gpadd(String key, double lat, double lon, long radius, UNITS unit, String member, String value);
	
	void gpupdate(String key, double lat, double lon, String member);

	void gpupdate(String key, double lat, double lon, long radius, UNITS unit, String member);

	void gprangeByRadius(String key, double lat, double lon, long radius, UNITS unit);
	
	void gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit);
	
	void gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit, RELATION scope, ORDERBY order);
	
	void gpcard(String key);

	void gprem(String key, String member);

	void gpget(String key, String member);
	
	void gpmget(String key, String[] members);

	void gpnn(String key, double lat, double lon, long count);

	void gprangeByRadiusWithMatch(String key, double lat, double lon, long radius, UNITS unit, String pattern);

	void gprangeByRegion(String key, Polygon<?> polygon);
	
	void gprangeByRegion(String key, LineString<?> lineString);

	void gprangeByRegion(String key, Point<?> point);
	
	void gprangeByRegionWithMatch(String key, Polygon<?> polygon, String pattern);
	
	void gprangeByRegionWithMatch(String key, LineString<?> lineString, String pattern);

	void gprangeByRegionWithMatch(String key, Point<?> point, String pattern);
	
	void gprangeBy(String key, String bykey, String bymember);

	void gprangeByWithMatch(String key, String bykey, String bymember, String pattern, long count);
	
	void gprangeCircleByRadiusWithMatch(String key, double lat, double lon, long radius, UNITS unit, String pattern);
	
	void gprangeCircleByRadiusWithMatch(String key, double lat, double lon, long radius, UNITS unit, String pattern, RELATION scope,
			ORDERBY order);
	
	void gprangeByRegionWithMatch(String key, Polygon<?> polygon, String pattern, long count);

	void ggadd(String key, String member, String value, Polygon<?> polygon);
	
	void ggadd(String key, String member, String value, LineString<?> lineString);
	
	void ggadd(String key, String member, String value, Point<?> point);
	
	void ggupdate(String key, String member, Polygon<?> polygon);

	void ggupdate(String key, String member, LineString<?> lineString);

	void ggupdate(String key, String member, Point<?> point);

	void ggrange(String key, long start, long stop);

	void ggrevrange(String key, long start, long stop);

	void ggcard(String key);

	void ggrem(String key, String member);

	void ggget(String key, String member);

	void ggmget(String key, String[] members);

	void ggrelation(String key, Polygon<?> polygon);

	void ggrelation(String key, LineString<?> lineString);

	void ggrelation(String key, Point<?> point);
	
	void ggrelationBy(String key, String byKey, String byMember);

	void ggnn(String key, double lat, double lon, long count);

	void ggnnWithMatch(String key, double lat, double lon, long count, String pattern);

	

}
