package redis.clients.jedis;

import static redis.clients.jedis.Protocol.DEFAULT_TIMEOUT;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.LineStringRange;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

abstract class Geodis extends BinaryJedis implements GeoCommands {

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

	@Override
	public Long gpupdate(final String key, final String member, double lat, double lon, double radius, UNITS unit, final String value,
			double score) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon, radius, unit, value, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final byte[] key, final byte[] member, double lat, double lon, double radius, UNITS unit, final byte[] value,
			double score) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon, radius, unit, value, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final String key, final String member, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpupdate(key, member, radius, unit);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final byte[] key, final byte[] member, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpupdate(key, member, radius, unit);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final String key, final String member, double score) {
		checkIsInMulti();
		client.gpupdate(key, member, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final byte[] key, final byte[] member, double score) {
		checkIsInMulti();
		client.gpupdate(key, member, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final String key, final String member, final String value) {
		checkIsInMulti();
		client.gpupdate(key, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(final byte[] key, final byte[] member, final byte[] value) {
		checkIsInMulti();
		client.gpupdate(key, member, value);
		return client.getIntegerReply();
	}

	// gpradius

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String valuePattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] valuePattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String min, final String max, final String valuePattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] min, final byte[] max, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String min, final String max, final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] min, final byte[] max, final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String min, final String max, final String valuePattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] min, final byte[] max, final byte[] valuePattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String min, final String max, final String memberPattern, final String valuePattern, final long offset, final long count,
			final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] min, final byte[] max, final byte[] memberPattern, final byte[] valuePattern, final long offset, final long count,
			final ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	// gpcircle

	@Override
	public Circle<String> gpcircle(final String key, final String member) {
		checkIsInMulti();
		client.gpcircle(key, member);
		return client.getSPATIAL_CIRCLEReply();
	}

	@Override
	public Circle<byte[]> gpcircle(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gpcircle(key, member);
		return client.getBYTE_SPATIAL_CIRCLEReply();
	}

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
			final String valuePattern) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, valuePattern);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] valuePattern) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, valuePattern);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String valuePattern, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, valuePattern, scope, order);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] valuePattern, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, valuePattern, scope, order);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gpcircle(final String key, final double lat, final double lon, final double radius, UNITS unit,
			final String memberPattern, final String valuePattern, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, memberPattern, valuePattern, scope, order);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] memberPattern, final byte[] valuePattern, final RELATION scope, final ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, memberPattern, valuePattern, scope, order);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	// gpradiusByMember

	@Override
	public List<Point<String>> gpradiusByMember(final String key, final String bykey, final String bymember) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(final byte[] key, final byte[] bykey, final byte[] bymember) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(final String key, final String bykey, final String bymember, final String valuePattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String valuePattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] min,
			final byte[] max, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String valuePattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] valuePattern,
			long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String memberPattern, final String valuePattern, final long offset, final long count,
			final ORDERBY order) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern, long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	// gpregionByMember

	@Override
	public List<Point<String>> gpregionByMember(final String key, final String bykey, final String bymember) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(final byte[] key, final byte[] bykey, final byte[] bymember) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(final String key, final String bykey, final String bymember, final String valuePattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String valuePattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] min,
			final byte[] max, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String valuePattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] min,
			final byte[] max, final byte[] valuePattern, final long offset, final long count, final ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(final String key, final String bykey, final String bymember, final String min,
			final String max, final String memberPattern, final String valuePattern, final long offset, final long count,
			final ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(final byte[] key, final byte[] bykey, final byte[] bymember, final byte[] min,
			final byte[] max, final byte[] memberPattern, final byte[] valuePattern, final long offset, final long count,
			final ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	// gpnn

	@Override
	public List<Point<String>> gpnn(final String key, final double lat, final double lon, final long offset, final long count) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpnn(final String key, final double lat, final double lon, final long offset, final long count,
			final String valuePattern) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count,
			final byte[] valuePattern) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpnn(final String key, final double lat, final double lon, final long offset, final long count,
			final String valuePattern, final String min, final String max, final ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, valuePattern, min, max, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count,
			final byte[] valuePattern, final byte[] min, final byte[] max, final ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, valuePattern, min, max, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpnn(final String key, final double lat, final double lon, final long offset, final long count,
			final String memberPattern, final String valuePattern, final String min, final String max, final ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, memberPattern, valuePattern, min, max, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count,
			final byte[] memberPattern, final byte[] valuePattern, final byte[] min, final byte[] max, final ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, memberPattern, valuePattern, min, max, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	// gpresion

	@Override
	public List<Point<String>> gpregion(final String key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gpregion(key, polygon);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Polygon<?> polygon) {
		checkIsInMulti();
		client.gpregion(key, polygon);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final LineString<?> lineString) {
		checkIsInMulti();
		client.gpregion(key, lineString);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final LineString<?> lineString) {
		checkIsInMulti();
		client.gpregion(key, lineString);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Point<?> point) {
		checkIsInMulti();
		client.gpregion(key, point);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Point<?> point) {
		checkIsInMulti();
		client.gpregion(key, point);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Polygon<?> polygon, final String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Polygon<?> polygon, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, Polygon<?> polygon, String min, String max, String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, Polygon<?> polygon, byte[] min, byte[] max, byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, LineString<?> lineString, String min, String max, String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, LineString<?> lineString, byte[] min, byte[] max, byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Polygon<?> polygon, final String min, final String max, final long offset,
			final long count, final String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, offset, count, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Polygon<?> polygon, final byte[] min, final byte[] max, final long offset,
			final long count, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, offset, count, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Polygon<?> polygon, final String min, final String max, final long offset,
			final long count, final String memberPattern, final String valuePattern, final ORDERBY order) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Polygon<?> polygon, final byte[] min, final byte[] max, final long offset,
			final long count, final byte[] memberPattern, final byte[] valuePattern, final ORDERBY order) {
		checkIsInMulti();
		client.gpregion(key, polygon, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final LineString<?> lineString, final String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final LineString<?> lineString, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Point<?> point, final String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, point, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Point<?> point, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, point, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final Point<?> point, final String memberPattern, final String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, point, memberPattern, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final Point<?> point, final byte[] memberPattern, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, point, memberPattern, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final LineString<?> lineString, final String min, final String max,
			final long offset, final long count, final String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max, offset, count, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final LineString<?> lineString, final byte[] min, final byte[] max,
			final long offset, final long count, final byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max, offset, count, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(final String key, final LineString<?> lineString, final String min, final String max,
			final long offset, final long count, final String memberPattern, final String valuePattern, final ORDERBY order) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(final byte[] key, final LineString<?> lineString, final byte[] min, final byte[] max,
			final long offset, final long count, final byte[] memberPattern, final byte[] valuePattern, final ORDERBY order) {
		checkIsInMulti();
		client.gpregion(key, lineString, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
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
		return client.getSPATIAL_GPOINTMultiBulkReply();

	}

	@Override
	public Point<byte[]> gpget(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gpget(key, member);
		return client.getBYTE_SPATIAL_GPOINTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpmget(final String key, final String... members) {
		checkIsInMulti();
		client.gpmget(key, members);
		return client.getSPATIAL_GPOINT_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.gpmget(key, members);
		return client.getBYTE_SPATIAL_GPOINT_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpscope(final String key, final String min, final String max, final String valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, valuePattern, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpscope(final byte[] key, final byte[] min, final byte[] max, final byte[] valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, valuePattern, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpscope(final String key, final String min, final String max, final long offset, final long count,
			final String valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, offset, count, valuePattern, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpscope(final byte[] key, final byte[] min, final byte[] max, final long offset, final long count,
			final byte[] valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, offset, count, valuePattern, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpscope(final String key, final String min, final String max, final long offset, final long count,
			final String memberPattern, final String valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpscope(final byte[] key, final byte[] min, final byte[] max, final long offset, final long count,
			final byte[] memberPattern, final byte[] valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public double gpdistance(final double lat1, final double lng1, final double lat2, final double lng2) {
		double earthRadius = GeoUtils.EarthRadius; // meters (EPSG 3785)
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
	public List<Geometry<String>> ggnn(final String key, final double lat, final double lon, final long count, final String valuePattern) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, valuePattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnn(final byte[] key, final double lat, final double lon, final long count, final byte[] valuePattern) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, valuePattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggnn(final String key, final double lat, final double lon, final long count, final String memberPattern,
			final String valuePattern) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnn(final byte[] key, final double lat, final double lon, final long count, final byte[] memberPattern,
			final byte[] valuePattern) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern);
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
	public List<Geometry<String>> gmnn(final String key, final double x, final double y, final long count, final String valuePattern) {
		checkIsInMulti();
		client.gmnn(key, x, y, count, valuePattern);
		return client.getSpatialMGETGEOWithDistanceMultiWBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmnn(final byte[] key, final double x, final double y, final long count, final byte[] valuePattern) {
		checkIsInMulti();
		client.gmnn(key, x, y, count, valuePattern);
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

	// TODO

//	public List<Point<String>> gpregion(final String key, final LineStringRange lineRange, final String min, final String max,
//			final long offset, final long count, final String memberPattern, final String valuePattern, final ORDERBY order) {
//
//		return null;
//	}
//
//	@Override
//	public List<Point<String>> gpregion(String key, LineStringRange lineRange) {
//		Map<String, Point<String>> resultMap = new HashMap<String, Point<String>>();
//		List<Circle<?>> circles = lineRange.getRangeCircles();
//		List<Polygon<?>> polygons = lineRange.getRangeRectangles();
//
//		for (int idx = 0; idx < circles.size(); idx++) {
//			resultMap.this.gpradius(key, circles.get(idx).getX(), circles.get(idx).getY(), circles.get(idx).getDistance(), circles.get(idx)
//					.getUnit());
//		}
//
//		return null;
//	}
//
//	@Override
//	public List<Point<byte[]>> gpregion(byte[] key, LineStringRange lineRange) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Point<String>> gpregion(String key, LineStringRange lineRange, String valuePattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Point<byte[]>> gpregion(byte[] key, LineStringRange lineRange, byte[] valuePattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Point<String>> gpregion(String key, LineStringRange lineRange, String min, String max, String valuePattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Point<byte[]>> gpregion(byte[] key, LineStringRange lineRange, byte[] min, byte[] max, byte[] valuePattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Point<String>> gpregion(String key, LineStringRange lineRange, String min, String max, long offset, long count,
//			String valuePattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Point<byte[]>> gpregion(byte[] key, LineStringRange lineRange, byte[] min, byte[] max, long offset, long count,
//			byte[] valuePattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Point<byte[]>> gpregion(byte[] key, LineStringRange lineRange, byte[] min, byte[] max, long offset, long count,
//			byte[] memberPattern, byte[] valuePattern, ORDERBY order) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
