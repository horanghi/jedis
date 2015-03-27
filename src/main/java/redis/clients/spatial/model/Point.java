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

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(String member, double x, double y, String value) {
		this.x = x;
		this.y = y;
		this.member = member;
		this.value = value;
	}

	public Point(byte[] member, double x, double y, byte[] value) {
		this.x = x;
		this.y = y;
		this.member = SafeEncoder.encode(member);
		this.value = SafeEncoder.encode(value);
	}

	@Override
	public Type getType() {
		return this.type;
	}

}
