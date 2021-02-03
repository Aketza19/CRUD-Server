/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Mikel
 */
@Entity
@DynamicUpdate
@Table(name = "herencia", schema = "almazon")
@XmlRootElement
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Persona implements Serializable {

    public Persona(Long id) {
        this.id = id;
    }

    public Persona() {
    }
    
    

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
