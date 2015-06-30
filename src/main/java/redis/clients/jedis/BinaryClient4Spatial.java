package redis.clients.jedis;

import static redis.clients.jedis.Protocol.toByteArray;
import static redis.clients.jedis.Protocol.Command.GGADD;
import static redis.clients.jedis.Protocol.Command.GGCARD;
import static redis.clients.jedis.Protocol.Command.GGGET;
import static redis.clients.jedis.Protocol.Command.GGMGET;
import static redis.clients.jedis.Protocol.Command.GGNN;
import static redis.clients.jedis.Protocol.Command.GGRANGE;
import static redis.clients.jedis.Protocol.Command.GGRELATION;
import static redis.clients.jedis.Protocol.Command.GGRELATIONBY;
import static redis.clients.jedis.Protocol.Command.GGREM;
import static redis.clients.jedis.Protocol.Command.GGREVRANGE;
import static redis.clients.jedis.Protocol.Command.GGUPDATEBY;
import static redis.clients.jedis.Protocol.Command.GMADD;
import static redis.clients.jedis.Protocol.Command.GMCARD;
import static redis.clients.jedis.Protocol.Command.GMGET;
import static redis.clients.jedis.Protocol.Command.GMGETBOUNDARY;
import static redis.clients.jedis.Protocol.Command.GMMGET;
import static redis.clients.jedis.Protocol.Command.GMNN;
import static redis.clients.jedis.Protocol.Command.GMRANGE;
import static redis.clients.jedis.Protocol.Command.GMREBUILDBOUNDARY;
import static redis.clients.jedis.Protocol.Command.GMRELATION;
import static redis.clients.jedis.Protocol.Command.GMRELATIONBY;
import static redis.clients.jedis.Protocol.Command.GMREM;
import static redis.clients.jedis.Protocol.Command.GMREVRANGE;
import static redis.clients.jedis.Protocol.Command.GMSETBOUNDARY;
import static redis.clients.jedis.Protocol.Command.GMUPDATEBY;
import static redis.clients.jedis.Protocol.Command.GPADD;
import static redis.clients.jedis.Protocol.Command.GPCARD;
import static redis.clients.jedis.Protocol.Command.GPGET;
import static redis.clients.jedis.Protocol.Command.GPMGET;
import static redis.clients.jedis.Protocol.Command.GPNN;
import static redis.clients.jedis.Protocol.Command.GPRANGEBY;
import static redis.clients.jedis.Protocol.Command.GPRANGEBYRADIUS;
import static redis.clients.jedis.Protocol.Command.GPRANGEBYREGION;
import static redis.clients.jedis.Protocol.Command.GPREM;
import static redis.clients.jedis.Protocol.Command.GPUPDATEBY;
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
import static redis.clients.jedis.Protocol.RELATION.CONTAINS;
import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
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
	public void gpadd(byte[] key, double latitude, double longitude, byte[] member, byte[] value) {
		gpadd(key, latitude, longitude, 0, UNITS.M, member, value);
	}

	@Override
	public void gpadd(final byte[] key, final double lat, final double lon, final long radius, final UNITS unit, final byte[] member,
			final byte[] value) {
		// GPADD key member value latitude longitude [RADIUS radius m|km]
		sendCommand(GPADD, key, member, value, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw);
	}

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final double lat, final double lon) {
		// GUPDATEBY key member latitude longitude [RADIUS radius m|km]
		sendCommand(GPUPDATEBY, key, member, toByteArray(lat), toByteArray(lon));
	}

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final double lat, final double lon, final long radius, final UNITS unit) {
		// GUPDATEBY key member latitude longitude [RADIUS radius m|km]
		sendCommand(GPUPDATEBY, key, member, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw);
	}

	@Override
	public void gprangeByRadius(final byte[] key, final double lat, final double lon, final long radius, final UNITS unit) {
		// GPRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GPRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, NR.raw, ASC.raw);
	}

	@Override
	public void gprangeCircleByRadius(final byte[] key, final double lat, final double lon, final long radius, final UNITS unit) {
		// GPRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GPRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, XR.raw, ASC.raw);
	}

	@Override
	public void gprangeCircleByRadius(final byte[] key, final double lat, final double lon, final long radius, final UNITS unit,
			final RELATION scope, final ORDERBY order) {
		// GPRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GPRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, scope.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, XR.raw, order.raw);
	}

	@Override
	public void gprangeByRadiusWithMatch(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern) {
		// GPRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GPRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, ASC.raw, NR.raw, MATCH.raw, pattern);
	}

	@Override
	public void gprangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern) {
		// GPRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GPRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, ASC.raw, XR.raw, MATCH.raw, pattern);
	}

	@Override
	public void gprangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern, RELATION scope,
			ORDERBY order) {
		// GPRANGEBYRADIUS key latitude longitude RADIUS radius m|km CONTAINS|WITHIN [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [ASC|DESC]
		// [LIMIT offset count]
		sendCommand(GPRANGEBYRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, scope.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, order.raw, XR.raw, MATCH.raw, pattern);
	}

	public void gpcard(final byte[] key) {
		// GPCARD key
		sendCommand(GPCARD, key);
	}

	@Override
	public void gprem(final byte[] key, final byte[] member) {
		sendCommand(GPREM, key, member);
	}

	@Override
	public void gpget(byte[] key, byte[] member) {
		// GPGET key member / GPMGET key member [member ...]
		sendCommand(GPGET, key, member);

	}

	@Override
	public void gpmget(final byte[] key, final byte[]... members) {
		// GPGET key member / GPMGET key member [member ...]
		int len = members.length;
		byte[][] bargs = new byte[len + 1][];
		bargs[0] = key;
		for (int i = 0; i < len; ++i) {
			bargs[i + 1] = members[i];
		}
		sendCommand(GPMGET, bargs);
	}

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long count) {
		// GPNN key latitude longitude LIMIT count [MATCH pattern] [NR|XR] [WITHVALUES] [WITHDISTANCE]
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), NR.raw, WITHVALUES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gprangeByRegion(byte[] key, Polygon<?> polygon) {
		// GPRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GPRANGEBYREGION, key, polygon.getJsonByte(), WITHVALUES.raw);
	}

	@Override
	public void gprangeByRegion(byte[] key, LineString<?> lineString) {
		// GPRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GPRANGEBYREGION, key, lineString.getJsonByte(), WITHVALUES.raw);
	}

	@Override
	public void gprangeByRegion(byte[] key, Point<?> point) {
		// GPRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GPRANGEBYREGION, key, point.getJsonByte(), WITHVALUES.raw);
	}

	@Override
	public void gprangeByRegionWithMatch(byte[] key, Polygon<?> polygon, byte[] pattern) {
		// GPRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GPRANGEBYREGION, key, polygon.getJsonByte(), MATCH.raw, pattern, WITHVALUES.raw);
	}

	@Override
	public void gprangeByRegionWithMatch(byte[] key, Polygon<?> polygon, byte[] pattern, long count) {
		// GPRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GPRANGEBYREGION, key, polygon.getJsonByte(), MATCH.raw, pattern, WITHVALUES.raw, ASC.raw, LIMIT.raw, toByteArray(0),
				toByteArray(count));
	}

	@Override
	public void gprangeByRegionWithMatch(byte[] key, LineString<?> lineString, byte[] pattern) {
		// GPRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GPRANGEBYREGION, key, lineString.getJsonByte(), MATCH.raw, pattern, WITHVALUES.raw);
	}

	@Override
	public void gprangeByRegionWithMatch(byte[] key, Point<?> point, byte[] pattern) {
		// GPRANGEBYREGION key geojson_region [CP latitude longitude] [MATCH pattern][NR|XR]
		// [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset count]
		sendCommand(GPRANGEBYREGION, key, point.getJsonByte(), MATCH.raw, pattern, WITHVALUES.raw);
	}

	@Override
	public void gprangeBy(byte[] key, byte[] bykey, byte[] bymember) {
		// GRANGEBY key BY bykey bymember CONTAINS|WITHIN [MATCH pattern] [NR|XR] [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset
		// count]
		sendCommand(GPRANGEBY, key, BY.raw, bykey, bymember, CONTAINS.raw, NR.raw, WITHVALUES.raw, WITHDISTANCE.raw, ASC.raw);
	}

	@Override
	public void gprangeByWithMatch(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern, long count) {
		// GRANGEBY key BY bykey bymember CONTAINS|WITHIN [MATCH pattern] [NR|XR] [WITHVALUES] [WITHDISTANCE] [ASC|DESC] [LIMIT offset
		// count]
		sendCommand(GPRANGEBY, key, BY.raw, bykey, bymember, CONTAINS.raw, MATCH.raw, pattern, NR.raw, WITHVALUES.raw, WITHDISTANCE.raw,
				ASC.raw, LIMIT.raw, toByteArray(0), toByteArray(count));
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

	// Geometry

	@Override
	public void gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		sendCommand(GMSETBOUNDARY, key, toByteArray(minx), toByteArray(miny), toByteArray(maxx), toByteArray(maxy));
	}

	@Override
	public void gmgetBoundary(byte[] key) {
		sendCommand(GMGETBOUNDARY, key);
	}

	@Override
	public void gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		sendCommand(GMREBUILDBOUNDARY, key, toByteArray(minx), toByteArray(miny), toByteArray(maxx), toByteArray(maxy));
	}

	@Override
	public void gmadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, polygon.getJsonByte());
	}

	@Override
	public void gmadd(byte[] key, byte[] member, byte[] value, Point<?> point) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, point.getJsonByte());
	}

	public void gmadd(byte[] key, double x, double y, byte[] member, byte[] value) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, new Point<byte[]>(x, y).getJsonByte());
	}

	@Override
	public void gmadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, lineString.getJsonByte());
	}

	@Override
	public void gmupdate(byte[] key, byte[] member, Polygon<?> polygon) {
		// GMUPDATEBY key member geojson
		sendCommand(GMUPDATEBY, key, member, polygon.getJsonByte());
	}

	@Override
	public void gmupdate(byte[] key, byte[] member, Point<?> point) {
		// GMUPDATEBY key member geojson
		sendCommand(GMUPDATEBY, key, member, point.getJsonByte());
	}

	@Override
	public void gmupdate(byte[] key, byte[] member, LineString<?> lineString) {
		// GMUPDATEBY key member geojson
		sendCommand(GMUPDATEBY, key, member, lineString.getJsonByte());
	}

	@Override
	public void gmrange(byte[] key, long start, long stop) {
		// GMADD key member value geojson
		sendCommand(GMRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw);
	}

	@Override
	public void gmrevrange(byte[] key, long start, long stop) {
		// GMADD key member value geojson
		sendCommand(GMREVRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw);
	}

	@Override
	public void gmcard(byte[] key) {
		sendCommand(GMCARD, key);
	}

	@Override
	public void gmrem(byte[] key, byte[] member) {
		sendCommand(GMREM, key, member);
	}

	@Override
	public void gmget(byte[] key, byte[] member) {
		// GMGET key member / GGMGET key member [member ...]
		sendCommand(GMGET, key, member);

	}

	@Override
	public void gmmget(final byte[] key, final byte[][] members) {
		// GMGET key member / GGMGET key member [member ...]
		int len = members.length;
		byte[][] bargs = new byte[len + 1][];
		bargs[0] = key;
		for (int i = 0; i < len; ++i) {
			bargs[i + 1] = members[i];
		}
		sendCommand(GMMGET, bargs);
	}

	@Override
	public void gmrelation(byte[] key, Polygon<?> polygon) {
		// GMRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GMRELATION, key, polygon.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelation(byte[] key, LineString<?> lineString) {
		// GMRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GMRELATION, key, lineString.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelation(byte[] key, Point<?> point) {
		// GMRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GMRELATION, key, point.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelationBy(byte[] key, byte[] byKey, byte[] byMember) {
		// GMRELATIONBY key BY bykey bymember CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		sendCommand(GMRELATIONBY, key, BY.raw, byKey, byMember, CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmnn(final byte[] key, final double lat, final double lon, final long count) {
		// GMnn mygg 0 0 limit 2 withvalues withdistance withgeojson
		sendCommand(GMNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmnnWithMatch(final byte[] key, final double lat, final double lon, final long count, final byte[] pattern) {
		// GMnn mygg 0 0 limit 2 match hello* withvalues withdistance withgeojson
		sendCommand(GMNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), MATCH.raw, pattern, WITHVALUES.raw,
				WITHGEOJSON.raw);
	}
}
