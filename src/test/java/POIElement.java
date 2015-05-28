import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class POIElement {
	class Category {
		String category_id;
		String type;
		String name;
	}
	class Activities{
		boolean wished;
		boolean picked;
	}
	class Main_image{
		String image;
		String type;
	}
	class Scoreboard{
		long wishes;
		long score;
		long up_ratings;
		long picks;
		long down_ratings;
		long comments;
		long images;
		long blog_reviews;
	}
	class Theme{
		long theme_id;
		long public_theme_id;
		String type;
		String name;
	}
	
	class Location{
		String type;
		ArrayList<Double> coordinates = new ArrayList<Double>();
	}
	
	@Getter@Setter
	Category category;
	
	@Getter@Setter
	Activities activities;
	
	@Getter@Setter
	long poi_id;
	
	@Getter@Setter
	Main_image main_image;
	
	@Getter@Setter
	String name;
	
	@Getter@Setter
	List<String> tags;
	
	@Getter@Setter
	Scoreboard scoreboard;
	
	@Getter@Setter
	String abbr_address;
	
	@Getter@Setter
	Theme theme;
	
	@Getter@Setter
	boolean has_coupon;
	
	@Getter@Setter
	Location location;
	
	@Getter@Setter
	String address;
	
	@Getter@Setter
	boolean is_closed;
}
