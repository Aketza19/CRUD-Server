package myapplication.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Imanol
 */
/**
 * Class containing all the orders and the products of which it consists. It
 * contains the following fields: order identification, product identification,
 * total price of the order and total quantity of products.
 */
@Entity
@Table(name = "order", schema = "almazon")
@NamedQueries({
    @NamedQuery(name="findAllOrders",query="SELECT o FROM Order o"),
    @NamedQuery(name="findOrdersByPrice", query="SELECT o FROM Order o WHERE o.total_price >= :price")
})
@XmlRootElement
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    private Float total_price;

    //Pregunta Enumtype: ¿Ordinal o String?
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //Define the relation to OrderProduct table. 
    @OneToMany(mappedBy = "order", fetch = EAGER, orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<OrderProduct> products;

    @ManyToOne
    private User user;

    //Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    
    public Set<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderProduct> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
