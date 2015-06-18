package src;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Color;

public class Services_Window extends JFrame {

	Connection connection;
	Statement stmt;
	ResultSet rs;
	final JTable tblServices = new JTable();
	String sQuery;
	JLabel lblError = new JLabel("");
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public Services_Window() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					stmt.close();
					connection.close();
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 479, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(tblServices);
		scrollPane.setBounds(8, 40, 465, 254);
		contentPane.add(scrollPane);	
		
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_h3w8", "a56415136");
			stmt = connection.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JLabel lblServices = new JLabel("Services:");
		lblServices.setBounds(8, 12, 61, 16);
		contentPane.add(lblServices);
		
		update();
		
		JButton btnDelete = new JButton("Delete");
		
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int iRowSelected = tblServices.getSelectedRow();
				lblError.setText("");
				sQuery = "";			
				if (iRowSelected != -1)
				{
					sQuery = "delete from service where serviceid = " + tblServices.getModel().getValueAt(iRowSelected, 0);
					executeQuery(sQuery);
					update();
				}
			}
		});
		btnDelete.setBounds(8, 295, 117, 29);
		contentPane.add(btnDelete);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		lblError.setBounds(133, 300, 340, 16);
		contentPane.add(lblError);
	}
	
	public void update()
	{
		ResultSet rs2 = null;
		rs2 = executeQuery("select serviceid as \"Service ID\",name,price from service");
		try {
			tblServices.setModel(Main_Window.buildTableModel(rs2));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// executes query
	public ResultSet executeQuery(String sQuery){
		ResultSet rs = null;
		try {			
			rs = stmt.executeQuery(sQuery);	
		} catch (SQLException e1) {
			if (e1.getErrorCode() == 2292)
				lblError.setText("Service is in use, please choose another.");
			else
				e1.printStackTrace();
		}
		
		return rs;
	}
}
