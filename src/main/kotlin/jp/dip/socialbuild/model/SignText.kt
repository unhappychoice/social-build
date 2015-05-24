package jp.dip.socialbuild.model

import org.bukkit.event.block.SignChangeEvent
import jp.dip.socialbuild.extension.darkAqua
import jp.dip.socialbuild.extension.green
import jp.dip.socialbuild.extension.blue
import jp.dip.socialbuild.repository.GoodRepository

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class SignText {
    class object {

        /**
         * make social build sign
         */
        public fun replaceSignText(e: SignChangeEvent) {
            e.setLine(0, titleLine())
            e.setLine(1, nameLine(e.getLine(1)))
            e.setLine(2, playerLine(e.getPlayer().getName()))
            e.setLine(3, goodLine(0))
        }

        public fun titleLine(): String {
            return "SocialBuild".blue()
        }

        public fun nameLine(name: String): String {
            return name.green()
        }

        public fun playerLine(playerName: String): String {
            return playerName
        }

        public fun goodLine(count: Int): String {
            return "good! : ${count}".darkAqua()
        }
    }
}