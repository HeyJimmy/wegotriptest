package com.example.wegotriptest.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wegotriptest.R
import com.example.wegotriptest.TourStep

class StepListAdapter(private var steps: Array<TourStep>, val onClickListener: OnClickListener)
    : RecyclerView.Adapter<StepListAdapter.StepListHolder>() {

    fun renewItems(newSteps: Array<TourStep>) {
        steps = newSteps

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepListHolder {
        val inflatedView: View = LayoutInflater.from(parent.context).inflate(R.layout.step_holder, null, false)

        return StepListHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: StepListHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }
        holder.bindStep(steps, position)
    }

    override fun getItemCount(): Int = steps.size

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

    class StepListHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var step: TourStep? = null

        fun bindStep(steps: Array<TourStep>, position: Int) {
            this.step = steps[position]

            view.findViewById<TextView>(R.id.list_step_label).text = "${position + 1}/${steps.size}"
            view.findViewById<TextView>(R.id.list_step_title).text = this.step?.title
        }
    }
}
