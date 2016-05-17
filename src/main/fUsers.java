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
import model.NguoiDungModel;
import utilities.DataService;
import dao.*;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class fUsers extends JFrame  {

	private JPanel contentPane;
	private JTextField txtMaNguoiDung;
	private JTextField txtTenNguoiDung;
	private JTextPane txtDienGiai;
	private JCheckBox chkStatus;	
	private JCheckBox chkGioiTinh;
	private DefaultTableModel model;
	private JTable tbl;
	private JPasswordField txtMatKhau;
	private JTextField txtCMND;
	private JTextField txtDienThoai;
	private JTextField txtDiaChi;
	private JDateChooser jdcNgaySinh;
	
	private JComboBox cboTrinhDo;
	private JComboBox cboViTri;	
	
	private List<Vitri> listVitri;
	private List<Trinhdo> listTrinhDo;
	List<Nguoidung> listNguoiDung;	
	List<NguoiDungModel> listNguoiDungModel;
	
	
	// Load combobox Vi tri
	private void FillcboViTri()
	{		
		ViTriDAO entities = new ViTriDAO();
		listVitri = entities.Load();
		for(Vitri entity : listVitri)
		{
			cboViTri.addItem(entity.getTenViTri());
		}		
	}
	//Load Combobox TrinhDo
	private void FillcboTrinhDo()
	{		
		TrinhDoDAO entities = new TrinhDoDAO();
		listTrinhDo = entities.Load();
		for(Trinhdo entity : listTrinhDo)
		{
			cboTrinhDo.addItem(entity.getTenTrinhDo());
		}		
	}
	// Load DataTable
	public void ShowTableData() 
    {
    	System.out.println("--- Loading ---");
    	NguoiDungDAO dao = new NguoiDungDAO();
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
	} 
	
	private int GetIndexVitri(List<Vitri> entities, String key) {
		int index = -1;
		for(int i = 0; i< entities.size(); i++)
		{			
			if(key.equals(entities.get(i).getMaViTri()))
			{				
				return i;
			}				
		}
		return index;
	}
	
	private int GetIndexTrinhDo(List<Trinhdo> entities, String key) {
		int index = -1;
		for(int i = 0; i< entities.size(); i++)
		{			
			if(key.equals(entities.get(i).getMaTrinhDo()))
			{				
				return i;
			}				
		}
		return index;
	}
	
    //Load Table click row
	private void ClickRowTable(int index) {
		Nguoidung entity = listNguoiDung.get(index);
		txtMaNguoiDung.setText(entity.getMaNguoiDung());
		txtTenNguoiDung.setText(entity.getTenNguoiDung());
		txtMatKhau.setText(entity.getMatKhau());
		txtCMND.setText(entity.getCmnd());
		chkGioiTinh.setSelected(entity.isGioiTinh());
		txtDiaChi.setText(entity.getDiaChi());
		txtDienThoai.setText(entity.getDienThoai());
		txtDienGiai.setText(entity.getDienGiai());
		chkStatus.setSelected(entity.isStatus());
		
		jdcNgaySinh.setDate(entity.getNgaySinh());	
		int intdexViTri = GetIndexVitri(listVitri, entity.getMaViTri());
		int intdexTrinhDo = GetIndexTrinhDo(listTrinhDo, entity.getMaTrinhDo());
		
		cboViTri.setSelectedIndex(intdexViTri);
		cboTrinhDo.setSelectedIndex(intdexTrinhDo);
	}
	
    private String EditData()
    {
    	String errMessage = "";
    	try {
    		NguoiDungDAO dao = new NguoiDungDAO();
			errMessage = dao.CheckEdit(txtMaNguoiDung.getText());
			if(errMessage.length()<1)
			{
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");				 
				Date ngaysinh = new Date();
				try {
					ngaysinh = formatter.parse(((JTextField)jdcNgaySinh.getDateEditor().getUiComponent()).getText());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error: " + e.toString());
				}
				String maNguoiDung = txtMaNguoiDung.getText();
				String tenNguoiDung = txtTenNguoiDung.getText();
				String matKhau = txtMatKhau.getText();
				String cmnd = txtCMND.getText();
				boolean gioiTinh = chkGioiTinh.isSelected();
				String diaChi = txtDiaChi.getText();
				String dienThoai = txtDienThoai.getText();
				String dienGiai = txtDienGiai.getText();
				boolean status = chkStatus.isSelected();			
				
				String maViTri = listVitri.get(cboViTri.getSelectedIndex()).getMaViTri();
				String maTrinhDo = listTrinhDo.get(cboTrinhDo.getSelectedIndex()).getMaTrinhDo();		
				
				dao.UpdateData(maNguoiDung, matKhau, tenNguoiDung, dienGiai, gioiTinh, ngaysinh, dienThoai, diaChi, cmnd, maViTri, maTrinhDo, status);
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
			NguoiDungDAO dao = new NguoiDungDAO();
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
    	NguoiDungDAO dao = new NguoiDungDAO();
		String errMessage = dao.CheckInsert(txtMaNguoiDung.getText());
		if(errMessage.length()<1)
		{
			Nguoidung entity = new Nguoidung();
			Date currentDate = new Date();
			try 
			{		
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				 
				Date ngaysinh = new Date();
				try {
					ngaysinh = formatter.parse(((JTextField)jdcNgaySinh.getDateEditor().getUiComponent()).getText());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error: " + e.toString());
				}
				entity.setMaNguoiDung(txtMaNguoiDung.getText());
				entity.setTenNguoiDung(txtTenNguoiDung.getText());
				entity.setMatKhau(txtMatKhau.getText());
				entity.setCmnd(txtCMND.getText());
				entity.setGioiTinh(chkGioiTinh.isSelected());
				entity.setDiaChi(txtDiaChi.getText());
				entity.setDienThoai(txtDienThoai.getText());
				entity.setDienGiai(txtDienGiai.getText());
				entity.setStatus(chkStatus.isSelected());
				
				entity.setNgaySinh(ngaysinh);
				entity.setMaViTri(listVitri.get(cboViTri.getSelectedIndex()).getMaViTri());
				entity.setMaTrinhDo(listTrinhDo.get(cboTrinhDo.getSelectedIndex()).getMaTrinhDo());				
				
				entity.setUpdatedBy(DataService.GetUserID());
				entity.setCreatedBy(DataService.GetUserID());
				entity.setUpdatedDate(currentDate);
				entity.setCreatedDate(currentDate);						
				
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
					fUsers frame = new fUsers();
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
	public fUsers() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Thoát Hệ thống người dùng");
			}							
			
		});
		setTitle("Nhân viên");
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
		
		JLabel lblNewLabel = new JLabel("Mã nhân viên");
		lblNewLabel.setBounds(21, 31, 81, 14);
		panel.add(lblNewLabel);
		
		txtMaNguoiDung = new JTextField();
		txtMaNguoiDung.setBounds(97, 28, 81, 20);
		panel.add(txtMaNguoiDung);
		txtMaNguoiDung.setColumns(10);
		
		JLabel lblVTr = new JLabel("Tên NV");
		lblVTr.setBounds(21, 59, 81, 14);
		panel.add(lblVTr);
		
		txtTenNguoiDung = new JTextField();
		txtTenNguoiDung.setColumns(10);
		txtTenNguoiDung.setBounds(97, 56, 159, 20);
		panel.add(txtTenNguoiDung);
		
		chkStatus = new JCheckBox("Sử dụng");
		chkStatus.setSelected(true);
		chkStatus.setBounds(184, 27, 79, 23);
		panel.add(chkStatus);
		
		JLabel lblDinGii = new JLabel("Diễn giải");
		lblDinGii.setBounds(21, 314, 53, 14);
		panel.add(lblDinGii);
		
		txtDienGiai = new JTextPane();
		txtDienGiai.setBounds(97, 292, 159, 68);
		panel.add(txtDienGiai);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setMargin(new Insets(2, 5, 2, 5));
		
		btnThem.setBounds(10, 391, 64, 23);
		panel.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		
		btnSua.setBounds(94, 391, 64, 23);
		panel.add(btnSua);
		
		JButton btnXoa = new JButton("Xóa");
		
		btnXoa.setBounds(173, 391, 64, 23);
		panel.add(btnXoa);
		
		JLabel lblMtM = new JLabel("Mật mã");
		lblMtM.setBounds(21, 87, 46, 14);
		panel.add(lblMtM);
		
		txtMatKhau = new JPasswordField();
		txtMatKhau.setBounds(97, 84, 159, 20);
		panel.add(txtMatKhau);
		
		JLabel lblCmnd = new JLabel("CMND");
		lblCmnd.setBounds(21, 117, 46, 14);
		panel.add(lblCmnd);
		
		txtCMND = new JTextField();
		txtCMND.setColumns(10);
		txtCMND.setBounds(97, 112, 81, 20);
		panel.add(txtCMND);
		
		chkGioiTinh = new JCheckBox("Nam");
		chkGioiTinh.setSelected(true);
		chkGioiTinh.setBounds(184, 111, 72, 23);
		panel.add(chkGioiTinh);
		
		JLabel lblinThoi = new JLabel("Điện thoại");
		lblinThoi.setBounds(21, 143, 66, 14);
		panel.add(lblinThoi);
		
		txtDienThoai = new JTextField();
		txtDienThoai.setColumns(10);
		txtDienThoai.setBounds(97, 140, 159, 20);
		panel.add(txtDienThoai);
		
		JLabel lblaCh = new JLabel("Địa chỉ");
		lblaCh.setBounds(21, 171, 66, 14);
		panel.add(lblaCh);
		
		txtDiaChi = new JTextField();
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(97, 168, 159, 20);
		panel.add(txtDiaChi);
		
		JLabel lblNgySinh = new JLabel("Ngày sinh");
		lblNgySinh.setBounds(21, 199, 66, 14);
		panel.add(lblNgySinh);
		
		jdcNgaySinh = new JDateChooser();
		jdcNgaySinh.setDateFormatString("dd-MM-yyyy");
		jdcNgaySinh.setBounds(97, 196, 115, 20);
		panel.add(jdcNgaySinh);
		
		JLabel lblTrnh = new JLabel("Trình độ");
		lblTrnh.setBounds(21, 229, 66, 14);
		panel.add(lblTrnh);
		
		cboTrinhDo = new JComboBox();
		cboTrinhDo.setBounds(97, 225, 159, 22);
		panel.add(cboTrinhDo);
		
		JLabel lblVTr_1 = new JLabel("Vị trí");
		lblVTr_1.setBounds(21, 260, 66, 14);
		panel.add(lblVTr_1);
		
		cboViTri = new JComboBox();
		cboViTri.setBounds(97, 256, 159, 22);
		panel.add(cboViTri);
		
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
		FillcboViTri();
		FillcboTrinhDo();
		
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
							String errMessage = DeleteData(txtMaNguoiDung.getText());
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
