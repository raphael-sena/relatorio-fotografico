package com.newenergy.inspecao_rei;

import com.newenergy.inspecao_rei.views.MainFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class InspecaoReiApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(InspecaoReiApplication.class, args);
	}

	@EventListener(org.springframework.boot.context.event.ApplicationReadyEvent.class)
	public void startGUI() {
		// Inicializa a interface gráfica na Event Dispatch Thread
		SwingUtilities.invokeLater(() -> {
			new MainFrame().setVisible(true); // Exibe a janela
		});
	}

}
