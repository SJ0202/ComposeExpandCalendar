package com.seongju.composeexpandcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.seongju.composeexpandcalendar.presentation.components.ExpandCalendar
import com.seongju.composeexpandcalendar.ui.theme.ComposeExpandCalendarTheme
import com.seongju.composeexpandcalendar.ui.theme.SpaceDefault
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExpandCalendarTheme {

                val currentDate: LocalDate = LocalDate.now()
                val expandState = remember { mutableStateOf(false) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
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
            }
        }
    }
}