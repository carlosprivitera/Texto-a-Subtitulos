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
import java.awt.List;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
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
import java.awt.Color;
import javax.swing.JSplitPane;

public class VentanaPrincipal {
	private static String currentDirectory = "";
	private JFrame frmTextoASubttulos;
	private JTextArea textArea;
	private JComboBox list;
	private JComboBox list_1;
	private JScrollPane scrollPane_1;
	private JTextArea txtrHolaEsteEs;
	private JButton btnExportar = new JButton("Exportar");

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
				saveTextAreaToFile(textArea);
			}
		});
		toolBar.add(btnExportar);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		toolBar.add(verticalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel("Segundos entre párrafos:");
		toolBar.add(lblNewLabel_1);
		
		list = new JComboBox();
		list.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"}));
		list.setSelectedIndex(1);
		toolBar.add(list);
		
		JLabel lblNewLabel_2 = new JLabel("Milisegundos por letra:");
		toolBar.add(lblNewLabel_2);
		
		list_1 = new JComboBox();
		list_1.setModel(new DefaultComboBoxModel(new String[] {"10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100"}));
		list_1.setSelectedIndex(10);
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
        this.txtrHolaEsteEs.setText(text);
        String[] paragraphs = text.split("\n");
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
}
