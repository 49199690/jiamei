<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/page/layout/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en" class="app">
<head>

</head>
<body class="">

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <header class="panel-heading">
                <span class="font-bold">修改密码</span>
            </header>
            <div class="row">
                <div class="col-sm-2">
                </div>
                <div class="col-sm-8">
                    <div>
                        <span class="text-success">${successMessage}</span>
                        <span class="text-danger">${errorMessage}</span>
                    </div>
                    <form class="form-horizontal" data-validate="parsley" action="${ctx}/admin/changePassword" method="POST" onsubmit="return changePassword()">
                        <section class="panel panel-default">
                            <header class="panel-heading">
                                <strong>请填写如下信息：</strong>
                            </header>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="text-danger">*</span>原始密码：</label>
                                    <div class="col-sm-9">
                                        <input type="password" class="form-control" data-required="true" name="oldPassword" id="oldPwd" onblur="checkPassword(this.value, 'oldPasswordError')">
                                        <span id="oldPasswordError" class="text-danger"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="text-danger">*</span>新密码：</label>
                                    <div class="col-sm-9">
                                        <input type="password" class="form-control" data-required="true" name="newPassword" id="pwd" onblur="checkPassword(this.value, 'passwordError')">
                                        <span id="passwordError" class="text-danger"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="text-danger">*</span>确认密码：</label>
                                    <div class="col-sm-9">
                                        <input type="password" class="form-control" data-required="true" name="newPassword2" id="pwd2" data-equalto="#pwd">
                                    </div>
                                </div>
                            </div>
                            <footer class="panel-footer text-right bg-light lter">
                                <button type="submit" class="btn btn-success btn-s-xs">提 交</button>
                            </footer>
                        </section>
                    </form>

                </div>
                <div class="col-sm-2">
                </div>
            </div>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
</section>

<script type="text/javascript">
    //验证密码合法性
    function checkPassword(userPassword, errorId){
        var passwordError = document.getElementById(errorId);
        passwordError.innerText = "";
        var pwdRes = /^[A-Za-z0-9_#@]{6,16}$/;
        //var pwdRes = /((?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$)){8,}/;
        if(!pwdRes.test(userPassword)){
            passwordError.innerText = "该项只能输入字母、数字及特殊符号（_#@），且只能输入6-16位";
            return false;
        }

        return true;
    }

    //修改密码
    function changePassword(){
        //验证原始密码
        var oldPwdValid = checkPassword(document.getElementById("oldPwd").value, "oldPasswordError");
        //验证新密码
        var pwdValid = checkPassword(document.getElementById("pwd").value, "passwordError");
        if(!oldPwdValid || !pwdValid){
            return false;
        }
        return true;
    }
</script>
</body>
</html>