package src;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class BuySell_Window extends JFrame {

	private JPanel contentPane;
	private JTextField txfCost;
	Connection connection;
	Statement stmt;
	ResultSet rs;
	
	/**
	 * Create the frame.
	 */
	public BuySell_Window(final String sAction, String sAccountName, int sAccountID) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 540, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JTable tblBuySellItems = new JTable();
		JScrollPane srpBuySellItems = new JScrollPane(tblBuySellItems);
		srpBuySellItems.setBounds(16, 23, 334, 341);
		contentPane.add(srpBuySellItems);
		
		final JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(16, 6, 84, 16);
		contentPane.add(lblTitle);
		
		JPanel panel = new JPanel();
		panel.setBounds(362, 23, 152, 142);
		contentPane.add(panel);
		panel.setLayout(null);
		
		final JLabel lblCost = new JLabel("Cost:");
		lblCost.setBounds(6, 47, 61, 16);
		panel.add(lblCost);
		
		txfCost = new JTextField();
		txfCost.setText("0");
		txfCost.setBounds(6, 64, 134, 28);
		panel.add(txfCost);
		txfCost.setColumns(10);
		
		final JButton btnAction = new JButton("Action");
		btnAction.setBounds(6, 6, 117, 29);
		panel.add(btnAction);
		
		final JLabel lblBuySellError = new JLabel("");
		lblBuySellError.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblBuySellError.setForeground(new Color(255, 0, 0));
		lblBuySellError.setBounds(6, 95, 134, 41);
		panel.add(lblBuySellError);
		
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_i6k8", "a21014121");
			stmt = connection.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sQuery = "";
		if (sAction == "Buy")
		{
			lblTitle.setText("Buy Items:");
			btnAction.setText("Buy");
			sQuery = "";
		}
		else
		{
			if (sAction == "Sell")
			{
				lblTitle.setText("Sell Items:");
				btnAction.setText("Sell");
				sQuery = "";
			}
			else
			{
				lblTitle.setText("Services:");
				btnAction.setText("Buy Service");
				lblCost.setVisible(false);
				txfCost.setVisible(false);
				sQuery = "";
			}
		}
		
		try {
			rs = executeQuery("select AccountID, COUNT(*) as \"Number of Characters\" from CharacterOwned Group By AccountID");
			tblBuySellItems.setModel(Main_Window.buildTableModel(rs));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					stmt.close();
					connection.close();
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// when action button pressed bs1
		btnAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try{
					int cost = Integer.parseInt(txfCost.getText());
					lblBuySellError.setText("");
					String sQuery = "";
					int iRowSelected = tblBuySellItems.getSelectedRow();
	
					
					if (iRowSelected != -1)
					{
						if (sAction == "Buy")
						{
							tblBuySellItems.getModel().getValueAt(iRowSelected, 0);
							sQuery = "";
						}
						else
						{
							if (sAction == "Sell")
							{
								tblBuySellItems.getModel().getValueAt(iRowSelected, 0);
								sQuery = "";
							}
							else
							{
								tblBuySellItems.getModel().getValueAt(iRowSelected, 0);
								sQuery = "";
							}
						}
							ResultSet rs = null;
							rs = executeQuery(sQuery);
							rs = executeQuery("select P1.AccountID, p1.AccountName, p2.server from Player1 P1, Player2 P2 where P1.AccountID=P2.AccountID");
							//tblBuySellItems.setModel(buildTableModel(rs));
					}
				}
				catch (NumberFormatException e1)
				{
					lblBuySellError.setText("<html>Please Enter Cost <br> As Integer</html>");
				}
			}
		});
	}

	// executes query
	public ResultSet executeQuery(String sQuery){
		ResultSet rs = null;
		try {			
			rs = stmt.executeQuery(sQuery);	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return rs;
	}
	

}
