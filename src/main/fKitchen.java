package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class fKitchen extends JFrame {

	private JPanel contentPane;
	private JTable tblNhaBep;
	private DefaultTableModel model;
	private List<BanModel> listBan;
	private List<HoaDonModel> listHoaDon;
	private List<NhaBepModel> listNhaBep;
	Timer timer;
    
	class CrunchifyReminder extends TimerTask {		
		public void run() {			
			ShowTable();
			System.out.format("Post back..!\n");				
		}
	}
	
	public void ShowTable( ) 
    {				
    	System.out.println("--- Loading ---");
    	NhaBepDAO dao = new NhaBepDAO();
    	model = new DefaultTableModel(){
    		@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return column == 6 || column == 7;
            }
    	};
    	
        //Set Column Title
    	Vector column = new Vector();
        
    	column.add("ID");
    	column.add("Mã HD");
    	column.add("Mã Thực đơn");
    	column.add("Bàn");
        column.add("Thực đơn");
        column.add("Số lượng");	        
        
        column.add("Còn hàng");
        column.add("Đã làm xong");
        
        model.setColumnIdentifiers(column);
        listNhaBep = dao.GetList();
        for (int i = 0; i < listNhaBep.size(); i++) {
        	NhaBepModel entity = (NhaBepModel)listNhaBep.get(i);
        	Vector row = new Vector();
        	row.add(entity.getiD());
        	row.add(entity.getMaHD());
        	row.add(entity.getMaThucDon());
        	row.add(entity.getTenBan());
        	row.add(entity.getTenThucDon());	        	
        	row.add(entity.getSoLuong());     	
        	
        	row.add(entity.isStock());
        	row.add(entity.isDone());
            model.addRow(row);
        }
        
        tblNhaBep.setModel(model);	
        DataService.SetVisibleColumnTable(tblNhaBep, 0);
        DataService.SetVisibleColumnTable(tblNhaBep, 1);
        DataService.SetVisibleColumnTable(tblNhaBep, 2);        
        DataService.SetWidhtColumnTable(tblNhaBep, 3, 110); 
        DataService.SetWidhtColumnTable(tblNhaBep, 4, 170); 
        DataService.SetWidhtColumnTable(tblNhaBep, 5, 80); 
        DataService.SetWidhtColumnTable(tblNhaBep, 6, 80); 
        DataService.SetWidhtColumnTable(tblNhaBep, 7, 80);
        DataService.SetColumnTableToCheckBox(tblNhaBep, 6);
        DataService.SetColumnTableToCheckBox(tblNhaBep, 7);
        
        
    	System.out.println("--- Success ---");
	} 	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fKitchen frame = new fKitchen();
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
	public fKitchen() {
				
		timer = new Timer();
		timer.schedule(new CrunchifyReminder(), 0, // initial delay
				10 * 1000); // subsequent rate
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				timer.cancel();
				System.out.println("Thoát nghiệp vụ Nhà bếp");
			}
		});
		
		setTitle("Nhà bếp");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 562);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Danh sách đặt thực đơn", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_2, BorderLayout.CENTER);
		
		JScrollPane scrollPaneChiTietHoaDon = new JScrollPane(tblNhaBep, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneChiTietHoaDon, GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(scrollPaneChiTietHoaDon, GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tblNhaBep = new JTable();
		tblNhaBep.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				JTable table =(JTable) evt.getSource();
				int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (col == 6) {		            
		            NhaBepDAO dao = new NhaBepDAO();
		            dao.UpdateDataStock((int)table.getValueAt(row, 0), (boolean)table.getValueAt(row, 6));
		        }
		        if(col == 7){
		        	NhaBepDAO dao = new NhaBepDAO();
		            dao.UpdateDataDone((int)table.getValueAt(row, 0), (boolean)table.getValueAt(row, 7));
		        }
			}
		});
		scrollPaneChiTietHoaDon.setViewportView(tblNhaBep);
		panel_2.setLayout(gl_panel_2);
		
		
	}
}
