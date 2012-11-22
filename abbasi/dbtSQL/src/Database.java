import java.io.*;
import java.util.*;

public class Database {
	String name;
	TableList tables;
	
	public Database(ArrayList<String> args){
		name = args.get(2).replace(";", "").trim().toUpperCase();
		tables = new TableList();
	}
	
	// drop the current database
	public void dropDB(String filename){
		// capitalize filename for consistency
		filename = filename.toUpperCase();
		
		// remove external database file
		File rmFile = new File(filename + ".dbt");
		rmFile.delete();
		
		if(filename == name)
			name = null;
	}
	
	
	// create a new table in the database
	public void createTable(String command){
		tables.add(new Table(command));
	}
	
	
	public boolean dbCheck(String dbName){
		if(dbName == name)
			return true;
		return false;
	}
	
	public String getName(){
		return name;
	}
}