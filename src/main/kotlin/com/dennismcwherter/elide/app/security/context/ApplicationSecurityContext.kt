package com.dennismcwherter.elide.app.security.context

import javax.ws.rs.core.SecurityContext

class ApplicationSecurityContext(
    private val _isSecure: Boolean,
    isLoggedIn: Boolean,
    userName: String
) : SecurityContext {
    private val principal: AccountPrincipal = AccountPrincipal(userName, isLoggedIn)

    override fun isUserInRole(role: String?) = false

    override fun getAuthenticationScheme() = "custom"

    override fun getUserPrincipal() = principal

    override fun isSecure() = _isSecure
}
