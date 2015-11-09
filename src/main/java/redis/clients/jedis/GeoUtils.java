package redis.clients.jedis;

import redis.clients.jedis.Protocol.UNITS;
import redis.clients.spatial.model.Point;

import com.skplanet.lbs.coordinate.CoordPoint;
import com.skplanet.lbs.coordinate.TransCoord;

public final class GeoUtils {

	public final static double EarthRadius = 6378137; // meters (EPSG 3785)

	// final static Ellipsoid bessel1841 = new Ellipsoid(6377397.155, 1.0 / 299.152813);
	// final static Ellipsoid wgs1984 = new Ellipsoid(GeoUtils.EarthRadius, 1.0 / 298.257223563);
	// final static Parameters7 params = new Parameters7(-115.8, 474.99, 674.11, -1.16, 2.31, 1.63, 6.43);
	// final static Ellip2Ellipsoid transform = new Ellip2Ellipsoid(bessel1841, wgs1984, params);

	@SuppressWarnings("rawtypes")
	public static Point transBessel1841ToWGS84(final Point point) {

		CoordPoint pt = new CoordPoint(point.getY(), point.getX());
		CoordPoint wgsPt = TransCoord.getTransCoord(pt, TransCoord.COORD_TYPE_BESSEL, TransCoord.COORD_TYPE_WGS84);

		return new Point(wgsPt.y, wgsPt.x);
	}

	@SuppressWarnings("rawtypes")
	public static Point transBessel1841ToWGS84(double x, double y) {

		return GeoUtils.transBessel1841ToWGS84(new Point(x, y));
	}

	// @SuppressWarnings("rawtypes")
	// public static Point transWGS84ToBessel1841(final Point point) {
	//
	// CoordPoint pt = new CoordPoint(point.getY(), point.getX());
	// CoordPoint besselPt = TransCoord.getTransCoord(pt, TransCoord.COORD_TYPE_WGS84, TransCoord.COORD_TYPE_BESSEL);
	//
	// return new Point(besselPt.y, besselPt.x);
	// }
	// @SuppressWarnings("rawtypes")
	// public static Point transWGS84ToBessel1841(double x, double y) {
	//
	// return GeoUtils.transWGS84ToBessel1841OfLBS(new Point(x, y));
	// }

	// @SuppressWarnings("rawtypes")
	// public static Point trans2Bessel1841ToWGS84(final Point point) {
	//
	// Values3 src = new Values3(point.getX(), point.getY(), 0);
	// Values3 dst = new Values3();
	// transform.transfom(src, dst);
	//
	// return new Point(dst.V1, dst.V2);
	// }
	//
	// @SuppressWarnings("rawtypes")
	// public static Point trans2Bessel1841ToWGS84(double x, double y) {
	//
	// return GeoUtils.trans2Bessel1841ToWGS84(new Point(x, y));
	// }

	// @SuppressWarnings("rawtypes")
	// public static Point trans2WGS84ToBessel1841(final Point point) {
	//
	// Values3 src = new Values3(point.getX(), point.getY(), 0);
	// Values3 dst = new Values3();
	// transform.reverseTransform(src, dst);
	//
	// return new Point(dst.V1, dst.V2);
	// }
	// @SuppressWarnings("rawtypes")
	// public static Point trans2WGS84ToBessel1841(double x, double y) {
	//
	// return GeoUtils.transWGS84ToBessel1841(new Point(x, y));
	// }

	public static double toMeter(UNITS units, double fromValue) {
		double resultValue = fromValue;
		switch (units) {
		case KM:
			resultValue = fromValue * 1000;
			break;
		case FEET:
			resultValue = fromValue * 0.3048;
			break;
		case MILES:
			resultValue = (fromValue / 0.6214) * 1000;
			break;
		default:
			break;
		}
		return resultValue;
	}

}
