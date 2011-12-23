package types;

import java.util.ArrayList;

public class SubCircuitNode {
	public long startLine;
	public long endLine;
	public String name;
	public ArrayList<String> arguments;
	public int used;
	public SubCircuitNode(int start){
		startLine=start;
		used=0;
	}
	public SubCircuitNode(long start, String Name){
		startLine=start;
		name = Name;
		used=0;
	}
	public SubCircuitNode(long start, String Name, ArrayList<String> args){
		startLine=start;
		name = Name;
		arguments = args;
		used=0;
	}
	public SubCircuitNode() {
		used=0;
		// TODO Auto-generated constructor stub
	}
}
