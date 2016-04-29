package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class fMain extends JFrame {

private JPanel contentPane;

	
	private String maNguoiDung;
	public void LoadTitle(String tenNguoiDung) {
		this.setTitle(this.getTitle() + " - " + tenNguoiDung);
	}
	
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
					fMain frame = new fMain();
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
	public fMain() {
		setTitle("Qu\u1EA3n l\u00FD qu\u00E1n \u0103n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 534);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnHThng = new JMenu("H\u1EC7 th\u1ED1ng");
		menuBar.add(mnHThng);
		
		JMenuItem mntmQunLNgi = new JMenuItem("Qu\u1EA3n l\u00FD ng\u01B0\u1EDDi d\u00F9ng");
		mnHThng.add(mntmQunLNgi);
		
		JMenuItem mntmThot = new JMenuItem("Tho\u00E1t");
		mnHThng.add(mntmThot);
		
		JMenu mnDanhMc = new JMenu("Danh m\u1EE5c");
		menuBar.add(mnDanhMc);
		
		JMenuItem mnItemViTri = new JMenuItem("V\u1ECB tr\u00ED");
		mnItemViTri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mở Danh mục vị trí");
				fIndex view = new fIndex();
				view.SetUser(maNguoiDung);
				view.setVisible(true);
			}
		});
		mnDanhMc.add(mnItemViTri);
		
		JMenuItem mntmTrnh = new JMenuItem("Tr\u00ECnh \u0111\u1ED9");
		mnDanhMc.add(mntmTrnh);
		
		JMenuItem mntmCaLmVic = new JMenuItem("Ca l\u00E0m vi\u1EC7c");
		mnDanhMc.add(mntmCaLmVic);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar.add(menuBar_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
	}

}
