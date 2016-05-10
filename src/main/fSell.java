package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JToggleButton;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.UIManager;
import entities.*;
import dao.*;

public class fSell extends JFrame implements ActionListener{

	private JPanel contentPane;
	private List<Ban> listBan;

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
	
	private void SetPanelPartial(JPanel panel)
	{
		BanDAO dao = new BanDAO();
		listBan = dao.Load();
		
		int row = listBan.size()/4; // 4 is the column
		
		panel.setLayout(new GridLayout(row,5,10,10));
	    for (Ban entity : listBan) {
	    	JButton button = null;
	    	button = new JButton(entity.getTenBan());
	    	button.setToolTipText("Bấm vào bàn để đặt thực đơn");
	    	button.setActionCommand(entity.getMaBan());
	    	button.setPreferredSize(new Dimension(100, 40));
	    	button.setMargin(new Insets(2, 2, 2, 2));
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
		setBounds(100, 100, 694, 656);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Danh s\u00E1ch b\u00E0n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.WEST);
		
		SetPanelPartial(panel);
	}

	@Override
	public void actionPerformed(ActionEvent arg) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, arg.getActionCommand());
	}

}
