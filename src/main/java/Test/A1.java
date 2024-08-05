package Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class A1 {

    public static Actions actions;

    public static void main(String[] args) throws InterruptedException {

        // Setting the driver path
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\wwwsa\\Downloads\\chromedriver-v126\\chromedriver-win64\\chromedriver.exe");

        // Creating an object for ChromeDriver class
        ChromeDriver driver = new ChromeDriver();

        // Visiting the website using the get() method
        driver.get("https://fitpeo.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Maximizing the windows
        driver.manage().window().maximize();

        // Clicking on the Revenue Calculator
        driver.findElement(By.linkText("Revenue Calculator")).click();

        // Scrolling down to the Revenue Calculator Slider
        Thread.sleep(3000);
        driver.executeScript("window.scrollBy(0,600)");
        driver.executeScript("window.scrollBy(0,-50)");
         
        //Adjust the slider to set its value to 820 & Once the slider is moved the bottom text field value should be updated to 820
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            WebElement slider = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.cssSelector("span.MuiSlider-thumb input[type=range]")));

            String script = "var slider = arguments[0];\n" +
                    "var value = 820;\n" +
                    "var max = slider.getAttribute('max');\n" +
                    "var min = slider.getAttribute('min');\n" +
                    "var percent = (value - min) / (max - min) * 100;\n" +
                    "slider.value = value;\n" +
                    "slider.setAttribute('value', value);\n" +
                    "slider.style.setProperty('--value', value);\n" +
                    "slider.style.setProperty('--x', percent + '%');\n" +
                    "slider.dispatchEvent(new Event('input', { bubbles: true }));\n" +
                    "slider.dispatchEvent(new Event('change', { bubbles: true }));\n" +
                    "var sliderRoot = slider.closest('.MuiSlider-root');\n" +
                    "var thumbElement = sliderRoot.querySelector('.MuiSlider-thumb');\n" +
                    "var trackElement = sliderRoot.querySelector('.MuiSlider-track');\n" +
                    "if (thumbElement) {\n" +
                    "    thumbElement.style.left = percent + '%';\n" +
                    "}\n" +
                    "if (trackElement) {\n" +
                    "    trackElement.style.width = percent + '%';\n" +
                    "}";

            driver.executeScript(script, slider);

            Thread.sleep(1000);

            System.out.println("Updated slider value: " + slider.getAttribute("value"));
            WebElement textbox = driver.findElement(By.cssSelector("input[type='number']"));
            String updateTextboxScript = "arguments[0].value = '820';" +
                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                    "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));";
            driver.executeScript(updateTextboxScript, textbox);

            // Wait for potential asynchronous updates
            Thread.sleep(1000);

            // Verify the update
            String updatedTextboxValue = textbox.getAttribute("value");
            System.out.println("Current textbox value has been updated to: " + updatedTextboxValue);
            
            
            
            
            //Entering the value 560 in the text field. The slider changes according to that
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", textbox);
            textbox.sendKeys(Keys.BACK_SPACE);
            textbox.sendKeys(Keys.BACK_SPACE);
            textbox.sendKeys(Keys.BACK_SPACE);
            textbox.sendKeys("5");
            textbox.sendKeys("6");
            textbox.sendKeys("0");
            
            
            driver.executeScript("window.scrollBy(0,-180)");
            
            // Verify the update
            Thread.sleep(10000);
            System.out.println("Again the Value has been update as : " + textbox.getAttribute("value"));
            System.out.println("We saw that the slider moved as per the value");
            
            
            //Ensure that when the value 560 is entered in the text field, the slider's position is updated to reflect the value 560.//
            driver.executeScript("window.scrollBy(0,-200)");
            Thread.sleep(1000);
            
            
            
            
            //CPT CheckBoxes///
            //select the checkboxes for CPT-99091, CPT-99453, CPT-99454, and CPT-99474.
             List<String> targetCPTCodes = Arrays.asList("CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474");

             List<WebElement> allCheckboxes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, 'MuiBox-root')]//input[@type='checkbox']")));

            for (WebElement checkbox : allCheckboxes) {
                WebElement parentDiv = (WebElement) ((JavascriptExecutor) driver).executeScript(
                    "return arguments[0].closest('.MuiBox-root')", checkbox);
                String cptCode = parentDiv.findElement(By.xpath(".//p[contains(@class, 'MuiTypography-root')]")).getText();

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", checkbox);
                Thread.sleep(500);

                boolean shouldBeSelected = targetCPTCodes.contains(cptCode);
                boolean isCurrentlySelected = (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked;", checkbox);

                if (shouldBeSelected != isCurrentlySelected) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click(); arguments[0].checked = " + shouldBeSelected + ";", checkbox);
                    System.out.println((shouldBeSelected ? "Selected" : "Unselected") + " checkbox for " + cptCode);
                } else {
                    System.out.println("Checkbox for " + cptCode + " don't want to be selected");
                }

                Thread.sleep(500);
            }

            textbox.sendKeys(Keys.BACK_SPACE);
            textbox.sendKeys(Keys.BACK_SPACE);
            textbox.sendKeys(Keys.BACK_SPACE);
            textbox.sendKeys("8");
            textbox.sendKeys("2");
            textbox.sendKeys("0");
            
            
            //Validate Total Recurring Reimbursement:
            //Verify that the header displaying Total Recurring Reimbursement for all Patients Per Month: shows the value $110700.	
            
        
            
            // Locate the element containing the value
            WebElement valueElement = driver.findElement(By.cssSelector("div.MuiBox-root.css-m1khva p.MuiTypography-body1"));

            // Get the text of the element
            String valueText = valueElement.getText();

            // Expected value
            String expectedValue = "$110700";

            // Verify the value and print out a statement
            if (valueText.equals(expectedValue)) {
                System.out.println("Verification successful: The value is " + expectedValue + ".");
            } else {
                System.out.println("Verification failed: The value is " + valueText + " instead of " + expectedValue + ".");
            }

            
            Thread.sleep(10000);
            driver.quit();

            
            }catch (Exception e) {
            System.out.println("Exception occurred while finding or interacting with the slider:");
            e.printStackTrace();

            // Print out the page source to see if the element is present in the HTML
            System.out.println("Page source:");
            System.out.println(driver.getPageSource());
        }
        
        
        

    }

}
