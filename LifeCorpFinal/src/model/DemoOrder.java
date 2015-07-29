package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DEMO_ORDERS database table.
 * 
 */
@Entity
@Table(name="DEMO_ORDERS", schema="TESTUSER")
@NamedQuery(name="DemoOrder.findAll", query="SELECT d FROM DemoOrder d")
public class DemoOrder implements Serializable, Comparable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ORDER_ID")
	private long orderId;

	@Temporal(TemporalType.DATE)
	@Column(name="ORDER_TIMESTAMP")
	private Date orderTimestamp;

	@Column(name="ORDER_TOTAL")
	private BigDecimal orderTotal;

	@Column(name="USER_ID")
	private BigDecimal userId;

	//bi-directional many-to-one association to DemoCustomer
	@ManyToOne
	@JoinColumn(name="CUSTOMER_ID")
	private DemoCustomer demoCustomer;

	//bi-directional many-to-one association to DemoOrderItem
	@OneToMany(mappedBy="demoOrder", cascade={CascadeType.PERSIST}, fetch=FetchType.EAGER)
	private List<DemoOrderItem> demoOrderItems;

	public DemoOrder() {
	}

	public long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderTimestamp() {
		return this.orderTimestamp;
	}

	public void setOrderTimestamp(Date orderTimestamp) {
		this.orderTimestamp = orderTimestamp;
	}

	public BigDecimal getOrderTotal() {
		return this.orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public DemoCustomer getDemoCustomer() {
		return this.demoCustomer;
	}

	public void setDemoCustomer(DemoCustomer demoCustomer) {
		this.demoCustomer = demoCustomer;
	}

	public List<DemoOrderItem> getDemoOrderItems() {
		return this.demoOrderItems;
	}

	public void setDemoOrderItems(List<DemoOrderItem> demoOrderItems) {
		this.demoOrderItems = demoOrderItems;
	}

	public DemoOrderItem addDemoOrderItem(DemoOrderItem demoOrderItem) {
		getDemoOrderItems().add(demoOrderItem);
		demoOrderItem.setDemoOrder(this);

		return demoOrderItem;
	}

	public DemoOrderItem removeDemoOrderItem(DemoOrderItem demoOrderItem) {
		getDemoOrderItems().remove(demoOrderItem);
		demoOrderItem.setDemoOrder(null);

		return demoOrderItem;
	}
	
	// Items below added to make cart work while not overriding original JPA classes
	public DemoOrderItem addCartOrderItem(DemoOrderItem item) 
	{
        String code = new Long(item.getDemoProductInfo().getProductId()).toString();
        BigDecimal quantity = new BigDecimal(item.getQuantity().toString());
        List<DemoOrderItem> items = getDemoOrderItems();
        for (int i = 0; i < items.size(); i++) 
        {
        	DemoOrderItem orderItem = items.get(i);
            if (new Long(orderItem.getDemoProductInfo().getProductId()).toString().equals(code)) 
            {
            	orderItem.setQuantity(quantity);
                // orderItem.setOrder(this);   ???
            	// return item;  original line - really want to return the updated item per next line
            	return orderItem;
            }
        }
        items.add(item);
        item.setDemoOrder(this);	// when creating the order item, need to have an order object
        return item;			// pointing to it.  
    }

    public DemoOrderItem removeCartOrderItem(DemoOrderItem item) 
    {
        String code = new Long(item.getDemoProductInfo().getProductId()).toString();
        List<DemoOrderItem> items = getDemoOrderItems();
        for (int i = 0; i < items.size(); i++) 
        {
        	DemoOrderItem orderItem = items.get(i);
            if (new Long(orderItem.getDemoProductInfo().getProductId()).toString().equals(code)) 
            {
                items.remove(i);
                orderItem.setDemoOrder(null);	// since this order is removed - it should have no order
                return item;				// object pointing to it.
            }
        }
        
        return item;
    }
    
    // Added method to enable order sorting by date
    public int compareTo(Object object)
    {
    	DemoOrder demoOrder = (DemoOrder) object;
    	if (this.getOrderTimestamp().before(demoOrder.getOrderTimestamp()))
    		return 1;
    	if (this.getOrderTimestamp().after(demoOrder.getOrderTimestamp()))
    		return -1;
    	return 0;
    }

}