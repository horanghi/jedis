package redis.clients.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public enum GEOMETRY {
	POINT("{\"type\":\"Point\",\"coordinates\":") {
		public Geometry<String> getGGeometry(final String typeJson) {
			// 삽질..성능 우선. Jackson 나중에..
			// [[1.00000000000000,1.00000000000000]]}
			String xys = typeJson.substring(this.toString().length() - 1);
			String[] xylist = StringUtils.split(xys, " ,[]{}:");
			return new Point<String>(Double.valueOf(xylist[1]), Double.valueOf(xylist[0]));
		}

		public Geometry<byte[]> getGBGeometry(final String typeJson) {
			// 삽질..성능 우선. Jackson 나중에..
			// [[1.00000000000000,1.00000000000000]]}
			String xys = typeJson.substring(this.toString().length() - 1);
			String[] xylist = StringUtils.split(xys, " [,]{}:");
			return new Point<byte[]>(Double.valueOf(xylist[1]), Double.valueOf(xylist[0]));
		}
	},

	POLYGON("{\"type\":\"Polygon\",\"coordinates\":") {
		public Geometry<String> getGGeometry(final String typeJson) {
			// 삽질..성능 우선. Jackson 나중에..
			// [[[1.00000000000000,1.00000000000000],[1.00000000000000,-1.00000000000000]]]}
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}:");
			List<Point<String>> resultList = new ArrayList<Point<String>>();
			for (int idx = 0; idx < xylist.length;) {
				if (xylist[idx] == null) {
					break;
				}
				double y = Double.valueOf(xylist[idx++]);
				double x = Double.valueOf(xylist[idx++]);
				Point<String> point = new Point<String>(x, y);
				resultList.add(point);
			}
			return new Polygon<String>(resultList);
		}

		public Geometry<byte[]> getGBGeometry(final String typeJson) {
			// 삽질..성능 우선. Jackson 고려할까 ? 말까 ? Object로 하는 부분에 대해서....
			// [[[1.00000000000000,1.00000000000000],[1.00000000000000,-1.00000000000000]]]}
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}:");
			List<Point<byte[]>> resultList = new ArrayList<Point<byte[]>>();
			for (int idx = 0; idx < xylist.length;) {
				if (xylist[idx] == null) {
					break;
				}
				double y = Double.valueOf(xylist[idx++]);
				double x = Double.valueOf(xylist[idx++]);
				Point<byte[]> point = new Point<byte[]>(x, y);
				resultList.add(point);
			}
			return new Polygon<byte[]>(resultList);
		}
	},
	LINESTRING("{\"type\":\"LineString\",\"coordinates\":") {
		public Geometry<String> getGGeometry(final String typeJson) {
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}:");
			List<Point<String>> resultList = new ArrayList<Point<String>>();
			for (int idx = 0; idx < xylist.length;) {
				if (xylist[idx] == null) {
					break;
				}
				double y = Double.valueOf(xylist[idx++]);
				double x = Double.valueOf(xylist[idx++]);
				Point<String> point = new Point<String>(x, y);
				resultList.add(point);
			}
			return new LineString<String>(resultList);
		}

		public Geometry<byte[]> getGBGeometry(final String typeJson) {
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}:");
			List<Point<byte[]>> resultList = new ArrayList<Point<byte[]>>();
			for (int idx = 0; idx < xylist.length;) {
				if (xylist[idx] == null) {
					break;
				}
				double y = Double.valueOf(xylist[idx++]);
				double x = Double.valueOf(xylist[idx++]);
				Point<byte[]> point = new Point<byte[]>(x, y);
				resultList.add(point);
			}
			return new LineString<byte[]>(resultList);
		}
	};

	final private String value;

	GEOMETRY(String value) {
		this.value = value;
	}

	public static Geometry<String> getGeometry(final String typeJson) {
		if (StringUtils.contains(typeJson.toUpperCase(), POLYGON.name())) {
			return POLYGON.getGGeometry(typeJson);
		} else if (StringUtils.contains(typeJson.toUpperCase(), LINESTRING.name())) {
			return LINESTRING.getGGeometry(typeJson);
		} else if (StringUtils.contains(typeJson.toUpperCase(), POINT.name())) {
			return POINT.getGGeometry(typeJson);
		} else {
			throw new RuntimeException("not support type.");
		}
	}

	public static Geometry<byte[]> getBGeometry(final byte[] typeJsonb) {
		String typeJson = SafeEncoder.encode(typeJsonb);
		if (StringUtils.contains(typeJson.toUpperCase(), POLYGON.name())) {
			return POLYGON.getGBGeometry(typeJson);
		} else if (StringUtils.contains(typeJson.toUpperCase(), LINESTRING.name())) {
			return LINESTRING.getGBGeometry(typeJson);
		} else if (StringUtils.contains(typeJson.toUpperCase(), POINT.name())) {
			return POINT.getGBGeometry(typeJson);
		} else {
			throw new RuntimeException("not support type.");
		}
	}

	public abstract Geometry<String> getGGeometry(String typeJson);

	public abstract Geometry<byte[]> getGBGeometry(String typeJson);

	public static boolean equals(List<Point<?>> point1, List<Point<?>> point2) {
		if (point1 == null || point2 == null) {
			return false;
		}
		if (point1.size() != point2.size()) {
			return false;
		}
		if (point1.size() + point2.size() == 0) {
			return false;
		}

		boolean match = false;
		for (Object ps : point1) {
			match = false;
			for (Object pb : point2) {
				if (((Point<?>) pb).equals((Point<?>) ps)) {
					match = true;
					break;
				}
			}
			if (!match) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		return this.value;
	}

}

