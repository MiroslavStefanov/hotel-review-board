class PageService {
    constructor(refresh) {
            this.pageButtonCount = 8;
            this.page = 0;
            this.size = 0;
            this.currentButtonClass = 'btn-blue';
            this.buttons = [];
            this.refresh = refresh;
    }

    setSize(size) {
        if(this.size < this.pageButtonCount && this.size < size && 0 < this.size) {
            this.increaseButtons(size);
        }
        this.size = size;
        this.processButtons();
    }

    initButtons(button) {
        this.buttons = [];
        for (let i = 1; i <= this.buttonsCount(); i++) {
            const newId = 'current-'+i.toString();
            button.attr('id', newId);
            button.text(i.toString());
            button.click(({id: newId}), (event) => this.selectPage(event.data.id));

            this.buttons.push(button);
            if(i < this.pageButtonCount && i < this.size) {
                let nextButton = button.clone();
                button.after(nextButton)
                button = nextButton;
            }
        }
    }

    updateButtons(){
        let stride = this.pageButtonCount;
        let page = this.page;
        const className = this.currentButtonClass;
        let size = this.size;
        let update = 0;

        const firstPage = parseInt(this.buttons[0].text()) - 1;
        if(this.page < firstPage)
            update = -1;

        const lastPage = parseInt(this.buttons[this.buttonsCount() - 1].text()) - 1;
        if(this.page > lastPage)
            update = 1;

        this.buttons.forEach(b => {
            let num = parseInt(b.text());
            num += update*stride;

            if(num === page+1){
                b.addClass(className);
            } else {
                b.removeClass(className);
            }

            b.text(num.toString());
            b.attr('hidden', num > size);
        });
    }

    processButtons() {
        // let bar = $('#pagination-bar');
        // let buttons = bar.find('.btn-page');
        // let button = buttons.last();
        // let elem;

        const button = $('#current');
        if(button.length > 0) {
            this.initButtons(button);
        }
        if(this.buttons.length === 0) {
            return;
        }

        this.updateButtons();

        let bar = $('#pagination-bar');
        bar.find('#prev').attr('disabled', this.page <= 0);
        bar.find('#next').attr('disabled', this.page >= this.size-1);

        const minPage = parseInt(this.buttons[0].text());
        let leftEllipsis = bar.find('#left-ellipsis');
        leftEllipsis.attr('hidden', minPage <= 1);

        const maxPage = parseInt(this.buttons[this.buttonsCount() - 1].text());
        let rightEllipsis = bar.find('#right-ellipsis');
        rightEllipsis.attr('hidden', maxPage >= this.size);
    }

    prev() {
        this.setPage(this.page - 1);
    }

    next() {
        this.setPage(this.page + 1);
    }

    handleEllipsis(id) {
        if(id === 'left-ellipsis')
            this.setPage(parseInt(this.buttons[0].text())-2);
        else if(id === 'right-ellipsis')
            this.setPage(parseInt(this.buttons[this.buttonsCount() - 1].text()));
    }

    selectPage(buttonId) {
        const button = $('#'+buttonId);
        if(button.length > 0) {
            const buttonText = button.text();
            if(buttonText === '...'){
                this.handleEllipsis(buttonId);
            } else {
                const newPage = parseInt(buttonText) - 1;
                this.setPage(newPage);
            }
        }
    }

    setPage(page) {
        this.page = page;
        this.refresh(this);
    }

    buttonsCount() {
        if(this.size < this.pageButtonCount) {
            return this.size;
        }
        return this.pageButtonCount;
    }

    increaseButtons(newSize) {
        let lastButton = this.buttons[this.buttonsCount() - 1];
        for (let i = this.size+1; i <= Math.min(newSize, this.pageButtonCount); i++) {
            const newId = 'current-'+i.toString();
            const newButton = lastButton.clone();
            newButton.attr('id', newId);
            newButton.text(i.toString());
            newButton.click(({id: newId}), (event) => this.selectPage(event.data.id));

            this.buttons.push(newButton);
            lastButton.after(newButton);
            lastButton = newButton
        }
    }
}

function initializePagination(pageService) {
    $("#prev").click(pageService, (event) => event.data.prev())

    let leftEllipsis = $("#left-ellipsis");
    leftEllipsis.click(pageService, (event) => event.data.selectPage(leftEllipsis));

    let rightEllipsis = $("#right-ellipsis");
    rightEllipsis.click(pageService, (event) => event.data.selectPage(rightEllipsis));

    $("#next").click(pageService, (event) => event.data.next());
}