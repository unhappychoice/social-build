package jp.dip.socialbuild.model

import jp.dip.socialbuild.repository.GoodRepository.GoodParams
import jp.dip.socialbuild.repository.GoodRepository
import jp.dip.socialbuild.extension.currentDate

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class Good(val params: GoodParams) {
    class object {
        public fun create(personId: String, signId: Int): Good {
            val params = GoodParams(
                    id = 0,
                    personId = personId,
                    signId = signId,
                    createdAt = currentDate(),
                    updatedAt = currentDate()
            )
            return Good(params)
        }

        public fun exists(personId: String, signId: Int): Boolean {
            return GoodRepository.where(personId, signId) != null
        }
    }

    public fun save(): Boolean {
        return GoodRepository.save(params)
    }
}