package com.haulmont.testtask.ui.window;

import com.haulmont.testtask.entity.Bank;
import com.haulmont.testtask.utils.Constants;
import com.vaadin.data.Binder;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

public class BankWindow extends Window {

    private TextField nameTf;
    private Button acceptAddBankBtn;
    private Button cancelAddBankBtn;

    private Binder<Bank> binder;

    private Bank bank;

    public Bank getBank() {
        return bank;
    }

    public Binder<Bank> getBinder() {
        return binder;
    }

    public Button getAcceptAddBankBtn() {
        return acceptAddBankBtn;
    }

    public BankWindow(String caption) {
        setCaption(caption);
        setWindowMode(WindowMode.NORMAL);
        setModal(true);
    }

    public void init(Bank bank) {
        VerticalLayout addBankLayout = new VerticalLayout();
        HorizontalLayout actions = new HorizontalLayout();

        nameTf = new TextField();
        nameTf.setPlaceholder(Constants.BANK_NAME);
        nameTf.setWidthFull();

        acceptAddBankBtn = new Button(Constants.OK);
        cancelAddBankBtn = new Button(Constants.CANCEL);
        actions.addComponents(acceptAddBankBtn, cancelAddBankBtn);
        addBankLayout.addComponents(nameTf, actions);

        initBinder();
        initListeners();
        initBank(bank);

        setContent(addBankLayout);
    }

    private void initBinder() {
        binder = new Binder<>();
        binder.forField(nameTf).asRequired(Constants.BANK_NAME_AS_REQUIRED)
                .withValidator(s -> (s.length() >= 3 && s.length() <= 20), Constants.BANK_NAME_CONSTRAINT)
                .bind(Bank::getName, Bank::setName);
    }

    private void initListeners() {
        acceptAddBankBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                setBankFields();
            }
        });

        cancelAddBankBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }
    private void setBankFields() {
        bank.setName(nameTf.getValue());
    }

    private void initBank(Bank bank) {
        this.bank = bank;
        if (bank.getId() != null) {
            nameTf.setValue(bank.getName());
        }
    }
}
