package com.dennismcwherter.elide.app.security.context

import java.security.Principal
import javax.ws.rs.core.SecurityContext

class ApplicationSecurityContext(val _isSecure : Boolean,
                                 isLoggedIn: Boolean,
                                 userName : String) : SecurityContext {
    val principal : AccountPrincipal = AccountPrincipal(userName, isLoggedIn)

    override fun isUserInRole(role: String?): Boolean {
        return false
    }

    override fun getAuthenticationScheme(): String {
        return "custom"
    }

    override fun getUserPrincipal(): Principal {
        return principal
    }

    override fun isSecure(): Boolean {
        return _isSecure
    }
}
