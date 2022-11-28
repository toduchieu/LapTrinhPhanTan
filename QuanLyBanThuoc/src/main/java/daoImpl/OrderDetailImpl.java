package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import dao.OrderDetailDao;
import entity.OrderDetail;
import util.HibernateUtil;

public class OrderDetailImpl extends UnicastRemoteObject implements OrderDetailDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6341664070505503845L;

	private EntityManager em;
	
	public OrderDetailImpl() throws RemoteException{	
		em = HibernateUtil.getInstance().getEntityManager();	
	}
	
	public boolean addOrderDetail(OrderDetail orderDetail) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
				em.merge(orderDetail);			
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}		
		return false;
	}

}
