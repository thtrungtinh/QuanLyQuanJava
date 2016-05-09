package main;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import utilities.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class fMain extends JFrame {

private JPanel contentPane;
	
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fMain frame = new fMain();					
					frame.setVisible(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					frame.setTitle("Qu\u1EA3n l\u00FD qu\u00E1n \u0103n - " + DataService.GetUserName());
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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Thoát màn hình chính");
			}
		});
		setTitle("Qu\u1EA3n l\u00FD qu\u00E1n \u0103n - " + DataService.GetUserName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 534);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnHThng = new JMenu("H\u1EC7 th\u1ED1ng");
		menuBar.add(mnHThng);
		
		JMenuItem mntmQunLNgi = new JMenuItem("Qu\u1EA3n l\u00FD ng\u01B0\u1EDDi d\u00F9ng");
		mntmQunLNgi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				fUsers view = new fUsers();	
				view.setLocationRelativeTo(null);
				view.setVisible(true);
			}
		});
		mnHThng.add(mntmQunLNgi);
		
		JMenuItem mntmThot = new JMenuItem("Tho\u00E1t");
		mnHThng.add(mntmThot);
		
		JMenu mnDanhMc = new JMenu("Danh m\u1EE5c");
		menuBar.add(mnDanhMc);
		
		JMenuItem mnItemViTri = new JMenuItem("V\u1ECB tr\u00ED");
		mnItemViTri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mở danh mục vị trí");
				fIndex view = new fIndex();	
				view.setLocationRelativeTo(null);
				view.setVisible(true);				
			}
		});
		mnDanhMc.add(mnItemViTri);
		
		JMenuItem mntmTrnh = new JMenuItem("Tr\u00ECnh \u0111\u1ED9");
		mntmTrnh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Mở danh mục Trình Độ");
				fLevel view = new fLevel();	
				view.setLocationRelativeTo(null);
				view.setVisible(true);		
			}
		});
		mnDanhMc.add(mntmTrnh);
		
		JMenuItem mntmCaLmVic = new JMenuItem("Ca l\u00E0m vi\u1EC7c");
		mntmCaLmVic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Mở danh mục Ca làm việc");
				fShift view = new fShift();	
				view.setLocationRelativeTo(null);
				view.setVisible(true);
			}
		});
		mnDanhMc.add(mntmCaLmVic);
		
		JMenuItem mntmBn = new JMenuItem("Bàn");
		mntmBn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mở danh mục Bàn");
				fTable view = new fTable();	
				view.setLocationRelativeTo(null);
				view.setVisible(true);
			}
		});
		mnDanhMc.add(mntmBn);
		
		JMenuItem mntmNhmThcn = new JMenuItem("Nhóm thực đơn");
		mntmNhmThcn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Mở danh mục Nhóm thực đơn");
				fGroupMenu view = new fGroupMenu();	
				view.setLocationRelativeTo(null);
				view.setVisible(true);
			}
		});
		mnDanhMc.add(mntmNhmThcn);
		
		JMenuItem mntmThcn = new JMenuItem("Thực đơn");
		mntmThcn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mở danh mục thực đơn");
				fMenu view = new fMenu();	
				view.setLocationRelativeTo(null);
				view.setVisible(true);
			}
		});
		mnDanhMc.add(mntmThcn);
		
		JMenu mnNghipV = new JMenu("Nghiệp vụ");
		menuBar.add(mnNghipV);
		
		JMenuItem mntmThiGianLm = new JMenuItem("Thời gian làm việc");
		mntmThiGianLm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Mở nghiệp vụ -> Thời gian làm việc");
				fTimeCanWork view = new fTimeCanWork();	
				view.setLocationRelativeTo(null);
				view.setVisible(true);
			}
		});
		mnNghipV.add(mntmThiGianLm);
		
		JMenuItem mntmChmCng = new JMenuItem("Chấm công");
		mntmChmCng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Mở nghiệp vụ -> Thời gian làm việc");
				fTimekeeping view = new fTimekeeping();	
				view.setLocationRelativeTo(null);
				view.setVisible(true);
			}
		});
		mnNghipV.add(mntmChmCng);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar.add(menuBar_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
	}

}
