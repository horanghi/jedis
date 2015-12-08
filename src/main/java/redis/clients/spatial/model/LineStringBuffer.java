package redis.clients.spatial.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import redis.clients.jedis.GeoUtils;
import redis.clients.jedis.Protocol.UNITS;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.operation.buffer.BufferOp;

public class LineStringBuffer {

	@Getter
	final LineString<?> linestring;
	final GeometryFactory fact;
	final com.vividsolutions.jts.geom.LineString linear;
	final com.vividsolutions.jts.geom.Geometry buffer;

	final double distance;
	final UNITS unit;

	public LineStringBuffer(final LineString<?> linestring, final double distance, UNITS unit) {
		this.linestring = linestring;
		this.distance = distance;
		this.unit = unit;

		fact = new GeometryFactory();
		Coordinate[] coords = linestring.getCoordinateList().toCoordinateArray();
		linear = new GeometryFactory().createLineString(coords);

		BufferOp bufOp = new BufferOp(linear);
		bufOp.setEndCapStyle(BufferOp.CAP_SQUARE);

		double _dist = GeoUtils.toMeter(unit, distance) * GeoUtils.TOMETER;
		buffer = bufOp.getResultGeometry(_dist);
	}

	public Polygon<String> getLinePolygon() {
		Coordinate[] clist = this.buffer.getCoordinates();
		List<Point<String>> ps = new ArrayList<Point<String>>();
		for (int idx = 0; idx < clist.length; idx++) {
			ps.add(new Point<String>(clist[idx].y, clist[idx].x));
		}
		Polygon<String> poly = new Polygon<String>(ps);
		return poly;
	}

	public com.vividsolutions.jts.geom.Geometry getGeometyOfJTS() {
		return linestring.getGeometyOfJTS();
	}
}
