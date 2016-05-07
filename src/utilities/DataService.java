package utilities;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.text.NumberFormatter;


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
	
	public static void SetVisibleColumnTable(JTable table, int index) {
		table.getColumnModel().getColumn(index).setWidth(0);
		table.getColumnModel().getColumn(index).setMinWidth(0);
		table.getColumnModel().getColumn(index).setMaxWidth(0); 
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
	
	public static void LoadSpinEditTime(JSpinner spe)
	{
		SpinnerDateModel model = new SpinnerDateModel();
		model.setCalendarField(Calendar.MINUTE);
		
		spe.setModel(model);
		spe.setEditor(new JSpinner.DateEditor(spe, "hh:mm a"));
	}
	
	public static void SetSpinEditYear(JSpinner spe)
	{
		Calendar calendar = Calendar.getInstance();
		int current = calendar.get(Calendar.YEAR);
        SpinnerModel model = new SpinnerNumberModel(current, //initial value
							        		current - 100, //min
							        		current + 100, //max
							        		1);           	//step
        spe.setModel(model);        
        spe.setEditor(new JSpinner.NumberEditor(spe, "#"));
	}
	
	public static void SetSpinEditMonth(JSpinner spe)
	{
		Calendar calendar = Calendar.getInstance();
		int current = calendar.get(Calendar.MONTH);
		SpinnerModel model = new SpinnerNumberModel(current, //initial value
									                1, //min
									                12, //max
									                1); //step
        spe.setModel(model);        
        spe.setEditor(new JSpinner.NumberEditor(spe, "#"));
	}
	
	public static void SetSpinEditMonth(JSpinner spe, int min)
	{
		Calendar calendar = Calendar.getInstance();
		int current = calendar.get(Calendar.MONTH);
		SpinnerModel model = new SpinnerNumberModel(current, //initial value
									                min, //min
									                12, //max
									                1); //step
        spe.setModel(model);        
        spe.setEditor(new JSpinner.NumberEditor(spe, "#"));
	}
	
	public static void SetSpinEditDay(JSpinner spe)
	{
		Calendar calendar = Calendar.getInstance();
		int current = calendar.get(Calendar.DAY_OF_MONTH);
		SpinnerModel model = new SpinnerNumberModel(current, //initial value
									                1, //min
									                31, //max
									                1); //step
        spe.setModel(model);        
        spe.setEditor(new JSpinner.NumberEditor(spe, "#"));
	}
	
	public static NumberFormatter SetTextFieldIntegerFormat() {
		NumberFormat intFormat = NumberFormat.getIntegerInstance();

		NumberFormatter numberFormatter = new NumberFormatter(intFormat);
		numberFormatter.setValueClass(Integer.class); //optional, ensures you will always get a long value
		numberFormatter.setAllowsInvalid(false); //this is the key!!
		numberFormatter.setMinimum(0); //Optional
		
		return numberFormatter;
	}
	
	public static NumberFormatter SetTextFieldBigDecimalFormat() {
		NumberFormat intFormat = NumberFormat.getIntegerInstance();
		BigDecimal bgd = new BigDecimal("0");
		NumberFormatter numberFormatter = new NumberFormatter(intFormat);
		numberFormatter.setValueClass(BigDecimal.class); //optional, ensures you will always get a long value
		numberFormatter.setAllowsInvalid(false); //this is the key!!
		numberFormatter.setMinimum(bgd); //Optional		
		
		return numberFormatter;
	}
	
	

}
