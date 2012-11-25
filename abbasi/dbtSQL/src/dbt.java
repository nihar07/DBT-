import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.*;
import com.thoughtworks.xstream.XStream;

public class dbt {
	private Database database;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
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
			System.out.println(s + "\n");
		} catch (Exception e) {
			System.out.print("Please enter something good");
		}
	    
		return s;
	}
	
	
	//Checking statement type
	public String checktypestatement(ArrayList<String> ts, String CompleteStatement) {
		String stype = new String(); //checking statement type like CREATE , INSERT or LOAD or SELECT
		String cs = CompleteStatement;
		
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
				String table = cs.substring((cs.toUpperCase()).indexOf("INTO")+4, cs.indexOf("(")).trim();
				
				//database.insert(cs.substring((cs.toUpperCase()).indexOf("FROM")+4, cs.length()).trim());
							stype = stype + "+" + "INTO";
							
			} } else {
				stype = "Syntax error";
			}
						
		} else if(ts.get(0).toUpperCase().equals("SELECT")  ){
			stype = "SELECT COMMAND";
			
			if(ts.size() > 1 ){
			
			if(ts.get(1).toUpperCase().equals("*")  ){
				
				stype = stype + "+" + "* is here";
				String table = ts.get(3).replace(";","");
				database.select(table);
				
			} } else {
				stype = "Syntax error";
			}
			
		} else if(ts.get(0).toUpperCase().equals("LOAD")  ){
			stype = "LOAD COMMAND";
			
			if(ts.size() > 2 ){
				if(ts.get(1).toUpperCase().equals("DATABASE")  ){
					stype = stype + "+" + "DATABASE";
					load(ts.get(2));
				}
			}else {
				stype = "Syntax error";
			}
		}else if(ts.get(0).replace(";", "").trim().toUpperCase().equals("SAVE") || ts.get(0).replace(";", "").trim().toUpperCase().equals("COMMIT") ){
			stype = "SAVE COMMAND";
			if(database != null){
				save(database);
			} else{
				System.out.println("A database needs to be created or loaded to " + ts.get(0) + ".");
			}
		} else if(ts.get(0).toUpperCase().equals("DELETE")  ){
			stype = "DELETE COMMAND";
			
			
			// assuming condition is only = (equals).
			if(ts.size() > 2 ){
				
				if(ts.get(1).toUpperCase().equals("FROM")  ){
					//database.deleteFrom(cs.substring((cs.toUpperCase()).indexOf("FROM")+4, cs.length()).trim());
					stype = stype + " +" + " FROM";
					
	} } else {
		stype = "Syntax error";
	}
			boolean whereclause = false;
			if(ts.size() > 3 )
			if(ts.get(3).toUpperCase().equals("WHERE")  ){
				//database.deleteFrom(cs.substring((cs.toUpperCase()).indexOf("FROM")+4, cs.length()).trim());
				stype = stype + " +" + " WHERE";
				whereclause = true;
} 			
			String tempRowname = ts.get(2).toUpperCase();
			String conditionfield;
			String fieldValue;
			if(!whereclause) {
			deleteRow(tempRowname.replace(";", "").trim());
			} else {
				if(ts.size() > 6 ){
				
				conditionfield = ts.get(4).toUpperCase();
				fieldValue = ts.get(6).toUpperCase().replace(";", "").trim();
				deleteRowwhere(tempRowname, conditionfield, fieldValue);
				
			} else {
				stype = "Syntax error bad where clause";
			}
				
			}
		
		
		} else if(ts.get(0).toUpperCase().equals("UPDATE")  ){
			stype = "UPDATE COMMAND";
			
			
			
		} else if(ts.get(0).toUpperCase().equals("DROP")  ){
			stype = "DROP COMMAND";
			if(ts.size() > 2  ){
				
				if(ts.get(1).toUpperCase().equals("DATABASE")  ){
					
					stype = stype + "+" + "DATABASE";
					if(database.dbCheck(ts.get(2).replace(";", "").trim().toUpperCase())){
						database = null;
						dropDB(ts.get(2).replace(";", "").trim().toUpperCase());
					}else{
						dropDB(ts.get(2).replace(";", "").trim().toUpperCase());
					}
					
				} else if(ts.get(1).toUpperCase().equals("TABLE")  ){   /// needs to add check for database is null or not
					
					stype = stype + "+" + "TABLE";
					if(!(database.tables.dropTable(ts.get(2).replace(";", "").trim().toUpperCase()))){
						System.out.println("Table doesn't exist.");
					}
					
					
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
	
	/* Save
	 * Saves any changes made to the current database to an xml file.
	 * @param DB Database object holding the data saved.
	 */
	public void save(Database DB){
		File file = new File(DB.getName().toUpperCase() + ".dbt");
		
		XStream xstream = new XStream();
		Writer writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(xstream.toXML(DB));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Load
	 * Loads the database from a xml file.
	 * @param dbName Name of the database to load.
	 */
	public void load(String dbName){
		XStream xstream = new XStream();
		dbName = dbName.replace(";", "").trim().toUpperCase();
		BufferedReader br;
		StringBuffer buff = null;
		try {
			br = new BufferedReader(new FileReader(dbName + ".dbt"));
			buff = new StringBuffer();
			String line;
			
			while((line = br.readLine()) != null){
			   buff.append(line);
			}

			database = (Database)xstream.fromXML(buff.toString());
		} catch (FileNotFoundException e) {
			System.out.println("Database doesn't exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// drop the database
	public void dropDB(String filename){

		// remove external database file
		File rmFile = new File(filename + ".dbt");
		if(rmFile.exists()){
			rmFile.delete();
		} else {
			System.out.println("Database doesn't exist.");
		}
	}
	
	//delete row without WHERE condition..
		public void deleteRow(String tblName){
			database.deleteRowst(tblName);
		}
		
		
		//delete row with WHERE condition..
		public void deleteRowwhere(String tblName, String condtionField, String fieldVa){
			database.deleteRowstwhere(tblName,condtionField, fieldVa);
		}
	

	public void insert(ArrayList<String> ts) {
		
		insertStatement is = new insertStatement();
		
		if(ts.size() > 7  ){
		
		is.tableName = (String) ts.get(2);
		
		if(!ts.get(3).toUpperCase().equals("VALUES")){
			
			System.out.println("syntax error missing VALUES");
			
		}
		
		int i = 4;
		boolean braccheck;
		boolean bracend = false;
		
		
if(!ts.get(4).toUpperCase().equals("(")){
			
	System.out.println("syntax error missing bracket");
			
		} else {
			braccheck = true;
		}
		while(ts.get(i).toUpperCase().equals(";")){
			
			if (bracend == true){
				if(!ts.get(i).isEmpty() ){
					System.out.println("syntax error insert not defined right");
				}
			}
			
			if(ts.get(i).toUpperCase().equals(")")){
				
				bracend = true;
						
					}
			
			i++;
		}
		
		
		
		} else {
			System.out.println("sysntax error missing parameteres");
		}
		
		
	}
}
