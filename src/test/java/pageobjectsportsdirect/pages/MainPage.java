package pageobjectsportsdirect.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageobject.BaseFunc;
import pageobjectsportsdirect.BaseFunctions;

import java.util.List;

public class MainPage {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final By ACCEPT_COOKIES_BTN = By.id("onetrust-accept-btn-handler");
    private final By MENU_ITEM = By.xpath(".//li[contains(@class, 'mmHasChild')]/a");
    private final By MENU = By.id("topMenu");

    private BaseFunctions baseFunctions;

    public MainPage(BaseFunctions baseFunctions) {
        LOGGER.info("We are on Home page");
        this.baseFunctions = baseFunctions;
    }

    public void acceptCookies() {
        baseFunctions.click(ACCEPT_COOKIES_BTN);
    }

    public void menuItem(String menuItemName) {

        WebElement menuBlock = baseFunctions.findElement(MENU);
        List<WebElement> items = menuBlock.findElements(MENU_ITEM);

        LOGGER.info("Selecting " + menuItemName + " from menu");
        boolean isSectionFound = false;
        for (WebElement we : items) {
            if (we.getText().equals(menuItemName)) {
                isSectionFound = true;
                we.click();
                break;
            }
        }
        Assertions.assertTrue(isSectionFound, "Can't find menu item " + menuItemName);
    }
}

