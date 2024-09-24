package LLD_Key_Value;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStore {
    private final ConcurrentHashMap<String, Map<String, Object>> store = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Map<String, Class<?>>> attributeTypes = new ConcurrentHashMap<>();

    public Map<String, Object> get(String key) {
        return store.getOrDefault(key, null);
    }

    public void put(String key, List<Pair<String, String>> listOfAttributePairs) throws IllegalArgumentException {
        Map<String, Object> attributes = new HashMap<>();
        Map<String, Class<?>> types = attributeTypes.getOrDefault(key, new HashMap<>());

        for (Pair<String, String> pair : listOfAttributePairs) {
            String attributeKey = pair.getKey();
            String attributeValue = pair.getValue();
            Object typedValue;

            if (types.containsKey(attributeKey)) {
                Class<?> expectedType = types.get(attributeKey);
                typedValue = TypeUtil.parseValue(attributeValue, expectedType);
            } else {
                typedValue = TypeUtil.inferType(attributeValue);
                types.put(attributeKey, typedValue.getClass());
            }

            attributes.put(attributeKey, typedValue);
        }

        store.put(key, attributes);
        attributeTypes.put(key, types);
    }

    public void delete(String key) {
        store.remove(key);
        attributeTypes.remove(key);
    }

    public List<String> search(String attributeKey, String attributeValue) {
        List<String> matchingKeys = new ArrayList<>();

        for (Map.Entry<String, Map<String, Object>> entry : store.entrySet()) {
            Map<String, Object> attributes = entry.getValue();
            if (attributes.containsKey(attributeKey) && attributes.get(attributeKey).toString().equals(attributeValue)) {
                matchingKeys.add(entry.getKey());
            }
        }

        Collections.sort(matchingKeys);
        return matchingKeys;
    }

    public List<String> keys() {
        List<String> keyList = new ArrayList<>(store.keySet());
        Collections.sort(keyList);
        return keyList;
    }
}

