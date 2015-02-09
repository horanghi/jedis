package redis.clients.jedis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class GCircle extends GPoint {
	@Setter
	@Getter
	private double distance;
	@Setter
	@Getter
	private UNITS unit;
	
	@Setter(value=AccessLevel.PROTECTED)
	@Getter(value=AccessLevel.PROTECTED)
	private boolean isCircle;
	
}
