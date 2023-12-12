package ltd.matrixstudios.helpers

import org.apache.commons.lang3.function.TriFunction

@JvmRecord
data class Tuple<A, B, C>(val a: A, val b: B, val c: C)
{
    fun <D, E, F> map(func: TriFunction<A, B, C, Tuple<D, E, F>>): Tuple<D, E, F>
    {
        return func.apply(a, b, c)
    }

    companion object
    {
        fun <A, B, C> of(a: A, b: B, c: C): Tuple<A, B, C>
        {
            return Tuple(a, b, c)
        }
    }
}