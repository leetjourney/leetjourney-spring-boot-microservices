package com.example.leetjourney.bookingservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    private Long userId;
    private Long eventId;
    private Long ticketCount;
}
