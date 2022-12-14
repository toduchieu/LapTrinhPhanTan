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

import org.hibernate.hql.ast.origin.hql.parse.HQLParser.new_key_return;

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
import net.bytebuddy.asm.Advice.This;


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


		hoaDonDao =  (OrderDao) Naming.lookup("rmi://"+ip+":9999/orderDao");
		khachHangDao = (CustomerDao) Naming.lookup("rmi://"+ip+":9999/customerDao");
		loaiThuocDao =  (TypeOfBookDao) Naming.lookup("rmi://"+ip+":9999/typeOfBookDao");
		nhanVienDao =  (EmployeeDao) Naming.lookup("rmi://"+ip+":9999/employeeDao");
		thuocDao =  (BookDao) Naming.lookup("rmi://"+ip+":9999/bookDao");

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

		JLabel lblQLBH = new JLabel("Qu???n l?? b??n h??ng");
		lblQLBH.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblQLBH.setBounds(10, 32, 209, 33);
		pMain.add(lblQLBH);

		JLabel lblTim = new JLabel("T??m ki???m:");
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

		btnTim = new JButton("T??m");
		btnTim.setBackground(new Color(41, 242, 255));
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnTim.setBounds(918, 35, 89, 35);
		pMain.add(btnTim);
		
		

		JPanel pKH = new JPanel();
		pKH.setToolTipText("Nh???p th??ng tin c???a kh??ch h??ng");
		pKH.setBorder(new TitledBorder(new LineBorder(new Color(91, 155, 213)), "Th\u00F4ng tin kh\u00E1ch h\u00E0ng", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		pKH.setBackground(new Color(255,255,255,10));
		pKH.setBounds(10, 72, 637, 210);
		pMain.add(pKH);
		pKH.setLayout(null);

		JLabel lblTenKH = new JLabel("T??n kh??ch h??ng:");
		lblTenKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTenKH.setBounds(10, 28, 133, 20);
		pKH.add(lblTenKH);

		txtTenKH = new JTextField();
		txtTenKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtTenKH.setBounds(139, 23, 158, 31);
		txtTenKH.setBorder(new LineBorder(new Color(91, 155, 213)));
		pKH.add(txtTenKH);
		txtTenKH.setColumns(10);

		JLabel lblSDT = new JLabel("S??? ??i???n tho???i:");
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
		cboGioiTinh.addItem("N???");



		JLabel lblGioiTinh = new JLabel("Gi???i t??nh:");
		lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblGioiTinh.setBounds(324, 67, 100, 20);
		pKH.add(lblGioiTinh);

		JLabel lblNgaySinh = new JLabel("Ng??y sinh:");
		lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblNgaySinh.setBounds(324, 28, 93, 20);
		pKH.add(lblNgaySinh);

		btnThemKH = new JButton("Th??m");
		btnThemKH.setToolTipText("Th??m kh??ch h??ng");
		btnThemKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnThemKH.setBounds(10, 165, 200, 34);
		btnThemKH.setBackground(new Color(41, 242, 255));
		pKH.add(btnThemKH);
		

		btnLamMoiKH = new JButton("L??m m???i");
		btnLamMoiKH.setToolTipText("L??m m???i l???i th??ng tin kh??ch h??ng");
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
		pThuoc.setToolTipText("Ch???n th??ng tin thu???c c???n mua");
		pThuoc.setBackground(new Color(255,255,255,10));
		pThuoc.setBorder(new TitledBorder(new LineBorder(new Color(91, 155, 213)), "Th\u00F4ng tin thu\u1ED1c", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		pThuoc.setBounds(657, 72, 350, 210);
		pMain.add(pThuoc);
		pThuoc.setLayout(null);

		JLabel lblLoaiThuoc = new JLabel("Lo???i thu???c");
		lblLoaiThuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblLoaiThuoc.setBounds(30, 27, 133, 20);
		pThuoc.add(lblLoaiThuoc);

		cboLoaiThuoc = new JComboBox<String>();
		cboLoaiThuoc.setBackground(Color.WHITE);
		cboLoaiThuoc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboLoaiThuoc.setBounds(118, 24, 202, 31);
		cboLoaiThuoc.setBorder(new LineBorder(new Color(91, 155, 213)));
		pThuoc.add(cboLoaiThuoc);

		JLabel lblTenThuoc = new JLabel("T??n thu???c");
		lblTenThuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTenThuoc.setBounds(30, 65, 133, 20);
		pThuoc.add(lblTenThuoc);

		cboTenThuoc = new JComboBox<String>();
		cboTenThuoc.setBackground(Color.WHITE);
		cboTenThuoc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboTenThuoc.setBounds(118, 60, 202, 31);
		cboTenThuoc.setBorder(new LineBorder(new Color(91, 155, 213)));
		pThuoc.add(cboTenThuoc);

		JLabel lblSoLuong = new JLabel("S??? l?????ng");
		lblSoLuong.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblSoLuong.setBounds(30, 101, 75, 20);
		pThuoc.add(lblSoLuong);

		txtSoLuong = new JTextField();
		txtSoLuong.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSoLuong.setColumns(10);
		txtSoLuong.setBounds(118, 96, 202, 31);
		txtSoLuong.setBorder(new LineBorder(new Color(91, 155, 213)));
		pThuoc.add(txtSoLuong);

		rdoGiamSL = new JRadioButton("Gi???m s??? l?????ng");
		rdoGiamSL.setBackground(new Color(222,240,250));
		rdoGiamSL.setFont(new Font("SansSerif", Font.BOLD, 15));
		rdoGiamSL.setBounds(118, 134, 142, 23);
		pThuoc.add(rdoGiamSL);

		btnThemThuoc = new JButton("Th??m thu???c");
		btnThemThuoc.setToolTipText("");
		btnThemThuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnThemThuoc.setBounds(30, 164, 133, 34);
		btnThemThuoc.setBackground(new Color(41, 242, 255));
		pThuoc.add(btnThemThuoc);

		btnXoaThuoc = new JButton("X??a thu???c");
		btnXoaThuoc.setToolTipText("X??a thu???c c???n mua");
		btnXoaThuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnXoaThuoc.setBackground(new Color(41, 242, 255));
		btnXoaThuoc.setBounds(187, 164, 133, 34);
		pThuoc.add(btnXoaThuoc);


		String col [] = {"STT","T??n thu???c", "Lo???i thu???c", "S??? l?????ng", "????n gi??","T???ng ti???n"};
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
		spMatHang.setToolTipText("Th??ng tin thu???c ???? ch???n");
		spMatHang.setViewportBorder(null);
		spMatHang.setBounds(10, 292, 997, 328);
		spMatHang.setBorder(new LineBorder(new Color(91, 155, 213), 1, true));
		spMatHang.setBackground(Color.white);
		pMain.add(spMatHang);

		btnLamMoiGD = new JButton("L??m m???i");
		btnLamMoiGD.setToolTipText("L??m m???i giao di???n");
		btnLamMoiGD.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnLamMoiGD.setBackground(new Color(41, 242, 255));
		btnLamMoiGD.setBounds(238, 655, 209, 34);
		pMain.add(btnLamMoiGD);

		JLabel lblSubThanhTien = new JLabel("Th??nh ti???n:");
		lblSubThanhTien.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblSubThanhTien.setBounds(733, 624, 101, 20);
		pMain.add(lblSubThanhTien);

		lblThanhTien = new JLabel("");
		lblThanhTien.setForeground(new Color(255, 0, 0));
		lblThanhTien.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblThanhTien.setBounds(840, 624, 167, 20);
		pMain.add(lblThanhTien);

		btnThanhToan = new JButton("Thanh to??n ");
		btnThanhToan.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnThanhToan.setBackground(new Color(41, 242, 255));
		btnThanhToan.setBounds(733, 655, 274, 34);
		pMain.add(btnThanhToan);

		btnDSKH = new JButton("Danh s??ch kh??ch h??ng");
		btnDSKH.setToolTipText("M??? danh s??ch kh??ch h??ng");
		btnDSKH.setBounds(10, 655, 218, 34);
		pMain.add(btnDSKH);
		btnDSKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDSKH.setBackground(new Color(41, 242, 255));
		
		String col1 [] = {"T??n KH","S??T", "Ng??y sinh", "Gi???i t??nh"};
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
		
		btnSua = new JButton("S???a");
		btnSua.setToolTipText("S???a th??ng tin kh??ch h??ng");
		btnSua.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnSua.setBackground(new Color(41, 242, 255));
		btnSua.setBounds(220, 165, 204, 34);
		pKH.add(btnSua);

		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 1031, 700);
		lblBackground.setIcon(new ImageIcon("data\\img\\bg.png"));
		pMain.add(lblBackground);


		

		//ng??y m???c ?????nh cho chooser
		LocalDate d = LocalDate.now();
		now = new Date(d.getYear()-1900,d.getMonthValue()-1,d.getDayOfMonth());
		chooserNgaySinh.setDate(now);

		//Load lo???i thu???c
		List<TypeOfBook> lsLoaiThuoc = loaiThuocDao.getAllTypeOfBook();
		for(TypeOfBook lt : lsLoaiThuoc) {
			cboLoaiThuoc.addItem(lt.getName());
		}
		
//		//Load t??n thu???c
//		String s = cboLoaiThuoc.getSelectedItem().toString();
//		TypeOfBook itemLT = null;
//		List<Book> dsThuoc = null ;
//		try {
//			itemLT = loaiThuocDao.getTypeOfBookByName(s);
//			dsThuoc = thuocDao.getBookByTypeId(itemLT.getId());
//		} catch (RemoteException e1) {
//			e1.printStackTrace();
//		}
//		cboTenThuoc.removeAllItems();
//		
//		//Load ds Thu???c
//		for (Book t : dsThuoc)
//		{
//			cboTenThuoc.addItem(t.getBookName());
//		}
		
		dfTable = new DecimalFormat("###,###");
		df = new DecimalFormat("###,### VN??");
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
		btnThemThuoc.setToolTipText("Th??m s??? l?????ng thu???c ???? mua");

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
		
//		cboLoaiThuoc.addItemListener(this);
		
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
				getSTT(),thuoc.getBookName(), thuoc.getTypeOfBook().getName(),thuoc.getQuantityInStock(),dfTable.format(thuoc.getPrice()), dfTable.format(thuoc.getQuantityInStock()*thuoc.getPrice())
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
		Customer khTim = khachHangDao.getCustomerByNumberPhone(sdt);
		if(ktRongKH()) {
			if(ktThongTinKH()) {
				if(khTim == null) {
					if(khachHangDao.addCustomer(kh)) {
						JOptionPane.showMessageDialog(this, "Th??m kh??ch h??ng th??nh c??ng");
						loadtableKH(kh);
					}
				}else JOptionPane.showMessageDialog(this, "S??? ??i???n tho???i ???? ???????c ????ng k??");
			}
		}
	}
	
	public void themThuoc() throws RemoteException {
		
		if(ktThongTinThuoc()) {
			String tenLoaiThuoc = cboLoaiThuoc.getSelectedItem().toString().trim();
			String tenThuoc = cboTenThuoc.getSelectedItem().toString().trim();
			
			TypeOfBook loaiThuoc = loaiThuocDao.getTypeOfBookByName(tenLoaiThuoc);
			Book t = thuocDao.getBookByBookNameVaTypeId(tenThuoc, loaiThuoc.getId());
			Book updateThuoc = t;
			int soLuongUpdate = updateThuoc.getQuantityInStock();
			
			int soLuongTon = t.getQuantityInStock();
			int soLuongMua = 0;
			t.setTypeOfBook(loaiThuoc);
			if(!rdoGiamSL.isSelected()) {
				soLuongMua = getSoLuongThem();
				soLuongUpdate -=  Integer.parseInt(txtSoLuong.getText());
				
				if(soLuongTon >= soLuongMua) {
					if(timRow()!=-1) {
						modelThuoc.removeRow(timRow());
					}
					
					t.setQuantityInStock(soLuongMua);
					addToTable(t);
					double thanhTien = tinhThanhTien();
					lblThanhTien.setText(df.format(thanhTien));
					updateThuoc.setQuantityInStock(soLuongUpdate);
					thuocDao.updateBook(updateThuoc);
				}
				else JOptionPane.showMessageDialog(this, "S??? l?????ng t???n kh??ng ?????!\nS??? l?????ng c??n: "+ soLuongTon);
			}
			else {
				soLuongMua =  getSoLuongGiam();
				if(soLuongMua >=0) {
					soLuongUpdate +=  Integer.parseInt(txtSoLuong.getText());

					if(timRow()!=-1) {
						modelThuoc.removeRow(timRow());
					}
					
					t.setQuantityInStock(soLuongMua);
					addToTable(t);
					double thanhTien = tinhThanhTien();
					lblThanhTien.setText(df.format(thanhTien));
					updateThuoc.setQuantityInStock(soLuongUpdate);
					thuocDao.updateBook(updateThuoc);
				}
				else JOptionPane.showMessageDialog(this, "S??? l?????ng gi???m ???? v?????t qu?? s??? l?????ng ???? mua");
			}
			
			
		}
		
		
	}
	
	public void removeThuoc() throws RemoteException {
		int row = tblThuoc.getSelectedRow();
		if(row>=0) {
			String tenLoaiThuoc = cboLoaiThuoc.getSelectedItem().toString();
			String tenThuoc = cboTenThuoc.getSelectedItem().toString();
			
			TypeOfBook loaiThuoc = loaiThuocDao.getTypeOfBookByName(tenLoaiThuoc);
			Book t = thuocDao.getBookByBookNameVaTypeId(tenThuoc, loaiThuoc.getId());
			
			int soLuongDaMua = Integer.parseInt(txtSoLuong.getText());
			t.setQuantityInStock(soLuongDaMua + t.getQuantityInStock());
			thuocDao.updateBook(t);
			modelThuoc.removeRow(timRow());
			
		}
		else JOptionPane.showMessageDialog(this, "Vui l??ng ch???n thu???c c???n x??a");
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
			else JOptionPane.showMessageDialog(this, "S??? l?????ng ph???i l???n h??n 0");
		}
		return false;
		
		
		
		
		
		
	}
	
	public void timKH() throws RemoteException {
		String sdt = txtTimKiem.getText().trim();
		Customer kh = khachHangDao.getCustomerByNumberPhone(sdt);
		if(kh == null) {
			JOptionPane.showMessageDialog(this, "Kh??ng c?? k???t qu??? t??m ki???m ph?? h???p!\n - Vui l??ng nh???p S??T c???a kh??ch h??ng ???? ???????c ????ng k?? bao g???m 10 ch??? s???.");
		}
		else {
			loadtableKH(kh);
			
		}
	}
	
	public void loadtableKH(Customer kh) {
		clearTableKH();
		modelKH.addRow(new Object[] {
				kh.getCustomerName(),kh.getNumberPhone(),sf.format(kh.getDayOfBirth()),kh.getGender()
		});
	}
	
	public void suaKH() throws RemoteException {
		int row = tblKH.getSelectedRow();
		if(row!= -1) {
			Customer kh = khachHangDao.getCustomerByNumberPhone(modelKH.getValueAt(row, 1).toString());
		
			if(ktRongKH()) {
				if(ktThongTinKH()) {
					Customer khTim = khachHangDao.getCustomerByNumberPhone(txtSDT.getText());
					if(khTim.getNumberPhone().equalsIgnoreCase(kh.getNumberPhone())||khTim == null) {
						kh.setCustomerName(txtTenKH.getText());;
						kh.setGender(cboGioiTinh.getSelectedItem().toString());;
						kh.setDayOfBirth(chooserNgaySinh.getDate());;
						kh.setNumberPhone(txtSDT.getText());;
						if(khachHangDao.updateCustomer(kh)) {
							JOptionPane.showMessageDialog(this, "S???a kh??ch h??ng th??nh c??ng");
							loadtableKH(kh);
						}
					}else JOptionPane.showMessageDialog(this, "S??? ??i???n tho???i ???? ???????c ????ng k??");
				}
			}
			
			
		}else JOptionPane.showMessageDialog(this, "Vui l??ng ch???n kh??ch h??ng tr?????c khi s???a");
	}
	public List<OrderDetail> getdsCTHD() throws RemoteException{
		int row = tblThuoc.getRowCount();
		List<OrderDetail> lsCT = new ArrayList<OrderDetail>();
		
		for(int i = 0;i<row;i++) {
			TypeOfBook lt = loaiThuocDao.getTypeOfBookByName(modelThuoc.getValueAt(i, 2).toString());
			String tenThuoc = modelThuoc.getValueAt(i, 1).toString();
			Book t = thuocDao.getBookByBookNameVaTypeId(tenThuoc, lt.getId());
			int soLuong = Integer.parseInt(modelThuoc.getValueAt(i, 3).toString());
			OrderDetail ct = new OrderDetail(t, soLuong);
			lsCT.add(ct);
			
		}
		
		return lsCT;
	}
	
	public void thanhToan() throws RemoteException {
		int rowKH = tblKH.getSelectedRow();
		int optThanhToan = JOptionPane.showConfirmDialog(this, "B???n c?? ch???n ch???n mu???n thanh to??n kh??ng?", "Th??ng b??o", JOptionPane.YES_NO_OPTION );
		
		if(optThanhToan == JOptionPane.YES_OPTION) {
			if(rowKH != -1) {
				int rowThuoc = tblThuoc.getRowCount();
				if(rowThuoc != 0 ) {
					Date ngayLap = now;
					Employee nv = nhanVienDao.getEmployeeByEmployeeId(maNV);
					Customer kh = khachHangDao.getCustomerByNumberPhone(modelKH.getValueAt(rowKH, 1).toString());
					List<OrderDetail> lsCTHD = getdsCTHD();
					Order hd = new Order(ngayLap, kh, nv, lsCTHD);
					if(hoaDonDao.addOrder(hd))
					{
						JOptionPane.showMessageDialog(this, "Thanh to??n th??nh c??ng.");
					}
					
				}
				else JOptionPane.showMessageDialog(this, "Vui l??ng nh???p thu???c c???n mua");
				
			}
			else JOptionPane.showMessageDialog(this, "Vui l??ng ch???n kh??ch h??ng tr?????c khi thanh to??n");
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
			btnThemThuoc.setText("Gi???m thu???c");
			Icon icGiam = IconFontSwing.buildIcon(FontAwesome.MINUS, 20, Color.red);
			btnThemThuoc.setIcon(icGiam);
			btnThemThuoc.setToolTipText("Gi???m s??? l?????ng thu???c ???? mua");
		}
		else {
			btnThemThuoc.setText("Th??m thu???c");
			Icon icGiam = IconFontSwing.buildIcon(FontAwesome.PLUS, 20, new Color(0, 176, 80));
			btnThemThuoc.setIcon(icGiam);
			btnThemThuoc.setToolTipText("Th??m s??? l?????ng thu???c ???? mua");
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
				lt = loaiThuocDao.getTypeOfBookByName(s);
				System.out.println(lt);
				dsThuoc = thuocDao.getBookByTypeId(lt.getId());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			cboTenThuoc.removeAllItems();
			for (Book t : dsThuoc)
			{

				cboTenThuoc.addItem(t.getBookName());
			}
		}
	}
	
}
