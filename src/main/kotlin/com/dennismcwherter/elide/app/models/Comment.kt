package com.dennismcwherter.elide.app.models

import com.dennismcwherter.elide.app.security.interfaces.OwnedEntity
import com.yahoo.elide.annotation.ComputedAttribute
import com.yahoo.elide.annotation.DeletePermission
import com.yahoo.elide.annotation.Exclude
import com.yahoo.elide.annotation.Include
import com.yahoo.elide.annotation.UpdatePermission
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Transient
import org.hibernate.envers.Audited

@Entity
@Include
@Audited
@UpdatePermission(expression = "is accessed by owner at operation")
@DeletePermission(expression = "is accessed by owner at operation")
class Comment : OwnedEntity {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @get:Column(nullable = false)
    var content: String? = null

    @get:OneToOne(optional = false)
    var post: Post? = null

    @get:ManyToOne(optional = false)
    var owner: Account? = null

    /**
     * While we don't allow other users to read someone else's user object, we can use this to expose
     * the author's user name. This is one of many ways to do this.
     */
    var authorName: String? = null
        @Transient
        @ComputedAttribute
        get() = owner?.name

    @Transient
    @Exclude
    override fun getOwningAccount(): Account? = owner
}
