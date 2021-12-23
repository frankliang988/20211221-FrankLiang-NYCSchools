package com.frankliang.a20211221_frankliang_nycschools.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.frankliang.a20211221_frankliang_nycschools.R
import com.frankliang.a20211221_frankliang_nycschools.databinding.FragmentMainBinding
import com.frankliang.a20211221_frankliang_nycschools.ui.MainActivity

class MainFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentMainBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.btnBrowseSchool.setOnClickListener(this)
        binding.btnSavedSchool.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnBrowseSchool,
            binding.btnSavedSchool -> {
                val bundle =
                    if (v.id == binding.btnBrowseSchool.id)
                        bundleOf(SchoolListFragment.KEY_IS_SAVED_ONLY to false)
                    else
                        bundleOf(SchoolListFragment.KEY_IS_SAVED_ONLY to true)

                navController.navigate(R.id.action_mainFragment_to_schoolListFragment, bundle)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity) {
            val activity = requireActivity() as MainActivity
            activity.updateAppBar(getString(R.string.title_home), false)
        }
    }

}