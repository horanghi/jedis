package redis.clients.spatial.model;

import static redis.clients.jedis.Protocol.OPCl.CCL;
import static redis.clients.jedis.Protocol.OPCl.CL;
import static redis.clients.jedis.Protocol.OPCl.CO;
import static redis.clients.jedis.Protocol.OPCl.OP;
import static redis.clients.util.GEOMETRY.POLYGON;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.util.GEOMETRY;
import redis.clients.util.SafeEncoder;

@ToString(doNotUseGetters = true)
public class Polygon<T> extends Geometry<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5983153208342809216L;

	@Getter
	final LinkedHashSet<Point<T>> points = new LinkedHashSet<Point<T>>();

	final private Type type = Type.POLYGON;

	@SuppressWarnings("unchecked")
	public Polygon(Point<T>... points) {
		super();
		for (Point<T> p : points) {
			this.points.add(p);
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
				if(vp == null){
					break;
				}
				if (idx != 0) {
					sb.append(CO.str);
				}
				sb.append(OP.str).append(vp.getX()).append(CO.str).append(vp.getY()).append(CL.str);
			}
			sb.append(CL.str).append(CL.str).append(CCL.str);
		}

		System.out.println(sb.toString());
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

		if(!GEOMETRY.equals((Set) this.points, (Set)((Polygon<T>) o).getPoints())){
			return false;
		}

		return true;
	}
}
