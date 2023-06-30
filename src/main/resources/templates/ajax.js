function addNewCountry() {
    let name = $('#name').val();
    // let CountryId = $('#mySelect').val();
    // let CountryName =$("option:selected", $("#mySelect")).text();
    let newCountry = {
        name: name,
    };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(newCountry),
        url: "http://localhost:8080/",
        success: successHandler

    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}
function getCountry() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/country",
        success: function (data) {
            let listCountry = '<select >';
            for (let i = 0; i < data.length; i++) {
                listCountry += '<option value="' + data[i].id + '" ' + '>' + data[i].name + '</option>';
            }
            listCountry += '</select>';
            $('#myCountry').val(listCountry);
        }
    });
}
function addNewCity() {
    //lay du lieu
    let name = $('#name').val();
    let area = $('#area').val();
    let population = $('#population').val();
    let description = $('#description').val();
    let newCity = {
        name: name,
        area: area,
        population: population,
        description: description,
    };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(newCity),
        url: "http://localhost:8080/city",
        success: successHandler
    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}


successHandler();
function successHandler() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/list",
        success: function (data) {
            let content = '<table>' + '<tr>\n' +
                '        <td>name</td>\n' +
                '        <td>area</td>\n' +
                '        <td>population</td>\n' +
                '        <td>description</td>\n' +
                '        <td>country</td>\n' +
                '        <td>edit</td>\n' +
                '        <td>Delete</td>\n' +
                '    </tr>';
            for (let i = 0; i < data.length; i++) {
                content += getCountry(data[i]);
            }
            content += '</table>';
            document.getElementById('cityList').innerHTML = content;
        }
    });
}