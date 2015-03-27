package redis.clients.spatial.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.jedis.Protocol.UNITS;

@ToString(callSuper = true, doNotUseGetters = true)
public class Circle extends Point implements Geometry {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5821580185041603150L;

	@Setter
	@Getter
	private double radius;
	@Setter
	@Getter
	private double distance;
	@Setter
	@Getter
	private UNITS unit;

	private Type type = Type.CIRCLE;

	public Circle(String member, double x, double y, double radius, UNITS unit, String value, double distance) {
		super(member, x, y, value);
		this.radius = radius;
		this.distance = distance;
		this.unit = unit;
	}

	public Circle(byte[] member, double x, double y, double radius, UNITS unit, byte[] value, double distance) {
		super(member, x, y, value);
		this.radius = radius;
		this.distance = distance;
		this.unit = unit;
	}

	@Override
	public Type getType() {
		return type;
	}

}
