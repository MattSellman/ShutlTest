package com.shutl.controller;
import com.shutl.model.Quote;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class QuoteController {

    @RequestMapping(value = "/quote", method = POST)
    public @ResponseBody Quote quote(@RequestBody Quote quote) {
        Long price = Math.abs((Long.valueOf(quote.getDeliveryPostcode(), 36) - Long.valueOf(quote.getPickupPostcode(), 36))/100000000);

        return new Quote(quote.getPickupPostcode(), quote.getDeliveryPostcode(), price);
    }

    
    


/*
 * so from what i've seen:
 * getters and setters
 * separate classes
 * hashmaps and sets
 * 
 * copy the top of this as a template:
 * copy test as well for completeness:
 * 
 * 
 * 
 */



  //then: spend 90mins for pending features
  
  /*
   * task1:
   * 
   * variable prices by vehicle add vehicle prices by:
    bicycle: 10%
    motorbike: 15%
    parcel_car: 20%
    small_van: 30%
    large_van: 40%
   */
  
  
/*
 * task2:
 * add price limits to each vehicle
 * 
    bicycle: 500
    motorbike: 750
    parcel_car: 1000
    small_van: 1500
    large_van: no limit
 */

 /*
  * task3:
  * calculate vehicle by weights
  * see github page
  * - other guy did this by hashmap
  */
  
  
  
  //then add weight calculations (switch statements?)

  //
}


