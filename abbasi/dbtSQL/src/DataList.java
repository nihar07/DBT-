import java.util.ArrayList;

public class DataList {
	private ArrayList<Object> entries;
	
	public DataList(){
		entries = new ArrayList<Object>();
	}
	
	public DataList(int size){
		entries = new ArrayList<Object>(size);
	}
	
	public void add(Object o){
		entries.add(o);
	}
	
	public void add(int index, Object o){
		entries.add(index, o);
	}
	
	public void remove(int index){
		entries.remove(index);
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
			Object o = new Header("d", "d", 0, false);
			if(entries.get(index).getClass() == o.getClass()){
				Header h = (Header)entries.get(index);
				if(h.getName().equalsIgnoreCase(fieldName))
					return index;
			}			
		}
		return -1;		
	}
	
	public void setData(int index, Object data){
		entries.set(index, data);
	}
}
