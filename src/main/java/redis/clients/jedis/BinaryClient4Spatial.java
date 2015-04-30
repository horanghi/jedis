package redis.clients.jedis;

import static redis.clients.jedis.Protocol.toByteArray;
import static redis.clients.jedis.Protocol.Command.GADD;
import static redis.clients.jedis.Protocol.Command.GCARD;
import static redis.clients.jedis.Protocol.Command.GGET;
import static redis.clients.jedis.Protocol.Command.GMGET;
import static redis.clients.jedis.Protocol.Command.GNN;
import static redis.clients.jedis.Protocol.Command.GRANGEBYRADIUS;
import static redis.clients.jedis.Protocol.Command.GRANGEBYREGION;
import static redis.clients.jedis.Protocol.Command.GREM;
import static redis.clients.jedis.Protocol.Command.GGADD;
import static redis.clients.jedis.Protocol.Command.GGCARD;
import static redis.clients.jedis.Protocol.Command.GGGET;
import static redis.clients.jedis.Protocol.Command.GGMGET;
import static redis.clients.jedis.Protocol.Command.GGRANGE;
import static redis.clients.jedis.Protocol.Command.GGREM;
import static redis.clients.jedis.Protocol.Command.GGREVRANGE;
import static redis.clients.jedis.Protocol.GeoOptions.ASC;
import static redis.clients.jedis.Protocol.GeoOptions.CONTAINS;
import static redis.clients.jedis.Protocol.GeoOptions.LIMIT;
import static redis.clients.jedis.Protocol.GeoOptions.MATCH;
import static redis.clients.jedis.Protocol.GeoOptions.NR;
import static redis.clients.jedis.Protocol.GeoOptions.RADIUS;
import static redis.clients.jedis.Protocol.GeoOptions.WITHDISTANCE;
import static redis.clients.jedis.Protocol.GeoOptions.WITHVALUES;
import static redis.clients.jedis.Protocol.GeoOptions.XR;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public class BinaryClient4Spatial extends BinaryClient implements Command4BinarySpatial {

	public BinaryClient4Spatial(String host) {
		super(host);
	}

	public BinaryClient4Spatial(final String host, final int port) {
		super(host, port);
	}

	@Override
	public void gadd(byte[] key, double latitude, double longitude, byte[] member, byte[] value) {
		gadd(key, latitude, longitude, 0, UNITS.M, member, value);
	}

	@Override
	public void gadd(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit, final byte[] member,
			final byte[] value) {
		// GFADD key member value latitude longitude [RADIUS radius m|km]
		sendCommand(GADD, key, member, value, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw);
	}

	@Override
	public void gfrangeByRadius(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, NR.raw, ASC.raw);
	}

	@Override
	public void grangeCircleByRadius(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, XR.raw, ASC.raw);
	}

	@Override
	public void grangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, ASC.raw, NR.raw, MATCH.raw, pattern);
	}

	@Override
	public void grangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, ASC.raw, XR.raw, MATCH.raw, pattern);
	}

	public void gcard(final byte[] key) {
		// GFCARD key
		sendCommand(GCARD, key);
	}

	@Override
	public void grem(final byte[] key, final byte[] member) {
		sendCommand(GREM, key, member);
	}

	@Override
	public void gget(byte[] key, byte[] member) {
		// GFGET key member / GFMGET key member [member ...]
		sendCommand(GGET, key, member);

	}

	@Override
	public void gmget(final byte[] key, final byte[]... members) {
		// GFGET key member / GFMGET key member [member ...]
		int len = members.length;
		byte[][] bargs = new byte[len + 1][];
		bargs[0] = key;
		for (int i = 0; i < len; ++i) {
			bargs[i + 1] = members[i];
		}
		sendCommand(GMGET, bargs);
	}

	@Override
	public void gnn(final byte[] key, final double lat, final double lon, final long count) {
		// GFNN key latitude longitude LIMIT count [MATCH pattern] [NR|XR] [WITHVALUES] [WITHDISTANCE]
		sendCommand(GNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), NR.raw, WITHVALUES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void grangeByRegion(byte[] key, Polygon<?> polygon) {
		// GFRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GRANGEBYREGION, key, polygon.getJsonByte(), WITHVALUES.raw);
	}

	@Override
	public void ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon) {
		// GGADD key member value geojson
		sendCommand(GGADD, key, member, value, polygon.getJsonByte());
	}

	@Override
	public void ggadd(byte[] key, byte[] member, byte[] value, Point<?> point) {
		// GGADD key member value geojson
		sendCommand(GGADD, key, member, value, point.getJsonByte());
	}

	@Override
	public void ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString) {
		// GGADD key member value geojson
		sendCommand(GGADD, key, member, value, lineString.getJsonByte());
	}

	@Override
	public void ggrange(byte[] key, long start, long stop) {
		// GGADD key member value geojson
		sendCommand(GGRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw);
	}

	@Override
	public void ggrevrange(byte[] key, long start, long stop) {
		// GGADD key member value geojson
		sendCommand(GGREVRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw);
	}

	@Override
	public void ggcard(byte[] key) {
		sendCommand(GGCARD, key);
	}

	@Override
	public void ggrem(byte[] key, byte[] member) {
		sendCommand(GGREM, key, member);
	}

	@Override
	public void ggget(byte[] key, byte[] member) {
		// GGGET key member / GGMGET key member [member ...]
		sendCommand(GGGET, key, member);

	}

	@Override
	public void ggmget(final byte[] key, final byte[][] members) {
		// GGGET key member / GGMGET key member [member ...]
		int len = members.length;
		byte[][] bargs = new byte[len + 1][];
		bargs[0] = key;
		for (int i = 0; i < len; ++i) {
			bargs[i + 1] = members[i];
		}
		sendCommand(GGMGET, bargs);
	}
}
