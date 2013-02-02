<%@ include file="../init.jspf"%>


<div class="moli-page-navigation">

    <div class="left-page">
        <c:if test="${leftPage != null}">
            Previous<br/>
            <a href="${leftPage.link}">${leftPage.name}</a>
        </c:if> 
    </div>
    <div class="upper-page">
        <c:if test="${upperPage != null}">
            Upper<br/>
            <a href="${upperPage.link}">${upperPage.name}</a>
        </c:if> 
    </div>
    <div class="right-page">
        <c:if test="${rightPage != null}">
            Next<br/>
            <a href="${rightPage.link}">${rightPage.name}</a>
        </c:if> 
    </div>
    
</div>
