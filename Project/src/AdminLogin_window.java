package src;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;



public class AdminLogin_window extends JFrame {
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
					AdminLogin_window frame = new AdminLogin_window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// connection to ORACLE
				try {
					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
																												//ora_i6k8    a21014121
					//connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_h3w8", "a56415136");
					connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_h3w8", "a56415136");
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
	public AdminLogin_window() {
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
		setBounds(425, 200, 500, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setDocument(new JTextFieldLimit(20));
		txtName.setBounds(144, 65, 182, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);
		txtName.requestFocus();
		
		final JButton btnLoging = new JButton("Log In");
		btnLoging.setFocusable(false);
		contentPane.getRootPane().setDefaultButton(btnLoging);
		btnLoging.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String username = txtName.getText();
					char[] charPassword = psfID.getPassword();
					String password = String.valueOf(charPassword);
					String sql = "SELECT Name,EmployeeID FROM admins WHERE Name='" + username + "' AND EmployeeID=" + password;                    
					ResultSet rs = stmt.executeQuery(sql);
					
					int count = 0;
					while(rs.next()) {
						count = count + 1;
					}
					if (count == 1 ) {
						JOptionPane.showMessageDialog(null, "Access Granted!");
						dispose();
						rs.close();
						stmt.close();
						connection.close();
						new Main_Window(password, "Admin").setVisible(true);
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
		
		JLabel lblAdminName = new JLabel("Admin Name :");
		lblAdminName.setBounds(52, 71, 94, 16);
		contentPane.add(lblAdminName);
		
		JLabel lblAdminID = new JLabel("Employee ID :");
		lblAdminID.setBounds(52, 111, 94, 16);
		contentPane.add(lblAdminID);
		
		psfID = new JPasswordField();
		psfID.setDocument(new JTextFieldLimit(20));
		psfID.setColumns(10);
		psfID.setBounds(144, 105, 182, 28);
		contentPane.add(psfID);
		
		JTextArea txtPlayerNameMax = new JTextArea();
		txtPlayerNameMax.setFocusable(false);
		txtPlayerNameMax.setEditable(false);
		txtPlayerNameMax.setFont(new Font("Monospaced", Font.PLAIN, 10));
		txtPlayerNameMax.setBackground(UIManager.getColor("Button.disabledForeground"));
		txtPlayerNameMax.setText("(20 words max)");
		txtPlayerNameMax.setBounds(336, 71, 88, 20);
		contentPane.add(txtPlayerNameMax);
		
		JTextArea textPlayerIDMax = new JTextArea();
		textPlayerIDMax.setFocusable(false);
		textPlayerIDMax.setEditable(false);
		textPlayerIDMax.setText("(20 numbers max)");
		textPlayerIDMax.setFont(new Font("Monospaced", Font.PLAIN, 10));
		textPlayerIDMax.setBackground(UIManager.getColor("Button.disabledForeground"));
		textPlayerIDMax.setBounds(336, 111, 100, 20);
		contentPane.add(textPlayerIDMax);
		
	}
	public ResultSet exQuery(String sQuery){
		ResultSet rs = null;
		try {			
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			//ora_i6k8    a21014121
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_h3w8", "a56415136");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_h3w8", "a56415136");
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);

			rs = stmt.executeQuery(sQuery);	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return rs;
	}
	
	public class JTextFieldLimit extends PlainDocument {
		  private int limit;
		  JTextFieldLimit(int limit) {
		    super();
		    this.limit = limit;
		  }

		  JTextFieldLimit(int limit, boolean upper) {
		    super();
		    this.limit = limit;
		  }

		  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if (str == null)
		      return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
	}
}
