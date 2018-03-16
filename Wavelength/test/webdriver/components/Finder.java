package webdriver.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import webdriver.driver.Driver;

class Finder {
	
	static WebElement find(Driver driver, String id, String text) {
		WebElement button = driver.findElement(By.id(id));
		if (text != null) {
			button = button.findElement(By.xpath("//*[text()='" + text + "']"));
		}
		return button;
	}
	
	static boolean hasElement(Driver driver, String id, String text) {
		driver.removeImplicitTimeout();
		if (driver.findElements(By.id(id)).size() == 0) {
			return false;
		}
		WebElement button = driver.findElement(By.id(id));
		if (text != null && button.findElements(By.xpath("//*[text()='" + text + "']")).size() == 0) {
			return false;
		}
		return true;
	}
	
}
