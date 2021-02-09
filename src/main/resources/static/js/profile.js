(function($) {
    // SETUP AJAX
    $.ajaxSetup({
        headers:
            {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')}
    });

    // FOLLOW BTN SUBMIT A FOLLOW REQUEST AJAX
    $( "#addFriend" ).click(function() {
        let formData = {"friendId" : userId}
        let url = "/users/follow";
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: url,
            data: JSON.stringify(formData),
            dataType: 'json'
        });
        setTimeout(function () {
            $('#addFriend').text()== "Follow" ? $('#addFriend').text('Unfollow'): $('#addFriend').text('Follow');
        },1000)
    });

})(jQuery);
