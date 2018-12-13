package exe2
import java.io.File
//hello shalom
fun main(args: Array<String>){
    var pathDir="C:\\Users\\Nurit\\Downloads\\Exercises\\Targil1\\project 07\\SimpleAdd"

    File(pathDir).walk()
            .forEach {
                if(it.extension=="vm") {
                    var index=it.name.indexOf(".")
                    var newFile= File(pathDir+"\\"+it.name.substring(0,index)+".asm")

                    it.forEachLine {
                        if(!it.startsWith("//") && it.length>0)
                            ConvertToHack(it, newFile)
                    }


                }

            }
}

//The function receives a line in the VM and translates it to HACK
fun ConvertToHack(file_line: String, newFile: File) {

    //This variable lineElements breaks the row into a list of words
    var lineElements = file_line.split(Regex("\\s"))
    //This variable command gets the first word of the line
    var command = lineElements[0]
    newFile.appendText("//"+file_line+"\n")//vm code in a command
    when(command){

    //lineElements[1] is segment like local or static and lineElements[2] is offset
        "push" -> MemoryAccess(newFile,lineElements[1], lineElements[2]).Push()
        "pop" -> MemoryAccess(newFile,lineElements[1], lineElements[2]).Pop()

        "add" -> BinaryCommand(newFile).Add()
        "sub" -> BinaryCommand(newFile).Sub()
        "and" -> BinaryCommand(newFile).And()
        "or" -> BinaryCommand(newFile).Or()

        "neg" -> UnaryCommand(newFile).Neg()
        "not" -> UnaryCommand(newFile).Not()

        "eq" -> ComparisionCommand(newFile).Eq()
        "gt" -> ComparisionCommand(newFile).Gt()
        "lt" -> ComparisionCommand(newFile).Lt()

        "label"->ProgramFlowCommand(newFile,lineElements[1]).buildLable()
        "goto"->ProgramFlowCommand(newFile,lineElements[1]).buildGoto()
        "if-goto"->ProgramFlowCommand(newFile,lineElements[1]).buildIfGoto()


        "function"->FunctionCallingCommand(newFile).buildFunctionDeclaration(lineElements[1],lineElements[2])
        "call"->FunctionCallingCommand(newFile).buildCall(lineElements[1],lineElements[2])
        "return"->FunctionCallingCommand(newFile).buildReturn()




        else -> throw IllegalArgumentException(command + " is not a legal vm command")


    }
}


