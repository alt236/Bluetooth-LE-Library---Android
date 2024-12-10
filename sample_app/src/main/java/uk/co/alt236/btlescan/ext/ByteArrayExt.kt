package uk.co.alt236.btlescan.ext

object ByteArrayExt {
    fun ByteArray.toCharString(): String {
        val chars = ArrayList<Char>(this.size)

        for (byte in this) {
            if (byte in 0..31) {
                val unicode = (0x2400 + byte).toChar()
                chars.add(unicode)
            } else {
                chars.add(byte.toChar())
            }
        }
        return String(chars.toCharArray())
    }
}
