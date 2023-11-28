package ce326.hw2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Histogram {
    int[] histogram;
    int[] newHistogram;
    int sumOfpixels;
    
    // class Histogram has 1 constructor
    public Histogram(YUVImage img) {
        int i, j;
        int coordinate;
        histogram = new int[236];
        sumOfpixels = img.height * img.width;

        for(i = 0; i < img.height; i++) {
            for(j = 0; j < img.width; j++) {
                coordinate = (img.pixels[i][j]).getY();
                histogram[coordinate]++;
            }
        }
    }

    // class Histogram has 4 methods
    public String toString() {
        int i, j;
        int thousands, hundreds, tens, units;
        String holder;
        StringBuilder retValue = new StringBuilder();
    
        for(i = 0; i < histogram.length; i++) {
            holder = String.format("\n%3d.(%4d)\t", i, histogram[i]);
            retValue.append(holder);
            thousands = (histogram[i] / 1000) % 10;
            hundreds = (histogram[i] / 100) % 10;
            tens = (histogram[i] / 10 ) % 10;
            units = histogram[i] % 10;

            for(j = 0; j < thousands; j++)
                retValue.append("#");

            for(j = 0; j < hundreds; j++)
                retValue.append("$");

            for(j = 0; j < tens; j++)
                retValue.append("@");

            for(j = 0; j < units; j++)
                retValue.append("*");

               
        }
        retValue.append("\n");      

        return retValue.toString();
    }

    public void toFile(File file) {
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
        int i;
        double[] propability = new double[236];
        double[] cumulativePropability = new double[236];
        newHistogram = new int[236];
        
        for(i = 0; i < histogram.length; i++) {
            propability[i] = ((double)histogram[i] / sumOfpixels);
        }

        // initialize the recursive values
        cumulativePropability[0] = propability[0];
        
        for(i = 1; i < histogram.length; i++) {
            cumulativePropability[i] = cumulativePropability[i - 1] + propability[i];
        }

         for(i = 0; i < histogram.length; i++) {
            newHistogram[i] = (int)(cumulativePropability[i] * 235);
        }
        
    }

    public short getEqualizedLuminocity(int luminocity) {
        short newLuminocity = (short)newHistogram[luminocity];

        return newLuminocity;
    }
}