package com.brett.processing.image;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageUrlInfoFetcher {
    
    private Map<List<String>, List<String>> searchCache = new HashMap<List<String>, List<String>>();
    
    protected boolean adultFilter = true;
    
    public List<String> searchForImageUrls(List<String> searchTerms) {
        if (searchCache.containsKey(searchTerms)) {
            return searchCache.get(searchTerms);
        }
        List<String> resultList = new ArrayList<String>();
        String urlStr = "http://images.info.com/search?qcat=images&qkw=";
        boolean first = true;
        for(String term: searchTerms) {
            if (first) {
                urlStr = urlStr + term;
                first = false;
            }
            else {
                urlStr = urlStr + "+" + term;
            }
        }
                
        try {
            Thread.sleep(250);
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);
            // newwindow+1+dpcollation_web+1+lang+0+familyfilter+1+bold+1+msRecentSearches+off+autocorrect+0+domain+infocom+ts+1406828819+last_cmp++engineset++zip++sess;
            String ts = "" + (System.currentTimeMillis() / 1000L);
            String familyFilter = "0";
            if (isAdultFilter()) {
                familyFilter = "1";
            }
            String myCookie = "a=newwindow+1+dpcollation_web+1+lang+0+familyfilter+" + familyFilter + "+bold+1+msRecentSearches+off+autocorrect+0+domain+infocom+ts+" + ts + 
                    "+last_cmp++engineset++zip++sess";
            urlConnection.setRequestProperty("Cookie", myCookie);
            urlConnection.connect();
//            String headerName=null;
//            for (int i=1; (headerName = urlConnection.getHeaderFieldKey(i))!=null; i++) {
//                if (headerName.equals("Set-Cookie")) {                  
//                    String cookie = urlConnection.getHeaderField(i);
//                    System.out.println("cookie = " + cookie);
//                }
//            }
            InputStream inputStream = urlConnection.getInputStream();
            //Object content = urlConnection.getContent();
            //System.out.println("content: " + content);
            Reader reader = new InputStreamReader(inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            int c = 0;
            char ch;
            while (c != -1) {
                c = reader.read();
                ch = (char) c;
                stringBuilder.append(ch);
             //   System.out.print(ch);
            }
            reader.close();
            String htmlText = stringBuilder.toString();
            //System.out.println(htmlText);
            resultList = getUrls(htmlText);
            System.out.println("\nfound " + resultList.size() + " urls.");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        searchCache.put(searchTerms, resultList);
        return resultList;
    }

    private List<String> getUrls(String htmlText) {
        List<String> result = new ArrayList<String>();
        // input is like
        // http://cs.dogpile.com/ClickHandler.ashx?du=http%3a%2f%2fwww.darkduck.net%2fimgs%2fnewimgs2%2fDronesDeep02_300.jpg&ru=...
        String[] parts = htmlText.split("&du=");
        for (String str: parts) {
            if (parts.length > 1) {
                String parts2[] = str.split("&ru=");
                if (parts2.length > 0) {
                    String url = parts2[0];
                    if (url.startsWith("http")) {
                        url = decodeURL(url);
                        if (!result.contains(url)) {
                            result.add(url);
                        }
                    }
                }
            }
        }

        return result;
    }
    
    public String decodeURL(String rawString) {
        String result = null;
        try {
            result = URLDecoder.decode(rawString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isAdultFilter() {
        return adultFilter;
    }

    public void setAdultFilter(boolean adultFilter) {
        this.adultFilter = adultFilter;
    }

}
