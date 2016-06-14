package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import utilities.DataService;
import entities.*;
import model.ChiTietHoaDonModel;
import dao.*;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;

import javax.swing.SwingConstants;

public class fSell extends JFrame implements ActionListener {

	/*
	 * Table hoadon cot Status
	 *  0 : Huỷ bàn
	 *  1 : Đang sử dụng
	 *  2 : Đã thanh toán	 
	 */
	private JPanel pnlChiTietHoaDon;
	private JPanel contentPane;
	private List<Ban> listBan;
	private JTable tblThucDon;
	private JTable tblChiTietHoaDon;
	private DefaultTableModel model;
	private List<Thucdon> listThucDon;
	private List<ChiTietHoaDonModel> listChiTietHoaDon;
	private JComboBox cboNhom;
	private List<Nhomthucdon> listNhomThucDon;
	private String keyMaHD = "";
	private String keyMaBan;
	private String keyMaHDGopBan="";
	private JButton sourceButton;
	private JButton sourceButtonChuyenBan;
	private JButton sourceButtonGopBan;
	private boolean isChuyenBan = false;
	private boolean isGopBan = false;
	private int thanhTien = 0;
	private JLabel lblThanhTien;
				
	// set title panel chi tiet hoa don
	private void SetTitlePanel(String title)
	{
		TitledBorder border;
		border = BorderFactory.createTitledBorder("Thực đơn đã gọi - " + title);
		pnlChiTietHoaDon.setBorder(border);
	}
	
	// Load danh sach thuc don
	private void Load_DanhSachThucDon()
	{
		String maNhom = listNhomThucDon.get(cboNhom.getSelectedIndex()).getMaNhom(); // lấy mã nhóm từ combobox nhóm thực đơn
		ShowTableData(maNhom);
	}
	//Load Combobox Nhom thuc don
	private void FillcboNhom()
	{		
		NhomThucDonDAO entities = new NhomThucDonDAO();
		listNhomThucDon = entities.Load();
		for(Nhomthucdon entity : listNhomThucDon)
		{
			cboNhom.addItem(entity.getTenNhom());		
		}		
	}
		
	// Load DataTable thuc don
	public void ShowTableData(String maNhom) 
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
        column.add("Mã ");
        column.add("Thực đơn");
        column.add("Diễn giải");
        column.add("Giá");
        
        model.setColumnIdentifiers(column);
        listThucDon = dao.GetList(maNhom);
        for (int i = 0; i < listThucDon.size(); i++) {
        	Thucdon entity = (Thucdon)listThucDon.get(i);
        	Vector row = new Vector();
        	row.add(entity.getMaThucDon());
        	row.add(entity.getTenThucDon());	        	
        	row.add(entity.getDienGiai());
        	row.add(entity.getGia());
        		            
            model.addRow(row);
        }
        
        tblThucDon.setModel(model);	        
        DataService.SetWidhtColumnTable(tblThucDon, 0, 0);
        DataService.SetWidhtColumnTable(tblThucDon, 1, 200);
        DataService.SetWidhtColumnTable(tblThucDon, 2, 80);
        DataService.SetWidhtColumnTable(tblThucDon, 3, 140);       
        
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
        listChiTietHoaDon = dao.GetList(maHD, 1);
        for (int i = 0; i < listChiTietHoaDon.size(); i++) {
        	ChiTietHoaDonModel entity = (ChiTietHoaDonModel)listChiTietHoaDon.get(i);
        	Vector row = new Vector();
        	row.add(entity.getiD());
        	row.add(entity.getMaHD());
        	row.add(entity.getMaThucDon());
        	row.add(entity.getTenThucDon());	        	
        	row.add(entity.getSoLuong());
        	row.add(entity.getGia());
        	row.add(entity.getSoLuong() * entity.getGia());
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
        lblThanhTien.setText(String.format("%,8d%n", thanhTien)); // thêm dấu phẩy (,) phần ngàn
    	System.out.println("--- Success ---");
	} 	
	
	//get mahd khi click button
	
	private void GetMaHDWhenClickButton(String maBan)
	{
		HoaDonDAO dao = new HoaDonDAO();
		keyMaHD = dao.GetKeyTop1(maBan);		
	}
	
	private void GetMaHDWhenClickButtonGopBan(String maBan)
	{
		HoaDonDAO dao = new HoaDonDAO();		
		keyMaHDGopBan = dao.GetKeyTop1(maBan);
	}
	
	/**
	 * Insert  hoa don
	 */
	
	private String InsertDataHoaDon(String maBan, int numOfCus)
    {   	
    	HoaDonDAO dao = new HoaDonDAO();
		String key = dao.GenerateBillID();
		String errMessage = "";
		keyMaHD = key;
		Hoadon entity = new Hoadon();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date currentDate = new Date();
		String currentTime = dateFormat.format(currentDate);
		String maCa =  dao.GetMaCa(currentTime);
		try 
		{						
			entity.setMaHd(key);
			entity.setMaban(maBan);				
			entity.setStatus(1);
			
			entity.setUpdatedBy(DataService.GetUserID());
			entity.setCreatedBy(DataService.GetUserID());
			entity.setUpdatedDate(currentDate);
			entity.setCreatedDate(currentDate);						
			entity.setNumOfCus(numOfCus);
			entity.setMaCa(maCa);
			errMessage = dao.Insert(entity);
			
		} 
		catch (Exception e) 
		{				
			e.printStackTrace();
			System.out.println("Error: " + e);				
		} 
	
		return errMessage;
    } 
	
	/**
	 * update thanh toan hoa don
	 */
	
	private void UpdateDataHoaDon(int status)
	{
		HoaDonDAO dao = new HoaDonDAO();
		ChiTietHoaDonDAO chiTietDAO = new ChiTietHoaDonDAO();
		String message = dao.CheckEdit(keyMaHD);		
		if(message == "")
		{
			dao.UpdateDataStatus(keyMaHD, status);	
			chiTietDAO.UpdateDataStatus(keyMaHD, status);
			sourceButton.setBackground(null);
			keyMaHD = "";
			SetTitlePanel("");
			ShowTableDataChiTietHoaDon(keyMaHD);
		}
	}
	
	private void UpdateDataHoaDonGopBan(int status)
	{
		HoaDonDAO dao = new HoaDonDAO();
		ChiTietHoaDonDAO chiTietDAO = new ChiTietHoaDonDAO();
		String message = dao.CheckEdit(keyMaHDGopBan);		
		if(message == "")
		{
			dao.UpdateDataStatus(keyMaHDGopBan, status);	
			chiTietDAO.UpdateDataStatus(keyMaHDGopBan, status);
			sourceButton.setBackground(null);
			keyMaHDGopBan = "";
			SetTitlePanel("");
			ShowTableDataChiTietHoaDon(keyMaHDGopBan);
		}
	}
	
	private void UpdateDataHoaDonChuyenBan(String maBan)
	{
		HoaDonDAO dao = new HoaDonDAO();
		
		String message = dao.CheckEdit(keyMaHD);		
		if(message == "")
		{
			dao.UpdateDataKeyTable(keyMaHD, maBan);			
		}
	}
	
	/**
	 * Insert chi tiet hoa don
	 */
	
	private String InsertDataChiTietHoaDon(String maHD, String maThucDon, String ghiChu, int soLuong, int gia)
    {   	
    	ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();		
		String errMessage = "";		
		Chitiethoadon entity = new Chitiethoadon();
		Date currentDate = new Date();
		try 
		{						
			entity.setMaHd(maHD);
			entity.setMaThucDon(maThucDon);	
			entity.setGhiChu(ghiChu);
			entity.setSoLuong(soLuong);
			entity.setGia(gia);
			entity.setStatus(true);			
			
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
	
		return errMessage;
    } 
	
	/**
	 * update chi tiet hoa don
	 */
	
	private void UpdateDataChiTietHoaDon(String maHD, String maThucDon, int soLuong)
	{
		ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();		
		dao.UpdateDataNumber(maHD, maThucDon, soLuong);
	}
	
	private void UpdateDataChiTietHoaDonGopBan(String maHD)
	{
		ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();	
		for (int i = 0; i < listChiTietHoaDon.size(); i++) {
        	ChiTietHoaDonModel entity = (ChiTietHoaDonModel)listChiTietHoaDon.get(i);
        	dao.UpdateDataMaHD(entity.getiD(), maHD);
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fSell frame = new fSell();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// load bàn
	private void SetPanelPartial(JPanel panel)
	{
		BanDAO dao = new BanDAO();
		listBan = dao.Load();
		HoaDonDAO hdDao = new HoaDonDAO();
		// 4 is the column
		
		panel.setLayout(new GridLayout(0,4,10,10));
	    for (Ban entity : listBan) {
	    	JButton button = null;
	    	button = new JButton(entity.getTenBan());// Gán tên button bằng tên bàn
	    	button.setToolTipText("Bấm vào bàn để đặt thực đơn");
	    	button.setActionCommand(entity.getMaBan()); //gán mã bàn cho button ActionCommand
	    	button.setPreferredSize(new Dimension(100, 40));
	    	if(hdDao.GetStatusTop1(entity.getMaBan()) == 1)
	    		button.setBackground(Color.green);
	    	button.setMargin(new Insets(2, 2, 2, 2));
	    	button.setName(entity.getMaBan());	    	
	    	panel.add(button);
	    	button.addActionListener(this);
	    }
	}
	

	/**
	 * Create the frame.
	 */
	public fSell() {
		setTitle("B\u00E1n h\u00E0ng");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 955, 656);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Danh s\u00E1ch b\u00E0n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.WEST);
		
		SetPanelPartial(panel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout( 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Danh s\u00E1ch th\u1EF1c \u0111\u01A1n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setPreferredSize(new Dimension(400, 300));
		
		JLabel lblNhmThcn = new JLabel("Nhóm thực đơn");
		
		cboNhom = new JComboBox();
		
		JScrollPane scrollPane = new JScrollPane(tblThucDon, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(42)
							.addComponent(lblNhmThcn)
							.addGap(18)
							.addComponent(cboNhom, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(398, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNhmThcn)
						.addComponent(cboNhom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tblThucDon = new JTable();
		
		scrollPane.setViewportView(tblThucDon);
		panel_2.setLayout(gl_panel_2);
		
		pnlChiTietHoaDon = new JPanel();
		pnlChiTietHoaDon.setBorder(new TitledBorder(null, "Th\u1EF1c \u0111\u01A1n \u0111\u00E3 g\u1ECDi", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(pnlChiTietHoaDon, BorderLayout.CENTER);
		pnlChiTietHoaDon.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		pnlChiTietHoaDon.add(panel_4, BorderLayout.CENTER);
		
		JScrollPane scrollPane_1 = new JScrollPane(tblChiTietHoaDon, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JButton btnThanhToan = new JButton("Thanh toán");
		
		btnThanhToan.setMargin(new Insets(2, 15, 2, 15));
		
		JButton btnHuyBan = new JButton("Hủy bàn");
		
		btnHuyBan.setMargin(new Insets(2, 15, 2, 15));
		
		JButton btnChuyenBan = new JButton("Chuyển bàn");
		
		btnChuyenBan.setMargin(new Insets(2, 15, 2, 15));
		
		JButton btnGopBan = new JButton("Gộp bàn");
		
		
		JLabel lblThnhTin = new JLabel("Thành tiền: ");
		
		lblThanhTien = new JLabel("0");
		lblThanhTien.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(btnHuyBan)
							.addGap(29)
							.addComponent(btnChuyenBan)
							.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(lblThnhTin)
									.addGap(29)
									.addComponent(lblThanhTien, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(btnGopBan)
									.addGap(34)
									.addComponent(btnThanhToan)))
							.addContainerGap(502, Short.MAX_VALUE))))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblThnhTin)
						.addComponent(lblThanhTien))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnHuyBan)
						.addComponent(btnChuyenBan)
						.addComponent(btnThanhToan)
						.addComponent(btnGopBan))
					.addContainerGap())
		);
		
		tblChiTietHoaDon = new JTable();
		tblChiTietHoaDon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table =(JTable) e.getSource();
		        Point p = e.getPoint();
		        int row = table.rowAtPoint(p); // lấy vị trí của dòng tại điểm đang click
		        if (e.getClickCount() == 2) {
		            // Goi ham xu ly double click
		        	String maThucDon = (String)table.getValueAt(row, 2);		        			        	
		        	if(keyMaHD != "")		        	
	        		{
		        		ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();
		        		if(dao.CheckEdit(keyMaHD, maThucDon) == "") // Món đang chọn đã có trên bàn thì lấy số lượng đang có - 1
	        			{
		        			int soluong = dao.GetNumber(keyMaHD, maThucDon) - 1; 
		        			UpdateDataChiTietHoaDon(keyMaHD, maThucDon, soluong);
	        			}	
		        		
	        		}		        	
		        	ShowTableDataChiTietHoaDon(keyMaHD);
		        }		        
			}
		});
		scrollPane_1.setViewportView(tblChiTietHoaDon);
		panel_4.setLayout(gl_panel_4);
		
		cboNhom.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{				
					Load_DanhSachThucDon();
				}
				
			}
		});
		
		// form load
		FillcboNhom();
		
		
		// ------ load action ---
		/*
		 * tbl thực đơn click double 	
		 */
		tblThucDon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table =(JTable) e.getSource();
		        Point p = e.getPoint();
		        int row = table.rowAtPoint(p);// lấy vị trí của dòng tại điểm đang click
		        if (e.getClickCount() == 2) {
		            // Goi ham xu ly double click
		        	String maThucDon = (String)table.getValueAt(row, 0);
		        	int gia = (int)table.getValueAt(row, 3);		        	
		        	if(keyMaHD != "")		        	
	        		{
		        		ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();
		        		if(dao.CheckEdit(keyMaHD, maThucDon) == "") // Món đang chọn đã có trên bàn thì lấy số lượng đang có + 1
	        			{
		        			int soluong = dao.GetNumber(keyMaHD, maThucDon) + 1;
		        			UpdateDataChiTietHoaDon(keyMaHD, maThucDon, soluong);
	        			}
		        		else
		        		{
		        			InsertDataChiTietHoaDon(keyMaHD, maThucDon, "", 1, gia);
		        			ShowTableDataChiTietHoaDon(keyMaHD);
	        			}
		        		
	        		}
		        	
		        	ShowTableDataChiTietHoaDon(keyMaHD);
		        }
			}
		});
		
		btnHuyBan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateDataHoaDon(0);
			}
		});
		
		btnThanhToan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateDataHoaDon(2);
			}
		});
		
		btnChuyenBan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sourceButton.getBackground()== Color.green)
				{
					isChuyenBan = true;
					sourceButtonChuyenBan = sourceButton;
					sourceButtonChuyenBan.setBackground(Color.red);
				}
			}
		});
		
		btnGopBan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sourceButton.getBackground()== Color.green)
				{
					isGopBan = true;
					sourceButtonGopBan = sourceButton;	
					GetMaHDWhenClickButtonGopBan(sourceButton.getActionCommand());
					sourceButtonGopBan.setBackground(Color.yellow);
				}
			}
		});
	}
		/*
		 * Thực hiện lệnh trên các button bàn
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg) {
			// TODO Auto-generated method stub	
			sourceButton = (JButton)arg.getSource();
			String name = sourceButton.getName();
			if (name == arg.getActionCommand())
			{
				if(isChuyenBan == true)
				{
					UpdateDataHoaDonChuyenBan(arg.getActionCommand());
					isChuyenBan = false;
					sourceButton.setBackground(Color.green);
					sourceButtonChuyenBan.setBackground(null);
				}
				if(isGopBan == true)
				{
					GetMaHDWhenClickButton(arg.getActionCommand());				
					UpdateDataChiTietHoaDonGopBan(keyMaHD);
					UpdateDataHoaDonGopBan(0);				
					isGopBan = false;
					sourceButton.setBackground(Color.green);
					sourceButtonGopBan.setBackground(null);
				}
				if(sourceButton.getBackground() != Color.green)
				{
					/*int reply = JOptionPane.showConfirmDialog(null, "Mở bàn mới !", "Mở bàn", JOptionPane.YES_NO_OPTION);
			        if (reply == JOptionPane.YES_OPTION) {
			        	String message = InsertDataHoaDon(arg.getActionCommand());			        	
			        	SetTitlePanel(sourceButton.getText());
			        	sourceButton.setBackground(Color.green);
			        	
			        }		     */
					
					String s = (String)JOptionPane.showInputDialog(
                            this,
                            "Mở bàn mới !\n"
                            + "\"Số lượng khách\"",
                            "Mở bàn",
                            JOptionPane.PLAIN_MESSAGE,
                            null,null,"1");			        
			        if ((s != null) && (s.length() > 0)) {			        	
			        	try
			        	{
			        		int numOfCus = Integer.parseInt(s);
			        		String message = InsertDataHoaDon(arg.getActionCommand(), numOfCus);			        	
				        	SetTitlePanel(sourceButton.getText());
				        	sourceButton.setBackground(Color.green);
			        	}
			        	catch(Exception ex)
			        	{
			        		JOptionPane.showMessageDialog(null, "Số lượng khách phải là số !");
			        		return;
			        	}			        	
			        }
			        
				}
				else 
				{
					GetMaHDWhenClickButton(arg.getActionCommand());
					SetTitlePanel(sourceButton.getText());
					
					
				}
				keyMaBan = arg.getActionCommand();
				// click vao bàn sẽ hiển thị chi tiết hoá đơn bàn đang sử dụng
				ShowTableDataChiTietHoaDon(keyMaHD);
			}	
	}	
}
