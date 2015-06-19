package redis.clients.jedis;

import static redis.clients.jedis.Protocol.DEFAULT_TIMEOUT;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.net.URI;
import java.util.List;

import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.ORDERBY;
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
	public Long gpadd(final String key, final double lat, final double lon, final String member, final String value) {
		return gpadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gpadd(final byte[] key, final double lat, final double lon, final byte[] member, final byte[] value) {
		return gpadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gpadd(final String key, final double lat, final double lon, final long radius, final UNITS unit, final String member,
			final String value) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gpadd(final byte[] key, final double lat, final double lon, final long radius, final UNITS unit, final byte[] member,
			final byte[] value) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final String key, final double lat, final double lon, final String member) {
		checkIsInMulti();
		client.gpupdate(key, lat, lon, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final byte[] key, final double lat, final double lon, final byte[] member) {
		checkIsInMulti();
		client.gpupdate(key, lat, lon, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final String key, double lat, double lon, long radius, UNITS unit, final String member) {
		checkIsInMulti();
		client.gpupdate(key, lat, lon, radius, unit, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final byte[] key, double lat, double lon, long radius, UNITS unit, final byte[] member) {
		checkIsInMulti();
		client.gpupdate(key, lat, lon, radius, unit, member);
		return client.getIntegerReply();
	}

	@Override
	public List<Point<String>> gprangeByRadius(final String key, final double lat, final double lon, final long radius, final UNITS unit) {
		checkIsInMulti();
		client.gprangeByRadius(key, lat, lon, radius, unit);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeByRadius(final byte[] key, final double lat, final double lon, final long radius, final UNITS unit) {
		checkIsInMulti();
		client.gprangeByRadius(key, lat, lon, radius, unit);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gprangeCircleByRadius(final String key, final double lat, final double lon, final long radius,
			final UNITS unit) {
		checkIsInMulti();
		client.gprangeCircleByRadius(key, lat, lon, radius, unit);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gprangeCircleByRadius(final byte[] key, final double lat, final double lon, final long radius,
			final UNITS unit) {
		checkIsInMulti();
		client.gprangeCircleByRadius(key, lat, lon, radius, unit);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gprangeCircleByRadius(final String key, final double lat, final double lon, final long radius,
			final UNITS unit, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gprangeCircleByRadius(key, lat, lon, radius, unit, scope, order);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gprangeCircleByRadius(final byte[] key, final double lat, final double lon, final long radius,
			final UNITS unit, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gprangeCircleByRadius(key, lat, lon, radius, unit, scope, order);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprangeByRadius(final String key, final double lat, final double lon, final long radius, final UNITS unit,
			final String pattern) {
		checkIsInMulti();
		client.gprangeByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeByRadius(final byte[] key, final double lat, final double lon, final long radius, final UNITS unit,
			final byte[] pattern) {
		checkIsInMulti();
		client.gprangeByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gprangeCircleByRadius(final String key, final double lat, final double lon, final long radius,
			final UNITS unit, final String pattern) {
		checkIsInMulti();
		client.gprangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gprangeCircleByRadius(final byte[] key, final double lat, final double lon, final long radius,
			final UNITS unit, final byte[] pattern) {
		checkIsInMulti();
		client.gprangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gprangeCircleByRadius(final String key, final double lat, final double lon, final long radius,
			final UNITS unit, final String pattern, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gprangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern, scope, order);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gprangeCircleByRadius(final byte[] key, final double lat, final double lon, final long radius,
			final UNITS unit, final byte[] pattern, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gprangeCircleByRadiusWithMatch(key, lat, lon, radius, unit, pattern, scope, order);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public Long gpcard(final String key) {
		checkIsInMulti();
		client.gpcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gpcard(final byte[] key) {
		checkIsInMulti();
		client.gpcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gprem(final String key, final String member) {
		checkIsInMulti();
		client.gprem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gprem(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gprem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public List<Point<String>> gpnn(final String key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, count);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(final byte[] key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, count);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public Point<String> gpget(final String key, final String member) {
		checkIsInMulti();
		client.gpget(key, member);
		return client.getSpatialGETMultiBulkReply();

	}

	@Override
	public Point<byte[]> gpget(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gpget(key, member);
		return client.getBinarySpatialGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpmget(final String key, final String... members) {
		checkIsInMulti();
		client.gpmget(key, members);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.gpmget(key, members);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprangeByRegion(final String key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gprangeByRegion(key, polygon);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeByRegion(final byte[] key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gprangeByRegion(key, polygon);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprangeByRegion(final String key, final LineString<?> lineString) {
		checkIsInMulti();
		client.gprangeByRegion(key, lineString);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeByRegion(final byte[] key, final LineString<?> lineString) {
		checkIsInMulti();
		client.gprangeByRegion(key, lineString);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprangeByRegion(final String key, final Point<?> point) {
		checkIsInMulti();
		client.gprangeByRegion(key, point);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeByRegion(final byte[] key, final Point<?> point) {
		checkIsInMulti();
		client.gprangeByRegion(key, point);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprangeByRegion(final String key, final Polygon<?> polygon, final String pattern) {
		checkIsInMulti();
		client.gprangeByRegionWithMatch(key, polygon, pattern);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeByRegion(final byte[] key, final Polygon<?> polygon, final byte[] pattern) {
		checkIsInMulti();
		client.gprangeByRegionWithMatch(key, polygon, pattern);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprangeByRegion(final String key, final Polygon<?> polygon, final String pattern, final long count) {
		checkIsInMulti();
		client.gprangeByRegionWithMatch(key, polygon, pattern, count);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeByRegion(final byte[] key, final Polygon<?> polygon, final byte[] pattern, final long count) {
		checkIsInMulti();
		client.gprangeByRegionWithMatch(key, polygon, pattern, count);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprangeByRegion(final String key, final LineString<?> lineString, final String pattern) {
		checkIsInMulti();
		client.gprangeByRegionWithMatch(key, lineString, pattern);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeByRegion(final byte[] key, final LineString<?> lineString, final byte[] pattern) {
		checkIsInMulti();
		client.gprangeByRegionWithMatch(key, lineString, pattern);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprangeByRegion(final String key, final Point<?> point, final String pattern) {
		checkIsInMulti();
		client.gprangeByRegionWithMatch(key, point, pattern);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeByRegion(final byte[] key, final Point<?> point, final byte[] pattern) {
		checkIsInMulti();
		client.gprangeByRegionWithMatch(key, point, pattern);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public double distance(final double dLat1, final double dLon1, final double dLat2, final double dLon2) {
		// d = |ax1 + by1 + c | / sqrt(a^2 + b^2)
		return Math.acos(Math.sin(dLat1) * Math.sin(dLat2) + Math.cos(dLat1) * Math.cos(dLat2) * Math.cos(dLon1 - dLon2));
	}

	@Override
	public Long ggadd(final String key, final String member, final String value, final Polygon<?> polygon) {
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
	public Long ggadd(final String key, final String member, final String value, final LineString<?> lineString) {
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
	public List<Point<String>> gprangeBy(final String key, final String bykey, final String bymember) {
		checkIsInMulti();
		client.gprangeBy(key, bykey, bymember);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeBy(final byte[] key, final byte[] bykey, final byte[] bymember) {
		checkIsInMulti();
		client.gprangeBy(key, bykey, bymember);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprangeBy(final String key, final String bykey, final String bymember, final String pattern, final long count) {
		checkIsInMulti();
		client.gprangeByWithMatch(key, bykey, bymember, pattern, count);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprangeBy(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] pattern, final long count) {
		checkIsInMulti();
		client.gprangeByWithMatch(key, bykey, bymember, pattern, count);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public Long ggadd(String key, String member, String value, Point<?> point) {
		checkIsInMulti();
		client.ggadd(key, member, value, point);
		return client.getIntegerReply();
	}

	@Override
	public Long ggadd(byte[] key, byte[] member, byte[] value, Point<?> point) {
		checkIsInMulti();
		client.ggadd(key, member, value, point);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(final String key, final String member, final Point<?> point) {
		checkIsInMulti();
		client.ggupdate(key, member, point);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(final byte[] key, final byte[] member, final Point<?> point) {
		checkIsInMulti();
		client.ggupdate(key, member, point);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(final String key, final String member, final Polygon<?> polygon) {
		checkIsInMulti();
		client.ggupdate(key, member, polygon);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(final byte[] key, final byte[] member, final Polygon<?> polygon) {
		checkIsInMulti();
		client.ggupdate(key, member, polygon);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(final String key, final String member, final LineString<?> lineString) {
		checkIsInMulti();
		client.ggupdate(key, member, lineString);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(final byte[] key, final byte[] member, final LineString<?> lineString) {
		checkIsInMulti();
		client.ggupdate(key, member, lineString);
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
	public List<Geometry<String>> ggrelation(final String key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.ggrelation(key, polygon);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelation(final byte[] key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.ggrelation(key, polygon);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrelation(final String key, final LineString<?> lineString) {
		checkIsInMulti();
		client.ggrelation(key, lineString);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelation(final byte[] key, final LineString<?> lineString) {
		checkIsInMulti();
		client.ggrelation(key, lineString);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrelation(final String key, final Point<?> point) {
		checkIsInMulti();
		client.ggrelation(key, point);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelation(final byte[] key, final Point<?> point) {
		checkIsInMulti();
		client.ggrelation(key, point);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrelationBy(final String key, final String byKey, final String byMember) {
		checkIsInMulti();
		client.ggrelationBy(key, byKey, byMember);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelationBy(final byte[] key, final byte[] byKey, final byte[] byMember) {
		checkIsInMulti();
		client.ggrelationBy(key, byKey, byMember);
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
	public List<Geometry<String>> ggnn(final String key, final double lat, final double lon, final long count, final String pattern) {
		checkIsInMulti();
		client.ggnnWithMatch(key, lat, lon, count, pattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnn(final byte[] key, final double lat, final double lon, final long count, final byte[] pattern) {
		checkIsInMulti();
		client.ggnnWithMatch(key, lat, lon, count, pattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

}