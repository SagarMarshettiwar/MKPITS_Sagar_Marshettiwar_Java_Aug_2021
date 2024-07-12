package com.ic;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Unevenimage {
    public static void main(String[] args) {
        String sourceDirectory = "C:\\Users\\TRUST\\Desktop\\iosssblue";
        String outputDirectory = "C:\\Users\\TRUST\\Desktop\\sizeimg";
        File folder = new File(sourceDirectory);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String sourceImagePath = file.getAbsolutePath();
                    convertImageToMultipleSizes(sourceImagePath, outputDirectory);
                }
            }
        }
    }
    public static void convertImageToMultipleSizes(String sourceImagePath, String outputDirectory) {
        int count = 0;
        try {
            BufferedImage sourceImage = ImageIO.read(new File(sourceImagePath));
            int[] x = { 2048, 1242, 1242, 1290 };
            int[] y = { 2732, 2208, 2688, 2796 };
            for (int i = 0; i < x.length; i++) {
                BufferedImage resizedImage = resizeImage(sourceImage, x[i], y[i]);
                String fileName = new File(sourceImagePath).getName();
                String outputPath = outputDirectory + "/" + "resized_" + fileName + "_" + x[i] + "X" + y[i] + ".png";
                ImageIO.write(resizedImage, "png", new File(outputPath));
                count++;
            }
            System.out.println("Converted " + count + " sizes for " + new File(sourceImagePath).getName());
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