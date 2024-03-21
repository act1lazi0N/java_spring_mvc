<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Delete User ${id}</title>

                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            </head>

            <body>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <h3>Delete user with id = ${id} </h3>
                            <div class="border-top my-4"></div>
                            <div class="alert alert-danger" role="alert">
                                Are you sure you want to delete this user?
                            </div>
                        </div>

                    </div>
                    <form:form method="post" action="/admin/user/delete" modelAttribute="newUser">
                        <div class="mb-3" style="display: none;">
                            <label class="form-label">Id:</label>
                            <form:input value="${id}" type="text" class="form-control" path="id" />
                        </div>
                        <button class="btn btn-outline-danger mt-3">
                            Delete
                        </button>
                    </form:form>
                </div>
            </body>

            </html>