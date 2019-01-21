package exe5
import java.io.File
import javax.tools.Diagnostic


var countOfTabs:Int=0
var class_Name:String=""
class ProgramStructure(parse_file: File, tokens_file: File) : Parsing(parse_file, tokens_file) {

    fun buildClass(){
        classSymbolTable.clear()
        initCounters(counterClassSymbolTable)
        verifyAndNextToken(1)//class
        class_Name=valueOfToken()
        verifyAndNextToken(2)//class name+ {
        buildClassVarDec()
        buildSubroutineDec()
        verifyAndNextToken(1)//}



    }

    private fun buildSubroutineDec() {
        subroutineSymbolTable.clear()
        initCounters(countersubroutineSymbolTable)
        while (index < tokensOfFile.lastIndex &&(valueOfToken()in arrayOf("constructor","function","method"))){
            var functionType=valueOfToken()
            verifyAndNextToken(1)//subroutine declaration
            buildType()
            var functionName=valueOfToken()
            verifyAndNextToken(1)//subroutine name
            verifyAndNextToken(1)// + (
            if(functionType=="method") {
                subroutineSymbolTable.add(SymbolTable("this", class_Name, "argument", 0))
                updateCounters(countersubroutineSymbolTable,"argument")
            }
            buildParameterList()
            verifyAndNextToken(1)// )
            buildSubroutineBody(functionType,functionName)


        }
    }
    fun updateCounters(_list: ArrayList<HelpCounters>,Kind:String):Int{
        for(i in counterClassSymbolTable){
            if(i._Segment==Kind) {
                i._Index++
                 return (i._Index-1)
            }
        }
        return 0
    }
    private fun buildClassVarDec() {
        while (index < tokensOfFile.lastIndex && (valueOfToken()=="static" || valueOfToken()=="field")){
            var kind=valueOfToken()
            verifyAndNextToken(1)//static|field
            var offset=updateCounters(counterClassSymbolTable,kind)
            var Type=valueOfToken()
            verifyAndNextToken(1)//type
            var row=SymbolTable(valueOfToken(),Type,kind,offset)
            verifyAndNextToken(1)// varName
            classSymbolTable.add(row)
            while (index < tokensOfFile.lastIndex && valueOfToken()==","){
                verifyAndNextToken(1)// ,
                offset=updateCounters(counterClassSymbolTable,kind)
                row= SymbolTable(valueOfToken(),Type,kind,offset)
                verifyAndNextToken(1)//varName
                classSymbolTable.add(row)
            }
            verifyAndNextToken(1)// ;


        }
    }

    private fun buildSubroutineBody(function_type:String,function_name:String) {

        verifyAndNextToken(1)//{
        while (index < tokensOfFile.lastIndex && valueOfToken()=="var"){
            buildVarDec()
        }
        var n=0
        countersubroutineSymbolTable.forEach{
            if (it._Segment=="var")
                n=it._Index
        }
        parse_file.appendText("function "+ class_Name+"."+function_name+" "+n+"\n")

        when(function_type){
            "constructor"-> {
                var countOfField=0
                countersubroutineSymbolTable.forEach{if(it._Segment=="field")countOfField=it._Index}
                parse_file.appendText("""
                    push constant $countOfField
                    call Memory.alloc 1
                    pop pointer 0
                """.trimIndent())
            }
            "method"->{
                parse_file.appendText("""
                    push argument 0
                    pop pointer 0
                """.trimIndent())
            }
        }
        Statements(parse_file, tokens_file).buildStatements()
        verifyAndNextToken(1)//}


    }

    private fun buildVarDec() {

        verifyAndNextToken(1)//var
        var t=valueOfToken()
        verifyAndNextToken(1)//type
        var n=valueOfToken()
        verifyAndNextToken(1)//varName
        var offset=updateCounters(countersubroutineSymbolTable,"var")
        subroutineSymbolTable.add(SymbolTable(n,t,"var",offset))
        while (index < tokensOfFile.lastIndex && valueOfToken()==","){
            verifyAndNextToken(1)// ,
            n=valueOfToken()
            verifyAndNextToken(1)//varName
            offset=updateCounters(countersubroutineSymbolTable,"var")
            subroutineSymbolTable.add(SymbolTable(n,t,"var",offset))
        }
        verifyAndNextToken(1)//;


    }

    private fun buildParameterList() {

        if(index < tokensOfFile.lastIndex && valueOfToken()!=")"){
            var t=valueOfToken()
            verifyAndNextToken(1)//type
            var n=valueOfToken()
            verifyAndNextToken(1)//varName
            var offset=updateCounters(countersubroutineSymbolTable,"argument")
            subroutineSymbolTable.add(SymbolTable(n,t,"argument",offset))
            while (index < tokensOfFile.lastIndex&& valueOfToken()==","){
                verifyAndNextToken(1)// ,
                t=valueOfToken()
                verifyAndNextToken(1)//type
                n=valueOfToken()
                verifyAndNextToken(1)//var name
                offset=updateCounters(countersubroutineSymbolTable,"argument")
                subroutineSymbolTable.add(SymbolTable(n,t,"argument",offset))

            }
        }


    }

    private fun buildType() {
        verifyAndNextToken(1)//type
    }


}