package com.rewards.app.purchase.rest;

import com.rewards.app.purchase.PurchaseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/purchases")
@AllArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addPurchase(@Valid @RequestBody PurchasePayload payload) {
        purchaseService.addPurchase(payload);
        return ok().build();
    }

    @PutMapping(value ="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePurchase(@Valid @RequestBody PurchasePayload payload, @PathVariable Long id) {
        purchaseService.updatePurchase(payload, id);
        return ok().build();
    }
}
