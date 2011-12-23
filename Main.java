import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.print.DocFlavor.INPUT_STREAM;

import types.SubCircuitNode;


public class Main {

	public static String fileName = "C:/Users/comp/Desktop/KARNA.src.net";

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
	//files
		
	//BufferedReader in = new BufferedReader(new FileReader("C:/Users/comp/Desktop/KARNA.src.net"));
	RandomAccessFile raf = new RandomAccessFile(fileName, "r"); 
	BufferedWriter out = new BufferedWriter(new FileWriter("C:/Users/comp/Desktop/out.txt"));
	BufferedWriter temp = new BufferedWriter(new FileWriter("C:/Users/comp/Desktop/temp.txt"));
	
	//Objects
	HelperFunctions help = new HelperFunctions();
		
	
	//Variables used
	//yetToFlatten for cases when not defined before using
	ArrayList<SubCircuitNode> subCircuits = new ArrayList<SubCircuitNode>();
	ArrayList<Integer> yetToFlatten = new ArrayList<Integer>();
	String[] tokens ;
	int lineNumber=0;
	
	//marking
	int temp2=0;
	Long offset=raf.getFilePointer();
	String line=raf.readLine();
	while(line!=null){
		//processing one line,lineNumber maintained
		if((line.length()!=0) && (line.charAt(0)!='*')){
			tokens = line.split("\\s") ;
			if(tokens[0].equals(".SUBCKT")){
				subCircuits.add(new SubCircuitNode(offset,tokens[1],help.findArguments(tokens)));
				int index_=subCircuits.size()-1;
				//if want find the ends from here only
				//ok finding
				/*for(int j=lineNumber+1;;j++){
					String temp = in.
				}*/
				offset = raf.getFilePointer();
				out.append(line);
				out.newLine();
				lineNumber++;
				line=raf.readLine();
				String[] tokens_;
				while(line.charAt(0)=='x'){
					tokens_=line.split("\\s");
					for(int j=1;j<tokens_.length;j++){
						subCircuits.get(index_).arguments.add(tokens_[j]);
					}
					offset = raf.getFilePointer();
					out.append(line);
					out.newLine();
					lineNumber++;
					line=raf.readLine();
					
				}
				
			}
			else if(tokens[0].charAt(0)=='X' || tokens[0].charAt(0)=='x'){
				
				//this is the main thing
				String calledSubCircuit=tokens[tokens.length-1];
				Integer foundIndex = help.isSubCircuitDefined(calledSubCircuit,subCircuits);
				//got the line number starting etc of all
				if(foundIndex==null){//yetToFlatten.add(lineNumber);
					out.append(line);
					out.newLine();
					lineNumber++;
					line=raf.readLine();
				}
				else{
					//System.out.println(subCircuits.size()+"  "+foundIndex+" "+line );
					subCircuits.get(foundIndex).used++;
					ArrayList<String> passedArguments_=new ArrayList<String>();
					passedArguments_=help.passedArguments(tokens);
					offset = raf.getFilePointer();
					//out.append(line);
					//out.newLine();
					lineNumber++;
					line=raf.readLine();
					String[] tokens_;
					while(line!=null && line.charAt(0)=='+'){
						tokens_=line.split("\\s");
						for(int j=1;j<tokens_.length;j++){
							passedArguments_.add(tokens_[j]);
						}
						offset = raf.getFilePointer();
						//out.append(line);
						//out.newLine();
						lineNumber++;
						line=raf.readLine();
					}
					
					String para = help.giveMePara(subCircuits,foundIndex,passedArguments_);
					//	System.out.println(temp2++);
					offset = raf.getFilePointer();
					out.append(para);
					out.newLine();
					//lineNumber++;
					//line=raf.readLine();
				}
				
			}
			else{
				out.append(line);
				out.newLine();
				lineNumber++;
				line=raf.readLine();
			}
				
			
			
		}
		//ie these lines perhaps will be there no hav to see now handling the cases only
		else{
			offset = raf.getFilePointer();
			out.append(line);
			out.newLine();
			lineNumber++;
			line=raf.readLine();
		}
		
	}
	raf.close();
	out.close();
	temp.close();
		
}
}

