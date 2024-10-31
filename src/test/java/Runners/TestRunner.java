
package Runners;

import Utils.TestListener;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestRunner {

    private TestNG testng;

    @BeforeClass
    public void setup() {
        // Initialize TestNG and add a custom listener
        testng = new TestNG();
        testng.addListener(new TestListener());  // Register the custom listener
        System.out.println("Setting up TestRunner environment.");
    }

    @Test
    public void runTests() {
        // Set the test classes to be run
        testng.setTestClasses(new Class[]{
                EcommerceTests.HomePageTests.class,
                EcommerceTests.AccountLoginPageTest.class,
                EcommerceTests.ProductListPageTests.class,
                EcommerceTests.ProductDetailPageTests.class,
                EcommerceTests.ShoppingCartPageTests.class,
                EcommerceTests.CheckOutPageTests.class
        });

        // Run the tests with the custom listener logging results
        testng.run();
    }

    @AfterClass
    public void tearDown() {
        // Cleanup operations after running tests
        System.out.println("Cleaning up TestRunner environment.");
    }
}