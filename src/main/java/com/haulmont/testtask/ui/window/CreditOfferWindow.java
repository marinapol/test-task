package com.haulmont.testtask.ui.window;

import com.haulmont.testtask.entity.Bank;
import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.entity.Credit;
import com.haulmont.testtask.entity.CreditOffer;
import com.haulmont.testtask.utils.Constants;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;
import java.time.LocalDate;
import java.util.List;

public class CreditOfferWindow extends Window {

    private ComboBox<Client> clientComboBox;
    private ComboBox<Credit> creditComboBox;
    private TextField creditAmountTf;
    private TextField creditTermTf;
    private DateField creditOfferDf;

    private Button acceptBtn;
    private Button cancelBtn;

    private CreditOffer creditOffer;
    private Bank currentBank;
    private LocalDate date;
    private int creditTerm;

    private Binder<CreditOffer> creditOfferFieldsBinder;
    private Binder<CreditOfferWindow> paymentCalculationFieldsBinder;

    public Binder<CreditOffer> getCreditOfferFieldsBinder() {
        return creditOfferFieldsBinder;
    }

    public Binder<CreditOfferWindow> getPaymentCalculationFieldsBinder() {
        return paymentCalculationFieldsBinder;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CreditOffer getCreditOffer() {
        setValuesFromTextFields();
        return creditOffer;
    }

    public Button getAcceptBtn() {
        return acceptBtn;
    }

    public int getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(int term) {
        this.creditTerm = term;
    }

    public void setCurrentBank(Bank bank) {
        currentBank = bank;
    }

    public void init(CreditOffer creditOffer,String caption) {
        FormLayout addCreditOfferLayout = new FormLayout();
        HorizontalLayout actions = new HorizontalLayout();
        initClientComboBox();
        initCreditComboBox();

        creditAmountTf = new TextField();
        creditAmountTf.setPlaceholder(Constants.CREDIT_AMOUNT);
        creditAmountTf.setWidth("370px");
        creditTermTf = new TextField();
        creditTermTf.setPlaceholder(Constants.CREDIT_TERM);
        creditTermTf.setWidth("370px");
        creditOfferDf = new DateField();
        creditOfferDf.setPlaceholder(Constants.CREDIT_DATE);
        creditOfferDf.setWidth("280px");
        creditOfferDf.setTextFieldEnabled(false);

        acceptBtn = new Button(Constants.OK);
        cancelBtn = new Button(Constants.CANCEL);
        actions.addComponents(acceptBtn, cancelBtn);

        initBinders();
        addCreditOfferLayout.addComponents(clientComboBox, creditComboBox,
                creditAmountTf,creditTermTf, creditOfferDf, actions);

        initFieldsListeners();
        initButtonListener();
        initCreditOffer(creditOffer);

        setWindowMode(WindowMode.NORMAL);
        setModal(true);
        setCaption(caption);
        setContent(addCreditOfferLayout);
        setWidth("440px");
        setHeight("350px");
    }

    private void initBinders() {
        creditOfferFieldsBinder = new Binder<>();
        paymentCalculationFieldsBinder = new Binder<>();
        creditOfferFieldsBinder.forField(clientComboBox).asRequired(Constants.CLIENT_AS_REQUIRED)
                .bind(CreditOffer::getClient, CreditOffer::setClient);
        creditOfferFieldsBinder.forField(creditComboBox).asRequired(Constants.INTEREST_RATE_AS_REQUIRED)
                .bind(CreditOffer::getCredit, CreditOffer::setCredit);
        creditOfferFieldsBinder.forField(creditAmountTf).asRequired(Constants.CREDIT_AMOUNT_AS_REQUIRED)
                .withValidator(s -> Long.parseLong(s) <= creditOffer.getCredit().getLimit(),
                        Constants.CREDIT_AMOUNT_CONSTRAINT)
                .bind((ValueProvider<CreditOffer, String>) creditOffer -> Long.toString(creditOffer.getCreditAmount()),
                        (creditOffer, s) -> creditOffer.setCreditAmount(Long.parseLong(s)));
        paymentCalculationFieldsBinder.forField(creditTermTf).asRequired(Constants.CREDIT_TERM_AS_REQUIRED)
                .withValidator(s -> s.matches("\\d+"), Constants.CREDIT_TERM_CONSTRAINT)
                .bind((ValueProvider<CreditOfferWindow, String>) creditOfferWindow ->
                                Integer.toString(creditOfferWindow.getCreditTerm()),
                        (creditOfferWindow, s) -> creditOfferWindow.setCreditTerm(Integer.parseInt(s)));
        paymentCalculationFieldsBinder.forField(creditOfferDf).asRequired(Constants.DATE_AS_REQUIRED)
                .bind(CreditOfferWindow::getDate, CreditOfferWindow::setDate);
    }

    private void initFieldsListeners() {
        clientComboBox.addValueChangeListener(new HasValue.ValueChangeListener<Client>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Client> valueChangeEvent) {
                creditOffer.setClient(valueChangeEvent.getValue());
            }
        });

        creditComboBox.addValueChangeListener(new HasValue.ValueChangeListener<Credit>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Credit> valueChangeEvent) {
                creditAmountTf.setPlaceholder(Constants.BEFORE + Long.toString(valueChangeEvent.getValue().getLimit()));
                creditOffer.setCredit(valueChangeEvent.getValue());
            }
        });

        creditOfferDf.addValueChangeListener(new HasValue.ValueChangeListener<LocalDate>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<LocalDate> valueChangeEvent) {
                setDate(valueChangeEvent.getValue());
            }
        });
    }

    private void initButtonListener() {
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }
    private void initClientComboBox() {
        clientComboBox = new ComboBox<>();
        List<Client> clients = currentBank.getClients();
        clientComboBox.setItems(clients);
        clientComboBox.setItemCaptionGenerator(Client::getName);
        clientComboBox.setTextInputAllowed(false);
        clientComboBox.setPlaceholder(Constants.CLIENT);
        clientComboBox.setEmptySelectionAllowed(false);
        clientComboBox.setWidth("370px");
    }

    private void initCreditComboBox() {
        creditComboBox = new ComboBox<>();
        List<Credit> credits = currentBank.getCredits();
        creditComboBox.setItems(credits);
        creditComboBox.setItemCaptionGenerator(Credit::getStringInterestRate);
        creditComboBox.setPlaceholder(Constants.INTEREST_RATE);
        creditComboBox.setTextInputAllowed(false);
        creditComboBox.setEmptySelectionAllowed(false);
        creditComboBox.setWidth("370px");
    }

    private void setValuesFromTextFields() {
        creditOffer.setCreditAmount(Long.parseLong(creditAmountTf.getValue()));
        setCreditTerm(Integer.parseInt(creditTermTf.getValue()));
    }

    private void initCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
        if (creditOffer.getId() != null) {
            clientComboBox.setValue(creditOffer.getClient());
            creditComboBox.setValue(creditOffer.getCredit());
            creditAmountTf.setValue(Long.toString(creditOffer.getCreditAmount()));
            creditOfferDf.setValue(creditOffer.getPaymentSchedule().get(0).getPaymentDate());
            creditTermTf.setValue(Integer.toString(creditOffer.getPaymentSchedule().size()));
        }
    }
}
