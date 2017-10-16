package org.ddd4j.domain.aggregate

import org.ddd4j.domain.MessageRouter
import org.ddd4j.domain.MessageRouter.Handler

sealed class Reaction<T : Any> {
	abstract fun <X : Any> map(behavior: (T) -> Behavior<X>): Behavior<X>
}

data class Accepted<T : Any>(val target: T, val events: List<Any> = emptyList()) : Reaction<T>() {
	constructor(target: T, event: Any) : this(target, listOf(event))

	override fun <X : Any> map(behavior: (T) -> Behavior<X>) = behavior(target)
}

data class Rejected<T : Any>(val reason: String) : Reaction<T>() {
	override fun <X : Any> map(behavior: (T) -> Behavior<X>) = { _: Session -> Rejected<X>(reason) }
}

data class Failed<T : Any>(val exception: Throwable) : Reaction<T>() {
	override fun <X : Any> map(behavior: (T) -> Behavior<X>) = { _: Session -> Failed<X>(exception) }
}

interface Session {
}

typealias Behavior<T> = (Session) -> Reaction<T>
fun <T : Any> T.behavior(map: (Behavior<T>) -> Behavior<T>): Behavior<T> = map({ Accepted(this) })
fun <T : Any, E : Any, X : Any> Behavior<T>.accept(event: E, handler: (T, E) -> X): Behavior<X>
		= { s: Session -> this(s).map { t -> { _: Session -> Accepted(handler(t, event), event) } }(s) }

fun <T : Any, E : Any> Behavior<T>.accept(event: E, handler: (T, E) -> Unit): Behavior<T>
		= { s: Session -> this(s).map { t -> { _: Session -> handler(t, event);Accepted(t, event) } }(s) }

fun <T : Any> Behavior<T>.guard(guard: (T) -> Boolean): Behavior<T>
		= { s: Session -> this(s).map { t -> if (guard(t)) this else { _: Session -> Rejected<T>(guard.toString()) } }(s) }
