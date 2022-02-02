package com.powilliam.mypackages.ui.validators

import javax.inject.Inject

private val letters: List<String> = listOf(
    "A",
    "B",
    "C",
    "D",
    "E",
    "F",
    "G",
    "H",
    "I",
    "J",
    "Q",
    "L",
    "M",
    "N",
    "O",
    "P",
    "Q",
    "R",
    "S",
    "T",
    "U",
    "V",
    "W",
    "Y",
    "X",
    "Z"
)

private val numbers: List<String> = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

class PackageValidator @Inject constructor() {
    fun hasValidName(name: String): ValidationResult = when {
        name.isEmpty() -> ValidationResult.InValid("O pacote deve possuir um nome")
        else -> ValidationResult.Valid
    }

    fun hasValidTracker(tracker: String): ValidationResult = when {
        tracker.length != 13 -> ValidationResult.InValid("O código de rastreio deve conter 13 caracteres")
        !tracker.substring(0..1)
            .all { letter -> letters.contains("$letter") } -> ValidationResult.InValid("Os dois primeiros caracteres devem ser letras")
        !tracker.substring(2..10)
            .all { number -> numbers.contains("$number") } -> ValidationResult.InValid("O código de rastreio deve conter 8 números após e antes dos primeiros e últimos dois caracteres")
        !tracker.substring(11..12)
            .all { letter -> letters.contains("$letter") } -> ValidationResult.InValid("Os dois últimos caracteres devem ser letras")
        else -> ValidationResult.Valid
    }
}