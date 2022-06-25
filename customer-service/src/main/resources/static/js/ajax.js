$(document).ready(function(){

    $(".get > .customerBtn").click(function(){
        var id = $(".get > .customerInput").val();
        getCustomer(id);
    });
    $(".post > .customerBtn").click(function(){
        let customer = {
            firstName: $('.post >>> input[name="firstName"]').val(),
            lastName: $('.post >>> input[name="lastName"]').val(),
            email: $('.post >>> input[name="email"]').val(),
            password: $('.post >>> input[name="password"]').val(),
            cityId: $('.post >> select[name="city"]').val()
        };
        console.log(customer);
        console.log(JSON.stringify(customer));
        addCustomer(customer);


    });
    $(".put > .customerBtn").click(function(){

        id = $('.put >>> input[name="id"]').val();
        firstName = $('.put >>> input[name="firstName"]').val();
        lastName = $('.put >>> input[name="lastName"]').val();
        email = $('.put >>> input[name="email"]').val();
        password = $('.put >>> input[name="password"]').val();
        cityId = $('.put >> select[name="city"]').val();

        let customer = {
        };
        if (firstName !== ""){
            customer.firstName = firstName;
        }
        if (lastName !== ""){
            customer.lastName = lastName;
        }
        if (email !== ""){
            customer.email = email;
        }
        if (password !== ""){
            customer.password = password;
        }
        if (cityId !== ""){
            customer.cityId = cityId;
        }
        console.log(customer);
        console.log(JSON.stringify(customer));
        updateCustomer(id, customer);
    });

    $(".delete > .customerBtn").click(function(){
        var id = $(".delete > .customerInput").val();
        deleteCustomer(id);
    });
    $(".get > .cityBtn").click(function(){
        var id = $(".get > .cityInput").val();
        getCity(id);
    });
    $(".post > .cityBtn").click(function(){
        let city = {
            name: $('.post >>> input[name="name"]').val(),
            status: $('.post >> select[name="status"]').val()
        };
        console.log(city);
        console.log(JSON.stringify(city));
        addCity(city);
    });
    $(".put > .cityBtn").click(function(){

        let id = $('.city > .put >>> input[name="id"]').val();
        let cityName = $('.city > .put >>> input[name="name"]').val();
        let status = $('.city > .put >> select[name="status"]').val();

        let city = {
        };
        if (cityName !== ""){
            city.name = cityName;
        }
        if (status !== ""){
            city.status = status;
        }
        console.log(city);
        console.log(JSON.stringify(city));
        updateCity(id, city);
    });
    $(".delete > .cityBtn").click(function(){
        let id = $(".delete > .cityInput").val();
        deleteCity(id);
    });

});

function getCustomer(id){

    $.ajax({
        url: "/customers/" + id,
        type: "GET",
        dataType: 'json',
    }).done(function (result) {
        document.getElementById("customer-get").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("customer-get").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function addCustomer(customer) {
    $.ajax({
        url: "/customers",
        type: "POST",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(customer),
    }).done(function (result) {
        document.getElementById("customer-post").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("customer-post").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function updateCustomer(id, customer) {
    $.ajax({
        url: "/customers/" + id,
        type: "PUT",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(customer),
    }).done(function (result) {
        document.getElementById("customer-put").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("customer-put").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}

function deleteCustomer(id){

    $.ajax({
        url: "/customers/" + id,
        type: "DELETE",
        dataType: 'json',
    }).done(function (result) {
        document.getElementById("customer-delete").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("customer-delete").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function getCity(id){

    $.ajax({
        url: "/cities/" + id,
        type: "GET",
        dataType: 'json',
    }).done(function (result) {
        document.getElementById("city-get").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("city-get").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function addCity(city) {
    $.ajax({
        url: "/cities",
        type: "POST",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(city),
    }).done(function (result) {
        document.getElementById("city-post").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("city-post").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function updateCity(id, city) {
    $.ajax({
        url: "/cities/" + id,
        type: "PUT",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(city),
    }).done(function (result) {
        document.getElementById("city-put").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("city-put").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
function deleteCity(id){

    $.ajax({
        url: "/cities/" + id,
        type: "DELETE",
        dataType: 'json',
    }).done(function (result) {
        document.getElementById("city-delete").innerHTML = JSON.stringify(result);
        console.log(result)
    }).fail(function (err) {
        document.getElementById("city-delete").innerHTML = JSON.stringify(err);
        console.log(err)
    })
}
