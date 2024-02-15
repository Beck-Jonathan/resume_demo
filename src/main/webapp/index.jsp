<%@include file="WEB-INF/Shared/top.jsp"%>

    <title>Java 3 Web Applications</title>
    <link href="https://cdn.jsdelivr.net/npm/nprogress@0.2.0/nprogress.css" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/nprogress@0.2.0/nprogress.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/loading.css">
    <script src="js/loading.js"></script>
</head>
<body>
<div class="container py-4 text-center">
    <h1 class="my-4">Java 3 Web Applications</h1>
    <h5>${appURL}</h5>
    <div class="row justify-content-center" style="text-align: center;">
        <div class="col-xs-12 col-sm-6 col-lg-5 mb-4">
            <h3>Personal Projects</h3>
            <div class="list-group">
                <a href="email" class="list-group-item list-group-item-action">Email Sender</a>
                <a href="apply" class="list-group-item list-group-item-action">Personal Project</a>
                <a href="#" class="list-group-item list-group-item-action">Third link item</a>
                <a href="#" class="list-group-item list-group-item-action">Fourth link item</a>
            </div>
        </div>
        <div class="col-xs-12 col-sm-6 col-lg-5">
            <h3>Class Demos</h3>
            <div class="list-group">
                <a href="artist-json" class="list-group-item list-group-item-action">Artist Json</a>
                <a href="all-users" class="list-group-item list-group-item-action">allUsers</a>
                <a href="${appURL}/learnx" class="list-group-item list-group-item-action">LearnX CMS</a>
                <a href="#" class="list-group-item list-group-item-action">Fourth link item</a>
            </div>
        </div>
    </div>
</div>
<%@include file="WEB-INF/Shared/bottom.jsp"%>