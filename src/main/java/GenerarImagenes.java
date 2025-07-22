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
import java.awt.event.ActionEvent;

public class GenerarImagenes extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JSlider slider_1;
	private JSlider slider_3;
	private JSlider slider_4;
	private JSlider slider_5;
	private JLabel lblAlto;
	private JLabel lblDetalles;
	private JLabel lblCalidad;
	private JTextArea txtPrompt;
	private JTextArea consoleArea;

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
				splitPane.setLeftComponent(panel);
				panel.setLayout(new GridLayout(12, 1, 0, 0));
				{
					JLabel lblPrompt = new JLabel("Prompt");
					lblPrompt.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(lblPrompt);
				}
				{
					txtPrompt = new JTextArea();
					txtPrompt.setRows(4);
					txtPrompt.setText(
							"Crear una imágen realista de una oficina donde un grupo de desarrolladores de software planifican un producto software en un tablero KanBan.");
					txtPrompt.setLineWrap(true);
					panel.add(txtPrompt);
				}
				{
					lblNewLabel = new JLabel("Ancho");
					panel.add(lblNewLabel);
				}
				{
					slider_1 = new JSlider();
					slider_1.setMajorTickSpacing(128);
					slider_1.setMinorTickSpacing(64);
					slider_1.setPaintTicks(true);
					slider_1.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							lblNewLabel.setText("Ancho: " + slider_1.getValue() + " px");
						}
					});

					slider_1.setValue(1920);
					slider_1.setMaximum(1920);
					slider_1.setMinimum(512);
					panel.add(slider_1);
				}
				{
					lblAlto = new JLabel("Alto");
					panel.add(lblAlto);
				}
				{
					slider_3 = new JSlider();
					slider_3.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							lblAlto.setText("Alto: " + slider_3.getValue() + " px");
						}
					});
					slider_3.setValue(1080);
					slider_3.setMajorTickSpacing(64);
					slider_3.setMinorTickSpacing(32);
					slider_3.setMaximum(1080);
					slider_3.setMinimum(512);
					slider_3.setPaintTicks(true);
					panel.add(slider_3);
				}
				{
					lblDetalles = new JLabel("Detalles");
					panel.add(lblDetalles);
				}
				{
					slider_4 = new JSlider();
					slider_4.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							lblDetalles.setText("Detalles: " + slider_4.getValue());
						}
					});
					slider_4.setValue(7);
					slider_4.setMinorTickSpacing(2);
					slider_4.setMinimum(1);
					slider_4.setMaximum(20);
					slider_4.setMajorTickSpacing(4);
					slider_4.setPaintTicks(true);
					panel.add(slider_4);
				}
				{
					lblCalidad = new JLabel("Calidad");
					panel.add(lblCalidad);
				}
				{
					slider_5 = new JSlider();
					slider_5.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							lblCalidad.setText("Calidad: " + slider_5.getValue() + "%");
						}
					});
					slider_5.setValue(40);
					slider_5.setMajorTickSpacing(10);
					slider_5.setMinorTickSpacing(5);
					slider_5.setMinimum(10);
					slider_5.setPaintTicks(true);
					panel.add(slider_5);
				}
				{
					JLabel lblSalida = new JLabel("Salida");
					lblSalida.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(lblSalida);
				}
				{
					consoleArea = new JTextArea();
					consoleArea.setRows(6);
					panel.add(consoleArea);
				}
			}
			{
				JPanel panel = new JPanel();
				splitPane.setRightComponent(panel);
			}
			splitPane.setDividerLocation(200);
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
				JButton btnNewButton = new JButton("Generar Imagen");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						generarImagenHilo();
					}
				});
				toolBar.add(btnNewButton);
			}
			{
				JButton btnPararProceso = new JButton("Parar proceso");
				toolBar.add(btnPararProceso);
			}
		}
		miIniciar();
	}

	private void miIniciar() {
		slider_1.setValue(1920);
		slider_3.setValue(1080);
		slider_4.setValue(7);
		slider_5.setValue(40);

	}
    
	private Thread hiloGenerarImagen;
	private void generarImagenHilo() {

		 String prompt = txtPrompt.getText();
			if (prompt == null || prompt.trim().isEmpty()) {
				consoleArea.append("⚠️ El campo de prompt está vacío.\n");
				return;
			}
		    if (prompt == null || prompt.trim().isEmpty()) return;

		    // Parámetros opcionales (pueden venir de otros componentes)
		    String height = "1080";
		    String width = "1920";
		    String scale = "7.5";
		    String steps = "40";
		    String nombreSalida = "imagen_generada.png";

		    String comando = String.format(
		        "python3 src-python/generar_imagen_sdxl.py \"%s\" %s %s %s %s %s",
		        prompt.replace("\"", "\\\""),
		        height, width, scale, steps, nombreSalida
		    );

		    hiloGenerarImagen = new Thread(() -> {
		        try {
		            ProcessBuilder pb = new ProcessBuilder("bash", "-c", comando);
		            pb.directory(new File("salida-imagenes"));
		            pb.redirectErrorStream(true);
		            Process process = pb.start();

		            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
		                String line;
		                while ((line = reader.readLine()) != null) {
		                    String finalLine = line;
		                    SwingUtilities.invokeLater(() -> {
		                        consoleArea.append(finalLine + "\n");
		                        consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
		                    });
		                }
		            }

		            int exitCode = process.waitFor();
		            SwingUtilities.invokeLater(() -> {
		                if (exitCode == 0) {
		                    consoleArea.append("✅ Imagen generada exitosamente.\n");
		                } else {
		                    consoleArea.append("❌ Error al generar imagen. Código: " + exitCode + "\n");
		                }
		            });

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            SwingUtilities.invokeLater(() -> {
		                consoleArea.append("⚠️ Error al generar imagen: " + ex.getMessage() + "\n");
		            });
		        }
		    });

		    hiloGenerarImagen.start();
	   
		
	}
}
