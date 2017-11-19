package com.dennismcwherter.elide.app

import com.yahoo.elide.standalone.ElideStandalone

fun main(args: Array<String>) {
    // Configure and start Elide
    val elide = ElideStandalone(ServiceSettings())
    elide.start()
}
