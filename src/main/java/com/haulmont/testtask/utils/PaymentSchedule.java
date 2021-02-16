package com.haulmont.testtask.utils;

import com.haulmont.testtask.entity.CreditOffer;
import com.haulmont.testtask.entity.Payment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentSchedule {

    public static List<Payment> getCalculatedPaymentSchedule(CreditOffer creditOffer, LocalDate date, int months) {
        List<Payment> payments = new ArrayList<>();
        LocalDate paymentDate = date;
        float creditAmountPayment;
        float creditInterestPayment;
        float creditBodyPayment = creditOffer.getCreditAmount() / (float) months;
        for (int i = 0; i < months; i++) {
            paymentDate = paymentDate.plusMonths(1);
            creditInterestPayment = (creditOffer.getCreditAmount() - creditBodyPayment * i) *
                    ((float) (creditOffer.getCredit().getInterestRate()) / 100f) /
                    (float) paymentDate.lengthOfYear() * paymentDate.lengthOfMonth();
            creditAmountPayment = creditBodyPayment + creditInterestPayment;
            payments.add(new Payment(paymentDate, creditAmountPayment, creditBodyPayment, creditInterestPayment));
        }
        return payments;
    }
}
