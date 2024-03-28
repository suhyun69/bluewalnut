$( document ).ready(function() {
    
    
    // 초기화 시 DB 조회
    
    
    $("#registryCard").click( function() {

        var data = {}
        data.ci = "12345";
        data.cardNo = $("#cardNo").val();

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/v1/user/card",
            // dataType : "json",
            accept: "application/json;charset=UTF-8",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
            success: function (result) {

                // Your Card Section에 추가
                var card = $( "<li class=\"list-group-item d-flex justify-content-between lh-sm\"><div><h6 id=\"cardRefId\" className=\"my-0\">" + result.refCardId + "</h6></div></li>");
                $("#cardList").append(card);

                var cardCount =$("#cardList").children().length;
                $("#cardCount").text(cardCount);
            }
        });
    });
});