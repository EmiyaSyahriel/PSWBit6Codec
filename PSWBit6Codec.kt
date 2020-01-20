import java.lang.Exception
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Modified Base64 Algorithm Object for Kotlin
 * @author PSW_Shizuna
 */
object PSWBit6Codec {
    fun encode(text : String) : String{
        var retval = ""
        val paddedText = text.padEnd(ceil(text.length/(3).toDouble() ).toInt() * 3, ' ')
        //println("Start Encoding with ${text.length} input (${paddedText.length} padded)")
        //val byteArray : ByteArray = paddedText.toByteArray()
        val maxIndex : Int = floor((paddedText.length - 1).toDouble() / (3).toDouble()).toInt()
        for(i : Int in 0 .. maxIndex){
            retval += encodeIndividual3byte(paddedText.substring(i*3, (i+1)*3))
        }

        //println("Encoded \"$text\" to \"$retval\"")

        return retval
    }

    /**
     * Decode Bit6+64 String (Modified Base64)
     * @author PSW_Shizuna
     * @return Decoded string
     * @param text multi-4 text
     **/
    fun decode(text : String) : String{
        var retval = ""
        //if(text.length % 4 != 0) throw Exception("Given BIT6 Format is invalid, ${text.length} is not mod of 4")
        val maxIndex : Int = floor((text.length - 1).toDouble()/4).toInt()
        for(i in 0 .. maxIndex){
            val index = i*4
            //println("Length : ${text.length} , currentIndex $index , maxIndex ${index+4}")
            retval += decodeIndividual4byte(text.substring(index, index+4))
        }

        //println("Decoded \"$text\" to \"$retval\"")
        return retval.trim()
    }

    /*############ HELPER FUNCTIONS #############*/

    // source : https://medium.com/@ssaurel/build-a-binary-converter-android-app-with-kotlin-ab565cf33860
    private fun binaryToString(binary:String) : String {
        if (!isBinary(binary))
            return "Not a binary value"

        val chars = CharArray(binary.length / 8)
        var i = 0

        while (i < binary.length) {
            val str = binary.substring(i, i + 8)
            val nb = Integer.parseInt(str, 2)
            chars[i / 8] = nb.toChar()
            i += 8
        }

        return String(chars)
    }

    private fun isBinary(txt:String?):Boolean {
        if (txt != null  &&  txt.length % 8 == 0) {
            for (c in txt.toCharArray()) {
                if (c != '0'  &&  c!= '1')
                    return false
            }
            return true
        }
        return false
    }

    private fun createBitPad(text : String, start : Int) : String{
        val retval = "01" + text.substring(start, start+6)
        //println("$text @ $start  \t--[createBitPad]-> $retval")
        return retval
    }

    private fun encodeIndividual3byte(text : String) : String{ // 3 -> 4
        if(text.length != 3) throw Exception("encodeIndividual3byte function only receive 3 byte string")
        val byteArray = text.toByteArray()
        var unEncodedBitData = ""
        var bitData = ""
        byteArray.forEach{ unEncodedBitData += it.toString(2).padStart(8,'0')}
        for(i in 0 .. 3) {
            bitData += createBitPad(unEncodedBitData, i*6)
        }
        return binaryToString(bitData)
    }

    /**
     * Multi4
     */
    private fun decodeIndividual4byte(text : String) : String{ // 4 -> 3
        if(text.length != 4) throw Exception("decodeIndividual4byte function only receive 4 byte string")
        val byteArray = text.toByteArray()
        var bitData = ""
        byteArray.forEach {
            bitData += it.toString(2).padStart(8,'0').substring(2,8)
        }
        return binaryToString(bitData)
    }
}