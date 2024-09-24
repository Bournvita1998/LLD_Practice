package LLD_URL_Shortener;

import java.util.HashMap;
import java.util.Map;

public class URLShortenerManager {
    private Map<String, URLShortenerService> services;

    public URLShortenerManager() {
        services = new HashMap<>();
    }

    public URLShortenerService createService(String serviceName) {
        URLShortenerService service = new URLShortenerService();
        services.put(serviceName, service);
        return service;
    }

    public URLShortenerService getService(String serviceName) {
        return services.get(serviceName);
    }

    public static void main(String[] args) {
        URLShortenerManager manager = new URLShortenerManager();
        URLShortenerService service = manager.createService("default");

        String longURL = "https://example.com";
        String shortURL = service.shortenURL(longURL);
        System.out.println("Short URL: " + shortURL);

        String retrievedURL = service.retrieveURL(shortURL);
        System.out.println("Original URL: " + retrievedURL);
    }
}
