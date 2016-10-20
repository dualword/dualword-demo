package com.dualword.javaeeweb;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTest {

	private WebDriver driver;
	private WebDriverWait wait;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/Java-EE-web/index.jsp");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		driver = null;
		wait = null;
	}

	@Test 
	public void test() {
		String xp = "//a[text()='Image Servlet'][1]";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xp)));
		assertNotNull(driver.findElement(By.xpath(xp)));
		driver.findElement(By.xpath(xp)).click();
	}

}
