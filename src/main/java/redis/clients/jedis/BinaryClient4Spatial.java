package redis.clients.jedis;

import static redis.clients.jedis.Protocol.toByteArray;
import static redis.clients.jedis.Protocol.Command.GADD;
import static redis.clients.jedis.Protocol.Command.GCARD;
import static redis.clients.jedis.Protocol.Command.GGADD;
import static redis.clients.jedis.Protocol.Command.GGCARD;
import static redis.clients.jedis.Protocol.Command.GGET;
import static redis.clients.jedis.Protocol.Command.GGGET;
import static redis.clients.jedis.Protocol.Command.GGMGET;
import static redis.clients.jedis.Protocol.Command.GGNN;
import static redis.clients.jedis.Protocol.Command.GGRANGE;
import static redis.clients.jedis.Protocol.Command.GGRELATION;
import static redis.clients.jedis.Protocol.Command.GGRELATIONBY;
import static redis.clients.jedis.Protocol.Command.GGREM;
import static redis.clients.jedis.Protocol.Command.GGREVRANGE;
import static redis.clients.jedis.Protocol.Command.GGUPDATEBY;
import static redis.clients.jedis.Protocol.Command.GMGET;
import static redis.clients.jedis.Protocol.Command.GNN;
import static redis.clients.jedis.Protocol.Command.GRANGEBY;
import static redis.clients.jedis.Protocol.Command.GRANGEBYRADIUS;
import static redis.clients.jedis.Protocol.Command.GRANGEBYREGION;
import static redis.clients.jedis.Protocol.Command.GREM;
import static redis.clients.jedis.Protocol.Command.GUPDATEBY;
import static redis.clients.jedis.Protocol.GeoOptions.BY;
import static redis.clients.jedis.Protocol.GeoOptions.LIMIT;
import static redis.clients.jedis.Protocol.GeoOptions.MATCH;
import static redis.clients.jedis.Protocol.GeoOptions.NR;
import static redis.clients.jedis.Protocol.GeoOptions.RADIUS;
import static redis.clients.jedis.Protocol.GeoOptions.WITHDISTANCE;
import static redis.clients.jedis.Protocol.GeoOptions.WITHGEOJSON;
import static redis.clients.jedis.Protocol.GeoOptions.WITHVALUES;
import static redis.clients.jedis.Protocol.GeoOptions.XR;
import static redis.clients.jedis.Protocol.ORDERBY.ASC;
import static redis.clients.jedis.Protocol.SCOPE.CONTAINS;
import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.SCOPE;
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
	public void gadd(final byte[] key, final double lat, final double lon, final long distance, final UNITS unit, final byte[] member,
			final byte[] value) {
		// GFADD key member value latitude longitude [RADIUS radius m|km]
		sendCommand(GADD, key, member, value, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw);
	}

	@Override
	public void gupdate(final byte[] key, final double lat, final double lon, final byte[] member) {
		// GUPDATEBY key member latitude longitude [RADIUS radius m|km]
		sendCommand(GUPDATEBY, key, member, toByteArray(lat), toByteArray(lon));
	}

	@Override
	public void gupdate(final byte[] key, final double lat, final double lon, final long radius, final UNITS unit, final byte[] member) {
		// GUPDATEBY key member latitude longitude [RADIUS radius m|km]
		sendCommand(GUPDATEBY, key, member, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw);
	}

	@Override
	public void grangeByRadius(final byte[] key, final double lat, final double lon, final long distance, final UNITS unit) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, NR.raw, ASC.raw);
	}

	@Override
	public void grangeCircleByRadius(final byte[] key, final double lat, final double lon, final long distance, final UNITS unit) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, XR.raw, ASC.raw);
	}

	@Override
	public void grangeCircleByRadius(final byte[] key, final double lat, final double lon, final long distance, final UNITS unit,
			final SCOPE scope, final ORDERBY order) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, scope.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, XR.raw, order.raw);
	}

	@Override
	public void grangeByRadiusWithMatch(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] pattern) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, ASC.raw, NR.raw, MATCH.raw, pattern);
	}

	@Override
	public void grangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] pattern) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, ASC.raw, XR.raw, MATCH.raw, pattern);
	}

	@Override
	public void grangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, long distance, UNITS unit, byte[] pattern, SCOPE scope,
			ORDERBY order) {
		// GFRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(distance), unit.raw, scope.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, order.raw, XR.raw, MATCH.raw, pattern);
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
	public void grangeByRegion(byte[] key, LineString<?> lineString) {
		// GFRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GRANGEBYREGION, key, lineString.getJsonByte(), WITHVALUES.raw);
	}

	@Override
	public void grangeByRegion(byte[] key, Point<?> point) {
		// GFRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GRANGEBYREGION, key, point.getJsonByte(), WITHVALUES.raw);
	}

	@Override
	public void grangeByRegionWithMatch(byte[] key, Polygon<?> polygon, byte[] pattern) {
		// GFRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GRANGEBYREGION, key, polygon.getJsonByte(), MATCH.raw, pattern, WITHVALUES.raw);
	}

	@Override
	public void grangeByRegionWithMatch(byte[] key, LineString<?> lineString, byte[] pattern) {
		// GFRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GRANGEBYREGION, key, lineString.getJsonByte(), MATCH.raw, pattern, WITHVALUES.raw);
	}

	@Override
	public void grangeByRegionWithMatch(byte[] key, Point<?> point, byte[] pattern) {
		// GFRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GRANGEBYREGION, key, point.getJsonByte(), MATCH.raw, pattern, WITHVALUES.raw);
	}

	@Override
	public void grangeBy(byte[] key, byte[] bykey, byte[] bymember) {
		// GRANGEBY key BY bykey bymember CONTAINS|WITHIN [MATCH pattern] [NR|XR] [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset
		// count]
		sendCommand(GRANGEBY, key, BY.raw, bykey, bymember, WITHVALUES.raw);
	}

	@Override
	public void grangeByWithMatch(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern, long count) {
		// GRANGEBY key BY bykey bymember CONTAINS|WITHIN [MATCH pattern] [NR|XR] [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset
		// count]
		sendCommand(GRANGEBY, key, BY.raw, bykey, bymember, MATCH.raw, pattern, WITHVALUES.raw, LIMIT.raw, toByteArray(count));
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
	public void ggupdate(byte[] key, byte[] member, Polygon<?> polygon) {
		// GGUPDATEBY key member geojson
		sendCommand(GGUPDATEBY, key, member, polygon.getJsonByte());
	}

	@Override
	public void ggupdate(byte[] key, byte[] member, Point<?> point) {
		// GGUPDATEBY key member geojson
		sendCommand(GGUPDATEBY, key, member, point.getJsonByte());
	}

	@Override
	public void ggupdate(byte[] key, byte[] member, LineString<?> lineString) {
		// GGUPDATEBY key member geojson
		sendCommand(GGUPDATEBY, key, member, lineString.getJsonByte());
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

	@Override
	public void ggrelation(byte[] key, Polygon<?> polygon) {
		// GGRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GGRELATION, key, polygon.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggrelation(byte[] key, LineString<?> lineString) {
		// GGRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GGRELATION, key, lineString.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggrelation(byte[] key, Point<?> point) {
		// GGRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GGRELATION, key, point.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggrelationBy(byte[] key, byte[] byKey, byte[] byMember) {
		// GGRELATIONBY key BY bykey bymember CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		sendCommand(GGRELATIONBY, key, BY.raw, byKey, byMember, CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggnn(final byte[] key, final double lat, final double lon, final long count) {
		// ggnn mygg 0 0 limit 2 withvalues withdistance withgeojson
		sendCommand(GGNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggnnWithMatch(final byte[] key, final double lat, final double lon, final long count, final byte[] pattern) {
		// ggnn mygg 0 0 limit 2 match hello* withvalues withdistance withgeojson
		sendCommand(GGNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), MATCH.raw, pattern, WITHVALUES.raw,
				WITHGEOJSON.raw);
	}
}
