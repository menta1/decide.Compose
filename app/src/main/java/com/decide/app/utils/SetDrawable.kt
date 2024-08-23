package com.decide.app.utils

import com.decide.uikit.R


fun setDrawable(idCategory: Int): Int {
    return when (idCategory) {
        1 -> {
            R.drawable.category1
        }

        2 -> {
            R.drawable.category2
        }

        3 -> {
            R.drawable.category3
        }

        4 -> {
            R.drawable.category4
        }

        5 -> {
            R.drawable.category5
        }

        6 -> {
            R.drawable.category6
        }

        7 -> {
            R.drawable.category7
        }

        8 -> {
            R.drawable.category8
        }

        9 -> {
            R.drawable.category9
        }

        10 -> {
            R.drawable.category10
        }

        11 -> {
            R.drawable.category11
        }

        else -> {
            R.drawable.category_placeholder
        }
    }
}