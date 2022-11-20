package com.coolhabit.mocktv.stream

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.navigation.fragment.navArgs
import com.coolhabit.mocktv.baseUI.extensions.load
import com.coolhabit.mocktv.baseUI.presentation.BaseFragment
import com.coolhabit.mocktv.baseUI.presentation.BaseViewModel
import com.coolhabit.mocktv.stream.databinding.FragmentTvStreamBinding
import com.coolhabit.mocktv.stream.utils.QualityItem
import com.coolhabit.mocktv.stream.utils.QualityItemInflater
import com.coolhabit.mocktv.stream.utils.generateQualityList
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class TvStreamFragment : BaseFragment(R.layout.fragment_tv_stream), Player.Listener {

    companion object {
        const val MOCK_URL =
            "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"
    }

    private val viewModel by viewModels<TvStreamViewModel>()
    private lateinit var binding: FragmentTvStreamBinding
    private val args by navArgs<TvStreamFragmentArgs>()
    var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    var qualityList = mutableListOf<QualityItem>()
    private var trackSelector: DefaultTrackSelector? = null
    private val qualityItemInflater = QualityItemInflater()
    var currentQualityName: String? = null


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

        binding.qualityBtn.setOnClickListener {
//            qualityPopUp?.show()
            val listView = binding.qualityListView.root
            if (listView.visibility == View.GONE) {
                listView.visibility = View.VISIBLE
            } else {
                listView.visibility = View.GONE
            }
        }
    }

    override fun withViewModel(): BaseViewModel = viewModel.apply {
        loadStream.collectWithState {
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
        trackSelector = DefaultTrackSelector(requireContext(), AdaptiveTrackSelection.Factory())
        player = ExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector!!)
            .build()
        player?.playWhenReady = true
        binding.videoView.player = player
        val mediaSource =
            HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
                .createMediaSource(MediaItem.fromUri(MOCK_URL))
        player?.setMediaSource(mediaSource)
        player?.seekTo(playbackPosition)
        player?.playWhenReady = playWhenReady
        player?.addListener(this)
        player?.prepare()
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

    private fun setUpQualityList() {
        qualityItemInflater.itemInflate(binding.qualityListView.qualityContainer, qualityList)
        qualityItemInflater.onItemClick = { qualityItem ->
            trackSelector!!.parameters = trackSelector!!.parameters
                .buildUpon()
                .setTrackSelectionOverrides(qualityItem.selectionOverride.build())
                .setTunnelingEnabled(true)
                .build()
            currentQualityName = qualityItem.name
            binding.qualityListView.root.visibility = View.GONE
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        if (playbackState == Player.STATE_READY) {
            trackSelector?.generateQualityList(currentQualityName)?.let {
                qualityList = it
                setUpQualityList()
            }
        }
    }
}
