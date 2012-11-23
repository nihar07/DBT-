import java.text.*;


public class Number {
	double number;
	int numInt;
	int numFrac;
	NumberFormat df;
	
	public Number(int x, int y, String input){
		numInt = x;
		numFrac = y;
		df = new DecimalFormat("#.0");
		df.setMaximumIntegerDigits(x);
		df.setMaximumFractionDigits(y);
		
		number = Double.parseDouble(input);
	}
	
	public int getNumInt(){
		return numInt;
	}
	
	public int getNumFrac(){
		return numFrac;
	}
	
	public String toString(){
		return df.format(number);
	}
}
