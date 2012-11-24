
public class Header {
	String type;
	String name;
	int places;
	int dec;
	
	public Header(String dataType, String fieldName, int numPlaces){
		type = dataType;
		name = fieldName;
		places = numPlaces;
	}
	
	public Header(String dataType, String fieldName, int numPlaces, int numDecimals){
		type = dataType;
		name = fieldName;
		places = numPlaces;
		dec = numDecimals;
	}
	
	public String getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPlaces(){
		return places;
	}
	
	public String toString(){
		return name;
	}
}
