
public class Character {
	String type = "CHARACTER";
	String contents;
	int numChar;
	
	public Character(int n, String input){
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
