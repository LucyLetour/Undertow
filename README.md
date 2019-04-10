# Undertow
## A Vector Field Library
Undertow is a library aimed at implementing simple vector fields. A vector field in Undertow is created with a vector function, expressed in `x` and `y` components, as well as optional `height` and `width` parameters, for convenience.

To define the components of a vector field, we pass in function for each, using the syntax:
```kotlin
{x, y -> [Your function here]}
```
Where `x` and  `y` represent any point on a vector field and the function is the vector component at that point. Notice, in the example provided in `VectorFieldTest`:
```kotlin
val xComp = {x: Double, y: Double -> x - y}
val yComp = {x: Double, y: Double -> y - x}
val vectorField2 = VectorField(width = 10.0, height = 10.0, xComp, yComp)
```

`width` and `height` are optional fields, and I'll explain why later, but notice `xComp` and `yComp` are defined as functions that take in two `Double` values, `x` and `y`, and then apply our function. In this case, the vector field can be defined as `F = <x - y, y - x>`. The functions per component can be whatever you want, so long as the only parameters are `x` and `y`

Alternatively, we can declare our function inline the `VectorField` declaration. See:
```kotlin
val vectorField = VectorField(width = 1.0, height = 1.0, xComp = {x, y -> x}, yComp = {x, y -> y})
```
We may now omit the type declaration from the parameters(`x` and `y`) as they are inferred from the constructor of `VectorField`. In this scenario, we may say `F = <x, y>`.

To access a vector field and observe the vectors within, we may use either `getFullField` or `getPartialField`. We may even use `getAtPoint` for a single vector.

#### `getPartialField(lowerLeftPoint: Point, upperRightPoint: Point, step: Double = 0.1)`
This funtion returns the vectors within the specified rectangular region, spaced out `step` units apart. For example a `step` of 1 in a sufficiently large area would yield vectors <0, 0>, <0, 1>, <1, 0>, and <1, 1>. The vectors are returned within an array. Every vector has a x-component, y-component, and a position. The position is represented as a Point, and has a `x` and `y` field.

#### `getFullField(step: Double = 0.1)`
This is a convenience function aimed at merely returning the field of size `width` and `height` centered at 0,0 and with the specified step(default is 0.1) 

## Including Undertow in your project
If using gradle, add jitpack.io to your repositories, like so:
```groovy
repositories {
  ...
  maven { url 'https://jitpack.io' }
}
```
and then add Undertow to your dependencies, like so:
```groovy
dependencies {
  implementation 'com.github.enderL2000:undertow:v1.0.0'
}
```
and profit!

If you're not using gradle, download the binaries and add `VectorField.kt` to your project.

### Happy Coding!
