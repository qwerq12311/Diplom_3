import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
    private ApiClient apiClient;
    private String accessToken;

    @Before
    public void setUp() {
        // Настройка ChromeOptions с общими опциями
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");

        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.manage().window().maximize();
        chromeWait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        pageObject = new PageObject(chromeDriver, chromeWait);
        apiClient = new ApiClient();

        // Создание уникального клиента через API и получение AccessToken
        apiClient.setup();
        String email = apiClient.generateRandomEmail();
        String name = apiClient.generateRandomName();
        String password = ApiClient.getUserPassword();
        Response registrationResponse = apiClient.registerUser(email, password, name);
        accessToken = registrationResponse.getBody().jsonPath().getString("accessToken");

        System.out.println("Registered user with accessToken: " + accessToken);

        // Настройка и создание экземпляра Яндекс.Браузера
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        ChromeOptions yandexOptions = new ChromeOptions();
        yandexOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");
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
        pageObject.goToRegisterPage();

        // Заполнение полей
        String email = apiClient.generateRandomEmail();
        String name = apiClient.generateRandomName();
        pageObject.fillName(name);
        pageObject.fillEmail(email);
        pageObject.fillPassword(ApiClient.getUserPassword());

        // Нажатие кнопки "Зарегистрироваться"
        pageObject.clickRegisterButton();

        // Ожидание загрузки страницы логина
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/login"));

        driver.navigate().refresh();

        // Проверка успешного перехода на страницу логина
        assertEquals("https://stellarburgers.nomoreparties.site/login", driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            // Удаление клиента через API по AccessToken
            apiClient.deleteUser(accessToken);
        }

        chromeDriver.quit();
        yandexDriver.quit(); // Закрытие и Яндекс.Браузера
    }
}
