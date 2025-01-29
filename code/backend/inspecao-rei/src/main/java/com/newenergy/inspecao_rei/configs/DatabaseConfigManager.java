package com.newenergy.inspecao_rei.configs;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DatabaseConfigManager {
    private static final String CONFIG_FILE = "db_config.properties";
    private static final String DB_PATH_KEY = "database.path";

    public static String getDatabasePath() {
        Properties properties = new Properties();

        if (Files.exists(Paths.get(CONFIG_FILE))) {
            try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
                properties.load(input);
                return properties.getProperty(DB_PATH_KEY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return askUserForDatabasePath();
    }

    private static String askUserForDatabasePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o local para armazenar o banco de dados");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
            saveDatabasePath(selectedPath);
            return selectedPath;
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum diretório selecionado. Encerrando aplicação.");
            System.exit(0);
            return null;
        }
    }

    private static void saveDatabasePath(String path) {
        Properties properties = new Properties();
        properties.setProperty(DB_PATH_KEY, path);

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Database Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

