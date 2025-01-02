package com.aichat.main;

import com.aichat.ui.ChatWindow;

public class MainApp {
    public static void main(String[] args) {
        String languageCode = "en"; // Default language
        int width = 800; // Default width
        int height = 800; // Default height

        // Parse command-line arguments if available
        if (args.length >= 1) {
            languageCode = args[0];
        }
        if (args.length >= 3) {
            try {
                width = Integer.parseInt(args[1]);
                height = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid dimensions provided. Using default size.");
            }
        }

        try {
            // Initialize the chat window
            new ChatWindow(languageCode, width, height);
            System.out.println("Application started with language: " + languageCode + ", size: " + width + "x" + height);
        } catch (Exception e) {
            System.err.println("Failed to start the application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); 
        }
    }
}