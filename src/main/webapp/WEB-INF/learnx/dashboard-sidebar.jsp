<div class="col-xl-3">
    <!-- Responsive offcanvas body START -->
    <div class="offcanvas-xl offcanvas-end" tabindex="-1" id="offcanvasSidebar">
        <!-- Offcanvas header -->
        <div class="offcanvas-header bg-light">
            <button  type="button" class="btn-close" data-bs-dismiss="offcanvas" data-bs-target="#offcanvasSidebar" aria-label="Close"></button>
        </div>
        <!-- Offcanvas body -->
        <div class="offcanvas-body p-3 p-xl-0">
            <div class="bg-dark border rounded-3  p-3 w-100">
                <!-- Dashboard menu -->
                <div class="list-group list-group-dark list-group-borderless">
                    <c:if test = "${sessionScope.activeUser.privileges eq 'admin'}">
                    <a class="list-group-item" href="${appURL}/all-users"><i class="fas fa-user-pen me-2"></i>All Users</a>
                    <a class="list-group-item" href="${appURL}/all-courses"><i class="fas fa-user-pen me-2"></i>All Courses</a>
                        <a class="list-group-item" href="${appURL}/applications"><i class="fas fa-user-pen me-2"></i>All Applications</a>
                    </c:if>
                    <a class="list-group-item" href="${appURL}/edit-profile"><i class="fas fa-user-pen me-2"></i>Edit Profile</a>
                    <c:if test = "${sessionScope.activeUser.privileges eq 'student'}">
                    <a class="list-group-item" href="${appURL}/delete-account"><i class="bi bi-trash fa-fw me-2"></i>Delete Account</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <!-- Responsive offcanvas body END -->
</div>