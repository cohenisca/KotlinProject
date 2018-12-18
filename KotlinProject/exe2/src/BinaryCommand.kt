package exe2

import java.io.File


class BinaryCommand(var out_file: File) {
   //convert add/sub/and/or to hack
    fun Add() {
    buildBinaryOp("D=D+A\n")
    }
    fun Sub(){
     buildBinaryOp("D=A-D\n")
    }
    fun And(){
     buildBinaryOp("D=A&D\n")
    }
    fun Or(){
     buildBinaryOp("D=A|D\n")
    }



    //This function prepare the environment of the registers, pointers... for binary command and then run the requiresd command
    //opCmd is action line to execute
    fun buildBinaryOp(opCmd:String){
     out_file.appendText("@SP\n")
     //adress of second argument
     out_file.appendText("M=M-1\n")
     //A= adress of second argument
     out_file.appendText("A=M\n")
     //D=the value of second argument
     out_file.appendText("//D=the value of second argument \n")
     out_file.appendText("D=M\n")
     out_file.appendText("@SP\n")
     out_file.appendText("M=M-1\n")
     //A=0
     out_file.appendText("@SP\n")
     //A=adress of first argument
     out_file.appendText("A=M\n")
     //A=the value of first argument
     out_file.appendText("//A=the value of first argument \n")
     out_file.appendText("A=M\n")
     //the operation, D=result of the operation that we need to do
     out_file.appendText("//D=result \n")
     out_file.appendText(opCmd+"\n")
     //A=0
     out_file.appendText("@SP\n")
     //A=adress
     out_file.appendText("A=M\n")
     //insert the solution to the RAM
     out_file.appendText("//insert the solution to the RAM \n")
     out_file.appendText("M=D\n")
     out_file.appendText("@SP\n")
     //update the sdress of stack
     out_file.appendText("M=M+1\n\n\n")

    }

}