class HotelPageHandler {
    arguments = {
        page: 0,
        size: 1,
        name: null
    }

    constructor(baseUrl, responseHandler) {
        this.baseUrl = baseUrl;
        this.responseHandler = responseHandler;
    }

    buildUrl() {
        let url = this.baseUrl;
        let isFirst = true;
        Object.entries(this.arguments).forEach(([key, value]) => {
            if(value !== null && value !== undefined && value !== '') {
                if(isFirst){
                    url += '?'
                    isFirst = false;
                } else {
                    url += '&'
                }
                url += key + '=' + value;
            }
        });
        return url;
    }

    handlePage(pageService) {
        this.arguments.page = pageService.page;
        let url = this.buildUrl();
        $.ajax({
            type: 'GET',
            url: url,
            success: (data) => {
                pageService.setSize(data.totalPages);
                this.responseHandler(data);
            }
        })
    }
}