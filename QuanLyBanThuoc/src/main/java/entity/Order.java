package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.bson.types.ObjectId;

@Entity
@Table(name="Orders")
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4430769447069649083L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ObjectId id;
	@Column(name= "date_founded")
	private Date dateFounded;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ElementCollection(fetch=FetchType.EAGER)
	private List<OrderDetail> OrderDetails;

	public Date getDateFounded() {
		return dateFounded;
	}

	public void setDateFounded(Date dateFounded) {
		this.dateFounded = dateFounded;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<OrderDetail> getOrderDetails() {
		return OrderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		OrderDetails = orderDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Order(ObjectId id, Date dateFounded, Customer customer, Employee employee, List<OrderDetail> orderDetails) {
		super();
		this.id = id;
		this.dateFounded = dateFounded;
		this.customer = customer;
		this.employee = employee;
		OrderDetails = orderDetails;
	}

	public Order(Date dateFounded, Customer customer, Employee employee, List<OrderDetail> orderDetails) {
		super();
		this.dateFounded = dateFounded;
		this.customer = customer;
		this.employee = employee;
		OrderDetails = orderDetails;
	}

	public Order() {
		super();
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", dateFounded=" + dateFounded + ", employeeId=" + employee + ", customerId="
				+ customer + ", OrderDetails=" + OrderDetails + "]";
	}

}
