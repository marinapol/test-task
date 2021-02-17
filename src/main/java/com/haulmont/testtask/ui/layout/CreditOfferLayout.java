package com.haulmont.testtask.ui.layout;

import com.haulmont.testtask.dao.CreditOfferDAO;
import com.haulmont.testtask.entity.Bank;
import com.haulmont.testtask.entity.CreditOffer;
import com.haulmont.testtask.ui.Notifier;
import com.haulmont.testtask.ui.window.ConfirmWindow;
import com.haulmont.testtask.ui.window.CreditOfferWindow;
import com.haulmont.testtask.ui.window.PaymentScheduleWindow;
import com.haulmont.testtask.utils.Constants;
import com.haulmont.testtask.utils.PaymentSchedule;
import com.vaadin.data.ValidationResult;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.*;
import java.time.LocalDate;
import java.util.List;

public class CreditOfferLayout extends VerticalLayout {

    private HorizontalLayout buttonsLayout;
    private Grid<CreditOffer> creditOfferGrid;

    private Button editBtn;
    private Button deleteBtn;
    private Button showPaymentScheduleBtn;
    private Button creditProcessingBtn;


    private List<CreditOffer> creditOffers;
    private CreditOffer selectedCreditOffer;
    private Bank currentBank;

    private CreditOfferDAO creditOfferDAO;

    public Bank getCreditOfferBank() {
        return currentBank;
    }

    public void setCreditOfferBank(Bank bank) {
        currentBank = bank;
        creditProcessingBtn.setEnabled(currentBank != null);
    }

    public CreditOffer getSelectedCreditOffer() {
        return selectedCreditOffer;
    }

    public void setSelectedCreditOffer(CreditOffer selectedCreditOffer) {
        this.selectedCreditOffer = selectedCreditOffer;
    }

    public void setCreditOffers(List<CreditOffer> creditOffers) {
        this.creditOffers = creditOffers;
        creditOfferGrid.setItems(this.creditOffers);
    }

    public void init() {
        creditOfferDAO = new CreditOfferDAO();

        initButtonsLayout();
        initButtonsListeners();
        initCreditOfferGrid();

        setHeightFull();
        addComponent(buttonsLayout);
        addComponentsAndExpand(creditOfferGrid);
    }

    private void initButtonsLayout() {
        buttonsLayout = new HorizontalLayout();

        creditProcessingBtn = new Button(Constants.MAKE_CREDIT);
        editBtn = new Button(Constants.EDIT);
        deleteBtn = new Button(Constants.DELETE);
        showPaymentScheduleBtn = new Button(Constants.OPEN_PAYMENT_SCHEDULE);

        buttonsLayout.addComponents(creditProcessingBtn, editBtn, deleteBtn, showPaymentScheduleBtn);

        setEditDeleteShowPaymentBtnsEnabled(false);
    }

    private void initButtonsListeners() {
        creditProcessingBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                creditProcessingBtnClick();
            }
        });
        editBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                editBtnClick();
            }
        });
        deleteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                deleteBtnClick();
            }
        });
        showPaymentScheduleBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                showPaymentScheduleBtnClick();
            }
        });
    }

    private void initCreditOfferGrid() {
        creditOfferGrid = new Grid<>(CreditOffer.class);
        creditOfferGrid.removeAllColumns();
        creditOfferGrid.setWidthFull();

        creditOfferGrid.addColumn(c -> c.getClient().getName()).setCaption(Constants.CLIENT);
        creditOfferGrid.addColumn(c -> c.getCredit().getInterestRate()).setCaption(Constants.INTEREST_RATE);
        creditOfferGrid.addColumn(CreditOffer::getCreditAmount).setCaption(Constants.CREDIT_AMOUNT);

        creditOfferGrid.addSelectionListener(new SelectionListener<CreditOffer>() {
            @Override
            public void selectionChange(SelectionEvent<CreditOffer> selectionEvent) {
                if (selectionEvent.getFirstSelectedItem().isPresent()) {
                    setSelectedCreditOffer(selectionEvent.getFirstSelectedItem().get());
                    setEditDeleteShowPaymentBtnsEnabled(true);
                }
                else {
                    setEditDeleteShowPaymentBtnsEnabled(false);
                }
            }
        });
    }

    private void creditProcessingBtnClick() {
        CreditOffer newCreditOffer = new CreditOffer();

        CreditOfferWindow creditOfferWindow = new CreditOfferWindow();
        creditOfferWindow.setCurrentBank(getCreditOfferBank());
        creditOfferWindow.init(newCreditOffer, Constants.CREDIT_PROCESSING);
        creditOfferWindow.getAcceptBtn().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (creditOfferWindow.getCreditOfferFieldsBinder().validate().isOk() &&
                    creditOfferWindow.getPaymentCalculationFieldsBinder().validate().isOk()) {
                    CreditOffer offer = creditOfferWindow.getCreditOffer();
                    LocalDate date = creditOfferWindow.getDate();
                    int months = creditOfferWindow.getCreditTerm();

                    offer.setPaymentSchedule(PaymentSchedule.getCalculatedPaymentSchedule(offer, date, months));
                    creditOfferDAO.saveOrUpdateCreditOffer(offer);
                    creditOffers.add(offer);
                    creditOfferGrid.getDataProvider().refreshAll();
                    Notifier.showTray(Constants.CREDIT_OFFER_ISSUED);
                    creditOfferWindow.close();
                }
                else {
                    List<ValidationResult> validationResults = creditOfferWindow.getCreditOfferFieldsBinder()
                            .validate().getValidationErrors();
                    validationResults.addAll(creditOfferWindow.getPaymentCalculationFieldsBinder()
                            .validate().getValidationErrors());
                    Notifier.showValidationErrors(validationResults);
                }
            }
        });

        getUI().addWindow(creditOfferWindow);
    }

    private void editBtnClick() {
        creditOfferDAO = new CreditOfferDAO();
        CreditOfferWindow creditOfferWindow = new CreditOfferWindow();
        creditOfferWindow.setCurrentBank(getCreditOfferBank());
        creditOfferWindow.init(getSelectedCreditOffer(), Constants.EDIT_CREDIT_OFFER);
        creditOfferWindow.getAcceptBtn().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (creditOfferWindow.getCreditOfferFieldsBinder().validate().isOk() &&
                    creditOfferWindow.getPaymentCalculationFieldsBinder().validate().isOk()) {

                    CreditOffer offer = creditOfferWindow.getCreditOffer();
                    LocalDate date = creditOfferWindow.getDate();
                    int months = creditOfferWindow.getCreditTerm();
                    creditOfferDAO.deletePaymentSchedule(offer);
                    offer.setPaymentSchedule(PaymentSchedule.getCalculatedPaymentSchedule(offer, date, months));
                    creditOfferDAO.saveOrUpdateCreditOffer(getSelectedCreditOffer());
                    creditOfferGrid.getDataProvider().refreshAll();
                    Notifier.showTray(Constants.CREDIT_OFFER_EDITED);
                    creditOfferWindow.close();
                }
                else {
                    List<ValidationResult> validationResults = creditOfferWindow.getCreditOfferFieldsBinder()
                            .validate().getValidationErrors();
                    validationResults.addAll(creditOfferWindow.getPaymentCalculationFieldsBinder()
                            .validate().getValidationErrors());
                    StringBuilder sb = new StringBuilder();
                    for(ValidationResult validationResult : validationResults) {
                        sb.append(String.format("%s\n", validationResult.getErrorMessage()));
                    }
                    Notifier.showTray(sb.toString());
                }
            }
        });
        getUI().addWindow(creditOfferWindow);

    }

    private void deleteBtnClick() {
        ConfirmWindow confirmWindow = new ConfirmWindow(Constants.DELETE_CREDIT_OFFER);
        confirmWindow.init(Constants.DELETE_CREDIT_OFFER);
        confirmWindow.getAcceptButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                creditOfferDAO.deleteCreditOffer(getSelectedCreditOffer());
                creditOffers.remove(getSelectedCreditOffer());
                creditOfferGrid.getDataProvider().refreshAll();
                creditOfferGrid.deselectAll();
                Notifier.showTray(Constants.CREDIT_OFFER_DELETED);
                confirmWindow.close();
            }
        });
        getUI().addWindow(confirmWindow);
    }

    private void showPaymentScheduleBtnClick() {
        PaymentScheduleWindow paymentScheduleWindow = new PaymentScheduleWindow();
        paymentScheduleWindow.init(getSelectedCreditOffer(), Constants.PAYMENT_SCHEDULE);
        getUI().addWindow(paymentScheduleWindow);
    }

    private void setEditDeleteShowPaymentBtnsEnabled( boolean enabled) {
        showPaymentScheduleBtn.setEnabled(enabled);
        editBtn.setEnabled(enabled);
        deleteBtn.setEnabled(enabled);
    }
}
