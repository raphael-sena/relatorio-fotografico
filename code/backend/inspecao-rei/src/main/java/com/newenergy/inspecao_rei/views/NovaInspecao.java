package com.newenergy.inspecao_rei.views;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.newenergy.inspecao_rei.models.Clientes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NovaInspecao extends JFrame {

    public NovaInspecao() {
        setTitle("Inspeção REI - Nova Inspeção");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setJMenuBar(Menu.createMenu());

        JLabel title = new JLabel("Nova Inspeção");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(0, 20, 10, 20); // Espaçamento
        add(title, gbc);


        // Header Panel
        JPanel header = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel clienteLabel = new JLabel("Cliente:");
        JComboBox<Clientes> clienteComboBox = new JComboBox<>(Clientes.values());
        clienteComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Clientes) {
                    setText(((Clientes) value).getDisplayName());
                }
                return this;
            }
        });
        JLabel pedidoCompraLabel = new JLabel("Pedido de Compra/Projeto:");
        JTextField pedidoCompraField = new JTextField(20);
        header.add(clienteLabel);
        header.add(clienteComboBox);
        header.add(pedidoCompraLabel);
        header.add(pedidoCompraField);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(header, gbc);

        // Table for Items
        String[] columnNames = {"Código CEMIG", "Imagem"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(tableScrollPane, gbc);

        // Form to Add Items
        JPanel addItemPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel codigoLabel = new JLabel("Código CEMIG:");
        JTextField codigoField = new JTextField(15);
        JLabel imagemLabel = new JLabel("Imagem:");
        JButton uploadButton = new JButton("Upload");
        JLabel imagemPathLabel = new JLabel("Nenhuma imagem selecionada");

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                imagemPathLabel.setText(imagePath);
            }
        });

        addItemPanel.add(codigoLabel);
        addItemPanel.add(codigoField);
        addItemPanel.add(imagemLabel);
        addItemPanel.add(uploadButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(addItemPanel, gbc);

        // Button to Add Item
        JButton addItemButton = new JButton("Adicionar Item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = codigoField.getText();
                String imagem = imagemPathLabel.getText();

                if (codigo.isEmpty() || imagem.equals("Nenhuma imagem selecionada")) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de adicionar um item.");
                } else {
                    tableModel.addRow(new Object[]{codigo, imagem});
                    codigoField.setText("");
                    imagemPathLabel.setText("Nenhuma imagem selecionada");
                }
            }
        });

        // Button to Remove Item
        JButton removeItemButton = new JButton("Remover Item");
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma linha para remover.");
                }
            }
        });

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(addItemButton);
        buttons.add(removeItemButton);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(buttons, gbc);

        // Button to Generate Report
        JButton exportPdfButton = new JButton("Exportar para PDF");
        exportPdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cliente = ((Clientes) clienteComboBox.getSelectedItem()).getDisplayName();
                String pedidoCompra = pedidoCompraField.getText();
                int rowCount = tableModel.getRowCount();

                if (cliente.isEmpty() || pedidoCompra.isEmpty() || rowCount == 0) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos e adicione ao menos um item antes de exportar.");
                    return;
                }

                // Abrir diálogo para salvar arquivo
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Salvar Relatório de Inspeção");
                fileChooser.setSelectedFile(new File("Relatorio_Inspecao.pdf"));

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    try {
                        // Configuração do PDF
                        Document document = new Document();
                        PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                        document.open();

                        // Adicionar cabeçalho geral
                        Paragraph title = new Paragraph("Relatório Fotográfico", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24));
                        title.setSpacingAfter(12);
                        document.add(title);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate now = LocalDate.parse(LocalDate.now().toString(), formatter);
                        document.add(new Paragraph("Data: " + now));
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("Cliente: " + cliente));
                        document.add(new Paragraph("Pedido de Compra/Projeto: " + pedidoCompra));
                        document.add(new Paragraph("\nItens da Inspeção:"));
                        document.add(new Paragraph("\n"));

                        // Criar layout de tabela flexível para 2 itens por "linha"
                        PdfPTable table = new PdfPTable(2); // 2 colunas para itens
                        table.setWidthPercentage(100);
                        table.setHorizontalAlignment(Element.ALIGN_CENTER);

                        for (int i = 0; i < rowCount; i++) {
                            String codigo = (String) tableModel.getValueAt(i, 0);
                            String imagemPath = (String) tableModel.getValueAt(i, 1);

                            // Adicionar código e imagem em uma célula
                            PdfPCell cell = new PdfPCell();
                            cell.setBorder(Rectangle.NO_BORDER);
                            cell.setPadding(20);
                            cell.addElement(new Paragraph("CÓDIGO CEMIG: " + codigo, FontFactory.getFont(FontFactory.HELVETICA, 12)));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                            try {
                                Image img = Image.getInstance(imagemPath);
                                img.scaleToFit(250, 250); // Ajusta o tamanho da imagem
                                img.setAlignment(Element.ALIGN_BASELINE);
                                cell.addElement(img);
                            } catch (IOException ioEx) {
                                cell.addElement(new Paragraph("Imagem não encontrada", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC)));
                            }

                            table.addCell(cell);

                            // Adiciona uma célula vazia se for o último item e a linha não estiver completa
                            if (i == rowCount - 1 && rowCount % 2 != 0) {
                                PdfPCell emptyCell = new PdfPCell();
                                emptyCell.setBorder(Rectangle.NO_BORDER);
                                table.addCell(emptyCell);
                            }
                        }

                        document.add(table);
                        document.close();

                        JOptionPane.showMessageDialog(null, "PDF salvo com sucesso em: " + fileToSave.getAbsolutePath());
                    } catch (IOException | DocumentException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao salvar o PDF: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operação de salvamento cancelada.");
                }
            }
        });

        // Adicionar o botão na interface gráfica
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(exportPdfButton, gbc);


        // footer
        JPanel footer = Footer.createFooter();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.insets = new Insets(10, 20, 20, 20); // Espaçamento
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche horizontalmente
        add(footer, gbc);

        setVisible(true);
    }
}
