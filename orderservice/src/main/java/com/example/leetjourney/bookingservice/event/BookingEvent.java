package com.example.leetjourney.bookingservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEvent {
    private Long userId;
    private Long eventId;
    private Long ticketCount;
    private BigDecimal totalPrice;
}
