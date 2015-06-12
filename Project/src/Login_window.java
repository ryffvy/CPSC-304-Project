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



public class Login_window extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private static Statement stmt;

	
	static Connection connection;
	private JTextField psfID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_window frame = new Login_window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// connection to ORACLE
				try {
					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
					connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_i6k8", "a21014121");
					stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT table_name FROM user_tables");
					String sResult = "";
					while(rs.next())
					{
						sResult += "<br>" + rs.getString("table_name");
					}
					//txtName = new JTextField("");
					//txtName.setText("Connected");
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
	public Login_window() {
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
		
		final JLabel lblError = new JLabel("");
		lblError.setBounds(74, 37, 285, 16);
		contentPane.add(lblError);
		
		JButton btnNewPlayer = new JButton("Create an account");
		btnNewPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((!txtName.getText().isEmpty()) || (!psfID.getText().isEmpty())){
					try {
						stmt.executeQuery("INSERT INTO Player1 VALUES ('" + txtName.getText() + "','" +psfID.getText() + "')");
						lblError.setText("Success");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					lblError.setText("Enter Somethin");
				}
			}
		});
		btnNewPlayer.setBounds(144, 180, 182, 29);
		contentPane.add(btnNewPlayer);
		
		JButton btnLoging = new JButton("Log In");
		btnLoging.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String username = txtName.getText();
					int password = Integer.parseInt(psfID.getText());
					String sql = "SELECT AccountName,AccountID FROM Player1 WHERE AcocuntName='" + username + "' AND AccountID=" + password;                    
					ResultSet rs = stmt.executeQuery(sql);
					
					int count = 0;
					while(rs.next()) {
						count += 1;
					}
					if (count == 1 ) {
						JOptionPane.showMessageDialog(null, "Access Granted!");
					}
					else {
						JOptionPane.showMessageDialog(null, "Access Denied.");
					}
				}
				catch (SQLException e1) {
					e1.printStackTrace();
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
		
		psfID = new JTextField();
		psfID.setColumns(10);
		psfID.setBounds(144, 105, 182, 28);
		contentPane.add(psfID);
		

	}
}
