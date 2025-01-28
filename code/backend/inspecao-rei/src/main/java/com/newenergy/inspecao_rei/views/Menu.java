package com.newenergy.inspecao_rei.views;

import javax.swing.*;

public class Menu {

    // Método que retorna o JMenuBar
    public static JMenuBar createMenu() {
        // Criando a barra de menus
        JMenuBar menuBar = new JMenuBar();

        // Criando os menus
        JMenu fileMenu = new JMenu("Arquivo");
        JMenu helpMenu = new JMenu("Ajuda");

        // Criando os itens de menu
        JMenuItem home = new JMenuItem("Home");
        JMenuItem newInspecao = new JMenuItem("Nova Inspeção");
        JMenuItem openInspecoes = new JMenuItem("Inspeções");
        JMenuItem exitItem = new JMenuItem("Sair");

        JMenuItem aboutItem = new JMenuItem("Sobre");

        // Adicionando os itens aos menus
        fileMenu.add(home);
        fileMenu.add(newInspecao);
        fileMenu.add(openInspecoes);
        fileMenu.addSeparator(); // Separador
        fileMenu.add(exitItem);

        helpMenu.add(aboutItem);

        // Adicionando os menus à barra de menus
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        // Ações dos itens de menu
        exitItem.addActionListener(e -> System.exit(0));
        home.addActionListener(e -> new MainFrame().setVisible(true));
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Sobre o aplicativo"));

        return menuBar;
    }
}

