package LLD_Key_Value;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        KeyValueStore store = new KeyValueStore();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");

            String command = parts[0];
            switch (command) {
                case "get":
                    handleGet(store, parts[1]);
                    break;
                case "put":
                    handlePut(store, Arrays.copyOfRange(parts, 1, parts.length));
                    break;
                case "delete":
                    handleDelete(store, parts[1]);
                    break;
                case "search":
                    handleSearch(store, parts[1], parts[2]);
                    break;
                case "keys":
                    handleKeys(store);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }

    private static void handleGet(KeyValueStore store, String key) {
        Map<String, Object> value = store.get(key);
        if (value == null) {
            System.out.println("No entry found for " + key);
        } else {
            System.out.println(formatAttributes(value));
        }
    }

    private static void handlePut(KeyValueStore store, String[] parts) {
        String key = parts[0];
        List<Pair<String, String>> attributePairs = new ArrayList<>();
        for (int i = 1; i < parts.length; i += 2) {
            attributePairs.add(new Pair<>(parts[i], parts[i + 1]));
        }
        try {
            store.put(key, attributePairs);
        } catch (IllegalArgumentException e) {
            System.out.println("Data Type Error");
        }
    }

    private static void handleDelete(KeyValueStore store, String key) {
        store.delete(key);
    }

    private static void handleSearch(KeyValueStore store, String attributeKey, String attributeValue) {
        List<String> keys = store.search(attributeKey, attributeValue);
        System.out.println(String.join(",", keys));
    }

    private static void handleKeys(KeyValueStore store) {
        List<String> keys = store.keys();
        System.out.println(String.join(",", keys));
    }

    private static String formatAttributes(Map<String, Object> attributes) {
        List<String> formatted = new ArrayList<>();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            formatted.add(entry.getKey() + ": " + entry.getValue().toString());
        }
        return String.join(", ", formatted);
    }
}

