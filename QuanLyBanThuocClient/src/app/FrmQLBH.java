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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;

import dao.OrderDao;
import dao.CustomerDao;
import dao.TypeOfBookDao;
import dao.EmployeeDao;
import dao.BookDao;
import entity.OrderDetail;
import entity.Order;
import entity.Customer;
import entity.TypeOfBook;
import entity.Employee;
import entity.Book;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;


public class FrmQLBH extends JPanel implements ActionListener,MouseListener,ItemListener {

	/**
	 * 
	 */
	private JFrame fMain;
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txtTimKiem;
	private JTextField txtTenKH;
	private JTextField txtSDT;
	private JTextField txtSoLuong;
	private DefaultTableModel modelThuoc;
	private JTable tblThuoc;
	private JButton btnDSKH;
	private JDateChooser chooserNgaySinh;
	
	private JComboBox<String> cboLoaiThuoc;
	private JComboBox<String> cboTenThuoc;
	private OrderDao hoaDonDao;
	private CustomerDao khachHangDao;
	private TypeOfBookDao loaiThuocDao;
	
	private EmployeeDao nhanVienDao;
	private BookDao thuocDao;
	private JButton btnTim;
	private JComboBox<String> cboGioiTinh;
	private JButton btnThemKH;
	private JButton btnLamMoiKH;
	private JRadioButton rdoGiamSL;
	private JButton btnThemThuoc;
	private JButton btnXoaThuoc;
	private JButton btnLamMoiGD;
	private JLabel lblThanhTien;
	private JButton btnThanhToan;
	private Date now;
	private DecimalFormat dfTable;
	private DecimalFormat df;
	private Regex regex;
	private DefaultTableModel modelKH;
	private JTable tblKH;
	private JButton btnSua;
	private SimpleDateFormat sf;
	private String maNV;



	/**
	 * Create the application.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */


	public FrmQLBH(JFrame fMain,String maNV) throws MalformedURLException, RemoteException, NotBoundException {
		this.fMain = fMain;
		this.maNV = maNV;
		initialize(fMain);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	@SuppressWarnings("deprecation")
	public void initialize(JFrame fMain) throws MalformedURLException, RemoteException, NotBoundException {



		String ip ="";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}


		hoaDonDao =  (OrderDao) Naming.lookup("rmi://"+ip+":9999/hoaDonDao");
		khachHangDao = (CustomerDao) Naming.lookup("rmi://"+ip+":9999/khachHangDao");
		loaiThuocDao =  (TypeOfBookDao) Naming.lookup("rmi://"+ip+":9999/loaiThuocDao");
		nhanVienDao =  (EmployeeDao) Naming.lookup("rmi://"+ip+":9999/nhanVienDao");
		thuocDao =  (BookDao) Naming.lookup("rmi://"+ip+":9999/thuocDao");

		 regex  = new Regex();
	
		
		IconFontSwing.register(FontAwesome.getIconFont());
		
		frame = new JFrame();
		frame.setBounds(0, 0, 1031, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);


		JPanel pMain = new JPanel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1031, 700);
		add(pMain);
		pMain.setLayout(null);

		JLabel lblQLBH = new JLabel("Quản lý bán hàng");
		lblQLBH.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblQLBH.setBounds(10, 32, 209, 33);
		pMain.add(lblQLBH);

		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTim.setBounds(576, 43, 90, 18);
		pMain.add(lblTim);

		txtTimKiem = new JTextField();
		txtTimKiem.setBackground(Color.WHITE);
		txtTimKiem.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtTimKiem.setBounds(657, 36, 252, 33);
		pMain.add(txtTimKiem);
		txtTimKiem.setBorder(new LineBorder(new Color(91, 155, 213)));
		txtTimKiem.setColumns(10);

		btnTim = new JButton("Tìm");
		btnTim.setBackground(new Color(41, 242, 255));
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnTim.setBounds(918, 35, 89, 35);
		pMain.add(btnTim);
		
		

		JPanel pKH = new JPanel();
		pKH.setToolTipText("Nhập thông tin của khách hàng");
		pKH.setBorder(new TitledBorder(new LineBorder(new Color(91, 155, 213)), "Th\u00F4ng tin kh\u00E1ch h\u00E0ng", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		pKH.setBackground(new Color(255,255,255,10));
		pKH.setBounds(10, 72, 637, 210);
		pMain.add(pKH);
		pKH.setLayout(null);

		JLabel lblTenKH = new JLabel("Tên khách hàng:");
		lblTenKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTenKH.setBounds(10, 28, 133, 20);
		pKH.add(lblTenKH);

		txtTenKH = new JTextField();
		txtTenKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtTenKH.setBounds(139, 23, 158, 31);
		txtTenKH.setBorder(new LineBorder(new Color(91, 155, 213)));
		pKH.add(txtTenKH);
		txtTenKH.setColumns(10);

		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblSDT.setBounds(10, 64, 133, 20);
		pKH.add(lblSDT);

		txtSDT = new JTextField();
		txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSDT.setColumns(10);
		txtSDT.setBounds(139, 59, 158, 31);
		txtSDT.setBorder(new LineBorder(new Color(91, 155, 213)));
		pKH.add(txtSDT);

		cboGioiTinh = new JComboBox<String>(); 
		cboGioiTinh.setEditable(true);
		cboGioiTinh.setBackground(Color.WHITE);
		cboGioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboGioiTinh.setBounds(434, 59, 193, 31);
		cboGioiTinh.setBorder(new LineBorder(new Color(91, 155, 213)));
		pKH.add(cboGioiTinh);

		cboGioiTinh.addItem("Nam");
		cboGioiTinh.addItem("Nữ");



		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblGioiTinh.setBounds(324, 67, 100, 20);
		pKH.add(lblGioiTinh);

		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblNgaySinh.setBounds(324, 28, 93, 20);
		pKH.add(lblNgaySinh);

		btnThemKH = new JButton("Thêm");
		btnThemKH.setToolTipText("Thêm khách hàng");
		btnThemKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnThemKH.setBounds(10, 165, 200, 34);
		btnThemKH.setBackground(new Color(41, 242, 255));
		pKH.add(btnThemKH);
		

		btnLamMoiKH = new JButton("Làm mới");
		btnLamMoiKH.setToolTipText("Làm mới lại thông tin khách hàng");
		btnLamMoiKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnLamMoiKH.setBackground(new Color(41, 242, 255));
		btnLamMoiKH.setBounds(434, 165, 193, 34);
		pKH.add(btnLamMoiKH);
		
	

		chooserNgaySinh = new JDateChooser();
		chooserNgaySinh.getCalendarButton().setFont(new Font("SansSerif", Font.PLAIN, 15));
		chooserNgaySinh.setBounds(434, 23, 193, 32);
		pKH.add(chooserNgaySinh);
		chooserNgaySinh.setBorder(BorderFactory.createLineBorder(new Color(91, 155, 213)));
		chooserNgaySinh.setDateFormatString("dd/MM/yyyy");
		chooserNgaySinh.setFont(new Font("SansSerif",Font.PLAIN, 15));

		

		//setBorder(new LineBorder(new Color(91, 155, 213)));
		JPanel pThuoc = new JPanel();
		pThuoc.setToolTipText("Chọn thông tin thuốc cần mua");
		pThuoc.setBackground(new Color(255,255,255,10));
		pThuoc.setBorder(new TitledBorder(new LineBorder(new Color(91, 155, 213)), "Th\u00F4ng tin thu\u1ED1c", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		pThuoc.setBounds(657, 72, 350, 210);
		pMain.add(pThuoc);
		pThuoc.setLayout(null);

		JLabel lblLoaiThuoc = new JLabel("Loại thuốc");
		lblLoaiThuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblLoaiThuoc.setBounds(30, 27, 133, 20);
		pThuoc.add(lblLoaiThuoc);

		cboLoaiThuoc = new JComboBox<String>();
		cboLoaiThuoc.setBackground(Color.WHITE);
		cboLoaiThuoc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboLoaiThuoc.setBounds(118, 24, 202, 31);
		cboLoaiThuoc.setBorder(new LineBorder(new Color(91, 155, 213)));
		pThuoc.add(cboLoaiThuoc);

		JLabel lblTenThuoc = new JLabel("Tên thuốc");
		lblTenThuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTenThuoc.setBounds(30, 65, 133, 20);
		pThuoc.add(lblTenThuoc);

		cboTenThuoc = new JComboBox<String>();
		cboTenThuoc.setBackground(Color.WHITE);
		cboTenThuoc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboTenThuoc.setBounds(118, 60, 202, 31);
		cboTenThuoc.setBorder(new LineBorder(new Color(91, 155, 213)));
		pThuoc.add(cboTenThuoc);

		JLabel lblSoLuong = new JLabel("Số lượng");
		lblSoLuong.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblSoLuong.setBounds(30, 101, 75, 20);
		pThuoc.add(lblSoLuong);

		txtSoLuong = new JTextField();
		txtSoLuong.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSoLuong.setColumns(10);
		txtSoLuong.setBounds(118, 96, 202, 31);
		txtSoLuong.setBorder(new LineBorder(new Color(91, 155, 213)));
		pThuoc.add(txtSoLuong);

		rdoGiamSL = new JRadioButton("Giảm số lượng");
		rdoGiamSL.setBackground(new Color(222,240,250));
		rdoGiamSL.setFont(new Font("SansSerif", Font.BOLD, 15));
		rdoGiamSL.setBounds(118, 134, 142, 23);
		pThuoc.add(rdoGiamSL);

		btnThemThuoc = new JButton("Thêm thuốc");
		btnThemThuoc.setToolTipText("");
		btnThemThuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnThemThuoc.setBounds(30, 164, 133, 34);
		btnThemThuoc.setBackground(new Color(41, 242, 255));
		pThuoc.add(btnThemThuoc);

		btnXoaThuoc = new JButton("Xóa thuốc");
		btnXoaThuoc.setToolTipText("Xóa thuốc cần mua");
		btnXoaThuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnXoaThuoc.setBackground(new Color(41, 242, 255));
		btnXoaThuoc.setBounds(187, 164, 133, 34);
		pThuoc.add(btnXoaThuoc);


		String col [] = {"STT","Tên thuốc", "Loại thuốc", "Số lượng", "Đơn giá","Tổng tiền"};
		modelThuoc = new DefaultTableModel(col,0);



		tblThuoc = new JTable(modelThuoc);
		tblThuoc.setBackground(Color.WHITE);
		tblThuoc.setFont(new Font("SansSerif", Font.PLAIN, 13));


		JTableHeader tbHeader = tblThuoc.getTableHeader();
		tbHeader.setBackground(new Color(131, 179, 226));
		tbHeader.setForeground(Color.CYAN);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 20));


		tblThuoc.setSelectionBackground(new Color(236, 243, 250));
		tblThuoc.setSelectionForeground(new Color(91, 155, 213));
		tblThuoc.setRowHeight(30);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tblThuoc.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tblThuoc.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tblThuoc.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);

		JScrollPane spMatHang = new JScrollPane(tblThuoc);
		spMatHang.setToolTipText("Thông tin thuốc đã chọn");
		spMatHang.setViewportBorder(null);
		spMatHang.setBounds(10, 292, 997, 328);
		spMatHang.setBorder(new LineBorder(new Color(91, 155, 213), 1, true));
		spMatHang.setBackground(Color.white);
		pMain.add(spMatHang);

		btnLamMoiGD = new JButton("Làm mới");
		btnLamMoiGD.setToolTipText("Làm mới giao diện");
		btnLamMoiGD.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnLamMoiGD.setBackground(new Color(41, 242, 255));
		btnLamMoiGD.setBounds(238, 655, 209, 34);
		pMain.add(btnLamMoiGD);

		JLabel lblSubThanhTien = new JLabel("Thành tiền:");
		lblSubThanhTien.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblSubThanhTien.setBounds(733, 624, 101, 20);
		pMain.add(lblSubThanhTien);

		lblThanhTien = new JLabel("");
		lblThanhTien.setForeground(new Color(255, 0, 0));
		lblThanhTien.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblThanhTien.setBounds(840, 624, 167, 20);
		pMain.add(lblThanhTien);

		btnThanhToan = new JButton("Thanh toán ");
		btnThanhToan.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnThanhToan.setBackground(new Color(41, 242, 255));
		btnThanhToan.setBounds(733, 655, 274, 34);
		pMain.add(btnThanhToan);

		btnDSKH = new JButton("Danh sách khách hàng");
		btnDSKH.setToolTipText("Mở danh sách khách hàng");
		btnDSKH.setBounds(10, 655, 218, 34);
		pMain.add(btnDSKH);
		btnDSKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDSKH.setBackground(new Color(41, 242, 255));
		
		String col1 [] = {"Tên KH","SĐT", "Ngày sinh", "Giới tính"};
		modelKH = new DefaultTableModel(col1,0);



		tblKH = new JTable(modelKH);
		tblKH.setBackground(Color.WHITE);
		tblKH.setFont(new Font("SansSerif", Font.PLAIN, 13));


		JTableHeader tblHeader = tblKH.getTableHeader();
		tblHeader.setBackground(new Color(91, 155, 213,80));
		tblHeader.setForeground(Color.CYAN);
		tblHeader.setFont(new Font("SansSerif", Font.BOLD, 20));


		tblKH.setSelectionBackground(new Color(236, 243, 250));
		tblKH.setSelectionForeground(new Color(91, 155, 213));
		tblKH.setRowHeight(30);

		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(JLabel.RIGHT);

		tblKH.getColumnModel().getColumn(1).setCellRenderer(right);
		tblKH.getColumnModel().getColumn(2).setCellRenderer(right);
		

		JScrollPane spKH = new JScrollPane(tblKH);
		spKH.setViewportBorder(null);
		spKH.setBounds(10, 95, 617, 64);
		spKH.setBorder(new LineBorder(new Color(91, 155, 213), 1, true));
		spKH.setBackground(Color.white);
		pKH.add(spKH);
		
		btnSua = new JButton("Sửa");
		btnSua.setToolTipText("Sửa thông tin khách hàng");
		btnSua.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnSua.setBackground(new Color(41, 242, 255));
		btnSua.setBounds(220, 165, 204, 34);
		pKH.add(btnSua);

		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 1031, 700);
		lblBackground.setIcon(new ImageIcon("data\\img\\bg.png"));
		pMain.add(lblBackground);


		

		//ngày mặc định cho chooser
		LocalDate d = LocalDate.now();
		now = new Date(d.getYear()-1900,d.getMonthValue()-1,d.getDayOfMonth());
		chooserNgaySinh.setDate(now);

		//Load loại thuốc
		List<TypeOfBook> lsLoaiThuoc = loaiThuocDao.getAllLoaiThuoc();
		for(TypeOfBook lt : lsLoaiThuoc) {
			cboLoaiThuoc.addItem(lt.getTenLoai());
		}
		
		//Load tên thuốc
		String s = cboLoaiThuoc.getSelectedItem().toString();
		TypeOfBook itemLT = null;
		List<Book> dsThuoc = null;
		try {
			itemLT = loaiThuocDao.getLoaiThuocTheoTen(s);
			dsThuoc = thuocDao.getThuocTheoMaLoai(itemLT.getId());
			
			
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		cboTenThuoc.removeAllItems();
		
		//Load ds Thuốc
		for (Book t : dsThuoc)
		{
			cboTenThuoc.addItem(t.getTenThuoc());
		}
		
		dfTable = new DecimalFormat("###,###");
		df = new DecimalFormat("###,### VNĐ");
		sf = new SimpleDateFormat("dd/MM/yyy");
		
		
		Icon icThem = IconFontSwing.buildIcon(FontAwesome.PLUS, 20, new Color(0, 176, 80));
		Icon icNgay = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, new Color(91, 155, 213));
		Icon icTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.black);
		Icon icLamMoi = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.blue);
		Icon icDS = IconFontSwing.buildIcon(FontAwesome.LIST_OL, 20, Color.orange);
		Icon icXoa = IconFontSwing.buildIcon(FontAwesome.TIMES, 20, Color.red);
		Icon icSua = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.darkGray);
		Icon icThanhToan = IconFontSwing.buildIcon(FontAwesome.CART_PLUS, 25, new Color(0, 176, 80));
		
		btnTim.setIcon(icTim);
		chooserNgaySinh.setIcon((ImageIcon) icNgay);
		btnThemKH.setIcon( icThem);
		btnThemThuoc.setIcon(icThem);
		btnLamMoiKH.setIcon( icLamMoi);
		btnLamMoiGD.setIcon(icLamMoi);
		btnDSKH.setIcon(icDS);
		btnXoaThuoc.setIcon(icXoa);
		btnSua.setIcon(icSua);
		btnThanhToan.setIcon(icThanhToan);
		btnThemThuoc.setToolTipText("Thêm số lượng thuốc đã mua");

		//action
		btnDSKH.addActionListener(this);
		btnLamMoiKH.addActionListener(this);
		btnThemKH.addActionListener(this);
		btnThemThuoc.addActionListener(this);
		btnXoaThuoc.addActionListener(this);
		rdoGiamSL.addActionListener(this);
		btnLamMoiGD.addActionListener(this);
		btnTim.addActionListener(this);
		btnSua.addActionListener(this);
		btnThanhToan.addActionListener(this);
		
		cboLoaiThuoc.addItemListener(this);
		
		tblThuoc.addMouseListener(this);
		tblKH.addMouseListener(this);
		
		txtTenKH.setText("Phan Huu Trong");
		txtSDT.setText("0363435019");
		
		
		

	}


	public void loadFrmDSKH() throws MalformedURLException, RemoteException, NotBoundException {
		
		DSHD frmKH = new DSHD(fMain);
		frmKH.setVisible(true);

		fMain.setVisible(false);


	}

	
	public void resetKH() {
		txtTenKH.setText("");
		txtSDT.setText("");
		cboGioiTinh.setSelectedIndex(0);
		chooserNgaySinh.setDate(now);
		clearTableKH();
	}
	
	public void resetAll() {
		resetKH();
		txtTimKiem.setText("");
		cboLoaiThuoc.setSelectedIndex(0);
		rdoGiamSL.setSelected(false);
		txtSoLuong.setText("");
		clearTable();
		lblThanhTien.setText("");
	}


	public int timRow() {
			int row = tblThuoc.getRowCount();
			for(int i=0;i<row;i++) {
				if(modelThuoc.getValueAt(i, 2).toString().equalsIgnoreCase(cboLoaiThuoc.getSelectedItem().toString())&&modelThuoc.getValueAt(i,1).toString().equalsIgnoreCase(cboTenThuoc.getSelectedItem().toString()))
				{
					return i;

				}
			}
			return -1;
	}
	
	public void clearTable() {
		while (tblThuoc.getRowCount() > 0) {
			modelThuoc.removeRow(0);
		}
	}
	public void clearTableKH() {
		while (tblKH.getRowCount() > 0) {
			modelKH.removeRow(0);
		}
	}
	
	public int getSTT() {
		
		return tblThuoc.getRowCount()+1;
	}
	
	public void addToTable(Book thuoc) {
		modelThuoc.addRow(new Object[] {
				getSTT(),thuoc.getTenThuoc(), thuoc.getLoaiThuoc().getTenLoai(),thuoc.getSoLuongTon(),dfTable.format(thuoc.getDonGia()), dfTable.format(thuoc.getSoLuongTon()*thuoc.getDonGia())
		});
	}
	
	public int getSoLuongThem() {
		int soLuong = 0;
		if(timRow()!=-1) {
			soLuong = Integer.parseInt(txtSoLuong.getText()) +  Integer.parseInt(modelThuoc.getValueAt(timRow(),3).toString());
		}
		else soLuong = Integer.parseInt(txtSoLuong.getText());
		return soLuong;
	}
	
	public int getSoLuongGiam() {
		int soLuong = 0;
		if(rdoGiamSL.isSelected()) {
			if(timRow()!=-1) {
				soLuong = - Integer.parseInt(txtSoLuong.getText()) +  Integer.parseInt(modelThuoc.getValueAt(timRow(),3).toString());
			}
			
		}
		return soLuong;
	}
	
	
	
	public void themKH() throws RemoteException {
		String tenKH = txtTenKH.getText().trim();
		String  sdt = txtSDT.getText().trim();
		Date ngaySinh = chooserNgaySinh.getDate();
		String gioiTinh  = cboGioiTinh.getSelectedItem().toString().trim();
		
		Customer kh = new Customer(tenKH, gioiTinh, ngaySinh, sdt);
		Customer khTim = khachHangDao.getKHTheoSDT(sdt);
		if(ktRongKH()) {
			if(ktThongTinKH()) {
				if(khTim == null) {
					if(khachHangDao.addKhachHang(kh)) {
						JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
						loadtableKH(kh);
					}
				}else JOptionPane.showMessageDialog(this, "Số điện thoại đã được đăng ký");
			}
		}
	}
	
	public void themThuoc() throws RemoteException {
		
		if(ktThongTinThuoc()) {
			String tenLoaiThuoc = cboLoaiThuoc.getSelectedItem().toString().trim();
			String tenThuoc = cboTenThuoc.getSelectedItem().toString().trim();
			
			TypeOfBook loaiThuoc = loaiThuocDao.getLoaiThuocTheoTen(tenLoaiThuoc);
			Book t = thuocDao.getThuocTheoTenVaMaLoai(tenThuoc, loaiThuoc.getId());
			Book updateThuoc = t;
			int soLuongUpdate = updateThuoc.getSoLuongTon();
			
			int soLuongTon = t.getSoLuongTon();
			int soLuongMua = 0;
			t.setLoaiThuoc(loaiThuoc);
			if(!rdoGiamSL.isSelected()) {
				soLuongMua = getSoLuongThem();
				soLuongUpdate -=  Integer.parseInt(txtSoLuong.getText());
				
				if(soLuongTon >= soLuongMua) {
					if(timRow()!=-1) {
						modelThuoc.removeRow(timRow());
					}
					
					t.setSoLuongTon(soLuongMua);
					addToTable(t);
					double thanhTien = tinhThanhTien();
					lblThanhTien.setText(df.format(thanhTien));
					updateThuoc.setSoLuongTon(soLuongUpdate);
					thuocDao.updateThuoc(updateThuoc);
				}
				else JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ!\nSố lượng còn: "+ soLuongTon);
			}
			else {
				soLuongMua =  getSoLuongGiam();
				if(soLuongMua >=0) {
					soLuongUpdate +=  Integer.parseInt(txtSoLuong.getText());

					if(timRow()!=-1) {
						modelThuoc.removeRow(timRow());
					}
					
					t.setSoLuongTon(soLuongMua);
					addToTable(t);
					double thanhTien = tinhThanhTien();
					lblThanhTien.setText(df.format(thanhTien));
					updateThuoc.setSoLuongTon(soLuongUpdate);
					thuocDao.updateThuoc(updateThuoc);
				}
				else JOptionPane.showMessageDialog(this, "Số lượng giảm đã vượt quá số lượng đã mua");
			}
			
			
		}
		
		
	}
	
	public void removeThuoc() throws RemoteException {
		int row = tblThuoc.getSelectedRow();
		if(row>=0) {
			String tenLoaiThuoc = cboLoaiThuoc.getSelectedItem().toString();
			String tenThuoc = cboTenThuoc.getSelectedItem().toString();
			
			TypeOfBook loaiThuoc = loaiThuocDao.getLoaiThuocTheoTen(tenLoaiThuoc);
			Book t = thuocDao.getThuocTheoTenVaMaLoai(tenThuoc, loaiThuoc.getId());
			
			int soLuongDaMua = Integer.parseInt(txtSoLuong.getText());
			t.setSoLuongTon(soLuongDaMua + t.getSoLuongTon());
			thuocDao.updateThuoc(t);
			modelThuoc.removeRow(timRow());
			
		}
		else JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc cần xóa");
	}
	
	public double tinhThanhTien() {
		int soRow = tblThuoc.getRowCount();
		double thanhTien = 0;
		for(int i = 0; i< soRow; i++) {
			try {
				double tongTien = Double.parseDouble(dfTable.parse(modelThuoc.getValueAt(i, 5).toString()).toString()) ;
				thanhTien += tongTien;
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		return thanhTien;
	}
	
	public boolean ktRongKH() {
		if(!regex.kiemTraRong(txtTenKH))
			return false;
		if(!regex.kiemTraRong(txtSDT))
			return false;
		return true;
	}
	public boolean ktThongTinKH() {
		if(ktRongKH()) {
			if(!regex.RegexTen(txtTenKH))
				return false;
			if(!regex.RegexSDT(txtSDT))
				return false;
			if(!regex.kiemTraNgaySinh(chooserNgaySinh))
				return false;
			
			
			return true;
		}
		return false;
	}
	public boolean ktThongTinThuoc() {
		
		if(regex.regexSoLuong(txtSoLuong))
		{
			int soLuong = Integer.parseInt(txtSoLuong.getText());
			if(soLuong > 0) {
				return true;
			}
			else JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
		}
		return false;
		
		
		
		
		
		
	}
	
	public void timKH() throws RemoteException {
		String sdt = txtTimKiem.getText().trim();
		Customer kh = khachHangDao.getKHTheoSDT(sdt);
		if(kh == null) {
			JOptionPane.showMessageDialog(this, "Không có kết quả tìm kiếm phù hợp!\n - Vui lòng nhập SĐT của khách hàng đã được đăng ký bao gồm 10 chữ số.");
		}
		else {
			loadtableKH(kh);
			
		}
	}
	
	public void loadtableKH(Customer kh) {
		clearTableKH();
		modelKH.addRow(new Object[] {
				kh.getTenKhachHang(),kh.getSdt(),sf.format(kh.getNgaySinh()),kh.getGioiTinh()
		});
	}
	
	public void suaKH() throws RemoteException {
		int row = tblKH.getSelectedRow();
		if(row!= -1) {
			Customer kh = khachHangDao.getKHTheoSDT(modelKH.getValueAt(row, 1).toString());
		
			if(ktRongKH()) {
				if(ktThongTinKH()) {
					Customer khTim = khachHangDao.getKHTheoSDT(txtSDT.getText());
					if(khTim.getSdt().equalsIgnoreCase(kh.getSdt())||khTim == null) {
						kh.setTenKhachHang(txtTenKH.getText());
						kh.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
						kh.setNgaySinh(chooserNgaySinh.getDate());
						kh.setSdt(txtSDT.getText());
						if(khachHangDao.updateKhachHang(kh)) {
							JOptionPane.showMessageDialog(this, "Sửa khách hàng thành công");
							loadtableKH(kh);
						}
					}else JOptionPane.showMessageDialog(this, "Số điện thoại đã được đăng ký");
				}
			}
			
			
		}else JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước khi sửa");
	}
	public List<OrderDetail> getdsCTHD() throws RemoteException{
		int row = tblThuoc.getRowCount();
		List<OrderDetail> lsCT = new ArrayList<OrderDetail>();
		
		for(int i = 0;i<row;i++) {
			TypeOfBook lt = loaiThuocDao.getLoaiThuocTheoTen(modelThuoc.getValueAt(i, 2).toString());
			String tenThuoc = modelThuoc.getValueAt(i, 1).toString();
			Book t = thuocDao.getThuocTheoTenVaMaLoai(tenThuoc, lt.getId());
			int soLuong = Integer.parseInt(modelThuoc.getValueAt(i, 3).toString());
			OrderDetail ct = new OrderDetail(t, soLuong);
			lsCT.add(ct);
			
		}
		
		return lsCT;
	}
	
	public void thanhToan() throws RemoteException {
		int rowKH = tblKH.getSelectedRow();
		int optThanhToan = JOptionPane.showConfirmDialog(this, "Bạn có chắn chắn muốn thanh toán không?", "Thông báo", JOptionPane.YES_NO_OPTION );
		
		if(optThanhToan == JOptionPane.YES_OPTION) {
			if(rowKH != -1) {
				int rowThuoc = tblThuoc.getRowCount();
				if(rowThuoc != 0 ) {
					Date ngayLap = now;
					Employee nv = nhanVienDao.getNhanVienTheoSoNV(maNV);
					Customer kh = khachHangDao.getKHTheoSDT(modelKH.getValueAt(rowKH, 1).toString());
					List<OrderDetail> lsCTHD = getdsCTHD();
					Order hd = new Order(ngayLap, nv, kh, lsCTHD);
					if(hoaDonDao.addHoaDon(hd))
					{
						JOptionPane.showMessageDialog(this, "Thanh toán thành công.");
					}
					
				}
				else JOptionPane.showMessageDialog(this, "Vui lòng nhập thuốc cần mua");
				
			}
			else JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước khi thanh toán");
		}
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnDSKH)) {
			try {
				loadFrmDSKH();
			} catch (MalformedURLException | RemoteException | NotBoundException e1) {
				e1.printStackTrace();
			}
		}
		if(o.equals(btnLamMoiKH)) {
			resetKH();
		}
		if(o.equals(btnThemKH)) {
			try {
				themKH();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if(rdoGiamSL.isSelected()) {
			btnThemThuoc.setText("Giảm thuốc");
			Icon icGiam = IconFontSwing.buildIcon(FontAwesome.MINUS, 20, Color.red);
			btnThemThuoc.setIcon(icGiam);
			btnThemThuoc.setToolTipText("Giảm số lượng thuốc đã mua");
		}
		else {
			btnThemThuoc.setText("Thêm thuốc");
			Icon icGiam = IconFontSwing.buildIcon(FontAwesome.PLUS, 20, new Color(0, 176, 80));
			btnThemThuoc.setIcon(icGiam);
			btnThemThuoc.setToolTipText("Thêm số lượng thuốc đã mua");
		}
		if(o.equals(btnThemThuoc)) {
			try {
				themThuoc();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if(o.equals(btnXoaThuoc)) {
			try {
				removeThuoc();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if(o.equals(btnLamMoiGD)) {
			resetAll();
		}
		if(o.equals(btnTim)) {
			try {
				timKH();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if(o.equals(btnSua)) {
			try {
				suaKH();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if(o.equals(btnThanhToan)) {
			try {
				thanhToan();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if(o.equals(tblThuoc)) {
		int row = tblThuoc.getSelectedRow();
		cboLoaiThuoc.setSelectedItem(modelThuoc.getValueAt(row, 2));
		cboTenThuoc.setSelectedItem(modelThuoc.getValueAt(row, 1));
		txtSoLuong.setText(modelThuoc.getValueAt(row, 3).toString());
		}
		if(o.equals(tblKH)) {
			int row = tblKH.getSelectedRow();
			txtTenKH.setText(modelKH.getValueAt(row,0).toString());
			txtSDT.setText(modelKH.getValueAt(row, 1).toString());
			Date ngaysinh = null;
			try {
				ngaysinh = sf.parse(modelKH.getValueAt(row, 2).toString());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			chooserNgaySinh.setDate(ngaysinh);
			cboGioiTinh.setSelectedItem(modelKH.getValueAt(row, 3));
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}



	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getItem() == cboLoaiThuoc.getSelectedItem()) {
			String s = cboLoaiThuoc.getSelectedItem().toString();
			TypeOfBook lt = null;
			List<Book> dsThuoc = null;
			try {
				lt = loaiThuocDao.getLoaiThuocTheoTen(s);
				dsThuoc = thuocDao.getThuocTheoMaLoai(lt.getId());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			cboTenThuoc.removeAllItems();
			for (Book t : dsThuoc)
			{

				cboTenThuoc.addItem(t.getTenThuoc());
			}
		}
	}
}
