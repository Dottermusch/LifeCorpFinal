package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DEMO_PRODUCT_INFO database table.
 * 
 */
@Entity
@Table(name="DEMO_PRODUCT_INFO", schema="TESTUSER")
@NamedQuery(name="DemoProductInfo.findAll", query="SELECT d FROM DemoProductInfo d")
public class DemoProductInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy=GenerationType.AUTO)
	@SequenceGenerator(name="SEQ_GEN", sequenceName="DEMO_PROD_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")

	@Column(name="PRODUCT_ID")
	private long productId;

	private String category;

	private String filename;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="IMAGE_LAST_UPDATE")
	private Date imageLastUpdate;

	@Column(name="LIST_PRICE")
	private BigDecimal listPrice;

	private String mimetype;

	@Column(name="PRODUCT_AVAIL")
	private String productAvail;

	@Column(name="PRODUCT_DESCRIPTION")
	private String productDescription;

	@Lob
	@Column(name="PRODUCT_IMAGE")
	private byte[] productImage;

	@Column(name="PRODUCT_NAME")
	private String productName;

	//bi-directional many-to-one association to DemoOrderItem
	@OneToMany(mappedBy="demoProductInfo", fetch=FetchType.EAGER)
	private List<DemoOrderItem> demoOrderItems;

	public DemoProductInfo() {
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getImageLastUpdate() {
		return this.imageLastUpdate;
	}

	public void setImageLastUpdate(Date imageLastUpdate) {
		this.imageLastUpdate = imageLastUpdate;
	}

	public BigDecimal getListPrice() {
		return this.listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public String getMimetype() {
		return this.mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getProductAvail() {
		return this.productAvail;
	}

	public void setProductAvail(String productAvail) {
		this.productAvail = productAvail;
	}

	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public byte[] getProductImage() {
		return this.productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<DemoOrderItem> getDemoOrderItems() {
		return this.demoOrderItems;
	}

	public void setDemoOrderItems(List<DemoOrderItem> demoOrderItems) {
		this.demoOrderItems = demoOrderItems;
	}

	public DemoOrderItem addDemoOrderItem(DemoOrderItem demoOrderItem) {
		getDemoOrderItems().add(demoOrderItem);
		demoOrderItem.setDemoProductInfo(this);

		return demoOrderItem;
	}

	public DemoOrderItem removeDemoOrderItem(DemoOrderItem demoOrderItem) {
		getDemoOrderItems().remove(demoOrderItem);
		demoOrderItem.setDemoProductInfo(null);

		return demoOrderItem;
	}

}