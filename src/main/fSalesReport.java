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

public class fSalesReport extends JFrame {

	private JPanel contentPane;
	private JTable tblHoaDon;
	private JTable tblChiTietHoaDon;
	private DefaultTableModel model;
	private List<BanModel> listBan;
	private List<HoaDonModel> listHoaDon;
	private List<ChiTietHoaDonModel> listChiTietHoaDon;
	private JComboBox cboBan;
	private JDateChooser dtcTuNgay;
	private JDateChooser dtcDenNgay;
	private int thanhTien;
	private int tongTien;
	private JLabel lblThanhTien;
	private JLabel lblTongTien;

	
	//Load Combobox Nhom thuc don
	private void FillcboBan()
	{		
		BanDAO entities = new BanDAO();
		listBan = entities.GetList(1);		
		for(BanModel entity : listBan)
		{
			cboBan.addItem(entity.getTenBan());		
		}		
	}
	
	// Load DataTable thuc don
	public void ShowTableDataHoaDon(Date tu, Date den, String maBan) 
    {		
		tongTien = 0;
    	System.out.println("--- Loading ---");
    	HoaDonDAO dao = new HoaDonDAO();
    	model = new DefaultTableModel(){
    		@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
    	};
    	
        //Set Column Title
    	Vector column = new Vector();
        column.add("Mã HD");        
        column.add("Bàn");
        column.add("Thành tiền hóa đơn");
        
        model.setColumnIdentifiers(column);
        listHoaDon = dao.GetList(tu, den, maBan);
        for (int i = 0; i < listHoaDon.size(); i++) {
        	HoaDonModel entity = (HoaDonModel)listHoaDon.get(i);
        	Vector row = new Vector();
        	row.add(entity.getMaHD());
        	row.add(entity.getTenBan());	        	
        	row.add(String.format("%,8d%n", entity.getTongTien()));
        	tongTien += entity.getTongTien();  		            
            model.addRow(row);
        }
        
        tblHoaDon.setModel(model);	        
        DataService.SetWidhtColumnTable(tblHoaDon, 0, 170);
        DataService.SetWidhtColumnTable(tblHoaDon, 1, 170);
        DataService.SetWidhtColumnTable(tblHoaDon, 2, 170);
        lblTongTien.setText(String.format("%,8d%n", tongTien));      
        
    	System.out.println("--- Success ---");
	} 
	
	//Load table chi tiet hoa don
		public void ShowTableDataChiTietHoaDon( String maHD ) 
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
	        listChiTietHoaDon = dao.GetList(maHD, 2);
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
	        	thanhTien += entity.getSoLuong() * entity.getGia();	            
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
					fSalesReport frame = new fSalesReport();
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
	public fSalesReport() {
		setTitle("B\u00E1o c\u00E1o doanh thu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 562);
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
		
		JLabel lblNewLabel = new JLabel("B\u00E0n");
		
		cboBan = new JComboBox();
		
		JButton btnLoc = new JButton("L\u1ECDc");
		btnLoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date tu = (Date)dtcTuNgay.getDate();
				Date den = (Date)dtcDenNgay.getDate();
				String maBan = listBan.get(cboBan.getSelectedIndex()).getMaBan();
				
				ShowTableDataHoaDon(tu, den, maBan);
				ShowTableDataChiTietHoaDon("");
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(46)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblT)
						.addComponent(lblNewLabel))
					.addGap(13)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(dtcTuNgay, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lbln, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addComponent(cboBan, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(18)
							.addComponent(btnLoc, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(dtcDenNgay, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(149))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(12)
							.addComponent(lblT))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(14)
							.addComponent(lbln))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(dtcTuNgay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(dtcDenNgay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(cboBan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLoc))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(300, 10));
		panel_1.setBorder(new TitledBorder(null, "H\u00F3a \u0111\u01A1n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1, BorderLayout.WEST);
		
		JScrollPane scrollPaneHoaDon = new JScrollPane(tblHoaDon, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		lblTongTien = new JLabel("0");
		lblTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(scrollPaneHoaDon, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addComponent(lblTongTien)
							.addGap(32))))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneHoaDon, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblTongTien))
		);
		
		tblHoaDon = new JTable();
		tblHoaDon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JTable table =(JTable) arg0.getSource();
				Point p = arg0.getPoint();
				int row = table.rowAtPoint(p);
	        	String maHD = (String)table.getValueAt(row, 0);
	        	ShowTableDataChiTietHoaDon(maHD);
	        }
			
		});
		scrollPaneHoaDon.setViewportView(tblHoaDon);
		panel_1.setLayout(gl_panel_1);
		
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
					.addComponent(scrollPaneChiTietHoaDon, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(11, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(269, Short.MAX_VALUE)
					.addComponent(lblThanhTien, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
					.addGap(35))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneChiTietHoaDon, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblThanhTien))
		);
		
		tblChiTietHoaDon = new JTable();
		scrollPaneChiTietHoaDon.setViewportView(tblChiTietHoaDon);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Th\u00E0nh ti\u1EC1n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_3, BorderLayout.SOUTH);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 862, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 10, Short.MAX_VALUE)
		);
		panel_3.setLayout(gl_panel_3);
		
		// call load form
		FillcboBan();
	}
}
