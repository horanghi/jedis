package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
/**
 * Common interface for sharded and non-sharded Jedis
 */
public interface GeoCommands {

	Long gadd(String key, double lat, double lon, String member, String value);

	Long gadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	Long gfadd(String key, double lat, double lon, double distance, UNITS unit, String member, String value);

	Long gfadd(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] member, byte[] value);
	
	List<String> gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit);

	List<byte[]> gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit);

	List<Circle> gfrangeByRadiusDetail(String key, double lat, double lon, double distance, UNITS unit);

	List<Circle> gfrangeByRadiusDetail(byte[] key, double lat, double lon, double distance, UNITS unit);

	long gfcard(String key);

	long gfcard(byte[] key);

	long gfrem(String key, String member);

	long gfrem(byte[] key, byte[] member);

	Circle gfget(String key, String member);

	Circle gfget(byte[] key, byte[] member);

	List<Circle> gfmget(String key, String... members);
	
	List<Circle> gfmget(byte[] key, byte[]... members);

	List<Circle> gfnn(String key, double lat, double lon, long count);

	List<Circle> gfnn(byte[] key, double lat, double lon, long count);




}
