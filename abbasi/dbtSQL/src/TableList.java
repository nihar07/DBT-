import java.util.ArrayList;

public class TableList {

	private ArrayList<Table> tables;
	
	public TableList(){
		tables = new ArrayList<Table>();
	}
	
	public void add(Table t){
		tables.add(t);
	}
	
	public Table getTable(String tableName){
		int index;
		boolean containsTable = false;
		for(index = 0; index < tables.size(); index++){
			if(tables.get(index).name.equalsIgnoreCase(tableName)){
				containsTable = true;
				break;
			}
		}
		if(containsTable == false){
			return null;
		}
		return tables.get(index);
	}
	
	public int getTableIndex(String tableName){
		int index;
		for(index = 0; index < tables.size(); index++){
			if(tables.get(index).name.equalsIgnoreCase(tableName)){
				break;
			}
		}
		return index;
	}
	
	public boolean dropTable(String tableName){
		if(getTable(tableName) != null){
			tables.remove(getTableIndex(tableName));
			return true;
		}
		
		return false;
	}
}