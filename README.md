# CollectionSwitch

[![Actions Status](https://github.com/DiegoEliasCosta/collectionSwitch/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/DiegoEliasCosta/collectionSwitch/actions)

A framework for Efficient and Dynamic Collection Selection.

The current version of this framework is a proof-of-concept for our paper at [CGO'18](https://www.researchgate.net/publication/322438185_CollectionSwitch_A_Framework_for_Efficient_and_Dynamic_Collection_Selection).

**We will soon be releasing a stable release version for everyones use.** We are currentl fixing some rough edges :)

This instructions will get you to build and use CollectionsSwitch in your application.

### Prerequisites

To build and run Collections-Bench you need:

```
Java >= 8
Maven
```

### Installing

You can simply install the CollectionSwitch by running `mvn install` in the project.

## Using CollectionSwitch Context

The entry point of the CollectionSwitch adaptation is the `AllocationContext`.
To instantiate the context with a default type as JDK ArrayList, you can call the factory method.

```java
static ListAllocationContext ctx = AllocationContextFactory.buildListContext(ListCollectionType.JDK_ARRAYLIST, "myContextName");
```

We recommend you to only use the context as a `static` member of a class.

To enable the adaptive behavior, you should allow the collection instantiation to be performed by the context, as follows:

```java
List<T> list = ctx.createList();

```

## Authors

* **Diego Costa** - [] 



