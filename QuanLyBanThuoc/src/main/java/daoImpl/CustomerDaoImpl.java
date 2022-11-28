package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import dao.CustomerDao;
import entity.Customer;
import util.HibernateUtil;

public class CustomerDaoImpl extends UnicastRemoteObject implements CustomerDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6664970320028235951L;
	private EntityManager em;

	public CustomerDaoImpl() throws RemoteException {
		em = HibernateUtil.getInstance().getEntityManager();
	}
	
	public boolean addCustomer(Customer customer) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(customer);	
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}
	
	public Customer getCustomerByNumberPhone(String numberPhone) throws RemoteException {
		
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			Customer kh  = (Customer) em.createNativeQuery("db.dsKhachHang.find({'number_phone' : '"+numberPhone+"'})",Customer.class).getSingleResult();
			tr.commit();		
			return kh;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}	
		return null;
	}

	public List<Customer> getAllCustomer() throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			@SuppressWarnings("unchecked")
			List<Customer> customers =  em.createNativeQuery("db.Customers.find({})",Customer.class).getResultList();
			tr.commit();
			return customers;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		
		return null;
	}

	public boolean updateCustomer(Customer customer) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.merge(customer);			
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}

}
