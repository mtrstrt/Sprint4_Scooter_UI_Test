import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobjects.MainPage;
import pageobjects.OrderPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;
    private String buttonType;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String date;
    private String rentalPeriod;
    private String color;
    private String comment;

    public OrderTest(String buttonType, String firstName, String lastName, String address, String metroStation, String phone, String date, String rentalPeriod, String color, String comment) {
        this.buttonType = buttonType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Заказ через кнопку {0}: {1} {2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"header", "Иван", "Иванов", "Москва, ул. Ленина, 10", "Сокольники", "+79991234567", "25.12.2024", "сутки", "чёрный", "Позвоните за час"}, // Заказ через верхнюю кнопку "Заказать"
                {"header", "Алексей", "Сидоров", "Москва, Тверская, 5", "Лубянка", "+79161112233", "01.01.2025", "трое суток", "чёрный", ""}, // Заказ через верхнюю кнопку "Заказать" второй набор данных
                {"middle", "Мария", "Петрова", "Москва, Проспект Мира, 100", "Черкизовская", "+79857654321", "30.12.2024", "двое суток", "серый", "Оставить у двери"}, // Заказ через кнопку "Заказать" в середине
                {"middle", "Елена", "Смирнова", "Москва, Кутузовский проспект, 7", "Красногвардейская", "+79250009988", "15.01.2025", "четверо суток", "серый", "Быстрая доставка"} // Заказ через кнопку "Заказать" в середине второй набор данных
        };
    }

    @Before
    public void setup() {
        String browser = System.getProperty("browser", "chrome");

        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().driverVersion("0.33.0").setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
    }

    @Test
    public void testCreateOrder() {
        // Шаг 1: Открыть главную страницу
        mainPage.open();

        // Шаг 2: Принять куки
        mainPage.acceptCookie();

        // Шаг 3: Нажать на кнопку "Заказать"
        if ("header".equals(buttonType)) {
            mainPage.clickOrderButtonHeader();
        } else {
            mainPage.clickOrderButtonMiddle();
        }

        // Шаг 4: Заполнить первую форму заказа
        orderPage.fillFirstOrderForm(firstName, lastName, address, metroStation, phone);

        // Шаг 5: Заполнить вторую форму заказа
        orderPage.fillSecondOrderForm(date, rentalPeriod, color, comment);

        // Шаг 6: Проверить, что заказ оформлен
        assertTrue("Сообщение об успешном оформлении заказа не появилось",
                orderPage.isOrderSuccessful());

        // Вывод в консоль
        String message = orderPage.getSuccessMessage();
        System.out.println("✓ Заказ успешно оформлен через кнопку " + buttonType + ": " + message);
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
