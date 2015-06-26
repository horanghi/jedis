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
	public Response<Long> gpadd(String key, double lat, double lon, String member, String value) {
		client.gpadd(key, lat, lon, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value) {
		client.gpadd(key, lat, lon, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpadd(String key, double lat, double lon, long radius, UNITS unit, String member, String value) {
		client.gpadd(key, lat, lon, radius, unit, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpadd(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] member, byte[] value) {
		client.gpadd(key, lat, lon, radius, unit, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(String key, String member, double lat, double lon) {
		client.gpupdate(key, member, lat, lon);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(byte[] key, byte[] member, double lat, double lon) {
		client.gpupdate(key, member, lat, lon);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(String key, String member, double lat, double lon, long radius, UNITS unit) {
		client.gpupdate(key, member, lat, lon, radius, unit);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(byte[] key, byte[] member, double lat, double lon, long radius, UNITS unit) {
		client.gpupdate(key, member, lat, lon, radius, unit);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Point<String>>> gprangeBy(String key, String bykey, String bymember) {
		client.gprangeBy(key, bykey, bymember);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gprangeBy(byte[] key, byte[] bykey, byte[] bymember) {
		client.gprangeBy(key, bykey, bymember);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> gprangeBy(String key, String bykey, String bymember, String pattern, long count) {
		client.gprangeByWithMatch(key, bykey, bymember, pattern, count);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gprangeBy(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern, long count) {
		client.gprangeByWithMatch(key, bykey, bymember, pattern, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> gprangeByRadius(String key, double lat, double lon, long radius, UNITS unit) {
		client.gprangeByRadius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gprangeByRadius(byte[] key, double lat, double lon, long radius, UNITS unit) {
		client.gprangeByRadius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit) {
		client.gprangeCircleByRadius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gprangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit) {
		client.gprangeCircleByRadius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit,
			RELATION scope, ORDERBY order) {
		client.gprangeCircleByRadius(key, lat, lon, radius, unit, scope, order);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gprangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit,
			RELATION scope, ORDERBY order) {
		client.gprangeCircleByRadius(key, lat, lon, radius, unit, scope, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> gprangeByRadius(String key, double lat, double lon, long radius, UNITS unit, String pattern) {
		client.gprangeByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gprangeByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern) {
		client.gprangeByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit, String pattern) {
		client.gprangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gprangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit, byte[] pattern) {
		client.gprangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gprangeCircleByRadius(String key, double lat, double lon, long radius, UNITS unit,
			String pattern, RELATION scope, ORDERBY order) {
		client.gprangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern, scope, order);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gprangeCircleByRadius(byte[] key, double lat, double lon, long radius, UNITS unit,
			byte[] pattern, RELATION scope, ORDERBY order) {
		client.gprangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern, scope, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<Long> gpcard(String key) {
		client.gpcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpcard(byte[] key) {
		client.gpcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gprem(String key, String member) {
		client.gprem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gprem(byte[] key, byte[] member) {
		client.gprem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Point<String>> gpget(String key, String member) {
		client.gpget(key, member);
		return getResponse(BuilderFactory.SPATIAL_GPOINT);
	}

	@Override
	public Response<Point<byte[]>> gpget(byte[] key, byte[] member) {
		client.gpget(key, member);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT);
	}

	@Override
	public Response<List<Point<String>>> gpmget(String key, String... members) {
		client.gpmget(key, members);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpmget(byte[] key, byte[]... members) {
		client.gpmget(key, members);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpnn(String key, double lat, double lon, long count) {
		client.gpnn(key, lat, lon, count);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long count) {
		client.gpnn(key, lat, lon, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> gprangeByRegion(String key, Polygon<?> polygon) {
		client.gprangeByRegion(key, polygon);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gprangeByRegion(byte[] key, Polygon<?> polygon) {
		client.gprangeByRegion(key, polygon);
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

	/* Geometry */

	@Override
	public Response<String> gmsetBoundary(String key, double minx, double miny, double maxx, double maxy) {
		client.gmsetBoundary(key, minx, miny, maxx, maxy);
		return getResponse(BuilderFactory.STRING);
	}

	@Override
	public Response<String> gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		client.gmsetBoundary(key, minx, miny, maxx, maxy);
		return getResponse(BuilderFactory.STRING);
	}

	@Override
	public Response<List<Point<String>>> gmgetBoundary(String key) {
		client.gmgetBoundary(key);
		return getResponse(BuilderFactory.SPATIAL_BOUNDARY_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gmgetBoundary(byte[] key) {
		client.gmgetBoundary(key);
		return getResponse(BuilderFactory.BYTE_SPATIAL_BOUNDARY_LIST);
	}

	@Override
	public Response<String> gmrebuildBoundary(String key, double minx, double miny, double maxx, double maxy) {
		client.gmrebuildBoundary(key, minx, miny, maxx, maxy);
		return getResponse(BuilderFactory.STRING);
	}

	@Override
	public Response<String> gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		client.gmrebuildBoundary(key, minx, miny, maxx, maxy);
		return getResponse(BuilderFactory.STRING);
	}

	@Override
	public Response<Long> gmadd(String key, String member, String value, Polygon<?> polygon) {
		client.gmadd(key, member, value, polygon);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon) {
		client.gmadd(key, member, value, polygon);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(String key, String member, String value, LineString<?> lineString) {
		client.gmadd(key, member, value, lineString);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString) {
		client.gmadd(key, member, value, lineString);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Geometry<String>>> gmrange(String key, long start, long stop) {
		client.gmrange(key, start, stop);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrange(byte[] key, long start, long stop) {
		client.gmrange(key, start, stop);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<Long> gmadd(String key, String member, String value, Point<?> point) {
		client.gmadd(key, member, value, point);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(byte[] key, byte[] member, byte[] value, Point<?> point) {
		client.gmadd(key, member, value, point);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Geometry<String>>> gmrevrange(String key, long start, long stop) {
		client.gmrevrange(key, start, stop);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrevrange(byte[] key, long start, long stop) {
		client.gmrevrange(key, start, stop);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<Long> gmcard(String key) {
		client.gmcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmcard(byte[] key) {
		client.gmcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmrem(String key, String member) {
		client.gmrem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmrem(byte[] key, byte[] member) {
		client.gmrem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Geometry<String>> gmget(String key, String member) {
		client.gmget(key, member);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY);
	}

	@Override
	public Response<Geometry<byte[]>> gmget(byte[] key, byte[] member) {
		client.gmget(key, member);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY);
	}

	@Override
	public Response<List<Geometry<String>>> gmmget(String key, String... members) {
		client.gmmget(key, members);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmmget(byte[] key, byte[]... members) {
		client.gmmget(key, members);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmrelation(final String key, final Polygon<?> polygon) {
		client.gmrelation(key, polygon);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelation(final byte[] key, final Polygon<?> polygon) {
		client.gmrelation(key, polygon);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmrelation(final String key, final LineString<?> lineString) {
		client.gmrelation(key, lineString);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelation(final byte[] key, final LineString<?> lineString) {
		client.gmrelation(key, lineString);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmrelation(final String key, final Point<?> point) {
		client.gmrelation(key, point);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelation(final byte[] key, final Point<?> point) {
		client.gmrelation(key, point);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmrelationBy(final String key, final String byKey, final String byMember) {
		client.gmrelationBy(key, byKey, byMember);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelationBy(final byte[] key, final byte[] byKey, final byte[] byMember) {
		client.gmrelationBy(key, byKey, byMember);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmnn(final String key, final double lat, final double lon, final long count) {
		client.gmnn(key, lat, lon, count);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmnn(final byte[] key, final double lat, final double lon, final long count) {
		client.gmnn(key, lat, lon, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmnn(final String key, final double lat, final double lon, final long count,
			final String pattern) {
		client.gmnnWithMatch(key, lat, lon, count, pattern);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmnn(final byte[] key, final double lat, final double lon, final long count,
			final byte[] pattern) {
		client.gmnnWithMatch(key, lat, lon, count, pattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

}