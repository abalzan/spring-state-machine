package com.andrei.springstatemachine.services;

import com.andrei.springstatemachine.domain.Payment;
import com.andrei.springstatemachine.domain.PaymentEvent;
import com.andrei.springstatemachine.domain.PaymentState;
import com.andrei.springstatemachine.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository repository;

    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("12.99")).build();
    }

    @Transactional
    @Test
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(payment);

        System.out.println("Should be NEW");
        System.out.println(savedPayment.getState());

        paymentService.preAuth(savedPayment.getId());

        Payment preAuthedPayment = repository.getOne(savedPayment.getId());

        System.out.println("Should be PRE_AUTH or PRE_AUTH_ERROR");
        System.out.println(preAuthedPayment);
    }

    @Test
    @RepeatedTest(10)
    void testAuth() {
        Payment savedPayment = paymentService.newPayment(payment);

        StateMachine<PaymentState, PaymentEvent> preAuthStateMachine = paymentService.preAuth(savedPayment.getId());
        if(preAuthStateMachine.getState().getId() == PaymentState.PRE_AUTH) {
            System.out.println("Payment is pre authorized");

            StateMachine<PaymentState, PaymentEvent> authStateMachine = paymentService.authorizePayment(savedPayment.getId());

            System.out.println("Result of auth "+ authStateMachine.getState().getId());
        } else {
            System.out.println("Payment failed pre-auth");
        }
    }
}