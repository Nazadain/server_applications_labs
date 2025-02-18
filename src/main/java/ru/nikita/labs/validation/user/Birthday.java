package ru.nikita.labs.validation.user;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class Birthday {

    public static boolean isNull(LocalDate birthday) {
        return birthday == null;
    }

    public static boolean isEqualOrOverYears(LocalDate birthday, int years) {
        return ChronoUnit.YEARS.between(birthday, LocalDate.now()) >= years;
    }
}
