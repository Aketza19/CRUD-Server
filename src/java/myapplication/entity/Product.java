/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.MERGE;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing products that a company have.
 * fields: id, weight, price, name
 * @author Aketza
 */
@Entity
@Table(name = "product", schema = "almazon")
@NamedQueries({
@NamedQuery (name="findProductByCompany", query="SELECT p FROM Product p, User u WHERE p.user=u.id and u.company.id = :company"),
@NamedQuery (name="findProductsByName", query="SELECT p FROM Product p WHERE p.name LIKE :name"),
@NamedQuery (name ="findAllProducts" , query="SELECT p FROM Product p")
})
@XmlRootElement
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identification field for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * The weight of the product.
     */
    @NotNull
    private float weight;
    /**
     * The price of the product.
     */
    @NotNull
    private double price;
    /**
     * The name of the product.
     */
    @NotNull
    private String name;

    /**
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id the id to be set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return the weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     *
     * @param weight the weight to be set
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price the price to be set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The relational field which contains the products in a order.
     *
     * @return the products
     */
    @XmlTransient
    public Set<OrderProduct> getOrders() {
        return orders;
    }

    /**
     * The relational field which contains the products in a order.
     *
     * @param products the products to be set
     */
    public void setOrders(Set<OrderProduct> orders) {
        this.orders = orders;
    }
    /**
     * The relational field which contains the list of products in an order.
     */
    @OneToMany(orphanRemoval = true, mappedBy = "product", fetch = EAGER)
    private Set<OrderProduct> orders;
    /**
     * User for the product.
     */
    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Integer representation for Product instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares two Products objects for equality.
     *
     * @param object The other Product object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the Product.
     *
     * @return The String representing the Product.
     */
    @Override
    public String toString() {
        return "myapplication.entity.Product[ id=" + id + " ]";
    }

}
