/**
 * Default permissions when unspecified.
 */
@CreatePermission(expression = "user is logged in")
@ReadPermission(expression = "any user")
@UpdatePermission(expression = "deny all")
@DeletePermission(expression = "deny all")
@SharePermission(expression = "deny all")
package com.dennismcwherter.elide.app.models;

import com.yahoo.elide.annotation.CreatePermission;
import com.yahoo.elide.annotation.DeletePermission;
import com.yahoo.elide.annotation.ReadPermission;
import com.yahoo.elide.annotation.SharePermission;
import com.yahoo.elide.annotation.UpdatePermission;
