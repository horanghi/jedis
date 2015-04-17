package redis.clients.jedis;

import static redis.clients.jedis.Protocol.DEFAULT_TIMEOUT;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.net.URI;
import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
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
	public List<Point<String>> gfrangeByRadius(final String key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gfrangeByRadius(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gfrangeCircleByRadius(final String key, final double lat, final double lon, final double distance,
			final UNITS unit) {
		checkIsInMulti();
		client.gfrangeCircleByRadius(key, lat, lon, distance, unit);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gfrangeCircleByRadius(final byte[] key, final double lat, final double lon, final double distance,
			final UNITS unit) {
		checkIsInMulti();
		client.gfrangeCircleByRadius(key, lat, lon, distance, unit);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Point<String>> gfrangeByRadiusWithMatch(final String key, final double lat, final double lon, final double distance,
			final UNITS unit, final String pattern) {
		checkIsInMulti();
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gfrangeByRadiusWithMatch(final byte[] key, final double lat, final double lon, final double distance,
			final UNITS unit, final byte[] pattern) {
		checkIsInMulti();
		client.gfrangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gfrangeCircleByRadiusWithMatch(final String key, final double lat, final double lon, final double distance,
			final UNITS unit, final String pattern) {
		checkIsInMulti();
		client.gfrangeCircleByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gfrangeCircleByRadiusWithMatch(final byte[] key, final double lat, final double lon, final double distance,
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
	public List<Point<String>> gfnn(final String key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gfnn(key, lat, lon, count);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gfnn(final byte[] key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gfnn(key, lat, lon, count);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public Point<String> gfget(final String key, final String member) {
		checkIsInMulti();
		client.gfget(key, member);
		return client.getSpatialGETMultiBulkReply();

	}

	@Override
	public Point<byte[]> gfget(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gfget(key, member);
		return client.getBinarySpatialGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gfmget(final String key, final String... members) {
		checkIsInMulti();
		client.gfmget(key, members);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gfmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.gfmget(key, members);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gfrangeByRegion(final String key, final Polygon<String> polygon) {
		checkIsInMulti();
		client.gfrangeByRegion(key, polygon);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gfrangeByRegion(final byte[] key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gfrangeByRegion(key, polygon);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public Double distance(final double dLat1, final double dLon1, final double dLat2, final double dLon2) {
		// d = |ax1 + by1 + c | / sqrt(a^2 + b^2)
		return Math.acos(Math.sin(dLat1) * Math.sin(dLat2) + Math.cos(dLat1) * Math.cos(dLat2) * Math.cos(dLon1 - dLon2));
	}

	@Override
	public Long ggadd(final String key, final String member, final String value, final Polygon<String> polygon) {
		checkIsInMulti();
		client.ggadd(key, member, value, polygon);
		return client.getIntegerReply();
	}

	@Override
	public Long ggadd(final byte[] key, final byte[] member, final byte[] value, final Polygon<?> polygon) {
		checkIsInMulti();
		client.ggadd(key, member, value, polygon);
		return client.getIntegerReply();
	}

	@Override
	public Long ggadd(final String key, final String member, final String value, final LineString<String> lineString) {
		checkIsInMulti();
		client.ggadd(key, member, value, lineString);
		return client.getIntegerReply();
	}

	@Override
	public Long ggadd(final byte[] key, final byte[] member, final byte[] value, final LineString<?> lineString) {
		checkIsInMulti();
		client.ggadd(key, member, value, lineString);
		return client.getIntegerReply();
	}

	@Override
	public List<Geometry<String>> ggrange(final String key, final long start, final long stop) {
		checkIsInMulti();
		client.ggrange(key, start, stop);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrange(final byte[] key, final long start, final long stop) {
		checkIsInMulti();
		client.ggrange(key, start, stop);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}
}
