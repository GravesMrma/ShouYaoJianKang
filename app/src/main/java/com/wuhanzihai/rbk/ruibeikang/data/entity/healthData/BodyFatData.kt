package com.wuhanzihai.rbk.ruibeikang.data.entity.healthData

data class BodyFatData(
        val code: Int,
        val details: Details,
        val msg: String,
        var time: String
)

data class Details(
        val ageOfBody: Int,
        val ageOfBodyRange: List<Double>,
        val bmi: Double,
        val bmiRange: List<Double>,
        val bmiWHORange: List<Double>,
        val bmr: Int,
        val bmrRange: List<Double>,
        val bodyShape: Int,
        val calcType: String,
        val desirableWeight: Double,
        val deviceInfo: DeviceInfo,
        val fatFreeBodyWeight: Double,
        val fatToControl: Double,
        val idealWeight: Double,
        val levelOfVisceralFat: Double,
        val levelOfVisceralFatRange: List<Double>,
        val muscleToControl: Double,
        val obesityLevel: Int,
        val rateOfBurnFat: RateOfBurnFat,
        val ratioOfFat: Double,
        val ratioOfFatRange: List<Double>,
        val ratioOfMuscle: Double,
        val ratioOfMuscleRange: List<Double>,
        val ratioOfProtein: Double,
        val ratioOfProteinRange: List<Double>,
        val ratioOfSkeletalMuscle: Double,
        val ratioOfSkeletalMuscleRange: List<Double>,
        val ratioOfSubcutaneousFat: Double,
        val ratioOfSubcutaneousFatRange: List<Double>,
        val ratioOfWater: Double,
        val ratioOfWaterRange: List<Double>,
        val score: Int,
        val sn: String,
        val stateOfNutrition: Int,
        val user: User,
        val weight: Double,
        val weightOfBone: Double,
        val weightOfBoneRange: List<Double>,
        val weightOfFat: Double,
        val weightOfFatRange: List<Double>,
        val weightOfMuscle: Double,
        val weightOfMuscleRange: List<Double>,
        val weightOfProtein: Double,
        val weightOfProteinRange: List<Double>,
        val weightOfSkeletalMuscle: Double,
        val weightOfSkeletalMuscleRange: List<Double>,
        val weightOfWater: Double,
        val weightOfWaterRange: List<Double>,
        val weightRange: List<Double>,
        val weightToControl: Double,
        val weightWHORange: List<Double>
)

data class DeviceInfo(
    val dataScale: Int,
    val deviceMac: String,
    val deviceSubType: Int,
    val deviceType: Int,
    val deviceVendor: Int
)

data class RateOfBurnFat(
    val max: Int,
    val min: Int
)

data class User(
    val age: Int,
    val gender: Int,
    val height: Int
)