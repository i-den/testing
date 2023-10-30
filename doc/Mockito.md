### Summary

Mockito concepts and usage cheatsheet

---

Basic stubbing
```java
@Test
void someMockitoTest() {
    LinkedList mockedList = mock(LinkedList.class);

    //stubbing
    when(mockedList.get(0)).thenReturn("first");
    when(mockedList.get(1)).thenThrow(new RuntimeException());

    // assert
    Assertions.assertEquals("first", mockedList.get(0));
    Assertions.assertThrows(RuntimeException.class, () -> mockedList.get(1));

    // verify methods are called with arguments
    verify(mockedList).get(0);
    verify(mockedList).get(1);
}
```

With argument matchers
```java
//stubbing using built-in anyInt() argument matcher
when(mockedList.get(anyInt())).thenReturn("element");

//stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
when(mockedList.contains(argThat(isValid()))).thenReturn(true);

//you can also verify using an argument matcher
verify(mockedList).get(anyInt());

//argument matchers can also be written as Java 8 Lambdas
verify(mockedList).add(argThat(someString -> someString.length() > 5));
```

If using an argument matcher, all arguments must be provided with argument matchers:
```java
verify(mock).someMethod(anyInt(), anyString(), eq("third argument"));
//above is correct - eq() is also an argument matcher

verify(mock).someMethod(anyInt(), anyString(), "third argument"); // missing eq()
//above is incorrect - exception will be thrown because third argument is given without an argument matcher.
```

Verify number of invocations
```java
mockedList.add("twice");
mockedList.add("twice");

verify(mockedList, times(2)).add("twice");

verify(mockedList, atMostOnce()).add("once");
verify(mockedList, atLeastOnce()).add("three times");

//verify that method was never called on a mock
verify(mockOne, never()).add("four");

// don't overuse
verifyNoMoreInteractions(mockedList);
```

Consecutive stubbing
```java
when(mock.someMethod("some arg"))
    .thenThrow(new RuntimeException())
    .thenReturn("foo");

//First call: throws runtime exception:
mock.someMethod("some arg");

//Second call: prints "foo"
System.out.println(mock.someMethod("some arg"));

//Any consecutive call: prints "foo" as well (last stubbing wins).
System.out.println(mock.someMethod("some arg"));
```

Stubbing void methods

Use when:
- stub void methods
- stub methods on spy objects
- stub the same method more than once, to change the behaviour of a mock in the middle of a test

```java
doThrow(new RuntimeException()).when(mockedList).clear();
// following throws RuntimeException:
mockedList.clear();
```

Timeouts:
```java
//passes when someMethod() is called no later than within 100 ms
//exits immediately when verification is satisfied (e.g. may not wait full 100 ms)
verify(mock, timeout(100)).someMethod();
//above is an alias to:
verify(mock, timeout(100).times(1)).someMethod();

//passes as soon as someMethod() has been called 2 times under 100 ms
verify(mock, timeout(100).times(2)).someMethod();

//equivalent: this also passes as soon as someMethod() has been called 2 times under 100 ms
verify(mock, timeout(100).atLeast(2)).someMethod();
```

Adding messages:
```java
// will print a custom message on verification failure
verify(mock, description("This will print on failure")).someMethod();

// will work with any verification mode
verify(mock, times(2).description("someMethod should be called twice")).someMethod();
```

Lambdas:
```java
verify(list, times(2)).add(argThat(string -> string.length() < 5));
verify(target, times(1)).receiveComplexObject(argThat(obj -> obj.getSubObject().get(0).equals("expected")));
when(mock.someMethod(argThat(list -> list.size()<3))).thenReturn(null);
```

Verification with Assertions:
```java
verify(serviceMock).doStuff(assertArg(param -> {
    assertThat(param.getField1()).isEqualTo("foo");
    assertThat(param.getField2()).isEqualTo("bar");
}));
```
