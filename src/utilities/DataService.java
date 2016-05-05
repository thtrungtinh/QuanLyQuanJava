package utilities;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.hibernate.annotations.Entity;

public class DataService {
	private static String userID;
	private static String userName;
	
	public static String GetUserID()
	{
		return userID;
	}
	
	public static void SetUserID(String user)
	{
		userID = user;
	}
	
	public static String GetUserName()
	{
		return userName;
	}
	
	public static void SetUserName(String name)
	{
		userName = name;
	}
	
	public static void SetColumnTableToCheckBox(JTable table, int index)
	{
		TableColumn tc = table.getColumnModel().getColumn(index);
        tc.setCellRenderer( table.getDefaultRenderer( Boolean.class ) );
        tc.setCellEditor( table.getDefaultEditor( Boolean.class ) );
	}
	
	public static void SetWidhtColumnTable(JTable table, int index, int width)
	{
		table.getColumnModel().getColumn(index).setPreferredWidth(width);
	}
	

}
