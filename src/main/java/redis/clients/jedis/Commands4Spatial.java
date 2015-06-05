package redis.clients.jedis;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.SCOPE;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public interface Commands4Spatial extends Commands {

	void gadd(String key, double lat, double lon, String member, String value);

	void gadd(String key, double lat, double lon, long distance, UNITS unit, String member, String value);
	
	void gupdate(String key, double lat, double lon, String member);

	void gupdate(String key, double lat, double lon, long distance, UNITS unit, String member);

	void grangeByRadius(String key, double lat, double lon, long distance, UNITS unit);
	
	void grangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit);
	
	void grangeCircleByRadius(String key, double lat, double lon, long distance, UNITS unit, SCOPE scope, ORDERBY order);
	
	void gcard(String key);

	void grem(String key, String member);

	void gget(String key, String member);
	
	void gmget(String key, String[] members);

	void gnn(String key, double lat, double lon, long count);

	void grangeByRadiusWithMatch(String key, double lat, double lon, long distance, UNITS unit, String pattern);

	void grangeByRegion(String key, Polygon<String> polygon);
	
	void grangeByRegion(String key, LineString<String> lineString);

	void grangeByRegion(String key, Point<String> point);
	
	void grangeByRegionWithMatch(String key, Polygon<String> polygon, String pattern);
	
	void grangeByRegionWithMatch(String key, LineString<String> lineString, String pattern);

	void grangeByRegionWithMatch(String key, Point<String> point, String pattern);
	
	void grangeBy(String key, String bykey, String bymember);

	void grangeByWithMatch(String key, String bykey, String bymember, String pattern, long count);
	
	void grangeCircleByRadiusWithMatch(String key, double lat, double lon, long distance, UNITS unit, String pattern);
	
	void grangeCircleByRadiusWithMatch(String key, double lat, double lon, long distance, UNITS unit, String pattern, SCOPE scope,
			ORDERBY order);

	void ggadd(String key, String member, String value, Polygon<String> polygon);
	
	void ggadd(String key, String member, String value, LineString<String> lineString);
	
	void ggadd(String key, String member, String value, Point<String> point);
	
	void ggupdate(String key, String member, Polygon<String> polygon);

	void ggupdate(String key, String member, LineString<String> lineString);

	void ggupdate(String key, String member, Point<String> point);

	void ggrange(String key, long start, long stop);

	void ggrevrange(String key, long start, long stop);

	void ggcard(String key);

	void ggrem(String key, String member);

	void ggget(String key, String member);

	void ggmget(String key, String[] members);

	void ggrelation(String key, Polygon<String> polygon);

	void ggrelation(String key, LineString<String> lineString);

	void ggrelation(String key, Point<String> point);
	
	void ggrelationBy(String key, String byKey, String byMember);

	void ggnn(String key, double lat, double lon, long count);

	void ggnnWithMatch(String key, double lat, double lon, long count, String pattern);

}
