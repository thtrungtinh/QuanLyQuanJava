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
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class fReportTimeKeeping extends JFrame  {

	private JPanel contentPane;
	private DefaultTableModel model;
	private JTable tbl;
	
	private JComboBox cboCaFilter;
	private JComboBox cboNguoiDungFilter;	
	private JSpinner speThangFilter;
	private JSpinner speNamFilter;
	private List<NhanVienModel> listNhanVien;
	private List<CaModel> listCa;			
	private List<Thoigiancothelamviec> listThoiGian;	
	private List<ChamCongModel> listChamCongModel;	
	private int luong;
	private JLabel lblTongLuong;
	private JLabel lblSoBuoi;
	
	private void SetSpinEdit()
	{
		DataService.SetSpinEditMonth(speThangFilter, 0);
		DataService.SetSpinEditYear(speNamFilter);
	}
	
	private void FillcboCa()
	{		
		CaLamViecDAO entities = new CaLamViecDAO();
		listCa = entities.GetList(1);		
		for(CaModel entity : listCa)
		{
			cboCaFilter.addItem(entity.getTenCa());		
		}		
	}
	
	
	private void FillcboNhanVien()
	{		
		NguoiDungDAO entities = new NguoiDungDAO();
		listNhanVien = (List<NhanVienModel>) entities.GetList(1);		
		for(NhanVienModel entity : listNhanVien)
		{
			cboNguoiDungFilter.addItem(entity.getTenNguoiDung());		
		}		
	}	
	// Load Data into table filter
	// Load DataTable
	public void ShowTableDataFilter() 
    {
		luong = 0;
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
        column.add("Ngày");
        column.add("Tháng");
        column.add("Năm");
        column.add("Diễn giải"); 
        column.add("Lương");
        
        model.setColumnIdentifiers(column);
        //listNguoiDung = dao.Load();
        String maCa = listCa.get(cboCaFilter.getSelectedIndex()).getMaCa();
		String maNguoiDung = listNhanVien.get(cboNguoiDungFilter.getSelectedIndex()).getMaNguoiDung();
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
        	row.add(entity.getLuongCaTheoNgay());
        	luong += entity.getLuongCaTheoNgay();
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
            
        lblTongLuong.setText(String.format("%,8d%n", luong));  
        lblSoBuoi.setText(String.format("%,8d%n", listChamCongModel.size()));  
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
    
    
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fReportTimeKeeping frame = new fReportTimeKeeping();
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
	public fReportTimeKeeping() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Thoát nghiệp vụ -> Chấm công");
			}							
			
		});
		setTitle("Chấm công");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 723, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Danh s\u00E1ch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		btnFilter.setBounds(360, 50, 107, 23);
		panel_2.add(btnFilter);
		
		lblTongLuong = new JLabel("0");
		
		JLabel lblSBui = new JLabel("Số buổi");
		
		lblSoBuoi = new JLabel("0");
		
		JLabel lblTngLng = new JLabel("Tổng lương");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSBui, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSoBuoi, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(79)
							.addComponent(lblTngLng, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblTongLuong, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addGap(39))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTongLuong)
						.addComponent(lblSBui)
						.addComponent(lblSoBuoi)
						.addComponent(lblTngLng))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		// call function get data default
				
		FillcboCa();
		FillcboNhanVien();
		SetSpinEdit();
		
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShowTableDataFilter();
			}
		});
		
	}
}
