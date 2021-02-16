package com.haulmont.testtask.ui.window;

import com.haulmont.testtask.entity.CreditOffer;
import com.haulmont.testtask.entity.Payment;
import com.haulmont.testtask.utils.Constants;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

public class PaymentScheduleWindow extends Window {

    private Grid<Payment> paymentScheduleGrid;

    private CreditOffer creditOffer;

    public void init(CreditOffer creditOffer, String caption) {
        this.creditOffer = creditOffer;

        VerticalLayout mainLayout = new VerticalLayout();
        TextField clientTf = new TextField("Клиент: ");
        clientTf.setValue(creditOffer.getClient().getName());
        clientTf.setEnabled(false);

        Label interestOverpaymentLabel = new Label();
        Label totalCreditAmountLabel = new Label();
        initPaymentScheduleGrid();

        float interestOverpayment = 0;
        for (Payment payment : creditOffer.getPaymentSchedule()) {
            interestOverpayment += payment.getInterestPaymentAmount();
        }
        float totalCreditAmount = creditOffer.getCreditAmount() + interestOverpayment;
        interestOverpaymentLabel.setValue(String.format(Constants.INTEREST_OVERPAYMENT_AMOUNT, interestOverpayment));
        totalCreditAmountLabel.setValue(String.format(Constants.TOTAL_CREDIT_AMOUNT, totalCreditAmount));
        mainLayout.addComponents(clientTf, interestOverpaymentLabel, totalCreditAmountLabel, paymentScheduleGrid);

        setCaption(caption);
        setContent(mainLayout);
        setModal(true);
        setWindowMode(WindowMode.NORMAL);
        setWidth("1000px");
    }

    private void initPaymentScheduleGrid() {
        paymentScheduleGrid = new Grid<>(Payment.class);
        paymentScheduleGrid.removeAllColumns();
        paymentScheduleGrid.setWidthFull();

        paymentScheduleGrid.addColumn(Payment::getPaymentDate).setCaption(Constants.PAYMENT_DATE);
        paymentScheduleGrid.addColumn(payment -> String.format("%.2f",payment.getPaymentAmount()))
                .setCaption(Constants.PAYMENT_AMOUNT);
        paymentScheduleGrid.addColumn(payment -> String.format("%.2f", payment.getCreditPaymentAmount()))
                .setCaption(Constants.CREDIT_PAYMENT_AMOUNT);
        paymentScheduleGrid.addColumn(payment -> String.format("%.2f", payment.getInterestPaymentAmount()))
                .setCaption(Constants.INTEREST_PAYMENT_AMOUNT);

        paymentScheduleGrid.setItems(creditOffer.getPaymentSchedule());
    }
}
