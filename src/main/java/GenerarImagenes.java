package main.java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Font;

public class GenerarImagenes extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JSlider slider_ancho;
	private JSlider slider_alto;
	private JSlider slider_escala;
	private JSlider slider_pasos;
	private JLabel lblAlto;
	private JLabel lblDetalles;
	private JLabel lblCalidad;
	private JTextArea txtPrompt;
	private JTextArea consoleArea;

	private Process process;

	private Thread hiloGenerarImagen = null;
	private JButton btnNewButton = null;
	private JButton btnGenerarImagen;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JSplitPane splitPane_1;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_VistaImagen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GenerarImagenes dialog = new GenerarImagenes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GenerarImagenes() {
		setModal(true);
		setTitle("Generador de Imágenes");
		setBounds(100, 100, 700, 565);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setContinuousLayout(true);
			contentPanel.add(splitPane);
			{
				JPanel panel = new JPanel();
				panel.setAutoscrolls(true);
				splitPane.setLeftComponent(panel);
				panel.setLayout(new GridLayout(11, 1, 0, 0));
				{
					lblNewLabel = new JLabel("Ancho");
					panel.add(lblNewLabel);
				}
				{
					slider_ancho = new JSlider();
					slider_ancho.setMajorTickSpacing(128);
					slider_ancho.setMinorTickSpacing(64);
					slider_ancho.setPaintTicks(true);
					slider_ancho.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							lblNewLabel.setText("Ancho: " + slider_ancho.getValue() + " px");
						}
					});

					slider_ancho.setValue(1920);
					slider_ancho.setMaximum(1920);
					slider_ancho.setMinimum(512);
					panel.add(slider_ancho);
				}
				{
					lblAlto = new JLabel("Alto");
					panel.add(lblAlto);
				}
				{
					slider_alto = new JSlider();
					slider_alto.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							lblAlto.setText("Alto: " + slider_alto.getValue() + " px");
						}
					});
					slider_alto.setValue(1080);
					slider_alto.setMajorTickSpacing(64);
					slider_alto.setMinorTickSpacing(32);
					slider_alto.setMaximum(1080);
					slider_alto.setMinimum(512);
					slider_alto.setPaintTicks(true);
					panel.add(slider_alto);
				}
				{
					lblDetalles = new JLabel("Detalles");
					panel.add(lblDetalles);
				}
				{
					slider_escala = new JSlider();
					slider_escala.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							lblDetalles.setText("Detalles: " + slider_escala.getValue());
						}
					});
					slider_escala.setValue(7);
					slider_escala.setMinorTickSpacing(2);
					slider_escala.setMinimum(1);
					slider_escala.setMaximum(20);
					slider_escala.setMajorTickSpacing(4);
					slider_escala.setPaintTicks(true);
					panel.add(slider_escala);
				}
				{
					lblCalidad = new JLabel("Calidad");
					panel.add(lblCalidad);
				}
				{
					slider_pasos = new JSlider();
					slider_pasos.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							lblCalidad.setText("Calidad: " + slider_pasos.getValue() + "%");
						}
					});
					slider_pasos.setValue(40);
					slider_pasos.setMajorTickSpacing(10);
					slider_pasos.setMinorTickSpacing(5);
					slider_pasos.setMinimum(10);
					slider_pasos.setPaintTicks(true);
					panel.add(slider_pasos);
				}
			}
			{
				JPanel panel = new JPanel();
				splitPane.setRightComponent(panel);
				panel.setLayout(new BorderLayout(0, 0));
				{
					splitPane_1 = new JSplitPane();
					splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
					panel.add(splitPane_1, BorderLayout.NORTH);
					{
						panel_1 = new JPanel();
						splitPane_1.setLeftComponent(panel_1);
						panel_1.setLayout(new BorderLayout(0, 0));
						{
							scrollPane_1 = new JScrollPane();
							panel_1.add(scrollPane_1);
							{
								txtPrompt = new JTextArea();
								txtPrompt.setFont(new Font("Open Sans Semibold", Font.PLAIN, 14));
								scrollPane_1.setViewportView(txtPrompt);
								txtPrompt.setRows(8);
								txtPrompt.setText("Imagen ultra realista de una oficina de trabajo moderna con ventanales grandes, "
										+ "muy iluminada con luz natural. Hay muchas plantas decorativas y el scritorio de trabajo bien organizado. "
										+ "Está trabajando un moderno Robot en una computadora y tomando una taza de café caliente.");
								txtPrompt.setLineWrap(true);
							}
						}
						{
							JLabel lblPrompt = new JLabel("Prompt y salida del proceso de generación.");
							panel_1.add(lblPrompt, BorderLayout.NORTH);
							lblPrompt.setHorizontalAlignment(SwingConstants.CENTER);
						}
					}
					{
						panel_2 = new JPanel();
						splitPane_1.setRightComponent(panel_2);
						panel_2.setLayout(new BorderLayout(0, 0));
						{
							scrollPane = new JScrollPane();
							panel_2.add(scrollPane);
							scrollPane.setSize(new Dimension(0, 100));
							scrollPane.setPreferredSize(new Dimension(3, 100));
							scrollPane.setMinimumSize(new Dimension(22, 100));
							{
								consoleArea = new JTextArea();
								consoleArea.setLineWrap(true);
								scrollPane.setViewportView(consoleArea);
								consoleArea.setRows(12);
							}
						}
					}
				}
				{
					panel_VistaImagen = new JPanel();
					panel.add(panel_VistaImagen);
					panel_VistaImagen.setLayout(new BorderLayout(0, 0));
				}
			}
			splitPane.setDividerLocation(250);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblNewLabel_1 = new JLabel("Ayuda");
				buttonPane.add(lblNewLabel_1);
			}
		}
		{
			JToolBar toolBar = new JToolBar();
			getContentPane().add(toolBar, BorderLayout.NORTH);
			{
				btnGenerarImagen = new JButton("Generar Imagen");
				btnGenerarImagen.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						generarImagenHilo();
					}
				});
				toolBar.add(btnGenerarImagen);
			}
			{
				JButton btnPararProceso = new JButton("Parar proceso");
				btnPararProceso.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						detenerGeneracionImagen();
					}
				});
				toolBar.add(btnPararProceso);
			}
		}
		miIniciar();
	}

	private void miIniciar() {
		slider_ancho.setValue(1920);
		slider_alto.setValue(1080);
		slider_escala.setValue(12);
		slider_pasos.setValue(40);

	}

	private void generarImagenHilo() {
		String prompt = txtPrompt.getText();

		if (prompt == null || prompt.trim().isEmpty()) {
			consoleArea.append("⚠️ El campo de prompt está vacío.\n");
			return;
		}

		consoleArea.setText(""); // Limpiar consola
		consoleArea.append("🔄 Generando imagen...\n");

		// Leer parámetros desde sliders
		String height = String.valueOf(slider_alto.getValue()); // 512 - 1080
		String width = String.valueOf(slider_ancho.getValue()); // 512 - 1920
		String scale = String.valueOf(slider_escala.getValue()); // 1 - 20
		String steps = String.valueOf(slider_pasos.getValue()); // 10 - 100

		// Generar nombre de archivo con timestamp
		String timestamp = new java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
		String nombreSalida = "salida-imagenes/imagen_" + timestamp + ".png";

		// Ruta al ejecutable Python del entorno virtual
		String pythonVenv = "python/sd-venv/bin/python3"; // adaptá si usás Windows o rutas absolutas

		String negative_prompt = "blurry, deformed, cartoon, low resolution, watermark";
		// Construir proceso
		List<String> comando = new ArrayList<>();
		comando.add(pythonVenv);
		comando.add("src-python/generar_imagen_sdxl.py");
		comando.add(prompt);
		comando.add(negative_prompt);
		comando.add(height);
		comando.add(width);
		comando.add(scale);
		comando.add(steps);
		comando.add(nombreSalida);

		hiloGenerarImagen = new Thread(() -> {
			btnGenerarImagen.setEnabled(false);
			try {
				ProcessBuilder pb = new ProcessBuilder(comando);

				pb.directory(new File(".")); // directorio raíz del proyecto
				pb.redirectErrorStream(true);
				process = pb.start(); // Esto inicia el proceso

				// Leer salida del proceso
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
					String line;
					while ((line = reader.readLine()) != null) {
						final String finalLine = line;
						SwingUtilities.invokeLater(() -> {
							//Copilot, no quiero que se agregue un salto de línea si la línea empieza con un número, debe sobrescribir la línea anterior 
							if (finalLine.matches("^\\d.*")) {
								// Si la línea empieza con un número, sobrescribir la línea anterior
								consoleArea.setText(consoleArea.getText().replaceFirst("(?m)^.*$", finalLine));
							} else {
								// Si no, agregar una nueva línea
								consoleArea.append(finalLine + "\n");
							}
							//consoleArea.append(finalLine + "\n");
							consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
						});
					}
				}

				int exitCode = process.waitFor();
				SwingUtilities.invokeLater(() -> {
					if (exitCode == 0) {
						consoleArea.append("✅ Imagen generada: " + nombreSalida + "\n");
						// Mostrar imagen en el panel
					    mostrarImagenEnPanel(nombreSalida, panel_VistaImagen);
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
				btnGenerarImagen.setEnabled(true);
			}
		});

		hiloGenerarImagen.start();
	}

	private void detenerGeneracionImagen() {
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
			btnGenerarImagen.setEnabled(true);
		} else {
			consoleArea.append("⚠️ No hay proceso de generación de imagen en ejecución para detener.\n");
		}
	}
	
	
	private void mostrarImagenEnPanel(String rutaImagen, JPanel panel) {
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
	
	class ImagenEscalable extends JPanel {
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
	
}
