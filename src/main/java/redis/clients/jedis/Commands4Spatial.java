package redis.clients.jedis;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public interface Commands4Spatial extends Commands {

	void gpexists(String key, String member);

	void gpadd(String key, double lat, double lon, String member, String value);

	void gpadd(String key, double lat, double lon, String member, String value, double score);

	void gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value);

	void gpadd(String key, double lat, double lon, double radius, UNITS unit, String member, String value, double score);

	// gpupdate

	void gpupdate(String key, String member, double lat, double lon);

	void gpupdate(String key, String member, double lat, double lon, double radius, UNITS unit);

	void gpupdate(String key, String member, double radius, UNITS unit);

	void gpupdate(String key, String member, double score);

	void gpupdate(String key, String member, String value);

	// gpradius

	void gpradius(String key, double lat, double lon, double radius, UNITS unit);

	void gpradius(String key, double lat, double lon, double radius, UNITS unit, String pattern);

	void gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max, String pattern);

	void gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max, ORDERBY order);

	void gpradius(String key, double lat, double lon, double radius, UNITS unit, String min, String max, String pattern, long offset,
			long count, ORDERBY order);

	// gpcircle
	
	void gpcircle(String key, String member);

	void gpcircle(String key, double lat, double lon, double radius, UNITS unit);

	void gpcircle(String key, double lat, double lon, double radius, UNITS unit, RELATION scope, ORDERBY order);

	void gpcircle(String key, double lat, double lon, double radius, UNITS unit, String pattern);

	void gpcircle(String key, double lat, double lon, double radius, UNITS unit, String pattern, RELATION ops, ORDERBY sort);

	// gpradiusByMember

	void gpradiusByMember(String key, String bykey, String bymember);

	void gpradiusByMember(String key, String bykey, String bymember, String pattern);

	void gpradiusByMember(String key, String bykey, String bymember, String min, String max, String pattern);

	void gpradiusByMember(String key, String bykey, String bymember, String min, String max, String pattern, long offset, long count,
			ORDERBY order);

	// gpregionByMember

	void gpregionByMember(String key, String bykey, String bymember);

	void gpregionByMember(String key, String bykey, String bymember, String pattern);

	void gpregionByMember(String key, String bykey, String bymember, String min, String max, String pattern);

	void gpregionByMember(String key, String bykey, String bymember, String min, String max, String pattern, long offset, long count,
			ORDERBY order);

	// gpnn

	void gpnn(String key, double lat, double lon, long offset, long count);

	void gpnn(String key, double lat, double lon, long offset, long count, String pattern);

	void gpnn(String key, double lat, double lon, long offset, long count, String pattern, String min, String max, ORDERBY order);

	// gpregion

	void gpregion(String key, Polygon<?> polygon);

	void gpregion(String key, LineString<?> lineString);

	void gpregion(String key, Point<?> point);

	void gpregion(String key, Polygon<?> polygon, String pattern);

	void gpregion(String key, LineString<?> lineString, String pattern);

	void gpregion(String key, Point<?> point, String pattern);

	void gpregion(String key, Polygon<?> polygon, String min, String max, String pattern);

	void gpregion(String key, LineString<?> lineString, String min, String max, String pattern);

	void gpregion(String key, Polygon<?> polygon, String min, String max, long offset, long count, String pattern);

	void gpregion(String key, LineString<?> lineString, String min, String max, long offset, long count, String pattern);

	void gpcard(String key);

	void gprem(String key, String member);

	void gpget(String key, String member);

	void gpmget(String key, String... members);

	void gpscope(String key, String min, String max, String pattern, ORDERBY order);

	void gpscope(String key, String min, String max, long offset, long count, String pattern, ORDERBY order);

	/* Geography */

	void ggexists(String key, String member);

	void ggadd(String key, String member, String value, Polygon<?> polygon);

	void ggadd(String key, String member, String value, LineString<?> lineString);

	void ggrange(String key, long start, long stop);

	void ggadd(String key, String member, String value, Point<?> point);

	void ggrevrange(String key, long start, long stop);

	void ggcard(String key);

	void ggrem(String key, String member);

	void ggget(String key, String member);

	void ggmget(String key, String... members);

	void ggrelation(String key, Polygon<?> polygon);

	void ggrelation(String key, LineString<?> lineString);

	void ggrelation(String key, Point<?> point);

	void ggnn(String key, double lat, double lon, long count);

	void ggnn(String key, double lat, double lon, long count, String pattern);

	void ggupdate(String key, String member, Point<?> point);

	void ggupdate(String key, String member, Polygon<?> polygon);

	void ggupdate(String key, String member, LineString<?> lineString);

	void ggrelationByMember(String key, String byKey, String byMember);

	/* Geometry */

	void gmexists(String key, String member);

	void gmsetBoundary(String key, double minx, double miny, double maxx, double maxy);

	void gmgetBoundary(String key);

	void gmrebuildBoundary(String key, double minx, double miny, double maxx, double maxy);

	void gmadd(String key, String member, String value, Polygon<?> polygon);

	void gmadd(String key, String member, String value, LineString<?> lineString);

	void gmadd(String key, String member, String value, Point<?> point);

	void gmadd(String key, double x, double y, String member, String value);

	void gmrange(String key, long start, long stop);

	void gmrevrange(String key, long start, long stop);

	void gmcard(String key);

	void gmrem(String key, String member);

	void gmget(String key, String member);

	void gmmget(String key, String... members);

	void gmrelation(String key, Polygon<?> polygon);

	void gmrelation(String key, LineString<?> lineString);

	void gmrelation(String key, Point<?> point);

	void gmnn(String key, double x, double y, long count);

	void gmnn(String key, double x, double y, long count, String pattern);

	void gmupdate(String key, String member, Point<?> point);

	void gmupdate(String key, String member, Polygon<?> polygon);

	void gmupdate(String key, String member, LineString<?> lineString);

	void gmrelationByMember(String key, String byKey, String byMember);

}
