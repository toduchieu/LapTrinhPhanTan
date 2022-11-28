package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.TableView.TableCell;

import org.bson.types.ObjectId;

import com.toedter.calendar.JDateChooser;

import dao.TypeOfBookDao;
import dao.NhaCungCapDao;
import dao.NuocSXDao;
import dao.BookDao;
import entity.TypeOfBook;
import entity.NhaCungCap;
import entity.NuocSX;
import entity.Book;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmQuanLyThuoc extends JPanel implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1486775462401966507L;
	private JFrame frame;
	private JTextField txttimkiemthuoc;
	private JTable tbl;
	private JTextField txttenthuoc;
	private JTextField txtsoluong;
	private JTextField txtdongia;
	private JComboBox<String> cbotenncc;
	private JTextField txtdiachi;
	private JComboBox<String> cbonuocsx;
	private JPanel p;
	private JButton btnthem;
	private JButton btnxoa;
	private JButton btnsua;
	private JButton btnlammoi;
	private DefaultTableModel modelthuoc;
	private JDateChooser datehansd;
	private JDateChooser datengaysx;
	private JButton btntim;

	private TypeOfBookDao loaiThuocDao;
	private NhaCungCapDao NCCDao;

	private NuocSXDao nuocSXDao;

	private BookDao thuocDao;

	private JComboBox<String> cboloaithuoc;

	private TypeOfBook loaiThuoc3=null;
	private NuocSX nuocSX3=null;
	private NhaCungCap nhaCungCap2=null;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * 
	 * @throws NotBoundException
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws HeadlessException
	 */
	public FrmQuanLyThuoc() throws HeadlessException, MalformedURLException, RemoteException, NotBoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws NotBoundException
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws HeadlessException
	 */
	private void initialize() throws HeadlessException, MalformedURLException, RemoteException, NotBoundException {
		frame = new JFrame();
		frame.setBounds(0, 0, 1031, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
	
		setLayout(null);
		
		String ip ="";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		loaiThuocDao =  (TypeOfBookDao) Naming.lookup("rmi://"+ip+":9999/loaiThuocDao");
		NCCDao =  (NhaCungCapDao) Naming.lookup("rmi://"+ip+":9999/nhaCungCapDao");
		nuocSXDao =  (NuocSXDao) Naming.lookup("rmi://"+ip+":9999/nuocSXDao");
		thuocDao =  (BookDao) Naming.lookup("rmi://"+ip+":9999/thuocDao");

		p = new JPanel();
		p.setBackground(Color.WHITE);
		p.setBounds(0, 0, 1370, 700);
		add(p);
		p.setLayout(null);

		btntim = new JButton("Tìm");
		btntim.setToolTipText("Tìm thuốc theo tên");
		btntim.setFont(new Font("SansSerif", Font.BOLD, 15));
		btntim.setBackground(new Color(41, 242, 255));
		btntim.setBounds(893, 42, 107, 33);
		p.add(btntim);

		btnthem = new JButton("Thêm");
		btnthem.setToolTipText("Thêm thuốc mới");
		btnthem.setBackground(Color.CYAN);
		btnthem.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnthem.setForeground(Color.BLACK);
		btnthem.setBounds(143, 229, 172, 33);
		p.add(btnthem);

		btnxoa = new JButton("Xóa");
		btnxoa.setToolTipText("Xóa thuốc");
		btnxoa.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnxoa.setBackground(Color.CYAN);
		btnxoa.setBounds(325, 229, 172, 33);
		p.add(btnxoa);

		btnsua = new JButton("Sửa");
		btnsua.setToolTipText("Sửa thông tin thuốc");
		btnsua.setBackground(Color.CYAN);
		btnsua.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnsua.setBounds(507, 229, 172, 33);
		p.add(btnsua);

		btnlammoi = new JButton("Làm mới");
		btnlammoi.setToolTipText("Làm mới thông tin như ban đầu");
		btnlammoi.setBackground(Color.CYAN);
		btnlammoi.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnlammoi.setBounds(690, 229, 172, 33);
		p.add(btnlammoi);

		JLabel lbltimthuoc = new JLabel("Tìm kiếm:");
		lbltimthuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		lbltimthuoc.setBounds(552, 41, 80, 32);
		p.add(lbltimthuoc);

		JLabel lbltieudequanlythuoc = new JLabel("Quản lý thuốc");
		lbltieudequanlythuoc.setFont(new Font("SansSerif", Font.BOLD, 25));
		lbltieudequanlythuoc.setBounds(10, 29, 178, 48);
		p.add(lbltieudequanlythuoc);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 273, 977, 397);
		String column[] = { "Tên thuốc", "Loại thuốc", "Ngày SX", "Hạn SD", "Đơn giá", "Số lượng", "Tên NCC", "Địa chỉ",
				"Nước SX" };
		modelthuoc = new DefaultTableModel(column, 0);

		JLabel lbltenthuong = new JLabel("Tên thuốc:");
		lbltenthuong.setFont(new Font("SansSerif", Font.BOLD, 15));
		lbltenthuong.setBounds(31, 88, 107, 32);
		p.add(lbltenthuong);

		JLabel lblloaithuoc = new JLabel("Loại thuốc:");
		lblloaithuoc.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblloaithuoc.setBounds(359, 88, 114, 32);
		p.add(lblloaithuoc);

		JLabel lblsoluong = new JLabel("Số lượng:");
		lblsoluong.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblsoluong.setBounds(720, 87, 120, 26);
		p.add(lblsoluong);

		JLabel lbldongia = new JLabel("Đơn giá:");
		lbldongia.setFont(new Font("SansSerif", Font.BOLD, 15));
		lbldongia.setBounds(31, 131, 93, 32);
		p.add(lbldongia);

		JLabel lbltenncc = new JLabel("Tên NCC:");
		lbltenncc.setFont(new Font("SansSerif", Font.BOLD, 15));
		lbltenncc.setBounds(31, 174, 102, 27);
		p.add(lbltenncc);

		JLabel lblngaysx = new JLabel("Ngày SX:");
		lblngaysx.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblngaysx.setBounds(359, 131, 93, 32);
		p.add(lblngaysx);

		JLabel lbldiachi = new JLabel("Địa chỉ:");
		lbldiachi.setFont(new Font("SansSerif", Font.BOLD, 15));
		lbldiachi.setBounds(359, 171, 80, 32);
		p.add(lbldiachi);

		JLabel lblhansd = new JLabel("Hạn SD:");
		lblhansd.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblhansd.setBounds(720, 124, 81, 32);
		p.add(lblhansd);

		JLabel lblnuocsx = new JLabel("Nước SX:");
		lblnuocsx.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblnuocsx.setBounds(720, 167, 80, 32);
		p.add(lblnuocsx);

		datehansd = new JDateChooser();
		datehansd.setBounds(822, 129, 178, 32);
		datehansd.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datehansd.setBorder(new LineBorder(new Color(91, 155, 213)));
		datehansd.setDateFormatString("dd/MM/yyyy");
		p.add(datehansd);

		datengaysx = new JDateChooser();
		datengaysx.setBounds(473, 131, 190, 32);
		datengaysx.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datengaysx.setBorder(new LineBorder(new Color(91, 155, 213)));
		datengaysx.setDateFormatString("dd/MM/yyyy");
		p.add(datengaysx);

		txttenthuoc = new JTextField();
		txttenthuoc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txttenthuoc.setBounds(143, 88, 172, 32);
		txttenthuoc.setBorder(new LineBorder(new Color(91, 155, 213)));
		p.add(txttenthuoc);
		txttenthuoc.setColumns(10);

		cboloaithuoc = new JComboBox<String>();
		cboloaithuoc.setEditable(true);
		cboloaithuoc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboloaithuoc.setBounds(473, 90, 190, 32);
		cboloaithuoc.setBorder(new LineBorder(new Color(91, 155, 213)));
		p.add(cboloaithuoc);

		txtsoluong = new JTextField();
		txtsoluong.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtsoluong.setBounds(822, 86, 178, 32);
		txtsoluong.setBorder(new LineBorder(new Color(91, 155, 213)));
		p.add(txtsoluong);
		txtsoluong.setColumns(10);

		txtdongia = new JTextField();
		txtdongia.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtdongia.setBounds(143, 133, 172, 32);
		txtdongia.setBorder(new LineBorder(new Color(91, 155, 213)));
		p.add(txtdongia);
		txtdongia.setColumns(10);

		cbotenncc = new JComboBox<String>();
		cbotenncc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbotenncc.setEditable(true);
		cbotenncc.setBounds(143, 176, 172, 32);
		cbotenncc.setBorder(new LineBorder(new Color(91, 155, 213)));
		p.add(cbotenncc);

		txtdiachi = new JTextField();
		txtdiachi.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtdiachi.setBounds(474, 173, 189, 32);
		txtdiachi.setBorder(new LineBorder(new Color(91, 155, 213)));
		p.add(txtdiachi);
		txtdiachi.setColumns(10);

		cbonuocsx = new JComboBox<String>();
		cbonuocsx.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbonuocsx.setBounds(822, 170, 178, 32);
		cbonuocsx.setEditable(true);
		cbonuocsx.setBorder(new LineBorder(new Color(91, 155, 213)));
		p.add(cbonuocsx);

		tbl = new JTable(modelthuoc);
		tbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
		tbl.setBackground(Color.WHITE);
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

		tbl.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tbl.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tbl.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tbl.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbl.getColumnModel().getColumn(0).setPreferredWidth(150);
		tbl.getColumnModel().getColumn(1).setPreferredWidth(120);
		tbl.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbl.getColumnModel().getColumn(3).setPreferredWidth(100);
		tbl.getColumnModel().getColumn(4).setPreferredWidth(90);
		tbl.getColumnModel().getColumn(5).setPreferredWidth(90);
		tbl.getColumnModel().getColumn(6).setPreferredWidth(100);
		tbl.getColumnModel().getColumn(7).setPreferredWidth(500);
		tbl.getColumnModel().getColumn(6).setPreferredWidth(100);
		
		JScrollPane spMatHang = new JScrollPane(tbl);
		spMatHang.setViewportBorder(null);
		spMatHang.setBounds(13, 273, 987, 407);
		spMatHang.setBorder(new LineBorder(new Color(91, 155, 213), 1, true));
		spMatHang.setBackground(Color.white);
		p.add(spMatHang);

		txttimkiemthuoc = new JTextField();
		txttimkiemthuoc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txttimkiemthuoc.setBounds(642, 41, 228, 32);
		txttimkiemthuoc.setBorder(new LineBorder(new Color(91, 155, 213)));
		p.add(txttimkiemthuoc);
		txttimkiemthuoc.setColumns(10);

		JLabel jlbbg = new JLabel("");
		jlbbg.setIcon(new ImageIcon("data\\img\\bgbutton2.jpg"));
		jlbbg.setBounds(0, 0, 1025, 700);
		//
		btntim.addActionListener(this);
		btnlammoi.addActionListener(this);
		btnsua.addActionListener(this);
		btnthem.addActionListener(this);
		btnxoa.addActionListener(this);
		tbl.addMouseListener(this);
		// loadthongtin
		loaddata();
		loadloaithuoccbo();
		loadtenncccbo();
		loadnuocsxcbo();
		//
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon icThem = IconFontSwing.buildIcon(FontAwesome.PLUS, 20, new Color(0, 176, 80));
		Icon icTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.black);
		Icon icLamMoi = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.blue);
		Icon icXoa = IconFontSwing.buildIcon(FontAwesome.TIMES, 20, Color.red);
		Icon icSua = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.darkGray);
		btnthem.setIcon(icThem);
		btnsua.setIcon(icSua);
		btnxoa.setIcon(icXoa);
		btntim.setIcon(icTim);
		btnlammoi.setIcon(icLamMoi);
		p.add(jlbbg);

	}

	private void loaddata() throws RemoteException {
		DecimalFormat df = new DecimalFormat("###,###,###.####");
		DateFormat dfd = new SimpleDateFormat("dd/MM/yyyy");
		List<Book> dsthuoc = thuocDao.getAllThuoc();
		
		for (Book t : dsthuoc) {
			modelthuoc.addRow(new Object[] { t.getTenThuoc(), t.getLoaiThuoc().getTenLoai(), dfd.format(t.getNgaySX()),
					dfd.format(t.getHanSD()), df.format(t.getDonGia()), t.getSoLuongTon(), t.getNcc().getTenNCC(),
					t.getNcc().getDiaChiNCC(), t.getNuocSX().getTenNuocSX() });
		}
	}
	

	private void loadloaithuoccbo() throws RemoteException {
		List<TypeOfBook> loaiThuocs = loaiThuocDao.getAllLoaiThuoc();
		for (TypeOfBook lt : loaiThuocs) {
			cboloaithuoc.addItem(lt.getTenLoai());
		}
	}

	public void loadtenncccbo() throws RemoteException {
		List<NhaCungCap> nhaCungCaps = NCCDao.getAllnhacungcap();
		for (NhaCungCap ls : nhaCungCaps) {
			cbotenncc.addItem(ls.getTenNCC());
		}
	}

	private void loadnuocsxcbo() throws RemoteException {
		List<NuocSX> nuocSXs = nuocSXDao.getAllNuocsc();
		for (NuocSX n : nuocSXs) {
			cbonuocsx.addItem(n.getTenNuocSX());
		}
	}

	private void lammoi() throws RemoteException {
		modelthuoc.getDataVector().removeAllElements();
		loaddata();
	}

	private Book reverThuoc() throws RemoteException {
		String tenthuoc = txttenthuoc.getText().trim();
		String loaithuoc = cboloaithuoc.getSelectedItem().toString();
		Date ngaysx = (Date) datengaysx.getDate();
		Date hansd = (Date) datehansd.getDate();
		double dongia = Double.parseDouble(txtdongia.getText());
		int soluong = Integer.parseInt(txtsoluong.getText());
		String trangthai = "Còn bán";
		String tenncc = cbotenncc.getSelectedItem().toString();
		String diachi = txtdiachi.getText().trim();
		String nuocsx = cbonuocsx.getSelectedItem().toString();
		TypeOfBook loaithuoc2=loaiThuocDao.getLoaiThuocTheoTen(loaithuoc);
		if(loaithuoc.equals(loaithuoc2.getTenLoai())) {
			 loaiThuoc3 =new TypeOfBook(loaithuoc2.getId(), loaithuoc2.getTenLoai());
		}else {
			 loaiThuoc3=new TypeOfBook(loaithuoc);
		}
		NuocSX nuocSX2=nuocSXDao.getnuocsanxuat(nuocsx);
		if(nuocSX2.getTenNuocSX().equals(nuocsx)) {
			 nuocSX3=new NuocSX(nuocSX2.getId(), nuocSX2.getTenNuocSX());
		}else {
			 nuocSX3=new NuocSX(nuocsx);
		}
		NhaCungCap nhaCungCap=NCCDao.getnhacungcaptheoten(tenncc);
		if(tenncc.equals(nhaCungCap.getTenNCC())) {
			 nhaCungCap2=new NhaCungCap(nhaCungCap.getId(), nhaCungCap.getTenNCC(), nhaCungCap.getDiaChiNCC());
		}
		else {
			 nhaCungCap2=new NhaCungCap(tenncc, diachi);
		}
	
		
		Book thuoc = new Book(tenthuoc, dongia, soluong, ngaysx, hansd, trangthai, loaiThuoc3, nuocSX3, nhaCungCap2);
		return thuoc;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = tbl.getSelectedRow();
		txttenthuoc.setText(modelthuoc.getValueAt(row, 0).toString());
		cboloaithuoc.setSelectedItem(modelthuoc.getValueAt(row, 1));
		datengaysx.setDate(new Date(modelthuoc.getValueAt(row, 2).toString()));
		datehansd.setDate(new Date(modelthuoc.getValueAt(row, 3).toString()));

		txtdongia.setText(modelthuoc.getValueAt(row, 4).toString().replaceAll("[-+.^:,]", ""));
		txtsoluong.setText(modelthuoc.getValueAt(row, 5).toString());
		cbotenncc.setSelectedItem(modelthuoc.getValueAt(row, 6).toString());
		txtdiachi.setText(modelthuoc.getValueAt(row, 7).toString());
		cbonuocsx.setSelectedItem(modelthuoc.getValueAt(row, 8).toString());
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnthem)) {
			if (txtdiachi.getText().equals("") || txtdongia.getText().equals("") || txtsoluong.getText().equals("")
					|| txttenthuoc.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền thông tin đầy đủ");
			} else {
				try {
					if (ktThongTinThuoc()) {
						Book thuoc = null;
						try {
							thuoc = reverThuoc();
						} catch (RemoteException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							thuocDao.addThuoc(thuoc);
							lammoi();
							JOptionPane.showMessageDialog(null, "Thêm thành công");
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
		if (o.equals(btnlammoi)) {

			txtdiachi.setText("");
			txtdongia.setText("");
			txtsoluong.setText("");
			txttenthuoc.setText("");
			txttimkiemthuoc.setText("");
			cboloaithuoc.setSelectedIndex(0);
			cbonuocsx.setSelectedIndex(0);
			cbotenncc.setSelectedIndex(0);
			try {
				lammoi();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
		}
		if (o.equals(btnsua)) {
			int row = tbl.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin cần sửa");
			} else {
				int n = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa dữ liệu này không ?", null,
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					try {
						ObjectId id = loaiThuocDao.getLoaiThuocTheoTen(modelthuoc.getValueAt(row, 1).toString())
								.getId();
						Book thuoc = thuocDao.getThuocTheoTenVaMaLoai(modelthuoc.getValueAt(row, 0).toString(), id);

						TypeOfBook lt = loaiThuocDao.getLoaiThuocTheoTen(cboloaithuoc.getSelectedItem().toString());
						thuoc.setLoaiThuoc(lt);

						NhaCungCap nhaCungCap = NCCDao.getnhacungcaptheoten(cbotenncc.getSelectedItem().toString());
						thuoc.setNcc(nhaCungCap);

						nhaCungCap.setDiaChiNCC(txtdiachi.getText());

						NCCDao.updatediachi(nhaCungCap);

						NuocSX nuocSX = nuocSXDao.getnuocsanxuat(cbonuocsx.getSelectedItem().toString());
						thuoc.setNuocSX(nuocSX);

						thuoc.setDonGia(Double.parseDouble(txtdongia.getText()));
						thuoc.setHanSD(datehansd.getDate());
						thuoc.setNgaySX(datengaysx.getDate());
						thuoc.setTenThuoc(txttenthuoc.getText());
						thuoc.setSoLuongTon(Integer.parseInt(txtsoluong.getText()));
						Book thuoc2 = thuoc;
						thuocDao.updateThuoc(thuoc2);
						JOptionPane.showMessageDialog(null, "Sửa thành công");
						lammoi();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		}
		if (o.equals(btntim)) {
			String key = txttimkiemthuoc.getText();
			DecimalFormat df = new DecimalFormat("###,###,###.####");
			DateFormat dfd = new SimpleDateFormat("yyyy/MM/dd");
			List<Book> dsthuoc = null;
			try {
				dsthuoc = thuocDao.timkiemthuoc(key);
				if(dsthuoc.size()>0) {
				modelthuoc.getDataVector().removeAllElements();
				for (Book t : dsthuoc) {
					modelthuoc.addRow(
							new Object[] { t.getTenThuoc(), t.getLoaiThuoc().getTenLoai(), dfd.format(t.getNgaySX()),
									dfd.format(t.getHanSD()), df.format(t.getDonGia()), t.getSoLuongTon(),
									t.getNcc().getTenNCC(), t.getNcc().getDiaChiNCC(), t.getNuocSX().getTenNuocSX() });

				}
				}
				else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy thuốc");
					txttimkiemthuoc.requestFocus();
					
				}
	
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		if (o.equals(btnxoa)) {
			int row = tbl.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin cần xóa");
			} else {
				int n = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa dữ liệu này không ?", null,
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					try {
						ObjectId id = loaiThuocDao.getLoaiThuocTheoTen(modelthuoc.getValueAt(row, 1).toString())
								.getId();
						Book thuoc = thuocDao.getThuocTheoTenVaMaLoai(modelthuoc.getValueAt(row, 0).toString(), id);
						thuoc.setTrangThaiThuoc("Ngưng kinh doanh");
						Book thuoc2 = thuoc;
						thuocDao.updateThuoc(thuoc2);
						JOptionPane.showMessageDialog(null, "Xóa thành công");
						lammoi();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

		}

	}

	public boolean ktThongTinThuoc() throws ParseException {
		String tenthuoc = txttenthuoc.getText();
		String soluong = txtsoluong.getText();
		String dongia = txtdongia.getText();
		String diachi = txtdiachi.getText();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date ngaysx = new SimpleDateFormat("dd/MM/yyyy").parse(df.format(datengaysx.getDate()));
		Date hdsd = new SimpleDateFormat("dd/MM/yyyy").parse(df.format(datehansd.getDate()));

		if (tenthuoc.length() > 0) {
			String regex = "^([\\.\\(\\)0-9 A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷýỹ]*(\\s?))+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(tenthuoc);
			if (!matcher.find()) {
				JOptionPane.showMessageDialog(null, "Tên thuốc không hợp lệ");
				txttenthuoc.requestFocus();
				txttenthuoc.selectAll();
				return false;
			}
		}
		if (soluong.length() > 0) {
			String regexs = "^[0-9]+$";
			if (!soluong.matches(regexs)) {
				JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên và lớn hơn 0 (ví dụ nhập: 1000)");
				txtsoluong.requestFocus();
				txtsoluong.selectAll();
				return false;
			}
		}
		if (dongia.length() > 0) {
			String regexs = "^[\\.0-9]+$";
			if (!dongia.matches(regexs)) {
				JOptionPane.showMessageDialog(this, "Giá bán phải là số ");
				txtdongia.requestFocus();
				txtdongia.selectAll();
				return false;
			}
		}
		if (dongia.length() > 0) {
			double giaban2 = Double.parseDouble(dongia);
			if (giaban2 <= 0) {
				JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn 0");
				txtdongia.requestFocus();
				txtdongia.selectAll();
				return false;
			}
		}
		if (ngaysx.equals(hdsd)) {
			JOptionPane.showMessageDialog(null, "Ngày sản xuất và hạn sử dụng không được trùng nhau");
			return false;
		} else if (ngaysx.after(hdsd)) {
			JOptionPane.showMessageDialog(null, "Ngày sản xuất không thể sau hạn sử dụng");
			return false;

		}
		if (diachi.length() > 0) {

			String regex = "^([ A-Za-z0-9,.a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]*(\\s?))+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(diachi);
			if (!matcher.find()) {
				JOptionPane.showMessageDialog(null, "Nhập sai địa chỉ (Ví dụ nhập:56a Cầu Xéo, Tân quí, Tân Phú");
				txtdiachi.requestFocus();
				txtdiachi.selectAll();
				return false;
			}
		}

		return true;

	}
}
