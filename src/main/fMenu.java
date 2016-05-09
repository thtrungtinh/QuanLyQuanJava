package main;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
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
import javax.swing.filechooser.FileNameExtensionFilter;
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
import model.NguoiDungModel;
import utilities.DataService;
import dao.*;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.LineBorder;

public class fMenu extends JFrame  {

	private JPanel contentPane;
	private JTextField txtMaThucDon;
	private JTextField txtTenThucDon;
	private JTextPane txtDienGiai;
	private JCheckBox chkStatus;	
	private DefaultTableModel model;
	private JTable tbl;
	private JFormattedTextField txtGia;
	private JLabel lblHinhAnh;
	
	private JComboBox cboNhom;
	
	private List<Vitri> listVitri;
	private List<Nhomthucdon> listNhomThucDon;
	List<Thucdon> listThucDon;	
	List<NguoiDungModel> listThucDonModel;
	
	
		
	//Load Combobox Nhom thuc don
	private void FillcboThucDon()
	{		
		NhomThucDonDAO entities = new NhomThucDonDAO();
		listNhomThucDon = entities.Load();
		for(Nhomthucdon entity : listNhomThucDon)
		{
			cboNhom.addItem(entity.getTenNhom());
		}		
	}
	// Load DataTable
	public void ShowTableData() 
    {
    	System.out.println("--- Loading ---");
    	ThucDonDAO dao = new ThucDonDAO();
    	model = new DefaultTableModel(){
    		@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
    	};
    	
        //Set Column Title
    	Vector column = new Vector();
        column.add("Mã TD");
        column.add("Tên thực đơn");
        column.add("Diễn giải");
        column.add("Giá");        
        column.add("Sử dụng");
        model.setColumnIdentifiers(column);
        listThucDon = dao.Load();
        for (int i = 0; i < listThucDon.size(); i++) {
        	Thucdon entity = (Thucdon)listThucDon.get(i);
        	Vector row = new Vector();
        	row.add(entity.getMaThucDon());
        	row.add(entity.getTenThucDon());
        	row.add(entity.getDienGiai());
        	row.add(entity.getGia());                         
            row.add((Boolean)entity.isStatus());
            
            model.addRow(row);
        }
        
        tbl.setModel(model);
        DataService.SetColumnTableToCheckBox(tbl, 4);
        
        DataService.SetWidhtColumnTable(tbl, 0, 110);
        DataService.SetWidhtColumnTable(tbl, 1, 200);
        DataService.SetWidhtColumnTable(tbl, 2, 200);
        DataService.SetWidhtColumnTable(tbl, 3, 110);
        DataService.SetWidhtColumnTable(tbl, 4, 80);
        
        
    	System.out.println("--- Success ---");
	} 
	
	private int GetIndexThucDon(List<Nhomthucdon> entities, String key) {
		int index = -1;
		for(int i = 0; i< entities.size(); i++)
		{			
			if(key.equals(entities.get(i).getMaNhom()))
			{				
				return i;
			}				
		}
		return index;
	}
	
    //Load Table click row
	private void ClickRowTable(int index) {
		Thucdon entity = listThucDon.get(index);
		txtMaThucDon.setText(entity.getMaThucDon());
		txtTenThucDon.setText(entity.getTenThucDon());
		
		txtGia.setValue(entity.getGia());
		txtDienGiai.setText(entity.getDienGiai());
		chkStatus.setSelected(entity.isStatus());
		
		
		int intdexThucDon = GetIndexThucDon(listNhomThucDon, entity.getMaNhom());
		
		
		cboNhom.setSelectedIndex(intdexThucDon);
	}
	
    private String EditData()
    {
    	String errMessage = "";
    	try {
    		ThucDonDAO dao = new ThucDonDAO();
			errMessage = dao.CheckEdit(txtMaThucDon.getText());
			if(errMessage.length()<1)
			{				
				String key = txtMaThucDon.getText();
				String ten = txtTenThucDon.getText();
				int gia =  (int)txtGia.getValue();
				
				byte[] hinhAnh = null;
				String dienGiai = txtDienGiai.getText();
				boolean status = chkStatus.isSelected();			
								
				String maNhom = listNhomThucDon.get(cboNhom.getSelectedIndex()).getMaNhom();		
				
				dao.UpdateData(key, ten, hinhAnh, dienGiai, gia, status, maNhom);
				errMessage = "Cập nhật thành công !";
			}			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e);
		}
    	return errMessage;
    }
    
    private String DeleteData(String key)
    {
    	String errMessage = "";
    	try {
			ThucDonDAO dao = new ThucDonDAO();
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
    	ThucDonDAO dao = new ThucDonDAO();
		String errMessage = dao.CheckInsert(txtMaThucDon.getText());
		if(errMessage.length()<1)
		{
			Thucdon entity = new Thucdon();
			Date currentDate = new Date();
			try 
			{	
				
				entity.setMaThucDon(txtMaThucDon.getText());
				entity.setTenThucDon(txtTenThucDon.getText());
				
				entity.setGia((int) txtGia.getValue());
				entity.setDienGiai(txtDienGiai.getText());
				entity.setStatus(chkStatus.isSelected());
				entity.setHinhAnh(null);
				
				entity.setMaNhom(listNhomThucDon.get(cboNhom.getSelectedIndex()).getMaNhom());				
				
				entity.setUpdatedBy(DataService.GetUserID());
				entity.setCreatedBy(DataService.GetUserID());
				entity.setUpdatedDate(currentDate);
				entity.setCreatedDate(currentDate);
						
				ShowTableData();
				errMessage = dao.Insert(entity);
				
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
					fMenu frame = new fMenu();
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
	public fMenu() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Thoát Hệ thống người dùng");
			}							
			
		});
		setTitle("Thực đơn");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1115, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Th\u00F4ng tin", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 271, 425);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mã thực đơn");
		lblNewLabel.setBounds(21, 31, 81, 14);
		panel.add(lblNewLabel);
		
		txtMaThucDon = new JTextField();
		txtMaThucDon.setBounds(97, 28, 81, 20);
		panel.add(txtMaThucDon);
		txtMaThucDon.setColumns(10);
		
		JLabel lblVTr = new JLabel("Tên ");
		lblVTr.setBounds(21, 59, 81, 14);
		panel.add(lblVTr);
		
		txtTenThucDon = new JTextField();
		txtTenThucDon.setColumns(10);
		txtTenThucDon.setBounds(97, 56, 159, 20);
		panel.add(txtTenThucDon);
		
		chkStatus = new JCheckBox("Sử dụng");
		chkStatus.setSelected(true);
		chkStatus.setBounds(184, 27, 79, 23);
		panel.add(chkStatus);
		
		JLabel lblDinGii = new JLabel("Diễn giải");
		lblDinGii.setBounds(21, 253, 53, 14);
		panel.add(lblDinGii);
		
		txtDienGiai = new JTextPane();
		txtDienGiai.setBounds(97, 231, 159, 68);
		panel.add(txtDienGiai);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setMargin(new Insets(2, 5, 2, 5));
		
		btnThem.setBounds(21, 321, 64, 23);
		panel.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		
		btnSua.setBounds(105, 321, 64, 23);
		panel.add(btnSua);
		
		JButton btnXoa = new JButton("Xóa");
		
		btnXoa.setBounds(184, 321, 64, 23);
		panel.add(btnXoa);
		
		JLabel lblaCh = new JLabel("Giá");
		lblaCh.setBounds(21, 171, 66, 14);
		panel.add(lblaCh);
		
		JLabel lblTrnh = new JLabel("Nhóm");
		lblTrnh.setBounds(21, 200, 66, 14);
		panel.add(lblTrnh);
		
		cboNhom = new JComboBox();
		cboNhom.setBounds(97, 196, 159, 22);
		panel.add(cboNhom);
		
		JButton cboLoadImage = new JButton("Hình");
		cboLoadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser file = new JFileChooser();
		          file.setCurrentDirectory(new File(System.getProperty("user.home")));
		          //filter the files
		          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
		          file.addChoosableFileFilter(filter);
		          int result = file.showSaveDialog(null);
		           //if the user click on save in Jfilechooser
		          if(result == JFileChooser.APPROVE_OPTION){
		              File selectedFile = file.getSelectedFile();
		              String path = selectedFile.getAbsolutePath();
		              lblHinhAnh.setIcon(DataService.ResizeImage(path, lblHinhAnh));
		          }
			}
		});
		cboLoadImage.setMargin(new Insets(2, 5, 2, 5));
		cboLoadImage.setBounds(21, 109, 64, 23);
		panel.add(cboLoadImage);
		
		lblHinhAnh = new JLabel("Loading ...");
		lblHinhAnh.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
		lblHinhAnh.setBounds(118, 87, 106, 70);
		panel.add(lblHinhAnh);
		
		txtGia = new JFormattedTextField(DataService.SetTextFieldIntegerFormat());
		txtGia.setBounds(97, 168, 159, 20);
		panel.add(txtGia);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Danh s\u00E1ch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(291, 11, 798, 425);
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
		System.out.println("Mở hê thống -> Quản lý người dùng");
		ShowTableData();
		FillcboThucDon();
				
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
							String errMessage = DeleteData(txtMaThucDon.getText());
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
