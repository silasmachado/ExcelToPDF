import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Janela {
	
	Janela(){
		initialize();
	}
	
	public static void main(String[] args) {
		new Janela();
	}

	private void initialize() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("INSTITUTO FOCAR - EXCEL PARA PDF");
		frame.setBounds(200, 200, 400, 310);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setIconImage(new ImageIcon(getClass().getResource("logo_focar_icon.png")).getImage());
		
		Border padding = BorderFactory.createEmptyBorder(30, 0, 0, 0);
		
		JPanel imagePanel = new JPanel();
		imagePanel.add(new JLabel(new ImageIcon(getClass().getResource("logo_focar_small.png"))));
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(padding);
		
		JButton uploadBtn = new JButton("Selecionar planilha");
		uploadBtn.setFocusable(false);
		uploadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					JFileChooser fc_excel = new JFileChooser();
					
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Excel (*.xls; *.xlsx)", "xsl", "xlsx");
					fc_excel.setAcceptAllFileFilterUsed(false);
					fc_excel.addChoosableFileFilter(filter);
					
					int result = fc_excel.showSaveDialog(null);
					
					if (result == JFileChooser.APPROVE_OPTION) {
						GeraRelatorio.geraRelatorio(fc_excel.getSelectedFile());
						JOptionPane.showMessageDialog(null, "Relatórios acadêmicos gerados!");
					}
					
				} catch (Exception exp) {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro! " + exp.getMessage() + "!" + exp.getLocalizedMessage());
				}
			}
		});

		btnPanel.add(uploadBtn);
		
		frame.add(imagePanel);
		frame.add(btnPanel);
		frame.setVisible(true);
	}
}
