/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author 2dam
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    /**
     * Creates the collection where the REST services are stored.
     * @return 
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project. If not defined, they are not going
     * to operate.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(myapplication.services.CompanyFacadeREST.class);
        resources.add(myapplication.services.OrderFacadeREST.class);
        resources.add(myapplication.services.OrderProductFacadeREST.class);
        resources.add(myapplication.services.ProductFacadeREST.class);
        resources.add(myapplication.services.UserFacadeREST.class);
    }
    
}
