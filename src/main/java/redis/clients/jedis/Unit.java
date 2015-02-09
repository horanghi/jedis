package redis.clients.jedis;

public enum Unit {
	KM("km"), M("m");
	
	private String value;
	
	Unit(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
