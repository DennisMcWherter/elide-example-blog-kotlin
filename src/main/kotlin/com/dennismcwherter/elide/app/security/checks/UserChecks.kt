package com.dennismcwherter.elide.app.security.checks

import com.dennismcwherter.elide.app.security.context.AccountPrincipal
import com.yahoo.elide.security.User
import com.yahoo.elide.security.checks.UserCheck

/**
 * Checks that operate only on the UserPrincipal.
 */
abstract class UserChecks {

    /**
     * Check if user is logged in.
     */
    class IsLoggedIn : UserCheck() {
        override fun ok(user: User?): Boolean {
            val principal: AccountPrincipal? = user?.opaqueUser as? AccountPrincipal
            return principal?.loggedIn ?: false
        }
    }
}
