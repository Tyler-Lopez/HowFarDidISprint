package com.company.howfardidisprint.model

enum class RunDistance(val distance: Int) {
    QUARTERMILE(400),
    HALFMILE(805),
    MILE(1609),
    FIVEK(5000);

    override fun toString(): String {
        return when(this) {
            QUARTERMILE -> "Quarter Mile"
            HALFMILE -> "Half Mile"
            MILE -> "Mile"
            FIVEK -> "5K"
        }
    }
}

