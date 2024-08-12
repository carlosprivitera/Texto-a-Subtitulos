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
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	private static String currentDirectory = "";
	private JFrame frmTextoASubttulos;
	private JTextArea textArea;
	private JComboBox list=null;
	private JComboBox list_1;
	private JScrollPane scrollPane_1;
	private JTextArea txtrHolaEsteEs;
	private JButton btnExportar = new JButton("Exportar");
	private JComboBox list_2 = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//aaaaassss
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
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
		
		JButton btnNewButton_1 = new JButton("Pegar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miButton_1actionPerformed(arg0);
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator);
		toolBar.add(btnNewButton_1);
		
		JButton btnCopiar = new JButton("Copiar");
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mibtnCopiarActionPerformer(arg0);
			}
		});
		toolBar.add(btnCopiar);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_1);
		
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
		toolBar.add(btnExportar);
		
		list_2.setModel(new DefaultComboBoxModel(new String[] {"UTF-8 \\n LinuxUNIX", "UTF-16 \\n LinuxUNIX", "UTF-8 \\r\\n Windows", "UTF-16 \\r\\n Windows"}));
		toolBar.add(list_2);
		
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
		list_1.setModel(new DefaultComboBoxModel(new String[] {"10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100"}));
		list_1.setSelectedIndex(8);
		toolBar.add(list_1);
		
		JLabel lblNewLabel = new JLabel("Ayuda:");
		panel.add(lblNewLabel, BorderLayout.SOUTH);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(5);
		panel.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Nimbus Mono PS", Font.PLAIN, 16));
		textArea.setEditable(false);
		textArea.setText("1\n00:00:00,000 --> 00:00:03,600\nHola, este es el primer subtítulo.\n\n2\n00:00:05,600 --> 00:00:08,600\nEste es el segundo subtítulo.\n");
		scrollPane.setViewportView(textArea);
		
		scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);
		
		txtrHolaEsteEs =   new JTextArea();
		txtrHolaEsteEs.setFont(new Font("Nimbus Mono PS", Font.PLAIN, 16));
		txtrHolaEsteEs.setText("Hola, este es el primer subtítulo.\nEste es el segundo subtítulo.\n");
		scrollPane_1.setViewportView(txtrHolaEsteEs);
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

        // Obtener la fecha y hora actuales
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        String formattedDateTime = now.format(formatter);

        // Crear el nombre del archivo
        String fileName = "Subtítulos" + encoding + "" + lineSeparatorOS + "" + formattedDateTime + ".srt";

        // Guardar el contenido en el archivo con la codificación especificada
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding))) {
            writer.write(text);
            JOptionPane.showMessageDialog(null, "El archivo de subtítulos se ha guardado como: " + 
                                                fileName + "\nen " + System.getProperty("user.dir"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        
        this.btnExportar.setEnabled(true);
    }
	
	
}
