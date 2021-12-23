package com.frankliang.a20211221_frankliang_nycschools.network

import android.content.Context
import android.util.Log
import com.frankliang.a20211221_frankliang_nycschools.dataBase.SchoolEntity
import com.frankliang.a20211221_frankliang_nycschools.di.ServiceLocator
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

object NetworkRepository {

    suspend fun getAndSaveSchoolList(context: Context) {
        val dataStoreManager = ServiceLocator.getInstance(context).datastoreManager
        val schoolListResponseList = getSchoolList()
        if (schoolListResponseList.isSuccessful && schoolListResponseList.body().orEmpty()
                .isNotEmpty()
        ) {
            insertSchoolListIntoDb(context, schoolListResponseList.body()!!)
            dataStoreManager.setHasData(true)
        }
    }

    private suspend fun getSchoolList(): Response<List<SchoolResponse>> {
        return NetWorkProvider.getRetrofitClient().create(RestApi::class.java).getSchoolList()
    }

    private fun insertSchoolListIntoDb(context: Context, schools: List<SchoolResponse>) {
        val serviceLocator = ServiceLocator.getInstance(context)
        val schoolEntities =
            schools.map { schoolResponse ->
                SchoolEntity(
                    schoolResponse.id!!,
                    schoolResponse.name,
                    schoolResponse.borough
                )
            }
        serviceLocator.dbRepository.insertSchools(schoolEntities)
    }

    suspend fun getSchoolInfo(context: Context, id: String) {
        coroutineScope {
            val satJob: Deferred<Response<List<SATResponse>>> = async { getSchoolSATInfo(id) }
            val infoJob: Deferred<Response<List<SchoolResponse>>> =
                async { getSchoolInfoExtra(id) }
            val sat = satJob.await()
            val info = infoJob.await()
            ServiceLocator.getInstance(context).dbRepository.updateSchoolInfoOnApiResponse(id, info, sat)
        }
    }

    private suspend fun getSchoolInfoExtra(id: String): Response<List<SchoolResponse>> {
        return NetWorkProvider.getRetrofitClient().create(RestApi::class.java).getSchoolInfo(id)
    }

    private suspend fun getSchoolSATInfo(id: String): Response<List<SATResponse>> {
        return NetWorkProvider.getRetrofitClient().create(RestApi::class.java).getSATInfo(id)
    }

}