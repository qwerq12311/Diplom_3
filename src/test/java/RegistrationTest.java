import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Epic("Diplom 3")
@Feature("Регистрация пользователей")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class RegistrationTest {
    private WebDriver chromeDriver;
    private WebDriver yandexDriver;
    private WebDriverWait chromeWait;
    private WebDriverWait yandexWait;
    private PageObject pageObject;

    @Before
    public void setUp() {
        // Настройка ChromeOptions с общими опциями
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080", "--no-default-browser-check", "--deny-permission-prompts", "--disable-save-password-bubble");

        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.manage().window().maximize();
        chromeWait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        pageObject = new PageObject(chromeDriver, chromeWait);

        // Настройка и создание экземпляра Яндекс.Браузера
        ChromeOptions yandexOptions = new ChromeOptions();
        yandexOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080", "--no-default-browser-check", "--deny-permission-prompts", "--disable-save-password-bubble");

        yandexOptions.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
        yandexDriver = new ChromeDriver(yandexOptions);
        yandexDriver.manage().window().maximize();
        yandexWait = new WebDriverWait(yandexDriver, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Тест регистрации в Chrome")
    @Description("Проверка функциональности регистрации в браузере Chrome")
    public void testA_RegistrationInChrome() {
        runRegistrationTest(chromeDriver, chromeWait);
    }

    @Test
    @DisplayName("Тест регистрации в Яндекс.Браузере")
    @Description("Проверка функциональности регистрации в Яндекс.Браузере")
    @Issue("Y.Browser Don't start")
    public void testB_RegistrationInYandex() {
        runRegistrationTest(yandexDriver, yandexWait);
    }

    private void runRegistrationTest(WebDriver driver, WebDriverWait wait) {
        // Переход на страницу регистрации
        System.out.println("Шаг 1: Переход на страницу регистрации");
        pageObject.goToRegisterPage();

        // Заполнение полей
        String email = ApiClient.generateRandomEmail();
        String name = ApiClient.generateRandomName();
        System.out.println("Шаг 2: Заполнение полей");
        System.out.println("Заполнено имя: " + name);
        System.out.println("Заполнен email: " + email);

        pageObject.fillName(name);
        pageObject.fillEmail(email);
        String password = ApiClient.getUserPassword();
        pageObject.fillPassword(password);

        // Нажатие кнопки "Зарегистрироваться"
        System.out.println("Шаг 3: Нажатие кнопки 'Зарегистрироваться'");
        pageObject.clickRegisterButton();

        System.out.println("Шаг 4: Проверка статуса регистрации");
        pageObject.openLoginPage(); // Этот метод открывает страницу входа

        String currentUrl = driver.getCurrentUrl();
        assertEquals(pageObject.getLoginPageUrl(), currentUrl);


    }

    @After
    public void tearDown() {
        chromeDriver.quit();
        yandexDriver.quit(); // Закрытие и Яндекс.Браузера
    }
}
