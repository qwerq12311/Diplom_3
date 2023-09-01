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

        // Вывести результат теста в консоль
        if (isSaucesTabActive) {
            System.out.println("Тест на активный раздел 'Соусы' прошел успешно.");
        } else {
            System.out.println("Тест на активный раздел 'Соусы' не прошел.");
        }
    }

    // Метод для проверки активного раздела


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

        // Вывести результат теста в консоль
        if (isBunsTabActive) {
            System.out.println("Тест на активный раздел 'Булки' прошел успешно.");
        } else {
            System.out.println("Тест на активный раздел 'Булки' не прошел.");
        }
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

        // Вывести результат теста в консоль
        if (isFillingsTabActive) {
            System.out.println("Тест на активный раздел 'Начинки' прошел успешно.");
        } else {
            System.out.println("Тест на активный раздел 'Начинки' не прошел.");
        }
    }



    @After
    public void tearDown() {
        chromeDriver.quit();
    }
}
