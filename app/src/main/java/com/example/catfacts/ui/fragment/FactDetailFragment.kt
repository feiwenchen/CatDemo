package com.example.catfacts.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.catfacts.base.BaseFragment
import com.example.catfacts.databinding.FragmentFactDetailBinding
import com.example.catfacts.request.RequestCatFactsViewModel
import com.example.catfacts.viewmodel.CatFactsViewModel

class FactDetailFragment : BaseFragment<CatFactsViewModel, FragmentFactDetailBinding>() {

    private var factId: String? = null

    //请求的ViewModel /** */
    private val requestCatFactsViewModel: RequestCatFactsViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        factId = arguments?.getString("factId")
        mDatabind.textviewContent.movementMethod = ScrollingMovementMethod()
    }

    override fun lazyLoadData() {
        factId?.let { requestCatFactsViewModel.getCatFactDetailData(it) }
    }

    @SuppressLint("SetTextI18n")
    override fun createObserver() {
        requestCatFactsViewModel.factDetailDataState.observe(viewLifecycleOwner, Observer {
            mDatabind.textviewName.text = "${it?.user?.name?.last} ${it?.user?.name?.first}"
            mDatabind.textviewTime.text = it?.createdAt
            mDatabind.textviewContent.text = it?.text
        })
    }
}