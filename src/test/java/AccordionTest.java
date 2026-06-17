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

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class AccordionTest {
    private WebDriver driver;
    private MainPage mainPage;

    private int questionIndex;
    private String expectedAnswerPart;

    public AccordionTest (int questionIndex, String expectedAnswerPart){
        this.questionIndex = questionIndex;
        this.expectedAnswerPart = expectedAnswerPart;
    }

    // Параметризация: индекс вопроса и часть ожидаемого ответа для проверки
    @Parameterized.Parameters(name = "Тест вопроса #{0}")
    public static Object[][] getTestData() {
        return new Object[][] {
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
    }
    @Before
    public void setup(){
// По умолчанию Chrome
        String browser = System.getProperty("browser", "chrome");

        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().driverVersion("0.33.0").setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        mainPage = new MainPage(driver);
    }


    @Test
    public void testAccordionItems() {
        // Открыть главную страницу
        mainPage.open();
        // Принятие куки
        mainPage.acceptCookie();
        // Прокрутить до раздела "Вопросы о важном"
        mainPage.scrollToQuestions();
        // Кликнуть на вопрос
        mainPage.clickQuestion(questionIndex);
        // Проверить, что ответ отображается
        assertTrue("Ответ на вопрос " + questionIndex + " не отображается",
                mainPage.isAnswerDisplayed(questionIndex));
        // Проверить, что текст ответа содержит ожидаемую часть
        String actualAnswer = mainPage.getAnswerText(questionIndex);
        assertTrue("Ответ не содержит ожидаемый текст. Ожидалось: '" + expectedAnswerPart +
                        "', получено: '" + actualAnswer + "'",
                actualAnswer.contains(expectedAnswerPart));

        System.out.println("✓ Вопрос " + questionIndex + " проверен успешно");
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
