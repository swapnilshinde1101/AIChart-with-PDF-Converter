package com.aichat.lang;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LanguageManager {
    private static final String BASE_NAME = "com.aichat.lang.messages";
    private static final String DEFAULT_LANGUAGE_CODE = "en";

    private ResourceBundle bundle;
    private Locale locale;

    // Constructor to initialize language bundle
    public LanguageManager(String languageCode) {
        setLocale(languageCode);
    }

    // Set the language and load the resource bundle
    public void setLocale(String languageCode) {
        try {
            locale = new Locale(languageCode);
            bundle = ResourceBundle.getBundle(BASE_NAME, locale);
        } catch (MissingResourceException e) {
            // Fallback to default language
            locale = new Locale(DEFAULT_LANGUAGE_CODE);
            bundle = ResourceBundle.getBundle(BASE_NAME, locale);
        }
    }

    // Method to fetch a message from the resource file
    public String getMessage(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "Message not found: [" + key + "] for locale: " + locale;
        }
    }

    // Get the current locale
    public Locale getLocale() {
        return locale;
    }
}
