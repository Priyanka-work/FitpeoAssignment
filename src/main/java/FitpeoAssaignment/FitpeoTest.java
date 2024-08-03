package FitpeoAssaignment;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class FitpeoTest {

	WebDriver driver;

	@BeforeTest
	public void openBrowser() {
		driver = new ChromeDriver();
	}

	@Test
	public void automating() throws InterruptedException, AWTException {
		driver.manage().window().maximize();
		driver.get("https://www.fitpeo.com/home");
		driver.get("https://fitpeo.com/revenue-calculator");

		WebElement slider = driver.findElement(By.xpath(
				"//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary MuiSlider-sizeMedium')]/span[3]"));

		Actions actions = new Actions(driver);

		actions.clickAndHold(slider).moveByOffset(94, 0).release().perform();

		WebElement textField = driver.findElement(By.cssSelector("input[class*=\"MuiInputBase-input\"]"));

		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		wait.until(ExpectedConditions.attributeToBe(textField, "value", "823"));

		String actualValue = textField.getAttribute("value");
		if (actualValue.equals("823")) {
			System.out.println("Slider adjustment successful. Text field value is: " + actualValue);
		} else {
			System.out.println("Slider adjustment failed. Expected: 823, Actual: " + actualValue);
		}
		driver.findElement(By.xpath("//input[contains(@class,\"MuiInputBase-input\")]")).clear();
		driver.findElement(By.xpath("//input[contains(@class,\"MuiInputBase-input\")]")).click();
		Thread.sleep(5000);
		WebElement ele = driver.findElement(By.xpath("//input[contains(@class,\"MuiInputBase-input\")]"));
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_BACK_SPACE);
		Thread.sleep(1000);
		rb.keyPress(KeyEvent.VK_BACK_SPACE);
		Thread.sleep(1000);
		rb.keyPress(KeyEvent.VK_BACK_SPACE);
		Thread.sleep(1000);
		rb.keyPress(KeyEvent.VK_5);
		rb.keyPress(KeyEvent.VK_6);
		rb.keyPress(KeyEvent.VK_0);

		String styleAttribute = slider.getAttribute("style");
		String leftValue = extractLeftValue(styleAttribute);
		Assert.assertEquals(leftValue, "28%", "Slider position did not update correctly based on text field input");
		driver.findElement(By.xpath("(//input[@type=\"checkbox\"])[1]")).click();
		driver.findElement(By.xpath("(//input[@type=\"checkbox\"])[2]")).click();
		driver.findElement(By.xpath("(//input[@type=\"checkbox\"])[3]")).click();
		driver.findElement(By.xpath("(//input[@type=\"checkbox\"])[8]")).click();

		WebElement reimbursementValue = driver
				.findElement(By.xpath("//p[text()=\"Total Recurring Reimbursement for all Patients Per Month:\"]/p"));
		wait.until(ExpectedConditions.textToBePresentInElement(reimbursementValue, "$75600"));
		String actualValue1 = reimbursementValue.getText();
		String expectedValue = "$75600";
		Assert.assertEquals(actualValue1, expectedValue, "The total recurring reimbursement value is incorrect");

	}

	private String extractLeftValue(String style) {
		String[] styles = style.split(";");
		for (String s : styles) {
			if (s.trim().startsWith("left")) {
				return s.split(":")[1].trim();
			}
		}
		return "";
	}
}
