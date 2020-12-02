package myapplication.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.MERGE;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Imanol
 */


@Entity
@Table(name="Order",schema="almazon")
/**
 *  Class containing all the orders and the products of which it consists. It contains the following fields: 
 *  order identification, product identification, total price of the order and total quantity of products.
 */
public class Order implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer id;
    private Timestamp date;
    private Float total_price;
    //Pregunta Enumtype: ¿Ordinal o String?
    @Enumerated
    private OrderStatus status;
 
    // 
    @OneToMany(mappedBy="order", cascade=(MERGE), fetch=EAGER)
    private Set<OrderProduct> orders;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.date);
        hash = 13 * hash + Objects.hashCode(this.total_price);
        hash = 13 * hash + Objects.hashCode(this.status);
        hash = 13 * hash + Objects.hashCode(this.orders);
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
        final Order other = (Order) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.total_price, other.total_price)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.orders, other.orders)) {
            return false;
        }
        return true;
    }

    
    //Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }

    public Set<OrderProduct> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderProduct> orders) {
        this.orders = orders;
    }
    
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    
    
}
