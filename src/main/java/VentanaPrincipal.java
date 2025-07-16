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
import javax.swing.DefaultComboBoxModel;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JSplitPane;

public class VentanaPrincipal {
	//01
	private static String currentDirectory = "";
	private JFrame frmTextoASubttulos;
	private JTextArea textArea;
	//private JTextArea textArea_1;
	private JComboBox list=null;
	private JComboBox list_1;
	private JScrollPane scrollPane_1;
	private JTextArea txtrHolaEsteEs;
	private JButton btnExportar = new JButton("Exportar");
	private JComboBox list_2 = new JComboBox();
	private static VentanaPrincipal window = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//aaaaassss
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new VentanaPrincipal();
					currentDirectory = System.getProperty("user.dir");
                    window.frmTextoASubttulos.setTitle(currentDirectory);
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	JButton btnNewButton_2 = null;
	private void initialize() {
		frmTextoASubttulos = new JFrame();
		frmTextoASubttulos.setTitle("Texto a subtítulos v15-06-24");
		frmTextoASubttulos.setBounds(100, 100, 786, 524);
		frmTextoASubttulos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(null, 5, true));
		frmTextoASubttulos.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Salir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		toolBar.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		toolBar.add(verticalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel("SEP");
		lblNewLabel_1.setToolTipText("Segundos entre párrafos");
		toolBar.add(lblNewLabel_1);
		
		list = new JComboBox();
		list.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"}));
		list.setSelectedIndex(1);
		toolBar.add(list);
		
		JLabel lblNewLabel_2 = new JLabel("MPP");
		lblNewLabel_2.setToolTipText("Milisegundos por letra");
		toolBar.add(lblNewLabel_2);
		
		list_1 = new JComboBox();
		list_1.setModel(new DefaultComboBoxModel(new String[] {"50", "60", "70", "80", "90", "100", "110", "120", "130", "140", "150", "160", "170", "180", "190", "200", "210", "120", "230"}));
		list_1.setSelectedIndex(12);
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
		
		JButton btnNewButton_3 = new JButton("Leer");
		btnNewButton_3.setToolTipText("Leer archivo *.txt");
		toolBar_1.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Guardar");
		btnNewButton_4.setToolTipText("Guardar archivo *.txt");
		toolBar_1.add(btnNewButton_4);
		
		JButton btnNewButton_1 = new JButton("Pegar");
		btnNewButton_1.setToolTipText("Pegar desde el porta papeles");
		toolBar_1.add(btnNewButton_1);
		
		JButton btnNewButton_5 = new JButton("Convertir a srt ==>");
		toolBar_1.add(btnNewButton_5);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miButton_1actionPerformed(arg0);
			}
		});
		
		JPanel panel_4 = new JPanel();
		splitPane.setRightComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar_2 = new JToolBar();
		panel_4.add(toolBar_2, BorderLayout.NORTH);
		
		JButton btnCopiar = new JButton("Copiar");
		btnCopiar.setToolTipText("Copiar al porta papeles los subtítulos");
		toolBar_2.add(btnCopiar);
		
		btnNewButton_2 = new JButton("Generar WAV");
		btnNewButton_2.setToolTipText("Generar archivos de sonido *.wav");
		toolBar_2.add(btnNewButton_2);
		btnExportar.setToolTipText("Exportar a un archivo *.srt");
		toolBar_2.add(btnExportar);
		toolBar_2.add(list_2);
		
		list_2.setModel(new DefaultComboBoxModel(new String[] {"UTF-8 \\n LinuxUNIX", "UTF-16 \\n LinuxUNIX", "UTF-8 \\r\\n Windows", "UTF-16 \\r\\n Windows"}));
		
		JPanel panel_2 = new JPanel();
		panel_4.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Nimbus Mono PS", Font.PLAIN, 16));
		textArea.setEditable(false);
		textArea.setText("\n1\n"
				+ "00:00:00,000 --> 00:00:00,149\n"
				+ "Hola, procesando títulos \n"
				+ "\n"
				+ "2\n"
				+ "00:00:02,150 --> 00:00:03,019\n"
				+ "Este es el segundo subtítulo, \ncon dos líneas de texto.\n"
				+ "\n"
				+ "3\n"
				+ "00:00:05,019 --> 00:00:05,860\n"
				+ "Este es el tercer subtítulo."
				+ "\n");
		//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		scrollPane.setViewportView(textArea);
		
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] p = new String[3];
				String s = list_2.getSelectedItem().toString();
				p = s.split(" ");
				int i = list_2.getSelectedIndex();
				if(i==0) p[1]="\n";   //LinuxUNIX
				if(i==1) p[1]="\n";   //LinuxUNIX
				if(i==2) p[1]="\r\n"; //Windows
				if(i==3) p[1]="\r\n"; //Windows
				saveTextAreaToFile(textArea, p[0], p[1], p[2]);
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    window.btnNewButton_2.setEnabled(false);
				btnNewButton_2Clicked(e);
				window.btnNewButton_2.setEnabled(true);
			}
		});
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mibtnCopiarActionPerformer(arg0);
			}
		});
		
		txtrHolaEsteEs =   new JTextArea();
		scrollPane_1.add(txtrHolaEsteEs);
		txtrHolaEsteEs.setFont(new Font("Nimbus Mono PS", Font.PLAIN, 16));
		txtrHolaEsteEs.setText("Hola, procesando títulos \nEste es el segundo subtítulo, \ncon dos líneas de texto.\nEste es el tercer subtítulo.\n");
		scrollPane_1.setViewportView(txtrHolaEsteEs);
		
		JLabel lblNewLabel = new JLabel("Ayuda");
		frmTextoASubttulos.getContentPane().add(lblNewLabel, BorderLayout.SOUTH);
	}

	private void mibtnCopiarActionPerformer(ActionEvent arg0) {
		// TODO Auto-generated method stub
		 copyToClipboard(textArea);
	}

	private String convertToSRT(Float timeBetweenParagraphs, Float timeBetweenLetters) throws IOException, UnsupportedFlavorException {
        // Get text from clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String text = (String) clipboard.getData(DataFlavor.stringFlavor);
        String text2 = (String) clipboard.getData(DataFlavor.stringFlavor);
        //Procesar los párrafos separandolos.
        text2 = procesarTextoParrafos(text2);
        /////////////////////////////////////////////
        //Procesar los párrafos separando las comas.
        text2 = procesarTextoComas(text2);
        /////////////////////////////////////////////
        this.txtrHolaEsteEs.setText(text);
        String[] paragraphs = text2.split("\n");
        StringBuilder srt = new StringBuilder();
        int counter = 1;
        float currentTime = 0.0f;

        for (String paragraph : paragraphs) {
            if (!paragraph.trim().isEmpty()) {
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
	        //String regex = "(?<=\\b[A-Z][^\\.]*)\\.(?=\\s+[a-z0-9])";
    	    String regex = "(?<=\\b[A-Z][^\\.]*)\\.(?=\\s+[A-Z0-9])";
	        String procesado = "";
	        procesado =	texto;
	        // Reemplazar el punto seguido de espacio por punto seguido de salto de línea
	        procesado = procesado.replaceAll(regex, ".\n");
	        
	        
	        // Expresión regular para detectar espacios en blanco al inicio de cada párrafo
	        regex = "(?m)^\\s+";
	        
	        // Reemplazar los espacios en blanco al inicio de cada párrafo con una cadena vacía
	        procesado = procesado.replaceAll(regex, "");
	        	        
	        
	        return procesado;
    }

    public static String procesarTextoComas(String texto) {
        // Expresión regular para detectar los párrafos que cumplen con las condiciones
        String regexParrafo = "(?m)^[A-Z].*?[a-z0-9]\\.$";
        String regexComa = ",\\s*";

        // Procesar cada párrafo que cumple con las condiciones
        StringBuilder resultado = new StringBuilder();
        String[] parrafos = texto.split("\n"); //se sacó /n/n
        for (String parrafo : parrafos) {
            if (parrafo.matches(regexParrafo)) {
                parrafo = parrafo.replaceAll(regexComa, ",\n");
            }
            resultado.append(parrafo).append("\n"); //se sacó /n/n
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
	
	private void copyToClipboard(JTextArea textArea) {
        String text = textArea.getText();
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
	
	private void miButton_1actionPerformed(ActionEvent arg0) {
	  String titulosSTR = null;
	  Float a = Float.parseFloat(list.getSelectedItem().toString());
	  Float b = Float.parseFloat( this.list_1.getSelectedItem().toString()) / 1000;
	  try {
		titulosSTR = convertToSRT(a, b);
	  } catch (IOException | UnsupportedFlavorException e) {
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(null, e.getMessage());
	  }finally {
		this.textArea.setText(titulosSTR);
	  }
	}

/**	
	public void saveTextAreaToFile(JTextArea textArea) {
		this.btnExportar.setEnabled(false);
        // Obtener el texto del JTextArea
        String text = textArea.getText();
        
        // Obtener la fecha y hora actuales
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDateTime = now.format(formatter);
        
        // Crear el nombre del archivo
        String fileName = "subtitles_" + formattedDateTime + ".srt";
        
        // Guardar el contenido en el archivo
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(text);
            //System.out.println("El archivo se ha guardado como: " + fileName);
            JOptionPane.showMessageDialog(null, "El archivo de subtítulos se ha guardado como: " + 
                                                fileName + "\nen " + currentDirectory);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        this.btnExportar.setEnabled(true);
    }

**/	
	
	public void saveTextAreaToFile(JTextArea textArea, String encoding, String lineSeparator, String lineSeparatorOS ) {
        this.btnExportar.setEnabled(false);

        // Obtener el texto del JTextArea
        String text = textArea.getText();

        // Reemplazar los saltos de línea con el lineSeparator especificado
        text = text.replace("\n", lineSeparator);

        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        String formattedDateTime = now.format(formatter);

        // Crear el nombre del archivo
        String fileName = "Subtítulos" + encoding + "" + lineSeparatorOS + "" + formattedDateTime + ".srt";

        // Guardar el contenido en el archivo con la codificación especificada
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("salida-srt/"+fileName), encoding))) {
            writer.write(text);
            JOptionPane.showMessageDialog(null, "El archivo de subtítulos se ha guardado como: " + 
                                                fileName + "\n en: " + System.getProperty("user.dir") + "/salida-srt/");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        
        this.btnExportar.setEnabled(true);
    }
	
	private void btnNewButton_2Clicked(ActionEvent e) {
		// Verificar que haya texto seleccionado
		if (textArea.getSelectedText() == null || textArea.getSelectedText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Por favor, seleccioná uno o más bloques de subtítulos para convertir a voz.");
			return;
		}
		// Verificar que haya un intérprete de Python instalado
		try {
			ProcessBuilder pb = new ProcessBuilder("python3", "--version");
			pb.redirectErrorStream(true);
			Process p = pb.start();
			int exitCode = p.waitFor();
			if (exitCode != 0) {
				JOptionPane.showMessageDialog(null, "❌ Error: No se encontró Python 3 instalado en el sistema.");
				return;
			}
		} catch (IOException | InterruptedException ex) {
			JOptionPane.showMessageDialog(null, "❌ Error al verificar Python 3: " + ex.getMessage());
			return;
		}
 
		//Copilot, crear la carpeta salida-wav si no existe
		File salidaWavDir = new File("salida-wav"); // Ruta relativa al directorio actual
		if (!salidaWavDir.exists()) { //Verifica si la carpeta no existe
			salidaWavDir.mkdir(); // Crea la carpeta
		} else {
			// Copilot, si existe, eliminar todos los archivos dentro de la carpeta
			// salida-wav
			File[] files = salidaWavDir.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						file.delete(); // Elimina el archivo
					}
				}
			}
		}

		
		// Obtener el texto seleccionado

		String seleccion = textArea.getSelectedText();

	    if (seleccion == null || seleccion.trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Por favor, seleccioná uno o más bloques de subtítulos para convertir.");
	        return;
	    }

	    // Separar bloques por línea en blanco
	    String[] bloques = seleccion.trim().split("\\n\\s*\\n");
	    List<String> bloquesInvalidos = new ArrayList<>();

	    for (String bloque : bloques) {
	        String[] lineas = bloque.split("\\n");
            //Copilot, poner en una variable booleana si la primera línea es un número entero válido
	        if (lineas.length >= 3) {
		        boolean esNumeroBloqueValido = false;
				try {
					Integer.parseInt(lineas[0].trim());
					esNumeroBloqueValido = true;
				} catch (NumberFormatException er) {
					esNumeroBloqueValido = false;
				}
				//Copilot, verificar con un patron regex si la segunda línea es un tiempo válido
				boolean esTiempoValido = lineas[1].matches("\\d{2}:\\d{2}:\\d{2},\\d{3} --> \\d{2}:\\d{2}:\\d{2},\\d{3}");
				//Copilot, si no es un número válido o el tiempo no es válido, agregar el bloque a la lista de bloques inválidos
				if (!esNumeroBloqueValido || !esTiempoValido) {
					bloquesInvalidos.add(bloque);
				}
	        }else {
	        	bloquesInvalidos.add(bloque);
	        }
	    }

	    if (!bloquesInvalidos.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "❌ Se encontraron bloques mal formados.\nCada bloque debe tener al menos:\n- Número de bloque\n- Tiempo de la forma 00:00:00,000 --> 00:00:00,000 \n- Una o más líneas de texto\n\nCorrige antes de continuar.");
	        return;
	    }

	    // Procesar cada bloque
	    for (String bloque : bloques) {
	        try {
	            String[] lineas = bloque.split("\\n");
	            String numeroBloque = lineas[0].trim();
	            String texto = String.join(" ", Arrays.copyOfRange(lineas, 2, lineas.length)).trim();
                //Copilot, si texto no termina con un punto, agregar uno al final. Excepto si ya tiene un caracter de los siguientes:,:
				if (!texto.isEmpty() && !texto.endsWith(".") && !texto.endsWith(",") && !texto.endsWith(":")) {
					texto += ".";
				}
	            // Verificar que el número del bloque es numérico
	            int bloqueId = Integer.parseInt(numeroBloque);
	            String bloqueStr = String.valueOf(bloqueId);

	            // -------------------------------
	            // Ejecutar coqui_script.py
	            // -------------------------------
	            ProcessBuilder pb1 = new ProcessBuilder(
	                //"python3",
            	    "python/coqui-venv/bin/python3",
            	    "src-python/coqui_script.py",	            		
	                texto,
	                bloqueStr
	            );
	            pb1.environment().put("PYTHONPATH", "python/coqui-venv/bin");
	            pb1.redirectErrorStream(true);
	            pb1.directory(new File(".")); // raíz del proyecto
	            System.out.println("Ejecutando bloque: " + bloqueStr + " En el directorio: " + pb1.directory().getAbsolutePath());
	            
	            Process p1 = pb1.start();
	            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()))) {
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    System.out.println("Salida: " + line);
	                }
	            }
	            
	            int error = p1.waitFor();
	            if (error != 0) {
	                System.err.println("Error: El proceso terminó con código " + error);
	            } else {
	                System.out.println("Proceso completado exitosamente.");
	            }
	            //Copilot, será necesario cerrar, close(), algún recurso o stream aquí?
	            // Copilot, cerrar el proceso p1
	            p1.destroy(); // Liberar el objeto Process
	            pb1 = null; // Liberar el objeto ProcessBuilder
	            
	            //Copilot, ejecutar sleep() durante un segundo para colaborar con otros procesos del sistema operativo antes de normalizar el archivo de audio.
	            Thread.sleep(1000); // Esperar 1 segundo para evitar problemas de acceso al archivo
	            
	            // -------------------------------
	            // Ejecutar normalizar_volume.py
	            // -------------------------------
	            ProcessBuilder pb2 = new ProcessBuilder(
	            	"python/coqui-venv/bin/python3",
	                "src-python/normalizar_volume.py",
	                bloqueStr
	            );
	            pb2.environment().put("PYTHONPATH", "python/coqui-venv/bin");
	            pb2.redirectErrorStream(true);
	            pb2.directory(new File("."));
	            Process p2 = pb2.start();
	            
				try (BufferedReader reader2 = new BufferedReader(new InputStreamReader(p2.getInputStream()))) {
					String line2;
					while ((line2 = reader2.readLine()) != null) {
						System.out.println("Salida normalización: " + line2);
					}
				}
	            
	            int error2 = p2.waitFor();
				if (error2 != 0) {
					System.err.println("Error: El proceso de normalización terminó con código " + error2);
				} else {
					System.out.println("Proceso de normalización completado exitosamente.");
				}

	            System.out.println("✅ Bloque procesado: " + bloqueStr);
	            
	            p2.destroy(); // Liberar el objeto Process
	            pb2 = null; // Liberar el objeto ProcessBuilder

	        } catch (Exception ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "❌ Error al procesar un bloque:\n" + ex.getMessage());
	        }
	    }

	    JOptionPane.showMessageDialog(null, "✅ Conversión completa. Archivos de voz en la carpeta /salida-wav/");
	    
	}
}
