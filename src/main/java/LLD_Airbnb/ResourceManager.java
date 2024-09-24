package LLD_Airbnb;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private Map<String, Resource> resources = new HashMap<>();

    public void addResource(Resource resource) {
        resources.put(resource.getId(), resource);
    }

    public Resource getResource(String resourceId) {
        return resources.get(resourceId);
    }

    public void updateResourceAvailability(String resourceId, boolean isAvailable) {
        Resource resource = resources.get(resourceId);
        if (resource != null) {
            resource.setAvailable(isAvailable);
        }
    }

    public Map<String, Resource> getAllResources() {
        return resources;
    }
}

