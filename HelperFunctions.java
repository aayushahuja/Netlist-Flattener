import java.io.BufferedReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import types.SubCircuitNode;


public class HelperFunctions {
	
	RandomAccessFile raf_;	
	public HelperFunctions() throws Exception{
		raf_= new RandomAccessFile(Main.fileName, "r");
	}
	
	public ArrayList<String> findArguments(String[] a){
		ArrayList<String> args = new ArrayList<String>();
		int i=2;
		while(i<a.length){
			args.add(a[i]);
			i++;
		}
		return args;
	}
	
	public ArrayList<String> passedArguments(String[] args){
		ArrayList<String> args_ = new ArrayList<String>();
		for(int i=1;i<=args.length-3;i++){
			args_.add(args[i]);
		}
		
		return args_;
	}
	
	public Integer isSubCircuitDefined(String name1,ArrayList<SubCircuitNode> list){
		boolean found=false;
		int index=0;
		Integer indexReturn;
		for(int i=0;i<list.size();i++){
			if(list.get(i).name.equals(name1)){
				found=true;
				index=i;
				break;
			}
		}
		if(found==true){
			indexReturn=index;
		}
		else{
			indexReturn=(Integer) null;
		}
		return indexReturn; 
	}
	
	public int giveNumberOfNodes(char a){
		int a_;
		 switch (a) {
         case 'M':  a_=4;
         			break;
         default: a_ = 2;
                  break;
		 }         
		return a_;
	}
	//prob with raf_ also,need to make a local instance or a stack,push state in the stack and pop from there
	//public BufferedReader goToLine(int lineNumber,BufferedReader in){};
	public String giveMePara(ArrayList<SubCircuitNode> list,int index,ArrayList<String> args) throws Exception{
		//list.get(index).used++;
		String para="";
		//goto that line
		raf_.seek(list.get(index).startLine);
		String line = raf_.readLine();
		line=raf_.readLine();
		String[] tokens = line.split("\\s");
		//check for length=0 lines also
		while(!(tokens[0].equals(".ENDS"))){
			if(tokens[0].charAt(0)=='*'  ){
				//para+=line;
				line = raf_.readLine();
				tokens = line.split("\\s");
			}
			//if it is sth else say an element then have to get its elements if any of these arguments is same as a 
			//sth for name also hav to do,this shouldnt happen in my case
			else if(tokens[0].charAt(0)=='X' || tokens[0].charAt(0)=='x'){
				//this is the main thing
				/*String calledSubCircuit=tokens[tokens.length-1];
				Integer foundIndex = this.isSubCircuitDefined(calledSubCircuit,list);
				//got the line number starting etc of all
				//if(foundIndex==null){yetToFlatten.add(lineNumber);}//no this has to be done cannot be done this way
				para += this.giveMePara(list,foundIndex,this.passedArguments(tokens));*/
				line = raf_.readLine();
				tokens = line.split("\\s");
			}
			//element case
			else{
				//find arguments,ie node comparison have to do
				//map arguments
				//return line
				//name also have to do so lots of stuff incrementally have to do
				//ek element ke fixed number of nodes to hongai hi
				//so where are we we are inside called function with having the calling function nodes
				//have to change element name
				
				int noOfNodes=this.giveNumberOfNodes(tokens[0].charAt(0));
				
				for(int i=1;i<(1+noOfNodes);i++){
					if(list.get(index).arguments.contains(tokens[i])){
						int index_=list.get(index).arguments.indexOf(tokens[i]);
						tokens[i]=new String(args.get(index_));
					}
				}
				tokens[0]=new String(tokens[0]+Integer.toString(list.get(index).used));
				//list.get(index).used++;
				String ln="";
				for(int i=0;i<tokens.length;i++){
					ln+=tokens[i]+" ";
				}
				ln+="\n";
				para+=ln;
				line = raf_.readLine();
				tokens = line.split("\\s");
			}
			
		}
		if(para.length()!=0){
			para=new String(para.substring(0, para.length()-1));
		}
		return para;
	}
}
