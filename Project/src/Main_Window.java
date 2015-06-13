import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class Main_Window extends JFrame{
	
	public Main_Window(){
		setSize(707,364);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(426, 27, 275, 208);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblInfo = new JLabel("Player Info:");
		lblInfo.setBounds(6, 6, 70, 16);
		panel.add(lblInfo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBounds(16, 37, 253, 144);
		panel.add(panel_1);
		
		JLabel label = new JLabel("");
		label.setBounds(251, 0, 70, 48);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(265, 85, 70, 48);
		panel_1.setLayout(null);
		panel_1.add(label);
		panel_1.add(label_1);
		
		JLabel lblAccountId = new JLabel("Account ID:");
		lblAccountId.setBounds(6, 0, 96, 20);
		panel_1.add(lblAccountId);
		
		JLabel lblGuild = new JLabel("Guild: ");
		lblGuild.setBounds(6, 30, 41, 20);
		panel_1.add(lblGuild);
		
		JLabel lblAccountName = new JLabel("Account Name:");
		lblAccountName.setBounds(6, 60, 119, 20);
		panel_1.add(lblAccountName);
		
		JLabel lblServer = new JLabel("Server:");
		lblServer.setBounds(6, 90, 70, 20);
		panel_1.add(lblServer);
		setVisible(true);
		
	}
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
