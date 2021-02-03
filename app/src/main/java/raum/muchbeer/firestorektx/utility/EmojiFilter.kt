package raum.muchbeer.firestorektx.utility

import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.widget.Toast

class EmojiFilter : InputFilter {

    private companion object {
        private const val TAG = "EmojiFilter"
    }
    override fun filter(source: CharSequence?, p1: Int, p2: Int, p3: Spanned?, p4: Int, p5: Int ):
            CharSequence {
      if (source==null || source.isEmpty()) {
          Log.d(TAG, "blank list is provided")
        return ""
      }

        val validCharType = listOf(Character.SURROGATE, Character.NON_SPACING_MARK,
                                     Character.OTHER_SYMBOL).map {
            it.toInt()
        }
        for (inputCharacter in source ) {
            val type = Character.getType(inputCharacter)
            Log.i(TAG, "Input type is: ${type}")
            if (!validCharType.contains(type)) {
              //  Toast.makeText(this@Activity, "Only emoji is allowed", Toast.LENGTH_LONG).show
                Log.d(TAG, "Only emoji is allowed")
            }
        }
        Log.d(TAG, "Length of the source is : ${source.length}")
        return source
    }
}