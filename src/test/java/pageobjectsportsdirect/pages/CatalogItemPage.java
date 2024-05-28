package pageobjectsportsdirect.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageobjectsportsdirect.BaseFunctions;
import java.util.ArrayList;
import java.util.List;

public class CatalogItemPage {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final By CATEGORY_ITEM = By.xpath(".//span[@class='slideName']/a");
    private final By LIST_ITEMS = By.xpath(".//div[@class='s-productthumbbox']");
    private final By DISCOUNTED_PRICE = By.xpath(".//span[contains(@class, 'CurrencySizeLarge curprice productHasRef')]");
    private final By NORMAL_PRICE = By.xpath(".//div[contains(@class, 'RefandPrice')]");
    private final By PRICE_MIN = By.id("PriceFilterTextEntryMin");
    private final By PRICE_MAX = By.id("PriceFilterTextEntryMax");
    private BaseFunctions baseFunctions;

    public CatalogItemPage(BaseFunctions baseFunctions) {
        LOGGER.info("We are on catalog page");
        this.baseFunctions = baseFunctions;
    }

    public void setPriceFilter(Double priceFrom, Double priceTo) {
        String priceFromAsText = String.valueOf(priceFrom.intValue());
        String priceToAsText = String.valueOf(priceTo.intValue());

        LOGGER.info("Set start price from " + priceFromAsText);
        baseFunctions.typeTextInCustomField(PRICE_MIN, priceFromAsText);
        baseFunctions.pressEnter(PRICE_MIN);

        LOGGER.info("Set start price to " + priceToAsText);
        baseFunctions.typeTextInCustomField(PRICE_MAX, priceToAsText);
        baseFunctions.pressEnter(PRICE_MAX);
    }

    public List<Double> getAllNormalPrices() {
        List<WebElement> products = baseFunctions.findElements(CATEGORY_ITEM);

        List<Double> actualPrices = new ArrayList<>();
        for (WebElement product : products) {
            if (baseFunctions.findElements(product, DISCOUNTED_PRICE).isEmpty()) {
                String price = baseFunctions.getText(product, NORMAL_PRICE).replaceAll(",", ".");
                actualPrices.add(Double.valueOf(price));
            } else {
                String price = baseFunctions.getText(product, DISCOUNTED_PRICE).replaceAll(",", ".").replaceAll(" €", "");
                actualPrices.add(Double.valueOf(price));
            }
        }
        return actualPrices;
    }

    public void selectItem() {
        for (WebElement we : baseFunctions.findElements(LIST_ITEMS)) {
            we.click();
            break;
        }
    }
}



