package main.java;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

public class GenerarSRT extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea;
	private JButton btnWAV;
	private JComboBox comboBoxUTF;
	private JButton btnExportar;
	private String textoTecnico = "";
	private JComboBox comboBox1;
	private JComboBox comboBox2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GenerarSRT dialog = new GenerarSRT();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GenerarSRT() {
		setBounds(100, 100, 678, 444);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JToolBar toolBar = new JToolBar();
			contentPanel.add(toolBar, BorderLayout.NORTH);
			{
				JButton btnCopiar = new JButton("Copiar");
				btnCopiar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						copyToClipboard(textArea);
					}
				});
				toolBar.add(btnCopiar);
			}
			{
				JButton btnPegar = new JButton("Pegar");
				btnPegar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						miButtonPegar_1actionPerformed(e);
					}
				});
				toolBar.add(btnPegar);
			}
			{
				JButton btnPNG = new JButton("PNG");
				btnPNG.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnPNG.setEnabled(false);
						btnGenerarImagenClicked(e);
						btnPNG.setEnabled(true);
					}
				});
				toolBar.add(btnPNG);
			}
			{
				btnWAV = new JButton("WAV");
				btnWAV.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnWAV.setEnabled(false);
						btnWAVClicked(e);
						btnWAV.setEnabled(true);
					}
				});
				btnWAV.setToolTipText("Generar archivos de sonido *.wav");
				toolBar.add(btnWAV);
			}
			{
				btnExportar = new JButton("Exportar");
				btnExportar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String s = comboBoxUTF.getSelectedItem().toString();
						saveTextAreaToFile(textArea, s);

					}
				});
				btnExportar.setToolTipText("Exportar a un archivo *.srt");
				toolBar.add(btnExportar);
			}
			{
				comboBoxUTF = new JComboBox();
				toolBar.add(comboBoxUTF);
			}
			{
				JLabel lblSEP = new JLabel("SEP");
				lblSEP.setToolTipText("Segundos entre párrafos");
				toolBar.add(lblSEP);
			}
			{
				comboBox1 = new JComboBox();
				comboBox1.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
				comboBox1.setSelectedIndex(0);
				toolBar.add(comboBox1);
			}
			{
				JLabel lblMPL = new JLabel("MPL");
				lblMPL.setToolTipText("Milisegundos por letra");
				toolBar.add(lblMPL);
			}
			{
				comboBox2 = new JComboBox();
				comboBox2.setModel(new DefaultComboBoxModel(new String[] { "30", "35", "40", "45", "50", "55", "60", "65", "70",
						"75", "80", "85", "90", "95", "100", "110", "120", "130", "140" }));
				comboBox2.setSelectedIndex(6);

				toolBar.add(comboBox2);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					textArea = new JTextArea();
					scrollPane.setViewportView(textArea);
				}
			}
		}
		{
			JLabel lblNewLabel_2 = new JLabel("New label");
			getContentPane().add(lblNewLabel_2, BorderLayout.SOUTH);
		}
		comboBoxUTF.setModel(new DefaultComboBoxModel(new String[] { "ISO-8859-1", "UTF-8", "UTF-16" }));
		mi_initialize();
	}
    private void mi_initialize() {
		// Initialization code can be added here if needed
		// For example, setting the title of the dialog or adding components to
		// contentPanel
		setTitle("Generar SRT");
		// You can add more components to contentPanel as needed
    }
    
	private void copyToClipboard(JTextArea textArea) {
		String text = textArea.getText();
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	protected void btnGenerarImagenClicked(ActionEvent e) {
		// TODO Auto-generated method stub
		GenerarImagenes generarImagenes = new GenerarImagenes();
		generarImagenes.setLocationRelativeTo(this);
		generarImagenes.setVisible(true);

	}
	
	private void btnWAVClicked(ActionEvent e) {
		String seleccion = textArea.getSelectedText();
		if (seleccion == null || seleccion.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Por favor, seleccioná uno o más bloques de subtítulos para convertir.");
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

		// Copilot, crear la carpeta salida-wav si no existe
		File salidaWavDir = new File("salida-wav"); // Ruta relativa al directorio actual
		if (!salidaWavDir.exists()) { // Verifica si la carpeta no existe
			salidaWavDir.mkdir(); // Crea la carpeta
		} // #else {
			// Copilot, si existe, eliminar todos los archivos dentro de la carpeta
			// salida-wav
			// #File[] files = salidaWavDir.listFiles();
			// #if (files != null) {
			// # for (File file : files) {
			// # if (file.isFile()) {
			// # file.delete(); // Elimina el archivo
			// # }
			// #}
			// #}
			// #}

		// Separar bloques por línea en blanco
		String[] bloques = seleccion.trim().split("\\n\\s*\\n");
		List<String> bloquesInvalidos = new ArrayList<>();

		for (String bloque : bloques) {
			String[] lineas = bloque.split("\\n");
			// Copilot, poner en una variable booleana si la primera línea es un número
			// entero válido
			if (lineas.length >= 3) {
				boolean esNumeroBloqueValido = false;
				try {
					Integer.parseInt(lineas[0].trim());
					esNumeroBloqueValido = true;
				} catch (NumberFormatException er) {
					esNumeroBloqueValido = false;
				}
				// Copilot, verificar con un patron regex si la segunda línea es un tiempo
				// válido
				boolean esTiempoValido = lineas[1]
						.matches("\\d{2}:\\d{2}:\\d{2},\\d{3} --> \\d{2}:\\d{2}:\\d{2},\\d{3}");
				// Copilot, si no es un número válido o el tiempo no es válido, agregar el
				// bloque a la lista de bloques inválidos
				if (!esNumeroBloqueValido || !esTiempoValido) {
					bloquesInvalidos.add(bloque);
				}
			} else {
				bloquesInvalidos.add(bloque);
			}
		}

		if (!bloquesInvalidos.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"❌ Se encontraron bloques mal formados.\nCada bloque debe tener al menos:\n- Número de bloque\n- Tiempo de la forma 00:00:00,000 --> 00:00:00,000 \n- Una o más líneas de texto\n\nCorrige antes de continuar.");
			return;
		}

		// Procesar cada bloque
		for (String bloque : bloques) {
			try {
				String[] lineas = bloque.split("\\n");
				String numeroBloque = lineas[0].trim();
				String texto = String.join(" ", Arrays.copyOfRange(lineas, 2, lineas.length)).trim();
				// Copilot, si texto no termina con un punto, agregar uno al final. Excepto si
				// ya tiene un caracter de los siguientes:,:
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
						// "python3",
						"python/coqui-venv/bin/python3", "src-python/coqui_script.py", texto, bloqueStr);
				pb1.environment().put("PYTHONPATH", "python/coqui-venv/bin");
				pb1.redirectErrorStream(true);
				pb1.directory(new File(".")); // raíz del proyecto
				System.out.println(
						"Ejecutando bloque: " + bloqueStr + " En el directorio: " + pb1.directory().getAbsolutePath());

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
				// Copilot, será necesario cerrar, close(), algún recurso o stream aquí?
				// Copilot, cerrar el proceso p1
				p1.destroy(); // Liberar el objeto Process
				pb1 = null; // Liberar el objeto ProcessBuilder

				// Copilot, ejecutar sleep() durante para colaborar con otros procesos del
				// sistema operativo antes de normalizar el archivo de audio.
				Thread.sleep(500); // Esperar 1/2 segundo para evitar problemas de acceso al archivo

				// -------------------------------
				// Ejecutar normalizar_volume.py
				// -------------------------------
				ProcessBuilder pb2 = new ProcessBuilder("python/coqui-venv/bin/python3",
						"src-python/normalizar_volume.py", bloqueStr);
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
	
	public void saveTextAreaToFile(JTextArea textArea, String encoding) {
		this.btnExportar.setEnabled(false);

		// Obtener el texto del JTextArea
		String text = textArea.getText();

		// Reemplazar los saltos de línea con el lineSeparator especificado
		// text = text.replace("\n", lineSeparator);

		// Obtener la fecha y hora actual
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
		String formattedDateTime = now.format(formatter);

		// Crear el nombre del archivo
		String fileName = "Subtítulos" + encoding + "" + "-" + "" + formattedDateTime + ".srt";

		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("salida-srt/" + fileName), encoding))) {
			writer.write(text);
			JOptionPane.showMessageDialog(null, "El archivo de subtítulos se ha guardado como: " + fileName + "\n en: "
					+ System.getProperty("user.dir") + "/salida-srt/");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}

		this.btnExportar.setEnabled(true);
	}

	public void setTextoTecnico(String texto) {
        textoTecnico = texto;
	}
	
	protected void btnNewButton_5actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String titulosSTR = null;
		Float a = Float.parseFloat(comboBox1.getSelectedItem().toString());
		Float b = Float.parseFloat(this.comboBox2.getSelectedItem().toString()) / 1000;
		try {
			//String textoTXTaSRT = this.textoTecnico.getText();
			titulosSTR = convertToSRT(a, b, textoTecnico);
		} catch (Exception er) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, er.getMessage());
		} finally {
			this.textArea.setText(titulosSTR);
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

	private void miButtonPegar_1actionPerformed(ActionEvent arg0) {
		String titulosSTR = null;
		Float a = Float.parseFloat(comboBox1.getSelectedItem().toString());
		Float b = Float.parseFloat(this.comboBox2.getSelectedItem().toString()) / 1000;
		try {
			// Get text from clipboard
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			String text2 = (String) clipboard.getData(DataFlavor.stringFlavor);
			textoTecnico = text2;
			String textoTXTaSRT = textoTecnico;
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

	
}
