package com.frankliang.a20211221_frankliang_nycschools.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.frankliang.a20211221_frankliang_nycschools.R
import com.frankliang.a20211221_frankliang_nycschools.dataBase.SchoolEntity
import com.frankliang.a20211221_frankliang_nycschools.databinding.FragmentSchoolDetailBinding
import com.frankliang.a20211221_frankliang_nycschools.ui.MainActivity
import com.frankliang.a20211221_frankliang_nycschools.ui.MainViewModel
import kotlin.properties.Delegates

class SchoolDetailFragment: Fragment(), OnSchoolSaveToggle {
    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentSchoolDetailBinding
    lateinit var schoolId: String
    var position = -1
    companion object {
        const val KEY_SCHOOL_ID = "school_id"
        const val KEY_SCHOOL_POS = "school_pos"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        schoolId = requireArguments().getString(KEY_SCHOOL_ID, "")
        position = requireArguments().getInt(KEY_SCHOOL_POS)
        postponeEnterTransition()
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity) {
            val activity = requireActivity() as MainActivity
            activity.updateAppBar(getString(R.string.title_detail), true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_school_detail, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.onSaveListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadOneSchool(schoolId)
        binding.executePendingBindings()
        if(position > -1) {
            binding.tvName.transitionName = requireContext().getString(R.string.trans_name, position)
            binding.tvLocation.transitionName = requireContext().getString(R.string.trans_location, position)
            binding.ivSaved.transitionName = requireContext().getString(R.string.trans_saved, position)
        }
        viewModel.schoolSelected.observe(requireActivity()) {
            (view.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    override fun onToggleSave(school: SchoolEntity) {
        viewModel.toggleSchoolSavedStatus(school)
    }
}