# Functions for Java 8

## Move

-   ConditionAssert to Expressions
-   FeatureSampler to Expressions
-   SamplerCondition to Expressions

## Adapt

-   Action to Consumer
-   Feature to Function

## Factory methods

-   Functions.named(name, function)
-   Functions.named(name, consumer)
-   Functions.named(name, condition)

## More Extreme Possibilities

-   Delete all function interfaces
    -   Migrate clients' code to Java 8 functions
-   Abandon SelfDescribing
    -   I like that it requires making expression classes diagnosable.
    -   But not all expressions make a diagnosis.
