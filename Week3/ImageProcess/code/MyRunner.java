import imagereader.Runner;  
  
public class MyRunner {  
  
    public static void main(String args[]){  
          
        MyImageIO myImageIO = new MyImageIO();  
          
        MyImageProcessor myImageProcessor = new MyImageProcessor();  
          
        Runner.run(myImageIO, myImageProcessor);  
    }  
}  
