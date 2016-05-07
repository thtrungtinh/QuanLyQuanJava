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

public class fTimekeeping extends JFrame  {

	private JPanel contentPane;
	private JTextPane txtDienGiai;
	private DefaultTableModel model;
	private JTable tbl;
	
	private JComboBox cboCa, cboCaFilter;
	private JComboBox cboNguoiDung, cboNguoiDungFilter;	
	private JSpinner speNgay;
	private JSpinner speThang, speThangFilter;
	private JSpinner speNam, speNamFilter;
	
	private List<Calamviec> listCa;	
	private List<Nguoidung> listNguoiDung;		
	private List<Thoigiancothelamviec> listThoiGian;	
	private List<ChamCongModel> listChamCongModel;	
	
	private void SetSpinEdit()
	{
		DataService.SetSpinEditDay(speNgay);
		DataService.SetSpinEditMonth(speThang);
		DataService.SetSpinEditYear(speNam);
		DataService.SetSpinEditMonth(speThangFilter, 0);
		DataService.SetSpinEditYear(speNamFilter);
	}
	
	// Load combobox Ca lam viec
	private void FillcboCa()
	{		
		CaLamViecDAO entities = new CaLamViecDAO();
		listCa = entities.Load();
		for(Calamviec entity : listCa)
		{
			cboCa.addItem(entity.getTenCa());
			cboCaFilter.addItem(entity.getTenCa());
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
			cboNguoiDungFilter.addItem(entity.getTenNguoiDung());
		}		
	}
	// Load DataTable
	public void ShowTableData() 
    {
    	System.out.println("--- Loading ---");
    	ChamCongDAO dao = new ChamCongDAO();
    	model = new DefaultTableModel(){
    		@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
    	};
    	
        //Set Column Title
    	Vector column = new Vector();
    	column.add("Mã Chấm Công");
        column.add("Ca");        
        column.add("Tên NV");  
        column.add("Ngay");
        column.add("Thang");
        column.add("Nam");
        column.add("Diễn giải");        
        
        model.setColumnIdentifiers(column);
        //listNguoiDung = dao.Load();
        listChamCongModel = dao.GetList("", "", 0, 0);
        for (int i = 0; i < listChamCongModel.size(); i++) {
        	ChamCongModel entity = (ChamCongModel)listChamCongModel.get(i);
        	Vector row = new Vector();
        	row.add(entity.getMaChamCong());        	
        	row.add(entity.getTenCa());        	
        	row.add(entity.getTenNguoiDung());
        	row.add(entity.getNgay());
        	row.add(entity.getThang());
        	row.add(entity.getNam());
        	row.add(entity.getDienGiai());      	
        	
            
            model.addRow(row);
        }
        
        tbl.setModel(model);
        DataService.SetVisibleColumnTable(tbl, 0);
                
        DataService.SetWidhtColumnTable(tbl, 1, 140);
        DataService.SetWidhtColumnTable(tbl, 2, 200);
        DataService.SetWidhtColumnTable(tbl, 3, 80);
        DataService.SetWidhtColumnTable(tbl, 4, 80);
        DataService.SetWidhtColumnTable(tbl, 5, 80);  
        DataService.SetWidhtColumnTable(tbl, 6, 200);
                
    	System.out.println("--- Success ---");
	} 

	// Load Data into table filter
	// Load DataTable
	public void ShowTableDataFilter() 
    {
    	System.out.println("--- Loading ---");
    	ChamCongDAO dao = new ChamCongDAO();
    	model = new DefaultTableModel(){
    		@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
    	};
    	
        //Set Column Title
    	Vector column = new Vector();
    	column.add("Mã Chấm Công");
        column.add("Ca");        
        column.add("Tên NV");  
        column.add("Ngay");
        column.add("Thang");
        column.add("Nam");
        column.add("Diễn giải");        
        
        model.setColumnIdentifiers(column);
        //listNguoiDung = dao.Load();
        String maCa = listCa.get(cboCaFilter.getSelectedIndex()).getMaCa();
		String maNguoiDung = listNguoiDung.get(cboNguoiDungFilter.getSelectedIndex()).getMaNguoiDung();
        listChamCongModel = dao.GetList(maCa, maNguoiDung, (int) speNamFilter.getValue(), (int) speThangFilter.getValue());
        for (int i = 0; i < listChamCongModel.size(); i++) {
        	ChamCongModel entity = (ChamCongModel)listChamCongModel.get(i);
        	Vector row = new Vector();
        	row.add(entity.getMaChamCong());        	
        	row.add(entity.getTenCa());        	
        	row.add(entity.getTenNguoiDung());
        	row.add(entity.getNgay());
        	row.add(entity.getThang());
        	row.add(entity.getNam());
        	row.add(entity.getDienGiai());      	
        	
            
            model.addRow(row);
        }
        
        tbl.setModel(model);
        DataService.SetVisibleColumnTable(tbl, 0);
                
        DataService.SetWidhtColumnTable(tbl, 1, 140);
        DataService.SetWidhtColumnTable(tbl, 2, 200);
        DataService.SetWidhtColumnTable(tbl, 3, 80);
        DataService.SetWidhtColumnTable(tbl, 4, 80);
        DataService.SetWidhtColumnTable(tbl, 5, 80);  
        DataService.SetWidhtColumnTable(tbl, 6, 200);
                
    	System.out.println("--- Success ---");
	} 
	
	
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
		ChamCongModel entity = listChamCongModel.get(index);		
		txtDienGiai.setText(entity.getDienGiai());
		
		
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
				
				
				//dao.UpdateData(key, dienGiai, status);
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
    		
			ChamCongDAO dao = new ChamCongDAO();
			
			int key = listChamCongModel.get(tbl.getSelectedRow()).getMaChamCong();
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
    	ChamCongDAO dao = new ChamCongDAO();
    	String maCa = listCa.get(cboCa.getSelectedIndex()).getMaCa();
		String maNguoiDung = listNguoiDung.get(cboNguoiDung.getSelectedIndex()).getMaNguoiDung();	
		
		String errMessage = "";
		if(errMessage.length()<1)
		{			
			Chamcong entity = new Chamcong();
			Date currentDate = new Date();
			try 
			{		
				entity.setMaCa(maCa);
				entity.setMaNguoiDung(maNguoiDung);
				entity.setNgay((int)speNgay.getValue());
				entity.setThang((int)speThang.getValue());
				entity.setNam((int)speNam.getValue());
				entity.setDienGiai(txtDienGiai.getText());				
								
				
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
					fTimekeeping frame = new fTimekeeping();
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
	public fTimekeeping() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Thoát nghiệp vụ -> Chấm công");
			}							
			
		});
		setTitle("Chấm công");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 766, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Th\u00F4ng tin", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 271, 450);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblDinGii = new JLabel("Diễn giải");
		lblDinGii.setBounds(20, 197, 53, 14);
		panel.add(lblDinGii);
		
		txtDienGiai = new JTextPane();
		txtDienGiai.setBounds(94, 174, 159, 68);
		panel.add(txtDienGiai);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setMargin(new Insets(2, 5, 2, 5));
		
		btnThem.setBounds(20, 349, 64, 23);
		panel.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		
		btnSua.setBounds(104, 349, 64, 23);
		panel.add(btnSua);
		
		JButton btnXoa = new JButton("Xóa");
		
		btnXoa.setBounds(183, 349, 64, 23);
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
		
		JLabel lblNgy = new JLabel("Ngày");
		lblNgy.setBounds(18, 96, 66, 14);
		panel.add(lblNgy);
		
		JLabel lblThng = new JLabel("Tháng ");
		lblThng.setBounds(18, 121, 66, 14);
		panel.add(lblThng);
		
		JLabel lblNm = new JLabel("Năm");
		lblNm.setBounds(18, 146, 66, 14);
		panel.add(lblNm);
		
		speNgay = new JSpinner();
		speNgay.setBounds(94, 93, 53, 20);
		panel.add(speNgay);
		
		speThang = new JSpinner();
		speThang.setBounds(94, 118, 53, 20);
		panel.add(speThang);
		
		speNam = new JSpinner();
		speNam.setBounds(94, 143, 53, 20);
		panel.add(speNam);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Danh s\u00E1ch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(291, 110, 453, 351);
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
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Truy v\u1EA5n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(291, 11, 449, 88);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel label = new JLabel("Ca");
		label.setBounds(22, 23, 66, 14);
		panel_2.add(label);
		
		cboCaFilter = new JComboBox();
		cboCaFilter.setBounds(98, 19, 122, 22);
		panel_2.add(cboCaFilter);
		
		cboNguoiDungFilter = new JComboBox();
		cboNguoiDungFilter.setBounds(98, 50, 122, 22);
		panel_2.add(cboNguoiDungFilter);
		
		JLabel label_1 = new JLabel("Nhân viên");
		label_1.setBounds(22, 54, 66, 14);
		panel_2.add(label_1);
		
		JLabel label_3 = new JLabel("Tháng ");
		label_3.setBounds(251, 54, 66, 14);
		panel_2.add(label_3);
		
		JLabel label_2 = new JLabel("Năm");
		label_2.setBounds(251, 23, 66, 14);
		panel_2.add(label_2);
		
		speThangFilter = new JSpinner();
		speThangFilter.setBounds(297, 51, 53, 20);
		panel_2.add(speThangFilter);
		
		speNamFilter = new JSpinner();
		speNamFilter.setBounds(297, 20, 53, 20);
		panel_2.add(speNamFilter);
		
		JButton btnFilter = new JButton("Lọc");
		
		btnFilter.setMargin(new Insets(2, 5, 2, 5));
		btnFilter.setBounds(360, 50, 79, 23);
		panel_2.add(btnFilter);
		
		// call function get data default
		
		ShowTableData();
		FillcboCa();
		FillcboNguoiDung();
		SetSpinEdit();
		
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
		
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShowTableDataFilter();
			}
		});
		
	}
}
