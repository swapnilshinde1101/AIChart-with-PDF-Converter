package com.aichat.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

public class FileConverter {

    // Convert Text File (.txt) to PDF
    public static void convertTextToPdf(JFrame parent, File txtFile, File pdfFile) {
        try {
            // Read the content of the text file
            String content = Files.readString(txtFile.toPath(), StandardCharsets.UTF_8);
            
            // Create a new PDF document
            PDDocument document = new PDDocument();

            // Create a new page
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a content stream for the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            // Set the font and size for the content
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750); // Position the text on the page
            
            // Split the content into lines (to prevent overflowing)
            String[] lines = content.split("\n");
            for (String line : lines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -15); // Move to the next line
            }
            
            // End the content stream
            contentStream.endText();
            contentStream.close();
            
            // Save the document to the specified file
            document.save(pdfFile);
            document.close();

            // Notify the user of successful conversion
            JOptionPane.showMessageDialog(parent, "Text file converted to PDF successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent, "Error reading the text file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
