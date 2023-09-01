import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
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
@Feature("Логин")

public class LoginTest {
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
    @DisplayName("Тест нажатия кнопки 'Войти в аккаунт'")
    @Description("Проверка функциональности нажатия кнопки 'Войти в аккаунт'")

    public void loginButtonMainPageTest() {
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

    }

    @Test
    @DisplayName("Тест входа через 'Личный кабинет'")
    @Description("Проверка функциональности входа через 'Личный кабинет'")

    public void loginPersonalAccountTest() {
        System.out.println("Starting the test...");

        // Переход на главную страницу
        pageObject.goToMainPage();
        System.out.println("Went to the main page.");

        // Нажатие кнопки "Личный кабинет"
        pageObject.clickPersonalAccountLink();
        System.out.println("Clicked the personal account link.");


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

    }

    @Test
    @DisplayName("Тест нажатия кнопки 'Войти' на странице регистрации")
    @Description("Проверка функциональности нажатия кнопки 'Войти' на странице регистрации")

    public void loginButtonRegisterPageTest() {
        System.out.println("Starting the test...");

        // Переход на cтраницу регистрации
        pageObject.goToRegisterPage();
        System.out.println("Went to the register page.");

        // Нажатие кнопки "Войти" на странице регистрации
        pageObject.clickLoginButtonRegisterPage();
        System.out.println("Clicked the personal account link.");


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

    }

    @Test
    @DisplayName("Тест нажатия кнопки 'Войти' на странице восстановления пароля")
    @Description("Проверка функциональности нажатия кнопки 'Войти' на странице восстановления пароля")

    public void loginButtonForgotPasswordPageTest() {
        System.out.println("Starting the test...");

        // Переход на cтраницу восстановления пароля
        pageObject.goToForgotPasswordPage();
        System.out.println("Went to the forgot password page.");

        // Нажатие кнопки "Войти" на странице восстановления пароля
        pageObject.clickLoginButtonRegisterPage();
        System.out.println("Clicked the login button .");


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
