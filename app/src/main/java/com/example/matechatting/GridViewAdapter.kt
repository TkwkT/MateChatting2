package com.example.matechatting

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView

class GridViewAdapter(
    private val list: List<String>,
    private val drawerLayout: DrawerLayout,
    private val context: DirectionActivity
) :
    RecyclerView.Adapter<GridViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gridview, parent, false)
        val holder = ViewHolder(view)
        holder.tv.setOnClickListener {
            val position = holder.adapterPosition
            drawerLayout.closeDrawers()
            DirectionFragment.bundlekey = listKey.get(position)
            context.replaceFragment(DirectionFragment())

            /**
             * 把用户点击过的大方向保存，并把颜色置黄
             * question :clicked_names.get(i)得到的是小方向，而listKey则是大方向的list ，要解决用小方向映射得到它的大方向
             */
            for (i in 0 until clicked_names.size - 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (clicked_names[i] in listKey) {
                        Log.e("wkx","clicked_names : $clicked_names")
                        holder.tv.background = context.getDrawable(R.color.bg_yellow)
                    } else holder.tv.background = context.getDrawable(R.color.bg_f6f6f6)
                }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = list[position]
        holder.tv.text = name

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv: TextView

        init {
            tv = view.findViewById<View>(R.id.tv_item_gradview) as TextView
        }
    }

}
