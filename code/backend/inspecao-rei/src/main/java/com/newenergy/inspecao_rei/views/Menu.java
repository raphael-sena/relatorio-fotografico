package com.newenergy.inspecao_rei.views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Menu {

    public static JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Arquivo");
        JMenu helpMenu = new JMenu("Ajuda");

        JMenuItem home = new JMenuItem("Home");
        JMenuItem newInspecao = new JMenuItem("Nova Inspeção");
        JMenuItem openInspecoes = new JMenuItem("Inspeções");
        JMenuItem exitItem = new JMenuItem("Sair");

        JMenuItem aboutItem = new JMenuItem("Sobre");

        fileMenu.add(home);
        fileMenu.add(newInspecao);
        fileMenu.add(openInspecoes);
        fileMenu.addSeparator(); // Separador
        fileMenu.add(exitItem);

        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        exitItem.addActionListener(e -> System.exit(0));
        home.addActionListener(e -> new MainFrame().setVisible(true));
        newInspecao.addActionListener(e -> new NovaInspecao().setVisible(true));
        openInspecoes.addActionListener(e -> new VisualizarInspecoes().setVisible(true));
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Sobre o aplicativo"));

        return menuBar;
    }
}

