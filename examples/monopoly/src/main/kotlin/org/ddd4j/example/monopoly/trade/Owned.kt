package org.ddd4j.example.monopoly.property

import org.ddd4j.example.monopoly.value.Identifier

typealias Subject = Identifier<Owned<*>>

class Owned<S>(val subject: S, val owner: Identifier<Owner>) {
	
	fun requestExchange(with: Identifier<Owner>) {
	}
}
