/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import myapplication.entity.Order;
import myapplication.entity.Product;
import myapplication.exceptions.ReadException;


/**
 *
 * @author 2dam
 */
public abstract class ProductAbstractFacade extends AbstractFacade<Product> {

    public ProductAbstractFacade(Class<Product> entityClass) {
        super(entityClass);
    }
     public Set<Product> findProductByCompany(Integer id) throws ReadException{
        try{
         return new HashSet<Product> (getEntityManager().createNamedQuery("findProductByCompany").setParameter("company", id).getResultList()); 
        }catch(Exception e){
            throw new ReadException(e.getMessage());
        }
        }
     public Set<Product> findAllProducts() throws ReadException{
         try{
        return new HashSet<Product> (getEntityManager().createNamedQuery("findAllProducts").getResultList());
      }catch(Exception e){
            throw new ReadException(e.getMessage());
        }
    }
      public Set<Product> findProductsByName(String name) throws ReadException{
          try{
        return new HashSet<Product> (getEntityManager().createNamedQuery("findProductsByName").setParameter("name", name).getResultList());
          }catch(Exception e){
              throw new ReadException(e.getMessage());
          }
          }
}
