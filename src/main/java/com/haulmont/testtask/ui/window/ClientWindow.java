package com.haulmont.testtask.ui.window;

import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.utils.Constants;
import com.vaadin.data.Binder;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

public class ClientWindow extends Window {

    private TextField nameTf;
    private TextField passportIDTf;
    private TextField phoneTf;
    private TextField emailTf;
    private Button acceptAddClientBtn;
    private Button cancelAddClientBtn;

    private Binder<Client> binder;

    private Client client;

    public ClientWindow(String caption) {
        setCaption(caption);
        setWindowMode(WindowMode.NORMAL);
        setModal(true);
    }

    public Binder<Client> getBinder() {
        return binder;
    }

    public Client getClient() {
        client.setName(nameTf.getValue());
        client.setPhone(phoneTf.getValue());
        client.setEmail(emailTf.getValue());
        client.setPassportID(passportIDTf.getValue());
        return client;
    }

    public Button getAcceptAddClientBtn() {
        return acceptAddClientBtn;
    }

    public  void init(Client client) {
        FormLayout addClientFormLayout = new FormLayout();
        HorizontalLayout actions = new HorizontalLayout();

        nameTf = new TextField();
        passportIDTf = new TextField();
        phoneTf = new TextField();
        emailTf = new TextField();
        nameTf.setPlaceholder(Constants.NAME);
        passportIDTf.setPlaceholder(Constants.PASSPORT_NUM);
        phoneTf.setPlaceholder(Constants.PHONE);
        emailTf.setPlaceholder(Constants.EMAIL);

        nameTf.setWidth("360px");
        passportIDTf.setWidth("360px");
        phoneTf.setWidth("360px");
        emailTf.setWidth("360px");

        acceptAddClientBtn = new Button(Constants.OK);
        cancelAddClientBtn = new Button(Constants.CANCEL);
        actions.addComponents(acceptAddClientBtn, cancelAddClientBtn);
        addClientFormLayout.addComponents(nameTf, phoneTf, emailTf, passportIDTf, actions);

        initBinder();
        initButtonsListeners();
        initClient(client);

        setContent(addClientFormLayout);
        setWidth("440px");
        setHeight("290px");
    }

    private void initBinder() {
        binder = new Binder<>();
        binder.forField(nameTf).asRequired(Constants.CLIENT_NAME_AS_REQUIRED)
                .withValidator(s -> (s.length() >= 5 && s.length() <= 60)
                        && s.matches("[a-zA-ZА-Яа-я\\s.]+"), Constants.CLIENT_NAME_CONSTRAINT)
                .bind(Client::getName, Client::setName);
        binder.forField(passportIDTf).asRequired(Constants.CLIENT_PASSPORT_NUM_AS_REQUIRED)
                .withValidator(s -> s.length() <= 60 && s.matches("\\d+"),
                        (Constants.CLIENT_PASSPORT_NUM_CONSTRAINT))
                .bind(Client::getPassportID, Client::setName);
        binder.forField(phoneTf).asRequired(Constants.CLIENT_PHONE_AS_REQUIRED)
                .withValidator(s -> s.matches("\\d{11}$"), Constants.CLIENT_PHONE_CONSTRAINT)
                .bind(Client::getPhone, Client::setPhone);
        binder.forField(emailTf).asRequired(Constants.CLIENT_EMAIL_AS_REQUIRED)
                .withValidator(s -> s.length() <= 60
                                && s.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$"),
                        Constants.CLIENT_EMAIL_CONSTRAINT).bind(Client::getEmail, Client::setEmail);
    }

    private void initButtonsListeners() {
        cancelAddClientBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }

    private void initClient(Client client) {
        this.client = client;
        if (client.getId() != null) {
            nameTf.setValue(client.getName());
            phoneTf.setValue(client.getPhone());
            emailTf.setValue(client.getEmail());
            passportIDTf.setValue(client.getPassportID());
        }
    }
}
