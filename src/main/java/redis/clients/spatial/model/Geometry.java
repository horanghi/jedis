package redis.clients.spatial.model;

import java.io.Serializable;
import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import redis.clients.jedis.Protocol.Type;

import com.vividsolutions.jts.geom.GeometryFactory;

@ToString(exclude = "gf")
public class Geometry<T> implements Serializable, Comparable<T> {

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
	protected Double score = null;

	final GeometryFactory gf = new GeometryFactory();

	protected Geometry() {
	}

	protected Geometry(final T member, final T value) {
		this.member = member;
		this.value = value;
	}

	protected Geometry(final T member, final T value, double score) {
		this.member = member;
		this.value = value;
		this.score = score;
	}

	public Type getType() {
		return Type.GEOMETRY;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		return this.equalsDeep((Geometry<T>) o);
	}

	@SuppressWarnings("unchecked")
	public boolean equalsDeep(Geometry<T> o) {
		try {
			if (this.member instanceof String) {
				Geometry<String> other = (Geometry<String>) o;
				if (this.member.equals(other.getMember())) {
					if (this.value == null && other.getValue() == null) {
						return true;
					} else if (this.value != null && this.value.equals(other.getValue())) {
						return true;
					}

				}
			} else {
				Geometry<byte[]> other = (Geometry<byte[]>) o;
				if (Arrays.equals(((Geometry<byte[]>) this).getMember(), ((Geometry<byte[]>) other).getMember())) {
					if (this.value == null && other.getValue() == null) {
						return true;
					} else if (this.value != null
							&& Arrays.equals(((Geometry<byte[]>) this).getValue(), ((Geometry<byte[]>) other).getValue())) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}

	public String getJsonStr() {
		return null;
	}

	public byte[] getJsonByte() {
		return null;
	}

	@Override
	public int compareTo(T o) {
		if (this.equals((Object) o)) {
			return 0;
		}
		return -1;
	}
}
