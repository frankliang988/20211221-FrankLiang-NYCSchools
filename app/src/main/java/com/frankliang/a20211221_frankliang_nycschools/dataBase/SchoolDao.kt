package com.frankliang.a20211221_frankliang_nycschools.dataBase

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface SchoolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchool(schoolEntity: SchoolEntity)

    @Update
    suspend fun updateSchoolInfo(schoolEntity: SchoolEntity)

    @Query("SELECT * FROM schools ORDER BY name ASC")
    fun getSchoolList(): DataSource.Factory<Int, SchoolEntity>

    @Query("SELECT * FROM schools WHERE isSaved = 1 ORDER BY name ASC")
    fun getSavedSchoolList(): DataSource.Factory<Int, SchoolEntity>

    @Query("SELECT * FROM schools WHERE id = :id")
    fun getOneSchoolLive(id: String): LiveData<SchoolEntity>

    @Query("SELECT * FROM schools WHERE id = :id")
    fun getOneSchool(id: String): SchoolEntity

    @Query("UPDATE schools SET isSaved = :isToSave WHERE id = :id")
    suspend fun updateSavedStatus(id: String, isToSave: Boolean)
}