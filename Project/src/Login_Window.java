package src;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JList;
import java.awt.Choice;



public class Login_Window extends JFrame {
	private boolean bVisible = false;
	private JPanel contentPane;
	private JTextField txtName;
	private static Statement stmt;
	static Connection connection;
	private JPasswordField psfID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Window frame = new Login_Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// connection to ORACLE
				try {
					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
					connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_i6k8", "a21014121");
					stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					//Hello There;
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login_Window() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(144, 65, 182, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);
		txtName.requestFocus();
		
		final JLabel lblError = new JLabel("");
		final JButton btnLoging = new JButton("Log In");
		
		// Drop down menu
		final Choice ddmServer = new Choice();
		ddmServer.setVisible(false);
		ddmServer.setEnabled(false);
		ddmServer.setBounds(144, 217, 182, 26);
		ddmServer.add("North America 1");
		ddmServer.add("North America 2");
		ddmServer.add("Asia");
		ddmServer.add("Europe");
		
		lblError.setBounds(20, 37, 404, 16);
		contentPane.add(lblError);
		
		final JButton btnNewPlayer = new JButton("Create an account");
		btnNewPlayer.addMouseListener(new MouseAdapter() {
			@Override
			// when click on create new player button (CreateNewPlayer)
			public void mouseClicked(MouseEvent e) {
				// check
				if (bVisible)
				{
					//check if name and playerID fields are empty
					if ((!txtName.getText().trim().isEmpty()) && (!(psfID.getPassword().length==0))){
						try {
							// check if playerID is integer
							int iID = 0;
							try {
								int tempPass = Integer.parseInt(String.valueOf(psfID.getPassword()));	
								stmt.executeQuery("INSERT INTO Player1 VALUES ('" + txtName.getText() + "','" +tempPass + "')");
								stmt.executeQuery("INSERT INTO Player2 VALUES ('" +psfID.getText() + "','" +ddmServer.getSelectedItem() + "')");
								lblError.setText("Success");
								}
							catch (NumberFormatException numError)
							{
								lblError.setText("Please Enter an integer value as PlayerID");
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							if (e1.getErrorCode() == 1)
								lblError.setText("The PlayerID is already taken please choose another.");
							else
								e1.printStackTrace();
						}
					}
					else
					{
						lblError.setText("Enter Something.");
					}
				}
				else
				{
					ddmServer.setEnabled(true);
					ddmServer.setVisible(true);
					ddmServer.setBounds(btnLoging.getBounds().x, btnLoging.getBounds().y, btnLoging.getBounds().width, btnLoging.getBounds().height);
					btnNewPlayer.setBounds(btnNewPlayer.getBounds().x, btnNewPlayer.getBounds().y+50, btnNewPlayer.getBounds().width, btnNewPlayer.getBounds().height);
					btnLoging.setBounds(btnLoging.getBounds().x, btnLoging.getBounds().y+50, btnLoging.getBounds().width, btnLoging.getBounds().height);
					bVisible = true;
				}
			}
		});
		btnNewPlayer.setBounds(144, 180, 182, 29);
		contentPane.add(btnNewPlayer);
		
		btnLoging.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String username = txtName.getText();
					char[] charPassword = psfID.getPassword();
					String password = String.valueOf(charPassword);
					String sql = "SELECT AccountName,AccountID FROM Player1 WHERE AccountName='" + username + "' AND AccountID=" + password;                    
					ResultSet rs = stmt.executeQuery(sql);
					
					int count = 0;
					while(rs.next()) {
						count = count + 1;
					}
					if (count == 1 ) {
						JOptionPane.showMessageDialog(null, "Access Granted!");
						dispose();
						stmt.close();
						rs.close();
						new Main_Window(connection).setVisible(true);
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Access Denied.");
						txtName.setText("");
						psfID.setText("");
						txtName.requestFocus();
					}
				}
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Access Denied.");
					txtName.setText("");
					psfID.setText("");
					txtName.requestFocus();
				}
			}
		});
		btnLoging.setBounds(144, 145, 182, 29);
		contentPane.add(btnLoging);
		
		JLabel lblPlayerName = new JLabel("Player Name :");
		lblPlayerName.setBounds(52, 71, 94, 16);
		contentPane.add(lblPlayerName);
		
		JLabel lblPlayerId = new JLabel("Player ID :");
		lblPlayerId.setBounds(52, 111, 94, 16);
		contentPane.add(lblPlayerId);
		
		psfID = new JPasswordField(20);
		psfID.setColumns(10);
		psfID.setBounds(144, 105, 182, 28);
		contentPane.add(psfID);
		contentPane.add(ddmServer);
		

	}
}
