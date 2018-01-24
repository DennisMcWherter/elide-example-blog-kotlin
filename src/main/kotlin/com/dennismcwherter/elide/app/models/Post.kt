package com.dennismcwherter.elide.app.models

import com.dennismcwherter.elide.app.security.interfaces.OwnedEntity
import com.yahoo.elide.annotation.ComputedAttribute
import com.yahoo.elide.annotation.Exclude
import com.yahoo.elide.annotation.Include
import com.yahoo.elide.annotation.UpdatePermission
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Transient
import org.hibernate.envers.Audited

@Entity
@Audited
@Include(rootLevel = true)
@UpdatePermission(expression = "is accessed by owner at operation")
class Post : OwnedEntity {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @get:Column(nullable = false)
    var content: String? = null

    // NOTE: For simplicity, we enable for community moderation :)
    //       With a few more checks you could limit users to only removing their own comments, etc.
    @get:OneToMany(mappedBy = "post")
    @get:UpdatePermission(expression = "user is logged in")
    var comments: Set<Comment> = HashSet()

    @get:ManyToOne(optional = false)
    var owner: Account? = null

    /**
     * While we don't allow other users to read someone else's user object, we can use this to expose
     * the author's user name.
     */
    var authorName: String? = null
        @Transient
        @ComputedAttribute
        get() = owner?.name

    /**
     * Internal method to support generic security checks across this object.
     */
    @Transient
    @Exclude
    override fun getOwningAccount(): Account? = owner
}
