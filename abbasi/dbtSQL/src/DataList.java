import java.util.ArrayList;

public class DataList {
	private ArrayList<Object> entries;
	
	public DataList(){
		entries = new ArrayList<Object>();
	}
	
	public void add(Object o){
		entries.add(o);
	}
	
	public Object getData(int index){
		return entries.get(index);
	}
}
