package com.example.rsshool2021_android_task_pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsshool2021_android_task_pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), StopwatchListener {

    private lateinit var binding: ActivityMainBinding

    private val stopwatchAdapter = StopwatchAdapter(this)
    private val stopwatches = mutableListOf<Stopwatch>()
    private var nextId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stopwatchAdapter
        }

        binding.addButton.setOnClickListener {
            println("add new stopwatch at index $nextId")
            stopwatches.add(Stopwatch(nextId++, 0, false))
            notifyChanges()
        }
    }

    override fun start(id: Int) {
        println("start stopwatch timer at index $id")
        changeStopwatch(id, null, true)
    }

    override fun stop(id: Int, currentMs: Long) {
        println("stop stopwatch timer at index $id")
        changeStopwatch(id, currentMs, false)
    }

    override fun delete(id: Int) {
        println("delete stopwatch at index $id")
        stopwatches.remove(stopwatches.find { it.id == id })
        notifyChanges()
    }

    private fun changeStopwatch(id: Int, currentMs: Long?, isStarted: Boolean) {
        val stopwatch = stopwatches.find { it.id == id }

        stopwatch?.apply {
            if (currentMs != null) {
                this.currentMs = currentMs
            }
            this.isStarted = isStarted
        }

        notifyChanges()
    }

    private fun notifyChanges() {
        stopwatchAdapter.notifyDataSetChanged()
        stopwatchAdapter.submitList(stopwatches.toList())
    }
}