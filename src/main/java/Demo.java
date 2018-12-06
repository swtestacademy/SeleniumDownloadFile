import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Demo {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");

        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", System.getProperty("user.dir"));

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);


        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to("https://www.thinkbroadband.com/download");

        //We find the download links
        List<WebElement> list =driver.findElements(By.cssSelector("div.module>p>a>img"));

        //Click the last one to downaload 5MB file :)
        WebElement el = list.get(list.size()-1);
        el.click();

        Thread.sleep(3000);
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

        Assert.assertTrue(found, "Downloaded document is not found");
        f.deleteOnExit();
        driver.close();

    }


}
