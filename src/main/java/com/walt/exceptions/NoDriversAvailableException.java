package com.walt.exceptions;

/**
 * Arises when there is no available driver
 */
public class NoDriversAvailableException extends Exception {

    public NoDriversAvailableException() {
        super("Error: no driver is available");
    }
}
