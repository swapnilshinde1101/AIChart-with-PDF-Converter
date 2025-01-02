package com.aichat.ui;

import javax.swing.*;
import java.awt.*;

public class LoadingIndicator {
    private JDialog loadingDialog;

    public LoadingIndicator() {
        loadingDialog = new JDialog();
        loadingDialog.setUndecorated(true);
        loadingDialog.setLayout(new BorderLayout());
        loadingDialog.setSize(200, 100);
        loadingDialog.setLocationRelativeTo(null); // Center on the screen

        JLabel loadingLabel = new JLabel("Loading...", JLabel.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loadingDialog.add(loadingLabel, BorderLayout.CENTER);

        // Prevent interaction with the background
        loadingDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    }

    public void showLoading() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loadingDialog.setVisible(true);
            }
        });
    }

    public void hideLoading() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loadingDialog.setVisible(false);
            }
        });
    }

}
