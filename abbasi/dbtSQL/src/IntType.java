
public class IntType {
	String type = "INTEGER";
	int number;
	int numDigits;
	int numFrac;
	
	public IntType(String input){
		number = Integer.parseInt(input);
	}
	
	public IntType(int x, String input){
		numDigits = x;
		number = Integer.parseInt(input);
	}
	
	public int getNumDigits(){
		return numDigits;
	}
	
	public String getType(){
		return type;
	}
	
	public String toString(){
		String retString = Integer.toString(number);
		if(numDigits == 0)
			retString = retString + "   ";
		else
			while(retString.length() < numDigits)
				retString = retString + " ";
		return retString;
	}
}
