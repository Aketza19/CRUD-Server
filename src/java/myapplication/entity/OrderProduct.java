package myapplication.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Imanol
 */


/**
 *  Class containing all the orders and the products of which it consists. 
 *  It contains the following fields: order identification, product identification, 
 *  total price of the order and total quantity of products.
 */
@Entity
@Table(name="orderProduct",schema="almazon")
@XmlRootElement
public class OrderProduct implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Embed the Id of this class into OrderProductId Entity
     */
    @EmbeddedId
    private OrderProductId id;
    
    
    @MapsId("orderId")
    /**
     * Define the relation to Order table
     */
    @ManyToOne
    private Order order;
    @MapsId("productId")
    /**
     * Define the relation to Product table
     */
    @ManyToOne
    private Product product;
    private Double total_price;
    private Integer total_quantity;

 
    /**
     * Getters and setters
     */
    public OrderProductId getId() {
        return id;
    }

    public void setId(OrderProductId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public Integer getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(Integer total_quantity) {
        this.total_quantity = total_quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderProduct other = (OrderProduct) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderProduct{" + "id=" + id + '}';
    }

   
}
