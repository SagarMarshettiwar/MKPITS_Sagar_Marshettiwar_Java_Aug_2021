package com.ic;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageConverter {
    public static void main(String[] args) {
    	String sourceImagePath = "C:\\Users\\TRUST\\Desktop\\veerseva.png";
        String outputDirectory = "C:\\Users\\TRUST\\Desktop\\sizeimg";
        convertImageToMultipleSizes(sourceImagePath, outputDirectory);
    }
    public static void convertImageToMultipleSizes(String sourceImagePath, String outputDirectory) {
        int count=0;
    	try {
            BufferedImage sourceImage = ImageIO.read(new File(sourceImagePath));
            int[] pixelSizes = {16,20,29,32,40,48,55,58,60,64,66,76,80,87,88,92,100,102,108,120,128,152,167,172,180,182,196,216,234,256,258,512,1024};
            for (int size : pixelSizes) {
                BufferedImage resizedImage = resizeImage(sourceImage, size, size);
                String outputPath = outputDirectory + "/" +"veerseva_logo_"+ size + ".png";
                ImageIO.write(resizedImage, "png", new File(outputPath));
                count++;
            }
            System.out.println("Allha ki den converted " +count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}

