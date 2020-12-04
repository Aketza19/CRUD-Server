package myapplication.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.MERGE;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Imanol
 */


/**
 *  Class containing all the orders and the products of which it consists. It contains the following fields: 
 *  order identification, product identification, total price of the order and total quantity of products.
 */
@Entity
@Table(name="Order",schema="almazon")
public class Order implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Timestamp date;
    private Double total_price;
    
    //Pregunta Enumtype: ¿Ordinal o String?
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
 
    //Define the relation to OrderProduct table. 
    @OneToMany(mappedBy="order", cascade=(MERGE), fetch=EAGER)
    private Set<OrderProduct> orders;

    //Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
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

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Order other = (Order) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + '}';
    }
    
}