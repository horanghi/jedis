package redis.clients.jedis;

import static redis.clients.jedis.Protocol.toByteArray;
import static redis.clients.jedis.Protocol.Command.GFADD;
import static redis.clients.jedis.Protocol.Command.GFCARD;
import static redis.clients.jedis.Protocol.Command.GFMGET;
import static redis.clients.jedis.Protocol.Command.GFNN;
import static redis.clients.jedis.Protocol.Command.GFRANGEBYRADIUS;
import static redis.clients.jedis.Protocol.Command.GFRANGEBYREGION;
import static redis.clients.jedis.Protocol.Command.GFREM;
import static redis.clients.jedis.Protocol.GeoOptions.ASC;
import static redis.clients.jedis.Protocol.GeoOptions.CONTAINS;
import static redis.clients.jedis.Protocol.GeoOptions.LIMIT;
import static redis.clients.jedis.Protocol.GeoOptions.MATCH;
import static redis.clients.jedis.Protocol.GeoOptions.RADIUS;
import static redis.clients.jedis.Protocol.GeoOptions.WITHDISTANCE;
import static redis.clients.jedis.Protocol.GeoOptions.WITHVALUES;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Polygon;

public class BinaryClient4Spatial extends BinaryClient {

	public BinaryClient4Spatial(String host) {
		super(host);
	}

	public BinaryClient4Spatial(final String host, final int port) {
		super(host, port);
	}

	public void gadd(byte[] key, double latitude, double longitude, byte[] member, byte[] value) {
		gfadd(key, latitude, longitude, 0, UNITS.M, member, value);
	}

	public void gfadd(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit, final byte[] member,
			final byte[] value) {
		// GFADD key member value latitude longitude [RADIUS radius m|km]
		sendCommand(GFADD, key, member, value, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw);
	}

	public void gfrangeByRadius(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GFRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw, ASC.raw);
	}

	public void gfrangeByRadiusDetail(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GFRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, ASC.raw);
	}
	
	public void gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GFRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw,  ASC.raw, MATCH.raw, pattern);
	}

	public void gfcard(final byte[] key) {
		// GFCARD key
		sendCommand(GFCARD, key);
	}

	public void gfrem(final byte[] key, final byte[] member) {
		sendCommand(GFREM, key, member);
	}

	public void gfmget(final byte[] key, final byte[]... members) {
		//GFGET key member / GFMGET key member [member ...]
		int len = members.length;
		byte[][] bargs = new byte[len + 1][];
		bargs[0] = key;
		for (int i = 0; i < len; ++i) {
			bargs[i + 1] = members[i];
		}
		sendCommand(GFMGET, bargs);
	}

	public void gfnn(final byte[] key, final double lat, final double lon, final long count) {
		//GFNN key latitude longitude LIMIT count [MATCH pattern] [NR|XR] [WITHVALUES] [WITHDISTANCE]
		sendCommand(GFNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), WITHVALUES.raw, WITHDISTANCE.raw);
	}
	
	public void gfrangeByRegion(byte[] key, Polygon polygon) {
		//GFRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR] 
		//[WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GFRANGEBYREGION, key, polygon.getJsonbyte(), WITHVALUES.raw);
	}

}
