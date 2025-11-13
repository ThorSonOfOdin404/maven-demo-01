import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import java.io.*;
import java.util.Properties;

public class BrowserLaunch {
    static Properties seleniumProps;
    private final static Logger LOGGER = Logger.getLogger(BrowserLaunch.class);

    static {
        try { seleniumProps = getSeleniumProps(); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public static void main(String[] args) throws IOException, InterruptedException{
        System.setProperty("webdriver.chrome.driver", seleniumProps.getProperty("chromeDriver"));
//        System.setProperty("webdriver.gecko.driver", seleniumProps.getProperty("geckoDriver"));

        testChromeBrowser();
        testBraveBrowser();
    }

    private static void testBraveBrowser() throws InterruptedException {
        LOGGER.info("\033[0;32;48mTesting Brave Browser\033[0m");
        ChromeOptions options = new ChromeOptions().setBinary("/usr/bin/brave-browser-stable");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.youtube.com");

        if (driver.getTitle().startsWith("YouTube")) {
            LOGGER.info("Opened The YouTube page on brave");
        } else {
            LOGGER.info("Could not open the YouTube page on brave browser");
        }
        Thread.sleep(2000);
        driver.close();
    }


    private static void testChromeBrowser() throws InterruptedException {
        LOGGER.info("\033[0;32;48mTesting Chrome Browser\033[0m");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.youtube.com");

        if (driver.getTitle().startsWith("YouTube")) {
            LOGGER.info("Opened The YouTube page on chrome");
            WebElement searchbox = driver.findElement(By.className("ytSearchboxComponentInput"));
            searchbox.click();
            searchbox.clear();
            searchbox.sendKeys("Universal IT services youtube channel");
            searchbox.submit();
            Thread.sleep(3000);
            driver.quit();
        } else {
            LOGGER.info("Could not open the YouTube page on chrome");
        }
    }

    private static Properties getSeleniumProps() throws IOException {
        try{
            FileInputStream fIS = new FileInputStream("selenium.properties");
            Properties props = new Properties();
            props.load(fIS);
            return props;
        }catch (IOException exception){
            LOGGER.info(exception);
            throw exception;
        }
    }
}
