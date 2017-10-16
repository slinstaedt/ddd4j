package org.ddd4j.example.monopoly.value

data class Money(val value: Int) : Comparable<Money> {

	override fun compareTo(other: Money) = this.value - other.value
	operator fun plus(other: Money) = Money(this.value + other.value)
	operator fun minus(other: Money) = if (this > other) Money(this.value - other.value) else throw IllegalArgumentException()
}
