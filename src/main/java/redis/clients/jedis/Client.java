package redis.clients.jedis;

import static redis.clients.jedis.Protocol.toByteArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import redis.clients.jedis.Protocol.ORDERBY;
import redis.clients.jedis.Protocol.RELATION;
import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;
import redis.clients.util.SafeEncoder;

public class Client extends BinaryClient4Spatial implements Commands4Spatial {
	public Client(final String host) {
		super(host);
	}

	public Client(final String host, final int port) {
		super(host, port);
	}

	public void set(final String key, final String value) {
		set(SafeEncoder.encode(key), SafeEncoder.encode(value));
	}

	public void set(final String key, final String value, final String nxxx, final String expx, final long time) {
		set(SafeEncoder.encode(key), SafeEncoder.encode(value), SafeEncoder.encode(nxxx), SafeEncoder.encode(expx), time);
	}

	public void get(final String key) {
		get(SafeEncoder.encode(key));
	}

	public void exists(final String key) {
		exists(SafeEncoder.encode(key));
	}

	public void del(final String... keys) {
		final byte[][] bkeys = new byte[keys.length][];
		for (int i = 0; i < keys.length; i++) {
			bkeys[i] = SafeEncoder.encode(keys[i]);
		}
		del(bkeys);
	}

	public void type(final String key) {
		type(SafeEncoder.encode(key));
	}

	public void keys(final String vpattern) {
		keys(SafeEncoder.encode(vpattern));
	}

	public void rename(final String oldkey, final String newkey) {
		rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
	}

	public void renamenx(final String oldkey, final String newkey) {
		renamenx(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
	}

	public void expire(final String key, final int seconds) {
		expire(SafeEncoder.encode(key), seconds);
	}

	public void expireAt(final String key, final long unixTime) {
		expireAt(SafeEncoder.encode(key), unixTime);
	}

	public void ttl(final String key) {
		ttl(SafeEncoder.encode(key));
	}

	public void move(final String key, final int dbIndex) {
		move(SafeEncoder.encode(key), dbIndex);
	}

	public void getSet(final String key, final String value) {
		getSet(SafeEncoder.encode(key), SafeEncoder.encode(value));
	}

	public void mget(final String... keys) {
		final byte[][] bkeys = new byte[keys.length][];
		for (int i = 0; i < bkeys.length; i++) {
			bkeys[i] = SafeEncoder.encode(keys[i]);
		}
		mget(bkeys);
	}

	public void setnx(final String key, final String value) {
		setnx(SafeEncoder.encode(key), SafeEncoder.encode(value));
	}

	public void setex(final String key, final int seconds, final String value) {
		setex(SafeEncoder.encode(key), seconds, SafeEncoder.encode(value));
	}

	public void mset(final String... keysvalues) {
		final byte[][] bkeysvalues = new byte[keysvalues.length][];
		for (int i = 0; i < keysvalues.length; i++) {
			bkeysvalues[i] = SafeEncoder.encode(keysvalues[i]);
		}
		mset(bkeysvalues);
	}

	public void msetnx(final String... keysvalues) {
		final byte[][] bkeysvalues = new byte[keysvalues.length][];
		for (int i = 0; i < keysvalues.length; i++) {
			bkeysvalues[i] = SafeEncoder.encode(keysvalues[i]);
		}
		msetnx(bkeysvalues);
	}

	public void decrBy(final String key, final long integer) {
		decrBy(SafeEncoder.encode(key), integer);
	}

	public void decr(final String key) {
		decr(SafeEncoder.encode(key));
	}

	public void incrBy(final String key, final long integer) {
		incrBy(SafeEncoder.encode(key), integer);
	}

	public void incr(final String key) {
		incr(SafeEncoder.encode(key));
	}

	public void append(final String key, final String value) {
		append(SafeEncoder.encode(key), SafeEncoder.encode(value));
	}

	public void substr(final String key, final int start, final int end) {
		substr(SafeEncoder.encode(key), start, end);
	}

	public void hset(final String key, final String field, final String value) {
		hset(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(value));
	}

	public void hget(final String key, final String field) {
		hget(SafeEncoder.encode(key), SafeEncoder.encode(field));
	}

	public void hsetnx(final String key, final String field, final String value) {
		hsetnx(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(value));
	}

	public void hmset(final String key, final Map<String, String> hash) {
		final Map<byte[], byte[]> bhash = new HashMap<byte[], byte[]>(hash.size());
		for (final Entry<String, String> entry : hash.entrySet()) {
			bhash.put(SafeEncoder.encode(entry.getKey()), SafeEncoder.encode(entry.getValue()));
		}
		hmset(SafeEncoder.encode(key), bhash);
	}

	public void hmget(final String key, final String... fields) {
		final byte[][] bfields = new byte[fields.length][];
		for (int i = 0; i < bfields.length; i++) {
			bfields[i] = SafeEncoder.encode(fields[i]);
		}
		hmget(SafeEncoder.encode(key), bfields);
	}

	public void hincrBy(final String key, final String field, final long value) {
		hincrBy(SafeEncoder.encode(key), SafeEncoder.encode(field), value);
	}

	public void hexists(final String key, final String field) {
		hexists(SafeEncoder.encode(key), SafeEncoder.encode(field));
	}

	public void hdel(final String key, final String... fields) {
		hdel(SafeEncoder.encode(key), SafeEncoder.encodeMany(fields));
	}

	public void hlen(final String key) {
		hlen(SafeEncoder.encode(key));
	}

	public void hkeys(final String key) {
		hkeys(SafeEncoder.encode(key));
	}

	public void hvals(final String key) {
		hvals(SafeEncoder.encode(key));
	}

	public void hgetAll(final String key) {
		hgetAll(SafeEncoder.encode(key));
	}

	public void rpush(final String key, final String... string) {
		rpush(SafeEncoder.encode(key), SafeEncoder.encodeMany(string));
	}

	public void lpush(final String key, final String... string) {
		lpush(SafeEncoder.encode(key), SafeEncoder.encodeMany(string));
	}

	public void llen(final String key) {
		llen(SafeEncoder.encode(key));
	}

	public void lrange(final String key, final long start, final long end) {
		lrange(SafeEncoder.encode(key), start, end);
	}

	public void ltrim(final String key, final long start, final long end) {
		ltrim(SafeEncoder.encode(key), start, end);
	}

	public void lindex(final String key, final long index) {
		lindex(SafeEncoder.encode(key), index);
	}

	public void lset(final String key, final long index, final String value) {
		lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
	}

	public void lrem(final String key, long count, final String value) {
		lrem(SafeEncoder.encode(key), count, SafeEncoder.encode(value));
	}

	public void lpop(final String key) {
		lpop(SafeEncoder.encode(key));
	}

	public void rpop(final String key) {
		rpop(SafeEncoder.encode(key));
	}

	public void rpoplpush(final String srckey, final String dstkey) {
		rpoplpush(SafeEncoder.encode(srckey), SafeEncoder.encode(dstkey));
	}

	public void sadd(final String key, final String... members) {
		sadd(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
	}

	public void smembers(final String key) {
		smembers(SafeEncoder.encode(key));
	}

	public void srem(final String key, final String... members) {
		srem(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
	}

	public void spop(final String key) {
		spop(SafeEncoder.encode(key));
	}

	public void smove(final String srckey, final String dstkey, final String member) {
		smove(SafeEncoder.encode(srckey), SafeEncoder.encode(dstkey), SafeEncoder.encode(member));
	}

	public void scard(final String key) {
		scard(SafeEncoder.encode(key));
	}

	public void sismember(final String key, final String member) {
		sismember(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	public void sinter(final String... keys) {
		final byte[][] bkeys = new byte[keys.length][];
		for (int i = 0; i < bkeys.length; i++) {
			bkeys[i] = SafeEncoder.encode(keys[i]);
		}
		sinter(bkeys);
	}

	public void sinterstore(final String dstkey, final String... keys) {
		final byte[][] bkeys = new byte[keys.length][];
		for (int i = 0; i < bkeys.length; i++) {
			bkeys[i] = SafeEncoder.encode(keys[i]);
		}
		sinterstore(SafeEncoder.encode(dstkey), bkeys);
	}

	public void sunion(final String... keys) {
		final byte[][] bkeys = new byte[keys.length][];
		for (int i = 0; i < bkeys.length; i++) {
			bkeys[i] = SafeEncoder.encode(keys[i]);
		}
		sunion(bkeys);
	}

	public void sunionstore(final String dstkey, final String... keys) {
		final byte[][] bkeys = new byte[keys.length][];
		for (int i = 0; i < bkeys.length; i++) {
			bkeys[i] = SafeEncoder.encode(keys[i]);
		}
		sunionstore(SafeEncoder.encode(dstkey), bkeys);
	}

	public void sdiff(final String... keys) {
		final byte[][] bkeys = new byte[keys.length][];
		for (int i = 0; i < bkeys.length; i++) {
			bkeys[i] = SafeEncoder.encode(keys[i]);
		}
		sdiff(bkeys);
	}

	public void sdiffstore(final String dstkey, final String... keys) {
		final byte[][] bkeys = new byte[keys.length][];
		for (int i = 0; i < bkeys.length; i++) {
			bkeys[i] = SafeEncoder.encode(keys[i]);
		}
		sdiffstore(SafeEncoder.encode(dstkey), bkeys);
	}

	public void srandmember(final String key) {
		srandmember(SafeEncoder.encode(key));
	}

	public void zadd(final String key, final double score, final String member) {
		zadd(SafeEncoder.encode(key), score, SafeEncoder.encode(member));
	}

	public void zrange(final String key, final long start, final long end) {
		zrange(SafeEncoder.encode(key), start, end);
	}

	public void zrem(final String key, final String... members) {
		zrem(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
	}

	public void zincrby(final String key, final double score, final String member) {
		zincrby(SafeEncoder.encode(key), score, SafeEncoder.encode(member));
	}

	public void zrank(final String key, final String member) {
		zrank(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	public void zrevrank(final String key, final String member) {
		zrevrank(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	public void zrevrange(final String key, final long start, final long end) {
		zrevrange(SafeEncoder.encode(key), start, end);
	}

	public void zrangeWithScores(final String key, final long start, final long end) {
		zrangeWithScores(SafeEncoder.encode(key), start, end);
	}

	public void zrevrangeWithScores(final String key, final long start, final long end) {
		zrevrangeWithScores(SafeEncoder.encode(key), start, end);
	}

	public void zcard(final String key) {
		zcard(SafeEncoder.encode(key));
	}

	public void zscore(final String key, final String member) {
		zscore(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	public void watch(final String... keys) {
		final byte[][] bargs = new byte[keys.length][];
		for (int i = 0; i < bargs.length; i++) {
			bargs[i] = SafeEncoder.encode(keys[i]);
		}
		watch(bargs);
	}

	public void sort(final String key) {
		sort(SafeEncoder.encode(key));
	}

	public void sort(final String key, final SortingParams sortingParameters) {
		sort(SafeEncoder.encode(key), sortingParameters);
	}

	public void blpop(final String[] args) {
		final byte[][] bargs = new byte[args.length][];
		for (int i = 0; i < bargs.length; i++) {
			bargs[i] = SafeEncoder.encode(args[i]);
		}
		blpop(bargs);
	}

	public void blpop(final int timeout, final String... keys) {
		List<String> args = new ArrayList<String>();
		for (String arg : keys) {
			args.add(arg);
		}
		args.add(String.valueOf(timeout));
		blpop(args.toArray(new String[args.size()]));
	}

	public void sort(final String key, final SortingParams sortingParameters, final String dstkey) {
		sort(SafeEncoder.encode(key), sortingParameters, SafeEncoder.encode(dstkey));
	}

	public void sort(final String key, final String dstkey) {
		sort(SafeEncoder.encode(key), SafeEncoder.encode(dstkey));
	}

	public void brpop(final String[] args) {
		final byte[][] bargs = new byte[args.length][];
		for (int i = 0; i < bargs.length; i++) {
			bargs[i] = SafeEncoder.encode(args[i]);
		}
		brpop(bargs);
	}

	public void brpop(final int timeout, final String... keys) {
		List<String> args = new ArrayList<String>();
		for (String arg : keys) {
			args.add(arg);
		}
		args.add(String.valueOf(timeout));
		brpop(args.toArray(new String[args.size()]));
	}

	public void zcount(final String key, final double min, final double max) {
		zcount(SafeEncoder.encode(key), toByteArray(min), toByteArray(max));
	}

	public void zcount(final String key, final String min, final String max) {
		zcount(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
	}

	public void zrangeByScore(final String key, final double min, final double max) {
		zrangeByScore(SafeEncoder.encode(key), toByteArray(min), toByteArray(max));
	}

	public void zrangeByScore(final String key, final String min, final String max) {
		zrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
	}

	public void zrangeByScore(final String key, final double min, final double max, final int offset, int count) {
		zrangeByScore(SafeEncoder.encode(key), toByteArray(min), toByteArray(max), offset, count);
	}

	public void zrangeByScoreWithScores(final String key, final double min, final double max) {
		zrangeByScoreWithScores(SafeEncoder.encode(key), toByteArray(min), toByteArray(max));
	}

	public void zrangeByScoreWithScores(final String key, final double min, final double max, final int offset, final int count) {
		zrangeByScoreWithScores(SafeEncoder.encode(key), toByteArray(min), toByteArray(max), offset, count);
	}

	public void zrevrangeByScore(final String key, final double max, final double min) {
		zrevrangeByScore(SafeEncoder.encode(key), toByteArray(max), toByteArray(min));
	}

	public void zrangeByScore(final String key, final String min, final String max, final int offset, int count) {
		zrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max), offset, count);
	}

	public void zrangeByScoreWithScores(final String key, final String min, final String max) {
		zrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
	}

	public void zrangeByScoreWithScores(final String key, final String min, final String max, final int offset, final int count) {
		zrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max), offset, count);
	}

	public void zrevrangeByScore(final String key, final String max, final String min) {
		zrevrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min));
	}

	public void zrevrangeByScore(final String key, final double max, final double min, final int offset, int count) {
		zrevrangeByScore(SafeEncoder.encode(key), toByteArray(max), toByteArray(min), offset, count);
	}

	public void zrevrangeByScore(final String key, final String max, final String min, final int offset, int count) {
		zrevrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min), offset, count);
	}

	public void zrevrangeByScoreWithScores(final String key, final double max, final double min) {
		zrevrangeByScoreWithScores(SafeEncoder.encode(key), toByteArray(max), toByteArray(min));
	}

	public void zrevrangeByScoreWithScores(final String key, final String max, final String min) {
		zrevrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min));
	}

	public void zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset, final int count) {
		zrevrangeByScoreWithScores(SafeEncoder.encode(key), toByteArray(max), toByteArray(min), offset, count);
	}

	public void zrevrangeByScoreWithScores(final String key, final String max, final String min, final int offset, final int count) {
		zrevrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min), offset, count);
	}

	public void zremrangeByRank(final String key, final long start, final long end) {
		zremrangeByRank(SafeEncoder.encode(key), start, end);
	}

	public void zremrangeByScore(final String key, final double start, final double end) {
		zremrangeByScore(SafeEncoder.encode(key), toByteArray(start), toByteArray(end));
	}

	public void zremrangeByScore(final String key, final String start, final String end) {
		zremrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(start), SafeEncoder.encode(end));
	}

	public void zunionstore(final String dstkey, final String... sets) {
		final byte[][] bsets = new byte[sets.length][];
		for (int i = 0; i < bsets.length; i++) {
			bsets[i] = SafeEncoder.encode(sets[i]);
		}
		zunionstore(SafeEncoder.encode(dstkey), bsets);
	}

	public void zunionstore(final String dstkey, final ZParams params, final String... sets) {
		final byte[][] bsets = new byte[sets.length][];
		for (int i = 0; i < bsets.length; i++) {
			bsets[i] = SafeEncoder.encode(sets[i]);
		}
		zunionstore(SafeEncoder.encode(dstkey), params, bsets);
	}

	public void zinterstore(final String dstkey, final String... sets) {
		final byte[][] bsets = new byte[sets.length][];
		for (int i = 0; i < bsets.length; i++) {
			bsets[i] = SafeEncoder.encode(sets[i]);
		}
		zinterstore(SafeEncoder.encode(dstkey), bsets);
	}

	public void zinterstore(final String dstkey, final ZParams params, final String... sets) {
		final byte[][] bsets = new byte[sets.length][];
		for (int i = 0; i < bsets.length; i++) {
			bsets[i] = SafeEncoder.encode(sets[i]);
		}
		zinterstore(SafeEncoder.encode(dstkey), params, bsets);
	}

	public void strlen(final String key) {
		strlen(SafeEncoder.encode(key));
	}

	public void lpushx(final String key, final String... string) {
		lpushx(SafeEncoder.encode(key), getByteParams(string));
	}

	public void persist(final String key) {
		persist(SafeEncoder.encode(key));
	}

	public void rpushx(final String key, final String... string) {
		rpushx(SafeEncoder.encode(key), getByteParams(string));
	}

	public void echo(final String string) {
		echo(SafeEncoder.encode(string));
	}

	public void linsert(final String key, final LIST_POSITION where, final String pivot, final String value) {
		linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
	}

	public void brpoplpush(String source, String destination, int timeout) {
		brpoplpush(SafeEncoder.encode(source), SafeEncoder.encode(destination), timeout);
	}

	public void setbit(final String key, final long offset, final boolean value) {
		setbit(SafeEncoder.encode(key), offset, value);
	}

	public void setbit(final String key, final long offset, final String value) {
		setbit(SafeEncoder.encode(key), offset, SafeEncoder.encode(value));
	}

	public void getbit(String key, long offset) {
		getbit(SafeEncoder.encode(key), offset);
	}

	public void setrange(String key, long offset, String value) {
		setrange(SafeEncoder.encode(key), offset, SafeEncoder.encode(value));
	}

	public void getrange(String key, long startOffset, long endOffset) {
		getrange(SafeEncoder.encode(key), startOffset, endOffset);
	}

	public void publish(final String channel, final String message) {
		publish(SafeEncoder.encode(channel), SafeEncoder.encode(message));
	}

	public void unsubscribe(final String... channels) {
		final byte[][] cs = new byte[channels.length][];
		for (int i = 0; i < cs.length; i++) {
			cs[i] = SafeEncoder.encode(channels[i]);
		}
		unsubscribe(cs);
	}

	public void psubscribe(final String... vpatterns) {
		final byte[][] ps = new byte[vpatterns.length][];
		for (int i = 0; i < ps.length; i++) {
			ps[i] = SafeEncoder.encode(vpatterns[i]);
		}
		psubscribe(ps);
	}

	public void punsubscribe(final String... vpatterns) {
		final byte[][] ps = new byte[vpatterns.length][];
		for (int i = 0; i < ps.length; i++) {
			ps[i] = SafeEncoder.encode(vpatterns[i]);
		}
		punsubscribe(ps);
	}

	public void subscribe(final String... channels) {
		final byte[][] cs = new byte[channels.length][];
		for (int i = 0; i < cs.length; i++) {
			cs[i] = SafeEncoder.encode(channels[i]);
		}
		subscribe(cs);
	}

	public void pubsubChannels(String vpattern) {
		pubsub(Protocol.PUBSUB_CHANNELS, vpattern);
	}

	public void pubsubNumPat() {
		pubsub(Protocol.PUBSUB_NUM_PAT);
	}

	public void pubsubNumSub(String... channels) {
		pubsub(Protocol.PUBSUB_NUMSUB, channels);
	}

	public void configSet(String parameter, String value) {
		configSet(SafeEncoder.encode(parameter), SafeEncoder.encode(value));
	}

	public void configGet(String vpattern) {
		configGet(SafeEncoder.encode(vpattern));
	}

	private byte[][] getByteParams(String... params) {
		byte[][] p = new byte[params.length][];
		for (int i = 0; i < params.length; i++)
			p[i] = SafeEncoder.encode(params[i]);

		return p;
	}

	public void eval(String script, int keyCount, String... params) {
		eval(SafeEncoder.encode(script), toByteArray(keyCount), getByteParams(params));
	}

	public void evalsha(String sha1, int keyCount, String... params) {
		evalsha(SafeEncoder.encode(sha1), toByteArray(keyCount), getByteParams(params));
	}

	public void scriptExists(String... sha1) {
		final byte[][] bsha1 = new byte[sha1.length][];
		for (int i = 0; i < bsha1.length; i++) {
			bsha1[i] = SafeEncoder.encode(sha1[i]);
		}
		scriptExists(bsha1);
	}

	public void scriptLoad(String script) {
		scriptLoad(SafeEncoder.encode(script));
	}

	public void zadd(String key, Map<String, Double> scoreMembers) {

		HashMap<byte[], Double> binaryScoreMembers = new HashMap<byte[], Double>();

		for (Map.Entry<String, Double> entry : scoreMembers.entrySet()) {

			binaryScoreMembers.put(SafeEncoder.encode(entry.getKey()), entry.getValue());
		}

		zaddBinary(SafeEncoder.encode(key), binaryScoreMembers);
	}

	public void objectRefcount(String key) {
		objectRefcount(SafeEncoder.encode(key));
	}

	public void objectIdletime(String key) {
		objectIdletime(SafeEncoder.encode(key));
	}

	public void objectEncoding(String key) {
		objectEncoding(SafeEncoder.encode(key));
	}

	public void bitcount(final String key) {
		bitcount(SafeEncoder.encode(key));
	}

	public void bitcount(final String key, long start, long end) {
		bitcount(SafeEncoder.encode(key), start, end);
	}

	public void bitop(BitOP op, final String destKey, String... srcKeys) {
		bitop(op, SafeEncoder.encode(destKey), getByteParams(srcKeys));
	}

	public void sentinel(final String... args) {
		final byte[][] arg = new byte[args.length][];
		for (int i = 0; i < arg.length; i++) {
			arg[i] = SafeEncoder.encode(args[i]);
		}
		sentinel(arg);
	}

	public void dump(final String key) {
		dump(SafeEncoder.encode(key));
	}

	public void restore(final String key, final int ttl, final byte[] serializedValue) {
		restore(SafeEncoder.encode(key), ttl, serializedValue);
	}

	public void pexpire(final String key, final int milliseconds) {
		pexpire(SafeEncoder.encode(key), milliseconds);
	}

	public void pexpireAt(final String key, final long millisecondsTimestamp) {
		pexpireAt(SafeEncoder.encode(key), millisecondsTimestamp);
	}

	public void pttl(final String key) {
		pttl(SafeEncoder.encode(key));
	}

	public void incrByFloat(final String key, final double increment) {
		incrByFloat(SafeEncoder.encode(key), increment);
	}

	public void psetex(final String key, final int milliseconds, final String value) {
		psetex(SafeEncoder.encode(key), milliseconds, SafeEncoder.encode(value));
	}

	public void set(final String key, final String value, final String nxxx) {
		set(SafeEncoder.encode(key), SafeEncoder.encode(value), SafeEncoder.encode(nxxx));
	}

	public void set(final String key, final String value, final String nxxx, final String expx, final int time) {
		set(SafeEncoder.encode(key), SafeEncoder.encode(value), SafeEncoder.encode(nxxx), SafeEncoder.encode(expx), time);
	}

	public void srandmember(final String key, final int count) {
		srandmember(SafeEncoder.encode(key), count);
	}

	public void clientKill(final String client) {
		clientKill(SafeEncoder.encode(client));
	}

	public void clientSetname(final String name) {
		clientSetname(SafeEncoder.encode(name));
	}

	public void migrate(final String host, final int port, final String key, final int destinationDb, final int timeout) {
		migrate(SafeEncoder.encode(host), port, SafeEncoder.encode(key), destinationDb, timeout);
	}

	public void hincrByFloat(final String key, final String field, double increment) {
		hincrByFloat(SafeEncoder.encode(key), SafeEncoder.encode(field), increment);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public void hscan(final String key, int cursor, final ScanParams params) {
		hscan(SafeEncoder.encode(key), cursor, params);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public void sscan(final String key, int cursor, final ScanParams params) {
		sscan(SafeEncoder.encode(key), cursor, params);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public void zscan(final String key, int cursor, final ScanParams params) {
		zscan(SafeEncoder.encode(key), cursor, params);
	}

	public void scan(final String cursor, final ScanParams params) {
		scan(SafeEncoder.encode(cursor), params);
	}

	public void hscan(final String key, final String cursor, final ScanParams params) {
		hscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
	}

	public void sscan(final String key, final String cursor, final ScanParams params) {
		sscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
	}

	public void zscan(final String key, final String cursor, final ScanParams params) {
		zscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
	}

	public void cluster(final String subcommand, final int... args) {
		final byte[][] arg = new byte[args.length + 1][];
		for (int i = 1; i < arg.length; i++) {
			arg[i] = toByteArray(args[i - 1]);
		}
		arg[0] = SafeEncoder.encode(subcommand);
		cluster(arg);
	}

	public void pubsub(final String subcommand, final String... args) {
		final byte[][] arg = new byte[args.length + 1][];
		for (int i = 1; i < arg.length; i++) {
			arg[i] = SafeEncoder.encode(args[i - 1]);
		}
		arg[0] = SafeEncoder.encode(subcommand);
		pubsub(arg);
	}

	public void cluster(final String subcommand, final String... args) {
		final byte[][] arg = new byte[args.length + 1][];
		for (int i = 1; i < arg.length; i++) {
			arg[i] = SafeEncoder.encode(args[i - 1]);
		}
		arg[0] = SafeEncoder.encode(subcommand);
		cluster(arg);
	}

	public void cluster(final String subcommand) {
		final byte[][] arg = new byte[1][];
		arg[0] = SafeEncoder.encode(subcommand);
		cluster(arg);
	}

	public void clusterNodes() {
		cluster(Protocol.CLUSTER_NODES);
	}

	public void clusterMeet(final String ip, final int port) {
		cluster(Protocol.CLUSTER_MEET, ip, String.valueOf(port));
	}

	public void clusterAddSlots(final int... slots) {
		cluster(Protocol.CLUSTER_ADDSLOTS, slots);
	}

	public void clusterDelSlots(final int... slots) {
		cluster(Protocol.CLUSTER_DELSLOTS, slots);
	}

	public void clusterInfo() {
		cluster(Protocol.CLUSTER_INFO);
	}

	public void clusterGetKeysInSlot(final int slot, final int count) {
		final int[] args = new int[] { slot, count };
		cluster(Protocol.CLUSTER_GETKEYSINSLOT, args);
	}

	public void clusterSetSlotNode(final int slot, final String nodeId) {
		cluster(Protocol.CLUSTER_SETSLOT, String.valueOf(slot), Protocol.CLUSTER_SETSLOT_NODE, nodeId);
	}

	public void clusterSetSlotMigrating(final int slot, final String nodeId) {
		cluster(Protocol.CLUSTER_SETSLOT, String.valueOf(slot), Protocol.CLUSTER_SETSLOT_MIGRATING, nodeId);
	}

	public void clusterSetSlotImporting(final int slot, final String nodeId) {
		cluster(Protocol.CLUSTER_SETSLOT, String.valueOf(slot), Protocol.CLUSTER_SETSLOT_IMPORTING, nodeId);
	}

	// Spatial methods =================================================================
	// TODO
	@Override
	public void gpexists(String key, String member) {
		gpexists(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public void gpadd(final String key, final double lat, final double lon, final String member, final String value) {
		gpadd(SafeEncoder.encode(key), lat, lon, 0, UNITS.M, SafeEncoder.encode(member), SafeEncoder.encode(value));
	}

	@Override
	public void gpadd(final String key, final double lat, final double lon, final String member, final String value, final double score) {
		gpadd(SafeEncoder.encode(key), lat, lon, 0, UNITS.M, SafeEncoder.encode(member), SafeEncoder.encode(value), score);
	}

	@Override
	public void gpadd(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String member,
			final String value) {
		gpadd(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(member), SafeEncoder.encode(value));
	}

	@Override
	public void gpadd(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String member,
			final String value, final double score) {
		gpadd(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(member), SafeEncoder.encode(value), score);
	}

	// gpupdate

	@Override
	public void gpupdate(final String key, final String member, final double lat, final double lon) {
		gpupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), lat, lon);
	}

	@Override
	public void gpupdate(final String key, final String member, final double lat, final double lon, final double radius, final UNITS unit) {
		gpupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), lat, lon, radius, unit);
	}

	@Override
	public void gpupdate(final String key, final String member, final double lat, final double lon, final double radius, final UNITS unit,
			final String value, double score) {
		gpupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), lat, lon, radius, unit, SafeEncoder.encode(value), score);
	}

	@Override
	public void gpupdate(String key, String member, double radius, UNITS unit) {
		gpupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), radius, unit);
	}

	@Override
	public void gpupdate(String key, String member, double score) {
		gpupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), score);
	}

	@Override
	public void gpupdate(String key, String member, String value) {
		gpupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), SafeEncoder.encode(value));
	}

	// gpradius

	@Override
	public void gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit) {
		gpradius(SafeEncoder.encode(key), lat, lon, radius, unit);
	}

	@Override
	public void gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String vpattern) {
		gpradius(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String min,
			final String max, final String vpattern) {
		gpradius(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(min), SafeEncoder.encode(max),
				SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String min,
			final String max, final ORDERBY order) {
		gpradius(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(min), SafeEncoder.encode(max), order);
	}

	@Override
	public void gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String min,
			final String max, final String vpattern, final long offset, final long count, final ORDERBY order) {
		gpradius(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(min), SafeEncoder.encode(max),
				SafeEncoder.encode(vpattern), offset, count, order);
	}

	@Override
	public void gpradius(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String min,
			final String max, final String mpattern, final String vpattern, final long offset, final long count, final ORDERBY order) {
		gpradius(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(min), SafeEncoder.encode(max),
				SafeEncoder.encode(mpattern), SafeEncoder.encode(vpattern), offset, count, order);
	}

	// gpcircle

	@Override
	public void gpcircle(final String key, final String member) {
		gpcircle(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public void gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit) {
		gpcircle(SafeEncoder.encode(key), lat, lon, radius, unit);
	}

	@Override
	public void gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit, final RELATION scope,
			final ORDERBY order) {
		gpcircle(SafeEncoder.encode(key), lat, lon, radius, unit, scope, order);
	}

	@Override
	public void gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit, final String vpattern) {
		gpcircle(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String vpattern, final RELATION scope, final ORDERBY order) {
		gpcircle(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(vpattern), scope, order);
	}

	@Override
	public void gpcircle(final String key, final double lat, final double lon, final double radius, final UNITS unit,
			final String mpattern, final String vpattern, final RELATION scope, final ORDERBY order) {
		gpcircle(SafeEncoder.encode(key), lat, lon, radius, unit, SafeEncoder.encode(mpattern), SafeEncoder.encode(vpattern), scope, order);
	}

	// gpradiusByMember

	@Override
	public void gpradiusByMember(final String key, final String bykey, final String bymember) {
		gpradiusByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember));
	}

	@Override
	public void gpradiusByMember(final String key, final String bykey, final String bymember, final String vpattern) {
		gpradiusByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember), SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpradiusByMember(final String key, final String bykey, final String bymember, final String min, final String max,
			final String vpattern) {
		gpradiusByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember), SafeEncoder.encode(min),
				SafeEncoder.encode(max), SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpradiusByMember(final String key, final String bykey, final String bymember, final String min, final String max,
			final String vpattern, final long offset, final long count, final ORDERBY order) {
		gpradiusByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember), SafeEncoder.encode(min),
				SafeEncoder.encode(max), SafeEncoder.encode(vpattern), offset, count, order);
	}

	@Override
	public void gpradiusByMember(final String key, final String bykey, final String bymember, final String min, final String max,
			final String mpattern, final String vpattern, final long offset, final long count, final ORDERBY order) {
		gpradiusByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember), SafeEncoder.encode(min),
				SafeEncoder.encode(max), SafeEncoder.encode(mpattern), SafeEncoder.encode(vpattern), offset, count, order);
	}

	// gpregionByMember

	@Override
	public void gpregionByMember(String key, String bykey, String bymember) {
		gpregionByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember));
	}

	@Override
	public void gpregionByMember(String key, String bykey, String bymember, String vpattern) {
		gpregionByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember), SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpregionByMember(String key, String bykey, String bymember, String min, String max, String vpattern) {
		gpregionByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember), SafeEncoder.encode(min),
				SafeEncoder.encode(max), SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpregionByMember(String key, String bykey, String bymember, String min, String max, String vpattern, long offset,
			long count, ORDERBY order) {
		gpregionByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember), SafeEncoder.encode(min),
				SafeEncoder.encode(max), SafeEncoder.encode(vpattern), offset, count, order);
	}

	@Override
	public void gpregionByMember(String key, String bykey, String bymember, String min, String max, String mpattern, String vpattern,
			long offset, long count, ORDERBY order) {
		gpregionByMember(SafeEncoder.encode(key), SafeEncoder.encode(bykey), SafeEncoder.encode(bymember), SafeEncoder.encode(min),
				SafeEncoder.encode(max), SafeEncoder.encode(mpattern), SafeEncoder.encode(vpattern), offset, count, order);
	}

	// gpnn

	@Override
	public void gpnn(final String key, final double lat, final double lon, final long offset, final long count) {
		gpnn(SafeEncoder.encode(key), lat, lon, offset, count);
	}

	@Override
	public void gpnn(final String key, final double lat, final double lon, final long offset, final long count, final String vpattern) {
		gpnn(SafeEncoder.encode(key), lat, lon, offset, count, SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpnn(final String key, final double lat, final double lon, final long offset, final long count, final String vpattern,
			final String min, final String max, ORDERBY order) {
		gpnn(SafeEncoder.encode(key), lat, lon, offset, count, SafeEncoder.encode(vpattern), SafeEncoder.encode(min),
				SafeEncoder.encode(max), order);
	}

	@Override
	public void gpnn(final String key, final double lat, final double lon, final long offset, final long count, final String mpattern,
			final String vpattern, final String min, final String max, ORDERBY order) {
		gpnn(SafeEncoder.encode(key), lat, lon, offset, count, SafeEncoder.encode(mpattern), SafeEncoder.encode(vpattern),
				SafeEncoder.encode(min), SafeEncoder.encode(max), order);
	}

	// gpregion

	@Override
	public void gpregion(final String key, final Polygon<?> polygon) {
		gpregion(SafeEncoder.encode(key), polygon);
	}

	@Override
	public void gpregion(final String key, final LineString<?> lineString) {
		gpregion(SafeEncoder.encode(key), lineString);
	}

	@Override
	public void gpregion(final String key, final Point<?> point) {
		gpregion(SafeEncoder.encode(key), point);
	}

	@Override
	public void gpregion(final String key, final Polygon<?> polygon, final String vpattern) {
		gpregion(SafeEncoder.encode(key), polygon, SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpregion(final String key, final LineString<?> lineString, final String vpattern) {
		gpregion(SafeEncoder.encode(key), lineString, SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpregion(final String key, final Point<?> point, final String vpattern) {
		gpregion(SafeEncoder.encode(key), point, SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpregion(final String key, final Polygon<?> polygon, final String min, final String max, final String vpattern) {
		gpregion(SafeEncoder.encode(key), polygon, SafeEncoder.encode(min), SafeEncoder.encode(max), SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpregion(final String key, final LineString<?> lineString, final String min, final String max, final String vpattern) {
		gpregion(SafeEncoder.encode(key), lineString, SafeEncoder.encode(min), SafeEncoder.encode(max), SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpregion(String key, Polygon<?> polygon, String min, String max, long offset, long count, String vpattern) {
		gpregion(SafeEncoder.encode(key), polygon, SafeEncoder.encode(min), SafeEncoder.encode(max), offset, count,
				SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpregion(String key, LineString<?> lineString, String min, String max, long offset, long count, String vpattern) {
		gpregion(SafeEncoder.encode(key), lineString, SafeEncoder.encode(min), SafeEncoder.encode(max), offset, count,
				SafeEncoder.encode(vpattern));
	}

	@Override
	public void gpcard(final String key) {
		gpcard(SafeEncoder.encode(key));
	}

	@Override
	public void gprem(final String key, final String member) {
		gprem(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public void gpget(final String key, final String member) {
		gpget(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public void gpmget(final String key, final String[] members) {
		byte[][] mlist = new byte[members.length][];
		int idx = 0;
		for (String member : members) {
			mlist[idx++] = SafeEncoder.encode(member);
		}
		gpmget(SafeEncoder.encode(key), mlist);
	}

	@Override
	public void gpscope(final String key, final String min, final String max, final String vpattern, ORDERBY order) {
		gpscope(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max), SafeEncoder.encode(vpattern), order);
	}

	@Override
	public void gpscope(final String key, final String min, final String max, final long offset, final long count, final String vpattern,
			ORDERBY order) {
		gpscope(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max), offset, count, SafeEncoder.encode(vpattern),
				order);
	}

	@Override
	public void gpscope(final String key, final String min, final String max, final long offset, final long count, final String mpattern,
			final String vpattern, ORDERBY order) {
		gpscope(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max), offset, count, SafeEncoder.encode(mpattern),
				SafeEncoder.encode(vpattern), order);
	}

	@Override
	public void ggadd(String key, String member, String value, Polygon<?> polygon) {
		ggadd(SafeEncoder.encode(key), SafeEncoder.encode(member), SafeEncoder.encode(value), polygon);
	}

	@Override
	public void ggadd(String key, String member, String value, LineString<?> lineString) {
		ggadd(SafeEncoder.encode(key), SafeEncoder.encode(member), SafeEncoder.encode(value), lineString);
	}

	@Override
	public void ggadd(String key, String member, String value, Point<?> point) {
		ggadd(SafeEncoder.encode(key), SafeEncoder.encode(member), SafeEncoder.encode(value), point);
	}

	@Override
	public void ggupdate(String key, String member, Polygon<?> polygon) {
		ggupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), polygon);
	}

	@Override
	public void ggupdate(String key, String member, LineString<?> lineString) {
		ggupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), lineString);
	}

	@Override
	public void ggupdate(String key, String member, Point<?> point) {
		ggupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), point);
	}

	@Override
	public void ggrange(final String key, final long start, final long stop) {
		ggrange(SafeEncoder.encode(key), start, stop);
	}

	@Override
	public void ggrevrange(String key, long start, long stop) {
		ggrevrange(SafeEncoder.encode(key), start, stop);
	}

	@Override
	public void ggcard(String key) {
		ggcard(SafeEncoder.encode(key));
	}

	@Override
	public void ggrem(String key, String member) {
		ggrem(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public void ggget(final String key, final String member) {
		ggget(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public void ggmget(final String key, final String[] members) {
		byte[][] mlist = new byte[members.length][];
		int idx = 0;
		for (String member : members) {
			mlist[idx++] = SafeEncoder.encode(member);
		}
		ggmget(SafeEncoder.encode(key), mlist);
	}

	@Override
	public void ggrelation(String key, Polygon<?> polygon) {
		ggrelation(SafeEncoder.encode(key), polygon);
	}

	@Override
	public void ggrelation(String key, LineString<?> lineString) {
		ggrelation(SafeEncoder.encode(key), lineString);
	}

	@Override
	public void ggrelation(String key, Point<?> point) {
		ggrelation(SafeEncoder.encode(key), point);
	}

	@Override
	public void ggrelationByMember(String key, String byKey, String byMember) {
		ggrelationByMember(SafeEncoder.encode(key), SafeEncoder.encode(byKey), SafeEncoder.encode(byMember));
	}

	@Override
	public void ggnn(String key, double lat, double lon, long count) {
		ggnn(SafeEncoder.encode(key), lat, lon, count);
	}

	@Override
	public void ggnn(String key, double lat, double lon, long count, String vpattern) {
		ggnn(SafeEncoder.encode(key), lat, lon, count, SafeEncoder.encode(vpattern));
	}

	@Override
	public void ggnn(String key, double lat, double lon, long count, String mpattern, String vpattern) {
		ggnn(SafeEncoder.encode(key), lat, lon, count, SafeEncoder.encode(mpattern), SafeEncoder.encode(vpattern));
	}

	/*Geometry */

	@Override
	public void gmsetBoundary(String key, double minx, double miny, double maxx, double maxy) {
		gmsetBoundary(SafeEncoder.encode(key), minx, miny, maxx, maxy);
	}

	@Override
	public void gmgetBoundary(String key) {
		gmgetBoundary(SafeEncoder.encode(key));
	}

	@Override
	public void gmrebuildBoundary(String key, double minx, double miny, double maxx, double maxy) {
		gmrebuildBoundary(SafeEncoder.encode(key), minx, miny, maxx, maxy);
	}

	@Override
	public void gmadd(String key, String member, String value, Polygon<?> polygon) {
		gmadd(SafeEncoder.encode(key), SafeEncoder.encode(member), SafeEncoder.encode(value), polygon);
	}

	@Override
	public void gmadd(String key, String member, String value, LineString<?> lineString) {
		gmadd(SafeEncoder.encode(key), SafeEncoder.encode(member), SafeEncoder.encode(value), lineString);
	}

	@Override
	public void gmadd(String key, String member, String value, Point<?> point) {
		gmadd(SafeEncoder.encode(key), SafeEncoder.encode(member), SafeEncoder.encode(value), point);
	}

	@Override
	public void gmadd(String key, double x, double y, String member, String value) {
		gmadd(SafeEncoder.encode(key), x, y, SafeEncoder.encode(member), SafeEncoder.encode(value));
	}

	@Override
	public void gmupdate(String key, String member, Polygon<?> polygon) {
		gmupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), polygon);
	}

	@Override
	public void gmupdate(String key, String member, LineString<?> lineString) {
		gmupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), lineString);
	}

	@Override
	public void gmupdate(String key, String member, Point<?> point) {
		gmupdate(SafeEncoder.encode(key), SafeEncoder.encode(member), point);
	}

	@Override
	public void gmrange(final String key, final long start, final long stop) {
		gmrange(SafeEncoder.encode(key), start, stop);
	}

	@Override
	public void gmrevrange(String key, long start, long stop) {
		gmrevrange(SafeEncoder.encode(key), start, stop);
	}

	@Override
	public void gmcard(String key) {
		gmcard(SafeEncoder.encode(key));
	}

	@Override
	public void gmrem(String key, String member) {
		gmrem(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public void gmget(final String key, final String member) {
		gmget(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public void gmmget(final String key, final String[] members) {
		byte[][] mlist = new byte[members.length][];
		int idx = 0;
		for (String member : members) {
			mlist[idx++] = SafeEncoder.encode(member);
		}
		gmmget(SafeEncoder.encode(key), mlist);
	}

	@Override
	public void gmrelation(String key, Polygon<?> polygon) {
		gmrelation(SafeEncoder.encode(key), polygon);
	}

	@Override
	public void gmrelation(String key, LineString<?> lineString) {
		gmrelation(SafeEncoder.encode(key), lineString);
	}

	@Override
	public void gmrelation(String key, Point<?> point) {
		gmrelation(SafeEncoder.encode(key), point);
	}

	@Override
	public void gmrelationByMember(String key, String byKey, String byMember) {
		gmrelationByMember(SafeEncoder.encode(key), SafeEncoder.encode(byKey), SafeEncoder.encode(byMember));
	}

	@Override
	public void gmnn(String key, double x, double y, long count) {
		gmnn(SafeEncoder.encode(key), x, y, count);
	}

	@Override
	public void gmnn(String key, double x, double y, long count, String vpattern) {
		gmnn(SafeEncoder.encode(key), x, y, count, SafeEncoder.encode(vpattern));
	}

	@Override
	public void gmnn(String key, double x, double y, long count, String mpattern, String vpattern) {
		gmnn(SafeEncoder.encode(key), x, y, count, SafeEncoder.encode(mpattern), SafeEncoder.encode(vpattern));
	}

	@Override
	public void ggexists(String key, String member) {
		ggexists(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public void gmexists(String key, String member) {
		gmexists(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

}
