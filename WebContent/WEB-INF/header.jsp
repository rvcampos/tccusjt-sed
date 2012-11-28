<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="app_context" value="/tccusjt-sed/" />
<link id="bootcss" rel="stylesheet" href="${app_context}css/bootstrap${layout}.css?t=1" type="text/css"/>
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
/* Compare the current date against another date.
*
* @param b  {Date} the other date
* @returns   -1 : if this < b
*             0 : if this === b
*             1 : if this > b
*            NaN : if a or b is an illegal date
*/ 
Date.prototype.compare = function(b) {
 if (b.constructor !== Date) {
   throw "invalid_date";
 }

return (isFinite(this.valueOf()) && isFinite(b.valueOf()) ? 
         (this>b)-(this<b) : NaN 
       );
};

/* Check if current date is between two dates
*
* @param a  {Date} the start date
* @param b  {Date} the end   date
* @returns    true : if this > a and this < b
*             false: if this < a or this > b
*            NaN : if a or b is an illegal date
*/ 
Date.prototype.between = function(a,b) {
	 if (a.constructor !== Date || b.constructor !== Date) {
	   throw "invalid_date";
	 }
	 return this.compare(a) == 1 && this.compare(b) == -1;
	};
$('.datepicker').datepicker();
});
</script>