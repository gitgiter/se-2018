package junit_test;

import static org.junit.Assert.*;
import junit_test.helloworld;
import org.junit.*;
import java.io.*;

public class helloworldTest {
	
	ByteArrayOutputStream mybyte = null;
	PrintStream console = null;
	
	// something need to be prepared before run test
	@Before
	public void setUp()
	{
		// to get the output on the console by stream
		mybyte = new ByteArrayOutputStream();
		console = System.out;
		System.setOut(new PrintStream(mybyte));
	}
	
	// test case
	@Test
	public void test() {
		// pass an empty argument
		String[] str = new String[1];
		// run static method
		helloworld.main(str);
		
		// compare the expected output and the console output
		assertEquals("Hello World!\n", mybyte.toString());
	}
}
