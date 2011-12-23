import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Test {

	public static void main(String[] args) throws Exception{
		String a= "MNM90 OUT net1 VSS VSS N_12_HSL130E W=2u L=120.0n M=20.0";
		String[] b= a.split("\\s");
		String ln="";
		for(int i=0;i<b.length;i++){
			ln+=b[i]+" ";
		}
		ln+="\n";
		System.out.print(ln);
		System.out.print("hi");
		
	}

}
