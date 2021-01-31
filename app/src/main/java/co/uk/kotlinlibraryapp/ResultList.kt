package co.uk.kotlinlibraryapp

data class ResultList constructor(
    var name: String? = "",
    var height: Double? = 0.0,
    var hill: String? = "",
    var reference: String? = ""


) {
    override fun toString(): String {
        return "ResultList(name=$name, height=$height, hill=$hill, reference=$reference)"
    }
}
