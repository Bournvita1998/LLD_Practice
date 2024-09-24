package LLD_URL_Shortener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class URLShortenerService {
    private Map<String, String> urlMap; // Maps short URL to long URL
    private Map<String, String> reverseUrlMap; // Maps long URL to short URL
    private AtomicInteger counter; // Counter for generating unique IDs
    private static final String BASE_URL = "http://short.url/";

    public URLShortenerService() {
        urlMap = new HashMap<>();
        reverseUrlMap = new HashMap<>();
        counter = new AtomicInteger(1);
    }

    // Method to shorten a URL
    public String shortenURL(String longURL) {
        if (reverseUrlMap.containsKey(longURL)) {
            return BASE_URL + reverseUrlMap.get(longURL);
        }
        int id = counter.getAndIncrement();
        String shortURL = encode(id);
        urlMap.put(shortURL, longURL);
        reverseUrlMap.put(longURL, shortURL);
        return BASE_URL + shortURL;
    }

    // Method to retrieve the original URL
    public String retrieveURL(String shortURL) {
        String key = shortURL.replace(BASE_URL, "");
        return urlMap.getOrDefault(key, "URL not found");
    }

    // Encoding method to convert integer to a base 62 string
    private String encode(int id) {
        char[] base62Chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(base62Chars[id % 62]);
            id /= 62;
        }
        return sb.reverse().toString();
    }
}
