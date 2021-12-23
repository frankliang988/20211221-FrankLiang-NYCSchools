package com.frankliang.a20211221_frankliang_nycschools.util

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.borjabravo.readmoretextview.ReadMoreTextView
import com.frankliang.a20211221_frankliang_nycschools.R
import com.frankliang.a20211221_frankliang_nycschools.dataBase.SchoolEntity
import com.frankliang.a20211221_frankliang_nycschools.ui.fragments.SchoolListAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("app:showIsSaved")
    fun loadContactAvatar(view: ImageView, school: SchoolEntity?) {
        school?.let {
            if(it.isSaved) {
                view.setBackgroundResource(R.drawable.ic_round_star_24)
            } else {
                view.setBackgroundResource(R.drawable.ic_round_star_border_24)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:setBoroughText")
    fun setBoroughText(view: TextView, school: SchoolEntity?) {
        school?.let {
            when(school.borough) {
                SchoolEntity.BROOKLYN -> view.text = view.context.getString(R.string.boro_brooklyn)
                SchoolEntity.STATEN_ISLAND -> view.text = view.context.getString(R.string.boro_staten_island)
                SchoolEntity.BRONX -> view.text = view.context.getString(R.string.boro_bronx)
                SchoolEntity.QUEENS -> view.text = view.context.getString(R.string.boro_queens)
                SchoolEntity.MANHATTAN -> view.text = view.context.getString(R.string.boro_manhattan)
                else -> view.text = view.context.getString(R.string.unknown)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:setMathSat")
    fun setMathSatText(textView: TextView, entity: SchoolEntity?) {
        entity?.let {
            val value: Int = entity.satMath?:0
            textView.text = if(value == 0) textView.context.getText(R.string.school_sat_mat_na)
            else textView.context.getString(R.string.school_sat_mat, value)
        }
    }

    @JvmStatic
    @BindingAdapter("app:setReadingSat")
    fun setReadingSatText(textView: TextView, entity: SchoolEntity?) {
        entity?.let {
            val value: Int = entity.satReading?:0
            textView.text = if(value == 0) textView.context.getText(R.string.school_sat_reading_na)
            else textView.context.getString(R.string.school_sat_reading, value)
        }
    }

    @JvmStatic
    @BindingAdapter("app:setWritingSat")
    fun setWritingSatText(textView: TextView, entity: SchoolEntity?) {
        entity?.let {
            val value: Int = entity.satWriting?:0
            textView.text = if(value == 0) textView.context.getText(R.string.school_sat_writing_na)
            else textView.context.getString(R.string.school_sat_writing, value)
        }
    }

    @JvmStatic
    @BindingAdapter("app:setTotalStudent")
    fun setTotalStudentText(textView: TextView, entity: SchoolEntity?) {
        entity?.let {
            val value: Int = entity.totalStudents?:0
            textView.text = if(value == 0) textView.context.getText(R.string.school_students_na)
            else textView.context.getString(R.string.school_students, value)
        }
    }

    @JvmStatic
    @BindingAdapter("app:setGradRate")
    fun setGradRateText(textView: TextView, entity: SchoolEntity?) {
        entity?.let {
            val value: Int = ((entity.rateGraduation ?: 0.0) * 100).toInt()
            var valueText = "$value%"
            textView.text = if(value == 0) textView.context.getText(R.string.school_rate_graduation_na)
            else textView.context.getString(R.string.school_rate_graduation, valueText)
        }
    }

    @JvmStatic
    @BindingAdapter("app:visibleIf")
    fun changeVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:visibleIfText")
    fun changeVisibility(view: View, text: String?) {
        view.visibility = if (!TextUtils.isEmpty(text)) View.VISIBLE else View.GONE
    }
}