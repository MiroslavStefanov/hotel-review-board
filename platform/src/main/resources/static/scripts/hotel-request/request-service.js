function createRequest(hotelId, review, successCallback) {
    $.ajax({
        type: 'POST',
        url: '/hotels/review/' + hotelId,
        dataType: 'json',
        contentType: 'text/plain',
        data: review,
        beforeSend: (request) => {
            let _tc = $("meta[name='_csrf']").attr("content");
            let _hc = $("meta[name='_csrf_header']").attr("content");
            request.setRequestHeader(_hc, _tc);
        },
        success: successCallback
    })
}

function deleteRequest(requestId, successCallback) {
    if(requestId !== null) {
        $.ajax({
            type: 'DELETE',
            url: '/hotelRequests/' + requestId,
            dataType: 'json',
            contentType: 'application/json',
            beforeSend: (request) => {
                let _tc = $("meta[name='_csrf']").attr("content");
                let _hc = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(_hc, _tc);
            },
            success: successCallback
        })
    }
}

function acceptRequest(requestId, hotelId, userId, successCallBack) {
    deleteRequest(requestId, () => {
        $.ajax({
            type: 'PATCH',
            url: '/hotels/' + hotelId + '/add',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                attendantId: userId
            }),
            beforeSend: (request) => {
                let _tc = $("meta[name='_csrf']").attr("content");
                let _hc = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(_hc, _tc);
            },
            success: successCallBack
        });
    });
}

function removeAttendant(hotelId, userId, successCallback) {
    $.ajax({
        type: 'PATCH',
        url: '/hotels/' + hotelId + '/remove',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            attendantId: userId
        }),
        beforeSend: request => {
            let _tc = $("meta[name='_csrf']").attr("content");
            let _hc = $("meta[name='_csrf_header']").attr("content");
            request.setRequestHeader(_hc, _tc);
        },
        success: successCallback
    });
}