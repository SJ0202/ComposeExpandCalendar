package com.seongju.composeexpandcalendar.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seongju.composeexpandcalendar.ui.theme.*
import java.time.LocalDate
import java.time.temporal.WeekFields

@Composable
fun ExpandCalendar(
    date: LocalDate,
    expandState: Boolean,
    onClick: () -> Unit
) {

    val dayList: List<String> = listOf("일", "월", "화", "수", "목", "금", "토")

    val monthCalendar = LocalDate.of(date.year, date.monthValue, 1)
    val weekOfMonthValue = date.get(WeekFields.SUNDAY_START.weekOfMonth())
    val weekOfMonth = (1-monthCalendar.dayOfWeek.value..monthCalendar.lengthOfMonth()).chunked(
        size = 7,
        transform = {
            it.toMutableList()
        }
    ).onEach {
        while (it.size < 7) it.add(0)
    }

    val iconButtonRotationAnimation by animateFloatAsState(
        targetValue = if (expandState) 180f else 0f
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = BoxRoundShapes)
            .background(Gray50)
            .padding(
                start = SpaceDefault,
                end = SpaceDefault,
                top = SpaceSmall,
                bottom = SpaceDefault
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .width(SpaceSmall)
                )
                Text(
                    text = "${monthCalendar.year} 년",
                    style = CustomTypography.caption1,
                    color = Gray600
                )
                Spacer(
                    modifier = Modifier
                        .width(SpaceSmall)
                )
                Text(
                    text = "${monthCalendar.monthValue} 월",
                    style = CustomTypography.headline
                )
                Spacer(
                    modifier = Modifier
                        .width(SpaceSmall)
                )
            } // Title
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = { onClick() }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ExpandMore,
                        contentDescription = "calendar expand icon button",
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .rotate(iconButtonRotationAnimation)
                    )
                }
            } // Expand icon button
        } // Top
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            dayList.forEach { date ->

                val dateColor = when(date) {
                    "일" -> SemanticNegative
                    "토" -> SemanticInfo
                    else -> Gray600
                }

                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date,
                        style = CustomTypography.caption1,
                        color = dateColor
                    )
                }
            }
        } // Date list
        Spacer(
            modifier = Modifier
                .height(SpaceSmall)
        )
        AnimatedVisibility(
            visible = !expandState,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                weekOfMonth[weekOfMonthValue-1].forEach {
                    val day = if (it > 0) it.toString() else ""
                    val dayBoxColor = if (it == date.dayOfMonth) SemanticInfo else Gray50
                    val dayTextColor = if (it == date.dayOfMonth) Color.White else Gray1000

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(BoxRoundShapes)
                            .background(dayBoxColor)
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day,
                            style = CustomTypography.caption1,
                            color = dayTextColor
                        )
                    }
                }
            }
        } // Week
        AnimatedVisibility(
            visible = expandState,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                weekOfMonth.forEach { weekList ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        weekList.forEach {
                            val day = if (it > 0) it.toString() else ""
                            val dayBoxColor = if (it == date.dayOfMonth) SemanticInfo else Gray50
                            val dayTextColor = if (it == date.dayOfMonth) Color.White else Gray1000

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(BoxRoundShapes)
                                    .background(dayBoxColor)
                                    .padding(5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = day,
                                    style = CustomTypography.caption1,
                                    color = dayTextColor
                                )
                            }
                        }
                    }
                }
            }
        } // Month
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandCalendarPreview(

){
    val currentDate: LocalDate = LocalDate.now()
    val expandState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(SpaceDefault)
    ) {
        ExpandCalendar(
            date = currentDate,
            expandState = expandState.value
        ) {
            expandState.value = !expandState.value
        }
    }
}