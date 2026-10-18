package com.company.automation.pages.saucedemo;

import com.company.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SauceDemoInventoryPage extends BasePage {

    private final By inventoryItems = By.className("inventory_item");
    private final By itemName = By.className("inventory_item_name");
    private final By itemPrice = By.className("inventory_item_price");
    private final By addToCartButton = By.className("btn_inventory");
    private final By cartLink = By.className("shopping_cart_link");

    public SauceDemoInventoryPage(WebDriver driver) {
        super(driver);
    }

    public record Product(String name, double price) {}

    public List<Product> getAllProducts() {
        List<WebElement> elements = driver.findElements(inventoryItems);
        List<Product> products = new ArrayList<>();

        for (WebElement element : elements) {
            String name = element.findElement(itemName).getText();
            String priceText = element.findElement(itemPrice).getText();
            double price = Double.parseDouble(priceText.replace("$", "").trim());
            products.add(new Product(name, price));
        }
        return products;
    }

    public SauceDemoInventoryPage addProductToCart(String productName) {
        // Use an XPath to find the add to cart button for the specific product name
        By specificButton = By.xpath("//div[@class='inventory_item'][.//div[text()='" + productName + "']]//button");
        click(specificButton);
        return this;
    }

    public SauceDemoCartPage goToCart() {
        click(cartLink);
        return new SauceDemoCartPage(driver);
    }
}
