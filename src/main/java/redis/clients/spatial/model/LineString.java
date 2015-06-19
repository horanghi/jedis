package redis.clients.spatial.model;

import static redis.clients.jedis.Protocol.OPCl.CCL;
import static redis.clients.jedis.Protocol.OPCl.CL;
import static redis.clients.jedis.Protocol.OPCl.CO;
import static redis.clients.jedis.Protocol.OPCl.OP;
import static redis.clients.util.GEOMETRY.LINESTRING;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.util.GEOMETRY;
import redis.clients.util.SafeEncoder;

@ToString(doNotUseGetters = true)
public class LineString<T> extends Geometry<T> implements Comparable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6291789145778774869L;

	@Getter
	final List<Point<T>> points = new ArrayList<Point<T>>();

	private Type type = Type.LINESTRING;

	@SuppressWarnings("unchecked")
	public LineString(Point<T>... points) {
		super();
		for (Point<T> p : points) {
			this.points.add(p);
		}
	}

	public LineString(List<Point<T>> points) {
		super();
		for (Point<T> p : points) {
			this.points.add(p);
		}
	}

	public LineString(T member, T value, List<Point<T>> points) {
		super(member, value);
		for (Point<T> p : points) {
			this.points.add(p);
		}
	}

	@SuppressWarnings("unchecked")
	public LineString(T member, T value, Point<T>... points) {
		super(member, value);
		for (Point<T> p : points) {
			this.points.add(p);
		}
	}

	// {"type": "LineString", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}
	public String getJsonStr() {
		StringBuffer sb = new StringBuffer(LINESTRING.toString());
		Iterator<Point<T>> iters = points.iterator();
		if (iters.hasNext()) {
			sb.append(OP.str);
			for (int idx = 0; iters.hasNext(); idx++) {
				Point<T> vp = iters.next();
				if (idx != 0) {
					sb.append(CO.str);
				}
				sb.append(OP.str).append(vp.getX()).append(CO.str).append(vp.getY()).append(CL.str);
			}
			sb.append(CL.str).append(CCL.str);
		}
		return sb.toString();

	}

	public byte[] getJsonByte() {
		return SafeEncoder.encode(getJsonStr());
	}

	@Override
	public Type getType() {
		return type;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof LineString)) {
			return false;
		}
		if (!GEOMETRY.equals((List) this.points, (List) ((LineString<T>) o).getPoints())) {
			return false;
		}
		return true;
	}

	public boolean equalsDeep(Point<T> o) {
		if (this.equals((Object) o)) {
			if (super.equalsDeep(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(T o) {
		if (this.equals((Object) o)) {
			return 0;
		}
		return -1;
	}
}