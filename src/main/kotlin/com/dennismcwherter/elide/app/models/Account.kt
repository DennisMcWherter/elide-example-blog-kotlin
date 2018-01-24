package com.dennismcwherter.elide.app.models

import com.yahoo.elide.annotation.ComputedAttribute
import com.yahoo.elide.annotation.CreatePermission
import com.yahoo.elide.annotation.DeletePermission
import com.yahoo.elide.annotation.Exclude
import com.yahoo.elide.annotation.Include
import com.yahoo.elide.annotation.ReadPermission
import com.yahoo.elide.annotation.SharePermission
import com.yahoo.elide.annotation.UpdatePermission
import org.hibernate.envers.Audited
import org.mindrot.jbcrypt.BCrypt
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Transient

/**
 * User account model.
 */
@Entity
@Include(rootLevel = true)
@CreatePermission(expression = "any user")
@ReadPermission(expression = "user is accessing self OR entity is newly created")
@UpdatePermission(expression = "user is accessing self")
@DeletePermission(expression = "user is accessing self")
@SharePermission
@Audited
class Account {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @get:Column(nullable = false, unique = true)
    var name: String? = null

    // Exclude this field from the Elide API, but not from the datastore
    @get:Exclude
    @get:Column(nullable = false)
    var password: String? = null

    /*
     * No one should read this field back from the API.
     * Similarly, it's a transient computedattribute since it should
     * never be read from the datastore.
     */
    @get:Transient
    @get:ComputedAttribute
    @get:ReadPermission(expression = "deny all")
    var newPassword: String? = null
        set(plaintext) {
            password = BCrypt.hashpw(plaintext, BCrypt.gensalt())
        }
}
