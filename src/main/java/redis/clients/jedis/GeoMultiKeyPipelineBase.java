package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

abstract class GeoMultiKeyPipelineBase extends MultiKeyPipelineBase implements Pipeline4Geo {

	@Override
	public Response<Long> gadd(String key, double lat, double lon, String member, String value) {
		client.gadd(key, lat, lon, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gadd(byte[] key, double lat, double lon, byte[] member, byte[] value) {
		client.gadd(key, lat, lon, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gadd(String key, double lat, double lon, long radius, UNITS unit, String member, String value) {
		client.gadd(key, lat, lon, radius, unit, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gadd(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] member, byte[] value) {
		client.gadd(key, lat, lon, radius, unit, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Point<String>>> grangeByRadius(String key, double lat, double lon, long radius, UNITS unit) {
		client.grangeByRadius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> grangeByRadius(byte[] key, double lat, double lon, long radius, UNITS unit) {
		client.grangeByRadius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> grangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit) {
		client.grangeCircleByRadius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> grangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit) {
		client.grangeCircleByRadius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> grangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit, RELATION scope,
			ORDERBY order) {
		client.grangeCircleByRadius(key, lat, lon, radius, unit, scope, order);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> grangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, RELATION scope,
			ORDERBY order) {
		client.grangeCircleByRadius(key, lat, lon, radius, unit, scope, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> grangeByRadius(String key, double lat, double lon, long radius, UNITS unit, String pattern) {
		client.grangeByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> grangeByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern) {
		client.grangeByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> grangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit, String pattern) {
		client.grangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> grangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern) {
		client.grangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> grangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit,
			String pattern, RELATION scope, ORDERBY order) {
		client.grangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern, scope, order);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> grangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit,
			byte[] pattern, RELATION scope, ORDERBY order) {
		client.grangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern, scope, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<Long> gcard(String key) {
		client.gcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gcard(byte[] key) {
		client.gcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> grem(String key, String member) {
		client.grem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> grem(byte[] key, byte[] member) {
		client.grem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Point<String>> gget(String key, String member) {
		client.gget(key, member);
		return getResponse(BuilderFactory.SPATIAL_GPOINT);
	}

	@Override
	public Response<Point<byte[]>> gget(byte[] key, byte[] member) {
		client.gget(key, member);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT);
	}

	@Override
	public Response<List<Point<String>>> gmget(String key, String... members) {
		client.gmget(key, members);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gmget(byte[] key, byte[]... members) {
		client.gmget(key, members);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_LIST);
	}

	@Override
	public Response<List<Point<String>>> gnn(String key, double lat, double lon, long count) {
		client.gnn(key, lat, lon, count);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gnn(byte[] key, double lat, double lon, long count) {
		client.gnn(key, lat, lon, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> grangeByRegion(String key, Polygon<?> polygon) {
		client.grangeByRegion(key, polygon);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> grangeByRegion(byte[] key, Polygon<?> polygon) {
		client.grangeByRegion(key, polygon);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_LIST);
	}

	@Override
	public Response<Long> ggadd(String key, String member, String value, Polygon<?> polygon) {
		client.ggadd(key, member, value, polygon);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon) {
		client.ggadd(key, member, value, polygon);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggadd(String key, String member, String value, LineString<?> lineString) {
		client.ggadd(key, member, value, lineString);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString) {
		client.ggadd(key, member, value, lineString);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Geometry<String>>> ggrange(String key, long start, long stop) {
		client.ggrange(key, start, stop);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrange(byte[] key, long start, long stop) {
		client.ggrange(key, start, stop);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<Long> ggadd(String key, String member, String value, Point<?> point) {
		client.ggadd(key, member, value, point);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Point<?> point) {
		client.ggadd(key, member, value, point);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Geometry<String>>> ggrevrange(String key, long start, long stop) {
		client.ggrevrange(key, start, stop);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrevrange(byte[] key, long start, long stop) {
		client.ggrevrange(key, start, stop);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<Long> ggcard(String key) {
		client.ggcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggcard(byte[] key) {
		client.ggcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggrem(String key, String member) {
		client.ggrem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggrem(byte[] key, byte[] member) {
		client.ggrem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Geometry<String>> ggget(String key, String member) {
		client.ggget(key, member);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY);
	}

	@Override
	public Response<Geometry<byte[]>> ggget(byte[] key, byte[] member) {
		client.ggget(key, member);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY);
	}

	@Override
	public Response<List<Geometry<String>>> ggmget(String key, String... members) {
		client.ggmget(key, members);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggmget(byte[] key, byte[]... members) {
		client.ggmget(key, members);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggrelation(final String key, final Polygon<?> polygon) {
		client.ggrelation(key, polygon);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrelation(final byte[] key, final Polygon<?> polygon) {
		client.ggrelation(key, polygon);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggrelation(final String key, final LineString<?> lineString) {
		client.ggrelation(key, lineString);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrelation(final byte[] key, final LineString<?> lineString) {
		client.ggrelation(key, lineString);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggrelation(final String key, final Point<?> point) {
		client.ggrelation(key, point);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrelation(final byte[] key, final Point<?> point) {
		client.ggrelation(key, point);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggrelationBy(final String key, final String byKey, final String byMember) {
		client.ggrelationBy(key, byKey, byMember);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrelationBy(final byte[] key, final byte[] byKey, final byte[] byMember) {
		client.ggrelationBy(key, byKey, byMember);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggnn(final String key, final double lat, final double lon, final long count) {
		client.ggnn(key, lat, lon, count);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggnn(final byte[] key, final double lat, final double lon, final long count) {
		client.ggnn(key, lat, lon, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggnn(final String key, final double lat, final double lon, final long count,
			final String pattern) {
		client.ggnnWithMatch(key, lat, lon, count, pattern);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggnn(final byte[] key, final double lat, final double lon, final long count,
			final byte[] pattern) {
		client.ggnnWithMatch(key, lat, lon, count, pattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<Long> gupdate(String key, double lat, double lon, String member) {
		client.gupdate(key, lat, lon, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gupdate(byte[] key, double lat, double lon, byte[] member) {
		client.gupdate(key, lat, lon, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gupdate(String key, double lat, double lon, long radius, UNITS unit, String member) {
		client.gupdate(key, lat, lon, radius, unit, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gupdate(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] member) {
		client.gupdate(key, lat, lon, radius, unit, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Point<String>>> grangeBy(String key, String bykey, String bymember) {
		client.grangeBy(key, bykey, bymember);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> grangeBy(byte[] key, byte[] bykey, byte[] bymember) {
		client.grangeBy(key, bykey, bymember);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> grangeBy(String key, String bykey, String bymember, String pattern, long count) {
		client.grangeByWithMatch(key, bykey, bymember, pattern, count);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> grangeBy(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern, long count) {
		client.grangeByWithMatch(key, bykey, bymember, pattern, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

}