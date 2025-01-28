package com.newenergy.inspecao_rei.views;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.newenergy.inspecao_rei.models.Clientes;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

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


        // header
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

        // tabela de itens
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

        // formulário de item
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

        // botão adicionar item
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

        // botão remover item
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

        // botão gerar relatório
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

        JButton saveData = new JButton("Salvar Relatório");
        saveData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cliente = ((Clientes) Objects.requireNonNull(clienteComboBox.getSelectedItem())).getDisplayName();
                String pedidoCompra = pedidoCompraField.getText();
                int rowCount = tableModel.getRowCount();

                if (cliente.isEmpty() || pedidoCompra.isEmpty() || rowCount == 0) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos e adicione ao menos um item antes de salvar.");
                    return;
                }

                try {
                    JSONObject inspecaoJson = new JSONObject();
                    inspecaoJson.put("cliente", cliente);
                    inspecaoJson.put("pedidoCompra", pedidoCompra);
                    inspecaoJson.put("data", LocalDate.now().toString());

                    JSONArray itensArray = new JSONArray();
                    for (int i = 0; i < rowCount; i++) {
                        JSONObject itemJson = new JSONObject();
                        itemJson.put("codigo", tableModel.getValueAt(i, 0));

                        String imagePath = (String) tableModel.getValueAt(i, 1);

                        if (imagePath != null && !imagePath.isEmpty()) {
                            try {
                                String base64Image = encodeImageToBase64(imagePath);
                                itemJson.put("imagem", base64Image);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Erro ao codificar a imagem: " + ex.getMessage());
                                return;
                            }
                        } else {
                            itemJson.put("imagem", JSONObject.NULL); // Caso nenhuma imagem tenha sido selecionada
                        }

                        itensArray.put(itemJson);
                    }

                    inspecaoJson.put("itens", itensArray);

                    String urlInspecao = "http://localhost:8080/inspecao";
                    HttpURLConnection connection = (HttpURLConnection) new URL(urlInspecao).openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");

                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = inspecaoJson.toString().getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 201) {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                            String responseBody = br.readLine();
                            Long inspecaoId = Long.parseLong(responseBody);
                            System.out.println("Inspeção criada com ID: " + inspecaoId);

                            enviarItens(inspecaoId, itensArray);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao salvar a inspeção. Código: " + responseCode);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar a inspeção: " + ex.getMessage());
                }
            }
        });


        JPanel optionButtons = new JPanel();
        optionButtons.add(exportPdfButton);
        optionButtons.add(saveData);

        // botões
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(optionButtons, gbc);

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

    private static String encodeImageToBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Path.of(imagePath));
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private void enviarItens(Long inspecaoId, JSONArray itensArray) throws IOException {
        String urlItens = "http://localhost:8080/inspecao/" + inspecaoId + "/itens";
        HttpURLConnection connection = (HttpURLConnection) new URL(urlItens).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = itensArray.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 200 || responseCode == 201) {
            JOptionPane.showMessageDialog(null, "Itens associados com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao associar os itens. Código: " + responseCode);
        }
    }
}
