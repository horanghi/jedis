package redis.clients.spatial.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;

@ToString(doNotUseGetters = true)
public class Point<T> implements Geometry {
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
	private double distance;
	@Setter
	@Getter
	private T member;
	@Setter
	@Getter
	private T value;

	private Type type = Type.POINT;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(T member, double x, double y, T value) {
		this.x = x;
		this.y = y;
		this.member = member;
		this.value = value;
	}

	public Point(T member, double x, double y, T value, double distance) {
		this.x = x;
		this.y = y;
		this.member = member;
		this.value = value;
		this.distance = distance;
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Point)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Point<T> other = (Point<T>) o;
		if (!other.canEqual((Object) this)) {
			return false;
		}
		if (!this.type.equals(other.type)) {
			return false;
		}
		if (this.getMember() == null) {
			return false;
		}
		if (this.getMember() instanceof String) {
			if (!this.getMember().equals(other.getMember())) {
				return false;
			}
		} else {
			if (!Arrays.equals(((byte[]) this.getMember()), ((byte[]) other.getMember()))) {
				return false;
			}
		}
		if (Double.compare(this.x, other.x) != 0) {
			return false;
		}
		if (Double.compare(this.y, other.y) != 0) {
			return false;
		}
		if (Double.compare(this.distance, other.distance) != 0) {
			return false;
		}
		return true;
	}

	protected boolean canEqual(Object other) {
		return other instanceof Point;
	}

}
