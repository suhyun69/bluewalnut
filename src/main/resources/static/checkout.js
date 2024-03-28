$( document ).ready(function() {

    var ci = "12345";
    
    // 내 카드 조회
    $.ajax({
        async: false,
        type: "GET",
        url: "http://localhost:8080/v1/user/card?ci=" + ci,
        accept: "application/json;charset=UTF-8",
        contentType: "application/json;charset=UTF-8",
        // data: JSON.stringify(data),
        success: function (result) {

            result.cardRefIds.forEach((element)=>{

                // Your Card Section에 추가
                var card = $( "<li class=\"list-group-item d-flex justify-content-between lh-sm\"><div><h6 className=\"my-0\">" + element + "</h6></div></li>");
                $("#cardList").append(card);

                var cardCount =$("#cardList").children().length;
                $("#cardCount").text(cardCount);

                var option = $("<option value=" + element + ">" + element + "</option>");
                $("#cardRefId").append(option);
            })
        }
    });

    // 내 결제건 조회
    $.ajax({
        async: false,
        type: "GET",
        url: "http://localhost:8080/v1/user/checkout?ci=" + ci,
        accept: "application/json;charset=UTF-8",
        contentType: "application/json;charset=UTF-8",
        // data: JSON.stringify(data),
        success: function (result) {

            result.checkouts.forEach((element)=>{

                var checkoutId = element.id;

                // Your Checkout Section에 추가
                var checkout = $( "<li id=\"" + checkoutId + "\" class=\"list-group-item d-flex justify-content-between lh-sm\"><div><h6 className=\"my-0\">" + checkoutId + "</h6></div></li>");
                $("#checkoutList").append(checkout);

                var checkoutCount =$("#checkoutList").children().length;
                $("#checkoutCount").text(checkoutCount);

                var small = $("<small className=\"text-body-secondary\">" + element.currency + " " + element.amount + "</small>");
                $("li[id=" + checkoutId + "]").children().append(small);

                var badge = "";
                badge = $("<Br/><span class=\"badge text-bg-primary rounded-pill\">" + element.status + "</span>");
                $("li[id=" + checkoutId + "]").children().append(badge);
            })
        }
    });
    
    
    $("#registryCard").click( function() {

        var refCardId = "";

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

                refCardId = result.refCardId;

                // Your Card Section에 추가
                var card = $( "<li class=\"list-group-item d-flex justify-content-between lh-sm\"><div><h6 className=\"my-0\">" + refCardId + "</h6></div></li>");
                $("#cardList").append(card);

                var cardCount =$("#cardList").children().length;
                $("#cardCount").text(cardCount);

                var option = $("<option value=" + refCardId + ">" + refCardId + "</option>");
                $("#cardRefId").append(option);
            }
        });
    });

    $("#checkout").click( function() {

        var ci = "12345";
        var checkoutId = "";
        var token = "";

        var nextStep = true;

        var data = {}
        data.ci = ci;
        data.cardRefId = $("#cardRefId").val();
        data.amount = $("#amount").val();

        // Step 1. POST v1/payment/checkout :: payByCardRefId
        // checkoutId 조회
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/v1/payment/checkout",
            accept: "application/json;charset=UTF-8",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
            success: function (result) {

                checkoutId = result.checkoutId;

                // Your Checkout Section에 추가
                var checkout = $( "<li id=\"" + checkoutId + "\" class=\"list-group-item d-flex justify-content-between lh-sm\"><div><h6 className=\"my-0\">" + checkoutId + "</h6></div></li>");
                $("#checkoutList").append(checkout);

                var checkoutCount =$("#checkoutList").children().length;
                $("#checkoutCount").text(checkoutCount);
            },
            error: function(result) {
                nextStep = false;
                if(result.responseJSON.error != undefined) {
                    alert(result.responseJSON.error);
                    return;
                }
            }
        });

        if(!nextStep) return;

        // Step 2. GET v1/token/issue :: request token
        // token 조회
        var url = "http://localhost:8080/v1/token/issue?checkoutId=" + checkoutId;
        $.ajax({
            async: false,
            type: "GET",
            url: url,
            accept: "application/json;charset=UTF-8",
            contentType: "application/json;charset=UTF-8",
            // data: JSON.stringify(data),
            success: function (result) {
                token = result.token;
            },
            error: function(result) {
                nextStep = false;
                if(result.responseJSON.error != undefined) {
                    alert(result.responseJSON.error);
                    return;
                }
            }
        });

        if(!nextStep) return;

        // Step 3. POST v1/payment/approval :: PG사 결제 요청
        var url = "http://localhost:8080/v1/payment/approval?token=" + token;
        $.ajax({
            async: false,
            type: "POST",
            url: url,
            accept: "application/json;charset=UTF-8",
            contentType: "application/json;charset=UTF-8",
            // data: JSON.stringify(data),
            success: function (result) {
            },
            error: function(result) {
                nextStep = false;
                if(result.responseJSON.error != undefined) {
                    alert(result.responseJSON.error);
                    return;
                }
            }
        });

        if(!nextStep) return;

        // Step 4. GET /v1/user/checkout :: 내 결제건 조회
        var url = "http://localhost:8080/v1/user/checkout?ci=" + ci;
        $.ajax({
            async: false,
            type: "GET",
            url: url,
            accept: "application/json;charset=UTF-8",
            contentType: "application/json;charset=UTF-8",
            // data: JSON.stringify(data),
            success: function (result) {

                result.checkouts.forEach((element)=>{
                    var small = $("<small className=\"text-body-secondary\">" + element.currency + " " + element.amount + "</small>");
                    $("li[id=" + checkoutId + "]").children().append(small);

                    var badge = "";
                    badge = $("<Br/><span class=\"badge text-bg-primary rounded-pill\">" + element.status + "</span>");
                    $("li[id=" + checkoutId + "]").children().append(badge);
                })
            },
            error: function(result) {
                nextStep = false;
                if(result.responseJSON.error != undefined) {
                    alert(result.responseJSON.error);
                    return;
                }
            }
        });

    });
});