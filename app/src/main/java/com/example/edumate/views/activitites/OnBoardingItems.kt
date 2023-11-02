package com.example.edumate.views.activitites

import com.example.edumate.R

class OnBoardingItems(
    val image: Int,
    val title: Int,
    val desc: Int
) {
    companion object{
        fun getData(): List<OnBoardingItems>{
            return listOf(
                OnBoardingItems(R.drawable.image1, R.string.tittle1, R.string.description1),
                OnBoardingItems(R.drawable.image2, R.string.tittle2, R.string.description2),
                OnBoardingItems(R.drawable.image3, R.string.tittle3, R.string.description3),
            )
        }
    }
}