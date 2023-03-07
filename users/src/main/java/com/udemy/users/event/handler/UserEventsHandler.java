package com.udemy.users.event.handler;

import com.udemy.shared.model.PaymentDetails;
import com.udemy.shared.model.User;
import com.udemy.shared.query.FetchUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;


@Component
public class UserEventsHandler {
    @QueryHandler
    public User getUserDetails(FetchUserPaymentDetailsQuery query) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("Johny Wallker")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        return User.builder()
                .firstName("Johny")
                .lastName("Wallker")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();
    }
}
