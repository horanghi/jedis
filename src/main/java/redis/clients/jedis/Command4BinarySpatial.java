package redis.clients.jedis;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public interface Command4BinarySpatial {

	public void gadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	public void gfadd(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] member, byte[] value);

	void gfrangeCircleByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);
	
	void gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	void gfcard(byte[] key);

	void gfrem(byte[] key, byte[] member);
	
	void gfget(byte[] key, byte[] member);

	void gfmget(byte[] key, byte[]... members);

	void gfnn(byte[] key, double lat, double lon, long count);

	void gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	void gfrangeByRegion(byte[] key, Polygon<?> polygon);

	void gfrangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	void ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);
	
	void ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);
	
	void ggrange(byte[] key, long start, long stop);

	void ggadd(byte[] key, byte[] member, byte[] value, Point<?> point);

	void ggrevrange(byte[] key, long start, long stop);

	void ggcard(byte[] key);

	void ggrem(byte[] key, byte[] member);

	void ggget(byte[] key, byte[] member);

	void ggmget(byte[] key, byte[][] members);

	

	

}
