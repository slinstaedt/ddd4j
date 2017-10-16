package org.ddd4j.domain

class EventRouter<T : Any, in E : Any>(val handlers: List<Handler<T, E>> = emptyList()) {

	companion object {
		inline infix fun <T : Any, E : Any, reified X : E> of(crossinline handler: (T, X) -> T) = EventRouter<T, E>() or handler
		inline infix fun <T : Any, E : Any, reified X : E> of(crossinline handler: (T, X) -> Unit) = EventRouter<T, E>() or handler
	}

	data class Handler<T : Any, in M : Any>(val canHandle: (Any) -> Boolean, val handle: (T, M) -> T)

	fun handle(target: T, event: E) = handlers.find { it.canHandle(event) }?.let { it.handle(target, event) }

	inline infix fun <reified X : E> or(crossinline handler: (T, X) -> T)
			= EventRouter(handlers + Handler({ it is X }, { t, m -> handler(t, m as X) }))

	inline infix fun <reified X : E> or(crossinline handler: (T, X) -> Unit): EventRouter<T, E> {
		val objectHandler: (T, X) -> T = { t: T, x: X -> handler(t, x);t }
		return or(objectHandler)
	}

	inline infix fun <reified X : E> orElse(crossinline handler: (X, T) -> T)
			= EventRouter(handlers + Handler({ it is X }, { t, m -> handler(m as X, t) }))
}
