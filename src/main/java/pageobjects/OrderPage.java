package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage {
    private WebDriver driver;

    // Форма "Для кого самокат":
    private By firstNameInput = By.xpath(".//input[@placeholder='* Имя']"); //Поле Имя
    private By lastNameInput = By.xpath(".//input[@placeholder='* Фамилия']"); //Поле Фамилия
    private By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']"); //Поле Адрес: куда привезти заказ
    private By metroStationInput = By.xpath(".//input[@placeholder='* Станция метро']"); //Поле Станция метро
    private By metroStationList = By.className("select-search__select");  // Выпадающий список станций метро
    private By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']"); // // Поле "Телефон"
    private By nextButton = By.xpath(".//button[text()='Далее']"); // кнопка Далее

    // Форма "Про аренду":

    private By dateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']"); //Поле Когда привезти самокат
    private By rentalPeriodDropdown = By.className("Dropdown-placeholder"); // Поле Срок аренды (выпадающий список)
    private By blackColorCheckbox = By.id("black"); // Чекбокс чёрный жемчуг
    private By greyColorCheckbox = By.id("grey"); // Чекбокс серая безысходность
    private By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");  // Поле Комментарий для курьера
    private By orderButton = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");   // Кнопка Заказать

    // Окно подтверждения заказа

    private By confirmYesButton = By.xpath(".//button[text()='Да']");   // Кнопка "Да"

    // Окно "Заказ оформлен"

    private By orderSuccessHeader = By.xpath(".//div[contains(@class, 'Order_ModalHeader') and contains(text(), 'Заказ оформлен')]"); // Заголовок формы

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // ПЕРВАЯ ФОРМА

    // Заполнение поля Имя
    public void setFirstName(String firstName) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    // Заполнение поля Фамилия
    public void setLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    // Заполнить адрес
    public void setAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    // Выбрать станцию метро
    public void selectMetroStation(String stationName) {
        // Кликаем на поле
        driver.findElement(metroStationInput).click();

        // Ждём появления списка
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(metroStationList));

        // Находим нужную станцию и кликаем
        By stationLocator = By.xpath(".//div[@class='select-search__select']//*[text()='" + stationName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(stationLocator));
        driver.findElement(stationLocator).click();
    }

    // Заполнить телефон
    public void setPhone(String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    // Нажать кнопку "Далее"
    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    // Заполнение первой формы
    public void fillFirstOrderForm(String firstName, String lastName, String address, String metroStation, String phone) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        selectMetroStation(metroStation);
        setPhone(phone);
        clickNextButton();
    }

    // ВТОРАЯ ФОРМА

    // Выбираем дату доставки
    public void setDeliveryDate(String date) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput));

        WebElement dateField = driver.findElement(dateInput);
        dateField.sendKeys(date);
        dateField.sendKeys(Keys.ENTER);
    }

    // Выбраем срок аренды
    public void selectRentalPeriod(String period) {
        // Кликаем на выпадающий список
        driver.findElement(rentalPeriodDropdown).click();

        // Находим и кликаем на нужный срок
        By periodLocator = By.xpath(".//div[@class='Dropdown-option' and text()='" + period + "']");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(periodLocator));
        driver.findElement(periodLocator).click();
    }

    // Выбраем цвет самоката
    public void selectScooterColor(String color) {
        if (color.equalsIgnoreCase("чёрный") || color.equalsIgnoreCase("black")) {
            driver.findElement(blackColorCheckbox).click();
        } else if (color.equalsIgnoreCase("серый") || color.equalsIgnoreCase("grey")) {
            driver.findElement(greyColorCheckbox).click();
        }
    }

    // Заполнияем комментарий
    public void setComment(String comment) {
        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentInput).sendKeys(comment);
        }
    }

    // Нажать кнопку "Заказать"
    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    // Подтвердить заказ нажать кнопку "Да"
    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        driver.findElement(confirmYesButton).click();
    }

    // Заполнение второй формы
    public void fillSecondOrderForm(String date, String rentalPeriod, String color, String comment) {
        setDeliveryDate(date);
        selectRentalPeriod(rentalPeriod);
        selectScooterColor(color);
        setComment(comment);
        clickOrderButton();
        confirmOrder();
    }

    // Проверить, что появилось сообщение "Заказ оформлен"
    public boolean isOrderSuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessHeader));
            return driver.findElement(orderSuccessHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Получить текст сообщения об успешном заказе
    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessHeader));
        return driver.findElement(orderSuccessHeader).getText();
    }

}
