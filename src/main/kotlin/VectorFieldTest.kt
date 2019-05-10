package vectorField

import org.mariuszgromada.math.mxparser.Function

fun main() {
    val vectorField = VectorFieldFunc(
        width = 1.0,
        height = 1.0,
        xComp = { x, y -> x },
        yComp = { x, y -> y }
    )
    vectorField.getFullField().forEach { println(it) }

    val xComp = {x: Double, y: Double -> x - y}
    val yComp = {x: Double, y: Double -> y - x}
    val vectorField2 = VectorFieldFunc(
        width = 10.0,
        height = 10.0,
        xComp = xComp,
        yComp = yComp
    )
    vectorField2.getFullField(precision = 1.0).forEach { println(it) }

    val vectorField3 = VectorFieldEQ(
        width = 1.0,
        height = 1.0,
        xComp = Function("P", "x", "x", "y"),
        yComp = Function("P", "y", "x", "y")
    )
    vectorField3.getFullField().forEach { println(it) }

    val xComp2 = Function("P", "x - y", "x", "y")
    val yComp2 = Function("P", "y - x", "x", "y")
    val vectorField4 = VectorFieldEQ(
        width = 10.0,
        height = 10.0,
        xComp = xComp2,
        yComp = yComp2
    )
    vectorField4.getFullField(precision = 1.0).forEach { println(it) }
}