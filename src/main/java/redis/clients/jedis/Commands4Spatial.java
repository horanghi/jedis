package redis.clients.jedis;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Polygon;

public interface Commands4Spatial extends Commands {

	public void gadd(String key, double lat, double lon, String member, String value);

	public void gadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	public void gfadd(String key, double lat, double lon, double distance, UNITS unit, String member, String value);

	public void gfadd(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] member, byte[] value);

	void gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit);

	void gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	void gfrangeByRadiusDetail(String key, double lat, double lon, double distance, UNITS unit);

	void gfrangeByRadiusDetail(byte[] key, double lat, double lon, double distance, UNITS unit);

	void gfcard(String key);

	void gfcard(byte[] key);

	void gfrem(String key, String member);

	void gfrem(byte[] key, byte[] member);

	void gfmget(String key, String[] members);

	void gfmget(byte[] key, byte[]... members);

	void gfnn(String key, double lat, double lon, long count);

	void gfnn(byte[] key, double lat, double lon, long count);

	void gfrangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern);

	void gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern);

	void gfrangeByRegion(String key, Polygon polygon);
	
	void gfrangeByRegion(byte[] key, Polygon polygon);

}
