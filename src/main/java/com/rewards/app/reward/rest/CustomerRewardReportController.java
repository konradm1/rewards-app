package com.rewards.app.reward.rest;

import com.rewards.app.reward.CustomerReportRepresentation;
import com.rewards.app.reward.RewardReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/awards", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CustomerRewardReportController {

    private final RewardReportService rewardReportService;

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<CustomerReportRepresentation> getAwardReportForCustomer(@PathVariable Long customerId) {
        return ok(rewardReportService.prepareReportForCustomer(customerId));
    }
}
