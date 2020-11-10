package com.walt.exceptions;


/**
 * Arises when the city of the customer is different from the city of the restaurant
 */
public class CitiesDontMatchException extends Exception {

    public CitiesDontMatchException(){
        super("Error: cities don't match");


    }
}
