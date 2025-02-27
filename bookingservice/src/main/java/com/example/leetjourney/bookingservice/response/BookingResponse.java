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
public class BookingResponse {
    private Long userId;
    private Long eventId;
    private Long ticketCount;
    private BigDecimal totalPrice;
}
