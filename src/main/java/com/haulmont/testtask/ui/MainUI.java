package com.haulmont.testtask.ui;

import com.haulmont.testtask.ui.layout.BankLayout;
import com.haulmont.testtask.ui.layout.ClientLayout;
import com.haulmont.testtask.ui.layout.CreditLayout;
import com.haulmont.testtask.ui.layout.CreditOfferLayout;
import com.haulmont.testtask.utils.Constants;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);

        Label bankLabel = new Label(Constants.BANK);

        CreditLayout creditLayout = new CreditLayout();
        creditLayout.init();

        ClientLayout clientLayout = new ClientLayout();
        clientLayout.init();

        CreditOfferLayout creditOfferLayout = new CreditOfferLayout();
        creditOfferLayout.init();

        BankLayout bankLayout = new BankLayout();
        bankLayout.setClientLayout(clientLayout);
        bankLayout.setCreditLayout(creditLayout);
        bankLayout.setCreditOfferLayout(creditOfferLayout);
        bankLayout.init();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth(mainLayout.getWidth()*0.7f, Unit.PERCENTAGE);
        tabSheet.setHeightFull();

        tabSheet.addTab(clientLayout, Constants.CLIENTS);
        tabSheet.addTab(creditLayout, Constants.CREDITS);
        tabSheet.addTab(creditOfferLayout, Constants.CREDIT_OFFERS);

        mainLayout.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        mainLayout.addComponents(bankLabel, bankLayout, tabSheet);
        mainLayout.setExpandRatio(tabSheet, (float) 0.9);

        setContent(mainLayout);
    }
}