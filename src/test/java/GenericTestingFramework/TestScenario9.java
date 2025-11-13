package GenericTestingFramework;

// Testing Forms

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestScenario9 extends BaseClass{
    private WebDriver driver;

    @Before
    public void init(){
        super.init();
        driver = getDriver();
    }

    @SneakyThrows
    @Test
    public void Test(){
        driver.get("https://demoqa.com/automation-practice-form");
        // Text Fields
        driver.findElement(By.id("firstName")).sendKeys("John");
        driver.findElement(By.id("lastName")).sendKeys("Doe");
        driver.findElement(By.id("userEmail")).sendKeys("john123@gmail.com");

        JavascriptExecutor js = (JavascriptExecutor)driver;

        WebElement genderRadioBtn = driver.findElement(By.xpath("//input[@name='gender' and @value='Male']"));
        js.executeScript("arguments[0].click();", genderRadioBtn);

        driver.findElement(By.id("userNumber")).sendKeys("1234567890");

        WebElement subjectsWE = driver.findElement(By.id("subjectsInput"));
        subjectsWE.sendKeys("English");
        subjectsWE.sendKeys(Keys.RETURN);
        subjectsWE.sendKeys("Comp");
        subjectsWE.sendKeys(Keys.RETURN);

        List<WebElement> hobbiesCheckboxes = driver.findElements(By.xpath("//input[contains(@id,'hobbies-checkbox-')]"));
        for (WebElement hobby : hobbiesCheckboxes){
            js.executeScript("arguments[0].click()",hobby);
        }

        driver.findElement(By.xpath("//input[@id='uploadPicture']")).sendKeys("/home/pratham/Pictures/wardrobe_design.excalidraw");

        driver.findElement(By.tagName("textarea")).sendKeys("H.no. 112/234, Panipat, Haryana");

        // REACT DROP DOWN TEST
        driver.findElement(By.xpath("//*[contains(text(),'Select State')]")).click();
        List<WebElement> stateOptions = driver.findElements(By.xpath("//*[contains(@id,'react-select-3-option-')]"));
        for(WebElement stateOption : stateOptions){
            if(stateOption.getText().equals("Haryana")){
                js.executeScript("arguments[0].click()", stateOption);
                break;
            }
        }
        driver.findElement(By.xpath("//*[contains(text(),'Select City')]")).click();
        List<WebElement> cityOptions = driver.findElements(By.xpath("//*[contains(@id,'react-select-4-option-')]"));
        for(WebElement cityOption : cityOptions){
            if(cityOption.getText().equals("Panipat")){
                js.executeScript("arguments[0].click()", cityOption);
                break;
            }
        }

        js.executeScript("arguments[0].click();",driver.findElement(By.id("submit")));

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        Assert.assertEquals("Haryana Panipat", driver.findElement(By.xpath("//td[text()='State and City']/following-sibling::td")).getText());
        String successTest = driver.findElement(By.id("example-modal-sizes-title-lg")).getText();
        Assert.assertEquals("Thanks for submitting the form", successTest);
    }

    @Test
    public void DatePickerTest(){
        driver.get("https://demoqa.com/automation-practice-form");

        // Date picker
        WebElement dobInput = driver.findElement(By.id("dateOfBirthInput"));
        JavascriptExecutor js = (JavascriptExecutor ) driver;
        js.executeScript("arguments[0].click()",dobInput);

        String dobString = "20-July-2024";
        String date = dobString.split("-")[0];
        Month month = Month.valueOf(dobString.split("-")[1].toUpperCase());
        String year = dobString.split("-")[2];

        WebElement selectMonthWE = driver.findElement(By.xpath("//select[@class='react-datepicker__month-select']"));
        Select selectMonth = new Select(selectMonthWE);
        selectMonth.selectByIndex(month.getValue());
        WebElement selectYearWE =  driver.findElement(By.xpath("//select[@class='react-datepicker__year-select']"));
        Select selectYear = new Select(selectYearWE);
        selectYear.selectByValue(year);

        String defaultDate = dobInput.getAttribute("value");
        WebElement day =
                driver.findElement(By.xpath("//*[@class='react-datepicker__week']/" +
                        "div[" +
                        "contains(@class,'react-datepicker__day--" + "0"+date+
                        "') and " +
                        "not(contains(@class,'outside-month'))" +
                        "]"));
        day.click();
        String updatedDate = dobInput.getAttribute("value");
        Assert.assertNotEquals(defaultDate, updatedDate);
    }

    @Test
    public void TestCheckBoxAndRadios(){
        driver.get("http://localhost:8800/radioAndCheckboxes/");

        List<WebElement> checkboxes = driver.findElements(By.name("lang"));
        for(WebElement checkbox : checkboxes){
            if(checkbox.getAttribute("value").equals("java")){
                Assert.assertTrue(checkbox.isSelected());
            }else if(checkbox.getAttribute("value").equals("perl")){
                Assert.assertFalse(checkbox.isEnabled());
            }
        }

        driver.findElement(By.xpath("//input[@type='radio' and @value='male']")).click();
        List<WebElement> radios = driver.findElements(By.name("gender"));
        for(WebElement radio : radios){
            if(radio.getAttribute("value").equals("male")){
                Assert.assertTrue(radio.isSelected());
            }
        }
    }

    @Test
    public void TestSelectSortingOrder(){
        driver.get("http://localhost:8800/selectMenu");

        WebElement cars = driver.findElement(By.id("cars"));
        //List of cars in menu are not in sorted order
        Select carsSelectMenu = new Select(cars);
        //This should fail
        Assert.assertTrue(testSorting(carsSelectMenu));

    }

    private boolean testSorting(Select selectWE){
        List<WebElement> options = selectWE.getOptions();
        List<WebElement> sortedList = new ArrayList<>(options);
        sortedList.sort( (elem1,elem2)-> {
            return elem1.getAttribute("value").compareTo(elem2.getAttribute("value"));
        } );
        return options.equals(sortedList);
    }

    public static void main(String[] args) {
    int x = 1173741824;
    int y = 1172311824;
        System.out.println(x+y);

    }
}

