package com.frankliang.a20211221_frankliang_nycschools.ui.fragments

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frankliang.a20211221_frankliang_nycschools.R
import com.frankliang.a20211221_frankliang_nycschools.dataBase.SchoolEntity
import com.frankliang.a20211221_frankliang_nycschools.databinding.ItemSchoolRowBinding

class SchoolListAdapter(private val clickListener: OnItemClickListener) :
    PagedListAdapter<SchoolEntity, SchoolListAdapter.SchoolViewHolder>(SCHOOL_DIFF) {
    companion object {
        val SCHOOL_DIFF = object :DiffUtil.ItemCallback<SchoolEntity>() {
            override fun areItemsTheSame(oldItem: SchoolEntity, newItem: SchoolEntity): Boolean {
                return TextUtils.equals(oldItem.id, newItem.id)
            }

            override fun areContentsTheSame(oldItem: SchoolEntity, newItem: SchoolEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val binding = DataBindingUtil.inflate<ItemSchoolRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_school_row,
            parent,
            false
        )
        return SchoolViewHolder(binding, clickListener)
    }


    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val school = getItem(position)
        school?.let { holder.onBind(it, position) }
    }

    class SchoolViewHolder(
        private val binding: ItemSchoolRowBinding,
        private val clickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(school: SchoolEntity, position: Int) {
            binding.schoolEntity = school
            val extra = initTransition(position)
            binding.container.setOnClickListener {
                clickListener.onSchoolClicked(school, extra, position)
            }
            binding.ivSelected.setOnClickListener { clickListener.onToggleSave(school) }
            binding.executePendingBindings()
        }
        private fun initTransition(position: Int): FragmentNavigator.Extras {
            val context = binding.tvBoro.context
            binding.tvName.transitionName = context.getString(R.string.trans_name, position)
            binding.tvBoro.transitionName = context.getString(R.string.trans_location, position)
            binding.ivSelected.transitionName = context.getString(R.string.trans_saved, position)
            return FragmentNavigatorExtras(
                binding.tvName to context.getString(R.string.trans_name, position),
                binding.tvBoro to context.getString(R.string.trans_location, position),
                binding.ivSelected to context.getString(R.string.trans_saved, position),
            )
        }
    }

    interface OnItemClickListener: OnSchoolSaveToggle {
        fun onSchoolClicked(school: SchoolEntity, extras: FragmentNavigator.Extras, position: Int)
    }
}