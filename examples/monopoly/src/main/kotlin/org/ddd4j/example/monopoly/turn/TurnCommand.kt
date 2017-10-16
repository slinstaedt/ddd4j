package org.ddd4j.example.monopoly.turn

import org.ddd4j.example.monopoly.Serializable
import org.ddd4j.example.monopoly.player.Player
import org.ddd4j.example.monopoly.value.Identifier
import org.ddd4j.example.monopoly.value.PID
import org.ddd4j.domain.aggregate.Behavior

@Serializable sealed class TurnCommand(val handler: (Turn) -> Behavior<Turn>)
@Serializable object endTurn : TurnCommand({ it.end() })
