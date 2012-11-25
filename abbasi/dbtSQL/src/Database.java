import java.io.*;
import java.util.*;

public class Database {
	String name;
	TableList tables;
	
	public Database(ArrayList<String> args){
		name = args.get(2).replace(";", "").trim().toUpperCase();
		tables = new TableList();
	}
	
	// create a new table in the database
	public void createTable(String command){
		tables.add(new Table(command));
	}
	
	
	
	public void select(String table){
		Table t = tables.getTable(table);
		if(t != null)
			t.print();
		else
			System.out.println("Could not find table " + table);
	}
	
	public void insert(String cmd){
		/*StringTokenizer st = new StringTokenizer(cmd);
		st.nextToken();
		if(!st.nextToken().equalsIgnoreCase("VALUES")){
			System.out.println("Syntax error in insert command");
			return;
		}*/
		
		String tableName = "";
		if(cmd.substring(0, cmd.toUpperCase().indexOf("VALUES")).contains("("))
			tableName = cmd.substring(0, cmd.indexOf('(')).trim();
		else
			tableName = cmd.substring(0, cmd.toUpperCase().indexOf("VALUES")).trim();
		
		Table t = tables.getTable(tableName);
		if(t == null){
			System.out.println("Insert failed:  Table " + tableName + " not found");
			return;
		}
		t.insert(cmd.substring(cmd.indexOf("(")));
	}
	
	//delete rows without where
	public void deleteRowst(String deleterows){
		Table t = tables.getTable(deleterows);
		if(t != null)
			t.deleteallrowsForthetable();
		else
			System.out.println("Could not find table " + deleterows);
	}
	
	
	//delete rows with where
		public void deleteRowstwhere(String deleterows, String condtionField, String fieldVa){
			Table t = tables.getTable(deleterows);
			if(t != null)
				t.deleterowswhere(condtionField,fieldVa);
			else
				System.out.println("Could not find table " + deleterows);
		}
	
	
	public boolean dbCheck(String dbName){
		return (dbName == name);
	}
	
	public String getName(){
		return name;
	}
	
	public Table getTable(String tableName){
		return tables.getTable(tableName);
	}
}