package ce326.hw2;

public class RGBPixel{

    // the RGBPixel class has 3 fields
    byte red;
    byte green;
    byte blue;

    // the RGBPixel class has two constructors
    public RGBPixel(short red, short green, short blue) {
        this.red = (byte)(red - 128);
        this.green = (byte)(green - 128);
        this.blue = (byte)(blue - 128);
    }

    public RGBPixel(RGBPixel pixel) {
        this.red = pixel.red;
        this.green = pixel.green;
        this.blue = pixel.blue;
    }
    
    public RGBPixel(YUVPixel pixel) {
        int C, D, E;

        C = pixel.getY() - 16;
        D = pixel.getU() - 128;
        E = pixel.getV() - 128;

        red = (byte) (clip((298 * C + 409 * E + 128 >> 8)) - 128);
        green = (byte) (clip((298 * C - 100 * D - 208 * E + 128) >> 8) - 128);
        blue = (byte) (clip((298 * C + 516 * D + 128) >> 8) - 128);
    }
    
    // clip method provides protection for RGB values 
    private int clip(int number) {
        if(number < 0)
            return 0;
        if(number > 255)
            return 255;

        return number;
    }

    // the RGBPixel class has 10 methods

    public short getRed() {
        return (short)(red + 128);
    }

    public short getGreen() {
        return (short)(green + 128);
    }

    public short getBlue() {
        return (short)(blue + 128);
    }

    public void setRed(short red) {
        this.red = (byte)(red - 128);
    }

    public void setGreen(short green) {
        this.green = (byte)(green - 128);
    }

    public void setBlue(short blue) {
        this.blue = (byte)(blue - 128);
    }

    public int getRGB() {
        int value = 0;
        short  nRed = (short)(red + 128), nGreen = (short)(green + 128), nBlue = (short)(blue + 128);
        
        value = value | nRed;
	    value = value << 8;
	    value = value | nGreen;
	    value = value << 8;
	    value = value | nBlue;

        return value;
    }

    public void setRGB(int value) {
        short r, g, b;

        b = (short)(value & 255);
        value = value >> 8;
        g = (short)(value & 255);
        value = value >> 8;
        r = (short)(value & 255);

        red = (byte)(r - 128);
        green = (byte)(g - 128);
        blue = (byte)(b - 128);

    }

    public final void setRGB(short red, short green, short blue) {
        this.red = (byte)(red - 128);
        this.green = (byte)(green - 128);
        this.blue = (byte)(blue - 128);
    }

    public String toString() {
        String str = String.format("%d %d %d", red + 128, green + 128, blue + 128); 
        
        return str;
    }

}