$(document).ready(function(){

    $(".get > .dishBtn").click(function(){
        var id = document.getElementById('idForDishGet').value;
        getDish(id);
    });
    $(".post > .dishBtn").click(function(){
        let dish = {
            name: $('.post >>> input[name="name"]').val(),
            description: $('.post >>> input[name="description"]').val(),
            ingredientsId: $('.post >> select[name="ingredients"]').val()
        };
        console.log(dish);
        console.log(JSON.stringify(dish));
        addDish(dish);


    });
    $(".put > .dishBtn").click(function(){

        id = $('.put >>> input[name="id"]').val();
        name_ = $('.put >>> input[name="name"]').val();
        description = $('.put >>> input[name="description"]').val();
        ingredientIds = $('.put >> select[name="ingredients"]').val();

        let dish = {
        };
        if (name_ !== ""){
            dish.name = name_;
        }
        if (description !== ""){
            dish.description = description;
        }
        if (ingredientIds !== ""){
            dish.ingredientsId = ingredientIds;
        }
        console.log(dish);
        console.log(JSON.stringify(dish));
        updateDish(id, dish);
    });

    $(".delete > .dishBtn").click(function(){
        var id = document.getElementById('idForDishDelete').value;
        deleteDish(id);
    });
    $(".get > .ingredientBtn").click(function(){
        var id = document.getElementById('idForIngredientGet').value;
        getIngredient(id);
    });
    $(".post > .ingredientBtn").click(function(){
        let ingredient = {
            name: $('.post >>> input[name="ingredientName"]').val(),
            block: $('.post >> select[name="statusForPost"]').val()
        };
        console.log(ingredient);
        console.log(JSON.stringify(ingredient));
        addIngredient(ingredient);
    });
    $(".put > .ingredientBtn").click(function(){

        let id = $('.ingredient > .put >>> input[name="id"]').val();
        let name = $('.ingredient > .put >>> input[name="ingredientName"]').val();
        let block = $('.ingredient > .put >> select[name="statusForPut"]').val();

        let ingredient = {
        };
        if (name !== ""){
            ingredient.name = name;
        }
        if (block !== ""){
            ingredient.block = block;
        }
        console.log(ingredient);
        console.log(JSON.stringify(ingredient));
        updateIngredient(id, ingredient);
    });
    $(".delete > .ingredientBtn").click(function(){
        var id = document.getElementById('idForIngredientDelete').value;
        deleteIngredient(id);
    });

});

function getDish(id){

    $.ajax({
        url: "/dish/" + id,
        type: "GET",
        dataType: 'json',
    }).done(function (result) {
        document.getElementById("dish-get").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("dish-get").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function addDish(dish) {
    $.ajax({
        url: "/dish",
        type: "POST",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(dish),
    }).done(function (result) {
        document.getElementById("dish-post").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("dish-post").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function updateDish(id, dish) {
    $.ajax({
        url: "/dish/" + id,
        type: "PUT",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(dish),
    }).done(function (result) {
        document.getElementById("dish-put").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("dish-put").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}

function deleteDish(id){

    $.ajax({
        url: "/dish/" + id,
        type: "DELETE",
        dataType: 'json',
    }).done(function (result) {
        document.getElementById("dish-delete").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("dish-delete").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function getIngredient(id){

    $.ajax({
        url: "/ingredient/" + id,
        type: "GET",
        dataType: 'json',
    }).done(function (result) {
        document.getElementById("ingredient-get").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("ingredient-get").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function addIngredient(ingredient) {
    $.ajax({
        url: "/ingredient",
        type: "POST",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(ingredient),
    }).done(function (result) {
        document.getElementById("ingredient-post").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("ingredient-post").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function updateIngredient(id, ingredient) {
    $.ajax({
        url: "/ingredient/" + id,
        type: "PUT",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(ingredient),
    }).done(function (result) {
        document.getElementById("ingredient-put").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("ingredient-put").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function deleteIngredient(id){

    $.ajax({
        url: "/ingredient/" + id,
        type: "DELETE",
        dataType: 'json',
    }).done(function (result) {
        document.getElementById("ingredient-delete").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("ingredient-delete").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
