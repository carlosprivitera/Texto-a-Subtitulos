package main.java;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Dimension;

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
					JLabel lblPrompt = new JLabel("Prompt");
					lblPrompt.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(lblPrompt);
				}
				{
					scrollPane_1 = new JScrollPane();
					panel.add(scrollPane_1);
					{
						txtPrompt = new JTextArea();
						scrollPane_1.setViewportView(txtPrompt);
						txtPrompt.setRows(8);
						txtPrompt.setText(
								"Crear una imágen realista de una oficina donde un grupo de desarrolladores de software planifican un producto software en un tablero KanBan.");
						txtPrompt.setLineWrap(true);
					}
				}
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
				{
					scrollPane = new JScrollPane();
					scrollPane.setSize(new Dimension(0, 100));
					scrollPane.setPreferredSize(new Dimension(3, 100));
					scrollPane.setMinimumSize(new Dimension(22, 100));
					panel.add(scrollPane);
					{
						consoleArea = new JTextArea();
						consoleArea.setLineWrap(true);
						scrollPane.setViewportView(consoleArea);
						consoleArea.setRows(12);
					}
				}
			}
			{
				JPanel panel = new JPanel();
				splitPane.setRightComponent(panel);
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
		slider_escala.setValue(7);
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

		// Construir proceso
		List<String> comando = new ArrayList<>();
		comando.add(pythonVenv);
		comando.add("src-python/generar_imagen_sdxl.py");
		comando.add(prompt);
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
							consoleArea.append(finalLine + "\n");
							consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
						});
					}
				}

				int exitCode = process.waitFor();
				SwingUtilities.invokeLater(() -> {
					if (exitCode == 0) {
						consoleArea.append("✅ Imagen generada: " + nombreSalida + "\n");
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
}
