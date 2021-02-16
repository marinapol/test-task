package com.haulmont.testtask.ui.window;

import com.haulmont.testtask.entity.Credit;
import com.haulmont.testtask.utils.Constants;
import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

public class CreditWindow extends Window {

    private TextField limitTf;
    private TextField interestRateTf;
    private Button acceptAddCreditBtn;
    private Button cancelAddCreditBtn;

    private Credit credit;

    private Binder<Credit> binder;

    public CreditWindow(String caption) {
        setCaption(caption);
        setWindowMode(WindowMode.NORMAL);
        setModal(true);
    }

    public Credit getCredit() {
        credit.setLimit(Long.parseLong(limitTf.getValue()));
        credit.setInterestRate(Double.parseDouble(interestRateTf.getValue()));
        return credit;
    }

    public Binder<Credit> getBinder() {
        return binder;
    }

    public Button getAcceptAddCreditBtn() {
        return acceptAddCreditBtn;
    }

    public  void init(Credit credit) {
        FormLayout addCreditFormLayout = new FormLayout();
        HorizontalLayout actions = new HorizontalLayout();

        limitTf = new TextField();
        interestRateTf = new TextField();
        limitTf.setPlaceholder(Constants.LIMIT);
        interestRateTf.setPlaceholder(Constants.INTEREST_RATE);

        limitTf.setWidth("320px");
        interestRateTf.setWidth("320px");

        acceptAddCreditBtn = new Button(Constants.OK);
        cancelAddCreditBtn = new Button(Constants.CANCEL);
        actions.addComponents(acceptAddCreditBtn, cancelAddCreditBtn);
        addCreditFormLayout.addComponents(limitTf, interestRateTf, actions);

        initButtonListener();
        initBinder();
        setContent(addCreditFormLayout);

        setWidth("390px");
        setHeight("190px");
        initCredit(credit);
    }

    private void initBinder() {
        binder = new Binder<>();
        binder.forField(limitTf).asRequired(Constants.CREDIT_LIMIT_AS_REQUIRED)
                .withValidator(s -> s.matches("\\d+"), Constants.CREDIT_LIMIT_CONSTRAINT)
                .bind((ValueProvider<Credit, String>) credit -> Long.toString(credit.getLimit()),
                        (credit, s) -> credit.setLimit(Long.parseLong(s)));
        binder.forField(interestRateTf)
                .asRequired(Constants.CREDIT_LIMIT_TF_AS_REQUIRED)
                .withValidator(s -> s.matches("([0-9]*[.])?[0-9]+"),
                Constants.INTEREST_RATE_CONSTRAINT)
                .bind((ValueProvider<Credit, String>) credit -> Double.toString(credit.getInterestRate()),
                (credit, s) -> credit.setInterestRate(Double.parseDouble(s)));
    }

    private void initButtonListener() {
        cancelAddCreditBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }

    private void initCredit(Credit credit) {
        this.credit = credit;
        if (credit.getId() != null) {
            limitTf.setValue(Long.toString(credit.getLimit()));
            interestRateTf.setValue(Double.toString(credit.getInterestRate()));
        }
    }
}
