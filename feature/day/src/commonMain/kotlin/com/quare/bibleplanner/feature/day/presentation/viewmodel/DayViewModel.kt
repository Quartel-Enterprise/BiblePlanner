package com.quare.bibleplanner.feature.day.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.quare.bibleplanner.core.books.domain.repository.BooksRepository
import com.quare.bibleplanner.core.model.plan.ReadingPlanType
import com.quare.bibleplanner.core.model.route.DayNavRoute
import com.quare.bibleplanner.feature.day.domain.usecase.GetDayDetailsUseCase
import com.quare.bibleplanner.feature.day.domain.usecase.UpdateChapterReadStatusUseCase
import com.quare.bibleplanner.feature.day.domain.usecase.UpdateDayReadStatusUseCase
import com.quare.bibleplanner.feature.day.domain.usecase.UpdateDayReadTimestampUseCase
import com.quare.bibleplanner.feature.day.presentation.model.DayUiEvent
import com.quare.bibleplanner.feature.day.presentation.model.DayUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DayViewModel(
    savedStateHandle: SavedStateHandle,
    private val getDayDetailsUseCase: GetDayDetailsUseCase,
    private val booksRepository: BooksRepository,
    private val updateDayReadStatusUseCase: UpdateDayReadStatusUseCase,
    private val updateChapterReadStatusUseCase: UpdateChapterReadStatusUseCase,
    private val updateDayReadTimestampUseCase: UpdateDayReadTimestampUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<DayUiState> = MutableStateFlow(DayUiState.Loading)
    val uiState: StateFlow<DayUiState> = _uiState.asStateFlow()

    private val _backUiAction: MutableSharedFlow<Unit> = MutableSharedFlow()
    val backUiAction: SharedFlow<Unit> = _backUiAction

    private val dayNumber: Int
    private val weekNumber: Int
    private val readingPlanType: ReadingPlanType

    init {
        // Extract day and week numbers from savedStateHandle using toRoute extension
        val route = savedStateHandle.toRoute<DayNavRoute>()
        dayNumber = route.dayNumber
        weekNumber = route.weekNumber
        readingPlanType = ReadingPlanType.valueOf(route.readingPlanType)

        loadDayDetails()
    }

    private fun loadDayDetails() {
        combine(
            getDayDetailsUseCase(weekNumber, dayNumber, readingPlanType),
            booksRepository.getBooksFlow(),
        ) { day, books ->
            if (day != null) {
                DayUiState.Loaded(
                    day = day,
                    weekNumber = weekNumber,
                    books = books,
                )
            } else {
                DayUiState.Loading
            }
        }.onEach { state ->
            _uiState.update { state }
        }.catch { error ->
            error.printStackTrace()
            _uiState.update { DayUiState.Loading }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: DayUiEvent) {
        when (event) {
            is DayUiEvent.OnChapterToggle -> {
                val currentState = _uiState.value
                if (currentState is DayUiState.Loaded) {
                    val passage = currentState.day.passages.getOrNull(event.passageIndex) ?: return

                    // Determine the new read status for this chapter
                    val newReadStatus = if (event.chapterIndex == -1) {
                        // Entire book (no chapters)
                        !passage.isRead
                    } else {
                        // Specific chapter - check if it's currently read
                        if (event.chapterIndex < 0 || event.chapterIndex >= passage.chapters.size) return
                        val chapter = passage.chapters[event.chapterIndex]
                        val book = currentState.books.find { it.id == passage.bookId } ?: return
                        val bookChapter = book.chapters.find { it.number == chapter.number } ?: return

                        // Check if chapter is read based on verse ranges
                        // Store nullable properties in local variables to enable smart cast
                        val startVerse = chapter.startVerse
                        val endVerse = chapter.endVerse
                        val isCurrentlyRead = when {
                            startVerse != null && endVerse != null -> {
                                val requiredVerses = startVerse..endVerse
                                requiredVerses.all { verseNumber ->
                                    bookChapter.verses.find { it.number == verseNumber }?.isRead == true
                                }
                            }

                            startVerse != null -> {
                                bookChapter.verses
                                    .filter { it.number >= startVerse }
                                    .all { it.isRead }
                            }

                            else -> {
                                bookChapter.isRead
                            }
                        }
                        !isCurrentlyRead
                    }

                    viewModelScope.launch {
                        updateChapterReadStatusUseCase(
                            weekNumber = weekNumber,
                            dayNumber = dayNumber,
                            passageIndex = event.passageIndex,
                            chapterIndex = event.chapterIndex,
                            isRead = newReadStatus,
                            readingPlanType = readingPlanType,
                        )
                        // State will be updated by the flow
                    }
                }
            }

            is DayUiEvent.OnDayReadToggle -> {
                viewModelScope.launch {
                    updateDayReadStatusUseCase(
                        weekNumber = weekNumber,
                        dayNumber = dayNumber,
                        isRead = event.isRead,
                        readingPlanType = readingPlanType,
                    )
                    // State will be updated by the flow
                }
            }

            is DayUiEvent.OnEditReadDate -> {
                viewModelScope.launch {
                    updateDayReadTimestampUseCase(
                        weekNumber = weekNumber,
                        dayNumber = dayNumber,
                        readTimestamp = event.timestamp,
                    )
                    // State will be updated by the flow
                }
            }

            is DayUiEvent.OnBackClick -> {
                viewModelScope.launch {
                    _backUiAction.emit(Unit)
                }
            }
        }
    }
}
