package redis.clients.spatial.model;

import static redis.clients.jedis.Protocol.OPCl.CCL;
import static redis.clients.jedis.Protocol.OPCl.CL;
import static redis.clients.jedis.Protocol.OPCl.OP;
import static redis.clients.util.GEOMETRY.POINT;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.util.SafeEncoder;

@ToString(doNotUseGetters = true, callSuper = true)
public class Point<T> extends Geometry<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1978761421041959442L;

	@Getter
	final private double x;
	@Getter
	final private double y;
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

	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Point)) {
			return false;
		}
		Point<T> other = (Point<T>) o;
		
		if (Double.compare(this.x, other.x) != 0) {
			return false;
		}
		if (Double.compare(this.y, other.y) != 0) {
			return false;
		}
		return true;
	}

	protected boolean canEqual(Object other) {
		return other instanceof Point;
	}

	// {"type": "Point", "coordinates": [1,1]}
	public String getJsonStr() {
		StringBuffer sb = new StringBuffer(POINT.toString());
		sb.append(OP.str).append(this.x).append(",").append(this.y).append(CL.str).append(CCL.str);
		return sb.toString();
	}

	public byte[] getJsonByte() {
		return SafeEncoder.encode(getJsonStr());
	}

}
