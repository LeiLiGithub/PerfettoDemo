package com.lei.perfettodemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SlowAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<SlowAdapter.SlowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slow_row, parent, false)
        return SlowViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlowViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class SlowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.titleTextView)

        fun bind(text: String) {
            // 在 Perfetto 里，这一段会作为一个 section 显示出来
//            Trace.beginSection("SlowViewHolder.bind")

            try {
                textView.text = text

                // 刻意制造一段 CPU 密集型计算，阻塞主线程
                simulateHeavyWork()
            } finally {
//                Trace.endSection()
            }
        }

        private fun simulateHeavyWork() {
            val start = System.nanoTime()
            var acc = 0.0
            // 忙等大约 45ms（按设备性能可能有点浮动）
            while (System.nanoTime() - start < 45_000_000L) { // 45ms
                acc += kotlin.math.sin(acc)
            }
            // 防止编译器完全优化掉
            if (acc == 42.0) {
                println("impossible")
            }
        }
    }
}