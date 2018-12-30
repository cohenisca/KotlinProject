package exe4

import java.io.File

class ProgramStructure(parse_file: File, tokens_file: File) : Parsing(parse_file, tokens_file) {

    fun buildClass(){
        parse_file.appendText("""
            <class>

        """.trimIndent())
        // class+ class name + {
        writeTOFile(3)
        buildClassVarDec()
        buildSubroutineDec()
        parse_file.appendText("""
            ${getNextToken()}
            </class>
        """.trimIndent())//}
    }

    private fun buildSubroutineDec() {
        while (index<tokensOfFile.lastIndex &&(valueOfToken()=="constructor"||valueOfToken()=="function"||valueOfToken()=="method")){
            parse_file.appendText("<subroutineDec>\n")
            writeTOFile(1)//subroutine declaration
            buildType()
            writeTOFile(2)//subroutine name + (
            buildParameterList()
            writeTOFile(1)// )
            buildSubroutineBody()
            parse_file.appendText("</subroutineDec>\n")
        }
    }
    private fun buildClassVarDec() {
        while (index<tokensOfFile.lastIndex && (valueOfToken()=="static" || valueOfToken()=="field")){
            parse_file.appendText("""
                <classVarDec>

            """.trimIndent())
            writeTOFile(3)// static|field + type + name
            while (index<tokensOfFile.lastIndex && valueOfToken()==","){
                writeTOFile(2)// , name
            }
            writeTOFile(1)// ;
            parse_file.appendText("</classVarDec>\n")
        }
    }

    private fun buildSubroutineBody() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun buildParameterList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun buildType() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}