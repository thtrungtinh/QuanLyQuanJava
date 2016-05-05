package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	private JTextField txtMaTrinhDo;
	private JTextField txtTenTrinhDo;
	private JTextPane txtDienGiai;
	private JCheckBox chkStatus;
	private String maNguoiDung;
	private DefaultTableModel model;
	private JTable tblIndex;
	
       
    public void ShowTableData() 
    {
    	System.out.println("--- Loading ---");
    	TrinhDoDAO dao = new TrinhDoDAO();
    	model = new DefaultTableModel(){
    		@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
    	};
    	
        //Set Column Title
    	Vector column = new Vector();
        column.add("Mã trình độ");
        column.add("Trình độ");
        column.add("Diễn Giải");
        column.add("Sử dụng");
        model.setColumnIdentifiers(column);
        List<Trinhdo> list = dao.Load();
        for (int i = 0; i < list.size(); i++) {
        	Trinhdo entity = (Trinhdo)list.get(i);
        	Vector row = new Vector();
            row.add(entity.getMaTrinhDo());
            row.add(entity.getTenTrinhDo());
            row.add(entity.getDienGiai());
            row.add((Boolean)entity.isStatus());
            
            model.addRow(row);
        }
        
        tblIndex.setModel(model);
        TableColumn tc = tblIndex.getColumnModel().getColumn(3);
        tc.setCellRenderer( tblIndex.getDefaultRenderer( Boolean.class ) );
        tc.setCellEditor( tblIndex.getDefaultEditor( Boolean.class ) );
    	System.out.println("--- Success ---");
	}
    
    private String EditData(String maViTri, String tenViTri, String dienGiai, boolean status)
    {
    	String errMessage = "";
    	try {
    		TrinhDoDAO dao = new TrinhDoDAO();
			errMessage = dao.CheckEdit(maViTri);
			if(errMessage.length()<1)
			{
				dao.UpdateData(maViTri, tenViTri, dienGiai, status);
				errMessage = "Cập nhật thành công !";
			}			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e);
		}
    	return errMessage;
    }
    
    private String DeleteData(String maViTri)
    {
    	String errMessage = "";
    	try {
			TrinhDoDAO dao = new TrinhDoDAO();
			errMessage = dao.CheckDelete(maViTri);
			if(errMessage.length()<1)
			{
				dao.Delete(maViTri);
				errMessage = "Xóa thành công !";
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
    	return errMessage;    	
    }
    
    private String InsertData()
    {
    	
		TrinhDoDAO dao = new TrinhDoDAO();
		String errMessage = dao.CheckInsert(txtMaTrinhDo.getText());
		if(errMessage.length()<1)
		{
			Trinhdo entity = new Trinhdo();
			Date currentDate = new Date();
			try
			{
				entity.setMaTrinhDo(txtMaTrinhDo.getText());
				entity.setTenTrinhDo(txtTenTrinhDo.getText());
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
		setTitle("Trình độ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 673, 294);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Th\u00F4ng tin", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 271, 227);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mã trình độ");
		lblNewLabel.setBounds(21, 29, 72, 14);
		panel.add(lblNewLabel);
		
		txtMaTrinhDo = new JTextField();
		txtMaTrinhDo.setBounds(91, 26, 64, 20);
		panel.add(txtMaTrinhDo);
		txtMaTrinhDo.setColumns(10);
		
		JLabel lblVTr = new JLabel("Trình độ");
		lblVTr.setBounds(21, 61, 46, 14);
		panel.add(lblVTr);
		
		txtTenTrinhDo = new JTextField();
		txtTenTrinhDo.setColumns(10);
		txtTenTrinhDo.setBounds(91, 58, 159, 20);
		panel.add(txtTenTrinhDo);
		
		chkStatus = new JCheckBox("Sử dụng");
		chkStatus.setSelected(true);
		chkStatus.setBounds(178, 25, 87, 23);
		panel.add(chkStatus);
		
		JLabel lblDinGii = new JLabel("Diễn giải");
		lblDinGii.setBounds(21, 113, 53, 14);
		panel.add(lblDinGii);
		
		txtDienGiai = new JTextPane();
		txtDienGiai.setBounds(91, 89, 159, 68);
		panel.add(txtDienGiai);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setMargin(new Insets(2, 5, 2, 5));
		
		btnThem.setBounds(10, 190, 64, 23);
		panel.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		
		btnSua.setBounds(94, 190, 64, 23);
		panel.add(btnSua);
		
		JButton btnXoa = new JButton("Xóa");
		
		btnXoa.setBounds(173, 190, 64, 23);
		panel.add(btnXoa);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Danh s\u00E1ch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(291, 11, 356, 227);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		tblIndex = new JTable();
		tblIndex.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {				
					if (e.getSource() == tblIndex) {
			            int iDongDaChon = tblIndex.getSelectedRow();
			            if (iDongDaChon != -1) {
			            	
			                String maViTri = tblIndex.getValueAt(iDongDaChon, 0).toString();
			                String tenViTri = tblIndex.getValueAt(iDongDaChon, 1).toString();
			                String dienGiai = tblIndex.getValueAt(iDongDaChon, 2).toString();
			                Boolean isStatus = (Boolean) tblIndex.getValueAt(iDongDaChon, 3);		            	
			            	
			                txtMaTrinhDo.setText(maViTri);
			                txtTenTrinhDo.setText(tenViTri);
			                txtDienGiai.setText(dienGiai);
			                chkStatus.setSelected(isStatus);
			            }
			        }
				} catch (Exception ex) {
					System.out.println("Error: " + ex);
				}
			}
		});
		tblIndex.setPreferredSize(new Dimension(500, 500));
		scrollPane.setViewportView(tblIndex);		
		
		ShowTableData();
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
							String errMessage = DeleteData(txtMaTrinhDo.getText());
							ShowTableData();
							JOptionPane.showMessageDialog(null, errMessage);
						}
			}
		});
		btnSua.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				String errMessage = EditData(txtMaTrinhDo.getText(), txtTenTrinhDo.getText(), txtDienGiai.getText(), chkStatus.isSelected());
				ShowTableData();
				JOptionPane.showMessageDialog(null, errMessage);
			}
		});
	}
}
