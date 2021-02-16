package com.haulmont.testtask.ui.layout;

import com.haulmont.testtask.dao.BankDAO;
import com.haulmont.testtask.dao.CreditOfferDAO;
import com.haulmont.testtask.entity.Bank;
import com.haulmont.testtask.ui.Notifier;
import com.haulmont.testtask.ui.window.BankWindow;
import com.haulmont.testtask.ui.window.ConfirmWindow;
import com.haulmont.testtask.utils.Constants;
import com.vaadin.data.HasValue;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankLayout extends HorizontalLayout {

    private ComboBox<Bank> bankComboBox;
    private ClientLayout clientLayout;
    private CreditLayout creditLayout;
    private CreditOfferLayout creditOfferLayout;
    private HorizontalLayout buttonsLayout;

    private Button addBtn;
    private Button editBtn;
    private Button deleteBtn;

    private Bank currentBank;
    private List<Bank> banks;
    private BankDAO bankDAO;
    private CreditOfferDAO creditOfferDAO;


    public void setCreditOfferLayout(CreditOfferLayout creditOfferLayout) {
        this.creditOfferLayout = creditOfferLayout;
    }

    public void setClientLayout(ClientLayout clientLayout) {
        this.clientLayout = clientLayout;
    }

    public void setCreditLayout(CreditLayout creditLayout) {
        this.creditLayout = creditLayout;
    }

    public Bank getCurrentBank() {
        return currentBank;
    }

    public void init() {
        bankDAO = new BankDAO();
        creditOfferDAO = new CreditOfferDAO();

        banks = bankDAO.getAllBanks();

        initButtons();
        initButtonsListeners();
        initBankComboBox();

        addComponents(bankComboBox, buttonsLayout);
    }

    private void initBankComboBox() {
        bankComboBox = new ComboBox<>();
        bankComboBox.setItems(banks);
        bankComboBox.setItemCaptionGenerator(Bank::getName);
        bankComboBox.setEmptySelectionAllowed(false);
        bankComboBox.setTextInputAllowed(false);
        if (banks.size() > 0)  {
            bankComboBox.setSelectedItem(banks.get(0));
            setCurrentBank(banks.get(0));
        }
        else {
            clientLayout.setClientsBank(null);
            creditLayout.setCreditsBank(null);
            creditOfferLayout.setCreditOfferBank(null);
        }

        bankComboBox.addValueChangeListener(new HasValue.ValueChangeListener<Bank>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Bank> valueChangeEvent) {
                if (valueChangeEvent.getValue() != null) {
                    editBtn.setEnabled(true);
                    deleteBtn.setEnabled(true);
                }
                setCurrentBank(valueChangeEvent.getValue());
            }
        });
    }

    private void setCurrentBank(Bank currentBank) {
        this.currentBank = currentBank;
        clientLayout.setClientsBank(currentBank);
        clientLayout.setClients(currentBank == null ? Collections.emptyList() : currentBank.getClients());
        creditLayout.setCreditsBank(currentBank);
        creditLayout.setCredits(currentBank == null ? Collections.emptyList() : currentBank.getCredits());
        creditOfferLayout.setCreditOfferBank(currentBank);
        creditOfferLayout.setCreditOffers(currentBank == null ? Collections.emptyList() :
                creditOfferDAO.getAllCreditOffers(currentBank));
    }

    private void initButtons() {
        buttonsLayout = new HorizontalLayout();
        addBtn = new Button(Constants.ADD);
        editBtn = new Button(Constants.EDIT);
        deleteBtn = new Button(Constants.DELETE);
        if (banks.size() == 0) {
            editBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
        }
        buttonsLayout.addComponents(addBtn, editBtn,deleteBtn);
    }

    private void initButtonsListeners() {
        addBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                addBankBtnClick();
            }
        });
        editBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                editBankBtnClick();
            }
        });
        deleteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                deleteBankBtnClick();
            }
        });
    }

    private void addBankBtnClick() {
        Bank newBank = getNewBank();

        BankWindow bankWindow = new BankWindow(Constants.ADD_BANK);
        bankWindow.init(newBank);
        bankWindow.getAcceptAddBankBtn().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (bankWindow.getBinder().validate().isOk()) {
                    bankDAO.saveOrUpdateBank(bankWindow.getBank());
                    banks.add(bankWindow.getBank());
                    bankComboBox.getDataProvider().refreshAll();
                    bankComboBox.setSelectedItem(bankWindow.getBank());
                    Notifier.showTray(Constants.BANK_ADDED);
                    bankWindow.close();
                }
                else {
                    Notifier.showValidationErrors(bankWindow.getBinder().validate().getValidationErrors());
                }
            }
        });
        getUI().addWindow(bankWindow);
    }

    private void editBankBtnClick() {
        BankWindow bankWindow = new BankWindow(Constants.EDIT_BANK);
        bankWindow.init(getCurrentBank());
        bankWindow.getAcceptAddBankBtn().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (bankWindow.getBinder().validate().isOk()) {
                    bankDAO.saveOrUpdateBank(bankWindow.getBank());
                    bankComboBox.getDataProvider().refreshAll();
                    Notifier.showTray(Constants.BANK_EDITED);
                    bankWindow.close();
                }
                else {
                    Notifier.showValidationErrors(bankWindow.getBinder().validate().getValidationErrors());
                }
            }
        });
        getUI().addWindow(bankWindow);
    }

    public void deleteBankBtnClick() {
        ConfirmWindow deleteWindow = new ConfirmWindow(Constants.DELETE_BANK);
        deleteWindow.init(Constants.DELETE_SELECTED_BANK);
        deleteWindow.getAcceptButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    bankDAO.deleteBank(getCurrentBank());
                    banks.remove(getCurrentBank());
                    bankComboBox.getDataProvider().refreshAll();
                    if (banks.size() > 0) {
                        currentBank = banks.get(0);
                        bankComboBox.setSelectedItem(currentBank);
                    } else {
                        currentBank = null;

                        bankComboBox.clear();
                        editBtn.setEnabled(false);
                        deleteBtn.setEnabled(false);
                    }
                    Notifier.showTray(Constants.BANK_DELETED);
                }
                catch (PersistenceException ex) {
                    Notifier.showError(Constants.BANK_HAS_CREDITS);
                }
                deleteWindow.close();
            }
        });
        getUI().addWindow(deleteWindow);
    }

    private Bank getNewBank() {
        Bank bank = new Bank();
        bank.setClients(new ArrayList<>());
        bank.setCredits(new ArrayList<>());
        return bank;
    }
}
