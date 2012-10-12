<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="app_context" value="/tccusjt-sed/" />
<link rel="stylesheet" href="${app_context}css/bootstrap.css" type="text/css" />
<script charset="UTF-8" src="${app_context}js/jquery.js"></script>
<script charset="UTF-8" src="${app_context}js/jquery-ui.js"></script>
<script src="${app_context}js/script.js"></script>
<script charset="UTF-8" src="${app_context}js/meiomask.js"></script>
<script charset="UTF-8" src="${app_context}js/bootstrap-dropdown.js"></script>
<script charset="UTF-8" src="${app_context}js/bootstrap-tab.js"></script>
<script charset="UTF-8" src="${app_context}js/bootstrap-button.js"></script>
<script src="${app_context}js/bootstrap-datepicker.js"></script>
<script src="https://apis.google.com/js/client.js?onload=OnLoadCallback"></script>
<link rel="stylesheet" href="${app_context}css/datepicker.css" type="text/css" />
<script charset="UTF-8" src="${app_context}js/bootstrap-modal.js"></script>
<link rel="stylesheet" href="${app_context}css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${app_context}css/style.css" type="text/css" />
<script src="${app_context}js/json2.js"></script>

<script type="text/javascript">
$(document).ready(function() {
$('input[type="text"]').setMask();
$('.dropdown-toggle').dropdown();
String.prototype.format = function (args) {
    var newStr = this;
    for (var key in args) {
        newStr = newStr.replace('{' + key + '}', args[key]);
        while(newStr.indexOf('{' + key + '}') != -1)
        {
        	newStr = newStr.replace('{' + key + '}', args[key]);
        }
    }
    return newStr;
}
$('.datepicker').datepicker();
});
</script>