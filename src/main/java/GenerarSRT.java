package main.java;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class GenerarSRT extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

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
				toolBar.add(btnCopiar);
			}
			{
				JButton btnPegar = new JButton("Pegar");
				toolBar.add(btnPegar);
			}
			{
				JButton btnWav = new JButton("WAV");
				btnWav.setToolTipText("Generar archivos de sonido *.wav");
				toolBar.add(btnWav);
			}
			{
				JButton btnExportar = new JButton("Exportar");
				btnExportar.setToolTipText("Exportar a un archivo *.srt");
				toolBar.add(btnExportar);
			}
			{
				JLabel lblSEP = new JLabel("SEP");
				lblSEP.setToolTipText("Segundos entre párrafos");
				toolBar.add(lblSEP);
			}
			{
				JComboBox comboBox1 = new JComboBox();
				toolBar.add(comboBox1);
			}
			{
				JLabel lblMPL = new JLabel("MPL");
				lblMPL.setToolTipText("Milisegundos por letra");
				toolBar.add(lblMPL);
			}
			{
				JComboBox comboBox2 = new JComboBox();
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
					JTextArea textArea = new JTextArea();
					scrollPane.setViewportView(textArea);
				}
			}
		}
		{
			JLabel lblNewLabel_2 = new JLabel("New label");
			getContentPane().add(lblNewLabel_2, BorderLayout.SOUTH);
		}
		mi_initialize();
	}
    private void mi_initialize() {
		// Initialization code can be added here if needed
		// For example, setting the title of the dialog or adding components to
		// contentPanel
		setTitle("Generar SRT");
		// You can add more components to contentPanel as needed
    }
}
