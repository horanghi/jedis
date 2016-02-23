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
import static redis.clients.jedis.Protocol.Command.GGUPDATE;
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
import static redis.clients.jedis.Protocol.Command.GMUPDATE;
import static redis.clients.jedis.Protocol.Command.GPADD;
import static redis.clients.jedis.Protocol.Command.GPCARD;
import static redis.clients.jedis.Protocol.Command.GPEXISTS;
import static redis.clients.jedis.Protocol.Command.GPGET;
import static redis.clients.jedis.Protocol.Command.GPMGET;
import static redis.clients.jedis.Protocol.Command.GPNN;
import static redis.clients.jedis.Protocol.Command.GPRADIUS;
import static redis.clients.jedis.Protocol.Command.GPRADIUSBYMEMBER;
import static redis.clients.jedis.Protocol.Command.GPRANGE;
import static redis.clients.jedis.Protocol.Command.GPREGION;
import static redis.clients.jedis.Protocol.Command.GPREGIONBYMEMBER;
import static redis.clients.jedis.Protocol.Command.GPREM;
import static redis.clients.jedis.Protocol.Command.GPREVRANGE;
import static redis.clients.jedis.Protocol.Command.GPSCOPE;
import static redis.clients.jedis.Protocol.Command.GPUPDATE;
import static redis.clients.jedis.Protocol.GeoOptions.BY;
import static redis.clients.jedis.Protocol.GeoOptions.LIMIT;
import static redis.clients.jedis.Protocol.GeoOptions.MATCH;
import static redis.clients.jedis.Protocol.GeoOptions.MATCHVALUE;
import static redis.clients.jedis.Protocol.GeoOptions.NR;
import static redis.clients.jedis.Protocol.GeoOptions.POS;
import static redis.clients.jedis.Protocol.GeoOptions.RADIUS;
import static redis.clients.jedis.Protocol.GeoOptions.SCORE;
import static redis.clients.jedis.Protocol.GeoOptions.VALUE;
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
import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.Point;

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
	public void gpadd(final byte[] key, final double latitude, final double longitude, final byte[] member, final byte[] value,
			double score) {
		gpadd(key, latitude, longitude, 0, UNITS.M, member, value, score);
	}

	@Override
	public void gpadd(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] member,
			final byte[] value) {
		// GPADD key member value latitude longitude [RADIUS radius m|km] [SCORE score]
		sendCommand(GPADD, key, member, value, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw);
	}

	@Override
	public void gpadd(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] member,
			final byte[] value, final double score) {
		// GPADD key member value latitude longitude [RADIUS radius m|km] [SCORE score]
		sendCommand(GPADD, key, member, value, toByteArray(lat), toByteArray(lon), SCORE.raw, toByteArray(score), RADIUS.raw,
				toByteArray(radius), unit.raw);
	}

	// gpupdate

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final double lat, final double lon) {
		/*
		 * GPUPDATE  key member [POS latitude longitude] [RADIUS radius m|km] [VALUE score] [SCORE score]
		 */
		sendCommand(GPUPDATE, key, member, POS.raw, toByteArray(lat), toByteArray(lon));
	}

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final double lat, final double lon, final double radius, final UNITS unit) {
		/*
		 * GPUPDATE  key member [POS latitude longitude] [RADIUS radius m|km] [VALUE score] [SCORE score]
		 */
		sendCommand(GPUPDATE, key, member, POS.raw, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw);
	}

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] value, double score) {
		/*
		 * GPUPDATE  key member [POS latitude longitude] [RADIUS radius m|km] [VALUE score] [SCORE score]
		 */
		sendCommand(GPUPDATE, key, member, POS.raw, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw,
				VALUE.raw, value, SCORE.raw, toByteArray(score));
	}

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final double radius, final UNITS unit) {
		/*
		 * GPUPDATE  key member [POS latitude longitude] [RADIUS radius m|km] [VALUE score] [SCORE score]
		 */
		sendCommand(GPUPDATE, key, member, RADIUS.raw, toByteArray(radius), unit.raw);
	}

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final double score) {
		/*
		 * GPUPDATE  key member [POS latitude longitude] [RADIUS radius m|km] [VALUE score] [SCORE score]
		 */
		sendCommand(GPUPDATE, key, member, SCORE.raw, toByteArray(score));
	}

	@Override
	public void gpupdate(final byte[] key, final byte[] member, final byte[] value) {
		/*
		 * GPUPDATE  key member [POS latitude longitude] [RADIUS radius m|km] [VALUE score] [SCORE score]
		 */
		sendCommand(GPUPDATE, key, member, VALUE.raw, value);
	}

	// gpradius

	@Override
	public void gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, NR.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] vpattern) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2, NR.raw,
				MATCHVALUE.raw, vpattern);
	}

	@Override
	public void gpradius(byte[] key, double lat, double lon, double radius, UNITS unit, byte[] min, byte[] max, byte[] vpattern) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw, SCORE.raw,
				min, max, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, SCORE_DESC.raw0, SCORE_DESC.raw1, SCORE_DESC.raw2, NR.raw,
				MATCHVALUE.raw, vpattern);
	}

	@Override
	public void gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] min,
			final byte[] max, final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw, SCORE.raw,
				min, max, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, NR.raw, order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] min,
			final byte[] max, final byte[] vpattern, final long offset, final long count, final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw, SCORE.raw,
				min, max, LIMIT.raw, toByteArray(offset), toByteArray(count), MATCHVALUE.raw, vpattern, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, NR.raw, order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpradius(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] min,
			final byte[] max, final byte[] mvpattern, final byte[] vvpattern, final long offset, final long count, final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw, SCORE.raw,
				min, max, LIMIT.raw, toByteArray(offset), toByteArray(count), MATCH.raw, mvpattern, MATCHVALUE.raw, vvpattern,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, NR.raw, order.raw0, order.raw1, order.raw2);
	}

	// gpcircle
	@Override
	public void gpcircle(final byte[] key, final byte[] member) {
		// GPGET key member / GPMGET key member [member ...]
		sendCommand(GPGET, key, member);
	}

	@Override
	public void gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, XR.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final RELATION scope,
			final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, scope.raw, WITHVALUES.raw,
				WITHDISTANCE.raw, XR.raw, order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit,
			final byte[] vpattern) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, CONTAINS.raw,
				WITHVALUES.raw, WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2, XR.raw, MATCHVALUE.raw,
				vpattern);
	}

	@Override
	public void gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] vpattern,
			final RELATION scope, final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, scope.raw, WITHVALUES.raw,
				WITHDISTANCE.raw, order.raw0, order.raw1, order.raw2, XR.raw, MATCHVALUE.raw, vpattern);
	}

	@Override
	public void gpcircle(final byte[] key, final double lat, final double lon, final double radius, final UNITS unit, final byte[] mpattern,
			final byte[] vpattern, final RELATION scope, final ORDERBY order) {
		/*
		* GPRADIUS key latitude longitude 
		* 		[RADIUS radius m|km] [CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUS, key, toByteArray(lat), toByteArray(lon), RADIUS.raw, toByteArray(radius), unit.raw, scope.raw, WITHVALUES.raw,
				WITHDISTANCE.raw, order.raw0, order.raw1, order.raw2, XR.raw, MATCH.raw, mpattern, MATCHVALUE.raw, vpattern);
	}

	// gpradiusByMember
	@Override
	public void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember) {
		/*
		* GPRADIUSBYMEMBER key BY bykey bymember 
		* 		[CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUSBYMEMBER, key, BY.raw, bykey, bymember, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw,
				DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] vpattern) {
		/*
		* GPRADIUSBYMEMBER key BY bykey bymember 
		* 		[CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUSBYMEMBER, key, BY.raw, bykey, bymember, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] vpattern) {
		/*
		* GPRADIUSBYMEMBER key BY bykey bymember 
		* 		[CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUSBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, SCORE_DESC.raw0, SCORE_DESC.raw1, SCORE_DESC.raw2);
	}

	@Override
	public void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] vpattern, long offset,
			long count, ORDERBY order) {
		/*
		* GPRADIUSBYMEMBER key BY bykey bymember 
		* 		[CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUSBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpradiusByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern,
			long offset, long count, ORDERBY order) {
		/*
		* GPRADIUSBYMEMBER key BY bykey bymember 
		* 		[CONTAINS|WITHIN] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPRADIUSBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCH.raw, mpattern, MATCHVALUE.raw, vpattern,
				NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), order.raw0,
				order.raw1, order.raw2);
	}

	// gpregionByMember

	@Override
	public void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember) {
		/*
		* GPREGIONBYMEMBER key BY bykey bymember 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGIONBYMEMBER, key, BY.raw, bykey, bymember, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] vpattern) {
		/*
		* GPREGIONBYMEMBER key BY bykey bymember 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGIONBYMEMBER, key, BY.raw, bykey, bymember, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw);
	}

	@Override
	public void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, final byte[] min, final byte[] max, final byte[] vpattern) {
		/*
		* GPREGIONBYMEMBER key BY bykey bymember 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGIONBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] vpattern, long offset,
			long count, ORDERBY order) {
		/*
		* GPREGIONBYMEMBER key BY bykey bymember 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGIONBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpregionByMember(byte[] key, byte[] bykey, byte[] bymember, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern,
			long offset, long count, ORDERBY order) {
		/*
		* GPREGIONBYMEMBER key BY bykey bymember 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGIONBYMEMBER, key, BY.raw, bykey, bymember, SCORE.raw, min, max, MATCH.raw, mpattern, MATCHVALUE.raw, vpattern,
				NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), order.raw0,
				order.raw1, order.raw2);
	}

	// gpnn

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count) {
		/*
		* GPNN key latitude longitude LIMIT offset count 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[WITHVALUES] [WITHDISTANCE]
		*/
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(offset), toByteArray(count), NR.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count, final byte[] vpattern) {
		/*
		* GPNN key latitude longitude LIMIT offset count 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[WITHVALUES] [WITHDISTANCE]
		*/
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(offset), toByteArray(count), MATCHVALUE.raw,
				vpattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, DISTANCE_ASC.raw0, DISTANCE_ASC.raw1,
				DISTANCE_ASC.raw2);
	}

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count, final byte[] vpattern,
			final byte[] min, final byte[] max) {
		/*
		* GPNN key latitude longitude LIMIT offset count 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[WITHVALUES] [WITHDISTANCE]
		*/
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(offset), toByteArray(count), MATCHVALUE.raw,
				vpattern, SCORE.raw, min, max, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, DISTANCE_ASC.raw0,
				DISTANCE_ASC.raw1, DISTANCE_ASC.raw2);
	}

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count, final byte[] vpattern,
			final byte[] min, final byte[] max, ORDERBY order) {
		/*
		* GPNN key latitude longitude LIMIT offset count 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[WITHVALUES] [WITHDISTANCE]
		*/
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(offset), toByteArray(count), MATCHVALUE.raw,
				vpattern, SCORE.raw, min, max, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw,
				order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpnn(final byte[] key, final double lat, final double lon, final long offset, final long count, final byte[] mpattern,
			final byte[] vpattern, final byte[] min, final byte[] max, ORDERBY order) {
		/*
		* GPNN key latitude longitude LIMIT offset count 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[WITHVALUES] [WITHDISTANCE]
		*/
		sendCommand(GPNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(offset), toByteArray(count), MATCH.raw, mpattern,
				MATCHVALUE.raw, vpattern, SCORE.raw, min, max, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, order.raw0, order.raw1, order.raw2);
	}

	// gpregion

	@Override
	public void gpregion(byte[] key, Geometry<?> geometry) {
		/*
		* GPREGION key geojson_region 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, geometry.getJsonByte(), NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, Geometry<?> geometry, byte[] vpattern) {
		/*
		* GPREGION key geojson_region 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, geometry.getJsonByte(), MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, Geometry<?> geometry, byte[] mpattern, byte[] vpattern) {
		/*
		* GPREGION key geojson_region 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, geometry.getJsonByte(), MATCH.raw, mpattern, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, Geometry<?> geometry, final byte[] min, final byte[] max, byte[] vpattern) {
		/*
		* GPREGION key geojson_region 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, geometry.getJsonByte(), MATCHVALUE.raw, vpattern, SCORE.raw, min, max, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, Geometry<?> geometry, final byte[] min, final byte[] max, byte[] mpattern, byte[] vpattern) {
		/*
		* GPREGION key geojson_region 
		* 		[CP latitude longitude] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[SCORE min max] [NR|XR] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, geometry.getJsonByte(), MATCH.raw, mpattern, MATCHVALUE.raw, vpattern, SCORE.raw, min, max, NR.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw);
	}

	@Override
	public void gpregion(byte[] key, Geometry<?> geometry, final byte[] min, final byte[] max, final long offset, long count,
			byte[] vpattern) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH vpattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, geometry.getJsonByte(), MATCHVALUE.raw, vpattern, SCORE.raw, min, max, NR.raw, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), SCORE_DESC.raw0, SCORE_DESC.raw1,
				SCORE_DESC.raw2);
	}

	@Override
	public void gpregion(byte[] key, Geometry<?> geometry, final byte[] min, final byte[] max, final long offset, long count,
			byte[] mpattern, byte[] vpattern, ORDERBY order) {
		/*
		* GPREGION key geojson
		*          [CP latitude longitude]
		*          [MATCH vpattern] [SCORE min max] [NR|XR]
		*          [ORDERBY SCORE|DIST ASC|DESC]
		*          [LIMIT offset count]
		*          [WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPREGION, key, geometry.getJsonByte(), MATCH.raw, mpattern, MATCHVALUE.raw, vpattern, SCORE.raw, min, max, NR.raw,
				WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, LIMIT.raw, toByteArray(offset), toByteArray(count), order.raw0,
				order.raw1, order.raw2);
	}

	@Override
	public void gprange(byte[] key, final long start, long stop) {
		/*
		 * GPRANGE/GPREVRANGE key start stop [WITHVALUES] [WITHSCORES] [WITHGEOHASH]
		 */
		sendCommand(GPRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw, WITHSCORES.raw);
	}

	@Override
	public void gprevrange(byte[] key, final long start, long stop) {
		/*
		 * GPRANGE/GPREVRANGE key start stop [WITHVALUES] [WITHSCORES] [WITHGEOHASH]
		 */
		sendCommand(GPREVRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw, WITHSCORES.raw);
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
	public void gpscope(final byte[] key, final byte[] min, final byte[] max, final byte[] vpattern, final ORDERBY order) {
		/*
		* GPSCOPE key min max 
		* 		[REGION region] 
		* 		[CP latitude longitude] [RADIUS radius m|km] 
		* 		[CONTAINS|WITHIN] [NR|XR] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPSCOPE, key, min, max, CONTAINS.raw, MATCHVALUE.raw, vpattern, NR.raw, WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpscope(final byte[] key, final byte[] min, final byte[] max, final long offset, final long count, final byte[] vpattern,
			final ORDERBY order) {
		/*
		* GPSCOPE key min max 
		* 		[REGION region] 
		* 		[CP latitude longitude] [RADIUS radius m|km] 
		* 		[CONTAINS|WITHIN] [NR|XR] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPSCOPE, key, min, max, CONTAINS.raw, MATCHVALUE.raw, vpattern, LIMIT.raw, toByteArray(offset), toByteArray(count),
				NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void gpscope(final byte[] key, final byte[] min, final byte[] max, final long offset, final long count, final byte[] mpattern,
			final byte[] vpattern, final ORDERBY order) {
		/*
		* GPSCOPE key min max 
		* 		[REGION region] 
		* 		[CP latitude longitude] [RADIUS radius m|km] 
		* 		[CONTAINS|WITHIN] [NR|XR] 
		* 		[MATCH key_pattern][MATCHVALUE value_pattern] 
		* 		[ORDERBY SCORE|DIST ASC|DESC] 
		* 		[LIMIT offset count] 
		* 		[WITHVALUES] [WITHSCORES] [WITHDISTANCE]
		*/
		sendCommand(GPSCOPE, key, min, max, CONTAINS.raw, MATCH.raw, mpattern, MATCHVALUE.raw, vpattern, LIMIT.raw, toByteArray(offset),
				toByteArray(count), NR.raw, WITHVALUES.raw, WITHSCORES.raw, WITHDISTANCE.raw, order.raw0, order.raw1, order.raw2);
	}

	@Override
	public void ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry) {
		// GGADD key member value geojson
		sendCommand(GGADD, key, member, value, geometry.getJsonByte());
	}

	@Override
	public void ggadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score) {
		// GGADD key member value geojson
		sendCommand(GGADD, key, member, value, geometry.getJsonByte(), SCORE.raw, toByteArray(score));
	}

	@Override
	public void ggupdate(byte[] key, byte[] member, Geometry<?> geometry) {
		// GGUPDATEBY key member geojson
		sendCommand(GGUPDATE, key, member, geometry.getJsonByte());
	}

	@Override
	public void ggupdate(byte[] key, byte[] member, Geometry<?> geometry, double score) {
		// GGUPDATEBY key member geojson score
		sendCommand(GGUPDATE, key, member, geometry.getJsonByte(), SCORE.raw, toByteArray(score));
	}

	@Override
	public void ggrange(byte[] key, long start, long stop) {
		// GGADD key member value geojson
		sendCommand(GGRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggrevrange(byte[] key, long start, long stop) {
		// GGADD key member value geojson
		sendCommand(GGREVRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
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
	public void ggrelation(byte[] key, Geometry<?> geometry, RELATION relation) {
		/*
		 * GGRELATION key geojson CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		 */
		sendCommand(GGRELATION, key, geometry.getJsonByte(), relation.raw, WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggrelation(byte[] key, Geometry<?> geometry, RELATION relation, byte[] min, byte[] max) {
		/*
		 * GGRELATION key geojson CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		 */
		sendCommand(GGRELATION, key, geometry.getJsonByte(), relation.raw, SCORE.raw, min, max, WITHVALUES.raw, WITHSCORES.raw,
				WITHGEOJSON.raw);
	}

	@Override
	public void ggrelationByMember(byte[] key, byte[] byKey, byte[] byMember, RELATION relation) {
		/*
		 * GGRELATIONBYMEMBER key BY bykey bymember CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		 */
		sendCommand(GGRELATIONBYMEMBER, key, BY.raw, byKey, byMember, relation.raw, WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggnn(final byte[] key, final double lat, final double lon, final long count) {
		/*
		 * GGNN key latitude longitude LIMIT count [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [WITHGEOJSON]
		 */
		sendCommand(GGNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), WITHVALUES.raw, WITHSCORES.raw,
				WITHGEOJSON.raw);
	}

	@Override
	public void ggnn(final byte[] key, final double lat, final double lon, final long count, final byte[] vpattern) {
		/*
		 * GGNN key latitude longitude LIMIT count [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [WITHGEOJSON]
		 */
		sendCommand(GGNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), MATCHVALUE.raw, vpattern, WITHVALUES.raw,
				WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggnn(final byte[] key, final double lat, final double lon, final long count, final byte[] mpattern, final byte[] vpattern) {
		/*
		 * GGNN key latitude longitude LIMIT count [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [WITHGEOJSON]
		 */
		sendCommand(GGNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), MATCH.raw, mpattern, MATCHVALUE.raw,
				vpattern, WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void ggnn(final byte[] key, final double lat, final double lon, final long count, final byte[] mpattern, final byte[] vpattern,
			final byte[] min, final byte[] max) {
		/*
		 * GGNN key latitude longitude LIMIT count [MATCH pattern] [WITHVALUES] [WITHDISTANCE] [WITHGEOJSON]
		 */
		sendCommand(GGNN, key, toByteArray(lat), toByteArray(lon), LIMIT.raw, toByteArray(count), SCORE.raw, min, max, MATCH.raw, mpattern,
				MATCHVALUE.raw, vpattern, WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	// Geometry

	@Override
	public void gmsetBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		/*
		 * GMSETBOUNDARY key minx miny maxx maxy
		 */
		sendCommand(GMSETBOUNDARY, key, toByteArray(minx), toByteArray(miny), toByteArray(maxx), toByteArray(maxy));
	}

	@Override
	public void gmgetBoundary(byte[] key) {
		/*
		 * GMGETBOUDARY key
		 */
		sendCommand(GMGETBOUNDARY, key);
	}

	@Override
	public void gmrebuildBoundary(byte[] key, double minx, double miny, double maxx, double maxy) {
		/*
		 * GMREBUILDBOUNDARY key minx miny maxx maxy
		 */
		sendCommand(GMREBUILDBOUNDARY, key, toByteArray(minx), toByteArray(miny), toByteArray(maxx), toByteArray(maxy));
	}

	@Override
	public void gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, geometry.getJsonByte());
	}

	public void gmadd(byte[] key, double x, double y, byte[] member, byte[] value) {
		// GMADD key member value geojson
		sendCommand(GMADD, key, member, value, new Point<byte[]>(x, y).getJsonByte());
	}

	@Override
	public void gmadd(byte[] key, byte[] member, byte[] value, Geometry<?> geometry, double score) {
		// GMADD key member value geojson score
		sendCommand(GMADD, key, member, value, geometry.getJsonByte(), SCORE.raw, toByteArray(score));
	}

	@Override
	public void gmadd(byte[] key, double x, double y, byte[] member, byte[] value, double score) {
		// GMADD key member value geojson score
		sendCommand(GMADD, key, member, value, new Point<byte[]>(x, y).getJsonByte(), SCORE.raw, toByteArray(score));
	}

	@Override
	public void gmupdate(byte[] key, byte[] member, Geometry<?> geometry) {
		// GMUPDATEBY key member geojson
		sendCommand(GMUPDATE, key, member, geometry.getJsonByte());
	}

	@Override
	public void gmupdate(byte[] key, byte[] member, Geometry<?> geometry, double score) {
		// GMUPDATEBY key member geojson
		sendCommand(GMUPDATE, key, member, geometry.getJsonByte(), SCORE.raw, toByteArray(score));
	}

	@Override
	public void gmrange(byte[] key, long start, long stop) {
		// GMADD key member value geojson
		sendCommand(GMRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrevrange(byte[] key, long start, long stop) {
		// GMADD key member value geojson
		sendCommand(GMREVRANGE, key, toByteArray(start), toByteArray(stop), WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
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
	public void gmrelation(byte[] key, Geometry<?> geometry) {
		/*
		 * GMRELATION key geojson CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		 */
		sendCommand(GMRELATION, key, geometry.getJsonByte(), CONTAINS.raw, WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelation(byte[] key, Geometry<?> geometry, byte[] mpattern, byte[] vpattern) {
		/*
		 * GMRELATION key geojson CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		 */
		sendCommand(GMRELATION, key, geometry.getJsonByte(), CONTAINS.raw, MATCH.raw, mpattern, MATCHVALUE.raw, vpattern, WITHVALUES.raw,
				WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelation(byte[] key, Geometry<?> geometry, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern) {
		/*
		 * GMRELATION key geojson CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		 */
		sendCommand(GMRELATION, key, geometry.getJsonByte(), CONTAINS.raw, SCORE.raw, min, max, MATCH.raw, mpattern, MATCHVALUE.raw,
				vpattern, WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember) {
		/*
		 * GMRELATIONBYMEMBER key BY bykey bymember CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		 */
		sendCommand(GMRELATIONBYMEMBER, key, BY.raw, byKey, byMember, CONTAINS.raw, WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] mpattern, byte[] vpattern) {
		/*
		 * GMRELATIONBYMEMBER key BY bykey bymember CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		 */
		sendCommand(GMRELATIONBYMEMBER, key, BY.raw, byKey, byMember, CONTAINS.raw, MATCH.raw, mpattern, MATCHVALUE.raw, vpattern,
				WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmrelationByMember(byte[] key, byte[] byKey, byte[] byMember, byte[] min, byte[] max, byte[] mpattern, byte[] vpattern) {
		/*
		 * GMRELATIONBYMEMBER key BY bykey bymember CONTAINS|WITHIN [WITHVALUES] [WITHGEOJSON]
		 */
		sendCommand(GMRELATIONBYMEMBER, key, BY.raw, byKey, byMember, CONTAINS.raw, SCORE.raw, min, max, MATCH.raw, mpattern,
				MATCHVALUE.raw, vpattern, WITHVALUES.raw, WITHSCORES.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmnn(final byte[] key, final double x, final double y, final long count) {
		/*
		 *  GMNN key latitude longitude LIMIT count [MATCHVALUE pattern] [WITHVALUES] [WITHDISTANCE] [WITHGEOJSON]
		 */
		sendCommand(GMNN, key, toByteArray(x), toByteArray(y), LIMIT.raw, toByteArray(count), WITHVALUES.raw, WITHSCORES.raw,
				WITHDISTANCE.raw, WITHGEOJSON.raw);
	}

	@Override
	public void gmnn(final byte[] key, final double x, final double y, final long count, final byte[] vpattern) {
		/*
		 *  GMNN key latitude longitude LIMIT count [MATCHVALUE pattern] [WITHVALUES] [WITHDISTANCE] [WITHGEOJSON]
		 */
		sendCommand(GMNN, key, toByteArray(x), toByteArray(y), LIMIT.raw, toByteArray(count), MATCHVALUE.raw, vpattern, WITHVALUES.raw,
				WITHSCORES.raw, WITHDISTANCE.raw, WITHGEOJSON.raw);
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
