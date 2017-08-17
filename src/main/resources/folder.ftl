<!DOCTYPE html>
<html>
<head>
    <title>EWebServer: ${header}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            background: #fff;
        }
    </style>
</head>
<body>
<header>
    <h1>${header}</h1>
</header>
<hr/>
<main>
		<pre id="contents">
<#list links as string>
${string}
</#list>
		</pre>
</main>
<hr/>
</body>
</html>