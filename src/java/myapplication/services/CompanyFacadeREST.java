package myapplication.services;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import myapplication.entity.Company;
import myapplication.entity.User;
import myapplication.exceptions.CreateException;
import myapplication.exceptions.DeleteException;
import myapplication.exceptions.ReadException;
import myapplication.exceptions.UpdateException;

/**
 * The class that contains the RESTful for Company.
 *
 * @author Iker de la Cruz
 */
@Stateless
@Path("company")
public class CompanyFacadeREST extends CompanyAbstractFacade {

    private Logger LOGGER = Logger.getLogger(CompanyFacadeREST.class.getName());

    @PersistenceContext(unitName = "CRUD-ServerPU")
    private EntityManager em;

    public CompanyFacadeREST() {
        super(Company.class);
    }

    /**
     * POST method to create companies.
     *
     * @param entity The company object.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Company entity) {
        try {
            super.create(entity);
        } catch (CreateException ex) {
            LOGGER.severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * PUT method to modify companies.
     *
     * @param entity The company object.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(Company entity) {
        try {
            Set<User> users = new HashSet();
            users.clear();
            entity.setUsers(users);
            super.edit(entity);
        } catch (UpdateException ex) {
            LOGGER.severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * DELETE method to remove a company by id.
     *
     * @param id The id for the company to remove.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            super.remove(super.find(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * GET method to obtain a company by id.
     *
     * @param id The company id.
     * @return A Company object.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Company find(@PathParam("id") Integer id) {
        try {
            return super.find(id);
        } catch (ReadException ex) {
            LOGGER.severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * GET method to obtain all companies.
     *
     * @return A list of Company objects.
     */
    @GET
    @Path("findAllCompanies")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Company> findAllCompanies() {
        return super.findAllCompanies();
    }

    /**
     * GET method to obtain the companies by the localization.
     *
     * @param localization The company localization.
     * @return A list of Company objects.
     */
    @GET
    @Path("findCompaniesByLocalizations/{localization}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Company> findCompaniesByLocalization(@PathParam("localization") String localization) {
        return super.findCompaniesByLocalization(localization);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
