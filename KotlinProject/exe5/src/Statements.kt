package exe5
import java.io.File

var whileLabelCounter=0
var ifLabelCounter=0
class Statements(parse_file: File, tokens_file: File) : Parsing(parse_file, tokens_file) {
    fun buildStatements() {
        while (index <tokensOfFile.lastIndex && valueOfToken()!="}"){
            buildStatement()
        }


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

        verifyAndNextToken(1)// return
        if (index <tokensOfFile.lastIndex && valueOfToken()!=";"){
            Expressions(parse_file, tokens_file).buildExpression()
            parse_file.appendText("return\n")
        }
        else
            parse_file.appendText("""
                push constant 0
                return
            """.trimIndent())
        verifyAndNextToken(1)// ;


    }

    private fun buildDoStatement() {
        verifyAndNextToken(1)//do
        Expressions(parse_file, tokens_file).buildSubroutineCall()
        verifyAndNextToken(1)// ;
        parse_file.appendText("pop temp 0\n")


    }

    private fun buildWhileStatement() {
        whileLabelCounter++
        verifyAndNextToken(2)// while (
        parse_file.appendText("label WHILE_EXP"+ whileLabelCounter+"\n")
        Expressions(parse_file, tokens_file).buildExpression()
        verifyAndNextToken(2)// ) {
        parse_file.appendText("""
            not
            if-goto WHILE_END$whileLabelCounter
        """.trimIndent())
        buildStatements()
        verifyAndNextToken(1)//}
        parse_file.appendText("""
            goto WHILE_EXP$whileLabelCounter
            label WHILE_END$whileLabelCounter
        """.trimIndent())

    }

    private fun buildIfStatement() {
        ifLabelCounter++
        verifyAndNextToken(2)// if (
        Expressions(parse_file, tokens_file).buildExpression()
        verifyAndNextToken(2)// ) {
        parse_file.appendText("""
            if-goto IF_TRUE$ifLabelCounter
            goto IF_FALSE$ifLabelCounter
            label IF_TRUE$ifLabelCounter
        """.trimIndent())
        buildStatements()
        verifyAndNextToken(1)// }
        if(index <tokensOfFile.lastIndex && valueOfToken()=="else"){
            parse_file.appendText("""
                goto IF_END$ifLabelCounter
                label IF_FALSE$ifLabelCounter
            """.trimIndent())
            verifyAndNextToken(2)//else {
            buildStatements()
            verifyAndNextToken(1)//}
            parse_file.appendText("label IF_END"+ ifLabelCounter+"\n")
        }
        else
            parse_file.appendText("label IF_FALSE")


    }

    private fun buildLetStatement() {

        verifyAndNextToken(1)// let
        var name=valueOfToken()
        verifyAndNextToken(1)//varName
        var row= subroutineSymbolTable.firstOrNull { it._name==name }
        if(row==null)
            row= classSymbolTable.firstOrNull { it._name==name }
        if (index <tokensOfFile.lastIndex && valueOfToken()=="["){
            verifyAndNextToken(1)//[
            Expressions(parse_file, tokens_file).buildExpression()
            verifyAndNextToken(1)// ]
            when (row!!._segment) {
                "var"->parse_file.appendText("push local ${row._index}")
                "argument"->parse_file.appendText("push argument ${row._index}")
                "field"->parse_file.appendText("push this ${row._index}")
                "static"->parse_file.appendText("push static ${row._index}")
            }
            parse_file.appendText("add"+"\n")
            verifyAndNextToken(1)//=
            Expressions(parse_file, tokens_file).buildExpression()
            parse_file.appendText("""
                pop temp 0
                pop pointer 0
                push temp 0
                pop that 0
            """.trimIndent())
            verifyAndNextToken(1)//;

        }
        else {
            verifyAndNextToken(1)// =
            Expressions(parse_file, tokens_file).buildExpression()
            verifyAndNextToken(1)// ;
            when (row!!._segment) {
                "var"->parse_file.appendText("push local ${row._index}")
                "argument"->parse_file.appendText("push argument ${row._index}")
                "field"->parse_file.appendText("push this ${row._index}")
                "static"->parse_file.appendText("push static ${row._index}")
            }

        }

    }
}