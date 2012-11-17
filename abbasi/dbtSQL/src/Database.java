import java.io.*;
import java.util.*;

public class Database {
	String name;
	//Table table;
	ArrayList<Table> tableList;
	
	public Database(ArrayList<String> args){
		name = args.get(3);
	}
	
	public void dropDB(String filename){
		File rmFile = new File(filename);
		rmFile.delete();
	}
	
	
	
	public void createTable(ArrayList<String> args){
		tableList.add(new Table(args));
	}
	
	public boolean dbCheck(String dbName){
		if(dbName == name)
			return true;
		return false;
	}
}