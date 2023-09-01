
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject {

    private WebDriver driver;
    private WebDriverWait wait;


    // Адреса страниц
    private String registerPageUrl = "https://stellarburgers.nomoreparties.site/register";
    private String loginPageUrl = "https://stellarburgers.nomoreparties.site/login";
    private String accountPageUrl = "https://stellarburgers.nomoreparties.site/account/profile";
    private String mainPageUrl = "https://stellarburgers.nomoreparties.site";
    private String forgotPasswordPageUrl = "https://stellarburgers.nomoreparties.site/forgot-password";

    // Локаторы элементов
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

    // Локаторы конструктора
    private By saucesLocator = By.xpath("//span[@class='text text_type_main-default' and text()='Соусы']");
    private By fillingsLocator = By.xpath("//span[@class='text text_type_main-default' and text()='Начинки']");
    private By bunsLocator = By.xpath("//span[@class='text text_type_main-default' and text()='Булки']");

    private By activeTabLocator = By.cssSelector(".tab_tab_type_current__2BEPc");


    // Web driver
    public PageObject(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Методы для взаимодействия с элементами
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

    public void clickLoginButtonMainPage() {
        driver.findElement(loginButtonMainPageLocator).click();
    }

    public void clickLoginButtonRegisterPage() {
        driver.findElement(loginButtonRegisterPageLocator).click();
    }




    // Методы для перехода на страницы
    public void goToRegisterPage() {
        driver.get(registerPageUrl);
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



    // Методы для взаимодействия с элементами конструктора

    public void clickFillings() {
        driver.findElement(fillingsLocator).click();
    }

    public void clickSauces() {
        driver.findElement(saucesLocator).click();
    }

    public void clickBuns() {
        driver.findElement(bunsLocator).click();
    }







    // Методы для проверки активной вкладки

    public By getActiveTabLocator() {
        return activeTabLocator;
    }

    public boolean checkActiveBuilderSection(String section) {
        // Получение актуального название активного раздела
        String actual = driver.findElement(getActiveTabLocator()).getText();
        String activeTabLocator = getActiveTabLocator().toString(); // Получаем строковое представление локатора

        // Вывод результатов проверки в консоль
        System.out.println("Expected Section: " + section);
        System.out.println("Actual Section: " + actual);
        System.out.println("Active Tab Locator: " + activeTabLocator);

        // Проверка, что актуальное название соответствует ожидаемому
        boolean isSectionActive = section.equals(actual);

        return isSectionActive;
    }
}



