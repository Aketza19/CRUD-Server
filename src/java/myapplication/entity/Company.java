package myapplication.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.OneToMany;

/**
 * Entity representating the Companies. It contains the following fields:
 * company id, company name, company type, company localization and company
 * amount of users.
 *
 * @author Iker de la Cruz
 */
@Entity
@Table(name = "company", schema = "almazon")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identification field of the Company.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Name of the Company.
     */
    private String name;

    /**
     * Type of the Company.
     */
    @Enumerated(EnumType.STRING)
    private CompanyType type;

    /**
     * Localization of the Company.
     */
    private String localization;

    /**
     * The amount of users of the Company.
     */
    private Integer n_users;

    /**
     * Relational field containing the list of users on the company.
     */
    @OneToMany(mappedBy = "user", fetch = EAGER)
    private Set<User> users;

    /**
     *
     * @return the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id the id to be set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the type.
     */
    public CompanyType getType() {
        return type;
    }

    /**
     *
     * @param type the type to set.
     */
    public void setType(CompanyType type) {
        this.type = type;
    }

    /**
     *
     * @return the localization.
     */
    public String getLocalization() {
        return localization;
    }

    /**
     *
     * @param localization the localization to set.
     */
    public void setLocalization(String localization) {
        this.localization = localization;
    }

    /**
     *
     * @return the amount of users.
     */
    public Integer getN_users() {
        return n_users;
    }

    /**
     *
     * @param n_users the amount of users to set.
     */
    public void setN_users(Integer n_users) {
        this.n_users = n_users;
    }

    /**
     * Relational field containing the list of users on the company.
     *
     * @return the users.
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Relational field containing the list of users on the company.
     *
     * @param users the users to set.
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Integer representation for Company instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Compares two Company objects for equality. This method consider a Company
     * equal to another Company if their id fields have the same value.
     *
     * @param obj The other Company object to compare to.
     * @return true if ids are equals.
     */
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
        final Company other = (Company) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Obtain a string representation of the Company.
     *
     * @return The String representation of the Company.
     */
    @Override
    public String toString() {
        return "Company{" + "id=" + id + '}';
    }
}