/**
 * 
 */
package pit.kos.falki.utils;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

/**
 * @author Piotr Kosmala
 *17 kwi 2016
 *13:26:55
 */
public class ImageUtils {
	
	
	public static BufferedImage makeImage(BufferedImage img,CONVERT_TYP typ) {
		if(typ==CONVERT_TYP.GRAY)
		{
			ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
			op.filter(img, img);
			return img;
		}
		else {
			return img;
		}
	}

	public static int[][] convertIntoArrays(BufferedImage img,CONVERT_TYP typ) {
		
		int[][] tabvalues = new int[img.getHeight()][img.getWidth()];
		int rgb;
		if(typ==CONVERT_TYP.COLOR)
		{
			for (int h = 0; h < img.getHeight(); ++h)
				for (int w = 0; w < img.getWidth(); ++w) 
				{
					rgb = img.getRGB(w, h);
					tabvalues[h][w] = rgb;// gray;
				}
			return tabvalues;
		}
		else 
		{
			int  r, g, b, grayLevel, gray;
			for (int h = 0; h < img.getHeight(); ++h)
				for (int w = 0; w < img.getWidth(); ++w)
				{
					rgb = img.getRGB(w, h);
					r = (rgb >> 16) & 0xFF;
					g = (rgb >> 8) & 0xFF;
					b = (rgb & 0xFF);
					grayLevel = (r + g + b) / 3;
					gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
					tabvalues[h][w] = gray;// gray;
				}
			return tabvalues;
		}
	}
	
	public static BufferedImage convertArrayIntoImage(int[][] tabvalue,CONVERT_TYP typ) {

		BufferedImage img=(typ==CONVERT_TYP.COLOR)?
		 new BufferedImage(tabvalue[0].length,tabvalue.length, BufferedImage.TYPE_INT_RGB):
		 new BufferedImage(tabvalue[0].length,tabvalue.length, BufferedImage.TYPE_BYTE_GRAY);

		for (int h = 0; h < img.getHeight(); ++h)
			for (int w = 0; w < img.getWidth(); ++w) 
				img.setRGB(w, h,  tabvalue[h][w]);
			
		return img;
	}
	
}
