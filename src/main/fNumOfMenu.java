package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;

import model.*;
import utilities.DataService;

import javax.swing.JComboBox;
import entities.*;
import dao.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class fNumOfMenu extends JFrame {

	private JPanel contentPane;
	private JTable tblChiTietHoaDon;
	private DefaultTableModel model;
	private List<ThucDonModel> listThucDon;
	private List<NhanVienModel> listNhanVien;
	private List<HoaDonModel> listHoaDon;
	private List<CaModel> listCa;
	private List<ChiTietHoaDonModel> listChiTietHoaDon;
	private JComboBox cboBan;
	private JDateChooser dtcTuNgay;
	private JDateChooser dtcDenNgay;
	private int thanhTien;
	private int tongTien;
	private int tongKhach;
	private JLabel lblThanhTien;

	
	
	private void FillcboBan()
	{		
		ThucDonDAO entities = new ThucDonDAO();
		listThucDon = entities.GetList(1);		
		for(ThucDonModel entity : listThucDon)
		{
			cboBan.addItem(entity.getTenThucDon());		
		}		
	}
	//Load table chi tiet hoa don
		public void ShowTableDataChiTietHoaDon( Date tu, Date den, String maThucDon ) 
	    {	
			thanhTien = 0;
	    	System.out.println("--- Loading ---");
	    	ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();
	    	model = new DefaultTableModel(){
	    		@Override
	            public boolean isCellEditable(int row, int column) {
	               //all cells false
	               return false;
	            }
	    	};
	    	
	        //Set Column Title
	    	Vector column = new Vector();
	        
	    	column.add("ID");
	    	column.add("Mã HD");
	    	column.add("Mã Thực đơn");
	        column.add("Thực đơn");
	        column.add("Số lượng");
	        column.add("Giá");
	        column.add("Thành tiền");
	        
	        model.setColumnIdentifiers(column);
	        listChiTietHoaDon = dao.GetList(tu, den, maThucDon);
	        for (int i = 0; i < listChiTietHoaDon.size(); i++) {
	        	ChiTietHoaDonModel entity = (ChiTietHoaDonModel)listChiTietHoaDon.get(i);
	        	Vector row = new Vector();
	        	row.add(entity.getiD());
	        	row.add(entity.getMaHD());
	        	row.add(entity.getMaThucDon());
	        	row.add(entity.getTenThucDon());	        	
	        	row.add(entity.getSoLuong());
	        	row.add(entity.getGia());
	        	row.add(String.format("%,8d%n",(entity.getSoLuong() * entity.getGia())));
	        	thanhTien += entity.getSoLuong();	            
	            model.addRow(row);
	        }
	        
	        tblChiTietHoaDon.setModel(model);	
	        DataService.SetVisibleColumnTable(tblChiTietHoaDon, 0);
	        DataService.SetVisibleColumnTable(tblChiTietHoaDon, 1);
	        DataService.SetVisibleColumnTable(tblChiTietHoaDon, 2);        
	        
	        DataService.SetWidhtColumnTable(tblChiTietHoaDon, 3, 170);  
	        DataService.SetWidhtColumnTable(tblChiTietHoaDon, 4, 80); 
	        DataService.SetWidhtColumnTable(tblChiTietHoaDon, 5, 140); 
	        DataService.SetWidhtColumnTable(tblChiTietHoaDon, 6, 140); 
	        lblThanhTien.setText(String.format("%,8d%n", thanhTien));
	    	System.out.println("--- Success ---");
		} 	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fNumOfMenu frame = new fNumOfMenu();
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
	public fNumOfMenu() {
		setTitle("B\u00E1o c\u00E1o doanh thu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 625, 667);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Truy v\u1EA5n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblT = new JLabel("T\u1EEB ng\u00E0y");
		
		dtcTuNgay = new JDateChooser();
		dtcTuNgay.setDateFormatString("dd-MM-yyyy");
		
		JLabel lbln = new JLabel("\u0111\u1EBFn ");
		
		dtcDenNgay = new JDateChooser();
		dtcDenNgay.setDateFormatString("dd-MM-yyyy");
		
		JLabel lblNewLabel = new JLabel("Thực đơn");
		
		cboBan = new JComboBox();
		
		JButton btnLoc = new JButton("L\u1ECDc");
		btnLoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date tu = (Date)dtcTuNgay.getDate();
				if(dtcTuNgay.getDate() == null)
				{
					JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu ... !");
					return;
				}
				Date den;
				if(dtcDenNgay.getDate() == null)
				{
					den = new Date();					
				}
				else
					den = (Date)dtcDenNgay.getDate();
				String maThucDon = listThucDon.get(cboBan.getSelectedIndex()).getMaThucDon();
				ShowTableDataChiTietHoaDon(tu, den, maThucDon);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(46)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLoc, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblT)
								.addComponent(lblNewLabel))
							.addGap(13)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cboBan, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(dtcTuNgay, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
							.addGap(18)
							.addComponent(lbln, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
							.addComponent(dtcDenNgay, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(539))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(12)
											.addComponent(lblT))
										.addGroup(gl_panel.createSequentialGroup()
											.addContainerGap()
											.addComponent(dtcTuNgay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel)
										.addComponent(cboBan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(14)
									.addComponent(lbln)))
							.addGap(18)
							.addComponent(btnLoc))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(dtcDenNgay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Chi ti\u1EBFt h\u00F3a \u0111\u01A1n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_2, BorderLayout.CENTER);
		
		JScrollPane scrollPaneChiTietHoaDon = new JScrollPane(tblChiTietHoaDon, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		lblThanhTien = new JLabel("0");
		lblThanhTien.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblThanhTien, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
							.addGap(26))
						.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
							.addComponent(scrollPaneChiTietHoaDon, GroupLayout.DEFAULT_SIZE, 921, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneChiTietHoaDon, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblThanhTien))
		);
		
		tblChiTietHoaDon = new JTable();
		scrollPaneChiTietHoaDon.setViewportView(tblChiTietHoaDon);
		panel_2.setLayout(gl_panel_2);
		
		// call load form
		FillcboBan();		
	}
}
