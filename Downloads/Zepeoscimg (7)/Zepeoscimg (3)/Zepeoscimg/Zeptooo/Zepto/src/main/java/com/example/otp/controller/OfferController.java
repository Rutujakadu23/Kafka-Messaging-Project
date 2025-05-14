package com.example.otp.controller;

import com.example.otp.model.Offer;
import com.example.otp.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    // Add or update offer
    @PostMapping("/save")
    public ResponseEntity<Offer> saveOffer(@RequestBody Offer offer) {
        return ResponseEntity.ok(offerService.saveOffer(offer));
    }

    // Get all offers
    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        return ResponseEntity.ok(offerService.getAllOffers());
    }

    // Delete offer by ID
    @DeleteMapping("/delete/{offerId}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long offerId) {
        offerService.deleteOffer(offerId);
        return ResponseEntity.ok().build();
    }
}
