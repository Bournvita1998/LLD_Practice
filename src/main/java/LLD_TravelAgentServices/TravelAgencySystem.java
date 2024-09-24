package LLD_TravelAgentServices;

import java.util.ArrayList;
import java.util.List;

public class TravelAgencySystem {
    private List<TravelPackage> travelPackages;

    public TravelAgencySystem() {
        travelPackages = new ArrayList<>();
    }

    public void addTravelPackage(TravelPackage travelPackage) {
        travelPackages.add(travelPackage);
    }

    public List<TravelPackage> getTravelPackages() {
        return travelPackages;
    }

    public TravelPackage findTravelPackageById(String packageId) {
        for (TravelPackage travelPackage : travelPackages) {
            if (travelPackage.getPackageId().equals(packageId)) {
                return travelPackage;
            }
        }
        return null;
    }
}

