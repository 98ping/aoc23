import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: String, name: String) = Path("src/$day/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Checks if a list is equivalent
 */

fun <T> equivalent(list1: MutableList<T>, list2: MutableList<T>) : Boolean {
    for (i in list1.indices) {
        if (list2[i] != list1[i]) {
            return false
        }
    }

    return true
}

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
