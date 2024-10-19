package com.decide.app.account.data

import com.decide.app.account.domain.kladr.KladrApi
import com.decide.app.account.domain.kladr.KladrClient
import com.decide.app.account.domain.kladr.kladrExceptionMapper
import com.decide.app.utils.DecideException
import com.decide.app.utils.NetworkChecker
import com.decide.app.utils.Resource
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class KladrClientImpl @Inject constructor(
    private val kladrClient: KladrApi,
    private val networkChecker: NetworkChecker
) : KladrClient {
    override suspend fun getCities(request: String): Resource<List<String>, DecideException> {
        return if (!networkChecker.isConnected()) {
            Resource.Error(DecideException.NoInternet())
        } else {
            try {
                Resource.Success(kladrClient.getCities(request).result.drop(1).map {
                    it.city
                })
            } catch (exception: HttpException) {
                Timber.tag("TAG").d(exception.message())
                Resource.Error(kladrExceptionMapper(exception.code()))
            } catch (exception: Exception) {
                Timber.tag("TAG").d(exception)
                Resource.Error(kladrExceptionMapper(-999))
            }
        }
    }
}