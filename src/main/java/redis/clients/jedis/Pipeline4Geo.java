package redis.clients.jedis;

import java.util.List;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.Point;

/**
 * @author horanghi
 */
public interface Pipeline4Geo {
	Response<Long> gpexists(String key, String member);

	Response<Long> gpexists(byte[] key, byte[] member);

	Response<Long> gpadd(String key, double lat, double lon, String member, String value);

	Response<Long> gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	Response<Long> gpadd(String key, double lat, double lon, String member, String value, double score);

	Response<Long> gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value, double score);

	Response<Long> gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value);

	Response<Long> gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value);

	Response<Long> gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value, double score);

	Response<Long> gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value, double score);

	// gpupdate

	Response<Long> gpupdate(String key, String member, double lat, double lon);

	Response<Long> gpupdate(byte[] key, byte[] member, double lat, double lon);

	Response<Long> gpupdate(String key, String member, double lat, double lon, double radius, UNITS unit);

	Response<Long> gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit);

	Response<Long> gpupdate(String key, String member, double lat, double lon, double radius, UNITS unit, String value, double score);

	Response<Long> gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit, byte[] value, double score);

	Response<Long> gpupdate(String key, String member, double radius, UNITS unit);

	Response<Long> gpupdate(byte[] key, byte[] member, double radius, UNITS unit);

	Response<Long> gpupdate(String key, String member, double score);

	Response<Long> gpupdate(byte[] key, byte[] member, double score);

	Response<Long> gpupdate(String key, String member, String value);

	Response<Long> gpupdate(byte[] key, byte[] member, byte[] value);

	// gpradius

	Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit);

	Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit);

	Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String valuePattern);

	Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] valuePattern);

	Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			String valuePattern);

	Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			byte[] valuePattern);

	Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			ORDERBY order);

	Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			ORDERBY order);

	Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			String valuePattern, long offset, long count, ORDERBY order);

	Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			byte[] valuePattern, long offset, long count, ORDERBY order);

	Response<List<Point<String>>> gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max,
			String memberPattern, String valuePattern, long offset, long count, ORDERBY order);

	Response<List<Point<byte[]>>> gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max,
			byte[] memberPattern, byte[] valuePattern, long offset, long count, ORDERBY order);

	// gpcircle

	Response<Circle<String>> gpcircle(String key, String member);

	Response<Circle<byte[]>> gpcircle(byte[] key, byte[] member);

	Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit);

	Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit);

	Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, RELATION scope, ORDERBY order);

	Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, RELATION scope, ORDERBY order);

	Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String valuePattern);

	Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] valuePattern);

	Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String valuePattern,
			RELATION ops, ORDERBY sort);

	Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] valuePattern,
			RELATION ops, ORDERBY sort);

	Response<List<Circle<String>>> gpcircle(String key, double lat, double lon, double radius, UNITS unit, String memberPattern,
			String valuePattern, RELATION ops, ORDERBY sort);

	Response<List<Circle<byte[]>>> gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] memberPattern,
			byte[] valuePattern, RELATION ops, ORDERBY sort);

	// gpradiusByMember

	Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember);

	Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember);

	Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember, String valuePattern);

	Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] valuePattern);

	Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember, String min, String max, String valuePattern);

	Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] valuePattern);

	Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember, String min, String max, String valuePattern,
			long offset, long count, ORDERBY order);

	Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] valuePattern,
			long offset, long count, ORDERBY order);

	Response<List<Point<String>>> gpradiusByMember(String key, String bykey, String bymember, String min, String max, String memberPattern,
			String valuePattern, long offset, long count, ORDERBY order);

	Response<List<Point<byte[]>>> gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern, long offset, long count, ORDERBY order);

	// gpregionByMember

	Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember);

	Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember);

	Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember, String valuePattern);

	Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] valuePattern);

	Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember, String min, String max, String valuePattern);

	Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] valuePattern);

	Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember, String min, String max, String valuePattern,
			long offset, long count, ORDERBY order);

	Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] valuePattern,
			long offset, long count, ORDERBY order);

	Response<List<Point<String>>> gpregionByMember(String key, String bykey, String bymember, String min, String max, String memberPattern,
			String valuePattern, long offset, long count, ORDERBY order);

	Response<List<Point<byte[]>>> gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern, long offset, long count, ORDERBY order);

	// gpnn

	Response<List<Point<String>>> gpnn(String key, double lat, double lon, long offset, long count);

	Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long offset, long count);

	Response<List<Point<String>>> gpnn(String key, double lat, double lon, long offset, long count, String valuePattern);

	Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] valuePattern);

	Response<List<Point<String>>> gpnn(String key, double lat, double lon, long offset, long count, String valuePattern, String min,
			String max, ORDERBY order);

	Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] valuePattern, byte[] min,
			byte[] max, ORDERBY order);

	Response<List<Point<String>>> gpnn(String key, double lat, double lon, long offset, long count, String memberPattern,
			String valuePattern, String min, String max, ORDERBY order);

	Response<List<Point<byte[]>>> gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] memberPattern,
			byte[] valuePattern, byte[] min, byte[] max, ORDERBY order);

	// gpregion

	Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry);

	Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry);

	Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String valuePattern);

	Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] valuePattern);

	Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String min, String max, long offset, long count,
			String valuePattern);

	Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, long offset, long count,
			byte[] valuePattern);

	Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String min, String max, String valuePattern);

	Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] valuePattern);

	Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String min, String max, long offset, long count,
			String memberPattern, String valuePattern);

	Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, long offset, long count,
			byte[] memberPattern, byte[] valuePattern);

	Response<List<Point<String>>> gprange(String key, long start, long stop);

	Response<List<Point<byte[]>>> gprange(byte[] key, long start, long stop);

	Response<List<Point<String>>> gprevrange(String key, long start, long stop);

	Response<List<Point<byte[]>>> gprevrange(byte[] key, long start, long stop);

	Response<Long> gpcard(String key);

	Response<Long> gpcard(byte[] key);

	Response<Long> gprem(String key, String member);

	Response<Long> gprem(byte[] key, byte[] member);

	Response<Point<String>> gpget(String key, String member);

	Response<Point<byte[]>> gpget(byte[] key, byte[] member);

	Response<List<Point<String>>> gpmget(String key, String... members);

	Response<List<Point<byte[]>>> gpmget(byte[] key, byte[]... members);

	Response<List<Point<String>>> gpscope(String key, String min, String max, String valuePattern, ORDERBY order);

	Response<List<Point<byte[]>>> gpscope(byte[] key, byte[] min, byte[] max, byte[] valuePattern, ORDERBY order);

	Response<List<Point<String>>> gpscope(String key, String min, String max, long offset, long count, String valuePattern, ORDERBY order);

	Response<List<Point<byte[]>>> gpscope(byte[] key, byte[] min, byte[] max, long offset, long count, byte[] valuePattern, ORDERBY order);

	Response<List<Point<String>>> gpscope(String key, String min, String max, long offset, long count, String memberPattern,
			String valuePattern, ORDERBY order);

	Response<List<Point<byte[]>>> gpscope(byte[] key, byte[] min, byte[] max, long offset, long count, byte[] memberPattern,
			byte[] valuePattern, ORDERBY order);

	/* Geography */

	Response<Long> ggexists(String key, String member);

	Response<Long> ggexists(byte[] key, byte[] member);

	Response<Long> ggadd(String key, String member, String value, Geometry<?> geometry);

	Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry);

	Response<List<Geometry<String>>> ggrange(String key, long start, long stop);

	Response<List<Geometry<byte[]>>> ggrange(byte[] key, long start, long stop);

	Response<List<Geometry<String>>> ggrevrange(String key, long start, long stop);

	Response<List<Geometry<byte[]>>> ggrevrange(byte[] key, long start, long stop);

	Response<Long> ggcard(String key);

	Response<Long> ggcard(byte[] key);

	Response<Long> ggrem(String key, String member);

	Response<Long> ggrem(byte[] key, byte[] member);

	Response<Geometry<String>> ggget(String key, String member);

	Response<Geometry<byte[]>> ggget(byte[] key, byte[] member);

	Response<List<Geometry<String>>> ggmget(String key, String... members);

	Response<List<Geometry<byte[]>>> ggmget(byte[] key, byte[]... members);

	Response<List<Geometry<String>>> ggrelation(String key, Geometry<?> geometry);

	Response<List<Geometry<byte[]>>> ggrelation(byte[] key, Geometry<?> geometry);

	Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count);

	Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count);

	Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count, String valuePattern);

	Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count, byte[] valuePattern);

	Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count, String memberPattern, String valuePattern);

	Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count, byte[] memberPattern, byte[] valuePattern);

	Response<Long> ggupdate(String key, String member, Geometry<?> geometry);

	Response<Long> ggupdate(byte[] key, byte[] member, Geometry<?> geometry);

	Response<List<Geometry<String>>> ggrelationByMember(String key, String byKey, String byMember);

	Response<List<Geometry<byte[]>>> ggrelationByMember(byte[] key, byte[] byKey, byte[] byMember);

	/* Geometry */

	Response<Long> gmexists(String key, String member);

	Response<Long> gmexists(byte[] key, byte[] member);

	Response<String> gmsetBoundary(String key, double minx, double miny, double maxx, double maxy);

	Response<byte[]> gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy);

	Response<List<Point<String>>> gmgetBoundary(String key);

	Response<List<Point<byte[]>>> gmgetBoundary(byte[] key);

	Response<Long> gmrebuildBoundary(String key, double minx, double miny, double maxx, double maxy);

	Response<Long> gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy);

	Response<Long> gmadd(String key, String member, String value, Geometry<?> geometry);

	Response<Long> gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry);

	Response<Long> gmadd(String key, double x, double y, String member, String value);

	Response<Long> gmadd(byte[] key, double x, double y, byte[] member, byte[] value);

	Response<List<Geometry<String>>> gmrange(String key, long start, long stop);

	Response<List<Geometry<byte[]>>> gmrange(byte[] key, long start, long stop);

	Response<List<Geometry<String>>> gmrevrange(String key, long start, long stop);

	Response<List<Geometry<byte[]>>> gmrevrange(byte[] key, long start, long stop);

	Response<Long> gmcard(String key);

	Response<Long> gmcard(byte[] key);

	Response<Long> gmrem(String key, String member);

	Response<Long> gmrem(byte[] key, byte[] member);

	Response<Geometry<String>> gmget(String key, String member);

	Response<Geometry<byte[]>> gmget(byte[] key, byte[] member);

	Response<List<Geometry<String>>> gmmget(String key, String... members);

	Response<List<Geometry<byte[]>>> gmmget(byte[] key, byte[]... members);

	Response<List<Geometry<String>>> gmrelation(String key, Geometry<?> geometry);

	Response<List<Geometry<byte[]>>> gmrelation(byte[] key, Geometry<?> geometry);

	Response<List<Geometry<String>>> gmnn(String key, double x, double y, long count);

	Response<List<Geometry<byte[]>>> gmnn(byte[] key, double x, double y, long count);

	Response<List<Geometry<String>>> gmnn(String key, double x, double y, long count, String valuePattern);

	Response<List<Geometry<byte[]>>> gmnn(byte[] key, double x, double y, long count, byte[] valuePattern);

	Response<Long> gmupdate(String key, String member, Geometry<?> geometry);

	Response<Long> gmupdate(byte[] key, byte[] member, Geometry<?> geometry);

	Response<List<Geometry<String>>> gmrelationByMember(String key, String byKey, String byMember);

	Response<List<Geometry<byte[]>>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember);

	Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String memberPattern, String valuePattern);

	Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] memberPattern, byte[] valuePattern);

	Response<List<Point<String>>> gpregion(String key, Geometry<?> geometry, String min, String max, String memberPattern,
			String valuePattern);

	Response<List<Point<byte[]>>> gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern);

	Response<Long> ggadd(String key, String member, String value, Geometry<?> geometry, double score);

	Response<Long> ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score);

	Response<Long> ggupdate(String key, String member, Geometry<?> geometry, double score);

	Response<Long> ggupdate(byte[] key, byte[] member, Geometry<?> geometry, double score);

	Response<Long> gmupdate(String key, String member, Geometry<?> geometry, double score);

	Response<Long> gmupdate(byte[] key, byte[] member, Geometry<?> geometry, double score);

	Response<List<Geometry<String>>> ggrelation(String key, Geometry<?> geometry, String min, String max);

	Response<List<Geometry<byte[]>>> ggrelation(byte[] key, Geometry<?> geometry, byte[] min, byte[] max);

	Response<List<Geometry<String>>> ggnn(String key, double lat, double lon, long count, String min, String max, String memberPattern,
			String valuePattern);

	Response<List<Geometry<byte[]>>> ggnn(byte[] key, double lat, double lon, long count, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern);

	Response<Long> gmadd(String key, String member, String value, Geometry<?> geometry, double score);

	Response<Long> gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score);

	Response<Long> gmadd(String key, double x, double y, String member, String value, double score);

	Response<Long> gmadd(byte[] key, double x, double y, byte[] member, byte[] value, double score);

	Response<List<Geometry<String>>> gmrelation(String key, Geometry<?> geometry, String memberPattern, String valuePattern);

	Response<List<Geometry<byte[]>>> gmrelation(byte[] key, Geometry<?> geometry, byte[] memberPattern, byte[] valuePattern);

	Response<List<Geometry<String>>> gmrelation(String key, Geometry<?> geometry, String min, String max, String memberPattern,
			String valuePattern);

	Response<List<Geometry<byte[]>>> gmrelation(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] memberPattern,
			byte[] valuePattern);

	Response<List<Geometry<String>>> gmrelationByMember(String key, String byKey, String byMember, String memberPattern, String valuePattern);

	Response<List<Geometry<byte[]>>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] memberPattern, byte[] valuePattern);

	Response<List<Geometry<String>>> gmrelationByMember(String key, String byKey, String byMember, String min, String max,
			String memberPattern, String valuePattern);

	Response<List<Geometry<byte[]>>> gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] min, byte[] max,
			byte[] memberPattern, byte[] valuePattern);

}
