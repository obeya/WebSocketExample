<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <input id="text" type="text"/>  
    <button onclick="send()">发送消息</button>  
    <hr/>       
    <button onclick="closeWebSocket()">关闭WebSocket连接</button>  
    <hr/>  
    <div id="message"></div>  
</body>

<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
    	websocket = new WebSocket('ws://127.0.0.1:8080/WebScoketExample/websocketUrl');
	} else {
		alert('No support WebSocket!');
	}
    
    websocket.onerror = function () {
    	setMessageInnerHTML('WebSocket连接发生错误');
    }
    
    websocket.onopen = function () {
    	setMessageInnerHTMl('连接成功');
    }
    
    websocket.onmessage = function () {
    	setMessageInnerHTML(event.data);
    }
    
    websocket.onclose = function () {
    	setMessageInnerHTML('连接关闭');
    }
    
    websocket.onbeforunload = function () {
    	closeWebSocket();
    }
    
    function closeWebSocket() {
    	websocket.close();
    }
    
    function send() {
    	var message = document.getElementById('text');
    	websocket.send(message);
    }

</script>
</html>