package main.java;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GenerarPrompt extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextArea textoTecnico;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerarPrompt dialog = new GenerarPrompt();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
		setBounds(100, 100, 526, 509);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnNewButton_3 = new JButton("New button");
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
		
		JTextArea prompt = new JTextArea();
		prompt.setLineWrap(true);
		scrollPane_1.setViewportView(prompt);
		
		JToolBar toolBar_1 = new JToolBar();
		panel_3.add(toolBar_1, BorderLayout.NORTH);
		
		JButton btnNewButton_1 = new JButton("Leer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leerPromptDesdeArchivo();
			}
		});
		btnNewButton_1.setToolTipText("Lee un archivo con un prompt");
		toolBar_1.add(btnNewButton_1);
		
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
		
		JButton btnNewButton_2 = new JButton("New button");
		toolBar_3.add(btnNewButton_2);
		splitPane.setDividerLocation(200);
		
		JLabel lblNewLabel = new JLabel("New label");
		getContentPane().add(lblNewLabel, BorderLayout.SOUTH);

	}
	
	protected void leerPromptDesdeArchivo() {
		// TODO Auto-generated method stub
		
	}

	public void cargarTextoTecnico(JTextArea textoTecnico) {
		// Implementación para cargar texto técnico
	    this.textoTecnico.setText(textoTecnico.getText());
	   
	}

}
