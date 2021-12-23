package com.frankliang.a20211221_frankliang_nycschools.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.frankliang.a20211221_frankliang_nycschools.R
import com.frankliang.a20211221_frankliang_nycschools.dataBase.SchoolEntity
import com.frankliang.a20211221_frankliang_nycschools.databinding.FragmentSchoolListBinding
import com.frankliang.a20211221_frankliang_nycschools.ui.MainActivity
import com.frankliang.a20211221_frankliang_nycschools.ui.MainViewModel

class SchoolListFragment: Fragment(), SchoolListAdapter.OnItemClickListener {
    companion object {
        const val KEY_IS_SAVED_ONLY = "isSavedOnly"
    }

    private lateinit var binding: FragmentSchoolListBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private lateinit var schoolAdapter: SchoolListAdapter
    var isSavedOnly = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        isSavedOnly = requireArguments().getBoolean(KEY_IS_SAVED_ONLY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_school_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity) {
            val activity = requireActivity() as MainActivity
            activity.updateAppBar(getString(R.string.title_schools), true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initRecyclerView()
        postponeEnterTransition()
        viewModel.loadData(isSavedOnly).observe(requireActivity()) {
            schoolAdapter.submitList(it)
            (view.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun initRecyclerView() {
        schoolAdapter = SchoolListAdapter(this@SchoolListFragment)
        binding.rvSchoolList.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = linearLayoutManager
            this.adapter = schoolAdapter
        }
    }


    override fun onSchoolClicked(school: SchoolEntity, extras: FragmentNavigator.Extras, position: Int) {
        val bundle = bundleOf(
            SchoolDetailFragment.KEY_SCHOOL_ID to school.id,
            SchoolDetailFragment.KEY_SCHOOL_POS to position
        )

        navController.navigate(R.id.action_schoolListFragment_to_schoolDetailFragment,
            bundle, null, extras)
    }

    override fun onToggleSave(school: SchoolEntity) {
        viewModel.toggleSchoolSavedStatus(school)
    }
}