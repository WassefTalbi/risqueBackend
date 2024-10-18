package OneWayDev.tn.OneWayDev.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AssetRequestValidator.class)
public @interface ValidAssetRequest {
    String message() default "Invalid asset request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
