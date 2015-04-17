package redis.clients.jedis;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public interface Commands4Spatial extends Commands {

	public void gadd(String key, double lat, double lon, String member, String value);

	public void gfadd(String key, double lat, double lon, double distance, UNITS unit, String member, String value);

	void gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit);

	void gfrangeCircleByRadius(String key, double lat, double lon, double distance, UNITS unit);

	void gfcard(String key);

	void gfrem(String key, String member);

	void gfget(String key, String member);
	
	void gfmget(String key, String[] members);

	void gfnn(String key, double lat, double lon, long count);

	void gfrangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	void gfrangeByRegion(String key, Polygon<String> polygon);
	
	void gfrangeCircleByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	void ggadd(String key, String member, String value, Polygon<String> polygon);
	
	void ggadd(String key, String member, String value, LineString<String> lineString);

	void ggrange(String key, long start, long stop);

	void ggadd(String key, String member, String value, Point<String> point);

	void ggrevrange(String key, long start, long stop);

	void ggcard(String key);

	void ggrem(String key, String member);

	void ggget(String key, String member);

	void ggmget(String key, String[] members);


}
