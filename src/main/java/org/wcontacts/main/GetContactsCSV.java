package org.wcontacts.main;

import java.io.FileWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.wcontacts.commons.Commands;
import org.wcontacts.commons.SendMail;

public class GetContactsCSV {

	private static final String fileName = System.getProperty("user.dir")
			+ "/output/messages.csv";
	private static final String delimiter = ",";
	private static final String separator = "\n";
	private static final String header = "chatName,phoneNumber,message,time"
			+ separator;
	private static FileWriter fileWriter = null;
	private static WebDriver driver = null;
	private static WebDriverWait wait = null;
	private static Actions act = null;
	private static JavascriptExecutor js = null;
	private static List<WebElement> chats = null;
	private static List<WebElement> messages = null;
	private static List<WebElement> times = null;
	private static String chatTitle = null;

	public static void main(String[] args) {

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.append(header);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 60);
			act = new Actions(driver);
			js = (JavascriptExecutor) driver;

			driver.get("https://web.whatsapp.com/");
			Commands.waitForElement(wait, "//div[@class='qrcode']");
			Commands.waitUntilElementInvisible(wait, "//div[@class='qrcode']");
			Commands.waitUntilAllElementsVisible(wait,
					"//div[@id='side']//div[@class='chat-avatar']");
			Object scrollH = js
					.executeScript("var elem = document.getElementById('pane-side'); return elem.scrollHeight;");
			int scrollHeight = Integer.parseInt(scrollH.toString());
			Object scrollT = js
					.executeScript("var elem = document.getElementById('pane-side'); return elem.scrollTop;");
			int scrollTop = Integer.parseInt(scrollT.toString());
			int scroll = 0;
			do {
				chats = driver
						.findElements(By
								.xpath("//div[@id='pane-side']//div[@class='chat-title']"));
				System.out
						.println("Total number of chats are: " + chats.size());
				for (WebElement chat : chats) {
					act.moveToElement(chat).build().perform();
					chat.click();
					chatTitle = chat.getText();
					System.out.println("Chat title is: " + chatTitle);
					messages = driver
							.findElements(By
									.xpath("//div[@class='message-text']//span[@class='emojitext selectable-text']"));
					System.out.println("Total number of messages are: "
							+ messages.size());
					for (WebElement msg : messages) {
						String message = msg.getText();
						String phoneText = msg.getAttribute("data-reactid");
						String[] phone = phoneText.split("[a-z]");
						phone = phone[9].split("-");
						phone = phone[1].split("@");
						System.out.println("Phone number is: " + phone[0]);
						System.out.println(message);
						String data = chatTitle + delimiter + phone[0]
								+ delimiter + message + separator;
						fileWriter.append(data);
					}
					times = driver
							.findElements(By
									.xpath("//div[@class='message-meta']//span[@class='message-datetime']"));
					for (WebElement time : times) {
						String msgTime = time.getText();
						System.out.println("");
					}
				}
				scroll = scroll + 1000;
				js.executeScript("var elem = document.getElementById('pane-side'); elem.scrollTop="
						+ scroll + ";");
				scrollT = js
						.executeScript("var elem = document.getElementById('pane-side'); return elem.scrollTop;");
				scrollTop = Integer.parseInt(scrollT.toString());
				System.out.println("");
				System.out.println("");
			} while (scrollTop < 1000);

			// driver.findElement(By.xpath("//button[@title='Menu']")).click();
			// driver.findElement(By.xpath("//a[@text='Log out']")).click();
			Thread.sleep(2000);
			driver.quit();

			//SendMail.sendEmailUsingGmail("wcontacttest@gmail.com", fileName,
					//"messages.csv");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}
		}

	}
}