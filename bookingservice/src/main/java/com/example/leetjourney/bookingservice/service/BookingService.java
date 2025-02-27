package com.example.leetjourney.bookingservice.service;

import com.example.leetjourney.bookingservice.client.InventoryServiceClient;
import com.example.leetjourney.bookingservice.entity.Customer;
import com.example.leetjourney.bookingservice.event.BookingEvent;
import com.example.leetjourney.bookingservice.repository.CustomerRepository;
import com.example.leetjourney.bookingservice.request.BookingRequest;
import com.example.leetjourney.bookingservice.response.BookingResponse;
import com.example.leetjourney.bookingservice.response.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    @Autowired
    public BookingService(final CustomerRepository customerRepository,
                          final InventoryServiceClient inventoryServiceClient,
                          final KafkaTemplate<String, BookingEvent> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public BookingResponse createBooking(final BookingRequest request) {
        // check if user exists
        final Customer customer = customerRepository.findById(request.getUserId()).orElse(null);
        if (customer == null) {
            throw new RuntimeException("User not found");
        }
        // check if there is enough inventory
        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.getEventId());
        log.info("Inventory Response: {}", inventoryResponse);
        if (inventoryResponse.getCapacity() < request.getTicketCount()) {
            throw new RuntimeException("Not enough inventory");
        }
        // create booking
        final BookingEvent bookingEvent = createBookingEvent(request, customer, inventoryResponse);
        // send booking to Order Service on a Kafka Topic
        kafkaTemplate.send("booking", bookingEvent);
        log.info("Booking sent to Kafka: {}", bookingEvent);
        return BookingResponse.builder()
                .userId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }

    private BookingEvent createBookingEvent(final BookingRequest request,
                                            final Customer customer,
                                            final InventoryResponse inventoryResponse) {
        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(request.getEventId())
                .ticketCount(request.getTicketCount())
                .totalPrice(inventoryResponse.getTicketPrice().multiply(BigDecimal.valueOf(request.getTicketCount())))
                .build();
    }
}
