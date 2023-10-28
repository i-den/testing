package com.idenchev.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Demonstration Tests")
public class DemonstrationTests {

    @Test
    @DisplayName("[⛏\uFE0F] Some Display Name")
    void someTest() {

    }

    @Test
    void someAssert() {
        var actual = 0 + 1;

        var expected = 1;
        Assertions.assertEquals(expected, actual, "Expected 0 + 1 to be 1");

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, 1, () -> "Lazily evaluated message")
        );

        Assertions.assertAll(
                "properties",
                () -> {
                    String firstName = "Jane";
                    Assertions.assertNotNull(firstName);

                    // Executed only if the previous assertion is valid.
                    Assertions.assertAll("first name",
                            () -> Assertions.assertTrue(firstName.startsWith("J")),
                            () -> Assertions.assertTrue(firstName.endsWith("e"))
                    );
                }
        );
    }

}
