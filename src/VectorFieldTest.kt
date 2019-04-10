fun main() {
    val vectorField = VectorField(width = 1.0, height = 1.0, xComp = {x, y -> x}, yComp = {x, y -> y})
    vectorField.getFullField().forEach { println(it) }

    val xComp = {x: Double, y: Double -> x - y}
    val yComp = {x: Double, y: Double -> y - x}
    val vectorField2 = VectorField(width = 10.0, height = 10.0, xComp = xComp, yComp = yComp)
    vectorField2.getFullField(1.0).forEach { println(it) }
}