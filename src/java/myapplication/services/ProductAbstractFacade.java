/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import myapplication.entity.Order;
import myapplication.entity.Product;

/**
 *
 * @author 2dam
 */
public abstract class ProductAbstractFacade extends AbstractFacade<Product> {

    public ProductAbstractFacade(Class<Product> entityClass) {
        super(entityClass);
    }
     public List<Product> findProductByCompany(Integer id){
        return getEntityManager().createNamedQuery("findProductByCompany").setParameter("company", id).getResultList();
    }
      public List<Product> findAllProducts(){
        return getEntityManager().createNamedQuery("findAllProducts").getResultList();
      
    }
      public List<Product> findProductsByName(String name){
        return getEntityManager().createNamedQuery("findProductsByName").setParameter("name", name).getResultList();
    }
}
