package com.company.automation.tests.saucedemo;

import com.company.automation.config.ConfigReader;
import com.company.automation.pages.saucedemo.SauceDemoCartPage;
import com.company.automation.pages.saucedemo.SauceDemoInventoryPage;
import com.company.automation.pages.saucedemo.SauceDemoLoginPage;
import com.company.automation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Comparator;
import java.util.List;

public class SauceDemoPurchaseTest extends BaseTest {

    @Test(description = "Login, add the cheapest product to the cart, and verify it is present")
    public void testPurchaseCheapestProduct() {
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver);

        // 1. Login
        SauceDemoInventoryPage inventoryPage = loginPage
                .openPage(ConfigReader.getSauceBaseUrl())
                .login(ConfigReader.getSauceUsername(), ConfigReader.getSaucePassword());

        // 2. Find the cheapest product
        List<SauceDemoInventoryPage.Product> products = inventoryPage.getAllProducts();
        SauceDemoInventoryPage.Product cheapestProduct = products.stream()
                .min(Comparator.comparingDouble(SauceDemoInventoryPage.Product::price))
                .orElseThrow(() -> new RuntimeException("No products found in inventory"));

        System.out.println("Cheapest product identified: " + cheapestProduct.name() + " at $" + cheapestProduct.price());

        // 3. Add cheapest product to cart
        inventoryPage.addProductToCart(cheapestProduct.name());

        // 4. Go to cart and verify
        SauceDemoCartPage cartPage = inventoryPage.goToCart();
        List<String> cartItems = cartPage.getCartItemNames();

        Assert.assertTrue(cartItems.contains(cheapestProduct.name()),
                "The cheapest product " + cheapestProduct.name() + " should be in the cart!");
    }
}
