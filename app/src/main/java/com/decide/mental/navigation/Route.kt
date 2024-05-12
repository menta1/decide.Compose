package com.decide.mental.navigation

interface DecideDestination {
    val route: String
}

object AssayList : DecideDestination {
    override val route: String
        get() = "ASSAY_LIST"
}

object AssayCategoriesList : DecideDestination {
    override val route: String
        get() = "ASSAY_CATEGORIES_LIST"
}

object Profile : DecideDestination {
    override val route: String
        get() = "PROFILE"
}

object Settings : DecideDestination {
    override val route: String
        get() = "SETTINGS"
}

object Auth : DecideDestination {
    override val route: String
        get() = "AUTH"
}

object Registr : DecideDestination {
    override val route: String
        get() = "REGISTR"
}

object RecoveryAccount : DecideDestination {
    override val route: String
        get() = "RECOVERY_ACCOUNT"
}

object DescriptionAssay : DecideDestination {
    override val route: String
        get() = "DESCRIPTION_ASSAY"
}

object CheckStartedAssay : DecideDestination {
    override val route: String
        get() = "CHECK_STARTED_ASSAY"
}

object AssayWithText : DecideDestination {
    override val route: String
        get() = "ASSAY_WITH_TEXT"
}

object AssayWithTime : DecideDestination {
    override val route: String
        get() = "ASSAY_WITH_TIME"
}

object AssayWithPicture : DecideDestination {
    override val route: String
        get() = "ASSAY_WITH_PICTURE"
}

object AssayWithResult : DecideDestination {
    override val route: String
        get() = "ASSAY_WITH_RESULT"
}