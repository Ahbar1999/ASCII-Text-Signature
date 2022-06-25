package signature
import java.io.File
import kotlin.math.abs

fun buildMapping(fileLines: List<String>): MutableList<MutableList<String>> {

    val fontSize: Int = fileLines[0].split(" ")[0].toInt()
    val numOfChars: Int = fileLines[0].split(" ")[1].toInt()

    val mapping = mutableListOf<MutableList<String>>()
    var skip: Boolean
    for (ch in 0 until numOfChars) {
        skip = true
        val font = mutableListOf<String>()
        for (line in 1..(fontSize+1)) {
            if (skip) {
                skip = false
                continue
            }
//            println(romanFile[line+ch*11])
            font.add(fileLines[line+ch*(fontSize+1)])
        }
        mapping.add(font)
//        println("\n")
    }

    return mapping
}

fun getIndex(diff: Int): Int {

    // capital letters start from 27th character so index = 26
    val capitalLettersOffset = 26
    val unicodeOffsetCapitalLetters = 7

    return if (diff >= 0) diff else 26 - abs(diff + unicodeOffsetCapitalLetters) + capitalLettersOffset - 1
}


fun generateFontedText(text: String, font: MutableList<MutableList<String>>, spacesInBetween: Int): MutableList<String> {

    val result = mutableListOf<String>()
    val fontSize = font[0].size

    for (line in 0 until fontSize) {
        val currentRow = mutableListOf<String>()
        for (ch in text) {
            if (ch == ' ') {
                currentRow.add(" ".repeat(spacesInBetween))
            } else {
                val idx = getIndex(ch - 'a')

                currentRow.add(font[idx][line])
            }
        }
//        println(currentRow.joinToString(separator = ""))
        result.add(currentRow.joinToString(separator = ""))
    }

    return result
}


fun main() {
    val romanFile = File("C://Users/Ahbar/Downloads/roman.txt").readLines()
    val mediumFile = File("C://Users/Ahbar/Downloads/medium.txt").readLines()

    val roman = buildMapping(romanFile)
    val medium = buildMapping(mediumFile)
    val borderFont = "88"
    val fullName = readln()
    val status = readln()
    val spacesInBetweenRoman = 10
    val spacesInBetweenMedium = 5
    val leftSideSpaces = 2
    val rightSideSpaces = 2
    val nameFonted = generateFontedText(fullName, roman, spacesInBetweenRoman)
    val statusFonted = generateFontedText(status, medium, spacesInBetweenMedium)

    val topBorder: String
    val bottomBorder: String
    val width: Int
    val drawName = mutableListOf<String>()
    val drawStatus = mutableListOf<String>()

    if (fullName.length > status.length) {
        // width in borderFont unit (2 chars wide)
        width = (nameFonted[0].length + 2*borderFont.length + leftSideSpaces + rightSideSpaces)
        topBorder = "8".repeat(width)
        bottomBorder = "8".repeat(width)

        for (row in nameFonted) {
            drawName.add(borderFont + " ".repeat(leftSideSpaces) + row + " ".repeat(rightSideSpaces) + borderFont)
        }

        //center the smaller text
        val statusOffset = (width - statusFonted[0].length) / 2
        val leftStatusSpace = " ".repeat(statusOffset - borderFont.length)
        val rightStatusSpace = " ".repeat(width - statusFonted[0].length - leftStatusSpace.length - 2*borderFont.length)
        for (row in statusFonted) {
            drawStatus.add(borderFont + leftStatusSpace + row + rightStatusSpace + borderFont)
        }
    } else {
        // width in borderFont unit (2 chars wide)
        width = (statusFonted[0].length + 2*borderFont.length + leftSideSpaces + rightSideSpaces)
        topBorder = "8".repeat(width)
        bottomBorder = "8".repeat(width)

        for (row in statusFonted) {
            drawStatus.add(borderFont + " ".repeat(leftSideSpaces) + row + " ".repeat(rightSideSpaces) + borderFont)
        }

        //center the smaller text
        val nameOffset = (width - nameFonted[0].length) / 2
        val leftNameSpace = " ".repeat(nameOffset - borderFont.length)
        val rightNameSpace = " ".repeat(width - nameFonted[0].length - leftNameSpace.length - 2*borderFont.length)
        for (row in nameFonted) {
            drawName.add(borderFont + leftNameSpace + row + rightNameSpace + borderFont)
        }
    }


//    draw
    println(topBorder)
    for (row in drawName) {
        println(row)
    }
    for (row in drawStatus) {
        println(row)
    }
    println(bottomBorder)
}
