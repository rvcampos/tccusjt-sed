<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${nvl eq 'basico'}">
		<c:set var="nvName" value="chkBasic" />
		<c:set var="txtName" value="txtBasic" />
		<c:set var="idTxt" value="hrB" />
		<c:set var="domingoI" value="${txtBasicHoraInicioDomingo}" />
		<c:set var="domingoT" value="${txtBasicHoraTerminoDomingo}" />
		<c:set var="segundaI" value="${txtBasicHoraInicioSegunda}" />
		<c:set var="segundaT" value="${txtBasicHoraTerminoSegunda}" />
		<c:set var="tercaI" value="${txtBasicHoraInicioTerca}" />
		<c:set var="tercaT" value="${txtBasicHoraTerminoTerca}" />
		<c:set var="quartaI" value="${txtBasicHoraInicioQuarta}" />
		<c:set var="quartaT" value="${txtBasicHoraTerminoQuarta}" />
		<c:set var="quintaI" value="${txtBasicHoraInicioQuinta}" />
		<c:set var="quintaT" value="${txtBasicHoraTerminoQuinta}" />
		<c:set var="sextaI" value="${txtBasicHoraInicioSexta}" />
		<c:set var="sextaT" value="${txtBasicHoraTerminoSexta}" />
		<c:set var="sabadoI" value="${txtBasicHoraInicioSabado}" />
		<c:set var="sabadoT" value="${txtBasicHoraTerminoSabado}" />
	</c:when>
	<c:when test="${nvl eq 'intermediario'}">
		<c:set var="nvName" value="chkInt" />
		<c:set var="txtName" value="txtInt" />
		<c:set var="idTxt" value="hrI" />
		<c:set var="domingoI" value="${txtIntHoraInicioDomingo}" />
		<c:set var="domingoT" value="${txtIntHoraTerminoDomingo}" />
		<c:set var="segundaI" value="${txtIntHoraInicioSegunda}" />
		<c:set var="segundaT" value="${txtIntHoraTerminoSegunda}" />
		<c:set var="tercaI" value="${txtIntHoraInicioTerca}" />
		<c:set var="tercaT" value="${txtIntHoraTerminoTerca}" />
		<c:set var="quartaI" value="${txtIntHoraInicioQuarta}" />
		<c:set var="quartaT" value="${txtIntHoraTerminoQuarta}" />
		<c:set var="quintaI" value="${txtIntHoraInicioQuinta}" />
		<c:set var="quintaT" value="${txtIntHoraTerminoQuinta}" />
		<c:set var="sextaI" value="${txtIntHoraInicioSexta}" />
		<c:set var="sextaT" value="${txtIntHoraTerminoSexta}" />
		<c:set var="sabadoI" value="${txtIntHoraInicioSabado}" />
		<c:set var="sabadoT" value="${txtIntHoraTerminoSabado}" />
	</c:when>
	<c:when test="${nvl eq 'avancado'}">
		<c:set var="nvName" value="chkAdv" />
		<c:set var="txtName" value="txtAdv" />
		<c:set var="idTxt" value="hrA" />
		<c:set var="domingoI" value="${txtAdvHoraInicioDomingo}" />
		<c:set var="domingoT" value="${txtAdvHoraTerminoDomingo}" />
		<c:set var="segundaI" value="${txtAdvHoraInicioSegunda}" />
		<c:set var="segundaT" value="${txtAdvHoraTerminoSegunda}" />
		<c:set var="tercaI" value="${txtAdvHoraInicioTerca}" />
		<c:set var="tercaT" value="${txtAdvHoraTerminoTerca}" />
		<c:set var="quartaI" value="${txtAdvHoraInicioQuarta}" />
		<c:set var="quartaT" value="${txtAdvHoraTerminoQuarta}" />
		<c:set var="quintaI" value="${txtAdvHoraInicioQuinta}" />
		<c:set var="quintaT" value="${txtAdvHoraTerminoQuinta}" />
		<c:set var="sextaI" value="${txtAdvHoraInicioSexta}" />
		<c:set var="sextaT" value="${txtAdvHoraTerminoSexta}" />
		<c:set var="sabadoI" value="${txtAdvHoraInicioSabado}" />
		<c:set var="sabadoT" value="${txtAdvHoraTerminoSabado}" />
	</c:when>
</c:choose>
<c:set var="domingoD" value="disabled=\"disabled\""/>
<c:set var="segundaD" value="disabled=\"disabled\""/>
<c:set var="tercaD" value="disabled=\"disabled\""/>
<c:set var="quartaD" value="disabled=\"disabled\""/>
<c:set var="quintaD" value="disabled=\"disabled\""/>
<c:set var="sextaD" value="disabled=\"disabled\""/>
<c:set var="sabadoD" value="disabled=\"disabled\""/>
<c:if test="${not empty domingoI or not empty domingoT}">
	<c:set var="domingo" value="checked=\"checked\""/>
	<c:set var="domingoD" value="required=\"required\""/>
</c:if>
<c:if test="${not empty segundaI or not empty segundaT}">
	<c:set var="segunda" value="checked=\"checked\""/>
	<c:set var="segundaD" value="required=\"required\""/>
</c:if>
<c:if test="${not empty tercaI or not empty tercaT}">
	<c:set var="terca" value="checked=\"checked\""/>
	<c:set var="tercaD" value="required=\"required\""/>
</c:if>
<c:if test="${not empty quartaI or not empty quartaT}">
	<c:set var="quarta" value="checked=\"checked\""/>
	<c:set var="quartaD" value="required=\"required\""/>
</c:if>
<c:if test="${not empty quintaI or not empty quintaT}">
	<c:set var="quinta" value="checked=\"checked\""/>
	<c:set var="quintaD" value="required=\"required\""/>
</c:if>
<c:if test="${not empty sextaI or not empty sextaT}">
	<c:set var="sexta" value="checked=\"checked\""/>
	<c:set var="sextaD" value="required=\"required\""/>
</c:if>
<c:if test="${not empty sabadoI or not empty sabadoT}">
	<c:set var="sabado" value="checked=\"checked\""/>
	<c:set var="sabadoD" value="required=\"required\""/>
</c:if>
<div class="control-group">
	<label class="control-label" for="${nvName}Domingo">Domingo</label>
	<div class="controls">
		<input type="checkbox" id="${nvName}Domingo" name="${nvName}Domingo"
			onchange="enableTime(this,'${idTxt}DomingoI','${idTxt}DomingoT');" ${domingo}/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraInicioDomingo">Hora
		Inicio:</label>
	<div class="controls">
		<input type="text" id="${idTxt}DomingoI" alt="time"
			name="${txtName}HoraInicioDomingo" ${domingoD} alt="time" value="${domingoI}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraTerminoDomingo">Hora
		Termino:</label>
	<div class="controls">
		<input type="text" id="${idTxt}DomingoT" alt="time"
			name="${txtName}HoraTerminoDomingo" ${domingoD} alt="time" value="${domingoT}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${nvName}Segunda">Segunda-Feira</label>
	<div class="controls">
		<input type="checkbox" id="${nvName}Segunda" name="${nvName}Segunda"
			onchange="enableTime(this,'${idTxt}SegundaI','${idTxt}SegundaT');" ${segunda}/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="{txtName}HoraInicioSegunda">Hora Inicio:</label>
	<div class="controls">
		<input type="text" id="${idTxt}SegundaI" name="${txtName}HoraInicioSegunda"
			${segundaD} alt="time" value="${segundaI}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraTerminoSegunda">Hora Termino:</label>
	<div class="controls">
		<input type="text" id="${idTxt}SegundaT" name="${txtName}HoraTerminoSegunda"
			${segundaD} alt="time" value="${segundaT}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${nvName}Terca">Terça-Feira</label>
	<div class="controls">
		<input type="checkbox" id="${nvName}Terca" name="${nvName}Terca"
			onchange="enableTime(this,'${idTxt}TercaI','${idTxt}TercaT');" ${terca}/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraInicioTerca">Hora Inicio:</label>
	<div class="controls">
		<input type="text" id="${idTxt}TercaI" name="${txtName}HoraInicioTerca"
			${tercaD} alt="time" value="${tercaI}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraTerminoTerca">Hora Termino:</label>
	<div class="controls">
		<input type="text" id="${idTxt}TercaT" name="${txtName}HoraTerminoTerca"
			${tercaD} alt="time" value="${tercaT}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${nvName}Quarta">Quarta-Feira</label>
	<div class="controls">
		<input type="checkbox" id="${nvName}Quarta" name="${nvName}Quarta"
			onchange="enableTime(this,'${idTxt}QuartaI','${idTxt}QuartaT');" ${quarta}/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraInicioQuarta">Hora Inicio:</label>
	<div class="controls">
		<input type="text" id="${idTxt}QuartaI" name="${txtName}HoraInicioQuarta"
			${quartaD} alt="time" value="${quartaI}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraTerminoQuarta">Hora Termino:</label>
	<div class="controls">
		<input type="text" id="${idTxt}QuartaT" name="${txtName}HoraTerminoQuarta"
			${quartaD} alt="time" value="${quartaT}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${nvName}Quinta">Quinta-Feira</label>
	<div class="controls">
		<input type="checkbox" id="${nvName}Quinta" name="${nvName}Quinta"
			onchange="enableTime(this,'${idTxt}QuintaI','${idTxt}QuintaT');" ${quinta}/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraInicioQuinta">Hora Inicio:</label>
	<div class="controls">
		<input type="text" id="${idTxt}QuintaI" name="${txtName}HoraInicioQuinta"
			${quintaD} value="${quintaI}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraTerminoQuinta">Hora Termino:</label>
	<div class="controls">
		<input type="text" id="${idTxt}QuintaT" name="${txtName}HoraTerminoQuinta"
			${quintaD} value="${quintaT}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${nvName}Sexta">Sexta-Feira</label>
	<div class="controls">
		<input type="checkbox" id="${nvName}Sexta" name="${nvName}Sexta"
			onchange="enableTime(this,'${idTxt}SextaI','${idTxt}SextaT');" ${sexta} />
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraInicioSexta">Hora Inicio:</label>
	<div class="controls">
		<input type="text" id="${idTxt}SextaI" name="${txtName}HoraInicioSexta"
			${sextaD} value="${sextaI}"/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraTerminoSexta">Hora Termino:</label>
	<div class="controls">
		<input type="text" id="${idTxt}SextaT" name="${txtName}HoraTerminoSexta"
			${sextaD} value="${sextaT}" />
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${nvName}Sabado">Sábado</label>
	<div class="controls">
		<input type="checkbox" id="${nvName}Sabado" name="${nvName}Sabado"
			onchange="enableTime(this,'${idTxt}SabadoI','${idTxt}SabadoT');" ${sabado}/>
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraInicioSabado">Hora Inicio:</label>
	<div class="controls">
		<input type="text" id="${idTxt}SabadoI" name="${txtName}HoraInicioSabado"
			${sabadoD} value="${sabadoI}" />
	</div>
</div>
<div class="control-group">
	<label class="control-label" for="${txtName}HoraTerminoSabado">Hora Termino:</label>
	<div class="controls">
		<input type="text" id="${idTxt}SabadoT" name="${txtName}HoraTerminoSabado"
			${sabadoD} value="${sabadoT}"/>
	</div>
</div>
