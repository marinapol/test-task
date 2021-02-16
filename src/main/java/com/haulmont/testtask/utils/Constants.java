package com.haulmont.testtask.utils;

public class Constants {
    public static final String BANK = "Банк";
    public static final String CLIENTS = "Клиенты";
    public static final String CLIENT = "Клиент";
    public static final String CREDITS = "Кредиты";
    public static final String CREDIT_OFFERS = "Кредитные предложения";
    public static final String LIMIT = "Лимит";

    public static final String ADD = "Добавить";
    public static final String EDIT = "Изменить";
    public static final String DELETE = "Удалить";
    public static final String CANCEL = "Отменить";
    public static final String BEFORE = "До ";
    public static final String OK = "Ок";

    public static final String CREDIT_AMOUNT = "Сумма кредита";
    public static final String INTEREST_RATE = "Процентная ставка";
    public static final String CREDIT_PROCESSING = "Оформление кредита";
    public static final String EDIT_CREDIT_OFFER = "Изменить кредитное предложение";
    public static final String DELETE_CREDIT_OFFER = "Удалить кредитное предложение";
    public static final String CREDIT_OFFER_ISSUED = "Кредит успешно оформлен";
    public static final String CREDIT_OFFER_EDITED = "Кредитное предложение успешно изменено";
    public static final String CREDIT_OFFER_DELETED = "Кредитное предложение успешно удалено";

    public static final String ADD_CLIENT = "Добавить клиента";
    public static final String EDIT_CLIENT = "Изменить клиента";
    public static final String DELETE_CLIENT = "Удалить клиента";
    public static final String DELETE_SELECTED_CLIENT = "Удалить выделенного клиента?";
    public static final String CLIENT_ADDED = "Клиент успешно добавлен";
    public static final String CLIENT_EDITED = "Клиент успешно изменён";
    public static final String CLIENT_DELETED = "Клиент успешно удалён";
    public static final String CLIENT_HAS_CREDITS = "У клиента имеются оформленные кредиты";

    public static final String ADD_CREDIT = "Добавить кредит";
    public static final String EDIT_CREDIT = "Изменить кредит";
    public static final String DELETE_CREDIT = "Удалить кредит";
    public static final String DELETE_SELECTED_CREDIT = "Удалить выделенный кредит?";
    public static final String CREDIT_LIMIT = "Лимит по кредиту";
    public static final String CREDIT_INTEREST_RATE = "Процентная ставка";
    public static final String CREDIT_ADDED = "Кредит успешно добавлен";
    public static final String CREDIT_EDITED = "Кредит успешно изменён";
    public static final String CREDIT_DELETED = "Кредит успешно удалён";
    public static final String CREDIT_HAS_CREDIT_OFFER = "По данному кредиту имеются кредитные предложения";
    public static final String MAKE_CREDIT = "Оформить кредит";

    public static final String CREDIT_LIMIT_AS_REQUIRED = "Поле 'Лимит' обязательно для заполнения!";
    public static final String CREDIT_LIMIT_TF_AS_REQUIRED = "Поле 'Лимит' обязательно для заполнения!";
    public static final String CREDIT_LIMIT_CONSTRAINT = "Поле 'Лимит' может содержать только цифры! ";
    public static final String INTEREST_RATE_CONSTRAINT = "Поле 'Процентная ставка' может содержать только цифры и '.'";

    public static final String OPEN_PAYMENT_SCHEDULE = "Открыть график платежей";
    public static final String PAYMENT_SCHEDULE = "График платежей";
    public static final String INTEREST_OVERPAYMENT_AMOUNT = "Итоговая сумма процентов по кредиту: %.2f";
    public static final String TOTAL_CREDIT_AMOUNT = "Итоговая сумма кредита: %.2f";
    public static final String PAYMENT_DATE = "Дата платежа";
    public static final String PAYMENT_AMOUNT = "Сумма платежа";
    public static final String CREDIT_PAYMENT_AMOUNT = "Сумма гашения тела кредита";
    public static final String INTEREST_PAYMENT_AMOUNT = "Сумма гашения процентов";

    public static final String ADD_BANK = "Добавить банк";
    public static final String EDIT_BANK = "Изменить банк";
    public static final String DELETE_BANK = "Удалить банк";
    public static final String DELETE_SELECTED_BANK = "Удалить выделенный банк?";
    public static final String BANK_ADDED = "Банк успешно добавлен";
    public static final String BANK_EDITED = "Банк успешно изменён";
    public static final String BANK_DELETED = "Банк успешно удалён";
    public static final String BANK_NAME = "Название банка";
    public static final String BANK_HAS_CREDITS = "У банка имеются оформленные кредиты";


    public static final String NAME = "ФИО";
    public static final String PHONE = "Телефон";
    public static final String EMAIL = "E-mail";
    public static final String PASSPORT_NUM = "Номер паспорта";

    public static final String BANK_NAME_AS_REQUIRED = "Поле 'Название банка' обязательно для заполнения!";
    public static final String BANK_NAME_CONSTRAINT = "Поле 'Название банка' должно содержать от 3 до 20 символов!";

    public static final String CLIENT_NAME_AS_REQUIRED = "Заполните поле 'ФИО'";
    public static final String CLIENT_PASSPORT_NUM_AS_REQUIRED = "Заполните поле 'Номер паспорта'";
    public static final String CLIENT_PHONE_AS_REQUIRED = "Заполните поле 'Телефон'";
    public static final String CLIENT_EMAIL_AS_REQUIRED = "Заполните поле 'Email'";
    public static final String CLIENT_NAME_CONSTRAINT = "Поле 'ФИО' должно содержать только буквы, от 5 до 60 символов";
    public static final String CLIENT_PASSPORT_NUM_CONSTRAINT = "Поле 'Номер паспорта' может содержать только цифры, не более 20";
    public static final String CLIENT_PHONE_CONSTRAINT = "Поле 'Телефон' должно содержать 11 цифр";
    public static final String CLIENT_EMAIL_CONSTRAINT = "Некорректный 'Email, не более 60 символов";

    public static final String CREDIT_TERM = "Срок кредита (месяцы)";
    public static final String CREDIT_DATE = "Дата оформления кредита";

    public static final String CLIENT_AS_REQUIRED = "Выберите клиента";
    public static final String INTEREST_RATE_AS_REQUIRED = "Выберите процентную ставку";
    public static final String CREDIT_AMOUNT_AS_REQUIRED = "Заполнение поля 'Сумма кредита' обязательно!";
    public static final String CREDIT_TERM_AS_REQUIRED = "Поле 'Срок кредита' обязательно для заполнения!";
    public static final String DATE_AS_REQUIRED ="Поле 'Дата' обязательно для заполнения!";
    public static final String CREDIT_AMOUNT_CONSTRAINT = "Сумма кредита должна быть корректной и не превышать лимит!";
    public static final String CREDIT_TERM_CONSTRAINT = "Поле 'Срок кредита' может содержать только цифры!";


}
