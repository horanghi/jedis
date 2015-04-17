package redis.clients.spatial.model;

import static redis.clients.jedis.Protocol.OPCl.CCL;
import static redis.clients.jedis.Protocol.OPCl.CL;
import static redis.clients.jedis.Protocol.OPCl.OP;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.util.SafeEncoder;

@ToString(doNotUseGetters = true)
public class Point<T> extends Geometry<T> {
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


	private Type type = Type.POINT;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Point(T member, double x, double y, T value) {
		super(member, value);
		this.x = x;
		this.y = y;
	}

	public Point(T member, double x, double y, T value, double distance) {
		super(member, value);
		this.x = x;
		this.y = y;
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

	// {"type": "Point", "coordinates": [1,1]}
	public String getJsonStr() {
		StringBuffer sb = new StringBuffer("{\"type\": \"Point\", \"coordinates\": ");
		sb.append(OP.str).append(this.x).append(",").append(this.y).append(CL.str).append(CCL.str);
		return sb.toString();
	}

	public byte[] getJsonbyte() {
		return SafeEncoder.encode(getJsonStr());
	}

}
