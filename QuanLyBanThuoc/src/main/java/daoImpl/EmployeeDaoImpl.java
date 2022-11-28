package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import dao.EmployeeDao;
import entity.Employee;
import util.HibernateUtil;

public class EmployeeDaoImpl extends UnicastRemoteObject implements EmployeeDao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -208819529374076682L;
	private EntityManager em;

	public EmployeeDaoImpl() throws RemoteException {
		em = HibernateUtil.getInstance().getEntityManager();
	}

	
	public boolean addEmployee(Employee employee) throws RemoteException {
		EntityTransaction tr = em.getTransaction();	
		try {
			tr.begin();
				em.persist(employee);
			
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}	
		return false;
	}


	public List<Employee> getAllEmployee() throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {		
			tr.begin();
			@SuppressWarnings("unchecked")
			List<Employee> employee =  em.createNativeQuery("db.Employees.find({'status' : 'Đang làm việc'})",Employee.class).getResultList();	
			tr.commit();
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return null;
	}



	public boolean updateEmployee(Employee Employee) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
				em.merge(Employee);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}


	public Employee getEmployeeByEmployeeId(String employeeId) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		
		try {
			tr.begin();
			String query = "db.Employees.find({'employee_id' : '"+employeeId+"' })";
				Employee employee = (Employee) em.createNativeQuery(query,Employee.class).getSingleResult();
			tr.commit();
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}	
		return null;
	}


	public List<Employee> search(String text) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			
			tr.begin();
			String query = "db.Employees.find({$text:{$search:'"+text+"' }})";
			@SuppressWarnings("unchecked")
			List<Employee> employee =  em.createNativeQuery(query,Employee.class).getResultList();
			tr.commit();
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return null;
	}

}
