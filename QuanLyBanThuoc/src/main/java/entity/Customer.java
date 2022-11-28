package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.bson.types.ObjectId;

@Entity
@Table(name = "Customers",indexes = {
		@Index(columnList = "numberphone",name="numberphone_Indexes"),
		@Index(columnList = "customer_name",name="customer_name_Indexes")
})
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 336216348033772704L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ObjectId id;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "gender")
	private String gender;
	@Column(name = "day_of_birth")
	private Date dayOfBirth;
	private String numberPhone;
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Customer(ObjectId id, String customerName, String gender, Date dayOfBirth, String numberPhone) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.gender = gender;
		this.dayOfBirth = dayOfBirth;
		this.numberPhone = numberPhone;
	}

	public Customer(String customerName, String gender, Date dayOfBirth, String numberPhone) {
		super();
		this.customerName = customerName;
		this.gender = gender;
		this.dayOfBirth = dayOfBirth;
		this.numberPhone = numberPhone;
	}

	public Customer() {
		super();
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerName=" + customerName + ", gender=" + gender + ", dayOfBirth="
				+ dayOfBirth + ", numberPhone=" + numberPhone + "]";
	}
	
}
