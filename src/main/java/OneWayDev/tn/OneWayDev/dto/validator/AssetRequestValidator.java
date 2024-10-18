package OneWayDev.tn.OneWayDev.dto.validator;

import OneWayDev.tn.OneWayDev.dto.request.AssetRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AssetRequestValidator implements ConstraintValidator<ValidAssetRequest, AssetRequest> {
    @Override
    public boolean isValid(AssetRequest assetRequest, ConstraintValidatorContext context) {
        // Check if it's a new asset request
        if (assetRequest.getIsNew()) {
            // Validate the logo field
            if (assetRequest.getLogo() == null || assetRequest.getLogo().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("actif logo is required.")
                        .addPropertyNode("logo").addConstraintViolation();
                return false; // Invalid request
            }
        }
        return true; // Valid request
    }
}
