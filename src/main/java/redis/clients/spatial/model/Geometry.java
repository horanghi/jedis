package redis.clients.spatial.model;

import java.io.Serializable;

import redis.clients.jedis.Protocol.Type;

public interface Geometry extends Serializable {

	public Type getType();
}
