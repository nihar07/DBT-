import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.*;

import com.thoughtworks.xstream.XStream;


public class dbt {
	private Database database;

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
				database = new Database(ts);
				
			} else if(ts.get(1).toUpperCase().equals("TABLE")  ){
				if(database == null){
					stype = "  *Error:  Cannot create table - no database loaded.";
					return stype;
				}
				String cs = CompleteStatement;
				//database.createTable(CompleteStatement);
				database.createTable(cs.substring((cs.toUpperCase()).indexOf("TABLE")+5, cs.length()).trim());	

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
	
	
	public void Save(Database DB){
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(DB.getName() + ".xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XStream xstream = new XStream();
		//Database
		xstream.alias("database", Database.class);
		//Tables
		xstream.alias("table", Table.class);
		xstream.alias("tables", TableList.class);
		xstream.addImplicitCollection(TableList.class, "tables");
		//Rows
		xstream.alias("row", DataList.class);
		xstream.alias("rows", RowList.class);
		xstream.addImplicitCollection(RowList.class, "rows");
		//Data
		xstream.alias("data", Object.class);
		
		DB.createTable("TBL1");
		DB.tables.getTable("TBL1").rows.add(new DataList());
		DB.tables.getTable("TBL1").rows.getRow(0).add(10);
		
		String xml = xstream.toXML(DB);
		System.out.println(xml);
	}
	
}
