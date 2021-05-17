import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumDownloadFileDemo {
    @Test
    @SneakyThrows
    public void  seleniumDownloadFile() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", System.getProperty("user.dir"));

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        driver.navigate().to("https://www.thinkbroadband.com/download");

        //We find the download links
        List<WebElement> list =driver.findElements(By.cssSelector("div.module>p>a>img"));

        //Click to 5MB web element
        WebElement el = list.get(list.size()-1);
        el.click();
        Thread.sleep(500);

        //Hide Google Popup Ad
        js.executeScript("document.querySelector(\"html > ins\").style.display='none'");

        //Again click to 5MB web element
        el.click();

        //Wait 15 seconds to download 5MB file.
        //You can write custom wait. Check Selenium Wait article on swtestacademy.com
        Thread.sleep(15000);

        //Get the user.dir folder
        File folder = new File(System.getProperty("user.dir"));

        //List the files on that folder
        File[] listOfFiles = folder.listFiles();

        boolean found = false;
        File f = null;

        //Look for the file in the files
        // You should write smart REGEX according to the filename
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String fileName = listOfFile.getName();
                System.out.println("File " + listOfFile.getName());
                if (fileName.matches("5MB.zip" )) {
                    f = new File(fileName);
                    found = true;
                }

            }
        }

        Assertions.assertTrue(found, "Downloaded document is not found");
        f.deleteOnExit();
        driver.close();
    }
}
