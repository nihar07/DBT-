import java.util.*;

public class Table {
	String name;
	ArrayList<ArrayList<Object>> rows;
	
	public Table(String command){
		name = command.substring(0, command.indexOf('('));
		command = command.substring(name.length(), command.length()).trim();
		ArrayList<String> cmdList = createCmd(command);
		
	}
	
	public ArrayList<String> createCmd(String command){
		ArrayList<String> parsedStatement = new ArrayList<String>();
		String cmd = command;
		
		cmd = cmd.replace(";", "");
		if(!(cmd.charAt(0) == '(' && cmd.charAt(cmd.length()-1) == ')')){
			System.out.println("Syntax error in Create Table command");
			return null;
		}
		cmd = cmd.substring(1, cmd.length());
		
		String temp;
		int pos = 0;
		while(pos < cmd.length()){
			if(cmd.substring(pos, cmd.lastIndexOf(')')).contains(",")){
				temp = cmd.substring(pos, cmd.indexOf(','));
				//pos = pos + temp.length()+1;
			}
			else
				temp = cmd.substring(pos, cmd.lastIndexOf(')'));
			pos = pos + temp.length()+1;
			if(temp.contains("(") && !temp.contains(")")){
				temp = temp + ",";
				if(cmd.substring(pos, cmd.length()).contains(",")){
					int i = pos;
					while(cmd.charAt(i) != ',')
						i++;
					temp = temp + cmd.substring(pos, i);
					pos = i + 1;
				}
				else
					temp = temp + cmd.substring(pos, cmd.lastIndexOf(')'));	
			}
			cmd = cmd.substring(pos, cmd.length()).trim();
			pos = 0;
				
		}
		//while(i < cmd.length()){
			
		//}
		
		//StringTokenizer st = new StringTokenizer(cmd, ",");
		
		//if(cmd.toUpperCase().contains("VALUES")){
		//	;
		//}
		
		//while(st.hasMoreTokens()){
		//	String temp = st.nextToken();
		//	if(temp.charAt(0) == '(')
		//		temp.replaceFirst("(", "");
			
		//	
		//	parsedStatement.add(temp);	
		//}
		
		
		
		return parsedStatement;
	}
}