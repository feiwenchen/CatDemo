package com.example.catfacts.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.catfacts.R
import com.example.catfacts.base.BaseFragment
import com.example.catfacts.databinding.FragmentFactListBinding
import com.example.catfacts.network.ListDataUiState
import com.example.catfacts.request.RequestCatFactsViewModel
import com.example.catfacts.ui.adapter.CatFactAdapter
import com.example.catfacts.ui.view.DefineLoadMoreView
import com.example.catfacts.ui.view.SpaceItemDecoration
import com.example.catfacts.ui.view.loadCallBack.EmptyCallback
import com.example.catfacts.ui.view.loadCallBack.ErrorCallback
import com.example.catfacts.ui.view.loadCallBack.LoadingCallback
import com.example.catfacts.util.SettingUtil
import com.example.catfacts.viewmodel.CatFactsViewModel
import com.example.common_plugin.base.appContext
import com.example.common_plugin.ext.nav
import com.example.common_plugin.ext.navigateAction
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.yanzhenjie.recyclerview.SwipeRecyclerView

class FactListFragment : BaseFragment<CatFactsViewModel, FragmentFactListBinding>() {
    //适配器
    private lateinit var catFactAdapter: CatFactAdapter

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //请求的ViewModel /** */
    private val requestCatFactsViewModel: RequestCatFactsViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        catFactAdapter = CatFactAdapter(arrayListOf())
        catFactAdapter.run {
            setOnItemClickListener { _, _, position ->
                nav().navigateAction(R.id.action_factListFragment_to_factDetailFragment,
                    Bundle().apply {
                        putString("factId", catFactAdapter.data[position].id)
                    })
            }
        }

        //状态页配置
        loadsir = loadServiceInit(mDatabind.includeRecyclerview.swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestCatFactsViewModel.getCatFactsData(true)
        }


        mDatabind.toolbar.run {
            inflateMenu(R.menu.facts_menu)
        }
        //初始化recyclerView
        mDatabind.includeRecyclerview.recyclerView.initSwipeRecyclerView(LinearLayoutManager(context), catFactAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, SettingUtil.dp2px(8f)))
            it.initFooter {
                //触发加载更多时请求数据
                requestCatFactsViewModel.getCatFactsData(false)
            }
        }
        //初始化 SwipeRefreshLayout
        mDatabind.includeRecyclerview.swipeRefresh.initSwipeRefreshLayout {
            //触发刷新监听时请求数据
            requestCatFactsViewModel.getCatFactsData(true)
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestCatFactsViewModel.getCatFactsData(true)
    }

    override fun createObserver() {
        requestCatFactsViewModel.factListDataState.observe(viewLifecycleOwner, Observer {
            loadListData(it, catFactAdapter, loadsir, mDatabind.includeRecyclerview.recyclerView,mDatabind.includeRecyclerview.swipeRefresh)
        })
    }

    //绑定SwipeRecyclerView
    private fun SwipeRecyclerView.initSwipeRecyclerView(
        layoutManger: RecyclerView.LayoutManager,
        bindAdapter: RecyclerView.Adapter<*>,
        isScroll: Boolean = true
    ): SwipeRecyclerView {
        layoutManager = layoutManger
        setHasFixedSize(true)
        adapter = bindAdapter
        isNestedScrollingEnabled = isScroll
        return this
    }

    private fun SwipeRecyclerView.initFooter(loadmoreListener: SwipeRecyclerView.LoadMoreListener): DefineLoadMoreView {
        val footerView = DefineLoadMoreView(appContext)
        //给尾部设置颜色
        footerView.setLoadViewColor(SettingUtil.getOneColorStateList(appContext))
        //设置尾部点击回调
        footerView.setmLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
            footerView.onLoading()
            loadmoreListener.onLoadMore()
        })
        this.run {
            //添加加载更多尾部
            addFooterView(footerView)
            setLoadMoreView(footerView)
            //设置加载更多回调
            setLoadMoreListener(loadmoreListener)
        }
        return footerView
    }

    private fun SwipeRefreshLayout.initSwipeRefreshLayout(onRefreshListener: () -> Unit) {
        this.run {
            setOnRefreshListener {
                onRefreshListener.invoke()
            }
            //设置主题颜色
            setColorSchemeColors(SettingUtil.getColor(appContext))
        }
    }

    private fun loadServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
        val loadsir = LoadSir.getDefault().register(view) {
            //点击重试时触发的操作
            callback.invoke()
        }
        loadsir.showSuccess()
        SettingUtil.setLoadingColor(SettingUtil.getColor(appContext), loadsir)
        return loadsir
    }

    private fun LoadService<*>.showEmpty() {
        this.showCallback(EmptyCallback::class.java)
    }

    /**
     * 设置加载中
     */
    private fun LoadService<*>.showLoading() {
        this.showCallback(LoadingCallback::class.java)
    }

    private fun LoadService<*>.showError(message: String = "") {
        if (message.isNotEmpty()) {
            this.setCallBack(ErrorCallback::class.java) { _, view ->
                view.findViewById<TextView>(R.id.error_text).text = message
            }
        }
        this.showCallback(ErrorCallback::class.java)
    }

    private fun <T> loadListData(
        data: ListDataUiState<T>,
        baseQuickAdapter: BaseQuickAdapter<T, *>,
        loadService: LoadService<*>,
        recyclerView: SwipeRecyclerView,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
        if (data.isSuccess) {
            //成功
            when {
                //第一页并没有数据 显示空布局界面
                data.isFirstEmpty -> {
                    loadService.showEmpty()
                }
                //是第一页
                data.isRefresh -> {
                    baseQuickAdapter.setList(data.listData)
                    loadService.showSuccess()
                }
                //不是第一页
                else -> {
                    baseQuickAdapter.addData(data.listData)
                    loadService.showSuccess()
                }
            }
        } else {
            //失败
            if (data.isRefresh) {
                //如果是第一页，则显示错误界面，并提示错误信息
                loadService.showError(data.errMessage)
            } else {
                recyclerView.loadMoreError(0, data.errMessage)
            }
        }
    }
}