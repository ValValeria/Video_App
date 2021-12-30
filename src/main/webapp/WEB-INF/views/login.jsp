<!doctype html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/static/style.css">
</head>
<body>
<div class="col-lg-6 col-md-8 login-box">
    <div class="col-lg-12 login-title">
        LOGIN
    </div>

    <div class="col-lg-12 login-form">
        <div class="col-lg-12 login-form">
            <form method="post">
                <div class="form-group">
                    <label class="form-control-label">USERNAME</label>
                    <input type="text" name="username" class="form-control">
                </div>
                <div class="form-group">
                    <label class="form-control-label">PASSWORD</label>
                    <input type="password" name="password" class="form-control" >
                </div>

                <div class="w-100  loginbttm">
                    <div class="w-100 login-btm login-text mb-3">
                        <a href="/signup">
                            Don't have an account? Click here to sign up
                        </a>
                    </div>
                    <div class=" login-btm login-button w-100 d-flex justify-content-center">
                        <button type="submit" class="btn btn-outline-primary">SUBMIT</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="col-lg-3 col-md-2"></div>
</div>
</body>
</html>