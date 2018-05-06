import imagereader.Runner;  
  
public class MyRunner {  
  
    public static void main(String args[]){  
          
        ImplementImageIO myImageIO = new ImplementImageIO();  
          
        ImplementImageProcessor myImageProcessor = new ImplementImageProcessor();  
          
        Runner.run(myImageIO, myImageProcessor);  
    }  
}  
