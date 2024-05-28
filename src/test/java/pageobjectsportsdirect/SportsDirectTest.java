package pageobjectsportsdirect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pageobjectsportsdirect.pages.CatalogItemPage;
import pageobjectsportsdirect.pages.CategoryPage;
import pageobjectsportsdirect.pages.ItemPage;
import pageobjectsportsdirect.pages.MainPage;


public class SportsDirectTest {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Test
    public void shoppingCartChecks() {

        BaseFunctions baseFunctions = new BaseFunctions();
        baseFunctions.openURL("sportsdirect.com");

        MainPage mainPage = new MainPage(baseFunctions);
        mainPage.acceptCookies();
        mainPage.menuItem("Kids");

        CategoryPage categoryPage = new CategoryPage(baseFunctions);
        categoryPage.selectCategory("Hoodies");

        CatalogItemPage catalogItemPage = new CatalogItemPage(baseFunctions);
        catalogItemPage.setPriceFilter(25.0, 35.0);
        LOGGER.info("Checking prices in catalog to fit in selected range");
        for (Double price : catalogItemPage.getAllNormalPrices()) {
            Assertions.assertTrue(price >= 25.0 && price <= 35.0, "Price mismatch!");
        }
        catalogItemPage.selectItem();

        ItemPage itemPage = new ItemPage(baseFunctions);
        itemPage.selectSize();
        itemPage.addToBag("ADD TO BAG");
        itemPage.checkItem();
        itemPage.checkPrice();

        mainPage.menuItem("Kids");

        categoryPage.selectCategory("Coats");
        catalogItemPage.selectItem();

        itemPage.selectSize();
        itemPage.addToBag("ADD TO BAG");
        itemPage.goToBag();
        itemPage.checkSummary();

        LOGGER.info("SportsDirect cart test passed successfully");

    }
}



