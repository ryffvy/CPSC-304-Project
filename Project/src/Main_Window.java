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
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

import javax.swing.JList;

import javax.swing.JList;

public class Main_Window extends JFrame{
	private JTable table;
	private static Connection connection;
	private static Statement stmt;
	public static String sSelectedChar;
	public static String sAccountID;
	
	public Main_Window(final String accountID){
		sAccountID = accountID;
		sSelectedChar = "";
		//Connect to the database
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_h3w8", "a56415136");
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE,
			         ResultSet.HOLD_CURSORS_OVER_COMMIT);
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
		lblGuild.setBounds(6, 30, 84, 20);
		panel_1.add(lblGuild);
		
		JLabel lblAccountName = new JLabel("Account Name: ");
		lblAccountName.setBounds(6, 60, 256, 20);
		panel_1.add(lblAccountName);
		
		JLabel lblServer = new JLabel("Server: ");
		lblServer.setBounds(6, 90, 256, 20);
		panel_1.add(lblServer);
		
		final JComboBox guildBox = new JComboBox();
		guildBox.setMaximumRowCount(5);
		guildBox.setBounds(42, 31, 180, 22);
		panel_1.add(guildBox);
		
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
		final JComboBox comboBox = new JComboBox();
		comboBox.insertItemAt("(No character selected)", 0);
		comboBox.setSelectedIndex(0);
		comboBox.setMaximumRowCount(3);
		JLabel lblCharacter = new JLabel("Characters: ");
		lblCharacter.setBounds(6, 6, 128, 16);
		panel_5.add(lblCharacter);
		try {
			ResultSet rs = stmt.executeQuery("Select * from CharacterOwned where AccountID = " + accountID);
			while(rs.next()){
				comboBox.addItem(rs.getString("CharName"));
			}
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		

		comboBox.setBounds(22, 34, 200, 27);
		panel_5.add(comboBox);
		
		//View a specified Character's inventory
		JButton btnViewInventory = new JButton("View Inventory");
		btnViewInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedChar = (String) comboBox.getSelectedItem();
				if (!(selectedChar.equals("(No character selected)"))) {
					try {
						ResultSet rs = stmt.executeQuery("select ItemName, Quantity "
								+ "						  from Contain c, Item i"
								+ "						  where c.ItemID=i.ItemID and c.CharName='" + selectedChar + "'"); 
						displayTable(rs, selectedChar + "'s Inventory");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnViewInventory.setBounds(534, 105, 136, 50);
		panPlayerInfo.add(btnViewInventory);
		
		JPanel panMarket = new JPanel();
		tabbedPane.addTab("Market", null, panMarket, null);
		panMarket.setLayout(null);
		
		final JTable tblSellOrders = new JTable();
		JScrollPane srpSellOrders = new JScrollPane(tblSellOrders);
		srpSellOrders.setBounds(23, 32, 214, 111);
		panMarket.add(srpSellOrders);
		
		JLabel lblYourSellOrders = new JLabel("Your Sell Orders:");
		lblYourSellOrders.setBounds(23, 10, 105, 16);
		panMarket.add(lblYourSellOrders);
		
		final JTable tblBuyOrders = new JTable();
		JScrollPane srpBuyOrders = new JScrollPane(tblBuyOrders);
		srpBuyOrders.setBounds(249, 32, 203, 111);
		panMarket.add(srpBuyOrders);
		
		JLabel lblBuyOrders = new JLabel("Your Buy Orders:");
		lblBuyOrders.setBounds(249, 10, 105, 16);
		panMarket.add(lblBuyOrders);
		
		JButton button = new JButton("View Inventory");
		button.setBounds(503, 93, 136, 50);
		panMarket.add(button);
		
		JPanel panBuySell = new JPanel();
		panBuySell.setBounds(23, 155, 136, 119);
		panMarket.add(panBuySell);
		panBuySell.setLayout(null);
		
		JButton btnBuy = new JButton("Buy Order");
		btnBuy.setBounds(0, 22, 132, 29);
		panBuySell.add(btnBuy);
		
		JButton btnSell = new JButton("Sell Order");
		btnSell.setBounds(0, 52, 132, 29);
		panBuySell.add(btnSell);
		
		JLabel lblPlaceOrder = new JLabel("Place Order:");
		lblPlaceOrder.setBounds(0, 0, 127, 16);
		panBuySell.add(lblPlaceOrder);
		
		JButton btnBuyService = new JButton("Buy Service");
		btnBuyService.setBounds(0, 82, 132, 29);
		panBuySell.add(btnBuyService);
		
		final JLabel lblCharMarket = new JLabel("Character: ");
		lblCharMarket.setBounds(464, 32, 195, 16);
		panMarket.add(lblCharMarket);
		
		final JLabel lblGoldMarket = new JLabel("Gold:");
		lblGoldMarket.setBounds(464, 60, 164, 16);
		panMarket.add(lblGoldMarket);
		
		final JTable tblTransaction = new JTable();
		JScrollPane srpTransaction = new JScrollPane(tblTransaction);
		srpTransaction.setBounds(171, 182, 292, 92);
		panMarket.add(srpTransaction);
		
		JLabel lblTransactions = new JLabel("Transactions: ");
		lblTransactions.setBounds(171, 165, 214, 16);
		panMarket.add(lblTransactions);
		
		JButton btnUpdateMarket = new JButton("Update");
		btnUpdateMarket.setBounds(511, 245, 117, 29);
		panMarket.add(btnUpdateMarket);
		
		
		//Create a Guild tab
		JPanel tabGuild1 = new JPanel();
		tabbedPane.addTab("Guild", null, tabGuild1, null);
		tabGuild1.setLayout(null);
		
		JLabel lblGuildLeader = new JLabel("GuildLeader:");
		lblGuildLeader.setBounds(17, 40, 90, 22);
		tabGuild1.add(lblGuildLeader);
		final JLabel lblGuildLeaderFill = new JLabel();
		lblGuildLeaderFill.setBounds(105, 40, 162, 22);
		tabGuild1.add(lblGuildLeaderFill);
		
		JLabel lblMembers = new JLabel("# of Members:");
		lblMembers.setBounds(17, 70, 90, 22);
		tabGuild1.add(lblMembers);
		final JLabel lblMembersFill = new JLabel();
		lblMembersFill.setBounds(105, 70, 162, 22);
		tabGuild1.add(lblMembersFill);
		
		JLabel lblGuildServer = new JLabel("Server:");
		lblGuildServer.setBounds(17, 100, 90, 22);
		tabGuild1.add(lblGuildServer);
		final JLabel lblGuildServerFill = new JLabel();
		lblGuildServerFill.setBounds(105, 100, 162, 22);
		tabGuild1.add(lblGuildServerFill);
		
		JLabel lblRosterSize = new JLabel("Roster Size:");
		lblRosterSize.setBounds(17, 130, 90, 22);
		tabGuild1.add(lblRosterSize);
		final JLabel lblRosterSizeFill = new JLabel();
		lblRosterSizeFill.setBounds(105, 130, 162, 22);
		tabGuild1.add(lblRosterSizeFill);
		
		JLabel lblGuild_1 = new JLabel("Guild: ");
		lblGuild_1.setBounds(17, 6, 41, 23);
		tabGuild1.add(lblGuild_1);
		
		JLabel lblGuildMembers = new JLabel("Guild Members:");
		lblGuildMembers.setBounds(277, 6, 200, 23);
		tabGuild1.add(lblGuildMembers);
		
		final JTable table = new JTable();
		JScrollPane scrollPane_2 = new JScrollPane(table);
		scrollPane_2.setBounds(277, 41, 393, 148);
		tabGuild1.add(scrollPane_2);
		
		//Display all the Guilds in the database from comboBox listGuildBox
		final JComboBox listGuildBox = new JComboBox();
		listGuildBox.insertItemAt("(no guild selected)", 0);
		listGuildBox.setSelectedIndex(0);
		listGuildBox.setMaximumRowCount(5);
		try {
			ResultSet rs = stmt.executeQuery("select GuildName from Guild_Owns");
			while(rs.next()) {
				listGuildBox.addItem(rs.getString("GuildName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Fill in all information when a guild is selected from listGuildBox
		listGuildBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String selectedGuild = (String) listGuildBox.getSelectedItem();
				if(!(selectedGuild.equals("(no guild selected)"))) {
					try {
						//Check if there's no guild leader
						ResultSet rs = stmt.executeQuery("select distinct AccountName "
								+ "						  from Player1 P1,GuildLeader GL,Guild_Owns GO "
								+ "						  where P1.AccountID=GL.AccountID and GO.AccountID=GL.AccountID and GO.GuildName='" +selectedGuild+ "'");
						if(!rs.next()) lblGuildLeaderFill.setText("(no guild leader)");
						else lblGuildLeaderFill.setText(rs.getString("AccountName"));
						
						rs = stmt.executeQuery("select RosterSize,Server"
								+ "						  from Guild_Owns"
								+ "						  where GuildName='" + selectedGuild + "'");
						if(rs.next()) {
							lblGuildServerFill.setText(rs.getString("Server"));
							lblRosterSizeFill.setText(rs.getString("RosterSize"));
						}
						rs = stmt.executeQuery("select count(AccountID) as Members"
								+ "				from BelongsTo"
								+ "				where GuildName='" + selectedGuild + "'");
						while(rs.next()) {
							lblMembersFill.setText(rs.getString("Members"));
						}
						//Display a table of all members in the selected guild
						rs = stmt.executeQuery("select P1.AccountName from Player1 P1, BelongsTo BT where BT.GuildName='" +selectedGuild+ "' and BT.AccountID=P1.AccountID");
						table.setModel(buildTableModel(rs));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else {
					lblGuildLeaderFill.setText("");
					lblMembersFill.setText("");
					lblGuildServerFill.setText("");
					lblRosterSizeFill.setText("");
					DefaultTableModel clearTable = new DefaultTableModel();
					table.setModel(clearTable);
				}
			}
		});
		listGuildBox.setBounds(57, 5, 200, 25);
		tabGuild1.add(listGuildBox);
		
		//Handle Join Guild Button
		JButton btnJoin = new JButton("Join Guild");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedGuild = (String) listGuildBox.getSelectedItem();
				if (!(selectedGuild.equals("(no guild selected)"))) {
					try {
						ResultSet rs = stmt.executeQuery("select AccountID from BelongsTo where Guildname='" + selectedGuild + "' and AccountID="+accountID);
						if (rs.next()) {
							JOptionPane.showMessageDialog(null, "You are already a member of the guild: " + selectedGuild);
						}
						else {
							try {
								rs = stmt.executeQuery("insert into BelongsTo values('" +selectedGuild+ "', " +accountID+")");
								//Update the label information
								rs = stmt.executeQuery("select count(AccountID) as Members"
										+ "				from BelongsTo"
										+ "				where GuildName='" + selectedGuild + "'");
								while(rs.next()) {
									lblMembersFill.setText(rs.getString("Members"));
								}
								guildBox.removeAllItems();
								rs = stmt.executeQuery("select distinct * from BelongsTo where AccountID=" +accountID);
								while(rs.next()) {
									guildBox.addItem(rs.getString("GuildName"));
								}
								JOptionPane.showMessageDialog(null, "Join Guild Successful!");
								
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "No guild has been selected.");
				}
			}
		});
		btnJoin.setBounds(17, 167, 115, 23);
		tabGuild1.add(btnJoin);
		
		
		
		//Handle Leave Guild button
		JButton btnLeave = new JButton("Leave Guild");
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedGuild = (String) listGuildBox.getSelectedItem();
				if (!(selectedGuild.equals("(no guild selected)"))) {
					try {
						ResultSet rs = stmt.executeQuery("select AccountID from BelongsTo where Guildname='" + selectedGuild + "' and AccountID="+accountID);
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null, "You are not a member of the guild: " + selectedGuild);
						}
						else {
							try {
								rs = stmt.executeQuery("delete from BelongsTo where accountid=" + accountID + "and GuildName='" + selectedGuild + "'");
								rs = stmt.executeQuery("delete from GuildLeader where accountid=" + accountID);
								//Update the label information
								rs = stmt.executeQuery("select GL.AccountID from GuildLeader GL, Guild_Owns GO where GO.GuildName='" +selectedGuild+ "'");
								if(!rs.next()) lblGuildLeaderFill.setText("(no guild leader)");
								rs = stmt.executeQuery("select count(AccountID) as Members"
										+ "				from BelongsTo"
										+ "				where GuildName='" + selectedGuild + "'");
								while(rs.next()) {
									lblMembersFill.setText(rs.getString("Members"));
								}
								guildBox.removeAllItems();
								rs = stmt.executeQuery("select distinct * from BelongsTo where AccountID=" +accountID);
								while(rs.next()) {
									guildBox.addItem(rs.getString("GuildName"));
								}
								JOptionPane.showMessageDialog(null, "Leave Guild Successful!");
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "No guild has been selected.");
				}
			}
		});
		btnLeave.setBounds(140, 167, 115, 23);
		tabGuild1.add(btnLeave);
		
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
		panel_4.setBounds(6, 6, 208, 250);
		tabAdmin.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblDisplayOptions = new JLabel("Available Options:");
		lblDisplayOptions.setBounds(6, 0, 140, 16);
		panel_4.add(lblDisplayOptions);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(6, 17, 202, 227);
		panel_4.add(panel_6);
		panel_6.setLayout(null);
		
		final ButtonGroup bg = new ButtonGroup();
		
		JRadioButton rdbtnCharacters = new JRadioButton("# of Characters");
		rdbtnCharacters.setName("CH");
		rdbtnCharacters.setSelected(true);
		rdbtnCharacters.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnCharacters.setBounds(0, 0, 150, 30);
		panel_6.add(rdbtnCharacters);
		bg.add(rdbtnCharacters);
		

		final JRadioButton rdbtnByServer = new JRadioButton("By server: ");
		rdbtnByServer.setName("SV");
		rdbtnByServer.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnByServer.setBounds(0, 22, 150, 30);
		panel_6.add(rdbtnByServer);
		bg.add(rdbtnByServer);
		
		// server combo box
		final JComboBox cmbServer = new JComboBox();
		cmbServer.setEnabled(false);
		cmbServer.setBounds(29, 52, 140, 22);
		panel_6.add(cmbServer);
		
		final JRadioButton rdbtnPlayersWith = new JRadioButton("<html>Players with > 2 characters and Level greater than: </html>");
		rdbtnPlayersWith.setName("PW");
		rdbtnPlayersWith.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnPlayersWith.setBounds(0, 78, 175, 37);
		panel_6.add(rdbtnPlayersWith);
		bg.add(rdbtnPlayersWith);
		
		// level combo box
		final JComboBox cmbLevel = new JComboBox();
		cmbLevel.setEnabled(false);
		cmbLevel.setBounds(29, 120, 75, 22);
		cmbLevel.addItem("5");
		cmbLevel.addItem("10");
		cmbLevel.addItem("15");
		cmbLevel.addItem("20");
		cmbLevel.addItem("25");
		cmbLevel.addItem("30");
		cmbLevel.addItem("35");
		cmbLevel.addItem("40");
		cmbLevel.addItem("45");
		cmbLevel.addItem("50");
		cmbLevel.addItem("55");
		cmbLevel.addItem("60");
		cmbLevel.addItem("65");
		cmbLevel.addItem("70");
		cmbLevel.addItem("75");
		cmbLevel.addItem("80");
		panel_6.add(cmbLevel);
		
		final JRadioButton rdbtnServicePurchaser = new JRadioButton("Service Purchaser");
		rdbtnServicePurchaser.setName("SP");
		rdbtnServicePurchaser.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnServicePurchaser.setBounds(0, 146, 127, 30);
		panel_6.add(rdbtnServicePurchaser);
		bg.add(rdbtnServicePurchaser);
		
		final JRadioButton rdbtnPlayerWithMost = new JRadioButton("Player With Most Gold");
		rdbtnPlayerWithMost.setName("PM");
		rdbtnPlayerWithMost.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnPlayerWithMost.setBounds(0, 174, 150, 30);
		panel_6.add(rdbtnPlayerWithMost);
		bg.add(rdbtnPlayerWithMost);

		// admin tab table
		final JTable tblAdmin = new JTable();
		JScrollPane srpAdmin = new JScrollPane(tblAdmin);
		srpAdmin.setBounds(226, 6, 434, 250);
		tabAdmin.add(srpAdmin);
		
		// show players button
		JButton btnShowPlayers = new JButton("Show Players");
		btnShowPlayers.setBounds(6, 262, 200, 28);
		tabAdmin.add(btnShowPlayers);
		
		final JButton btnDeletePlayer = new JButton("Delete Player");
		btnDeletePlayer.setEnabled(false);
		btnDeletePlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int iRowSelected = tblAdmin.getSelectedRow();
				if (iRowSelected != -1)
				{
					ResultSet rs = null;
					try {
					rs = exQuery("delete from Player1 where AccountID = " + tblAdmin.getModel().getValueAt(iRowSelected, 0));
					rs = exQuery("select P1.AccountID, p1.AccountName, p2.server from Player1 P1, Player2 P2 where P1.AccountID=P2.AccountID");
						tblAdmin.setModel(buildTableModel(rs));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnDeletePlayer.setBounds(506, 262, 154, 28);
		tabAdmin.add(btnDeletePlayer);
		
		JRadioButton rdbtAllPlayer = new JRadioButton("All Players");
		rdbtAllPlayer.setName("AP");
		rdbtAllPlayer.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtAllPlayer.setBounds(0, 204, 150, 30);
		bg.add(rdbtAllPlayer);
		panel_6.add(rdbtAllPlayer);
		
		final JComboBox cmbPurchaser = new JComboBox();
		cmbPurchaser.setEnabled(false);
		cmbPurchaser.setBounds(123, 148, 79, 27);
		cmbPurchaser.addItem("Best");
		cmbPurchaser.addItem("Worst");
		panel_6.add(cmbPurchaser);
		
		// fill the cmbServer
		try {
			ResultSet rs = exQuery("select server from player2 group by server");
			while(rs.next()) {
				cmbServer.addItem(rs.getString("server"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// when place buy order button is pressed mb1
		btnBuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (sSelectedChar != "")
					new BuySell_Window("Buy", "", Integer.parseInt(accountID)).setVisible(true);
			}
		});
		
		// when update button is pressed
		btnUpdateMarket.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (sSelectedChar != "")
				{
					RefreshMarket(tblSellOrders, tblBuyOrders, lblGoldMarket, tblTransaction);
				}
			}
		});
		
		// when place sell order button is pressed mb2
		btnSell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (sSelectedChar != "")
					new BuySell_Window("Sell", "", Integer.parseInt(accountID)).setVisible(true);
			}
		});
		
		// when buy service button is pressed mb3
		btnBuyService.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new BuySell_Window("Service", "", Integer.parseInt(accountID)).setVisible(true);
			}
		});
		
		// when rdbtnByServer radio button is selected/deselected
		rdbtnByServer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (rdbtnByServer.isSelected())
					cmbServer.setEnabled(true);
				else
					cmbServer.setEnabled(false);
			}
		});
		
		// when rdbtnPlayersWith radio button is selected/deselected
		rdbtnPlayersWith.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (rdbtnPlayersWith.isSelected())
					cmbLevel.setEnabled(true);
				else
					cmbLevel.setEnabled(false);
			}
		});
		
		// when rdbtnServicePurchaser radio button is selected/deselected
		rdbtnServicePurchaser.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (rdbtnServicePurchaser.isSelected())
					cmbPurchaser.setEnabled(true);
				else
					cmbPurchaser.setEnabled(false);
			}
		});
		
		// when rdbtAllPlayer radio button is selected/deselected
		rdbtAllPlayer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (!rdbtnPlayersWith.isSelected())
					btnDeletePlayer.setEnabled(false);
			}
		});
		
		//Fill in the information of the selected Character
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String selectedChar = (String) comboBox.getSelectedItem();
				if(!(selectedChar.equals("(No character selected)"))) {
					sSelectedChar = selectedChar;
					try {
						ResultSet rs = stmt.executeQuery("Select * from CharacterOwned CO where CharName='"+ selectedChar + "'");
						while(rs.next()) {
							lblNameFill.setText(selectedChar);
							lblClassFill.setText(rs.getString("Class"));
							lblLevelFill.setText(rs.getString("Levels"));
							lblRaceFill.setText(rs.getString("Race"));
//							lblGoldFill.setText(rs.getString("Gold"));
							//lblGoldMarket.setText(rs.getString("Gold"));
							lblCharMarket.setText(selectedChar);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else {
					sSelectedChar = "";
					lblNameFill.setText("");
					lblClassFill.setText("");
					lblLevelFill.setText("");
					lblRaceFill.setText("");
					lblGoldFill.setText("");
					lblGoldMarket.setText("");
					lblCharMarket.setText("");
				}
			}
		});
		
		// show players button event handler
		btnShowPlayers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sName = getSelectedButtonLabel(bg);
				ResultSet rs = null;
				
				if (sName == "CH")
				{
					try {
					rs = exQuery("select AccountID, COUNT(*) as \"Number of Characters\" from CharacterOwned Group By AccountID");
						tblAdmin.setModel(buildTableModel(rs));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					if (sName == "SV")
					{
						try {
						rs = exQuery("select P1.AccountName as \"Player Name\", P2.Server from Player1 P1, Player2 P2 where P1.AccountID=P2.AccountID and P2.server='" + cmbServer.getSelectedItem().toString() + "'");

							tblAdmin.setModel(buildTableModel(rs));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else
					{
						if (sName == "PW")
						{
							try {
							rs = exQuery("select c.AccountID from CharacterOwned c where c.Levels>" +  Integer.parseInt(cmbLevel.getSelectedItem().toString()) + "group by c.AccountID having COUNT(*)>1");
								tblAdmin.setModel(buildTableModel(rs));
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else
						{
							if (sName == "SP")
							{
								try {
									String sAggregation = "";
									
									if (cmbPurchaser.getSelectedIndex() == 0)
										sAggregation = "MAX";
									else
										sAggregation = "MIN";
									
									//executeQuery("drop view Servicemoney");
									exQuery("create view Servicemoney(AccountID, SumPrice) as select AccountID, sum(s.Price) from Purchases p, Service s where p.ServiceID=s.ServiceID group by p.AccountID");
									rs = exQuery("select p.AccountName as \"Player Name\" from Player1 p, Servicemoney s where p.AccountID = S. AccountID and s.SumPrice in (select " + sAggregation + "(SumPrice) from Servicemoney)");
									tblAdmin.setModel(buildTableModel(rs));
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							else
							{
		
								if (sName == "PM")
								{
									try {
										//executeQuery("drop view SumCharMoney");
										exQuery("create view SumCharMoney(AccountID, SumMoney) as select c.AccountID,sum(i.Gold) from  CharacterOwned c, InventoryHad i where c.CharName=i.CharName group by c.AccountID");						
										rs = exQuery("select p.AccountName as \"Player Name\" from Player1 p, SumCharMoney s where p.AccountID = s.AccountID and s.SumMoney in (select MAX(SumMoney) from SumCharMoney)");

										tblAdmin.setModel(buildTableModel(rs));
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								else
								{
									if (sName == "AP")
									{
										try {
											rs = exQuery("select P1.AccountID, p1.AccountName, p2.server from Player1 P1, Player2 P2 where P1.AccountID=P2.AccountID");
											tblAdmin.setModel(buildTableModel(rs));
											btnDeletePlayer.setEnabled(true);
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
				/*switch(getSelectedButtonLabel(bg)) {
				case "qw" : 
					break;
				}*/

			}
		});
		
	    //Display Player Info
		try {
			ResultSet rs = stmt.executeQuery("select * from Player1 p1, Player2 p2 "
										+ "   where p1.AccountID = p2.AccountID and p1.AccountID = " +accountID);
			while(rs.next()){
				lblAccountId.setText(lblAccountId.getText() + rs.getString("AccountID"));
				lblAccountName.setText(lblAccountName.getText() + rs.getString("AccountName"));
				lblServer.setText(lblServer.getText() + rs.getString("Server"));
			}
			rs = stmt.executeQuery("select distinct * from BelongsTo where AccountID=" +accountID);
			while(rs.next()) {
				guildBox.addItem(rs.getString("GuildName"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	// exQueryM
	public ResultSet exQuery(String sQuery){
		ResultSet rs = null;
		try {			
			rs = stmt.executeQuery(sQuery);	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return rs;
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
	
	// retrieve label of selected radio button
	public String getSelectedButtonLabel(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getName();
                
            }
        }

        return null;
    }
	
	public void RefreshMarket(JTable jSell, JTable jBuy, JLabel lblGold, JTable jTrans )
	{
		ResultSet rs = null;
		try {			
			rs = stmt.executeQuery("select orderid from placebuy where charname = '" + sSelectedChar + "'");
			jBuy.setModel(buildTableModel(rs));
			rs = stmt.executeQuery("select orderid from placesell where charname = '" + sSelectedChar + "'");
			jSell.setModel(buildTableModel(rs));
			rs = stmt.executeQuery("select borderid as \"Buy ID\",sorderid as \"Sell ID\" from fulfills");
			jTrans.setModel(buildTableModel(rs));
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
