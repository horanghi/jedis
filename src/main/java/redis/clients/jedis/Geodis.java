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
	public Long gpexists(String key, String member) {
		checkIsInMulti();
		client.gpexists(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gpexists(byte[] key, byte[] member) {
		checkIsInMulti();
		client.gpexists(key, member);
		return client.getIntegerReply();
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
	public Long gpadd(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String member,
			final String value) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gpadd(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] member,
			final byte[] value) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gpadd(final String key, final double lat, final double lon, final String member, final String value, final double score) {
		return gpadd(key, lat, lon, 0, M, member, value, score);
	}

	@Override
	public Long gpadd(final byte[] key, final double lat, final double lon, final byte[] member, final byte[] value, final double score) {
		return gpadd(key, lat, lon, 0, M, member, value, score);
	}

	@Override
	public Long gpadd(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String member,
			final String value, final double score) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpadd(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] member,
			final byte[] value, final double score) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value, score);
		return client.getIntegerReply();
	}

	// gpupdate

	@Override
	public Long gpupdate(final String key, final String member, final double lat, final double lon) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final byte[] key, final byte[] member, final double lat, final double lon) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final String key, final String member, double lat, double lon, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon, radius, unit);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final byte[] key, final byte[] member, double lat, double lon, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon, radius, unit);
		return client.getIntegerReply();
	}

	// gpradius

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String pattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] pattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String min, final String max, final String pattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, pattern);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] min, final byte[] max, final byte[] pattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, pattern);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String min, final String max, final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, order);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] min, final byte[] max, final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, order);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String min, final String max, final String pattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, pattern, offset, count, order);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] min, final byte[] max, final byte[] pattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, pattern, offset, count, order);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	// gpcircle

	@Override
	public List<Circle<String>> gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, scope, order);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, scope, order);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String pattern) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, pattern);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] pattern) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, pattern);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String pattern, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, pattern, scope, order);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] pattern, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, pattern, scope, order);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	// gpradiusByMember

	@Override
	public List<Point<String>> gpradiusByMember(final String key, final String bykey, final String bymember) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(final byte[] key, final byte[] bykey, final byte[] bymember) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(final String key, final String bykey, final String bymember, final String pattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] pattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String pattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, pattern);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] min,
			final byte[] max, final byte[] pattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, pattern);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String pattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, pattern, offset, count, order);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] pattern,
			long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, pattern, offset, count, order);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	// gpregionByMember

	@Override
	public List<Point<String>> gpregionByMember(final String key, final String bykey, final String bymember) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(final byte[] key, final byte[] bykey, final byte[] bymember) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(final String key, final String bykey, final String bymember, final String pattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] pattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String pattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, pattern);
		return client.getSpatialWithScoreMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] min,
			final byte[] max, final byte[] pattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, pattern);
		return client.getBinarySpatialWithScoreMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String pattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, pattern, offset, count, order);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] min,
			final byte[] max, final byte[] pattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, pattern, offset, count, order);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	// gpnn

	@Override
	public List<Point<String>> gpnn(final String key, final double lat, final double lon, final long offset, final long count) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpnn(final String key, final double lat, final double lon, final long offset, final long count,
			final String pattern) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, pattern);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count,
			final byte[] pattern) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, pattern);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpnn(final String key, final double lat, final double lon, final long offset, final long count,
			final String pattern, final String min, final String max, final ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, pattern, min, max, order);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count,
			final byte[] pattern, final byte[] min, final byte[] max, final ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, pattern, min, max, order);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	// gpresion

	@Override
	public List<Point<String>> gpregion(final String key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gpregion(key, polygon);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gpregion(key, polygon);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final LineString<?> lineString) {
		checkIsInMulti();
		client.gpregion(key, lineString);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final LineString<?> lineString) {
		checkIsInMulti();
		client.gpregion(key, lineString);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Point<?> point) {
		checkIsInMulti();
		client.gpregion(key, point);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Point<?> point) {
		checkIsInMulti();
		client.gpregion(key, point);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Polygon<?> polygon, final String pattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, pattern);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Polygon<?> polygon, final byte[] pattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, pattern);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, Polygon<?> polygon, String min, String max, String pattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, pattern);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, Polygon<?> polygon, byte[] min, byte[] max, byte[] pattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, pattern);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, LineString<?> lineString, String min, String max, String pattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max, pattern);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, LineString<?> lineString, byte[] min, byte[] max, byte[] pattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max,  pattern);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Polygon<?> polygon, final String min, final String max, final long offset,
			final long count, final String pattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, offset, count, pattern);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Polygon<?> polygon, final byte[] min, final byte[] max, final long offset,
			final long count, final byte[] pattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, offset, count, pattern);
		return client.getBinarySpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final LineString<?> lineString, final String pattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, pattern);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final LineString<?> lineString, final byte[] pattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, pattern);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Point<?> point, final String pattern) {
		checkIsInMulti();
		client.gpregion(key, point, pattern);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Point<?> point, final byte[] pattern) {
		checkIsInMulti();
		client.gpregion(key, point, pattern);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final LineString<?> lineString, final String min, final String max,
			final long offset, final long count, final String pattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max, offset, count, pattern);
		return client.getSpatialWithScoreMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final LineString<?> lineString, final byte[] min, final byte[] max,
			final long offset, final long count, final byte[] pattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max, offset, count, pattern);
		return client.getBinarySpatialWithScoreMultiBulkReply();
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
		return client.getSpatialWithScoreMGETMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.gpmget(key, members);
		return client.getBinarySpatialWithScoreMGETMultiBulkReply();
	}

	@Override
	public double gpdistance(final double lat1, final double lng1, final double lat2, final double lng2) {
		double earthRadius = 6378137; // meters (EPSG 3785)
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);

		return dist;
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
	public List<Geometry<String>> ggrelationByMember(final String key, final String byKey, final String byMember) {
		checkIsInMulti();
		client.ggrelationByMember(key, byKey, byMember);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelationByMember(final byte[] key, final byte[] byKey, final byte[] byMember) {
		checkIsInMulti();
		client.ggrelationByMember(key, byKey, byMember);
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
		client.ggnn(key, lat, lon, count, pattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnn(final byte[] key, final double lat, final double lon, final long count, final byte[] pattern) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, pattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	/* Geometry */

	@Override
	public double gmdistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	@Override
	public String gmsetBoundary(final String key, final double minx, final double miny, final double maxx, final double maxy) {
		checkIsInMulti();
		client.gmsetBoundary(key, minx, miny, maxx, maxy);
		return client.getStatusCodeReply();
	}

	@Override
	public String gmsetBoundary(final byte[] key, final double minx, final double miny, final double maxx, final double maxy) {
		checkIsInMulti();
		client.gmsetBoundary(key, minx, miny, maxx, maxy);
		return client.getStatusCodeReply();
	}

	@Override
	public List<Point<String>> gmgetBoundary(final String key) {
		checkIsInMulti();
		client.gmgetBoundary(key);
		return client.getSpatialBoundaryReply();
	}

	@Override
	public List<Point<byte[]>> gmgetBoundary(final byte[] key) {
		checkIsInMulti();
		client.gmgetBoundary(key);
		return client.getBinarySpatialBoundaryReply();
	}

	@Override
	public Long gmrebuildBoundary(final String key, final double minx, final double miny, final double maxx, final double maxy) {
		checkIsInMulti();
		client.gmrebuildBoundary(key, minx, miny, maxx, maxy);
		return client.getIntegerReply();
	}

	@Override
	public Long gmrebuildBoundary(final byte[] key, final double minx, final double miny, final double maxx, final double maxy) {
		checkIsInMulti();
		client.gmrebuildBoundary(key, minx, miny, maxx, maxy);
		return client.getIntegerReply();

	}

	@Override
	public Long gmadd(final String key, final String member, final String value, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gmadd(key, member, value, polygon);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(final byte[] key, final byte[] member, final byte[] value, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gmadd(key, member, value, polygon);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(final String key, final String member, final String value, final LineString<?> lineString) {
		checkIsInMulti();
		client.gmadd(key, member, value, lineString);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(final byte[] key, final byte[] member, final byte[] value, final LineString<?> lineString) {
		checkIsInMulti();
		client.gmadd(key, member, value, lineString);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(final String key, String member, final String value, final Point<?> point) {
		checkIsInMulti();
		client.gmadd(key, member, value, point);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(final byte[] key, final byte[] member, final byte[] value, final Point<?> point) {
		checkIsInMulti();
		client.gmadd(key, member, value, point);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(final String key, final double x, final double y, final String member, final String value) {
		checkIsInMulti();
		client.gmadd(key, x, y, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(final byte[] key, final double x, final double y, final byte[] member, final byte[] value) {
		checkIsInMulti();
		client.gmadd(key, x, y, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(final String key, final String member, final Point<?> point) {
		checkIsInMulti();
		client.gmupdate(key, member, point);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(final byte[] key, final byte[] member, final Point<?> point) {
		checkIsInMulti();
		client.gmupdate(key, member, point);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(final String key, final String member, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gmupdate(key, member, polygon);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(final byte[] key, final byte[] member, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gmupdate(key, member, polygon);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(final String key, final String member, final LineString<?> lineString) {
		checkIsInMulti();
		client.gmupdate(key, member, lineString);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(final byte[] key, final byte[] member, final LineString<?> lineString) {
		checkIsInMulti();
		client.gmupdate(key, member, lineString);
		return client.getIntegerReply();
	}

	@Override
	public List<Geometry<String>> gmrange(final String key, final long start, final long stop) {
		checkIsInMulti();
		client.gmrange(key, start, stop);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrange(final byte[] key, final long start, final long stop) {
		checkIsInMulti();
		client.gmrange(key, start, stop);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrevrange(final String key, final long start, final long stop) {
		checkIsInMulti();
		client.gmrevrange(key, start, stop);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrevrange(final byte[] key, final long start, final long stop) {
		checkIsInMulti();
		client.gmrevrange(key, start, stop);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public Long gmcard(final String key) {
		checkIsInMulti();
		client.gmcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gmcard(final byte[] key) {
		checkIsInMulti();
		client.gmcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gmrem(final String key, final String member) {
		checkIsInMulti();
		client.gmrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gmrem(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gmrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Geometry<String> gmget(final String key, final String member) {
		checkIsInMulti();
		client.gmget(key, member);
		return client.getSpatialGETGEOMultiBulkReply();
	}

	@Override
	public Geometry<byte[]> gmget(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gmget(key, member);
		return client.getBinarySpatialGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmmget(final String key, final String... members) {
		checkIsInMulti();
		client.gmmget(key, members);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.gmmget(key, members);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelation(final String key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gmrelation(key, polygon);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelation(final byte[] key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gmrelation(key, polygon);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelation(final String key, final LineString<?> lineString) {
		checkIsInMulti();
		client.gmrelation(key, lineString);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelation(final byte[] key, final LineString<?> lineString) {
		checkIsInMulti();
		client.gmrelation(key, lineString);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelation(final String key, final Point<?> point) {
		checkIsInMulti();
		client.gmrelation(key, point);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelation(final byte[] key, final Point<?> point) {
		checkIsInMulti();
		client.gmrelation(key, point);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelationByMember(final String key, final String byKey, final String byMember) {
		checkIsInMulti();
		client.gmrelationByMember(key, byKey, byMember);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelationByMember(final byte[] key, final byte[] byKey, final byte[] byMember) {
		checkIsInMulti();
		client.gmrelationByMember(key, byKey, byMember);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmnn(final String key, final double x, final double y, final long count) {
		checkIsInMulti();
		client.gmnn(key, x, y, count);
		return client.getSpatialMGETGEOWithDistanceMultiWBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmnn(final byte[] key, final double x, final double y, final long count) {
		checkIsInMulti();
		client.gmnn(key, x, y, count);
		return client.getBinarySpatialMGETGEOWithDistanceMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmnn(final String key, final double x, final double y, final long count, final String pattern) {
		checkIsInMulti();
		client.gmnn(key, x, y, count, pattern);
		return client.getSpatialMGETGEOWithDistanceMultiWBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmnn(final byte[] key, final double x, final double y, final long count, final byte[] pattern) {
		checkIsInMulti();
		client.gmnn(key, x, y, count, pattern);
		return client.getBinarySpatialMGETGEOWithDistanceMultiBulkReply();
	}

	// exists

	@Override
	public Long ggexists(String key, String member) {
		checkIsInMulti();
		client.ggexists(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long ggexists(byte[] key, byte[] member) {
		checkIsInMulti();
		client.ggexists(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gmexists(String key, String member) {
		checkIsInMulti();
		client.gmexists(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gmexists(byte[] key, byte[] member) {
		checkIsInMulti();
		client.gmexists(key, member);
		return client.getIntegerReply();
	}

}
