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
		Employee employee1 = new Employee("NV1", "Tô Đức Hiếu", "Nam", new Date(2000-1900,4-1,20), "0966105479", "60 Thống Nhất, p10, Q.Gò Vấp","Quản lý", 5000000, "Đang làm việc", new Account("NV1", "123456"));
		employeeDao.addEmployee(employee1);
//		Employee nv2 = new Employee("NV2", "Nguyá»…n Tuáº¥n Thanh", "Nam", new Date(2001-1900,5-1,20), "0374779028", "56 Ä�Æ°á»�ng sá»‘ 4, p Hiá»‡p BÃ¬nh PhÆ°á»›c, Q.Thá»§ Ä�á»©c","NhÃ¢n viÃªn bÃ¡n hÃ ng", 5000000, "Ä�ang lÃ m viá»‡c", new Account("NV2", "123"));
//		nhanVienDao.addNhanVien(nv2);
//		
//		Employee nv3 = new Employee("NV3", "Pháº¡m Thá»‹ Hoa", "Ná»¯", new Date(2000-1900,6-1,20), "0966712345", "170 Thá»‘ng Nháº¥t, p10, Q.GÃ² Váº¥p","NhÃ¢n viÃªn bÃ¡n hÃ ng", 5000000, "Ä�Ã£ nghá»‰ viá»‡c", new Account("NV3", "123"));
//		nhanVienDao.addNhanVien(nv3);

		//customer
		Customer customer1 = new Customer("Tô Đức Hiếu", "Nam", new Date(2001-1900,3-1,01), "0708985897");
		customerDao.addCustomer(customer1);
//		Customer kh2 = new Customer("Ä�oÃ n Thá»‹ Thá»§y", "Ná»¯", new Date(2001-1900,4-1,01), "0708985977");
//		khachHangDao.addKhachHang(kh2);
//		Customer kh3 = new Customer("Nguyá»…n CÃ´ng Danh", "Nam", new Date(1999-1900,3-1,01), "0374479123");
//		khachHangDao.addKhachHang(kh3);

		//type of book
		TypeOfBook type1=new TypeOfBook("Sách Truyện, tiểu thuyết");
		TypeOfBook type2=new TypeOfBook("Sách Giáo trình");
		TypeOfBook type3=new TypeOfBook("Sách Văn học nghệ thuật");
		TypeOfBook type4=new TypeOfBook("Sách Tâm lý, tâm linh, tôn giáo");
//		TypeOfBook lt5=new TypeOfBook("Thuá»‘c da liá»…u");
//		TypeOfBook lt6=new TypeOfBook("Thuá»‘c giáº£m cÃ¢n");
//		TypeOfBook lt7=new TypeOfBook("Thuá»‘c Máº¯t/Tai/MÅ©i");
//		TypeOfBook lt8=new TypeOfBook("Thuá»‘c tiÃªu hÃ³a");
//		TypeOfBook lt9=new TypeOfBook("Thuá»‘c tháº§n kinh");
//		TypeOfBook lt10=new TypeOfBook("Giáº£m Ä‘au, háº¡ sá»‘t");
		
		typeOfBookDao.addTypeOfBook(type4);
		typeOfBookDao.addTypeOfBook(type4);
		typeOfBookDao.addTypeOfBook(type4);
		typeOfBookDao.addTypeOfBook(type4);
//		loaiThuocDao.addLoaiThuoc(lt5);
//		loaiThuocDao.addLoaiThuoc(lt6);
//		loaiThuocDao.addLoaiThuoc(lt7);
//		loaiThuocDao.addLoaiThuoc(lt8);
//		loaiThuocDao.addLoaiThuoc(lt9);
//		loaiThuocDao.addLoaiThuoc(lt10);

		
		
		//book
		Book book1 = new Book("Book1 ", 75000.00, 500,"Nguyễn Du", "Kim Đồng", type1);
		Book book2 = new Book("Book2 ", 85000.00, 30, "Nguyễn Du", "Kim Đồng", type2);
		Book book3 = new Book("Book3 ", 33000.00, 130,"Nguyễn Du", "Kim Đồng", type3);
		bookDao.addBook(book1);
		bookDao.addBook(book2);
		bookDao.addBook(book3);
	
	}
}
