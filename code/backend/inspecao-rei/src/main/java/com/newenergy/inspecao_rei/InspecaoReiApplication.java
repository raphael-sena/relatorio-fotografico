package com.newenergy.inspecao_rei;

import com.newenergy.inspecao_rei.configs.DatabaseConfigManager;
import com.newenergy.inspecao_rei.views.MainFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import javax.swing.SwingUtilities;
import java.io.File;

@SpringBootApplication
public class InspecaoReiApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");

		String databasePath = DatabaseConfigManager.getDatabasePath();
		String jdbcUrl = "jdbc:h2:file:" + databasePath + File.separator + "inspecao_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
		System.setProperty("spring.datasource.url", jdbcUrl);

		SpringApplication.run(InspecaoReiApplication.class, args);
	}

	@EventListener(org.springframework.boot.context.event.ApplicationReadyEvent.class)
	public void startGUI() {
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}
