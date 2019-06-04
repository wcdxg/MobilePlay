import android.os.Environment
import java.io.File

/**
 *   Create by hanjun
 *   on 2019-06-4
 *   歌词加载类
 */
object LyricLoader {


    val dir = File(Environment.getExternalStorageDirectory(), "Download/Lyric")

    /**
     * 根据歌曲名，获取歌词文件
     */
    fun loadLyricFile(displayName: String): File {
        return File(dir, "$displayName.lrc")
    }

}