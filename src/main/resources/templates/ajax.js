function addNewCity() {
    //lay du lieu
    let name = $('#name').val();
    let area = $('#area').val();
    let population = $('#population').val();
    let description = $('#description').val();
    let countryId = $('#country').val();
    let countryName =$("option:selected", $("#country")).text();
    let newCity = {
        name: name,
        area: area,
        population: population,
        description: description,
        country: {
            id : countryId,
            name: countryName,
        },
    };
    console.log(JSON.stringify(newCity))
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(newCity),
        url: "http://localhost:8080/api/city",
        success: successHandler

    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

$(function () {
    successHandler();
    loadCountry();
});

function successHandler() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/list",
        success: function(data){
            let content = '<table>' + '<tr>\n' +
                '        <td>name</td>\n' +
                '        <td>area</td>\n' +
                '        <td>population</td>\n' +
                '        <td>description</td>\n' +
                '        <td>Country</td>\n' +
                '        <td>edit</td>\n' +
                '        <td>Delete</td>\n' +
                '    </tr>';
            for (let i = 0; i < data.length; i++) {
                    content += getCity(data[i]);
                }
            content += '</table>';
            document.getElementById('cityList').innerHTML = content;
        }
    });
}
function searchByName(){
    let search = $('#search').val();
    if (!search){
        successHandler()
    } else {
        $.ajax({
            type: "get",
            url: "http://localhost:8080/search/"+search,
            success: function (data) {
                let content = '<table>' + '<tr>\n' +
                    '        <td>name</td>\n' +
                    '        <td>area</td>\n' +
                    '        <td>population</td>\n' +
                    '        <td>description</td>\n' +
                    '        <td>Country</td>\n' +
                    '        <td>edit</td>\n' +
                    '        <td>Delete</td>\n' +
                    '    </tr>';
                for (let i = 0; i < data.length; i++) {
                    if (getCity(data[i])==data.country.name){
                        content += getCity(data[i]);
                    }            }
                content += '</table>';
                document.getElementById('cityList').innerHTML = content;
            }
        });
    } }
function loadCountry(){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/country",
        success: function (data) {
            let listCountry = '<select id="country">';
            for (let i = 0; i < data.length; i++) {
                listCountry += '<option value="' + data[i].id + '" ' +  '>' + data[i].name + '</option>';
            }
            listCountry += '</select>';
            document.getElementById('country').innerHTML = listCountry;
        }
    });
}

function getCity(city) {
    let log = `<tr>
    <td>${city.name}</td>
    <td>${city.area}</td>
    <td>${city.population}</td>
    <td>${city.description}</td>
    <td>${city.country.name}</td>
    <td><button class="editCity" onclick="editById(${city.id})">Edit</button></td>
    <td><button class="deleteCity" onclick="deleteById(${city.id})">Delete</button></td>
    </tr>`;
    return log;
}

function deleteById(id) {
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/api/city/" + id,
        success() {successHandler();
        }
    });
}

function editById(id) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/city/" + id,
        success: function (city) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/country",
                success: function (data) {
                    let listCountry = '<select id="mySelect">';
                    for (let i = 0; i < data.length; i++) {
                        let selected = '';
                        if (city.country!=null){
                            if (  data[i].name == city.country.name) {
                                selected = 'selected';
                            }}
                        listCountry += '<option value="' + data[i].id + '" ' + selected + '>' + data[i].name + '</option>';
                    }
                    listCountry += '</select>';
                    // Đổ thông tin vào các trường input
                    $('#name').val(city.name);
                    $('#area').val(city.area);
                    $('#population').val(city.population);
                    $('#description').val(city.description);
                    $('#idItem').val(id);
                    $('#country').html(listCountry);
                    $('#country option:selected').text();
                }
            });
        }
    });
}
function postEdit() {
    // Lấy thông tin hình ảnh sau khi chỉnh sửa
    let name = $('#name').val();
    let area = $('#area').val();
    let population = $('#population').val();
    let description = $('#description').val();
    let countryId = $('#country').val();
    let countryName =$("option:selected", $("#country")).text();
    let id = $('#idItem').val();
    let updatedCity = {
        id: id,
        name: name,
        area: area,
        population: population,
        description: description,
        country: {
            id : countryId,
            name: countryName,
        },
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "PUT",
        data: JSON.stringify(updatedCity),
        url: "http://localhost:8080/api/city/" + id,
        success: function () {
            successHandler();
        }
    });
}