package com.rewards.app.reward.rest;

import com.rewards.app.reward.CustomerReportRepresentation;
import com.rewards.app.reward.RewardReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerRewardReportController.class)
class CustomerRewardReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RewardReportService rewardReportService;

    @Test
    void prepareReportForCustomer() throws Exception {

        // given
        long id = 1L;
        when(rewardReportService.prepareReportForCustomer(id)).thenReturn(
                CustomerReportRepresentation.builder()
                        .pointsByMonth(Map.of("2024-02", BigDecimal.valueOf(280)))
                        .totalPoints(BigDecimal.valueOf(280))
                        .build()
        );

        // when then
        mockMvc.perform(get("/awards/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString().contains("""
                        {
                            "pointsByMonth": {
                                "2024-02": 280.00
                            },
                            "totalPoints": 280.00
                            }
                        }
                        """);
    }
}
