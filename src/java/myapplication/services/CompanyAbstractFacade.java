/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import javax.persistence.EntityManager;
import myapplication.entity.Company;

/**
 *
 * @author 2dam
 */
public abstract class CompanyAbstractFacade extends AbstractFacade<Company> {

    public CompanyAbstractFacade() {
        super(null);
    }

    public CompanyAbstractFacade(Class<Company> entityClass) {
        super(entityClass);
    }
    
    
}