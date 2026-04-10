package org.example.smartstock;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.example.smartstock.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductValidationTest {
    private ValidatorFactory validatorFactory;
    private Validator validator;

    @BeforeEach
    void setUp() {
        validatorFactory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterEach
    void tearDown() {
        if (validatorFactory != null) {
            validatorFactory.close();
        }
    }

    @Test
    void shouldHaveNoViolationsForValidProduct() {
        Product product = new Product("LAP-001", "Laptop", 10);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldReportViolationsForInvalidProductState() {
        Product product = new Product("LAP-001", "Laptop", 0);

        Set<ConstraintViolation<Product>> violations = validator.validateValue(Product.class, "sku", "");
        violations.addAll(validator.validateValue(Product.class, "name", ""));
        violations.addAll(validator.validateValue(Product.class, "quantity", -1));

        assertEquals(3, violations.size());
    }
}
