package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class PPMImageStacker {
	List<PPMImage> photos = new ArrayList<PPMImage>();;
	PPMImage newImage;


    public PPMImageStacker(java.io.File dir) throws FileNotFoundException, UnsupportedFileFormatException{

        if (!dir.exists()) {
			System.out.format("[ERROR] Directory %s does not exist!\n", dir.getName());
			throw new FileNotFoundException();
		}

        if (!dir.isDirectory()) {
			System.out.format("[ERROR] %s is not a directory!\n", dir.getName());
			throw new FileNotFoundException();
		}

        int i;
        File[] files = dir.listFiles();
        for(i = 0; i < files.length; i ++) {
            try {
                photos.add(new PPMImage(files[i]));
            }
            catch (UnsupportedFileFormatException ex) {
                throw new UnsupportedFileFormatException();
            }
        }
    }
    
    public void stack() {
        int i, j, k;
        int r, g, b;
        r = g = b = 0;
        if(photos.isEmpty()) {
            return;
        }

        newImage = new PPMImage(photos.get(0));
        for(i = 0; i < newImage.height; i++) {
            for(j = 0; j < newImage.width; j++) {
                for(k = 0; k < photos.size(); k++) {
                    r += photos.get(k).pixels[i][j].getRed();
                    g += photos.get(k).pixels[i][j].getGreen();
                    b += photos.get(k).pixels[i][j].getBlue();

                }
                r /= photos.size();
                g /= photos.size();
                b /= photos.size();
                newImage.setPixel(i, j, new RGBPixel((short)(r), (short)(g), (short)(b)));
                r = g = b = 0;
            }
        }
        
    }
    public PPMImage getStackedImage(){
        return newImage;
    }

}