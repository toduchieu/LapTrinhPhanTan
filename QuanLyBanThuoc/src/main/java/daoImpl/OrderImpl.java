package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.bson.types.ObjectId;

import dao.OrderDao;
import entity.Order;
import util.HibernateUtil;

public class OrderImpl extends UnicastRemoteObject implements OrderDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6500597230454124721L;
	private EntityManager em;
	public OrderImpl() throws RemoteException {
		em = HibernateUtil.getInstance().getEntityManager();
		
	}
	
	public boolean addOrder(Order order) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.merge(order);
			
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}		
		return false;
	}
	
	public List<Order> getHoaDonTheoNgay(Date ngayDen, Date ngayKT) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			Format sf = new SimpleDateFormat("yyyy-MM-dd");
			String query = "db.dsHoaDon.aggregate([{'$match' : {'$and' : [{'ngay_Lap' : {'$lte' : ISODate('"+sf.format(ngayKT)+"')}},{'ngay_Lap' : {'$gte' : ISODate('"+sf.format(ngayDen)+"')}}]}}])";
			@SuppressWarnings("unchecked")
			List<Order> ldHD = em.createNativeQuery(query,Order.class).getResultList();
			
			
			
			tr.commit();
			return ldHD;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return null;
	}
		
	public List<Order> getOrderByDateFounded(Date dateFounded) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			Format sf = new SimpleDateFormat("yyyy-MM-dd");
			String query = "db.Orders.aggregate([{'$match' : {'date_founded' : {'$eq' : ISODate('"+sf.format(dateFounded)+"')}}}])";
			@SuppressWarnings("unchecked")
			List<Order> orders = em.createNativeQuery(query,Order.class).getResultList();
		tr.commit();
			return orders;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}	
		return null;
	}

	public List<Order> getOrderByCustomerId(ObjectId customerId) throws RemoteException {
		
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			String query = "db.Orders.find({customer_id :  ObjectId('"+customerId.toString()+"')})";
			@SuppressWarnings("unchecked")
			List<Order> orders = em.createNativeQuery(query,Order.class).getResultList();
			
			tr.commit();
			return orders;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return null;
	}
	public List<Order> getOrderTheoOrderId(String orderId) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			String query = "db.Orders.find({_id :  ObjectId('"+orderId.toString()+"')})";
			@SuppressWarnings("unchecked")
			List<Order> orders = em.createNativeQuery(query,Order.class).getResultList();
			
			tr.commit();
			return orders;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return null;
	}
	
}
