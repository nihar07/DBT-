
public class CharType {
	String type = "CHARACTER";
	String contents;
	int numChar;
	
	public CharType(int n, String input){
		numChar = n;
		if(input.length() < numChar)
			contents = input;
	}
	
	public String getType(){
		return type;
	}
	
	public String toString(){
		while(contents.length() < numChar)
			contents = contents + " ";
		
	return contents;
	}
}
