import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapterCache;
import org.junit.*;
import java.io.*;

import java.awt.*;
import java.awt.image.*;

import imagereader.IImageIO; 
import imagereader.IImageProcessor;  

import javax.imageio.ImageIO;

public class MyImageIOTest {

    @Before
    public void setUp() throws Exception {
    }

    public void testOne(String which, String color) throws IOException {
        String sourcePath = "/home/administrator/Desktop/se-2018/Week3/ImageProcess/bmptest/" + which + ".bmp";
        MyImageIO myImage = new MyImageIO();
        Image image= myImage.myRead(sourcePath);

        MyImageProcessor processor = new MyImageProcessor();         
        Image myProcessedImage = null;
        switch (color)
        {
            case "blue":
                myProcessedImage = processor.showChanelB(image);
                break;
            case "green":
                myProcessedImage = processor.showChanelG(image);
                break;
            case "red":
                myProcessedImage = processor.showChanelR(image);
                break;
            case "gray":
                myProcessedImage = processor.showGray(image);
                break;
        }

        String goalPath = "/home/administrator/Desktop/se-2018/Week3/ImageProcess/bmptest/goal/" + which + "_" + color + "_goal.bmp";
        FileInputStream goalFile = new FileInputStream(goalPath);
        BufferedImage goalImage = ImageIO.read(goalFile);

        int w = myProcessedImage.getWidth(null);
        int d = myProcessedImage.getHeight(null);
        BufferedImage myBufferedImage = new BufferedImage(w, d, BufferedImage.TYPE_3BYTE_BGR);
        myBufferedImage.getGraphics().drawImage(myProcessedImage, 0, 0, w, d, null);   
        
        for (int i = 0; i < goalImage.getWidth(null); i++) {            
            for (int j = 0; j < goalImage.getHeight(null); j++) {
                assertEquals(goalImage.getRGB(i, j), myBufferedImage.getRGB(i, j));
            }
        }

        assertEquals(myProcessedImage.getWidth(null), goalImage.getWidth(null));
        assertEquals(myProcessedImage.getHeight(null), goalImage.getHeight(null));
    }

    @Test
    public void testmyBlue1() throws IOException {
        testOne("1", "blue");
    }

    @Test
    public void testmyBlue2() throws IOException {
        testOne("2", "blue");
    }

    @Test
    public void testGreen1() throws IOException {
        testOne("1", "green");
    }

    @Test
    public void testGreen2() throws IOException {
        testOne("2", "green");
    }

    @Test
    public void testRed1() throws IOException {
        testOne("1", "red");
    }

    @Test
    public void testRed2() throws IOException {
        testOne("2", "red");
    }

    @Test
    public void testGray1() throws IOException {
        testOne("1", "gray");
    }

    @Test
    public void testGray2() throws IOException {
        testOne("1", "gray");
    }

}