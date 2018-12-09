package exe1

import java.io.File

//rivky kremer  208817585
//isca cohen 316179175

//fun main(args: Array<String>){
//    var count:Int=1
//    var pathDir="C:\\Users\\Nurit\\Downloads\\nand2tetris\\projects\\07\\MemoryAccess\\BasicTest"
//    var newFile=File(pathDir+"\\newFile.asm")
//    File(pathDir).walk()
 //           .forEach {
//                if(it.extension=="vm") {
 //                   var index=it.name.indexOf(".")
 //                   newFile.appendText("//START of file: name of current file=:"+it.name.substring(0,index)+"\n")
  //                  it.forEachLine {
  //                      if(!it.startsWith("//") && it.length>0)
   //                         ConvertToHack(it, newFile)
   //                 }
  //                  newFile.appendText("//finish file:"+ it.name.substring(0,index)+"\n")

  //              }

 //           }
//}


fun main(args: Array<String>){
    var lst=("").toList()

    //val directoryNames: MutableList<String> = mutableListOf("\\MemoryAccess\\BasicTest", "\\MemoryAccess\\PointerTest", "\\MemoryAccess\\StaticTest","\\StackArithmetic\\SimpleAdd","\\StackArithmetic\\StackTest")
    //var pathDir="C:\\Users\\Nurit\\Downloads\\nand2tetris\\projects\\07\\StackArithmetic\\StackTest"
    var pathDir="C:\\Users\\Nurit\\Downloads\\nand2tetris\\projects\\07\\StackArithmetic\\StackTest"

    File(pathDir).walk()
            .forEach {
                if(it.extension=="vm") {
                    var index=it.name.indexOf(".")
                    var newFile=File(pathDir+"\\"+it.name.substring(0,index)+".asm")

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

        "neg" ->UnaryCommand(newFile).Neg()
        "not" ->UnaryCommand(newFile).Not()

        "eq" -> ComparisionCommand(newFile).Eq()
        "gt" -> ComparisionCommand(newFile).Gt()
        "lt" -> ComparisionCommand(newFile).Lt()




        else -> throw IllegalArgumentException(command + " is not a legal vm command")


    }
}



