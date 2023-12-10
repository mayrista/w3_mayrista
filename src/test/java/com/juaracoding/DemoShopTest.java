package com.juaracoding;
import com.juaracoding.DemoShop;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class DemoShopTest {
    int loginAtt=0;
    static WebDriver driver;
    static WebElement btnLogin;
    static WebElement usernameInput;
    static WebElement passwordInput;
    static WebElement wrongPasswordInput;
    static WebElement addToCartBp;
    static WebElement addToCartTs;
    static  WebElement btncart;
    static WebElement btncheckOut;
    static WebElement firstName;
    static WebElement lastName;
    static WebElement postalCode;
    static WebElement btnNext;
    static WebElement finishCheckout;
    static WebElement btnbackHome;
    static WebElement menuProfile;
    static  WebElement btnLogout;

    @BeforeClass
    public void setup(){
        System.setProperty(DemoShop.webdriverChrome, DemoShop.path);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test (priority = 1)
    public void testLogin() {
        driver.get(DemoShop.url);
        login();
        WebElement productsLabel = driver.findElement(By.className("inventory_list"));
        Assert.assertTrue(productsLabel.isDisplayed(), "Login Failed");

    }
    @Test (priority = 2)
    public void addProductToCart() {
        delay(2);
        addToCartBp = driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]"));
        addToCartTs = driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]"));
        btncart = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]"));
        addToCartBp.click();
        addToCartTs.click();
        delay(2);
        btncart.click();
    }

    @Test (priority = 3)
    public void Checkout(){

        delay(2);
        btncheckOut = driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
        btncheckOut.click();

        //input Data
        firstName = driver.findElement(By.name("firstName"));
        lastName = driver.findElement(By.name("lastName"));
        postalCode = driver.findElement(By.name("postalCode"));
        firstName.sendKeys("Mayrista");
        lastName.sendKeys("Hanul F");
        postalCode.sendKeys("221221");
        btnNext = driver.findElement(By.xpath("//*[@id=\"continue\"]"));
        delay(2);
        btnNext.click();

        //Finish Checkout
        finishCheckout = driver.findElement(By.xpath("//*[@id=\"finish\"]"));
        delay(2);
        finishCheckout.click();
        btnbackHome = driver.findElement(By.id("back-to-products"));
        delay(2);
        btnbackHome.click();

        //Logout
        delay(2);
        menuProfile =driver.findElement(By.xpath("//*[@id=\"react-burger-menu-btn\"]"));
        menuProfile.click();
        delay(2);
        btnLogout = driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]"));
        btnLogout.click();
    }

    @Test(priority = 4)
    public void testLoginBlock(){
        driver.get(DemoShop.url);
        WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]"));
        String errorMessage = errorElement.getText();
        if (errorMessage.contains("locked_out_user")) {
            Assert.fail("Account is locked out. Test Aborted");
        }
        loginAtt++;
        if (loginAtt < 3) {
            loginBlocked();
        } else {
            Assert.fail("Account is locked after 3 unsuccessful login");
        }
        delay(2);
    }


    @AfterClass
    public void quit(){
        driver.quit();
    }
    public void login (){
        usernameInput = driver.findElement(By.id("user-name"));
        passwordInput = driver.findElement(By.id("password"));
        btnLogin = driver.findElement(By.id("login-button"));

        usernameInput.sendKeys(DemoShop.username);
        passwordInput.sendKeys(DemoShop.password);
        btnLogin.click();

    }
    public void loginBlocked (){
        usernameInput = driver.findElement(By.id("user-name"));
        wrongPasswordInput = driver.findElement(By.id("password"));
        btnLogin = driver.findElement(By.id("login-button"));

        usernameInput.sendKeys(DemoShop.username);
        wrongPasswordInput.sendKeys(DemoShop.wrongPassword);
        btnLogin.click();

        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Login Failed for user"+DemoShop.username);
    }
    static void delay(long detik) {
        try {
            Thread.sleep(detik * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
