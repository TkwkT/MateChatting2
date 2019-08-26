package com.example.matechatting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DirectionAdapter(private val lists: List<String>) :
    RecyclerView.Adapter<DirectionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_directlayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = lists[holder.adapterPosition]
        holder.tv.text = name


        /**
         * 记录点击的checkbox的名字对应的check状态
         * 并把选中的名字加入clicked_names
         */
        holder.checkBox.setOnClickListener {
            val ischecked = mapChecked.get(name)
            if (ischecked == false) {
                mapChecked.put(name.toString(), true)
                clicked_names.add(name)
            } else {
                mapChecked.put(name.toString(), false)
                clicked_names.remove(name)
            }
            //刷新
            notifyDataSetChanged()
        }
        
        /**
         * 当用户选中大于等于三个时，除了选中的之外其他都不能点击
         */
        if (clicked_names.size >= 3 || clicked_names.size < 0) {
            if (name in clicked_names) {
                holder.checkBox.isEnabled = true
            } else holder.checkBox.isEnabled = false
        }else{
            holder.checkBox.isEnabled = true
        }

        if (mapChecked.get(name) == true) {
            holder.checkBox.isChecked = true
        } else holder.checkBox.isChecked = false

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var checkBox: CheckBox
        var tv: TextView

        init {
            tv = view.findViewById<View>(R.id.item_textview) as TextView
            checkBox = view.findViewById(R.id.item_checkbox) as CheckBox
        }
    }


}
