package org.ddd4j.example.monopoly.property

import org.ddd4j.domain.aggregate.*

data class BarterRequest(val items: Map<Subject, Party>, val accepted: Set<Party> = emptySet()) {

	val allAccepted = false

	fun counterOffer(items: Map<Subject, Party>) = BarterRequest(items)
	operator fun plus(item: Pair<Subject, Party>) = counterOffer(items + item)
	operator fun minus(item: Subject) = counterOffer(items - item)

	fun accept(party: Party) = copy(accepted = accepted + party)
	fun decline(party: Party) = BarterRequest(items.filterKeys(party::equals))

	fun perform(command: BarterRequestCommand) = behavior {
		when (command) {
			is AcceptBarterRequest -> if (command.party in accepted) it else it.accept(BarterRequestAccepted(command.party), BarterRequest::on)
			is DeclineBarterRequest -> it.accept(BarterRequestDeclined(command.party), BarterRequest::on)
			else -> it
		}
	}

	fun on(event: BarterRequestEvent) = when (event) {
		is BarterRequestAccepted -> copy(accepted = accepted + event.party)
		is BarterRequestDeclined -> copy(accepted = emptySet())
		else -> this
	}
}