package rozetka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.By.*;
import static org.openqa.selenium.Keys.ENTER;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class RozetkaTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    
    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rozetka.com.ua/");
        wait = new WebDriverWait(driver, 60);
        actions = new Actions(driver);
    }

    @Test
    public void checkThatAddingToCartWorksCorrect(){
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//li[@class='menu-categories__item ng-star-inserted']/a[contains(@href, '/computers-notebooks')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(linkText("Ноутбуки"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//select[contains(@class, 'ng-star-inserted')]"))).click();
        Select sortingTypeDropdown = new Select(driver.findElement(xpath("//select[contains(@class, 'ng-star-inserted')]")));
        sortingTypeDropdown.selectByVisibleText("От дорогих к дешевым");
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpath("//input[@id='Готов к отправке']/ancestor::li"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//input[@id='Acer']/ancestor::li"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(cssSelector(".goods-tile__heading"))).click();
        actions.moveToElement(driver.findElement(name("search"))).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable(cssSelector(".button_size_large"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(partialLinkText("Продолжить покупки"))).click();

        driver.findElement(cssSelector("a.header__logo")).click();
        driver.findElement(xpath("//ul[contains(@class,'categories_type_main')]//a[contains(@href, '/telefony')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//a[contains(@title, 'mobile-phones/c80003/producer=apple/')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//input[@id='Rozetka']/ancestor::li"))).click();
        actions.moveToElement(driver.findElement(xpath("//input[@id='5G']/ancestor::li"))).click().perform();
        actions.moveToElement(driver.findElement(cssSelector(".goods-tile__heading"))).click().perform();
        actions.moveToElement(driver.findElement(name("search"))).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable(cssSelector(".button_size_large"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//div[@class='cart-receipt ng-star-inserted']/preceding-sibling::a"))).click();

        driver.findElement(id("fat-menu")).click();
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//a[@href='https://bt.rozetka.com.ua/']/parent::li"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//a[@href='https://rozetka.com.ua/pages/bosch/']"))).click();
        actions.moveToElement(driver.findElement(xpath("//*[contains(@href, '/producer=bosch;32745=40140/')]"))).click().perform();
        ArrayList<String> tabs2 = new ArrayList<> (driver.getWindowHandles());
        driver.close();
        driver.switchTo().window(tabs2.get(1));
        actions.moveToElement(driver.findElement(xpath("//*[text() =' Ok ']"))).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpath("//input[@formcontrolname='max']"))).clear();
        driver.findElement(xpath("//input[@formcontrolname='max']")).sendKeys("10000", ENTER);
        actions.moveToElement(driver.findElement(name("search"))).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable(cssSelector(".goods-tile__heading"))).click();
        actions.moveToElement(driver.findElement(name("search"))).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable(cssSelector(".button_size_large"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpath("//*[text() = ' Корзина ']")));

        List<WebElement> itemsInCart = driver.findElements(cssSelector(".cart-list__item"));
        assertEquals(3, itemsInCart.size());

        WebElement totalSum = driver.findElement(xpath("//div[@class='cart-receipt__sum-price']"));
        assertTrue(Integer.parseInt(totalSum.getText().replace(" ₴", "")) < 500000);
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}