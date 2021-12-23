package com.frankliang.a20211221_frankliang_nycschools.dataBase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.frankliang.a20211221_frankliang_nycschools.network.SATResponse
import com.frankliang.a20211221_frankliang_nycschools.network.SchoolResponse
import retrofit2.Response

class DatabaseRepository(private val appDatabase: AppDatabase) {
    private val mSchoolDao = appDatabase.schoolDao()

    fun insertSchools(schools: List<SchoolEntity>) {
        appDatabase.runInTransaction {
            schools.forEach { school ->
                mSchoolDao.insertSchool(school)
            }
        }
    }

    suspend fun updateSchoolInfoOnApiResponse(
        id: String,
        infoResponse: Response<List<SchoolResponse>>,
        satResponse: Response<List<SATResponse>>
    ) {
        val newSchool = SchoolEntity(id)
        if(infoResponse.isSuccessful && !infoResponse.body().isNullOrEmpty()) {
            newSchool.setInfoData(infoResponse.body()!![0])
        } else {
            Log.e("test:", "${infoResponse.errorBody()}")
        }

        if(satResponse.isSuccessful && !satResponse.body().isNullOrEmpty()) {
            newSchool.setSATData(satResponse.body()!![0])
            Log.e("test:", "${satResponse.errorBody()}")
        }
        val oldEntity = mSchoolDao.getOneSchool(id)
        newSchool.name = oldEntity.name
        newSchool.isSaved = oldEntity.isSaved
        newSchool.borough = oldEntity.borough
        if(newSchool == oldEntity) return
        mSchoolDao.updateSchoolInfo(newSchool)
    }

    fun getSchoolList(isSaveOnly: Boolean): DataSource.Factory<Int, SchoolEntity> {
        return if (isSaveOnly) mSchoolDao.getSavedSchoolList() else mSchoolDao.getSchoolList()
    }

    suspend fun toggleSavedStatus(schoolEntity: SchoolEntity) {
        mSchoolDao.updateSavedStatus(schoolEntity.id, !schoolEntity.isSaved)
    }

    fun getOneSchoolInfo(id: String): LiveData<SchoolEntity> {
        return mSchoolDao.getOneSchoolLive(id)
    }
}