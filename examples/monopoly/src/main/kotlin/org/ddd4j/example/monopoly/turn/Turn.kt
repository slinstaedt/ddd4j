package org.ddd4j.example.monopoly.turn

import org.ddd4j.example.monopoly.player.Player
import org.ddd4j.example.monopoly.value.Identifier
import org.ddd4j.domain.aggregate.*
import org.ddd4j.domain.EventRouter
import org.ddd4j.domain.aggregate.CommandRouter
import org.ddd4j.example.monopoly.value.PID

class Turn(val players: List<Identifier<Player>>, var current: Identifier<Player> = players[0]) {

	companion object {
		val eventRouter = EventRouter<Turn, TurnEvent>() orElse TurnEnded::apply
		val commandRouter: CommandRouter<Turn, TurnCommand> = CommandRouter()
	}

	fun end() = behavior { it.accept(TurnEnded(current, nextPlayer()), TurnEvent::apply) }

	private fun nextPlayer() = players[(players.indexOf(current) + 1) % players.size]

	fun onTurnEnded(next: PID) {
		current = next
	}
}