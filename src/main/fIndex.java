package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.hibernate.*;
import entities.*;
import dao.*;

public class fIndex extends JFrame {

	private JPanel contentPane;
	private JTextField txtMaViTri;
	private JTextField txtTenViTri;
	private JTable table;
	private String maNguoiDung;
	
	SessionFactory factory = HibernateUtil.getSessionFactory();	 
    Session session = factory.getCurrentSession();
    
    public void SetUser( String maNguoiDung)
	{
		this.maNguoiDung = maNguoiDung;
	}
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fIndex frame = new fIndex();
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
	public fIndex() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Thoát Danh mục vị trí");
			}
		});
		setTitle("V\u1ECB tr\u00ED");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 673, 294);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Th\u00F4ng tin", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 258, 227);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mã vị trí");
		lblNewLabel.setBounds(21, 31, 46, 14);
		panel.add(lblNewLabel);
		
		txtMaViTri = new JTextField();
		txtMaViTri.setBounds(78, 28, 64, 20);
		panel.add(txtMaViTri);
		txtMaViTri.setColumns(10);
		
		JLabel lblVTr = new JLabel("Vị trí");
		lblVTr.setBounds(21, 63, 46, 14);
		panel.add(lblVTr);
		
		txtTenViTri = new JTextField();
		txtTenViTri.setColumns(10);
		txtTenViTri.setBounds(78, 60, 159, 20);
		panel.add(txtTenViTri);
		
		JCheckBox chkStatus = new JCheckBox("Sử dụng");
		chkStatus.setSelected(true);
		chkStatus.setBounds(165, 27, 87, 23);
		panel.add(chkStatus);
		
		JLabel lblDinGii = new JLabel("Diễn giải");
		lblDinGii.setBounds(21, 113, 53, 14);
		panel.add(lblDinGii);
		
		JTextPane txtDienGiai = new JTextPane();
		txtDienGiai.setBounds(78, 91, 159, 68);
		panel.add(txtDienGiai);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vitri vitri = new Vitri();
				Date currentDate = new Date();
				try {
					
					session.getTransaction().begin();
					vitri.setMaViTri(txtMaViTri.getText());
					vitri.setTenViTri(txtTenViTri.getText());
					vitri.setDienGiai(txtDienGiai.getText());
					vitri.setStatus(chkStatus.isSelected());
					vitri.setUpdatedBy(maNguoiDung);
					vitri.setCreatedBy(maNguoiDung);
					vitri.setUpdatedDate(currentDate);
					vitri.setCreatedDate(currentDate);
					session.save(vitri);
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error: "+ e);
					session.getTransaction().rollback();
				}
			}
		});
		btnThem.setBounds(10, 190, 64, 23);
		panel.add(btnThem);
		
		JButton btnSa = new JButton("Sửa");
		btnSa.setBounds(94, 190, 64, 23);
		panel.add(btnSa);
		
		JButton btnXa = new JButton("Xóa");
		btnXa.setBounds(173, 190, 64, 23);
		panel.add(btnXa);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Danh s\u00E1ch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(291, 11, 356, 227);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ng\u01B0\u1EDDi t\u1EA1o", "S\u1EED d\u1EE5ng", "Di\u1EC5n gi\u1EA3i", "V\u1ECB tr\u00ED", "M\u00E3 v\u1ECB tr\u00ED", "Ng\u00E0y t\u1EA1o"
			}
		));
		panel_1.add(table, BorderLayout.CENTER);
		
	}
}
