<#macro fetch_page contextPath=rc.contextPath globalContext={} additionalStylesheets=[]>
	<#compress>
        <#assign basePageTitle = "IP Australia | SDSM" />
        <#assign currentPageTitle = "" />
    <!DOCTYPE html>
    
    <html lang="en" ng-app="MyApp">
		<head>
			<meta name="viewport" content="width=960, initial-scale=1">
	        <link rel="icon" type="image/png" href="${contextPath}/resources/images/favicon.png">
	        <meta charset="iso-8859-1">
			<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
				integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
				crossorigin="anonymous">
			<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.2/angular.min.js"></script>
			<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.2/angular-animate.js"></script>
			<script type="text/javascript" src="https://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.2.5.min.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/js/app.js"></script>
			<link rel="stylesheet" href="${contextPath}/resources/css/fetch.min.css"></link>
			<link rel="stylesheet" href="${contextPath}/resources/css/app.css"></link>
			
			
			<#if rc.requestUri?contains('/deltas')>
	            <#assign currentPageTitle = "Deltas | " />
	        <#elseif rc.requestUri?contains('/ranges')>
	            <#assign currentPageTitle = "Ranges | " />
	        <#elseif rc.requestUri?contains('/full')>
	            <#assign currentPageTitle = "Biblio Range | " />
	        <#elseif rc.requestUri?contains('/ccheck')>
	            <#assign currentPageTitle = "Check | " />
	        <#elseif rc.requestUri?contains('/run')>
	            <#assign currentPageTitle = "Run | " />
	        <#elseif rc.requestUri?contains('/changeSet/search')>
                <#assign currentPageTitle = "ChangeSet Search | " />
            <#elseif rc.requestUri?contains('/ipRight/search')>
                <#assign currentPageTitle = "IP Right Search| " />
	        </#if>
		
	
	        <title>${currentPageTitle + basePageTitle}</title>
			
		</head>
		<body>
		    <header class="grid-100">
		        <div class="logo grid-25" title="Australian Government IP Australia"></div>
		
	            <div class="middle grid-50">
                    <ul>
                        <li role="presentation">
                            <a id="qa-global-search"
                               tabindex="-1"
                               class="${rc.requestUri?contains('/changeSetAdmin')?then('active', '')}"
                               href="${contextPath}/changeSetAdmin"
                               title="ChangeSet Admin">ChangeSet Admin</a>
                        </li>
                        <li role="presentation">
                            <a id="qa-global-search"
                               tabindex="-1"
                               class="${rc.requestUri?contains('/ipRightAdmin')?then('active', '')}"
                               href="${contextPath}/ipRightAdmin"
                               title="IP Right Admin">IP Right Admin</a>
                        </li>
                        <li role="presentation">
                            <a id="qa-global-search"
                               tabindex="-1"
                               class="${rc.requestUri?contains('/deltas')?then('active', '')}"
                               href="${contextPath}/deltas"
                               title="Deltas">Deltas</a>
                        </li>
                        <li role="presentation">
                            <a id="qa-global-search"
                               tabindex="-1"
                               class="${rc.requestUri?contains('/ranges')?then('active', '')}"
                               href="${contextPath}/ranges"
                               title="Ranges">Ranges</a>
                        </li>
                        <li role="presentation">
                            <a id="qa-global-range"
                               tabindex="-1"
                               class="${rc.requestUri?contains('/full')?then('active', '')}"
                               href="${contextPath}/full"
                               title="Full Extract">Full Extract</a>
                        </li>
                        <li role="presentation">
                            <a id="qa-global-record"
                               tabindex="-1"
                               class="${rc.requestUri?contains('/ccheck')?then('active', '')}"
                               href="${contextPath}/ccheck"
                               title="Consistency Checks">Consistency Checks</a>
                         <li role="presentation">
                            <a id="qa-global-record"
                               tabindex="-1"
                               class="${rc.requestUri?contains('/run')?then('active', '')}"
                               href="${contextPath}/run"
                               title="Run">Run</a>
                        </li>
                    </ul>
	            </div>
		    </header>
			
			<div id="pageContent">
		        <#nested/>
		    </div>
		</body>
	</html>
	
	</#compress>
</#macro>
