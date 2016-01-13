package redis.clients.jedis;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Geometry;

public interface Command4BinarySpatial {

	void gpexists(byte[] key, byte[] member);

	void gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value);

	void gpadd(byte[] key, double lat, double lon, byte[] member, byte[] value, double score);

	void gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value);

	void gpadd(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] member, byte[] value, double score);

	// gpupdate

	void gpupdate(byte[] key, byte[] member, double lat, double lon);

	void gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit);

	void gpupdate(byte[] key, byte[] member, double lat, double lon, double radius, UNITS unit, byte[] value, double score);

	void gpupdate(byte[] key, byte[] member, double radius, UNITS unit);

	void gpupdate(byte[] key, byte[] member, double score);

	void gpupdate(byte[] key, byte[] member, byte[] value);

	// gpradius

	void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit);

	void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] vpattern);

	void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, byte[] vpattern);

	void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, ORDERBY order);

	void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, byte[] vpattern, long offset,
			long count, ORDERBY order);

	void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern,
			long offset, long count, ORDERBY order);

	// gpcircle

	void gpcircle(byte[] key, byte[] member);

	void gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit);

	void gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, RELATION scope, ORDERBY order);

	void gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] vpattern);

	void gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] vpattern, RELATION ops, ORDERBY sort);

	void gpcircle(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] mpattern, byte[] vpattern, RELATION ops,
			ORDERBY sort);

	// gpradiusByMember

	void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember);

	void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] vpattern);

	void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] vpattern);

	void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] vpattern, long offset, long count,
			ORDERBY order);

	void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern, long offset,
			long count, ORDERBY order);

	// gpregionByMember

	void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember);

	void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] vpattern);

	void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] vpattern);

	void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] vpattern, long offset, long count,
			ORDERBY order);

	void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern, long offset,
			long count, ORDERBY order);

	// gpnn

	void gpnn(byte[] key, double lat, double lon, long offset, long count);

	void gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] vpattern);

	void gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] vpattern, byte[] min, byte[] max);

	void gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] vpattern, byte[] min, byte[] max, ORDERBY order);

	void gpnn(byte[] key, double lat, double lon, long offset, long count, byte[] mpattern, byte[] vpattern, byte[] min, byte[] max,
			ORDERBY order);

	// gpregion

	void gpregion(byte[] key, Geometry<?> geometry);

	void gpregion(byte[] key, Geometry<?> geometry, byte[] vpattern);

	void gpregion(byte[] key, Geometry<?> geometry, byte[] mpattern, byte[] vpattern);

	void gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] vpattern);

	void gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern);

	void gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, long offset, long count, byte[] vpattern);

	void gpregion(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, long offset, long count, byte[] mpattern, byte[] vpattern,
			ORDERBY order);

	void gprange(byte[] key, long start, long stop);

	void gprevrange(byte[] key, long start, long stop);

	void gpcard(byte[] key);

	void gprem(byte[] key, byte[] member);

	void gpget(byte[] key, byte[] member);

	void gpmget(byte[] key, byte[]... members);

	void gpscope(byte[] key, byte[] min, byte[] max, byte[] vpattern, ORDERBY order);

	void gpscope(byte[] key, byte[] min, byte[] max, long offset, long count, byte[] vpattern, ORDERBY order);

	void gpscope(byte[] key, byte[] min, byte[] max, long offset, long count, byte[] mpattern, byte[] vpattern, ORDERBY order);

	/* Geography */

	void ggexists(byte[] key, byte[] member);

	void ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry);

	void ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score);

	void ggrange(byte[] key, long start, long stop);

	void ggrevrange(byte[] key, long start, long stop);

	void ggcard(byte[] key);

	void ggrem(byte[] key, byte[] member);

	void ggget(byte[] key, byte[] member);

	void ggmget(byte[] key, byte[]... members);

	void ggrelation(byte[] key, Geometry<?> geometry);

	void ggrelation(byte[] key, Geometry<?> geometry, byte[] min, byte[] max);

	void ggnn(byte[] key, double lat, double lon, long count);

	void ggnn(byte[] key, double lat, double lon, long count, byte[] vpattern);

	void ggnn(byte[] key, double lat, double lon, long count, byte[] mpattern, byte[] vpattern);

	void ggnn(byte[] key, double lat, double lon, long count, byte[] mpattern, byte[] vpattern, byte[] min, byte[] max);

	void ggupdate(byte[] key, byte[] member, Geometry<?> geometry);

	void ggupdate(byte[] key, byte[] member, Geometry<?> geometry, double score);

	void ggrelationByMember(byte[] key, byte[] byKey, byte[] byMember);

	/* Geometry */

	void gmexists(byte[] key, byte[] member);

	void gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy);

	void gmgetBoundary(byte[] key);

	void gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy);

	void gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry);

	void gmadd(byte[] key, double x, double y, byte[] member, byte[] value);

	void gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score);

	void gmadd(byte[] key, double x, double y, byte[] member, byte[] value, double score);

	void gmrange(byte[] key, long start, long stop);

	void gmrevrange(byte[] key, long start, long stop);

	void gmcard(byte[] key);

	void gmrem(byte[] key, byte[] member);

	void gmget(byte[] key, byte[] member);

	void gmmget(byte[] key, byte[]... members);

	void gmrelation(byte[] key, Geometry<?> geometry);

	void gmrelation(byte[] key, Geometry<?> geometry, byte[] mpattern, byte[] vpattern);

	void gmrelation(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern);

	void gmnn(byte[] key, double x, double y, long count);

	void gmnn(byte[] key, double x, double y, long count, byte[] vpattern);

	void gmupdate(byte[] key, byte[] member, Geometry<?> geometry);

	void gmupdate(byte[] key, byte[] member, Geometry<?> geometry, double score);

	void gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember);

	void gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] mpattern, byte[] vpattern);

	void gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern);

}
