package org.ddd4j.example.monopoly.value

import org.ddd4j.example.monopoly.player.Player
import java.util.UUID

data class Identifier<T>(val value: UUID) {

	companion object {

		fun <T> create() = Identifier<T>(UUID.randomUUID())
	}
}

typealias PID = Identifier<Player>