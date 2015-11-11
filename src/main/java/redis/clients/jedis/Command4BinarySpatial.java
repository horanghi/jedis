package redis.clients.jedis;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

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

	void gpregion(byte[] key, Polygon<?> polygon);

	void gpregion(byte[] key, LineString<?> lineString);

	void gpregion(byte[] key, Point<?> point);

	void gpregion(byte[] key, Polygon<?> polygon, byte[] vpattern);

	void gpregion(byte[] key, LineString<?> lineString, byte[] vpattern);

	void gpregion(byte[] key, Point<?> point, byte[] vpattern);

	void gpregion(byte[] key, Point<?> point, byte[] mpattern, byte[] vpattern);

	void gpregion(byte[] key, Polygon<?> polygon, byte[] min, byte[] max, byte[] vpattern);

	void gpregion(byte[] key, LineString<?> lineString, byte[] min, byte[] max, byte[] vpattern);

	void gpregion(byte[] key, Polygon<?> polygon, byte[] min, byte[] max, long offset, long count, byte[] vpattern);

	void gpregion(byte[] key, Polygon<?> polygon, byte[] min, byte[] max, long offset, long count, byte[] mpattern, byte[] vpattern,
			ORDERBY order);

	void gpregion(byte[] key, LineString<?> lineString, byte[] min, byte[] max, long offset, long count, byte[] vpattern);

	void gpregion(byte[] key, LineString<?> lineString, byte[] min, byte[] max, long offset, long count, byte[] mpattern, byte[] vpattern,
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

	void ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);

	void ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	void ggrange(byte[] key, long start, long stop);

	void ggadd(byte[] key, byte[] member, byte[] value, Point<?> point);

	void ggrevrange(byte[] key, long start, long stop);

	void ggcard(byte[] key);

	void ggrem(byte[] key, byte[] member);

	void ggget(byte[] key, byte[] member);

	void ggmget(byte[] key, byte[]... members);

	void ggrelation(byte[] key, Polygon<?> polygon);

	void ggrelation(byte[] key, LineString<?> lineString);

	void ggrelation(byte[] key, Point<?> point);

	void ggnn(byte[] key, double lat, double lon, long count);

	void ggnn(byte[] key, double lat, double lon, long count, byte[] vpattern);

	void ggnn(byte[] key, double lat, double lon, long count, byte[] mpattern, byte[] vpattern);

	void ggupdate(byte[] key, byte[] member, Point<?> point);

	void ggupdate(byte[] key, byte[] member, Polygon<?> polygon);

	void ggupdate(byte[] key, byte[] member, LineString<?> lineString);

	void ggrelationByMember(byte[] key, byte[] byKey, byte[] byMember);

	/* Geometry */

	void gmexists(byte[] key, byte[] member);

	void gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy);

	void gmgetBoundary(byte[] key);

	void gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy);

	void gmadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon);

	void gmadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString);

	void gmadd(byte[] key, byte[] member, byte[] value, Point<?> point);

	void gmadd(byte[] key, double x, double y, byte[] member, byte[] value);

	void gmrange(byte[] key, long start, long stop);

	void gmrevrange(byte[] key, long start, long stop);

	void gmcard(byte[] key);

	void gmrem(byte[] key, byte[] member);

	void gmget(byte[] key, byte[] member);

	void gmmget(byte[] key, byte[]... members);

	void gmrelation(byte[] key, Polygon<?> polygon);

	void gmrelation(byte[] key, LineString<?> lineString);

	void gmrelation(byte[] key, Point<?> point);

	void gmnn(byte[] key, double x, double y, long count);

	void gmnn(byte[] key, double x, double y, long count, byte[] vpattern);

	void gmupdate(byte[] key, byte[] member, Point<?> point);

	void gmupdate(byte[] key, byte[] member, Polygon<?> polygon);

	void gmupdate(byte[] key, byte[] member, LineString<?> lineString);

	void gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember);

}
