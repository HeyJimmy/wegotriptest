package com.example.wegotriptest.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wegotriptest.R
import com.example.wegotriptest.TourStep

class StepListAdapter(private var steps: Array<TourStep>) : RecyclerView.Adapter<StepListAdapter.StepListHolder>() {

    fun renewItems(newSteps: Array<TourStep>) {
        steps = newSteps

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepListHolder {
        val inflatedView: View = LayoutInflater.from(parent.context).inflate(R.layout.step_holder, null, false)

        return StepListHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: StepListHolder, position: Int) {
        holder.bindStep(steps, position)
    }

    override fun getItemCount(): Int = steps.size

    class StepListHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var step: TourStep? = null

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.i("CREATION", step!!.title)
        }

        fun bindStep(steps: Array<TourStep>, position: Int) {
            this.step = steps[position]

            view.findViewById<TextView>(R.id.list_step_label).text = "${position + 1}/${steps.size}"
            view.findViewById<TextView>(R.id.list_step_title).text = this.step?.title
        }
    }
}
