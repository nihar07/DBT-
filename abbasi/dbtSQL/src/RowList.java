import java.util.ArrayList;

public class RowList {
	private ArrayList<DataList> rows;
	
	public RowList(){
		rows = new ArrayList<DataList>();
	}
	
	public void add(DataList d){
		rows.add(d);
	}
	
	public DataList getRow(int index){
		return rows.get(index);
	}
	
	public DataList deleteRow(int index){
		return rows.remove(index);
	}
	
	public int getSize(){
		return rows.size();
	}
}
