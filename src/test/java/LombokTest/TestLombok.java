package LombokTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
class Student {
    private int id;
    private String name;
}

public class TestLombok{
    public static final Logger LOGGER = Logger.getLogger(TestLombok.class);
    private WebDriver driver;

    @Before
    public void init(){
        WebDriverManager.chromedriver().setup();
        PropertyConfigurator.configure("log4j.properties");

        driver = new ChromeDriver(new ChromeOptions().addArguments("--incognito"));
    }

    @Test
    @SneakyThrows
    public void Test1(){
        Student student = Student.builder().id(10).build();
        System.out.println(student);

        LOGGER.info("This is info message");
        LOGGER.trace("This is trace message");
        LOGGER.warn("This is warn message");
        LOGGER.error("This is error message");
        LOGGER.fatal("This is fatal message");
    }

    @Test
    @SneakyThrows
    public void TestApp(){
        driver.get("http://192.168.1.101:3005/user/login");
        WebElement email = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement submit = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/main/main/div/form/button"));

        email.sendKeys("testmail@gmail.com");
        pass.sendKeys("testpass");
        Thread.sleep(1000);
        submit.click();
        Thread.sleep(1000);

        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/ul/li")).isDisplayed());
    }

    @After
    public void cleanup(){
        driver.quit();
    }
}
