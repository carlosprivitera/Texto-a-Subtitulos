package main.java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class GenerarImagenesHilo {
	private Thread hiloGenerarImagen = null;
	private Process process;
	
	public GenerarImagenesHilo() {
		// TODO Auto-generated constructor stub
	}

    
    
    public void iniciarGeneracionImagen(List<String> comando, String nombreSalida, JTextArea consoleArea, JPanel panel_VistaImagen) {
    	    // Iniciar el hilo para generar la imagen

    	hiloGenerarImagen = new Thread(() -> {
    		//btnGenerarImagen.setEnabled(false);
    		try {
    			ProcessBuilder pb = new ProcessBuilder(comando);

    			pb.directory(new File(".")); // directorio raíz del proyecto
    			pb.redirectErrorStream(true);
    			process = pb.start(); // Esto inicia el proceso

    			// Leer salida del proceso
    			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
    				String line = "";
    				String line_temporal = "";
    				while ((line = reader.readLine()) != null) {
    					final String finalLine = line;
    					CompararCadenas comparar_cadenas = new CompararCadenas();
    					int iguales_s = comparar_cadenas.comparar(line_temporal, line);
    					if (iguales_s < 60) { // Si la línea es diferente en un x% o más
    						SwingUtilities.invokeLater(() -> {
    							consoleArea.append(finalLine + "\n");
    							consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
    						});
    					} else {
    						SwingUtilities.invokeLater(() -> {
    						    String textoActual = consoleArea.getText();
    						    int ultimaLinea = textoActual.lastIndexOf("\n");
    						    String nuevoTexto;
    						    if (ultimaLinea >= 0) {
    						        nuevoTexto = textoActual.substring(0, ultimaLinea) + "\n" + finalLine;
    						    } else {
    						        nuevoTexto = finalLine + "\n";
    						    }
    						    consoleArea.setText(nuevoTexto);
    						    consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
    						});
    					}
    					//Guardar la última línea leída
    					line_temporal = line;
    				}
    			}

    			int exitCode = process.waitFor();
    			SwingUtilities.invokeLater(() -> {
    				if (exitCode == 0) {
    					consoleArea.append("\n✅ Imagen generada: " + nombreSalida + "\n");
    					// Mostrar imagen en el panel
    				    mostrarImagenEnPanel(nombreSalida, panel_VistaImagen, consoleArea);
    				} else {
    					consoleArea.append("❌ Error al generar imagen. Código: " + exitCode + "\n");
    				}
    			});

    		} catch (Exception ex) {
    			ex.printStackTrace();
    			SwingUtilities.invokeLater(() -> {
    				consoleArea.append("⚠️ Error al generar imagen: " + ex.getMessage() + "\n");
    			});
    		} finally {
    			//btnGenerarImagen.setEnabled(true);
    		}
    	});
    	
    	hiloGenerarImagen.start();
    }
    
    
	private void mostrarImagenEnPanel(String rutaImagen, JPanel panel, JTextArea consoleArea) {
	    try {
	        BufferedImage imagen = ImageIO.read(new File(rutaImagen));
	        ImagenEscalable imagenPanel = new ImagenEscalable(imagen);

	        panel.removeAll();
	        panel.setLayout(new BorderLayout());
	        panel.add(imagenPanel, BorderLayout.CENTER);
	        panel.revalidate();
	        panel.repaint();
	    } catch (IOException e) {
	        consoleArea.append("❌ Error IO al cargar imagen para mostrar: " + e.getMessage() + "\n");
		} catch (Exception e) {
			consoleArea.append("❌ Error no determinado al mostrar imagen: " + e.getMessage() + "\n");
		}
	}
	
	public void detenerGeneracionImagen(JTextArea consoleArea) {
		if (process != null && process.isAlive()) {
			try {
				process.destroy(); // O destroyForcibly() si no responde
				Thread.sleep(5000); // Esperar un poco para que el proceso se detenga
				if (process.isAlive()) {
					consoleArea.append("⚠️ El proceso no respondió, forzando detención...\n");
					process.destroyForcibly();
				}
			} catch (Exception e) {
				consoleArea.append("🛑 El proceso dio un error al intentar detenerlo. Error=" + e.getMessage() + "\n");
			}
			consoleArea.append("🛑 Proceso detenido por el usuario.\n");
			//btnGenerarImagen.setEnabled(true);
		} else {
			consoleArea.append("⚠️ No hay proceso de generación de imagen en ejecución para detener.\n");
		}
	}
	
	
	private class ImagenEscalable extends JPanel {
	    private BufferedImage imagen;

	    public ImagenEscalable(BufferedImage img) {
	        this.imagen = img;
	        this.setBackground(Color.BLACK); // Fondo si la imagen no cubre todo
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (imagen != null) {
	            int panelWidth = getWidth();
	            int panelHeight = getHeight();

	            // Escalado proporcional
	            double escalaX = (double) panelWidth / imagen.getWidth();
	            double escalaY = (double) panelHeight / imagen.getHeight();
	            double escala = Math.min(escalaX, escalaY);

	            int ancho = (int) (imagen.getWidth() * escala);
	            int alto = (int) (imagen.getHeight() * escala);

	            int x = (panelWidth - ancho) / 2;
	            int y = (panelHeight - alto) / 2;

	            g.drawImage(imagen, x, y, ancho, alto, this);
	        }
	    }
	}
	
	//Copilot: No olvides cerrar el hilo si es necesario al finalizar la aplicación
	public void cerrarHilo() {
		if (hiloGenerarImagen != null && hiloGenerarImagen.isAlive()) {
			hiloGenerarImagen.interrupt(); // Interrumpir el hilo si está en ejecución
			hiloGenerarImagen = null; // Limpiar la referencia al hilo
		}
		if (process != null && process.isAlive()) {
			process.destroy(); // Asegurarse de que el proceso también se detenga
		}
	}
	
	//Copilot: Me haces un método que retorne verdadero si el hilo de generación de imagen está activo, falso en caso contrario.
	public boolean isHiloGenerarImagenActivo() {
		return hiloGenerarImagen != null && hiloGenerarImagen.isAlive();
	}
	
}
