
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
	
	public int getDec(){
		return dec;
	}
	
	public String toString(){
		String retString = name;
		if((places + dec) > 0)
			while(retString.length() < (places + dec))
				retString = retString + " ";
		else if(type.contentEquals("DATE"))
			while(retString.length() < 10)
				retString = retString + " ";
		else
			retString = retString + "     ";
		return retString;
	}
}
