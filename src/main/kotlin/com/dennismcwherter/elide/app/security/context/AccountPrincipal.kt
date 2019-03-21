package com.dennismcwherter.elide.app.security.context

import java.security.Principal
import javax.security.auth.Subject

/**
 * Account principal
 */
class AccountPrincipal(private val _name: String, val loggedIn: Boolean) : Principal {

    override fun getName() = _name

    override fun implies(subject: Subject?) = false
}
