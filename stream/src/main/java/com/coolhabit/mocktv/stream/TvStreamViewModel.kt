package com.coolhabit.mocktv.stream

import com.coolhabit.mocktv.baseUI.model.StatefulData
import com.coolhabit.mocktv.baseUI.presentation.BaseViewModel
import com.coolhabit.mocktv.domain.entities.TvChannel
import com.coolhabit.mocktv.domain.usecases.ChannelsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvStreamViewModel @Inject constructor(
    private val useCase: ChannelsUseCase,
) : BaseViewModel() {

    private val _loadStream = statefulSharedFlow<TvChannel>()
    val loadStream: Flow<StatefulData<TvChannel>>
        get() = _loadStream

    fun initContent(channelId: Int) {
        _loadStream.fetch {
            useCase.loadChannelById(channelId)
        }
    }
}
