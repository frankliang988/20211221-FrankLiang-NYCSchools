package com.frankliang.a20211221_frankliang_nycschools.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frankliang.a20211221_frankliang_nycschools.util.DbConstant

@Database(entities = [SchoolEntity::class], version = DbConstant.DB_VERSION)
abstract class AppDatabase: RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
}