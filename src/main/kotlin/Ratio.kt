package vectorField

import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sign

class Ratio: Comparable<Ratio> {
    val numerator: Int
    val denominator: Int

    constructor(numerator: Int, denominator: Int) {
        this.numerator = numerator * denominator.sign
        this.denominator = denominator.absoluteValue
    }

    constructor(value: Int) {
        this.numerator = value
        this.denominator = 1
    }

    constructor(value: Double) {
        var dec = value
        var places = 0
        while(dec % 1 != 0.0) {
            dec *= 10
            places++
        }
        var ratio = Ratio(dec.toInt(), 10.0.pow(places.toDouble()).toInt())
        ratio = ratio.reduce()
        this.numerator = ratio.numerator
        this.denominator = ratio.denominator
    }

    fun decValue(): Double = numerator.toDouble() / denominator.toDouble()

    fun intValue(): Int = numerator / denominator

    //region Operators

    operator fun plus(other: Ratio): Ratio {
        val newDenom = lcm(this.denominator, other.denominator)
        val n1Num = (newDenom / this.denominator) * this.numerator
        val n2Num = (newDenom / other.denominator) * other.numerator
        return Ratio(n1Num + n2Num, newDenom)
    }

    operator fun minus(other: Ratio): Ratio {
        val newDenom = lcm(this.denominator, other.denominator)
        val n1Num = (newDenom / this.denominator) * this.numerator
        val n2Num = (newDenom / other.denominator) * other.numerator
        return Ratio(n1Num - n2Num, newDenom)
    }

    operator fun times(other: Ratio): Ratio =
        Ratio(this.numerator * other.numerator, this.denominator * other.denominator).reduce()

    operator fun div(other: Ratio): Ratio =
        times(other.reciprocal())

    operator fun plus(otherInt: Int): Ratio {
        val other = Ratio(otherInt)
        val newDenom = lcm(this.denominator, other.denominator)
        val n1Num = (newDenom / this.denominator) * this.numerator
        val n2Num = (newDenom / other.denominator) * other.numerator
        return Ratio(n1Num + n2Num, newDenom)
    }

    operator fun minus(otherInt: Int): Ratio {
        val other = Ratio(otherInt)
        val newDenom = lcm(this.denominator, other.denominator)
        val n1Num = (newDenom / this.denominator) * this.numerator
        val n2Num = (newDenom / other.denominator) * other.numerator
        return Ratio(n1Num - n2Num, newDenom)
    }

    operator fun times(otherInt: Int): Ratio =
        Ratio(this.numerator * otherInt, this.denominator).reduce()

    operator fun div(otherInt: Int): Ratio =
        Ratio(this.numerator, this.denominator * otherInt).reduce()

    operator fun plus(otherDbl: Double): Ratio {
        val other = Ratio(otherDbl)
        val newDenom = lcm(this.denominator, other.denominator)
        val n1Num = (newDenom / this.denominator) * this.numerator
        val n2Num = (newDenom / other.denominator) * other.numerator
        return Ratio(n1Num + n2Num, newDenom)
    }

    operator fun minus(otherDbl: Double): Ratio {
        val other = Ratio(otherDbl)
        val newDenom = lcm(this.denominator, other.denominator)
        val n1Num = (newDenom / this.denominator) * this.numerator
        val n2Num = (newDenom / other.denominator) * other.numerator
        return Ratio(n1Num - n2Num, newDenom)
    }

    operator fun times(otherDbl: Double): Ratio =
        Ratio(
            this.numerator * Ratio(otherDbl).numerator,
            this.denominator * Ratio(otherDbl).denominator
        ).reduce()

    operator fun div(otherDbl: Double): Ratio =
        times(Ratio(otherDbl).reciprocal())

    operator fun unaryMinus(): Ratio =
        Ratio(-numerator, denominator)

    override operator fun compareTo(other: Ratio): Int {
        return when {
            this.decValue() < other.decValue()  -> -1
            this.decValue() > other.decValue()  ->  1
            this.decValue() == other.decValue() ->  0
            else -> 0
        }
    }

    operator fun rangeTo(other: Ratio) = RatioRange(this, other)

    //endregion

    fun reduce(): Ratio {
        val gcd = gcd(numerator.absoluteValue, denominator.absoluteValue)
        return Ratio(numerator / gcd, denominator / gcd)
    }

    fun reciprocal(): Ratio = Ratio(denominator, numerator)

    override fun toString(): String {
        return "$numerator / $denominator"
    }
}

class RatioRange(
    override val start: Ratio,
    override val endInclusive: Ratio
): ClosedRange<Ratio>, Iterable<Ratio> {

    var step = Ratio(1)

    override fun iterator(): Iterator<Ratio> {
        return RatioIterator(start, endInclusive, step)
    }
}

class RatioIterator(val start: Ratio, val endInclusive: Ratio, val step: Ratio): Iterator<Ratio> {
    var initVal = start

    override fun hasNext(): Boolean {
        return initVal <= endInclusive
    }

    override fun next(): Ratio {
        initVal += step
        return initVal - step
    }
}

infix fun RatioRange.step(precision: Ratio): RatioRange {
    return this.apply { this.step = precision }
}

fun gcd(a: Int, b: Int): Int {
    var n1 = a
    var n2 = b
    var temp: Int
    while(n2 > 0) {
        temp = n2
        n2 = n1 % n2
        n1 = temp
    }
    return n1
}

fun lcm(a: Int, b: Int) = a * (b / gcd(a, b))

fun main() {
    val ratio1 = Ratio(1, 2)
    val ratio2 = Ratio(2, 3)
    val ratio3 = Ratio(2, 4)
    val ratio4 = Ratio(0.4)
    val ratio5 = Ratio(0.1)
    val ratio6 = Ratio(0.0098)
    println(ratio1 + ratio2)
    println(ratio2 + ratio1)
    println(ratio1 - ratio2)
    println(ratio2 - ratio1)
    println(ratio2.reduce())
    println(ratio3.reduce())
    println(ratio4)
    println(ratio5)
    println(ratio6)
}