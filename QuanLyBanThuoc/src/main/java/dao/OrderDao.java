package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import entity.Order;

public interface OrderDao extends Remote {
	public boolean addOrder(Order order) throws RemoteException;
	
	public List<Order> getOrderByDateFounded(Date dateFounded) throws RemoteException;
	
	public List<Order> getOrderByCustomerId(ObjectId customerId) throws RemoteException;
	
	public List<Order> getOrderTheoOrderId(String orderId) throws RemoteException;
}
