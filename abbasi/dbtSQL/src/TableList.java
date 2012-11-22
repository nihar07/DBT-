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
		for(index = 0; index < tables.size(); index++){
			if(tables.get(index).name.equalsIgnoreCase(tableName)){
				break;
			}
		}
		return tables.get(index);
	}
}