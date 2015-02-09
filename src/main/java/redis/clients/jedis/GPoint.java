package redis.clients.jedis;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class GPoint {
	@Setter
	@Getter
	private double x;
	@Setter
	@Getter
	private double y;
	@Setter
	@Getter
	private String member;
	@Setter
	@Getter
	private String value;
}
