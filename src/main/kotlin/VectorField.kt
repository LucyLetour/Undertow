package vectorField

import org.mariuszgromada.math.mxparser.Function

abstract class VectorField(open val width: Double = 1.0,
                           open val height: Double = 1.0) {

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

class VectorFieldFunc(override val width: Double = 1.0,
                  override val height: Double = 1.0,
                  var xComp: (x: Double, y: Double) -> Double,
                  var yComp: (x: Double, y: Double) -> Double): VectorField(width, height) {

    override fun getAtPoint(point: Point): Vector = Vector(
        xComp = xComp(point.x.decValue(), point.y.decValue()),
        yComp = yComp(point.x.decValue(), point.y.decValue()),
        location = point
    )
}

class VectorFieldEQ(override val width: Double = 1.0,
                    override val height: Double = 1.0,
                    var xComp: Function,
                    var yComp: Function): VectorField(width, height) {

    override fun getAtPoint(point: Point): Vector = Vector(
        xComp = xComp.calculate(point.x.decValue(), point.y.decValue()),
        yComp = yComp.calculate(point.x.decValue(), point.y.decValue()),
        location = point
    )
}

data class Point(val x: Ratio, val y: Ratio) {
    constructor(x: Double, y: Double): this(Ratio(x), Ratio(y))
}

data class Vector(val xComp: Ratio, val yComp: Ratio, val location: Point = Point(0.0, 0.0)) {
    constructor(xComp: Double, yComp: Double, location: Point = Point(0.0, 0.0)):
            this(Ratio(xComp), Ratio(yComp), location)
}