package exe4
import java.io.File




open class Parsing(var parse_file: File,var tokens_file:File) {
    var tokensOfFile:List<String>
    var index:Int=0

    init {
        tokensOfFile=tokens_file.readLines()
        index=0
    }


    fun getNextToken(): String {
        return tokensOfFile[index++]
    }

    fun valueOfToken(): String {
        return tokensOfFile[index].substringAfter(' ').substringBefore(' ')
        //return the value between > and <
    }
    fun writeTOFile(count:Int){
        for (i in 0..count)
            parse_file.appendText(getNextToken()+"\n")
    }


}