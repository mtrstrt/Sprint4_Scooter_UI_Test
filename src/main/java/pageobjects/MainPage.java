package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;

    // URL главной страницы
    private static final String URL = "https://qa-scooter.praktikum-services.ru/";

    // Кнопка принятия куки
    private By cookieButton = By.id("rcc-confirm-button");

    // Кнопка "Заказать" в шапке
    private By orderButtonHeader = By.className("Button_Button__ra12g");

    // Кнопка "Заказать" в середине сайта
    private By orderButtonMiddle = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button");

    // Раздел "Вопросы о важном"
    private By questionsSection = By.className("Home_FourPart__1uthg");

    // Список всех элементов FAQ:
    // Вопросы:
    private By question0 = By.id("accordion__heading-0");//Сколько это стоит? И как оплатить?
    // Вопросы:
    private By firstQuestion = By.id("accordion__heading-0");//Сколько это стоит? И как оплатить?
    private By secondQuestion  = By.id("accordion__heading-1");//Хочу сразу несколько самокатов! Так можно?
    private By thirdQuestion  = By.id("accordion__heading-2");//Как рассчитывается время аренды?
    private By fourthQuestion  = By.id("accordion__heading-3");//Можно ли заказать самокат прямо на сегодня?
    private By fifthQuestion  = By.id("accordion__heading-4");//Можно ли продлить заказ или вернуть самокат раньше?
    private By sixthQuestion  = By.id("accordion__heading-5");//Вы привозите зарядку вместе с самокатом?
    private By seventhQuestion  = By.id("accordion__heading-6");//Можно ли отменить заказ?
    private By eighthQuestion  = By.id("accordion__heading-7");//Я жизу за МКАДом, привезёте?

    //Ответы
    private By firstAnswer = By.id("accordion__panel-0");//Сутки — 400 рублей. Оплата курьеру — наличными или картой.
    private By secondAnswer = By.id("accordion__panel-1");//Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.
    private By thirdAnswer = By.id("accordion__panel-2");//Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.
    private By fourtAnswer = By.id("accordion__panel-3");//Только начиная с завтрашнего дня. Но скоро станем расторопнее.
    private By fifthAnswer = By.id("accordion__panel-4");//Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.
    private By sixthAnswer = By.id("accordion__panel-5");//Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.
    private By seventhAnswer = By.id("accordion__panel-6");//Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.
    private By eighthAnswer = By.id("accordion__panel-7");//Да, обязательно. Всем самокатов! И Москве, и Московской области.

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    // Открыть главную страницу
    public void open() {
        driver.get(URL);
    }

    // Принятие куки
    public void acceptCookie() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
        driver.findElement(cookieButton).click();
    }

    // Прокрутить до раздела "Вопросы о важном"
    public void scrollToQuestions() {
        WebElement element = driver.findElement(questionsSection);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    // Кликнуть на вопрос по индексу (0-7)
    public void clickQuestion(int index) {
        By questionLocator = By.id("accordion__heading-" + index);
        WebElement element = driver.findElement(questionLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(questionLocator));
        element.click();
    }

    // Получить текст ответа по индексу
    public String getAnswerText(int index) {
        By answerLocator = By.id("accordion__panel-" + index);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return driver.findElement(answerLocator).getText();
    }

    // Проверить, что ответ отображается
    public boolean isAnswerDisplayed(int index) {
        By answerLocator = By.id("accordion__panel-" + index);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
            return driver.findElement(answerLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Нажать на кнопку "Заказать" в шапке (вверху)
    public void clickOrderButtonHeader() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonHeader));
        driver.findElement(orderButtonHeader).click();
    }

    // Нажать на кнопку "Заказать" в середине страницы
    public void clickOrderButtonMiddle() {
        // Прокручиваем до кнопки
        WebElement button = driver.findElement(orderButtonMiddle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonMiddle));
        button.click();
    }

}
