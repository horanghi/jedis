package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
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
	public Response<List<Point>> gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit) {
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return getResponse(BuilderFactory.SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point>> gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit) {
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Circle>> gfrangeCircleByRadius(String key, double lat, double lon, double distance, UNITS unit) {
		client.gfrangeCircleByRadius(key, lat, lon, distance, unit);
		return getResponse(BuilderFactory.SPATIAL_GCircle_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle>> gfrangeCircleByRadius(byte[] key, double lat, double lon, double distance, UNITS unit) {
		client.gfrangeCircleByRadius(key, lat, lon, distance, unit);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GCircle_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Point>> gfrangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern) {
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return getResponse(BuilderFactory.SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point>> gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern) {
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Circle>> gfrangeCircleByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit,
			String pattern) {
		client.gfrangeCircleByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return getResponse(BuilderFactory.SPATIAL_GCircle_WITHDISTANCE_LIST);
	}

	@Override
	public Response<List<Circle>> gfrangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit,
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
	public Response<Point> gfget(String key, String member) {
		client.gfget(key, member);
		return getResponse(BuilderFactory.SPATIAL_GPoint);
	}

	@Override
	public Response<Point> gfget(byte[] key, byte[] member) {
		client.gfget(key, member);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint);
	}

	@Override
	public Response<List<Point>> gfmget(String key, String... members) {
		client.gfmget(key, members);
		return getResponse(BuilderFactory.SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point>> gfmget(byte[] key, byte[]... members) {
		client.gfmget(key, members);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point>> gfnn(String key, double lat, double lon, long count) {
		client.gfnn(key, lat, lon, count);
		return getResponse(BuilderFactory.SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point>> gfnn(byte[] key, double lat, double lon, long count) {
		client.gfnn(key, lat, lon, count);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point>> gfrangeByRegion(String key, Polygon polygon) {
		client.gfrangeByRegion(key, polygon);
		return getResponse(BuilderFactory.SPATIAL_GPoint_LIST);
	}

	@Override
	public Response<List<Point>> gfrangeByRegion(byte[] key, Polygon polygon) {
		client.gfrangeByRegion(key, polygon);
		return getResponse(BuilderFactory.BYTE_SPATIAL_GPoint_LIST);
	}

}
