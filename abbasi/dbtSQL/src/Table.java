import java.util.*;

public class Table {
	String name;
	ArrayList<ArrayList<Object>> rows;
	
	public Table(String command){
		// pull table name, trim whitespace before opening parenthesis
		name = command.substring(0, command.indexOf('('));
		command = command.substring(name.length(), command.length()).trim();
		ArrayList<String> cmdList = createCmd(command);
		
	}
	
	public ArrayList<String> createCmd(String command){
		ArrayList<String> parsedStatement = new ArrayList<String>();
		String cmd = command;
		
		// remove semicolon & trim white space
		cmd = cmd.replace(";", "").trim();
		
		// check for opening & closing parentheses
		if(!(cmd.charAt(0) == '(' && cmd.charAt(cmd.length()-1) == ')')){
			System.out.println("Syntax error in Create Table command");
			return null;
		}
		
		// remove leading parenthesis
		cmd = cmd.substring(1, cmd.length());
		
		String temp;
		int pos = 0;
		
		// parse data types & labels
		while(pos < cmd.length()){
			
			// if comma exists, pull string before comma index
			if(cmd.substring(pos, cmd.lastIndexOf(')')).contains(","))
				temp = cmd.substring(pos, cmd.indexOf(','));
			// else pull rest of string (last term)
			else
				temp = cmd.substring(pos, cmd.lastIndexOf(')'));
			
			// increase pos to index after comma
			pos = pos + temp.length()+1;
			
			// if string contains unmatched parenthesis
			if(temp.contains("(") && !temp.contains(")")){
				temp = temp + ","; //insert skipped comma
				
				// if another comma exists, append substring
				if(cmd.substring(pos, cmd.length()).contains(",")){
					int i = pos;
					while(cmd.charAt(i) != ',')
						i++;
					temp = temp + cmd.substring(pos, i);
					pos = i + 1;
				}
				// else (no comma) pull rest of string (final term)
				else{
					temp = temp + cmd.substring(pos, cmd.lastIndexOf(')'));
					pos = cmd.lastIndexOf(')');
				}
			}
			
			// trim temp string, set new trimmed cmd string, reset pos to 0
			temp = temp.trim();
			cmd = cmd.substring(pos, cmd.length()).trim();
			pos = 0;
				
		}	
		
		return parsedStatement;
	}
}