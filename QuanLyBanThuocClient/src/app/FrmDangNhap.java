 package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import dao.EmployeeDao;
import dao.AccountDao;
import entity.Employee;
import entity.Account;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmDangNhap extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4420857605153751161L;
	private JTextField txttendangnhap;
	private JPasswordField txtmatkhau;
	private JButton btndangnhap;
	private JButton btnthoat;
	private EmployeeDao nhanVienDao;
	private AccountDao tkDao;
	
	private Account taiKhoan=null;
	private Employee nhanVien=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					FrmDangNhap window = new FrmDangNhap();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public FrmDangNhap() throws MalformedURLException, RemoteException, NotBoundException {
		
		setResizable(false);
		setTitle("Hệ thống quản lý hiệu sách");
		setBounds(100, 100, 650, 391);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		


		IconFontSwing.register(FontAwesome.getIconFont());

//		String ip ="localhost";
//		try {
//			ip = InetAddress.getLocalHost().getHostAddress();
//		} catch (UnknownHostException e1) {
//			e1.printStackTrace();
//		}
		
		
		nhanVienDao =  (EmployeeDao) Naming.lookup("rmi://localhost:9999/employeeDao");
		tkDao =  (AccountDao) Naming.lookup("rmi://localhost:9999/accountDao");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 0, 644, 371);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblhethong = new JLabel("Hệ thống quản lý hiệu sách");
		lblhethong.setIcon(new ImageIcon("C:\\Users\\Tai\\eclipse-workspace\\HeThongQuanLyThuoc\\img\\tieude2.png"));
		lblhethong.setFont(new Font("Dialog", Font.BOLD, 25));
		lblhethong.setBounds(30, 11, 325, 116);
		panel_1.add(lblhethong);
		
//		JLabel txtquenmk = new JLabel("Quên mật khẩu?");
//		txtquenmk.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				try {
//					FrmQuenMatKhau frmQuenMatKhau=new FrmQuenMatKhau();
//					frmQuenMatKhau.setVisible(true);
//					frmQuenMatKhau.setResizable(false);
//					frmQuenMatKhau.setTitle("Hệ thống quản lý hiệu sách");
//					frmQuenMatKhau.setBounds(100, 100, 650, 391);
////					frmHThngQun.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//					frmQuenMatKhau.setLocationRelativeTo(null);
//					setVisible(false);
//				} catch (MalformedURLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (RemoteException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (NotBoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//			}
//		});
//		txtquenmk.setFont(new Font("SansSerif", Font.ITALIC, 12));
//		txtquenmk.setBounds(536, 202, 108, 15);
//		panel_1.add(txtquenmk);
		
		JLabel lbldangnhap = new JLabel("Đăng nhập");
		lbldangnhap.setFont(new Font("SansSerif", Font.BOLD, 25));
		lbldangnhap.setBounds(449, 31, 131, 31);
		panel_1.add(lbldangnhap);
		
		JLabel lbltendangnhap = new JLabel("Tên đăng nhập");
		lbltendangnhap.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lbltendangnhap.setBounds(394, 103, 108, 24);
		panel_1.add(lbltendangnhap);
		
		JLabel lblmatkhau = new JLabel("Mật khẩu");
		lblmatkhau.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblmatkhau.setBounds(394, 163, 61, 21);
		panel_1.add(lblmatkhau);
		
		txttendangnhap = new JTextField();
		txttendangnhap.setBounds(498, 102, 136, 31);
		txttendangnhap.setBorder(new LineBorder(new Color(91, 155, 213)));
		
       
		panel_1.add(txttendangnhap);
		txttendangnhap.setColumns(10);
		
		txtmatkhau = new JPasswordField();
		txtmatkhau.setBounds(497, 160, 137, 31);
		panel_1.add(txtmatkhau);
		txtmatkhau.setColumns(10);
		txtmatkhau.setBorder(new LineBorder(new Color(91, 155, 213)));
		
		 btndangnhap = new JButton("Đăng nhập");
		 btndangnhap.setToolTipText("Đăng nhập vào hệ thống");
		btndangnhap.setForeground(new Color(255, 255, 255));
		btndangnhap.setFont(new Font("SansSerif", Font.BOLD, 15));
		btndangnhap.setBackground(new Color(0, 153, 255));
		btndangnhap.setBounds(394, 228, 240, 39);
		btndangnhap.addActionListener(this);
		
		panel_1.add(btndangnhap);
		
		 btnthoat = new JButton("Thoát");
		 btnthoat.setToolTipText("Đóng ứng dụng");
		btnthoat.setForeground(new Color(255, 255, 255));
		btnthoat.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnthoat.setBackground(new Color(0, 153, 255));
		btnthoat.addActionListener(this);
		btnthoat.setBounds(394, 278, 240, 39);
		panel_1.add(btnthoat);
		

	
		Icon icDangnhap = IconFontSwing.buildIcon(FontAwesome.SIGN_IN, 30, Color.yellow);
		Icon icThoat = IconFontSwing.buildIcon(FontAwesome.POWER_OFF, 30, Color.red);
		Icon icLogo = IconFontSwing.buildIcon(FontAwesome.HOSPITAL_O, 17, Color.orange);
		ImageIcon imgImage = (ImageIcon) icLogo;
		setIconImage(imgImage.getImage());
		btndangnhap.setIcon(icDangnhap);
		btnthoat.setIcon(icThoat);
		
		
		
		JLabel lblbg = new JLabel("");
		lblbg.setIcon(new ImageIcon("data\\img\\bg3.jpg"));
		lblbg.setBounds(0, 0, 384, 361);
		panel_1.add(lblbg);
		
		
	
	}
//	private static class RoundedBorder implements Border {
//
//	    private int radius;
//
//
//	    RoundedBorder(int radius) {
//	        this.radius = radius;
//	    }
//
//
//	    public Insets getBorderInsets(Component c) {
//	        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
//	    }
//
//
//	    public boolean isBorderOpaque() {
//	        return true;
//	    }
//
//
//	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
//	        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
//	    }
//	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object o=e.getSource();
		if(o.equals(btnthoat)) {
			System.exit(0);
		}
		if(o.equals(btndangnhap)) {
			String tendangnhap=txttendangnhap.getText().trim();
			@SuppressWarnings("deprecation")
			String mk=txtmatkhau.getText().trim();
			
			try {
				taiKhoan = tkDao.findAccountByName(tendangnhap);
				 nhanVien=nhanVienDao.getEmployeeByEmployeeId(tendangnhap);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(taiKhoan==null) {
				JOptionPane.showMessageDialog(null, "Đăng nhập không thành công");
			}
			else {
				if(mk.equals(taiKhoan.getPassword())&&nhanVien.getStatus().equalsIgnoreCase("Đang làm việc")) {
					
						try {
							FrmMain frmMain=new FrmMain(tendangnhap,nhanVien.getPosition());
							frmMain.setVisible(true);
							frmMain.setResizable(false);
							frmMain.setBounds(0, 0, 1285, 700);
							frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							frmMain.setLocationRelativeTo(null);
						
							
							
							setVisible(false);
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NotBoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					
				}
				else if(mk.equals(taiKhoan.getPassword())&&nhanVien.getStatus().equalsIgnoreCase("Đã nghỉ việc")) {
					JOptionPane.showMessageDialog(null, "Tài khoản này không còn hoạt động");
				}
				else {
					JOptionPane.showMessageDialog(null, "Đăng nhập không thành công");
				}
			}
			
				
			
			
		}
		
	}
}
