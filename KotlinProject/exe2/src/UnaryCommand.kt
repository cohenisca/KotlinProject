package exe2
import java.io.File



class UnaryCommand(var out_file: File) {
    //convert neg/not to hack
    fun Neg(){
        buildUnaryOp("D=-M\n")
    }
    fun Not(){
        buildUnaryOp("D=!M\n")
    }


    //This function prepare the environment for unary command and then run the command.
    //opCmd is action line to execute
    fun buildUnaryOp(opCmd:String){
        //A=0
        out_file.appendText("@SP\n")
        //A=the adress of the argument
        out_file.appendText("""
            //A=the adress of the argument

        """.trimIndent())
        out_file.appendText("A=M-1\n")
        //D=the result
        out_file.appendText(opCmd+"\n")
        //result of the operation that we need to do and insert the solution to the RAM
        out_file.appendText("M=D\n\n\n")
    }
}