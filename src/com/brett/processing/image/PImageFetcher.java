package com.brett.processing.image;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import processing.core.PImage;

/**
 * Subclass of ImageFetcher to return PImages and ImageHolders
 *
 */
public class PImageFetcher extends ImageFetcher {

    public List<PImage> fetchPImages(final List<String> args) {
        List<BufferedImage> bufferedImages = processAsyncSpider(args);
        List<PImage> pimages = new ArrayList<PImage>(20);
        for (BufferedImage bufferedImage: bufferedImages) {
            PImage pimage = createPImage(bufferedImage);
            if (pimage != null) {
                pimages.add(pimage);
            }
        }
        return pimages;
    }

    public Future<List<PImage>> fetchPImagesAsync(final List<String> args) {
        return pool.submit(new Callable<List<PImage>>() {
            public List<PImage> call() throws Exception {
                return fetchPImages(args);
            }
        });
    }

    public List<ImageHolder> fetchImageHolders(final List<String> args) {
        List<ImageHolder> results = new ArrayList<ImageHolder>(20);
        List<PImage> pimages = fetchPImages(args);
        for (PImage pimage: pimages) {
            results.add(new ImageHolder(pimage));
        }
        return results;
    }

    public Future<List<ImageHolder>> fetchImageHoldersAsync(final List<String> args) {
        return pool.submit(new Callable<List<ImageHolder>>() {
            public List<ImageHolder> call() throws Exception {
                return fetchImageHolders(args);
            }
        });
    }

    protected PImage createPImage(BufferedImage bufferedImage) {
        PImage result = null;
        if (null == bufferedImage) {
            return null;
        }
        try {
            int w = bufferedImage.getWidth(null);
            int h = bufferedImage.getHeight(null);
            int[] pixels = new int[w * h];
            PixelGrabber pg =
                    new PixelGrabber(bufferedImage, 0, 0, w, h, pixels, 0, w);
            try {
                pg.grabPixels();
            } catch (InterruptedException e) { }

            result = new PImage(w,h);
            result.loadPixels();
            for (int i = 0; i < w * h; i++) {
                result.pixels[i] = pixels[i];
            }
            result.updatePixels();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
