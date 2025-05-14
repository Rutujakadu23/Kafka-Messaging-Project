package com.example.otp.service;

import com.example.otp.model.Offer;
import com.example.otp.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    // Add or Update Offer
    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    // Get all Offers
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    // Delete Offer by ID
    public void deleteOffer(Long offerId) {
        offerRepository.deleteById(offerId);
    }
}
