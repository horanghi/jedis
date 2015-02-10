package redis.clients.jedis;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import redis.clients.util.SafeEncoder;

public class Geodis extends Jedis implements GeoCommands {

	public Geodis(String host, int port, int timeout) {
		super(host, port, timeout);
	}

	public Geodis(String host, int port) {
		super(host, port, Protocol.DEFAULT_TIMEOUT);
	}

	private Long gadd(final byte[] key, final double x, final double y, final double distance, final byte[] unitb, final byte[] member,
			final byte[] value) {
		checkIsInMulti();
		client.gadd(key, x, y, distance, unitb, member, value);
		return client.getIntegerReply();
	}

	public Long gadd(final String key, final double x, final double y, final double distance, final Unit unit, final String member,
			final String value) {
		checkIsInMulti();
		client.gadd(key, x, y, distance, unit, member, value);
		return client.getIntegerReply();
	}

	@Override
	public Long gadd(final String key, final double x, final double y, final double distance, final UNITS unit, final String member,
			final String value) {
		return gadd(SafeEncoder.encode(key), x, y, distance, SafeEncoder.encode(unit.toValue()), SafeEncoder.encode(member),
				SafeEncoder.encode(value));
	}

	@Override
	public Long gadd(final String key, final double x, final double y, final double distance, final String member, final String value) {
		return gadd(key, x, y, distance, UNITS.M, member, value);
	}

	@Override
	public Long gaddPoint(final String key, final double x, final double y, final String member, final String value) {
		return gadd(key, x, y, 0, UNITS.M, member, value);
	}

	@Override
	public Long gaddCircle(final String key, final double x, final double y, final double distance, final String member, final String value) {
		return gadd(key, x, y, distance, UNITS.M, member, value);
	}

	private Set<String> gradius(final byte[] key, final double x, final double y, final double distance, final byte[] unitb) {
		checkIsInMulti();
		client.gradius(key, x, y, distance, unitb);
		final List<String> _members = client.getMultiBulkReply();
		return new LinkedHashSet<String>(_members);
	}

	private GCircle gmember(final byte[] key, final byte[] member) {
		checkIsInMulti();
		client.gmember(key, member);
		final List<String> values = client.getMultiBulkReply();
		int idx = 0;
		GCircle gObj = null;
		if (values != null && !values.isEmpty()) {
			gObj = new GCircle();
			gObj.setMember(new String(member));
			gObj.setX(Double.valueOf(values.get(idx++).toString()));
			gObj.setY(Double.valueOf(values.get(idx++).toString()));
			gObj.setDistance(Double.valueOf(values.get(idx++).toString()));
			idx++;
			idx++;
			gObj.setValue(values.get(idx++).toString());
		}
		return gObj;
	}

	@Override
	public Set<String> gradius(final String key, final double x, final double y, final double distance, final UNITS unit) {
		return gradius(SafeEncoder.encode(key), x, y, distance, SafeEncoder.encode(unit.toValue()));
	}

	@Override
	public Set<String> gradius(final String key, final double x, final double y, final double distance) {
		return gradius(key, x, y, distance, UNITS.M);
	}

	@Override
	public GCircle gmember(final String key, final String member) {
		return gmember(SafeEncoder.encode(key), SafeEncoder.encode(member));
	}

	@Override
	public Set<GCircle> gsearchCircle(final String key, final double x, final double y, final double distance) {
		Set<String> members = gradius(key, x, y, distance, UNITS.M);
		Set<GCircle> result = new LinkedHashSet<GCircle>();
		for (String member : members) {
			GCircle circle = this.gmember(key, member);
			if (circle != null) {
				if (circle.getDistance() > 0) {
					result.add(circle);
				}
			}
		}
		return result;
	}

	@Override
	public Set<GPoint> gsearchPoint(String key, double x, double y, double distance) {
		Set<String> members = gradius(key, x, y, distance, UNITS.M);
		Set<GPoint> result = new LinkedHashSet<GPoint>();
		for (String member : members) {
			GCircle circle = this.gmember(key, member);
			if (circle != null) {
				if (circle.getDistance() == 0) {
					result.add(circle);
				}
			}
		}
		return result;
	}

	@Override
	public Double distance(final double dLat1, final double dLon1, final double dLat2, final double dLon2) {
		// d = |ax1 + by1 + c | / sqrt(a^2 + b^2)
		return Math.acos(Math.sin(dLat1) * Math.sin(dLat2) + Math.cos(dLat1) * Math.cos(dLat2) * Math.cos(dLon1 - dLon2));
	}

}
