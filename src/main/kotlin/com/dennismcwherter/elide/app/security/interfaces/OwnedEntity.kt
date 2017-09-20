package com.dennismcwherter.elide.app.security.interfaces

import com.dennismcwherter.elide.app.models.Account

/**
 * Interface describing "account owned" objects.
 */
interface OwnedEntity {
    fun getOwningAccount() : Account?
}
