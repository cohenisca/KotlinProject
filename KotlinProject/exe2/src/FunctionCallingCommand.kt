package exe2
import java.io.File

 var functionNamaLAbel=""
var returnAdressCount=0


class FunctionCallingCommand(var out_file: File) {

    fun buildFunctionDeclaration( functionNane:String, numOfLocals:String){
        //???
        functionNamaLAbel=functionNane
        out_file.appendText("("+functionNane+")")
        // 0 until numOfLocals.toInt()
        for(i in 1..numOfLocals.toInt())
        {
            ConvertToHack("push constant 0",out_file)
        }
    }

    fun buildCall(functionNane: String, numOfArgs:String){
        //push return address
        var temp="LabelReturnAddress_"+ returnAdressCount.toString()
        returnAdressCount++
        out_file.appendText("""


            @$temp
            D=A
            @SP
            A=M
            M=D
            @SP
            M=M+1
        """.trimIndent())
        storeSegmentFunction("LCL")
        storeSegmentFunction("ARG")
        storeSegmentFunction("THIS")
        storeSegmentFunction("THAT")
        out_file.appendText("""


            @$numOfArgs
            D=A
            @5
            D=D+A
            @SP
            D=M-D
            @ARG
            M=D
            @SP
            D=M
            @LCL
            M=D
            @$functionNane
            0;JMP
            ($temp)
        """.trimIndent())
    }

    fun storeSegmentFunction(segmentName:String){
        out_file.appendText("""


            @$segmentName
            D=M
            @SP
            A=M
            M=D
            @SP
            M=M+1
        """.trimIndent())
    }
    fun buildReturn(){
        out_file.appendText("""

            //return address

            @LCL
            D=M
            @5
            D=D-A
            A=D
            D=M
            @R14
            M=D

            //return value
            @SP
            A=M-1
            D=M
            @ARG
            A=M
            M=D


            //sp reposition

            @ARG
            D=M+1
            @SP
            M=D


            //that reposition
            @LCL
            D=M-1
            A=D
            D=M
            @THAT
            M=D


            //this reposition

            @LCL
            D=M
            @2
            D=D-A
            A=D
            D=M
            @THIS
            M=D

            //arg reposition
            @LCL
            D=M
            @3
            D=D-A
            A=D
            D=M
            @ARG
            M=D

            //lcl reposition
            @LCL
            D=M
            @4
            D=D-A
            A=D
            D=M
            @LCL
            M=D

            @R14
            A=M
            0;JMP
        """.trimIndent())
    }


}