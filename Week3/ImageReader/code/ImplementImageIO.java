import java.awt.Graphics;  
import java.awt.Image;  
import java.awt.Toolkit;  
import java.awt.image.BufferedImage;  
import java.awt.image.ImageProducer;  
import java.awt.image.MemoryImageSource;  
import java.awt.image.RenderedImage;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream; 
import java.io.IOException;
  
import javax.imageio.ImageIO;  
  
import imagereader.IImageIO;  
  
public class ImplementImageIO implements IImageIO{  
    private Image _img;  
      
    public int readNumFromBytes(byte[] bytes, int start, int end){
        int byteNum = end - start + 1;
        int num = 0;
        for (int i = 0; i < byteNum; i++){
            num |= (int)(bytes[start + i] & 0xff) << 8 * i;
        }
        return num;
    }

    public Image myRead(String path){  
        File file = new File(path);  
        try{  
            //read the file
            FileInputStream fileReader = new FileInputStream(file);  
              
            //store the header and the info  
            byte header[] = new byte[14];  
            byte info[] = new byte[40];  
            
            //read the header and info
            fileReader.read(header, 0, 14);  
            fileReader.read(info, 0, 40);  

            //read the file size from the header (index from 2 to 5)
            int headerSize = readNumFromBytes(header, 2, 5);  
              
            //read the image size from the info (index from 34 to 37, in info is 20~23) : bytes
            // infoSize = width * height * bytes of each pixel
            int infoSize = readNumFromBytes(info, 20, 23);  
              
            //read the width of bitmap image (index from 18 to 21, in info is 4~7) : pixels
            int width = readNumFromBytes(info, 4, 7);  
              
            //read the height of bitmap image (index from 22 to 25, in info is 8~11) : pixels
            int height = readNumFromBytes(info, 8, 11);  
          
            //read the total bits of each pixel (index from 28 to 29, in info is 14~15)
            //which specifies the deepth of the color
            //to see if it's a 1bit, 4bits, 8bits(gray), 24bits(colorful)
            int bitNum = readNumFromBytes(info, 14, 15);
              
            if (bitNum == 24){  
                  
                //get the number of empty bytes
                //infoSize / height = width * bytes of each pixel
                int numOfEmptyByte = infoSize / height - 3 * width;  

                //if the number of bytes left is four, it's not empty
                if(numOfEmptyByte == 4){  
                    numOfEmptyByte = 0;  
                }  

                //store every pixel to a array from left to right, bottom to top 
                int temp = 0;  
                  
                int pixelArray[] = new int [width * height];  
                byte bmpTotalByte[] = new byte[infoSize];  
                fileReader.read(bmpTotalByte, 0, infoSize);  

                // bottom to top
                for(int i = height - 1; i >= 0; i--){     
                    // left to right
                    for( int j = 0; j < width; j++){                          
                        //in a image file, it only save rgb
                        //but a java image, it must specify rgba
                        //so we should add an opacity to the rgb read from image file
                        //the 0xff << 24 is the opacity, defaul to max
                        pixelArray[ width * i + j ] = 0xff << 24  
                                | (bmpTotalByte[temp+2] & 0xff) << 16   
                                | (bmpTotalByte[temp+1] & 0xff) << 8   
                                | (bmpTotalByte[temp] & 0xff);  
                        //to next pixel's first byte
                        temp += 3;  
                    }  
                    // skip the empty byte
                    temp += numOfEmptyByte;  
                }  
                //create the java image
                _img = Toolkit.getDefaultToolkit().createImage((ImageProducer) new MemoryImageSource(  
                        width, height, pixelArray, 0, width));  
            }  
            fileReader.close();  
            return _img;  
                        
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return (Image) null;  
          
    }  

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);

        // Draw the image on to the buffered image
        Graphics bGr = bufferedImage.getGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bufferedImage;
    }

    //convert image to bufferimage and write to file  
    public Image myWrite(Image img, String path) throws IOException{         
        try {
            BufferedImage bufferedImage = toBufferedImage(img);
            ImageIO.write((RenderedImage)bufferedImage, "bmp", new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return img;
    }
}