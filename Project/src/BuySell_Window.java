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
import java.awt.Component;

public class BuySell_Window extends JFrame {

	private JPanel contentPane;
	private JTextField txfCost;
	Connection connection;
	Statement stmt;
	ResultSet rs;
	private JTextField txfQuantity;
	private String sActionSelected;
	final JLabel lblBuySellError = new JLabel("");
	private boolean bSuccess;
	
	// elements
	final JTable tblBuySellItems = new JTable();
	final JLabel lblTitle = new JLabel("Title:");
	JPanel panel = new JPanel();
	final JLabel lblCost = new JLabel("Cost:");
	final JButton btnAction = new JButton("Action");
	JLabel lblQuantity = new JLabel("Quantity:");
	JPanel panServices = new JPanel();	
	final JTable tblPurchasedServices = new JTable();
	JLabel lblPurchasedServices = new JLabel("Purchased Services:");
	JScrollPane scrollPane = new JScrollPane(tblPurchasedServices);

	
	/**
	 * Create the frame.
	 */
	public BuySell_Window(final String sAction, String sAccountName, int sAccountID) {
		sActionSelected = sAction;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 702, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane srpBuySellItems = new JScrollPane(tblBuySellItems);
		srpBuySellItems.setBounds(16, 23, 659, 258);
		contentPane.add(srpBuySellItems);
		
		lblTitle.setBounds(16, 6, 84, 16);
		contentPane.add(lblTitle);
		
		panel.setBounds(16, 286, 336, 119);
		contentPane.add(panel);
		panel.setLayout(null);
		

		lblCost.setBounds(6, 47, 61, 16);
		panel.add(lblCost);
		
		txfCost = new JTextField();
		txfCost.setText("0");
		txfCost.setBounds(6, 64, 134, 28);
		panel.add(txfCost);
		txfCost.setColumns(10);
		

		btnAction.setBounds(6, 6, 117, 29);
		panel.add(btnAction);
		
		lblBuySellError.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblBuySellError.setForeground(new Color(255, 0, 0));
		lblBuySellError.setBounds(157, 6, 173, 41);
		panel.add(lblBuySellError);
		
		txfQuantity = new JTextField();
		txfQuantity.setText("0");
		txfQuantity.setColumns(10);
		txfQuantity.setBounds(157, 64, 134, 28);
		panel.add(txfQuantity);
		
		lblQuantity.setBounds(157, 47, 61, 16);
		panel.add(lblQuantity);
		
		// purchased services panel
		panServices.setBounds(352, 286, 332, 102);
		contentPane.add(panServices);
		panServices.setLayout(null);	
		scrollPane.setBounds(0, 19, 341, 83);
		panServices.add(scrollPane);		
		lblPurchasedServices.setBounds(0, 0, 287, 16);
		panServices.add(lblPurchasedServices);
		
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_h3w8", "a56415136");
			stmt = connection.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		update();
		
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
					int iPrice = Integer.parseInt(txfCost.getText());
					int iQuantity = Integer.parseInt(txfQuantity.getText());
					lblBuySellError.setText("");
					String sQuery = "";
					int iRowSelected = tblBuySellItems.getSelectedRow();
	
					
					if (iRowSelected != -1)
					{
						if (sAction == "Buy" )
						{
							if (iPrice > 0 && iQuantity > 0)
							{
								sQuery = "insert into placebuy values ('" + Main_Window.sSelectedChar + "',sequencebuy.nextval,"
								+ tblBuySellItems.getModel().getValueAt(iRowSelected, 2) + ","
								+ iPrice + ","
								+ iQuantity + ")";
							}
							else
							{
								sQuery = "";
								lblBuySellError.setText("<html>Price and Quantity <br> must be larger than 0</html>");
							}
						}
						else
						{
							if (sAction == "Sell")
							{
								if (iPrice > 0 && iQuantity > 0)
								{
									tblBuySellItems.getModel().getValueAt(iRowSelected, 0);
									sQuery = "insert into placesell values ('" + Main_Window.sSelectedChar + "', sequencesell.nextval,"
											+ tblBuySellItems.getModel().getValueAt(iRowSelected, 0) + ","
											+ iPrice + ","
											+ iQuantity 
											+ ", sysdate, 1)";
								}
								else
								{
									sQuery = "";
									lblBuySellError.setText("<html>Price and Quantity <br> must be larger than 0</html>");
								}
							}
							else
							{
								sQuery = "insert into purchases values (" + Main_Window.sAccountID + ", " + tblBuySellItems.getModel().getValueAt(iRowSelected, 0) + ")";
							}
						}
						if (sQuery != "")
						{
							ResultSet rs = null;
							rs = executeQuery(sQuery);
							if (bSuccess)
								if (sAction != "Service")
									dispose();
								else
									update();
						}
							//rs = executeQuery("select P1.AccountID, p1.AccountName, p2.server from Player1 P1, Player2 P2 where P1.AccountID=P2.AccountID");*/
							//tblBuySellItems.setModel(buildTableModel(rs));
					}
				}
				catch (NumberFormatException e1)
				{
					lblBuySellError.setText("<html>Please Enter Price <br>and Quantity As Integer</html>");
				}
			}
		});
	}

	// updates the window
	public void update()
	{
		String sQuery = "";
		if (sActionSelected == "Buy")
		{
			lblTitle.setText("Buy Items:");
			btnAction.setText("Buy");
			panServices.setVisible(false);
			sQuery = "select charname as \"CharacterName\", orderid as \"Order ID\", itemid as \"Item ID\", sellprice as \"Price\", quantity as \"Quantity\", fulfill as \"Fulfilled\" from placesell";
		}
		else
		{
			if (sActionSelected == "Sell")
			{
				lblTitle.setText("Sell Items:");
				btnAction.setText("Sell");
				panServices.setVisible(false);
				sQuery = "select i.itemid as \"Item ID\" from ininventory n, item i where i.itemid = n.itemid and n.charname = '" + Main_Window.sSelectedChar + "'";
			}
			else
			{
				lblTitle.setText("Services:");
				btnAction.setText("Buy Service");
				lblCost.setVisible(false);
				txfCost.setVisible(false);
				lblQuantity.setVisible(false);
				txfQuantity.setVisible(false);
				sQuery = "select s.serviceid as \"Service ID\", s.name as \"Name\" from purchases p, service s where accountid = " + Main_Window.sAccountID + " and  p.serviceid = s.serviceid";
				rs = executeQuery(sQuery);
				try {
					tblPurchasedServices.setModel(Main_Window.buildTableModel(rs));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sQuery = "select serviceid as \"Service ID\", name, price from service";
			}
		}
		
		try {
			rs = executeQuery(sQuery);
			tblBuySellItems.setModel(Main_Window.buildTableModel(rs));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	// executes query
	public ResultSet executeQuery(String sQuery){
		ResultSet rs = null;
		try {			
			rs = stmt.executeQuery(sQuery);	
			bSuccess = true;
		} catch (SQLException e1) {
			if (sActionSelected == "Service")
				lblBuySellError.setText("<html>You already purchased <br> this service</html>");
			else
				e1.printStackTrace();
			bSuccess = false;
		}
		
		return rs;
	}
}
