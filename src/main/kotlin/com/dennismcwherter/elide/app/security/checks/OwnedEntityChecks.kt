package com.dennismcwherter.elide.app.security.checks

import com.dennismcwherter.elide.app.security.context.AccountPrincipal
import com.dennismcwherter.elide.app.security.interfaces.OwnedEntity
import com.yahoo.elide.security.ChangeSpec
import com.yahoo.elide.security.RequestScope
import com.yahoo.elide.security.checks.CommitCheck
import com.yahoo.elide.security.checks.OperationCheck
import java.util.*

/**
 * Class operating on all owned entities.
 */
abstract class OwnedEntityChecks {

    abstract class IsAccessedByOwner {
        companion object {
            private fun isAccessedByOwner(entity: OwnedEntity?, requestScope: RequestScope?): Boolean =
                entity?.getOwningAccount()?.name?.let { name ->
                    val principal = requestScope?.user?.opaqueUser as? AccountPrincipal
                    name == principal?.name
                } ?: false
        }

        class AtCommit : CommitCheck<OwnedEntity>() {
            override fun ok(
                `object`: OwnedEntity?,
                requestScope: RequestScope?,
                changeSpec: Optional<ChangeSpec>?
            ): Boolean =
                isAccessedByOwner(`object`, requestScope)
        }

        class AtOperation : OperationCheck<OwnedEntity>() {
            override fun ok(
                `object`: OwnedEntity?,
                requestScope: RequestScope?,
                changeSpec: Optional<ChangeSpec>?
            ): Boolean =
                isAccessedByOwner(`object`, requestScope)
        }
    }
}
