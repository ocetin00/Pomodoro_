package com.oguzhancetin.pomodorotimer.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.oguzhancetin.pomodorotimer.databinding.FragmentSettingsBinding
import com.oguzhancetin.pomodorotimer.util.Times
import com.oguzhancetin.pomodorotimer.util.TimesSharedPreferences


class SettingsFragment : Fragment() {
    private lateinit var sharedPref:SharedPreferences

    override fun onStart() {
        super.onStart()
        sharedPref = TimesSharedPreferences.getSharred(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)

        //save new times
        binding.buttonDone.setOnClickListener {
            val normalTime:Long? = (binding.editTextNormalTime.text ).toString().toLongOrNull()
            val shortTime:Long? = (binding.editTextShortTime.text ).toString().toLongOrNull()
            val longTime:Long? = (binding.editTextLongTime.text ).toString().toLongOrNull()

            sharedPref.apply {
                normalTime?.let {
                    sharedPref.edit().putLong(Times.START_TIME.name,it*60000).apply()
                }
                shortTime?.let {
                    sharedPref.edit().putLong(Times.SHORT_BREAK.name,it*60000).apply()
                }
                longTime?.let {
                    sharedPref.edit().putLong(Times.LONG_BREAK.name,it*60000).apply()
                }

                Toast.makeText(requireContext(),"Settings are changed!",Toast.LENGTH_SHORT).show()


            }




        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}