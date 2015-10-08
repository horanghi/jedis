package redis.clients.jedis;

import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

import redis.clients.spatial.model.Point;

public final class GeoUtils {

	final CRSFactory crsFactory = new CRSFactory();

	final CoordinateReferenceSystem bessel1841 = crsFactory.createFromName("epsg:4162");
	final CoordinateReferenceSystem wgs84 = crsFactory.createFromName("epsg:4326");

	final CoordinateTransformFactory ctf = new CoordinateTransformFactory();

	final CoordinateTransform transBessel2WGS = ctf.createTransform(bessel1841, wgs84);
	final CoordinateTransform transWGS2Bessel = ctf.createTransform(wgs84, bessel1841);

	@SuppressWarnings("rawtypes")
	public final Point transBessl1841ToWGS84(final Point point) {
		ProjCoordinate src = new ProjCoordinate(point.getX(), point.getY());
		ProjCoordinate dest = new ProjCoordinate();
		transBessel2WGS.transform(src, dest);
		return new Point(dest.x, dest.y);
	}

	@SuppressWarnings("rawtypes")
	public final Point transWGS84ToBessl1841(final Point point) {
		ProjCoordinate src = new ProjCoordinate(point.getX(), point.getY());
		ProjCoordinate dest = new ProjCoordinate();
		transWGS2Bessel.transform(src, dest);
		return new Point(dest.x, dest.y);
	}

	@SuppressWarnings("rawtypes")
	public final Point transBessl1841ToWGS84(double x, double y) {
		ProjCoordinate src = new ProjCoordinate(x, y);
		ProjCoordinate dest = new ProjCoordinate();
		transBessel2WGS.transform(src, dest);
		return new Point(dest.x, dest.y);
	}

	@SuppressWarnings("rawtypes")
	public final Point transWGS84ToBessl1841(double x, double y) {
		ProjCoordinate src = new ProjCoordinate(x, y);
		ProjCoordinate dest = new ProjCoordinate();
		transWGS2Bessel.transform(src, dest);
		return new Point(dest.x, dest.y);
	}

}
