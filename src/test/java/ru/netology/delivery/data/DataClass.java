package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import java.util.Locale;

 @UtilityClass
 public class DataClass {

     public class Registration {
         public static RegistrationInfo generateInfo(String locale) {
             Faker faker = new Faker(new Locale(locale));
             return new RegistrationInfo(
                     faker.address().cityName(),
                     faker.name().lastName() + " " + faker.name().firstName(),
                     faker.phoneNumber().phoneNumber()
             );
         }
     }

    public String dataGenerate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

     @Value
     public static class RegistrationInfo {
         public final String city;
         public final String name;
         public final String phone;
     }

 }

