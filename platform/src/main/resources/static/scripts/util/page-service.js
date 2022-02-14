class PageService {
    constructor() {
            this.pageButtonCount = 8;
            this.page = 0;
            this.size = 0;
            this.currentButtonClass = 'btn-blue';
            this.buttons = [];
    }

    setSize(size) {
        this.size = size;
        this.processButtons();
    }

    initButtons(button) {
        this.buttons = [];
        for (let i = 1; i <= this.pageButtonCount && i <= this.size; i++) {
            const newId = '#current-'+i.toString();
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

        const lastPage = parseInt(this.buttons[this.size - 1].text()) - 1;
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
        this.updateButtons();

        let bar = $('#pagination-bar');
        bar.find('#prev').attr('disabled', this.page <= 0);
        bar.find('#next').attr('disabled', this.page >= this.size-1);

        const minPage = parseInt(this.buttons[0].text());
        let leftEllipsis = bar.find('#left-ellipsis');
        leftEllipsis.attr('hidden', minPage <= 1);

        const maxPage = parseInt(this.buttons[this.size - 1].text());
        let rightEllipsis = bar.find('#right-ellipsis');
        rightEllipsis.attr('hidden', maxPage >= this.size);
    }

    prev() {
        this.page--;
    }

    next() {
        this.page++;
    }

    handleEllipsis(id) {
        if(id === 'left-ellipsis')
            this.page = parseInt(this.buttons[0].text())-2;
        else if(id === 'right-ellipsis')
            this.page = parseInt(this.buttons[this.size - 1].text());
    }

    selectPage(buttonId) {
        let button = $(buttonId);
        if(button.innerHTML === '...'){
            this.handleEllipsis(button);
        } else {
            this.page = parseInt(button.innerHTML) - 1;
        }
    }
}