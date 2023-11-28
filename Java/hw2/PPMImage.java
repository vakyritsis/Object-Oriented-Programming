package ce326.hw2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PPMImage extends RGBImage {

    //PPMImage class has 3 constructors 

    public PPMImage(java.io.File file) throws UnsupportedFileFormatException, java.io.FileNotFoundException{
        
        super(0,0,0);
        
        if(!file.exists() || !file.canRead())
            throw new java.io.FileNotFoundException();

        int  i, j;

        Scanner sc = new Scanner(file);
        // get magic number
        if (!sc.hasNext() || !sc.next().equals("P3")) {
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

        if (!sc.hasNext()) {
            sc.close();
            throw new UnsupportedFileFormatException();
        }
        else
            colordepth = sc.nextInt();

        pixels = new RGBPixel[height][width];
        // now get the rgb pixel with while loop
        for(i = 0; i < height; i++) {
            for(j = 0; j < width; j++) {
                pixels[i][j] = new RGBPixel(sc.nextShort(), sc.nextShort(), sc.nextShort());
            }
        }
        sc.close();
    }

    public PPMImage(RGBImage img) {
        int i, j;
        // default constructor of RGBImage is used here
        this.height = img.getHeight();
        this.width = img.getWidth();
        this.colordepth = img.getColorDepth();

        this.pixels = new RGBPixel[img.getHeight()][img.getWidth()];

        for(i = 0; i < this.height; i++) {
			for(j = 0; j < this.width; j++)
				this.pixels[i][j] = new RGBPixel(img.pixels[i][j]);
		}
    }

    public PPMImage(YUVImage img) {
        int i, j;
        // default constructor of RGBImage is used here
        this.height = img.height;
        this.width = img.width;
        this.colordepth = MAX_COLORDEPTH;

        this.pixels = new RGBPixel[img.height][img.width];

        for(i = 0; i < this.height; i++) {
			for(j = 0; j < this.width; j++)
				this.pixels[i][j] = new RGBPixel(img.pixels[i][j]);
		}
    }

    //PPImage class has 2 methods

    public String toString() {
        StringBuilder holder = new StringBuilder();
        int i, j;

        // appending the format of RGBImage
        holder.append("P3\n" + width + " " + height + "\n" + colordepth + "\n");
        
        // appending the pixels

        for(i = 0; i < height; i++) {
            for(j = 0; j < width; j++) {
                holder.append(pixels[i][j].getRed()).append(" ");
                holder.append(pixels[i][j].getGreen()).append(" ");
                holder.append(pixels[i][j].getBlue()).append("\n");

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
}