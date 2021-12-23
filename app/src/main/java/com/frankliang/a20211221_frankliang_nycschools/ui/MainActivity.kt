package com.frankliang.a20211221_frankliang_nycschools.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.frankliang.a20211221_frankliang_nycschools.R
import com.frankliang.a20211221_frankliang_nycschools.databinding.ActivityMainBinding
import com.frankliang.a20211221_frankliang_nycschools.di.ServiceLocator
import kotlin.time.ExperimentalTime

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ServiceLocator.getInstance(this)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.fetchSchoolListIfNeeded()
    }

    fun updateAppBar(title: String, showHomeButton: Boolean) {
        supportActionBar?.let {
            it.setHomeButtonEnabled(showHomeButton)
            it.setDisplayHomeAsUpEnabled(showHomeButton)
            it.title = title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}