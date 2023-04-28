import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ArabamAppTest {

    AndroidDriver<AndroidElement> driver;
    final String cihazAdi="PIXEL2";
    final String platformIsmi="Android";
    final String version="10.0";
    final String automation="UiAutomator2";

    @BeforeTest
    public void setup() throws MalformedURLException {

        DesiredCapabilities capabilities=new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, cihazAdi);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automation);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformIsmi);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
        capabilities.setCapability("apppackage","com.dogan.arabam");//hangi uygulama üzerinde calisacagiz
        capabilities.setCapability("appActivity","com.dogan.arabam.presentation.feature.home.HomeActivity");//uygulamada hangi sayfada baslayacagimiz yer
        capabilities.setCapability(MobileCapabilityType.NO_RESET,false);
        // eger false kullanirsak uygulama calistiktan sonra yapilacak adimlari gerceklestirir uygulamayi islem bittikten sonra SIFIRLAR
        // eger true olursa uygulama calistiktan sonra yapilacak adimlari gercceklestirir uygulamayi islem bittikten sonra SIFIRLAMAZ

        driver=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }
    @Test
    public void arabamTest() throws InterruptedException {
        //driver.findElement(By.xpath("//*[@text='İlan ver']")).click();

        driver.findElement(By.xpath("//*[@text='Arabam kaç para?']")).click();
        // Aracimin fiyatini merak ediyorum bolumunetiklayalim
        AndroidElement fiyatMerak =driver.findElement(By.xpath("//*[@text='Aracımın fiyatını merak ediyorum']"));
        fiyatMerak.click();
        // Wolkswagen markasini secelim

        TouchAction action= new TouchAction<>(driver);
        action.press(PointOption.point(537, 1779))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(540,287)).release().perform();

        Thread.sleep(3000);

        /*
        action.press(PointOption.point(540,287))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(537, 1779)).release().perform();390 1370

         */
        driver.findElementByXPath("//*[@text='Volkswagen']").click();
        driver.findElementByXPath("//*[@text='2011']").click();
        driver.findElementByXPath("//*[@text='Passat']").click();
        driver.findElementByXPath("//*[@text='Sedan']").click();
        driver.findElementByXPath("//*[@text='Benzin']").click();
        driver.findElementByXPath("//*[@text='Yarı Otomatik']").click();

        Thread.sleep(1000);

        action.press(PointOption.point(453,1738)).release().perform();
        Thread.sleep(2000);
        if (driver.isKeyboardShown()){
            driver.getKeyboard().pressKey("190000");
            Thread.sleep(2000);
        }else{
            driver.findElementById("com.dogan.arabam:id/et_km").sendKeys("150000");
            Thread.sleep(2000);
        }

        driver.findElementByXPath("//*[@text='Devam']").click();
        driver.findElementByXPath("//*[@text='Gri (metalik)']").click();
        driver.findElementById("com.dogan.arabam:id/btnNext").click();
        AndroidElement kaput = driver.findElementById("com.dogan.arabam:id/iv_B01001");
        kaput.click();
        Thread.sleep(1000);
        driver.findElementByXPath("(//*[@text='Boyalı'])[2]").click();
        Thread.sleep(2000);
        driver.findElementByXPath("//*[@text='Devam']").click();
        Thread.sleep(1000);
        driver.findElementByAccessibilityId("com.dogan.arabam:id/rbHasNoTramerEntry").click();
        Thread.sleep(1000);
        driver.findElementByAccessibilityId("com.dogan.arabam:id/btnNext").click();

        String aracFiyati= driver.findElementByAccessibilityId("com.dogan.arabam:id/tvAveragePrice").getText();
        String lastPrice= aracFiyati.replaceAll("\\D","");
        Assert.assertTrue(Integer.parseInt(lastPrice)>500000);

        driver.closeApp();

}
}
