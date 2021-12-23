package com.frankliang.a20211221_frankliang_nycschools.ui.fragments

import com.frankliang.a20211221_frankliang_nycschools.dataBase.SchoolEntity

interface OnSchoolSaveToggle {
    fun onToggleSave(school: SchoolEntity)
}