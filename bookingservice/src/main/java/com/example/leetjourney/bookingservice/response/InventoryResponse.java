package com.example.leetjourney.bookingservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private Long eventId;
    private String event;
    private Long capacity;
    private VenueResponse venue;
    private BigDecimal ticketPrice;
}
