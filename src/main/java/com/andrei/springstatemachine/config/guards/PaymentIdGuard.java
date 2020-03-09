package com.andrei.springstatemachine.config.guards;

import com.andrei.springstatemachine.domain.PaymentEvent;
import com.andrei.springstatemachine.domain.PaymentState;
import com.andrei.springstatemachine.services.PaymentServiceImpl;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class PaymentIdGuard implements Guard<PaymentState, PaymentEvent> {


    @Override
    public boolean evaluate(StateContext<PaymentState, PaymentEvent> context) {
        return context.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER) != null;
    }
}
