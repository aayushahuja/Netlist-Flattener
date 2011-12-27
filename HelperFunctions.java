import java.io.RandomAccessFile;
import java.util.ArrayList;

import types.Line;
import types.SubCircuitNode;


public class HelperFunctions {
	
	static RandomAccessFile raf_;
	static Long offset2;
	static Long currentOffset;
	public static Line singleLine() throws Exception{
		String condensedline="";
		String expandedLine="";
		Line line_=new Line();
		String line="";
		line = raf_.readLine();
		//System.out.println(line);
		if(line==null){
			line_=null;
		}
		else{
			condensedline=line;
			expandedLine=line;
			offset2 = raf_.getFilePointer();
			line = raf_.readLine();
			while(line!=null && line.length()!=0 && line.charAt(0)=='+'){
				condensedline+=line.substring(2);
				expandedLine += "\n" + line;
				offset2 = raf_.getFilePointer();
				line = raf_.readLine();
			}
			//System.out.println(expandedLine);
			raf_.seek(offset2);
			line_.condensedLine=condensedline;
			line_.expandedLine=expandedLine;
		}
		
		return (line_);
	}

	public ArrayList<String> tokenToList(String[] tokens,int start,int end){
		ArrayList<String> list_ = new ArrayList<String>();
		for(int i=start;i<=end;i++){
			list_.add(tokens[i]);
			}
		return list_;	
		}

	public ArrayList<String> tokenToList(String[] tokens,int start,int end,ArrayList<String> oldList){
		for(int i=start;i<=end;i++){
			oldList.add(tokens[i]);
			}
		return oldList;	
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
		currentOffset = Main.out.getFilePointer();
		String para="";
		Line line;
		raf_=Main.out;
		raf_.seek(list.get(index).startLine);
		line = singleLine();
		line = singleLine();
		//System.out.println("1 "+line.condensedLine);
		String[] tokens = line.condensedLine.split("\\s");
		
		while(!tokens[0].equals(".ENDS")){
			//System.out.println(line.condensedLine);
			if(tokens[0].charAt(0)=='*'){
				line = singleLine();
				tokens = line.condensedLine.split("\\s");
			}
			else if(tokens[0].charAt(0)=='X' || tokens[0].charAt(0)=='x'){
				para+=line.expandedLine+"\n";
				line = singleLine();
				tokens = line.condensedLine.split("\\s");
			}
			else{
				int noOfNodes=this.giveNumberOfNodes(tokens[0].charAt(0));
				//MMMGI7__4 VSS QB1 Q VSS N_12_HSL130E W=8.8E-07 L=1.2E-07 M=1.0
				for(int i=1;i<(1+noOfNodes);i++){
					if(list.get(index).arguments.contains(tokens[i])){
						int index_=list.get(index).arguments.indexOf(tokens[i]);
						tokens[i]=new String(args.get(index_));
					}
				}
				tokens[0]=new String(tokens[0]+'_'+list.get(index).name+Integer.toString(list.get(index).used));
				String ln="";
				for(int i=0;i<tokens.length;i++){
					ln+=tokens[i]+" ";
				}
				ln+="\n";
				para+=ln;
				line = singleLine();
				tokens = line.condensedLine.split("\\s");
			}
		}
		if(para.length()!=0){
			para=new String(para.substring(0, para.length()-1));
		}
		Main.out.seek(currentOffset);
		return para;
	}
}
