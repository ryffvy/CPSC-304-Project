package src;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;

public class Main_Window extends JFrame{
	private static Connection connection;
	private static Statement stmt;
	public static String sSelectedChar;
	public static String sAccountID;
	public static String sUserType;
	
	final JLabel lblGoldFill = new JLabel();
	final JLabel mktGoldFill = new JLabel();
	final JLabel lblAccountId = new JLabel("Account ID: ");
	final JLabel lblName = new JLabel("Name: ");
	final JLabel lblNameFill = new JLabel();
	final JLabel lblClassFill = new JLabel();
	final JLabel lblLevelFill = new JLabel();
	final JLabel lblRaceFill = new JLabel();
	final JLabel mktCharFill = new JLabel();
	final JLabel lblGuildLeaderFill = new JLabel();
	final JLabel lblMembersFill = new JLabel();
	final JLabel lblGuildServerFill = new JLabel();
//	final JLabel lblRosterSizeFill = new JLabel();
	final JTextField lblRosterSizeFill = new JTextField();
	JLabel lblGuildMembers = new JLabel("Guild Members:");
	JLabel lblAccountName = new JLabel("Account Name: ");
	JLabel lblServer = new JLabel("Server: ");
	
	final JTable tblSellOrders = new JTable();
	final JTable tblBuyOrders = new JTable();
	final JTable tblTransaction = new JTable();
	final JTable table3 = new JTable();
	final JTable table = new JTable();
	final JTable tblAdmin = new JTable();
	
	final JComboBox comboBox = new JComboBox();
	final JComboBox guildBox = new JComboBox();
	final JComboBox listGuildBox = new JComboBox();
	final JComboBox addPlayerBox = new JComboBox();
	final JComboBox cmbServer = new JComboBox();
	final JComboBox cmbLevel = new JComboBox();
	final JComboBox cmbPurchaser = new JComboBox();
	
	final JButton btnDeletePlayer = new JButton("Delete Player");
	final ButtonGroup bg = new ButtonGroup();
	final JRadioButton rdbtnByServer = new JRadioButton("By server: ");
	final JRadioButton rdbtnPlayersWith = new JRadioButton("<html>Players with > 2 characters and Level greater than: </html>");
	final JRadioButton rdbtnServicePurchaser = new JRadioButton("Service Purchaser");
	final JRadioButton rdbtnPlayerWithMost = new JRadioButton("Player With Most Gold");
	JButton button = new JButton("View Inventory");
	JButton btnViewInventory = new JButton("View Inventory");
	JButton btnJoin = new JButton("Join Guild");
	JButton btnLeave = new JButton("Leave Guild");
	JButton btnAdd = new JButton("Add");
	JButton btnSell = new JButton("Sell Order");
	JButton btnBuy = new JButton("Buy Order");
	JButton btnUpdateMarket = new JButton("Update");
	JButton btnBuyService = new JButton("Buy Service");
	JButton btnShowPlayers = new JButton("Show Players");
	JRadioButton rdbtAllPlayer = new JRadioButton("All Players");
	JPanel panMarket = new JPanel();
	JPanel tabAdmin = new JPanel();
	JPanel panPlayerInfo = new JPanel();
	JPanel panel_8 = new JPanel();
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	JButton btnServices = new JButton("Services");
	JButton btnNewButton = new JButton("Change Size");
	
	public Main_Window(final String accountID, final String userType){
		sAccountID = accountID;
		sSelectedChar = "";
		sUserType = userType;
		
		//Connect to the database
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_h3w8", "a56415136");
			stmt = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//Create the design of the application
		createDesign();
		SetAccess();
		
		
		//Create and add the list of Characters the Player owns into the combo box
		try {
			ResultSet rs = stmt.executeQuery("Select * from CharacterOwned where AccountID = " + accountID);
			while(rs.next()){
				comboBox.addItem(rs.getString("CharName"));
			}
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		//Fill in the information of the selected Character
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String selectedChar = (String) comboBox.getSelectedItem();
				if(!(selectedChar.equals("(No character selected)"))) {
					try {
						ResultSet rs = stmt.executeQuery("Select * from CharacterOwned CO, InventoryHad IH "
								+ "						  where CO.CharName='"+ selectedChar + "' and CO.CharName=IH.CharName");
						while(rs.next()) {
							lblNameFill.setText(selectedChar);
							lblClassFill.setText(rs.getString("Class"));
							lblLevelFill.setText(rs.getString("Levels"));
							lblRaceFill.setText(rs.getString("Race"));
							lblGoldFill.setText(rs.getString("Gold"));
							sSelectedChar = selectedChar;
							mktCharFill.setText(selectedChar);
							mktGoldFill.setText(rs.getString("Gold"));
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
					mktCharFill.setText("");
					mktGoldFill.setText("");
					sSelectedChar = "";
					DefaultTableModel clearTable = new DefaultTableModel();
					tblSellOrders.setModel(clearTable);
					tblBuyOrders.setModel(clearTable);
					tblTransaction.setModel(clearTable);
				}
			}
		});
		
		
		//View a specified Character's inventory
		btnViewInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedChar = (String) comboBox.getSelectedItem();
				if (!(selectedChar.equals("(No character selected)"))) {
					try {
						ResultSet rs = stmt.executeQuery("select I.ItemName as \"Item Name\", IIN.Quantity, I.ItemID as \"Item ID\""
								+ "						  from Item I, InInventory IIN"
								+ "						  where IIN.CharName='" + selectedChar + "' and IIN.ItemID=I.ItemID"); 
						displayTable(rs, selectedChar + "'s Inventory");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "No character is selected.");
				}
			}
		});
		
		
		//Handle when 'View Inventory' is pressed
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedChar = (String) comboBox.getSelectedItem();
				if (!(selectedChar.equals("(No character selected)"))) {
					try {
						ResultSet rs = stmt.executeQuery("select I.ItemName as \"Item Name\", IIN.Quantity, I.ItemID as \"Item ID\""
								+ "						  from Item I, InInventory IIN"
								+ "						  where IIN.CharName='" + selectedChar + "' and IIN.ItemID=I.ItemID"); 
						displayTable(rs, selectedChar + "'s Inventory");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "No character is selected.");
				}
			}
		});
				
		
		//Display a fixed table of Players in all guilds
		try{ 
			ResultSet rs = stmt.executeQuery("select p.AccountName,p.AccountID from Player1 p "
					+ "where NOT EXISTS ((select g.GuildName from Guild_Owns g) "
					+ "					  minus "
					+ "					 (select b.GuildName from BelongsTo b where b.AccountID=p.AccountID))");
			table3.setModel(buildTableModel(rs));
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		//Populate the list with all the guild names in database
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
						rs = stmt.executeQuery("select P1.AccountName as \"Account Name\" from Player1 P1, BelongsTo BT where BT.GuildName='" +selectedGuild+ "' and BT.AccountID=P1.AccountID");
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
		
		
		//Handle when 'Join Guild' is pressed
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedGuild = (String) listGuildBox.getSelectedItem();
				if (!(selectedGuild.equals("(no guild selected)"))) {
					try {
						ResultSet rs = stmt.executeQuery("select AccountID as \"Account ID\" from BelongsTo where Guildname='" + selectedGuild + "' and AccountID="+accountID);
						if (rs.next()) {
							JOptionPane.showMessageDialog(null, "You are already a member of the guild: " + selectedGuild);
						}
						else {
							try {
								//Check the roster size before inserting
								if (Integer.parseInt(lblRosterSizeFill.getText()) > Integer.parseInt(lblMembersFill.getText())) {
									rs = stmt.executeQuery("insert into BelongsTo values('" +selectedGuild+ "', " +accountID+")");
									//Update the label information
									rs = stmt.executeQuery("select count(AccountID) as Members"
											+ "				from BelongsTo"
											+ "				where GuildName='" + selectedGuild + "'");
									while(rs.next()) {
										lblMembersFill.setText(rs.getString("Members"));
									}
									//Update the list all members in guild table
									rs = stmt.executeQuery("select P1.AccountName from Player1 P1, BelongsTo BT where BT.GuildName='" +selectedGuild+ "' and BT.AccountID=P1.AccountID");
									table.setModel(buildTableModel(rs));
									//Update the players in all guilds table
									rs = stmt.executeQuery("select p.AccountName,p.AccountID from Player1 p "
											+ "where NOT EXISTS ((select g.GuildName from Guild_Owns g) "
											+ "					  minus "
											+ "					 (select b.GuildName from BelongsTo b where b.AccountID=p.AccountID))");
									table3.setModel(buildTableModel(rs));
									//Update list of guilds the player is in
									guildBox.removeAllItems();
									rs = stmt.executeQuery("select distinct * from BelongsTo where AccountID=" +accountID);
									while(rs.next()) {
										guildBox.addItem(rs.getString("GuildName"));
									}
									JOptionPane.showMessageDialog(null, "Join Guild Successful!");
								}
								else JOptionPane.showMessageDialog(null, "Guild is full.");
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
		
		
		//Handle when 'Leave Guild' is pressed
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
								rs = stmt.executeQuery("select GL.AccountID from GuildLeader GL, Guild_Owns GO where GL.AccountID=GO.AccountID and GO.GuildName='" +selectedGuild+ "'");
								if(!rs.next()) lblGuildLeaderFill.setText("(no guild leader)");
								rs = stmt.executeQuery("select count(AccountID) as Members"
										+ "				from BelongsTo"
										+ "				where GuildName='" + selectedGuild + "'");
								while(rs.next()) {
									lblMembersFill.setText(rs.getString("Members"));
								}
								//Update list all players in a guild table
								rs = stmt.executeQuery("select P1.AccountName from Player1 P1, BelongsTo BT where BT.GuildName='" +selectedGuild+ "' and BT.AccountID=P1.AccountID");
								table.setModel(buildTableModel(rs));
								//Update the players in all guilds table
								rs = stmt.executeQuery("select p.AccountName,p.AccountID from Player1 p "
										+ "where NOT EXISTS ((select g.GuildName from Guild_Owns g) "
										+ "					  minus "
										+ "					 (select b.GuildName from BelongsTo b where b.AccountID=p.AccountID))");
								table3.setModel(buildTableModel(rs));
								//Update list of guilds the player is in
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
		
		
		//Populate the list with all the players in database
		try {
			ResultSet rs = stmt.executeQuery("select AccountName from Player1");
			while (rs.next()) {
				addPlayerBox.addItem(rs.getString("AccountName"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		//Handle when 'Add' button is pressed
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedPlayer = (String) addPlayerBox.getSelectedItem();
				String selectedGuild = (String) listGuildBox.getSelectedItem();
				if (selectedPlayer.equals("(no player selected)")) JOptionPane.showMessageDialog(null, "No player is selected.");
				if (selectedGuild.equals("(no guild selected)")) JOptionPane.showMessageDialog(null, "No guild is selected.");
				int selectedPlayerID = 0;
				try {
					//Check if already in the guild
					ResultSet rs = stmt.executeQuery("select P1.AccountID from BelongsTo BT, Player1 P1 "
							+ "where Guildname='" + selectedGuild + "' and P1.AccountName='" +selectedPlayer+ "' and P1.AccountID=BT.AccountID");
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "You are already a member of the guild: " + selectedGuild);
					}
					else {
						//Check the roster size before inserting
						try {							
						if (Integer.parseInt(lblRosterSizeFill.getText()) > Integer.parseInt(lblMembersFill.getText())) {
							rs = stmt.executeQuery("select * from Player1 where AccountName='" +selectedPlayer+ "'");
							if(rs.next()) selectedPlayerID = rs.getInt("AccountID");
							rs = stmt.executeQuery("insert into BelongsTo values ('" +selectedGuild+ "',"+selectedPlayerID+ ")");
							//Update the label information
							rs = stmt.executeQuery("select count(AccountID) as Members"
									+ "				from BelongsTo"
									+ "				where GuildName='" + selectedGuild + "'");
							while(rs.next()) {
								lblMembersFill.setText(rs.getString("Members"));
							}
							//Update the list all members in guild table
							rs = stmt.executeQuery("select P1.AccountName from Player1 P1, BelongsTo BT where BT.GuildName='" +selectedGuild+ "' and BT.AccountID=P1.AccountID");
							table.setModel(buildTableModel(rs));
							//Update the players in all guilds table
							rs = stmt.executeQuery("select p.AccountName,p.AccountID from Player1 p "
									+ "where NOT EXISTS ((select g.GuildName from Guild_Owns g) "
									+ "					  minus "
									+ "					 (select b.GuildName from BelongsTo b where b.AccountID=p.AccountID))");
							table3.setModel(buildTableModel(rs));
							//Update list of guilds the player is in
							guildBox.removeAllItems();
							rs = stmt.executeQuery("select distinct * from BelongsTo BT, Player1 P1 "
									+ " where P1.AccountName='" +selectedPlayer+ "' and P1.AccountID=BT.AccountID");
							while(rs.next()) {
								guildBox.addItem(rs.getString("GuildName"));
							}
							JOptionPane.showMessageDialog(null, "Successfully added!");
						}
						else JOptionPane.showMessageDialog(null, "Guild is full.");
						} catch (NumberFormatException e4)
						{
							
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
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
						e1.printStackTrace();
					}
				}
			}
		});
		
		
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
					RefreshMarket(tblSellOrders, tblBuyOrders, tblTransaction);
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
							e1.printStackTrace();
						}
					}
					else
					{
						if (sName == "PW")
						{
							try {
							rs = exQuery("select c.AccountID as \"Account ID\" from CharacterOwned c where c.Levels>" +  Integer.parseInt(cmbLevel.getSelectedItem().toString()) + "group by c.AccountID having COUNT(*)>1");
								tblAdmin.setModel(buildTableModel(rs));
							} catch (SQLException e1) {
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
									
									exQuery("drop view Servicemoney");
									exQuery("create view Servicemoney(AccountID, SumPrice) as select AccountID, sum(s.Price) from Purchases p, Service s where p.ServiceID=s.ServiceID group by p.AccountID");
									rs = exQuery("select p.AccountName as \"Player Name\" from Player1 p, Servicemoney s where p.AccountID = S. AccountID and s.SumPrice in (select " + sAggregation + "(SumPrice) from Servicemoney)");
									tblAdmin.setModel(buildTableModel(rs));
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
							else
							{
		
								if (sName == "PM")
								{
									try {
										exQuery("drop view SumCharMoney");
										exQuery("create view SumCharMoney(AccountID, SumMoney) as select c.AccountID,sum(i.Gold) from  CharacterOwned c, InventoryHad i where c.CharName=i.CharName group by c.AccountID");						
										rs = exQuery("select p.AccountName as \"Player Name\" from Player1 p, SumCharMoney s where p.AccountID = s.AccountID and s.SumMoney in (select MAX(SumMoney) from SumCharMoney)");

										tblAdmin.setModel(buildTableModel(rs));
									} catch (SQLException e1) {
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
											e1.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			}
		});
		
		
		
		//Change roster size
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (((String)listGuildBox.getSelectedItem()).equals("(no guild selected)")) 
					JOptionPane.showMessageDialog(null, "No guild is selected.");
				else {
					try {
						int newRosterSize = Integer.parseInt(lblRosterSizeFill.getText());
						if(newRosterSize > 400) JOptionPane.showMessageDialog(null, "Roster size constraint violated.");
						else if (newRosterSize >= Integer.parseInt(lblMembersFill.getText())) {
							ResultSet rs = exQuery("Update Guild_Owns set RosterSize=" +newRosterSize+ " "
									+ "				where GuildName='" +(String)listGuildBox.getSelectedItem()+ "'");
							JOptionPane.showMessageDialog(null, "Roster size updated.");
						}
						else JOptionPane.showMessageDialog(null, "Number less than existing amount of members.");
					} catch (NumberFormatException exc) { 
						JOptionPane.showMessageDialog(null, "Size must be a number.");
					}
				}
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
			//List of guilds the player is in
			rs = stmt.executeQuery("select distinct * from BelongsTo where AccountID=" +accountID);
			while(rs.next()) {
				guildBox.addItem(rs.getString("GuildName"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	// exQuery (Replication of executeQuery())
	public ResultSet exQuery(String sQuery){
		ResultSet rs = null;
		try {			
			rs = stmt.executeQuery(sQuery);	
		} catch (Exception exc) {
			if (exc instanceof SQLException) 
				exc.printStackTrace();
			if (exc instanceof SQLIntegrityConstraintViolationException)
				JOptionPane.showMessageDialog(null, "Roster size constraint violated.");
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
	
	public void SetAccess(){
		if (sUserType == "Admin")
		{
			btnJoin.setEnabled(false);
			btnLeave.setEnabled(false);
			
			tabbedPane.remove(0);
			tabbedPane.remove(0);
		}
		else
		{
			tabbedPane.remove(3);
			panel_8.setVisible(false);
			btnNewButton.setEnabled(false);
		}
	}
	
	
	//Update all objects' displaying information
	public void RefreshMarket(JTable jSell, JTable jBuy, JTable jTrans )
	{
		ResultSet rs = null;
		try {			
			rs = stmt.executeQuery("select orderid as \"Order ID\" from placebuy where charname = '" + sSelectedChar + "'");
			jBuy.setModel(buildTableModel(rs));
			rs = stmt.executeQuery("select orderid  as \"Order ID\" from placesell where charname = '" + sSelectedChar + "'");
			jSell.setModel(buildTableModel(rs));
			rs = stmt.executeQuery("select sorderid as \"Sell ID\",borderid as \"Buy ID\" from fulfills");
			jTrans.setModel(buildTableModel(rs));
			rs = stmt.executeQuery("select * from InventoryHad where CharName='" +sSelectedChar+ "'");
			if (!rs.next()) {
				lblGoldFill.setText(rs.getString("Gold"));
				mktGoldFill.setText(rs.getString("Gold"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void createDesign() {
		setBounds(325,150,707,364);
		getContentPane().setLayout(null);
		
		tabbedPane.setBounds(6, 0, 697, 342);
		getContentPane().add(tabbedPane);
		
		tabbedPane.addTab("Player Info", null, panPlayerInfo, null);
		panPlayerInfo.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 250, 161);
		panPlayerInfo.add(panel);
		panel.setLayout(null);
		
		JLabel lblInfo = new JLabel("Player Info: ");
		lblInfo.setBounds(6, 6, 238, 16);
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
		
		lblAccountId.setBounds(6, 0, 256, 20);
		panel_1.add(lblAccountId);
		
		lblAccountName.setBounds(6, 60, 256, 20);
		panel_1.add(lblAccountName);
		
		lblServer.setBounds(6, 90, 256, 20);
		panel_1.add(lblServer);
		
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
		
		JLabel lblGuild = new JLabel("Guild: ");
		lblGuild.setBounds(6, 30, 84, 20);
		panel_1.add(lblGuild);
		
		JLabel lblClass = new JLabel("Class: ");
		JLabel lblCharacterInfo = new JLabel("Character Info: ");
		lblCharacterInfo.setBounds(0, 6, 210, 16);
		panel_2.add(lblCharacterInfo);
		
		JLabel lblLevel = new JLabel("Level: ");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 32, 198, 241);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		lblName.setBounds(6, 0, 44, 16);
		panel_3.add(lblName);
		lblNameFill.setBounds(48, 1, 150, 16);
		panel_3.add(lblNameFill);
		
		lblClass.setBounds(6, 30, 44, 16);
		panel_3.add(lblClass);
		lblClassFill.setBounds(48, 30, 150, 16);
		panel_3.add(lblClassFill);
		
		lblLevel.setBounds(6, 60, 44, 16);
		panel_3.add(lblLevel);
		lblLevelFill.setBounds(48, 60, 150, 16);
		panel_3.add(lblLevelFill);
		
		JLabel lblRace = new JLabel("Race: ");
		lblRace.setBounds(6, 90, 44, 16);
		panel_3.add(lblRace);
		lblRaceFill.setBounds(48, 90, 150, 16);
		panel_3.add(lblRaceFill);
		
		JLabel lblGold = new JLabel("Gold: ");
		lblGold.setBounds(6, 120, 44, 16);
		panel_3.add(lblGold);
		 
		lblGoldFill.setBounds(48, 120, 150, 16);
		panel_3.add(lblGoldFill);
		
		tabbedPane.addTab("Market", null, panMarket, null);
		panMarket.setLayout(null);
		
		mktCharFill.setBounds(534, 32, 105, 16);
		panMarket.add(mktCharFill);
		
		mktGoldFill.setBounds(522, 59, 117, 16);
		panMarket.add(mktGoldFill);
		
		comboBox.setBounds(22, 34, 200, 27);
		panel_5.add(comboBox);
		comboBox.insertItemAt("(No character selected)", 0);
		comboBox.setSelectedIndex(0);
		comboBox.setMaximumRowCount(3);
		JLabel lblCharacter = new JLabel("Characters: ");
		lblCharacter.setBounds(6, 6, 128, 16);
		panel_5.add(lblCharacter);
		
		btnViewInventory.setBounds(534, 105, 136, 50);
		panPlayerInfo.add(btnViewInventory);
		
		JScrollPane srpSellOrders = new JScrollPane(tblSellOrders);
		srpSellOrders.setBounds(23, 32, 214, 111);
		panMarket.add(srpSellOrders);
		
		JLabel lblYourSellOrders = new JLabel("Your Sell Orders:");
		lblYourSellOrders.setBounds(23, 10, 105, 16);
		panMarket.add(lblYourSellOrders);
		
		JScrollPane srpBuyOrders = new JScrollPane(tblBuyOrders);
		srpBuyOrders.setBounds(249, 32, 203, 111);
		panMarket.add(srpBuyOrders);
		
		JLabel lblBuyOrders = new JLabel("Your Buy Orders:");
		lblBuyOrders.setBounds(249, 10, 105, 16);
		panMarket.add(lblBuyOrders);
		
		button.setBounds(503, 93, 136, 50);
		panMarket.add(button);
		
		JPanel panBuySell = new JPanel();
		panBuySell.setBounds(23, 155, 136, 119);
		panMarket.add(panBuySell);
		panBuySell.setLayout(null);
		
		btnSell.setBounds(0, 52, 132, 29);
		panBuySell.add(btnSell);
		
		JLabel lblPlaceOrder = new JLabel("Place Order:");
		lblPlaceOrder.setBounds(0, 0, 127, 16);
		panBuySell.add(lblPlaceOrder);
		
		btnBuy.setBounds(0, 22, 132, 29);
		panBuySell.add(btnBuy);
		btnBuyService.setBounds(0, 82, 132, 29);
		panBuySell.add(btnBuyService);
		
		final JLabel lblCharMarket = new JLabel("Character: ");
		lblCharMarket.setBounds(464, 32, 68, 16);
		panMarket.add(lblCharMarket);
		
		final JLabel lblGoldMarket = new JLabel("Gold:");
		lblGoldMarket.setBounds(464, 60, 38, 16);
		panMarket.add(lblGoldMarket);
		
		JScrollPane srpTransaction = new JScrollPane(tblTransaction);
		srpTransaction.setBounds(171, 182, 292, 92);
		panMarket.add(srpTransaction);
		
		JLabel lblTransactions = new JLabel("Transactions: ");
		lblTransactions.setBounds(171, 165, 214, 16);
		panMarket.add(lblTransactions);
		
		btnUpdateMarket.setBounds(511, 245, 117, 29);
		panMarket.add(btnUpdateMarket);
		
		//Create a Guild tab
		JPanel tabGuild1 = new JPanel();
		tabbedPane.addTab("Guild", null, tabGuild1, null);
		tabGuild1.setLayout(null);
		
		JLabel lblGuildLeader = new JLabel("GuildLeader:");
		lblGuildLeader.setBounds(17, 40, 90, 22);
		tabGuild1.add(lblGuildLeader);
		lblGuildLeaderFill.setBounds(105, 40, 162, 22);
		tabGuild1.add(lblGuildLeaderFill);
		
		JLabel lblMembers = new JLabel("# of Members:");
		lblMembers.setBounds(17, 70, 90, 22);
		tabGuild1.add(lblMembers);
		lblMembersFill.setBounds(105, 70, 162, 22);
		tabGuild1.add(lblMembersFill);
		
		JLabel lblGuildServer = new JLabel("Server:");
		lblGuildServer.setBounds(17, 100, 90, 22);
		tabGuild1.add(lblGuildServer);
		lblGuildServerFill.setBounds(105, 100, 162, 22);
		tabGuild1.add(lblGuildServerFill);
		
		JLabel lblRosterSize = new JLabel("Roster Size:");
		lblRosterSize.setBounds(17, 130, 90, 22);
		tabGuild1.add(lblRosterSize);
		lblRosterSizeFill.setBounds(105, 130, 41, 22);
		tabGuild1.add(lblRosterSizeFill);
		
		JLabel lblGuild_1 = new JLabel("Guild: ");
		lblGuild_1.setBounds(17, 6, 41, 23);
		tabGuild1.add(lblGuild_1);
		
		panel_8.setBounds(6, 196, 664, 104);
		tabGuild1.add(panel_8);
		panel_8.setLayout(null);
		
		JLabel lblP = new JLabel("Players in All Guilds:");
		lblP.setBounds(260, 6, 200, 16);
		panel_8.add(lblP);
		
		JScrollPane scrollPane_3 = new JScrollPane(table3);
		scrollPane_3.setBounds(260, 33, 393, 65);
		panel_8.add(scrollPane_3);
		
		lblGuildMembers.setBounds(277, 6, 200, 23);
		tabGuild1.add(lblGuildMembers);
		
		JScrollPane scrollPane_2 = new JScrollPane(table);
		scrollPane_2.setBounds(277, 41, 393, 148);
		tabGuild1.add(scrollPane_2);
		
		listGuildBox.insertItemAt("(no guild selected)", 0);
		listGuildBox.setSelectedIndex(0);
		listGuildBox.setMaximumRowCount(5);
		
		listGuildBox.setBounds(57, 5, 200, 25);
		tabGuild1.add(listGuildBox);
		
		btnJoin.setBounds(17, 164, 115, 26);
		tabGuild1.add(btnJoin);
		
		btnLeave.setBounds(140, 164, 115, 26);
		tabGuild1.add(btnLeave);
		
		JLabel label_2 = new JLabel("Add Player:");
		label_2.setBounds(6, 41, 77, 16);
		panel_8.add(label_2);
		
		addPlayerBox.insertItemAt("(no player selected)", 0);
		addPlayerBox.setSelectedIndex(0);
		addPlayerBox.setMaximumRowCount(10);
		addPlayerBox.setBounds(83, 38, 165, 25);
		panel_8.add(addPlayerBox);
		
		btnAdd.setBounds(83, 75, 165, 23);
		panel_8.add(btnAdd);
		
		btnNewButton.setBounds(150, 131, 117, 22);
		tabGuild1.add(btnNewButton);
		
		//Create an Admin tab
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
		
		JRadioButton rdbtnCharacters = new JRadioButton("# of Characters");
		rdbtnCharacters.setName("CH");
		rdbtnCharacters.setSelected(true);
		rdbtnCharacters.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnCharacters.setBounds(0, 0, 150, 30);
		panel_6.add(rdbtnCharacters);
		bg.add(rdbtnCharacters);
		
		rdbtnByServer.setName("SV");
		rdbtnByServer.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnByServer.setBounds(0, 22, 150, 30);
		panel_6.add(rdbtnByServer);
		bg.add(rdbtnByServer);
		
		cmbServer.setEnabled(false);
		cmbServer.setBounds(29, 52, 140, 22);
		panel_6.add(cmbServer);
		
		rdbtnPlayersWith.setName("PW");
		rdbtnPlayersWith.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnPlayersWith.setBounds(0, 78, 175, 37);
		panel_6.add(rdbtnPlayersWith);
		bg.add(rdbtnPlayersWith);
		
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
		
		rdbtnServicePurchaser.setName("SP");
		rdbtnServicePurchaser.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnServicePurchaser.setBounds(0, 146, 127, 30);
		panel_6.add(rdbtnServicePurchaser);
		bg.add(rdbtnServicePurchaser);
		
		rdbtnPlayerWithMost.setName("PM");
		rdbtnPlayerWithMost.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtnPlayerWithMost.setBounds(0, 174, 150, 30);
		panel_6.add(rdbtnPlayerWithMost);
		bg.add(rdbtnPlayerWithMost);

		JScrollPane srpAdmin = new JScrollPane(tblAdmin);
		srpAdmin.setBounds(226, 6, 434, 250);
		tabAdmin.add(srpAdmin);
		
		btnShowPlayers.setBounds(6, 262, 200, 28);
		tabAdmin.add(btnShowPlayers);
		
		btnDeletePlayer.setEnabled(false);
		
		btnDeletePlayer.setBounds(506, 262, 154, 28);
		tabAdmin.add(btnDeletePlayer);
		
		rdbtAllPlayer.setName("AP");
		rdbtAllPlayer.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		rdbtAllPlayer.setBounds(0, 204, 150, 30);
		bg.add(rdbtAllPlayer);
		panel_6.add(rdbtAllPlayer);
		
		cmbPurchaser.setEnabled(false);
		cmbPurchaser.setBounds(133, 153, 69, 22);
		cmbPurchaser.addItem("Best");
		cmbPurchaser.addItem("Worst");
		panel_6.add(cmbPurchaser);
		
		btnServices.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Services_Window frame = new Services_Window();
				frame.setVisible(true);
			}
		});
		btnServices.setBounds(218, 262, 117, 29);
		tabAdmin.add(btnServices);
	}
}
