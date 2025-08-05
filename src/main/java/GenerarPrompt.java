package main.java;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class GenerarPrompt extends JDialog {
    private static final String tituloVentana = "Generar Prompt";
	private static final long serialVersionUID = 1L;
	private JTextArea textoTecnico;
	private String nombreArchivoPromptAbierto=""; // Nombre del archivo abierto, vacio si no hay ninguno abierto.
	private String rutaArchivoPromptAbierto=""; // Ruta del archivo abierto, vacio si no hay ninguno abierto.
	private boolean archivoPromptAbiertoEditado=false; // Indica si el archivo con el prmpt se ha modificado. Si está true hay que activar el boton Abrir.
	private JTextArea prompt;
	private JButton btnAbrir;
	private JButton btnGuardar;
	private JButton btnGuardarComo;
	private JButton btnPreguntarMistral7Bv02;
	private JTextArea respuestaIA;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerarPrompt dialog = new GenerarPrompt();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setTitle(tituloVentana); //No funciona si se pone aquí, hay que ponerlo en el constructor.					
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public GenerarPrompt() {
		setModal(true);
		setBounds(100, 100, 682, 508);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnNewButton_3 = new JButton("Salir");
		toolBar.add(btnNewButton_3);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		panel.add(splitPane);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_1.add(splitPane_1, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		splitPane_1.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_3.add(scrollPane_1, BorderLayout.CENTER);
		
		prompt = new JTextArea();
		prompt.setLineWrap(true);
		scrollPane_1.setViewportView(prompt);
		
		JToolBar toolBar_1 = new JToolBar();
		panel_3.add(toolBar_1, BorderLayout.NORTH);
		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Aquí se implementaría la lógica para crear un nuevo prompt
				// Por ejemplo, limpiar el JTextArea 'prompt' y resetear el estado de edición
				if (archivoPromptAbiertoEditado==true) {
					int respuesta = javax.swing.JOptionPane.showConfirmDialog(null,
							"El prompt ha sido editado. ¿Desea guardar los cambios?", "Guardar cambios",
							javax.swing.JOptionPane.YES_NO_CANCEL_OPTION);
					if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
						if (guardarPromptEnUnArchivo() == false) {
							return; // Si no se ha podido guardar, cancelar la operación de nuevo
						}
					}
					if (respuesta == javax.swing.JOptionPane.CANCEL_OPTION) {
						return; // Cancelar la operación de nuevo
					}
					if (respuesta == javax.swing.JOptionPane.NO_OPTION) {
						// No hacer nada, continuar con la creación de un nuevo prompt

					}
				} else {
					// Si no hay un prompt editado, continuar con la creación de un nuevo prompt
					//Copilot: Hacer un beep por el parlante del ordenador.
					//Toolkit.getDefaultToolkit().beep(); // Emitir un beep
					Beep.miBeep(); // Emitir un beep
				}
				prompt.setText(""); // Limpiar el JTextArea 'prompt'
				nombreArchivoPromptAbierto = ""; // Resetear el nombre del archivo abierto
				rutaArchivoPromptAbierto = ""; // Resetear la ruta del archivo abierto
				miSetTitle(tituloVentana); // Resetear el título de la ventana
				archivoPromptAbiertoEditado = false; // Reseteamos el estado de edición al crear un nuevo prompt
				btnGuardar.setEnabled(false); // Deshabilitar botón Guardar al crear un nuevo prompt
			}
		});
		btnNuevo.setToolTipText("Lee un archivo con un prompt");
		toolBar_1.add(btnNuevo);
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Aquí se implementaría la lógica para abrir un archivo de prompt
				// Por ejemplo, mostrar un diálogo de selección de archivo y cargar su contenido
				// en el JTextArea 'prompt'
				// Copilot: si archivoPromptAbiertoEditado es true, entonces preguntar al usuario si quiere guardar o no guardar o cancelar la operación de Abrir.
				if (archivoPromptAbiertoEditado) {
					int respuesta = javax.swing.JOptionPane.showConfirmDialog(null,
							"El prompt ha sido editado. ¿Desea guardar los cambios?", "Guardar cambios",
							javax.swing.JOptionPane.YES_NO_CANCEL_OPTION);
					if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
						if (guardarPromptEnUnArchivo()==false) {
                             return; // Si no se ha podido guardar, cancelar la operación de abrir un nuevo archivo
						}
					} 
					if (respuesta == javax.swing.JOptionPane.CANCEL_OPTION) {
						return; // Cancelar la operación de abrir un nuevo archivo
					}
					if (respuesta == javax.swing.JOptionPane.NO_OPTION) {
						// No hacer nada, continuar con la apertura del nuevo archivo
						
					}
				} 
				if(leerPromptDesdeArchivo()==false) { // Lógica para leer el prompt desde un archivo
					return; // Si no se ha podido leer el archivo, cancelar la operación de abrir un nuevo archivo
				}else {
				  archivoPromptAbiertoEditado = false; // Al abrir un archivo, se resetea el estado de edición
				  btnGuardar.setEnabled(false); // Deshabilitar botón Guardar al abrir un nuevo archivo
				}  
			}
		});
		toolBar_1.add(btnAbrir);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setEnabled(false);
		toolBar_1.add(btnGuardar);
		
		btnGuardarComo = new JButton("Guardar...");
		btnGuardarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Copilot: si archivoPromptAbiertoEditado es true, entonces preguntar al usuario si quiere guardar o no guardar o cancelar la operación de Guardar Como.
				if (archivoPromptAbiertoEditado) {
					int respuesta = javax.swing.JOptionPane.showConfirmDialog(null,
							"El prompt ha sido editado. ¿Desea guardar los cambios?", "Guardar cambios",
							javax.swing.JOptionPane.YES_NO_CANCEL_OPTION);
					if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
						if (guardarPromptEnUnArchivo() == false) {
							return; // Si no se ha podido guardar, cancelar la operación de Guardar Como
						}
					}
					if (respuesta == javax.swing.JOptionPane.CANCEL_OPTION) {
						return; // Cancelar la operación de Guardar Como
					}
					if (respuesta == javax.swing.JOptionPane.NO_OPTION) {
						// No hacer nada, continuar con la operación de Guardar Como
					}
				}
				if (guardarComoPromptEnUnArchivo() == false) { // Lógica para guardar el prompt en un archivo
					return; // Si no se ha podido guardar, cancelar la operación de Guardar Como
				} else {
					archivoPromptAbiertoEditado = false; // Al guardar como, se resetea el estado de edición
					btnGuardar.setEnabled(false); // Deshabilitar botón Guardar al guardar como un nuevo archivo
				}
				
			}
		});
		toolBar_1.add(btnGuardarComo);
		
		JPanel panel_4 = new JPanel();
		splitPane_1.setRightComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_4.add(scrollPane_2, BorderLayout.CENTER);
		
		textoTecnico = new JTextArea();
		textoTecnico.setEditable(false);
		textoTecnico.setLineWrap(true);
		scrollPane_2.setViewportView(textoTecnico);
		
		JToolBar toolBar_2 = new JToolBar();
		panel_4.add(toolBar_2, BorderLayout.NORTH);
		
		JCheckBox chckbxConcatenarAlPrompt = new JCheckBox("Incluir en el Prompt");
		chckbxConcatenarAlPrompt.setSelected(true);
		toolBar_2.add(chckbxConcatenarAlPrompt);
		splitPane_1.setDividerLocation(200);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		respuestaIA = new JTextArea();
		respuestaIA.setEditable(false);
		respuestaIA.setLineWrap(true);
		scrollPane.setViewportView(respuestaIA);
		
		JToolBar toolBar_3 = new JToolBar();
		panel_2.add(toolBar_3, BorderLayout.NORTH);
		
		btnPreguntarMistral7Bv02 = new JButton("Generar");
		btnPreguntarMistral7Bv02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String promptMasTextoTecnico;
				if (chckbxConcatenarAlPrompt.isSelected()) {
					// Concatenar el texto técnico al prompt
					promptMasTextoTecnico = prompt.getText() + "\n" + textoTecnico.getText();
					//promptMasTextoTecnico = promptMasTextoTecnico.replaceAll("\\s+", " ").trim(); // Limpiar el texto de entrada
					ejecutarMistral7Bv02Responder(promptMasTextoTecnico, respuestaIA);
				}else {
					promptMasTextoTecnico = prompt.getText();
					//promptMasTextoTecnico = promptMasTextoTecnico.replaceAll("\\s+", " ").trim(); // Limpiar el texto de entrada
				   ejecutarMistral7Bv02Responder(promptMasTextoTecnico, respuestaIA);
				}
			}
		});
		toolBar_3.add(btnPreguntarMistral7Bv02);
		splitPane.setDividerLocation(300);
		
		JLabel lblNewLabel = new JLabel("New label");
		getContentPane().add(lblNewLabel, BorderLayout.SOUTH);
        
		miInicializar();
	}
	
	protected void ejecutarMistral7Bv02Responder(String text, JTextArea respuestaIA2) {
		// TODO Auto-generated method stub
		MistralLLM7Bv02 mistralLLM7Bv02 = new MistralLLM7Bv02();
		SwingUtilities.invokeLater(() -> {
			respuestaIA2.setText(""); // Limpiar el JTextArea 'respuestaIA' antes de generar una nueva respuesta
			respuestaIA2.append("Generando respuesta...\n");
		});
		mistralLLM7Bv02.ejecutarMistralResponder(text, respuestaIA2);
	}

	private void miInicializar() {
		// Inicialización de variables y componentes si es necesario
		// Copilot: detectar si el componente del tipo JTextArea llamado prompt ha cambiado el texto, 
		//  entonces poner la variable archivoPromptAbiertoEditado a true. Y activar el botón Guardar.
		prompt.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				archivoPromptAbiertoEditado = true;
				btnGuardar.setEnabled(true);
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				archivoPromptAbiertoEditado = true;
				btnGuardar.setEnabled(true);
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				archivoPromptAbiertoEditado = true;
				btnGuardar.setEnabled(true);
			}
		});
		this.setTitle(tituloVentana);
	}
	
	protected boolean leerPromptDesdeArchivo() {
		// TODO Auto-generated method stub
		boolean LeerSiNO = false;
		// Aquí se implementaría la lógica para leer un archivo de prompt
		//Copilot: implementar la lógica para leer el prompt desde un archivo de texto, El directorio del archivo se debe seleccionar con un JFileChooser.
		//Si se ha podido leer el archivo, entonces LeerSiNO será true. Si no se ha podido leer el archivo, entonces LeerSiNO será false.
		//La carpeta de de trabajo es proyectos-prompt.
		//El nombre del archivo se debe guardar en la variable nombreArchivoPromptAbierto.
		//La ruta del archivo se debe guardar en la variable rutaArchivoPromptAbierto.
		//Si el archivo se ha podido leer, entonces se debe cargar el contenido del archivo en el JTextArea llamado prompt.
	
		// Ejemplo de implementación:
		JFileChooser fileChooser = new JFileChooser("proyectos-prompt");
		int returnValue = fileChooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				String contenido = new String(Files.readAllBytes(selectedFile.toPath()), StandardCharsets.UTF_8);
				prompt.setText(contenido);
				nombreArchivoPromptAbierto = selectedFile.getName();
				rutaArchivoPromptAbierto = selectedFile.getAbsolutePath();
				this.setTitle(tituloVentana + " - " + nombreArchivoPromptAbierto);
				LeerSiNO = true; // Si se ha podido leer el archivo, LeerSiNO será true
			} catch (IOException e) {
				//e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				LeerSiNO = false; // Si no se ha podido leer el archivo, LeerSiNO será false
			}
		}else {
			JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún archivo.", "Error", JOptionPane.ERROR_MESSAGE);
			LeerSiNO = false; // Si el usuario cancela la selección del archivo, LeerSiNO será false
		}
		// Fin de la implementación
		return LeerSiNO;
	}
	
	protected boolean guardarPromptEnUnArchivo() {
		// TODO Auto-generated method stub
		boolean guardoSiNO = false;
		//Copilot: implementar la lógica para guardar el prompt en un archivo de texto, El directorio del archivo se debe seleccionar con un JFileChooser.
		//Si se ha podido guardar el archivo, entonces guardoSiNO será true. Si no se ha podido guardar el archivo, entonces guardoSiNO será false.
		// La carpeta de de trabajo es proyectos-prompt.
		// El nombre del archivo se debe guardar en la variable nombreArchivoPromptAbierto.
		// La ruta del archivo se debe guardar en la variable rutaArchivoPromptAbierto.
		// Si el archivo se ha podido guardar, entonces se debe actualizar el título de la ventana con el nombre del archivo guardado.
		JFileChooser fileChooser = new JFileChooser("proyectos-prompt");
		int returnValue = fileChooser.showSaveDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				// Guardar el contenido del JTextArea 'prompt' en el archivo seleccionado
				Files.write(selectedFile.toPath(), prompt.getText().getBytes(StandardCharsets.UTF_8));
				nombreArchivoPromptAbierto = selectedFile.getName();
				rutaArchivoPromptAbierto = selectedFile.getAbsolutePath();
				miSetTitle(tituloVentana + " - " + nombreArchivoPromptAbierto);
				archivoPromptAbiertoEditado = false; // Reseteamos el estado de edición al guardar
				btnGuardar.setEnabled(false); // Deshabilitar botón Guardar al guardar correctamente
				guardoSiNO = true; // Si se ha podido guardar el archivo, guardoSiNO será true
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				guardoSiNO = false; // Si no se ha podido guardar el archivo, guardoSiNO será false
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún archivo para guardar.", "Error",
					JOptionPane.ERROR_MESSAGE);
			guardoSiNO = false; // Si el usuario cancela la selección del archivo, guardoSiNO será false
		}
		// Fin de la implementación
		return guardoSiNO;
	}
	protected boolean guardarComoPromptEnUnArchivo() {
		// TODO Auto-generated method stub
		boolean guardoSiNO = false;
		// Copilot: implementar la lógica para guardar el prompt en un archivo de texto, El directorio del archivo se debe seleccionar con un JFileChooser.
		// Si se ha podido guardar el archivo, entonces guardoSiNO será true. Si no se ha podido guardar el archivo, entonces guardoSiNO será false.
		// La carpeta de de trabajo es proyectos-prompt.
		// El nombre del archivo se debe guardar en la variable nombreArchivoPromptAbierto.
		// La ruta del archivo se debe guardar en la variable rutaArchivoPromptAbierto.
		// Si el archivo se ha podido guardar, entonces se debe actualizar el título de la ventana con el nombre del archivo guardado.
		JFileChooser fileChooser = new JFileChooser("proyectos-prompt");
		int returnValue = fileChooser.showSaveDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				// Guardar el contenido del JTextArea 'prompt' en el archivo seleccionado
				Files.write(selectedFile.toPath(), prompt.getText().getBytes(StandardCharsets.UTF_8));
				nombreArchivoPromptAbierto = selectedFile.getName();
				rutaArchivoPromptAbierto = selectedFile.getAbsolutePath();
				miSetTitle(tituloVentana + " - " + nombreArchivoPromptAbierto);
				archivoPromptAbiertoEditado = false; // Reseteamos el estado de edición al guardar
				btnGuardar.setEnabled(false); // Deshabilitar botón Guardar al guardar correctamente
				guardoSiNO = true; // Si se ha podido guardar el archivo, guardoSiNO será true
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				guardoSiNO = false; // Si no se ha podido guardar el archivo, guardoSiNO será false
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún archivo para guardar.", "Error",
					JOptionPane.ERROR_MESSAGE);
			guardoSiNO = false; // Si el usuario cancela la selección del archivo, guardoSiNO será false
		}
		// Fin de la implementación
		return guardoSiNO;		
	}

	public void cargarTextoTecnico(JTextArea textoTecnico) {
		// Implementación para cargar texto técnico
	    this.textoTecnico.setText(textoTecnico.getText());
	   
	}
	
	private void miSetTitle(String titulo) {
        // Método para establecer el título de la ventana
        this.setTitle(titulo);
	}

	public String getNombreArchivoPromptAbierto() {
		return nombreArchivoPromptAbierto;
	}
	
}
