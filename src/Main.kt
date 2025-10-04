// C:/YEAR3/Calculator-app/src/Main.kt
// Section C – Simple Calculator App (error-free)

fun main() = runWholeApp()

/* -----------------------------------------------------------
 * Entry-point orchestration
 * ----------------------------------------------------------- */
private fun runWholeApp() {
    println(
        """
        ╔════════════════════════════════════════════════╗
        ║     SECTION C: SIMPLE CALCULATOR APP          ║
        ╚════════════════════════════════════════════════╝
        """.trimIndent()
    )

    demonstrateCalculator()
    demonstrateEnhancedCalculator()

    // Uncomment to activate the interactive prompt
    // println("\n\nStarting Interactive Calculator…\n")
    // runInteractiveCalculator()

    println("\n=== Section C Complete ===")
}

/* -----------------------------------------------------------
 * Core arithmetic helpers
 * ----------------------------------------------------------- */
private fun add(a: Double, b: Double): Double = a + b
private fun subtract(a: Double, b: Double): Double = a - b
private fun multiply(a: Double, b: Double): Double = a * b
private fun divide(a: Double, b: Double): Double? =
    if (b == 0.0) null else a / b

/* -----------------------------------------------------------
 * Simple calculator (final – cannot be extended)
 * ----------------------------------------------------------- */
class SimpleCalculator {

    fun calculate(num1: String, num2: String, operation: String): String {
        val n1 = parseNumber(num1) ?: return "Error: '$num1' is not a valid number"
        val n2 = parseNumber(num2) ?: return "Error: '$num2' is not a valid number"

        return when (operation.lowercase()) {
            "+", "add", "addition" -> format(n1, n2, "+", add(n1, n2))
            "-", "subtract", "subtraction" -> format(n1, n2, "-", subtract(n1, n2))
            "*", "multiply", "multiplication" -> format(n1, n2, "×", multiply(n1, n2))
            "/", "divide", "division" -> {
                val res = divide(n1, n2)
                if (res == null) "Error: Division by zero is not allowed!"
                else format(n1, n2, "÷", res)
            }
            else -> "Error: Unknown operation '$operation'. Use +, -, *, or /"
        }
    }

    fun showMenu() {
        println(
            """
        ╔════════════════════════════════════════╗
        ║        SIMPLE CALCULATOR APP           ║
        ╠════════════════════════════════════════╣
        ║ Available Operations:                  ║
        ║   + or add       : Addition            ║
        ║   - or subtract  : Subtraction         ║
        ║   * or multiply  : Multiplication      ║
        ║   / or divide    : Division            ║
        ╚════════════════════════════════════════╝
            """.trimIndent()
        )
    }

    private fun parseNumber(input: String): Double? =
        input.trim().toDoubleOrNull()

    private fun format(a: Double, b: Double, op: String, res: Double) =
        "Result: $a $op $b = $res"
}

/* -----------------------------------------------------------
 * Enhanced calculator – composition-based
 * ----------------------------------------------------------- */
class EnhancedCalculator(
    private val delegate: SimpleCalculator = SimpleCalculator()
) {
    private val history = mutableListOf<String>()

    fun calculateAndSaveHistory(num1: String, num2: String, operation: String): String {
        val result = delegate.calculate(num1, num2, operation)
        if (!result.startsWith("Error")) history += result
        return result
    }

    fun showHistory() {
        if (history.isEmpty()) {
            println("No calculation history available.")
            return
        }
        println("\n╔════════════════════════════════════╗")
        println("║      CALCULATION HISTORY           ║")
        println("╠════════════════════════════════════╣")
        history.forEachIndexed { idx, line -> println("${idx + 1}. $line") }
        println("╚════════════════════════════════════╝\n")
    }

    fun clearHistory() {
        history.clear()
        println("History cleared.")
    }

    fun getHistorySize(): Int = history.size
}

/* -----------------------------------------------------------
 * Demonstrations
 * ----------------------------------------------------------- */
private fun demonstrateCalculator() {
    println("\n=== CALCULATOR DEMONSTRATION ===\n")
    val calc = SimpleCalculator()
    calc.showMenu()

    val tests = listOf(
        Triple("10", "5", "+"),
        Triple("15.5", "7.3", "-"),
        Triple("8", "6", "*"),
        Triple("20", "4", "/"),
        Triple("10", "0", "/"),          // div-zero
        Triple("abc", "5", "+"),         // bad num1
        Triple("10", "xyz", "*"),        // bad num2
        Triple("5", "3", "%"),           // bad op
        Triple("3.14", "2.5", "*"),      // decimals
        Triple("-15", "3", "/"),         // negative
        Triple("100", "25", "divide"),   // alias
        Triple("1", "3", "/"),           // small result
        Triple("999999", "888888", "+"), // large nums
        Triple("0", "5", "*"),           // zero
        Triple("  10  ", "  5  ", "+")   // whitespace
    )

    tests.forEachIndexed { idx, (a, b, op) ->
        println("Test ${idx + 1}: ${op.uppercase()}")
        println(calc.calculate(a, b, op))
        println()
    }
}

private fun demonstrateEnhancedCalculator() {
    println("\n\n=== ENHANCED CALCULATOR WITH HISTORY ===\n")
    val calc = EnhancedCalculator()

    calc.calculateAndSaveHistory("10", "5", "+")
    calc.calculateAndSaveHistory("20", "4", "/")
    calc.calculateAndSaveHistory("7", "8", "*")
    calc.calculateAndSaveHistory("100", "35", "-")

    calc.showHistory()
    println("Total calculations in history: ${calc.getHistorySize()}")
}

/* -----------------------------------------------------------
 * Interactive prompt (optional)
 * ----------------------------------------------------------- */
private fun runInteractiveCalculator() {
    val calc = SimpleCalculator()
    calc.showMenu()
    println("\nEnter 'quit' or 'exit' at any time to stop\n")

    while (true) {
        print("Enter first number: ")
        val n1 = readLine()?.trim() ?: break
        if (n1.equals("quit", true) || n1.equals("exit", true)) break

        print("Enter operation (+, -, *, /): ")
        val op = readLine()?.trim() ?: break
        if (op.equals("quit", true) || op.equals("exit", true)) break

        print("Enter second number: ")
        val n2 = readLine()?.trim() ?: break
        if (n2.equals("quit", true) || n2.equals("exit", true)) break

        println("\n${calc.calculate(n1, n2, op)}\n")
        println("-".repeat(40))
    }
    println("Thank you for using the calculator!")
}