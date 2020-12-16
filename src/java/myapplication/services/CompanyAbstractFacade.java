/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
import myapplication.entity.Company;

/**
 *
 * @author Iker de la Cruz
 */
public abstract class CompanyAbstractFacade extends AbstractFacade<Company> {

    public CompanyAbstractFacade(Class<Company> entityClass) {
        super(entityClass);
    }

    public List<Company> findAllCompanies() {
        return getEntityManager().createNamedQuery("findAllCompanies").getResultList();
    }

    public List<Company> providersCompanies() {
        return getEntityManager().createNamedQuery("providersCompanies").getResultList();
    }

    public List<Company> findCompaniesByLocalization(String localization) {
        return getEntityManager().createNamedQuery("findCompaniesByLocalization").setParameter("localization", localization).getResultList();
    }

}
