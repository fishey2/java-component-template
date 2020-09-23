# Abstraction with Lombok

[Lombok](https://projectlombok.org/) does a good job of abstracting boilerplate
code through the use of annotations.

One of the main principles in any Java project is to abstract common code to avoid duplication. OOP Inheritance and Polymorphism.

[Lombok](https://projectlombok.org/) provides an experimental feature, that allows the use of inheritance.

## Java Example (Fluent Builder)

To achieve a fluent builder with inheritance, it requires a fair bit of effort. Here, we give you an example of what it
would look like in Java to have a simple fluent builder, where `id` has been extracting into an abstract class.
  
The usage would look no different than any other builder.

```java
public class Application {
    public static void main(String[] args) {
        var entity = NewEntity.builder()
            .id("abc")
            .key("Some String")
            .build();

        System.out.println(entity.getId());
        System.out.println(entity.getKey());
    }
}
```
The underlying construct to have that transparency of the abstraction requires the use of generics for the
builder class.

We make sure we bind the usage such that it extends the abstract builder - this will avoid having class cast 
exceptions, and mismatch between types. This is how we can safely cast `this` in the `AbstractEntityBuilder`.

```java
public abstract class AbstractEntity {

    private final String id;

    protected <T extends AbstractEntityBuilder<?>> AbstractEntity(T builder) {
        this.id = builder.getId();
    }

    public String getId() {
        return id;
    }

    public static class AbstractEntityBuilder<U extends AbstractEntityBuilder<?>> {

        private String id;

        AbstractEntityBuilder() {
            // Empty
        }

        public U id(String id) {
            this.id = id;
            return (U) this;
        }

        public String getId() {
            return id;
        }
    }
}
```

Then we can create a standard fluent builder class, extending the `AbstractEntity`, where the `NewEntityBuilder` 
extends `AbstractEntityBuilder`. This allows the builder access to the fields in the abstract builder.

We also need to make sure that `super(builder)` is called in the private constructor to ensure the immutable
fields are filled. 

```java
public class NewEntity extends AbstractEntity {

    private final String key;

    private NewEntity(NewEntityBuilder builder) {
        super(builder);
        this.key = builder.getKey();
    }

    public String getKey() {
        return key;
    }

    public static NewEntityBuilder builder() {
        return new NewEntityBuilder();
    }

    public static class NewEntityBuilder extends AbstractEntity.AbstractEntityBuilder<NewEntityBuilder> {

        private String key;

        public NewEntityBuilder() {
            super();
        }

        public NewEntityBuilder key(String key) {
            this.key = key;
            return this;
        }

        public String getKey() {
            return key;
        }

        public NewEntity build() {
            return new NewEntity(this);
        }
    }
}
```
This is quite a lot of code to achieve inheritance, and there are many opportunities where you can easily miss/overlook
something, and it will not work as intended.

## Lombok Equivalent

```java
@Getter
@SuperBuilder
public abstract class AbstractEntity {
    private String id;
}
```

```java
@Getter
@SuperBuilder
public class AnEntity extends AbstractEntity {
    private String key;
}
```

Then the usage would remain the same, and dramatically reduces the boilerplate required.
 
```java
public class Application {
    public static void main(String[] args) {
        var entity = NewEntity.builder()
            .id("abc")
            .key("Some String")
            .build();

        System.out.println(entity.getId());
        System.out.println(entity.getKey());
    }
}
```

## toString with Inheritance



## See Also:

1. [Lombok SuperBuilder](https://projectlombok.org/features/experimental/SuperBuilder)
1. [Lombok Builder](lombok-builder.md)