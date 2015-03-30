package redis.clients.jedis;

import static redis.clients.jedis.Protocol.DEFAULT_TIMEOUT;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public class Geodis extends BinaryJedis implements GeoCommands {

	public Geodis(final String host) {
		super(host);
	}
	
	public Geodis(String host, int port, int timeout) {
		super(host, port, timeout);
	}

	public Geodis(String host, int port) {
		super(host, port, DEFAULT_TIMEOUT);
	}

	public Geodis(JedisShardInfo shardInfo) {
		super(shardInfo);
	}

	public Geodis(URI uri) {
		super(uri);
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
	public List<Point> gfrangeByRadius(final String key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point> gfrangeByRadius(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Circle> gfrangeCircleByRadius(final String key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeCircleByRadius(key, lat, lon, distance, unit);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle> gfrangeCircleByRadius(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeCircleByRadius(key, lat, lon, distance, unit);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Point> gfrangeByRadiusWithMatch(final String key, final double lat, final double lon, final double distance,
			final UNITS unit, final String pattern) {
		checkIsInMulti();
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point> gfrangeByRadiusWithMatch(final byte[] key, final double lat, final double lon, final double distance,
			final UNITS unit, final byte[] pattern) {
		checkIsInMulti();
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point> gfrangeCircleByRadiusWithMatch(final String key, final double lat, final double lon, final double distance,
			final UNITS unit, final String pattern) {
		checkIsInMulti();
		client.gfrangeCircleByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point> gfrangeCircleByRadiusWithMatch(final byte[] key, final double lat, final double lon, final double distance,
			final UNITS unit, final byte[] pattern) {
		checkIsInMulti();
		client.gfrangeCircleByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public Long gfcard(final String key) {
		checkIsInMulti();
		client.gfcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gfcard(final byte[] key) {
		checkIsInMulti();
		client.gfcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gfrem(final String key, final String member) {
		checkIsInMulti();
		client.gfrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gfrem(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gfrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public List<Point> gfnn(final String key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gfnn(key, lat, lon, count);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point> gfnn(final byte[] key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gfnn(key, lat, lon, count);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public Point gfget(final String key, final String member) {
		List<Point> result = gfmget(key, member);
		if (!result.isEmpty()) {
			Iterator<Point> PointsIterator = gfmget(key, member).iterator();
			if (PointsIterator.hasNext()) {
				return PointsIterator.next();
			}
		}
		return null;

	}

	@Override
	public Point gfget(final byte[] key, final byte[] member) {
		Iterator<Point> PointsIterator = gfmget(key, member).iterator();
		if (PointsIterator.hasNext()) {
			return PointsIterator.next();
		} else {
			return null;
		}
	}

	@Override
	public List<Point> gfmget(final String key, final String... members) {
		checkIsInMulti();
		client.gfmget(key, members);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point> gfmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.gfmget(key, members);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point> gfrangeByRegion(final String key, final Polygon polygon) {
		checkIsInMulti();
		client.gfrangeByRegion(key, polygon);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point> gfrangeByRegion(final byte[] key, final Polygon polygon) {
		checkIsInMulti();
		client.gfrangeByRegion(key, polygon);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public Double distance(final double dLat1, final double dLon1, final double dLat2, final double dLon2) {
		// d = |ax1 + by1 + c | / sqrt(a^2 + b^2)
		return Math.acos(Math.sin(dLat1) * Math.sin(dLat2) + Math.cos(dLat1) * Math.cos(dLat2) * Math.cos(dLon1 - dLon2));
	}

}
