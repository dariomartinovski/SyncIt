package mk.ukim.finki.syncit.data.model.enums

enum class Category(val label: String) {
    FUN("Fun"),
    GAMING("Gaming"),
    PARTY("Party"),
    RETRO_HITS("Retro Hits"),
    FESTIVAL("Festival"),
    SUMMER("Summer"),
    WINTER("Winter"),
    OTHERS("Others"),
    NIGHT("Night");

    override fun toString(): String {
        return label
    }
}