package exe4

//public class Token {
//    var tokenType:TokenTypes
//    var value:String
 //       get() = this.toString()
 //       set(v) {
 //           value=v // parses the string and assigns values to other properties
//        }
 //   init {
 //       tokenType=TokenTypes.IDENTIFIER
 //       value=""
 //   }

//}


data class Token(var t:TokenTypes,var v:String){}
