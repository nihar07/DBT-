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
	
	public int getSize(){
		return entries.size();
	}
	
	public int getFieldIndex(String fieldName){
		int index;
		for(index = 0; index < entries.size(); index++){
			Object o = new Header("d", "d", 0);
			if(entries.get(index).getClass() == o.getClass()){
				Header h = (Header)entries.get(index);
				if(h.getName().equalsIgnoreCase(fieldName))
					return index;
			}			
		}
		return -1;		
	}
}
