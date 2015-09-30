package redis.clients.util;

import redis.clients.spatial.model.Point;

public class Covert2Coordinates {
	public static final double BASE_TM_LON = 127.0D;
	public static final double BASE_TM_LAT = 38.0D;
	public static final double BASE_KTM_LON = 128.0D;
	public static final double BASE_KTM_LAT = 38.0D;
	public static final double BASE_UTM_LON = 129.0D;
	public static final double BASE_UTM_LAT = 0.0D;
	private static final int[][] COORD_BASE = { new int[2], { 127, 38 }, { -1, -1 }, { 129 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
			{ 127, 38 }, { -1, -1 }, new int[2], { -1, -1 } };

	@SuppressWarnings("rawtypes")
	public static Point transCoord(final Point point, COORD fromType, COORD toType) {
		CoordinatesPoint inPoint = new CoordinatesPoint(point);
		Point resultPoint = point.clone();
		CoordinatesPoint toPoint = convertCoord(inPoint, fromType.code, toType.code, COORD_BASE[fromType.code][0],
				COORD_BASE[fromType.code][1], COORD_BASE[toType.code][0], COORD_BASE[toType.code][1]);
		resultPoint.setX(toPoint.x);
		resultPoint.setY(toPoint.y);
		return resultPoint;
	}

	private static CoordinatesPoint convertCoord(CoordinatesPoint point, int fromType, int toType, double frombx, double fromby,
			double tobx, double toby) {
		CoordinatesPoint transPt = null;
		double bx = frombx;
		switch (fromType) {
		case 1:
			if (frombx <= 0.0D) {
				bx = 127.0D;
				fromby = 38.0D;
			}
			transPt = convertTM2(point, toType, bx, fromby, tobx, toby);
			break;
		case 2:
			if (frombx <= 0.0D) {
				bx = 128.0D;
				fromby = 38.0D;
			}
			transPt = convertKTM2(point, toType, tobx, toby);
			break;
		case 3:
			if (frombx <= 0.0D) {
				bx = 129.0D;
				fromby = 0.0D;
			}
			transPt = convertUTM2(point, toType, bx, fromby, tobx, toby);
			break;
		case 4:
			if (frombx <= 0.0D) {
				bx = 127.0D;
				fromby = 38.0D;
			}
			transPt = convertCONGNAMUL2(point, toType, bx, fromby, tobx, toby);
			break;
		case 5:
			transPt = convertWGS2(point, toType, bx, fromby, tobx, toby);
			break;
		case 6:
			transPt = convertBESSEL2(point, toType, bx, fromby, tobx, toby);
			break;
		case 7:
			if (frombx <= 0.0D) {
				bx = 127.0D;
				fromby = 38.0D;
			}
			transPt = convertWTM2(point, toType, bx, fromby, tobx, toby);
			break;
		case 8:
			if (frombx <= 0.0D) {
				bx = 128.0D;
				fromby = 38.0D;
			}
			transPt = convertWKTM2(point, toType, bx, frombx, tobx, toby);
			break;
		case 10:
			if (frombx <= 0.0D) {
				bx = 127.0D;
				fromby = 38.0D;
			}
			transPt = convertWCONGNAMUL2(point, toType, bx, fromby, tobx, toby);
		}
		return transPt;
	}

	private static CoordinatesPoint convertTM2(CoordinatesPoint point, int toType, double frombx, double fromby, double tobx, double toby) {
		CoordinatesPoint transPt = point.clone();
		switch (toType) {
		case 1:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case 2:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2KTM();
			break;
		case 3:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case 4:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2CONG();
			break;
		case 5:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			break;
		case 6:
			transPt.convertTM2BESSEL(frombx, fromby);
			break;
		case 7:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case 8:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WKTM();
			break;
		case 10:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WCONG();
		}
		return transPt;
	}

	private static CoordinatesPoint convertKTM2(CoordinatesPoint point, int toType, double tobx, double toby) {
		CoordinatesPoint transPt = point.clone();
		switch (toType) {
		case 1:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case 2:
			break;
		case 3:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case 4:
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case 5:
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case 6:
			transPt.convertKTM2BESSEL();
			break;
		case 7:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case 8:
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WKTM();
			break;
		case 10:
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WCONG();
		}
		return transPt;
	}

	private static CoordinatesPoint convertUTM2(CoordinatesPoint point, int d, double e, double h, double g, double j) {
		CoordinatesPoint transPt = point.clone();
		switch (d) {
		case 1:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertUTM2WGS(e, h);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(g, j);
			break;
		case 2:
			transPt.convertUTM2WGS(e, h);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case 3:
			if (g <= 0.0D) {
				g = 129.0D;
				j = 0.0D;
			}
			transPt.convertUTM2WGS(e, h);
			transPt.convertWGS2UTM(g, j);
			break;
		case 4:
			transPt.convertUTM2WGS(e, h);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case 5:
			transPt.convertUTM2WGS(e, h);
			break;
		case 6:
			transPt.convertUTM2WGS(e, h);
			transPt.convertWGS2BESSEL();
			break;
		case 7:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertUTM2WGS(e, h);
			transPt.convertWGS2WTM(g, j);
			break;
		case 8:
			transPt.convertUTM2WGS(e, h);
			transPt.convertWGS2WKTM();
			break;
		case 10:
			transPt.convertUTM2WGS(e, h);
			transPt.convertWGS2WCONG();
		}
		return transPt;
	}

	private static CoordinatesPoint convertCONGNAMUL2(CoordinatesPoint point, int d, double e, double h, double g, double j) {
		CoordinatesPoint transPt = point.clone();
		switch (d) {
		case 1:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2TM(g, j);
			break;
		case 2:
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case 3:
			if (g <= 0.0D) {
				g = 129.0D;
				j = 0.0D;
			}
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2UTM(g, j);
			break;
		case 4:
			break;
		case 5:
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case 6:
			transPt.convertCONG2BESSEL();
			break;
		case 7:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WTM(g, j);
			break;
		case 8:
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WKTM();
			break;
		case 10:
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WCONG();
		}
		return transPt;
	}

	private static CoordinatesPoint convertWGS2(CoordinatesPoint point, int d, double e, double h, double g, double j) {
		CoordinatesPoint transPt = point.clone();
		switch (d) {
		case 1:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(g, j);
			break;
		case 2:
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case 3:
			if (g <= 0.0D) {
				g = 129.0D;
				j = 0.0D;
			}
			transPt.convertWGS2UTM(g, j);
			break;
		case 4:
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case 5:
			break;
		case 6:
			transPt.convertWGS2BESSEL();
			break;
		case 7:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertWGS2WTM(g, j);
			break;
		case 8:
			transPt.convertWGS2WKTM();
			break;
		case 10:
			transPt.convertWGS2WCONG();
		}
		return transPt;
	}

	private static CoordinatesPoint convertBESSEL2(CoordinatesPoint point, int d, double e, double h, double g, double j) {
		CoordinatesPoint transPt = point.clone();
		switch (d) {
		case 1:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertBESSEL2TM(g, j);
			break;
		case 2:
			transPt.convertBESSEL2KTM();
			break;
		case 3:
			if (g <= 0.0D) {
				g = 129.0D;
				j = 0.0D;
			}
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2UTM(g, j);
			break;
		case 4:
			transPt.convertBESSEL2CONG();
			break;
		case 5:
			transPt.convertBESSEL2WGS();
			break;
		case 6:
			break;
		case 7:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WTM(g, j);
			break;
		case 8:
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WKTM();
			break;
		case 10:
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WCONG();
		}
		return transPt;
	}

	private static CoordinatesPoint convertWTM2(CoordinatesPoint point, int d, double e, double h, double g, double j) {
		CoordinatesPoint transPt = point.clone();
		switch (d) {
		case 1:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertWTM2WGS(e, h);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(g, j);
			break;
		case 2:
			transPt.convertWTM2WGS(e, h);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case 3:
			if (g <= 0.0D) {
				g = 129.0D;
				j = 0.0D;
			}
			transPt.convertWTM2WGS(e, h);
			transPt.convertWGS2UTM(g, j);
			break;
		case 4:
			transPt.convertWTM2WGS(e, h);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case 5:
			transPt.convertWTM2WGS(e, h);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case 6:
			transPt.convertWTM2WGS(e, h);
			transPt.convertWGS2BESSEL();
			break;
		case 7:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertWTM2WGS(e, h);
			transPt.convertWGS2WTM(g, j);
			break;
		case 8:
			transPt.convertWTM2WGS(e, h);
			transPt.convertWGS2WKTM();
			break;
		case 10:
			transPt.convertWTM2WGS(e, h);
			transPt.convertWGS2WCONG();
		}
		return transPt;
	}

	private static CoordinatesPoint convertWKTM2(CoordinatesPoint point, int d, double e, double h, double g, double j) {
		CoordinatesPoint transPt = point.clone();
		switch (d) {
		case 1:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertWKTM2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(g, j);
			break;
		case 2:
			break;
		case 3:
			if (g <= 0.0D) {
				g = 129.0D;
				j = 0.0D;
			}
			transPt.convertWKTM2WGS();
			transPt.convertWGS2UTM(g, j);
			break;
		case 4:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case 5:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case 6:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2BESSEL();
			break;
		case 7:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertWKTM2WGS();
			transPt.convertWGS2WTM(g, j);
			break;
		case 8:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2WKTM();
			break;
		case 10:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2WCONG();
		}
		return transPt;
	}

	private static CoordinatesPoint convertWCONGNAMUL2(CoordinatesPoint point, int d, double e, double h, double g, double j) {
		CoordinatesPoint transPt = point.clone();
		switch (d) {
		case 1:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(g, j);
			break;
		case 2:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case 3:
			if (g <= 0.0D) {
				g = 129.0D;
				j = 0.0D;
			}
			transPt.convertWCONG2WGS();
			transPt.convertWGS2UTM(g, j);
			break;
		case 4:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case 5:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case 6:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			break;
		case 7:
			if (g <= 0.0D) {
				g = 127.0D;
				j = 38.0D;
			}
			transPt.convertWCONG2WGS();
			transPt.convertWGS2WTM(g, j);
			break;
		case 8:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2WKTM();
			break;
		}
		return transPt;
	}
}
