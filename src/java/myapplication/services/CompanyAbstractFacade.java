package myapplication.services;

import java.util.HashSet;
import java.util.Set;
import myapplication.entity.Company;

/**
 * The class that contain the logical methods RESTful for Company, extends the
 * class AbstractFacade.
 *
 * @author Iker de la Cruz
 */
public abstract class CompanyAbstractFacade extends AbstractFacade<Company> {

    public CompanyAbstractFacade(Class<Company> entityClass) {
        super(entityClass);
    }

    /**
     * Method to get all companies, executing the query that is specified in the
     * entity.
     *
     * @return A list of Company object.
     */
    public Set<Company> findAllCompanies() {
        return new HashSet<Company>(getEntityManager().createNamedQuery("findAllCompanies").getResultList());
    }

    /**
     * Method to get companies by the localization, executing the query that is
     * specified in the entity.
     *
     * @param localization The company localization.
     * @return A list of Company object.
     */
    public Set<Company> findCompaniesByLocalization(String localization) {
        return new HashSet<Company>(getEntityManager().createNamedQuery("findCompaniesByLocalization").setParameter("localization", localization).getResultList());
    }

}
