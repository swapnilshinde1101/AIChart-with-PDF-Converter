package com.aichat.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileHandler {

    // Save chat history to a file
    public static void saveChatToFile(JFrame parent, String chatContent) {
        JFileChooser fileChooser = createFileChooser("Save Chat History", "Save", "Text Files", "txt");
        int userSelection = fileChooser.showSaveDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Ensure .txt extension
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            // Confirm overwrite if the file exists
            if (file.exists() && !confirmOverwrite(parent)) {
                return;
            }

            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
                writer.write(chatContent);
                JOptionPane.showMessageDialog(parent, "Chat saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                showError(parent, "Error saving chat: " + e.getMessage());
            }
        }
    }

    // Load chat history from a file
    public static String loadChatFromFile(JFrame parent) {
        JFileChooser fileChooser = createFileChooser("Load Chat History", "", "Text Files", "txt");
        int userSelection = fileChooser.showOpenDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                return Files.readString(file.toPath(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                showError(parent, "Error loading chat: " + e.getMessage());
            }
        }
        return null;
    }

    // Helper method to create a file chooser with common settings
    private static JFileChooser createFileChooser(String title, String buttonLabel, String description, String extension) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setApproveButtonText(buttonLabel);
        fileChooser.setFileFilter(new FileNameExtensionFilter(description, extension));
        return fileChooser;
    }

    // Confirm overwrite dialog
    private static boolean confirmOverwrite(JFrame parent) {
        int confirm = JOptionPane.showConfirmDialog(parent,
                "The file already exists. Do you want to overwrite it?",
                "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }

    // Show error message
    private static void showError(JFrame parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
