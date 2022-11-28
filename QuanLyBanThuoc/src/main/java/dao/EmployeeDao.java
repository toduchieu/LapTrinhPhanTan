package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Employee;

public interface EmployeeDao extends Remote {
	public boolean addEmployee(Employee employee) throws RemoteException;
	
	public List<Employee> getAllEmployee() throws RemoteException;
	
	public boolean updateEmployee(Employee employee) throws RemoteException;
	
	public List<Employee> search(String text) throws RemoteException;
	
	public Employee getEmployeeByEmployeeId(String employeeId) throws RemoteException;
	
}
