package GenericTestingFramework;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

//Action DEMO
public class TestScenario11 extends BaseClass{
    private WebDriver driver;
    @Before
    public void init(){
        super.init();
        driver = getDriver();
    }
    @SneakyThrows
    @Test
    public void Test01(){
        driver.get("http://localhost:8800/form/textBoxAndAreas/");
        driver.manage().window().fullscreen();
        WebElement textArea1 = driver.findElement(By.id("area1"));
        WebElement textArea2 = driver.findElement(By.id("area2"));
        textArea1.sendKeys("This is a great world and we can play our part to make it better!");

        Actions action = new Actions(driver);

        action
            .keyDown(textArea1, Keys.CONTROL)
            .sendKeys("ac")
            .keyUp(Keys.CONTROL)
            .build()
            .perform();

        action
            .keyDown(textArea2, Keys.CONTROL)
            .sendKeys("v")
            .keyUp(Keys.CONTROL)
            .build()
            .perform();
        //Asserting successful copy and pase operation
        Assert.assertEquals(textArea1.getText(), textArea2.getText());

        WebElement doubleClickArea = driver.findElement(By.id("doubleClickArea"));
        action.doubleClick(doubleClickArea).perform();

        //Asserting successful double click
        Assert.assertEquals("This area was double clicked",doubleClickArea.getText());
    }

    @Test
    public void TestDragAndDrop(){
        driver.get("http://localhost:8800/dragAndDrop");

        WebElement draggableElement = driver.findElement(By.id("draggableItem"));
        String draggableElementText = draggableElement.getText();

        WebElement dropZone1 = driver.findElement(By.id("dropZone1"));
        WebElement dropZone2 = driver.findElement(By.id("dropZone2"));

        Actions actions = new Actions(driver);

        actions
                .dragAndDrop(draggableElement, dropZone1)
                .perform();
        Assert.assertTrue(dropZone1.getText().contains(draggableElementText));

        actions
                .clickAndHold(draggableElement)
                .moveToElement(dropZone2)
                .release()
                .perform();
        Assert.assertTrue(dropZone2.getText().contains(draggableElementText));
    }

    @Test
    public void TestSlider(){
        driver.get("https://www.w3schools.com/howto/howto_js_rangeslider.asp");


        WebElement slider = driver.findElements(By.xpath("//input[@type='range']")).get(1);
        String sliderValueElemXPath = "//div[@id=\"slidecontainer\"]/child::span[last()]";
        WebElement sliderValueElem = driver.findElement(By.xpath(sliderValueElemXPath));
        Actions actions = new Actions(driver);

        actions
                .clickAndHold(slider)
                .moveByOffset(100,0)
                .perform();

        Assert.assertNotEquals(50 ,Integer.parseInt(sliderValueElem.getText()));
    }

    @Test
    public void TestReplaceTextBoxValue(){
        driver.get("http://localhost:8800/form/textBoxAndAreas");
        WebElement textArea1 = driver.findElement(By.id("area1"));
        String defaultText = "This is default text";
        textArea1.sendKeys(defaultText);

        Actions actions = new Actions(driver);
        actions
                .keyDown(textArea1, Keys
                .CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys("This is new text")
                .perform();
        Assert.assertNotEquals(defaultText, textArea1.getText());
    }

    @Test
    public void TestHover(){
        driver.get("http://localhost:8800/hoverAction");

        WebElement container = driver.findElement(By.className("container"));
        Actions action = new Actions(driver);
        try{
            driver.findElement(By.className("hidden-element"));
        } catch (NoSuchElementException e) {
            System.out.println("The hidden element is not present in the DOM.");
        }

        action.moveToElement(container).perform();

        Assert.assertTrue(driver.findElement(By.className("hidden-element")).isDisplayed());
    }
}
