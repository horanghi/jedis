package redis.clients.jedis;

import java.util.List;

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
	public Response<Long> gfadd(String key, double lat, double lon, double distance, UNITS unit, String member, String value) {
		client.gfadd(key, lat, lon, distance, unit, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gfadd(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] member, byte[] value) {
		client.gfadd(key, lat, lon, distance, unit, member, value);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<List<Point<String>>> gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit) {
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return getResponse(BuilderFactory.SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit) {
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gfrangeCircleByRadius(String key, double lat, double lon, double distance, UNITS unit) {
		client.gfrangeCircleByRadius(key, lat, lon, distance, unit);
		return getResponse(BuilderFactory.SPATIAL_GCircle_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gfrangeCircleByRadius(byte[] key, double lat, double lon, double distance, UNITS unit) {
		client.gfrangeCircleByRadius(key, lat, lon, distance, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCircle_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<String>>> gfrangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit,
			String pattern) {
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return getResponse(BuilderFactory.SPATIAL_GPoint_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit,
			byte[] pattern) {
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<String>>> gfrangeCircleByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit,
			String pattern) {
		client.gfrangeCircleByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return getResponse(BuilderFactory.SPATIAL_GCircle_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle<byte[]>>> gfrangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit,
			byte[] pattern) {
		client.gfrangeCircleByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCircle_WITHDISTANCE_LIST);
	}

	@Override
	public Response<Long> gfcard(String key) {
		client.gfcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gfcard(byte[] key) {
		client.gfcard(key);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gfrem(String key, String member) {
		client.gfrem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> gfrem(byte[] key, byte[] member) {
		client.gfrem(key, member);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Point<String>> gfget(String key, String member) {
		client.gfget(key, member);
		return getResponse(BuilderFactory.SPATIAL_GPoint);
	}

	@Override
	public Response<Point<byte[]>> gfget(byte[] key, byte[] member) {
		client.gfget(key, member);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint);
	}

	@Override
	public Response<List<Point<String>>> gfmget(String key, String... members) {
		client.gfmget(key, members);
		return getResponse(BuilderFactory.SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gfmget(byte[] key, byte[]... members) {
		client.gfmget(key, members);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point<String>>> gfnn(String key, double lat, double lon, long count) {
		client.gfnn(key, lat, lon, count);
		return getResponse(BuilderFactory.SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gfnn(byte[] key, double lat, double lon, long count) {
		client.gfnn(key, lat, lon, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point<String>>> gfrangeByRegion(String key, Polygon<String> polygon) {
		client.gfrangeByRegion(key, polygon);
		return getResponse(BuilderFactory.SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point<byte[]>>> gfrangeByRegion(byte[] key, Polygon<byte[]> polygon) {
		client.gfrangeByRegion(key, polygon);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<Long> ggadd(String key, String member, String value, Polygon<String> polygon) {
		client.ggadd(key, member, value, polygon);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon) {
		client.ggadd(key, member, value, polygon);
		return getResponse(BuilderFactory.LONG);
	}

	@Override
	public Response<Long> ggadd(String key, String member, String value, LineString<String> lineString) {
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
		return getResponse(BuilderFactory.SPATIAL_GGraphy_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrange(byte[] key, long start, long stop) {
		client.ggrange(key, start, stop);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGraphy_LIST);
	}

	@Override
	public Response<Long> ggadd(String key, String member, String value, Point<String> point) {
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
		return getResponse(BuilderFactory.SPATIAL_GGraphy_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggrevrange(byte[] key, long start, long stop) {
		client.ggrevrange(key, start, stop);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGraphy_LIST);
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
		return getResponse(BuilderFactory.SPATIAL_GGraphy);
	}

	@Override
	public Response<Geometry<byte[]>> ggget(byte[] key, byte[] member) {
		client.ggget(key, member);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGraphy);
	}

	@Override
	public Response<List<Geometry<String>>> ggmget(String key, String... members) {
		client.ggmget(key, members);
		return getResponse(BuilderFactory.SPATIAL_GGraphy_LIST);
	}

	@Override
	public Response<List<Geometry<byte[]>>> ggmget(byte[] key, byte[]... members) {
		client.ggmget(key, members);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GGraphy_LIST);
	}

}
