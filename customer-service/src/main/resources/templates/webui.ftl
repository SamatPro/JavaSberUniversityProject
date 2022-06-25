<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Simple Web UI for REST-API</title>
    <meta charset="utf-8">
<#--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.js"></script>-->
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <script src="/js/ajax.js"></script>
</head>
<body>
<h1>Customers</h1>
<div class="customer">
    <div class="get">
        <h4>GET</h4>
        <input type="text" class="customerInput" value="">
        <button class="customerBtn">GET</button>
        <div id="customer-get"></div>

    </div>

    <div class="post">
        <h4>POST</h4>
        <p><label>Имя: <input type="text" name="firstName" value="" required></label></p>
        <p><label>Фамилия: <input type="text" name="lastName" value="" required></label></p>
        <p><label>Эл. почта: <input type="email" name="email" value="" required></label></p>
        <p><label>Пароль: <input type="password" name="password" value="" required></label></p>
        <p>Выберите город: <select size="1" name="city" required>
                <option disabled>Выберите город</option>
                <#list cities as city>
                    <option value="${city.id}">${city.name}</option>
                </#list>
            </select></p>
        <button class="customerBtn">POST</button>
        <div id="customer-post"></div>

    </div>

    <div class="put">
        <h4>PUT</h4>
        <p><label>ID: <input type="text" name="id" value="" required></label></p>
        <p><label>Имя: <input type="text" name="firstName" value="" ></label></p>
        <p><label>Фамилия: <input type="text" name="lastName" value="" ></label></p>
        <p><label>Эл. почта: <input type="email" name="email" value="" ></label></p>
        <p><label>Пароль: <input type="password" name="password" value="" ></label></p>
        <p>Выберите город: <select size="1" name="city" >
                <option value="">Выберите город</option>
                <#list cities as city>
                    <option value="${city.id}">${city.name}</option>
                </#list>
            </select></p>
        <button class="customerBtn">PUT</button>
        <div id="customer-put"></div>

    </div>

    <div class="delete">
        <h4>DELETE</h4>
        <input type="text" class="customerInput" value="">
        <button class="customerBtn">DELETE</button>
        <div id="customer-delete"></div>

    </div>


</div>

<h1>Cities</h1>
<div class="city">
    <div class="get">
        <h4>GET</h4>
        <input type="text" class="cityInput" value="">
        <button class="cityBtn">GET</button>
        <div id="city-get"></div>

    </div>

    <div class="post">
        <h4>POST</h4>
        <p><label>Название города: <input type="text" name="name" value="" required></label></p>
        <p>Выберите статус: <select size="1" name="status" required>
                <option disabled>Выберите статус</option>
                <option value="SERVED">Обслуживаемый</option>
                <option value="NOT_SERVED">Необслуживаемый</option>
            </select></p>
        <button class="cityBtn">POST</button>
        <div id="city-post"></div>
    </div>
    <div class="put">
        <h4>PUT</h4>
        <p><label>ID: <input type="text" name="id" value="" required></label></p>
        <p><label>Название города: <input type="text" name="name" value=""></label></p>
        <p>Выберите статус: <select size="1" name="status" >
                <option value="">Выберите статус</option>
                <option value="SERVED">Обслуживаемый</option>
                <option value="NOT_SERVED">Необслуживаемый</option>
            </select></p>
        <button class="cityBtn">PUT</button>
        <div id="city-put"></div>
    </div>
    <div class="delete">
        <h4>DELETE</h4>
        <input type="text" class="cityInput" value="">
        <button class="cityBtn">DELETE</button>
        <div id="city-delete"></div>

    </div>

</div>




</body>
</html>