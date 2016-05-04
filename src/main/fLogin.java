package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import entities.*;
import dao.*;
import utilities.*;

public class fLogin extends JFrame {

	private JPanel contentPane;
	private JTextField txtMaNguoiDung;
	private JPasswordField txtMatKhau;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fLogin frame = new fLogin();
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
	public fLogin() {
		setTitle("Đăng nhập");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 417, 173);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tên đăng nhập");
		lblNewLabel.setBounds(24, 16, 86, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mật khẩu");
		lblNewLabel_1.setBounds(58, 51, 52, 16);
		contentPane.add(lblNewLabel_1);
		
		txtMaNguoiDung = new JTextField();
		txtMaNguoiDung.setBounds(143, 13, 229, 22);
		contentPane.add(txtMaNguoiDung);
		txtMaNguoiDung.setColumns(10);
		
		txtMatKhau = new JPasswordField();
		txtMatKhau.setBounds(143, 48, 229, 22);
		contentPane.add(txtMatKhau);
		
		JButton btnLogin = new JButton("Đăng nhập");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String maNguoiDung = txtMaNguoiDung.getText();		
				NguoiDungDAO nguoiDungDAO  = new NguoiDungDAO();
				
				Nguoidung entity = nguoiDungDAO.GetListID(maNguoiDung, new String(txtMatKhau.getPassword()));
				if(entity != null)
				{			
					DataService.SetUserID(maNguoiDung);
					DataService.SetUserName(entity.getTenNguoiDung());
					
					fMain view = new fMain();									
					view.setVisible(true);
					setVisible(false);
					System.out.println("Đăng nhập thành công !" + maNguoiDung);
				}
				else {
					JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không đúng !");
				}
				
			}
		});
		btnLogin.setBounds(143, 83, 229, 31);
		contentPane.add(btnLogin);
	}
}
