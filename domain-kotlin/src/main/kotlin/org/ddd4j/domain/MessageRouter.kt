package org.ddd4j.domain

abstract class MessageRouter<S, T : Any, M : Any>(val handlers: List<Handler<T, M>> = emptyList()) {

	data class Handler<T : Any, in M : Any>(val canHandle: (Any) -> Boolean, val handle: (T, M) -> T)

	protected abstract val constructor: (List<Handler<T, M>>) -> S

	fun handle(target: T, message: M) = handlers.find { it.canHandle(message) }?.let { it.handle(target, message) }

	inline infix fun <reified X : M> orElse(noinline handler: (T, X) -> T): S
			= constructor(handlers + Handler({ it is X }, { t, m -> handler(t, m as X) }))
}
