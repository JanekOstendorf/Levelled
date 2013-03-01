/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ozzyfant.levelled;

import junit.framework.TestCase;

/**
 *
 * @author Florian Reichmuth
 */
public class LoggerTest extends TestCase {
    
    public LoggerTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of info method, of class Logger.
     */
    public void testInfo() {
        System.out.println("info");
        String message = "Test Info Logging";
        Logger instance = new Logger();
        instance.info(message);
    }

    /**
     * Test of severe method, of class Logger.
     */
    public void testSevere() {
        System.out.println("severe");
        String message = "Test severe Logging";
        Logger instance = new Logger();
        instance.severe(message);
    }
}
