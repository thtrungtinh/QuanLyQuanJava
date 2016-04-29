package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.BoxLayout;

public class fMain extends JFrame {

	private JPanel contentPane;

	
	public void LoadTitle(String tenNguoiDung) {
		this.setTitle(this.getTitle() + " " + tenNguoiDung);
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
		
		JMenuItem mntmVTr = new JMenuItem("V\u1ECB tr\u00ED");
		mnDanhMc.add(mntmVTr);
		
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
