package exe4

import java.io.File


var countOfTabs:Int=0
class ProgramStructure(parse_file: File, tokens_file: File) : Parsing(parse_file, tokens_file) {

    fun buildClass(){
        parse_file.appendText("""
            <class>

        """.trimIndent())
        countOfTabs++
        // class+ class name + {
        Parsing(parse_file, tokens_file).writeTokensTOFile(3)
        buildClassVarDec()
        buildSubroutineDec()
        Parsing(parse_file, tokens_file).writeTokensTOFile(1)
        countOfTabs--
        parse_file.appendText("</class>\n")


    }

    private fun buildSubroutineDec() {
        while (index<Parsing(parse_file, tokens_file).tokensOfFile.lastIndex &&(Parsing(parse_file, tokens_file).valueOfToken()in arrayOf("constructor","function","method"))){
            printTabs()
            parse_file.appendText("<subroutineDec>\n")
            countOfTabs++
            Parsing(parse_file, tokens_file).writeTokensTOFile(1)//subroutine declaration
            buildType()
            Parsing(parse_file, tokens_file).writeTokensTOFile(2)//subroutine name + (
            buildParameterList()
            Parsing(parse_file, tokens_file).writeTokensTOFile(1)// )
            buildSubroutineBody()
            countOfTabs--
            printTabs()
            parse_file.appendText("</subroutineDec>\n")

        }
    }
    private fun buildClassVarDec() {
        while (index<Parsing(parse_file, tokens_file).tokensOfFile.lastIndex && (Parsing(parse_file, tokens_file).valueOfToken()=="static" || Parsing(parse_file, tokens_file).valueOfToken()=="field")){
            printTabs()
            parse_file.appendText("""
                <classVarDec>

            """.trimIndent())
            countOfTabs++
            Parsing(parse_file, tokens_file).writeTokensTOFile(3)// static|field + type + name
            while (index<Parsing(parse_file, tokens_file).tokensOfFile.lastIndex && Parsing(parse_file, tokens_file).valueOfToken()==","){
                Parsing(parse_file, tokens_file).writeTokensTOFile(2)// , name
            }
            Parsing(parse_file, tokens_file).writeTokensTOFile(1)// ;
            countOfTabs--
            printTabs()
            parse_file.appendText("</classVarDec>\n")

        }
    }

    private fun buildSubroutineBody() {
        printTabs()
        parse_file.appendText("<subroutineBody>\n")
        countOfTabs++
        Parsing(parse_file, tokens_file).writeTokensTOFile(1)//{
        while (index<Parsing(parse_file, tokens_file).tokensOfFile.lastIndex && Parsing(parse_file, tokens_file).valueOfToken()=="var"){
            buildVarDec()
        }
        Statements(parse_file,tokens_file).buildStatements()
        Parsing(parse_file, tokens_file).writeTokensTOFile(1)//}
        countOfTabs--
        printTabs()
        parse_file.appendText("</subroutineBody>\n")

    }

    private fun buildVarDec() {
        printTabs()
        parse_file.appendText("<varDec>\n")
        countOfTabs++
        Parsing(parse_file, tokens_file).writeTokensTOFile(1)//var
        buildType()
        Parsing(parse_file, tokens_file).writeTokensTOFile(1)//varName
        while (index<Parsing(parse_file, tokens_file).tokensOfFile.lastIndex && Parsing(parse_file, tokens_file).valueOfToken()==","){
            Parsing(parse_file, tokens_file).writeTokensTOFile(2)// , varName
        }
        Parsing(parse_file, tokens_file).writeTokensTOFile(1)//;
        countOfTabs--
        printTabs()
        parse_file.appendText("</varDec>\n")


    }

    private fun buildParameterList() {
        printTabs()
        parse_file.appendText("<parameterList>\n")
        countOfTabs++
        if(index<Parsing(parse_file, tokens_file).tokensOfFile.lastIndex && Parsing(parse_file, tokens_file).valueOfToken()!=")"){
            buildType()
            Parsing(parse_file, tokens_file).writeTokensTOFile(1)//var Name
            while (index<Parsing(parse_file, tokens_file).tokensOfFile.lastIndex&& Parsing(parse_file, tokens_file).valueOfToken()==","){
                Parsing(parse_file, tokens_file).writeTokensTOFile(1)// ,
                buildType()
                Parsing(parse_file, tokens_file).writeTokensTOFile(1)//varName
            }
        }
        countOfTabs--
        printTabs()
        parse_file.appendText("</parameterList>\n")

    }

    private fun buildType() {
        Parsing(parse_file, tokens_file).writeTokensTOFile(1)//type
    }


}