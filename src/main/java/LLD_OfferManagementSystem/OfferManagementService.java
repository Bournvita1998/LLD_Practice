package LLD_OfferManagementSystem;

import java.util.HashMap;
import java.util.Map;

public class OfferManagementService {
    private Map<String, Offer> offers;

    public OfferManagementService() {
        this.offers = new HashMap<>();
    }

    public void createOffer(Offer offer) {
        offers.put(offer.getId(), offer);
    }

    public Offer getOffer(String offerId) {
        return offers.get(offerId);
    }
}
