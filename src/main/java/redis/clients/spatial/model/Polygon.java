package redis.clients.spatial.model;

import static redis.clients.jedis.Protocol.OPCl.CCL;
import static redis.clients.jedis.Protocol.OPCl.CL;
import static redis.clients.jedis.Protocol.OPCl.CO;
import static redis.clients.jedis.Protocol.OPCl.OP;

import java.util.Iterator;
import java.util.LinkedHashSet;

import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.util.SafeEncoder;

@ToString(doNotUseGetters = true)
public class Polygon implements Geometry {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5983153208342809216L;

	final LinkedHashSet<Point> points = new LinkedHashSet<Point>();

	StringBuffer sb = new StringBuffer("{\"type\": \"Polygon\", \"coordinates\":");

	private Type type = Type.POLYGON;

	public Polygon(Point... points) {
		for (Point p : points) {
			this.points.add(p);
		}
	}

	// {"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}
	public String getJsonStr() {
		Iterator<Point> iters = points.iterator();
		if (iters.hasNext()) {
			sb.append(OP.str).append(OP.str);
			for (int idx = 0; iters.hasNext(); idx++) {
				Point vp = iters.next();
				if(idx != 0){
					sb.append(CO.str);
				}
				sb.append(OP.str).append(vp.getX()).append(CO.str).append(vp.getY()).append(CL.str);
			}
			sb.append(CL.str).append(CL.str).append(CCL.str);
		}

		System.out.println(sb.toString());
		return sb.toString();

	}
	public byte[] getJsonbyte() {
		return SafeEncoder.encode(sb.toString());

	}

	@Override
	public Type getType() {
		return type;
	}

}

