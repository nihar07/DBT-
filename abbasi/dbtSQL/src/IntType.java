import java.text.DecimalFormat;
import java.text.NumberFormat;


public class IntType {
	int number;
	int numDigits;
	int numFrac;
	NumberFormat df;
	
	public IntType(int x, String input){
		numDigits = x;
		df = new DecimalFormat("#");
		df.setMaximumIntegerDigits(x);
		df.setMaximumFractionDigits(0);
		
		number = Integer.parseInt(input);
	}
	
	public int getNumDigits(){
		return numDigits;
	}
	
	public String toString(){
		return df.format(number);
	}
}
