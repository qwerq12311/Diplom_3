import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String STATIC_PASSWORD = "MySecretPassword123"; // Статический пароль

    // Адреса страниц
    private String registerPageUrl = "https://stellarburgers.nomoreparties.site/register";
    private String loginPageUrl = "https://stellarburgers.nomoreparties.site/login";
    private String accountPageUrl = "https://stellarburgers.nomoreparties.site/account/profile";
    private String mainPageUrl = "https://stellarburgers.nomoreparties.site";
    private String forgotPasswordPageUrl = "https://stellarburgers.nomoreparties.site/forgot-password";

    // Локаторы
    private By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input[@name='name']");
    private By emailInput = By.xpath("//label[text()='Email']/following-sibling::input[@name='name']");
    private By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input[@name='Пароль']");
    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private By loginButton = By.cssSelector(".button_button__33qZ0");
    private By personalAccountLink = By.xpath("//a[contains(@class,'AppHeader_header__link__3D_hX') and .//p[text()='Личный Кабинет']]");
    private By constructorLink = By.xpath("//a[contains(@class,'AppHeader_header__link__3D_hX')]");
    private By logoutButton = By.xpath("//button[contains(@class,'Account_button__14Yp3')]");

    private By invalidPasswordError = By.xpath("//p[@class='input__error text_type_main-default' and text()='Некорректный пароль']");

    private By loginButtonMainPageLocator = By.xpath("//button[text()='Войти в аккаунт']");

    private By loadingImage = By.xpath("//div[contains(@class, 'Modal_modal__P3_V5')]/img[@src='./static/media/loading.89540200.svg']");

    private By loginButtonRegisterPageLocator = By.cssSelector("a.Auth_link__1fOlj[href='/login']");

    private By headerMainLogo = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']//a//*[name()='svg']");


    public PageObject(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Методы для работы с элементами
    public void fillName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    public void fillEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void fillPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void loginButtonMainPageLocator() {
        driver.findElement(loginButtonMainPageLocator).click();
    }


    public void clickPersonalAccountLink() {
        driver.findElement(personalAccountLink).click();
    }

    public void clickConstructorLink() {
        driver.findElement(constructorLink).click();
    }

    public void clickHeaderMainLogo() {
        driver.findElement(headerMainLogo).click();
    }

    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    // Методы для генерации уникальных данных
    public String generateUniqueName() {
        Faker faker = new Faker();
        return faker.name().firstName() + faker.number().randomNumber();
    }

    public String generateUniqueEmail() {
        Faker faker = new Faker();
        return "test" + faker.number().randomNumber() + "@example.com";
    }

    // Методы для перехода на страницы
    public void goToRegisterPage() {
        driver.get(registerPageUrl);
    }


    public void goToLoginPage() {
        driver.get(loginPageUrl);
    }

    public void goToAccountPage() {
        driver.get(accountPageUrl);
    }
    public String getAccountPageUrl() {
        return accountPageUrl;
    }

    public void goToMainPage() {
        driver.get(mainPageUrl);
    }

    public void goToForgotPasswordPage() {
        driver.get(forgotPasswordPageUrl);
    }

    public static String getStaticPassword() {
        return STATIC_PASSWORD;
    }

    public boolean isInvalidPasswordErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(invalidPasswordError));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isOnMainPage() {
        return driver.getCurrentUrl().equals(mainPageUrl);
    }

    public void clickLoginButtonMainPage() {
        driver.findElement(loginButtonRegisterPageLocator).click();
    }

    public void clickLoginButtonRegisterPage() {
        driver.findElement(loginButtonRegisterPageLocator).click();
    }

    public void clickLoginButtonForgotPasswordPage() {
        driver.findElement(loginButtonRegisterPageLocator).click();
    }

}
