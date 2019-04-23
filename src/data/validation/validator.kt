package data.validation

import LANGUAGE_CODES
import data.request.NewPostRequest

typealias Validator<T> = T.() -> Unit

class ValidatorException(
    private val problems: List<String>
) : Exception() {
    override val message: String
        get() = "Because of:\n${problems.joinToString("\n\t• ", "\t• ")}"
}

fun NewPostRequest.validate() {
    val problems = ArrayList<String>()

    if (author != null && author.length > 64) {
        problems.add("Author length must be less then 65")
    }

    if (title.isBlank()) {
        problems.add("Title cannot be empty")
    }

    if (content.isBlank()) {
        problems.add("Content cannot be empty")
    }

    if (lang != null) {
        if (!LANGUAGE_CODES.contains(lang)) {
            problems.add("Lang must be 2-letter lang from ISO-639")
        }
    }

    if (tags != null) {
        if (tags.size > 5) {
            problems.add("You can put only 5 tags")
        }

        tags.forEach {
            if (it.length > 64) {
                problems.add("Tag length must be less then 65 ($it)")
            }
        }
    }

    if (problems.isNotEmpty()) {
        throw ValidatorException(problems)
    }
}