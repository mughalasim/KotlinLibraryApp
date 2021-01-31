package co.uk.mylibrary

data class ResultList constructor(
    var name: String? = "",
    var height: Double? = 0.0,
    var hill: String? = "",
    var reference: String? = "",
    var category: String? = "" // Using the post 1997 either MUN or TOP
)
