package cglib2d.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Utils {

	public static BufferedImage readImage(Object obj,String imageFileName) {
		BufferedImage bi = null;
		URL imageSrc = null;
		
		imageSrc = obj.getClass().getClassLoader().getResource(imageFileName);
		try {
			bi = ImageIO.read(imageSrc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Image could not be read");
		}
		
		
		
		return bi;
	}

}
