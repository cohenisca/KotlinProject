package exe5
import java.io.File

val listOfOp= listOf<String>("+","-","*","/","&amp;","|","&lt;","&gt;","=")

class Expressions(parse_file: File, tokens_file: File) : Parsing(parse_file, tokens_file) {

    fun buildExpression() {
        printTabs()
        parse_file.appendText("<expression>\n")
        countOfTabs++
        buildTerm()
        while(index <tokensOfFile.lastIndex && valueOfToken() in listOfOp){
            verifyAndNextToken(1)// op
            buildTerm()
        }
        countOfTabs--
        printTabs()
        parse_file.appendText("</expression>\n")

    }

    private fun buildTerm() {
        printTabs()
        parse_file.appendText("<term>\n")
        countOfTabs++
        if (index <tokensOfFile.lastIndex){
            when(AllTokens[index].t){
                TokenTypes.integerConstant ->verifyAndNextToken(1)//integerConstant
                TokenTypes.stringConstant ->verifyAndNextToken(1)//string
                TokenTypes.keyword ->{
                    if ((AllTokens[index].v) in arrayOf("true","false","null","this"))
                        verifyAndNextToken(1)
                }//keyword}
                TokenTypes.symbol ->{
                    when(AllTokens[index].v) {
                        "-"->{
                            verifyAndNextToken(1)
                            buildTerm()
                        }
                        "~"->{
                            verifyAndNextToken(1)
                            buildTerm()
                        }
                        "("->{
                            verifyAndNextToken(1)//(
                            buildExpression()
                            verifyAndNextToken(1)//)
                        }
                    }
                }
                TokenTypes.identifier ->{
                    var d=0
                    if(index <tokensOfFile.lastIndex-1){
                        when(valueOfTokenByIndex(index +1)){
                            "["->{
                                d=1
                                verifyAndNextToken(2)//varName [
                                buildExpression()
                                verifyAndNextToken(1)//]
                            }
                            "("->{
                                d=1
                                buildSubroutineCall()
                            }
                            "."->{
                                d=1
                                buildSubroutineCall()
                            }
                            else-> {
                                d=1
                                verifyAndNextToken(1)
                            }//varName
                        }
                    }
                    if(d==0)
                        verifyAndNextToken(1)//varName
                }
            }

        }
        countOfTabs--
        printTabs()
        parse_file.appendText("</term>\n")

    }

    fun buildSubroutineCall() {
        //parse_file.appendText("<subroutineCall>\n")
        if (index <tokensOfFile.lastIndex-1 ){
            when(valueOfTokenByIndex(index +1)){
                "("->{
                    verifyAndNextToken(2)//subroutineName (
                    buildExpressionList()
                    verifyAndNextToken(1)//)
                }
                "."->{
                    verifyAndNextToken(4)// className| varName . subroutineName (
                    buildExpressionList()
                    verifyAndNextToken(1)//)
                }
            }
        }
        //parse_file.appendText("</subroutineCall>\n")
    }

    private fun buildExpressionList() {
        printTabs()
        parse_file.appendText("<expressionList>\n")
        countOfTabs++
        if(index <tokensOfFile.lastIndex && valueOfToken()!=")"){
            buildExpression()
            while(index <tokensOfFile.lastIndex && valueOfToken()==","){
                verifyAndNextToken(1)//,
                buildExpression()
            }
        }
        countOfTabs--
        printTabs()
        parse_file.appendText("</expressionList>\n")

    }
}

