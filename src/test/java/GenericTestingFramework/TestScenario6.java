package GenericTestingFramework;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestScenario6 extends BaseClass{
    private WebDriver driver;
    private final Logger LOGGER = Logger.getLogger(TestScenario6.class);

    @Before
    public void init(){
        super.init();
        if(driver == null){
            driver = getDriver();
        }
    }

    @Test
    public void TestIframe(){
        driver.get("file:///home/pratham/mycode/test/html-codes/iframes/index.html");
        List<WebElement> iFrames = driver.findElements(By.tagName("iframe"));
        Map<String, WebElement> id_to_iframe = new HashMap<>();
        for(WebElement element : iFrames){
            String elem_id = element.getAttribute("id");
            id_to_iframe.put(elem_id,element);
        }

        for (Map.Entry<String,WebElement> entry : id_to_iframe.entrySet()){
            driver.switchTo().frame(entry.getValue());
            String text = driver.findElement(By.id("sample-heading")).getText();
            LOGGER.info("Text from " + entry.getKey() + " : " + text);

            if(text.contains("page 1")){
                driver.switchTo().frame(driver.findElement(By.id("iframe1")));
                text = driver.findElement(By.id("sample-heading")).getText();

                LOGGER.info("\tText from child of iframe1 -:- " + text);
                Assert.assertTrue(text.contains("page 2"));

                driver.switchTo().parentFrame();

                text = driver.findElement(By.id("sample-heading")).getText();
                Assert.assertTrue(text.contains("page 1"));
            }
            driver.switchTo().defaultContent();
            Assert.assertEquals("IFrames Demo",driver.getTitle());
        }
        driver.switchTo().frame(1);
        String text = driver.findElement(By.id("sample-heading")).getText();
        Assert.assertTrue(text.contains("page 2"));
        System.out.println(driver.findElement(By.tagName("title")).getText());

        String text1 = driver.switchTo().alert().getText();
    }
}
