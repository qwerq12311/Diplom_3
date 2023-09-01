import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Epic("Diplom 3")

public class LogoutTest {
    private WebDriver chromeDriver;
    private WebDriverWait chromeWait;
    private PageObject pageObject;

    private String accessToken;

    private String emailAfterRegistration;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        chromeWait = new WebDriverWait(chromeDriver, Duration.ofSeconds(15));

        pageObject = new PageObject(chromeDriver, chromeWait);

        // Создание уникального клиента через API и получение AccessToken
        ApiClient.setup();
        String email = ApiClient.generateRandomEmail();
        String name = ApiClient.generateRandomName();
        String password = ApiClient.getUserPassword();
        Response registrationResponse = ApiClient.registerUser(email, password, name);
        accessToken = registrationResponse.getBody().jsonPath().getString("accessToken");

        // Сохранение email после регистрации
        emailAfterRegistration = email;

        System.out.println("Registered user with accessToken: " + accessToken);
    }


    @Test
    @DisplayName("Тест выхода из аккаунта")
    @Description("Проверка функциональности выхода из аккаунта")

    public void LogoutTest() {
        System.out.println("Starting the test...");

        // Переход на главную страницу
        pageObject.goToMainPage();
        System.out.println("Went to the main page.");

        // Нажатие кнопки "Войти в аккаунт"
        pageObject.loginButtonMainPageLocator();
        System.out.println("Clicked the login button.");

        // Ожидание загрузки страницы логина
        chromeWait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/login"));
        System.out.println("Login page loaded.");

        // Заполнение поля email данными из регистрации
        pageObject.fillEmail(emailAfterRegistration);

        // Заполнение поля пароля известным паролем
        pageObject.fillPassword(ApiClient.getUserPassword());
        System.out.println("Filled in email and password.");

        // Нажатие кнопки "Войти"
        pageObject.clickLoginButton();
        System.out.println("Clicked the login button.");


        // Ожидание успешного входа и перехода на главную страницу
        chromeWait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
        System.out.println("Logged in successfully.");

        // Нажатие кнопки "Личный кабинет"
        pageObject.clickPersonalAccountLink();
        System.out.println("Clicked the user profile button.");

        // Ожидание перехода на страницу личного кабинета
        chromeWait.until(ExpectedConditions.urlToBe(pageObject.getAccountPageUrl()));

        pageObject.clickLogoutButton();
        System.out.println("Clicked the Logout.");

        // Ожидание успешного входа и перехода на  страницу логина
        chromeWait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/login"));

        System.out.println("Test is finished.");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            // Удаление клиента через API по AccessToken
            ApiClient.deleteUser(accessToken);
        }

        chromeDriver.quit();
    }
}
