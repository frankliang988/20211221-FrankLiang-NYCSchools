package com.frankliang.a20211221_frankliang_nycschools.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.frankliang.a20211221_frankliang_nycschools.dataBase.AppDatabase
import com.frankliang.a20211221_frankliang_nycschools.dataBase.DatabaseRepository
import com.frankliang.a20211221_frankliang_nycschools.util.DbConstant
import com.frankliang.a20211221_frankliang_nycschools.util.SingletonHolder

class ServiceLocator private constructor(private val context: Context){
    companion object : SingletonHolder<ServiceLocator, Context>(::ServiceLocator) {
        const val DATA_STORE_NAME = "app_datastore"


    }
    private val mContext = context.applicationContext
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    private val appDatabase: AppDatabase =
        Room.databaseBuilder(mContext, AppDatabase::class.java, DbConstant.DB_NAME).build()
    val dbRepository: DatabaseRepository = DatabaseRepository(appDatabase)
    val datastoreManager: DataStoreManager = DataStoreManager(context.dataStore)
}