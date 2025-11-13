package JSExecuterUtil;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class JsExecutorUtil {
    public static void flashElement(WebElement element, WebDriver driver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String color = element.getCssValue("backgroundColor");
        int time = 100;
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0)
                js.executeScript("arguments[0].style.backgroundColor=arguments[1]", element, color);
            else {
                js.executeScript("arguments[0].style.backgroundColor='#ffffff'", element);
            }
            Thread.sleep(time--);
        }
    }

    public static void borderElement(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='6px solid red';", element);
    }

    public static void takeScreenshot(WebElement element, WebDriver driver) throws IOException {
        borderElement(element, driver);
        File ss_output = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File ss_destination = new File("./logs/screenshots/SS1.jpg");
        FileUtils.copyFile(ss_output, ss_destination);
    }

    public static void navigateToURL(String url, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("navigation.navigate(arguments[0])", url);
    }

    public static long scrollToElement(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView()", element);
        Object obj = js.executeScript("return window.pageYOffset");
        return (long) obj;
    }
}
