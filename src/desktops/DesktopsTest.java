package desktops;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utilities.Utility;

import java.time.Duration;
import java.util.List;

public class DesktopsTest extends Utility {
    String baseUrl = "http://tutorialsninja.com/demo/index.php";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyProductArrangeInAlphabeticalOrder() {

        /**
         * Test name verifyProductArrangeInAlphaBaticalOrder()
         * 1.1 Mouse hover on Desktops Tab and click
         * 1.2 Click on “Show All Desktops”
         * 1.3 Select Sort By position "Name: Z to A"
         * 1.4 Verify the Product will arrange in Descending order.
         */

        WebElement desktop = driver.findElement(By.linkText("Desktops"));
        Actions actions = new Actions(driver);
        // 1.1 Mouse hover on Desktops Tab.and click
        actions.moveToElement(desktop).click().build().perform();
        //1.2 Click on “Show All Desktops”
        clickOnElement(By.linkText("Show AllDesktops"));
        //1.3 Select Sort By position "Name: Z to A"
        selectByVisibleTextFromDropDown(By.id("input-sort"), "Name (Z - A)");
        List<WebElement> items = driver.findElements(By.xpath("//div[@class = 'caption']//a"));
        //1.4 verify descending order

        boolean isDescending = false;
        for (int i = 0; i < items.size() - 1; i++) {
            String currentElement = items.get(i).getText();
            String nextElement = items.get(i + 1).getText();
            if (currentElement.compareTo(nextElement) > 0) {
                isDescending = true;
                break;
            }
        }

        if (isDescending) {
            System.out.println("Elements are in descending order.");
        } else {
            System.out.println("Elements are not in descending order.");
        }
    }

    /**
     * Test name verifyProductAddedToShoppingCartSuccessFully()
     * 2.1 Mouse hover on Currency Dropdown and click
     * 2.2 Mouse hover on £Pound Sterling and click
     * 2.3 Mouse hover on Desktops Tab.
     * 2.4 Click on “Show All Desktops”
     * 2.5 Select Sort By position "Name: A to Z"
     */
    @Test
    public void verifyProductAddedToShoppingCartSuccessfully() {

        WebElement desktop = driver.findElement(By.linkText("Desktops"));
        //2.2 Mouse hover on £Pound Sterling and click
        Actions actions = new Actions(driver);
        //2.3 Mouse hover on Desktops Tab.
        actions.moveToElement(desktop).click().build().perform();
        //2.4 Click on “Show All Desktops”
        clickOnElement(By.linkText("Show AllDesktops"));
        //2.5 Select Sort By position "Name: A to Z"
        selectByVisibleTextFromDropDown(By.id("input-sort"), "Name (A - Z)");

        /**
         * 2.6 Select product “HP LP3065”
         * 2.7 Verify the Text "HP LP3065"
         * 2.8 Select Delivery Date "2023-11-27"
         * 2.9.Enter Qty "1” using Select class.
         * 2.10 Click on “Add to Cart” button
         * 2.11 Verify the Message “Success: You have added HP LP3065 to your shopping cart!”
         */
        clickOnElement(By.linkText("HP LP3065"));
        String expectedmodel = "HP LP3065";
        String actualmodel = getTextFromElement(By.xpath("//h1[contains(text(),'HP LP3065')]"));
        Assert.assertEquals(expectedmodel, actualmodel);

        String year = "2022";
        String month = "November";
        String date = "30";


        clickOnElement(By.xpath("//span[@class = 'input-group-btn']//button[@class = 'btn btn-default']"));
        while (true) {
            String monthYear = driver.findElement(By.xpath("//div[@class = 'datepicker-days']//th[@class = 'picker-switch']")).getText();
            String[] a = monthYear.split(" ");
            String mon = a[0];
            String yer = a[1].split("\n")[0];

            if (mon.equalsIgnoreCase(month) && yer.equalsIgnoreCase(year)) {
                break;
            } else {
                clickOnElement(By.xpath("//div[@class = 'datepicker-days']//th[@class = 'next']"));
            }
        }

        //select the date
        List<WebElement> allDates = driver.findElements(By.xpath("//tbody/tr/td"));
        for (WebElement dt : allDates) {
            if (dt.getText().equalsIgnoreCase(date)) {
                dt.click();
                break;
            }
        }

        driver.findElement(By.id("input-quantity")).clear();
        sendTextToElement(By.id("input-quantity"), "1");
        clickOnElement(By.id("button-cart"));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        String eSuccessMsg = "Success: You have added HP LP3065 to your shopping cart!" + "×";
        // Select the date
        String aSuccessMsg = driver.findElement(By.xpath("//div[@class = 'alert alert-success alert-dismissible']")).getText();
        Assert.assertEquals(eSuccessMsg, aSuccessMsg);

        try {
            clickOnElement(By.xpath("//a[contains(text(),'shopping cart')]"));
        } catch (ElementClickInterceptedException e) {
            clickOnElement(By.xpath("//a[contains(text(),'shopping cart')]"));
        }

        // * 2.13 Verify the text "Shopping Cart  (1.00kg)"
        String eText = "Shopping Cart  (1.00kg)";
        String aText = driver.findElement(By.xpath("//h1[contains(text(),'Shopping Cart')]")).getText();
        Assert.assertEquals(eText, aText);

        // * 2.14 Verify the Product name "HP LP3065"
        String eProductName = "HP LP3065";
        String aProductName = getTextFromElement(By.xpath("//body[1]/div[2]/div[1]/div[1]/form[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]/a[1]"));
        Assert.assertEquals(eProductName, aProductName);

        // * 2.15 Verify the Delivery Date "2023-11-27"
        String eDate = "Delivery Date:2023-11-27";
        String aDate = getTextFromElement(By.xpath("//small[contains(text(),'Delivery Date:2022-11-30')]"));
        Assert.assertEquals(eDate, aDate);

        // * 2.16 Verify the Model "Product 21"
        String eModel = "Product 21";
        String aModel = getTextFromElement(By.xpath("//td[contains(text(),'Product 21')]"));
        Assert.assertEquals(eModel, aModel);

        // * 2.17 Verify the Total "£74.73"
        String eTotal = "$74.73";
        String aTotal = getTextFromElement(By.xpath("//tbody/tr[1]/td[6]"));
        Assert.assertEquals(eTotal, aTotal);

    }


    @After
    public void tearDown() {

        closeBrowser();
    }

}
