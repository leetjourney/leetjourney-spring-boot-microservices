package com.example.leetjourney.bookingservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueResponse {
    private Long id;
    private String name;
    private String address;
    private Long totalCapacity;
}
