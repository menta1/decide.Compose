package com.decide.app.account.domain.kladr

import com.decide.app.account.exceptions.KladrException
import com.decide.app.utils.DecideException

fun kladrExceptionMapper(responseCode: Int): DecideException.KladrException {
    return when (responseCode) {
        400 -> KladrException.BadRequest(responseCode.toString(), "BadRequest")
        404 -> KladrException.NotFound(responseCode.toString(), "NotFound")
        500 -> KladrException.InternalServerError(responseCode.toString(), "InternalServerError")

        else -> KladrException.Undefined("Undefined", "Undefined")
    }
}