package redis.clients.spatial.model;

import static redis.clients.jedis.Protocol.OPCl.CCL;
import static redis.clients.jedis.Protocol.OPCl.CL;
import static redis.clients.jedis.Protocol.OPCl.CO;
import static redis.clients.jedis.Protocol.OPCl.OP;
import static redis.clients.util.GEOMETRY.LINESTRING;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateList;

import lombok.Getter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.GEOMETRY;
import redis.clients.util.SafeEncoder;

@ToString(doNotUseGetters = true)
public class LineString<T> extends Geometry<T> implements Comparable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6291789145778774869L;

	@Getter
	final List<Point<?>> points = new ArrayList<Point<?>>();

	private Type type = Type.LINESTRING;

	@SuppressWarnings("unchecked")
	public LineString(Point<T>... points) {
		super();
		for (Point<T> p : points) {
			this.points.add(p);
		}
	}

	public LineString(double x, double y, double... xy) {
		super();
		this.points.add(new Point<T>(x, y));
		if (xy.length % 2 != 0) {
			throw new JedisException("parameter error. check it out.");
		}
		int idx = 0;
		while (idx < xy.length) {
			double _x = xy[idx++];
			double _y = xy[idx++];
			this.points.add(new Point<T>(_x, _y));
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
		Iterator<Point<?>> iters = points.iterator();
		if (iters.hasNext()) {
			sb.append(OP.str);
			for (int idx = 0; iters.hasNext(); idx++) {
				Point<?> vp = iters.next();
				if (idx != 0) {
					sb.append(CO.str);
				}
				sb.append(OP.str).append(vp.getY()).append(CO.str).append(vp.getX()).append(CL.str);
			}
			sb.append(CL.str).append(CCL.str);
		}
		return sb.toString();

	}

	public byte[] getJsonByte() {
		return SafeEncoder.encode(getJsonStr());
	}

	public CoordinateList getCoordinateList() {
		CoordinateList cl = new CoordinateList();
		for (Point<?> p : points) {
			cl.add(new Coordinate(p.getY(), p.getX()), true);
		}
		return cl;
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
	
	@SuppressWarnings("rawtypes")
	public com.vividsolutions.jts.geom.Geometry getGeometyOfJTS() {
		
		Coordinate[] coordinates = new Coordinate[points.size()];
		int idx = 0;
		for(Point p : points){
			coordinates[idx++] = new Coordinate(p.getY(), p.getX());
		}
		return gf.createLineString(coordinates);
	}
	
}
