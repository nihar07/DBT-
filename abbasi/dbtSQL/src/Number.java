import java.text.*;


public class Number {
	String type = "NUMBER";
	double number;
	int numInt;
	int numFrac;
	NumberFormat df;
	
	public Number(String input){
		number = Double.parseDouble(input);
	}
	
	public Number(int x, String input){
		numInt = x;
		df = new DecimalFormat("#");
		df.setMaximumIntegerDigits(x);
		df.setMaximumFractionDigits(0);
		
		number = Double.parseDouble(input);
	}
	
	public Number(int x, int y, String input){
		numInt = x;
		numFrac = y;
		df = new DecimalFormat("#.0");
		df.setMaximumIntegerDigits(x);
		df.setMaximumFractionDigits(y);
		
		number = Double.parseDouble(input);
	}
	
	public String getDataType(){
		return type;
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
