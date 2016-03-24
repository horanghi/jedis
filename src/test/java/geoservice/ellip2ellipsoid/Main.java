package geoservice.ellip2ellipsoid;

public class Main {

	public static void main(String[] args) {

		// 127.4391389 37.09011111 127.4369722 37.09294444

		Ellipsoid bessel1841 = new Ellipsoid(6377397.155, 1.0 / 299.152813);
		Ellipsoid wgs1984 = new Ellipsoid(6378137, 1.0 / 298.257223563);
		Parameters7 params = new Parameters7(-115.8, 474.99, 674.11, -1.16, 2.31, 1.63, 6.43);

		Parameters7 params2 = new Parameters7(-145.87375, 503.34752, 684.74189, -1.161703, 2.3457, 1.591998, 6.3423);

		Ellip2Ellipsoid transform = new Ellip2Ellipsoid(bessel1841, wgs1984, params);
		
		Ellip2Ellipsoid transform2 = new Ellip2Ellipsoid(wgs1984, bessel1841, params2);

		Values3 src = new Values3(37.09011111, 127.4391389, 0);
		Values3 dst = new Values3();

		System.out.println("bessel lat/lng -> wgs84 lat/lng");
		transform.transfom(src, dst);
		System.out.println(src + " -> " + dst + "\n");

		Values3 src1 = new Values3(dst.V1, dst.V2, 0);
		Values3 dst1 = new Values3();

		System.out.println("wgs84 lat/lng -> bessel lat/lng");
		transform2.transfom(src1, dst1);
		System.out.println(src1 + " -> " + dst1);

		Values3 src2 = new Values3(dst1.V1, dst1.V2, 0);
		Values3 dst2 = new Values3();

		System.out.println("bessel lat/lng -> wgs84 lat/lng");
		transform.transfom(src2, dst2);
		System.out.println(src2 + " -> " + dst2);
		
		//
		// // B 127.0966667 37.31841667
		// // W 127.0945278 37.32122222
		//
		// Ellipsoid bessel1841 = new Ellipsoid(6377397.155, 1.0 / 299.152813);
		// Ellipsoid wgs1984 = new Ellipsoid(6378137, 1.0 / 298.257223563);
		// Parameters7 params = new Parameters7(-115.8, 474.99, 674.11, -1.16, 2.31, 1.63, 6.43);
		//
		// Ellip2Ellipsoid transform = new Ellip2Ellipsoid(bessel1841, wgs1984, params);
		//
		// double y = 127.0966667;
		// double x = 37.31841667;
		//
		// // 36.65460384473778, 128.12819486689486,
		//
		// Values3 src = new Values3(x, y, 0);
		// Values3 dst = new Values3();
		//
		// System.out.println("bessel lat/lng -> wgs84 lat/lng");
		// transform.transfom(src, dst);
		// System.out.println(src + " -> " + dst + "\n");
		// // 37.3212430488147, 127.09455843178665
		//
		// Values3 src1 = new Values3(37.3212430488147, 127.09455843178665, 0);
		// Values3 dst1 = new Values3();
		//
		// System.out.println("wgs84 lat/lng -> bessel lat/lng   ");
		// transform.reverseTransform(src1, dst1);
		// System.out.println(src1 + " -> " + dst1);
		//
		// Values3 src2 = new Values3(dst1.V1, dst.V2, 0);
		// Values3 dst2 = new Values3();
		//
		// System.out.println("bessel lat/lng -> wgs84 lat/lng");
		// transform.transfom(src2, dst2);
		// System.out.println(src2 + " -> " + dst2 + "\n");

		// 36.65169444 128.1303889
		// 36.64587455071312, 128.13477749722443
		// 36.65167390220105, 128.1303607487695

		// CoordPoint pt = new CoordPoint(y, x);
		// CoordPoint besselPt = TransCoord.getTransCoord(pt, TransCoord.COORD_TYPE_BESSEL, TransCoord.COORD_TYPE_WGS84);
		// System.out.println("WGS84 XY 좌표 :: " + besselPt.y + ", " + besselPt.x);

	}
	// 129.3876389 36.34802778 129.3853333 36.351

	// 127.8112222 37.98369444 127.809 37.98644444
	// 127.4391389 37.09011111 127.4369722 37.09294444
	// 127.9853611 34.74922222 127.9832222 34.75233333
	// 128.5805556 35.20302778 128.5783333 35.20611111
	// 128.3424444 36.89147222 128.3402222 36.89436111
	// 127.3188056 37.35688889 127.3166389 37.35969444
	// 127.0005556 37.49430556 126.9984167 37.49708333
	// 127.0276389 35.36244444 127.0255556 35.36547222
	// 128.1303889 36.65169444 128.1281667 36.65458333
	// 127.7557222 37.87361111 127.7535 37.87636111
	// 126.9500833 35.33377778 126.9480278 35.33680556
	// 127.04225 37.52263889 127.0401111 37.52541667
	// 126.6011944 34.97561111 126.5991667 34.97866667
	// 126.3218056 37.68394444 126.3197222 37.68669444
	// 128.0423333 34.79691667 128.0401944 34.80002778
	// 126.9314167 37.55175 126.9292778 37.55452778
	// 126.9871111 37.34063889 126.9849722 37.34344444
	// 126.524 36.43613889 126.5219444 36.43902778
	// 127.0966667 37.31841667 127.0945278 37.32122222
	// 127.0044444 37.55744444 127.0023056 37.56022222
	// 126.8416111 37.64016667 126.8395 37.64291667
	// 127.4976389 37.49072222 127.4954722 37.49352778
	// 126.5126667 37.60911111 126.5105833 37.61186111
	// 126.7426944 37.34755556 126.7405833 37.35036111
	// 126.6473889 37.20338889 126.6453056 37.20619444
	// 126.8523056 36.67666667 126.8502222 36.67952778
	// 129.1281389 35.56008333 129.1258611 35.56313889
	// 126.8537778 37.71188889 126.8516667 37.71463889
	// 126.9755278 37.2645 126.9734167 37.26730556
	// 127.9279444 36.98597222 127.92575 36.98883333
	// 127.0400556 37.48155556 127.0379167 37.48433333
	// 127.1528889 35.80855556 127.1507778 35.81152778
	// 127.12825 37.509 127.1261111 37.51177778
	// 127.0253333 37.50730556 127.0231944 37.51008333
	// 127.1546667 35.81005556 127.1525556 35.81302778
	// 126.9241389 37.54983333 126.922 37.55261111
	// 127.1418333 37.59930556 127.1396944 37.60208333
	// 126.9018333 37.48063889 126.8997222 37.48341667
	// 127.0376944 37.49175 127.0355556 37.49452778

}