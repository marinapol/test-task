package com.haulmont.testtask.ui;

import com.vaadin.data.ValidationResult;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

import java.util.List;

public class Notifier {

    public static void showTray(String message) {
        Notification notification = new Notification(null, message, Notification.Type.TRAY_NOTIFICATION);
        notification.show(Page.getCurrent());
    }

    public static void showError(String message) {
        Notification notification = new Notification(null, message, Notification.Type.ERROR_MESSAGE);
        notification.show(Page.getCurrent());
    }

    public static void showValidationErrors(List<ValidationResult> validationErrors) {
        StringBuilder sb = new StringBuilder();
        for (ValidationResult validationError : validationErrors) {
            sb.append(String.format("%s\n", validationError.getErrorMessage()));
        }
        showTray(sb.toString());
    }
}
