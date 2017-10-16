package org.ddd4j.example.monopoly.property

import org.ddd4j.example.monopoly.value.Identifier

sealed class BarterRequestEvent
data class BarterRequestCancelled(val void: Any? = null) : BarterRequestEvent()
data class BarterRequestCompleted(val items: Map<Subject, Party>) : BarterRequestEvent()
data class BarterRequestCounterOffered(val items: Map<Subject, Party>) : BarterRequestEvent()
data class BarterRequestAccepted(val party: Party) : BarterRequestEvent()
data class BarterRequestDeclined(val party: Party) : BarterRequestEvent()
