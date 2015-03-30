package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

abstract class GeoMultiKeyPipelineBase extends MultiKeyPipelineBase implements GeoPipeline { 
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfrangeByRadius(String key, double lat, double lon, double distance, UNITS unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfrangeByRadius(byte[] key, double lat, double lon, double distance, UNITS unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Circle>> gfrangeCircleByRadius(String key, double lat, double lon, double distance, UNITS unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Circle>> gfrangeCircleByRadius(byte[] key, double lat, double lon, double distance, UNITS unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfrangeByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit, String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfrangeByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit, byte[] pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfrangeCircleByRadiusWithMatch(String key, double lat, double lon, double distance, UNITS unit,
			String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfrangeCircleByRadiusWithMatch(byte[] key, double lat, double lon, double distance, UNITS unit,
			byte[] pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<Long> gfcard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<Long> gfcard(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<Long> gfrem(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<Long> gfrem(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<Point> gfget(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<Point> gfget(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfmget(String key, String... members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfmget(byte[] key, byte[]... members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfnn(String key, double lat, double lon, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfnn(byte[] key, double lat, double lon, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfrangeByRegion(String key, Polygon polygon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<Point>> gfrangeByRegion(byte[] key, Polygon polygon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Client getClient(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Client getClient(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

}
