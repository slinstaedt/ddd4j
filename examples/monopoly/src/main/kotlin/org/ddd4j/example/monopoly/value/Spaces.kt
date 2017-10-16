package org.ddd4j.example.monopoly.property

import org.ddd4j.example.monopoly.value.Money
import org.ddd4j.example.monopoly.value.PID

sealed class Space {

	open fun movedOver(player: PID) {}
	abstract fun moveEnded(player: PID)
}

sealed class Chance() : Space() {

	override fun moveEnded(player: PID) {
		TODO()
	}
}

sealed class CommunityChest() : Space() {

	override fun moveEnded(player: PID) {
		TODO()
	}
}

sealed class Property(price: Money, rent: Money) : Space() {

	override fun moveEnded(player: PID) {
		TODO()
	}
}

sealed class Tax(rate: Money) : Space() {

	override fun moveEnded(player: PID) {
		TODO()
	}
}

object go : Space() {

	override fun movedOver(player: PID) {
		TODO() //200
	}

	override fun moveEnded(player: PID) = movedOver(player)
}

object incomeTax : Tax(Money(200))
object luxuryTax : Tax(Money(100))

