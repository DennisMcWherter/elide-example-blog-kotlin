package com.dennismcwherter.elide.app.filters

import com.dennismcwherter.elide.app.models.Account
import com.dennismcwherter.elide.app.security.context.ApplicationSecurityContext
import com.yahoo.elide.core.DataStore
import com.yahoo.elide.datastores.hibernate5.HibernateStore
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject
import javax.ws.rs.WebApplicationException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter

/**
 * User authentication filter
 */
class UserAuthFilter : ContainerRequestFilter {

    @Inject var dataStore : DataStore? = null

    override fun filter(requestContext: ContainerRequestContext?) {
        requestContext?.let { ctx ->
            val user = ctx.getHeaderString("User") ?: ""
            val plaintext = ctx.getHeaderString("Password") ?: ""
            val isSecure = ctx.securityContext.isSecure

            var loggedIn = false
            var userName = ""

            if (!user.isEmpty()) {
                // Since we let Elide standalone bootstrap _everything_, we need to use it to get to our
                // transactions. Note, there are other ways to lazily evaluate this inside of Elide, but they
                // are less straightforward than just opening a short-lived DB connection to verify the user.
                dataStore?.let { ds ->
                    val hibernateStore = ds as HibernateStore
                    hibernateStore.beginReadTransaction().use { tx ->
                        // Within the open transaction, fetch a session
                        val session = hibernateStore.session
                        val hashedPassword = session.createQuery("select password from Account where name = :name")
                                .setParameter("name", user)
                                .uniqueResult() as String?
                        tx.commit(null)
                        hashedPassword?.let { hash ->
                            if (BCrypt.checkpw(plaintext, hash)) {
                                loggedIn = true
                                userName = user
                            }
                        }
                    }
                }
                // Credentials provided but invalid
                if (!loggedIn) {
                    throw WebApplicationException("Invalid login credentials.", 401)
                }
            }
            ctx.securityContext = ApplicationSecurityContext(isSecure, loggedIn, userName)
        }
    }
}
