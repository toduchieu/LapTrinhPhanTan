package entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.bson.types.ObjectId;

@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5231787976699307772L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ObjectId id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	@JoinColumn(name = "employee_id")
	private Employee employee;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ObjectId getId() {
		return id;
	}

	public Account(ObjectId id, String username, String password, Employee employee) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.employee = employee;
	}

	public Account(String username, String password, Employee employee) {
		super();
		this.username = username;
		this.password = password;
		this.employee = employee;
	}

	public Account(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Account() {
		super();
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", password=" + password + ", employee=" + employee
				+ "]";
	}
	
}
