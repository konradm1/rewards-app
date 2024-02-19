package com.rewards.app.purchase.rest;

import com.rewards.app.purchase.PurchaseService;
import com.rewards.app.purchase.exception.PurchaseNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseService purchaseService;

    @Test
    void testAddPurchase() throws Exception {
        String jsonPayload =
                """ 
                    {
                      "customerId": 5,
                      "amount": 30,
                      "createdAt": "2024-02-19T08:21:55.898Z"
                    }
                """;

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                        .andExpect(status().isOk());
    }

    @Test
    void testAddPurchaseWhenPayloadIncorrect() throws Exception {
        String jsonPayload =
                """
                    {
                        "customerId": null,
                        "amount": 30,
                        "createdAt": "2024-02-19T08:21:55.898Z"
                    };
                """;

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                        .andExpect(status().isBadRequest());
    }

   @Test
    void testUpdatePurchaseWhenPurchaseNoExist() throws Exception {
       String jsonPayload =
               """
                   {
                       "customerId": 5,
                       "amount": 30,
                       "createdAt": "2024-02-19T08:21:55.898Z"
                    };
                """;
        Long id = 1L;

       doThrow(new PurchaseNotFoundException(id)).when(purchaseService).updatePurchase(any(), eq(id));

        mockMvc.perform(put("/purchases/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                        .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePurchaseWhenPurchaseExist() throws Exception {
        String jsonPayload =
                """
                    {
                        "customerId": 5,
                        "amount": 30,
                        "createdAt": "2024-02-19T08:21:55.898Z"
                    }
                """;

        long id = 1L;

        mockMvc.perform(put("/purchases/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePurchaseWhenPayloadIncorrect() throws Exception {
        String jsonPayload =
                """
                    {
                        "customerId": 5,
                        "amount": 0,
                        "createdAt": "2024-02-19T08:21:55.898Z"
                    }
                """;

        long id = 1L;

        mockMvc.perform(put("/purchases/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                        .andExpect(status().isBadRequest());
    }
}
