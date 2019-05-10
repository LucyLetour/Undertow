package vectorField

import org.mariuszgromada.math.mxparser.Function

abstract class VectorField(open val width: Ratio = Ratio(1),
                           open val height: Ratio = Ratio(1)) {

    abstract fun getAtPoint(point: Point): Vector

    fun getFullField(precision: Double = 0.1): Array<Vector> = getPartialField(
        lowerLeftPoint = Point(-(width / 2), -(height / 2)),
        upperRightPoint = Point((width / 2), (height / 2)),
        precision = precision
    )

    fun getPartialField(lowerLeftPoint: Point, upperRightPoint: Point, precision: Double = 0.1): Array<Vector> {
        val step = Ratio(precision)
        val field: Array<Vector> = Array((
                ((upperRightPoint.x - lowerLeftPoint.x) / step + 2) *
                        ((upperRightPoint.y - lowerLeftPoint.y) / step + 2)
                ).intValue()) { Vector(0.0, 0.0) }
        var curArrayPos = 0

        for(curX in lowerLeftPoint.x..upperRightPoint.x step step) {
            for(curY in lowerLeftPoint.y..upperRightPoint.y step step) {
                field[curArrayPos] = getAtPoint(Point(curX, curY))
                curArrayPos++
            }
        }

        return field
    }
}

class VectorFieldFunc(override val width: Ratio = Ratio(1),
                  override val height: Ratio = Ratio(1),
                  var xComp: (x: Double, y: Double) -> Double,
                  var yComp: (x: Double, y: Double) -> Double): VectorField(width, height) {

    constructor(width: Double,
                height: Double,
                xComp: (x: Double, y: Double) -> Double,
                yComp: (x: Double, y: Double) -> Double):
            this(Ratio(width), Ratio(height), xComp, yComp)

    override fun getAtPoint(point: Point): Vector = Vector(
        xComp = xComp(point.x.decValue(), point.y.decValue()),
        yComp = yComp(point.x.decValue(), point.y.decValue()),
        location = point
    )
}

class VectorFieldEQ(override val width: Ratio = Ratio(1),
                    override val height: Ratio = Ratio(1),
                    var xComp: Function,
                    var yComp: Function): VectorField(width, height) {
    constructor(width: Double,
                height: Double,
                xComp: Function,
                yComp: Function):
            this(Ratio(width), Ratio(height), xComp, yComp)

    constructor(width: Ratio,
                height: Ratio,
                xComp: String,
                yComp: String):
            this(width, height, Function("P", xComp, "x", "y"), Function("P", yComp, "x", "y"))

    constructor(width: Double,
                height: Double,
                xComp: String,
                yComp: String):
            this(Ratio(width), Ratio(height), Function("P", xComp, "x", "y"), Function("P", yComp, "x", "y"))

    override fun getAtPoint(point: Point): Vector = Vector(
        xComp = xComp.calculate(point.x.decValue(), point.y.decValue()),
        yComp = yComp.calculate(point.x.decValue(), point.y.decValue()),
        location = point
    )
}

data class Point(val x: Ratio, val y: Ratio) {
    constructor(x: Double, y: Double): this(Ratio(x), Ratio(y))
}

data class Vector(val xComp: Ratio, val yComp: Ratio, val location: Point = Point(
    0.0,
    0.0)) {
    constructor(xComp: Double, yComp: Double, location: Point = Point(0.0, 0.0)):
            this(Ratio(xComp), Ratio(yComp), location)
}