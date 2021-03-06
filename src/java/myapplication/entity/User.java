/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Mikel
 */
@Entity
@DynamicUpdate
@Table(name = "user", schema = "almazon")
@NamedQueries({
    @NamedQuery(name = "findUsersByCompanyName", query = "SELECT u FROM User u, Company c WHERE u.company.id=c.id AND c.name=:companyName")
    ,
    //@NamedQuery(name = "getAllUsers", query = "SELECT u.name, u.surname,u.username, u.lastAccess,u.lastPasswordChange, u.email, u.company, u.id, u.privilege, u.status,u.password FROM User u")
    @NamedQuery(name = "getAllUsers", query = "SELECT u FROM User u")
    ,
    @NamedQuery(name = "findUsersByName", query = "SELECT u FROM User u WHERE u.username= :username")
    ,
     @NamedQuery(name = "findUsersByUsername", query = "SELECT u FROM User u WHERE u.username= :username")
    ,
       @NamedQuery(name = "findUsersByEmail", query = "SELECT u FROM User u WHERE u.email= :email")
    ,
    @NamedQuery(name = "getPasswordByEmail", query = "SELECT u.password FROM User u WHERE u.email = :email"),})
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The auto generated id of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The username of the user.
     */
    @Column(unique = true)
    @NotNull
    private String username;
    /**
     * The email of the user.
     */
    @Column(unique = true)
    @NotNull
    private String email;
    /**
     * The name of the user.
     */
    @NotNull
    private String name;
    /**
     * The Surname of the user
     */

    @NotNull
    private String surname;

    /**
     * The status of the user. Enum.
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status;
    /**
     * The privilege of the user.
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserPrivilege privilege;
    /**
     * The password of the user.
     */
    @NotNull
    private String password;
    /**
     * The last access of the user.
     */
    @Temporal(TemporalType.DATE)
    private Date lastAccess;
    /**
     * The last passsword change that has been made for this user.
     */
    @Temporal(TemporalType.DATE)
    private Date lastPasswordChange;

    /**
     * The company object where this user belongs.
     */
    @ManyToOne()
    private Company company;
    /**
     * The product relation. A user can create 0 or more products.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Product> products;
    /**
     * The orders relation. A user can create 0 or more orders.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Order> orders;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @XmlTransient
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @XmlTransient
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myapplication.entity.User[ id=" + id + " ]";
    }

}
