package com.snowhitejsk.tp05todo


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.snowhitejsk.tp05todo.databinding.RecyclerviewItemTodoBinding


class TodoRecyclerAdapter constructor(val context:Context, var todoItems:MutableList<TodoItem>) : RecyclerView.Adapter<TodoRecyclerAdapter.VH>() {

    val categoryIcons:Array<Int> = arrayOf(
        R.drawable.profle,
        R.drawable.ic_baseline_laptop_chromebook_24,
        R.drawable.ic_baseline_menu_book_24,
        R.drawable.ic_baseline_self_improvement_24,
        R.drawable.ic_baseline_color_lens_24,
        R.drawable.ic_baseline_groups_2_24,
        R.drawable.ic_baseline_bubble_chart_24
    )

    inner class VH constructor(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val binding:RecyclerviewItemTodoBinding = RecyclerviewItemTodoBinding.bind(itemView)
        init {
            binding.root.setOnClickListener {
                // TodoActivity 의 BottomSheet를 보여주는 기능메소드를 호출
                (context as TodoActivity).showBottomSheet(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        var itemView:View = layoutInflater.inflate(R.layout.recyclerview_item_todo, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.todoItemTvTitle.text= todoItems.get(position).title
        holder.binding.todoItemTvDate.text= todoItems[position].date
        holder.binding.todoItemIvCategory.setImageResource(categoryIcons[ todoItems[position].category ])
    }

    //코틀린언어의 함수 표기문법 중 return 값을 간략히 쓰기 위한 [함수 단순화]문법
    override fun getItemCount(): Int = todoItems.size


}