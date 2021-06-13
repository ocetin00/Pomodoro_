package com.oguzhancetin.pomodorotimer.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.oguzhancetin.pomodorotimer.R
import com.oguzhancetin.pomodorotimer.databinding.FragmentSettingsBinding
import com.oguzhancetin.pomodorotimer.util.Times
import com.oguzhancetin.pomodorotimer.util.TimesSharedPreferences
import com.oguzhancetin.pomodorotimer.viewmodel.FragmentSettingViewmodel


class SettingsFragment : Fragment() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var viewModel: FragmentSettingViewmodel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        sharedPref = TimesSharedPreferences.getSharred(requireContext())!!
        val binding = FragmentSettingsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(FragmentSettingViewmodel::class.java)


        initializeData(binding.normalSeekbar, Times.START_TIME, binding.normalTextView)
        initializeData(binding.shortSeekbar, Times.SHORT_BREAK, binding.shortTextView)
        initializeData(binding.longSeekbar, Times.LONG_BREAK, binding.longTextView)


        binding.normalSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var progress = 0
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                this.progress = progress
                binding.normalTextView.text = progress.toString() + " min"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                set(normalTime = progress.toLong())
            }


        })
        binding.shortSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var progress = 0
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                this.progress = progress
                binding.shortTextView.text = progress.toString() + " min"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                set(shortTime = progress.toLong())
            }


        })

        binding.longSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var progress = 0
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                this.progress = progress
                binding.longTextView.text = progress.toString() + " min"
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                set(longTime = progress.toLong())
            }


        })




        return binding.root
    }

    fun set(normalTime: Long? = null, shortTime: Long? = null, longTime: Long? = null) {

        sharedPref.apply {
            normalTime?.let {
                sharedPref.edit().putLong(Times.START_TIME.name, it * 60000).apply()
            }
            shortTime?.let {
                sharedPref.edit().putLong(Times.SHORT_BREAK.name, it * 60000).apply()
            }
            longTime?.let {
                sharedPref.edit().putLong(Times.LONG_BREAK.name, it * 60000).apply()
            }

            Toast.makeText(requireContext(), "Settings are changed!", Toast.LENGTH_SHORT).show()

        }
    }


    /*
   Initialize seekbars data and their texts
    */
    fun initializeData(bar: SeekBar, times: Times, seekBarText: TextView) {
        bar.max = 60
        bar.min = 1
        var latestProgress = (sharedPref.getLong(times.name, times.time) / (60000)).toInt()
        bar.progress = latestProgress
        seekBarText.text = latestProgress.toString() + " min"

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_alldata -> {
                viewModel.deleteAlldata()
                true
            }
            else -> {
                false
            }
        }
    }


}