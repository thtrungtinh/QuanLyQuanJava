package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
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
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import entities.*;
import utilities.DataService;
import dao.*;
import java.awt.Insets;
import java.awt.event.MouseAdapter;

public class fShift extends JFrame {

	private JPanel contentPane;
	private JTextField txtMaCa;
	private JTextField txtTenCa;
	private JTextPane txtDienGiai;
	private JCheckBox chkStatus;	
	private DefaultTableModel model;
	private JTable tbl;
	
	private JSpinner speBatDau;
	private JSpinner speKetThuc;  
	
	private JFormattedTextField txtNhanVienToiThieu;
	private JFormattedTextField txtNhanVienToiDa;
	private JFormattedTextField txtLuongCaTheoNgay;
	
	private List<Calamviec> listCaLamViec;
	
    public void ShowTableData() 
    {
    	System.out.println("--- Loading ---");
    	CaLamViecDAO dao = new CaLamViecDAO();
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
        column.add("Ca");
        column.add("Bắt đầu");
        column.add("Kết thúc");
        column.add("Diễn giải");
        column.add("Lương/Ngày");
        column.add("Sử dụng");
        model.setColumnIdentifiers(column);
        listCaLamViec = dao.Load();
        for (int i = 0; i < listCaLamViec.size(); i++) {
        	Calamviec entity = (Calamviec)listCaLamViec.get(i);
        	Vector row = new Vector();
            row.add(entity.getMaCa());
            row.add(entity.getTenCa());
            row.add(entity.getBatDau());
            row.add(entity.getKetThuc());
            row.add(entity.getDienGiai());
            row.add(entity.getLuongCaTheoNgay());
            row.add((Boolean)entity.isStatus());
            
            model.addRow(row);
        }
        
        tbl.setModel(model);
        DataService.SetColumnTableToCheckBox(tbl, 6);  
        DataService.SetWidhtColumnTable(tbl, 0, 140);
        DataService.SetWidhtColumnTable(tbl, 1, 170);
        DataService.SetWidhtColumnTable(tbl, 2, 110);
        DataService.SetWidhtColumnTable(tbl, 3, 110);
        DataService.SetWidhtColumnTable(tbl, 4, 170);
        DataService.SetWidhtColumnTable(tbl, 5, 110);
        DataService.SetWidhtColumnTable(tbl, 6, 80);
    	System.out.println("--- Success ---");
	}
    
    private void ClickRowTable(int index) {
		Calamviec entity = listCaLamViec.get(index);
		txtMaCa.setText(entity.getMaCa());
		txtTenCa.setText(entity.getTenCa());
		txtDienGiai.setText(entity.getDienGiai());
		chkStatus.setSelected(entity.isStatus());
		txtNhanVienToiThieu.setValue(entity.getNhanVienToiThieu());
		txtNhanVienToiDa.setValue(entity.getNhanVienToiDa());
		txtLuongCaTheoNgay.setValue(entity.getLuongCaTheoNgay());
		speBatDau.setValue(entity.getBatDau());
		speKetThuc.setValue(entity.getKetThuc());		
	}
    
    private String EditData()
    {
    	String errMessage = "";
    	try {
    		CaLamViecDAO dao = new CaLamViecDAO();
			errMessage = dao.CheckEdit(txtMaCa.getText());
			if(errMessage.length()<1)
			{
				Date batDau = (Date)speBatDau.getValue();
				Date ketThuc = (Date)speKetThuc.getValue();
				int nhanVienToiThieu = (int)txtNhanVienToiThieu.getValue();
				int nhanVienToiDa = ((int)txtNhanVienToiDa.getValue());
				int LuongCaTheoNgay = ((int)txtLuongCaTheoNgay.getValue());
				
				dao.UpdateData(txtMaCa.getText(), txtTenCa.getText(), batDau, ketThuc, nhanVienToiThieu, nhanVienToiDa, LuongCaTheoNgay, txtDienGiai.getText(), chkStatus.isSelected());
				errMessage = "Cập nhật thành công !";
			}			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e);
		}
    	return errMessage;
    }
    
    private String DeleteData(String maCa)
    {
    	String errMessage = "";
    	try {
			CaLamViecDAO dao = new CaLamViecDAO();
			errMessage = dao.CheckDelete(maCa);
			if(errMessage.length()<1)
			{
				dao.Delete(maCa);
				errMessage = "Xóa thành công !";
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
    	return errMessage;    	
    }
    
    private String InsertData()
    {
    	
		CaLamViecDAO dao = new CaLamViecDAO();
		String errMessage = dao.CheckInsert(txtMaCa.getText());
		if(errMessage.length()<1)
		{
			Calamviec entity = new Calamviec();
			Date currentDate = new Date();
			try
			{
				Date batDau = (Date)speBatDau.getValue();
				Date ketThuc = (Date)speKetThuc.getValue();
				entity.setMaCa(txtMaCa.getText());
				entity.setTenCa(txtTenCa.getText());
				entity.setBatDau(new java.sql.Time(batDau.getTime()));
				entity.setKetThuc(new java.sql.Time(ketThuc.getTime()));
				entity.setNhanVienToiThieu((int)txtNhanVienToiThieu.getValue());
				entity.setNhanVienToiDa((int)txtNhanVienToiDa.getValue());
				entity.setLuongCaTheoNgay((int)txtLuongCaTheoNgay.getValue());
				entity.setDienGiai(txtDienGiai.getText());
				entity.setStatus(chkStatus.isSelected());
				entity.setUpdatedBy(DataService.GetUserID());
				entity.setCreatedBy(DataService.GetUserID());
				entity.setUpdatedDate(currentDate);
				entity.setCreatedDate(currentDate);
				
				errMessage = dao.Insert(entity);
				ShowTableData();						
			} 
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error: "+ e.toString());	
				return e.toString();
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
					fShift frame = new fShift();
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
	public fShift() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Thoát Danh mục trình độ");
			}							
			
		});
		setTitle("Ca làm việc");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 869, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Th\u00F4ng tin", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 271, 318);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mã ca");
		lblNewLabel.setBounds(10, 29, 72, 14);
		panel.add(lblNewLabel);
		
		txtMaCa = new JTextField();
		txtMaCa.setBounds(91, 26, 64, 20);
		panel.add(txtMaCa);
		txtMaCa.setColumns(10);
		
		JLabel lblVTr = new JLabel("Tên ca");
		lblVTr.setBounds(10, 61, 46, 14);
		panel.add(lblVTr);
		
		txtTenCa = new JTextField();
		txtTenCa.setColumns(10);
		txtTenCa.setBounds(91, 58, 170, 20);
		panel.add(txtTenCa);
		
		chkStatus = new JCheckBox("Sử dụng");
		chkStatus.setSelected(true);
		chkStatus.setBounds(184, 25, 77, 23);
		panel.add(chkStatus);
		
		JLabel lblDinGii = new JLabel("Diễn giải");
		lblDinGii.setBounds(10, 114, 53, 14);
		panel.add(lblDinGii);
		
		txtDienGiai = new JTextPane();
		txtDienGiai.setBounds(91, 89, 170, 68);
		panel.add(txtDienGiai);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setMargin(new Insets(2, 5, 2, 5));
		
		btnThem.setBounds(21, 266, 64, 23);
		panel.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		
		btnSua.setBounds(105, 266, 64, 23);
		panel.add(btnSua);
		
		JButton btnXoa = new JButton("Xóa");
		
		btnXoa.setBounds(184, 266, 64, 23);
		panel.add(btnXoa);
		
		speBatDau = new JSpinner();
		speBatDau.setBounds(91, 168, 72, 20);
		panel.add(speBatDau);
		
		speKetThuc = new JSpinner();
		speKetThuc.setBounds(192, 168, 69, 20);
		panel.add(speKetThuc);
		
		JLabel lblThiGian = new JLabel("Thời gian");
		lblThiGian.setBounds(10, 171, 53, 14);
		panel.add(lblThiGian);
		
		JLabel lbln = new JLabel("đến");
		lbln.setBounds(168, 171, 53, 14);
		panel.add(lbln);
		
		JLabel lblTiThiu = new JLabel("Số nhân viên");
		lblTiThiu.setBounds(10, 199, 72, 14);
		panel.add(lblTiThiu);
		
		JLabel label = new JLabel("đến");
		label.setBounds(168, 199, 53, 14);
		panel.add(label);
		
		JLabel lblLngngy = new JLabel("Lương/Ngày");
		lblLngngy.setBounds(10, 227, 72, 14);
		panel.add(lblLngngy);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Danh s\u00E1ch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(291, 11, 552, 318);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		tbl = new JTable();
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {				
					if (e.getSource() == tbl) {
			            int iDongDaChon = tbl.getSelectedRow();
			            if (iDongDaChon != -1) {
			            	
			            	ClickRowTable(iDongDaChon);
			            }
			        }
				} catch (Exception ex) {
					System.out.println("Error: " + ex);
				}
			}
		});
		tbl.setPreferredSize(new Dimension(500, 500));
		scrollPane.setViewportView(tbl);		
		
		txtNhanVienToiThieu = new JFormattedTextField(DataService.SetTextFieldIntegerFormat());
		txtNhanVienToiThieu.setText("0");
		txtNhanVienToiThieu.setBounds(91, 196, 72, 20);
		panel.add(txtNhanVienToiThieu);
		
		txtNhanVienToiDa = new JFormattedTextField(DataService.SetTextFieldIntegerFormat());
		txtNhanVienToiDa.setText("0");
		txtNhanVienToiDa.setBounds(192, 196, 69, 20);
		panel.add(txtNhanVienToiDa);
		
		txtLuongCaTheoNgay = new JFormattedTextField(DataService.SetTextFieldIntegerFormat());
		txtLuongCaTheoNgay.setText("0");
		txtLuongCaTheoNgay.setBounds(91, 224, 170, 20);
		panel.add(txtLuongCaTheoNgay);
		
		// Load data default
		ShowTableData();
		DataService.LoadSpinEditTime(speBatDau);
		DataService.LoadSpinEditTime(speKetThuc);	
				
		// Load action 
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
							String errMessage = DeleteData(txtMaCa.getText());
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
