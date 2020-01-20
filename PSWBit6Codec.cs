using System;
using System.Collections.Generic;
using System.Text;

/**
 * Modified Base64 Algorithm Object for C# - Originally for Kotlin
 * @author PSW_Shizuna
 */
public static class PSWBit6Codec
{

    public static string Encode(string text)
    {
        string retval = "";
        string paddedText = text.PadRight((int)Math.Ceiling(text.Length / 3d) * 3, ' ');

        Console.WriteLine($"Start encoding with {text.Length} input ({paddedText.Length} padded)");

        int maxIndex = (int)Math.Floor((paddedText.Length - 1) / 3d);
        
        for(int i = 0; i <= maxIndex; i++)
        {
            retval += EncodeIndividual3Byte(paddedText.Substring(i * 3, 3));
        }

        Console.WriteLine($"Encoded {text} to {retval}");

        return retval;
    }

    public static string Decode(string text)
    {
        string retval = "";
        if (text.Length % 4 != 0) throw new Exception($"Given BIT6 Format is invalid, {text.Length} is not mod of 4");
        int maxIndex = (int)Math.Floor((text.Length - 1) / 4d);
        for(int i = 0; i <= maxIndex; i++)
        {
            int index = i * 4;
            Console.WriteLine($"Length : {text.Length} , currentIndex {index} , maxIndex {index+4}");
            retval += DecodeIndividual4Byte(text.Substring(index, 4));
        }

        Console.WriteLine($"Encoded {text} to {retval}");

        return retval.Trim();
    }

    /*########## HELPER FUNCTIONS ##########*/
    private static string BinaryToString(string binary)
    {
        string retval = "";

        if (!binary.IsBinary()) throw new Exception(binary + "This is not a binary");

        char[] chars = new char[binary.Length / 8];
        int i = 0;
        while( i < binary.Length)
        {
            string str = binary.Substring(i, 8);
            int nb = Convert.ToInt32(str, 2);
            chars[i / 8] = Convert.ToChar(nb);
            i += 8;
        }

        retval = chars.ConcatChars();

        return retval;
    }

    public static string ConcatChars(this char[] chars)
    {
        string retval = "";
        foreach(char it in chars)
        {
            retval += it;
        }
        return retval;
    }

    public static bool IsBinary(this string txt)
    {
        if(txt != null && txt.Length % 8 == 0)
        {
            foreach(char c in txt.ToCharArray()) if (c != '0' && c != '1') return false;
            return true;
        }
        return false;
    }

    private static string CreateBitPad(string text, int start)
    {
        return "01" + text.Substring(start, 6);
    }

    private static string EncodeIndividual3Byte(string text)
    {
        if (text.Length != 3) throw new Exception(text+" is not an individual 3 byte!");
        byte[] byteArray = Encoding.ASCII.GetBytes(text);
        string unEncodedBitData = "";
        string bitData = "";
        foreach(byte it in byteArray) unEncodedBitData += Convert.ToString(it, 2).PadLeft(8, '0');
        for(int i = 0; i <= 3; i++)
        {
            bitData += CreateBitPad(unEncodedBitData, i * 6);
        }
        return BinaryToString(bitData);
    }

    private static string DecodeIndividual4Byte(string text)
    {
        if (text.Length != 4) throw new Exception("This is not a 4Byte notation");
        byte[] byteArray = Encoding.ASCII.GetBytes(text);
        string bitData = "";
        foreach(byte it in byteArray)
        {
            bitData += Convert.ToString(it, 2).PadLeft(8, '0').Substring(2, 6);
        }
        return BinaryToString(bitData);
    }
}
