package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Customer;

public interface CustomerDao extends Remote {
	
	public boolean addCustomer(Customer customer) throws RemoteException;
	
	public Customer getCustomerByNumberPhone(String numberPhone) throws RemoteException;

	public List<Customer> getAllCustomer ()throws RemoteException;

	public boolean updateCustomer(Customer customer) throws RemoteException;
	

}
