import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.assertTrue;

@Epic("Diplom 3")
@Feature("Переходы по разделам конструктора")

public class MainSectionsTest {
    private WebDriver chromeDriver;
    private WebDriverWait chromeWait;
    private PageObject pageObject;
    private Actions actions;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        chromeWait = new WebDriverWait(chromeDriver, Duration.ofSeconds(15));
        pageObject = new PageObject(chromeDriver, chromeWait);

        // Открытие главной страницы
        pageObject.goToMainPage();
        System.out.println("Went to the main page.");

        // Ожидание загрузки главной страницы
        System.out.println("Main page loaded.");
        pageObject.clickConstructorLink();
        System.out.println("clickConstructorLink");

        // Инициализация объекта Actions
        actions = new Actions(chromeDriver);
    }

    @Test
    @DisplayName("Тест раздела 'Соусы'")
    @Description("Проверка функциональности раздела 'Соусы'")

    public void testSaucesTab() {


        pageObject.clickSauces();
        actions.moveToElement(chromeDriver.findElement(pageObject.getActiveTabLocator())).perform();
        assertTrue("Tab 'Sauces' is not active", pageObject.isTabActive(pageObject.getActiveTabLocator()));
        System.out.println("clickSauces");


    }

    @Test
    @DisplayName("Тест раздела 'Булки'")
    @Description("Проверка функциональности раздела 'Булки'")

    public void tesBunsTab() {

        pageObject.clickFillings();
        actions.moveToElement(chromeDriver.findElement(pageObject.getActiveTabLocator())).perform();
        assertTrue("Tab 'Fillings' is not active", pageObject.isTabActive(pageObject.getActiveTabLocator()));


        pageObject.clickBuns();
        actions.moveToElement(chromeDriver.findElement(pageObject.getActiveTabLocator())).perform();
        assertTrue("Tab 'Buns' is not active", pageObject.isTabActive(pageObject.getActiveTabLocator()));

    }

    @Test
    @DisplayName("Тест раздела 'Начинки'")
    @Description("Проверка функциональности раздела 'Начинки'")

    public void testFillingsTab() {



        pageObject.clickFillings();
        actions.moveToElement(chromeDriver.findElement(pageObject.getActiveTabLocator())).perform();
        assertTrue("Tab 'Fillings' is not active", pageObject.isTabActive(pageObject.getActiveTabLocator()));
    }

    @After
    public void tearDown() {
        chromeDriver.quit();
    }
}
