package com.example.catfacts.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.catfacts.R
import com.example.data_plugin.model.FactResponse

class CatFactAdapter(data: ArrayList<FactResponse>) : BaseQuickAdapter<FactResponse, BaseViewHolder>(R.layout.item_fact, data) {

    override fun convert(holder: BaseViewHolder, item: FactResponse) {
        //赋值
        item.run {
            holder.setText(R.id.item_fact_title, item.text)
            holder.setText(R.id.item_fact_time, item.createdAt)
        }
    }
}


