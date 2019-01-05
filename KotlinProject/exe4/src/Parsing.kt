package exe4
import java.io.File


var index:Int = 0

open class Parsing(var parse_file: File,var tokens_file:File) {
    var tokensOfFile:List<String>

    init {
        var temp_file=tokens_file

        tokensOfFile=temp_file.readLines()
        tokensOfFile-="<tokens>"
        tokensOfFile-="</tokens>"

    }


    fun getNextToken(): String {
        return tokensOfFile[index++]
    }

    fun valueOfToken(): String {
        return tokensOfFile[index].substringAfter(' ').substringBefore(' ')
        //return the value between > and <
    }
    fun valueOfTokenByIndex(num:Int): String {
        return tokensOfFile[num].substringAfter(' ').substringBefore(' ')
        //return the value between > and <
    }
    fun writeTokensTOFile(count:Int){

        for (i in 0..(count-1)) {
            for (i in 0..(countOfTabs-1))
                parse_file.appendText("  ")
            parse_file.appendText(getNextToken() + "\n")
        }

    }
    fun printTabs(){
        for (i in 0..(countOfTabs-1))
            parse_file.appendText("  ")
    }


}