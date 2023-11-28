package ce326.hw2;

public class RGBImage implements Image {

	// the RGBImage class has 3 fields
	protected RGBPixel[][] pixels;
	protected int width, height, colordepth;
	public final static int MAX_COLORDEPTH = 255;

	// the RGBImage class has 4 constructors
	public RGBImage() {
		//default constructor
	}

	public RGBImage(int width, int height, int colordepth) {
		pixels = new RGBPixel[height][width];
		this.width = width;
		this.height = height;
		this.colordepth = colordepth;
	}
	
	public RGBImage(RGBImage copyImg) {
		pixels = copyImg.pixels.clone();
		width = copyImg.width;
		height = copyImg.height;
		colordepth = copyImg.colordepth;
	}

	public RGBImage(YUVImage YUVImg) {
		int i, j;

		width = YUVImg.width;
		height = YUVImg.height;
		pixels = new RGBPixel[height][width];

		for(i = 0; i < height; i++) {
			for(j = 0; j < width; j++)
				pixels[i][j] = new RGBPixel(YUVImg.pixels[i][j]);
		}

	}

	// the RGBImage class has 5 mothods

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getColorDepth() {
		return colordepth;
	}

	public RGBPixel getPixel(int row, int col) {
		return pixels[row][col];
	}

	public void setPixel(int row, int col, RGBPixel pixel) {
		pixels[row][col] = pixel;
	}

	// 
	public void grayscale() {
		int i, j;
		short grey;

		for(i = 0; i < height; i++) {
			for(j = 0; j < width; j++) {
				grey = (short) (pixels[i][j].getRed() * 0.3 + pixels[i][j].getGreen() * 0.59 + pixels[i][j].getBlue() * 0.11);
				pixels[i][j].setRGB(grey, grey, grey);
			}
		}
	}

	public void doublesize() {
		int i, j;
		RGBPixel[][] newPixels = new RGBPixel[height * 2][width * 2];

		for(i = 0; i < height; i++) {
			for(j = 0; j < width; j++) {
				newPixels[2 * i][2 * j] = new RGBPixel(pixels[i][j]);
				newPixels[2 * i + 1][2 * j] = new RGBPixel(pixels[i][j]);
				newPixels[2 * i][2 * j + 1] = new RGBPixel(pixels[i][j]);
				newPixels[2 * i + 1][2 * j + 1] = new RGBPixel(pixels[i][j]);
			}
		}

		width *= 2;
		height *= 2;
		pixels = newPixels;

	}
	public void halfsize() {
		int i, j;
		RGBPixel[][] newPixels = new RGBPixel[height / 2][width / 2];
		short newRed, newGreen, newBlue;

		for(i = 0; i < height/2; i++) {
			for(j = 0; j < width/2; j++) {
				newRed = (short) ((pixels[2 * i][2 * j].getRed() + pixels[2 * i + 1][2 * j].getRed() + pixels[2 * i][2 * j + 1].getRed() + pixels[2 * i + 1][2 * j + 1].getRed()) / 4);
				newGreen = (short) ((pixels[2 * i][2 * j].getGreen() + pixels[2 * i + 1][2 * j].getGreen() + pixels[2 * i][2 * j + 1].getGreen() + pixels[2 * i + 1][2 * j + 1].getGreen()) / 4);
				newBlue = (short) ((pixels[2 * i][2 * j].getBlue() + pixels[2 * i + 1][2 * j].getBlue() + pixels[2 * i][2 * j + 1].getBlue() + pixels[2 * i + 1][2 * j + 1].getBlue()) / 4);
				newPixels[i][j] = new RGBPixel(newRed, newGreen, newBlue);
			}
		}

		width /= 2;
		height /= 2;
		pixels = newPixels;


	}
	public void rotateClockwise() {
		int i, j;
		
		// create a new array with reverted dimentions
		RGBPixel[][] newPixels = new RGBPixel[width][height];

		for(i = 0; i < height; i++) {
			for(j = 0; j < width; j++) {
				newPixels[j][(height - 1) - i] = pixels[i][j];
			}
		}
		
		//swap width-height
		height = height + width;   
        width = height - width;   
        height = height - width;   

		pixels = newPixels;
		
	}

}