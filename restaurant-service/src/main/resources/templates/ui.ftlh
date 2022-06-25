<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Web UI</title>
    <meta charset="utf-8">
    <#--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.js"></script>-->
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <script src="/js/ajax.js"></script>
</head>
<body>
<h1>Dish</h1>
<div class="dish">
    <div class="get">
        <h4>GET</h4>
        <input type="text" class="dishInput" id="idForDishGet" name="idForGet" value="">
        <button class="dishBtn">GET</button>
        <div id="dish-get"></div>

    </div>

    <div class="post">
        <h4>POST</h4>
        <p><label>Название: <input type="text" name="name" value="" required></label></p>
        <p><label>Описание: <input type="text" name="description" value="" required></label></p>
        <p>Выберите ингридиенты: <select multiple name="ingredients" required>
                <option disabled>Выберите ингридиенты</option>
                <#list ingredients as ingredient>
                    <option value="${ingredient.id}">${ingredient.name}</option>
                </#list>
            </select></p>
        <button class="dishBtn">POST</button>
        <div id="dish-post"></div>

    </div>

    <div class="put">
        <h4>PUT</h4>
        <p><label>ID: <input type="text" name="id" value="" required></label></p>
        <p><label>Название: <input type="text" name="name" value="" ></label></p>
        <p><label>Описание: <input type="text" name="description" value="" ></label></p>
        <p>Выберите ингридиенты: <select multiple name="ingredients" >
                <option value="">Выберите ингридиенты</option>
                <#list ingredients as ingredient>
                    <option value="${ingredient.id}">${ingredient.name}</option>
                </#list>
            </select></p>
        <button class="dishBtn">PUT</button>
        <div id="dish-put"></div>

    </div>

    <div class="delete">
        <h4>DELETE</h4>
        <input type="text" id="idForDishDelete" class="dishInput" value="">
        <button class="dishBtn">DELETE</button>
        <div id="dish-delete"></div>

    </div>


</div>

<h1>Ingredients</h1>
<div class="ingredient">
    <div class="get">
        <h4>GET</h4>
        <input type="text" id="idForIngredientGet" class="ingredientInput" value="">
        <button class="ingredientBtn">GET</button>
        <div id="ingredient-get"></div>
    </div>
    <div class="post">
        <h4>POST</h4>
        <p><label>Название ингридиента: <input type="text" name="ingredientName" value="" required></label></p>
        <p>Выберите состояние ингридента: <select name="statusForPost" required>
                <option disabled>Выберите состояние ингридиента</option>
                <option value="true">В блок-листе</option>
                <option value="false">В наличии</option>
            </select></p>
        <button class="ingredientBtn">POST</button>
        <div id="ingredient-post"></div>
    </div>
    <div class="put">
        <h4>PUT</h4>
        <p><label>ID: <input type="text" name="id" value="" required></label></p>
        <p><label>Название ингридиента: <input type="text" name="ingredientName" value=""></label></p>
        <p>Выберите состояние ингридиента: <select name="statusForPut" >
                <option value="">Выберите состояние ингридиента</option>
                <option value="true">В блок-листе</option>
                <option value="false">В наличии</option>
            </select></p>
        <button class="ingredientBtn">PUT</button>
        <div id="ingredient-put"></div>
    </div>
    <div class="delete">
        <h4>DELETE</h4>
            <input type="text" class="ingredientInput" id="idForIngredientDelete" value="">
        <button class="ingredientBtn">DELETE</button>
        <div id="ingredient-delete"></div>
    </div>
</div>
</body>
</html>