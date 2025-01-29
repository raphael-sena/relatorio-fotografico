package com.newenergy.inspecao_rei.views;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.Item;
import org.springframework.web.client.RestTemplate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

public class VisualizarInspecoes extends JFrame {

    private final JTable table;
    private int paginaAtual = 1;
    private List<Inspecao> inspecaoList;

    public VisualizarInspecoes() {
        setTitle("Gerenciador de Relatórios Fotográficos - Visualizar Inspeções");
        setSize(1280, 1024);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setJMenuBar(Menu.createMenu());

        // header
        JLabel title = new JLabel("Visualizar Inspeções");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 20, 10, 20); // Espaçamento
        add(title, gbc);

        table = new JTable();
        updateTabela();

        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 20, 10, 20); // Espaçamento
        add(scrollPane, gbc);

        // Botões de navegação (Anterior/Próximo)
        JPanel navPanel = new JPanel();
        JButton btnAnterior = new JButton("Anterior");
        JButton btnProximo = new JButton("Próximo");

        btnAnterior.addActionListener(e -> navegarPagina(-1));
        btnProximo.addActionListener(e -> navegarPagina(1));

        navPanel.add(btnAnterior);
        navPanel.add(btnProximo);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 20, 20, 20); // Espaçamento
        add(navPanel, gbc);

        // Botão para gerar relatório da inspeção selecionada
        JButton btnGerarRelatorio = new JButton("Gerar Relatório");
        btnGerarRelatorio.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Inspecao inspecaoSelecionada = inspecaoList.get(row);

                // Buscar os itens da inspeção selecionada
                RestTemplate restTemplate = new RestTemplate();
                String urlItens = String.format("http://localhost:8080/inspecao/%d/itens", inspecaoSelecionada.getId());
                Item[] itens = restTemplate.getForObject(urlItens, Item[].class);

                if (itens != null) {
                    // Chama o método de exportação passando os itens
                    PdfExporter.exportarParaPdf(table, inspecaoSelecionada.getCliente(), inspecaoSelecionada.getPedidoCompra(), itens);
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível obter os itens da inspeção.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma inspeção para gerar o relatório.");
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 20, 20, 20);
        add(btnGerarRelatorio, gbc);

        // footer
        JPanel footer = Footer.createFooter();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(10, 20, 20, 20); // Espaçamento
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche horizontalmente
        add(footer, gbc);
    }

    private void updateTabela() {
        // Busca as inspeções da página atual via API
        int itensPorPagina = 10;
        String url = String.format("http://localhost:8080/inspecao?page=%d&size=%d", paginaAtual, itensPorPagina);

        // Faz a requisição para o backend
        RestTemplate restTemplate = new RestTemplate();
        Inspecao[] inspecaoArray = restTemplate.getForObject(url, Inspecao[].class);

        if (inspecaoArray != null) {
            inspecaoList = List.of(inspecaoArray);
        }

        String[] colunas = {"ID", "Data", "Cliente", "Pedido de Compra", "Qtd Itens"};
        Object[][] dados = new Object[inspecaoList.size()][5]; // Agora com 5 colunas

        for (int i = 0; i < inspecaoList.size(); i++) {
            dados[i][0] = inspecaoList.get(i).getId();
            dados[i][1] = inspecaoList.get(i).getData();
            dados[i][2] = inspecaoList.get(i).getCliente();
            dados[i][3] = inspecaoList.get(i).getPedidoCompra();
            dados[i][4] = inspecaoList.get(i).getItens().size(); // Número de itens na inspeção
        }

        table.setModel(new javax.swing.table.DefaultTableModel(dados, colunas));
    }

    private void navegarPagina(int direcao) {
        paginaAtual += direcao;
        if (paginaAtual < 1) paginaAtual = 1;
        updateTabela();
    }

    private void gerarRelatorio() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Inspecao inspecaoSelecionada = inspecaoList.get(row);
            JOptionPane.showMessageDialog(this, "Gerando relatório para a inspeção: " + inspecaoSelecionada.getId());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma inspeção para gerar o relatório.");
        }
    }
}
