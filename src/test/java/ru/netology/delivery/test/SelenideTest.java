package ru.netology.delivery.test;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import com.codeborne.selenide.*;
import ru.netology.delivery.data.DataClass;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class SelenideTest {

    @Test
    void shouldGenerateTestData() {
        DataClass.RegistrationInfo info = DataClass.DataGenerator.Registration.generateInfo("ru");
        System.out.println(info);
        String date1 = DataClass.DataGenerator.dataGenerate(5);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(info.city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date1);
        $("[data-test-id=name] input").setValue(info.name);
        $("[data-test-id=phone] input").setValue(info.phone);
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(5));
        $("[data-test-id=success-notification]").shouldHave(Condition.text("Встреча успешно запланирована на " + date1));
        $(withText("Запланировать")).click();
        $(withText("Необходимо подтверждение")).shouldBe(Condition.visible, Duration.ofSeconds(5));
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldHave(Condition.text("Встреча успешно запланирована на " + date1));
    }
}