package GenericTestingFramework;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;

/*
    Examples of finding WebElements using different types of Locators
 */
public class TestScenario2 extends BaseClass{
    private WebDriver driver;
    private static Logger LOGGER = Logger.getLogger(TestScenario2.class);
    private Properties objRepo;

    @Before
    public void init(){
        if(driver == null)
            super.init();
        objRepo = getObjectRepo();
        driver = getDriver();
    }

    @Test
    public void Test1() throws InterruptedException{
        driver.get(objRepo.getProperty("site_url"));
        driver.findElement(By.partialLinkText("Products")).click();
        driver.manage().window().fullscreen();
        Thread.sleep(2000);

    }

    @Test
    public void Test2(){
        driver.get("https://demoqa.com/broken");

        List<WebElement> links = driver.findElements(By.tagName("a"));
        for(WebElement link : links){
            String linkHref = link.getAttribute("href");
            if(linkHref != null)
                verifyLink(linkHref);
        }
    }

    private void verifyLink(String linkHref) {
        try{
            URL url = new URL(linkHref);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if(responseCode >= 400){
                LOGGER.warn("Link "+linkHref+ " is broken");
            }else{
                LOGGER.info("Link "+linkHref + " is OK! ");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
