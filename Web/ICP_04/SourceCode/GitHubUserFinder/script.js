function getGithubInfo(user, callback) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it. The function should finally return the object(it now contains the response!)
    var request = new XMLHttpRequest();
    let url = `https://api.github.com/users/${user}`;

    request.open('GET', url,true);
    request.send();
    request.onreadystatechange = function () {
        if (this.readyState == 4){    // request.status == 200
            callback(this);
        }
    }
}

function showUser(user) {
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
    $("h2").text("");
    var div = document.getElementById('avatar');
    div.innerHTML = `<img src="` + user.avatar_url + `" width="300px" height="300px"/>`;

    $("#information").text()
    var obj = $("#information").text("Name: "+user.name +"\n Github Id: "+user.id +"\n Followers: "+user.followers +"\n Following: "+user.following)
    obj.html(obj.html().replace(/\n/g,'<br/>'));

    //User's github link
    $('#github_link').html("");
    $('#github_link').append("Github Profile:").append('<a href="'+user.html_url+'" + target="_blank" +>' + user.html_url + '</a>');
}

function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    $("h2").text("");
    var div = document.getElementById('avatar');
    div.innerHTML = '';

    var div2 = document.getElementById('information');
    div2.innerHTML = '';

    $('#github_link').html("");

    $("h2").text("No such profile")
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        if (e.which == 13) {         //check if the enter(i.e return) key is pressed
            username = $(this).val();              //get what the user enters
            $(this).val("");              //reset the text typed in the input
             getGithubInfo(username, function (response)             //get the user's information and store the respsonse
             {
                 if (response.status == 200) {                  //if the response is successful show the user's details
                     showUser(JSON.parse(response.responseText));
                 } else {                                       //else display suitable message
                     noSuchUser(username);
                 }
             });
        }
    });
    $("#username").css("color","blue");
});
