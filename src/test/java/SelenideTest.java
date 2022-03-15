import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.threeten.bp.LocalDate;
import com.codeborne.selenide.*;
import org.threeten.bp.format.DateTimeFormatter;
import java.time.Duration;
import java.util.Locale;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class SelenideTest {

    @Data
    @RequiredArgsConstructor
    public static class RegistrationInfo {
        private final String city;
        private final String date;
        private final String name;
        private final String phone;
    }

    @UtilityClass
    public class DataGenerator {

        @UtilityClass
        public class Registration {
            public static RegistrationInfo generateInfo(String locale) {
                Faker faker = new Faker(new Locale("ru"));
                return new RegistrationInfo(
                        faker.address().cityName(),
                        LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        faker.name().lastName() + " " + faker.name().firstName(),
                        faker.phoneNumber().phoneNumber()
                );
            }
        }
    }

    @Test
    void shouldGenerateTestData() {
        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");
        System.out.println(info);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(info.city);
        {
            $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $("[data-test-id=date] input").setValue(info.date);
            $("[data-test-id=name] input").setValue(info.name);
            $("[data-test-id=phone] input").setValue(info.phone);
            $("[data-test-id=agreement]").click();
            $(withText("Запланировать")).click();
            $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(5));
            $("[data-test-id=success-notification]").shouldHave(Condition.text("Встреча успешно запланирована на " + info.date));
            $(withText("Запланировать")).click();
            $(withText("Необходимо подтверждение")).shouldBe(Condition.visible, Duration.ofSeconds(5));
            $(withText("Перепланировать")).click();
            $("[data-test-id=success-notification]").shouldHave(Condition.text("Встреча успешно запланирована на " + info.date));
        }
    }
}