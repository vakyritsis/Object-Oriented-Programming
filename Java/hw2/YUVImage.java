package ce326.hw2;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class YUVImage {

    // YUVImage class has 3 fields
    int width, height;
    YUVPixel[][] pixels;

    // YUVImage class has 4 constructors

    public YUVImage(int width, int height) {
        int i,j;

        this.width = width;
        this.height = height;
        this.pixels = new YUVPixel[height][width];

        for(i = 0; i < height; i++) {
            for(j = 0; j < width; j++) 
                pixels[i][j] = new YUVPixel((short) 16, (short) 128, (short) 128);
        }
    }

    public YUVImage(YUVImage copyImg) {
        height = copyImg.height;
		width = copyImg.width;
		pixels = copyImg.pixels.clone();
    }
    
    public YUVImage(RGBImage RGBImg) {
        int i,j;

        width = RGBImg.getWidth();
        height = RGBImg.getHeight();
        pixels = new YUVPixel[height][width];

        for(i = 0; i < height; i++) {
            for(j = 0; j < width; j++)
                pixels[i][j] = new YUVPixel(RGBImg.getPixel(i, j));
        }

    }

    public YUVImage(java.io.File file) throws UnsupportedFileFormatException, FileNotFoundException {
        if(!file.exists() || !file.canRead())
            throw new FileNotFoundException();

        int i, j;

        Scanner sc = new Scanner(file);
        // get magic number
        if (!sc.hasNext() || !sc.next().equals("YUV3")) {
			sc.close();
			throw new UnsupportedFileFormatException();
		}

        if (!sc.hasNext()) {
			sc.close();
			throw new UnsupportedFileFormatException();
		}
        else
            width = sc.nextInt();

        if (!sc.hasNext()) {
            sc.close();
            throw new UnsupportedFileFormatException();
        }
        else
            height = sc.nextInt();
        

        pixels = new YUVPixel[height][width];
        // now get the yuv pixel with while loop
        for(i = 0; i < height; i++) {
            for(j = 0; j < width; j++) {
                pixels[i][j] = new YUVPixel(sc.nextShort(), sc.nextShort(), sc.nextShort());
            }
        }
        sc.close();
    }

    // YUVImage class has 3 methods

    public String toString() {
        StringBuilder holder = new StringBuilder();
        int i, j;

        // appending the format of YUVImage
        holder.append("YUV3\n" + width + " " + height + "\n");

        // appending the pixels

        for(i = 0; i < height; i++) {
            for(j = 0; j < width; j++) {
                holder.append(pixels[i][j].getY()).append(" ");
                holder.append(pixels[i][j].getU()).append(" ");
                holder.append(pixels[i][j].getV()).append("\n");

            }
        }

        return holder.toString();
    }

    public void toFile(java.io.File file) {
        
        try (FileWriter fw = new FileWriter(file)) {
            if(file.exists())
			    fw.write(toString());
            else 
                fw.append(toString());
		} catch (IOException ex) {
			System.err.format("File %s not found", file.getName());
		}

    }

    public void equalize() {
        int i, j;
        Histogram histogr = new Histogram(this);
        histogr.equalize();
        for(i = 0; i < height; i++) {
            for(j = 0; j < width; j++) {
                pixels[i][j].setY(histogr.getEqualizedLuminocity(pixels[i][j].getY()));
            }
        }
    }
}