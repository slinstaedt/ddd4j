package org.ddd4j.example.monopoly.property

import org.ddd4j.example.monopoly.value.Identifier

sealed class BarterRequestCommand
data class CounterOfferBarterRequest(val items: Map<Subject, Party>) : BarterRequestCommand()
data class AcceptBarterRequest(val party: Party) : BarterRequestCommand()
data class DeclineBarterRequest(val party: Party) : BarterRequestCommand()