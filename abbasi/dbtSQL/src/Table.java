import java.util.*;

public class Table {
	String name;
	RowList table;
	
	public Table(String command){
		// pull table name, trim whitespace before opening parenthesis
		name = command.substring(0, command.indexOf('(')).trim().toUpperCase();
		command = command.substring(name.length(), command.length()).trim();
		ArrayList<String> createList = parseCreate(command);
		table = new RowList();
		createFields(createList);
	}
	
	public ArrayList<String> parseCreate(String command){
		ArrayList<String> parsedStatement = new ArrayList<String>();
		String cmd = command;
		
		// remove semicolon & trim white space
		cmd = cmd.replace(";", "").trim();
		
		// check for matching sets of parentheses
		if(!checkParentheses(cmd))
			return null;
		
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
			if(!(temp.contentEquals("")))
				parsedStatement.add(temp);
			cmd = cmd.substring(pos, cmd.length()).trim();
			pos = 0;
				
		}	
		
		return parsedStatement;
	}
	
	public void createFields(ArrayList<String> list){
		String name;
		String type;
		DataList dlist = new DataList();
		
		if(list == null)
			return;
		
		for(int i = 0; i < list.size(); i++){
			String error = "Error in Create Table Syntax:  Field " + (i+1);
			int places = 0;
			String string = list.get(i);
			StringTokenizer tokens = new StringTokenizer(list.get(i));
			name = tokens.nextToken();
			type = string.substring(name.length() + 1).trim().toUpperCase();
			
			//check for CHAR
			if(type.contains("CHAR")){
				//error if no opening & closing parentheses
				if(!(type.contains("(") && type.contains(")"))){
					System.out.println(error);
					return;
				}
				else{
					if(type.substring(0, type.indexOf('(')).trim().contentEquals("CHAR") ||
					type.substring(0, type.indexOf('(')).trim().contentEquals("CHARACTER")){
					
						//pull number of characters & create Header object
						places = Integer.parseInt(type.substring(
							             type.indexOf('(')+1,type.indexOf(')')).trim());
						Header header = new Header("CHARACTER", name, places);
						dlist.add(header);
					}
					//mismatched data type
					else{
						System.out.println(error);
						return;
					}
				}
			}
			//check for INT
			else if(type.contains("INT")){
				//check for opening & closing parentheses
				if(type.contains("(") && type.contains(")")){
					if(type.substring(0, type.indexOf('(')).trim().contentEquals("INT") ||
						type.substring(0, type.indexOf('(')).trim().contentEquals("INTEGER")){
						places = Integer.parseInt(type.substring(
								type.indexOf('(')+1, type.indexOf(')')).trim());
						Header header = new Header("INTEGER", name, places);
						dlist.add(header);
					}
					//mismatched data type
					else{
						System.out.println(error);
						return;
					}
				}
				//no parentheses
				else if(type.contentEquals("INT") || 
					type.contentEquals("INTEGER")){
					Header header = new Header("INTEGER", name, places);
					dlist.add(header);
				}
				//mismatched data type
				else{
					System.out.println(error);
					return;
				}
			}
			//check for NUM
			else if(type.contains("NUM")){
				Header header;
				//check for opening & closing parentheses
				if(type.contains("(") && type.contains(")")){
					if(type.substring(0, type.indexOf('(')).trim().contentEquals("NUM") ||
							type.substring(0, type.indexOf('(')).trim().contentEquals("NUMBER")){
						//check for decimal digit formatting - parse integer & decimal digits
						if(type.substring(type.indexOf('(')+1, type.indexOf(')')).contains(",")){
							places = Integer.parseInt(type.substring(
									type.indexOf('(')+1, type.indexOf(',')).trim());
							int dec = Integer.parseInt(type.substring(
									type.indexOf(',')+1, type.indexOf(')')).trim());
							header = new Header("NUMBER", name, places, dec);
						}
						//no comma - parse integer digits
						else{
							places = Integer.parseInt(type.substring(type.indexOf('('),
																	 type.indexOf(')')).trim());
							header = new Header("NUMBER", name, places);
						}
						//add Header to DataList
						dlist.add(header);
					}
					//mismatched data type
					else{
						System.out.println(error);
						return;
					}
				}
				//no argument for # digits
				else if(type.contentEquals("NUM") || type.contentEquals("NUMBER")){
					header = new Header("NUMBER", name, places);
				}
				//mismatched data type
				else{
					System.out.println(error);
					return;
				}
			}
			//check for DATE
			else if(type.contentEquals("DATE")){
				Header header = new Header("DATE", name, 0);
				dlist.add(header);
			}
			//unknown data type
			else{
				System.out.println(error);
				return;
			}
			
		} //end for loop (all fields added to header row)
		
		table.add(dlist);
	}
	
	public void insert(String cmd){
		String literalError;
		ArrayList<String> fields = null;
		ArrayList<String> literals = new ArrayList<String>();
		DataList dlist = new DataList();
		StringTokenizer st;
		Header h;
		int index;
		
		if(!checkParentheses(cmd))
			return;
		
		//check for (field[, field]...)
		if(cmd.substring(0, cmd.toUpperCase().indexOf("VALUES")).contains("(")
				&& cmd.substring(0, cmd.toUpperCase().indexOf("VALUES")).contains(")")){
			String f = cmd.substring(cmd.indexOf("(") + 1, cmd.indexOf(")"));
			st = new StringTokenizer(f, ",");
			fields = new ArrayList<String>();
			while(st.hasMoreTokens())
				fields.add(st.nextToken().trim());
			cmd = cmd.substring(cmd.indexOf(")") + 1);
		}
		
		//put literals into ArrayList
		String l = cmd.substring(cmd.indexOf("(") + 1, cmd.lastIndexOf(")"));
		st = new StringTokenizer(l, ",");
		while(st.hasMoreTokens())
			literals.add(st.nextToken().trim());
		
		//check number of literals
		if(literals.size() != table.getRow(0).getSize()){
			System.out.println("Error inserting tuple:  # literals != # attributes");
			return;
		}
		
		//if (field[, field]...) exists
		if(fields != null){
			//check number of fields
			if(fields.size() != table.getRow(0).getSize()){
				System.out.println("Error inserting tuple:  # fields != # attributes");
				return;
			}
			//check number of fields against number of literals
			if(literals.size() != fields.size()){
				System.out.println("Syntax error:  # fields != # literals");
				return;
			}
		}
		
		//insert as many Objects into dlist as there are literals
		for(int i = 0; i < literals.size(); i++)
			dlist.add(new Object());
		
		for(int i = 0; i < literals.size(); i++){
			String fieldError = "Syntax error:  Field " + (i+1);
			literalError = "Syntax error:  Literal " + (i+1);
			
			//if (field[, field]...) exists
			if(fields != null){
				//search for field name in the attributes
				if((index = table.getRow(0).getFieldIndex(fields.get(i))) == -1){
					System.out.println(fieldError);
					return;
				}
			}
			//(field[, field]...) not present; index literals in order
			else
				index = i;
			
			h = (Header)table.getRow(0).getData(index);
			l = literals.get(i);
				
			//check for char() type by start & end quotes
			if(l.startsWith("\"") && l.endsWith("\"")){
				l = l.substring(1, l.length() - 1);
				if(!(h.getType().equalsIgnoreCase("character"))){
					System.out.println(fieldError);
					return;
				}
				//check length of string against assigned places
				if(l.length() > h.getPlaces()){
					System.out.println("Too many characters in literal " + (i+1));
					return;
				}
				dlist.remove(index);
				dlist.add(index, new CharType(h.getPlaces(), l));
			}
			//check for number type in field
			else if(h.getType().equalsIgnoreCase("integer")){
				//verify that literal is integer
				if(!(isInteger(l))){
					System.out.println(literalError);
					return;
				}
				//if places != 0, make sure integer isn't too long
				if(h.getPlaces() > 0){
					if(!(l.length() > h.getPlaces())){
						dlist.remove(index);
						dlist.add(index, new IntType(h.getPlaces(),l));
					}
					else{
						System.out.println("Too many digits in literal " + (i+1));
						return;
					}
				}
				else{  //else places == 0, add literal
					dlist.remove(index);
					dlist.add(index, new IntType(l));
				}
			}
			//check for number type in field
			else if(h.getType().equalsIgnoreCase("number")){
				//verify that literal is double
				if(!(isDouble(l))){
					System.out.println(literalError);
					return;
				}
				//if places != 0
				if(h.getPlaces() > 0){
					//check for decimal
					if(l.contains(".")){
						//verify length of integer part
						if(!(l.substring(0,l.indexOf(".")).length()> (h.getPlaces() - h.getDec()))
								&& h.getDec() > 0){
							//verify length of decimal part
							if(!(l.substring(l.indexOf(".")+1).length() > h.getDec())){
								dlist.remove(index);
								dlist.add(index, new Number(h.getPlaces(), h.getDec(), l));
								continue;
							}
						}					
					}
					//no decimal part, check length of integer
					else if(!(l.length() > h.getPlaces())){
						dlist.remove(index);
						dlist.add(index, new Number(h.getPlaces(), l));
						continue;
					}
				}
				//no length specifiers; add to row
				else{
					dlist.remove(index);
					dlist.add(index, new Number(l));
					continue;
				}
				
				//error has occurred in formatting
				System.out.println(literalError);
				return;
			}
			//check for date type
			else if(h.getType().equalsIgnoreCase("date")){
				//check for both slashes in date
				if(l.contains("/")){
					if(l.substring(l.indexOf("/")).contains("/")){
						dlist.remove(index);
						dlist.add(index, new DateType(l));
					}
				}
				//date format error
				else{
					System.out.println(literalError);
					return;
				}
			}
			else{
				System.out.println("An unknown error has occurred in INSERT.  Please try again.\n");
				return;
			}	
		}
		
		table.add(dlist);
	}
	
	public void print(){
		//print table name
		System.out.println(name + ": Table");
		
		//print attributes
		for(int i = 0; i < table.getRow(0).getSize(); i++){
			System.out.print(table.getRow(0).getData(i));
		}
		System.out.println("\n----------------------------------------------");
		if(table.getSize() > 0){
			for(int i = 1; i < table.getSize(); i++){
				for(int j = 0; j < table.getRow(i).getSize(); j++){
					System.out.print(table.getRow(i).getData(j) + "   ");
				}
				System.out.println();
			
			}
		}
		for(int p = 0; p < 2; p++)
			System.out.println();
	}
	
	
	
	
	public void deleteallrowsForthetable(){
		
		if(table.getSize() == 1){
			//do nothing
			System.out.println("no rows to delete");
		} else {
			for(int i = (table.getSize() - 1); i > 1; i--){
				
				table.deleteRow(i);
				//System.out.print(table.getRow(0).getData(i) + "\t");
				
				
			}
			System.out.println("deleted all rows from table");
		}
		
		
	}
	
	public void deleterowswhere(String conditionField, String fieldValue){
		String temprowfield;
		int indexofcondfield = -1;
		//System.out.println("table row size" + table.getRow(0).getSize() + "conditionField " + conditionField + "field value" + fieldValue);
		
		for(int j = 0; j < table.getRow(0).getSize(); j++ ) {
			//System.out.println("conditionField " + conditionField + "  " + table.getRow(0).getData(j)); 
			if(conditionField.equals(table.getRow(0).getData(j).toString().toUpperCase())){
				//System.out.println("conditionField " + conditionField + "  " + table.getRow(0).getData(j)); 
		indexofcondfield = j;
			}
		}
		System.out.println(indexofcondfield);
		
		if(indexofcondfield == -1){
			System.out.println("condition field is not valid or does not exist");
		} else {
for(int i = (table.getSize() - 1); i > 1; i--){
	
	if(table.getRow(i).getData(indexofcondfield).toString().equals(fieldValue)){
		table.deleteRow(i);
		System.out.println("Row " + (i+1) + "got deleted");
	}
				
				
				//System.out.print(table.getRow(0).getData(i) + "\t");
			}
		}
		//for(int j = 0; j < )
	//	table.getRow(0).getData(index)
		
		
		if(table.getSize() == 1){
			//do nothing
		} else {
			for(int i = table.getSize(); i > 1; i--){
				
				table.deleteRow(i);
				//System.out.print(table.getRow(0).getData(i) + "\t");
			}
		}
		
		
	}

	public boolean checkParentheses(String cmd){
		int paren = 0;
		for(int i = 0; i < cmd.length(); i++){
			if(Character.valueOf(cmd.charAt(i)) == Character.valueOf('('))
				paren += 1;
			if(Character.valueOf(cmd.charAt(i)) == Character.valueOf(')'))
				paren -= 1;
		}
		if(paren != 0){
			System.out.println("Syntax error in command:  mismatched parentheses");
			return false;
		}
		return true;
	}
	
	public boolean isInteger(String input){
		try{
			Integer.parseInt(input);
			return true;
		} catch(Exception e){
			return false;
		}
	}
	
	public boolean isDouble(String input){
		try{
			Double.parseDouble(input);
			return true;
		} catch(Exception e){
			return false;
		}
	}
}