package com.decide.app.navigation


//fun scaleIntoContainer(
//    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
//    initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.9f else 1.1f
//): EnterTransition {
//    return scaleIn(
//        animationSpec = tween(220, delayMillis = 90),
//        initialScale = initialScale
//    ) + fadeIn(animationSpec = tween(220, delayMillis = 90))
//}
//
//fun scaleOutOfContainer(
//    direction: ScaleTransitionDirection = ScaleTransitionDirection.OUTWARDS,
//    targetScale: Float = if (direction == ScaleTransitionDirection.INWARDS) 0.9f else 1.1f
//): ExitTransition {
//    return scaleOut(
//        animationSpec = tween(
//            durationMillis = 220,
//            delayMillis = 90
//        ), targetScale = targetScale
//    ) + fadeOut(tween(delayMillis = 90))
//}
//
//        enterTransition = {
//            when (initialState.destination.route) {
//                "details" ->
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        animationSpec = tween(700)
//                    )
//
//                else -> null
//            }
//        },
//        exitTransition = {
//            when (targetState.destination.route) {
//                "details" ->
//                    slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        animationSpec = tween(700)
//                    )
//
//                else -> null
//            }
//        },
//        popEnterTransition = {
//            when (initialState.destination.route) {
//                "details" ->
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Right,
//                        animationSpec = tween(700)
//                    )
//
//                else -> null
//            }
//        },
//        popExitTransition = {
//            when (targetState.destination.route) {
//                "details" ->
//                    slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Right,
//                        animationSpec = tween(700)
//                    )
//
//                else -> null
//            }
//        }
