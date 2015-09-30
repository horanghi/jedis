package redis.clients.util;

public enum COORD {
	COORD_TYPE_TM(1),
	COORD_TYPE_KTM(2),
	COORD_TYPE_UTM(3),
	COORD_TYPE_CONGNAMUL(4),
	COORD_TYPE_WGS84(5),
	COORD_TYPE_BESSEL(6),
	COORD_TYPE_WTM(7),
	COORD_TYPE_WKTM(8),
	COORD_TYPE_WCONGNAMUL(10);

	public int code;

	COORD(int code) {
		this.code = code;
	}

}
