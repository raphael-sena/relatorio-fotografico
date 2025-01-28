package com.newenergy.inspecao_rei.views;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Inspeção REI - CEMIG");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setJMenuBar(Menu.createMenu());

        JLabel boasVindas = new JLabel("Bem-vindo, Humberto!");
        boasVindas.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(0, 20, 10, 20); // Espaçamento
        add(boasVindas, gbc);

        // Seção de conteúdo principal
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2, 20, 0)); // 1 linha, 2 colunas, espaçamento horizontal

        JButton novaInspecaoButton = new JButton("Nova Inspeção");
        JButton visualizarInspecaoButton = new JButton("Visualizar Inspeções");

        novaInspecaoButton.addActionListener(e -> {
            System.out.println("Abrir tela de Nova Inspeção");
        });

        visualizarInspecaoButton.addActionListener(e -> {
            System.out.println("Abrir tela de Inspeções Anteriores");
        });

        contentPanel.add(novaInspecaoButton);
        contentPanel.add(visualizarInspecaoButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(10, 20, 10, 20); // Espaçamento
        add(contentPanel, gbc);


        // footer
        JLabel hyperlink = new JLabel("https://raphaelsena.com");
        hyperlink.setForeground(Color.BLUE.darker());
        hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlink.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {

                    Desktop.getDesktop().browse(new URI("https://raphaelsena.com"));

                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        footer.add(new JLabel("© 2025 - Todos os direitos reservados. Desenvolvido por Raphael Sena A. Brito -"));
        footer.add(hyperlink);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(10, 20, 20, 20); // Espaçamento
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche horizontalmente
        add(footer, gbc);

        setVisible(true);
    }

    private String[] parseItems(String json) {
        Gson gson = new Gson();
        List<String> itemList = gson.fromJson(json, new TypeToken<List<String>>() {}.getType());
        return itemList.toArray(new String[0]);
    }
}

