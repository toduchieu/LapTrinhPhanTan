package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;

import dao.EmployeeDao;
import entity.Employee;
import entity.Account;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmQuanLyNhanVien extends JPanel implements ActionListener,MouseListener,ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txttim;
	private JTextField txthoten;
	private JTextField txtdiachi;
	private JTextField txtsdt;
	private JTable tbl;
	private JLabel lblNewLabel;
	private JComboBox<String> cboGT;
	private JComboBox<String> cboCV;
	private JButton btnLammoi;
	private JButton btnhuy;
	private JButton btnsua;
	private JButton btnthem;
	private JButton btntim;
	private JFrame fMain;
	
	private DefaultTableModel modelNhanVien;
	private JDateChooser datengaysinh;
	private EmployeeDao nhanVienDao;
	private JLabel lblLuong;
	private JTextField txtLuong;
	private List<Employee> dsNV;
	private int currentIndex = 0;

	private Date now;
	private SimpleDateFormat ngay;




	/**
	 * Create the application.
	 */
	public FrmQuanLyNhanVien()throws MalformedURLException, RemoteException, NotBoundException {
		initialize(fMain);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize(JFrame fMain) throws MalformedURLException, RemoteException, NotBoundException {

		String ip ="";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		nhanVienDao =  (EmployeeDao) Naming.lookup("rmi://"+ip+":9999/nhanVienDao");

		 
		frame = new JFrame();
		frame.setBounds(0, 0, 1043, 736);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		IconFontSwing.register(FontAwesome.getIconFont());
		
         ngay = new SimpleDateFormat("dd/MM/yyyy");		
		JPanel pMain = new JPanel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(-62, -132, 1102, 848);
		add(pMain);
		pMain.setLayout(null);
		
		Icon icThem = IconFontSwing.buildIcon(FontAwesome.PLUS, 20, new Color(0, 176, 80));
		Icon icNgay = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, new Color(91, 155, 213));
		Icon icTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.black);
		Icon icLamMoi = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.blue);
		Icon icXoa = IconFontSwing.buildIcon(FontAwesome.TIMES, 20, Color.red);
		Icon icSua = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.darkGray);
		
		JLabel lblQLNV = new JLabel("Quản lý nhân viên ");
		lblQLNV.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblQLNV.setBounds(91, 160, 236, 53);
		pMain.add(lblQLNV);
		
		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTim.setBounds(608, 176, 75, 28);
		pMain.add(lblTim);
		txttim = new JTextField();
		txttim.setColumns(10);
		txttim.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txttim.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txttim.setBounds(709, 174, 228, 32);
		txttim.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(txttim);
		
		btntim = new JButton("Tìm");
		btntim.setToolTipText("Tìm nhân viên");
		btntim.setFont(new Font("Tahoma", Font.BOLD, 15));
		btntim.setBackground(new Color(41, 242, 255));
		btntim.setBounds(949, 176, 108, 32);
		pMain.add(btntim);
		
		JLabel lblhoten = new JLabel("Họ và tên:");
		lblhoten.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblhoten.setBounds(91, 223, 107, 32);
		pMain.add(lblhoten);
		
		txthoten = new JTextField();
		txthoten.setColumns(10);
		txthoten.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txthoten.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txthoten.setBounds(180, 223, 202, 32);
		txthoten.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(txthoten);
		
		JLabel lblsdt = new JLabel("Số điện thoại:");
		lblsdt.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblsdt.setBounds(407, 223, 107, 32);
		pMain.add(lblsdt);
		
		JLabel lblngaysinh = new JLabel("Ngày sinh:");
		lblngaysinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblngaysinh.setBounds(750, 219, 107, 32);
		pMain.add(lblngaysinh);
		
	    datengaysinh = new JDateChooser();
		datengaysinh.setBounds(867, 227, 190, 28);
		datengaysinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datengaysinh.setDateFormatString("dd/MM/yyyy");
		datengaysinh.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		datengaysinh.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(datengaysinh);
		
		JLabel lbldiachi = new JLabel("Địa chỉ:");
		lbldiachi.setFont(new Font("SansSerif", Font.BOLD, 15));
		lbldiachi.setBounds(91, 265, 107, 32);
		pMain.add(lbldiachi);
		
		txtdiachi = new JTextField();
		txtdiachi.setColumns(10);
		txtdiachi.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtdiachi.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txtdiachi.setBounds(180, 265, 202, 33);
		txtdiachi.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(txtdiachi);
		
		JLabel lblgioitinh = new JLabel("Giới tính");
		lblgioitinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblgioitinh.setBounds(407, 265, 107, 32);
		pMain.add(lblgioitinh);
		
		cboGT = new JComboBox<String>();
		cboGT.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboGT.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		cboGT.setBounds(511, 265, 190, 32);
		cboGT.setBorder(new LineBorder(new Color(91, 155, 213)));
		cboGT.addItem("Nam");
		cboGT.addItem("Nữ");
	
		pMain.add(cboGT);
		
		JLabel lblchucvu = new JLabel("Chức vụ");
		lblchucvu.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblchucvu.setBounds(750, 261, 107, 32);
		pMain.add(lblchucvu);
		
		cboCV = new JComboBox<String>();
	//	txtchucvu.setColumns(10);
		cboCV.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboCV.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		cboCV.setBounds(867, 265, 188, 32);
	    cboCV.setBorder(new LineBorder(new Color(91, 155, 213)));
		cboCV.addItem("Quản lý");
		cboCV.addItem("Nhân viên bán hàng");
		pMain.add(cboCV);
		
	    btnthem = new JButton("Thêm");
	    btnthem.setToolTipText("Thêm nhân viên");
		btnthem.setForeground(Color.BLACK);
		btnthem.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnthem.setBackground(Color.CYAN);
		btnthem.setBounds(180, 359, 180, 33);
		pMain.add(btnthem);
		
		txtsdt = new JTextField();
		txtsdt.setColumns(10);
		txtsdt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtsdt.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txtsdt.setBounds(511, 223, 190, 32);
		txtsdt.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(txtsdt);
		
		lblLuong = new JLabel("Lương");
		lblLuong.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblLuong.setBounds(91, 307, 107, 32);
		pMain.add(lblLuong);
		
		txtLuong = new JTextField();
		txtLuong.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtLuong.setColumns(10);
		txtLuong.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txtLuong.setBorder(new LineBorder(new Color(91, 155, 213)));
		txtLuong.setBounds(180, 308, 202, 33);
		pMain.add(txtLuong);
		
		btnsua = new JButton("Sửa");
		btnsua.setToolTipText("Cập nhật nhân viên");
		btnsua.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnsua.setBackground(Color.CYAN);
		btnsua.setBounds(388, 359, 186, 33);
		pMain.add(btnsua);
		
		btnLammoi = new JButton("Làm mới");
		btnLammoi.setToolTipText("Làm mới giao diện");
		btnLammoi.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnLammoi.setBackground(Color.CYAN);
		btnLammoi.setBounds(810, 359, 180, 33);
		pMain.add(btnLammoi);
		
		btnhuy = new JButton("Xóa nhân viên");
		btnhuy.setToolTipText("Xóa nhân viên");
		btnhuy.setForeground(Color.BLACK);
		btnhuy.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnhuy.setBackground(Color.CYAN);
		btnhuy.setBounds(595, 359, 180, 33);
		pMain.add(btnhuy);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(83, 414, 974, 398);
		pMain.add(scrollPane);
		
		String column[] = { "Mã nhân viên","Họ tên","Giới tính", "Ngày sinh","Địa chỉ","Chứ vụ","Số điện thoại","Lương","Trạng Thái"};
		modelNhanVien = new DefaultTableModel(column, 0);
		tbl = new JTable(modelNhanVien);
		scrollPane.setViewportView(tbl);
		
		JTableHeader tbHeader = tbl.getTableHeader();
		tbHeader.setBackground(new Color(131, 179, 226));
		tbHeader.setForeground(Color.CYAN);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		
		tbl.setSelectionBackground(new Color(236, 243, 250));
		tbl.setSelectionForeground(new Color(91, 155, 213));
		tbl.setRowHeight(30);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tbl.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tbl.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
	//	tbl.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbl.getColumnModel().getColumn(0).setPreferredWidth(90);
		tbl.getColumnModel().getColumn(1).setPreferredWidth(150);
		tbl.getColumnModel().getColumn(2).setPreferredWidth(90);
		tbl.getColumnModel().getColumn(3).setPreferredWidth(100);
		tbl.getColumnModel().getColumn(4).setPreferredWidth(350);
		tbl.getColumnModel().getColumn(5).setPreferredWidth(120);
		tbl.getColumnModel().getColumn(6).setPreferredWidth(100);
		tbl.getColumnModel().getColumn(7).setPreferredWidth(100);
		tbl.getColumnModel().getColumn(6).setPreferredWidth(150);
		
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("data\\img\\bg.png"));
		lblNewLabel.setBounds(40, 136, 1052, 712);
		pMain.add(lblNewLabel);
		
		
		
		LocalDate d = LocalDate.now();
		now = new Date(d.getYear()-1900,d.getMonthValue()-1,d.getDayOfMonth());
		datengaysinh.setDate(now);
		
		btntim.setIcon(icTim);
		btnthem.setIcon(icThem);
		btnsua.setIcon(icSua);
		btnLammoi.setIcon(icLamMoi);
		btnhuy.setIcon(icXoa);
		datengaysinh.setIcon((ImageIcon) icNgay);
		
	
		loadData();
		
		

		
		
 
	

		
		//action
		btnLammoi.addActionListener(this);
		btnhuy.addActionListener(this);
		btnthem.addActionListener(this);
		btnsua.addActionListener(this);
		btnhuy.addActionListener(this);
		btntim.addActionListener(this);
		
		txthoten.setText("Nguyễn Thị Mai");
		txtdiachi.setText("TP Hồ Chí Minh");
		txtLuong.setText("5000000");
		txtsdt.setText("0918050354");
		
	    tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row = tbl.getSelectedRow();
				try {
					if(row ==-1)
						return;
					//int row = tbl.getSelectedRow();
//					String temp= modelNhanVien.getValueAt(row, 3).toString();
//					Date date = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(temp);
//					Date date  = (Date) modelNhanVien.getValueAt(row, 3);
					txthoten.setText(modelNhanVien.getValueAt(row, 1).toString());
					txtsdt.setText(modelNhanVien.getValueAt(row, 6).toString());
					Date ngaysinh = null;
					try {
						ngaysinh = ngay.parse(modelNhanVien.getValueAt(row, 3).toString());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					datengaysinh.setDate(ngaysinh);
					
				//	datengaysinh.setDate(new Date(modelNhanVien.getValueAt(row, 3).toString()));
					txtdiachi.setText(modelNhanVien.getValueAt(row, 4).toString());
					cboGT.setSelectedItem(modelNhanVien.getValueAt(row, 2));
					cboCV.setSelectedItem(modelNhanVien.getValueAt(row, 5));
					txtLuong.setText(modelNhanVien.getValueAt(row, 7).toString().replaceAll("[-+.^:,]", ""));
					
				
					
				}catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
     public boolean validata() {
    	String hoten = txthoten.getText();
 		String sdt = txtsdt.getText();
// 		Date ngaySinh = datengaysinh.getDate();
 		String diachi = txtdiachi.getText();
    	String Luong = txtLuong.getText();
 		if(hoten.isEmpty()) {
 			JOptionPane.showMessageDialog(this, "Họ tên không được trống");
 			txthoten.requestFocus();
 			return false;
 		}
 		else if(!hoten.matches("[\\p{Lu}[A-Z]][\\p{L}[a-z]]*(\\s+[\\p{Lu}[A-Z]][\\p{L}[a-z]]*)*")) {
 			JOptionPane.showMessageDialog(this, "Họ tên bắt đầu bằng chữ in hoa, không chứ số và không có ký tự đắc biệt");
 			txthoten.requestFocus();
 			return false;
 		}
 		if(sdt.isEmpty()) {
 			JOptionPane.showMessageDialog(this, "Số điện thoại không được trống");
 			txthoten.requestFocus();
 			return false;
 		}
 		else if(!sdt.matches("^[0][0-9]{9}$")) {
 			JOptionPane.showMessageDialog(this, "Số điện thoại gồm 10 số");
 			txtsdt.requestFocus();
 			return false;
 		}
 		
 		long time = (System.currentTimeMillis() - datengaysinh.getDate().getTime());
		double yearsBetween = time / 3.15576e+10;
		int age = (int) Math.floor(yearsBetween);
		if(age < 18) {
			JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lệ!\nTuổi phải lớn hơn hoặc bằng 18");
			return false;
		}
		
 		if(diachi.isEmpty()) {
 			JOptionPane.showMessageDialog(this, "Địa chỉ không được trống");
 			txthoten.requestFocus();
 		}
 		else if(!diachi.matches("[\\p{Lu}[A-Za-z0-9,.]][\\p{L}[a-z0-9,.]]*(\\s+[\\p{Lu}[A-Za-z0-9,.]][\\p{L}[a-z0-9,.]]*)*")) {
 			JOptionPane.showMessageDialog(this, "Địa chỉ bắt đầu bằng chữ cái hoặc số và không có ký tự đắc biệt");
 			txtdiachi.requestFocus();
 			return false;
 		}
 		
 		double luong = Double.parseDouble(Luong);
 		
 		if(Luong.isEmpty()) {
 			JOptionPane.showMessageDialog(this, "Lương không được để trống!");
 			txtLuong.requestFocus();
 		
 		}
 		else if(luong<0) {
 			JOptionPane.showMessageDialog(this, "Lương không được nhập âm");
 			txtLuong.requestFocus();
 			return false;
 		}
 		
     
	return true;
    	 
     }

	@Override
	public void actionPerformed(ActionEvent e) {
	    Object o = e.getSource();
	    if(o.equals(btnLammoi))
	    {
	    	txttim.setText("");
	    	txtdiachi.setText("");
	    	txthoten.setText("");
	    	txthoten.requestFocus();
	    	txtsdt.setText("");
	    	txtLuong.setText("");
	    	cboGT.setSelectedIndex(0);
	    	cboCV.setSelectedIndex(0); 
	    	datengaysinh.setDate(now);
	    	try {
				loadData();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	taiDuLieuLenBang(dsNV);
	    }
	    if(o.equals(btnthem))
	    {
	    	if(validata()) {
	    	
	    	try {
				themNhanVien();
				taiDuLieuLenBang(dsNV);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
	    	}
	    }
	    if(o.equals(btnsua))
	    {
	    	try {
				updateNhanVien();
				taiDuLieuLenBang(dsNV);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
	    }
	    if(o.equals(btnhuy)) 
	    {
			
	    	try {
				huyTaiKhoan();
				taiDuLieuLenBang(dsNV);
			} catch (RemoteException e1) {
				//e1.printStackTrace();
			}	
		}
	    if(o.equals(btntim)) {

	    	String tim = txttim.getText().trim();
	    	if(tim.isEmpty()) {
	    		JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm");
	    	}else {
	    	try {
	    		loadDataTim();
				taiDuLieuLenBang(dsNV);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}	
	    }
	    	if(modelNhanVien.getRowCount()==0){
	    		JOptionPane.showMessageDialog(this, "Không có kết quả tìm kiếm");
	    	}
	    }
	}
	
	private void loadData() throws RemoteException {
	    dsNV = new ArrayList<Employee>();
		dsNV = nhanVienDao.getDSNhanVien();
		DecimalFormat df = new DecimalFormat("###,###,###.####");
		
		for(Employee nhanVien : dsNV) {
			modelNhanVien.addRow(new Object[] {nhanVien.getMaNhanVien(), nhanVien.getTenNhanVien(),nhanVien.getGioiTinh(),ngay.format(nhanVien.getNgaySinh()),nhanVien.getDiaChi(),nhanVien.getChucVu(),nhanVien.getSdt(),df.format(nhanVien.getLuong()),nhanVien.getTrangThaiLamViec()});	
		}
	}
	private void loadDataTim() throws RemoteException{
		dsNV = new ArrayList<Employee>();
		String text = txttim.getText();
		dsNV = nhanVienDao.getTim(text);
		DecimalFormat df = new DecimalFormat("###,###,###.####");
		DateFormat dfd = new SimpleDateFormat("dd/MM/yyyy");
		for(Employee nhanVien : dsNV) {
			modelNhanVien.addRow(new Object[] {nhanVien.getMaNhanVien(), nhanVien.getTenNhanVien(),nhanVien.getGioiTinh(),dfd.format(nhanVien.getNgaySinh()),nhanVien.getDiaChi(),nhanVien.getChucVu(),nhanVien.getSdt(),df.format(nhanVien.getLuong()),nhanVien.getTrangThaiLamViec()});	
		}
	}
	private void taiDuLieuLenBang(List<Employee> dsNV) {
		modelNhanVien.getDataVector().removeAllElements();
		modelNhanVien.fireTableDataChanged();
		new Thread(() -> {

		  

			for (Employee nhanVien : dsNV) {

				SwingUtilities.invokeLater(() -> {
					DecimalFormat df = new DecimalFormat("###,###,###.####");
					DateFormat dfd = new SimpleDateFormat("dd/MM/yyyy");

					modelNhanVien.addRow(new Object[] { nhanVien.getMaNhanVien(), nhanVien.getTenNhanVien(),
							nhanVien.getGioiTinh(),
							dfd.format(nhanVien.getNgaySinh()), nhanVien.getDiaChi(),
							nhanVien.getChucVu(), nhanVien.getSdt(), df.format(nhanVien.getLuong()),
							nhanVien.getTrangThaiLamViec()});
				});

			}
		}).start();
		;
	}
	public void themNhanVien() throws RemoteException{
		int n= nhanVienDao.soNhanVien();
		int x =n+1;
		String maNV = "NV"+x;
		String hoten = txthoten.getText();
		String sdt = txtsdt.getText();
		Date ngaySinh = datengaysinh.getDate();
		String diachi = txtdiachi.getText();
		String gioiTinh  = cboGT.getSelectedItem().toString();
		String chucvu= cboCV.getSelectedItem().toString();
		String Luong = txtLuong.getText();
		double luong = Double.parseDouble(Luong);
		String trangthai = "Đang làm việc";
        
		Employee nv = new Employee(maNV, hoten, gioiTinh, ngaySinh, sdt, diachi, chucvu, luong,trangthai, new Account(maNV, "123"));
		Employee nvTim = nhanVienDao.getNVTheoSDT(sdt);
		if(nvTim == null) {
		
              if(nhanVienDao.addNhanVien(nv)) {
       
        	         JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công");
        	         loadData();
		           }
		} else 
			JOptionPane.showMessageDialog(this, "Số điện thoại đã được đăng ký");
       	
}

	
	public void updateNhanVien() throws RemoteException{
		int row = tbl.getSelectedRow();
	     // lấy ra toàn thông tin cũ của NV thông qua Mã NV;
		
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên");

			} else {
				
				row += currentIndex;
				String maNV1 = (String) modelNhanVien.getValueAt(row, 0);
				String hoten = txthoten.getText();
				String sdt = txtsdt.getText();
				Date ngaySinh = datengaysinh.getDate();
				String diachi = txtdiachi.getText();
				String gioiTinh  = cboGT.getSelectedItem().toString();
				String chucvu= cboCV.getSelectedItem().toString();
				String Luong = txtLuong.getText();
				double luong = Double.parseDouble(Luong);

				
				Employee nv1 = nhanVienDao.getNhanVienTheoSoNV(maNV1);
				nv1.setTenNhanVien(hoten);
				nv1.setSdt(sdt);
			    nv1.setNgaySinh(ngaySinh);
			    nv1.setDiaChi(diachi);
			    nv1.setGioiTinh(gioiTinh);
			    nv1.setChucVu(chucvu);
			    nv1.setLuong(luong);
				
		        nhanVienDao.updateNhanVien(nv1) ;
		        JOptionPane.showMessageDialog(this, "Sửa thành công");
		        loadData();
			}
	}
	public void huyTaiKhoan()throws RemoteException{
	 
		    int row = tbl.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên");
                
			} else {
				
				row += currentIndex;
			
				String maNV2 = (String) modelNhanVien.getValueAt(row, 0);
				String trangThai= "Đã nghỉ việc";
				Employee nv2 = nhanVienDao.getNhanVienTheoSoNV(maNV2);
				
			    nv2.setTrangThaiLamViec(trangThai);
				int click= JOptionPane.showConfirmDialog(this, "Bạn muốn hủy tài khoản này","Cảnh báo", JOptionPane.YES_NO_OPTION);
				if(click== JOptionPane.YES_OPTION) {
		        nhanVienDao.updateNhanVien(nv2) ;
		        JOptionPane.showMessageDialog(this, "Hủy thành công");
		        loadData();
				}else
					return;
			}
	}
	
}
