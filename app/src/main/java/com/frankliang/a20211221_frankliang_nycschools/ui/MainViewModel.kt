package com.frankliang.a20211221_frankliang_nycschools.ui

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.frankliang.a20211221_frankliang_nycschools.dataBase.SchoolEntity
import com.frankliang.a20211221_frankliang_nycschools.di.ServiceLocator
import com.frankliang.a20211221_frankliang_nycschools.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.waitMillis
import java.lang.Exception
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime

class MainViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var schools: LiveData<PagedList<SchoolEntity>>
    lateinit var schoolSelected: LiveData<SchoolEntity>
    val fetchInfoSuccess = MutableLiveData<Boolean>()
    private var isSavedOnly = false
    private val dataStoreManager = ServiceLocator.getInstance(getApplication()).datastoreManager
    private val databaseRepository = ServiceLocator.getInstance(getApplication()).dbRepository

    init {
        fetchInfoSuccess.value = true
    }
    //TODO: save timestamp to datastore on fetch success and resync data ever x time
    fun fetchSchoolListIfNeeded() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataStoreManager.getHasData().collect { hasData ->
                    Log.e("Test: ", "hasdata: $hasData")
                    if(!hasData) NetworkRepository.getAndSaveSchoolList(getApplication())
                }
            }
        }
    }

    fun loadData(isSavedOnly: Boolean): LiveData<PagedList<SchoolEntity>>{
        if(isSavedOnly != this.isSavedOnly || !this::schools.isInitialized) {
            this.isSavedOnly = isSavedOnly
            initPagedList(isSavedOnly)
        }
        return schools
    }

    private fun initPagedList(isSavedOnly: Boolean) {
        val factory = databaseRepository.getSchoolList(isSavedOnly)
        val config = PagedList.Config.Builder().setPageSize(30).setEnablePlaceholders(true).build()
        val pagedListBuilder = LivePagedListBuilder(factory, config)
        schools = pagedListBuilder.build()
    }

    fun loadOneSchool(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    NetworkRepository.getSchoolInfo(getApplication(), id)
                    updateFetchInfoSuccess(true)
                } catch (e: Exception) {
                    updateFetchInfoSuccess(false)
                }
            }
        }
        schoolSelected = databaseRepository.getOneSchoolInfo(id)
        Transformations.distinctUntilChanged(schoolSelected)
    }

    private suspend fun updateFetchInfoSuccess(isSuccess: Boolean) {
        withContext(Dispatchers.Main) {
            fetchInfoSuccess.value = isSuccess
        }
    }

    fun toggleSchoolSavedStatus(schoolEntity: SchoolEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseRepository.toggleSavedStatus(schoolEntity)
            }
        }
    }

}