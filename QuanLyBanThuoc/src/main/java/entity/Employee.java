package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.bson.types.ObjectId;
import org.hibernate.ogm.options.shared.IndexOption;
import org.hibernate.ogm.options.shared.IndexOptions;

@Entity
@Table(name = "Employees",indexes = {
		@Index(columnList = "numberphone,employee_name",name="numberphone_name_Indexes"),
		
})
@IndexOptions(
	    @IndexOption(forIndex = "numberphone_name_Indexes", options = "{text: true}"))

public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6735862733378529418L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ObjectId id;
	@Column(name = "employee_id")
	private String employeeId;
	@Column(name = "employee_name")
	private String employeeName;
	@Column(name = "gender")
	private String gender;
	@Column(name = "day_of_birth")
	private Date dayOfBirth;
	private String numberPhone;
	@Column(name = "add_ress")
	private String addRess;
	private String position;
	private double salary;
	@Column(name = "status")
	private String status;
	
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "account_id")
	private Account account;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(Date dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public String getNumberPhone() {
		return numberPhone;
	}

	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}

	public String getAddRess() {
		return addRess;
	}

	public void setAddRess(String addRess) {
		this.addRess = addRess;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Employee(ObjectId id, String employeeId, String employeeName, String gender, Date dayOfBirth,
			String numberPhone, String addRess, String position, double salary, String status, Account account) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.gender = gender;
		this.dayOfBirth = dayOfBirth;
		this.numberPhone = numberPhone;
		this.addRess = addRess;
		this.position = position;
		this.salary = salary;
		this.status = status;
		this.account = account;
	}

	public Employee(String employeeId, String employeeName, String gender, Date dayOfBirth, String numberPhone,
			String addRess, String position, double salary, String status, Account account) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.gender = gender;
		this.dayOfBirth = dayOfBirth;
		this.numberPhone = numberPhone;
		this.addRess = addRess;
		this.position = position;
		this.salary = salary;
		this.status = status;
		this.account = account;
	}

	public Employee() {
		super();
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeId=" + employeeId + ", employeeName=" + employeeName + ", gender="
				+ gender + ", dayOfBirth=" + dayOfBirth + ", numberPhone=" + numberPhone + ", addRess=" + addRess
				+ ", position=" + position + ", salary=" + salary + ", status=" + status + ", account=" + account + "]";
	}
	
}
