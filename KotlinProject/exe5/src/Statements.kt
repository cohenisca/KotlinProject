package exe5
import java.io.File

class Statements(parse_file: File, tokens_file: File) : Parsing(parse_file, tokens_file) {
    fun buildStatements() {
        printTabs()
        parse_file.appendText("<statements>\n")
        countOfTabs++
        while (index <tokensOfFile.lastIndex && valueOfToken()!="}"){
            buildStatement()
        }
        countOfTabs--
        printTabs()
        parse_file.appendText("</statements>\n")


    }

    private fun buildStatement() {
        if (index <tokensOfFile.lastIndex){
            when(valueOfToken()){
                "let"->buildLetStatement()
                "if"->buildIfStatement()
                "while"->buildWhileStatement()
                "do"->buildDoStatement()
                "return"->buildReturnStatement()
            }
        }
    }

    private fun buildReturnStatement() {
        printTabs()
        parse_file.appendText("<returnStatement>\n")
        countOfTabs++
        verifyAndNextToken(1)// return
        if (index <tokensOfFile.lastIndex && valueOfToken()!=";"){
            Expressions(parse_file, tokens_file).buildExpression()
        }
        verifyAndNextToken(1)// ;
        countOfTabs--
        printTabs()
        parse_file.appendText("</returnStatement>\n")

    }

    private fun buildDoStatement() {
        printTabs()
        parse_file.appendText("<doStatement>\n")
        countOfTabs++
        verifyAndNextToken(1)//do
        Expressions(parse_file, tokens_file).buildSubroutineCall()
        verifyAndNextToken(1)// ;
        countOfTabs--
        printTabs()
        parse_file.appendText("</doStatement>\n")

    }

    private fun buildWhileStatement() {
        printTabs()
        parse_file.appendText("<whileStatement>\n")
        countOfTabs++
        verifyAndNextToken(2)// while (
        Expressions(parse_file, tokens_file).buildExpression()
        verifyAndNextToken(2)// ) {
        buildStatements()
        verifyAndNextToken(1)//}
        countOfTabs--
        printTabs()
        parse_file.appendText("</whileStatement>\n")

    }

    private fun buildIfStatement() {
        printTabs()
        parse_file.appendText("<ifStatement>\n")
        countOfTabs++
        verifyAndNextToken(2)// if (
        Expressions(parse_file, tokens_file).buildExpression()
        verifyAndNextToken(2)// ) {
        buildStatements()
        verifyAndNextToken(1)// }
        if(index <tokensOfFile.lastIndex && valueOfToken()=="else"){
            verifyAndNextToken(2)//else {
            buildStatements()
            verifyAndNextToken(1)//}
        }
        countOfTabs--
        printTabs()
        parse_file.appendText("</ifStatement>\n")

    }

    private fun buildLetStatement() {
        printTabs()
        parse_file.appendText("<letStatement>\n")
        countOfTabs++
        verifyAndNextToken(2)// let varName
        if (index <tokensOfFile.lastIndex && valueOfToken()=="["){
            verifyAndNextToken(1)//[
            Expressions(parse_file, tokens_file).buildExpression()
            verifyAndNextToken(1)// ]
        }
        verifyAndNextToken(1)// =
        Expressions(parse_file, tokens_file).buildExpression()
        verifyAndNextToken(1)// ;
        countOfTabs--
        printTabs()
        parse_file.appendText("</letStatement>\n")

    }
}