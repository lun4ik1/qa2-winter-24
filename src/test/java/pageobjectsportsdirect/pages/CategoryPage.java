package pageobjectsportsdirect.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageobjectsportsdirect.BaseFunctions;

public class CategoryPage {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final By CATEGORY_NAME = By.xpath(".//div[@class='swiper-slide swiper-slide-visible']");


    private BaseFunctions baseFunctions;

    public CategoryPage(BaseFunctions baseFunctions) {
        LOGGER.info("We are on category page");
        this.baseFunctions = baseFunctions;
    }

    public void selectCategory(String categoryName) {
        LOGGER.info("Selecting " + categoryName + " from menu");
        boolean isSectionFound = false;
        for (WebElement we : baseFunctions.findElements(CATEGORY_NAME)) {
            if (we.getText().equals(categoryName)) {
                isSectionFound = true;
                we.click();
                break;
            }
        }
        Assertions.assertTrue(isSectionFound, "Can't find menu item " + categoryName);
    }

}
