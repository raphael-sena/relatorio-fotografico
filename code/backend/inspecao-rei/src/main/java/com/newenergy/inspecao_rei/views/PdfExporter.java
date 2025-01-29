package com.newenergy.inspecao_rei.views;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.newenergy.inspecao_rei.models.Item;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PdfExporter {

    public static void exportarParaPdf(JTable table, String cliente, String pedidoCompra, Item[] itens) {
        if (cliente.isEmpty() || pedidoCompra.isEmpty() || itens.length == 0) {
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

                // Criar uma tabela para o título e a data na mesma linha
                PdfPTable headerTable = new PdfPTable(2); // 2 colunas para título e data
                headerTable.setWidthPercentage(100);
                headerTable.setSpacingBefore(12);

                // Título
                PdfPCell titleCell = new PdfPCell(new Phrase("Relatório Fotográfico", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24)));
                titleCell.setBorder(Rectangle.NO_BORDER);
                titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCell.setPadding(4);
                headerTable.addCell(titleCell);

                // Data com grande espaço
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate now = LocalDate.now();
                String formattedDate = now.format(formatter);
                PdfPCell dateCell = new PdfPCell(new Phrase("Data: " + formattedDate, FontFactory.getFont(FontFactory.HELVETICA, 12)));
                dateCell.setBorder(Rectangle.NO_BORDER);
                dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                dateCell.setPadding(4);
                headerTable.addCell(dateCell);

                // Adicionar a tabela ao documento
                document.add(headerTable);

                // Restante do conteúdo
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Cliente: " + cliente));
                document.add(new Paragraph("Pedido de Compra/Projeto: " + pedidoCompra));
                document.add(new Paragraph("\nItens da Inspeção:"));
                document.add(new Paragraph("\n"));

                // Criar layout de tabela flexível para 2 itens por "linha"
                PdfPTable pdfTable = new PdfPTable(2); // 2 colunas para itens
                pdfTable.setWidthPercentage(100);
                pdfTable.setHorizontalAlignment(Element.ALIGN_CENTER);

                // Iterar sobre os itens recebidos da API
                for (Item item : itens) {
                    // Adicionar código e imagem em uma célula
                    PdfPCell cell = new PdfPCell();
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPadding(20);
                    cell.addElement(new Paragraph("CÓDIGO CEMIG: " + item.getCodigo(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                    try {
                        Image img = Image.getInstance(item.getImagem());
                        img.scaleToFit(250, 250);
                        img.setAlignment(Element.ALIGN_BASELINE);
                        cell.addElement(img);
                    } catch (IOException ioEx) {
                        cell.addElement(new Paragraph("Imagem não encontrada", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC)));
                    }

                    pdfTable.addCell(cell);

                    // Adiciona uma célula vazia se for o último item e a linha não estiver completa
                    if (item == itens[itens.length - 1] && itens.length % 2 != 0) {
                        PdfPCell emptyCell = new PdfPCell();
                        emptyCell.setBorder(Rectangle.NO_BORDER);
                        pdfTable.addCell(emptyCell);
                    }
                }

                document.add(pdfTable);
                document.close();

                JOptionPane.showMessageDialog(null, "PDF salvo com sucesso em: " + fileToSave.getAbsolutePath());
            } catch (IOException | DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar o PDF: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação de salvamento cancelada.");
        }
    }
}

