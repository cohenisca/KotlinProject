package exe4
import java.io.File

//rivky kremer  208817585
//isca cohen 316179175

fun main(args: Array<String>) {
    var pathDir="C:\\Users\\Nurit\\Downloads\\Exercises\\Targil2\\project 08\\ProgramFlow\\BasicLoop9"
    var newFile:File



    File(pathDir).walk()
            .forEach {
                if(it.extension=="jack") {
                    var fileName=File(it.name).nameWithoutExtension
                    newFile= File(pathDir+"\\"+fileName.toString() +"T.xml")
                    if(newFile.exists()){
                        newFile.delete()
                    }
                    newFile.appendText("""

                        <tokens>

                    """.trimIndent())
                    it.forEachLine {
                        //check what happens if there is a note in the middle of a line
                        if(!it.startsWith("//") && !it.startsWith("/**")&& !it.startsWith("*") && !it.endsWith("*/") && it.length>0){
                            if(it.contains("//")){
                                Tokenizing(newFile,it.substringBefore("//").toCharArray())
                            }
                            else if(it.contains("/**")and it.contains("*/")){
                                Tokenizing(newFile,it.substringBefore("/**").toCharArray())
                            }
                            else
                                Tokenizing(newFile,it.toCharArray())

                            //ConvertToHack(it, newFile)
                        }
                    }
                    newFile.appendText("""

                        </tokens>

                    """.trimIndent())
                }
            }
}

