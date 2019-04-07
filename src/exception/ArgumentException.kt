package exception

class ArgumentException(
    argumentName: String
) : Exception("Wrong argument \"$argumentName\"")