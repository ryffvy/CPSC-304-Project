import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class Main_Window extends JFrame{
	private JTable table;
	
	public Main_Window(){
		setSize(500,500);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		setVisible(true);
		
		ResultSet rs = null;
		int totalCol = 0;
		int totalRow = getTotalRow(rs);
		try {
			totalCol = ((ResultSetMetaData) rs).getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object rows[][] = new Object[totalRow][totalCol];
		Object headers[] = new Object[totalCol];
		try {
			while(rs.next()) {
				for(int i=0; i<totalRow++; i++) {
					for(int j=0; j<totalCol++; j++) {
						rows[i][j] = rs.getString(j); 
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getTotalRow(ResultSet rs) {
		int row =0;
		try {
			while(!rs.isLast()) {
				rs.next();
			}
			row = rs.getRow();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}
}
