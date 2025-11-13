package GenericTestingFramework;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

import lombok.Getter;
import lombok.SneakyThrows;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

@Getter
public class BaseClass {
    private static final Logger LOGGER = Logger.getLogger(BaseClass.class);
    private static Properties objRepo;

    private WebDriver driver;
    public static DriverManagerType driverManagerType;
    static{
        PropertyConfigurator.configure("log4j.properties");
    }

    @SneakyThrows
    public static Properties getObjectRepo(){
        if(objRepo == null){
            objRepo = new Properties();
            objRepo.load(new FileInputStream("or.properties"));
        }
        return objRepo;
    }

    @BeforeClass
    public static void initProperties(){
        PropertyConfigurator.configure("log4j.properties");
        objRepo = getObjectRepo();
        driverManagerType = DriverManagerType.valueOf(BaseClass.objRepo.getProperty("browser").toUpperCase());
        WebDriverManager.getInstance(driverManagerType).setup();
    }

    @Before
    @SneakyThrows
    public void init(){
        if(driver == null) {
            Class<?> webDriverClass = Class.forName(driverManagerType.browserClass());
            switch (driverManagerType) {
                case CHROME:
                    Constructor<?> constructor = webDriverClass.getConstructor(ChromeOptions.class);
                    driver = (WebDriver) constructor.newInstance(
                            new ChromeOptions().addArguments(objRepo.getProperty("browser_props").split(" "))
                    );
                    break;
                default:
                    driver = (WebDriver) webDriverClass.newInstance();
            }
        }
    }

    @After
    public void cleanUp(){
        driver.quit();
    }
}
