package myapplication.services;

import java.util.List;
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
    public List<Company> findAllCompanies() {
        return getEntityManager().createNamedQuery("findAllCompanies").getResultList();
    }

    /**
     * Method to get companies by the localization, executing the query that is
     * specified in the entity.
     *
     * @param localization The company localization.
     * @return A list of Company object.
     */
    public List<Company> findCompaniesByLocalization(String localization) {
        return getEntityManager().createNamedQuery("findCompaniesByLocalization").setParameter("localization", localization).getResultList();
    }

}
