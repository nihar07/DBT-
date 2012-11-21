
public class Number {
	int whole;
	int numDigits;
	int decimal;
	int numDecimals;
	
	public Number(int w, int d, String input){
		if(d > 0){
			whole = Integer.parseInt(input.substring(0, input.indexOf('.')));
			System.out.println(input.indexOf('.')+1);
			System.out.println(input.indexOf('.')+1);
			System.out.println(input.length());
			decimal = Integer.parseInt(input.substring(input.indexOf('.')+1));
		}
		else
			whole = Integer.parseInt(input);
			
				
		
			
	}
	
	public String toString(){
		String str = Integer.toString(whole) + "." + Integer.toString(decimal);
		return str;
	}
}
