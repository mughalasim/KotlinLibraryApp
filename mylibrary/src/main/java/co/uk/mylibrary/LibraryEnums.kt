package co.uk.mylibrary

object LibraryEnums {
    enum class Sort { ASC, DEC }
    enum class Operation { GreaterThan, LessThan }
    enum class Categories (val type: String) { MUN("MUN"), TOP("TOP") }
}