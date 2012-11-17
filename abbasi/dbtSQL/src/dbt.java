import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class dbt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		dbt mydbt = new dbt();
		String temp = new String();
		String firstkeyword = new String();
		String input = mydbt.entercommand();
		ArrayList<String> tokenizestatement = new ArrayList<String>();
		 StringTokenizer st;
		
		while(input.toUpperCase().contains("EXIT")==false ){
			
			
			st = new StringTokenizer(input);
			
			while (st.hasMoreTokens()) {
				tokenizestatement.add(st.nextToken());
		        // System.out.println(st.nextToken());
		     }
			//firstkeyword = (String) st.nextElement();
			
			temp = mydbt.checktypestatement(tokenizestatement, input);
			
			System.out.println(temp);
			
			tokenizestatement = new ArrayList<String>();
			input = mydbt.entercommand();
			
			
			
		}

	}

	public String entercommand() {
		System.out.print("DbtSQL>");
		String s = null;
 
	    try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			s = bufferRead.readLine();
			System.out.println(s);
		} catch (Exception e) {
			System.out.print("Please enter something good");
			// TODO: handle exception
		}
		return s;
		
	}
	
	
	//Checking statement type
	public String checktypestatement(ArrayList<String> ts, String CompleteStatement) {
		String stype = new String(); //checking statement type like CREATE , INSERT or LOAD or SELECT
		
		
		if(ts.get(0).toUpperCase().equals("CREATE")  ){
			
			stype = "CREATE COMMAND";
			
			if(ts.size() > 2  ){
			
			if(ts.get(1).toUpperCase().equals("DATABASE")  ){
				
				stype = stype + "+" + "DATABASE";
				
			} else if(ts.get(1).toUpperCase().equals("TABLE")  ){   /// needs to add check for dabase is null or not
				

				stype = stype + "+" + "TABLE";
				
				
			}else {
				stype = "Please enter something good";
			}
			} else {
				stype = "Syntax error";
			}
		//  stype = "CREATE COMMAND";
			
		} else if(ts.get(0).toUpperCase().equals("INSERT")  ){
						stype = "INSERT COMMAND";
						
						if(ts.size() > 1 ){
							
			if(ts.get(1).toUpperCase().equals("INTO")  ){
							
							stype = stype + "+" + "INTO";
							
			} } else {
				stype = "Syntax error";
			}
						
		} else if(ts.get(0).toUpperCase().equals("SELECT")  ){
			stype = "SELECT COMMAND";
			
			if(ts.size() > 1 ){
			
			if(ts.get(1).toUpperCase().equals("*")  ){
				
				stype = stype + "+" + "* is here";
				
} } else {
	stype = "Syntax error";
}
			
		} else if(ts.get(0).toUpperCase().equals("LOAD")  ){
			stype = "LOAD COMMAND";
			
			if(ts.size() > 2 ){
			
			if(ts.get(1).toUpperCase().equals("DATABASE")  ){
				
				stype = stype + "+" + "DATABASE";
				
				
				
			}} else {
				stype = "Syntax error";
			}
		}else if(ts.get(0).toUpperCase().equals("SAVE") || ts.get(0).toUpperCase().equals("COMMIT") ){
			stype = "SAVE COMMAND";
			
			
			
		} else if(ts.get(0).toUpperCase().equals("DELETE")  ){
			stype = "DELETE COMMAND";
			if(ts.size() > 1 ){
				
				if(ts.get(1).toUpperCase().equals("FROM")  ){
					
					stype = stype + " +" + " FROM";
					
	} } else {
		stype = "Syntax error";
	}
			
			
		} else if(ts.get(0).toUpperCase().equals("UPDATE")  ){
			stype = "DELETE COMMAND";
			
			
			
		} else if(ts.get(0).toUpperCase().equals("DROP")  ){
			stype = "DELETE COMMAND";
			if(ts.size() > 2  ){
				
				if(ts.get(1).toUpperCase().equals("DATABASE")  ){
					
					stype = stype + "+" + "DATABASE";
					
				} else if(ts.get(1).toUpperCase().equals("TABLE")  ){   /// needs to add check for database is null or not
					

					stype = stype + "+" + "TABLE";
					
					
				}else {
					stype = "Please enter something good";
				}
				} else {
					stype = "Syntax error";
				}
			
			
		} 
		
		else {
			stype = "Please enter something good";
		}
		
		return stype;
	}
	
	
	
	
}
