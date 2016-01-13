package redis.clients.jedis;

import static redis.clients.jedis.Protocol.DEFAULT_TIMEOUT;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.LineStringBuffer;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

import com.vividsolutions.jts.operation.distance.DistanceOp;

class Geodis extends BinaryJedis implements GeoCommands {

	public Geodis(String host) {
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
	public Long gpadd(String key, double lat, double lon, String member, String value) {
		return gpadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value) {
		return gpadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gpadd(String key, double lat, double lon, String member, String value, double score) {
		return gpadd(key, lat, lon, 0, M, member, value, score);
	}

	@Override
	public Long gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value, double score) {
		return gpadd(key, lat, lon, 0, M, member, value, score);
	}

	@Override
	public Long gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value, double score) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value, double score) {
		checkIsInMulti();
		client.gpadd(key, lat, lon, radius, unit, member, value, score);
		return client.getIntegerReply();
	}

	// gpupdate

	@Override
	public Long gpupdate(String key, String member, double lat, double lon) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(byte[] key, byte[] member, double lat, double lon) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(String key, String member, double lat, double lon, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon, radius, unit);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon, radius, unit);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(String key, String member, double lat, double lon, double radius, UNITS unit, String value, double score) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon, radius, unit, value, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit, byte[] value, double score) {
		checkIsInMulti();
		client.gpupdate(key, member, lat, lon, radius, unit, value, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(String key, String member, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpupdate(key, member, radius, unit);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(byte[] key, byte[] member, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpupdate(key, member, radius, unit);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(String key, String member, double score) {
		checkIsInMulti();
		client.gpupdate(key, member, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(byte[] key, byte[] member, double score) {
		checkIsInMulti();
		client.gpupdate(key, member, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(String key, String member, String value) {
		checkIsInMulti();
		client.gpupdate(key, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gpupdate(byte[] key, byte[] member, byte[] value) {
		checkIsInMulti();
		client.gpupdate(key, member, value);
		return client.getIntegerReply();
	}

	// gpradius

	@Override
	public List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String valuePattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] valuePattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			String valuePattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			byte[] valuePattern) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max, ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			String valuePattern, long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			byte[] valuePattern, long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			String memberPattern, String valuePattern, long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			byte[] memberPattern, byte[] valuePattern, long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpradius(key, lat, lon, radius, unit, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	// gpcircle

	@Override
	public Circle<String> gpcircle(String key, String member) {
		checkIsInMulti();
		client.gpcircle(key, member);
		return client.getSPATIAL_CIRCLEReply();
	}

	@Override
	public Circle<byte[]> gpcircle(byte[] key, byte[] member) {
		checkIsInMulti();
		client.gpcircle(key, member);
		return client.getBYTE_SPATIAL_CIRCLEReply();
	}

	@Override
	public List<Circle<String>> gpcircle(String key, double lat, double lon, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, RELATION scope, ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, scope, order);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, RELATION scope, ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, scope, order);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String valuePattern) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, valuePattern);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] valuePattern) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, valuePattern);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String valuePattern,
			RELATION scope, ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, valuePattern, scope, order);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] valuePattern,
			RELATION scope, ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, valuePattern, scope, order);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<String>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String memberPattern,
			String valuePattern, RELATION scope, ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, memberPattern, valuePattern, scope, order);
		return client.getSpatialCircleMultiBulkReply();
	}

	@Override
	public List<Circle<byte[]>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] memberPattern,
			byte[] valuePattern, RELATION scope, ORDERBY order) {
		checkIsInMulti();
		client.gpcircle(key, lat, lon, radius, unit, memberPattern, valuePattern, scope, order);
		return client.getBinarySpatialCircleMultiBulkReply();
	}

	// gpradiusByMember

	@Override
	public List<Point<String>> gpradiusByMember(String key, String bykey, String bymember) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(String key, String bykey, String bymember, String valuePattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] valuePattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(String key, String bykey, String bymember, String min, String max, String valuePattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] valuePattern) {
		checkIsInMulti();
		client.gpradiusByMember(key, bykey, bymember, min, max, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpradiusByMember(String key, String bykey, String bymember, String min, String max, String valuePattern,
			long offset, long count, ORDERBY order) {
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
	public List<Point<String>> gpradiusByMember(String key, String bykey, String bymember, String min, String max, String memberPattern,
			String valuePattern, long offset, long count, ORDERBY order) {
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
	public List<Point<String>> gpregionByMember(String key, String bykey, String bymember) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(String key, String bykey, String bymember, String valuePattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] valuePattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(String key, String bykey, String bymember, String min, String max, String valuePattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] valuePattern) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(String key, String bykey, String bymember, String min, String max, String valuePattern,
			long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] valuePattern,
			long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregionByMember(String key, String bykey, String bymember, String min, String max, String memberPattern,
			String valuePattern, long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern, long offset, long count, ORDERBY order) {
		checkIsInMulti();
		client.gpregionByMember(key, bykey, bymember, min, max, memberPattern, valuePattern, offset, count, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	// gpnn

	@Override
	public List<Point<String>> gpnn(String key, double lat, double lon, long offset, long count) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(byte[] key, double lat, double lon, long offset, long count) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpnn(String key, double lat, double lon, long offset, long count, String valuePattern) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] valuePattern) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpnn(String key, double lat, double lon, long offset, long count, String valuePattern, String min,
			String max, ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, valuePattern, min, max, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] valuePattern, byte[] min,
			byte[] max, ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, valuePattern, min, max, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpnn(String key, double lat, double lon, long offset, long count, String memberPattern, String valuePattern,
			String min, String max, ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, memberPattern, valuePattern, min, max, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] memberPattern, byte[] valuePattern,
			byte[] min, byte[] max, ORDERBY order) {
		checkIsInMulti();
		client.gpnn(key, lat, lon, offset, count, memberPattern, valuePattern, min, max, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	// gpregion

	@Override
	public List<Point<String>> gpregion(String key, Geometry<?> geometry) {
		checkIsInMulti();
		client.gpregion(key, geometry);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, Geometry<?> geometry) {
		checkIsInMulti();
		client.gpregion(key, geometry);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, Geometry<?> geometry, String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, Geometry<?> geometry, byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, Geometry<?> geometry, String memberPattern, String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, memberPattern, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, Geometry<?> geometry, byte[] memberPattern, byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, memberPattern, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, Geometry<?> geometry, String min, String max, String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, min, max, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, min, max, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, Geometry<?> geometry, String min, String max, long offset, long count,
			String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, min, max, offset, count, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, long offset, long count,
			byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, min, max, offset, count, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, Geometry<?> geometry, String min, String max, String memberPattern, String valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, min, max, memberPattern, valuePattern);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] memberPattern, byte[] valuePattern) {
		checkIsInMulti();
		client.gpregion(key, geometry, min, max, memberPattern, valuePattern);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpregion(String key, Geometry<?> geometry, String min, String max, long offset, long count,
			String memberPattern, String valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpregion(key, geometry, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, long offset, long count,
			byte[] memberPattern, byte[] valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpregion(key, geometry, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprange(String key, long start, long stop) {
		checkIsInMulti();
		client.gprange(key, start, stop);
		return client.getSPATIAL_GPOINT_WITHSCORE_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprange(byte[] key, long start, long stop) {
		checkIsInMulti();
		client.gprange(key, start, stop);
		return client.getBYTE_SPATIAL_GPOINT_WITHSCORE_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gprevrange(String key, long start, long stop) {
		checkIsInMulti();
		client.gprevrange(key, start, stop);
		return client.getSPATIAL_GPOINT_WITHSCORE_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gprevrange(byte[] key, long start, long stop) {
		checkIsInMulti();
		client.gprevrange(key, start, stop);
		return client.getBYTE_SPATIAL_GPOINT_WITHSCORE_LISTMultiBulkReply();
	}

	@Override
	public Long gpcard(String key) {
		checkIsInMulti();
		client.gpcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gpcard(byte[] key) {
		checkIsInMulti();
		client.gpcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gprem(String key, String member) {
		checkIsInMulti();
		client.gprem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gprem(byte[] key, byte[] member) {
		checkIsInMulti();
		client.gprem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Point<String> gpget(String key, String member) {
		checkIsInMulti();
		client.gpget(key, member);
		return client.getSPATIAL_GPOINTMultiBulkReply();

	}

	@Override
	public Point<byte[]> gpget(byte[] key, byte[] member) {
		checkIsInMulti();
		client.gpget(key, member);
		return client.getBYTE_SPATIAL_GPOINTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpmget(String key, String... members) {
		checkIsInMulti();
		client.gpmget(key, members);
		return client.getSPATIAL_GPOINT_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpmget(byte[] key, byte[]... members) {
		checkIsInMulti();
		client.gpmget(key, members);
		return client.getBYTE_SPATIAL_GPOINT_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpscope(String key, String min, String max, String valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, valuePattern, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpscope(byte[] key, byte[] min, byte[] max, byte[] valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, valuePattern, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpscope(String key, String min, String max, long offset, long count, String valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, offset, count, valuePattern, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpscope(byte[] key, byte[] min, byte[] max, long offset, long count, byte[] valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, offset, count, valuePattern, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<String>> gpscope(String key, String min, String max, long offset, long count, String memberPattern,
			String valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getSPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public List<Point<byte[]>> gpscope(byte[] key, byte[] min, byte[] max, long offset, long count, byte[] memberPattern,
			byte[] valuePattern, ORDERBY order) {
		checkIsInMulti();
		client.gpscope(key, min, max, offset, count, memberPattern, valuePattern, order);
		return client.getBYTE_SPATIAL_GPOINT_WITHDISTANCE_WITHSCORES_LISTMultiBulkReply();
	}

	@Override
	public double gpdistance(double lat1, double lng1, double lat2, double lng2) {
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
	public double gpdistance(double lat1, double lng1, double lat2, double lng2, double lat3, double lng3) {

		return this.gpdistance(new LineString<String>(lat1, lng1, lat2, lng2), new Point<String>(lat3, lng3));
	}

	@Override
	public double gpdistance(LineString<?> line, Point<?> point) {
		// angle = atan2(y1x2-x1y2, x1x2+y1y2);
		List<Point<?>> xys = line.getPoints();
		Point<?> p0 = xys.get(0);
		Point<?> p1 = xys.get(1);
		double x1 = (point.getX() - p0.getX());
		double y1 = (point.getY() - p0.getY());
		double x2 = (p1.getX() - p0.getX());
		double y2 = (p1.getY() - p0.getY());
		double dist = this.gpdistance(p0.getX(), p0.getY(), point.getX(), point.getY());

		double seta = Math.atan2((y1 * x2 - x1 * y2), (x1 * x2 + y1 * y2));
		double r = Math.abs(Math.sin(seta) * dist);
		return r;
	}

	@Override
	public Long ggadd(String key, String member, String value, Geometry<?> geometry) {
		checkIsInMulti();
		client.ggadd(key, member, value, geometry);
		return client.getIntegerReply();
	}

	@Override
	public Long ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry) {
		checkIsInMulti();
		client.ggadd(key, member, value, geometry);
		return client.getIntegerReply();
	}

	@Override
	public Long ggadd(String key, String member, String value, Geometry<?> geometry, double score) {
		checkIsInMulti();
		client.ggadd(key, member, value, geometry, score);
		return client.getIntegerReply();
	}

	@Override
	public Long ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score) {
		checkIsInMulti();
		client.ggadd(key, member, value, geometry, score);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(String key, String member, Geometry<?> geometry) {
		checkIsInMulti();
		client.ggupdate(key, member, geometry);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(byte[] key, byte[] member, Geometry<?> geometry) {
		checkIsInMulti();
		client.ggupdate(key, member, geometry);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(String key, String member, Geometry<?> geometry, double score) {
		checkIsInMulti();
		client.ggupdate(key, member, geometry, score);
		return client.getIntegerReply();
	}

	@Override
	public Long ggupdate(byte[] key, byte[] member, Geometry<?> geometry, double score) {
		checkIsInMulti();
		client.ggupdate(key, member, geometry, score);
		return client.getIntegerReply();
	}

	@Override
	public List<Geometry<String>> ggrange(String key, long start, long stop) {
		checkIsInMulti();
		client.ggrange(key, start, stop);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrange(byte[] key, long start, long stop) {
		checkIsInMulti();
		client.ggrange(key, start, stop);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrevrange(String key, long start, long stop) {
		checkIsInMulti();
		client.ggrevrange(key, start, stop);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrevrange(byte[] key, long start, long stop) {
		checkIsInMulti();
		client.ggrevrange(key, start, stop);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public Long ggcard(String key) {
		checkIsInMulti();
		client.ggcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long ggcard(byte[] key) {
		checkIsInMulti();
		client.ggcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long ggrem(String key, String member) {
		checkIsInMulti();
		client.ggrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long ggrem(byte[] key, byte[] member) {
		checkIsInMulti();
		client.ggrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Geometry<String> ggget(String key, String member) {
		checkIsInMulti();
		client.ggget(key, member);
		return client.getSpatialGETGEOMultiBulkReply();
	}

	@Override
	public Geometry<byte[]> ggget(byte[] key, byte[] member) {
		checkIsInMulti();
		client.ggget(key, member);
		return client.getBinarySpatialGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggmget(String key, String... members) {
		checkIsInMulti();
		client.ggmget(key, members);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggmget(byte[] key, byte[]... members) {
		checkIsInMulti();
		client.ggmget(key, members);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrelation(String key, Geometry<?> geometry) {
		checkIsInMulti();
		client.ggrelation(key, geometry);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelation(byte[] key, Geometry<?> geometry) {
		checkIsInMulti();
		client.ggrelation(key, geometry);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrelation(String key, Geometry<?> geometry, String min, String max) {
		checkIsInMulti();
		client.ggrelation(key, geometry, min, max);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelation(byte[] key, Geometry<?> geometry, byte[] min, byte[] max) {
		checkIsInMulti();
		client.ggrelation(key, geometry, min, max);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggrelationByMember(String key, String byKey, String byMember) {
		checkIsInMulti();
		client.ggrelationByMember(key, byKey, byMember);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggrelationByMember(byte[] key, byte[] byKey, byte[] byMember) {
		checkIsInMulti();
		client.ggrelationByMember(key, byKey, byMember);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggnn(String key, double lat, double lon, long count) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnn(byte[] key, double lat, double lon, long count) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggnn(String key, double lat, double lon, long count, String valuePattern) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, valuePattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnn(byte[] key, double lat, double lon, long count, byte[] valuePattern) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, valuePattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggnn(String key, double lat, double lon, long count, String memberPattern, String valuePattern) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnn(byte[] key, double lat, double lon, long count, byte[] memberPattern, byte[] valuePattern) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> ggnn(String key, double lat, double lon, long count, String memberPattern, String valuePattern,
			String min, String max) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern, min, max);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> ggnn(byte[] key, double lat, double lon, long count, byte[] memberPattern, byte[] valuePattern,
			byte[] min, byte[] max) {
		checkIsInMulti();
		client.ggnn(key, lat, lon, count, memberPattern, valuePattern, min, max);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	/* Geometry */

	@Override
	public double gmdistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	@Override
	public String gmsetBoundary(String key, double minx, double miny, double maxx, double maxy) {
		checkIsInMulti();
		client.gmsetBoundary(key, minx, miny, maxx, maxy);
		return client.getStatusCodeReply();
	}

	@Override
	public String gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		checkIsInMulti();
		client.gmsetBoundary(key, minx, miny, maxx, maxy);
		return client.getStatusCodeReply();
	}

	@Override
	public List<Point<String>> gmgetBoundary(String key) {
		checkIsInMulti();
		client.gmgetBoundary(key);
		return client.getSpatialBoundaryReply();
	}

	@Override
	public List<Point<byte[]>> gmgetBoundary(byte[] key) {
		checkIsInMulti();
		client.gmgetBoundary(key);
		return client.getBinarySpatialBoundaryReply();
	}

	@Override
	public Long gmrebuildBoundary(String key, double minx, double miny, double maxx, double maxy) {
		checkIsInMulti();
		client.gmrebuildBoundary(key, minx, miny, maxx, maxy);
		return client.getIntegerReply();
	}

	@Override
	public Long gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		checkIsInMulti();
		client.gmrebuildBoundary(key, minx, miny, maxx, maxy);
		return client.getIntegerReply();

	}

	@Override
	public Long gmadd(String key, String member, String value, Geometry<?> geometry) {
		checkIsInMulti();
		client.gmadd(key, member, value, geometry);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry) {
		checkIsInMulti();
		client.gmadd(key, member, value, geometry);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(String key, double x, double y, String member, String value) {
		checkIsInMulti();
		client.gmadd(key, x, y, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(byte[] key, double x, double y, byte[] member, byte[] value) {
		checkIsInMulti();
		client.gmadd(key, x, y, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(String key, String member, String value, Geometry<?> geometry, double score) {
		checkIsInMulti();
		client.gmadd(key, member, value, geometry, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score) {
		checkIsInMulti();
		client.gmadd(key, member, value, geometry, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(String key, double x, double y, String member, String value, double score) {
		checkIsInMulti();
		client.gmadd(key, x, y, member, value, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gmadd(byte[] key, double x, double y, byte[] member, byte[] value, double score) {
		checkIsInMulti();
		client.gmadd(key, x, y, member, value, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(String key, String member, Geometry<?> geometry) {
		checkIsInMulti();
		client.gmupdate(key, member, geometry);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(byte[] key, byte[] member, Geometry<?> geometry) {
		checkIsInMulti();
		client.gmupdate(key, member, geometry);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(String key, String member, Geometry<?> geometry, double score) {
		checkIsInMulti();
		client.gmupdate(key, member, geometry, score);
		return client.getIntegerReply();
	}

	@Override
	public Long gmupdate(byte[] key, byte[] member, Geometry<?> geometry, double score) {
		checkIsInMulti();
		client.gmupdate(key, member, geometry, score);
		return client.getIntegerReply();
	}

	@Override
	public List<Geometry<String>> gmrange(String key, long start, long stop) {
		checkIsInMulti();
		client.gmrange(key, start, stop);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrange(byte[] key, long start, long stop) {
		checkIsInMulti();
		client.gmrange(key, start, stop);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrevrange(String key, long start, long stop) {
		checkIsInMulti();
		client.gmrevrange(key, start, stop);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrevrange(byte[] key, long start, long stop) {
		checkIsInMulti();
		client.gmrevrange(key, start, stop);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public Long gmcard(String key) {
		checkIsInMulti();
		client.gmcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gmcard(byte[] key) {
		checkIsInMulti();
		client.gmcard(key);
		return client.getIntegerReply();
	}

	@Override
	public Long gmrem(String key, String member) {
		checkIsInMulti();
		client.gmrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Long gmrem(byte[] key, byte[] member) {
		checkIsInMulti();
		client.gmrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public Geometry<String> gmget(String key, String member) {
		checkIsInMulti();
		client.gmget(key, member);
		return client.getSpatialGETGEOMultiBulkReply();
	}

	@Override
	public Geometry<byte[]> gmget(byte[] key, byte[] member) {
		checkIsInMulti();
		client.gmget(key, member);
		return client.getBinarySpatialGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmmget(String key, String... members) {
		checkIsInMulti();
		client.gmmget(key, members);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmmget(byte[] key, byte[]... members) {
		checkIsInMulti();
		client.gmmget(key, members);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelation(String key, Geometry<?> geometry) {
		checkIsInMulti();
		client.gmrelation(key, geometry);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelation(byte[] key, Geometry<?> geometry) {
		checkIsInMulti();
		client.gmrelation(key, geometry);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelation(String key, Geometry<?> geometry, String memberPattern, String valuePattern) {
		checkIsInMulti();
		client.gmrelation(key, geometry, memberPattern, valuePattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelation(byte[] key, Geometry<?> geometry, byte[] memberPattern, byte[] valuePattern) {
		checkIsInMulti();
		client.gmrelation(key, geometry, memberPattern, valuePattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelation(String key, Geometry<?> geometry, String min, String max, String memberPattern,
			String valuePattern) {
		checkIsInMulti();
		client.gmrelation(key, geometry, min, max, memberPattern, valuePattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelation(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern) {
		checkIsInMulti();
		client.gmrelation(key, geometry, min, max, memberPattern, valuePattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelationByMember(String key, String byKey, String byMember) {
		checkIsInMulti();
		client.gmrelationByMember(key, byKey, byMember);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember) {
		checkIsInMulti();
		client.gmrelationByMember(key, byKey, byMember);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelationByMember(String key, String byKey, String byMember, String memberPattern, String valuePattern) {
		checkIsInMulti();
		client.gmrelationByMember(key, byKey, byMember, memberPattern, valuePattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] memberPattern, byte[] valuePattern) {
		checkIsInMulti();
		client.gmrelationByMember(key, byKey, byMember, memberPattern, valuePattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmrelationByMember(String key, String byKey, String byMember, String min, String max,
			String memberPattern, String valuePattern) {
		checkIsInMulti();
		client.gmrelationByMember(key, byKey, byMember, min, max, memberPattern, valuePattern);
		return client.getSpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] min, byte[] max,
			byte[] memberPattern, byte[] valuePattern) {
		checkIsInMulti();
		client.gmrelationByMember(key, byKey, byMember, min, max, memberPattern, valuePattern);
		return client.getBinarySpatialMGETGEOMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmnn(String key, double x, double y, long count) {
		checkIsInMulti();
		client.gmnn(key, x, y, count);
		return client.getSpatialMGETGEOWithDistanceMultiWBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmnn(byte[] key, double x, double y, long count) {
		checkIsInMulti();
		client.gmnn(key, x, y, count);
		return client.getBinarySpatialMGETGEOWithDistanceMultiBulkReply();
	}

	@Override
	public List<Geometry<String>> gmnn(String key, double x, double y, long count, String valuePattern) {
		checkIsInMulti();
		client.gmnn(key, x, y, count, valuePattern);
		return client.getSpatialMGETGEOWithDistanceMultiWBulkReply();
	}

	@Override
	public List<Geometry<byte[]>> gmnn(byte[] key, double x, double y, long count, byte[] valuePattern) {
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

	@Override
	public List<Point<String>> gpregion(String key, LineStringBuffer lineBuffer) {
		return this.gpregion(key, lineBuffer, "*", ORDERBY.DISTANCE_ASC);
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, LineStringBuffer lineBuffer) {
		return this.gpregion(key, lineBuffer, "*".getBytes(), ORDERBY.DISTANCE_ASC);
	}

	@Override
	public List<Point<String>> gpregion(String key, LineStringBuffer lineBuffer, String valuePattern, ORDERBY order) {
		List<Point<String>> points = this.gpregion(key, lineBuffer.getLinePolygon(), valuePattern);
		return this.sortBy(order, points, lineBuffer);
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, LineStringBuffer lineBuffer, byte[] valuePattern, ORDERBY order) {
		List<Point<byte[]>> points = this.gpregion(key, lineBuffer.getLinePolygon(), valuePattern);
		return this.sortBy(order, points, lineBuffer);
	}

	@Override
	public List<Point<String>> gpregion(String key, LineStringBuffer lineBuffer, String min, String max, String valuePattern, ORDERBY order) {
		List<Point<String>> points = this.gpregion(key, lineBuffer.getLinePolygon(), min, max, valuePattern);
		return this.sortBy(order, points, lineBuffer);
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, LineStringBuffer lineBuffer, byte[] min, byte[] max, byte[] valuePattern, ORDERBY order) {
		List<Point<byte[]>> points = this.gpregion(key, lineBuffer.getLinePolygon(), min, max, valuePattern);
		return this.sortBy(order, points, lineBuffer);
	}

	@Override
	public List<Point<String>> gpregion(String key, LineStringBuffer lineBuffer, String min, String max, String memberPattern,
			String valuePattern, ORDERBY order) {
		List<Point<String>> points = this.gpregion(key, lineBuffer.getLinePolygon(), min, max, memberPattern, valuePattern);
		return this.sortBy(order, points, lineBuffer);
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, LineStringBuffer lineBuffer, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern, ORDERBY order) {
		List<Point<byte[]>> points = this.gpregion(key, lineBuffer.getLinePolygon(), min, max, memberPattern, valuePattern);
		return this.sortBy(order, points, lineBuffer);
	}

	@Override
	public List<Point<String>> gpregion(String key, LineStringBuffer lineBuffer, String min, String max, long offset, long count,
			String valuePattern, ORDERBY order) {
		List<Point<String>> points = this.gpregion(key, lineBuffer.getLinePolygon(), min, max, valuePattern);
		return this.sortBy(order, points, lineBuffer, offset, count);
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, LineStringBuffer lineBuffer, byte[] min, byte[] max, long offset, long count,
			byte[] valuePattern, ORDERBY order) {
		List<Point<byte[]>> points = this.gpregion(key, lineBuffer.getLinePolygon(), min, max, valuePattern);
		return this.sortBy(order, points, lineBuffer, offset, count);
	}

	@Override
	public List<Point<String>> gpregion(String key, LineStringBuffer lineBuffer, String min, String max, long offset, long count,
			String memberPattern, String valuePattern, ORDERBY order) {
		List<Point<String>> points = this.gpregion(key, lineBuffer.getLinePolygon(), min, max, memberPattern, valuePattern);
		return this.sortBy(order, points, lineBuffer, offset, count);
	}

	@Override
	public List<Point<byte[]>> gpregion(byte[] key, LineStringBuffer lineBuffer, byte[] min, byte[] max, long offset, long count,
			byte[] memberPattern, byte[] valuePattern, ORDERBY order) {
		List<Point<byte[]>> points = this.gpregion(key, lineBuffer.getLinePolygon(), min, max, memberPattern, valuePattern);
		return this.sortBy(order, points, lineBuffer, offset, count);
	}

	@SuppressWarnings("rawtypes")
	public <T> List<Point<T>> sortBy(ORDERBY order, List<Point<T>> points, LineStringBuffer lineStringBuffer) {
		List<Point<T>> rpoints = new ArrayList<Point<T>>();

		if (lineStringBuffer != null) {
			com.vividsolutions.jts.geom.Geometry line = lineStringBuffer.getGeometyOfJTS();
			for (Point<T> p : points) {
				double distance = DistanceOp.distance(line, p.getGeometyOfJTS()) * GeoUtils.BYMETER;
				if (lineStringBuffer.getDistance() >= distance) {
					p.setDistance(distance);
					rpoints.add(p);
				}

			}
		} else {
			rpoints = points;
		}

		switch (order) {
		case DISTANCE_ASC:
			Collections.sort(rpoints, new Comparator<Point<T>>() {
				@Override
				public int compare(Point<T> o1, Point<T> o2) {
					if (o1.getDistance() < o2.getDistance()) {
						return -1;
					} else if (o1.getDistance() > o2.getDistance()) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			break;
		case DISTANCE_DESC:
			Collections.sort(rpoints, new Comparator<Point<T>>() {
				@Override
				public int compare(Point<T> o1, Point<T> o2) {
					if (o1.getDistance() < o2.getDistance()) {
						return 1;
					} else if (o1.getDistance() > o2.getDistance()) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			break;
		case SCORE_ASC:
			Collections.sort(rpoints, new Comparator<Point<T>>() {
				@Override
				public int compare(Point<T> o1, Point<T> o2) {
					if (o1.getScore() == null) {
						return 0;
					}
					if (o2.getScore() == null) {
						return -1;
					}
					if (o1.getScore() < o2.getScore()) {
						return -1;
					} else if (o1.getScore() > o2.getScore()) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			break;
		case SCORE_DESC:
			Collections.sort(rpoints, new Comparator<Point<T>>() {
				@Override
				public int compare(Point<T> o1, Point<T> o2) {
					if (o1.getScore() == null) {
						return 0;
					}
					if (o2.getScore() == null) {
						return -1;
					}
					if (o1.getScore() < o2.getScore()) {
						return 1;
					} else if (o1.getScore() > o2.getScore()) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			break;
		default:
			break;
		}

		return rpoints;
	}

	@SuppressWarnings("rawtypes")
	public <T> List<Point<T>> sortBy(ORDERBY order, List<Point<T>> points, LineStringBuffer lineStringBuffer, long offset, long count) {
		List<Point<T>> result = null;
		if (lineStringBuffer != null) {
			result = this.sortBy(order, points, lineStringBuffer);
		} else {
			result = this.sortBy(order, points);
		}

		int fromIndex = 0;
		int toIndex = 0;
		if (Integer.MAX_VALUE < offset) {
			fromIndex = Integer.MAX_VALUE;
		} else {
			fromIndex = (int) offset;
		}
		long max = offset + count;
		if (Integer.MAX_VALUE < max) {
			toIndex = Integer.MAX_VALUE;
		} else {
			toIndex = (int) max;
		}
		if (points.size() < fromIndex) {
			fromIndex = points.size();
		}
		if (points.size() < toIndex) {
			toIndex = points.size();
		}
		return result.subList(fromIndex, toIndex);
	}

	public <T> List<Point<T>> sortBy(ORDERBY order, List<Point<T>> points) {
		return this.sortBy(order, points, null);
	}

	public <T> List<Point<T>> sortBy(ORDERBY order, List<Point<T>> points, long offset, long count) {
		return this.sortBy(order, points, null, offset, count);
	}

	// TDODO with Score

}
