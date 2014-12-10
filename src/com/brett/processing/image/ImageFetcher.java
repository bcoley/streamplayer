package com.brett.processing.image;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

public class ImageFetcher {

    protected ImageResizer resizer = new ImageResizer();

    protected final ExecutorService pool = Executors.newFixedThreadPool(20);

    private final String[] filteredWords = {null, "", ",", "a", "an", "the",
            "-", "on", "of", "http", "com"};

    private final List<String> filteredList = new ArrayList<String>(Arrays.asList(filteredWords));

    private Map<String, BufferedImage> urlImageMap = new HashMap<String, BufferedImage>();

    protected Random rand = new Random(System.currentTimeMillis());

    protected boolean adultFilter = true;

    protected Dimension BOUNDARY = new Dimension(640, 480);

//    public static void main(String[] args) {
//
//        ImageFetcher getPics = new ImageFetcher();
//
//        // getPics.process(args);
//
//        long pTime = System.currentTimeMillis();
//        Future<List<BufferedImage>> futureList = getPics.fetchImagesAsync(Arrays.asList(args));
//        try {
//            List<BufferedImage> list = futureList.get();
//            for (BufferedImage image: list) {
//                getPics.saveImage(image);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        System.out.println(" processAsyncTime = " + (System.currentTimeMillis() - pTime));
//        System.exit(0);
//    }

    public BufferedImage getImageFromUrl(final String urlString) {
        BufferedImage image = null;
        if (urlImageMap.containsKey(urlString)) {
            return urlImageMap.get(urlString);
        }
        System.out.println("Start  " + urlString);
        try {
            Thread.sleep(rand.nextInt(1000));
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            InputStream in = con.getInputStream();
            image = ImageIO.read(in);
            in.close();
            if (image != null) {
                System.out.println("Loaded " + urlString);
                if (resizer.isResizeNecessary(new Dimension(image.getWidth(), image.getHeight()), BOUNDARY)) {
                    image = resizer.resizeImage(image, BOUNDARY);
                    System.out.println("Resizing image from " + urlString);
                }
            } 
            else {
                System.out.println("Could not load image from " + urlString);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        urlImageMap.put(urlString, image);
        return image;
    }


    public Future<BufferedImage> getImageFromUrlAsync(final String urlString) {
        return pool.submit(new Callable<BufferedImage>() {
            public BufferedImage call() throws Exception {
                return getImageFromUrl(urlString);
            }
        });
    }

    //    private void saveImageFromUrl(String urlString) {
    //
    //        BufferedImage image = getImageFromUrl(urlString);
    //
    //        if (image == null) {
    //            System.err.println("no image for " + urlString);
    //            return;
    //        }
    //
    //        if (resizer.isResizeNecessary(new Dimension(image.getWidth(), image.getHeight()), BOUNDARY)) {
    //            image = resizer.resizeImage(image, BOUNDARY);
    //            System.out.println("Resizing image from " + urlString);
    //        }
    //
    //        writeImageToFile(urlString, image);
    //    }

//    private void saveImage(BufferedImage image) {
//
//        if (image == null) {
//            return;
//        }
//
//        if (resizer.isResizeNecessary(new Dimension(image.getWidth(), image.getHeight()), BOUNDARY)) {
//            image = resizer.resizeImage(image, BOUNDARY);
//        }
//
//        writeImageToFile("", image);
//    }

//    private void writeImageToFile(String urlString, BufferedImage image) {
//        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
//
//        ImageWriter writer = writers.next();
//        ImageWriteParam iwp = writer.getDefaultWriteParam();
//
//        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//        iwp.setCompressionQuality(1.0F);   
//        // values range from 0.0 to 1.0
//        // 0.0 = "high compression is important", 1.0 = "high image quality is important."
//
//        try {
//            File tempFile = File.createTempFile("addimage", ".jpg");
//            FileImageOutputStream output = new FileImageOutputStream(tempFile);
//            writer.setOutput(output);
//            IIOImage iioImage = new IIOImage(image, null, null);
//            writer.write(null, iioImage, iwp);
//            output.close();
//            writer.dispose();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }        

    //    private List<BufferedImage> process(String[] args) {
    //        long startTime = System.currentTimeMillis();
    //        List<BufferedImage> resultList = new ArrayList<BufferedImage>(20);
    //        ImageUrlFetcher fetcher = new ImageUrlInfoFetcher();
    //        List<String> results = fetcher.searchForImageUrls(Arrays.asList(args));
    //        for (String result: results) {
    //            System.out.println("url = " + result);
    //            BufferedImage image = getImageFromUrl(result);
    //            resultList.add(image);
    //        }
    //        System.out.println(" process time = " + (System.currentTimeMillis() - startTime));
    //        return resultList;
    //    }

    public Future<List<BufferedImage>> fetchImagesAsync(final List<String> args) {
        return pool.submit(new Callable<List<BufferedImage>>() {
            public List<BufferedImage> call() throws Exception {
                return processAsyncSpider(args);
            }
        });
    }

    protected List<BufferedImage> processAsyncSpider(final List<String> searchTerms) {
        long startTime = System.currentTimeMillis();
        List<String> terms = new ArrayList<String>(searchTerms.size());
        terms.addAll(searchTerms);
        List<BufferedImage> resultList = new ArrayList<BufferedImage>(20);
        ImageUrlInfoFetcher fetcher = new ImageUrlInfoFetcher();
        fetcher.setAdultFilter(adultFilter);
        List<String> urls = fetcher.searchForImageUrls(terms);
        boolean hopeless = false;

        while ((urls.size() == 0) && !hopeless) {
            terms.remove(0);
            System.out.println("Retry search = " + terms);
            urls = fetcher.searchForImageUrls(terms);
            if (terms.size() < 2) {
                hopeless = true;
            }
        }

        List<Future<BufferedImage>> futures = new ArrayList<Future<BufferedImage>>(urls.size());
        
        for (String url: urls) {
            System.out.println("url = " + url);
            futures.add(getImageFromUrlAsync(url));
        }
        
        for (Future<BufferedImage> future: futures) {
            try {
                BufferedImage image = future.get();
                if (image != null) {
                    resultList.add(image);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(" process async time = " + (System.currentTimeMillis() - startTime) + ", found " + resultList.size() + " images.");
        return resultList;
    }

    public List<String> createSearchTerms(String mp3FileName) {

        List<String> result = new ArrayList<String>();

        String fileName = mp3FileName;

        // split off path info
        if (mp3FileName.contains(File.separator)) {
            fileName = mp3FileName.substring(1 + mp3FileName.lastIndexOf(File.separator));
        }

        // split off suffix
        if (fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }

        // we assume filenames contain spaces
        String[] parts = fileName.split(" ");

        for (String term : parts) {
            term = fixup(term);
            if (!isFilteredWord(term) && !result.contains(term)) {
                result.add(term);
            }
        }

        return result;
    }

    private String fixup(String term) {
        // remove special chars
        String result = term.replaceAll("\\W", "");
        result = result.trim();
        result = result.toLowerCase();
        return result;
    }

    private boolean isFilteredWord(String term) {
        String word = fixup(term);

        if (null == word) {
            return true;
        }

        if (filteredList.contains(word)) {
            return true;
        }
        return false;
    }


    public boolean isAdultFilter() {
        return adultFilter;
    }


    public void setAdultFilter(boolean adultFilter) {
        this.adultFilter = adultFilter;
    }
    
    
}
