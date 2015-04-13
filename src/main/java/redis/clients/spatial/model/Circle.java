package redis.clients.spatial.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.jedis.Protocol.UNITS;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, doNotUseGetters = true)
public class Circle<T> extends Point<T> implements Geometry {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5821580185041603150L;

	@Setter
	@Getter
	private double radius;
	@Setter
	@Getter
	private UNITS unit;

	private Type type = Type.CIRCLE;

	public Circle(T member, double x, double y, double radius, UNITS unit, T value) {
		super(member, x, y, value);
		this.radius = radius;
		this.unit = unit;
	}

	public Circle(T member, double x, double y, double radius, UNITS unit, T value, double distance) {
		super(member, x, y, value, distance);
		this.radius = radius;
		this.unit = unit;
	}

	@Override
	public Type getType() {
		return type;
	}

}
