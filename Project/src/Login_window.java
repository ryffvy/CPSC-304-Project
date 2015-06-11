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
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;


public class Login_window extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JPasswordField psfID;

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
					Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_i6k8", "a21014121");
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT table_name FROM user_tables");
					String sResult = "";
					while(rs.next())
					{
						sResult += "<br>" + rs.getString("table_name");
					}
					connection.close();
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
		
		psfID = new JPasswordField();
		psfID.setBounds(144, 105, 182, 28);
		contentPane.add(psfID);
		
		JButton btnNewPlayer = new JButton("Create an account");
		btnNewPlayer.setBounds(144, 180, 182, 29);
		contentPane.add(btnNewPlayer);
		
		JButton btnLoging = new JButton("Log In");
		btnLoging.setBounds(144, 145, 182, 29);
		contentPane.add(btnLoging);
		
		JLabel lblPlayerName = new JLabel("Player Name :");
		lblPlayerName.setBounds(52, 71, 94, 16);
		contentPane.add(lblPlayerName);
		
		JLabel lblPlayerId = new JLabel("Player ID :");
		lblPlayerId.setBounds(52, 111, 94, 16);
		contentPane.add(lblPlayerId);
	}
}
