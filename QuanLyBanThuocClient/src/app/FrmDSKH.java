package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import dao.OrderDao;
import entity.OrderDetail;
import entity.Order;
import entity.Customer;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmDSKH extends JFrame implements  ActionListener,MouseListener,ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3119054749493430978L;
	private JPanel contentPane;
	private JTable tbldsThuoc;
	private JTable tbldsKH;
	private OrderDao hoaDonDao;
	private DefaultTableModel modelthuoc1;
	private DefaultTableModel modelKhachhang1;
	private Customer khSelect;
	private SimpleDateFormat sf;
	private JLabel lblthanhtien;
	private JButton btnLammoi;

	

	/**
	 * Create the frame.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public FrmDSKH(JFrame frm,Customer khSelect) throws MalformedURLException, RemoteException, NotBoundException {
		
		this.khSelect = khSelect;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1012, 672);
		setLocationRelativeTo(null);
		setTitle("Nhà thuốc T3");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon icLamMoi = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.blue);
		String ip ="";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		hoaDonDao =  (OrderDao) Naming.lookup("rmi://"+ip+":9999/hoaDonDao");
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 998, 651);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, -12, 1003, 671);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTieude = new JLabel("Chi tiết hóa đơn");
		lblTieude.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblTieude.setBounds(10, 20, 353, 48);
		panel_1.add(lblTieude);
		
		JPanel pThuoc = new JPanel();
		pThuoc.setBounds(10, 335, 983, 282);
		panel_1.add(pThuoc);
		pThuoc.setBorder(new TitledBorder(new LineBorder(new Color(91, 155, 213)), "Danh sách thuốc", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		pThuoc.setBackground(new Color(255,255,255,10));
		pThuoc.setLayout(null);
		
		JScrollPane dsThuoc = new JScrollPane();
		dsThuoc.setBounds(10, 20, 963, 252);
		pThuoc.add(dsThuoc);
		
		
		String column[] = { "Tên thuốc","Loại thuốc","Số lượng","Đơn giá","Tổng tiền"};
	    modelthuoc1 = new DefaultTableModel(column, 0);
		tbldsThuoc = new JTable(modelthuoc1);
		dsThuoc.setViewportView(tbldsThuoc);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);

		tbldsThuoc.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tbldsThuoc.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tbldsThuoc.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		
//		tbldsThuoc.getColumnModel().getColumn(3).setCellRenderer(center);
		
		JTableHeader tbHeader1 = tbldsThuoc.getTableHeader();
		tbHeader1.setBackground(new Color(91, 155, 213,80));
		tbHeader1.setForeground(Color.CYAN);
		tbHeader1.setFont(new Font("SansSerif", Font.BOLD, 20));

//		
		tbldsThuoc.setSelectionBackground(new Color(91, 155, 213,30));
		tbldsThuoc.setSelectionForeground(new Color(91, 155, 213));
		tbldsThuoc.setRowHeight(30);
		
		lblthanhtien = new JLabel("0VNĐ");
		lblthanhtien.setForeground(Color.RED);
		lblthanhtien.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblthanhtien.setBounds(846, 627, 167, 20);
		panel_1.add(lblthanhtien);
		
		JLabel lblSubThanhTien = new JLabel("Thành tiền:");
		lblSubThanhTien.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblSubThanhTien.setBounds(736, 627, 101, 20);
		panel_1.add(lblSubThanhTien);
		
		JPanel pKH = new JPanel();
		pKH.setBounds(10, 79, 983, 246);
		panel_1.add(pKH);
		pKH.setBorder(new TitledBorder(new LineBorder(new Color(91, 155, 213)), "Danh sách khách hàng", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		pKH.setBackground(new Color(255,255,255,10));
		pKH.setLayout(null);
		
		JScrollPane dsKH = new JScrollPane();
		dsKH.setBounds(10, 20, 963, 201);
		pKH.add(dsKH);
		
		String columnKH[] = { "Mã hóa đơn","Tên khách hàng","Số điện thoại","Ngày mua"};
	     modelKhachhang1 = new DefaultTableModel(columnKH, 0);
		tbldsKH = new JTable(modelKhachhang1);
		dsKH.setViewportView(tbldsKH);
		
		tbldsKH.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		
		JTableHeader tbHeader = tbldsKH.getTableHeader();
		tbHeader.setBackground(new Color(91, 155, 213,80));
		tbHeader.setForeground(Color.CYAN);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		
		tbldsKH.setSelectionBackground(new Color(91, 155, 213,30));
		tbldsKH.setSelectionForeground(new Color(91, 155, 213));
		tbldsKH.setRowHeight(30);
		
		btnLammoi = new JButton("Làm mới");
		btnLammoi.setToolTipText("Làm mới danh sách thuốc");
		btnLammoi.setBackground(Color.CYAN);
		btnLammoi.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnLammoi.setBounds(885, 31, 108, 34);
		panel_1.add(btnLammoi);
		
		JLabel lblNewLabel = new JLabel("New label");
//		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("data\\img\\bg.png"));
		lblNewLabel.setBounds(0, 10, 1039, 661);
		panel_1.add(lblNewLabel);
		
		tbldsKH.addMouseListener(this);
		
		sf = new SimpleDateFormat("dd/MM/yyyy");
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e)
			{
				frm.setVisible(true);
			}
		});
		
		btnLammoi.setIcon(icLamMoi);
		loadData();
//		lblthanhtien +=modelKhachhang1.
		
		btnLammoi.addActionListener(this);
		
		
		
		
	}
	




	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}

	


	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if(o.equals(tbldsKH)) {
			try {
				modelthuoc1.setRowCount(0);
				loadDataThuoc();
			    
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			
			

		}
		
		
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
	Object o = e.getSource();
	if(o.equals(btnLammoi)) {
		modelthuoc1.setRowCount(0);
		lblthanhtien.setText("0");
	}
		
	}
	private void loadData() throws RemoteException{
		
		 List<Order> dsHD = hoaDonDao.getHoaDonTheoMaKH(khSelect.getId());
		 for(Order hd : dsHD) {
			 
			 modelKhachhang1.addRow(new Object[] { hd.getId(), hd.getKhachHang().getTenKhachHang(),hd.getKhachHang().getSdt(),sf.format( hd.getNgayLap())});
		 }
	}
	private void loadDataThuoc() throws RemoteException{
		int row = tbldsKH.getSelectedRow();
		String maHD = modelKhachhang1.getValueAt(row, 0).toString();
		List<Order> dsHD1 = hoaDonDao.getHoaDonTheoMaHD(maHD);
		DecimalFormat df = new DecimalFormat("###,###,###.####");
		for(Order hd : dsHD1) {
			List<OrderDetail> dsCTHD = hd.getDsCTHD();
			 for(OrderDetail cthd : dsCTHD) {
				modelthuoc1.addRow(new Object[] {cthd.getThuoc().getTenThuoc(),cthd.getThuoc().getLoaiThuoc().getTenLoai(),cthd.getSoLuong(),df.format(cthd.getThuoc().getDonGia()),df.format(cthd.getTongTien())});	
			}
		}
		addTongTien();
		
	}
	public void addTongTien() {
		int row = tbldsThuoc.getRowCount();
		double tongTien = 0;
		for(int i =0 ; i<row; i++) {
			tongTien += Double.parseDouble(modelthuoc1.getValueAt(i, 4).toString());
		}
		DecimalFormat df = new DecimalFormat("###,### VNĐ");
		lblthanhtien.setText(df.format(tongTien));
		
	}
}