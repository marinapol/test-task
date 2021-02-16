package com.haulmont.testtask.ui.layout;

import com.haulmont.testtask.dao.CreditDAO;
import com.haulmont.testtask.entity.Bank;
import com.haulmont.testtask.entity.Credit;
import com.haulmont.testtask.ui.Notifier;
import com.haulmont.testtask.ui.window.CreditWindow;
import com.haulmont.testtask.utils.Constants;
import com.vaadin.data.ValidationResult;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.*;
import javax.persistence.PersistenceException;
import java.util.List;

public class CreditLayout extends VerticalLayout {

    private HorizontalLayout buttonsLayout;
    private Grid<Credit> creditGrid;
    private Button addBtn;
    private Button editBtn;
    private Button deleteBtn;

    private Bank creditsBank;
    private Credit selectedCredit;
    private List<Credit> credits;

    private CreditDAO creditDAO;

    public void setCreditsBank(Bank creditsBank) {
        this.creditsBank = creditsBank;
        addBtn.setEnabled(creditsBank != null);
    }

    public Credit getSelectedCredit() {
        return selectedCredit;
    }

    public void setSelectedCredit(Credit selectedCredit) {
        this.selectedCredit = selectedCredit;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
        creditGrid.setItems(this.credits);
    }

    public void init() {

        initButtonsLayout();
        initButtonsListeners();
        initCreditGrid();
        addComponents(buttonsLayout, creditGrid);
    }

    private void initCreditGrid() {
        creditGrid = new Grid<>(Credit.class);
        creditGrid.removeAllColumns();
        creditGrid.setWidthFull();

        creditGrid.addColumn(Credit::getLimit).setCaption(Constants.CREDIT_LIMIT);
        creditGrid.addColumn(Credit::getInterestRate).setCaption(Constants.CREDIT_INTEREST_RATE);

        creditGrid.addSelectionListener(new SelectionListener<Credit>() {
            @Override
            public void selectionChange(SelectionEvent<Credit> selectionEvent) {
                if (selectionEvent.getFirstSelectedItem().isPresent()) {
                    setSelectedCredit(selectionEvent.getFirstSelectedItem().get());
                    deleteBtn.setEnabled(true);
                    editBtn.setEnabled(true);
                }
                else {
                    deleteBtn.setEnabled(false);
                    editBtn.setEnabled(false);
                }
            }
        });
    }

    private void initButtonsListeners() {
        addBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                addCreditBtnClick();
            }
        });
        editBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                editCreditBtnClick();
            }
        });
        deleteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                deleteCreditBtnClick();
            }
        });
    }

    public void addCreditBtnClick() {
        creditDAO = new CreditDAO();
        Credit newCredit = new Credit();
        newCredit.setCreditBank(creditsBank);

        CreditWindow creditWindow = new CreditWindow(Constants.ADD_CREDIT);
        creditWindow.init(newCredit);
        creditWindow.getAcceptAddCreditBtn().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (creditWindow.getBinder().validate().isOk()) {
                    creditDAO.saveOrUpdateCredit(creditWindow.getCredit());
                    credits.add(creditWindow.getCredit());
                    creditGrid.getDataProvider().refreshAll();
                    Notifier.showTray(Constants.CREDIT_ADDED);
                    creditWindow.close();
                }
                else {
                    List<ValidationResult> validationResults = creditWindow.getBinder().validate().getValidationErrors();
                    StringBuilder sb = new StringBuilder();
                    for(ValidationResult validationResult : validationResults) {
                        sb.append(String.format("%s\n", validationResult.getErrorMessage()));
                    }
                    Notifier.showTray(sb.toString());
                }
            }
        });
        getUI().addWindow(creditWindow);
    }

    public void editCreditBtnClick() {

        creditDAO = new CreditDAO();
        CreditWindow creditWindow = new CreditWindow(Constants.EDIT_CREDIT);
        creditWindow.init(getSelectedCredit());
        creditWindow.getAcceptAddCreditBtn().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (creditWindow.getBinder().validate().isOk()) {
                    creditDAO.saveOrUpdateCredit(creditWindow.getCredit());
                    creditGrid.getDataProvider().refreshAll();
                    Notifier.showTray(Constants.CREDIT_EDITED);
                    creditWindow.close();
                }
                else {
                    List<ValidationResult> validationResults = creditWindow.getBinder().validate().getValidationErrors();
                    StringBuilder sb = new StringBuilder();
                    for(ValidationResult validationResult : validationResults) {
                        sb.append(String.format("%s\n", validationResult.getErrorMessage()));
                    }
                    Notifier.showTray(sb.toString());
                }
            }
        });
        getUI().addWindow(creditWindow);
    }

    public void deleteCreditBtnClick() {

        creditDAO = new CreditDAO();
        Window deleteCreditWindow = new Window(Constants.DELETE_CREDIT);
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout actions = new HorizontalLayout();
        verticalLayout.addComponent(new Label(Constants.DELETE_SELECTED_CREDIT));
        verticalLayout.addComponent(actions);
        deleteCreditWindow.setModal(true);

        Button acceptDeleteCreditBtn = new Button(Constants.DELETE);
        Button cancelDeleteCreditBtn = new Button(Constants.CANCEL);
        actions.addComponents(acceptDeleteCreditBtn, cancelDeleteCreditBtn);

        acceptDeleteCreditBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    creditDAO.deleteCredit(getSelectedCredit());
                    credits.remove(getSelectedCredit());
                    creditGrid.getDataProvider().refreshAll();
                    creditGrid.deselectAll();
                    Notifier.showTray(Constants.CREDIT_DELETED);
                    deleteCreditWindow.close();
                }
                catch (PersistenceException exception) {
                    Notifier.showError(Constants.CREDIT_HAS_CREDIT_OFFER);
                }
            }
        });
        cancelDeleteCreditBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                deleteCreditWindow.close();
            }
        });
        deleteCreditWindow.setContent(verticalLayout);
        getUI().addWindow(deleteCreditWindow);
    }

    private void initButtonsLayout() {
        buttonsLayout = new HorizontalLayout();

        addBtn = new Button(Constants.ADD);
        editBtn = new Button(Constants.EDIT);
        deleteBtn = new Button(Constants.DELETE);
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);

        buttonsLayout.addComponents(addBtn, editBtn, deleteBtn);
    }

}
