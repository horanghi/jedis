package redis.clients.spatial.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.Protocol.Type;

public class Geometry<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6597411885909001624L;

	@Setter
	@Getter
	private T member;
	@Setter
	@Getter
	private T value;

	public Geometry() {
	}

	public Geometry(final T member, final T value) {
		this.member = member;
		this.value = value;
	}

	public Type getType(){
		return Type.GEOMETRY;
	}
}
