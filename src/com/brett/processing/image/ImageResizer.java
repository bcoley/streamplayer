package com.brett.processing.image;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageResizer {

    public boolean isResizeNecessary(Dimension imageSize, Dimension boundary) {

        int originalWidth = imageSize.width;
        int originalHeight = imageSize.height;
        int boundaryWidth = boundary.width;
        int boundaryHeight = boundary.height;

        if (originalWidth > boundaryWidth) {
            return true;
        }
        if (originalHeight > boundaryHeight) {
            return true;
        }
        return false;
    }

    public Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {

        int originalWidth = imageSize.width;
        int originalHeight = imageSize.height;
        int boundaryWidth = boundary.width;
        int boundaryHeight = boundary.height;
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        // first check if we need to scale width
        if (originalWidth > boundaryWidth) {
            //scale width to fit
            newWidth = boundaryWidth;
            //scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        }

        // then check if we need to scale even with the new height
        if (newHeight > boundaryHeight) {
            //scale height to fit instead
            newHeight = boundaryHeight;
            //scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }

        return new Dimension(newWidth, newHeight);
    }

    public BufferedImage resizeImage(BufferedImage originalImage, Dimension boundary) {
        Dimension newSize = getScaledDimension(new Dimension(originalImage.getWidth(), originalImage.getHeight()), boundary);
        BufferedImage resizedImage = new BufferedImage(newSize.width, newSize.height,  originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newSize.width, newSize.height, null);
        g.dispose();

        return resizedImage;
    }
}
