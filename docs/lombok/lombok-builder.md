It is very easy to start annotating everything with

```java
@Getter
@Setter
public class SomeEntity {

    private String id; 
}
```

Which is the equivilent to doing the following

```java
public class SomeEntity {
    
    private String id;
 
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }   
}
```

This is completely insecure and mutable and should really never be done.

If we need some immutability, this should be at the very least restricted, which you can
use Access.PACKAGE or Access.PROTECTED for.

```java
@Getter
@Setter(Access.PACKAGE)
public class SomeEntity {

    private String id;

}
```

Which would be the equivalent to changing the setter to:

```java

public class SomeEntity {

    private String id;
    
    void setId(String id) {
        this.id = id;
    }
}
```

Making it only accessible from within the same package.

Ideally, you want objects such as this to be completely immutable.

Lombok also provides a way to do this via a Fluent Builder, which would look something like:

```java
public class SomeEntity {
    
    private String id;
    
    SomeEntity(SomeEntityBuilder builder) {
        this.id = builder.getId();
    }   
    
    public String getId() {
        return id;
    }

    public class SomeEntityBuilder {
        
        private String id;

        SomeEntityBuilder() {
            // No Args Constructor    
        }       
   
        public SomeEntityBuilder id(String id) {
            this.id = id;
            return this;
        }
        
        public String getId() {
            return id;
        }
    
        public SomeEntity build() {
            return new SomeEntity(this);
        }
    }        

    public static SomeEntityBuilder builder() {
        return new SomeEntityBuilder();
    }   
}
```

With lombok, this can be simplified to just:

```java
@Builder
@Getter
public class SomeEntity {
    
    private String id;

}
```

Removing a lot of the boilerplate required, making it more readable and maintainable.

## JPA
To make it compatible with JPA, you will also need to add an @AllArgsConstructor