package redis.clients.jedis;

import static redis.clients.jedis.Protocol.DEFAULT_TIMEOUT;
import static redis.clients.jedis.Protocol.UNITS.M;

import java.util.Iterator;
import java.util.List;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Circle;

public class Geodis extends Jedis implements GeoCommands {

	public Geodis(String host, int port, int timeout) {
		super(host, port, timeout);
	}

	public Geodis(String host, int port) {
		super(host, port, DEFAULT_TIMEOUT);
	}

	@Override
	public Long gadd(final String key, final double lat, final double lon, final String member, final String value) {
		return gfadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gadd(final byte[] key, final double lat, final double lon, final byte[] member, final byte[] value) {
		return gfadd(key, lat, lon, 0, M, member, value);
	}

	@Override
	public Long gfadd(final String key, final double lat, final double lon, final double distance, final UNITS unit, final String member,
			final String value) {
		checkIsInMulti();
		client.gfadd(key, lat, lon, distance, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gfadd(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit, final byte[] member,
			final byte[] value) {
		checkIsInMulti();
		client.gfadd(key, lat, lon, distance, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public List<String> gfrangeByRadius(final String key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return client.getMultiBulkReply();
	}

	@Override
	public List<byte[]> gfrangeByRadius(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadius(key, lat, lon, distance, unit);
		return client.getBinaryMultiBulkReply();
	}

	@Override
	public List<Circle> gfrangeByRadiusDetail(final String key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadiusDetail(key, lat, lon, distance, unit);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Circle> gfrangeByRadiusDetail(final byte[] key, final double lat, final double lon, final double distance, final UNITS unit) {
		checkIsInMulti();
		client.gfrangeByRadiusDetail(key, lat, lon, distance, unit);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public long gfcard(final String key) {
		checkIsInMulti();
		client.gfcard(key);
		return client.getIntegerReply();
	}

	@Override
	public long gfcard(final byte[] key) {
		checkIsInMulti();
		client.gfcard(key);
		return client.getIntegerReply();
	}

	@Override
	public long gfrem(final String key, final String member) {
		checkIsInMulti();
		client.gfrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public long gfrem(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gfrem(key, member);
		return client.getIntegerReply();
	}

	@Override
	public List<Circle> gfnn(final String key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gfnn(key, lat, lon, count);
		return client.getSpatialMultiBulkReply();
	}

	@Override
	public List<Circle> gfnn(final byte[] key, final double lat, final double lon, final long count) {
		checkIsInMulti();
		client.gfnn(key, lat, lon, count);
		return client.getBinarySpatialMultiBulkReply();
	}

	@Override
	public Circle gfget(final String key, final String member) {
		Iterator<Circle> circlesIterator = gfmget(key, member).iterator();
		if (circlesIterator.hasNext()) {
			return circlesIterator.next();
		} else {
			return null;
		}
	}

	@Override
	public Circle gfget(final byte[] key, final byte[] member) {
		Iterator<Circle> circlesIterator = gfmget(key, member).iterator();
		if (circlesIterator.hasNext()) {
			return circlesIterator.next();
		} else {
			return null;
		}
	}

	@Override
	public List<Circle> gfmget(final String key, final String... members) {
		checkIsInMulti();
		client.gfmget(key, members);
		return client.getSpatialMGETMultiBulkReply();
	}

	@Override
	public List<Circle> gfmget(final byte[] key, final byte[]... members) {
		checkIsInMulti();
		client.gfmget(key, members);
		return client.getBinarySpatialMGETMultiBulkReply();
	}

	//
	// private GCircle gmember(final byte[] key, final byte[] member) {
	// checkIsInMulti();
	// client.gmember(key, member);
	// final List<String> values = client.getMultiBulkReply();
	// int idx = 0;
	// GCircle gObj = null;
	// if (values != null && !values.isEmpty()) {
	// gObj = new GCircle();
	// gObj.setMember(new String(member));
	// gObj.setX(Double.valueOf(values.get(idx++).toString()));
	// gObj.setY(Double.valueOf(values.get(idx++).toString()));
	// gObj.setDistance(Double.valueOf(values.get(idx++).toString()));
	// idx++;
	// idx++;
	// gObj.setValue(values.get(idx++).toString());
	// }
	// return gObj;
	// }
	//
	// @Override
	// public Set<String> gradius(final String key, final double x, final double y, final double distance, final UNITS unit) {
	// return gradius(SafeEncoder.encode(key), x, y, distance, SafeEncoder.encode(unit.toValue()));
	// }
	//
	// @Override
	// public Set<String> gradius(final String key, final double x, final double y, final double distance) {
	// return gradius(key, x, y, distance, UNITS.M);
	// }
	//
	// @Override
	// public GCircle gmember(final String key, final String member) {
	// return gmember(SafeEncoder.encode(key), SafeEncoder.encode(member));
	// }
	//
	// @Override
	// public Set<GCircle> gsearchCircle(final String key, final double x, final double y, final double distance) {
	// Set<String> members = gradius(key, x, y, distance, UNITS.M);
	// Set<GCircle> result = new LinkedHashSet<GCircle>();
	// for (String member : members) {
	// GCircle circle = this.gmember(key, member);
	// if (circle != null) {
	// if (circle.getDistance() > 0) {
	// result.add(circle);
	// }
	// }
	// }
	// return result;
	// }
	//
	// @Override
	// public Set<GPoint> gsearchPoint(String key, double x, double y, double distance) {
	// Set<String> members = gradius(key, x, y, distance, UNITS.M);
	// Set<GPoint> result = new LinkedHashSet<GPoint>();
	// for (String member : members) {
	// GCircle circle = this.gmember(key, member);
	// if (circle != null) {
	// if (circle.getDistance() == 0) {
	// result.add(circle);
	// }
	// }
	// }
	// return result;
	// }

	// @Override
	// public Double distance(final double dLat1, final double dLon1, final double dLat2, final double dLon2) {
	// // d = |ax1 + by1 + c | / sqrt(a^2 + b^2)
	// return Math.acos(Math.sin(dLat1) * Math.sin(dLat2) + Math.cos(dLat1) * Math.cos(dLat2) * Math.cos(dLon1 - dLon2));
	// }

}
