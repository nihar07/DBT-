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
		
		System.out.println("Welcome to DBT SQL!");
		dbt mydbt = new dbt();
		String temp = new String();
		String input = mydbt.entercommand();
		ArrayList<String> tokenizestatement = new ArrayList<String>();
		StringTokenizer st;
		
		while(input.toUpperCase().contains("EXIT")==false ){
			st = new StringTokenizer(input);
			
			//split input into tokens
			while (st.hasMoreTokens()) {
				tokenizestatement.add(st.nextToken());
		     }
			
			//check input
			temp = mydbt.checktypestatement(tokenizestatement, input);
			System.out.println(temp);
			tokenizestatement = new ArrayList<String>();
			input = mydbt.entercommand();
		}
		
		System.out.println("\"Exit\" detected.  Program terminated.");
	}

	public String entercommand() {
		System.out.print("DbtSQL>");
		String s = null;
 
	    try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			s = bufferRead.readLine();
		} catch (Exception e) {
			System.out.print("Invalid input");
		}
	    
		return s;
	}
	
	
	//Check statement type
	public String checktypestatement(ArrayList<String> ts, String CompleteStatement) {
		String stype = new String(); //checking statement type like CREATE , INSERT or LOAD or SELECT
		String cs = CompleteStatement;
		
		//if CREATE
		if(ts.get(0).toUpperCase().equals("CREATE")  ){
			//stype = "CREATE COMMAND";
			
			if(ts.size() > 2  ){
				if(ts.get(1).toUpperCase().equals("DATABASE")  ){
				
					//stype = stype + "+" + "DATABASE";
					database = new Database(ts);
					stype = "Database created:  " + database.getName() + "\n";
				
				}
				else if(ts.get(1).toUpperCase().equals("TABLE")  && cs.contains("(")){
					String cmd = cs.substring((cs.toUpperCase()).indexOf("TABLE")+5, cs.length()).trim();
					String name = cmd.substring(0, cmd.indexOf("(")).trim().toUpperCase();
					
					if(database == null){
						stype = "  *Error:  Cannot create table - no database loaded";
						return stype;
					}
					else if(database.getTable(name) != null){
						stype = "  *Error:  Table already exists in database";
						return stype;
					}
					
					database.createTable(cmd);	
					stype = "Table created:  " + name + "\n";
				
				}
				else 
					stype = "Please enter something good";
			}
			else {
				stype = "Syntax error";
			}
			//stype = "CREATE COMMAND";
		}
		else if(ts.get(0).toUpperCase().equals("INSERT")  ){
			stype = "INSERT COMMAND";
			
			if(ts.size() > 1 && 
					ts.get(1).toUpperCase().equals("INTO") && 
					cs.toUpperCase().contains("VALUES")){
							
				if(database != null)
					database.insert(cs.substring(cs.toUpperCase().indexOf("INTO")+4).trim());
				else
					System.out.println("You must first create or load a database");
				
				stype = stype + "+" + "INTO";
							
			}
			else
				stype = "Syntax error";
						
		}
		else if(ts.get(0).toUpperCase().equals("SELECT")  ){
			stype = "SELECT COMMAND";
			
			if(ts.size() > 1 ){
				if(ts.get(1).toUpperCase().equals("*")  ){
					stype = stype + "+" + "* is here";
					String table = ts.get(3).replace(";","");
					database.select(table);
				}
			}
			else {
				stype = "Syntax error";
			}
		}
		else if(ts.get(0).toUpperCase().equals("LOAD")  ){
			stype = "LOAD COMMAND";
			
			if(ts.size() > 2 && ts.get(1).toUpperCase().equals("DATABASE")  ){
				stype = stype + "+" + "DATABASE";
				load(ts.get(2));
			}
			else {
				stype = "Syntax error";
			}
		}
		else if(ts.get(0).replace(";", "").trim().toUpperCase().equals("SAVE") || ts.get(0).replace(";", "").trim().toUpperCase().equals("COMMIT") ){
			stype = "SAVE COMMAND";
			if(database != null){
				save(database);
			}
			else{
				System.out.println("A database needs to be created or loaded to " + ts.get(0) + ".");
			}
		}
		else if(ts.get(0).toUpperCase().equals("DELETE")  ){
			stype = "DELETE COMMAND";
			
			// assuming condition is only = (equals).
			if(ts.size() > 2 ){
				if(ts.get(1).toUpperCase().equals("FROM")  ){
					//database.deleteFrom(cs.substring((cs.toUpperCase()).indexOf("FROM")+4, cs.length()).trim());
					stype = stype + " +" + " FROM";	
				}
			}
			else
				stype = "Syntax error";
			
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
			}
			else {
				if(ts.size() > 6 ){
					conditionfield = ts.get(4).toUpperCase();
					if(!ts.get(5).toUpperCase().equals("=")){
						System.out.println("Syntax error missing = sign" + ts.get(5).toUpperCase());
					}
					fieldValue = ts.get(6).toUpperCase().replace(";", "").trim();
					deleteRowwhere(tempRowname, conditionfield, fieldValue);
				}
				else {
					stype = "Syntax error bad where clause";
				}
			}
		}
		else if(ts.get(0).toUpperCase().equals("UPDATE")  ){
			stype = "UPDATE COMMAND";
			if(database == null){
				return "A database must be created or loaded first.";
			}
			if(ts.size() < 4){
				return "Syntax Error: Missing arguments.";
			}
			if(database.tables == null){
				return "A table must be created first.";
			}
			if(database.tables.getTable(ts.get(1).trim().toUpperCase()) != null){
					if(ts.get(2).equalsIgnoreCase("SET")){
					
						System.out.println(update(ts.get(1), CompleteStatement));
					}
					else {
						return "Syntax Error: Missing 'SET' keyword.";
					}
			}
			else{
				return "Table doesn't exist in database " + database.name + ".";
			}
		}
		else if(ts.get(0).toUpperCase().equals("DROP")  ){
			stype = "DROP COMMAND";
			
			if(ts.size() > 2  ){
				if(ts.get(1).toUpperCase().equals("DATABASE")  ){
					stype = stype + "+" + "DATABASE";
					if(database == null)
						System.out.println("No database present - create or load a database first");
					else if(database.dbCheck(ts.get(2).replace(";", "").trim().toUpperCase())){
						database = null;
						dropDB(ts.get(2).replace(";", "").trim().toUpperCase());
					}
					else{
						dropDB(ts.get(2).replace(";", "").trim().toUpperCase());
					}
				}
				else if(ts.get(1).toUpperCase().equals("TABLE")  ){   /// needs to add check for database is null or not	
					stype = stype + "+" + "TABLE";
					
					if(!(database.tables.dropTable(ts.get(2).replace(";", "").trim().toUpperCase()))){
						System.out.println("Table doesn't exist.");
					}
				}
				else
					stype = "Please enter something good";
			}
			else
					stype = "Syntax error";
		} 
		else
			stype = "Please enter something good";
		
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
	
	/* DropDB
	 * Drop the entered database, removing the dbt file.
	 * @param filename Name of the database file to be removed.
	 */
	public void dropDB(String filename){

		// remove external database file
		File rmFile = new File(filename + ".dbt");
		if(rmFile.exists()){
			rmFile.delete();
		} else {
			System.out.println("Database doesn't exist.");
		}
	}
	
	/* Update
	 * Updates the entered table value. A condition can be added to limit where the value is set.
	 * @param tableName Holds the table name being updated.
	 * @param cs Holds the complete statement entered in the console.
	 */
	public String update(String tableName, String cs){
		String[] fields = cs.split(",");
		fields = cs.split("=");
		
		if(fields.length <=1){
			return "Syntax error: The fields are not formatted correctly.";
		}else{
			StringTokenizer st = new StringTokenizer(cs.trim());
			st.nextToken(); //Update
			st.nextToken(); //Table Name
			st.nextToken(); //Set
			boolean conditional = false;
			String attrStr = "";
			String condStr = "";
			String condName = "";
			Object condValue = new Object();
			while(st.hasMoreTokens()){
				String str = st.nextToken();
				
				if(str.equalsIgnoreCase("where")){
					conditional = true;
					break;
				}

				attrStr = attrStr + str;
			}
			if(conditional){
				while(st.hasMoreTokens()){
					condStr = condStr + st.nextToken().replace(";", "").trim();
				}
				Object[] obj = condStr.split("=");
				if(obj.length > 1){
					condName = (String)obj[0];
					condValue = obj[1];
				} else{
					return "Syntax Error: The conditional statement is not formatted correctly."; 
				}
			}
			
			String[] attributes = attrStr.replace(";", "").trim().split(",");
			ArrayList<String> attrNames = new ArrayList<String>();
			DataList attrValues = new DataList();
			
			for(int i = 0; i < attributes.length; i++){
				Object[] obj = attributes[i].split("=");
				attrNames.add((String)obj[0]);
				attrValues.add(obj[1]);
			}
			
			//Updates table
			return database.tables.getTable(tableName).updateFields(attrNames, attrValues, condName, condValue, conditional);
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
}
