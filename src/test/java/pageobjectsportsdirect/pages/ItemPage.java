package pageobjectsportsdirect.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageobjectsportsdirect.BaseFunctions;

import java.util.ArrayList;
import java.util.List;

public class ItemPage {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final By ITEM_SIZE = By.id("liItem");
    private final By BAG_BTN = By.id("aAddToBag");
    private final By CART_ITEM = By.cssSelector("#divulBagParent > div > div > div.product-line-card__description > div.product-line-card__description-name > a");
    private final By CART_PRICE = By.xpath("//*[@id=\"divulBagParent\"]/div/div/div[4]/div[1]/div[1]/span[2]");
    private final By ITEM_NAME = By.xpath(".//span[@class=\"prodTitle\"]");
    private final By ITEM_PRICE = By.xpath("//*[@class='pdpPriceRating col-xs-12']/div[2]/span");
    private final By SLIDE_BAG = By.cssSelector("#gvBasketDetails > div.product-line-card.has-savings");
    private final By BAG = By.xpath("//*[@id=\"bagQuantityContainer\"]/span[1]");
    private final By BAG_PRICE = By.xpath(".//span[@class='product-line-card__description-subtotal-amount']");
    private final By SUM = By.xpath("//*[@id=\"TotalValue\"]");
    private final By POPUP_MENU = By.xpath("//*[@id=\"divulBagParent\"]");

    private BaseFunctions baseFunctions;

    public ItemPage(BaseFunctions baseFunctions) {
        LOGGER.info("We are on Item page");
        this.baseFunctions = baseFunctions;
    }

    public void selectSize() {
        LOGGER.info("Selecting item size");
        for (WebElement we : baseFunctions.findElements(ITEM_SIZE)) {
            we.click();
            break;
        }
    }

    public void addToBag(String categoryName) {
        LOGGER.info("Adding item to cart");
        boolean isSectionFound = false;
        for (WebElement we : baseFunctions.findElements(BAG_BTN)) {
            if (we.getText().equals(categoryName)) {
                isSectionFound = true;
                we.click();
                break;
            }
        }
        Assertions.assertTrue(isSectionFound, "Can't find menu item " + categoryName);
    }

    public void checkItem() {
        LOGGER.info("Checking that selected item is correct");
        WebElement popUpMenu = baseFunctions.findElement(POPUP_MENU);
        String cartItem = baseFunctions.getText(popUpMenu, CART_ITEM);
        String itemName = baseFunctions.getTextCompare(ITEM_NAME);
        Assertions.assertEquals(cartItem, itemName, "Item do not match");
    }


    public void checkPrice() {
        LOGGER.info("Checking that selected item price is correct");
        WebElement cartPriceValue = baseFunctions.findElement(CART_PRICE);
        String cartPrice = baseFunctions.getText(cartPriceValue, CART_PRICE).replaceAll(",", ".").replaceAll(" €", "");
        Double cartPricesAsDouble = Double.valueOf(cartPrice);
        WebElement itemPriceValue = baseFunctions.findElement(CART_PRICE);
        String itemPrice = baseFunctions.getText(itemPriceValue, ITEM_PRICE).replaceAll(",", ".").replaceAll(" €", "");
        Double itemPricesAsDouble = Double.valueOf(itemPrice);
        Assertions.assertEquals(cartPricesAsDouble, itemPricesAsDouble, "Item do not match");
    }

    public void goToBag() {
        baseFunctions.waitForElement(BAG);
        boolean isSectionFound = false;
        for (WebElement we : baseFunctions.findElements(BAG)) {
            isSectionFound = true;
            we.click();
            break;

        }
        Assertions.assertTrue(isSectionFound, "Can't find menu item ");
    }

    public void checkSummary() {
        LOGGER.info("Checking order Summary");
        List<WebElement> productPrices = baseFunctions.findElements(SLIDE_BAG);

        List<Double> actualPrices = new ArrayList<>();
        for (WebElement product : productPrices) {
            String price = baseFunctions.getText(product, BAG_PRICE).replaceAll(",", ".").replaceAll(" €", "");
            actualPrices.add(Double.valueOf(price));
        }

        Double sum = actualPrices.stream().reduce(Double::sum).get();
        String totalPrice = baseFunctions.getTextCompare(SUM).replaceAll(",", ".").replaceAll(" €", "");
        Double totalPriceAsDouble = Double.valueOf(totalPrice);
        Assertions.assertEquals(sum, totalPriceAsDouble, "Values are not equal");
    }
}



