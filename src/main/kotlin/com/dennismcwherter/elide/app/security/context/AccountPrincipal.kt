package com.dennismcwherter.elide.app.security.context

import java.security.Principal
import javax.security.auth.Subject

/**
 * Account principal
 */
class AccountPrincipal(val _name: String, val loggedIn: Boolean) : Principal {

    override fun getName(): String {
        return _name
    }

    override fun implies(subject: Subject?): Boolean {
        return false
    }
}
