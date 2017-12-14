<html>
	<head>
		
	</head>
	
	<body>
		<#list articleList as tempArti >
			<a href="/zzz/front/${currentDate}/${tempArti.id}.html">${tempArti.username}</a><br/>
		</#list>
	</body>
</html>