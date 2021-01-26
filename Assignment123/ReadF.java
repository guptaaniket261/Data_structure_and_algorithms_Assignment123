import java.io.File; 
import java.util.Scanner; 
public class ReadF 
{ 
  public static void main(String[] args) throws Exception 
  { 
    // pass the path to the file as a parameter 
    File file = 
      new File("C:\\Users\\gupta\\Sem3\\COL106\\Assignment\\Assignment123\\StubsA123\\xyz.txt"); 
    File ff= new File("C:\\Users\\gupta\\Sem3\\COL106\\Assignment\\Assignment123\\StubsA123\\hugeout.txt");
    Scanner sc = new Scanner(file); 
  	Scanner sc1= new Scanner(ff);
  	String a="a",b="b";
  	int correct=0;
  	int wrong =0;
    int li=1;
    int f=0;
    while (sc.hasNextLine() && sc1.hasNextLine()){
      a=sc.nextLine();
      b=sc1.nextLine();
      if(a.equals(b))correct++; 
      else {wrong++;if(f==0){System.out.println(li);f=1;}}
      li++;
    }
      System.out.println(correct+" "+wrong);
  } 
}