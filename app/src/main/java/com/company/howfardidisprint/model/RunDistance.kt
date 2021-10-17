package com.company.howfardidisprint.model

enum class RunDistance(val distance: Long) {
    TESTDISTANCE(50L),
    QUARTERMILE(400L),
    HALFMILE(805L),
    MILE(1609L),
    TWOMILE(3219L),
    FIVEK(5000L);

    override fun toString(): String {
        return when(this) {
            TESTDISTANCE -> "50 Meters"
            QUARTERMILE -> "Quarter Mile"
            HALFMILE -> "Half Mile"
            MILE -> "Mile"
            TWOMILE -> "Two Mile"
            FIVEK -> "5K"
        }
    }
}

