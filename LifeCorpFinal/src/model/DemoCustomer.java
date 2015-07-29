package model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the DEMO_CUSTOMERS database table.
 * 
 */
@Entity
@Table(name="DEMO_CUSTOMERS", schema="TESTUSER")
@NamedQuery(name="DemoCustomer.findAll", query="SELECT d FROM DemoCustomer d")
public class DemoCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue (strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_GEN_CUST", sequenceName="DEMO_CUST_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN_CUST")
	
	@Column(name="CUSTOMER_ID")
	private long customerId;

	@Column(name="CREDIT_LIMIT")
	private BigDecimal creditLimit;

	@Column(name="CUST_CITY")
	private String custCity;

	@Column(name="CUST_EMAIL")
	private String custEmail;

	@Column(name="CUST_FIRST_NAME")
	private String custFirstName;

	@Column(name="CUST_LAST_NAME")
	private String custLastName;

	@Column(name="CUST_PASSWORD")
	private String custPassword;

	@Column(name="CUST_POSTAL_CODE")
	private String custPostalCode;

	@Column(name="CUST_STATE")
	private String custState;

	@Column(name="CUST_STREET_ADDRESS1")
	private String custStreetAddress1;

	@Column(name="CUST_STREET_ADDRESS2")
	private String custStreetAddress2;

	@Column(name="PHONE_NUMBER1")
	private String phoneNumber1;

	@Column(name="PHONE_NUMBER2")
	private String phoneNumber2;

	//bi-directional many-to-one association to DemoOrder
	@OneToMany(mappedBy="demoCustomer", fetch=FetchType.EAGER)
	private List<DemoOrder> demoOrders;

	public DemoCustomer() {
	}

	public long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCreditLimit() {
		return this.creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCustCity() {
		return this.custCity;
	}

	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}

	public String getCustEmail() {
		return this.custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	public String getCustFirstName() {
		return this.custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return this.custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getCustPassword() {
		return this.custPassword;
	}

	public void setCustPassword(String custPassword) {
		this.custPassword = custPassword;
	}

	public String getCustPostalCode() {
		return this.custPostalCode;
	}

	public void setCustPostalCode(String custPostalCode) {
		this.custPostalCode = custPostalCode;
	}

	public String getCustState() {
		return this.custState;
	}

	public void setCustState(String custState) {
		this.custState = custState;
	}

	public String getCustStreetAddress1() {
		return this.custStreetAddress1;
	}

	public void setCustStreetAddress1(String custStreetAddress1) {
		this.custStreetAddress1 = custStreetAddress1;
	}

	public String getCustStreetAddress2() {
		return this.custStreetAddress2;
	}

	public void setCustStreetAddress2(String custStreetAddress2) {
		this.custStreetAddress2 = custStreetAddress2;
	}

	public String getPhoneNumber1() {
		return this.phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return this.phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public List<DemoOrder> getDemoOrders() {
		return this.demoOrders;
	}

	public void setDemoOrders(List<DemoOrder> demoOrders) {
		this.demoOrders = demoOrders;
	}

	public DemoOrder addDemoOrder(DemoOrder demoOrder) {
		getDemoOrders().add(demoOrder);
		demoOrder.setDemoCustomer(this);

		return demoOrder;
	}

	public DemoOrder removeDemoOrder(DemoOrder demoOrder) {
		getDemoOrders().remove(demoOrder);
		demoOrder.setDemoCustomer(null);

		return demoOrder;
	}

}