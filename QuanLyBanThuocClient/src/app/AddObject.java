package app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

import dao.OrderDetailDao;
import dao.OrderDao;
import dao.CustomerDao;
import dao.TypeOfBookDao;
import dao.EmployeeDao;
import dao.AccountDao;
import dao.BookDao;
import entity.Customer;
import entity.TypeOfBook;
import entity.Employee;
import entity.Account;
import entity.Book;

public class AddObject {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			System.setProperty("java.security.policy","rmi/ManageBook.policy");
			System.setSecurityManager(new SecurityManager());
		}

		OrderDetailDao orderDetailDao =  (OrderDetailDao) Naming.lookup("rmi://localhost:9999/orderDetailDao");
		OrderDao orderDao =  (OrderDao) Naming.lookup("rmi://localhost:9999/orderDao");
	    CustomerDao customerDao = (CustomerDao) Naming.lookup("rmi://localhost:9999/customerDao");
		TypeOfBookDao typeOfBookDao =  (TypeOfBookDao) Naming.lookup("rmi://localhost:9999/typeOfBookDao");
		EmployeeDao employeeDao =  (EmployeeDao) Naming.lookup("rmi://localhost:9999/employeeDao");
		AccountDao accountDao =  (AccountDao) Naming.lookup("rmi://localhost:9999/accountDao");
		BookDao bookDao =  (BookDao) Naming.lookup("rmi://localhost:9999/bookDao");
	
		//employee-account
		Employee employee1 = new Employee("NV1", "Tô Đức Hiếu", "Nam", new Date(2000-1900,4-1,20), "0966105479", "60 Thống Nhất, p10, Q.Gò Vấp","Quản lý", 10000000, "Đang làm việc", new Account("NV1", "123456"));
		employeeDao.addEmployee(employee1);
		Employee employee2 = new Employee("NV2", "Lê Xuân Hòa", "Nam", new Date(2001-1900,5-1,20), "0374779028", "56 Quang Trunh, p11, Q.Gò Vấp","Nhân viên bán hàng", 5000000, "Đang làm việc", new Account("NV2", "123456"));
		employeeDao.addEmployee(employee2);	
		Employee employee3 = new Employee("NV3", "Hữu Nghị", "Nam", new Date(2000-1900,6-1,20), "0966712345", "170 Thống Nhất, p10, Q.Gò Vấp","Nhân viên bán hàng", 5000000, "Đang làm việc", new Account("NV3", "123456"));
		employeeDao.addEmployee(employee3);
		Employee employee4 = new Employee("NV4", "Tuấn Vỹ", "Nam", new Date(2000-1900,6-1,20), "0966764582", "10 Nguyễn Văn Bảo, p4, Q.Gò Vấp","Nhân viên bán hàng", 5000000, "Đang làm việc", new Account("NV4", "123456"));
		employeeDao.addEmployee(employee4);

		//customer
		Customer customer1 = new Customer("Tô Đức Hiếu", "Nam", new Date(2001-1900,3-1,01), "0708985897");
		customerDao.addCustomer(customer1);
		Customer customer2 = new Customer("Thuý Vân", "Nữ", new Date(2001-1900,4-1,01), "0708985977");
		customerDao.addCustomer(customer2);
		Customer customer3 = new Customer("Thúy Kiều", "Nữ", new Date(1999-1900,3-1,01), "0374479123");
		customerDao.addCustomer(customer3);
		Customer customer4 = new Customer("Lâm Trọng Hùng", "Nam", new Date(2001-1900,3-1,01), "0378985899");
		customerDao.addCustomer(customer4);

		//type of book
		TypeOfBook type1=new TypeOfBook("Sách Truyện, tiểu thuyết");
		TypeOfBook type2=new TypeOfBook("Sách Giáo trình");
		TypeOfBook type3=new TypeOfBook("Sách Văn học nghệ thuật");
		TypeOfBook type4=new TypeOfBook("Sách Tâm lý, tâm linh, tôn giáo");
		TypeOfBook type5=new TypeOfBook("Sách Khoa học công nghệ – Kinh tế");
		TypeOfBook type6=new TypeOfBook("Sách Chính trị – pháp luật");
		TypeOfBook type7=new TypeOfBook("Sách Văn hóa xã hội – Lịch sử");
		TypeOfBook type8=new TypeOfBook("Sách thiếu nhi");
				
		typeOfBookDao.addTypeOfBook(type1);
		typeOfBookDao.addTypeOfBook(type2);
		typeOfBookDao.addTypeOfBook(type3);
		typeOfBookDao.addTypeOfBook(type4);
		typeOfBookDao.addTypeOfBook(type5);
		typeOfBookDao.addTypeOfBook(type6);
		typeOfBookDao.addTypeOfBook(type7);
		typeOfBookDao.addTypeOfBook(type8);			
				
		//book
		Book book1 = new Book("Có Một Ngày, Bố Mẹ Sẽ Già Đi", 75000.00, 500,"Nhiều tác giả", "NXB Thế Giới", type1);
		Book book2 = new Book("Giáo trình Hình học vi phân", 85000.00, 89, "Nguyễn Doãn Tuấn", "NXB Đại học sư phạm", type2);
		Book book3 = new Book("HẸN GẶP NHAU TRONG VŨ TRỤ", 115000.00, 130,"Jack Cheng", "NXB Trẻ", type3);
		Book book4 = new Book("Sức Mạnh Của Trí Tuệ Tâm Linh", 58000.00,59 ,"Tony Buzan", "NXB Tổng hợp tpHCM", type4);
		Book book5 = new Book("Cuộc Chiến Công Nghệ Số", 81000.00, 190,"Charles Arthur", "NXB Dân Trí", type5);
		Book book6 = new Book("Chính Sách Pháp Luật - Những Vấn Đề Lý Luận Và Thực Tiễn", 215000.00, 200,"GS.TS. Võ Khánh Vinh", "NXB Chính Trị Quốc Gia Sự Thật", type6);
		Book book7 = new Book("Địa Chí Văn Hóa Thành Phố Hồ Chí Minh", 330000.00, 130,"Hội Đồng Khoa Học Xã Hội TPHCM", "NXB Tổng Hợp TPHCM", type7);
		Book book8 = new Book("Xã Hội Việt Nam", 83000.00, 150,"Lương Đức Hiệp", "NXB Tri Thức", type7);
		Book book9 = new Book("Dế mèn phiêu lưu ký", 49000.00, 330,"Tô Hoài", "NXB Tân Dân", type8);
		Book book10 = new Book("Những cuộc phiêu lưu của Pinocchio", 128000.00, 20,"Carlo Collodi", "Libreria Editrice Felice Paggi", type8);
		bookDao.addBook(book1);
		bookDao.addBook(book2);
		bookDao.addBook(book3);
		bookDao.addBook(book4);
		bookDao.addBook(book5);
		bookDao.addBook(book6);
		bookDao.addBook(book7);
		bookDao.addBook(book8);
		bookDao.addBook(book9);
		bookDao.addBook(book10);
	
	}
}
