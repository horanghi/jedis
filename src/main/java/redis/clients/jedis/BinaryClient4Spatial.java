package redis.clients.jedis;

import static redis.clients.jedis.Protocol.toByteArray;
import static redis.clients.jedis.Protocol.Command.GGADD;
import static redis.clients.jedis.Protocol.Command.GGCARD;
import static redis.clients.jedis.Protocol.Command.GGEXISTS;
import static redis.clients.jedis.Protocol.Command.GGGET;
import static redis.clients.jedis.Protocol.Command.GGMGET;
import static redis.clients.jedis.Protocol.Command.GGNN;
import static redis.clients.jedis.Protocol.Command.GGRANGE;
import static redis.clients.jedis.Protocol.Command.GGRELATION;
import static redis.clients.jedis.Protocol.Command.GGRELATIONBYMEMBER;
import static redis.clients.jedis.Protocol.Command.GGREM;
import static redis.clients.jedis.Protocol.Command.GGREVRANGE;
import static redis.clients.jedis.Protocol.Command.GGUPDATEBY;
import static redis.clients.jedis.Protocol.Command.GMADD;
import static redis.clients.jedis.Protocol.Command.GMCARD;
import static redis.clients.jedis.Protocol.Command.GMEXISTS;
import static redis.clients.jedis.Protocol.Command.GMGET;
import static redis.clients.jedis.Protocol.Command.GMGETBOUNDARY;
import static redis.clients.jedis.Protocol.Command.GMMGET;
import static redis.clients.jedis.Protocol.Command.GMNN;
import static redis.clients.jedis.Protocol.Command.GMRANGE;
import static redis.clients.jedis.Protocol.Command.GMREBUILDBOUNDARY;
import static redis.clients.jedis.Protocol.Command.GMRELATION;
import static redis.clients.jedis.Protocol.Command.GMRELATIONBYMEMBER;
import static redis.clients.jedis.Protocol.Command.GMREM;
import static redis.clients.jedis.Protocol.Command.GMREVRANGE;
import static redis.clients.jedis.Protocol.Command.GMSETBOUNDARY;
import static redis.clients.jedis.Protocol.Command.GMUPDATEBY;
import static redis.clients.jedis.Protocol.Command.GPADD;
import static redis.clients.jedis.Protocol.Command.GPCARD;
import static redis.clients.jedis.Protocol.Command.GPEXISTS;
import static redis.clients.jedis.Protocol.Command.GPGET;
import static redis.clients.jedis.Protocol.Command.GPMGET;
import static redis.clients.jedis.Protocol.Command.GPNN;
import static redis.clients.jedis.Protocol.Command.GPRADIUS;
import static redis.clients.jedis.Protocol.Command.GPRADIUSBYMEMBER;
import static redis.clients.jedis.Protocol.Command.GPREGION;
import static redis.clients.jedis.Protocol.Command.GPREGIONBYMEMBER;
import static redis.clients.jedis.Protocol.Command.GPREM;
import static redis.clients.jedis.Protocol.Command.GPSCOPE;
import static redis.clients.jedis.Protocol.Command.GPUPDATEBY;
import static redis.clients.jedis.Protocol.Command.PL_END;
import static redis.clients.jedis.Protocol.Command.PL_START;
import static redis.clients.jedis.Protocol.GeoOptions.BY;
import static redis.clients.jedis.Protocol.GeoOptions.LIMIT;
import static redis.clients.jedis.Protocol.GeoOptions.MATCH;
import static redis.clients.jedis.Protocol.GeoOptions.NR;
import static redis.clients.jedis.Protocol.GeoOptions.RADIUS;
import static redis.clients.jedis.Protocol.GeoOptions.SCORE;
import static redis.clients.jedis.Protocol.GeoOptions.WITHDISTANCE;
import static redis.clients.jedis.Protocol.GeoOptions.WITHGEOJSON;
import static redis.clients.jedis.Protocol.GeoOptions.WITHSCORES;
import static redis.clients.jedis.Protocol.GeoOptions.WITHVALUES;
import static redis.clients.jedis.Protocol.GeoOptions.XR;
import static redis.clients.jedis.Protocol.ORDERBY.DISTANCE_ASC;
import static redis.clients.jedis.Protocol.ORDERBY.SCORE_DESC;
import static redis.clients.jedis.Protocol.RELATION.CONTAINS;
import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public class BinaryClient4Spatial extends BinaryClient implements Command4BinarySpatial {

	public BinaryClient4Spatial(String host) {
		super(host);
	}

	public BinaryClient4Spatial(final String host, final int port) {
		super(host, port);
	}
	
	@Override
	public void gpexists(final byte[] key, final byte[] member) {
		sendCommand(GPEXISTS, key, member);
	}

	@Override
	public void gpadd(final byte[] key, final double latitude, final double longitude, final byte[] member, final byte[] value) {
		gpadd(key, latitude, longitude, 0, UNITS.M, member, value);
	}

	@Override
	public void gpadd(final byte[] key, final double latitude, final double longitude, final byte[] member, final byte[] value, double score) {
		gpadd(key, latitude, longitude, 0, UNITS.M, member, value, score);
	}

	@Override
	public void gpadd(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] member,
			final byte[] value) {
		// GPADD key member value latitude longitude [RADIUS radius m|km]
		sendCommand(GPADD, key, member, value, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw);
	}

	@Override
	public void gpadd(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] member,
			final byte[] value, final double score) {
		// GPADD key member value latitude longitude [SCORE score] [RADIUS radius m|km]
		sendCommand(GPADD, key, member, value, toByteArray(lat), toByteArray(lon), SCORE.raw, toByteArray(score), RADIUS.raw,
				toByteArray(radius), unit.raw);
	}

	// gpupdate

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final double lat, final double lon) {
		// GUPDATEBY key member latitude longitude [RADIUS radius m|km]
		sendCommand(GPUPDATEBY, key, member, toByteArray(lat), toByteArray(lon));
	}

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final double lat, final double lon, final double radius, final UNITS unit) {
		// GUPDATEBY key member latitude longitude [RADIUS radius m|km]
		sendCommand(GPUPDATEBY, key, member, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw);
	}

	// gpradius

	@Override
	public void gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit) {
		/*
		* GPRADIUS key latitude longitude
		*          [RADIUS radius m|km] [CONTAINS|WITHIN]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, NR.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] pattern) {
		/*
		* GPRADIUS key latitude longitude
		*          [RADIUS radius m|km] [CONTAINS|WITHIN]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2, NR.raw,
				MATCH.raw, pattern);
	}

	@Override
	public void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, byte[] pattern) {
		/*
		* GPRADIUS key latitude longitude
		*          [RADIUS radius m|km] [CONTAINS|WITHIN]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw, SCORE.raw,
				min, max, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, SCORE_DESC.raw0, SCORE_DESC.raw1, SCORE_DESC.raw2, NR.raw,
				MATCH.raw, pattern);
	}

	@Override
	public void gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] min,
			final byte[] max, final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude
		*          [RADIUS radius m|km] [CONTAINS|WITHIN]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw, SCORE.raw,
				min, max, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, NR.raw, order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] min,
			final byte[] max, final byte[] pattern, final long offset, final long count, final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude
		*          [RADIUS radius m|km] [CONTAINS|WITHIN]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw, SCORE.raw,
				min, max, LIMIT.raw, toByteArray(offset), toByteArray(count), MATCH.raw, pattern, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, NR.raw, order.raw0, order.raw1, order.raw2);
	}

	// gpcircle

	@Override
	public void gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit) {
		/*
		* GPRADIUS key latitude longitude
		*          [RADIUS radius m|km] [CONTAINS|WITHIN]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, XR.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final RELATION scope,
			final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude
		*          [RADIUS radius m|km] [CONTAINS|WITHIN]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, scope.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, XR.raw, order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] pattern) {
		/*
		* GPRADIUS key latitude longitude
		*          [RADIUS radius m|km] [CONTAINS|WITHIN]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2, XR.raw, MATCH.raw, pattern);
	}

	@Override
	public void gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] pattern,
			final RELATION scope, final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude
		*          [RADIUS radius m|km] [CONTAINS|WITHIN]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, scope.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, order.raw0, order.raw1, order.raw2, XR.raw, MATCH.raw, pattern);
	}

	// gpradiusByMember

	public void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember) {
		/*
		* GPRADIUSBYMEMBER key BY key member
		*                  [CP latitude longitude]
		*                  [MATCH pattern] [SCORE min max] [NR|XR]
		*                  [ORDERBY SCORE|DIST ASC|DESC]
		*                  [LIMIT offset count]
		*                  [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUSBYMEMBER, key, BY.raw, bykey, bymember, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw,
				DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	public void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern) {
		/*
		* GPRADIUSBYMEMBER key BY key member
		*                  [CP latitude longitude]
		*                  [MATCH pattern] [SCORE min max] [NR|XR]
		*                  [ORDERBY SCORE|DIST ASC|DESC]
		*                  [LIMIT offset count]
		*                  [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUSBYMEMBER, key, BY.raw, bykey, bymember, MATCH.raw, pattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	public void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] pattern) {
		/*
		* GPRADIUSBYMEMBER key BY key member
		*                  [CP latitude longitude]
		*                  [MATCH pattern] [SCORE min max] [NR|XR]
		*                  [ORDERBY SCORE|DIST ASC|DESC]
		*                  [LIMIT offset count]
		*                  [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUSBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCH.raw, pattern, NR.raw, WITHVALUES.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, SCORE_DESC.raw0, SCORE_DESC.raw1, SCORE_DESC.raw2);
	}

	public void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] pattern, long offset,
			long count, ORDERBY order) {
		/*
		* GPRADIUSBYMEMBER key BY key member
		*                  [CP latitude longitude]
		*                  [MATCH pattern] [SCORE min max] [NR|XR]
		*                  [ORDERBY SCORE|DIST ASC|DESC]
		*                  [LIMIT offset count]
		*                  [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUSBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCH.raw, pattern, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), order.raw0, order.raw1, order.raw2);
	}

	// gpregionByMember

	@Override
	public void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember) {
		/*
		* GPREGIONBYMEMBER key BY key member
		*                  [CP latitude longitude]
		*                  [MATCH pattern] [SCORE min max] [NR|XR]
		*                  [ORDERBY SCORE|DIST ASC|DESC]
		*                  [LIMIT offset count]
		*                  [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGIONBYMEMBER, key, BY.raw, bykey, bymember, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw,
				DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] pattern) {
		/*
		* GPREGIONBYMEMBER key BY key member
		*                  [CP latitude longitude]
		*                  [MATCH pattern] [SCORE min max] [NR|XR]
		*                  [ORDERBY SCORE|DIST ASC|DESC]
		*                  [LIMIT offset count]
		*                  [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGIONBYMEMBER, key, BY.raw, bykey, bymember, MATCH.raw, pattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, final byte[] min, final byte[] max, final byte[] pattern) {
		/*
		* GPREGIONBYMEMBER key BY key member
		*                  [CP latitude longitude]
		*                  [MATCH pattern] [SCORE min max] [NR|XR]
		*                  [ORDERBY SCORE|DIST ASC|DESC]
		*                  [LIMIT offset count]
		*                  [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGIONBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCH.raw, pattern, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw, SCORE_DESC.raw0, SCORE_DESC.raw1, SCORE_DESC.raw2);
	}

	@Override
	public void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] pattern, long offset,
			long count, ORDERBY order) {
		/*
		* GPREGIONBYMEMBER key BY key member
		*                  [CP latitude longitude]
		*                  [MATCH pattern] [SCORE min max] [NR|XR]
		*                  [ORDERBY SCORE|DIST ASC|DESC]
		*                  [LIMIT offset count]
		*                  [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGIONBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCH.raw, pattern, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), order.raw0, order.raw1, order.raw2);
	}

	// gpnn

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count) {
		/*
		* GPNN key latitude longitude LIMIT offset count
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(offset), toByteArray(count), NR.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count, final byte[] pattern) {
		/*
		* GPNN key latitude longitude LIMIT offset count
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(offset), toByteArray(count), MATCH.raw, pattern,
				NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count, final byte[] pattern,
			final byte[] min, final byte[] max) {
		/*
		* GPNN key latitude longitude LIMIT offset count
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(offset), toByteArray(count), MATCH.raw, pattern,
				SCORE.raw, min, max, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1,
				DISTANCE_ASC.raw2);
	}

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count, final byte[] pattern,
			final byte[] min, final byte[] max, ORDERBY order) {
		/*
		* GPNN key latitude longitude LIMIT offset count
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(offset), toByteArray(count), MATCH.raw, pattern,
				SCORE.raw, min, max, MATCH.raw, pattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, order.raw0, order.raw1,
				order.raw2);
	}

	// gpregion

	@Override
	public void gpregion(byte[] key, Polygon<?> polygon) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, polygon.getJsonByte(), NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, LineString<?> lineString) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, lineString.getJsonByte(), NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, Point<?> point) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, point.getJsonByte(), NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, Polygon<?> polygon, byte[] pattern) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, polygon.getJsonByte(), MATCH.raw, pattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, LineString<?> lineString, byte[] pattern) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, lineString.getJsonByte(), MATCH.raw, pattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, Point<?> point, byte[] pattern) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, point.getJsonByte(), MATCH.raw, pattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, Polygon<?> polygon, final byte[] min, final byte[] max, byte[] pattern) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, polygon.getJsonByte(), MATCH.raw, pattern, SCORE.raw, min, max, NR.raw, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, DISTANCE_ASC.raw0, SCORE_DESC.raw1, SCORE_DESC.raw2);
	}

	@Override
	public void gpregion(byte[] key, LineString<?> lineString, final byte[] min, final byte[] max, byte[] pattern) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, lineString.getJsonByte(), MATCH.raw, pattern, SCORE.raw, min, max, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw, SCORE_DESC.raw0, SCORE_DESC.raw1, SCORE_DESC.raw2);
	}

	@Override
	public void gpregion(byte[] key, Polygon<?> polygon, final byte[] min, final byte[] max, final long offset, long count, byte[] pattern) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, polygon.getJsonByte(), MATCH.raw, pattern, SCORE.raw, min, max, NR.raw, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), SCORE_DESC.raw0, SCORE_DESC.raw1, SCORE_DESC.raw2);
	}

	@Override
	public void gpregion(byte[] key, LineString<?> lineString, final byte[] min, final byte[] max, final long offset, long count,
			byte[] pattern) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH pattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, lineString.getJsonByte(), MATCH.raw, pattern, SCORE.raw, min, max, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), SCORE_DESC.raw0, SCORE_DESC.raw1,
				SCORE_DESC.raw2);
	}

	public void gpcard(final byte[] key) {
		// GPCARD key
		sendCommand(GPCARD, key);
	}

	@Override
	public void gprem(final byte[] key, final byte[] member) {
		sendCommand(GPREM, key, member);
	}

	@Override
	public void gpget(byte[] key, byte[] member) {
		// GPGET key member / GPMGET key member [member ...]
		sendCommand(GPGET, key, member);

	}

	@Override
	public void gpmget(final byte[] key, final byte[]... members) {
		// GPGET key member / GPMGET key member [member ...]
		int len = members.length;
		byte[][] bargs = new byte[len + 1][];
		bargs[0] = key;
		for (int i = 0; i < len; ++i) {
			bargs[i + 1] = members[i];
		}
		sendCommand(GPMGET, bargs);
	}

	@Override
	public void gpscope(final byte[] key, final byte[] min, final byte[] max, final byte[] pattern, final ORDERBY order) {
		/*
		* GPSCOPE key min max (REGION region | lat lon)
		*         [CP latitude longitude]
		*         [RADIUS radius m|km] [CONTAINS|WITHIN]
		*         [MATCH pattern] [NR|XR]
		*         [ORDERBY SCORE|DIST ASC|DESC]
		*         [LIMIT offset count]
		*         [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPSCOPE, key, min, max, CONTAINS.raw, MATCH.raw, pattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw,
				order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpscope(final byte[] key, final byte[] min, final byte[] max, final long offset, final long count, final byte[] pattern,
			final ORDERBY order) {
		/*
		* GPSCOPE key min max (REGION region | lat lon)
		*         [CP latitude longitude]
		*         [RADIUS radius m|km] [CONTAINS|WITHIN]
		*         [MATCH pattern] [NR|XR]
		*         [ORDERBY SCORE|DIST ASC|DESC]
		*         [LIMIT offset count]
		*         [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPSCOPE, key, min, max, CONTAINS.raw, MATCH.raw, pattern, LIMIT.raw, toByteArray(offset), toByteArray(count), NR.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void ggadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon) {
		// GGADD key member value geojson
		sendCommand(GGADD, key, member, value, polygon.getJsonByte());
	}

	@Override
	public void ggadd(byte[] key, byte[] member, byte[] value, Point<?> point) {
		// GGADD key member value geojson
		sendCommand(GGADD, key, member, value, point.getJsonByte());
	}

	@Override
	public void ggadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString) {
		// GGADD key member value geojson
		sendCommand(GGADD, key, member, value, lineString.getJsonByte());
	}

	@Override
	public void ggupdate(byte[] key, byte[] member, Polygon<?> polygon) {
		// GGUPDATEBY key member geojson
		sendCommand(GGUPDATEBY, key, member, polygon.getJsonByte());
	}

	@Override
	public void ggupdate(byte[] key, byte[] member, Point<?> point) {
		// GGUPDATEBY key member geojson
		sendCommand(GGUPDATEBY, key, member, point.getJsonByte());
	}

	@Override
	public void ggupdate(byte[] key, byte[] member, LineString<?> lineString) {
		// GGUPDATEBY key member geojson
		sendCommand(GGUPDATEBY, key, member, lineString.getJsonByte());
	}

	@Override
	public void ggrange(byte[] key, long start, long stop) {
		// GGADD key member value geojson
		sendCommand(GGRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw);
	}

	@Override
	public void ggrevrange(byte[] key, long start, long stop) {
		// GGADD key member value geojson
		sendCommand(GGREVRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw);
	}

	@Override
	public void ggcard(byte[] key) {
		sendCommand(GGCARD, key);
	}

	@Override
	public void ggrem(byte[] key, byte[] member) {
		sendCommand(GGREM, key, member);
	}

	@Override
	public void ggget(byte[] key, byte[] member) {
		// GGGET key member / GGMGET key member [member ...]
		sendCommand(GGGET, key, member);

	}

	@Override
	public void ggmget(final byte[] key, final byte[][] members) {
		// GGGET key member / GGMGET key member [member ...]
		int len = members.length;
		byte[][] bargs = new byte[len + 1][];
		bargs[0] = key;
		for (int i = 0; i < len; ++i) {
			bargs[i + 1] = members[i];
		}
		sendCommand(GGMGET, bargs);
	}

	@Override
	public void ggrelation(byte[] key, Polygon<?> polygon) {
		// GGRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GGRELATION, key, polygon.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggrelation(byte[] key, LineString<?> lineString) {
		// GGRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GGRELATION, key, lineString.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggrelation(byte[] key, Point<?> point) {
		// GGRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GGRELATION, key, point.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggrelationByMember(byte[] key, byte[] byKey, byte[] byMember) {
		// GGRELATIONBY key BY bykey bymember CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		sendCommand(GGRELATIONBYMEMBER, key, BY.raw, byKey, byMember, CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggnn(final byte[] key, final double lat, final double lon, final long count) {
		// ggnn mygg 0 0 limit 2 withvalues withdistance withgeojson
		sendCommand(GGNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggnn(final byte[] key, final double lat, final double lon, final long count, final byte[] pattern) {
		// ggnn mygg 0 0 limit 2 match hello* withvalues withdistance withgeojson
		sendCommand(GGNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), MATCH.raw, pattern, WITHVALUES.raw,
				WITHGEOJSON.raw);
	}

	// Geometry

	@Override
	public void gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		sendCommand(GMSETBOUNDARY, key, toByteArray(minx), toByteArray(miny), toByteArray(maxx), toByteArray(maxy));
	}

	@Override
	public void gmgetBoundary(byte[] key) {
		sendCommand(GMGETBOUNDARY, key);
	}

	@Override
	public void gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		sendCommand(GMREBUILDBOUNDARY, key, toByteArray(minx), toByteArray(miny), toByteArray(maxx), toByteArray(maxy));
	}

	@Override
	public void gmadd(byte[] key, byte[] member, byte[] value, Polygon<?> polygon) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, polygon.getJsonByte());
	}

	@Override
	public void gmadd(byte[] key, byte[] member, byte[] value, Point<?> point) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, point.getJsonByte());
	}

	public void gmadd(byte[] key, double x, double y, byte[] member, byte[] value) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, new Point<byte[]>(x, y).getJsonByte());
	}

	@Override
	public void gmadd(byte[] key, byte[] member, byte[] value, LineString<?> lineString) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, lineString.getJsonByte());
	}

	@Override
	public void gmupdate(byte[] key, byte[] member, Polygon<?> polygon) {
		// GMUPDATEBY key member geojson
		sendCommand(GMUPDATEBY, key, member, polygon.getJsonByte());
	}

	@Override
	public void gmupdate(byte[] key, byte[] member, Point<?> point) {
		// GMUPDATEBY key member geojson
		sendCommand(GMUPDATEBY, key, member, point.getJsonByte());
	}

	@Override
	public void gmupdate(byte[] key, byte[] member, LineString<?> lineString) {
		// GMUPDATEBY key member geojson
		sendCommand(GMUPDATEBY, key, member, lineString.getJsonByte());
	}

	@Override
	public void gmrange(byte[] key, long start, long stop) {
		// GMADD key member value geojson
		sendCommand(GMRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw);
	}

	@Override
	public void gmrevrange(byte[] key, long start, long stop) {
		// GMADD key member value geojson
		sendCommand(GMREVRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw);
	}

	@Override
	public void gmcard(byte[] key) {
		sendCommand(GMCARD, key);
	}

	@Override
	public void gmrem(byte[] key, byte[] member) {
		sendCommand(GMREM, key, member);
	}

	@Override
	public void gmget(byte[] key, byte[] member) {
		// GMGET key member / GGMGET key member [member ...]
		sendCommand(GMGET, key, member);

	}

	@Override
	public void gmmget(final byte[] key, final byte[][] members) {
		// GMGET key member / GGMGET key member [member ...]
		int len = members.length;
		byte[][] bargs = new byte[len + 1][];
		bargs[0] = key;
		for (int i = 0; i < len; ++i) {
			bargs[i + 1] = members[i];
		}
		sendCommand(GMMGET, bargs);
	}

	@Override
	public void gmrelation(byte[] key, Polygon<?> polygon) {
		// GMRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GMRELATION, key, polygon.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelation(byte[] key, LineString<?> lineString) {
		// GMRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GMRELATION, key, lineString.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelation(byte[] key, Point<?> point) {
		// GMRELATION mygg '{"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}' contains withvalues withgeojson
		sendCommand(GMRELATION, key, point.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember) {
		// GMRELATIONBY key BY bykey bymember CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		sendCommand(GMRELATIONBYMEMBER, key, BY.raw, byKey, byMember, CONTAINS.raw, WITHVALUES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmnn(final byte[] key, final double x, final double y, final long count) {
		// GMnn mygg 0 0 limit 2 withvalues withdistance withgeojson
		sendCommand(GMNN, key, toByteArray(x), toByteArray(y), LIMIT.raw, toByteArray(count), WITHVALUES.raw, WITHDISTANCE.raw,
				WITHGEOJSON.raw);
	}

	@Override
	public void gmnn(final byte[] key, final double x, final double y, final long count, final byte[] pattern) {
		// GMnn mygg 0 0 limit 2 match hello* withvalues withdistance withgeojson
		sendCommand(GMNN, key, toByteArray(x), toByteArray(y), LIMIT.raw, toByteArray(count), MATCH.raw, pattern, WITHVALUES.raw,
				WITHDISTANCE.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggexists(final byte[] key, final byte[] member) {
		sendCommand(GGEXISTS, key, member);
	}

	@Override
	public void gmexists(final byte[] key, final byte[] member) {
		sendCommand(GMEXISTS, key, member);
	}
}
