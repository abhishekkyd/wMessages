package org.wcontacts.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.wcontacts.commons.Commands;
import org.wcontacts.commons.Excel;

public class GetContacts {

	private static String filepath = System.getProperty("user.dir")
			+ "/output/Messages.xls";
	private static File file = null;
	private static FileInputStream inFile = null;
	private static FileOutputStream outFile = null;
	private static WebDriver driver = null;
	private static WebDriverWait wait = null;
	private static WebDriverWait wait1 = null;
	private static Actions act = null;
	private static JavascriptExecutor js = null;
	private static List<WebElement> chats = null;
	private static List<WebElement> authors = null;
	private static List<WebElement> messages = null;
	private static List<WebElement> times = null;
	private static String chatTitle = null;
	private static String author = null;
	private static String message = null;
	private static String phoneText = null;
	private static String[] phone = null;
	private static String time = null;
	private static int chatCount = 1;
	private static HSSFWorkbook workbook = null;
	private static HSSFSheet sheet = null;
	private static Row row = null;
	private static int rowNext = 1;
	private static int rowNum = 1;
	private static Cell cell1 = null;
	private static Cell cell2 = null;
	private static Cell cell3 = null;
	private static Cell cell4 = null;
	private static Cell cell5 = null;
	private static int cellNum1 = 0;
	private static int cellNum2 = 1;
	private static int cellNum3 = 2;
	private static int cellNum4 = 3;
	private static int cellNum5 = 4;

	public static void main(String[] args) {

		try {
			file = new File(filepath);
			inFile = new FileInputStream(file);
			workbook = new HSSFWorkbook(inFile);
			sheet = workbook.getSheetAt(0);
			rowNext = sheet.getLastRowNum() + 1;

			System.setProperty("webdriver.chrome.driver",
					"drivers/chromedriver");
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile remoteProfile = profile.getProfile("myProfile");
			driver = new FirefoxDriver(remoteProfile);
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 60);
			wait1 = new WebDriverWait(driver, 5);
			act = new Actions(driver);
			js = (JavascriptExecutor) driver;

			driver.get("https://web.whatsapp.com/");
			// Commands.waitForElement(wait, "//div[@class='qrcode']");
			// Commands.waitUntilElementInvisible(wait,
			// "//div[@class='qrcode']");
			Commands.waitUntilAllElementsVisible(wait,
					"//div[@id='side']//div[@class='chat-avatar']");
			Object scrollH = js
					.executeScript("var elem = document.getElementById('pane-side'); return elem.scrollHeight;");
			int scrollHeight = Integer.parseInt(scrollH.toString());
			System.out.println(scrollHeight);
			int scrollEnd = (int) ((scrollHeight / 100) * 95);
			System.out.println(scrollEnd);
			Object scrollT = js
					.executeScript("var elem = document.getElementById('pane-side'); return elem.scrollTop;");
			int scrollTop = Integer.parseInt(scrollT.toString());
			int scroll = 0;
			Thread.sleep(10000);
			do {
				try {
					chats = driver
							.findElements(By
									.xpath("//div[@id='pane-side']//div[@class='chat-title']"));
					System.out.println("Total number of chats are: "
							+ chats.size());
					System.out.println("");
					try {
						for (WebElement chat : chats) {
							act.moveToElement(chat).build().perform();
							chat.click();
							chatTitle = chat.getText();
							chatCount++;
							rowNum = rowNext;
							int l = 0;
							while (l <= 2) {
								js.executeScript("var elem = document.getElementById('main'); elem.scrollTop=0;");
								Commands.waitUntilElementInvisible(wait1,
										"//div[@class='btn-more']");
								Commands.waitUntilElementInvisible(wait1,
										"//div[@class='btn-more']");
								l++;
							}
							messages = driver
									.findElements(By
											.xpath("//div[@class='bubble bubble-text']//div[@class='message-text']//span[@class='emojitext selectable-text']"));
							System.out.println("Chat" + chatCount + ": "
									+ chatTitle);
							System.out.println("Total number of messages are: "
									+ messages.size());
							System.out.println("");
							for (WebElement msg : messages) {
								message = msg.getText();
								phoneText = msg.getAttribute("data-reactid");
								phone = phoneText.split("[a-z]");
								phone = phone[9].split("-");
								phone = phone[1].split("@");
								row = Excel.getRow(sheet, row, rowNum);
								cell1 = Excel.getCell(sheet, row, cell1,
										cellNum1);
								cell2 = Excel.getCell(sheet, row, cell2,
										cellNum2);
								cell4 = Excel.getCell(sheet, row, cell4,
										cellNum4);
								cell1.setCellValue(chatTitle);
								cell2.setCellValue(phone[0]);
								cell4.setCellValue(message);
								rowNum++;
							}
							rowNum = rowNext;
							times = driver
									.findElements(By
											.xpath("//div[@class='bubble bubble-text']//div[@class='message-meta']//span[@class='message-datetime']"));
							for (WebElement tm : times) {
								time = tm.getText();
								row = Excel.getRow(sheet, row, rowNum);
								cell5 = Excel.getCell(sheet, row, cell5,
										cellNum5);
								cell5.setCellValue(time);
								rowNum++;
							}
//							try {
//								rowNum = rowNext;
//								authors = driver
//										.findElements(By
//												.xpath("//div[@class='bubble bubble-text']//h3[contains(@class,'message-author')]"));
//								for (WebElement aut : authors) {
//									author = aut.getText();
//									row = Excel.getRow(sheet, row, rowNum);
//									cell3 = Excel.getCell(sheet, row, cell3,
//											cellNum3);
//									cell3.setCellValue(author);
//									rowNum++;
//								}
//							} catch (Exception e) {
//								
//								for (int i = rowNum; i <= sheet.getLastRowNum(); i++) {
//									author = chatTitle;
//									row = Excel.getRow(sheet, row, rowNum);
//									cell3 = Excel.getCell(sheet, row, cell3,
//											cellNum3);
//									cell3.setCellValue(author);
//								}
//							}
							rowNext = sheet.getLastRowNum() + 2;
						}
					} catch (Exception e) {
						
					}
					scroll = scroll + 1000;
					js.executeScript("var elem = document.getElementById('pane-side'); elem.scrollTop="
							+ scroll + ";");
					scrollT = js
							.executeScript("var elem = document.getElementById('pane-side'); return elem.scrollTop;");
					scrollTop = Integer.parseInt(scrollT.toString());
					System.out.println(scrollTop);
					System.out.println("");
					System.out.println("");
				} catch (Exception e) {
					
				}
				if (scrollTop == 0) {
					break;
				}
			} while (scrollTop < scrollHeight-1);

			// driver.findElement(By.xpath("//button[@title='Menu']")).click();
			// driver.findElement(By.xpath("//a[@text='Log out']")).click();
			// driver.quit();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				inFile.close();
				outFile = new FileOutputStream(file);
				workbook.write(outFile);
				outFile.close();
				workbook.close();
				// SendMail.sendEmailUsingGmail("wcontacttest@gmail.com",
				// filepath,
				// "Messages.xls");
			} catch (Exception e) {
				
				System.out.println(e);
			}
		}

	}

}
