package com.brett.processing.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ImageManager {

    private static Random rand = new Random(System.currentTimeMillis());

    private int currentGroupBegin = -1;
    private int currentGroupEnd = -1;

    private List<ImageHolder> images = new ArrayList<ImageHolder>();

    private float columnLimit = 0;

    float width = 0;
    float height = 0;
    int minSeconds = 15;
    int maxSeconds = 30;

    public int nextGroup(float width, float height, int minSeconds, int maxSeconds) {
        this.width = width;
        this.height = height;
        this.maxSeconds = maxSeconds;
        this.minSeconds = minSeconds;

        if (currentGroupBegin == -1) {
            currentGroupBegin = 0;
        }
        else {
            currentGroupBegin = currentGroupEnd;
            currentGroupBegin++;
            if (currentGroupBegin >= images.size()) {
                shuffle();
                currentGroupBegin = 0;
            }
        }
        currentGroupEnd = currentGroupBegin;
        ImageHolder holder = images.get(currentGroupBegin);
        processImage(holder, 0);

        // see if more will fit
        boolean moreWillFit = true;

        while(moreWillFit) {
            int nextImage = (currentGroupEnd + 1) % images.size();
            holder = images.get(nextImage);
            if ((holder.getImage().width + columnLimit) < width) {
                processImage(holder, columnLimit);
                currentGroupEnd = nextImage;
            } 
            else {
                moreWillFit = false;
            }
        }

        // group of size 1?  Centerish the pic
        if (currentGroupBegin == currentGroupEnd) {
            holder = images.get(currentGroupBegin);
            float deltax = (width - holder.getImage().width) / 2;
            if (deltax < 0) {
                deltax = 0;
            }
            deltax = deltax + rand.nextInt((int) (width/ 10));
            holder.setX(deltax);
        }


        return 0;
    }

    private void processImage(ImageHolder holder, float startingColumn) {
        long expiration = calculateExpiration(minSeconds, maxSeconds);
        holder.setExpirationTime(expiration);

        holder.setNowShowing(true);

        holder.setX(startingColumn + 10 + rand.nextInt((int) (width/8)));
        //holder.setY(rand.nextInt((int) (height/8)));
        
        holder.setY(rand.nextInt(1 + (int) Math.abs(this.height - holder.getImage().height)));

        columnLimit = holder.getX() + holder.getImage().width;
    }

    public int numberDisplaying() {
        int result = 0;
        if (images != null) {
            for (ImageHolder holder: images) {
                if (holder.isNowShowing()) {
                    result++;
                }
            }
        }
        return result;
    }

    private long calculateExpiration(int minSeconds, int maxSeconds) {
        return System.currentTimeMillis() + (1000L * minSeconds) + (1000L * rand.nextInt(maxSeconds));
    }

    private void shuffle() {
        Collections.shuffle(images);
        currentGroupBegin = -1;
        currentGroupEnd = 0;
    }

    public List<ImageHolder> getImages() {
        return images;
    }

    public void addImage(ImageHolder imageHolder) {
        images.add(imageHolder);
    }
    
    public void addImages(List<ImageHolder> imageHolders) {
        images.addAll(imageHolders);
    }
    
    public void resetImages() {
        images.clear();
        currentGroupBegin = -1;
        currentGroupEnd = 0;
    }

}
