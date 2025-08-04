package main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MistralLLM7Bv02 {
    private boolean isRunning = false;
    private boolean pararHilo = false;
    
	public MistralLLM7Bv02() {
		// TODO Auto-generated constructor stub
	}
	private final ExecutorService colaSecuencial = Executors.newSingleThreadExecutor();
	public void ejecutarMistralResponder(String textoEntrada, JTextArea respuestaIA) {
	   // Thread hiloLLM = new Thread(() -> {
		colaSecuencial.submit(() -> {
	        try {
	            String pythonVenv = "python/mistral-venv/bin/python";
	            List<String> comando = new ArrayList<>();
	            comando.add(pythonVenv);
	            comando.add("src-python/mistral_prompt.py");
	            comando.add(textoEntrada);

	            ProcessBuilder pb = new ProcessBuilder(comando);
	            pb.redirectErrorStream(true);
	            Process process = pb.start();

	            try (BufferedReader reader = new BufferedReader(
	                    new InputStreamReader(process.getInputStream()))) {
	                String line;
	                while ((line = reader.readLine()) != null && !pararHilo) {
	                    String finalLine = line;
	                    SwingUtilities.invokeLater(() -> {
	                    	respuestaIA.append(finalLine + "\n");
	                    	respuestaIA.setCaretPosition(respuestaIA.getDocument().getLength());
	                    });
	                }
					if (pararHilo) {
						SwingUtilities.invokeLater(() -> {
							respuestaIA.append("⏹️ Proceso detenido por el usuario.\n");
						});
						//return; // Salir del hilo si se ha solicitado detenerlo
					}
	            }
				SwingUtilities.invokeLater(() -> {
					respuestaIA.append("✅ Esperando a que el proceso termine...\n");
				});	
	            int exitCode = process.waitFor();//esta línea espera a que el proceso termine
	            if (exitCode != 0) {
	                SwingUtilities.invokeLater(() -> {
	                	respuestaIA.append("❌ Error: el proceso terminó con código " + exitCode + "\n");
	                });
				} else {
					SwingUtilities.invokeLater(() -> {
						respuestaIA.append("✅ Proceso completado con éxito.\n");
					});
				}
				isRunning = false; // Marcar que el hilo ha terminado
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
				SwingUtilities.invokeLater(() -> {
					respuestaIA.append("⏹️ Proceso interrumpido.\n");
				});	
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            SwingUtilities.invokeLater(() -> {
	            	respuestaIA.append("⚠️ Excepción: " + ex.getMessage() + "\n");
	            });
	        }
	    });

	    //hiloLLM.start();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setPararHilo(boolean pararHilo) {
		this.pararHilo = pararHilo;
	}
	
	
}
