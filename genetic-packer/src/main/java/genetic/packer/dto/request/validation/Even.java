package genetic.packer.dto.request.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {Even.EvenValidator.class})
public @interface Even {

    String message() default "{genetic.packer.dto.request.validation.Even.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    boolean value() default true;

    class EvenValidator implements ConstraintValidator<Even, Integer> {

        private int remainder;

        @Override
        public void initialize(Even constraintAnnotation) {
            remainder = constraintAnnotation.value() ? 0 : 1;
        }

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            return value % 2 == remainder;
        }
    }
}
