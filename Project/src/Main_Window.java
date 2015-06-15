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
	
	public Main_Window(final String accountID){
		
		//Connect to the database
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_i6k8", "a21014121");
			stmt = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setBounds(325,150,707,364);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 0, 697, 342);
		getContentPane().add(tabbedPane);
		
		JPanel panPlayerInfo = new JPanel();
		tabbedPane.addTab("Player Info", null, panPlayerInfo, null);
		panPlayerInfo.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 250, 161);
		panPlayerInfo.add(panel);
		panel.setLayout(null);
		
		JLabel lblInfo = new JLabel("Player Info: ");
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
		
		final JLabel lblAccountId = new JLabel("Account ID: ");
		lblAccountId.setBounds(6, 0, 256, 20);
		panel_1.add(lblAccountId);
		
		JLabel lblGuild = new JLabel("Guild: ");
		lblGuild.setBounds(6, 30, 256, 20);
		panel_1.add(lblGuild);
		
		JLabel lblAccountName = new JLabel("Account Name: ");
		lblAccountName.setBounds(6, 60, 256, 20);
		panel_1.add(lblAccountName);
		
		JLabel lblServer = new JLabel("Server: ");
		lblServer.setBounds(6, 90, 256, 20);
		panel_1.add(lblServer);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(6, 179, 260, 82);
		panPlayerInfo.add(panel_5);
		panel_5.setLayout(null);
		
		//Create and add the list of Characters the Player owns into the combo box
		JLabel lblCharacter = new JLabel("Characters: ");
		lblCharacter.setBounds(0, 6, 128, 16);
		panel_5.add(lblCharacter);
		String[] playerCharList = {"","","","",""};		//A player can only have up to 5 characters.
		try {
			ResultSet rs = stmt.executeQuery("Select * from CharacterOwned where AccountID = " + accountID);
			int count = 0;
			while(rs.next()){
				playerCharList[count] = rs.getString("CharName");
				count++;
			}
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		JComboBox comboBox = new JComboBox(playerCharList);
		comboBox.setBounds(16, 34, 200, 27);
		panel_5.add(comboBox);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(276, 6, 232, 284);
		panPlayerInfo.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblCharacterInfo = new JLabel("Character Info: ");
		lblCharacterInfo.setBounds(0, 6, 94, 16);
		panel_2.add(lblCharacterInfo);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 32, 198, 241);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblName = new JLabel("Name: ");
		lblName.setBounds(6, 0, 208, 16);
		panel_3.add(lblName);
		
		JLabel lblClass = new JLabel("Class: ");
		lblClass.setBounds(6, 30, 208, 16);
		panel_3.add(lblClass);
		
		JLabel lblLevel = new JLabel("Label: ");
		lblLevel.setBounds(6, 60, 208, 16);
		panel_3.add(lblLevel);
		
		JLabel lblRace = new JLabel("Race: ");
		lblRace.setBounds(6, 90, 208, 16);
		panel_3.add(lblRace);
		
		JLabel lblServer_1 = new JLabel("Server: ");
		lblServer_1.setBounds(6, 120, 208, 16);
		panel_3.add(lblServer_1);
		
		JLabel lblGold = new JLabel("Gold: ");
		lblGold.setBounds(6, 150, 208, 16);
		panel_3.add(lblGold);
		
		//View a specified Character's inventory
		JButton btnViewInventory = new JButton("View Inventory");
		btnViewInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ResultSet rs = stmt.executeQuery("select * from CharacterOwned"); 
					displayTable(rs, "Inventory");
				} catch (SQLException e) {
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
		
		
	    //Display Player Info
		try {			
			ResultSet rs = stmt.executeQuery("select * from Player1 p1, Player2 p2 where p1.AccountID = p2.AccountID and p1.AccountID = " + accountID);
			while(rs.next()){ 
				lblAccountId.setText(lblAccountId.getText() + rs.getString("AccountID"));
				lblAccountName.setText(lblAccountName.getText() + rs.getString("AccountName"));
				lblServer.setText(lblServer.getText() + rs.getString("Server"));
			}		
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	//Create and display a pop up table
	public void displayTable(ResultSet rs, String tableName) {
		try {
			JTable table = new JTable(buildTableModel(rs));
			JScrollPane scrollTable = new JScrollPane(table);
			JFrame frame = new JFrame(tableName);
			frame.getContentPane().add(scrollTable, BorderLayout.CENTER);
		    frame.setBounds(400,200,table.getPreferredSize().width, 200);
		    frame.setVisible(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	//Save values from database into object
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
}
