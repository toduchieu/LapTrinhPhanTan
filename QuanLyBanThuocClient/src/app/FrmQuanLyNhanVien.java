package app;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
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
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;

import dao.EmployeeDao;
import entity.Account;
import entity.Employee;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmQuanLyNhanVien extends JPanel implements ActionListener, MouseListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txtSearch;
	private JTextField txtFullName;
	private JTextField txtAddress;
	private JTextField txtTel;
	private JTable tbl;
	private JLabel lblNewLabel;
	private JComboBox<String> cboGender;
	private JComboBox<String> cboWork;
	private JButton btnRefresh;
	private JButton btnCancel;
	private JButton btnEdit;
	private JButton btnAdd;
	private JButton btnSearch;
	private JFrame fMain;

	private DefaultTableModel employeeModel;
	private JDateChooser dateDayOfBirth;
	private EmployeeDao employeeDao;
	private JLabel lblSalary;
	private JTextField txtSalary;
	private List<Employee> employees;
	private int currentIndex = 0;

	private Date now;
	private SimpleDateFormat simpleDateFormat;

	public FrmQuanLyNhanVien() throws MalformedURLException, RemoteException, NotBoundException {
		initialize(fMain);
	}

	@SuppressWarnings("deprecation")
	private void initialize(JFrame fMain) throws MalformedURLException, RemoteException, NotBoundException {

		final String RMI_SERVER_IP = "localhost";
		employeeDao = (EmployeeDao) Naming.lookup("rmi://" + RMI_SERVER_IP + ":9999/employeeDao");

		frame = new JFrame();
		frame.setBounds(0, 0, 1043, 736);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		IconFontSwing.register(FontAwesome.getIconFont());

		simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		JPanel pMain = new JPanel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(-62, -132, 1102, 848);
		add(pMain);
		pMain.setLayout(null);

		Icon icoAdd = IconFontSwing.buildIcon(FontAwesome.PLUS, 20, new Color(0, 176, 80));
		Icon icoDate = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, new Color(91, 155, 213));
		Icon icoSearch = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.black);
		Icon icoRefresh = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.blue);
		Icon icoDelete = IconFontSwing.buildIcon(FontAwesome.TIMES, 20, Color.red);
		Icon icoEdit = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.darkGray);

		JLabel lblEmployeeManagement = new JLabel("HOA");
		lblEmployeeManagement.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblEmployeeManagement.setBounds(91, 160, 236, 53);
		pMain.add(lblEmployeeManagement);

		JLabel lblSearch = new JLabel("Tìm kiếm:");
		lblSearch.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblSearch.setBounds(608, 176, 75, 28);
		pMain.add(lblSearch);
		txtSearch = new JTextField();
		txtSearch.setColumns(10);
		txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSearch.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txtSearch.setBounds(709, 174, 228, 32);
		txtSearch.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(txtSearch);

		btnSearch = new JButton("Tìm");
		btnSearch.setToolTipText("Tìm nhân viên");
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSearch.setBackground(new Color(41, 242, 255));
		btnSearch.setBounds(949, 176, 108, 32);
		pMain.add(btnSearch);

		JLabel lblFullName = new JLabel("Họ và tên:");
		lblFullName.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblFullName.setBounds(91, 223, 107, 32);
		pMain.add(lblFullName);

		txtFullName = new JTextField();
		txtFullName.setColumns(10);
		txtFullName.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtFullName.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txtFullName.setBounds(180, 223, 202, 32);
		txtFullName.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(txtFullName);

		JLabel lblTel = new JLabel("Số điện thoại:");
		lblTel.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTel.setBounds(407, 223, 107, 32);
		pMain.add(lblTel);

		JLabel lblDateOfBirth = new JLabel("Ngày sinh:");
		lblDateOfBirth.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblDateOfBirth.setBounds(750, 219, 107, 32);
		pMain.add(lblDateOfBirth);

		dateDayOfBirth = new JDateChooser();
		dateDayOfBirth.setBounds(867, 227, 190, 28);
		dateDayOfBirth.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dateDayOfBirth.setDateFormatString("dd/MM/yyyy");
		dateDayOfBirth.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		dateDayOfBirth.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(dateDayOfBirth);

		JLabel lblAddress = new JLabel("Địa chỉ:");
		lblAddress.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblAddress.setBounds(91, 265, 107, 32);
		pMain.add(lblAddress);

		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtAddress.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txtAddress.setBounds(180, 265, 202, 33);
		txtAddress.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(txtAddress);

		JLabel lblGender = new JLabel("Giới tính");
		lblGender.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblGender.setBounds(407, 265, 107, 32);
		pMain.add(lblGender);

		cboGender = new JComboBox<String>();
		cboGender.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboGender.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		cboGender.setBounds(511, 265, 190, 32);
		cboGender.setBorder(new LineBorder(new Color(91, 155, 213)));
		cboGender.addItem("Nam");
		cboGender.addItem("Nữ");

		pMain.add(cboGender);

		JLabel lblWork = new JLabel("Chức vụ");
		lblWork.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblWork.setBounds(750, 261, 107, 32);
		pMain.add(lblWork);

		cboWork = new JComboBox<String>();
		cboWork.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboWork.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		cboWork.setBounds(867, 265, 188, 32);
		cboWork.setBorder(new LineBorder(new Color(91, 155, 213)));
		cboWork.addItem("Quản lý");
		cboWork.addItem("Nhân viên bán hàng");
		pMain.add(cboWork);

		btnAdd = new JButton("Thêm");
		btnAdd.setToolTipText("Thêm nhân viên");
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnAdd.setBackground(Color.CYAN);
		btnAdd.setBounds(180, 359, 180, 33);
		pMain.add(btnAdd);

		txtTel = new JTextField();
		txtTel.setColumns(10);
		txtTel.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtTel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txtTel.setBounds(511, 223, 190, 32);
		txtTel.setBorder(new LineBorder(new Color(91, 155, 213)));
		pMain.add(txtTel);

		lblSalary = new JLabel("Lương");
		lblSalary.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblSalary.setBounds(91, 307, 107, 32);
		pMain.add(lblSalary);

		txtSalary = new JTextField();
		txtSalary.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSalary.setColumns(10);
		txtSalary.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		txtSalary.setBorder(new LineBorder(new Color(91, 155, 213)));
		txtSalary.setBounds(180, 308, 202, 33);
		pMain.add(txtSalary);

		btnEdit = new JButton("Sửa");
		btnEdit.setToolTipText("Cập nhật nhân viên");
		btnEdit.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnEdit.setBackground(Color.CYAN);
		btnEdit.setBounds(388, 359, 186, 33);
		pMain.add(btnEdit);

		btnRefresh = new JButton("Làm mới");
		btnRefresh.setToolTipText("Làm mới giao diện");
		btnRefresh.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnRefresh.setBackground(Color.CYAN);
		btnRefresh.setBounds(810, 359, 180, 33);
		pMain.add(btnRefresh);

		btnCancel = new JButton("Xóa nhân viên");
		btnCancel.setToolTipText("Xóa nhân viên");
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnCancel.setBackground(Color.CYAN);
		btnCancel.setBounds(595, 359, 180, 33);
		pMain.add(btnCancel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(83, 414, 974, 398);
		pMain.add(scrollPane);

		String column[] = { "Mã nhân viên", "Họ tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Chứ vụ", "Số điện thoại",
				"Lương", "Trạng Thái" };
		employeeModel = new DefaultTableModel(column, 0);
		tbl = new JTable(employeeModel);
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
		now = new Date(d.getYear() - 1900, d.getMonthValue() - 1, d.getDayOfMonth());
		dateDayOfBirth.setDate(now);

		btnSearch.setIcon(icoSearch);
		btnAdd.setIcon(icoAdd);
		btnEdit.setIcon(icoEdit);
		btnRefresh.setIcon(icoRefresh);
		btnCancel.setIcon(icoDelete);
		dateDayOfBirth.setIcon((ImageIcon) icoDate);

		loadData();

		btnRefresh.addActionListener(this);
		btnCancel.addActionListener(this);
		btnAdd.addActionListener(this);
		btnEdit.addActionListener(this);
		btnCancel.addActionListener(this);
		btnSearch.addActionListener(this);

		txtFullName.setText("Nguyễn Thị Mai");
		txtAddress.setText("TP Hồ Chí Minh");
		txtSalary.setText("5000000");
		txtTel.setText("0918050354");

		tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row = tbl.getSelectedRow();
				try {
					if (row == -1)
						return;

					txtFullName.setText(employeeModel.getValueAt(row, 1).toString());
					txtTel.setText(employeeModel.getValueAt(row, 6).toString());
					Date dateOfBirth = null;
					try {
						dateOfBirth = simpleDateFormat.parse(employeeModel.getValueAt(row, 3).toString());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					dateDayOfBirth.setDate(dateOfBirth);

					txtAddress.setText(employeeModel.getValueAt(row, 4).toString());
					cboGender.setSelectedItem(employeeModel.getValueAt(row, 2));
					cboWork.setSelectedItem(employeeModel.getValueAt(row, 5));
					txtSalary.setText(employeeModel.getValueAt(row, 7).toString().replaceAll("[-+.^:,]", ""));

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

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

	public boolean validata() {
		String fullName = txtFullName.getText();
		String tel = txtTel.getText();
		String address = txtAddress.getText();
		String salary = txtSalary.getText();

		if (fullName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Họ tên không được trống");
			txtFullName.requestFocus();
			return false;
		} else if (!fullName.matches("[\\p{Lu}[A-Z]][\\p{L}[a-z]]*(\\s+[\\p{Lu}[A-Z]][\\p{L}[a-z]]*)*")) {
			JOptionPane.showMessageDialog(this,
					"Họ tên bắt đầu bằng chữ in hoa, không chứ số và không có ký tự đắc biệt");
			txtFullName.requestFocus();
			return false;
		}

		if (tel.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Số điện thoại không được trống");
			txtFullName.requestFocus();
			return false;
		} else if (!tel.matches("^[0][0-9]{9}$")) {
			JOptionPane.showMessageDialog(this, "Số điện thoại gồm 10 số");
			txtTel.requestFocus();
			return false;
		}

		if (address.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Địa chỉ không được trống");
			txtFullName.requestFocus();
		} else if (!address
				.matches("[\\p{Lu}[A-Za-z0-9,.]][\\p{L}[a-z0-9,.]]*(\\s+[\\p{Lu}[A-Za-z0-9,.]][\\p{L}[a-z0-9,.]]*)*")) {
			JOptionPane.showMessageDialog(this, "Địa chỉ bắt đầu bằng chữ cái hoặc số và không có ký tự đắc biệt");
			txtAddress.requestFocus();
			return false;
		}

		double dSalary = Double.parseDouble(salary);

		if (salary.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Lương không được để trống!");
			txtSalary.requestFocus();
		} else if (dSalary < 0) {
			JOptionPane.showMessageDialog(this, "Lương không được nhập âm");
			txtSalary.requestFocus();
			return false;
		}

		return true;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnRefresh)) {
			txtSearch.setText("");
			txtAddress.setText("");
			txtFullName.setText("");
			txtFullName.requestFocus();
			txtTel.setText("");
			txtSalary.setText("");
			cboGender.setSelectedIndex(0);
			cboWork.setSelectedIndex(0);
			dateDayOfBirth.setDate(now);
			try {
				loadData();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			loadDataFromDatabase(employees);
		}

		if (o.equals(btnAdd)) {
			if (validata()) {
				try {
					addEmployee();
					loadDataFromDatabase(employees);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (o.equals(btnEdit)) {
			try {
				updateEmployee();
				loadDataFromDatabase(employees);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

		if (o.equals(btnCancel)) {

			try {
				cancelEmployee();
				loadDataFromDatabase(employees);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

		if (o.equals(btnSearch)) {

			String search = txtSearch.getText().trim();
			if (search.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm");
			} else {
				try {
					loadDataSearch();
					loadDataFromDatabase(employees);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			if (employeeModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "Không có kết quả tìm kiếm");
			}
		}
	}

	private void loadData() throws RemoteException {
		employees = new ArrayList<Employee>();
		employees = employeeDao.getAllEmployee();
		DecimalFormat df = new DecimalFormat("###,###,###.####");

		for (Employee employee : employees) {
			employeeModel.addRow(new Object[] { employee.getEmployeeId(), employee.getEmployeeName(),
					employee.getGender(), simpleDateFormat.format(employee.getDayOfBirth()), employee.getAddRess(),
					employee.getPosition(), employee.getNumberPhone(), df.format(employee.getSalary()),
					employee.getStatus() });
		}
	}

	private void loadDataSearch() throws RemoteException {
		employees = new ArrayList<Employee>();
		String text = txtSearch.getText();
//		employees = employeeDao.getSearch(text);
//		DecimalFormat df = new DecimalFormat("###,###,###.####");
//		DateFormat dfd = new SimpleDateFormat("dd/MM/yyyy");
//		for (Employee employee : employees) {
//			employeeModel
//					.addRow(new Object[] { employee.getEmployeeId(), employee.getEmployeeName(), employee.getGender(),
//							dfd.format(employee.getDayOfBirth()), employee.getAddRess(), employee.getPosition(),
//							employee.getNumberPhone(), df.format(employee.getSalary()), employee.getStatus() });
//		}
	}

	private void loadDataFromDatabase(List<Employee> employees) {
		employeeModel.getDataVector().removeAllElements();
		employeeModel.fireTableDataChanged();
		new Thread(() -> {
			for (Employee employee : employees) {
				SwingUtilities.invokeLater(() -> {
					DecimalFormat df = new DecimalFormat("###,###,###.####");
					DateFormat dfd = new SimpleDateFormat("dd/MM/yyyy");
					employeeModel.addRow(
							new Object[] { employee.getEmployeeId(), employee.getEmployeeName(), employee.getGender(),
									dfd.format(employee.getDayOfBirth()), employee.getAddRess(), employee.getPosition(),
									employee.getNumberPhone(), df.format(employee.getSalary()), employee.getStatus() });
				});
			}
		}).start();
		;
	}

	public void addEmployee() throws RemoteException {
//		int n = employeeDao.totalEmployee();
//		int x = n + 1;
//		String employeeId = "NV" + x;
		String fullName = txtFullName.getText();
		String tel = txtTel.getText();
		Date dateOfBirth = dateDayOfBirth.getDate();
		String address = txtAddress.getText();
		String gender = cboGender.getSelectedItem().toString();
		String work = cboWork.getSelectedItem().toString();
		String salary = txtSalary.getText();
		double dSalary = Double.parseDouble(salary);
		String status = "Đang làm việc";

//		Employee employee = new Employee(employeeId, fullName, gender, dateOfBirth, tel, address, work, dSalary, status,
//				new Account(employeeId, "123"));
//		Employee employeeSearch = employeeDao.getEmployeeByTel(tel);
//		if (employeeSearch == null) {
//			if (employeeDao.addEmployee(employee)) {
//				JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công");
//				loadData();
//			}
//		} else
//			JOptionPane.showMessageDialog(this, "Số điện thoại đã được đăng ký");
	}

	public void updateEmployee() throws RemoteException {
		int row = tbl.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên");
		} else {
			row += currentIndex;
			String employeeId = (String) employeeModel.getValueAt(row, 0);
			String fullName = txtFullName.getText();
			String tel = txtTel.getText();
			Date dateOfBirth = dateDayOfBirth.getDate();
			String address = txtAddress.getText();
			String gender = cboGender.getSelectedItem().toString();
			String work = cboWork.getSelectedItem().toString();
			String salary = txtSalary.getText();
			double dSalary = Double.parseDouble(salary);

			Employee employee = employeeDao.getEmployeeByEmployeeId(employeeId);
			employee.setEmployeeName(fullName);
			employee.setNumberPhone(tel);
			employee.setDayOfBirth(dateOfBirth);
			employee.setAddRess(address);
			employee.setGender(gender);
			employee.setPosition(work);
			employee.setSalary(dSalary);

			employeeDao.updateEmployee(employee);
			JOptionPane.showMessageDialog(this, "Sửa thành công");
			loadData();
		}
	}

	public void cancelEmployee() throws RemoteException {
		int row = tbl.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên");

		} else {
			row += currentIndex;
			String employeeId = (String) employeeModel.getValueAt(row, 0);
			String status = "Đã nghỉ việc";
			Employee employee = employeeDao.getEmployeeByEmployeeId(employeeId);

			employee.setStatus(status);
			int click = JOptionPane.showConfirmDialog(this, "Bạn muốn hủy tài khoản này", "Cảnh báo",
					JOptionPane.YES_NO_OPTION);
			if (click == JOptionPane.YES_OPTION) {
				employeeDao.updateEmployee(employee);
				JOptionPane.showMessageDialog(this, "Hủy thành công");
				loadData();
			} else
				return;
		}
	}
	
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

}
