package org.ddd4j.example.monopoly.turn

import org.ddd4j.example.monopoly.Serializable
import org.ddd4j.example.monopoly.player.Player
import org.ddd4j.example.monopoly.value.Identifier
import org.ddd4j.example.monopoly.value.PID

@Serializable sealed class TurnEvent(val handler: (Turn) -> Unit) {
	fun apply(turn: Turn): Turn {
		handler(turn)
		return turn
	}
}

@Serializable data class TurnEnded(val previous: PID, val next: PID) : TurnEvent({ it.onTurnEnded(next) })
