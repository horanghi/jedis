package redis.clients.spatial.model;

import redis.clients.jedis.Protocol.Type;

public interface GeometryInterface {
	public Type getType();
}
