package org.ddd4j.example.monopoly.player

import org.ddd4j.example.monopoly.property.Space
import org.ddd4j.example.monopoly.value.Identifier

sealed class PlayerEvent
data class PlayerMovedOver(val over: Space) : PlayerEvent()
data class PlayerMoveEnded(val start: Space, val end: Space) : PlayerEvent()