package com.andrei.springstatemachine.config.actions;

import com.andrei.springstatemachine.domain.PaymentEvent;
import com.andrei.springstatemachine.domain.PaymentState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class AuthDeclinedAction implements Action<PaymentState, PaymentEvent> {
    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> context) {
        System.out.println("Sending Notification Auth Declined");
    }
}
