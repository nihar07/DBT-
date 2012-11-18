import java.io.*;
import java.util.*;

public class Database {
	String name;
	ArrayList<Table> tableList;
	
	public Database(ArrayList<String> args){
		name = args.get(2).replace(";", "").trim();	
	}
	
	// drop the current database
	public void dropDB(String filename){
		//remove external database file
		File rmFile = new File(filename);
		rmFile.delete();
		
		name = null;
	}
	
	
	// create a new table in the database
	public void createTable(ArrayList<String> args){
		tableList.add(new Table(args));
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