package redis.clients.jedis;

import java.util.Set;

/**
 * Geo interface
 * 
 * @author horanghi
 */
public interface GeoCommands {

	Long gadd(String key, double x, double y, double distance, UNITS unit, String member, String value);

	Long gadd(String key, double x, double y, double distance, String member, String value);

	Set<String> gradius(String key, double x, double y, double distance, UNITS unit);

	Set<String> gradius(String key, double x, double y, double distance);

	GCircle gmember(final String key, final String member);

	// biz..

	Long gaddPoint(final String key, final double x, final double y, final String member, final String value);

	Long gaddCircle(final String key, final double x, final double y, final double distance, final String member, final String value);

	Set<GCircle> gsearchCircle(String key, double x, double y, double distance);

	// Long gadd(String key, Map<String, Double> scoreMembers);

	// Set<String> grange(String key, long start, long end);
	//
	// Long grem(String key, String... member);
	//
	// Double gincrby(String key, double score, String member);
	//
	// Long grank(String key, String member);
	//
	// Long grevrank(String key, String member);
	//
	// Set<String> grevrange(String key, long start, long end);
	//
	// Set<Tuple> grangeWithScores(String key, long start, long end);
	//
	// Set<Tuple> grevrangeWithScores(String key, long start, long end);
	//
	// Long gcard(String key);
	//
	// Double gscore(String key, String member);
	//
	// Long gcount(String key, double min, double max);
	//
	// Long gcount(String key, String min, String max);
	//
	// Set<String> grangeByScore(String key, double min, double max);
	//
	// Set<String> grangeByScore(String key, String min, String max);
	//
	// Set<String> grevrangeByScore(String key, double max, double min);
	//
	// Set<String> grangeByScore(String key, double min, double max, int offset, int count);
	//
	// Set<String> grevrangeByScore(String key, String max, String min);
	//
	// Set<String> grangeByScore(String key, String min, String max, int offset, int count);
	//
	// Set<String> grevrangeByScore(String key, double max, double min, int offset, int count);
	//
	// Set<Tuple> grangeByScoreWithScores(String key, double min, double max);
	//
	// Set<Tuple> grevrangeByScoreWithScores(String key, double max, double min);
	//
	// Set<Tuple> grangeByScoreWithScores(String key, double min, double max, int offset, int count);
	//
	// Set<String> grevrangeByScore(String key, String max, String min, int offset, int count);
	//
	// Set<Tuple> grangeByScoreWithScores(String key, String min, String max);
	//
	// Set<Tuple> grevrangeByScoreWithScores(String key, String max, String min);
	//
	// Set<Tuple> grangeByScoreWithScores(String key, String min, String max, int offset, int count);
	//
	// Set<Tuple> grevrangeByScoreWithScores(String key, double max, double min, int offset, int count);
	//
	// Set<Tuple> grevrangeByScoreWithScores(String key, String max, String min, int offset, int count);
	//
	// Long gremrangeByRank(String key, long start, long end);
	//
	// Long gremrangeByScore(String key, double start, double end);
	//
	// Long gremrangeByScore(String key, String start, String end);
	//
	// ScanResult<Tuple> gscan(final String key, int cursor);
	//
	// ScanResult<Tuple> gscan(final String key, final String cursor);
}
