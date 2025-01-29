package com.newenergy.inspecao_rei.views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Gerenciador de Relatórios Fotográficos");
        setSize(1280, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setJMenuBar(Menu.createMenu());

        String userName = System.getProperty("user.name");

        JLabel boasVindas = new JLabel("Bem-vindo(a), " + userName + "!");
        boasVindas.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(0, 20, 10, 20);
        add(boasVindas, gbc);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2, 20, 0));

        JButton novaInspecaoButton = new JButton("Nova Inspeção");
        JButton visualizarInspecaoButton = new JButton("Visualizar Inspeções");

        novaInspecaoButton.addActionListener(e -> {
            new NovaInspecao().setVisible(true);
        });

        visualizarInspecaoButton.addActionListener(e -> {
            new VisualizarInspecoes().setVisible(true);
        });

        contentPanel.add(novaInspecaoButton);
        contentPanel.add(visualizarInspecaoButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(10, 20, 10, 20); // Espaçamento
        add(contentPanel, gbc);

        // footer
        JPanel footer = Footer.createFooter();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(10, 20, 20, 20); // Espaçamento
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche horizontalmente
        add(footer, gbc);

        setVisible(true);
    }
}

