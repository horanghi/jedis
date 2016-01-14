package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.Point;

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
	public Response<Long> gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value) {
		client.gpadd(key, lat, lon, radius, unit, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value) {
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
	public Response<Long> gpupdate(String key, String member, double lat, double lon, double radius, UNITS unit) {
		client.gpupdate(key, member, lat, lon, radius, unit);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit) {
		client.gpupdate(key, member, lat, lon, radius, unit);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(String key, String member, double lat, double lon, double radius, UNITS unit, String value, double score) {
		client.gpupdate(key, member, lat, lon, radius, unit, value, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit, byte[] value, double score) {
		client.gpupdate(key, member, lat, lon, radius, unit, value, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(String key, String member, double radius, UNITS unit) {
		client.gpupdate(key, member, radius, unit);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(byte[] key, byte[] member, double radius, UNITS unit) {
		client.gpupdate(key, member, radius, unit);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(String key, String member, double score) {
		client.gpupdate(key, member, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(byte[] key, byte[] member, double score) {
		client.gpupdate(key, member, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(String key, String member, String value) {
		client.gpupdate(key, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpupdate(byte[] key, byte[] member, byte[] value) {
		client.gpupdate(key, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember) {
		client.gpregionByMember(key, bykey, bymember);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember) {
		client.gpregionByMember(key, bykey, bymember);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember, String min, String max,
			String valuePattern, long offset, long count, ORDERBY order) {
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max,
			byte[] valuePattern, long offset, long count, ORDERBY order) {
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember, String min, String max,
			String memberPattern, String valuePattern, long offset, long count, ORDERBY order) {
		client.gpregionByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max,
			byte[] memberPattern, byte[] valuePattern, long offset, long count, ORDERBY order) {
		client.gpregionByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit) {
		client.gpradius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit) {
		client.gpradius(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<Circle<String>> gpcircle(String key, String member) {
		client.gpcircle(key, member);
		return getResponse(BuilderFactory.SPATIAL_CIRCLE);
	}

	@Override
	public Response<Circle<byte[]>> gpcircle(byte[] key, byte[] member) {
		client.gpcircle(key, member);
		return getResponse(BuilderFactory.BYTE_SPATIAL_CIRCLE);
	}

	@Override
	public Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit) {
		client.gpcircle(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit) {
		client.gpcircle(key, lat, lon, radius, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, RELATION scope,
			ORDERBY order) {
		client.gpcircle(key, lat, lon, radius, unit, scope, order);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, RELATION scope,
			ORDERBY order) {
		client.gpcircle(key, lat, lon, radius, unit, scope, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String memberPattern,
			String valuePattern, RELATION scope, ORDERBY order) {
		client.gpcircle(key, lat, lon, radius, unit, memberPattern, valuePattern, scope, order);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] memberPattern,
			byte[] valuePattern, RELATION scope, ORDERBY order) {
		client.gpcircle(key, lat, lon, radius, unit, memberPattern, valuePattern, scope, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String valuePattern) {
		client.gpradius(key, lat, lon, radius, unit, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] valuePattern) {
		client.gpradius(key, lat, lon, radius, unit, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String valuePattern) {
		client.gpcircle(key, lat, lon, radius, unit, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] valuePattern) {
		client.gpcircle(key, lat, lon, radius, unit, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String valuePattern,
			RELATION scope, ORDERBY order) {
		client.gpcircle(key, lat, lon, radius, unit, valuePattern, scope, order);
		return getResponse(BuilderFactory.SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] valuePattern,
			RELATION scope, ORDERBY order) {
		client.gpcircle(key, lat, lon, radius, unit, valuePattern, scope, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCIRCLE_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> gprange(String key, long start, long stop) {
		client.gprange(key, start, stop);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHSCORE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gprange(byte[] key, long start, long stop) {
		client.gprange(key, start, stop);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHSCORE_LIST);
	}

	@Override
	public Response<List<Point<String>>> gprevrange(String key, long start, long stop) {
		client.gprevrange(key, start, stop);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHSCORE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gprevrange(byte[] key, long start, long stop) {
		client.gprevrange(key, start, stop);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHSCORE_LIST);
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
	public Response<List<Point<String>>> gpscope(String key, String min, String max, String valuePattern, ORDERBY order) {
		client.gpscope(key, min, max, valuePattern, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpscope(byte[] key, byte[] min, byte[] max, byte[] valuePattern, ORDERBY order) {
		client.gpscope(key, min, max, valuePattern, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpscope(String key, String min, String max, long offset, long count, String valuePattern,
			ORDERBY order) {
		client.gpscope(key, min, max, offset, count, valuePattern, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpscope(byte[] key, byte[] min, byte[] max, long offset, long count, byte[] valuePattern,
			ORDERBY order) {
		client.gpscope(key, min, max, offset, count, valuePattern, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpscope(String key, String min, String max, long offset, long count, String memberPattern,
			String valuePattern, ORDERBY order) {
		client.gpscope(key, min, max, offset, count, memberPattern, valuePattern, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpscope(byte[] key, byte[] min, byte[] max, long offset, long count, byte[] memberPattern,
			byte[] valuePattern, ORDERBY order) {
		client.gpscope(key, min, max, offset, count, memberPattern, valuePattern, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpnn(String key, double lat, double lon, long offset, long count) {
		client.gpnn(key, lat, lon, offset, count);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long offset, long count) {
		client.gpnn(key, lat, lon, offset, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpnn(String key, double lat, double lon, long offset, long count, String valuePattern) {
		client.gpnn(key, lat, lon, offset, count, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] valuePattern) {
		client.gpnn(key, lat, lon, offset, count, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpnn(String key, double lat, double lon, long offset, long count, String memberPattern,
			String valuePattern, String min, String max, ORDERBY order) {
		client.gpnn(key, lat, lon, offset, count, memberPattern, valuePattern, min, max, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] memberPattern,
			byte[] valuePattern, byte[] min, byte[] max, ORDERBY order) {
		client.gpnn(key, lat, lon, offset, count, memberPattern, valuePattern, min, max, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry) {
		client.gpregion(key, geometry);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry) {
		client.gpregion(key, geometry);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<Long> ggadd(String key, String member, String value, Geometry<?> geometry) {
		client.ggadd(key, member, value, geometry);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry) {
		client.ggadd(key, member, value, geometry);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggadd(String key, String member, String value, Geometry<?> geometry, double score) {
		client.ggadd(key, member, value, geometry, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score) {
		client.ggadd(key, member, value, geometry, score);
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
	public Response<List<Geometry<String>>> ggrelation(String key, Geometry<?> geometry) {
		client.ggrelation(key, geometry);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrelation(byte[] key, Geometry<?> geometry) {
		client.ggrelation(key, geometry);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggrelation(String key, Geometry<?> geometry, String min, String max) {
		client.ggrelation(key, geometry, min, max);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrelation(byte[] key, Geometry<?> geometry, byte[] min, byte[] max) {
		client.ggrelation(key, geometry, min, max);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggrelationByMember(String key, String byKey, String byMember) {
		client.ggrelationByMember(key, byKey, byMember);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrelationByMember(byte[] key, byte[] byKey, byte[] byMember) {
		client.ggrelationByMember(key, byKey, byMember);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count) {
		client.ggnn(key, lat, lon, count);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count) {
		client.ggnn(key, lat, lon, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count, String valuePattern) {
		client.ggnn(key, lat, lon, count, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count, byte[] valuePattern) {
		client.ggnn(key, lat, lon, count, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count, String memberPattern, String valuePattern) {
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count, byte[] memberPattern, byte[] valuePattern) {
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count, String min, String max,
			String memberPattern, String valuePattern) {
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern, min, max);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count, byte[] min, byte[] max,
			byte[] memberPattern, byte[] valuePattern) {
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern, min, max);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	/* Geometry */

	@Override
	public Response<String> gmsetBoundary(String key, double minx, double miny, double maxx, double maxy) {
		client.gmsetBoundary(key, minx, miny, maxx, maxy);
		return getResponse(BuilderFactory.STRING);
	}

	@Override
	public Response<byte[]> gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		client.gmsetBoundary(key, minx, miny, maxx, maxy);
		return getResponse(BuilderFactory.BYTE_ARRAY);
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
	public Response<Long> gmrebuildBoundary(String key, double minx, double miny, double maxx, double maxy) {
		client.gmrebuildBoundary(key, minx, miny, maxx, maxy);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		client.gmrebuildBoundary(key, minx, miny, maxx, maxy);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(String key, String member, String value, Geometry<?> geometry) {
		client.gmadd(key, member, value, geometry);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry) {
		client.gmadd(key, member, value, geometry);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(String key, double x, double y, String member, String value) {
		client.gmadd(key, x, y, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(byte[] key, double x, double y, byte[] member, byte[] value) {
		client.gmadd(key, x, y, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(String key, double x, double y, String member, String value, double score) {
		client.gmadd(key, x, y, member, value, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(byte[] key, double x, double y, byte[] member, byte[] value, double score) {
		client.gmadd(key, x, y, member, value, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(String key, String member, String value, Geometry<?> geometry, double score) {
		client.gmadd(key, member, value, geometry, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score) {
		client.gmadd(key, member, value, geometry, score);
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
	public Response<List<Geometry<String>>> gmrelation(String key, Geometry<?> geometry) {
		client.gmrelation(key, geometry);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelation(byte[] key, Geometry<?> geometry) {
		client.gmrelation(key, geometry);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmrelation(String key, Geometry<?> geometry, String memberPattern, String valuePattern) {
		client.gmrelation(key, geometry, memberPattern, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelation(byte[] key, Geometry<?> geometry, byte[] memberPattern, byte[] valuePattern) {
		client.gmrelation(key, geometry, memberPattern, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmrelation(String key, Geometry<?> geometry, String min, String max, String memberPattern,
			String valuePattern) {
		client.gmrelation(key, geometry, min, max, memberPattern, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelation(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern) {
		client.gmrelation(key, geometry, min, max, memberPattern, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmrelationByMember(String key, String byKey, String byMember) {
		client.gmrelationByMember(key, byKey, byMember);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember) {
		client.gmrelationByMember(key, byKey, byMember);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmrelationByMember(String key, String byKey, String byMember, String memberPattern,
			String valuePattern) {
		client.gmrelationByMember(key, byKey, byMember, memberPattern, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] memberPattern,
			byte[] valuePattern) {
		client.gmrelationByMember(key, byKey, byMember, memberPattern, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmrelationByMember(String key, String byKey, String byMember, String min, String max,
			String memberPattern, String valuePattern) {
		client.gmrelationByMember(key, byKey, byMember, min, max, memberPattern, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] min, byte[] max,
			byte[] memberPattern, byte[] valuePattern) {
		client.gmrelationByMember(key, byKey, byMember, min, max, memberPattern, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmnn(String key, double x, double y, long count) {
		client.gmnn(key, x, y, count);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmnn(byte[] key, double x, double y, long count) {
		client.gmnn(key, x, y, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Geometry<String>>> gmnn(String key, double x, double y, long count, String valuePattern) {
		client.gmnn(key, x, y, count, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GGRAPHY_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> gmnn(byte[] key, double x, double y, long count, byte[] valuePattern) {
		client.gmnn(key, x, y, count, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGRAPHY_WITHDISTANCE_LIST);
	}

	@Override
	public Response<Long> gpexists(String key, String member) {
		client.gpexists(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpexists(byte[] key, byte[] member) {
		client.gpexists(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggexists(String key, String member) {
		client.ggexists(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggexists(byte[] key, byte[] member) {
		client.ggexists(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmexists(String key, String member) {
		client.gmexists(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmexists(byte[] key, byte[] member) {
		client.gmexists(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpadd(String key, double lat, double lon, String member, String value, double score) {
		client.gpadd(key, lat, lon, member, value, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value, double score) {
		client.gpadd(key, lat, lon, member, value, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value, double score) {
		client.gpadd(key, lat, lon, radius, unit, member, value, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value, double score) {
		client.gpadd(key, lat, lon, radius, unit, member, value, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			String valuePattern) {
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			byte[] valuePattern) {
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			ORDERBY order) {
		client.gpradius(key, lat, lon, radius, unit, min, max, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			ORDERBY order) {
		client.gpradius(key, lat, lon, radius, unit, min, max, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			String valuePattern, long offset, long count, ORDERBY order) {
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			byte[] valuePattern, long offset, long count, ORDERBY order) {
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			String memberPattern, String valuePattern, long offset, long count, ORDERBY order) {
		client.gpradius(key, lat, lon, radius, unit, min, max, memberPattern, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			byte[] memberPattern, byte[] valuePattern, long offset, long count, ORDERBY order) {
		client.gpradius(key, lat, lon, radius, unit, min, max, memberPattern, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember) {
		client.gpradiusByMember(key, bykey, bymember);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember) {
		client.gpradiusByMember(key, bykey, bymember);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember, String valuePattern) {
		client.gpradiusByMember(key, bykey, bymember, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] valuePattern) {
		client.gpradiusByMember(key, bykey, bymember, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember, String min, String max,
			String valuePattern) {
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max,
			byte[] valuePattern) {
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember, String min, String max,
			String valuePattern, long offset, long count, ORDERBY order) {
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max,
			byte[] valuePattern, long offset, long count, ORDERBY order) {
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember, String min, String max,
			String memberPattern, String valuePattern, long offset, long count, ORDERBY order) {
		client.gpradiusByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max,
			byte[] memberPattern, byte[] valuePattern, long offset, long count, ORDERBY order) {
		client.gpradiusByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember, String valuePattern) {
		client.gpregionByMember(key, bykey, bymember, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] valuePattern) {
		client.gpregionByMember(key, bykey, bymember, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember, String min, String max,
			String valuePattern) {
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max,
			byte[] valuePattern) {
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpnn(String key, double lat, double lon, long offset, long count, String valuePattern, String min,
			String max, ORDERBY order) {
		client.gpnn(key, lat, lon, offset, count, valuePattern, min, max, order);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] valuePattern, byte[] min,
			byte[] max, ORDERBY order) {
		client.gpnn(key, lat, lon, offset, count, valuePattern, min, max, order);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String valuePattern) {
		client.gpregion(key, geometry, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] valuePattern) {
		client.gpregion(key, geometry, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String memberPattern, String valuePattern) {
		client.gpregion(key, geometry, memberPattern, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] memberPattern, byte[] valuePattern) {
		client.gpregion(key, geometry, memberPattern, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String min, String max, String valuePattern) {
		client.gpregion(key, geometry, min, max, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] valuePattern) {
		client.gpregion(key, geometry, min, max, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String min, String max, long offset, long count,
			String valuePattern) {
		client.gpregion(key, geometry, min, max, offset, count, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, long offset, long count,
			byte[] valuePattern) {
		client.gpregion(key, geometry, min, max, offset, count, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String min, String max, String memberPattern,
			String valuePattern) {
		client.gpregion(key, geometry, min, max, memberPattern, valuePattern);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern) {
		client.gpregion(key, geometry, min, max, memberPattern, valuePattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String min, String max, long offset, long count,
			String memberPattern, String valuePattern) {
		client.gpregion(key, geometry, min, max, offset, count, memberPattern, valuePattern, ORDERBY.SCORE_DESC);
		return getResponse(BuilderFactory.SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, long offset, long count,
			byte[] memberPattern, byte[] valuePattern) {
		client.gpregion(key, geometry, min, max, offset, count, memberPattern, valuePattern, ORDERBY.SCORE_DESC);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LIST);
	}

	@Override
	public Response<Long> ggupdate(String key, String member, Geometry<?> geometry) {
		client.ggupdate(key, member, geometry);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggupdate(byte[] key, byte[] member, Geometry<?> geometry) {
		client.ggupdate(key, member, geometry);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggupdate(String key, String member, Geometry<?> geometry, double score) {
		client.ggupdate(key, member, geometry, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggupdate(byte[] key, byte[] member, Geometry<?> geometry, double score) {
		client.ggupdate(key, member, geometry, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmupdate(String key, String member, Geometry<?> geometry) {
		client.gmupdate(key, member, geometry);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmupdate(byte[] key, byte[] member, Geometry<?> geometry) {
		client.gmupdate(key, member, geometry);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmupdate(String key, String member, Geometry<?> geometry, double score) {
		client.gmupdate(key, member, geometry, score);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gmupdate(byte[] key, byte[] member, Geometry<?> geometry, double score) {
		client.gmupdate(key, member, geometry, score);
		return getResponse(BuilderFactory.LONG);
	}
}