/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import javax.persistence.EntityManager;
import myapplication.entity.Product;

/**
 *
 * @author 2dam
 */
public abstract class ProductAbstractFacade extends AbstractFacade<Product> {

    public ProductAbstractFacade(Class<Product> entityClass) {
        super(entityClass);
    }

}
