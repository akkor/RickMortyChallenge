package com.acorpas.rickmortychallenge.utils.factory

import com.acorpas.rickmortychallenge.data.remote.model.InfoTO

class InfoToFactory {

    companion object {
        fun makeInfoToModel() : InfoTO {
            return InfoTO(count = 0,
                nextPage = "",
                pages = 0,
                prevPage = "")
        }
    }
}