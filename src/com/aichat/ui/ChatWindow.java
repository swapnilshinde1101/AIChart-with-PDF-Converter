package com.aichat.ui;

import com.aichat.chatbot.Chatbot;
import com.aichat.utils.FileConverter;
//import com.aichat.utils.FileConverter;
import com.aichat.utils.FileHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ChatWindow {

    private JFrame frame;
    private JTextPane chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton historyButton;
    private JButton newChatButton;
    private JButton uploadFileButton;
    private JButton uploadPhotoButton;
    private JButton convertTextToPdfButton;
    private JButton convertPdfToTextButton;
    private JPanel suggestionPanel;
    private JPanel controlPanel;
    private Chatbot chatbot;
    private StyledDocument doc;
    private Style userStyle;
    private Style botStyle;
    private LoadingIndicator loadingIndicator;

    public ChatWindow(String languageCode, int width, int height) {
        chatbot = new Chatbot(languageCode);
        loadingIndicator = new LoadingIndicator();
        setupUI(width, height);
    }

    public void setupUI(int width, int height) {
        frame = new JFrame("AI Chat Bot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BorderLayout());

        // Top panel for suggestion and feature buttons (40% height)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        suggestionPanel = new JPanel();
        suggestionPanel.setLayout(new BoxLayout(suggestionPanel, BoxLayout.Y_AXIS));
        suggestionPanel.setPreferredSize(new Dimension(width, height / 2));
        suggestionPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        addSuggestedQuestions();

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 4, 10, 10));

        historyButton = createControlButton("History", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadChatHistory();
            }
        });

        newChatButton = createControlButton("New Chat", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewChat();
            }
        });

//        uploadFileButton = createControlButton("Upload File", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                uploadFile();
//            }
//        });

        uploadPhotoButton = createControlButton("Upload Photo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // uploadPhoto();
            }
        });

        convertTextToPdfButton = createControlButton("Text to PDF", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTextToPdf();
            }
        });

        convertPdfToTextButton = createControlButton("PDF to Text", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertPdfToText();
            }
        });


     // Remove these redundant lines:
        controlPanel.add(historyButton);
        controlPanel.add(newChatButton);
//        controlPanel.add(uploadFileButton);
        controlPanel.add(uploadPhotoButton);
        controlPanel.add(convertTextToPdfButton);
        controlPanel.add(convertPdfToTextButton);


        topPanel.add(suggestionPanel, BorderLayout.NORTH);
        topPanel.add(controlPanel, BorderLayout.SOUTH);

        // Bottom panel for chat area (60% height)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setBackground(Color.WHITE);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        doc = chatArea.getStyledDocument();
        userStyle = chatArea.addStyle("User", null);
        StyleConstants.setForeground(userStyle, new Color(0, 123, 255));
        botStyle = chatArea.addStyle("Bot", null);
        StyleConstants.setForeground(botStyle, new Color(0, 123, 0));

        inputField = new JTextField(30);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(0, 123, 255));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.addActionListener(e -> sendMessage());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(inputPanel, BorderLayout.SOUTH);

        // Adding top and bottom panels to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.CENTER);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    private JButton createControlButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(123, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(action);
        return button;
    }

    private void addSuggestedQuestions() {
        String[] suggestions = {
            "How are you?", "What is your name?", "Tell me a joke",
            "What is your purpose?", "What is Java?",
            "How old are you?", "Who created you?", "Can you help me?"
        };

        for (String suggestion : suggestions) {
            JButton button = new JButton(suggestion);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setBackground(Color.WHITE); // Set background color to white
            button.setForeground(Color.BLACK); // Set text color to black
            button.setFocusPainted(false);

            // Wrap each button in a panel for centering
            JPanel buttonWrapper = new JPanel();
            buttonWrapper.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center align the button
            buttonWrapper.setBackground(Color.WHITE); // Match the panel background
            buttonWrapper.add(button);

            button.addActionListener(e -> {
                inputField.setText(suggestion);
                sendMessage();
            });

            suggestionPanel.add(buttonWrapper); // Add the wrapper to the suggestion panel
        }

        suggestionPanel.revalidate();
        suggestionPanel.repaint();
    }


    private void sendMessage() {
        String userInput = inputField.getText();
        if (userInput.trim().isEmpty()) {
            return;
        }

        displayMessage("You", userInput, true);
        inputField.setText("");

        loadingIndicator.showLoading();
        new Thread(new ChatbotResponseTask(userInput)).start();
    }

    private class ChatbotResponseTask extends SwingWorker<String, Void> {
        private String userInput;

        public ChatbotResponseTask(String userInput) {
            this.userInput = userInput;
        }

        @Override
        protected String doInBackground() {
            System.out.println("Fetching response for: " + userInput);
            return chatbot.getResponse(userInput); 
        }

        @Override
        protected void done() {
            try {
                String botResponse = get();
                System.out.println("Received response: " + botResponse);
                loadingIndicator.hideLoading();
                displayMessage("Chatbot", botResponse, false);
            } catch (Exception e) {
                e.printStackTrace();  // Print the stack trace for better error visibility
                loadingIndicator.hideLoading();
                displayMessage("Chatbot", "An error occurred: " + e.getMessage(), false);
            }
        }

    }

    private void displayMessage(String sender, String message, boolean isUser) {
        try {
            if (isUser) {
                doc.insertString(doc.getLength(), sender + ": " + message + "\n", userStyle);
            } else {
                doc.insertString(doc.getLength(), sender + ": " + message + "\n", botStyle);
            }
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void loadChatHistory() {
        String chatHistory = FileHandler.loadChatFromFile(frame);
        if (chatHistory != null) {
            chatArea.setText(chatHistory);
        }
    }

    private void startNewChat() {
        chatArea.setText("");
    }

//    private void uploadFile() {
//        JFileChooser fileChooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text & Word Files", "txt", "docx", "pdf");
//        fileChooser.setFileFilter(filter);
//        int option = fileChooser.showOpenDialog(frame);
//
//        if (option == JFileChooser.APPROVE_OPTION) {
//            File selectedFile = fileChooser.getSelectedFile();
//            displayMessage("System", "File uploaded: " + selectedFile.getName(), false);
//
//            // File processing logic
//            String fileName = selectedFile.getName().toLowerCase();
//            if (fileName.endsWith(".txt")) {
//                JFileChooser saveChooser = new JFileChooser();
//                saveChooser.setDialogTitle("Save PDF file");
//                saveChooser.setSelectedFile(new File(selectedFile.getName().replace(".txt", ".pdf")));
//                int saveOption = saveChooser.showSaveDialog(frame);
//                if (saveOption == JFileChooser.APPROVE_OPTION) {
//                    File pdfFile = saveChooser.getSelectedFile();
//                    FileConverter.convertTextToPdf(frame, selectedFile, pdfFile);
//                }
//            } else if (fileName.endsWith(".docx")) {
//                JFileChooser saveChooser = new JFileChooser();
//                saveChooser.setDialogTitle("Save PDF file");
//                saveChooser.setSelectedFile(new File(selectedFile.getName().replace(".docx", ".pdf")));
//                int saveOption = saveChooser.showSaveDialog(frame);
//                if (saveOption == JFileChooser.APPROVE_OPTION) {
//                    File pdfFile = saveChooser.getSelectedFile();
//                    FileConverter.convertDocxToPdf(frame, selectedFile, pdfFile);
//                }
//            } else {
//                displayMessage("System", "Unsupported file format. Please upload a .txt or .docx file.", false);
//            }
//        }
//    }

    
    
    private void convertTextToPdf() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JFileChooser saveChooser = new JFileChooser();
            saveChooser.setDialogTitle("Save PDF file");
            saveChooser.setSelectedFile(new File(selectedFile.getName().replace(".txt", ".pdf")));
            int saveOption = saveChooser.showSaveDialog(frame);
            if (saveOption == JFileChooser.APPROVE_OPTION) {
                File pdfFile = saveChooser.getSelectedFile();
                FileConverter.convertTextToPdf(frame, selectedFile, pdfFile);
            }
        }
    }

    private void convertPdfToText() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF files", "pdf"));
        int option = fileChooser.showOpenDialog(frame);

        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                // Load the PDF document
                PDDocument document = PDDocument.load(selectedFile);

                // Extract text from the PDF using PDFTextStripper
                PDFTextStripper stripper = new PDFTextStripper();
                String pdfText = stripper.getText(document);
                document.close();

                // Save the extracted text as a .txt file
                JFileChooser saveChooser = new JFileChooser();
                saveChooser.setDialogTitle("Save Text file");
                saveChooser.setSelectedFile(new File(selectedFile.getName().replace(".pdf", ".txt")));
                int saveOption = saveChooser.showSaveDialog(frame);

                if (saveOption == JFileChooser.APPROVE_OPTION) {
                    File textFile = saveChooser.getSelectedFile();

                    try (FileWriter writer = new FileWriter(textFile)) {
                        writer.write(pdfText);
                        JOptionPane.showMessageDialog(frame, "PDF converted to text successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(frame, "Error saving text file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error reading PDF file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new ChatWindow("en", 700, 800);
    }
}
