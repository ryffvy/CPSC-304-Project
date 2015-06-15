package src;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;




import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Main_Window extends JFrame{
	private JTable table;
	private static Connection connection;
	private Statement stmt;
	
	public Main_Window(String accountID){
		
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_i6k8", "a21014121");
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		setSize(707,364);
		getContentPane().setLayout(null);
		
		setSize(707,364);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 0, 697, 342);
		getContentPane().add(tabbedPane);
		
		JPanel panPlayerInfo = new JPanel();
		tabbedPane.addTab("Player Info", null, panPlayerInfo, null);
		panPlayerInfo.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 290, 161);
		panPlayerInfo.add(panel);
		panel.setLayout(null);
		
		JLabel lblInfo = new JLabel("Player Info:");
		lblInfo.setBounds(6, 6, 70, 16);
		panel.add(lblInfo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBounds(16, 37, 268, 118);
		panel.add(panel_1);
		
		JLabel label = new JLabel("");
		label.setBounds(251, 0, 70, 48);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(265, 85, 70, 48);
		panel_1.setLayout(null);
		panel_1.add(label);
		panel_1.add(label_1);
		
		final JLabel lblAccountId = new JLabel("Account ID:");
		lblAccountId.setBounds(6, 0, 256, 20);
		panel_1.add(lblAccountId);
		
		JLabel lblGuild = new JLabel("Guild: ");
		lblGuild.setBounds(6, 30, 256, 20);
		panel_1.add(lblGuild);
		
		JLabel lblAccountName = new JLabel("Account Name:");
		lblAccountName.setBounds(6, 60, 256, 20);
		panel_1.add(lblAccountName);
		
		JLabel lblServer = new JLabel("Server:");
		lblServer.setBounds(6, 90, 256, 20);
		panel_1.add(lblServer);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(6, 179, 260, 82);
		panPlayerInfo.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblCharacter = new JLabel("Characters:");
		lblCharacter.setBounds(6, 6, 128, 16);
		panel_5.add(lblCharacter);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(16, 34, 200, 27);
		panel_5.add(comboBox);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(298, 6, 232, 284);
		panPlayerInfo.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblCharacterInfo = new JLabel("Character Info:");
		lblCharacterInfo.setBounds(6, 6, 94, 16);
		panel_2.add(lblCharacterInfo);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(6, 37, 220, 241);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(6, 10, 208, 16);
		panel_3.add(lblName);
		
		JLabel lblClass = new JLabel("Class: ");
		lblClass.setBounds(6, 40, 208, 16);
		panel_3.add(lblClass);
		
		JLabel lblLevel = new JLabel("Label: ");
		lblLevel.setBounds(6, 70, 208, 16);
		panel_3.add(lblLevel);
		
		JLabel lblRace = new JLabel("Race:");
		lblRace.setBounds(6, 100, 208, 16);
		panel_3.add(lblRace);
		
		JLabel lblServer_1 = new JLabel("Server: ");
		lblServer_1.setBounds(6, 130, 208, 16);
		panel_3.add(lblServer_1);
		
		JLabel lblGold = new JLabel("Gold: ");
		lblGold.setBounds(6, 160, 208, 16);
		panel_3.add(lblGold);
		
		JButton btnViewInventory = new JButton("View Inventory");
		btnViewInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ResultSet rs = stmt.executeQuery("select * from Player1");
					displayTable(rs, "Inventory");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnViewInventory.setBounds(534, 105, 136, 50);
		panPlayerInfo.add(btnViewInventory);
		
		JPanel tabGuild = new JPanel();
		tabbedPane.addTab("Guild", null, tabGuild, null);
		
		JPanel tabAdmin = new JPanel();
		tabbedPane.addTab("Admin", null, tabAdmin, null);
		
		//Fetch Data from the database
//		try {
//			Statement stmt = connection.createStatement();
//			ResultSet rs = stmt.executeQuery("select * from Player1");
//			JTable table = new JTable(buildTableModel(rs));
//			JScrollPane scrollTable = new JScrollPane(table);
//			JFrame frame = new JFrame("Testing Table");
//			frame.getContentPane().add(scrollTable, BorderLayout.CENTER);
//		    frame.setSize(300, 150);
//		    frame.setVisible(true);
////			JOptionPane.showMessageDialog(null, new JScrollPane(table));
//			
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
//		TO BE WORKED ON
		//Button for showing table Player1
//		JButton btnGetTableplayer = new JButton("Get Table \"Player1\"");
//		btnGetTableplayer.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				displayTable();
//				
//			}
//		});
//		btnGetTableplayer.setBounds(39, 74, 200, 50);
//		getContentPane().add(btnGetTableplayer);
		
		
	    // display account info
//		try {
//			
//			ResultSet rs;
//			rs = stmt.executeQuery("select * from Player1 p1, player2 p2 where p1.accountid = p2.accountid and p1.accountID = " + accountID);
//		    table = new JTable(buildTableModel(rs));
//		    lblAccountId.setText(lblAccountId.getText());
//		    lblAccountName.setText(lblAccountName.getText() + " " + table.getModel().getValueAt(0, 0));
//		    lblServer.setText(lblAccountId.getText() + accountID);
//		    lblAccountId.setText(lblAccountId.getText() + accountID);
//			
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}
	
//	TO BE WORKED ON
	public void displayTable(ResultSet rs, String name) {
				//Fetch Data from the database
				try {
//					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
//					Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_i6k8", "a21014121");
//					Statement stmt = connection.createStatement();
					JTable table = new JTable(buildTableModel(rs));
					JScrollPane scrollTable = new JScrollPane(table);
					JFrame frame = new JFrame(name);
					frame.getContentPane().add(scrollTable, BorderLayout.CENTER);
				    frame.setSize(300, 150);
				    frame.setVisible(true);
//					JOptionPane.showMessageDialog(null, new JScrollPane(table));
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
	
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
