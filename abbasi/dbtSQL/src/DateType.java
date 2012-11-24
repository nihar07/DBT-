import java.util.*;


public class DateType {
	String month;
	String day;
	String year;
	
	public DateType(String input){
		StringTokenizer tokens = new StringTokenizer(input, "/");
		ArrayList<String> date = new ArrayList<String>();
		
		while(tokens.hasMoreTokens())
			date.add(tokens.nextToken());
		
		//check number of characters in month
		if(date.get(0).length() == 1)
			month = "0" + date.get(0);
		else if(date.get(0).length() == 2)
			month = date.get(0);	
		else
			System.out.println("Error in date format (month)");
		
		//check number of characters in day
		if(date.get(1).length() == 1)
			day = "0" + date.get(1);
		else if(date.get(1).length() == 2)
			day = date.get(1);
		else
			System.out.println("Error in date format (day)");
		
		//check number of characters in year
		//DEFAULT: 2-digit year is 1900 + year (82 = 1982; 26 = 1926)
		if(date.get(2).length() == 2)
			year = "19" + date.get(2);
		else if(date.get(2).length() == 4)
			year = date.get(2);
		else
			System.out.println("Error in date format (year)");
	}
	
	public String toString(){
		String rVal = month + "/" + day + "/" + year;
		return rVal;
	}
}
