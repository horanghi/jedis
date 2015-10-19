package redis.clients.spatial.model;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.GeoUtils;
import redis.clients.jedis.Protocol.UNITS;

public class LineStringRange {

	final LineString<?> linestring;
	final double distance;
	final UNITS unit;

	public LineStringRange(final LineString<?> linestring, final double distance, UNITS unit) {
		this.linestring = linestring;
		this.distance = distance;
		this.unit = unit;
	}

	@SuppressWarnings("rawtypes")
	public List<Geometry> geRangeGeometry() {
		List<Geometry> result = new ArrayList<Geometry>();
		result.addAll(this.getRangeCircles());
		result.addAll(this.getRangeRectangles());
		return result;
	}

	/*
	 * 1. Point 별 Radius 반경 리스트 산
	 * 2. 2 point별 Rectangle 직사각형을 구한다. 
	 */

	@SuppressWarnings("rawtypes")
	public List<Circle<?>> getRangeCircles() {
		List<Circle<?>> circles = new ArrayList<Circle<?>>();
		List<Point<?>> points = linestring.getPoints();
		for (int idx = 0; idx < points.size(); idx++) {
			circles.add(new Circle(points.get(idx).getX(), points.get(idx).getY(), this.distance, this.unit));
		}
		return circles;
	}

	public List<Polygon<?>> getRangeRectangles() {
		List<Polygon<?>> polygons = new ArrayList<Polygon<?>>();

		List<Point<?>> points = linestring.getPoints();
		double _dist = GeoUtils.toMeter(this.unit, this.distance);

		for (int idx = 0; idx < points.size() - 1; idx++) {
			int nextp = idx;
			// 4개점 구하기
			polygons.add(getRectangle(points.get(nextp).getX(), points.get(nextp++).getY(), points.get(nextp).getX(), points.get(nextp)
					.getY(), _dist));
		}
		return polygons;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Polygon<String> getRectangle(double x1, double y1, double x2, double y2, double radius) {
		// 두 좌표의 거리를 구한다.
		double tDis = distance(x1, y1, x2, y2);

		// 0,0 좌표 이동
		double a = x2 - x1;
		double b = y2 - y1;

		// 첫번째 좌표 구하기
		double _x = (-1 * (b * radius) / tDis);
		double _y = ((a * radius) / tDis);

		// 좌표 보정
		Point xy01 = new Point(_x + x1, _y + y1);
		Point xy02 = new Point(-1 * _x + x1, -1 * _y + y1);
		Point xy03 = new Point(a - _x + x1, b - _y + y1);
		Point xy04 = new Point(a + _x + x1, b + _y + y1);

		return new Polygon<String>(xy01, xy02, xy03, xy04, xy01);
	}

	private double distance(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = GeoUtils.EarthRadius;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);
		return dist;
	}

}
