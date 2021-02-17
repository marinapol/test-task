package com.haulmont.testtask.ui.layout;

import com.haulmont.testtask.dao.ClientDAO;
import com.haulmont.testtask.entity.Bank;
import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.ui.window.ClientWindow;
import com.haulmont.testtask.ui.Notifier;
import com.haulmont.testtask.ui.window.ConfirmWindow;
import com.haulmont.testtask.utils.Constants;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.*;
import javax.persistence.PersistenceException;
import java.util.List;

public class ClientLayout extends VerticalLayout {

    private HorizontalLayout buttonsLayout;
    private Grid<Client> clientGrid;
    private Button addBtn;
    private Button editBtn;
    private Button deleteBtn;

    private Bank clientsBank;
    private List<Client> clients;
    private Client selectedClient;

    private ClientDAO clientDAO;

    public void setClientsBank(Bank clientsBank) {
        this.clientsBank = clientsBank;
        addBtn.setEnabled(clientsBank != null);
        clientGrid.getDataProvider().refreshAll();
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
        clientGrid.setItems(this.clients);
    }

    public void init() {
        clientDAO = new ClientDAO();

        initButtonsLayout();
        initButtonsListeners();
        initClientGrid();

        setHeightFull();
        addComponents(buttonsLayout);
        addComponentsAndExpand(clientGrid);
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

    private void initButtonsListeners() {
        addBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                addClientBtnClick();
            }
        });
        editBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                editClientBtnClick();
            }
        });
        deleteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                deleteClientBtnClick();
            }
        });
    }

    private void initClientGrid() {
        clientGrid = new Grid<>(Client.class);
        clientGrid.removeAllColumns();
        clientGrid.setWidthFull();

        clientGrid.addColumn(Client::getName).setCaption(Constants.NAME);
        clientGrid.addColumn(Client::getPhone).setCaption(Constants.PHONE);
        clientGrid.addColumn(Client::getEmail).setCaption(Constants.EMAIL);
        clientGrid.addColumn(Client::getPassportID).setCaption(Constants.PASSPORT_NUM);

        clientGrid.addSelectionListener(new SelectionListener<Client>() {
            @Override
            public void selectionChange(SelectionEvent<Client> selectionEvent) {
                if (selectionEvent.getFirstSelectedItem().isPresent()) {
                    setSelectedClient(selectionEvent.getFirstSelectedItem().get());
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

    private void addClientBtnClick() {
        Client newClient = new Client();
        newClient.setClientBank(clientsBank);

        ClientWindow clientWindow = new ClientWindow(Constants.ADD_CLIENT);
        clientWindow.init(newClient);
        clientWindow.getAcceptAddClientBtn().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (clientWindow.getBinder().validate().isOk()) {
                    clientDAO.saveOrUpdateClient(clientWindow.getClient());
                    clients.add(clientWindow.getClient());
                    clientGrid.getDataProvider().refreshAll();
                    Notifier.showTray(Constants.CLIENT_ADDED);
                    clientWindow.close();
                }
                else {
                    Notifier.showValidationErrors(clientWindow.getBinder().validate().getValidationErrors());
                }
            }
        });
        getUI().addWindow(clientWindow);
    }

    private void editClientBtnClick() {
        ClientWindow clientWindow = new ClientWindow(Constants.EDIT_CLIENT);
        clientWindow.init(getSelectedClient());
        clientWindow.getAcceptAddClientBtn().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (clientWindow.getBinder().validate().isOk()) {
                    clientDAO.saveOrUpdateClient(clientWindow.getClient());
                    clientGrid.getDataProvider().refreshAll();
                    Notifier.showTray(Constants.CLIENT_EDITED);
                    clientWindow.close();
                }
                else {
                    Notifier.showValidationErrors(clientWindow.getBinder().validate().getValidationErrors());
                }
            }
        });
        getUI().addWindow(clientWindow);
    }

    private void deleteClientBtnClick() {
        ConfirmWindow confirmWindow = new ConfirmWindow(Constants.DELETE_CLIENT);
        confirmWindow.init(Constants.DELETE_SELECTED_CLIENT);
        confirmWindow.getAcceptButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    clientDAO.deleteClient(getSelectedClient());
                    clients.remove(getSelectedClient());
                    clientGrid.getDataProvider().refreshAll();
                    clientGrid.deselectAll();
                    Notifier.showTray(Constants.CLIENT_DELETED);
                }
                catch (PersistenceException ex) {
                    Notifier.showError(Constants.CLIENT_HAS_CREDITS);
                }
                confirmWindow.close();
            }
        });
        getUI().addWindow(confirmWindow);
    }
}
