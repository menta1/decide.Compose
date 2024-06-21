package com.decide.app.navigation

interface DecideDestination {
    val route: String
}

object Assay : DecideDestination {
    override val route: String
        get() = "ASSAY_LIST"
}

object Category : DecideDestination {
    override val route: String
        get() = "ASSAY_CATEGORIES_LIST"
}

object Passed : DecideDestination {
    override val route: String
        get() = "PASSED"
}

object Profile : DecideDestination {
    override val route: String
        get() = "PROFILE"
}

//Assay Branch
object AssayRouteBranch : DecideDestination {
    override val route: String
        get() = "ASSAY_ROUTE_BRANCH"
}

object AssayDescription : DecideDestination {
    override val route: String
        get() = "DESCRIPTION_ASSAY"
}

object AssayCheckStarted : DecideDestination {
    override val route: String
        get() = "CHECK_STARTED_ASSAY"
}

object AssayProcess : DecideDestination {
    override val route: String
        get() = "ASSAY_PROCESS"
}

object AssayWithResult : DecideDestination {
    override val route: String
        get() = "ASSAY_WITH_RESULT"
}

object categoriesSpecific: DecideDestination{
    override val route: String
        get() = "CATEGORIES_SPECIFIC"

}

//Passed Branch
object PassedRouteBranch : DecideDestination {
    override val route: String
        get() = "PASSED_ROUTE_BRANCH"

}

//Profile Branch
object ProfileRouteBranch : DecideDestination {
    override val route: String
        get() = "PROFILE_ROUTE_BRANCH"
}

object Settings : DecideDestination {
    override val route: String
        get() = "SETTINGS"
}

object EditProfile: DecideDestination{
    override val route: String
        get() = "EDIT_PROFILE"
}

object Notification: DecideDestination{
    override val route: String
        get() = "NOTIFICATION"

}

object Terms: DecideDestination{
    override val route: String
        get() = "TERMS"

}

object AuthenticationRouteBranch: DecideDestination{
    override val route: String
        get() = "AUTHENTICATION_ROUTE_BRANCH"
}

object Authentication : DecideDestination {
    override val route: String
        get() = "AUTH"
}

object Registration : DecideDestination {
    override val route: String
        get() = "REGISTR"
}

object RecoveryAccount : DecideDestination {
    override val route: String
        get() = "RECOVERY_ACCOUNT"
}