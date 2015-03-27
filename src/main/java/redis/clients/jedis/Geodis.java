package redis.clients.jedis;

import static redis.clients.jedis.Protocol.DEFAULT_TIMEOUT;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.util.Iterator;
import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Polygon;

public class Geodis extends Jedis implements GeoCommands {

	public Geodis(String host, int port, int timeout) {
		super(host, port, timeout);
	}

	public Geodis(String host, int port) {
		super(host, port, DEFAULT_TIMEOUT);
	}

	@Override
	public Long gadd(final String key, final double lat, final double lon, final String member, final String value) {
		return gfadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gadd(final byte[] key, final double lat, final double lon, final byte[] member, final byte[] value) {
		return gfadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gfadd(final String key, final double lat, final double lon, final double distance, final UNITS unit, final String member,
			final String value) {
		checkIsInMulti();
		client.gfadd(key, lat, lon, distance, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gfadd(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit, final byte[] member,
			final byte[] value) {
		checkIsInMulti();
		client.gfadd(key, lat, lon, distance, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public List<String> gfrangeByRadius(final String key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return client.getMultiBulkReply();
	}

	@Override
	public List<byte[]> gfrangeByRadius(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return client.getBinaryMultiBulkReply();
	}

	@Override
	public List<Circle> gfrangeByRadiusDetail(final String key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadiusDetail(key, lat, lon, distance, unit);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Circle> gfrangeByRadiusDetail(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadiusDetail(key, lat, lon, distance, unit);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Circle> gfrangeByRadiusWithMatch(final String key, final double lat, final double lon, final double distance,
			final UNITS unit, final String pattern) {
		checkIsInMulti();
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Circle> gfrangeByRadiusWithMatch(final byte[] key, final double lat, final double lon, final double distance,
			final UNITS unit, final byte[] pattern) {
		checkIsInMulti();
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public long gfcard(final String key) {
		checkIsInMulti();
		client.gfcard(key);
		return client.getIntegerReply();
	}

	@Override
	public long gfcard(final byte[] key) {
		checkIsInMulti();
		client.gfcard(key);
		return client.getIntegerReply();
	}

	@Override
	public long gfrem(final String key, final String member) {
		checkIsInMulti();
		client.gfrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public long gfrem(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gfrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public List<Circle> gfnn(final String key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gfnn(key, lat, lon, count);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Circle> gfnn(final byte[] key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gfnn(key, lat, lon, count);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public Circle gfget(final String key, final String member) {
		List<Circle> result = gfmget(key, member);
		if (!result.isEmpty()) {
			Iterator<Circle> circlesIterator = gfmget(key, member).iterator();
			if (circlesIterator.hasNext()) {
				return circlesIterator.next();
			}
		}
		return null;

	}

	@Override
	public Circle gfget(final byte[] key, final byte[] member) {
		Iterator<Circle> circlesIterator = gfmget(key, member).iterator();
		if (circlesIterator.hasNext()) {
			return circlesIterator.next();
		} else {
			return null;
		}
	}

	@Override
	public List<Circle> gfmget(final String key, final String... members) {
		checkIsInMulti();
		client.gfmget(key, members);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Circle> gfmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.gfmget(key, members);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Circle> gfrangeByRegion(final String key, final Polygon polygon) {
		checkIsInMulti();
		client.gfrangeByRegion(key, polygon);
		return client.getSpatialMGETMultiBulkReply();
	}
	
	@Override
	public List<Circle> gfrangeByRegion(final byte[] key, final Polygon polygon) {
		checkIsInMulti();
		client.gfrangeByRegion(key, polygon);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	// @Override
	// public Double distance(final double dLat1, final double dLon1, final double dLat2, final double dLon2) {
	// // d = |ax1 + by1 + c | / sqrt(a^2 + b^2)
	// return Math.acos(Math.sin(dLat1) * Math.sin(dLat2) + Math.cos(dLat1) * Math.cos(dLat2) * Math.cos(dLon1 - dLon2));
	// }

}
