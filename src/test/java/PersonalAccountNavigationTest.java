import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountNavigationTest {
    private WebDriver chromeDriver;
    private WebDriverWait chromeWait;
    private PageObject pageObject;
    private ApiClient apiClient;
    private String accessToken;

    private String emailAfterRegistration;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        chromeWait = new WebDriverWait(chromeDriver, Duration.ofSeconds(15));
        pageObject = new PageObject(chromeDriver, chromeWait);
        apiClient = new ApiClient();

        // Создание уникального клиента через API и получение AccessToken
        apiClient.setup();
        String email = apiClient.generateRandomEmail();
        String name = apiClient.generateRandomName();
        String password = ApiClient.getUserPassword();
        Response registrationResponse = apiClient.registerUser(email, password, name);
        accessToken = registrationResponse.getBody().jsonPath().getString("accessToken");

        // Сохранение email после регистрации
        emailAfterRegistration = email;

        System.out.println("Registered user with accessToken: " + accessToken);
    }

    @Test
    public void personalAccountNavigationTest() {
        System.out.println("Starting the test...");

        // Переход на главную страницу
        pageObject.goToMainPage();
        System.out.println("Went to the main page.");

        // Нажатие кнопки "Войти в аккаунт"
        pageObject.clickLoginButtonMainPage();
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

        //chromeWait.until(ExpectedConditions.invisibilityOfElementLocated(pageObject.getLoadingImageLocator()));
        //System.out.println("Loading finished.");

        // Ожидание успешного входа и перехода на главную страницу
        chromeWait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
        System.out.println("Logged in successfully.");

        // Нажатие кнопки "Личный кабинет"
        pageObject.clickPersonalAccountLink();
        System.out.println("Clicked the user profile button.");

        // Ожидание перехода на страницу личного кабинета
        chromeWait.until(ExpectedConditions.urlToBe(pageObject.getAccountPageUrl()));



        System.out.println("User profile page loaded.");
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
