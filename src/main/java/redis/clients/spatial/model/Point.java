package redis.clients.spatial.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.util.SafeEncoder;

@EqualsAndHashCode
@ToString(doNotUseGetters = true)
public class Point implements Geometry {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1978761421041959442L;

	@Setter
	@Getter
	private double x;
	@Setter
	@Getter
	private double y;
	@Setter
	@Getter
	private String member;
	@Setter
	@Getter
	private String value;

	private Type type = Type.POINT;

	Point(double lat, double lon) {
		this.x = lat;
		this.y = lon;
	}

	Point(String member, double lat, double lon, String value) {
		this.x = lat;
		this.y = lon;
		this.member = member;
		this.value = value;
	}

	Point(byte[] member, double lat, double lon, byte[] value) {
		this.x = lat;
		this.y = lon;
		this.member = SafeEncoder.encode(member);
		this.value = SafeEncoder.encode(value);
	}

	@Override
	public Type getType() {
		return this.type;
	}

}
