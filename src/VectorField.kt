class VectorField(val width: Double = 1.0,
                  val height: Double = 1.0,
                  var xComp: (x: Double, y: Double) -> Double,
                  var yComp: (x: Double, y: Double) -> Double) {

    fun getAtPoint(point: Point): Vector = Vector(xComp(point.x, point.y), yComp(point.x, point.y))

    fun getFullField(step: Double = 0.1): Array<Vector> = getPartialField(
        Point(-(width / 2), -(height / 2)),
        Point((width / 2), (height / 2)),
        step)

    fun getPartialField(lowerLeftPoint: Point, upperRightPoint: Point, step: Double = 0.1): Array<Vector> {
        val field: Array<Vector> = Array(
            (((upperRightPoint.x - lowerLeftPoint.x) / step).toInt() + 2) *
                (((upperRightPoint.y - lowerLeftPoint.y) / step).toInt()) + 2)
                {Vector(0.0, 0.0)}
        var curX: Double = lowerLeftPoint.x
        var curY: Double = lowerLeftPoint.y
        var curVec: Vector
        var curArrayPos = 0

        while(curX <= upperRightPoint.x) {
            while (curY <= upperRightPoint.y) {
                curVec = Vector(xComp(curX, curY), yComp(curX, curY), Point(curX, curY))
                field[curArrayPos] = curVec
                curArrayPos++
                curY += step
                curY = Math.round(curY * 10000.0) / 10000.0
            }
            curY = lowerLeftPoint.x
            curX += step
            curX = Math.round(curX * 10000.0) / 10000.0
        }

        return field
    }
}

data class Point(val x: Double, val y: Double)

data class Vector(val xComp: Double, val yComp: Double, val location: Point = Point(0.0, 0.0))