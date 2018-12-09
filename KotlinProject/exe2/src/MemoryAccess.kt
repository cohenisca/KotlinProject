package exe2
import java.io.File

class MemoryAccess(var out_file:File,var segment:String,var offset:String) {
    //implementation of pop from stack
    fun Pop(){
        var loadSegment:String=""
        var calculateOffset:String=""

        //pop to nowhere
        if(segment=="constant"){
            out_file.appendText("@SP\n")
            //remove one organ from the stack
            out_file.appendText("M=M-1\n")
            return
        }

        when(segment){
            "local"->{
                loadSegment="@LCL"
                //D=RAM[A]+D
                calculateOffset="D=M+D"
            }
            "argument"->{
                loadSegment="@ARG"
                calculateOffset="D=M+D"
            }
            "this"->{
                loadSegment="@THIS"
                calculateOffset="D=M+D"
            }
            "that"->{
                loadSegment="@THAT"
                calculateOffset="D=M+D"
            }
            "static"->{
                loadSegment="@16"
                calculateOffset="D=A+D"
            }
            "pointer"->{
                loadSegment="@3"
                calculateOffset="D=A+D"
            }
            "temp"->{
                loadSegment="@5"
                calculateOffset="D=A+D"
            }
            else->throw IllegalArgumentException("'$segment' is not a legal VM segment")
        }

        out_file.appendText("@SP\n")
        //remove one organ from the stack
        out_file.appendText("M=M-1\n")
        //A=offset
        out_file.appendText("@"+offset+"\n")
        //D=offset
        out_file.appendText("D=A\n")
        //A=addres of segment that we want
        out_file.appendText(loadSegment+"\n")
        //D=the exact location where we want to put the value from the stack
        out_file.appendText(calculateOffset+"\n")
        //R13 will save this location
        out_file.appendText("@R13\n")
        out_file.appendText("M=D\n")
        //A=0
        out_file.appendText("@SP\n")
        out_file.appendText("A=M\n")
        //D=the value that we want to remove from the stack
        out_file.appendText("D=M\n")
        out_file.appendText("@R13\n")
        out_file.appendText("A=M\n")
        out_file.appendText("M=D\n\n\n")
    }

    //implementation of push to stack
    fun Push(){
        if(segment=="constant"){
            //A=offset
            out_file.appendText("@"+offset+"\n")
            //D=offset
            out_file.appendText("D=A\n")
            //A=0
            out_file.appendText("@SP\n")
            out_file.appendText("A=M\n")
            //insert to stack a parameter
            out_file.appendText("M=D\n")
            out_file.appendText("@SP\n")
            //update the SP
            out_file.appendText("M=M+1\n\n\n")

        }
        else {

            var loadSegment: String = ""
            var calculateOffset: String = ""

            when (segment) {
                "local" -> {
                    loadSegment = "@LCL"
                    calculateOffset = "A=M+D"
                }
                "argument" -> {
                    loadSegment = "@ARG"
                    calculateOffset = "A=M+D"
                }
                "this" -> {
                    loadSegment = "@THIS"
                    calculateOffset = "A=M+D"
                }
                "that" -> {
                    loadSegment = "@THAT"
                    calculateOffset = "A=M+D"
                }
                "static" -> {
                    loadSegment = "@16"
                    calculateOffset = "A=A+D"
                }
                "pointer" -> {
                    loadSegment = "@3"
                    calculateOffset = "A=A+D"
                }
                "temp" -> {
                    loadSegment = "@5"
                    calculateOffset = "A=A+D"
                }
                else -> throw IllegalArgumentException("'$segment' is not a legal VM segment")
            }

            //A=offset
            out_file.appendText("@" + offset + "\n")
            //D=offset
            out_file.appendText("D=A\n")
            //A=the segment that we want
            out_file.appendText(loadSegment + "\n")
            //A=the exact location where we want to take the value
            out_file.appendText(calculateOffset + "\n")
            //D=the value that we want to pun in a stack
            out_file.appendText("D=M\n")
            out_file.appendText("@SP\n")
            out_file.appendText("A=M\n")
            //insert the value to the stack
            out_file.appendText("M=D\n")
            out_file.appendText("@SP\n")
            //update SP
            out_file.appendText("M=M+1\n\n\n")
        }
    }




}