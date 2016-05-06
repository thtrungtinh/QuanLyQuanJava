package main;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.table.*;
import java.util.List;
import java.util.Vector;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import entities.*;
import model.*;
import utilities.DataService;
import dao.*;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class fTimeCanWork extends JFrame  {

	private JPanel contentPane;
	private JTextPane txtDienGiai;
	private JCheckBox chkStatus;	
	private DefaultTableModel model;
	private JTable tbl;
	
	private JComboBox cboCa;
	private JComboBox cboNguoiDung;	
	
	private List<Calamviec> listCa;	
	private List<Nguoidung> listNguoiDung;		
	private List<Thoigiancothelamviec> listThoiGian;	
	private List<ThoiGianLamViecModel> listThoiGianModel;	
	
	// Load combobox Ca lam viec
	private void FillcboCa()
	{		
		CaLamViecDAO entities = new CaLamViecDAO();
		listCa = entities.Load();
		for(Calamviec entity : listCa)
		{
			cboCa.addItem(entity.getTenCa());
		}		
	}
	//Load Combobox Nguoi dung
	private void FillcboNguoiDung()
	{		
		NguoiDungDAO entities = new NguoiDungDAO();
		listNguoiDung = entities.Load();
		for(Nguoidung entity : listNguoiDung)
		{
			cboNguoiDung.addItem(entity.getTenNguoiDung());
		}		
	}
	// Load DataTable
	public void ShowTableData() 
    {
    	System.out.println("--- Loading ---");
    	ThoiGianCoTheLamViecDAO dao = new ThoiGianCoTheLamViecDAO();
    	model = new DefaultTableModel(){
    		@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
    	};
    	
        //Set Column Title
    	Vector column = new Vector();
        column.add("Mã ca");
        column.add("Tên ca");
        column.add("Mã NV");
        column.add("Tên NV");        
        column.add("Diễn giải");        
        column.add("Sử dụng");
        model.setColumnIdentifiers(column);
        //listNguoiDung = dao.Load();
        listThoiGianModel = dao.GetList();
        for (int i = 0; i < listThoiGianModel.size(); i++) {
        	ThoiGianLamViecModel entity = (ThoiGianLamViecModel)listThoiGianModel.get(i);
        	Vector row = new Vector();
        	row.add(entity.getMaCa());
        	row.add(entity.getTenCa());
        	row.add(entity.getMaNguoiDung());
        	row.add(entity.getTenNguoiDung());
        	row.add(entity.getDienGiai());
        	row.add((Boolean)entity.isStatus());
        	
            
            model.addRow(row);
        }
        
        tbl.setModel(model);
        DataService.SetColumnTableToCheckBox(tbl, 5);
        
        DataService.SetWidhtColumnTable(tbl, 0, 110);
        DataService.SetWidhtColumnTable(tbl, 1, 200);
        DataService.SetWidhtColumnTable(tbl, 2, 110);
        DataService.SetWidhtColumnTable(tbl, 3, 120);
        DataService.SetWidhtColumnTable(tbl, 4, 200);
        DataService.SetWidhtColumnTable(tbl, 5, 80);               
        
    	System.out.println("--- Success ---");
	} 
	/*
	// Load DataTable
		public void ShowTableData() 
	    {
	    	System.out.println("--- Loading ---");
	    	ThoiGianCoTheLamViecDAO dao = new ThoiGianCoTheLamViecDAO();
	    	model = new DefaultTableModel(){
	    		@Override
	            public boolean isCellEditable(int row, int column) {
	               //all cells false
	               return false;
	            }
	    	};
	    	
	        //Set Column Title
	    	Vector column = new Vector();
	        column.add("Mã NV");
	        column.add("Tên NV");
	        column.add("Nam");
	        column.add("Ngày sinh");
	        column.add("CMND");
	        column.add("Điện thoại");
	        column.add("Địa chỉ");        
	        column.add("Sử dụng");
	        model.setColumnIdentifiers(column);
	        listNguoiDung = dao.Load();
	        for (int i = 0; i < listNguoiDung.size(); i++) {
	        	Nguoidung entity = (Nguoidung)listNguoiDung.get(i);
	        	Vector row = new Vector();
	        	row.add(entity.getMaNguoiDung());
	        	row.add(entity.getTenNguoiDung());
	        	row.add((Boolean)entity.isGioiTinh());
	        	row.add(entity.getNgaySinh());
	        	row.add(entity.getCmnd());
	        	row.add(entity.getDienThoai());
	        	row.add(entity.getDiaChi());                 
	            row.add((Boolean)entity.isStatus());
	            
	            model.addRow(row);
	        }
	        
	        tbl.setModel(model);
	        DataService.SetColumnTableToCheckBox(tbl, 2);
	        DataService.SetColumnTableToCheckBox(tbl, 7);
	        DataService.SetWidhtColumnTable(tbl, 0, 140);
	        DataService.SetWidhtColumnTable(tbl, 1, 200);
	        DataService.SetWidhtColumnTable(tbl, 2, 80);
	        DataService.SetWidhtColumnTable(tbl, 3, 140);
	        DataService.SetWidhtColumnTable(tbl, 4, 140);
	        DataService.SetWidhtColumnTable(tbl, 5, 140);
	        DataService.SetWidhtColumnTable(tbl, 6, 140);
	        DataService.SetWidhtColumnTable(tbl, 7, 80);
	        
	    	System.out.println("--- Success ---");
		} */
	
	private int GetIndexCa(List<Calamviec> entities, String key) {
		int index = -1;
		for(int i = 0; i< entities.size(); i++)
		{			
			if(key.equals(entities.get(i).getMaCa()))
			{				
				return i;
			}				
		}
		return index;
	}
	
	private int GetIndexNguoiDung(List<Nguoidung> entities, String key) {
		int index = -1;
		for(int i = 0; i< entities.size(); i++)
		{			
			if(key.equals(entities.get(i).getMaNguoiDung()))
			{				
				return i;
			}				
		}
		return index;
	}
	
    //Load Table click row
	private void ClickRowTable(int index) {
		ThoiGianLamViecModel entity = listThoiGianModel.get(index);		
		txtDienGiai.setText(entity.getDienGiai());
		chkStatus.setSelected(entity.isStatus());		
		
		int intdexCa = GetIndexCa(listCa, entity.getMaCa());
		int intdexNguoiDung = GetIndexNguoiDung(listNguoiDung, entity.getMaNguoiDung());
		cboCa.setSelectedIndex(intdexCa);
		cboNguoiDung.setSelectedIndex(intdexNguoiDung);		
	}
	
    private String EditData()
    {
    	String errMessage = "";
    	try {
    		ThoiGianCoTheLamViecDAO dao = new ThoiGianCoTheLamViecDAO();
    		String maCa = listCa.get(cboCa.getSelectedIndex()).getMaCa();
			String maNguoiDung = listNguoiDung.get(cboNguoiDung.getSelectedIndex()).getMaNguoiDung();		
    		ThoigiancothelamviecId key = new ThoigiancothelamviecId(maCa, maNguoiDung);
			errMessage = dao.CheckEdit(key);
			if(errMessage.length()<1)
			{				
				String dienGiai = txtDienGiai.getText();
				boolean status = chkStatus.isSelected();	
				
				dao.UpdateData(key, dienGiai, status);
				ShowTableData();
				errMessage = "Cập nhật thành công !";
			}			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e);
		}
    	return errMessage;
    }
    
    private String DeleteData()
    {
    	String errMessage = "";
    	try {
    		
			ThoiGianCoTheLamViecDAO dao = new ThoiGianCoTheLamViecDAO();
			String maCa = listCa.get(cboCa.getSelectedIndex()).getMaCa();
			String maNguoiDung = listNguoiDung.get(cboNguoiDung.getSelectedIndex()).getMaNguoiDung();
			ThoigiancothelamviecId key = new ThoigiancothelamviecId(maCa, maNguoiDung);
			errMessage = dao.CheckDelete(key);
			if(errMessage.length()<1)
			{
				dao.Delete(key);
				errMessage = "Xóa thành công !";
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
    	return errMessage;    	
    }
    
    private String InsertData()
    {   	
    	ThoiGianCoTheLamViecDAO dao = new ThoiGianCoTheLamViecDAO();
    	String maCa = listCa.get(cboCa.getSelectedIndex()).getMaCa();
		String maNguoiDung = listNguoiDung.get(cboNguoiDung.getSelectedIndex()).getMaNguoiDung();	
		ThoigiancothelamviecId key = new ThoigiancothelamviecId(maCa, maNguoiDung);
		String errMessage = dao.CheckInsert(key);
		if(errMessage.length()<1)
		{			
			Thoigiancothelamviec entity = new Thoigiancothelamviec();
			Date currentDate = new Date();
			try 
			{		
				entity.setId(key);
				entity.setDienGiai(txtDienGiai.getText());				
				entity.setStatus(chkStatus.isSelected());				
				
				entity.setUpdatedBy(DataService.GetUserID());
				entity.setCreatedBy(DataService.GetUserID());
				entity.setUpdatedDate(currentDate);
				entity.setCreatedDate(currentDate);						
				
				errMessage = dao.Insert(entity);
				ShowTableData();
			} 
			catch (Exception e) 
			{				
				e.printStackTrace();
				System.out.println("Error: " + e);				
			} 
		}
		return errMessage;
    }
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fTimeCanWork frame = new fTimeCanWork();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);		
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public fTimeCanWork() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Thoát nghiệp vụ thời gian làm việc");
			}							
			
		});
		setTitle("Nhân viên");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 766, 289);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Th\u00F4ng tin", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 271, 226);
		contentPane.add(panel);
		panel.setLayout(null);
		
		chkStatus = new JCheckBox("Sử dụng");
		chkStatus.setSelected(true);
		chkStatus.setBounds(91, 79, 79, 23);
		panel.add(chkStatus);
		
		JLabel lblDinGii = new JLabel("Diễn giải");
		lblDinGii.setBounds(18, 131, 53, 14);
		panel.add(lblDinGii);
		
		txtDienGiai = new JTextPane();
		txtDienGiai.setBounds(94, 109, 159, 68);
		panel.add(txtDienGiai);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setMargin(new Insets(2, 5, 2, 5));
		
		btnThem.setBounds(20, 193, 64, 23);
		panel.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		
		btnSua.setBounds(104, 193, 64, 23);
		panel.add(btnSua);
		
		JButton btnXoa = new JButton("Xóa");
		
		btnXoa.setBounds(183, 193, 64, 23);
		panel.add(btnXoa);
		
		JLabel lblTrnh = new JLabel("Ca");
		lblTrnh.setBounds(18, 23, 66, 14);
		panel.add(lblTrnh);
		
		cboCa = new JComboBox();
		cboCa.setBounds(94, 19, 159, 22);
		panel.add(cboCa);
		
		JLabel lblVTr_1 = new JLabel("Nhân viên");
		lblVTr_1.setBounds(18, 54, 66, 14);
		panel.add(lblVTr_1);
		
		cboNguoiDung = new JComboBox();
		cboNguoiDung.setBounds(94, 50, 159, 22);
		panel.add(cboNguoiDung);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Danh s\u00E1ch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(291, 11, 453, 226);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane(tbl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		tbl = new JTable();
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {				
					if (e.getSource() == tbl) {
			            int index = tbl.getSelectedRow();
			            if (index != -1) 
			            {			            	
			            	ClickRowTable(index);	                	                
			            }
			        }
				} catch (Exception ex) {
					System.out.println("Error: " + ex);
				}
			}
		});
		tbl.setPreferredSize(new Dimension(500, 500));
		scrollPane.setViewportView(tbl);
		
		// call function get data default
		
		ShowTableData();
		FillcboCa();
		FillcboNguoiDung();
		
		// call action
		btnThem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				String errMessage = InsertData();
				ShowTableData();
				JOptionPane.showMessageDialog(null, errMessage);
			}
		});
		btnXoa.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				int selectedOption = JOptionPane.showConfirmDialog(null, 
                        "Bạn có chắc sẽ xóa dữ liệu này", 
                        "Xóa dữ liệu", 
                        JOptionPane.YES_NO_OPTION); 
						if (selectedOption == JOptionPane.YES_OPTION) {
							String errMessage = DeleteData();
							ShowTableData();
							JOptionPane.showMessageDialog(null, errMessage);
						}
			}
		});
		btnSua.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				String errMessage = EditData();
				ShowTableData();
				JOptionPane.showMessageDialog(null, errMessage);
			}
		});	
		
	}
}
