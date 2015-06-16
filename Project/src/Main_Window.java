package src;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Font;

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

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


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
		
		final JLabel lblName = new JLabel("Name: ");
		lblName.setBounds(6, 0, 44, 16);
		panel_3.add(lblName);
		final JLabel lblNameFill = new JLabel();
		lblNameFill.setBounds(48, 1, 150, 16);
		panel_3.add(lblNameFill);
		
		JLabel lblClass = new JLabel("Class: ");
		lblClass.setBounds(6, 30, 44, 16);
		panel_3.add(lblClass);
		final JLabel lblClassFill = new JLabel();
		lblClassFill.setBounds(48, 30, 150, 16);
		panel_3.add(lblClassFill);
		
		JLabel lblLevel = new JLabel("Level: ");
		lblLevel.setBounds(6, 60, 44, 16);
		panel_3.add(lblLevel);
		final JLabel lblLevelFill = new JLabel();
		lblLevelFill.setBounds(48, 60, 150, 16);
		panel_3.add(lblLevelFill);
		
		JLabel lblRace = new JLabel("Race: ");
		lblRace.setBounds(6, 90, 44, 16);
		panel_3.add(lblRace);
		final JLabel lblRaceFill = new JLabel();
		lblRaceFill.setBounds(48, 90, 150, 16);
		panel_3.add(lblRaceFill);
		
		JLabel lblGold = new JLabel("Gold: ");
		lblGold.setBounds(6, 120, 44, 16);
		panel_3.add(lblGold);
		final JLabel lblGoldFill = new JLabel();
		lblGoldFill.setBounds(48, 120, 150, 16);
		panel_3.add(lblGoldFill);
		
		//Create and add the list of Characters the Player owns into the combo box
		JLabel lblCharacter = new JLabel("Characters: ");
		lblCharacter.setBounds(6, 6, 128, 16);
		panel_5.add(lblCharacter);
		String[] playerCharList = {"(No character selected)","","","","",""};		//A player can only have up to 5 characters.
															//+1 space to show an empty space when the application is first opened
		try {
			ResultSet rs = stmt.executeQuery("Select * from CharacterOwned where AccountID = " + accountID);
			int count = 1;
			while(rs.next()){
				playerCharList[count] = rs.getString("CharName");
				count++;
			}
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		//Fill in the information of the selected Character
		final JComboBox comboBox = new JComboBox(playerCharList);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String selectedChar = (String) comboBox.getSelectedItem();
				if(!(selectedChar.equals("(No character selected)"))) {
					try {
						ResultSet rs = stmt.executeQuery("Select * from CharacterOwned CO where CharName='"+ selectedChar + "'");
						while(rs.next()) {
							lblNameFill.setText(selectedChar);
							lblClassFill.setText(rs.getString("Class"));
							lblLevelFill.setText(rs.getString("Levels"));
							lblRaceFill.setText(rs.getString("Race"));
//							lblGoldFill.setText(rs.getString("Gold"));
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else {
					lblNameFill.setText("");
					lblClassFill.setText("");
					lblLevelFill.setText("");
					lblRaceFill.setText("");
					lblGoldFill.setText("");
				}
			}
		});
		comboBox.setBounds(22, 34, 200, 27);
		panel_5.add(comboBox);
		
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
		
		
		//Create a Guild tab
		JPanel tabGuild1 = new JPanel();
		tabbedPane.addTab("Guild", null, tabGuild1, null);
		tabGuild1.setLayout(null);
		
		JLabel lblGuild_1 = new JLabel("Guild: ");
		lblGuild_1.setBounds(17, 6, 41, 23);
		tabGuild1.add(lblGuild_1);
		
		JComboBox listGuildBox = new JComboBox();
		listGuildBox.setBounds(57, 5, 200, 25);
		tabGuild1.add(listGuildBox);
		
//		JPanel panel_7 = new JPanel();
//		panel_7.setBounds(17, 41, 242, 121);
//		tabGuild1.add(panel_7);
//		panel_7.setLayout(null);
		
		JLabel lblGuildLeader = new JLabel("GuildLeader:");
		lblGuildLeader.setBounds(17, 40, 200, 22);
		tabGuild1.add(lblGuildLeader);
//		lblGuildLeader.setBounds(0, 0, 200, 22);
//		panel_7.add(lblGuildLeader);
		
		JLabel lblMembers = new JLabel("# Members:");
//		lblMembers.setBounds(0, 30, 200, 22);
		lblMembers.setBounds(17, 70, 200, 22);
		tabGuild1.add(lblMembers);
//		panel_7.add(lblMembers);
		
		JLabel lblGuildServer = new JLabel("Server:");
//		lblGuildServer.setBounds(0, 60, 200, 22);
		lblGuildServer.setBounds(17, 100, 200, 22);
		tabGuild1.add(lblGuildServer);
//		panel_7.add(lblGuildServer);
		
		JLabel lblRosterSize = new JLabel("Roster Size:");
//		lblRosterSize.setBounds(0, 90, 200, 22);
		lblRosterSize.setBounds(17, 130, 200, 22);
		tabGuild1.add(lblRosterSize);
//		panel_7.add(lblRosterSize);

		
		JButton btnJoin = new JButton("Join Guild");
		btnJoin.setBounds(17, 167, 115, 23);
		tabGuild1.add(btnJoin);
		
		JButton btnLeave = new JButton("Leave Guild");
		btnLeave.setBounds(140, 167, 115, 23);
		tabGuild1.add(btnLeave);
		
		JLabel lblGuildMembers = new JLabel("Guild Members:");
		lblGuildMembers.setBounds(277, 6, 200, 23);
		tabGuild1.add(lblGuildMembers);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(277, 41, 393, 148);
		tabGuild1.add(scrollPane_2);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(29, 196, 641, 104);
		tabGuild1.add(panel_8);
		panel_8.setLayout(null);
		
		JButton btnAddPlayer = new JButton("Add Chosen Player");
		btnAddPlayer.setBounds(0, 6, 172, 29);
		panel_8.add(btnAddPlayer);
		
		JLabel lblToGuild = new JLabel("to Guild: ");
		lblToGuild.setBounds(10, 47, 65, 16);
		panel_8.add(lblToGuild);
		
		JComboBox toGuildBox = new JComboBox();
		toGuildBox.setBounds(62, 45, 177, 25);
		panel_8.add(toGuildBox);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(247, 22, 388, 76);
		panel_8.add(scrollPane_3);
		
		JLabel lblP = new JLabel("Players in All Guilds:");
		lblP.setBounds(247, 6, 200, 16);
		panel_8.add(lblP);
		
		
		//Create an Admin tab
		JPanel tabAdmin = new JPanel();
		tabAdmin.setVisible(false);
		tabbedPane.addTab("Admin", null, tabAdmin, null);
		tabAdmin.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(6, 6, 197, 250);
		tabAdmin.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblDisplayOptions = new JLabel("Available Options:");
		lblDisplayOptions.setBounds(6, 0, 140, 16);
		panel_4.add(lblDisplayOptions);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(16, 17, 175, 214);
		panel_4.add(panel_6);
		panel_6.setLayout(null);
		
		ButtonGroup bg = new ButtonGroup();
		
		JRadioButton rdbtnCharacters = new JRadioButton("# of Characters");
		rdbtnCharacters.setSelected(true);
		rdbtnCharacters.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnCharacters.setBounds(0, 0, 150, 30);
		panel_6.add(rdbtnCharacters);
		bg.add(rdbtnCharacters);
		
		JRadioButton rdbtnByServer = new JRadioButton("By server: ");
		rdbtnByServer.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnByServer.setBounds(0, 22, 150, 30);
		panel_6.add(rdbtnByServer);
		bg.add(rdbtnByServer);
		
		JComboBox byServerBox = new JComboBox();
		byServerBox.setBounds(29, 52, 140, 22);
		panel_6.add(byServerBox);
		
		JRadioButton rdbtnPlayersWith = new JRadioButton("<html>Players with > 2 characters and Level greater than: </html>");
		rdbtnPlayersWith.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnPlayersWith.setBounds(0, 78, 175, 37);
		panel_6.add(rdbtnPlayersWith);
		bg.add(rdbtnPlayersWith);
		
		JComboBox levelBox = new JComboBox();
		levelBox.setBounds(29, 120, 75, 22);
		panel_6.add(levelBox);
		
		JRadioButton rdbtnBestPurchaser = new JRadioButton("Best Purchaser");
		rdbtnBestPurchaser.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnBestPurchaser.setBounds(0, 146, 150, 30);
		panel_6.add(rdbtnBestPurchaser);
		bg.add(rdbtnBestPurchaser);
		
		JRadioButton rdbtnPlayerWithMost = new JRadioButton("Player With Most Gold");
		rdbtnPlayerWithMost.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnPlayerWithMost.setBounds(0, 174, 150, 30);
		panel_6.add(rdbtnPlayerWithMost);
		bg.add(rdbtnPlayerWithMost);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(215, 6, 445, 250);
		tabAdmin.add(scrollPane_1);
		
		JButton btnShowPlayers = new JButton("Show Players");
		btnShowPlayers.setBounds(6, 262, 200, 28);
		tabAdmin.add(btnShowPlayers);
		
		JButton btnNewButton = new JButton("Delete Player");
		btnNewButton.setBounds(506, 262, 154, 28);
		tabAdmin.add(btnNewButton);
		
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
