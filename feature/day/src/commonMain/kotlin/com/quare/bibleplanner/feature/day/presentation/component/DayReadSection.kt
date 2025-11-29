package com.quare.bibleplanner.feature.day.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bibleplanner.feature.day.generated.resources.Res
import bibleplanner.feature.day.generated.resources.cancel
import bibleplanner.feature.day.generated.resources.completed_date
import bibleplanner.feature.day.generated.resources.edit
import bibleplanner.feature.day.generated.resources.mark_day_as_read
import bibleplanner.feature.day.generated.resources.next
import bibleplanner.feature.day.generated.resources.no_date_set
import bibleplanner.feature.day.generated.resources.ok
import bibleplanner.feature.day.generated.resources.select_time
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalTime::class, ExperimentalMaterial3Api::class)
@Composable
internal fun DayReadSection(
    isRead: Boolean,
    readTimestamp: Long?,
    onToggle: (Boolean) -> Unit,
    onEditDate: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var selectedLocalDate by remember { mutableStateOf<LocalDate?>(null) }

    val initialTimestamp = readTimestamp ?: Clock.System.now().toEpochMilliseconds()
    val initialDate = Instant.fromEpochMilliseconds(initialTimestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialTimestamp,
    )
    val timePickerState = rememberTimePickerState(
        initialHour = initialDate.hour,
        initialMinute = initialDate.minute,
        is24Hour = true,
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { dateMillis ->
                            // Extract date components directly from the selected date timestamp
                            // DatePicker's selectedDateMillis represents the selected date at midnight UTC
                            // We extract the date components (year, month, day) which are timezone-independent
                            @Suppress("DEPRECATION")
                            val dateTime = kotlinx.datetime.Instant.fromEpochMilliseconds(dateMillis)
                                .toLocalDateTime(TimeZone.UTC)
                            val localDate = LocalDate(
                                year = dateTime.year,
                                month = dateTime.month,
                                dayOfMonth = dateTime.dayOfMonth,
                            )
                            selectedDateMillis = dateMillis
                            selectedLocalDate = localDate
                            showDatePicker = false
                            showTimePicker = true
                        }
                    },
                ) {
                    Text(stringResource(Res.string.next))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(Res.string.cancel))
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            title = {
                Text(stringResource(Res.string.select_time))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedLocalDate?.let { localDate ->
                            // Combine date and time using the local date directly (not converted from UTC)
                            val timeZone = TimeZone.currentSystemDefault()
                            val startOfDay = localDate.atStartOfDayIn(timeZone)
                            val timeOffsetMillis = (
                                timePickerState.hour * 3600_000L +
                                    timePickerState.minute * 60_000L
                            )
                            val duration = timeOffsetMillis.milliseconds
                            val finalInstant = startOfDay + duration
                            onEditDate(finalInstant.toEpochMilliseconds())
                            showTimePicker = false
                            selectedDateMillis = null
                            selectedLocalDate = null
                        }
                    },
                ) {
                    Text(stringResource(Res.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text(stringResource(Res.string.cancel))
                }
            },
        ) {
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(),
            )
        }
    }

    Column(
        modifier = modifier.padding(vertical = 16.dp),
    ) {
        // Mark day as read toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.mark_day_as_read),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
            )
            Switch(
                checked = isRead,
                onCheckedChange = onToggle,
            )
        }

        // Completed date section - show when day is marked as read
        if (isRead) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Text(
                    text = stringResource(Res.string.completed_date),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Calendar",
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    Text(
                        text = readTimestamp?.let { formatReadDate(it) } ?: stringResource(Res.string.no_date_set),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f),
                    )
                    TextButton(
                        onClick = {
                            showDatePicker = true
                        },
                    ) {
                        Text(stringResource(Res.string.edit))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
private fun formatReadDate(timestamp: Long): String {
    // Using kotlinx.datetime.Instant only for toLocalDateTime conversion
    @Suppress("DEPRECATION")
    val localDateTime = kotlinx.datetime.Instant.fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())
    // Format: "26 Nov 2025, 22:47"
    val day = localDateTime.day
    val month = localDateTime.month.name.take(3) // First 3 letters of month
    val year = localDateTime.year
    val hour = localDateTime.hour.toString().padStart(2, '0')
    val minute = localDateTime.minute.toString().padStart(2, '0')
    return "$day $month $year, $hour:$minute"
}
