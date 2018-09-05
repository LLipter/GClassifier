
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;




public class GClassifier {
	
	public static void usage() {
		System.out.println("GClassifier input_file.json");
	}
	
	
	public static void judge(String filename) {
		FileReader reader;
		try {
			reader = new FileReader(filename);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		JsonParser parser = new JsonParser();
		JsonObject json;
		try {
			json = (JsonObject) parser.parse(reader);
		} catch (JsonSyntaxException e) {
			System.out.println("invalid input : json syntax error");
			return;
		}
		
		
		JsonElement grammar = json.get("grammar");
		if(grammar == null) {
			System.out.println("invalid input : grammar is not provided");
			return;
		}

		Set<String> Vn = new HashSet<String>();
		try {
			JsonArray vn = json.get("Vn").getAsJsonArray();
			if(vn == null) {
				System.out.println("invalid input : Vn is not provided");
				return;
			}
			for (JsonElement v : vn)
				Vn.add(v.getAsString());
		} catch (IllegalStateException e) {
			System.out.println("invalid input : Vn should be a json array");
			return;
		}
		
		JsonArray production;
		try {
			production = json.get("production").getAsJsonArray();
			if(production == null) {
				System.out.println("invalid input : production is not provided");
				return;
			}
		} catch (IllegalStateException e) {
			System.out.println("invalid input : production should be a json array");
			return;
		}

		boolean is_23 = true;
		boolean is_3 = true;
		boolean is_1 = true;
		boolean is_extend = false;
		Set<String> Vt = new HashSet<String>();
		Set<String> prod = new HashSet<String>();
		for (JsonElement p : production) {
			String p_str = p.getAsString().replace(" ", ""); // remove all white spaces
			prod.add(p_str);
			for(int i=0;i<p_str.length();i++) {
				String s = p_str.substring(i,i+1);
				if(!s.equals(":") && !s.equals("=") && !s.equals("|") && !Vn.contains(s))
					Vt.add(s);
			}
			String[] str_arr = p_str.split("::=");
			if(str_arr.length != 2 || str_arr[0].length() == 0 || str_arr[1].length() == 0) {
				System.out.println("invalid input : production " + p + " syntax error");
				return;
			}
			String left_side = str_arr[0];
			String[] right_sides = str_arr[1].split("\\|");
			if(left_side.length() > 1 || !Vn.contains(left_side))
				is_23 = false;
			for(String right_side : right_sides) {
				if(right_side.length() == 0)
					continue;
				else if(right_side.length() == 1 && Vn.contains(right_side))
					is_3 = false;
				else if(right_side.length() == 1 && right_side.equals("ε"))
					is_extend = true;
				else if(right_side.length() == 2) {
					String first = right_side.substring(0,1);
					String second = right_side.substring(1,2);
					if(Vn.contains(first) && Vn.contains(second))
						is_3 = false;
					if(!Vn.contains(first) && !Vn.contains(second))
						is_3 = false;
				}else if(right_side.length() > 2)
					is_3 = false;
				
				if(right_side.length() < left_side.length())
					is_1 = false;

			}
			
			
			
			
		}
		
		
		System.out.println(filename);
		if(!is_23 && !is_1)
			System.out.println("verdict : type 0 language, PSG (Phrase Structure Grammar)");
		else if(!is_23 && is_1)
			System.out.println("verdict : type 1 language, CSG (Context Sensitive Grammar)");
		else if(is_23 && !is_3 && !is_extend)
			System.out.println("verdict : type 2 language, CFG (Context Free Grammar)");
		else if(is_23 && !is_3 && is_extend)
			System.out.println("verdict : type 2 language, extend CFG (Context Free Grammar)");
		else if(is_23 && is_3 && !is_extend)
			System.out.println("verdict : type 3 language, RG (Regular Grammar)");
		else if(is_23 && is_3 && is_extend)
			System.out.println("verdict : type 3 language, extend RG (Regular Grammar)");
		
		System.out.println(String.format("%s = (Vt, Vn, P, %s)", grammar.getAsString(), grammar.getAsString().substring(2,3)));
		System.out.println("Vt : " + Vt);
		System.out.println("Vn : " + Vn);
		System.out.println("P  : " + prod);
		System.out.println();

	}

	public static void main(String[] args) {

		if(args.length == 0) {
			usage();
			return;
		}
		
		
		for(String filename : args)
			judge(filename);
		

		
		
			
	}

}
