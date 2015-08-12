package redis.clients.spatial.model;

import static redis.clients.jedis.Protocol.OPCl.CCL;
import static redis.clients.jedis.Protocol.OPCl.CL;
import static redis.clients.jedis.Protocol.OPCl.CO;
import static redis.clients.jedis.Protocol.OPCl.OP;
import static redis.clients.util.GEOMETRY.POLYGON;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.SafeEncoder;

@ToString(doNotUseGetters = true)
public class Polygon<T> extends Geometry<T> implements Comparable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5983153208342809216L;

	@Getter
	final List<Point<T>> points = new ArrayList<Point<T>>();

	final private Type type = Type.POLYGON;

	@SuppressWarnings("unchecked")
	public Polygon(Point<T>... points) {
		super();
		for (Point<T> p : points) {
			this.points.add(p);
		}
	}
	
	public Polygon(double x, double y, double... xy) {
		super();
		this.points.add(new Point<T>(x, y));
		if ((xy.length % 2 != 0) && xy.length > 2) {
			throw new JedisException("parameter error. check it out.");
		}
		int idx = 0;
		while (idx < xy.length) {
			double _x = xy[idx++];
			double _y = xy[idx++];
			this.points.add(new Point<T>(_x, _y));
		}
	}

	public Polygon(List<Point<T>> points) {
		super();
		for (Point<T> p : points) {
			this.points.add(p);
		}
	}

	@SuppressWarnings("unchecked")
	public Polygon(final T member, final T value, Point<T>... points) {
		super(member, value);
		for (Point<T> p : points) {
			this.points.add(p);
		}
	}

	public Polygon(final T member, final T value, List<Point<T>> points) {
		super(member, value);
		for (Point<T> p : points) {
			this.points.add(p);
		}
	}

	// {"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}
	public String getJsonStr() {
		StringBuffer sb = new StringBuffer(POLYGON.toString());
		Iterator<Point<T>> iters = points.iterator();
		if (iters.hasNext()) {
			sb.append(OP.str).append(OP.str);
			for (int idx = 0; iters.hasNext(); idx++) {
				Point<T> vp = iters.next();
				if (vp == null) {
					break;
				}
				if (idx != 0) {
					sb.append(CO.str);
				}
				sb.append(OP.str).append(vp.getY()).append(CO.str).append(vp.getX()).append(CL.str);
			}
			sb.append(CL.str).append(CL.str).append(CCL.str);
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
		if (!(o instanceof Polygon)) {
			return false;
		}

		if (this.points.size() == 0) {
			return false;
		}
		List<Point<T>> p2 = (List) ((Polygon<T>) o).getPoints();
		if (!this.points.containsAll(p2)) {
			return false;
		}
		
		if (!p2.containsAll(this.points)) {
			return false;
		}

		return true;
	}

	public boolean equalsDeep(Polygon<T> o) {
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
