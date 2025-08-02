package main.java;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.awt.event.ActionEvent;

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
	private JButton btnGuardar_1;
	private JButton btnNewButton_2;

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
		
		btnGuardar_1 = new JButton("Guardar...");
		toolBar_1.add(btnGuardar_1);
		
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
		
		JButton btnNewButton = new JButton("New button");
		toolBar_2.add(btnNewButton);
		splitPane_1.setDividerLocation(200);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea respuestaIA = new JTextArea();
		respuestaIA.setEditable(false);
		respuestaIA.setLineWrap(true);
		scrollPane.setViewportView(respuestaIA);
		
		JToolBar toolBar_3 = new JToolBar();
		panel_2.add(toolBar_3, BorderLayout.NORTH);
		
		btnNewButton_2 = new JButton("Generar");
		toolBar_3.add(btnNewButton_2);
		splitPane.setDividerLocation(250);
		
		JLabel lblNewLabel = new JLabel("New label");
		getContentPane().add(lblNewLabel, BorderLayout.SOUTH);
        
		miInicializar();
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
		
		return guardoSiNO;
	}
	protected boolean guardarComoPromptEnUnArchivo() {
		// TODO Auto-generated method stub
		boolean guardoSiNO = false;
		
		return guardoSiNO;		
	}

	public void cargarTextoTecnico(JTextArea textoTecnico) {
		// Implementación para cargar texto técnico
	    this.textoTecnico.setText(textoTecnico.getText());
	   
	}

}
