package redis.clients.util;

import org.apache.commons.lang3.StringUtils;

import redis.clients.spatial.model.Geometry;
import redis.clients.spatial.model.LineString;
import redis.clients.spatial.model.Point;
import redis.clients.spatial.model.Polygon;

public enum GEOMETRY {
	POINT("{\"type\": \"Point\", \"coordinates\":") {
		public Geometry<String> getGGeometry(final String typeJson) {
			// 삽질..성능 우선. Jackson 나중에..
			// [[1.00000000000000,1.00000000000000]]}
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}");
			return new Point<String>(Double.valueOf(xylist[0]), Double.valueOf(xylist[1]));
		}

		public Geometry<byte[]> getGBGeometry(final String typeJson) {
			// 삽질..성능 우선. Jackson 나중에..
			// [[1.00000000000000,1.00000000000000]]}
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}");
			return new Point<byte[]>(Double.valueOf(xylist[0]), Double.valueOf(xylist[1]));
		}
	},

	POLYGON("{\"type\": \"Polygon\", \"coordinates\":") {
		public Geometry<String> getGGeometry(final String typeJson) {
			// 삽질..성능 우선. Jackson 나중에..
			// [[[1.00000000000000,1.00000000000000],[1.00000000000000,-1.00000000000000]]]}
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}");
			@SuppressWarnings("unchecked")
			Point<String>[] pointArray = new Point[xylist.length / 2];
			int iin = 0;
			for (int idx = 0; xylist[idx] == null;) {
				Point<String> point = new Point<String>(Double.valueOf(xylist[idx++]), Double.valueOf(xylist[idx++]));
				pointArray[iin++] = point;
			}
			return new Polygon<String>(pointArray);
		}

		public Geometry<byte[]> getGBGeometry(final String typeJson) {
			// 삽질..성능 우선. Jackson 나중에..
			// [[[1.00000000000000,1.00000000000000],[1.00000000000000,-1.00000000000000]]]}
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}");
			@SuppressWarnings("unchecked")
			Point<byte[]>[] pointArray = new Point[xylist.length / 2];
			int iin = 0;
			for (int idx = 0; xylist[idx] == null;) {
				Point<byte[]> point = new Point<byte[]>(Double.valueOf(xylist[idx++]), Double.valueOf(xylist[idx++]));
				pointArray[iin++] = point;
			}
			return new Polygon<byte[]>(pointArray);
		}
	},
	LINESTRING("{\"type\": \"Linestring\", \"coordinates\":") {
		public Geometry<String> getGGeometry(final String typeJson) {
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}");
			@SuppressWarnings("unchecked")
			Point<String>[] pointArray = new Point[xylist.length / 2];
			int iin = 0;
			for (int idx = 0; xylist[idx] == null;) {
				Point<String> point = new Point<String>(Double.valueOf(xylist[idx++]), Double.valueOf(xylist[idx++]));
				pointArray[iin++] = point;
			}
			return new LineString<String>(pointArray);
		}

		public Geometry<byte[]> getGBGeometry(final String typeJson) {
			String xys = typeJson.substring(this.toString().length());
			String[] xylist = StringUtils.split(xys, " [,]{}");
			@SuppressWarnings("unchecked")
			Point<byte[]>[] pointArray = new Point[xylist.length / 2];
			int iin = 0;
			for (int idx = 0; xylist[idx] == null;) {
				Point<byte[]> point = new Point<byte[]>(Double.valueOf(xylist[idx++]), Double.valueOf(xylist[idx++]));
				pointArray[iin++] = point;
			}
			return new LineString<byte[]>(pointArray);
		}
	};

	final private String value;

	GEOMETRY(String value) {
		this.value = value;
	}

	public static Geometry<String> getGeometry(final String typeJson) {
		if (typeJson.startsWith(POLYGON.toString())) {
			return POLYGON.getGGeometry(typeJson);
		} else if (typeJson.startsWith(LINESTRING.toString())) {
			return LINESTRING.getGGeometry(typeJson);
		} else if (typeJson.startsWith(POINT.toString())) {
			return POINT.getGGeometry(typeJson);
		}
		return null;
	}

	public static Geometry<byte[]> getBGeometry(final byte[] typeJsonb) {
		String typeJson = SafeEncoder.encode(typeJsonb);
		if (typeJson.startsWith(POLYGON.toString())) {
			return POLYGON.getGBGeometry(typeJson);
		} else if (typeJson.startsWith(LINESTRING.toString())) {
			return LINESTRING.getGBGeometry(typeJson);
		} else if (typeJson.startsWith(POINT.toString())) {
			return POINT.getGBGeometry(typeJson);
		}
		return null;
	}

	public abstract Geometry<String> getGGeometry(String typeJson);

	public abstract Geometry<byte[]> getGBGeometry(String typeJson);

	public String toString() {
		return this.value;
	}

}
