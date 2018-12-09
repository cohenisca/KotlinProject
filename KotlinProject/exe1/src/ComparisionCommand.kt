package exe1
import java.io.File



class ComparisionCommand(var out_file: File) {
     companion object {
         var count=0
     }
    //convert eq/gt/lt to hack
    fun Eq(){
        //if D and A equals, D will be 0 and this main the condition is true
        buildComparision("D=D-A","JEQ")
    }
    fun Gt(){
        //if A>D, the answer will be greater than 0 and therefore the condition is true
        buildComparision("D=A-D","JGT")
    }
    fun Lt(){
        //if A<D, the answer will be greater than 0 and therefore the condition is true
        buildComparision("D=D-A","JGT")
    }


    //This function prepare the environment of the registers, pointers... for binary comparation command and then run the requiresd command
    //opCmp is the operation that we do to determine the comparison result
    //jumpCmp is tupe of jump
    fun buildComparision(opCmp:String,jumpCmp:String){
        //create a new label for true/false condition and count=num last label
        var ifTrue="ifTrue_lable_"+count++.toString()
        var ifFalse="ifFalse_lable"+count++.toString()
        //A=0
        out_file.appendText("@SP\n")
        //the adress of second argument
        out_file.appendText("M=M-1\n")
        //A=adress of second argument
        out_file.appendText("A=M\n")
        //D=the value of second argument
        out_file.appendText("D=M\n")
        //A=0
        out_file.appendText("@SP\n")
        //the adress of first argument
        out_file.appendText("M=M-1\n")
        //A=adress of first argument
        out_file.appendText("A=M\n")
        //A=the value of first argument
        out_file.appendText("A=M\n")
        //the operation, D=result of the operation that we need to do
        out_file.appendText(opCmp+"\n")
        //A=label if the condition is true
        out_file.appendText("@"+ifTrue+"\n")
        //jump command
        out_file.appendText("D;"+jumpCmp+"\n")
        //A=label if the condition is false
        out_file.appendText("@"+ifFalse+"\n")
        //always is 0, this is main false
        out_file.appendText("D=0;JEQ\n")
        out_file.appendText("("+ifTrue+")\n")
        //D=true
        out_file.appendText("D=-1\n")
        out_file.appendText("("+ifFalse+")\n")
        //insert the solution to the RAM and update the stack
        out_file.appendText("@SP\n")
        out_file.appendText("A=M\n")
        out_file.appendText("M=D\n")
        out_file.appendText("@SP\n")
        out_file.appendText("M=M+1\n\n\n")

    }

}