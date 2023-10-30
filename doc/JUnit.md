### Summary

JUnit concepts and usage cheatsheet

--- 

### JUnit 5

#### Annotations

Main

|      Annotation      |              Description              |
|:--------------------:|:-------------------------------------:|
|       `@Test`        |                 Basic                 |
| `@ParameterizedTest` |             Parameterized             |
|   `@RepeatedTest`    |               Repeated                |
|    `@DisplayName`    | Description for running tests locally |
|     `@Disabled`      |            Disable a test             |
|      `@Timeout`      |          Timeout for a test           |
|      `@TempDir`      |          Temporary directory          |


Lifecycle

|      Annotation      |              Description              |
|:--------------------:|:-------------------------------------:|
|     `@BeforeAll`     |          Run once before all          |
|     `@AfterAll`      |          Run once after all           |
|    `@BeforeEach`     |         Run before each test          |
|     `@AfterEach`     |          Run after each test          |

Less used

| Annotation |          Description           |
|:----------:|:------------------------------:|
|   `@Tag`   | Tags / used for enable/disable |

---

#### Display Names

```java
@DisplayName("Demonstration Tests")
public class DemonstrationTests {
    
    @Test
    @DisplayName("[⛏\uFE0F] Some Display Name")
    void someTest() { }
    
}
```

#### Assertions

Basic example
```java
@Test
void someAssert() {
    var actual = 0 + 1;
    
    var expected = 1;
    Assertions.assertEquals(expected, actual, "Expected 0 + 1 to be 1");
}
```

Assert All:
```java
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
```

Exception:
```java 
@Test
void exceptionTesting() {
    Exception exception = assertThrows(
            ArithmeticException.class, () -> calculator.divide(1, 0)
    );
    assertEquals("/ by zero", exception.getMessage());
}
```

#### Assumptions

Assumtpions are used to skip tests based on some condition.

```java
@Test
void testOnlyOnCiServer() {
    assumeTrue("CI".equals(System.getenv("ENV")));
    // remainder of test
}
```

#### Repeated Tests

```java
@BeforeEach
void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
    int currentRepetition = repetitionInfo.getCurrentRepetition();
    int totalRepetitions = repetitionInfo.getTotalRepetitions();
    String methodName = testInfo.getTestMethod().get().getName();
    logger.info(String.format("About to execute repetition %d of %d for %s", //
        currentRepetition, totalRepetitions, methodName));
}

@RepeatedTest(10)
void repeatedTest() {
    // ...
}
```

#### Parameterized Tests

```java
@ParameterizedTest
@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
void palindromes(String candidate) {
    assertTrue(StringUtils.isPalindrome(candidate));
}
```

```java
@ParameterizedTest
@MethodSource("stringIntAndListProvider")
void testWithMultiArgMethodSource(String str, int num, List<String> list) {
    assertEquals(5, str.length());
    assertTrue(num >=1 && num <=2);
    assertEquals(2, list.size());
}

static Stream<Arguments> stringIntAndListProvider() {
    return Stream.of(
        arguments("apple", 1, Arrays.asList("a", "b")),
        arguments("lemon", 2, Arrays.asList("x", "y"))
    );
}
```
