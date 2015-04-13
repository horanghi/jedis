package redis.clients.spatial.model;

import static redis.clients.jedis.Protocol.OPCl.CCL;
import static redis.clients.jedis.Protocol.OPCl.CL;
import static redis.clients.jedis.Protocol.OPCl.CO;
import static redis.clients.jedis.Protocol.OPCl.OP;

import java.util.Iterator;
import java.util.LinkedHashSet;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;
import redis.clients.util.SafeEncoder;

@EqualsAndHashCode
@ToString(doNotUseGetters = true)
public class Polygon implements Geometry {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5983153208342809216L;

	final LinkedHashSet<Point<String>> points = new LinkedHashSet<Point<String>>();

	StringBuffer sb = new StringBuffer("{\"type\": \"Polygon\", \"coordinates\":");

	private Type type = Type.POLYGON;

	@SuppressWarnings("unchecked")
	public Polygon(Point<String>... points) {
		for (Point<String> p : points) {
			this.points.add(p);
		}
	}

	// {"type": "Polygon", "coordinates": [[[1,1], [1,-1], [-1,-1], [-1,1], [1,1]]]}
	public String getJsonStr() {
		Iterator<Point<String>> iters = points.iterator();
		if (iters.hasNext()) {
			sb.append(OP.str).append(OP.str);
			for (int idx = 0; iters.hasNext(); idx++) {
				Point<String> vp = iters.next();
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

