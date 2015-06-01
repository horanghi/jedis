package redis.clients.jedis;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.SCOPE;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public interface Commands4Spatial extends Commands {

	public void gadd(String key, double lat, double lon, String member, String value);

	public void gadd(String key, double lat, double lon, long distance, UNITS unit, String member, String value);

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
	
	void grangeCircleByRadiusWithMatch(String key, double lat, double lon, long distance, UNITS unit, String pattern);
	
	void grangeCircleByRadiusWithMatch(String key, double lat, double lon, long distance, UNITS unit, String pattern, SCOPE scope,
			ORDERBY order);

	void ggadd(String key, String member, String value, Polygon<String> polygon);
	
	void ggadd(String key, String member, String value, LineString<String> lineString);

	void ggrange(String key, long start, long stop);

	void ggadd(String key, String member, String value, Point<String> point);

	void ggrevrange(String key, long start, long stop);

	void ggcard(String key);

	void ggrem(String key, String member);

	void ggget(String key, String member);

	void ggmget(String key, String[] members);

	void ggrelation(String key, Polygon<String> polygon);

	void ggrelation(String key, LineString<String> lineString);

	void ggrelation(String key, Point<String> point);

	void ggnn(String key, double lat, double lon, long count);

	void ggnnWithMatch(String key, double lat, double lon, long count, String pattern);


}
