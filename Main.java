import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;

import types.Line;
import types.SubCircuitNode;


public class Main {

	public static String fileName = "./KARNA.src.net";
	public static String outputFileName = "./out.src.net";
	public static RandomAccessFile out;
	static RandomAccessFile raf;
	static RandomAccessFile rafPlusOne;
	static Long offset;
	static int lineNumber=0;

	public static Line singleLine() throws Exception{
		String condensedline="";
		String expandedLine="";
		Line line_=new Line();
		String line="";
		String line2="";
		line = raf.readLine();
		if(line==null){
			line_=null;
		}
		else{
			lineNumber++;
			condensedline=line;
			expandedLine=line;
			raf.getFilePointer();
			rafPlusOne.seek(raf.getFilePointer());
			line2 = rafPlusOne.readLine();
			while(line2!=null && line2.length()!=0 && line2.charAt(0)=='+'){
				condensedline+=line2.substring(2);
				expandedLine += "\n" + line2;
				line = raf.readLine();
				//offset = raf.getFilePointer();
				rafPlusOne.seek(raf.getFilePointer());
				line2 = rafPlusOne.readLine();
				lineNumber++;
			}
			line_.condensedLine=condensedline;
			line_.expandedLine=expandedLine;
		}
		
		return (line_);
	}
	
	public static void main(String[] args) throws Exception{
		
	raf = new RandomAccessFile(args[0], "r"); 
	rafPlusOne = new RandomAccessFile(args[0], "r");
	out = new RandomAccessFile(outputFileName, "rw");
	BufferedWriter temp = new BufferedWriter(new FileWriter("./temp.txt"));
	
	//Objects
	HelperFunctions help = new HelperFunctions();
	ArrayList<SubCircuitNode> subCircuits = new ArrayList<SubCircuitNode>();
	String[] tokens ;
	lineNumber=0;
	offset = out.getFilePointer();
	Line line = singleLine();
	String temp2="";
	while(line!=null){
		if((line.expandedLine.length()!=0) && (line.expandedLine.charAt(0)!='*')){
			tokens = line.condensedLine.split("\\s") ;
			if(tokens[0].equals(".SUBCKT")){
				subCircuits.add(new SubCircuitNode(offset,tokens[1],help.tokenToList(tokens, 2, tokens.length-1)));
				temp2 = line.expandedLine+"\n";
				out.write(temp2.getBytes());
				offset = out.getFilePointer();
				line = singleLine();
			}
			else if(tokens[0].charAt(0)=='X' || tokens[0].charAt(0)=='x'){
				//this is the main thing
				String calledSubCircuit=tokens[tokens.length-1];
				Integer foundIndex = help.isSubCircuitDefined(calledSubCircuit,subCircuits);
				if(foundIndex==null){
					temp2 = line.expandedLine+"\n";
					out.write(temp2.getBytes());
					offset = out.getFilePointer();
					line=singleLine();
				}
				else{
					subCircuits.get(foundIndex).used++;
					ArrayList<String> passedArguments_=new ArrayList<String>();
					passedArguments_=help.tokenToList(tokens,1,tokens.length-3);
					String para = help.giveMePara(subCircuits,foundIndex,passedArguments_);
					temp2 = para+"\n";
					out.write(temp2.getBytes());
					offset = out.getFilePointer();
					line = singleLine();
				}
				
			}
			else{
				temp2 = line.expandedLine+"\n";
				out.write(temp2.getBytes());
				offset = out.getFilePointer();
				line=singleLine();
			}
		}
		else{
			temp2 = line.expandedLine+"\n";
			out.write(temp2.getBytes());
			offset = out.getFilePointer();
			line = singleLine();
					
		}
	}
	raf.close();
	rafPlusOne.close();
	out.close();
	temp.close();
		
}
}

