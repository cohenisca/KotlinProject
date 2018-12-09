package exe2
import java.io.File

class ProgramFlowCommand(var out_file: File,var labelName:String) {

    fun buildLable(){
        out_file.appendText("("+ functionNamaLAbel+"."+labelName+")\n")
    }

    fun buildGoto(){
        out_file.appendText("""
            @$functionNamaLAbel.$labelName
            0;JMP
        """.trimIndent())
    }

    fun buildIfGoto(){
        out_file.appendText("""
            @SP
            M=M-1
            A=M
            D=M
            @$functionNamaLAbel.$labelName
            D;JNE
        """.trimIndent())
    }
}