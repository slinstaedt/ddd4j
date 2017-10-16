package org.ddd4j.domain.aggregate

class CommandRouter<T : Any, in C : Any>(val handlers: List<Handler<T, C>> = emptyList()) {

	companion object {
		inline infix fun <T : Any, C : Any, reified X : C> of(crossinline handler: (T, X) -> Behavior<T>) = CommandRouter<T, C>() orElse handler
	}

	data class Handler<T : Any, in M>(val canHandle: (Any) -> Boolean, val handle: (T, M) -> Behavior<T>)

	fun handle(target: T, message: C) = handlers.find { it.canHandle(message) }?.let { it.handle(target, message) }

	inline infix fun <reified X : C> orElse(crossinline handler: (T, X) -> Behavior<T>)
			= CommandRouter(handlers + Handler({ it is X }, { t, m -> handler(t, m as X) }))

	inline infix fun <reified X : C> eventless(crossinline handler: (T, X) -> T)
			= orElse<X> { t, x -> handler(t, x).behavior { it } }
}
