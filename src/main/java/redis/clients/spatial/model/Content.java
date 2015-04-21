package redis.clients.spatial.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;

@ToString
@EqualsAndHashCode
public class Content<T> implements Serializable {

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
	@Setter
	@Getter
	private double distance;

	public Content() {
	}

	public Content(final T member, final T value) {
		this.member = member;
		this.value = value;
	}

	public Type getType() {
		return Type.GEOMETRY;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public boolean equals(Object o) {
//		if (o == null) {
//			return false;
//		}
//		
//		if (this.member instanceof String) {
//			Geometry<String> go = (Geometry<String>) o;
//			if(!go.member.equals(this.member)){
//				return false;
//			}
//			if(!go.value.equals(this.value)){
//				return false;
//			}
//		}else if (this.member instanceof byte[]) {
//			Geometry<byte[]> go = (Geometry<byte[]>) o;
//			if(!go.member.equals(this.member)){
//				return false;
//			}
//			if(!go.value.equals(this.value)){
//				return false;
//			}
//		}
//		
//		return true;
//	}
}
