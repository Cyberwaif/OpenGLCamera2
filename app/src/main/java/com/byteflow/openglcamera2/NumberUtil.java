package com.byteflow.openglcamera2;

public class NumberUtil
{
    private final static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    private final static char[] HEX_LOWER_CASE_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String dumpHexString(byte[] array) {
        if (array == null) return "(null)";
        return dumpHexString(array, 0, array.length);
    }

    public static String dumpHexString(byte[] array, int offset, int length)
    {
        if (array == null) return "(null)";
        StringBuilder result = new StringBuilder();

        byte[] line = new byte[16];
        int lineIndex = 0;

        result.append("\n0x");
        result.append(toHexString(offset));

        for (int i = offset ; i < offset + length ; i++)
        {
            if (lineIndex == 16)
            {
                result.append(" ");

                for (int j = 0 ; j < 16 ; j++)
                {
                    if (line[j] > ' ' && line[j] < '~')
                    {
                        result.append(new String(line, j, 1));
                    }
                    else
                    {
                        result.append(".");
                    }
                }

                result.append("\n0x");
                result.append(toHexString(i));
                lineIndex = 0;
            }

            byte b = array[i];
            result.append(" ");
            result.append(HEX_DIGITS[(b >>> 4) & 0x0F]);
            result.append(HEX_DIGITS[b & 0x0F]);

            line[lineIndex++] = b;
        }

        if (lineIndex != 16)
        {
            int count = (16 - lineIndex) * 3;
            count++;
            for (int i = 0 ; i < count ; i++)
            {
                result.append(" ");
            }

            for (int i = 0 ; i < lineIndex ; i++)
            {
                if (line[i] > ' ' && line[i] < '~')
                {
                    result.append(new String(line, i, 1));
                }
                else
                {
                    result.append(".");
                }
            }
        }

        return result.toString();
    }

    public static String toHexString(byte b)
    {
        return toHexString(toByteArray(b));
    }

    
    public static String toHexString(byte[] array)
    {
        return toHexString(array, 0, array.length, true);
    }

    
    public static String toHexString(byte[] array, boolean upperCase)
    {
        return toHexString(array, 0, array.length, upperCase);
    }

    
    public static String toHexString(byte[] array, int offset, int length)
    {
        return toHexString(array, offset, length, true);
    }

    public static String toHexString(byte[] array, int offset, int length, boolean upperCase)
    {
        char[] digits = upperCase ? HEX_DIGITS : HEX_LOWER_CASE_DIGITS;
        char[] buf = new char[length * 2];

        int bufIndex = 0;
        for (int i = offset ; i < offset + length; i++)
        {
            byte b = array[i];
            buf[bufIndex++] = digits[(b >>> 4) & 0x0F];
            buf[bufIndex++] = digits[b & 0x0F];
        }

        return new String(buf);
    }

    
    public static String toHexString(int i)
    {
        return toHexString(toByteArray(i));
    }

    public static byte[] toByteArray(byte b)
    {
        byte[] array = new byte[1];
        array[0] = b;
        return array;
    }

    public static byte[] toByteArray(int i)
    {
        byte[] array = new byte[4];

        array[3] = (byte)(i & 0xFF);
        array[2] = (byte)((i >> 8) & 0xFF);
        array[1] = (byte)((i >> 16) & 0xFF);
        array[0] = (byte)((i >> 24) & 0xFF);

        return array;
    }

    private static int toByte(char c)
    {
        if (c >= '0' && c <= '9') return (c - '0');
        if (c >= 'A' && c <= 'F') return (c - 'A' + 10);
        if (c >= 'a' && c <= 'f') return (c - 'a' + 10);

        throw new RuntimeException ("Invalid hex char '" + c + "'");
    }

    
    public static byte[] hexStringToByteArray(String hexString)
    {
        int length = hexString.length();
        byte[] buffer = new byte[length / 2];

        for (int i = 0 ; i < length ; i += 2)
        {
            buffer[i / 2] = (byte)((toByte(hexString.charAt(i)) << 4) | toByte(hexString.charAt(i+1)));
        }

        return buffer;
    }

    public static StringBuilder appendByteAsHex(StringBuilder sb, byte b, boolean upperCase) {
        char[] digits = upperCase ? HEX_DIGITS : HEX_LOWER_CASE_DIGITS;
        sb.append(digits[(b >> 4) & 0xf]);
        sb.append(digits[b & 0xf]);
        return sb;
    }


    /**
     * int到byte[] 由高位到低位
     * @param i 需要转换为byte数组的整行值。
     * @return byte数组
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }

    /**
     * byte[]转int
     * @param bytes 需要转换成int的数组
     * @return int值
     */
    public static int byteArrayToInt(byte[] bytes) {
        int value=0;
        for(int i = 0; i < 4; i++) {
            int shift= (3-i) * 8;
            value +=(bytes[i] & 0xFF) << shift;
        }
        return value;
    }
    public static int convertByteToInt(byte data) {
        int heightBit = (int) ((data >> 4) & 0x0F);
        int lowBit = (int) (0x0F & data);
        return heightBit * 16 + lowBit;
    }
}
