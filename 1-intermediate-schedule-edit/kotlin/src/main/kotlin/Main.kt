fun main() {
    var exit = false
    do {
        print("> ")
        readLine()?.let { input ->
            when (input) {

                "help" -> {
                    println("help!")
                }

                "exit" -> {
                    exit = true
                }

                else -> {
                    println("undefined command")
                }

            }
        }
    } while (!exit)
}