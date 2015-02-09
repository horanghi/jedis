package redis.clients.jedis;

public enum UNITS {
	M("m"), KM("km");
	
	private String value;
	
	UNITS(final String value){
		this.value = value;
	}
	
	public String toValue(){
		return this.value;
	}
}
