package com.haulmont.testtask.ui.window;

import com.haulmont.testtask.utils.Constants;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

public class ConfirmWindow extends Window {

    VerticalLayout layout;
    HorizontalLayout buttonsLayout;
    Button acceptButton;
    Button cancelButton;

    public Button getAcceptButton() {
        return acceptButton;
    }

    public ConfirmWindow(String caption) {
        setCaption(caption);
        setWindowMode(WindowMode.NORMAL);
        setModal(true);
    }

    public void init(String message) {
        layout = new VerticalLayout();
        buttonsLayout = new HorizontalLayout();
        acceptButton = new Button(Constants.DELETE);
        cancelButton = new Button(Constants.CANCEL);
        buttonsLayout.addComponents(acceptButton, cancelButton);
        layout.addComponents(new Label(message), buttonsLayout);

        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        setContent(layout);
    }

}
