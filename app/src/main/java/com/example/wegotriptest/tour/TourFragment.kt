package com.example.wegotriptest.tour

import android.app.Application
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wegotriptest.R
import com.example.wegotriptest.Tour
import com.example.wegotriptest.databinding.FragmentTourBinding

class TourFragment : Fragment() {

    private lateinit var binding: FragmentTourBinding
    private lateinit var viewModel: TourViewModel

    // Media player variables
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private var pause: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application: Application = requireNotNull(activity).application
        binding = FragmentTourBinding.inflate(inflater)
        binding.lifecycleOwner = this

        var tour: Tour
        var stepIndex: Int

        try {
            tour = TourFragmentArgs.fromBundle(requireArguments()).tour
            stepIndex = TourFragmentArgs.fromBundle(requireArguments()).stepIndex
        } catch (e: IllegalArgumentException) { // TODO: Make it beautiful
            tour = Tour.initTourList(resources).first()
            stepIndex = 0
        }

        val viewModelFactory = TourViewModelFactory(tour, stepIndex)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TourViewModel::class.java)
        binding.viewModel = viewModel

        // Image slider
        val stepImageAdapter = StepImageAdapter()
        binding.imageSlider.setSliderAdapter(stepImageAdapter)

        // Media player
        initMediaPlayer(stepIndex)

        // Click listeners
        binding.audioPlayer.setOnClickListener {
            viewModel.navigateTo("step")
        }

        setMediaPlayerListeners()

        // Observers
        viewModel.stepIndex.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                binding.stepLabel.text = application.applicationContext.getString(
                    R.string.step_label, it + 1, viewModel.stepsCount
                )

                binding.stepTitle.text = viewModel.currentStep.title
                binding.playerStepTitle.text = viewModel.currentStep.title

                stepImageAdapter.renewItems(
                    viewModel.currentStep.imgUrls
                )
            }
        })

        viewModel.navigateToFragment.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    TourFragmentDirections.actionShowStep(tour, viewModel.stepIndex.value!!)
                )
                viewModel.navigateComplete()
            }
        })

        return binding.root
    }

    private fun initMediaPlayer(stepIndex: Int) {
        val resourceName = when(stepIndex) {
            0 -> R.raw.step_1
            1 -> R.raw.step_2
            2 -> R.raw.step_3
            3 -> R.raw.step_4
            else -> R.raw.step_1
        }

        mediaPlayer = MediaPlayer.create(context, resourceName)
    }

    private fun setMediaPlayerListeners() {
        binding.playerButtonPlay.setOnClickListener {
            if(mediaPlayer.isPlaying) { // stop playing
                mediaPlayer.pause()
                pause = true
                it.setBackgroundResource(R.drawable.ic_play)
            } else { // start playing
                if (pause) {
                    mediaPlayer.seekTo(mediaPlayer.currentPosition)
                }
                mediaPlayer.start()
                pause = false
                it.setBackgroundResource(R.drawable.ic_pause)
            }

            initSeekBar()

            mediaPlayer.setOnCompletionListener { _ ->
                it.setBackgroundResource(R.drawable.ic_play)
            }
        }

        binding.playerButtonForward.setOnClickListener {
            val forwardTime = mediaPlayer.currentPosition + 5000 // 5 seconds

            if (forwardTime <= mediaPlayer.duration) {
                mediaPlayer.seekTo(forwardTime)
            }
        }

        binding.playerButtonBackward.setOnClickListener {
            val backwardTime = mediaPlayer.currentPosition - 5000 // 5 seconds

            if (backwardTime > 0) {
                mediaPlayer.seekTo(backwardTime)
            }
        }

        binding.playerSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    if (mediaPlayer.isPlaying || pause) {
                        mediaPlayer.seekTo(progress)
                    } else {
                        binding.playerSeekBar.progress = 0
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    private fun initSeekBar() {
        binding.playerSeekBar.max = mediaPlayer.duration
        binding.playerSeekBar.progress = mediaPlayer.currentPosition

        runnable = Runnable {
            binding.playerSeekBar.progress = mediaPlayer.currentPosition

            handler.postDelayed(runnable, 100)
        }
        handler.postDelayed(runnable, 100)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }
}