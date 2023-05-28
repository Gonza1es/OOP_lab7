package com.example.lab7;

import com.example.lab7.functional.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ApplicationController extends JFrame implements ActionListener {

    private JButton run = new JButton("Выбрать файл");

    private final JFileChooser chooser = new JFileChooser("C:\\Users\\1\\IdeaProjects\\lab7\\src\\main\\resources");


    public void runApplication() {
        JFrame f = new JFrame("Lab 7");
        f.setSize(400, 400);

        // set the frame's visibility
        f.setVisible(true);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        run.addActionListener(this);
        JPanel jPanel = new JPanel();
        jPanel.add(run);
        f.add(jPanel);

        f.show();
    }

    private String getFilePayload(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString();
        } catch (IOException exception) {
            throw new IOException("При чтении файла что-то пошло не так: " + exception.getMessage());
        }
    }

    private void showErrorWindow(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        chooser.setFileFilter(filter);
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String payload = getFilePayload(chooser.getSelectedFile());
                Matrix matrix = new Matrix(payload);
                GraphService graphService = new GraphServiceImpl(matrix);
                GraphWindow graphWindow = new StateGraphWindow(graphService.getGraph(), matrix);
                graphWindow.showGraphWindow();
            } catch (IOException | IllegalArgumentException ex) {
                showErrorWindow(ex.getMessage());
            }

        }
    }
}