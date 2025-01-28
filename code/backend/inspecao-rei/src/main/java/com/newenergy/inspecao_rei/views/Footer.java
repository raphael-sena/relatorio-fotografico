package com.newenergy.inspecao_rei.views;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Footer {

    public static JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel footerText = new JLabel("© 2025 - Todos os direitos reservados. Desenvolvido por Raphael Sena A. Brito - ");

        JLabel hyperlink = new JLabel("https://raphaelsena.com");

        // Estilizando o hyperlink
        hyperlink.setForeground(Color.BLUE.darker());
        hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://raphaelsena.com"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Adicionando os componentes ao painel
        footer.add(footerText);
        footer.add(hyperlink);
        return footer;
    }
}

