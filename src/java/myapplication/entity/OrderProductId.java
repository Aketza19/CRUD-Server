package myapplication.entity;

import javax.persistence.Embeddable;

/**
 *
 * @author Imanol
 */

/*
    This entity contains the relation between the entities Order and Products. It contains 
    the following fields: order identification and product identification.
*/

//Makes the entity embeddable 
@Embeddable
public class OrderProductId {
    
    //Identification field for the order
    private Integer orderId;
    
    //Identification field for the product
    private Integer productId;

    //Empty constructor
    public OrderProductId() {
    }
    
    //Constructor
    public OrderProductId(Integer orderId, Integer productId){
        this.orderId=orderId;
        this.productId=productId;
    }

    //Getters and setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    
}
