

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.time.Duration;

public class RegistrationTest {
    private WebDriver chromeDriver;
    private WebDriverWait chromeWait;
    private PageObject pageObject;
    private ApiClient apiClient;
    private String accessToken;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
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


    }

    @Test
    public void testRegistration() {
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
        chromeWait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/login"));


        // Проверка успешного перехода на страницу логина
        assertEquals("https://stellarburgers.nomoreparties.site/login", chromeDriver.getCurrentUrl());
    }

    @Test
    public void testRegistrationWithInvalidPassword() {
        // Переход на страницу регистрации
        pageObject.goToRegisterPage();

        // Заполнение полей
        String email = apiClient.generateRandomEmail();
        String name = apiClient.generateRandomName();
        pageObject.fillName(name);
        pageObject.fillEmail(email);
        pageObject.fillPassword("12345"); // Пароль менее 6 символов

        // Нажатие кнопки "Зарегистрироваться"
        pageObject.clickRegisterButton();

        // Проверка появления ошибки некорректного пароля
        assertTrue(pageObject.isInvalidPasswordErrorDisplayed());
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            // Удаление клиента через API по AccessToken
            apiClient.deleteUser(accessToken);
        }

        chromeDriver.quit();
    }
}
