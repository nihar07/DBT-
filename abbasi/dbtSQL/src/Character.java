
public class Character {
	String contents;
	int numChar;
	
	public Character(int n, String input){
		numChar = n;
		if(input.length() < numChar)
			contents = input;
	}
	
	public String toString(){
		while(contents.length() < numChar)
			contents = contents + " ";
		
	return contents;
	}
}
