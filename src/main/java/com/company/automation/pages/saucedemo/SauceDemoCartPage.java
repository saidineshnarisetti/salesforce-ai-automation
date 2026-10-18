package com.company.automation.pages.saucedemo;

import com.company.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class SauceDemoCartPage extends BasePage {

    private final By cartItems = By.className("cart_item");
    private final By itemName = By.className("inventory_item_name");

    public SauceDemoCartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getCartItemNames() {
        return driver.findElements(cartItems).stream()
                .map(item -> item.findElement(itemName).getText())
                .collect(Collectors.toList());
    }
}
