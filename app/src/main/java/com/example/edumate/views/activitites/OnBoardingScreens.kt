package com.example.edumate.views.activitites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.edumate.ui.theme.EduMateTheme

class OnBoardingScreens : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                EduMateTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        PreviewFunction()
                    }
                }
            }
        }
@Preview(showBackground = true)
@Composable
fun PreviewFunction(){
    Surface(modifier = Modifier.fillMaxSize()) {
        OnBoarding()
        }
    }
}
