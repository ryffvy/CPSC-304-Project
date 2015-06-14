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
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Main_Window extends JFrame{
	private JTable table;
//	private static Connection connection;
	
	public Main_Window(){
		
//		connection = con;
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		setSize(707,364);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(425, 23, 275, 208);
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
		
		setSize(707,364);
		getContentPane().setLayout(null);
		
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
		JButton btnGetTableplayer = new JButton("Get Table \"Player1\"");
		btnGetTableplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayTable();
				
			}
		});
		btnGetTableplayer.setBounds(39, 74, 200, 50);
		getContentPane().add(btnGetTableplayer);
		
		
		
	}
	
//	TO BE WORKED ON
	public void displayTable() {
				//Fetch Data from the database
				try {
					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
					Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_i6k8", "a21014121");
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("select * from Player1");
					JTable table = new JTable(buildTableModel(rs));
					JScrollPane scrollTable = new JScrollPane(table);
					JFrame frame = new JFrame("Testing Table");
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
