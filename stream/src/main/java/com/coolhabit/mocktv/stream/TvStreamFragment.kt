package com.coolhabit.mocktv.stream

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.navigation.fragment.navArgs
import com.coolhabit.mocktv.baseUI.extensions.load
import com.coolhabit.mocktv.baseUI.presentation.BaseFragment
import com.coolhabit.mocktv.baseUI.presentation.BaseViewModel
import com.coolhabit.mocktv.stream.databinding.FragmentTvStreamBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class TvStreamFragment : BaseFragment(R.layout.fragment_tv_stream) {

    companion object {
        const val MOCK_URL = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
    }

    private val viewModel by viewModels<TvStreamViewModel>()
    private lateinit var binding: FragmentTvStreamBinding
    private val args by navArgs<TvStreamFragmentArgs>()
    var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initContent(args.channelId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manageFullScreen()
        binding = FragmentTvStreamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            releasePlayer()
            viewModel.navigateBack()
        }
    }

    override fun withViewModel(): BaseViewModel = viewModel.apply {
        loadStream.observe {
            it.isSuccessful { channel ->
                with(binding) {
                    channelLogo.load(channel.channelLogo)
                    channelName.text = channel.channelName
                    currentProgram.text = channel.currentProgram
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onResume() {
        super.onResume()
        if (player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releasePlayer()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
                val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
                val mediaSource: MediaSource = HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(MOCK_URL)))
                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    private fun manageFullScreen() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requireActivity().window.insetsController?.hide(WindowInsets.Type.systemBars())
    }
}
