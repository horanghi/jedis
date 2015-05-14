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
		return gadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gadd(final byte[] key, final double lat, final double lon, final byte[] member, final byte[] value) {
		return gadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gadd(final String key, final double lat, final double lon, final double distance, final UNITS unit, final String member,
			final String value) {
		checkIsInMulti();
		client.gadd(key, lat, lon, distance, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gadd(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit, final byte[] member,
			final byte[] value) {
		checkIsInMulti();
		client.gadd(key, lat, lon, distance, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public List<Point<String>> grangeByRadius(final String key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.grangeByRadius(key, lat, lon, distance, unit);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> grangeByRadius(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.grangeByRadius(key, lat, lon, distance, unit);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Circle<String>> grangeCircleByRadius(final String key, final double lat, final double lon, final double distance,
			final UNITS unit) {
		checkIsInMulti();
		client.grangeCircleByRadius(key, lat, lon, distance, unit);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> grangeCircleByRadius(final byte[] key, final double lat, final double lon, final double distance,
			final UNITS unit) {
		checkIsInMulti();
		client.grangeCircleByRadius(key, lat, lon, distance, unit);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Point<String>> grangeByRadiusWithMatch(final String key, final double lat, final double lon, final double distance,
			final UNITS unit, final String pattern) {
		checkIsInMulti();
		client.grangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> grangeByRadiusWithMatch(final byte[] key, final double lat, final double lon, final double distance,
			final UNITS unit, final byte[] pattern) {
		checkIsInMulti();
		client.grangeByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Circle<String>> grangeCircleByRadiusWithMatch(final String key, final double lat, final double lon, final double distance,
			final UNITS unit, final String pattern) {
		checkIsInMulti();
		client.grangeCircleByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> grangeCircleByRadiusWithMatch(final byte[] key, final double lat, final double lon, final double distance,
			final UNITS unit, final byte[] pattern) {
		checkIsInMulti();
		client.grangeCircleByRadiusWithMatch(key, lat, lon, distance, unit, pattern);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public Long gcard(final String key) {
		checkIsInMulti();
		client.gcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gcard(final byte[] key) {
		checkIsInMulti();
		client.gcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long grem(final String key, final String member) {
		checkIsInMulti();
		client.grem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long grem(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.grem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public List<Point<String>> gnn(final String key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gnn(key, lat, lon, count);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gnn(final byte[] key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gnn(key, lat, lon, count);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public Point<String> gget(final String key, final String member) {
		checkIsInMulti();
		client.gget(key, member);
		return client.getSpatialGETMultiBulkReply();

	}

	@Override
	public Point<byte[]> gget(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gget(key, member);
		return client.getBinarySpatialGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gmget(final String key, final String... members) {
		checkIsInMulti();
		client.gmget(key, members);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.gmget(key, members);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> grangeByRegion(final String key, final Polygon<String> polygon) {
		checkIsInMulti();
		client.grangeByRegion(key, polygon);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> grangeByRegion(final byte[] key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.grangeByRegion(key, polygon);
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
	public Long ggadd(final String key, final String member, final String value, final Point<String> point) {
		checkIsInMulti();
		client.ggadd(key, member, value, point);
		return client.getIntegerReply();
	}

	@Override
	public Long ggadd(final byte[] key, final byte[] member, final byte[] value, final Point<?> point) {
		checkIsInMulti();
		client.ggadd(key, member, value, point);
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

	@Override
	public List<Geometry<String>> ggrevrange(final String key, final long start, final long stop) {
		checkIsInMulti();
		client.ggrevrange(key, start, stop);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrevrange(final byte[] key, final long start, final long stop) {
		checkIsInMulti();
		client.ggrevrange(key, start, stop);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public Long ggcard(final String key) {
		checkIsInMulti();
		client.ggcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long ggcard(final byte[] key) {
		checkIsInMulti();
		client.ggcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long ggrem(final String key, final String member) {
		checkIsInMulti();
		client.ggrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long ggrem(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.ggrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Geometry<String> ggget(final String key, final String member) {
		checkIsInMulti();
		client.ggget(key, member);
		return client.getSpatialGETGEOMultiBulkReply();
	}

	@Override
	public Geometry<byte[]> ggget(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.ggget(key, member);
		return client.getBinarySpatialGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggmget(final String key, final String... members) {
		checkIsInMulti();
		client.ggmget(key, members);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.ggmget(key, members);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrelation(final String key, final Polygon<String> polygon) {
		checkIsInMulti();
		client.ggrelation(key, polygon);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelation(final byte[] key, final Polygon<byte[]> polygon) {
		checkIsInMulti();
		client.ggrelation(key, polygon);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrelation(final String key, final LineString<String> lineString) {
		checkIsInMulti();
		client.ggrelation(key, lineString);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelation(final byte[] key, final LineString<byte[]> lineString) {
		checkIsInMulti();
		client.ggrelation(key, lineString);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrelation(final String key, final Point<String> point) {
		checkIsInMulti();
		client.ggrelation(key, point);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelation(final byte[] key, final Point<byte[]> point) {
		checkIsInMulti();
		client.ggrelation(key, point);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggnn(final String key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnn(final byte[] key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggnnWithMatch(final String key, final double lat, final double lon, final long count, final String pattern) {
		checkIsInMulti();
		client.ggnnWithMatch(key, lat, lon, count, pattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnnWithMatch(final byte[] key, final double lat, final double lon, final long count, final byte[] pattern) {
		checkIsInMulti();
		client.ggnnWithMatch(key, lat, lon, count, pattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

}
