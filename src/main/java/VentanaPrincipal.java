package main.java;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.Component;
import java.awt.Desktop;

import javax.swing.Box;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JSplitPane;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class VentanaPrincipal {
	private String nombreArchivoTextoAbierto=""; // Nombre del archivo abierto, vacio si no hay ninguno abierto.
	private String rutaArchivoTextoAbierto="."; // La ruta es el directorio actual, vasio si no hay un archivo abierto.
	private String nombreRutaArchivoTextoAbierto=""; // Nombre y ruta del archivo abierto, vacio si no hay ninguno abierto.
	private boolean archivoTextoAbiertoEditado=false; // Indica si el archivo con el texto técnico se ha modificado. Si es true hay que activar el botón Guardar.
	// 01
	//private static String currentDirectory = "";
	private static final String directorioRaizProyectoJava = System.getProperty("user.dir");
	private JFrame frmTextoASubttulos;
	//private JTextArea textArea;
	// private JTextArea textArea_1;
	private JComboBox list = null;
	private JComboBox list_1;
	private JScrollPane scrollPane_1;
	private JTextArea textoTecnico;
	private static VentanaPrincipal window = null;
	//private String archivoTextoTecnicoAbierto = "";
	private final String tituloVentanaPrincipal = "Texto a subtítulos v15-06-24";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// aaaaassss
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new VentanaPrincipal();
					//currentDirectory = System.getProperty("user.dir");
					// window.frmTextoASubttulos.setTitle(tituloVentanaPrincipal);
					// Copilot, centrar la ventana en la pantalla
					window.frmTextoASubttulos.setLocationRelativeTo(null);
					window.frmTextoASubttulos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
		textoTecnico.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {//Se llama cuando se inserta texto
				btnGuardarTxtTecnico.setEnabled(true);
				archivoTextoAbiertoEditado = true; // Indicar que el archivo ha sido editado
				frmTextoASubttulos.setTitle(nombreArchivoTextoAbierto + " *"); // Agregar asterisco al título
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {//Se ejecuta cuando se elimina texto
				btnGuardarTxtTecnico.setEnabled(true);
				archivoTextoAbiertoEditado = true; // Indicar que el archivo ha sido editado
				frmTextoASubttulos.setTitle(nombreArchivoTextoAbierto + " *"); // Agregar asterisco al título
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {//Se ejecuta cuando se cambia el formato del texto, pero no el contenido
				btnGuardarTxtTecnico.setEnabled(true);
				archivoTextoAbiertoEditado = true; // Indicar que el archivo ha sido editado
				frmTextoASubttulos.setTitle(nombreArchivoTextoAbierto + " *"); // Agregar asterisco al título
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	JButton btnNewButton_2 = null;
	private JButton btnGuardarTxtTecnico;
	private JButton btnGuardarComoTxtTecnico;
	private JLabel lblNewLabel;

	private void initialize() {
		frmTextoASubttulos = new JFrame();
		frmTextoASubttulos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//lblNewLabel.setText("Ayuda: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "  -" + nombreRutaArchivoTextoAbierto);
				if (nombreRutaArchivoTextoAbierto.isEmpty()) {
					lblNewLabel.setText("Ayuda: No hay un archivo abierto.");
				} else {
					// lblNewLabel.setText("Ayuda: " +
					// LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy
					// HH:mm:ss")) + " -" + nombreRutaArchivoTextoAbierto);
				   lblNewLabel.setText("Ayuda: " + nombreRutaArchivoTextoAbierto);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel.setText("Ayuda:");
			}
		});
		frmTextoASubttulos.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
		});
		frmTextoASubttulos.setTitle(tituloVentanaPrincipal);
		frmTextoASubttulos.setBounds(100, 100, 786, 524);
		frmTextoASubttulos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(null, 5, true));
		frmTextoASubttulos.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.NORTH);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				
				if ((nombreArchivoTextoAbierto.isEmpty()==false) && (archivoTextoAbiertoEditado==false)) {
					lblNewLabel.setText("Ayuda: Hay un archivo abierto y no hay cambios en pantalla para guardar." + " Salida inmediata.");
				}				
				if ((nombreArchivoTextoAbierto.isEmpty()==true) && (archivoTextoAbiertoEditado==false)) {
					lblNewLabel.setText("Ayuda: No hay un archivo abierto y no hay cambios en pantalla para guardar." + " Salida inmediata.");
				}
				if ((nombreArchivoTextoAbierto.isEmpty()==true) && (archivoTextoAbiertoEditado==true)) {
					lblNewLabel.setText("Ayuda: No hay un archivo abierto y hay cambios en pantalla para guardar." + " Debería guardar como antes de salir.");
				} 
				if ((nombreArchivoTextoAbierto.isEmpty()==false) && (archivoTextoAbiertoEditado==true)) {
					lblNewLabel.setText("Ayuda: Hay un archivo abierto y hay cambios en pantalla para guardar." + " Debería guardar antes de salir.");
				} 
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel.setText("Ayuda:");
			}
		});
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean salir = true;
				if ((nombreArchivoTextoAbierto.isEmpty()==true) && (archivoTextoAbiertoEditado==true)) {
					//lblNewLabel.setText("Ayuda: No hay un archivo abierto y hay cambios en pantalla para guardar." + " Debería guardar como antes de salir.");
					 salir = guardarComoArchivoTxtTecnico();
				} 
				if ((nombreArchivoTextoAbierto.isEmpty()==false) && (archivoTextoAbiertoEditado==true)) {
					//lblNewLabel.setText("Ayuda: Hay un archivo abierto y hay cambios en pantalla para guardar." + " Debería guardar antes de salir.");
					salir = guardarArchivoTxtTecnico();
				} 
				if (salir==true) {
				  System.exit(0);
				}else {
					Beep.miBeep();
					lblNewLabel.setText("Ayuda: No se pudo guardar el archivo.");
					//Mostrar un JOptionPane indicando para dar la opción de salir sin guardar o no salir y continuar trabajando.
					int response = JOptionPane.showConfirmDialog(frmTextoASubttulos,
							"¿Desea salir sin guardar los cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						System.exit(0);
					} else {
						return; // Cancelar la acción si el usuario elige no salir
					}
				}
			}
		});
		toolBar.add(btnSalir);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_1);

		JButton btnNewButton_6 = new JButton("Navegar directorio de proyecto");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Copilot, abrir el explorador de archivos en el directorio raíz del proyecto
				// Java, usar java.awt.Desktop compatible con Windows, Linux y MacOS
				try {
					// Copilot, abrir el explorador de archivos en el directorio raíz del proyecto
					// Java
					File dir = new File(directorioRaizProyectoJava);
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(dir);
					} else {
						JOptionPane.showMessageDialog(frmTextoASubttulos,
								"El escritorio no es compatible con esta operación.");
					}
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(frmTextoASubttulos,
							"Error al abrir el directorio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(btnNewButton_6);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		toolBar.add(verticalStrut_1);

		JLabel lblNewLabel_1 = new JLabel("SEP");
		lblNewLabel_1.setToolTipText("Segundos entre párrafos");
		toolBar.add(lblNewLabel_1);

		list = new JComboBox();
		list.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
		list.setSelectedIndex(0);
		toolBar.add(list);

		JLabel lblNewLabel_2 = new JLabel("MPP");
		lblNewLabel_2.setToolTipText("Milisegundos por letra");
		toolBar.add(lblNewLabel_2);

		list_1 = new JComboBox();
		list_1.setModel(new DefaultComboBoxModel(new String[] { "30", "35", "40", "45", "50", "55", "60", "65", "70",
				"75", "80", "85", "90", "95", "100", "110", "120", "130", "140" }));
		list_1.setSelectedIndex(6);
		toolBar.add(list_1);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(5);
		splitPane.setDividerLocation(400);
		panel.add(splitPane, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar_1 = new JToolBar();
		panel_3.add(toolBar_1, BorderLayout.NORTH);

		JButton btnAbrirTxtTecnico = new JButton("Abrir");
		btnAbrirTxtTecnico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean abrir = true;
				if ((nombreArchivoTextoAbierto.isEmpty()==true) && (archivoTextoAbiertoEditado==true)) {
					//lblNewLabel.setText("Ayuda: No hay un archivo abierto y hay cambios en pantalla para guardar." + " Debería guardar como antes de salir.");
					 abrir = guardarComoArchivoTxtTecnico();
				} 
				if ((nombreArchivoTextoAbierto.isEmpty()==false) && (archivoTextoAbiertoEditado==true)) {
					//lblNewLabel.setText("Ayuda: Hay un archivo abierto y hay cambios en pantalla para guardar." + " Debería guardar antes de salir.");
					abrir = guardarArchivoTxtTecnico();
				} 
				if (abrir==false) {
					Beep.miBeep();
					lblNewLabel.setText("Ayuda: No se pudo guardar el archivo.");			
					int response = JOptionPane.showConfirmDialog(frmTextoASubttulos,
							"¿Desea abrir un nuevo archivo sin guardar los cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.NO_OPTION) {
						return; // Cancelar la acción si el usuario elige no abrir un nuevo archivo
					} 				
				} 				
				btnAbrirTxtTecnico.setEnabled(false);
		        btnAbrirTxtTecnico_actionPerformed(e);
		        btnAbrirTxtTecnico.setEnabled(true);
			}
		});

		JButton btnNuevoArchivo = new JButton("Nuevo");
		btnNuevoArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean nuevo = true;
				if ((nombreArchivoTextoAbierto.isEmpty()==true) && (archivoTextoAbiertoEditado==true)) {
					//lblNewLabel.setText("Ayuda: No hay un archivo abierto y hay cambios en pantalla para guardar." + " Debería guardar como antes de salir.");
					 nuevo = guardarComoArchivoTxtTecnico();
				} 
				if ((nombreArchivoTextoAbierto.isEmpty()==false) && (archivoTextoAbiertoEditado==true)) {
					//lblNewLabel.setText("Ayuda: Hay un archivo abierto y hay cambios en pantalla para guardar." + " Debería guardar antes de salir.");
					nuevo = guardarArchivoTxtTecnico();
				} 
				if (nuevo==false) {
					Beep.miBeep();
					lblNewLabel.setText("Ayuda: No se pudo guardar el archivo.");			
					int response = JOptionPane.showConfirmDialog(frmTextoASubttulos,
							"¿Desea crear un nuevo archivo sin guardar los cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.NO_OPTION) {
						return; // Cancelar la acción si el usuario elige no crear un nuevo archivo
					} 				
				} 
				textoTecnico.setText(""); // Limpiar el JTextArea
	        	nombreArchivoTextoAbierto = ""; // Reiniciar el nombre del archivo
		        frmTextoASubttulos.setTitle(tituloVentanaPrincipal); // Reiniciar el título de la ventana
		        btnGuardarTxtTecnico.setEnabled(false); // Deshabilitar el botón de guardar	
		        archivoTextoAbiertoEditado = false; // Reiniciar el estado de edición
		        rutaArchivoTextoAbierto = "."; // Reiniciar la ruta del directorio
		        nombreRutaArchivoTextoAbierto = ""; // Reiniciar el nombre y ruta del archivo abierto                
			}
		});
		toolBar_1.add(btnNuevoArchivo);
		btnAbrirTxtTecnico.setToolTipText("Abrir un archivo TXT");
		toolBar_1.add(btnAbrirTxtTecnico);

		btnGuardarComoTxtTecnico = new JButton("Guardar...");
		btnGuardarComoTxtTecnico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGuardarComoTxtTecnico.setEnabled(false);
				guardarComoArchivoTxtTecnico();
				btnGuardarComoTxtTecnico.setEnabled(true);

			}
		});

		btnGuardarTxtTecnico = new JButton("Guardar");
		btnGuardarTxtTecnico.setEnabled(false);
		btnGuardarTxtTecnico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarArchivoTxtTecnico();
			}
		});
		btnGuardarTxtTecnico.setToolTipText("Guarda un archivo TXT abierto.");
		toolBar_1.add(btnGuardarTxtTecnico);
		btnGuardarComoTxtTecnico
				.setToolTipText("Guardar en un archivo TXT con nuevo nombre, o sobrescribir un archivo existente.");
		toolBar_1.add(btnGuardarComoTxtTecnico);

		JButton btnNewButton_1 = new JButton("Pegar");
		btnNewButton_1.setToolTipText("Pegar desde el porta papeles");
		toolBar_1.add(btnNewButton_1);

		JButton btnNewButton_5 = new JButton("srt ==>");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_5.setEnabled(false);
				// Copilot, llamar al método miButton_1actionPerformed() para convertir el texto
				// a subtítulos
				btnNewButton_5actionPerformed(e);
				btnNewButton_5.setEnabled(true);
			}
		});

		JButton btnNewButton_7 = new JButton("Prompt");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarVentanaGenerarPrompt();
			}
		});
		toolBar_1.add(btnNewButton_7);
		toolBar_1.add(btnNewButton_5);

		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));

		scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewButton_1.setEnabled(false);
				miButton_1actionPerformed(arg0);
				btnNewButton_1.setEnabled(true);
			}
		});

		JPanel panel_4 = new JPanel();
		splitPane.setRightComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_4.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Nimbus Mono PS", Font.PLAIN, 16));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);


		textoTecnico = new JTextArea();
		textoTecnico.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
				// No se usa, pero es necesario para implementar el InputMethodListener
				
			}
			public void inputMethodTextChanged(InputMethodEvent event) {
				//JOptionPane.showMessageDialog(frmTextoASubttulos,
					//	"El texto técnico ha sido modificado. No olvides guardar los cambios.");
			}
		});
		textoTecnico.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				//JOptionPane.showMessageDialog(frmTextoASubttulos,
				//		"El texto técnico ha sido modificado. No olvides guardar los cambios.");
			}
		});
		textoTecnico.setLineWrap(true);
		scrollPane_1.add(textoTecnico);
		textoTecnico.setFont(new Font("Open Sans", Font.PLAIN, 16));
		textoTecnico.setText(
				"Hola, procesando títulos \nEste es el segundo subtítulo, dividido en dos subtítulos.\nEste es el cuarto subtítulo.\n");
		scrollPane_1.setViewportView(textoTecnico);

		lblNewLabel = new JLabel("Ayuda");
		lblNewLabel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}
		});
		frmTextoASubttulos.getContentPane().add(lblNewLabel, BorderLayout.SOUTH);
	}

	protected void cargarVentanaGenerarPrompt() {
		// TODO Auto-generated method stub
		GenerarPrompt generarPrompt = new GenerarPrompt();
		generarPrompt.cargarTextoTecnico(textoTecnico);
		generarPrompt.setLocationRelativeTo(frmTextoASubttulos);
		generarPrompt.setVisible(true);
		generarPrompt.setModal(true);
		generarPrompt.setTitle("Generar Prompt");

	}



	protected boolean guardarArchivoTxtTecnico() {
			try {
				Files.write(Paths.get(nombreRutaArchivoTextoAbierto), textoTecnico.getText().getBytes());
				JOptionPane.showMessageDialog(frmTextoASubttulos,
						"Archivo guardado correctamente: " + nombreArchivoTextoAbierto, "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				return true; // Indicar que el guardado fue exitoso
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(frmTextoASubttulos, "Error al guardar el archivo: " + ex.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
				return false; // Indicar que hubo un error al guardar
			}
	}

	protected boolean guardarComoArchivoTxtTecnico() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(rutaArchivoTextoAbierto)); // Usar por defecto el directorio actual con "."
		fileChooser.setDialogTitle("Guardar archivo de texto, seleccionar ruta y nombre.");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showSaveDialog(frmTextoASubttulos);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				// Guardar el contenido del JTextArea en el archivo seleccionado por el usuario con el JFileChooser
				Files.write(Paths.get(selectedFile.getAbsolutePath()), textoTecnico.getText().getBytes());
				JOptionPane.showMessageDialog(frmTextoASubttulos,
						"Archivo guardado correctamente: " + selectedFile.getAbsolutePath(), "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				nombreArchivoTextoAbierto = selectedFile.getName(); // Guardar el nombre del archivo abierto
				rutaArchivoTextoAbierto = selectedFile.getParent(); // Guardar la ruta del directorio del archivo abierto
				nombreRutaArchivoTextoAbierto = selectedFile.getAbsolutePath(); // Guardar el nombre y ruta del archivo abierto
				frmTextoASubttulos.setTitle(nombreArchivoTextoAbierto);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(frmTextoASubttulos,
						"Error al guardar con otro nombre en un archivo: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return false; // Indicar que hubo un error al guardar
			}
		} else {
			JOptionPane.showMessageDialog(frmTextoASubttulos, "Operación cancelada por el usuario.", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			return false; // Indicar que el usuario canceló la operación
		}
		return true; // Indicar que el guardado fue exitoso
	}

	private void btnAbrirTxtTecnico_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(rutaArchivoTextoAbierto));
		fileChooser.setDialogTitle("Seleccionar archivo de texto para convertir a subtítulos.");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showOpenDialog(frmTextoASubttulos);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				String content = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
				textoTecnico.setText(content);
				nombreArchivoTextoAbierto = selectedFile.getName(); // Guardar el nombre del archivo abierto
				rutaArchivoTextoAbierto = selectedFile.getParent(); // Guardar la ruta del directorio del archivo abierto
				nombreRutaArchivoTextoAbierto = selectedFile.getAbsolutePath(); // Guardar el nombre y ruta del archivo abierto
				frmTextoASubttulos.setTitle(nombreArchivoTextoAbierto); // Actualizar el título de la ventana
				archivoTextoAbiertoEditado = false; // Reiniciar el estado de edición
				btnGuardarTxtTecnico.setEnabled(false); // Deshabilitar el botón de guardar
				
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(frmTextoASubttulos, "Error al leer el archivo: " + ex.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(frmTextoASubttulos, "Operación cancelada por el usuario.", "Información",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}



	private String convertToSRT(Float timeBetweenParagraphs, Float timeBetweenLetters, String textoTXTaSRT)
			throws IOException, UnsupportedFlavorException {
		// Get text from clipboard
//##        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//##        String text = (String) clipboard.getData(DataFlavor.stringFlavor);
//##        String text2 = (String) clipboard.getData(DataFlavor.stringFlavor);
		// Procesar los párrafos separandolos.
		String text2 = procesarTextoParrafos(textoTXTaSRT);
		/////////////////////////////////////////////
		// Procesar los párrafos separando las comas.
		text2 = procesarTextoComas(text2);
		/////////////////////////////////////////////
//##        this.txtrHolaEsteEs.setText(text);
		String[] paragraphs = text2.split("\n");
		StringBuilder srt = new StringBuilder();
		int counter = 1;
		float currentTime = 0.0f;

		for (String paragraph : paragraphs) {
			paragraph = paragraph.trim();
			if (!paragraph.isEmpty()) {
				int letterCount = paragraph.length();
				float duration = letterCount * timeBetweenLetters;

				String startTime = formatTime(currentTime);
				String endTime = formatTime(currentTime + duration);

				srt.append(counter).append("\n");
				srt.append(startTime).append(" --> ").append(endTime).append("\n");
				srt.append(paragraph).append("\n\n");

				currentTime += duration + timeBetweenParagraphs;
				counter++;
			}
		}

		return srt.toString();
	}

	public static String procesarTextoParrafos(String texto) {
		// Expresión regular para detectar los párrafos que cumplen con las condiciones
		// String regex = "(?<=\\b[A-Z][^\\.]*)\\.(?=\\s+[a-z0-9])";
		String regex = "(?<=\\b[A-Z][^\\.]*)\\.(?=\\s+[A-Z0-9])";
		String procesado = "";
		procesado = texto;
		// Reemplazar el punto seguido de espacio por punto seguido de salto de línea
		procesado = procesado.replaceAll(regex, ".\n");

		// Expresión regular para detectar espacios en blanco al inicio de cada párrafo
		regex = "(?m)^\\s+";

		// Reemplazar los espacios en blanco al inicio de cada párrafo con una cadena
		// vacía
		procesado = procesado.replaceAll(regex, "");

		return procesado;
	}

	public static String procesarTextoComas(String texto) {
		// Expresión regular para detectar los párrafos que cumplen con las condiciones
		String regexParrafo = "(?m)^[A-Z].*?[a-z0-9]\\.$";
		String regexComa = ",\\s*";

		// Procesar cada párrafo que cumple con las condiciones
		StringBuilder resultado = new StringBuilder();
		String[] parrafos = texto.split("\n"); // se sacó /n/n
		for (String parrafo : parrafos) {
			if (parrafo.matches(regexParrafo)) {
				parrafo = parrafo.replaceAll(regexComa, ",\n");
			}
			resultado.append(parrafo).append("\n"); // se sacó /n/n
		}

		return resultado.toString().trim();
	}

	private String formatTime(float seconds) {
		int hours = (int) seconds / 3600;
		int minutes = ((int) seconds % 3600) / 60;
		int secs = (int) seconds % 60;
		int millis = (int) ((seconds - (int) seconds) * 1000);

		return String.format("%02d:%02d:%02d,%03d", hours, minutes, secs, millis);
	}



	protected void btnNewButton_5actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String titulosSTR = null;
		Float a = Float.parseFloat(list.getSelectedItem().toString());
		Float b = Float.parseFloat(this.list_1.getSelectedItem().toString()) / 1000;
		try {
			String textoTXTaSRT = this.textoTecnico.getText();
			titulosSTR = convertToSRT(a, b, textoTXTaSRT);
		} catch (Exception er) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, er.getMessage());
		} finally {
			this.textArea.setText(titulosSTR);
		}
	}

	private void miButton_1actionPerformed(ActionEvent arg0) {
		String titulosSTR = null;
		Float a = Float.parseFloat(list.getSelectedItem().toString());
		Float b = Float.parseFloat(this.list_1.getSelectedItem().toString()) / 1000;
		try {
			// Get text from clipboard
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			String text2 = (String) clipboard.getData(DataFlavor.stringFlavor);
			textoTecnico.setText(text2);
			String textoTXTaSRT = this.textoTecnico.getText();
			titulosSTR = convertToSRT(a, b, textoTXTaSRT);
		} catch (IOException | UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error al procesar el texto: " + e.getMessage());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al procesar el texto: " + e.getMessage());
		} finally {
			this.textArea.setText(titulosSTR);
		}
	}

	/**
	 * public void saveTextAreaToFile(JTextArea textArea) {
	 * this.btnExportar.setEnabled(false); // Obtener el texto del JTextArea String
	 * text = textArea.getText();
	 * 
	 * // Obtener la fecha y hora actuales LocalDateTime now = LocalDateTime.now();
	 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
	 * String formattedDateTime = now.format(formatter);
	 * 
	 * // Crear el nombre del archivo String fileName = "subtitles_" +
	 * formattedDateTime + ".srt";
	 * 
	 * // Guardar el contenido en el archivo try (FileWriter writer = new
	 * FileWriter(fileName)) { writer.write(text); //System.out.println("El archivo
	 * se ha guardado como: " + fileName); JOptionPane.showMessageDialog(null, "El
	 * archivo de subtítulos se ha guardado como: " + fileName + "\nen " +
	 * currentDirectory); } catch (IOException e) {
	 * JOptionPane.showMessageDialog(null, "Error: " + e.getMessage()); }
	 * this.btnExportar.setEnabled(true); }
	 * 
	 **/




}
