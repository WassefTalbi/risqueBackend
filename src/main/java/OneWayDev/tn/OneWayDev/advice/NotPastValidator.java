package OneWayDev.tn.OneWayDev.advice;

import OneWayDev.tn.OneWayDev.Enum.RoleType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;


public class NotPastValidator implements ConstraintValidator<NotPast, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; // null values are handled by @NotNull
        }
        return !date.isBefore(LocalDate.now());
    }
}
