package Test_Case;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Send_Emails_to_Startups2 {

	private static WebDriver driver;
	public int statups_no;
	private static String chromedriverpath = "E:\\Siddhartha\\Selenium\\Browser Drivers\\chrome\\chromedriver_win32\\chromedriver.exe";

	public JavascriptExecutor jse;
	public int i;
	public WebElement DIVelement;

	public String Name = "1";
	public static String Email = "tty";
	public String message = "1";

	public Send_Emails_to_Startups2() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", chromedriverpath);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		options.addArguments("--disable-web-security");
		options.addArguments("--allow-running-insecure-content");
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.merge(capabilities);
		driver = new ChromeDriver(options);
		jse = (JavascriptExecutor) driver;
		driver.get("https://mappedinisrael.com/");

	}

	public void Check_total_startup() throws InterruptedException {
		Thread.sleep(20000);
		driver.switchTo().frame(0);
		Thread.sleep(3000);
		WebElement ele = driver.findElement(By.xpath("//div[@style=\"color:#e64c3c\"]"));
		String No_of_startups = ele.getText();
		statups_no = Integer.valueOf(No_of_startups);

	}

	public void navigate() throws InterruptedException {

		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(text(),'Startups')]")).click();
		Thread.sleep(3000);

	}

	public void Send_Email() throws InterruptedException {

		Thread.sleep(2000);

		String Startupname = driver
				.findElement(By.cssSelector(".place_header__title.place_open_color--dark.ng-binding")).getText();

		WebElement Contact = driver.findElement(By.cssSelector(".cta--email.cta.btn.ng-scope"));
		Contact.click();
		Thread.sleep(2000);

		System.out.println("Startup no" + ":" + i);
		System.out.println("Sending mail to...... " + ":" + Startupname);

		driver.findElement(By.cssSelector("#name")).sendKeys(Name);

		Thread.sleep(2000);

		driver.findElement(By.cssSelector("input[id='email']:nth-child(2)")).sendKeys(Email);
		Thread.sleep(2000);

		driver.findElement(By.cssSelector("#message")).sendKeys(message);
		Thread.sleep(2000);

		WebElement sentbutton = driver.findElement(By.cssSelector(".btn.btn-success.btn-lg.btn-block.ng-scope"));

		if (sentbutton.isEnabled()) {

			sentbutton.click();

			Thread.sleep(2000);

			try {

				if (driver.findElement(By.cssSelector(".ng-binding.toast-title")).isDisplayed()) {

					System.out.println(driver.findElement(By.cssSelector(".ng-binding.toast-title")).getText());

				}

				else if (driver
						.findElement(By
								.xpath(".//*[contains(text(),'Could not send your message, please try again later')]"))
						.isDisplayed()) {

					System.out.println(driver
							.findElement(By
									.xpath(".//*[contains(text(),'Could not send your message, please try again later')]"))
							.getText());
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

			System.out.println("-------------------------------------");

			driver.navigate().back();
			Thread.sleep(2000);
		}

		else {

			System.err.println("Message not sent:");

			Thread.sleep(1000);

			try {

				if (driver.findElement(By.xpath(".//*[contains(text(),'Invalid email field')]")).isDisplayed()) {

					System.out.println("Invalid Email id entered");

				}

				if (driver.findElement(By.xpath(".//*[contains(text(),'Please enter a message')]")).isDisplayed()) {

					System.out.println("Please enter message");

				}
			}

			catch (Exception e) {

				e.printStackTrace();
			}

			System.out.println("-------------------------------------");

			driver.navigate().back();
			Thread.sleep(2000);
		}

	}

	public void select_startup() throws InterruptedException {

		if (i == 1) {
			navigate();
			scroll_click_startup();
		}

		else if (i % 3 == 0) {

			scroll_click_startup();

		}

		else {

			driver.switchTo().frame(0);
			Thread.sleep(2000);

			scroll_click_startup();
		}
	}

	public void scroll_click_startup() throws InterruptedException {
		Thread.sleep(3000);

		DIVelement = driver.findElement(By.xpath(".//*[@id='places-list']/div[1]/div[" + i + "]/div"));
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", DIVelement);

		String js = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";

		jse.executeScript(js, DIVelement);
		Thread.sleep(1000);
		DIVelement.click();
		Thread.sleep(3000);
		Send_Email();

	}

	public void Send_Email_to_all_Startups() throws InterruptedException {

		int scroll = 50;

		Check_total_startup();

		for (i = 1; i < statups_no; i++) {

			if (i > 3) {

				driver.switchTo().frame(0);
				Thread.sleep(3000);

				WebElement ele = driver.findElement(By.cssSelector("#places-list"));
				jse.executeScript("arguments[0].scrollTop = arguments[1];", ele, scroll * i);
				Thread.sleep(3000);
				select_startup();

			}

			else {

				select_startup();

			}

		}

	}

	public static void main(String[] args) throws InterruptedException {

		Send_Emails_to_Startups2 s = new Send_Emails_to_Startups2();
		s.Send_Email_to_all_Startups();

	}

}