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

import org.junit.Assert;


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

    public void selectSaucesSectionTest() {
        // Кликнуть на кнопку конструктора
        pageObject.clickConstructorLink();

        // Кликнуть на раздел "Соусы"
        pageObject.clickSauces();

        // Проверить активность раздела "Соусы"
        boolean isSaucesTabActive = pageObject.checkActiveBuilderSection("Соусы");

        //  Assert для проверки
        Assert.assertTrue("Тест на активный раздел 'Соусы' не прошел.", isSaucesTabActive);

        //  Результат теста в консоль
        System.out.println("Результат теста на активный раздел 'Соусы': " + (isSaucesTabActive ? "Прошел успешно" : "Не прошел"));
    }




    @Test
    @DisplayName("Тест раздела 'Булки'")
    @Description("Проверка функциональности раздела 'Булки'")

    public void selectBunSectionTest() {
        // Кликнуть на кнопку конструктора
        pageObject.clickConstructorLink();

        pageObject.clickSauces();

        // Кликнуть на раздел "Булки"
        pageObject.clickBuns();

        // Проверить активность раздела "Булки"
        boolean isBunsTabActive = pageObject.checkActiveBuilderSection("Булки");

        //  Assert для проверки
        Assert.assertTrue("Тест на активный раздел 'Булки' не прошел.", isBunsTabActive);

        //  Результат теста в консоль
        System.out.println("Результат теста на активный раздел 'Булки': " + (isBunsTabActive ? "Прошел успешно" : "Не прошел"));
    }


    @Test
    @DisplayName("Тест раздела 'Начинки'")
    @Description("Проверка функциональности раздела 'Начинки'")

    public void selectFillingsSectionTest() {
        // Кликнуть на кнопку конструктора
        pageObject.clickConstructorLink();

        // Кликнуть на раздел "Начинки"
        pageObject.clickFillings();

        // Проверить активность раздела "Начинки"
        boolean isFillingsTabActive = pageObject.checkActiveBuilderSection("Начинки");

        //  Assert для проверки
        Assert.assertTrue("Тест на активный раздел 'Начинки' не прошел.", isFillingsTabActive);

        //  Результат теста в консоль
        System.out.println("Результат теста на активный раздел 'Начинки': " + (isFillingsTabActive ? "Прошел успешно" : "Не прошел"));
    }



    @After
    public void tearDown() {
        chromeDriver.quit();
    }
}
