package serverApp;

//import java.net.InetAddress;
//import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.NamingException;

import dao.OrderDetailDao;
import dao.OrderDao;
import dao.CustomerDao;
import dao.TypeOfBookDao;
import dao.EmployeeDao;
import dao.AccountDao;
import dao.BookDao;
import daoImpl.OrderDetailImpl;
import daoImpl.OrderImpl;
import daoImpl.CustomerDaoImpl;
import daoImpl.TypeOfBookDaoImpl;
import daoImpl.EmployeeDaoImpl;
import daoImpl.AccountDaoImpl;
import daoImpl.BookDaoImpl;



public class App {
	public static void main(String[] args) throws RemoteException, NamingException {
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			System.setProperty("java.security.policy","rmi/ManageBook.policy");
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			LocateRegistry.createRegistry(9999);
			OrderDetailDao orderDetailDao = new OrderDetailImpl();
			OrderDao orderDao = new OrderImpl();
			CustomerDao customerDao = new CustomerDaoImpl();
			TypeOfBookDao typeOfBookDao = new TypeOfBookDaoImpl();
			EmployeeDao employeeDao = new EmployeeDaoImpl();
			AccountDao accountDao = new AccountDaoImpl();
			BookDao bookDao = new BookDaoImpl();
			
			Naming.bind("rmi://localhost:9999/orderDetailDao", orderDetailDao);
			Naming.bind("rmi://localhost:9999/orderDao", orderDao);
			Naming.bind("rmi://localhost:9999/customerDao", customerDao);
			Naming.bind("rmi://localhost:9999/typeOfBookDao", typeOfBookDao);
			Naming.bind("rmi://localhost:9999/employeeDao", employeeDao);
			Naming.bind("rmi://localhost:9999/accountDao", accountDao);
			Naming.bind("rmi://localhost:9999/bookDao", bookDao);
			
			System.out.println("Ready...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
